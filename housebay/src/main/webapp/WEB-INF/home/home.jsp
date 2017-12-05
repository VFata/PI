<%-- 
    Document   : home
    Created on : Nov 18, 2017, 11:23:11 AM
    Author     : Diego
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">        
        <title>Astec Home</title>
        <c:url var="index_url" value="/" />
        <c:import url="/WEB-INF/_head.jsp" />
    <body>
        <div class="columns is-mobile">
            <c:import url="/WEB-INF/_vertical_menu.jsp" />
            
            <div class="column is-11-touch is-9-desktop is-9-widescreen is-10-fullhd ">
                <main class="hero">
                    <div class="hero-head">
                        <div class="container is-fluid">
                            <h1 class="title is-4">Home</h1>
                            <nav class="subtitle is-6 breadcrumb" aria-label="breadcrumbs">
                                <ul>
                                    <li class="is-active"><a href="#" aria-current="page">Home</a></li>
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
                                <p><c:out value="${note}" /></p>
                            </div>
                        </c:forEach>
                        
                        <div class="container is-fluid gallery">
                            <div class="tile is-ancestor">
                                <div class="tile is-parent">
                                    <article class="tile is-child">
                                        <figure class="image is-16by9">
                                            <img src="https://images.unsplash.com/photo-1450778869180-41d0601e046e?dpr=1&auto=format&fit=crop&w=376&h=221&q=60&cs=tinysrgb&ixid=dW5zcGxhc2guY29tOzs7Ozs%3D">
                                        </figure>
                                    </article>
                                </div>
                                <div class="tile is-parent">
                                    <article class="tile is-child">
                                        <figure class="image is-3by2">
                                            <img src="https://images.unsplash.com/photo-1445499348736-29b6cdfc03b9?dpr=1&auto=format&fit=crop&w=376&h=251&q=60&cs=tinysrgb&ixid=dW5zcGxhc2guY29tOzs7Ozs%3D">
                                        </figure>
                                    </article>
                                </div>
                                <div class="tile is-parent">
                                    <article class="tile is-child">
                                        <figure class="image is-3by2">
                                            <img src="https://images.unsplash.com/photo-1502673530728-f79b4cab31b1?dpr=1&auto=format&fit=crop&w=376&h=251&q=60&cs=tinysrgb&ixid=dW5zcGxhc2guY29tOzs7Ozs%3D">
                                        </figure>
                                    </article>
                                </div>
                            </div>
                            
                            <div class="tile is-ancestor">
                                <div class="tile is-parent">
                                    <article class="tile is-child">
                                        <figure class="image is-4by3">
                                            <img src="https://images.unsplash.com/photo-1452857297128-d9c29adba80b?dpr=1&auto=format&fit=crop&w=376&h=282&q=60&cs=tinysrgb&ixid=dW5zcGxhc2guY29tOzs7Ozs%3D">
                                        </figure>
                                    </article>
                                </div>
                                <div class="tile is-parent">
                                    <article class="tile is-child">
                                        <figure class="image is-3by2">
                                            <img src="https://images.unsplash.com/photo-1425082661705-1834bfd09dca?dpr=1&auto=format&fit=crop&w=376&h=249&q=60&cs=tinysrgb&ixid=dW5zcGxhc2guY29tOzs7Ozs%3D">
                                        </figure>
                                    </article>
                                </div>
                                <div class="tile is-parent">
                                    <article class="tile is-child">
                                        <figure class="image is-3by2">
                                            <img src="https://images.unsplash.com/photo-1457014749444-4dfbbd2426d2?dpr=1&auto=format&fit=crop&w=376&h=251&q=60&cs=tinysrgb&ixid=dW5zcGxhc2guY29tOzs7Ozs%3D">
                                        </figure>
                                    </article>
                                </div>
                            </div>
                        </div>
                    </div>
                </main>
            </div>
        </div>
    </body>
</html>
