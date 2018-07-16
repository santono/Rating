<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title></title>
</head>
<body>
<h1>Ошибка в работе программного комплекса</h1>

<p>В работе приложения возникла ошибка. Пожалуйста сообщите об ошибке администратору ...</p>

<!--
Failed URL: ${url}
Exception:  ${exception.message}
<c:forEach items="${exception.stackTrace}" var="ste">
 ${ste}
</c:forEach>
-->
</body>
</html>
