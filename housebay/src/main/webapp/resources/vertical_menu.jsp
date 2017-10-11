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
                    <!-- Novos itens de menu devem ser inseridos aqui -->
                    <a href="${base}pets" class="has-tooltip">
                        <i class="fa fa-paw" aria-hidden="true"></i>
                        <span class="is-hidden-touch">&nbsp;Pets</span>
                        <span class="tooltip tag is-black is-hidden-desktop">Pets</span>
                    </a>
                </li>
            </ul>
        </aside>
    </div>
</div>
