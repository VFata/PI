/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.db.DAOVenda;
import br.senac.tads.housebay.exception.VendaException;
import br.senac.tads.housebay.model.Venda;
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

@WebServlet(name = "Vendas", urlPatterns = {"/vendas", "/vendas/new", "/vendas/edit"})
public class Vendas extends HttpServlet {
    
    
    /*  ROTAS:
     *  GET:  /vendas             => Lista de vendas
     *  GET:  /vendas?id=xxx      => Detalhes do venda
     *  GET:  /vendas/new         => Formulário para criar
     *  POST: /vendas/create      => Cria venda
     *  GET:  /vendas/edit?id=xxx => Formulário para alterar
     *  POST: /vendas/update      => Altera venda
     *  POST: /vendas/destroy     => Apaga venda
     *
     *  Session:
     *  "mensagem"  => Notificações de alterações
     *  "erro"      => Notificações de erros
     *  "venda"       => Venda com erro (retornar ao formulário)
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
        
        if (url.equals("/vendas") && id == null) {
            //Lista vendas
            String query = request.getParameter("q");
            List vendas = DAOVenda.search(query);
            request.setAttribute("vendas", vendas);
            responseURL = "/WEB-INF/venda/venda_list.jsp";
        } else if (url.equals("/vendas") && id != null) {
            //Detalhes do venda id
            Venda venda = (Venda) DAOVenda.read(Long.parseLong(id));
            request.setAttribute("venda", venda);
            responseURL = "/WEB-INF/venda/venda_show.jsp";
        } else if (url.equals("/vendas/new") && id == null) {
            //Form novo venda
            newForm(request, response, sessao);
            return;
        } else if (url.equals("/vendas/edit") && id != null) {
            //Form alterar venda
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
     * /vendas?id=xxx         #deleta o venda id=xxx
     * /vendas/new            #cria um novo venda
     * /vendas/edit           #alterar o venda id=xxx
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
        
        if (url.equals("/vendas/destroy") && id != null) {
            //Deleta o venda id=xxx
            Venda venda = new Venda();
            venda.setId(Long.parseLong(id));
            if(DAOVenda.delete(venda)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Venda removido.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/vendas");
            }
        } else if (url.equals("/vendas/create") && id == null) {
            //Cria um novo venda
            Venda venda = new Venda();
            venda.setClienteId(Long.parseLong(request.getParameter("clienteId")));
            venda.setEmpresaId(Long.parseLong(request.getParameter("empresaId")));
            venda.setProdutoId(Long.parseLong(request.getParameter("produtoId")));
            
            Calendar agora = Calendar.getInstance();
            venda.setCriado((GregorianCalendar) agora);
            venda.setModificado((GregorianCalendar) agora);
            venda.setAtivo(true);          
            
            try {
                ValidateVenda.create(venda);
            } catch (VendaException ex) {
                System.out.println("Venda Exception: " + ex.getMessage());
                sessao.setAttribute("venda", venda);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                newForm(request, response, sessao);
                return;
            }
            
            Long newId = DAOVenda.create(venda);
            if (newId > 0) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Venda criado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/vendas?id=" + newId);
            }
        } else if (url.equals("/vendas/update") && id != null) {
            //Altera o venda id=xxx            
            Venda venda = new Venda();
            venda.setId(Long.parseLong(id));
            venda.setClienteId(Long.parseLong(request.getParameter("clienteId")));
            venda.setEmpresaId(Long.parseLong(request.getParameter("empresaId")));
            venda.setProdutoId(Long.parseLong(request.getParameter("produtoId")));
            
            Calendar agora = Calendar.getInstance();
            venda.setCriado((GregorianCalendar) agora);
            venda.setModificado((GregorianCalendar) agora);
            venda.setAtivo(true);
            
            try {
                ValidateVenda.update(venda);
            } catch (VendaException ex) {
                System.out.println("Venda Exception: " + ex.getMessage());                
                sessao.setAttribute("venda", venda);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                editForm(request, response, sessao, Long.parseLong(id));
                return;
            }
            
            if (DAOVenda.update(venda)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Venda alterado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/vendas?id=" + id);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }    
    }
    
    private void newForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao)
        throws ServletException, IOException {
        Venda venda = (Venda) sessao.getAttribute("venda");
        if (venda != null) {
            request.setAttribute("venda", venda);
            sessao.removeAttribute("venda");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/venda/venda_form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao, Long id)
        throws ServletException, IOException {
        Venda venda = (Venda) sessao.getAttribute("venda");
        if (venda == null) {
            venda = (Venda) DAOVenda.read(id);
        } else {
            sessao.removeAttribute("venda");
        }
        request.setAttribute("venda", venda);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/venda/venda_form.jsp");
        dispatcher.forward(request, response);
    }
    
}
