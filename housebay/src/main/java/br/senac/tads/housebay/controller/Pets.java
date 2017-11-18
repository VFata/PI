package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.controller.validate.ValidatePet;
import br.senac.tads.housebay.db.DAOPet;
import br.senac.tads.housebay.exception.PetException;
import br.senac.tads.housebay.model.Pet;
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

/**
 *
 * @author Diego
 */
@WebServlet(name = "Pets", urlPatterns = {/*"/pets", "/pets/new", "/pets/create",*/ "/pets/edit", "/pets/update" /*, "/pets/destroy"*/})
public class Pets extends HttpServlet {
    /*  ROTAS:
     *  GET:  /pets             => Lista de pets
     *  GET:  /pets?id=xxx      => Detalhes do pet
     *  GET:  /pets/new         => Formulário para criar
     *  POST: /pets/create      => Cria pet
     *  GET:  /pets/edit?id=xxx => Formulário para alterar
     *  POST: /pets/update      => Altera pet
     *  POST: /pets/destroy     => Apaga pet
     *
     *  Session:
     *  "mensagem"  => Notificações de alterações
     *  "erro"      => Notificações de erros
     *  "pet"       => Pet com erro (retornar ao formulário)
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
        
        //String responseURL;
        /*
        if (url.equals("/pets") && id == null) {
            //Lista pets
            String query = request.getParameter("q");
            List<Pet> pets = DAOPet.search(query);
            request.setAttribute("pets", pets);
            responseURL = "/WEB-INF/pet/pet_list.jsp";
        } else if (url.equals("/pets") && id != null) {
            //Detalhes do pet id
            Pet pet = DAOPet.read(Long.parseLong(id));
            request.setAttribute("pet", pet);
            responseURL = "/WEB-INF/pet/pet_show.jsp";
        } else if (url.equals("/pets/new") && id == null) {
            //Form novo pet
            newForm(request, response, sessao);
            return;
        } else*/ 
        if (url.equals("/pets/edit") && id != null) {
            //Form alterar pet
            editForm(request, response, sessao, Long.parseLong(id));
            return;
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        /*
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
        */
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * Responde:
     * /pets?id=xxx         #deleta o pet id=xxx
     * /pets/new            #cria um novo pet
     * /pets/edit           #alterar o pet id=xxx
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
        
        /*
        if (url.equals("/pets/destroy") && id != null) {
            //Deleta o pet id=xxx
            Pet pet = new Pet();
            pet.setId(Long.parseLong(id));
            try {
                if(DAOPet.delete(pet)) {
                    if (mensagens == null) {
                        mensagens = new ArrayList();
                    }
                    mensagens.add("Pet removido.");
                    sessao.setAttribute("mensagem", mensagens);
                    response.sendRedirect(request.getContextPath() + "/pets");
                }
            } catch(PetException ex) {
                System.out.println("Pet Exception: " + ex.getMessage());
                sessao.setAttribute("pet", pet);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                newForm(request, response, sessao);
                return;
            }
        } else if (url.equals("/pets/create") && id == null) {
            //Cria um novo pet
            Pet pet = new Pet();
            pet.setNome(request.getParameter("nome"));
            pet.setDescricao(request.getParameter("descricao"));
            pet.setClienteId(Long.parseLong(request.getParameter("cliente_id")));
            Long newId;
            try {
                ValidatePet.create(pet);
                
                newId = DAOPet.create(pet);
            } catch (PetException ex) {
                System.out.println("Pet Exception: " + ex.getMessage());
                sessao.setAttribute("pet", pet);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                newForm(request, response, sessao);
                return;
            }
            
            if (newId > 0) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Pet criado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/pets?id=" + newId);
            }
        } else*/ 
        if (url.equals("/pets/update") && id != null) {
            //Altera o pet id=xxx            
            Pet pet = new Pet();
            pet.setId(Long.parseLong(id));
            pet.setNome(request.getParameter("nome"));
            pet.setDescricao(request.getParameter("descricao"));
            pet.setAtivo(true);
            pet.setClienteId(Long.parseLong(request.getParameter("cliente_id")));
            boolean update;
            try {
                ValidatePet.update(pet);
                
                update = DAOPet.update(pet);
            } catch (PetException ex) {
                System.out.println("Pet Exception: " + ex.getMessage());                
                sessao.setAttribute("pet", pet);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                editForm(request, response, sessao, Long.parseLong(id));
                return;
            }
            
            if (update) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Pet alterado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/clientes?id=" + pet.getClienteId());
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }    
    }
    
    /*
    private void newForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao)
        throws ServletException, IOException {
        Pet pet = (Pet) sessao.getAttribute("pet");
        if (pet != null) {
            request.setAttribute("pet", pet);
            sessao.removeAttribute("pet");
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pet/pet_form.jsp");
        dispatcher.forward(request, response);
    }
    */
    
    private void editForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao, Long id)
        throws ServletException, IOException {
        Pet pet = (Pet) sessao.getAttribute("pet");
        if (pet == null) {
            pet = DAOPet.read(id);
        } else {
            sessao.removeAttribute("pet");
        }
        request.setAttribute("pet", pet);
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/pet/pet_form.jsp");
        dispatcher.forward(request, response);
    }   
}