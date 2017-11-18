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
import java.util.HashMap;
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
@WebServlet(name = "Login", urlPatterns = {"/index.html", "/login", "/logout"})
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
        
        if(request.getServletPath().equals("/logout")) {
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
        HashMap erros = (HashMap) session.getAttribute("erro");
        if (erros != null) {
            request.setAttribute("errors", erros);
            session.removeAttribute("erro");
        }
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/login.jsp");
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
        
        Funcionario user = LoginUtils.autenticar(request.getParameter("email"), request.getParameter("senha"));
        if (user != null) {
            session.setAttribute("user", user);
            if (null == user.getCargo()) {
                authError(request, response);
            } else {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Login efetuado com sucesso.");
                session.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/home");
            }
        } else {
            authError(request, response);
        }
    }

    private void authError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        
        List mensagens = (List) session.getAttribute("mensagem");
        if (mensagens != null) {
            request.setAttribute("notifications", mensagens);
            session.removeAttribute("mensagem");
        }
        
        HashMap erros = (HashMap) session.getAttribute("erro");
        if (erros == null) {
            erros = new HashMap();
        }
        erros.put("autenticacao", "Desculpe, seu e-mail ou senha está incorreto!");
        request.setAttribute("errors", erros);
        session.removeAttribute("erro");
        
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/login.jsp");
            dispatcher.forward(request, response);
        } catch (ServletException | IOException ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
