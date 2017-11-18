<%-- 
    Document   : servico_list
    Created on : 27/10/2017, 21:18:47
    Author     : vinicius.fsilv11
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Lista de Serviços</title>
        
        <c:url var="index_url" value="/" />
        <c:url var="main_url" value="/servicos" />
        <c:url var="new_url" value="/servicos/new" />
        <c:url var="edit_url" value="/servicos/edit" />
        <c:url var="destroy_url" value="/servicos/destroy" />
        
        <c:import url="/WEB-INF/_head.jsp" />
    </head>
    <body>
        <div class="columns is-mobile">
            <c:import url="/WEB-INF/_vertical_menu.jsp" />
            
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class="hero-head">
                        <div class="container is-fluid">
                            <h1 class="title is-4">Lista de Serviços</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Serviços</a></li>
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
                                                
                        <form action="" method="get" class="field is-grouped">
                            <p class="control is-expanded">
                                <input name="q" class="input" type="text" placeholder="Pesquisar Serviço">
                            </p>
                            <p class="control">
                                <button class="button is-info">
                                    Pesquisar
                                </button>
                            </p>
                            
                            <p class="control">
                                <a class="button is-success" href="${new_url}">Novo Serviço</a>
                            </p>
                        </form>

                        <table class="table is-hoverable is-fullwidth">
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th> Valor</th>
                                     <th>Ações</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${servicos}" var="servico">
                                    <tr>
                                        <td><c:out value="${servico.nome}" /></td>
                                        <td><c:out value="${servico.formatValor}" /></td>
                                        <td>
                                            <a class="button is-info is-outlined" href='${main_url}?id=${servico.id}'>
                                                <i class="fa fa-info-circle" aria-hidden="true"></i>&nbsp;
                                                Detalhes
                                            </a>

                                            <a class="button is-warning is-outlined" href='${edit_url}?id=${servico.id}'>
                                                <i class="fa fa-pencil" aria-hidden="true"></i>&nbsp;
                                                Alterar
                                            </a>

                                            <form class="delete-action" action="${destroy_url}?id=${servico.id}" method="post" confirm="Tem certeza?">
                                                <button class="button is-danger is-outlined send">
                                                    <span class="icon">
                                                        <i class="fa fa-trash" aria-hidden="true"></i>
                                                    </span>
                                                    <span> Apagar</span>    
                                                </button>
                                            </form>
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
