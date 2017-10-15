/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.model.DAOCliente;
import br.senac.tads.housebay.model.Cliente;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Clientes", urlPatterns = {"/clientes", "/clientes/new", "/clientes/edit"})
public class Clientes extends HttpServlet{
    
     /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * Responde:
     * /clientes                #lista "todos" clientes
     * /clientes?id=xxx         #detalhes do cliente id=xxx
     * /clientes/new            #form para cliente novo pet
     * /clientes/edit?id=xxx    #form para alterar o cliente id=xxx
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
        
        if (url.equals("/clientes") && id == null) {
            //Lista pets
            String query = request.getParameter("q");
            List<Cliente> clientes = DAOCliente.search(query);
            request.setAttribute("clinetes", clientes);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente/cliente_list.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/clientes") && id != null) {
            //Detalhes do cliente id
            Cliente cliente = DAOCliente.read(Long.parseLong(id));
            request.setAttribute("cliente", cliente);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente/cliente_show.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/clientes/new") && id == null) {
            //Form novo cliente
            request.setAttribute("type", "new");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente/cliente_form.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/clientes/edit") && id != null) {
            //Form alterar cliente
            Cliente cliente = DAOCliente.read(Long.parseLong(id));
            request.setAttribute("cliente", cliente);
            request.setAttribute("type", "edit");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente/cliente_form.jsp");
            dispatcher.forward(request, response);
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * Responde:
     * /clientes?id=xxx         #deleta o cliente id=xxx
     * /clientes/new            #cria um novo cliente
     * /clientes/edit           #alterar o cliente id=xxx
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
        
        if (url.equals("/clientes") && id != null) {
            //Deleta o pet id=xxx
            Cliente cliente = new Cliente();
            cliente.setId(Long.parseLong(id));
            if(DAOCliente.delete(cliente)) {
                response.sendRedirect(request.getContextPath() + "/clientes");
            }
        } else if (url.equals("/clientes/new") && id == null) {
            //Cria um novo cliente
            Cliente cliente = new Cliente();
            cliente.setNome(request.getParameter("nome"));
            cliente.setCpf(request.getParameter("cpf"));
            cliente.setDataNascimento(dataNascimento);
            cliente.setEmail(request.getParameter("email"));
            cliente.setTelefone(request.getParameter("telefone"));             
            cliente.setAtivo(true);
            
            //TODO validar ValidatePet.create(cliente)
            
            Long newID = DAOCliente.create(cliente);
            if (newID > 0) {
                response.sendRedirect(request.getContextPath() + "/clientes?id=" + newID);
            }
        }else if (url.equals("/clientes/edit") && id != null) {
            //Altera o cliente id=xxx
            Cliente cliente = new Cliente();
            cliente.setId(Long.parseLong(id));
            cliente.setNome(request.getParameter("nome"));
            cliente.setCpf(request.getParameter("cpf"));
            cliente.setDataNascimento(dataNascimento);
            cliente.setEmail(request.getParameter("email"));
            cliente.setTelefone(request.getParameter("telefone"));  
            cliente.setAtivo(true);
            
            //TODO validar ValidatePet.update(pet)
            
            if (DAOCliente.update(cliente)) {
                response.sendRedirect(request.getContextPath() + "/clientes?id=" + id);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    
    
    
    
}
