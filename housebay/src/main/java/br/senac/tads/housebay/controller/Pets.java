package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.model.DAOPet;
import br.senac.tads.housebay.model.Pet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Diego
 */
@WebServlet(name = "Pets", urlPatterns = {"/pets", "/pets/new", "/pets/edit"})
public class Pets extends HttpServlet {
    
    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * Responde:
     * /pets                #lista "todos" pets
     * /pets?id=xxx         #detalhes do pet id=xxx
     * /pets/new            #form para criar novo pet
     * /pets/edit?id=xxx    #form para alterar o pet id=xxx
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
        
        if (url.equals("/pets") && id == null) {
            //Lista pets
            String query = request.getParameter("q");
            List<Pet> pets = DAOPet.search(query);
            request.setAttribute("pets", pets);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pet/pet_list.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/pets") && id != null) {
            //Detalhes do pet id
            Pet pet = DAOPet.read(Long.parseLong(id));
            request.setAttribute("pet", pet);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pet/pet_show.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/pets/new") && id == null) {
            //Form novo pet
            request.setAttribute("type", "new");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pet/pet_form.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/pets/edit") && id != null) {
            //Form alterar pet
            Pet pet = DAOPet.read(Long.parseLong(id));
            request.setAttribute("pet", pet);
            request.setAttribute("type", "edit");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pet/pet_form.jsp");
            dispatcher.forward(request, response);
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
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

        System.out.println("DEBUG: post method");
        
        if (url.equals("/pets") && id != null) {
            //Deleta o pet id=xxx
            Pet pet = new Pet();
            pet.setId(Long.parseLong(id));
            if(DAOPet.delete(pet)) {
                response.sendRedirect(request.getContextPath() + "/pets");
            }
        } else if (url.equals("/pets/new") && id == null) {
            //Cria um novo pet
            Pet pet = new Pet();
            pet.setNome(request.getParameter("nome"));
            pet.setDescricao(request.getParameter("descricao"));
            pet.setAtivo(true);
            
            //TODO validar ValidatePet.create(pet)
            
            Long newID = DAOPet.create(pet);
            if (newID > 0) {
                response.sendRedirect(request.getContextPath() + "/pets?id=" + newID);
            }
        }else if (url.equals("/pets/edit") && id != null) {
            //Altera o pet id=xxx
            Pet pet = new Pet();
            pet.setId(Long.parseLong(id));
            pet.setNome(request.getParameter("nome"));
            pet.setDescricao(request.getParameter("descricao"));
            pet.setAtivo(true);
            
            //TODO validar ValidatePet.update(pet)
            
            if (DAOPet.update(pet)) {
                response.sendRedirect(request.getContextPath() + "/pets?id=" + id);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
