<%-- 
    Document   : venda_form
    Created on : 27/10/2017, 21:41:09
    Author     : vinicius.fsilv11
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    
    <head>
        <meta charset="UTF-8">
        
        <!-- Título da página -->
        <title>Nova Venda</title>
    
        <c:url var="index_url" value="/" />
        <c:url var="resources_url" value="/resources" />
        <c:url var="main_url" value="/vendas" />
        <c:url var="create_url" value="/vendas/create" />
        <c:url var="update_url" value="/vendas/update" />
        
        <link rel="stylesheet" href="${resources_url}/css/bulma.css" />
        <link rel="stylesheet" href="${resources_url}/css/font-awesome.css" />
        <link rel="stylesheet" href="${resources_url}/css/custom.css" />
        <script type="text/javascript" src="${resources_url}/js/application.js"></script>
        <script type="text/javascript" src="${resources_url}/js/venda.js"></script>
    </head>
    
    <body>
        <div class="columns is-mobile">
            <!-- Inclui menu vertical -->
            <jsp:include page="/WEB-INF/vertical_menu.jsp" />
        
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class=hero-head">
                        <div class="container is-fluid">
                            <h1 class="title is-4">Nova Venda</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li><a href="${main_url}">Vendas</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Novo</a></li>
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
                        
                        <div class="box">
                            <div class="droppanel is-active">
                                <div class="droppanel-trigger">
                                    <a>
                                        <span>Cliente</span>
                                        <span class="icon is-small">
                                            <i class="fa fa-angle-down" aria-hidden="true"></i>
                                        </span>
                                    </a>
                                </div>
                                <div class="droppanel-content">

                                    <!--form action="" method="get" class="field is-grouped" -->
                                    <div class="field is-grouped">
                                        <span class="control is-expanded">
                                            <input name="cliente-q" class="input" type="text" placeholder="Pesquisar Cliente">
                                        </span>
                                        <span class="control">
                                            <button id="search-cliente" class="button is-light">
                                                Pesquisar
                                            </button>
                                        </span>
                                    </div>
                                    <!-- /form -->

                                    <table class="table is-hoverable is-fullwidth" id="cliente-table">
                                        <thead>
                                            <tr>
                                                <th>Nome</th>
                                                <th>Telefone</th>
                                                <th>E-mail</th>
                                                <th>Ações</th>
                                            </tr>
                                        </thead>
                                        <tbody></tbody>
                                    </table>                  
                                    
                                </div>
                                <div class="droppanel-footer">
                                    <p><strong>Selecionado:</strong> <span id="selected-cliente"><c:out value="${venda.cliente.nome}" /></span></p>
                                </div>
                            </div>
                        </div>
                        
                        <div class="box">
                            <div class="droppanel">
                                <div class="droppanel-trigger">
                                    <a>
                                        <span>Produtos</span>
                                        <span class="icon is-small">
                                            <i class="fa fa-angle-down" aria-hidden="true"></i>
                                        </span>
                                    </a>
                                </div>
                                <div class="droppanel-content">
                                    <!--<form action="" method="get" class="field is-grouped">-->
                                    <div class="field is-grouped">
                                        <span class="control is-expanded">
                                            <input name="produto-q" class="input" type="text" placeholder="Pesquisar Produto">
                                        </span>
                                        <span class="control">
                                            <button id="search-produto" class="button is-light">
                                                Pesquisar
                                            </button>
                                        </span>
                                    </div>
                                    <!--</form>-->

                                    <table class="table is-hoverable is-fullwidth" id="produto-table">
                                        <thead>
                                            <tr>
                                                <th>Nome</th>
                                                <th>Valor</th>
                                                <th>Estoque</th>
                                                <th>Ações</th>
                                            </tr>
                                        </thead>
                                        <tbody></tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        
                        <div class="box">
                            <div class="droppanel">
                                <div class="droppanel-trigger">
                                    <a>
                                        <span>Serviços</span>
                                        <span class="icon is-small">
                                            <i class="fa fa-angle-down" aria-hidden="true"></i>
                                        </span>
                                    </a>
                                </div>
                                <div class="droppanel-content">
                                    <p>Tabela de serviços</p>
                                </div>
                            </div>
                        </div>
                        
                        <form action="${new_url}" method="post">
                            <input type="hidden" name="cliente" value="${venda.cliente.id}">
                            
                            <table class="table is-hoverable is-fullwidth" id="carrinho">
                                <thead>
                                    <tr>
                                        <th>Nome</th>
                                        <th>Valor</th>
                                        <th>Quantidade</th>
                                        <th>Total</th>
                                        <th>Ações</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr id="relacao_linha_1">
                                        <td>Osso</td>
                                        <td>R$ 13</td>
                                        <td>
                                            <div class="field">
                                                <div class="control">
                                                    <input class="input" type="number" name="relacao_qtd_1" placeholder="Quantidade">
                                                    <input class="input" type="hidden" value="13">
                                                </div>
                                            </div>
                                        </td>
                                        <td id="relacao_total_1">R$132132</td>
                                        <td>
                                            <input type="hidden" name="relacao_id_1" value="1">
                                            <a class="delete"></a>
                                        </td>
                                    </tr>
                                    
                                </tbody>
                            </table>
                            
                            
                            <div class="field is-horizontal">
                                <div class="field-label is-normal">
                                    <label class="label">Empresa</label>
                                </div>
                                <div class="field-body">
                                    <div class="field">
                                        <div class="control">
                                            <span class="select">
                                                <select name="empresa">
                                                    <c:forEach items="${empresas}" var="empresa" >
                                                        <option  value="${empresa.id}"><c:out value="${empresa.nome}" /></option>
                                                    </c:forEach>
                                                </select>
                                            </span>
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
                                            <button class="button is-primary" >Vender</button>
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

