<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!--
<spring:url var="logoutUrl" value="/j_spring_security_logout"/>
-->
<spring:url var="logoutUrl" value="logout"/>

<div class="header">

    <div id="userinfo" class="bg-warning">
        <sec:authorize access="isAuthenticated()">
            <span>
            <sec:authentication property="principal.userDTO.FIO"/>&nbsp;
<%--
            <sec:authentication property="principal.userDTO.nameRole" />&nbsp;
            <sec:authentication property="principal.userDTO.nameKaf" />
            <sec:authentication property="principal.userDTO.y" />
            <sec:authentication property="principal.userDTO.m" />
--%>
            </span>
        </sec:authorize>
    </div>
</div>
