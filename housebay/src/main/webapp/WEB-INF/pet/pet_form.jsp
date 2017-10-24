<%-- 
    Document   : pet_form
    Created on : Oct 5, 2017, 2:24:49 AM
    Author     : Diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        
        <!-- Título da página -->
    <c:choose><c:when test="${type=='edit' && pet != null}">
        <title>Alterar Pet</title>
    </c:when><c:otherwise>
        <title>Novo Pet</title>
    </c:otherwise></c:choose>
        
        <c:url var="index_url" value="/" />
        <c:url var="resources_url" value="/resources" />
        <c:url var="main_url" value="/pets" />
        <c:url var="create_url" value="/pets/create" />
        <c:url var="update_url" value="/pets/update" />
        
        <link rel="stylesheet" href="${resources_url}/css/bulma.css" />
        <link rel="stylesheet" href="${resources_url}/css/font-awesome.css" />
        <link rel="stylesheet" href="${resources_url}/css/custom.css" />
        <script type="text/javascript" src="${resources_url}/js/application.js"></script>
    </head>
    <body>
        <div class="columns is-mobile">
            <!-- Inclui menu vertical -->
            <jsp:include page="/WEB-INF/vertical_menu.jsp" />
        
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class=hero-head">
                        <div class="container is-fluid">
                        <c:choose><c:when test="${type=='edit' && pet != null}">
                            <h1 class="title is-4">Alterar Pet</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Pets</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Alterar</a></li>
                                    <!-- Incluir novos itens na breadcrumbs, caso necessário -->
                                </ul>
                            </nav>
                        </c:when><c:otherwise>
                            <h1 class="title is-4">Novo Pet</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Pets</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Novo</a></li>
                                    <!-- Incluir novos itens na breadcrumbs, caso necessário -->
                                </ul>
                            </nav>
                        </c:otherwise></c:choose>
                            
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
                        
                        <c:choose><c:when test="${type=='edit' && pet != null}">
                            <form action="${update_url}" method="post">
                                <input type="hidden" name="id" value="${pet.id}" />
                        </c:when><c:otherwise>
                            <form action="${create_url}" method="post">
                        </c:otherwise></c:choose>

                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Nome</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="text" name="nome" placeholder="Nome" value="${pet.nome}">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Descrição</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <textarea class="textarea" name="descricao" placeholder="Descrição">${pet.descricao}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="field is-horizontal">
                                <div class="field-label">
                                    <!-- Left empty for spacing -->
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <button class="button is-primary" >
                                                <c:choose><c:when test="${type=='edit' && pet != null}">
                                                    Alterar
                                                </c:when><c:otherwise>
                                                    Salvar
                                                </c:otherwise></c:choose> 
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </form>
                    </div>
                    
                </main>
            </div>

        </div>
    </body>
</html>
