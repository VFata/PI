/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.db.RelatorioUtils;
import br.senac.tads.housebay.model.Empresa;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
        request.setAttribute("empresas", RelatorioUtils.getEmpresaList());
        
        List<String> mensagens = (List<String>) session.getAttribute("mensagem");
        if (mensagens != null) {
            request.setAttribute("notifications", mensagens);
            session.removeAttribute("mensagem");
        }
        List erros = (List) session.getAttribute("erro");
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
        Empresa empresa = RelatorioUtils.getEmpresa(Long.parseLong(request.getParameter("empresa")));
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        String strInicio = request.getParameter("inicio");
        GregorianCalendar inicio = new GregorianCalendar();
        try {
            if (strInicio != null) {
                inicio = new GregorianCalendar();
                inicio.setTime(format.parse(strInicio));
                //No inicio do dia
                inicio.set(Calendar.HOUR_OF_DAY, 0);
                inicio.set(Calendar.MINUTE, 0);
                inicio.set(Calendar.SECOND, 0);
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
                //No fim do dia
                fim.set(Calendar.HOUR_OF_DAY, 23);
                fim.set(Calendar.MINUTE, 59);
                fim.set(Calendar.SECOND, 59);
            }
        } catch (ParseException ex) {
            System.err.println(ex.getMessage());
        }
        
        if (!inicio.before(fim)) {
            request.setAttribute("tipos", RelatorioUtils.getTipoList());
            
            List<String> mensagens = (List<String>) session.getAttribute("mensagem");
            if (mensagens != null) {
                request.setAttribute("notifications", mensagens);
                session.removeAttribute("mensagem");
            }
            List erros = (List) session.getAttribute("erro");
            
            if (erros != null) {
                session.removeAttribute("erro");
            } else {
                erros = new ArrayList();
            }
            erros.add("Data inicio não é anterior à data fim.");
            request.setAttribute("errors", erros);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/relatorio/relatorio_form.jsp");
            dispatcher.forward(request, response);
            return;
        }
        
        List<Map> result = RelatorioUtils.geraRelatorio(tipo, empresa, inicio, fim);
        
        Long qtd = 0l;
        Double total = 0d;
        Long dias = 0l;
        for(Map map : result) {
            map.put("ftotal", "R$ " + formataValor((Double) map.get("total")) );
            
            qtd += (Long) map.get("quantidade");
            total += (Double) map.get("total");
            dias = (Long) map.get("dias");
            
            Double qtdMedia = ( ((Long) map.get("quantidade")) / ((Long) map.get("dias")).doubleValue());
            map.put("avgqtd", formataValor(qtdMedia));

            Double valorMedio = ( ((Double) map.get("total")) / ((Long) map.get("dias")));
            map.put("avgtotal", "R$ " + formataValor(valorMedio) );
        }

        request.setAttribute("relatorio", result);
        
        request.setAttribute("qtd", qtd);
        request.setAttribute("ftotal", "R$ " + formataValor(total));
        request.setAttribute("avgqtd", formataValor(qtd/dias.doubleValue()));
        request.setAttribute("avgtotal", "R$ " + formataValor(total/dias));
                
        List mensagens = (List) session.getAttribute("mensagem");
        if (mensagens == null) {
            mensagens = new ArrayList();
        }
        request.setAttribute("notifications", mensagens);
        session.removeAttribute("mensagem");
        
        List erros = (List) session.getAttribute("erro");
        if (erros != null) {
            request.setAttribute("errors", erros);
            session.removeAttribute("erro");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/relatorio/relatorio_result.jsp");
        dispatcher.forward(request, response);
    }
    
    public String formataValor(Double valor) {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator(',');
        decimalFormatSymbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00", decimalFormatSymbols);
        return decimalFormat.format(valor);
    }
}
