<%-- 
    Document   : venda_show
    Created on : 27/10/2017, 21:41:21
    Author     : vinicius.fsilv11
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalhes Vendas</title>
        
        <c:url var="index_url" value="/" />
        <c:url var="main_url" value="/vendas" />
        <c:url var="new_url" value="/vendas/new" />
        <c:url var="edit_url" value="/vendas/edit" />

        <c:import url="/WEB-INF/_head.jsp" />
    </head>
    <body>
        <div class="columns is-mobile">
            <c:import url="/WEB-INF/_vertical_menu.jsp" />
            
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class="hero-head">
                        <div class="container is-fluid">
                            <h1 class="title is-4">Detalhes Vendas</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Vendas</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Detalhes</a></li>
                                </ul>
                            </nav>
                        </div>
                    </div>
                    
                    <div class="hero-body">
                      <c:forEach items="${notifications}" var="note">
                        <div class="notification">
                            <button class="delete"></button>
                            <p><c:out value="${note}" /></p>
                        </div>
                      </c:forEach>

                      <c:forEach items="${errors}" var="note">
                        <div class="notification is-danger">
                            <button class="delete"></button>
                            <p><c:out value="${note.value}" /></p>
                        </div>
                      </c:forEach>
                        <p>
                            <strong>Empresa:</strong> <c:out value="${venda.empresa.nome}" />
                        </p>
                        <p>
                            <strong>Cliente:</strong> <c:out value="${venda.cliente.nome}" />
                        </p>
                        
                        
                        <table class="table is-hoverable is-fullwidth" id="carrinho">
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th>Valor</th>
                                    <th>Quantidade</th>
                                    <th>Total</th>
                                </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${venda.carrinho}" var="v">
                                <tr>
                                    <td>
                                        <c:out value="${v.vendavel.nome}" />
                                    </td>
                                    <td>
                                        <c:out value="${v.vendavel.formatValor}" />
                                    </td>
                                    <td>
                                        <c:out value="${v.quantidade}" />
                                    </td>
                                    <td>
                                        <c:out value="${v.formatValorTotal}" />
                                    </td>
                                </tr>
                              </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>
