<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<spring:url value="/" var="homeUrl" htmlEscape="true"/>
<spring:url value="/about" var="aboutUrl" htmlEscape="true"/>
<%-- <spring:url var= "logoutUrl" value= "logout" /> --%>
<spring:url var="logoutUrl" value="/logout"/>
<style>

    .dropdown-submenu {
        position: relative;
    }

    .dropdown-submenu>.dropdown-menu {
        top: 0;
        left: 100%;
        margin-top: -6px;
        margin-left: -1px;
        -webkit-border-radius: 0 6px 6px 6px;
        -moz-border-radius: 0 6px 6px;
        border-radius: 0 6px 6px 6px;
    }

    .dropdown-submenu:hover>.dropdown-menu {
        display: block;
    }

    .dropdown-submenu>a:after {
        display: block;
        content: " ";
        float: right;
        width: 0;
        height: 0;
        border-color: transparent;
        border-style: solid;
        border-width: 5px 0 5px 5px;
        border-left-color: #ccc;
        margin-top: 5px;
        margin-right: -10px;
    }

    .dropdown-submenu:hover>a:after {
        border-left-color: #fff;
    }

    .dropdown-submenu.pull-left {
        float: none;
    }

    .dropdown-submenu.pull-left>.dropdown-menu {
        left: -100%;
        margin-left: 10px;
        -webkit-border-radius: 6px 0 6px 6px;
        -moz-border-radius: 6px 0 6px 6px;
        border-radius: 6px 0 6px 6px;
    }
</style>


<%--
<c:if test="${not empty tfList}">
    <c:out value="Fill-----------------------------------------" />
</c:if>
<c:if test="${empty tfList}">
    <c:out value="Empty-----------------------------------------" />
</c:if>
--%>
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
                <span class="sr-only">Скрыть меню</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Таблицы</a>
        </div>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="${homeUrl}"> <span class="glyphicon glyphicon-home"></span></a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Дороги<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="roads" class="tooltip-bottom ajaxwait" title="Список автодорог"> Список
                                автодорог</a>
                            <a href="roadmap" class="tooltip-bottom ajaxwait" title="Дороги на карте"> Привязка дорог к
                                карте</a>
                            <%--                         <a href="ya" class="tooltip-bottom ajaxwait" title="Геокодирование"> Выполнить геокодирование</a>   --%>
                            <%--                         <a href="yaxx" class="tooltip-bottom ajaxwait" title="Геокодирование"> Декодировать координаты</a>  --%>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Печать<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="print?shifridwr=1" class="tooltip-bottom ajaxwait" title="Титульный список международных автодорог"> Международные</a>
                            <a href="print?shifridwr=2" class="tooltip-bottom ajaxwait" title="Титульный список национальных автодорог"> Национальные</a>
                            <a href="print?shifridwr=3" class="tooltip-bottom ajaxwait" title="Титульный список региональных автодорог"> Региональные</a>
                            <a href="print?shifridwr=4" class="tooltip-bottom ajaxwait" title="Титульный список территориальных автодорог"> Територриальные</a>
                            <a href="print?shifridwr=5" class="tooltip-bottom ajaxwait" title="Титульный список областных автодорог"> Областные</a>
                            <a href="print?shifridwr=6" class="tooltip-bottom ajaxwait" title="Титульный список районных автодорог"> Районные</a>
                            <%--                         <a href="ya" class="tooltip-bottom ajaxwait" title="Геокодирование"> Выполнить геокодирование</a>   --%>
                            <%--                         <a href="yaxx" class="tooltip-bottom ajaxwait" title="Геокодирование"> Декодировать координаты</a>  --%>
                        </li>
                    </ul>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">Справочники<b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="podr" class="tooltip-bottom ajaxwait" title="Список подразделений"> Список подразделений</a>
                            <a href="usesrs" class="tooltip-bottom ajaxwait" title="Список пользователей"> Список пользователей</a>
                            <a href="widdamage" class="tooltip-bottom ajaxwait" title="Список видов повреждений"> Список видов повреждений</a>
                            <a href="widblob" class="tooltip-bottom ajaxwait" title="Список допустимых типов документов для загрузки в БД"> Список типов документов</a>
                            <a href="widcover" class="tooltip-bottom ajaxwait" title="Список видов покрытий автодорог"> Список видов покрытий</a>
                            <%--                         <a href="ya" class="tooltip-bottom ajaxwait" title="Геокодирование"> Выполнить геокодирование</a>   --%>
                            <%--                         <a href="yaxx" class="tooltip-bottom ajaxwait" title="Геокодирование"> Декодировать координаты</a>  --%>
                        </li>
                    </ul>
                </li>
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
