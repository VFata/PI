<%-- 
    Document   : servico_form
    Created on : 27/10/2017, 21:18:35
    Author     : vinicius.fsilv11
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    
    <head>
        <meta charset="UTF-8">
        
        <!-- Título da página -->
    <c:choose><c:when test="${type=='edit' && servico != null}">
        <title>Alterar Serviço</title>
    </c:when><c:otherwise>
        <title>Novo Serviço</title>
    </c:otherwise></c:choose>
        
        <c:url var="index_url" value="/" />
        <c:url var="resources_url" value="/resources" />
        <c:url var="main_url" value="/servico" />
        <c:url var="create_url" value="/servico/create" />
        <c:url var="update_url" value="/servico/update" />
        
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
                            
                        <c:choose><c:when test="${type=='edit' && servico != null}">
                            <h1 class="title is-4">Alterar Serviço</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Serviço</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Alterar</a></li>
                                    <!-- Incluir novos itens na breadcrumbs, caso necessário -->
                                </ul>
                            </nav>
                        </c:when><c:otherwise>
                            <h1 class="title is-4">Novo Serviço</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Serviços</a></li>
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
                        
                        <c:choose><c:when test="${type=='edit' && servico != null}">
                            <form action="${edit_url}" method="post">
                                <input type="hidden" name="id" value="${servico.id}" />
                        </c:when><c:otherwise>
                            <form action="${new_url}" method="post">
                        </c:otherwise></c:choose>

                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Serviço</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="text" name="Serviço" placeholder="Serviço" value="${servico.servico}">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Tipo:</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <textarea class="textarea" name="tipo" placeholder="Tipo">${servico.tipo}</textarea>
                                        </div>
                                    </div>
                                </div>
                            </div>
                             
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Valor</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="text" name="valor" placeholder="Valor" value="${servico.valor}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Estoque</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="text" name="estoque" placeholder="Estoque" value="${servico.estoque}">
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
                                                <c:choose><c:when test="${type=='edit' && servico != null}">
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

