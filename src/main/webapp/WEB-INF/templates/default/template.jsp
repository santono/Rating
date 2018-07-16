<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="en">
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Science report">
    <meta name="author" content="Group of peoples">
    <link rel="shortcut icon" href="resources/ico/favicon.ico">
    <!--
        <link rel="stylesheet" type="text/css" media="screen" href="resources/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" media="screen" href="resources/css/stylesjsp.css"/>
    -->

    <!-- jqGrid -->
    <!--    <spring:url value="resources/css/jquery/ui-lightness/jquery-ui-1.8.6.custom.css" var="ui_jquery_css" />    -->
    <spring:url value="/resources/css/jquery/ui-lightness/jquery-ui.css" var="ui_jquery_css"/>
    <spring:url value="/resources/css/jqgrid/ui.jqgrid.css" var="jqgrid_css"/>
    <spring:url value="/resources/js/jqgrid/i18n/grid.locale-ru.js" var="jqgrid_locale_url"/>
    <spring:url value="/resources/js/jqgrid/jquery.jqGrid.min.js" var="jqgrid_url"/>
    <spring:url value="/resources/js/jquery-1.11.1.min.js" var="jqueryjs_url"/>
    <!--   <spring:url value="resources/js/jquery/jquery-ui-1.8.6.custom.min.js" var="jqueryuijs_url" />  -->
    <spring:url value="/resources/js/jquery/jquery-ui.js" var="jqueryuijs_url"/>

    <spring:url value="/resources/css/noty/buttons.css" var="notybuttonscss_url"/>
    <spring:url value="/resources/css/noty/animate.css" var="notyanimatecss_url"/>
    <spring:url value="/resources/css/bootstrap.min.css" var="bootstrapss_url"/>
    <spring:url value="/resources/css/stylesjsp.css" var="stylesjspss_url"/>
    <spring:url value="/resources/css/noty/font-awesome/css/font-awesome.min.css" var="notyfontawesomecss_url"/>
    <spring:url value="/resources/js/noty/packaged/jquery.noty.packaged.min.js" var="notyjs_url"/>
    <spring:url value="/resources/js/noty/notification_html.js" var="notynotificationjs_url"/>
    <spring:url value="/resources/js//bootstrap.min.js" var="bootstrapjs_url"/>


    <link rel="stylesheet" type="text/css" media="screen" href="${ui_jquery_css}"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${jqgrid_css}"/>

    <link rel="stylesheet" type="text/css" href="${notybuttonscss_url}"/>
    <link rel="stylesheet" type="text/css" href="${notyanimatecss_url}"/>
    <link rel="stylesheet" type="text/css" href="${notyfontawesomecss_url}"/>

    <link rel="stylesheet" type="text/css" media="screen" href="${bootstrapss_url}"/>
    <link rel="stylesheet" type="text/css" media="screen" href="${stylesjspss_url}"/>


    <style>
        input.ui-pg-input {
            width: auto;
            padding: 0px;
            margin: 0px;
            line-height: 14px
        }

        select.ui-pg-selbox {
            width: auto;
            padding: 0px;
            margin: 0px;
            line-height: 18px
        }

        .seldisc {
            padding: 0px;
            margin: 0px;
            border: 0px
        }

        !important
    </style>


    <!--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"><jsp:text /></script> -->
    <script type="text/javascript" src="${jqueryjs_url}">
        <jsp:text/>
    </script>
    <script type="text/javascript">
        var jq = jQuery.noConflict();
    </script>
    <script type="text/javascript" src="${jqueryuijs_url}">
        <jsp:text/>
    </script>
    <%--
        <script type="text/javascript" src="${jqgrid_locale_url}"><jsp:text /></script>
        <script type="text/javascript" src="${jqgrid_url}"></script>
    --%>
    <script type="text/javascript" src="${bootstrapjs_url}">
        <jsp:text/>
    </script>
    <title><tiles:insertAttribute name="title"/></title>

    <script type="text/javascript" src="${notyjs_url}"></script>


</head>
<body>
<div class="page container-fluid">
    <tiles:insertAttribute name="header"/>
    <tiles:insertAttribute name="menu"/>
    <tiles:insertAttribute name="body"/>
    <tiles:insertAttribute name="footer"/>
</div>
</body>
</html>
