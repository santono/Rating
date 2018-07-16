<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<spring:url value="/roads" var="homeUrl" htmlEscape="true"/>
<spring:url value="/about" var="aboutUrl" htmlEscape="true"/>
<spring:url var="logoutUrl" value="logout"/>
<spring:url value="/resources/css/photoswipe.css?v=4.1.1-1.0.4" var="photoswipecss" htmlEscape="true"/>
<spring:url value="/resources/css/default-skin/default-skin.css?v=4.1.1-1.0.4" var="photoswipeskincss" htmlEscape="true"/>
<spring:url value="/resources/js/photoswipe.min.js?v=4.1.1-1.0.4" var="photoswipejs" htmlEscape="true"/>
<spring:url value="/resources/js/photoswipe-ui-default.min.js?v=4.1.1-1.0.4" var="photoswipeuijs" htmlEscape="true"/>

<link href="${photoswipecss}" rel="stylesheet" />
<link href="${photoswipeskincss}" rel="stylesheet" />

<script src="${photoswipejs}"></script>
<script src="${photoswipeuijs}"></script>


<div class="menu navbar navbar-default" role="navigation">
    <!--
     <a href="${homeUrl}">В начало</a>
     <a href="${aboutUrl}">About</a>
     <span align="right">
     <sec:authorize access="isAuthenticated()">
          <a href="${logoutUrl}">Выход</a>
     </sec:authorize>
    -->
    <!-- Static navbar -->
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <!--  <span class="sr-only">Toggle navigation</span> -->
                <span>Навигация</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="roads" id="wait">Дороги</a>
        </div>
        <div class="navbar-collapse collapse">

            <ul class="nav navbar-nav">

                <li class="active"><a href="${homeUrl}"> <span class="glyphicon glyphicon-home"></span></a></li>
                <li><a href="#" onclick="editRec(0);">Добавить строку</a></li>
     <%--           <li><a href="rp/print" id="print">Печать</a></li>   --%>

            </ul>

            <sec:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="${logoutUrl}" onclick="javascript:return askExit(event);"><span
                            class="glyphicon glyphicon-log-out"></span> Выход</a></li>
                </ul>
            </sec:authorize>
        </div>
        <!--/.nav-collapse -->
    </div>
    <!--/.container-fluid -->

    <!--
     <sec:authorize access="isAnonymous()">
          Войдите в систему
     </sec:authorize>
     -->


</div>
