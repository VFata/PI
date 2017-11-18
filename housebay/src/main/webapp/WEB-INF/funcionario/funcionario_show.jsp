<%-- 
    Document   : funcionario_show
    Created on : 27/10/2017, 20:40:32
    Author     : vinicius.fsilv11
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalhes Funcionários</title>
        
        <c:url var="index_url" value="/" />
        <c:url var="main_url" value="/funcionarios" />
        <c:url var="new_url" value="/funcionarios/new" />
        <c:url var="edit_url" value="/funcionarios/edit" />
        
        <c:import url="/WEB-INF/_head.jsp" />
    </head>
    <body>
        <div class="columns is-mobile">
            <c:import url="/WEB-INF/_vertical_menu.jsp" />
            
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class="hero-head">
                        <div class="container is-fluid">
                            <h1 class="title is-4">Detalhes Funcionários</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Funcionários</a></li>
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
                            <strong>Nome:</strong> <c:out value="${funcionario.nome}" />
                        </p>
                        <p>
                            <strong>Data de Nascimento:</strong> <c:out value="${funcionario.formatDataNascimento}" />
                        </p>
                        <p>
                            <strong>Telefone:</strong> <c:out value="${funcionario.telefone}" />
                        </p>
                        <p>
                            <strong>CPF:</strong> <c:out value="${funcionario.cpf}" />
                        </p>
                        <p>
                            <strong>Cargo:</strong> <c:out value="${funcionario.cargo}" />
                        </p>
                        <p>
                            <strong>E-mail:</strong> <c:out value="${funcionario.email}" />
                        </p>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>
