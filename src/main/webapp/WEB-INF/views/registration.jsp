<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored = "false" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 03.11.2021
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link href="../static/css/style.css" type="text/css" rel="stylesheet" >
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">

</head>
<body>
<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>

<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="/">Sweater</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/main">Messages</a>
                </li>
                <li class="nav-item">
                    <sec:authorize access="hasAuthority('ADMIN')">
                        <a class="nav-link" href="/user">User list</a>
                    </sec:authorize>
                </li>
                <li class="nav-item">
                    <sec:authorize access="hasAuthority('ADMIN')">
                        <a class="nav-link" href="/h2-console">Database</a>
                    </sec:authorize>
                </li>
                <li class="nav-item">
                    <sec:authorize access="isAuthenticated()">
                        <a class="nav-link" href="/user/profile">Profile</a>
                    </sec:authorize>
                </li>
            </ul>
            <div class="navbar-text mr-3">
                <sec:authorize access="isAuthenticated()">
                    <sec:authentication property="name"/>
                </sec:authorize>
            </div>
        </div>
    </nav>
</header>

Add new user
<c:if test="${!messages}">
    <p>${messages}</p>
</c:if>

<form:form action="/registration" modelAttribute="user" method="post">
    <form:label path="username">Username: </form:label>
    <form:input path="username"/>
    <form:errors path="username" class="err"/>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <br>
    <form:label path="password">Password: </form:label>
    <form:input path="password"/>
    <form:errors path="password" class="err"/>
    <br>
    <form:label path="email">E-mail: </form:label>
    <form:input path="email"/>
    <form:errors path="email" class="err"/>
    <div><button type="submit">Save</button></div>
</form:form>

</body>
</html>
