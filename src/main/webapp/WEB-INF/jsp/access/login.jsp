<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>

<html>
<head>
    <link rel="stylesheet" type="text/css" media="screen" href="resources/styles/style.css"/>
    <title>Login</title>
</head>
<body>
<form class="login-form" action="j_spring_security_check" method="post">
    <fieldset>
        <legend>Вход в систему</legend>
        <table>
            <tr>
                <td><label for="j_username">Логин</label>:</td>
                <td align="left"><input id="j_username" name="j_username" size="20" maxlength="50" type="text"/></td>
            </tr>
            <tr>
                <td><label for="j_password">Пароль</label>:</td>
                <td align="left"><input id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
                </td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Вход"/></td>
            </tr>
        </table>
    </fieldset>
</form>
<p class="message">${message}</p>
</body>
</html>




