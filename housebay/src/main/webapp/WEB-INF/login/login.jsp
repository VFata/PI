
<%-- 
    Document   : login
    Created on : Nov 15, 2017, 3:57:00 PM
    Author     : Diego
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="UTF-8">
        <title>Login</title>
        <c:url var="index_url" value="/" />
        <c:url var="main_url" value="/login" />

        <c:import url="/WEB-INF/_head.jsp" />
    </head>
    <body>
        <section class="hero is-white is-fullheight">
            <div class="hero-body">
                <div class="container">
                    
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
                    
                    <div class="column is-4 is-offset-4">
                        <h1 class="title has-text-centered has-text-grey">Astec</h1>
                        <div class="box">
                            <form action="${main_url}" method="post">
                                <div class="field">
                                    <label class="label">Email</label>
                                    <div class="control">
                                        <input class="input" type="email" name="email" placeholder="Your Email" autofocus="">
                                    </div>
                                </div>

                                <div class="field">
                                    <label class="label">Senha</label>
                                    <div class="control">
                                        <input class="input" type="password" name="senha" placeholder="Your Password">
                                    </div>
                                </div>
                                
                                <div class="field is-grouped is-grouped-right">
                                  <p class="control">
                                    <button class="button is-link">Login</button>
                                  </p>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </body>
</html>
