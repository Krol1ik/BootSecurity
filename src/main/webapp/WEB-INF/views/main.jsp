<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored = "false" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 03.11.2021
  Time: 18:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/static/style.css">
</head>
<body>
<div>
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="Enter text">
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <input type="text" name="tag" placeholder="Tag">
        <input type="file" name="file">
        <button type="submit">Send messages</button>
    </form>
</div>
<form method="get" action="/main">
    <input type="text" name="filter" placeholder="Search by tag" value="${filter}">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button type="submit">Search</button>
</form>
<c:forEach items="${messages}" var="mess">
    <div>
        <tr>
            <td>${mess.text}</td>
            <td>${mess.tag}</td>
            <td>${mess.nameAuthor}</td>
        </tr>
        <div>
            <c:if test="${!mess.filename}" >
                <img src="/img/${mess.filename}" alt="">
            </c:if>
        </div>
    </div>
    </c:forEach>
<form action="/logout" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <input type="submit" value="Sign Out"/>
    <button type="button"><a href="/user">User list</a> </button>
</form>
</body>
</html>
