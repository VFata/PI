<%-- 
    Document   : cliente_form
    Created on : 26/10/2017, 19:42:03
    Author     : diego.matsuki
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <!-- Título da página -->
    <c:choose><c:when test="${type=='edit' && cliente != null}">
        <title>Alterar Cliente</title>
    </c:when><c:otherwise>
        <title>Novo Cliente</title>
    </c:otherwise></c:choose>
        
        <c:url var="index_url" value="/" />
        <c:url var="resources_url" value="/resources" />
        <c:url var="main_url" value="/clientes" />
        <c:url var="create_url" value="/clientes/create" />
        <c:url var="update_url" value="/clientes/update" />
        
        <link rel="stylesheet" href="${resources_url}/css/bulma.css" />
        <link rel="stylesheet" href="${resources_url}/css/font-awesome.css" />
        <link rel="stylesheet" href="${resources_url}/css/custom.css" />
        <script type="text/javascript" src="${resources_url}/js/application.js"></script>
        <script type="text/javascript" src="${resources_url}/js/cliente.js"></script>
    </head>
    <body>
        <div class="columns is-mobile">
            <!-- Inclui menu vertical -->
            <c:import url="/WEB-INF/vertical_menu.jsp" />
        
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class=hero-head">
                        <div class="container is-fluid">
                        <c:choose><c:when test="${type=='edit' && cliente != null}">
                            <h1 class="title is-4">Alterar Clientes</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Clientes</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Alterar</a></li>
                                    <!-- Incluir novos itens na breadcrumbs, caso necessário -->
                                </ul>
                            </nav>
                        </c:when><c:otherwise>
                            <h1 class="title is-4">Novo Cliente</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Clientes</a></li>
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
                                <p><c:out value="${note.value}" /></p>
                            </div>
                        </c:forEach>
                        
                        <c:choose><c:when test="${type=='edit' && cliente != null}">
                            <form action="${update_url}" method="post">
                                <input type="hidden" name="id" value="${cliente.id}" />
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
                                            <input class="input" type="text" name="nome" placeholder="Nome" value="${cliente.nome}">
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
                                            <%-- TODO: define css class: input-date  --%>
                                            <input class="input" type="date" name="nascimento"  value="${cliente.inputDataNascimento}">
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
                                            <input class="input" type="text" name="telefone" placeholder="Telefone" value="${cliente.telefone}">
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
                                            <input class="input" type="text" name="cpf" placeholder="Cpf" value="${cliente.cpf}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Email</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <input class="input" type="text" name="email" placeholder="Email" value="${cliente.email}">
                                        </div>
                                    </div>
                                </div>
                            </div>
                                        
                            <%-- <h3 class="title is-5">Pets</h3> --%>
                            
                            
                            <fieldset class="fieldset">
                                
                                <div class="field is-horizontal">
                                    <div class="field-label">
                                        <label class="label">Pets</label>
                                    </div>
                                    <div class="field-body">
                                        <div class="field">
                                            <div class="control">
                                                <a class="button is-info" id="add-form-pets">Adicionar Pet</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            
                                <div id="list-form-pets" class="list-block-parent">
                                    <c:forEach items="${cliente.pets}" var="pet" varStatus="loop">
                                        <input type="hidden" name="pet_id_${loop.index}" value ="${pet.id}">
                                        <div class="list-block">
                                            <a class="delete "></a>
                                            <div class="field is-horizontal">
                                                <div class="field-label is-normal">
                                                    <label class="label">Nome</label>
                                                </div>
                                                <div class="field-body">
                                                    <div class="field">
                                                        <div class="control">
                                                            <input class="input" type="text" name="pet_nome_${loop.index}" placeholder="Nome" value="${pet.nome}">
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
                                                            <textarea class="textarea" name="pet_descricao_${loop.index}" placeholder="Descrição">${pet.descricao}</textarea>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </c:forEach>             
                                </div>
                            </fieldset>
                                                
                                                
                                                
                                                
                            <div class="field is-horizontal">
                                <div class="field-label">
                                    <!-- Left empty for spacing -->
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <button class="button is-success" >
                                                <c:choose><c:when test="${type=='edit' && cliente != null}">
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
