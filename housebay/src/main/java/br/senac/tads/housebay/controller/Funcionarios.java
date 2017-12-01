package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.controller.validate.ValidateFuncionario;
import br.senac.tads.housebay.db.DAOFuncionario;
import br.senac.tads.housebay.exception.FuncionarioException;
import br.senac.tads.housebay.model.Cargo;
import br.senac.tads.housebay.model.Funcionario;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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


@WebServlet(name = "Funcionarios", urlPatterns = {"/funcionarios", "/funcionarios/new", "/funcionarios/create", "/funcionarios/edit", "/funcionarios/update", "/funcionarios/destroy"})
public class Funcionarios extends HttpServlet{
    
    /*  ROTAS:
     *  GET:  /funcionarios             => Lista de funcionarios
     *  GET:  /funcionarios?id=xxx      => Detalhes do funcionario
     *  GET:  /funcionarios/new         => Formulário para criar
     *  POST: /funcionarios/create      => Cria funcionario
     *  GET:  /funcionarios/edit?id=xxx => Formulário para alterar
     *  POST: /funcionarios/update      => Altera funcionario
     *  POST: /funcionarios/destroy     => Apaga funcionario
     *
     *  Session:
     *  "mensagem"  => Notificações de alterações
     *  "erro"      => Notificações de erros
     *  "funcionario"       => Funcionario com erro (retornar ao formulário)
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
        
        if (url.equals("/funcionarios") && id == null) {
            //Lista funcionarios
            String query = request.getParameter("q");
            List<Funcionario> funcionarios = DAOFuncionario.search(query);
            request.setAttribute("funcionarios", funcionarios);
            responseURL = "/WEB-INF/funcionario/funcionario_list.jsp";
        } else if (url.equals("/funcionarios") && id != null) {
            //Detalhes do funcionario id
            Funcionario funcionario = DAOFuncionario.read(Long.parseLong(id));
            request.setAttribute("funcionario", funcionario);
            responseURL = "/WEB-INF/funcionario/funcionario_show.jsp";
        } else if (url.equals("/funcionarios/new") && id == null) {
            //Form novo funcionario
            newForm(request, response, sessao);
            return;
        } else if (url.equals("/funcionarios/edit") && id != null) {
            //Form alterar funcionario
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
        HttpSession sessao = request.getSession();
        List mensagens = (List) sessao.getAttribute("mensagem");
        HashMap erros = (HashMap) sessao.getAttribute("erro");
        //System.out.println("DEBUG: post method");

        //response.setContentType("text/html;charset=UTF-8");
        
        if (url.equals("/funcionarios/destroy") && id != null) {
            //Deleta o funcionario id=xxx
            Funcionario funcionario = new Funcionario();
            funcionario.setId(Long.parseLong(id));
            if(DAOFuncionario.delete(funcionario)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Funcionario removido.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/funcionarios");
            }
        } else if (url.equals("/funcionarios/create") && id == null) {
            //Cria um novo funcionario
            Funcionario funcionario = new Funcionario();
            funcionario.setNome(request.getParameter("nome"));
            funcionario.setTelefone(request.getParameter("telefone"));
            funcionario.setCpf(request.getParameter("cpf"));
            funcionario.setCargo(Cargo.getCargo(Integer.parseInt(request.getParameter("cargo"))));
            
            funcionario.setEmail(request.getParameter("email"));
            funcionario.setSenha(DAOFuncionario.geraSenha(request.getParameter("senha")));
            
            /*
            Calendar agora = Calendar.getInstance();
            funcionario.setCriado((GregorianCalendar) agora);
            funcionario.setModificado((GregorianCalendar) agora);
            funcionario.setAtivo(true);
            */
            
            String dataNascimento = request.getParameter("nascimento");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            GregorianCalendar nasc = new GregorianCalendar();
            try {
                 nasc.setTime(format.parse(dataNascimento));
                
            } catch (ParseException ex) {
                Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            }
            funcionario.setDataNascimento(nasc);
           
            
            try {
                ValidateFuncionario.create(funcionario);
            } catch (FuncionarioException ex) {
                System.out.println("Funcionario Exception: " + ex.getMessage());
                sessao.setAttribute("funcionario", funcionario);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                newForm(request, response, sessao);
                return;
            }
            
            Long newId = DAOFuncionario.create(funcionario);
            if (newId > 0) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Funcionario criado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/funcionarios?id=" + newId);
            }
        } else if (url.equals("/funcionarios/update") && id != null) {
            //Altera o funcionario id=xxx            
            
            Funcionario funcionario = new Funcionario();
            funcionario.setId(Long.parseLong(id));
            funcionario.setNome(request.getParameter("nome"));
            funcionario.setCargo(Cargo.getCargo(Integer.parseInt(request.getParameter("cargo"))));
            funcionario.setCpf(request.getParameter("cpf"));
            //funcionario.setEmail(request.getParameter("email"));
            funcionario.setTelefone(request.getParameter("telefone"));            
            
            String dataNascimento = request.getParameter("nascimento");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            GregorianCalendar nasc = new GregorianCalendar();
            try {
                 nasc.setTime(format.parse(dataNascimento));
            } catch (ParseException ex) {
                System.err.println(ex.getMessage());
            }
            funcionario.setDataNascimento(nasc);
            
            try {
                ValidateFuncionario.update(funcionario);
            } catch (FuncionarioException ex) {
                System.out.println("Funcionario Exception: " + ex.getMessage());                
                sessao.setAttribute("funcionario", funcionario);
                if (erros == null) {
                    erros = new HashMap();
                }
                erros.putAll(ex.getErrors());
                sessao.setAttribute("erro", erros);
                editForm(request, response, sessao, Long.parseLong(id));
                return;
            }
            
            if (DAOFuncionario.update(funcionario)) {
                if (mensagens == null) {
                    mensagens = new ArrayList();
                }
                mensagens.add("Funcionario alterado com sucesso.");
                sessao.setAttribute("mensagem", mensagens);
                response.sendRedirect(request.getContextPath() + "/funcionarios?id=" + id);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }    
    }
    
    private void newForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao)
        throws ServletException, IOException {
        Funcionario funcionario = (Funcionario) sessao.getAttribute("funcionario");
        if (funcionario != null) {
            request.setAttribute("funcionario", funcionario);
            sessao.removeAttribute("funcionario");
        }
        request.setAttribute("type", "new");
        request.setAttribute("cargos", DAOFuncionario.getCargoList());
        
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/funcionario/funcionario_form.jsp");
        dispatcher.forward(request, response);
    }
    
    private void editForm(HttpServletRequest request, HttpServletResponse response, HttpSession sessao, Long id)
        throws ServletException, IOException {
        Funcionario funcionario = (Funcionario) sessao.getAttribute("funcionario");
        if (funcionario == null) {
            funcionario = DAOFuncionario.read(id);
        } else {
            sessao.removeAttribute("funcionario");
        }
        request.setAttribute("funcionario", funcionario);
        request.setAttribute("type", "edit");
        request.setAttribute("cargos", DAOFuncionario.getCargoList());
        
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
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/funcionario/funcionario_form.jsp");
        dispatcher.forward(request, response);
    }
    
    
}
