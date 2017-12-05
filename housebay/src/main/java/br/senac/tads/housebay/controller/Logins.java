/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.db.LoginUtils;
import br.senac.tads.housebay.model.Funcionario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Diego
 */
@WebServlet(name = "Login", urlPatterns = {"/index.html", "/login", "/logout", "/password"})
public class Logins extends HttpServlet {

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String forwardURL = "/WEB-INF/login/login.jsp";
        
        if(request.getServletPath().equals("/password")) {
            Funcionario user = (Funcionario) session.getAttribute("user");
            request.setAttribute("email", user.getEmail() );
            forwardURL = "/WEB-INF/login/password.jsp";
        } else if(request.getServletPath().equals("/logout")) {
            session.removeAttribute("user");
            
            List mensagens = (List) session.getAttribute("mensagem");
            if (mensagens == null) {
                mensagens = new ArrayList();
            }
            mensagens.add("Logout efetuado com sucesso.");
            session.setAttribute("mensagem", mensagens);
            
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        } else if (session.getAttribute("user") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        
        List mensagens = (List) session.getAttribute("mensagem");
        if (mensagens != null) {
            request.setAttribute("notifications", mensagens);
            session.removeAttribute("mensagem");
        }
        List erros = (List) session.getAttribute("erro");
        if (erros != null) {
            request.setAttribute("errors", erros);
            session.removeAttribute("erro");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher(forwardURL);
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        List mensagens = (List) session.getAttribute("mensagem");
        
        if(request.getServletPath().equals("/login")) {
            Funcionario user = LoginUtils.autenticar(request.getParameter("email"), request.getParameter("senha"));
            if (user != null) {
                session.setAttribute("user", user);
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Seja bem-vindo ao sistema Astec.");
                session.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/home");

            } else {
                authError(request, response);
            }
        } else if(request.getServletPath().equals("/password") && session.getAttribute("user") != null) {
            Funcionario sessionUser = (Funcionario) session.getAttribute("user");
            Funcionario user = LoginUtils.autenticar(sessionUser.getEmail(), request.getParameter("senha"));
            if (user != null) {
                LoginUtils.updateSenha(user, request.getParameter("nova-senha"));
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Senha modificada com sucesso.");
                session.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/home");
            } else {
                updateError(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
            
    }

    private void authError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        
        List mensagens = (List) session.getAttribute("mensagem");
        if (mensagens != null) {
            request.setAttribute("notifications", mensagens);
            session.removeAttribute("mensagem");
        }
        
        List erros = (List) session.getAttribute("erro");
        if (erros == null) {
            erros = new ArrayList();
        } else {
            session.removeAttribute("erro");
        }
        erros.add("Desculpe, seu e-mail ou senha está incorreto!");
        request.setAttribute("errors", erros);
        
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/login.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    private void updateError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        
        Funcionario user = (Funcionario) session.getAttribute("user");
        request.setAttribute("email", user.getEmail() );
        
        List mensagens = (List) session.getAttribute("mensagem");
        if (mensagens != null) {
            request.setAttribute("notifications", mensagens);
            session.removeAttribute("mensagem");
        }
        
        List erros = (List) session.getAttribute("erro");
        if (erros == null) {
            erros = new ArrayList();
        } else {
            session.removeAttribute("erro");
        }
        erros.add("Desculpe, sua senha está incorreta!");
        request.setAttribute("errors", erros);
        
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/password.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
