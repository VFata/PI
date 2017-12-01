/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.controller.validate.ValidateCliente;
import br.senac.tads.housebay.controller.validate.ValidatePet;
import br.senac.tads.housebay.db.DAOCliente;
import br.senac.tads.housebay.exception.ClienteException;
import br.senac.tads.housebay.exception.PetException;
import br.senac.tads.housebay.model.Cliente;
import br.senac.tads.housebay.model.Pet;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
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


@WebServlet(name = "Clientes", urlPatterns = {"/clientes", "/clientes/new", "/clientes/create", "/clientes/edit", "/clientes/update", "/clientes/destroy"} )
public class Clientes extends HttpServlet{
    
    
    /*  ROTAS:
     *  GET:  /clientes             => Lista de clientes
     *  GET:  /clientes?id=xx       => Detalhes do cliente
     *  GET:  /clientes/new         => Formulário para criar
     *  POST: /clientes/create      => Cria cliente
     *  GET:  /clientes/edit?id=xx  => Formulário para alterar
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
        request.setCharacterEncoding("UTF-8");
        
        String url = request.getServletPath();
        String id = request.getParameter("id");
        HttpSession sessao = request.getSession();

        //response.setContentType("text/html;charset=UTF-8");
        
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
     * /clientes?id=xx          #deleta o cliente id=xx
     * /clientes/new            #cria um novo cliente
     * /clientes/edit           #alterar o cliente id=xx
     * 
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        String url = request.getServletPath();
        String id = request.getParameter("id");
        HttpSession sessao = request.getSession();
        List mensagens = (List) sessao.getAttribute("mensagem");
        HashMap erros = (HashMap) sessao.getAttribute("erro");
        //System.out.println("DEBUG: post method");

        //response.setContentType("text/html;charset=UTF-8");
        
        if (url.equals("/clientes/destroy") && id != null) {
            //Deleta o cliente id=xx
            Cliente cliente = DAOCliente.read(Long.parseLong(id));            
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
            
            String dataNascimento = request.getParameter("nascimento");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            GregorianCalendar nasc = null;
            try {
                if (dataNascimento != null) {
                    nasc = new GregorianCalendar();
                    nasc.setTime(format.parse(dataNascimento));
                }
            } catch (ParseException ex) {
                System.err.println(ex.getMessage());
            }
            cliente.setDataNascimento(nasc);
            
            try {
                Enumeration<String> pets = request.getParameterNames();
                                
                while(pets.hasMoreElements()) {
                    String petKey = pets.nextElement();
                    if(petKey.startsWith("pet_id_")) {
                        String nomeKey = "pet_nome_" + petKey.substring(7);
                        String descKey = "pet_descricao_" + petKey.substring(7);
                        
                        Pet pet = new Pet(request.getParameter(nomeKey), request.getParameter(descKey));
                        Long petId = Long.parseLong(request.getParameter(petKey));
                        if (petId > 0) {
                            pet.setId(petId);
                        }
                        cliente.addPets(pet);
                    } 
                }
                                       
                ValidateCliente.create(cliente);
                for(Pet pet: cliente.getPets()) {
                    ValidatePet.create(pet);
                }
            } catch (ClienteException | PetException ex) {
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
            //Altera o cliente id=xx            
            Cliente cliente = new Cliente();
            cliente.setId(Long.parseLong(id));
            cliente.setNome(request.getParameter("nome"));
            cliente.setCpf(request.getParameter("cpf"));
            cliente.setEmail(request.getParameter("email"));
            cliente.setTelefone(request.getParameter("telefone"));
                        
            String dataNascimento = request.getParameter("nascimento");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            GregorianCalendar nasc = null;
            try {
                if (dataNascimento != null) {
                    nasc = new GregorianCalendar();
                    nasc.setTime(format.parse(dataNascimento));
                }
            } catch (ParseException ex) {
                System.err.println(ex.getMessage());
            }
            cliente.setDataNascimento(nasc);
            
            try {
                Enumeration<String> pets = request.getParameterNames();
                                
                while(pets.hasMoreElements()) {
                    String petKey = pets.nextElement();
                    if(petKey.startsWith("pet_id_")) {
                        String nomeKey = "pet_nome_" + petKey.substring(7);
                        String descKey = "pet_descricao_" + petKey.substring(7);
                        
                        Pet pet = new Pet(request.getParameter(nomeKey), request.getParameter(descKey));
                        Long petId = Long.parseLong(request.getParameter(petKey));
                        if (petId > 0) {
                            pet.setId(petId);
                        }
                        cliente.addPets(pet);
                    } 
                }
                
                ValidateCliente.update(cliente);
                for(Pet pet: cliente.getPets()) {
                    ValidatePet.create(pet);
                }
            } catch (ClienteException | PetException ex) {
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
