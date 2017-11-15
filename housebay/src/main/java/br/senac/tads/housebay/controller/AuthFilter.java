/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.tads.housebay.controller;

import java.io.IOException;
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
        urlPatterns = {"/clientes/*", "/funcionarios/*"}, 
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD}
)
public class AuthFilter implements Filter {
    private static final boolean debug = false;
    
    public AuthFilter() {
    }
    
    private void doBeforeProcessing(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html;charset=UTF-8");
    }    
    
    private void doAfterProcessing(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
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
                
        HttpSession session = httpRequest.getSession();
        //Funcionario funcionario = (Funcionario) session.getAttribute("lala");
        
        chain.doFilter(request, response);
        doAfterProcessing(httpRequest, httpResponse);
    }

    @Override public void destroy() {}

    @Override public void init(FilterConfig filterConfig) {}
}
