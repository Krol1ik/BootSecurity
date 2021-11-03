<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page isELIgnored = "false" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 03.11.2021
  Time: 23:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit user</title>
</head>
<body>
<form action="/user" method="post">
    <input type="text" value="${user.username}" name="username">
    <c:forEach items="${roles}" var="role">
        <div>
            <label><input type="checkbox" name="${role}"
            <c:if test="${fn:contains({user.roles}, role)}"> checked="checked"
            </c:if>>
                    ${role}
            </label>
        </div>
    </c:forEach>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button type="submit">Save</button>
</form>
</body>
</html>
