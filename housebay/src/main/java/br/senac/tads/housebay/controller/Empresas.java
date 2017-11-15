/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.controller.validate.ValidateEmpresa;
import br.senac.tads.housebay.db.DAOEmpresa;
import br.senac.tads.housebay.exception.EmpresaException;
import br.senac.tads.housebay.model.Empresa;
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

@WebServlet(name = "Empresas", urlPatterns = {"/empresas", "/empresas/new", "/empresas/create", "/empresas/edit", "/empresas/update", "/empresas/destroy"})
public class Empresas extends HttpServlet {
    
    
    /*  ROTAS:
     *  GET:  /empresas             => Lista de empresas
     *  GET:  /empresas?id=xx       => Detalhes do empresa
     *  GET:  /empresas/new         => Formulário para criar
     *  POST: /empresas/create      => Cria empresa
     *  GET:  /empresas/edit?id=xx  => Formulário para alterar
     *  POST: /empresas/update      => Altera empresa
     *  POST: /empresas/destroy     => Apaga empresa
     *
     *  Session:
     *  "mensagem"  => Notificações de alterações
     *  "erro"      => Notificações de erros
     *  "empresa"       => Empresa com erro (retornar ao formulário)
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

        response.setContentType("text/html;charset=UTF-8");
        
        String responseURL;
        
        if (url.equals("/empresas") && id == null) {
            //Lista empresas
            String query = request.getParameter("q");
            List empresas = DAOEmpresa.search(query);
            request.setAttribute("empresas", empresas);
            responseURL = "/WEB-INF/empresa/empresa_list.jsp";
        } else if (url.equals("/empresas") && id != null) {
            //Detalhes do empresa id
            Empresa empresa = (Empresa) DAOEmpresa.read(Long.parseLong(id));
            request.setAttribute("empresa", empresa);
            responseURL = "/WEB-INF/empresa/empresa_show.jsp";
        } else if (url.equals("/empresas/new") && id == null) {
            //Form novo empresa
            newForm(request, response, sessao);
            return;
        } else if (url.equals("/empresas/edit") && id != null) {
            //Form alterar empresa
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
     * /empresas?id=xx          #deleta o empresa id=xx
     * /empresas/new            #cria um novo empresa
     * /empresas/edit           #alterar o empresa id=xx
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

        response.setContentType("text/html;charset=UTF-8");
        
        if (url.equals("/empresas/destroy") && id != null) {
            //Deleta o empresa id=xx
            Empresa empresa = new Empresa();
            empresa.setId(Long.parseLong(id));
            if(DAOEmpresa.delete(empresa)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Empresa removido.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/empresas");
            }
        } else if (url.equals("/empresas/create") && id == null) {
            //Cria um novo empresa
            Empresa empresa = new Empresa();
            empresa.setNome(request.getParameter("nome"));
            empresa.setCnpj(request.getParameter("cnpj"));

            /*
            Calendar agora = Calendar.getInstance();
            empresa.setCriado((GregorianCalendar) agora);
            empresa.setModificado((GregorianCalendar) agora);
            empresa.setAtivo(true);          
            */
            try {
                ValidateEmpresa.create(empresa);
            } catch (EmpresaException ex) {
                System.out.println("Empresa Exception: " + ex.getMessage());
                sessao.setAttribute("empresa", empresa);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                newForm(request, response, sessao);
                return;
            }
            Long newId = DAOEmpresa.create(empresa);
            if (newId > 0) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Empresa criado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/empresas?id=" + newId);
            }
        } else if (url.equals("/empresas/update") && id != null) {
            //Altera o empresa id=xx            
            Empresa empresa = new Empresa();
            empresa.setId(Long.parseLong(id));
            empresa.setNome(request.getParameter("nome"));
            empresa.setCnpj(request.getParameter("cnpj"));
            /*
            Calendar agora = Calendar.getInstance();
            empresa.setCriado((GregorianCalendar) agora);
            empresa.setModificado((GregorianCalendar) agora);
            empresa.setAtivo(true);
            */
            try {
                ValidateEmpresa.update(empresa);
            } catch (EmpresaException ex) {
                System.out.println("Empresa Exception: " + ex.getMessage());                
                sessao.setAttribute("empresa", empresa);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                editForm(request, response, sessao, Long.parseLong(id));
                return;
            }
            if (DAOEmpresa.update(empresa)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Empresa alterado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/empresas?id=" + id);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }    
    }
    
    private void newForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao)
        throws ServletException, IOException {
        Empresa empresa = (Empresa) sessao.getAttribute("empresa");
        if (empresa != null) {
            request.setAttribute("empresa", empresa);
            sessao.removeAttribute("empresa");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/empresa/empresa_form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao, Long id)
        throws ServletException, IOException {
        Empresa empresa = (Empresa) sessao.getAttribute("empresa");
        if (empresa == null) {
            empresa = (Empresa) DAOEmpresa.read(id);
        } else {
            sessao.removeAttribute("empresa");
        }
        request.setAttribute("empresa", empresa);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/empresa/empresa_form.jsp");
        dispatcher.forward(request, response);
    }
    
}
