<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored = "false" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 03.11.2021
  Time: 23:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>User list</title>
</head>
<body>
<table>
    <th>Name</th>
    <th>Role</th>
    <th></th>
    <c:forEach items="${userList}" var="users">
    <tr>
        <td>${users.username}</td>
        <td>${users.roles}</td>
        <td><button type="button"><a href="/user/${users.id}">Edit</a> </button></td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
