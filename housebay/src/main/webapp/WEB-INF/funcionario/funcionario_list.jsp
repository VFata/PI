<%-- 
    Document   : list_funcionario
    Created on : 27/10/2017, 20:39:57
    Author     : vinicius.fsilv11
--%>

<html>
    <head>
        <meta charset="UTF-8">
        
        <title>Lista de Funcionarios</title>
        <c:url var="index_url" value="/" />
        <c:url var="resources_url" value="/resources" />
        <c:url var="main_url" value="/funcionarios" />
        <c:url var="new_url" value="/funcionarios/new" />
        <c:url var="edit_url" value="/funcionarios/edit" />
        <c:url var="destroy_url" value="/funcionarios/destroy" />
        
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
                            <h1 class="title is-4">Lista de Funcionarios</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li><a href="${index_url}">Home</a></li>
                                    <li class="is-active"><a href="#" aria-current="page">Funcionarios</a></li>
                                    <!-- Incluir novos itens na breadcrumbs, caso necess�rio -->
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
                                <input name="q" class="input" type="text" placeholder="Pesquisar Cliente">
                            </p>
                            <p class="control">
                                <button class="button is-light">
                                    Pesquisar
                                </button>
                            </p>
                            
                            <p class="control">
                                <a class="button is-success" href="${new_url}">Novo Cliente</a>
                            </p>
                        </form>

                        <table class="table is-hoverable is-fullwidth">
                            <thead>
                                <tr>
                                    <th>Nome</th>
                                    <th>Telefone</th>
                                    <th>Email</th>
                                     <th>A��es</th>
                                    
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${funcionarios}" var="funcionario">
                                    <tr>
                                        <td><c:out value="${funcionario.nome}" /></td>
                                        <td><c:out value="${funcionario.telefone}" /></td>
                                        <td><c:out value="${funcionario.email}" /></td>
                                        <td>
                                            <a class="button is-info is-outlined" href='${main_url}?id=${funcionario.id}'>
                                                <i class="fa fa-info-circle" aria-hidden="true"></i>&nbsp;
                                                Detalhes
                                            </a>

                                            <a class="button is-warning is-outlined" href='${edit_url}?id=${funcionario.id}'>
                                                <i class="fa fa-pencil" aria-hidden="true"></i>&nbsp;
                                                Alterar
                                            </a>

                                            <form class="delete-action" action="${destroy_url}?id=${funcionario.id}" method="post" confirm="Tem certeza?">
                                                <button class="button is-danger is-outlined send">
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
