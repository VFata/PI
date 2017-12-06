<%-- 
    Document   : relatorio_result
    Created on : Nov 17, 2017, 4:50:40 PM
    Author     : Diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">        
        <title>Relatórios</title>
        <c:url var="index_url" value="/" />
        <c:url var="main_url" value="/relatorios" />
        <c:import url="/WEB-INF/_head.jsp" />
    </head>
    <body>
        <div class="columns is-mobile">
            <c:import url="/WEB-INF/_vertical_menu.jsp" />
            
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class="hero-head">
                        <div class="container is-fluid">
                            <h1 class="title is-4">Relatórios</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}home">Home</a></li>
                                    <li><a href="${main_url}">Relatórios</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Visualizar</a></li>
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
                                <p><c:out value="${note}" /></p>
                            </div>
                        </c:forEach>
                        
                        <table class="table is-hoverable is-fullwidth">
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th>Quantidade</th>
                                    <th>Valor Total</th>
                                    <th>Qtd média diária</th>
                                    <th>Valor média diária</th>
                                </tr>
                            </thead>
                            <tbody>
                              <c:forEach items="${relatorio}" var="map">
                                <tr>
                                    <td>
                                        <c:out value="${map['nome']}" />
                                    </td>
                                    <td>
                                        <c:out value="${map['quantidade']}" />
                                    </td>
                                    <td>
                                        <c:out value="${map['ftotal']}" />
                                    </td>
                                    <td>
                                        <c:out value="${map['avgqtd']}" />
                                    </td>
                                    <td>
                                        <c:out value="${map['avgtotal']}" />
                                    </td>
                                </tr>
                              </c:forEach>
                            </tbody>
                                <tfoot>
                                    <tr>
                                        <th>Total</th>
                                        <th><c:out value="${qtd}" /></th>
                                        <th><c:out value="${ftotal}" /></th>
                                        <th><c:out value="${avgqtd}" /></th>
                                        <th><c:out value="${avgtotal}" /></th>
                                    </tr>
                                </tfoot>
                        </table>
                        
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>
