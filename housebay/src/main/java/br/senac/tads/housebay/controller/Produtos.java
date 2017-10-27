/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.db.DAOProduto;
import br.senac.tads.housebay.model.Vendavel;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Produtos", urlPatterns = {"/produtos", "/produtos/new", "/produtos/edit"})
public class Produtos extends HttpServlet {
    
    
    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * Responde:
     * /produtos                #lista "todos" produtos
     * /produtos?id=xxx         #detalhes do produto id=xxx
     * /produtos/new            #form para criar novo produto
     * /produtos/edit?id=xxx    #form para alterar o produto id=xxx
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
        
        System.out.println("DEBUG: get method");
        
        if (url.equals("/produtos") && id == null) {
            //Lista produtos
            String query = request.getParameter("q");
            List<Vendavel> produtos = DAOProduto.search(query);
            request.setAttribute("produtos", produtos);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/produto/produto_list.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/produtos") && id != null) {
            //Detalhes do produto id
            Vendavel produto = DAOProduto.read(Long.parseLong(id));
            request.setAttribute("produto", produto);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/produto/produto_show.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/produtos/new") && id == null) {
            //Form novo produto
            request.setAttribute("type", "new");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/produto/produto_form.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/produtos/edit") && id != null) {
            //Form alterar produto
            Vendavel produto = DAOProduto.read(Long.parseLong(id));
            request.setAttribute("produto", produto);
            request.setAttribute("type", "edit");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/produto/produto_form.jsp");
            dispatcher.forward(request, response);
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * Responde:
     * /produtos?id=xxx         #deletar do produto id=xxx
     * /produtos/new            #criar um novo produto
     * /produtos/edit           #alterar o produto id=xxx
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

        System.out.println("DEBUG: post method");
        
        if (url.equals("/produtos") && id != null) {
            //Deleta o produto id=xxx
            Vendavel produto = new Vendavel();
            produto.setId(Long.parseLong(id));
            if(DAOProduto.delete(produto)) {
                response.sendRedirect(request.getContextPath() + "/produtos");
            }
        } else if (url.equals("/produtos/new") && id == null) {
            //Cria um novo produto
            Vendavel produto = new Vendavel();
            produto.setProduto(request.getParameter("produto"));
            produto.setTipo(request.getParameter("tipo"));
            produto.setValor(request.getParameter("valor"));
            produto.setCodigoDeBarras(request.getParameter("codigobarras"));
            produto.setAtivo(true);
            
            //TODO validar ValidateProduto.create(produto)
            
            Long newID = DAOProduto.create(produto);
            if (newID > 0) {
                response.sendRedirect(request.getContextPath() + "/produtos?id=" + newID);
            }
        }else if (url.equals("/produtos/edit") && id != null) {
            //Altera o produto id=xxx
            Vendavel produto = new Vendavel();
            produto.setId(Long.parseLong(id));
            produto.setProduto(request.getParameter("produto"));
            produto.setTipo(request.getParameter("tipo"));
            produto.setValor(request.getParameter("valor"));
            produto.setCodigoDeBarras(request.getParameter("codigobarras"));
            produto.setAtivo(true);
            
            //TODO validar ValidateProduto.update(produto)
            
            if (DAOProduto.update(produto)) {
                response.sendRedirect(request.getContextPath() + "/produtos?id=" + id);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
}
