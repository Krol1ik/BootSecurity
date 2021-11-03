<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 03.11.2021
  Time: 21:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
Form login
<form action="/login" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
    <a href="/registration">Registration new user</a>
</form>
</body>
</html>
