<%-- 
    Document   : form_funcionario
    Created on : 27/10/2017, 20:39:43
    Author     : vinicius.fsilv11
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
    <c:choose><c:when test="${type=='edit' && funcionario != null}">
        <title>Alterar Funcionário</title>
    </c:when><c:otherwise>
        <title>Novo Funcionário</title>
    </c:otherwise></c:choose>
        
        <c:url var="index_url" value="/" />
        <c:url var="main_url" value="/funcionarios" />
        <c:url var="create_url" value="/funcionarios/create" />
        <c:url var="update_url" value="/funcionarios/update" />
        
        <c:import url="/WEB-INF/_head.jsp" />
    </head>
    <body>
        <div class="columns is-mobile">
            <c:import url="/WEB-INF/_vertical_menu.jsp" />
        
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class=hero-head">
                        <div class="container is-fluid">
                        <c:choose><c:when test="${type=='edit' && funcionario != null}">
                            <h1 class="title is-4">Alterar Funcionário</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}home">Home</a></li>
                                    <li><a href="${main_url}">Funcionários</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Alterar</a></li>
                                    <!-- Incluir novos itens na breadcrumbs, caso necessário -->
                                </ul>
                            </nav>
                        </c:when><c:otherwise>
                            <h1 class="title is-4">Novo Funcionário</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}home">Home</a></li>
                                    <li><a href="${main_url}">Funcionários</a></li>
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
                                <p><c:out value="${note}" /></p>
                            </div>
                        </c:forEach>
                        
                        <c:choose><c:when test="${type=='edit' && funcionario != null}">
                            <form action="${update_url}" method="post">
                                <input type="hidden" name="id" value="${funcionario.id}" />
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
                                            <input class="input" type="text" name="nome" placeholder="Nome" value="${funcionario.nome}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                                           
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Data de Nascimento</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="date" name="nascimento" value="${funcionario.inputDataNascimento}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Telefone</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="text" name="telefone" placeholder="Telefone" value="${funcionario.telefone}">
                                        </div>
                                    </div>
                                </div>
                            </div>      
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">CPF</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="text" name="cpf" placeholder="CPF" value="${funcionario.cpf}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                                        
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Cargo</label>
                                </div>
                                <div class="field-body">
                                    <div class="field is-narrow">
                                        <div class="control">
                                            <div class="select is-fullwidth">
                                                <select name="cargo">
                                                    <c:forEach items="${cargos}" var="cargo">
                                                        <option value="${cargo.value}"><c:out value="${cargo}" /></option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <c:if test="${type != 'edit'}">
                                <fieldset class="fieldset">
                                    <div class="field is-horizontal">
                                        <div class="field-label is-normal">
                                            <label class="label">E-mail</label>
                                        </div>
                                        <div class="field-body">
                                            <div class="field">
                                                <div class="control">
                                                    <input class="input" type="text" name="email" placeholder="Email" value="${funcionario.email}">
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="field is-horizontal">
                                        <div class="field-label is-normal">
                                            <label class="label">Senha</label>
                                        </div>
                                        <div class="field-body">
                                            <div class="field">
                                                <div class="control">
                                                    <input class="input" type="password" name="senha" placeholder="Senha" value="">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </fieldset>
                            </c:if>
                                        
                            <div class="field is-horizontal">
                                <div class="field-label">
                                    <!-- Left empty for spacing -->
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <button class="button is-primary" >
                                                <c:choose><c:when test="${type=='edit' && funcionario != null}">
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