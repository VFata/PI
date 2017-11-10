<%-- 
    Document   : cliente_show
    Created on : 26/10/2017, 19:42:23
    Author     : diego.matsuki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalhes Clientes</title>
        <c:url var="index_url" value="/" />
        <c:url var="resources_url" value="/resources" />
        <c:url var="main_url" value="/clientes" />
        <c:url var="new_url" value="/clientes/new" />
        <c:url var="edit_url" value="/clientes/edit" />
        
        <link rel="stylesheet" href="${resources_url}/css/bulma.css" />
        <link rel="stylesheet" href="${resources_url}/css/font-awesome.css" />
        <link rel="stylesheet" href="${resources_url}/css/custom.css" />
        <script type="text/javascript" src="${resources_url}/js/application.js"></script>
    </head>
    <body>
        <div class="columns is-mobile">
            <!-- Inclui menu vertical -->
            <c:import url="/WEB-INF/vertical_menu.jsp" />
            
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class="hero-head">
                        <div class="container is-fluid">
                            <h1 class="title is-4">Detales Clientes</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Clientes</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Detalhes</a></li>
                                    <!-- Incluir novos itens na breadcrumbs, caso necessário -->
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
                            <strong>Nome:</strong> <c:out value="${cliente.nome}" />
                        </p>
                        <p>
                            <strong>CPF:</strong> <c:out value="${cliente.cpf}" />
                        </p>
                        <p>
                            <strong>Data de nascimento:</strong> <c:out value="${cliente.formatDataNascimento}" />
                        </p>
                        <p>
                            <strong>E-mail:</strong> <c:out value="${cliente.email}" />
                        </p>
                        <p>
                            <strong>Telefone:</strong> <c:out value="${cliente.telefone}" />
                        </p>
                        
                        <c:if test="${not empty cliente.pets}" >
                            <p>
                                <strong>Pets:</strong>
                            </p>
                            <div>
                                <c:forEach items="${cliente.pets}" var="pet">
                                    <div class="nested-show">
                                        <p><strong><c:out value="${pet.nome}:" /></strong></p>
                                        <p><c:out value="${pet.descricao}" /></p>
                                    </div>
                                </c:forEach>
                            </div>
                        </c:if>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>