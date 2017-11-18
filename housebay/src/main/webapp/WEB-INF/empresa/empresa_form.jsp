<%-- 
    Document   : empresa_form
    Created on : Oct 5, 2017, 2:24:49 AM
    Author     : Diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
    <c:choose><c:when test="${type=='edit' && empresa != null}">
        <title>Alterar Empresa</title>
    </c:when><c:otherwise>
        <title>Novo Empresa</title>
    </c:otherwise></c:choose>
        
        <c:url var="index_url" value="/" />
        <c:url var="main_url" value="/empresas" />
        <c:url var="create_url" value="/empresas/create" />
        <c:url var="update_url" value="/empresas/update" />
        
        <c:import url="/WEB-INF/_head.jsp" />
    </head>
    <body>
        <div class="columns is-mobile">
            <c:import url="/WEB-INF/_vertical_menu.jsp" />
        
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class=hero-head">
                        <div class="container is-fluid">
                        <c:choose><c:when test="${type=='edit' && empresa != null}">
                            <h1 class="title is-4">Alterar Empresa</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Empresas</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Alterar</a></li>
                                </ul>
                            </nav>
                        </c:when><c:otherwise>
                            <h1 class="title is-4">Novo Empresa</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Empresas</a></li>
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
                        
                        <c:choose><c:when test="${type=='edit' && empresa != null}">
                            <form action="${update_url}" method="post">
                                <input type="hidden" name="id" value="${empresa.id}" />
                        </c:when><c:otherwise>
                            <form action="${create_url}" method="post">
                        </c:otherwise></c:choose>

                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Nome</label>
                                </div>
                                <div class="field-body">
                                    <div class="field"><div class="control">
                                        <input class="input" type="text" name="nome" placeholder="Nome" value="${empresa.nome}">
                                    </div></div>
                                </div>
                            </div>

                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">CNPJ</label>
                                </div>
                                <div class="field-body">
                                    <div class="field"><div class="control">
                                        <input class="input" name="cnpj" placeholder="CNPJ" value="${empresa.cnpj}">
                                    </div></div>
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
                                                <c:choose><c:when test="${type=='edit' && empresa != null}">
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
