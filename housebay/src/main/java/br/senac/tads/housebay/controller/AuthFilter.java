package br.senac.tads.housebay.controller;

import br.senac.tads.housebay.model.Cargo;
import br.senac.tads.housebay.model.Funcionario;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Diego
 */
@WebFilter(filterName = "AuthFilter", 
        urlPatterns = {"/home/*", "/clientes/*", "/empresas/*", "/funcionarios/*", "/pets/*", "/produtos/*", "/servicos", "/vendas/*", "/vendasJson/*", "/relatorios", "/password"}, 
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)
public class AuthFilter implements Filter {
    //private static final boolean debug = false;
    private static final Map<Cargo, List> ACESSO = generate();
        
    public AuthFilter() {
    }
    
    private void doBeforeProcessing(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
    }    
    
    private void doAfterProcessing(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        //request.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html;charset=UTF-8");
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        doBeforeProcessing(httpRequest, httpResponse);
        
        String url = httpRequest.getServletPath();
        HttpSession session = httpRequest.getSession();
        
        /* desabilita auth */
        Funcionario funcionario = (Funcionario) session.getAttribute("user");
        if (funcionario != null) {
            if(!ACESSO.get(funcionario.getCargo()).contains(url)) {
                session.setAttribute("errors", Arrays.asList("Acesso negado!"));
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/home");
                return;
            }
        } else {
            session.setAttribute("errors", Arrays.asList("Acesso negado!"));
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            session.invalidate();
            return;
        }
        /**/
        chain.doFilter(request, response);
        doAfterProcessing(httpRequest, httpResponse);
    }

    @Override public void destroy() {}

    @Override public void init(FilterConfig filterConfig) {}
    
    private static Map<Cargo, List> generate() {
        Map map = new HashMap();
        
        map.put(Cargo.DIRETORIA , Arrays.asList("/home", "/password", "/relatorios",
                "/empresas", "/empresas/new", "/empresas/create", "/empresas/edit", "/empresas/update", "/empresas/destroy",  
                "/produtos", "/produtos/new", "/produtos/create", "/produtos/edit", "/produtos/update", "/produtos/destroy", 
                "/servicos", "/servicos/new", "/servicos/create", "/servicos/edit", "/servicos/update", "/servicos/destroy"
        ));
        
        map.put(Cargo.BACKOFFICE , Arrays.asList("/home", "/password", "/relatorios",
                "/produtos", "/produtos/new", "/produtos/create", "/produtos/edit", "/produtos/update", "/produtos/destroy", 
                "/servicos", "/servicos/new", "/servicos/create", "/servicos/edit", "/servicos/update", "/servicos/destroy"
        ));
        
        map.put(Cargo.VENDEDOR , Arrays.asList("/home", "/password", 
                "/clientes", "/clientes/new", "/clientes/create", "/clientes/edit", "/clientes/update", "/clientes/destroy",
                /*"/pets", "/pets/new", "/pets/create",*/ "/pets/edit", "/pets/update", /*"/pets/destroy",*/
                "/vendas", "/vendas/new", "/vendas/create", "/vendasJson/clientes", "/vendasJson/produtos", "/vendasJson/servicos"
        ));
        
        map.put(Cargo.SUPORTE , Arrays.asList("/home", "/password", 
                "/funcionarios", "/funcionarios/new", "/funcionarios/create", "/funcionarios/edit", "/funcionarios/update", "/funcionarios/destroy"
        ));
        
        return map;
    }
}
