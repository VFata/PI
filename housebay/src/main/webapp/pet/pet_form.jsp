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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Novo / Alterar Pet</title>
        <c:url var="resources" value="/resources" />
        <link rel="stylesheet" href="${resources}/css/bulma.css" />
        <link rel="stylesheet" href="${resources}/css/font-awesome.css" />
        <link rel="stylesheet" href="${resources}/css/custom.css" />
        <script type="text/javascript" src="${resources}/js/application.js"></script>
    </head>
    <body>
        <main class="hero">
        <c:url var="create_url" value="/pets" />
        <c:choose>
            <c:when test="${type=='edit' && pet != null}">
                <div class=hero-head">
                    <div class="container">
                        <h1 class="title">Alterar Pet</h1>
                    </div>
                </div>
                <div class="hero-body">
                    <div class="container">
                        <form action="${create_url}" method="post">
                            <input type="hidden" name="_method" value="put" />
                            <input type="hidden" name="id" value="{pet.id}" />
                            
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Nome</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="text" placeholder="Nome" value="${pet.nome}">
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
                                            <button class="button is-primary" type="button" data-method="put" data-href="${create_url}">
                                                Alterar
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </c:when>    
            <c:otherwise>
                <div class=hero-head">
                    <div class="container">
                        <h1 class="title">Novo Pet</h1>
                    </div>
                </div>
                <div class="hero-body">
                    <div class="container">
                        <form action="${create_url}" method="post">
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Nome</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="text" placeholder="Nome">
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
                                            <textarea class="textarea" name="descricao" placeholder="Descrição"></textarea>
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
                                            <button class="button is-primary">
                                                Salvar
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
        </main>
    </body>
</html>
