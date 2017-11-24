<%-- 
    Document   : relatorio_form
    Created on : Nov 17, 2017, 4:50:10 PM
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
        <c:import url="/WEB-INF/_head.jsp" />
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
                                    <li class="is-active"><a href="#" aria-current="page">Relatórios</a></li>
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
                        
                        <form action="${index_url}relatorios" method="post">

                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Tipo</label>
                                </div>
                                <div class="field-body">
                                    <div class="field is-narrow">
                                        <div class="control">
                                            <div class="select is-fullwidth">
                                                <select name="tipo">
                                                    <c:forEach items="${tipos}" var="tipo">
                                                        <option value="${tipo.value}"><c:out value="${tipo}" /></option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Inicio</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="date" name="inicio" >
                                        </div>
                                    </div>
                                </div>
                            </div>
                                        
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Fim</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="date" name="fim" >
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            
                            <div class="field is-horizontal">
                                <div class="field-label">
                                    <!-- Left empty for spacing -->
                                </div>
                                <div class="field">
                                    <div class="field">
                                        <div class="control">
                                            <button class="button is-primary" >
                                                Gerar
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
