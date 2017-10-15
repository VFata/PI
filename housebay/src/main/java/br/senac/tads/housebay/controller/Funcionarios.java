package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.model.DAOFuncionario;
import br.senac.tads.housebay.model.Funcionario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "Funcionarios", urlPatterns = {"/Funcionrios", "/Funcionarios/new", "/Funcionarios/edit"})
public class Funcionarios extends HttpServlet{
    
    /**
     * Handles the HTTP <code>GET</code> method.
     * 
     * Responde:
     * /Funcionarios             #lista "todos" Funcioanrios
     * /Funcionarios?id=xxx      #detalhes do Funcionario id=xxx
     * /Funcioanrios/new         #form para criar novo Funcioanrio
     * /Funcionarios/edit?id=xxx #form para alterar o Funcionario id=xxx
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
        
        if (url.equals("/funcionarios") && id == null) {
            //Lista funcionarios
            String query = request.getParameter("q");
            List<Funcionario> funcionarios = DAOFuncionario.search(query);
            request.setAttribute("funcionarios", funcionarios);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/Funcionario/Funcionario_list.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/funcionarios") && id != null) {
            //Detalhes do funcionario id
            Funcionario funcionario = DAOFuncionario.read(Long.parseLong(id));
            request.setAttribute("funcionario", funcionario);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/funcionario/funcionario_show.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/funcionarios/new") && id == null) {
            //Form novo funcionario
            request.setAttribute("type", "new");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/funcionario/funcionario_form.jsp");
            dispatcher.forward(request, response);
            
        } else if (url.equals("/funcionarios/edit") && id != null) {
            //Form alterar funcionario
            Funcionario funcionario = DAOFuncionario.read(Long.parseLong(id));
            request.setAttribute("funcionario", funcionario);
            request.setAttribute("type", "edit");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/funcionario/funcionario_form.jsp");
            dispatcher.forward(request, response);
            
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * 
     * Responde:
     * /funcionarios?id=xxx         #deleta o funcionario id=xxx
     * /funcionarios/new            #cria um novo funcionario
     * /funcionarios/edit           #alterar o funcionario id=xxx
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
        
        if (url.equals("/funcionarios") && id != null) {
            //Deleta o funcionario id=xxx
            Funcionario funcionario = new Funcionario();
            funcionario.setId(Long.parseLong(id));
            if(DAOFuncionario.delete(funcionario)) {
                response.sendRedirect(request.getContextPath() + "/funcionarios");
            }
        } else if (url.equals("/funcionarios/new") && id == null) {
            //Cria um novo funcionario
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(request.getParameter("nome"));
            funcionario.setDatanascimento(request.getParameter("datanascimento"));
            funcionario.setTelefone(request.getParameter("telefone"));
            funcionario.setCpf(request.getParameter("cpf"));
            funcionario.setCargo(request.getParameter("cargo"));
            funcionario.setEmail(request.getParameter("email"));
            funcionario.setAtivo(true);
            
            //TODO validar ValidateFuncionario.create(funcionario)
            
            Long newID = DAOFuncionario.create(funcionario);
            if (newID > 0) {
                response.sendRedirect(request.getContextPath() + "/funcionario?id=" + newID);
            }
        }else if (url.equals("/funcionario/edit") && id != null) {
            //Altera o funcionario id=xxx
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(request.getParameter("nome"));
            funcionario.setDatanascimento(request.getParameter("datanascimento"));
            funcionario.setTelefone(request.getParameter("telefone"));
            funcionario.setCpf(request.getParameter("cpf"));
            funcionario.setCargo(request.getParameter("cargo"));
            funcionario.setEmail(request.getParameter("email"));
            funcionario.setAtivo(true);
            
            //TODO validar ValidateFuncionario.update(funcionario)
            
            if (DAOFuncionario.update(funcionario)) {
                response.sendRedirect(request.getContextPath() + "/funcionario?id=" + id);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    
}
