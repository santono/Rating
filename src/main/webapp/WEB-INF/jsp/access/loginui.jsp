<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %><%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <!-- Standard Meta -->
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

  <!-- Site Properties -->
  <title>Вход с систему учета научных работ НПР</title>
  <spring:url value="/resources/js/jquery/jquery-3.2.1.min.js" var="jquery_js_url"/>
  <spring:url value="/resources/js/semanticui/components/form.js" var="form_js_url"/>
  <spring:url value="/resources/js/semanticui/components/transition.js" var="transition_js_url"/>
  <spring:url value="/resources/css/semanticui/components/reset.css" var="reset_css_url"/>
  <spring:url value="/resources/css/semanticui/components/site.css"  var="site_css_url"/>
  <spring:url value="/resources/css/semanticui/components/container.css" var="container_css_url"/>
  <spring:url value="/resources/css/semanticui/components/grid.css" var="grid_css_url"/>
  <spring:url value="/resources/css/semanticui/components/header.css" var="header_css_url"/>
  <spring:url value="/resources/css/semanticui/components/image.css" var="image_css_url"/>
  <spring:url value="/resources/css/semanticui/components/menu.css" var="menu_css_url"/>
  <spring:url value="/resources/css/semanticui/components/divider.css" var="divider_css_url"/>
  <spring:url value="/resources/css/semanticui/components/segment.css" var="segment_css_url"/>
  <spring:url value="/resources/css/semanticui/components/form.css" var="form_css_url"/>
  <spring:url value="/resources/css/semanticui/components/input.css" var="input_css_url"/>
  <spring:url value="/resources/css/semanticui/components/button.css" var="button_css_url"/>
  <spring:url value="/resources/css/semanticui/components/list.css" var="list_css_url"/>
  <spring:url value="/resources/css/semanticui/components/message.css" var="message_css_url"/>
  <spring:url value="/resources/css/semanticui/components/icon.css" var="icon_css_url"/>
  <link rel="stylesheet" type="text/css" href="${reset_css_url}">
  <link rel="stylesheet" type="text/css" href="${site_css_url}">

  <link rel="stylesheet" type="text/css" href="${container_css_url}">
  <link rel="stylesheet" type="text/css" href="${grid_css_url}">
  <link rel="stylesheet" type="text/css" href="${header_css_url}">
  <link rel="stylesheet" type="text/css" href="${image_css_url}">
  <link rel="stylesheet" type="text/css" href="${menu_css_url}">

  <link rel="stylesheet" type="text/css" href="${divider_css_url}">
  <link rel="stylesheet" type="text/css" href="${segment_css_url}">
  <link rel="stylesheet" type="text/css" href="${form_css_url}">
  <link rel="stylesheet" type="text/css" href="${input_css_url}">
  <link rel="stylesheet" type="text/css" href="${button_css_url}">
  <link rel="stylesheet" type="text/css" href="${list_css_url}">
  <link rel="stylesheet" type="text/css" href="${message_css_url}">
  <link rel="stylesheet" type="text/css" href="${icon_css_url}">

  <script src="${jquery_js_url}"></script>
  <script src="${form_js_url}"></script>
  <script src="${transition_js_url}"></script>
  <script src='https://www.google.com/recaptcha/api.js'></script>

  <style type="text/css">
    body {
      background-color: #DADADA;
    }
    body > .grid {
      height: 100%;
    }
    .image {
      margin-top: -100px;
    }
    .column {
      max-width: 450px;
    }
  </style>
    <c:url var="loginUrl" value="/j_spring_security_check"/>
    <c:set var="uname" value="j_username"/>
    <c:set var="upwd" value="j_password"/>

  <script>
  $(document)
    .ready(function() {

          $(".button").click(function(){
               //   alert('you clicked me');
               //Adding 'loading' class here....
              if($('.ui.form').form('is valid')) {
                  // form is valid (both email and name)
                  $(this).addClass("loading");
              }
          });
          $(window).on("unload",function() {
               $(".button").removeClass("loading");
          });


      $('.ui.form')
        .form({
       //   inline : true,
          on     : 'blur',

          fields: {
            name: {
              identifier  : '${uname}',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'Пожалуйста, введите логин'
                },
                {
                  type   : 'maxLength[16]',
                  prompt : 'Логин должен содержать не более 16 символов'
                },
                {
                  type   : 'minLength[1]',
                  prompt : 'Логин должен содержать не менее 1 символа'
                }
              ]
            },
            password: {
              identifier  : '${upwd}',
              rules: [
                {
                  type   : 'empty',
                  prompt : 'Пожалуйста, введите Ваш пароль'
                },
                {
                  type   : 'maxLength[16]',
                  prompt : 'Пароль должен содержать не более 16 символов'
                },
                {
                  type   : 'minLength[1]',
                  prompt : 'Пароль должен содержать не менее 1 символа'
                }
              ]
            }
          }
        })
      ;
    })
  ;
  </script>
</head>
<body>

<div class="ui middle aligned center aligned grid">
  <div class="column">
    <h2 class="ui teal image header">
   <!--   <img src="assets/images/logo.png" class="image"> -->
      <div class="content">
        Вход в информационную систему
      </div>
    </h2>
    <c:url var="loginUrl" value="/j_spring_security_check"/>
    <c:set var="uname" value="j_username"/>
    <c:set var="upwd" value="j_password"/>

    <form class="ui large form" action="${loginUrl}" method="post">
      <div class="ui stacked segment">
        <div class="required field">
          <div class="ui left icon input">
            <i class="user icon"></i>
            <input type="text" name="${uname}" placeholder="Логин..." maxlength="16" />
          </div>
        </div>
        <div class="required field">
          <div class="ui left icon input">
            <i class="lock icon"></i>
            <input type="password" name="${upwd}" placeholder="Пароль..." maxlength="12">
          </div>
        </div>
          <spring:eval var="sitekey" expression="@ratingProperties.google_recaptcha_site_key"/>
   <!--     <div class="g-recaptcha" data-sitekey="6LdKxA4TAAAAAM2D7p6SlcnaDFl7NeS-_AUfZUlQ"></div> -->
        <div class="field">
        <div class="g-recaptcha" data-sitekey="${sitekey}"></div>
        <span id="captchaError" class="error"
              style="display:none"></span>
        </div>
        <div class="ui fluid large teal submit button">Вход</div>
      </div>
      <c:if test="${not empty message}">
          <div class="ui message"><c:out value="${message}"/></div>
      </c:if>
      <div class="ui error message"></div>
    </form>

    <div class="ui message">
      Не зарегистрированы? <a href="user/register">Регистрация</a>
    </div>
  </div>
</div>

</body>

</html>
