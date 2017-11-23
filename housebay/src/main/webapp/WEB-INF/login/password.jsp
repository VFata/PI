<%-- 
    Document   : password
    Created on : Nov 23, 2017, 12:23:29 PM
    Author     : Diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Alterar Senha</title>
        
        <c:url var="index_url" value="/" />
        <c:url var="main_url" value="/password" />
        
        <c:import url="/WEB-INF/_head.jsp" />
    </head>
    <body>
        <div class="columns is-mobile">
            <c:import url="/WEB-INF/_vertical_menu.jsp" />
        
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd">
                <main class="hero">
                    <div class="hero-head">
                        <div class="container is-fluid">
                            <h1 class="title is-4">Alterar Senha</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}home">Home</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Alterar Senha</a></li>
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
                        
                        
                        <form action="${main_url}" method="post">
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">E-mail</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="text" value="${email}" disabled>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Senha Atual</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="password" name="senha" placeholder="Senha Atual">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Nova Senha</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="password" name="nova-senha" placeholder="Nova Senha">
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
                                                Alterar Senha
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