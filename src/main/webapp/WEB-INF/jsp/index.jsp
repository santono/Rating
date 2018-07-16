<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="resources/ico/favicon.ico">
    <link rel="stylesheet" type="text/css" media="screen" href="resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" media="screen" href="resources/css/jumbotron-narrow.css"/>
    <spring:url value="/resources/js/jquery-1.11.1.min.js" var="jqueryjs_url"/>
    <!--   <spring:url value="/resources/js/jquery/jquery-ui-1.8.6.custom.min.js" var="jqueryuijs_url" />  -->
    <spring:url value="/resources/js/jquery/jquery-ui.js" var="jqueryuijs_url"/>
    <spring:url value="/resources/css/noty/buttons.css" var="notybuttonscss_url"/>
    <spring:url value="/resources/css/noty/animate.css" var="notyanimatecss_url"/>
    <spring:url value="/resources/css/noty/font-awesome/css/font-awesome.min.css" var="notyfontawesomecss_url"/>
    <spring:url value="/resources/js/noty/packaged/jquery.noty.packaged.min.js" var="notyjs_url"/>
    <script type="text/javascript" src="${jqueryjs_url}">
        <jsp:text/>
    </script>
    <link rel="stylesheet" type="text/css" href="${notybuttonscss_url}"/>
    <link rel="stylesheet" type="text/css" href="${notyanimatecss_url}"/>
    <link rel="stylesheet" type="text/css" href="${notyfontawesomecss_url}"/>

    <script type="text/javascript" src="${notyjs_url}"></script>
    <script type="text/javascript">
        var jq = jQuery.noConflict();
    </script>

    <script type="text/javascript" src="${jqueryuijs_url}">
        <jsp:text/>
    </script>


    <%--  <title>Учебные планы СНУ им.В.Даля</title> --%>
    <%--  <title>Учебные планы ТНУ</title>           --%>

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]>
    <script src="/resources/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

</head>

<body>

<div class="container">
    <div class="header">
        <ul class="nav nav-pills pull-right">
            <!--  <li class="active"><a href="#">Home</a></li>  -->
            <li class="active"><a href="#" onclick="javascript:return showAbout(event);">О системе</a></li>
            <li><a href="#">Контакты</a></li>
        </ul>
        <h3 class="text-muted">Инвентаризация автодорог</h3>
    </div>

    <div class="jumbotron">
        <h1>Информационная система автодора</h1>

        <p class="lead">Информационная система предназначена для автоматизации обработки дорожной информации.</p>

        <p><a class="btn btn-lg btn-success" href="mainpage" role="button" id="enterbutton">Вход</a></p>
    </div>


    <div class="footer">
        <p>&copy; КФУ 2015-2017</p>
    </div>

</div>
<!-- /container -->


<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script>
    jQuery(document).ready(function () {
        jQuery.fn.center = function () {
            this.css("position", "absolute");
            this.css("top", ((jQuery(window).height() - this.outerHeight()) / 2) + jQuery(window).scrollTop() + "px");
            this.css("left", ((jQuery(window).width() - this.outerWidth()) / 2) + jQuery(window).scrollLeft() + "px");
            return this;
        }

        <%-- end of bs_grid --%>
        ajaxLoader();
        //      ajaxFinished();
        jQuery(window).on('unload', function () {
            jQuery('#loadingDiv').hide();
            //          jQuery('#finishedDiv').hide();
            //     return "Handler for .unload() called.";
        });

        jQuery('#enterbutton').on('click',
                function () {
                    jQuery('#loadingText').text("Вход в систему.");
                    jQuery('#loadingDiv').show();
                    return true;
                }
        )


    });

    function ajaxLoader() {
        jQuery('body').append('<div id="loadingDiv"></div>');

        jQuery('#loadingDiv')
                .append('<p id="loadingText"></p>')
// src     .css('background', 'url(' + Xrm.Page.context.getServerUrl() + '/WebResources/new_ajax.gif) no-repeat 50% 25%')
                .css('background', 'url(resources/images/ajaxwait.gif) no-repeat 50% 25%')
                .css('padding-top', '90px')
                .css('background-color', '#F5F5F5')
                .css('border', '3px solid #00008B')
                .css('height', '160px')
                .css('width', '300px')
                .css('opacity', '0.7')
                .center()
                .hide(); // изначально скрываем сообщение

        jQuery('#loadingText')
                .css('text-align', 'center')
                .css('font', '20px bolder')
                .css('font-family', 'Segoe UI, Tahoma, Arial');


    }
    function e() {
        jQuery('#loadingText').text("Вход в систему.");
        jQuery('#loadingDiv').show();
        return true;
    }


    function generateNoty(type, text) {

        var n = noty({
            text:text,
            type:type,
            dismissQueue:true,
            layout:'center',
            closeWith:['click'],
            theme:'relax',
            maxVisible:10,
            animation:{
                open:'animated rollIn',
                close:'animated zoomOutUp',
                easing:'bounce',
                speed:500
            }
        });
        //   console.log('html: ' + n.options.id);
    }


    function showAbout(event) {
        event.stopPropagation();
        var mes = '<div class="activity-item">  <div class="activity"><h4> ' + 'Облачная автоматизированная система предназначена для информационного обеспечения производствеенного процесса Крымского автодора.' + '</h4></div> </div>'
        generateNoty('error', mes);
        setTimeout(function () {
            var mes = '<div class="activity-item">  <div class="activity"> <h3>' + 'Версия 1.0 сборка 23 от 10.01.2016' + '</h3></div> </div>'
            generateNoty('error', mes);
        }, 500);

        return false;
    }

</script>
</body>
</html>
