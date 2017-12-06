/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.controller.validate.ValidateVenda;
import br.senac.tads.housebay.db.DAOVenda;
import br.senac.tads.housebay.exception.VendaException;
import br.senac.tads.housebay.model.Venda;
import br.senac.tads.housebay.model.Vendavel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Vendas", urlPatterns = {"/vendas", "/vendas/new", "/vendas/create" /*, "/vendas/edit", "/vendas/update", "/vendas/destroy"*/})
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
     *  "venda"     => Venda (retornar ao formulário)
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

        //response.setContentType("text/html;charset=UTF-8");
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
            sessao.setAttribute("venda", parseForm(request));
            newForm(request, response, sessao);
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
        List erros = (List) sessao.getAttribute("erro");
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
        List erros = (List) sessao.getAttribute("erro");
        //System.out.println("DEBUG: post method");

        //response.setContentType("text/html;charset=UTF-8");
        if (url.equals("/vendas/create") && id == null) {
            //Cria um novo venda
            Venda venda = null;
            try {
                venda = parseForm(request);
                ValidateVenda.create(venda);
            } catch (VendaException ex) {
                System.out.println("Venda Exception: " + ex.getMessage());
                sessao.setAttribute("venda", venda);
                if (erros == null) {
                    erros = new ArrayList();
                }
                erros.addAll(ex.getErrors());
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
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }    
    }
    
    private Venda parseForm(HttpServletRequest request) {
        Venda venda = new Venda();
        if (request.getParameter("cliente") != null && !request.getParameter("cliente").equals("")) {
            venda.setCliente(DAOVenda.getCliente(Long.parseLong(request.getParameter("cliente"))));
        }
        if (request.getParameter("empresa") != null && !request.getParameter("empresa").equals("")) {
            venda.setEmpresa(DAOVenda.getEmpresa(Long.parseLong(request.getParameter("empresa"))));
        }

        Enumeration<String> parametros = request.getParameterNames();

        while(parametros.hasMoreElements()) {
            String vendKey = parametros.nextElement();
            if(vendKey.startsWith("relacao_id_")) {
                System.err.println("Relacao: " + vendKey);
                String qtdKey = "relacao_qtd_" + vendKey.substring(11);
                int qtd = Integer.parseInt(request.getParameter(qtdKey));

                Vendavel vendavel = DAOVenda.getVendavel(Long.parseLong(request.getParameter(vendKey)));
                if(vendavel != null) {
                    venda.addCarrinho(new Venda.Relacao(vendavel, qtd, vendavel.getValor()*qtd));
                }
            }
        }
        return venda;
    }
    
    private void newForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao)
        throws ServletException, IOException {
        Venda venda = (Venda) sessao.getAttribute("venda");
        if (venda != null) {
            request.setAttribute("venda", venda);
            sessao.removeAttribute("venda");
        }
        
        request.setAttribute("empresas", DAOVenda.getEmpresaList(null));
        
        request.setAttribute("type", "new");
        List mensagens = (List) sessao.getAttribute("mensagem");
        if (mensagens != null) {
            request.setAttribute("notifications", mensagens);
            sessao.removeAttribute("mensagem");
        }
        List erros = (List) sessao.getAttribute("erro");
        if (erros != null) {
            request.setAttribute("errors", erros);
            sessao.removeAttribute("erro");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/venda/venda_form.jsp");
        dispatcher.forward(request, response);
    }    
}
