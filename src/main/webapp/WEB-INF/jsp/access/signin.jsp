<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <!--link rel="shortcut icon" href="../../assets/ico/favicon.ico"-->
    <spring:url value="/resources/js/jquery-1.11.1.min.js" var="jqueryjs_url"/>
    <spring:url value="/resources/js/jquery/jquery-ui.js" var="jqueryuijs_url"/>


    <title>Регистрация в системе</title>

    <!-- Bootstrap core CSS -->
    <!--link href="../../dist/css/bootstrap.min.css" rel="stylesheet"-->
    <link rel="stylesheet" type="text/css" media="screen" href="resources/css/bootstrap.min.css"/>

    <!-- Custom styles for this template -->
    <!--link href="signin.css" rel="stylesheet"-->
    <link rel="stylesheet" type="text/css" media="screen" href="resources/css/signin.css"/>

    <!-- Just for debugging purposes. Don't actually copy this line! -->
    <!--[if lt IE 9]>
    <script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script type="text/javascript" src="${jqueryjs_url}">
        <jsp:text/>
    </script>
    <script type="text/javascript">
        var jq = jQuery.noConflict();
    </script>

    <script type="text/javascript" src="${jqueryuijs_url}">
        <jsp:text/>
    </script>
    <script type="text/javascript" src="resources/js/bootstrap.min.js">
        <jsp:text/>
    </script>

    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>

<body>

<div class="container">

    <%--
    <c:url var="loginUrl" value="j_spring_security_check" />
    <c:url var="loginUrl" value="${pageContext.request.contextPath}/j_spring_security_check" />
    <c:set var="uname" value="j_username" />
    <c:set var="upwd" value="j_password" />
    --%>
    <%--
        <c:url var="loginUrl" value="/login" />
            <c:set var="uname" value="username" />
            <c:set var="upwd" value="password" />
    --%>
    <c:url var="loginUrl" value="/j_spring_security_check"/>
    <c:set var="uname" value="j_username"/>
    <c:set var="upwd" value="j_password"/>
    <form class="form-signin" role="form" action="${loginUrl}" method="post">
        <h2 class="form-signin-heading">Пожалуйста, войдите в систему</h2>
        <input type="text" class="form-control" name="${uname}" placeholder="Логин.." required autofocus/>
        <input type="password" class="form-control" name="${upwd}" placeholder="Пароль.." required/>
        <!--
        <label class="checkbox">
            <input type="checkbox" value="remember-me"> Запомнить меня
        </label>
        -->
        <div class="g-recaptcha" data-sitekey="6LdKxA4TAAAAAM2D7p6SlcnaDFl7NeS-_AUfZUlQ"></div>
        <button id="btnD" class="btn btn-lg btn-primary btn-block" data-loading-text="Регистрация..." type="submit"
                autocomplete="off">Вход
        </button>
    </form>
    <p class="message">${message}</p>


</div>
<!-- /container -->

<script type="text/javascript">
    jQuery(document).ready(function () {
        jQuery.fn.center = function () {
            this.css("position", "absolute");
            this.css("top", ((jQuery(window).height() - this.outerHeight()) / 4) + jQuery(window).scrollTop() + "px");
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

        jQuery('#btnD').on('click',
                function () {
                    jQuery('#loadingText').text("Вход в систему.");
                    jQuery('#loadingDiv').show();
                    return true;
                }
        )


//    jQuery(document).ready(function(){
//        jQuery('#btnD').click(function(){
//            var btn = jQuery(this);
//            btn.button('Регистрация...');
//            setTimeout(function () {
//                btn.button('Вход');
//            }, 3000)
//        });
//    });
//      jQuery('#btnD').on('click', function () {
//          jQuery(this).button('loading') // button text will be "finished!"
//          setTimeout(function () {
//              btn.button('reset');
//          }, 15000)
//      })
//      jQuery( window ).on('unload', function() {
//          jQuery('#btnD').button('reset');
        //     return "Handler for .unload() called.";
//  });
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
</script>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
</body>
<head>
    <meta HTTP-EQUIV="Cache-Control: " CONTENT="no-store, no-cache, must-revalidate, max-age=0">
</head>
</html>
