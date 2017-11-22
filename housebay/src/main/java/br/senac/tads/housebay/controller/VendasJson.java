/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.db.DAOVenda;
import br.senac.tads.housebay.model.Cliente;
import br.senac.tads.housebay.model.Produto;
import br.senac.tads.housebay.model.Servico;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Diego
 */
@WebServlet(name = "VendasJson", urlPatterns = {"/vendasJson/clientes", "/vendasJson/produtos", "/vendasJson/servicos"})
public class VendasJson extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        
        String url = request.getServletPath();
        
        try (PrintWriter out = response.getWriter()) {
            String query = request.getParameter("q");
            if(url.equals("/vendasJson/clientes")) {
                List<Cliente> clientes = DAOVenda.getClienteList(query);

                StringBuilder builder = new StringBuilder("[");
                for (int i = 0; i < clientes.size(); i++) {
                    builder.append("{\"id\": \"").append(clientes.get(i).getId())
                            .append("\",\"nome\": \"").append(clientes.get(i).getNome())
                            .append("\",\"telefone\": \"").append(clientes.get(i).getTelefone())
                            .append("\",\"email\": \"").append(clientes.get(i).getEmail())
                            .append("\",\"cpf\": \"").append(clientes.get(i).getCpf())
                            .append("\"}");
                    if (i != clientes.size()-1) {
                        builder.append(",");
                    }
                }
                builder.append("]");
                out.println(builder.toString());
            } else if (url.equals("/vendasJson/produtos")) {
                List<Produto> produtos = DAOVenda.getProdutoList(query);

                StringBuilder builder = new StringBuilder("[");
                for (int i = 0; i < produtos.size(); i++) {
                    builder.append("{\"id\": \"").append(produtos.get(i).getId())
                            .append("\",\"nome\": \"").append(produtos.get(i).getNome())
                            .append("\",\"valor\": \"").append(produtos.get(i).getValor())
                            .append("\",\"formatValor\": \"").append(produtos.get(i).getFormatValor())
                            .append("\",\"estoque\": \"").append(produtos.get(i).getEstoque())
                            .append("\"}");
                    if (i != produtos.size()-1) {
                        builder.append(",");
                    }
                }
                builder.append("]");
                out.println(builder.toString());
            } else if (url.equals("/vendasJson/servicos")) {
                List<Servico> produtos = DAOVenda.getServicoList(query);

                StringBuilder builder = new StringBuilder("[");
                for (int i = 0; i < produtos.size(); i++) {
                    builder.append("{\"id\": \"").append(produtos.get(i).getId())
                            .append("\",\"nome\": \"").append(produtos.get(i).getNome())
                            .append("\",\"valor\": \"").append(produtos.get(i).getValor())
                            .append("\",\"formatValor\": \"").append(produtos.get(i).getFormatValor())
                            .append("\"}");
                    if (i != produtos.size()-1) {
                        builder.append(",");
                    }
                }
                builder.append("]");
                out.println(builder.toString());
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }   
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
