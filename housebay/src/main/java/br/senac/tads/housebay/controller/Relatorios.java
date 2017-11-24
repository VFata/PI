/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.db.RelatorioUtils;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        
        request.setAttribute("tipos", RelatorioUtils.getTipoList());
        
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
        
        int tipo = Integer.parseInt(request.getParameter("tipo"));
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        
        String strInicio = request.getParameter("inicio");
        GregorianCalendar inicio = new GregorianCalendar();
        try {
            if (strInicio != null) {
                inicio = new GregorianCalendar();
                inicio.setTime(format.parse(strInicio));
            }
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
        }
        
        String strFim = request.getParameter("fim");
        GregorianCalendar fim = new GregorianCalendar();
        try {
            if (strFim != null) {
                fim = new GregorianCalendar();
                fim.setTime(format.parse(strFim));
            }
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
        }
        
        if (!inicio.before(fim)) {
            request.setAttribute("tipos", RelatorioUtils.getTipoList());
            
            List mensagens = (List) session.getAttribute("mensagem");
            if (mensagens != null) {
                request.setAttribute("notifications", mensagens);
                session.removeAttribute("mensagem");
            }
            HashMap erros = (HashMap) session.getAttribute("erro");
            
            if (erros != null) {
                session.removeAttribute("erro");
            } else {
                erros = new HashMap();
            }
            erros.put("Data", "Data inicio não é anterior à data fim.");

            request.setAttribute("errors", erros);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/relatorio/relatorio_form.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        List<Map> result = RelatorioUtils.geraRelatorio(tipo, inicio, fim);
        
        for(Map map : result) {
            Double qtd = ( ((Long) map.get("quantidade")) / ((Long) map.get("dias")).doubleValue());
            map.put("avgqtd", String.format("%1$,.2f", qtd));
            
            Double total = ( ((Double) map.get("total")) / ((Long) map.get("dias")).doubleValue());
            map.put("avgtotal", String.format("%1$,.2f", total));
        }
        
        
        request.setAttribute("relatorio", result);
        
        List mensagens = (List) session.getAttribute("mensagem");
        if (mensagens == null) {
            mensagens = new ArrayList();
        }
        mensagens.add("Relatório entre " + strInicio + " e " + strFim + ".");
        mensagens.add("Relatório entre " + format.format(inicio.getTime()) + " e " + format.format(fim.getTime()) + ".");
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
