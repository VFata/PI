<%-- 
    Document   : produto_list
    Created on : 25/10/2017, 21:33:27
    Author     : vinicius.fsilv11
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        
        <title>Lista de Produtos</title>
        <c:url var="index_url" value="/" />
        <c:url var="resources_url" value="/resources" />
        <c:url var="main_url" value="/produtos" />
        <c:url var="new_url" value="/produtos/new" />
        <c:url var="edit_url" value="/produtos/edit" />
        <c:url var="destroy_url" value="/produtos/destroy" />
        
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
                    <div class="hero-head">
                        <div class="container is-fluid">
                            <h1 class="title is-4">Lista de Produtos</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Produtos</a></li>
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
                                                
                        <form action="" method="get" class="field is-grouped">
                            <p class="control is-expanded">
                                <input name="q" class="input" type="text" placeholder="Pesquisar Produto">
                            </p>
                            <p class="control">
                                <button class="button is-info">
                                    Pesquisar
                                </button>
                            </p>
                            
                            <p class="control">
                                <a class="button is-success" href="${new_url}">Novo Produto</a>
                            </p>
                        </form>

                        <table class="table is-hoverable is-fullwidth">
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th>Valor</th>
                                    <th>Estoque</th>
                                    <th colspan="3">Ações</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${produtos}" var="produto">
                                    <tr>
                                        <td><c:out value="${produto.nome}" /></td>
                                        <td><c:out value="${produto.formatValor}" /></td>
                                        <td><c:out value="${produto.estoque}" /></td>
                                        <td>
                                            <div class="buttons">
                                                <a class="button is-info is-outlined" href='${main_url}?id=${produto.id}'>
                                                    <i class="fa fa-info-circle" aria-hidden="true"></i>&nbsp;
                                                    Detalhes
                                                </a>
                                                <a class="button is-warning is-outlined" href='${edit_url}?id=${produto.id}'>
                                                    <i class="fa fa-pencil" aria-hidden="true"></i>&nbsp;
                                                    Alterar
                                                </a>
                                                <form class="delete-action" action="${destroy_url}?id=${produto.id}" method="post" confirm="Tem certeza?">
                                                    <button class="button is-danger is-outlined send">
                                                        <i class="fa fa-trash" aria-hidden="true"></i>&nbsp;
                                                        Apagar    
                                                    </button>
                                                </form>
                                            </div>
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
