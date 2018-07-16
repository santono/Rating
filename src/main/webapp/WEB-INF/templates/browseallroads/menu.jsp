<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<spring:url value="" var="homeUrl" htmlEscape="true"/>
<spring:url value="/about" var="aboutUrl" htmlEscape="true"/>
<spring:url var="logoutUrl" value="logout"/>

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
            <a class="navbar-brand" href="mainpage">Главная</a>
        </div>
        <div class="navbar-collapse collapse">

            <ul class="nav navbar-nav">

                <li class="active"><a href="${homeUrl}"> <span class="glyphicon glyphicon-home"></span></a></li>
                <li><a href="#" onclick="addRecord()">Добавить запись</a></li>
<!--
                <li><a href="roads/reportall">Печать списка автодорог</a></li>
-->
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Печать<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <c:if test="${c1>0}">
                               <a href="roads/reportAll?shifridwr=1" class="tooltip-bottom ajaxwait" title="Cписок международных автодорог"> Международные</a>
                            </c:if>
                            <c:if test="${c2>0}">
                               <a href="roads/reportAll?shifridwr=2" class="tooltip-bottom ajaxwait" title="Cписок национальных автодорог"> Национальные</a>
                            </c:if>
                            <c:if test="${c3>0}">
                               <a href="roads/reportAll?shifridwr=3" class="tooltip-bottom ajaxwait" title="Cписок региональных автодорог"> Региональные</a>
                            </c:if>
                            <c:if test="${c4>0}">
                               <a href="roads/reportAll?shifridwr=4" class="tooltip-bottom ajaxwait" title="Cписок территориальных автодорог"> Територриальные</a>
                            </c:if>
                            <c:if test="${c5>0}">
                               <a href="roads/reportAll?shifridwr=5" class="tooltip-bottom ajaxwait" title="Cписок областных автодорог"> Областные</a>
                            </c:if>
                            <c:if test="${c6>0}">
                               <a href="roads/reportAll?shifridwr=6" class="tooltip-bottom ajaxwait" title="Cписок районных автодорог"> Районные</a>
                            </c:if>
                            <c:if test="${c0>0}">
                               <a href="roads/reportAll?shifridwr=0" class="tooltip-bottom ajaxwait" title="Cписок автодорог с неуказанной разновидностью">Неуказанной разновидности</a>
                            </c:if>
                            <%--                         <a href="ya" class="tooltip-bottom ajaxwait" title="Геокодирование"> Выполнить геокодирование</a>   --%>
                            <%--                         <a href="yaxx" class="tooltip-bottom ajaxwait" title="Геокодирование"> Декодировать координаты</a>  --%>
                        </li>
                    </ul>
                </li>



                <!--
                                <li><a href="#">Заочное</a></li>
                                <li class="dropdown">
                                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Режимы<b class="caret"></b></a>
                                    <ul class="dropdown-menu">
                                        <li><a href="#">Действие 1</a></li>
                                        <li><a href="#">Действие 2</a></li>
                                        <li><a href="#">Действие 3</a></li>
                                        <li class="divider"></li>
                                        <li class="dropdown-header">Nav header</li>
                                        <li><a href="#">Separated link</a></li>
                                        <li><a href="#">One more separated link</a></li>
                                    </ul>
                                </li>
                -->
            </ul>

            <sec:authorize access="isAuthenticated()">
                <ul class="nav navbar-nav navbar-right">
                    <li class="active"><a href="${logoutUrl}"><span class="glyphicon glyphicon-log-out"></span>
                        Выход</a></li>
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


