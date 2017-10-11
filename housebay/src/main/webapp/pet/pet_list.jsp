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
        <c:url var="resources" value="/resources" />
        <link rel="stylesheet" href="${resources}/css/bulma.css" />
        <link rel="stylesheet" href="${resources}/css/font-awesome.css" />
        <link rel="stylesheet" href="${resources}/css/custom.css" />
        <script type="text/javascript" src="${resources}/js/application.js"></script>
    </head>
    <body>
        <div class="columns is-mobile">
            <div class="column is-narrow-touch is-3-desktop is-3-widescreen is-2-fullhd">
                <div class="hero is-fullheight is-dark">
                    <aside class="menu is-dark">
                        <div class="menu-label">
                            <figure class="image is-square">
                                <img src="resources/img/astec_logo.png">
                            </figure>
                        </div>
                        <ul class="menu-list">
                            <li>
                                <a class="has-tooltip">
                                    <i class="fa fa-paw" aria-hidden="true"></i>
                                    <span class="is-hidden-touch">&nbsp;Pets</span>
                                    <span class="tooltip tag is-black is-hidden-desktop">Pets</span>
                                </a>
                            </li>
                        </ul>
                    </aside>
                </div>
            </div>
            
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class="hero-body">
                        <c:url var="show_url" value="/pets" />
                        <c:url var="new_url" value="/pets/new" />
                        <c:url var="edit_url" value="/pets/edit" />
                        <div class="hero-head">
                            <nav class="breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="#">Home</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Pets</a></li>
                                </ul>
                            </nav>
                        </div>
                        
                        <h1 class="title">Lista de Pets</h1>
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
                                            <a class="button is-info is-outlined" href='${show_url}?id=${pet.id}'>
                                                <i class="fa fa-info-circle" aria-hidden="true"></i>&nbsp;
                                                Detalhes
                                            </a>

                                            <a class="button is-warning is-outlined" href='${edit_url}?id=${pet.id}'>
                                                <i class="fa fa-pencil" aria-hidden="true"></i>&nbsp;
                                                Alterar
                                            </a>

                                            <form action="${show_url}?id=${pet.id}" method="post" style="display: inline;">
                                                <button class="button is-danger is-outlined" >
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
