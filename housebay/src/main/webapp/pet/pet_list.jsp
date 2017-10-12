<%-- 
    Document   : pet_list
    Created on : Oct 5, 2017, 2:24:49 AM
    Author     : Diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Pets</title>
        <c:url var="index_url" value="/" />
        <c:url var="resources_url" value="/resources" />
        <c:url var="main_url" value="/pets" />
        <c:url var="new_url" value="/pets/new" />
        <c:url var="edit_url" value="/pets/edit" />
        
        <link rel="stylesheet" href="${resources_url}/css/bulma.css" />
        <link rel="stylesheet" href="${resources_url}/css/font-awesome.css" />
        <link rel="stylesheet" href="${resources_url}/css/custom.css" />
        <script type="text/javascript" src="${resources_url}/js/application.js"></script>
    </head>
    <body>
        <div class="columns is-mobile">
            <!-- Inclui menu vertical -->
            <jsp:include page="/resources/vertical_menu.jsp" />
            
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class="hero-head">
                        <div class="container is-fluid">
                            <h1 class="title is-4">Lista de Pets</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Pets</a></li>
                                    <!-- Incluir novos itens na breadcrumbs, caso necessário -->
                                </ul>
                            </nav>
                        </div>
                    </div>
                    
                    <div class="hero-body">
                        <!-- TODO: Incluir campo de busca param: q -->
                        <!-- TODO: incluir Notificações param: msg -->

                        <div>
                            <a class="button is-success" href="${new_url}">Novo Pet</a>
                        </div>

                        <table class="table is-hoverable is-fullwidth">
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th>Descrição</th>
                                    <th>Ações</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${pets}" var="pet">
                                    <tr>
                                        <td><c:out value="${pet.nome}" /></td>
                                        <td><c:out value="${pet.descricao}" /></td>
                                        <td>
                                            <a class="button is-info is-outlined" href='${main_url}?id=${pet.id}'>
                                                <i class="fa fa-info-circle" aria-hidden="true"></i>&nbsp;
                                                Detalhes
                                            </a>

                                            <a class="button is-warning is-outlined" href='${edit_url}?id=${pet.id}'>
                                                <i class="fa fa-pencil" aria-hidden="true"></i>&nbsp;
                                                Alterar
                                            </a>

                                            <form class="delete-action" action="${main_url}?id=${pet.id}" method="post" confirm="Tem certeza?">
                                                <button class="button is-danger is-outlined send" type="button" >
                                                    <i class="fa fa-trash" aria-hidden="true"></i>&nbsp;
                                                    Apagar    
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
