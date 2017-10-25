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
                <li>
                    <a href="${base}clientes" class="has-tooltip">
                        <i class="fa fa-address-card" aria-hidden="true"></i>
                        <span class="is-hidden-touch">&nbsp;Clientes</span>
                        <span class="tooltip tag is-black is-hidden-desktop">Clientes</span>
                    </a>
                      <a href="${base}vendas" class="has-tooltip">
                        <i class="fa fa-credit-card" aria-hidden="true"></i>
                        <span class="is-hidden-touch">&nbsp;Vendas</span>
                        <span class="tooltip tag is-black is-hidden-desktop">Vendas</span>
                    </a>     
                    <a href="${base}pets" class="has-tooltip">
                        <i class="fa fa-paw" aria-hidden="true"></i>
                        <span class="is-hidden-touch">&nbsp;Pets</span>
                        <span class="tooltip tag is-black is-hidden-desktop">Pets</span>
                    </a>
                    <a href="${base}funcionarios" class="has-tooltip">
                        <i class="fa fa-users" aria-hidden="true"></i>
                        <span class="is-hidden-touch">&nbsp;Funcionarios</span>
                        <span class="tooltip tag is-black is-hidden-desktop">Funcionarios</span>
                    </a>    
                  
                    <a href="${base}relatorios" class="has-tooltip">
                        <i class="fa fa-bar-chart" aria-hidden="true"></i>
                        <span class="is-hidden-touch">&nbsp;Relatorios</span>
                        <span class="tooltip tag is-black is-hidden-desktop">Relatorios</span>
                    </a>     
                    <a href="${base}estoque" class="has-tooltip">
                        <i class="fa fa-archive" aria-hidden="true"></i>
                        <span class="is-hidden-touch">&nbsp;Estoque</span>
                        <span class="tooltip tag is-black is-hidden-desktop">Estoque</span>
                    </a>     
                </li>
                <!-- Novos itens de menu devem ser inseridos aqui -->
            </ul>
        </aside>
    </div>
</div>
