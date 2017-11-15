/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.db.Login;
import br.senac.tads.housebay.model.Cargo;
import br.senac.tads.housebay.model.Funcionario;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        HashMap erros = (HashMap) session.getAttribute("erro");
        
        Funcionario user = Login.autenticar(request.getParameter("email"), request.getParameter("senha"));
        if (user != null) {
            session.setAttribute("user", user);
            if (null == user.getCargo()) {
                authError(request, response, session, erros);
            } else switch (user.getCargo()) {
                case GERENTE:
                    if (mensagens == null) {
                        mensagens = new ArrayList();
                    }
                    mensagens.add("Login efetuado com sucesso.");
                    session.setAttribute("mensagem", mensagens);
                    response.sendRedirect(request.getContextPath() + "/empresas");
                    break;
                case SUPORTE:
                    if (mensagens == null) {
                        mensagens = new ArrayList();
                    }
                    mensagens.add("Login efetuado com sucesso.");
                    session.setAttribute("mensagem", mensagens);
                    response.sendRedirect(request.getContextPath() + "/produtos");
                    break;
                case VENDEDOR:
                    if (mensagens == null) {
                        mensagens = new ArrayList();
                    }
                    mensagens.add("Login efetuado com sucesso.");
                    session.setAttribute("mensagem", mensagens);
                    response.sendRedirect(request.getContextPath() + "/vendas");
                    break;
                case TI:
                    if (mensagens == null) {
                        mensagens = new ArrayList();
                    }
                    mensagens.add("Login efetuado com sucesso.");
                    session.setAttribute("mensagem", mensagens);
                    response.sendRedirect(request.getContextPath() + "/funcionarios");
                    break;
                default:
                    session.removeAttribute("user");
                    authError(request, response, session, erros);
                    break;
            }
        } else {
            authError(request, response, session, erros);
        }
    }

    private void authError(HttpServletRequest request, HttpServletResponse response, HttpSession session, Map erros) throws IOException {
        session.setAttribute("email", request.getParameter("email"));
        if (erros == null) {
            erros = new HashMap();
        }
        erros.put("autenticacao", "E-mail ou Senha incorretos!");
        session.setAttribute("erro", erros);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login/login.jsp");
        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException ex) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
