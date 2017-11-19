/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import java.io.IOException;
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
@WebServlet(name = "Relatorios", urlPatterns = {"/relatorios"})
public class Relatorios extends HttpServlet {
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
        HttpSession session = request.getSession();
        
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
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/relatorio/relatorio_form.jsp");
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
        HttpSession session = request.getSession();
        
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
        
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/relatorio/relatorio_result.jsp");
        dispatcher.forward(request, response);
    }
}
