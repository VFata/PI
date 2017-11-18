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
      <c:choose><c:when test="${type=='edit' && servico != null}">
        <title>Alterar Serviço</title>
      </c:when><c:otherwise>
        <title>Novo Serviço</title>
      </c:otherwise></c:choose>
        
        <c:url var="index_url" value="/" />
        <c:url var="main_url" value="/servicos" />
        <c:url var="create_url" value="/servicos/create" />
        <c:url var="update_url" value="/servicos/update" />
        
        <c:import url="/WEB-INF/_head.jsp" />
    </head>
    
    <body>
        <div class="columns is-mobile">
            <c:import url="/WEB-INF/_vertical_menu.jsp" />
        
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class=hero-head">
                        <div class="container is-fluid">
                            
                        <c:choose><c:when test="${type=='edit' && servico != null}">
                            <h1 class="title is-4">Alterar Serviço</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}home">Home</a></li>
                                    <li><a href="${main_url}">Serviço</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Alterar</a></li>
                                </ul>
                            </nav>
                        </c:when><c:otherwise>
                            <h1 class="title is-4">Novo Serviço</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}home">Home</a></li>
                                    <li><a href="${main_url}">Serviços</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Novo</a></li>
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
                            <form action="${update_url}" method="post">
                                <input type="hidden" name="id" value="${servico.id}" />
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
                                            <input class="input" type="text" name="nome" placeholder="Nome" value="${servico.nome}">
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Descrição:</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <textarea class="textarea" name="descricao" placeholder="Descricao">${servico.descricao}</textarea>
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

