<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:url var="base" value="/" ></c:url>
<div class="column is-narrow-touch is-3-desktop is-3-widescreen is-2-fullhd">
    <div class="hero is-fullheight is-dark">
        <aside class="menu is-dark">
            <div class="menu-label">
                <figure class="image is-3by2 menu-logo">
                    <img src="${base}resources/img/astec_logo.png">
                </figure>
            </div>
            <ul class="menu-list">
                <c:if test="${sessionScope.user.cargo == 1}">
                    <li>
                        <a href="${base}clientes" class="has-tooltip">
                            <i class="fa fa-address-card" aria-hidden="true"></i>
                            <span class="is-hidden-touch">&nbsp;Clientes</span>
                            <span class="tooltip tag is-black is-hidden-desktop">Clientes</span>
                        </a>
                    </li>
                

                    <li>
                        <a href="${base}vendas" class="has-tooltip">
                            <i class="fa fa-credit-card" aria-hidden="true"></i>
                            <span class="is-hidden-touch">&nbsp;Vendas</span>
                            <span class="tooltip tag is-black is-hidden-desktop">Vendas</span>
                        </a>
                    </li>
                    <li>        
                        <a href="${base}pets" class="has-tooltip">
                            <i class="fa fa-paw" aria-hidden="true"></i>
                            <span class="is-hidden-touch">&nbsp;Pets</span>
                            <span class="tooltip tag is-black is-hidden-desktop">Pets</span>
                        </a>
                    </li>
                    <li>        
                        <a href="${base}funcionarios" class="has-tooltip">
                            <i class="fa fa-users" aria-hidden="true"></i>
                            <span class="is-hidden-touch">&nbsp;Funcionarios</span>
                            <span class="tooltip tag is-black is-hidden-desktop">Funcionarios</span>
                        </a>
                    </li>
                    <li>
                        <a href="${base}relatorios" class="has-tooltip">
                            <i class="fa fa-bar-chart" aria-hidden="true"></i>
                            <span class="is-hidden-touch">&nbsp;Relatorios</span>
                            <span class="tooltip tag is-black is-hidden-desktop">Relatorios</span>
                        </a>
                    </li>
                    <li>   
                        <a href="${base}produtos" class="has-tooltip">
                            <i class="fa fa-archive" aria-hidden="true"></i>
                            <span class="is-hidden-touch">&nbsp;Produtos</span>
                            <span class="tooltip tag is-black is-hidden-desktop">Produtos</span>
                        </a>
                    </li>
                    <li>        
                        <a href="${base}servicos" class="has-tooltip">
                            <i class="fa fa-cogs" aria-hidden="true"></i>
                            <span class="is-hidden-touch">&nbsp;Serviços</span>
                            <span class="tooltip tag is-black is-hidden-desktop">Serviços</span>
                        </a>
                    </li>
                    <li>
                        <a href="${base}empresas" class="has-tooltip">
                            <i class="fa fa-building" aria-hidden="true"></i>
                            <span class="is-hidden-touch">&nbsp;Empresas</span>
                            <span class="tooltip tag is-black is-hidden-desktop">Empresas</span>
                        </a>
                    </li>
                    <li>
                        <a href="${base}logout" class="has-tooltip">
                            <i class="fa fa-sign-out" aria-hidden="true"></i>
                            <span class="is-hidden-touch">&nbsp;Logout</span>
                            <span class="tooltip tag is-black is-hidden-desktop">Logout</span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </aside>
    </div>
</div>
