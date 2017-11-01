/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.db.DAOCliente;
import br.senac.tads.housebay.exception.ClienteException;
import br.senac.tads.housebay.model.Cliente;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "Clientes", urlPatterns = {"/clientes", "/clientes/new", "/clientes/create", "/clientes/edit", "/clientes/update", "/clientes/destroy"} )
public class Clientes extends HttpServlet{
    
    
    /*  ROTAS:
     *  GET:  /clientes             => Lista de clientes
     *  GET:  /clientes?id=xxx      => Detalhes do cliente
     *  GET:  /clientes/new         => Formulário para criar
     *  POST: /clientes/create      => Cria cliente
     *  GET:  /clientes/edit?id=xxx => Formulário para alterar
     *  POST: /clientes/update      => Altera cliente
     *  POST: /clientes/destroy     => Apaga cliente
     *
     *  Session:
     *  "mensagem"  => Notificações de alterações
     *  "erro"      => Notificações de erros
     *  "cliente"       => Cliente com erro (retornar ao formulário)
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
        
        if (url.equals("/clientes") && id == null) {
            //Lista clientes
            String query = request.getParameter("q");
            List<Cliente> clientes = DAOCliente.search(query);
            request.setAttribute("clientes", clientes);
            responseURL = "/WEB-INF/cliente/cliente_list.jsp";
            
        } else if (url.equals("/clientes") && id != null) {
            //Detalhes do cliente id
            Cliente cliente = DAOCliente.read(Long.parseLong(id));
            request.setAttribute("cliente", cliente);
            responseURL = "/WEB-INF/cliente/cliente_show.jsp";
            
        } else if (url.equals("/clientes/new") && id == null) {
            //Form novo cliente
            newForm(request, response, sessao);
            return;
            
        } else if (url.equals("/clientes/edit") && id != null) {
            //Form alterar cliente
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
        HttpSession sessao = request.getSession();
        List mensagens = (List) sessao.getAttribute("mensagem");
        HashMap erros = (HashMap) sessao.getAttribute("erro");
        //System.out.println("DEBUG: post method");

        response.setContentType("text/html;charset=UTF-8");
        
        if (url.equals("/clientes/destroy") && id != null) {
            //Deleta o cliente id=xxx
            Cliente cliente = new Cliente();
            cliente.setId(Long.parseLong(id));
            if(DAOCliente.delete(cliente)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Cliente removido.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/clientes");
            }
            
        } else if (url.equals("/clientes/create") && id == null) {
            //Cria um novo cliente
            Cliente cliente = new Cliente();
            cliente.setNome(request.getParameter("nome"));
            cliente.setCpf(request.getParameter("cpf"));
            cliente.setEmail(request.getParameter("email"));
            cliente.setTelefone(request.getParameter("telefone"));
            /*
            Calendar agora = Calendar.getInstance();
            cliente.setCriado((GregorianCalendar) agora);
            cliente.setModificado((GregorianCalendar) agora);
            cliente.setAtivo(true);
            */
            
            String dataNascimento = request.getParameter("nascimento");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            GregorianCalendar nasc = null;
            try {
                if (dataNascimento != null) {
                    nasc = new GregorianCalendar();
                    nasc.setTime(format.parse(dataNascimento));
                }
            } catch (ParseException ex) {
                System.err.println(ex.getMessage());
                //Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            }
            cliente.setDataNascimento(nasc);
            
            try {
                ValidateCliente.create(cliente);
            } catch (ClienteException ex) {
                System.out.println("Cliente Exception: " + ex.getMessage());
                sessao.setAttribute("cliente", cliente);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                newForm(request, response, sessao);
                return;
            }
            
            Long newId = DAOCliente.create(cliente);
            if (newId > 0) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Cliente criado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/clientes?id=" + newId);
            }
            
        } else if (url.equals("/clientes/update") && id != null) {
            //Altera o cliente id=xxx            
            Cliente cliente = new Cliente();
            cliente.setId(Long.parseLong(id));
            cliente.setNome(request.getParameter("nome"));
            cliente.setCpf(request.getParameter("cpf"));
            cliente.setEmail(request.getParameter("email"));
            cliente.setTelefone(request.getParameter("telefone"));
            Calendar agora = Calendar.getInstance();
            //cliente.setCriado((GregorianCalendar) agora);
            cliente.setModificado((GregorianCalendar) agora);
            cliente.setAtivo(true);
            
            String dataNascimento = request.getParameter("nascimento");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            GregorianCalendar nasc = null;
            try {
                if (dataNascimento != null) {
                    nasc = new GregorianCalendar();
                    nasc.setTime(format.parse(dataNascimento));
                }
            } catch (ParseException ex) {
                System.err.println(ex.getMessage());
                //Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            }
            cliente.setDataNascimento(nasc);
            
            try {
                ValidateCliente.update(cliente);
            } catch (ClienteException ex) {
                System.out.println("Cliente Exception: " + ex.getMessage());                
                sessao.setAttribute("cliente", cliente);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                editForm(request, response, sessao, Long.parseLong(id));
                return;
            }
            
            if (DAOCliente.update(cliente)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Cliente alterado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/clientes?id=" + id);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }    
    }
    
    private void newForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao)
        throws ServletException, IOException {
        Cliente cliente = (Cliente) sessao.getAttribute("cliente");
        if (cliente != null) {
            request.setAttribute("cliente", cliente);
            sessao.removeAttribute("cliente");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/cliente/cliente_form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao, Long id)
        throws ServletException, IOException {
        Cliente cliente = (Cliente) sessao.getAttribute("cliente");
        if (cliente == null) {
            cliente = DAOCliente.read(id);
        } else {
            sessao.removeAttribute("cliente");
        }
        request.setAttribute("cliente", cliente);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/cliente/cliente_form.jsp");
        dispatcher.forward(request, response);
    }

    
}
