/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.db.DAOVendavel;
import br.senac.tads.housebay.exception.ServicoException;
import br.senac.tads.housebay.model.Servico;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "Servicos", urlPatterns = {"/servicos", "/servicos/new", "/servicos/create", "/servicos/edit", "/servicos/update", "/servicos/destroy"})
public class Servicos extends HttpServlet {

     /*  ROTAS:
     *  GET:  /servicos             => Lista de servicos
     *  GET:  /servicos?id=xxx      => Detalhes do serviço
     *  GET:  /servicos/new         => Formulário para criar
     *  POST: /servicos/create      => Cria serviço
     *  GET:  /servicos/edit?id=xxx => Formulário para alterar
     *  POST: /servicos/update      => Altera serviço
     *  POST: /servicos/destroy     => Apaga serviço
     *
     *  Session:
     *  "mensagem"  => Notificações de alterações
     *  "erro"      => Notificações de erros
     *  "serviço"       => Servico com erro (retornar ao formulário)
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
        
        if (url.equals("/servicos") && id == null) {
            //Lista servicos
            String query = request.getParameter("q");
            List servicos = DAOVendavel.searchServico(query);
            request.setAttribute("servicos", servicos);
            responseURL = "/WEB-INF/servico/servico_list.jsp";
        } else if (url.equals("/servicos") && id != null) {
            //Detalhes do servico id
            Servico servico = DAOVendavel.readServico(Long.parseLong(id));
            request.setAttribute("servico", servico);
            responseURL = "/WEB-INF/servico/servico_show.jsp";
        } else if (url.equals("/servicos/new") && id == null) {
            //Form novo servico
            newForm(request, response, sessao);
            return;
        } else if (url.equals("/servicos/edit") && id != null) {
            //Form alterar servico
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
        
        if (url.equals("/servicos/destroy") && id != null) {
            //Deleta o servico id=xxx
            Servico servico = new Servico();
            servico.setId(Long.parseLong(id));
            if(DAOVendavel.delete(servico)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Servico removido.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/servicos");
            }
        } else if (url.equals("/servicos/create") && id == null) {
            //Cria um novo servico
            Servico servico = new Servico();
            
            servico.setNome(request.getParameter("nome"));
            servico.setDescricao(request.getParameter("descricao"));
            servico.setValor(Double.parseDouble(request.getParameter("valor")));
                                  
            
            try {
                ValidateServico.create(servico);
            } catch (ServicoException ex) {
                System.out.println("Servico Exception: " + ex.getMessage());
                sessao.setAttribute("servico", servico);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                newForm(request, response, sessao);
                return;
            }
            
            Long newId = DAOVendavel.createServico(servico);
            if (newId > 0) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Servico criado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/servicos?id=" + newId);
            }
        } else if (url.equals("/servicos/update") && id != null) {
            //Altera o servico id=xxx            
            Servico servico = new Servico();
            servico.setId(Long.parseLong(id));
            servico.setNome(request.getParameter("nome"));
            servico.setDescricao(request.getParameter("descricao"));
            servico.setValor(Double.parseDouble(request.getParameter("valor")));
            
            
            try {
                ValidateServico.update(servico);
            } catch (ServicoException ex) {
                System.out.println("Servico Exception: " + ex.getMessage());                
                sessao.setAttribute("servico", servico);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                editForm(request, response, sessao, Long.parseLong(id));
                return;
            }
            
            if (DAOVendavel.updateServico(servico)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Servico alterado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/servicos?id=" + id);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }    
    }
    
    private void newForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao)
        throws ServletException, IOException {
        Servico servico = (Servico) sessao.getAttribute("servico");
        if (servico != null) {
            request.setAttribute("servico", servico);
            sessao.removeAttribute("servico");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/servico/servico_form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao, Long id)
        throws ServletException, IOException {
        Servico servico = (Servico) sessao.getAttribute("servico");
        if (servico == null) {
            servico = DAOVendavel.readServico(id);
        } else {
            sessao.removeAttribute("servico");
        }
        request.setAttribute("servico", servico);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/servico/servico_form.jsp");
        dispatcher.forward(request, response);
    }
    
    
    
}
