/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.db.DAOVendavel;
import br.senac.tads.housebay.exception.ProdutoException;
import br.senac.tads.housebay.model.Produto;
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

@WebServlet(name = "Produtos", urlPatterns = {"/produtos", "/produtos/new", "/produtos/create", "/produtos/edit", "/produtos/update", "/produtos/destroy"})
public class Produtos extends HttpServlet {
    /*  ROTAS:
     *  GET:  /produtos             => Lista de produtos
     *  GET:  /produtos?id=xxx      => Detalhes do produto
     *  GET:  /produtos/new         => Formulário para criar
     *  POST: /produtos/create      => Cria produto
     *  GET:  /produtos/edit?id=xxx => Formulário para alterar
     *  POST: /produtos/update      => Altera produto
     *  POST: /produtos/destroy     => Apaga produto
     *
     *  Session:
     *  "mensagem"  => Notificações de alterações
     *  "erro"      => Notificações de erros
     *  "produto"       => Produto com erro (retornar ao formulário)
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
        
        if (url.equals("/produtos") && id == null) {
            //Lista produtos
            String query = request.getParameter("q");
            List produtos = DAOVendavel.search(query);
            request.setAttribute("produtos", produtos);
            responseURL = "/WEB-INF/produto/produto_list.jsp";
        } else if (url.equals("/produtos") && id != null) {
            //Detalhes do produto id
            Produto produto = (Produto) DAOVendavel.read(Long.parseLong(id));
            request.setAttribute("produto", produto);
            responseURL = "/WEB-INF/produto/produto_show.jsp";
        } else if (url.equals("/produtos/new") && id == null) {
            //Form novo produto
            newForm(request, response, sessao);
            return;
        } else if (url.equals("/produtos/edit") && id != null) {
            //Form alterar produto
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
        
        if (url.equals("/produtos/destroy") && id != null) {
            //Deleta o produto id=xxx
            Produto produto = new Produto();
            produto.setId(Long.parseLong(id));
            if(DAOVendavel.delete(produto)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Produto removido.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/produtos");
            }
        } else if (url.equals("/produtos/create") && id == null) {
            //Cria um novo produto
            Produto produto = new Produto();
            
            produto.setNome(request.getParameter("nome"));
            produto.setDescricao(request.getParameter("descricao"));
            produto.setValor(Double.parseDouble(request.getParameter("valor")));
            
            produto.setEstoque(Integer.parseInt(request.getParameter("estoque")));
            produto.setCodigoDeBarras(request.getParameter("barras"));
            
            
            try {
                ValidateProduto.create(produto);
            } catch (ProdutoException ex) {
                System.out.println("Produto Exception: " + ex.getMessage());
                sessao.setAttribute("produto", produto);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                newForm(request, response, sessao);
                return;
            }
            
            Long newId = DAOVendavel.createProduto(produto);
            if (newId > 0) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Produto criado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/produtos?id=" + newId);
            }
        } else if (url.equals("/produtos/update") && id != null) {
            //Altera o produto id=xxx            
            Produto produto = new Produto();
            produto.getId();
            produto.setNome(request.getParameter("nome"));
            produto.setDescricao(request.getParameter("descricao"));
            produto.setValor(Double.parseDouble(request.getParameter("valor")));
            
            produto.setEstoque(Integer.parseInt(request.getParameter("estoque")));
            produto.setCodigoDeBarras(request.getParameter("barras"));
            
            try {
                ValidateProduto.update(produto);
            } catch (ProdutoException ex) {
                System.out.println("Produto Exception: " + ex.getMessage());                
                sessao.setAttribute("produto", produto);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                editForm(request, response, sessao, Long.parseLong(id));
                return;
            }
            
            if (DAOVendavel.updateProduto(produto)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Produto alterado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/produtos?id=" + id);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }    
    }
    
    private void newForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao)
        throws ServletException, IOException {
        Produto produto = (Produto) sessao.getAttribute("produto");
        if (produto != null) {
            request.setAttribute("produto", produto);
            sessao.removeAttribute("produto");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/produto/produto_form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao, Long id)
        throws ServletException, IOException {
        Produto produto = (Produto) sessao.getAttribute("produto");
        if (produto == null) {
            produto = (Produto) DAOVendavel.read(id);
        } else {
            sessao.removeAttribute("produto");
        }
        request.setAttribute("produto", produto);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/produto/produto_form.jsp");
        dispatcher.forward(request, response);
    }
    
}
