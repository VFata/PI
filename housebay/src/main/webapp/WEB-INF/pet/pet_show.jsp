<%-- 
    Document   : pet_show
    Created on : Oct 5, 2017, 2:24:49 AM
    Author     : Diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Detalhes Pet</title>
        <c:url var="index_url" value="/" />
        <c:url var="main_url" value="/pets" />
        <c:url var="new_url" value="/pets/new" />
        <c:url var="edit_url" value="/pets/edit" />
        
        <c:import url="/WEB-INF/_head.jsp" />
    </head>
    <body>
        <div class="columns is-mobile">
            <c:import url="/WEB-INF/_vertical_menu.jsp" />
            
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class="hero-head">
                        <div class="container is-fluid">
                            <h1 class="title is-4">Detalhes Pet</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Pets</a></li>
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
                            <strong>Nome:</strong> <c:out value="${pet.nome}" />
                        </p>
                        <p>
                            <strong>Descrição:</strong> <c:out value="${pet.descricao}" />
                        </p>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>
