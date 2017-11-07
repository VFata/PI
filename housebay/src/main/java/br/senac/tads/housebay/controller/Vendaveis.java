/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.db.DAOVendavel;
import br.senac.tads.housebay.exception.VendavelException;
import br.senac.tads.housebay.model.Vendavel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Vendaveis", urlPatterns = {"/vendaveis", "/vendaveis/new", "/vendaveis/edit"})
public class Vendaveis extends HttpServlet {
    
    
    /*  ROTAS:
     *  GET:  /vendaveis             => Lista de vendaveis
     *  GET:  /vendaveis?id=xxx      => Detalhes do vendavel
     *  GET:  /vendaveis/new         => Formulário para criar
     *  POST: /vendaveis/create      => Cria vendavel
     *  GET:  /vendaveis/edit?id=xxx => Formulário para alterar
     *  POST: /vendaveis/update      => Altera vendavel
     *  POST: /vendaveis/destroy     => Apaga vendavel
     *
     *  Session:
     *  "mensagem"  => Notificações de alterações
     *  "erro"      => Notificações de erros
     *  "vendavel"       => Vendaveis com erro (retornar ao formulário)
     */
    
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
        String url = request.getServletPath();
        String id = request.getParameter("id");
        HttpSession sessao = request.getSession();

        response.setContentType("text/html;charset=UTF-8");
        
        String responseURL;
        
        if (url.equals("/vendaveis") && id == null) {
            //Lista vendaveis
            String query = request.getParameter("q");
            List vendaveis = DAOVendavel.search(query);
            request.setAttribute("vendaveis", vendaveis);
            responseURL = "/WEB-INF/vendavel/vendavel_list.jsp";
        } else if (url.equals("/vendaveis") && id != null) {
            //Detalhes do vendavel id
            Vendavel vendavel = (Vendavel) DAOVendavel.read(Long.parseLong(id));
            request.setAttribute("vendavel", vendavel);
            responseURL = "/WEB-INF/vendavel/vendavel_show.jsp";
        } else if (url.equals("/vendaveis/new") && id == null) {
            //Form novo vendavel
            newForm(request, response, sessao);
            return;
        } else if (url.equals("/vendaveis/edit") && id != null) {
            //Form alterar vendavel
            editForm(request, response, sessao, Long.parseLong(id));
            return;
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        List mensagens = (List) sessao.getAttribute("mensagem");
        if (mensagens != null) {
            request.setAttribute("notifications", mensagens);
            sessao.removeAttribute("mensagem");
        }
        HashMap erros = (HashMap) sessao.getAttribute("erro");
        if (erros != null) {
            request.setAttribute("errors", erros);
            sessao.removeAttribute("erro");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(responseURL);
        dispatcher.forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * Responde:
     * /vendaveis?id=xxx         #deleta o vendavel id=xxx
     * /vendaveis/new            #cria um novo vendavel
     * /vendaveis/edit           #alterar o vendavel id=xxx
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = request.getServletPath();
        String id = request.getParameter("id");
        HttpSession sessao = request.getSession();
        List mensagens = (List) sessao.getAttribute("mensagem");
        HashMap erros = (HashMap) sessao.getAttribute("erro");
        //System.out.println("DEBUG: post method");

        response.setContentType("text/html;charset=UTF-8");
        
        if (url.equals("/vendaveis/destroy") && id != null) {
            //Deleta o vendavel id=xxx
            Vendavel vendavel = new Vendavel();
            vendavel.setId(Long.parseLong(id));
            if(DAOVendavel.delete(vendavel)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Vendavel removido.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/vendaveis");
            }
        } else if (url.equals("/vendaveis/create") && id == null) {
            //Cria um novo vendavel
            Vendavel vendavel = new Vendavel();

            
            Calendar agora = Calendar.getInstance();
            vendavel.setCriado((GregorianCalendar) agora);
            vendavel.setModificado((GregorianCalendar) agora);
            vendavel.setAtivo(true);          
            
            try {
                ValidateVendavel.create(vendavel);
            } catch (VendavelException ex) {
                System.out.println("Vendavel Exception: " + ex.getMessage());
                sessao.setAttribute("vendavel", vendavel);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                newForm(request, response, sessao);
                return;
            }
            
            Long newId = DAOVendavel.create(vendavel);
            if (newId > 0) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Vendavel criado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/vendaveis?id=" + newId);
            }
        } else if (url.equals("/vendaveis/update") && id != null) {
            //Altera o vendavel id=xxx            
            Vendavel vendavel = new Vendavel();
            
            Calendar agora = Calendar.getInstance();
            vendavel.setCriado((GregorianCalendar) agora);
            vendavel.setModificado((GregorianCalendar) agora);
            vendavel.setAtivo(true);
            
            try {
                ValidateVendavel.update(vendavel);
            } catch (VendavelException ex) {
                System.out.println("Vendavel Exception: " + ex.getMessage());                
                sessao.setAttribute("vendavel", vendavel);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                editForm(request, response, sessao, Long.parseLong(id));
                return;
            }
            
            if (DAOVendavel.update(vendavel)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Vendavel alterado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/vendaveis?id=" + id);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }    
    }
    
    private void newForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao)
        throws ServletException, IOException {
        Vendavel vendavel = (Vendavel) sessao.getAttribute("vendavel");
        if (vendavel != null) {
            request.setAttribute("vendavel", vendavel);
            sessao.removeAttribute("vendavel");
        }
        request.setAttribute("type", "new");
        List mensagens = (List) sessao.getAttribute("mensagem");
        if (mensagens != null) {
            request.setAttribute("notifications", mensagens);
            sessao.removeAttribute("mensagem");
        }
        HashMap erros = (HashMap) sessao.getAttribute("erro");
        if (erros != null) {
            request.setAttribute("errors", erros);
            sessao.removeAttribute("erro");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vendavel/vendavel_form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao, Long id)
        throws ServletException, IOException {
        Vendavel vendavel = (Vendavel) sessao.getAttribute("vendavel");
        if (vendavel == null) {
            vendavel = (Vendavel) DAOVendavel.read(id);
        } else {
            sessao.removeAttribute("vendavel");
        }
        request.setAttribute("vendavel", vendavel);
        request.setAttribute("type", "edit");
        
        List mensagens = (List) sessao.getAttribute("mensagem");
        if (mensagens != null) {
            request.setAttribute("notifications", mensagens);
            sessao.removeAttribute("mensagem");
        }
        HashMap erros = (HashMap) sessao.getAttribute("erro");
        if (erros != null) {
            request.setAttribute("errors", erros);
            sessao.removeAttribute("erro");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/vendavel/vendavel_form.jsp");
        dispatcher.forward(request, response);
    }
    
}
