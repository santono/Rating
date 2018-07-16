<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %><%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %><%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %><%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<!DOCTYPE html>
<html lang="en">

<head>
  <!-- Standard Meta -->
  <meta charset="utf-8" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <spring:url value="/resources/js/jquery/jquery-3.2.1.min.js" var="jquery_js_url"/>
  <spring:url value="/resources/js/semanticui/components/visibility.js" var="visibility_js_url"/>
  <spring:url value="/resources/js/semanticui/components/sidebar.js" var="sidebar_js_url"/>
  <spring:url value="/resources/js/semanticui/components/transition.js" var="transition_js_url"/>

  <!--   <spring:url value="/resources/js/jquery/jquery-ui-1.8.6.custom.min.js" var="jqueryuijs_url" />  -->
  <spring:url value="/resources/css/semanticui/components/reset.css" var="reset_css_url"/>
  <spring:url value="/resources/css/semanticui/components/site.css"  var="site_css_url"/>
  <spring:url value="/resources/css/semanticui/components/container.css"  var="container_css_url"/>
  <spring:url value="/resources/css/semanticui/components/site.css"  var="site_css_url"/>
  <spring:url value="/resources/css/semanticui/components/grid.css"  var="grid_css_url"/>
  <spring:url value="/resources/css/semanticui/components/header.css"  var="header_css_url"/>
  <spring:url value="/resources/css/semanticui/components/image.css"  var="image_css_url"/>
  <spring:url value="/resources/css/semanticui/components/menu.css"  var="menu_css_url"/>
  <spring:url value="/resources/css/semanticui/components/divider.css"  var="divider_css_url"/>
  <spring:url value="/resources/css/semanticui/components/dropdown.css"  var="dropdown_css_url"/>
  <spring:url value="/resources/css/semanticui/components/segment.css"  var="segment_css_url"/>
  <spring:url value="/resources/css/semanticui/components/button.css"  var="button_css_url"/>
  <spring:url value="/resources/css/semanticui/components/list.css"  var="list_css_url"/>
  <spring:url value="/resources/css/semanticui/components/icon.css"  var="icon_css_url"/>
  <spring:url value="/resources/css/semanticui/components/sidebar.css"  var="sidebar_css_url"/>
  <spring:url value="/resources/css/semanticui/components/transition.css"  var="transition_css_url"/>
  <spring:url value="/resources/images/kfu_photo.jpg" var="kfu_photo_url"/>
  <spring:url value="/resources/css/noty/buttons.css" var="notybuttonscss_url"/>
  <spring:url value="/resources/css/noty/animate.css" var="notyanimatecss_url"/>
  <spring:url value="/resources/css/noty/font-awesome/css/font-awesome.min.css" var="notyfontawesomecss_url"/>
  <spring:url value="/resources/js/noty/packaged/jquery.noty.packaged.min.js" var="notyjs_url"/>

  
  
  <!-- Site Properties -->
  <title>Учет научных работ НПР</title>

  <link rel="stylesheet" type="text/css" href="${reset_css_url}" />
  <link rel="stylesheet" type="text/css" href="${site_css_url}" />

  <link rel="stylesheet" type="text/css" href="${container_css_url}" />
  <link rel="stylesheet" type="text/css" href="${grid_css_url}" />
  <link rel="stylesheet" type="text/css" href="${header_css_url}" />
  <link rel="stylesheet" type="text/css" href="${image_css_url}" />
  <link rel="stylesheet" type="text/css" href="${menu_css_url}" />

  <link rel="stylesheet" type="text/css" href="${divider_css_url}" />
  <link rel="stylesheet" type="text/css" href="${dropdown_css_url}" />
  <link rel="stylesheet" type="text/css" href="${segment_css_url}" />
  <link rel="stylesheet" type="text/css" href="${button_css_url}" />
  <link rel="stylesheet" type="text/css" href="${list_css_url}" />
  <link rel="stylesheet" type="text/css" href="${icon_css_url}" />
  <link rel="stylesheet" type="text/css" href="${sidebar_css_url}" />
  <link rel="stylesheet" type="text/css" href="${transition_css_url}" />

  <style type="text/css">

    .hidden.menu {
      display: none;
    }

    .masthead.segment {
      min-height: 700px;
      padding: 1em 0em;
    }
    .masthead .logo.item img {
      margin-right: 1em;
    }
    .masthead .ui.menu .ui.button {
      margin-left: 0.5em;
    }
    .masthead h1.ui.header {
      margin-top: 3em;
      margin-bottom: 0em;
      font-size: 4em;
      font-weight: normal;
    }
    .masthead h2 {
      font-size: 1.7em;
      font-weight: normal;
    }

    .ui.vertical.stripe {
      padding: 8em 0em;
    }
    .ui.vertical.stripe h3 {
      font-size: 2em;
    }
    .ui.vertical.stripe .button + h3,
    .ui.vertical.stripe p + h3 {
      margin-top: 3em;
    }
    .ui.vertical.stripe .floated.image {
      clear: both;
    }
    .ui.vertical.stripe p {
      font-size: 1.33em;
    }
    .ui.vertical.stripe .horizontal.divider {
      margin: 3em 0em;
    }

    .quote.stripe.segment {
      padding: 0em;
    }
    .quote.stripe.segment .grid .column {
      padding-top: 5em;
      padding-bottom: 5em;
    }

    .footer.segment {
      padding: 5em 0em;
    }

    .secondary.pointing.menu .toc.item {
      display: none;
    }

    @media only screen and (max-width: 700px) {
      .ui.fixed.menu {
        display: none !important;
      }
      .secondary.pointing.menu .item,
      .secondary.pointing.menu .menu {
        display: none;
      }
      .secondary.pointing.menu .toc.item {
        display: block;
      }
      .masthead.segment {
        min-height: 350px;
      }
      .masthead h1.ui.header {
        font-size: 2em;
        margin-top: 1.5em;
      }
      .masthead h2 {
        margin-top: 0.5em;
        font-size: 1.5em;
      }
    }


  </style>

  <script src="${jquery_js_url}"></script>
  <script src="${visibility_js_url}"></script>
  <script src="${sidebar_js_url}"></script>
  <script src="${transition_js_url}"></script>
  <script>
  $(document)
    .ready(function() {


              $(".button.canload").click(function(){
                  //alert('you clicked me');
                  //Adding 'loading' class here....
                  $(this).addClass("loading");
              });
              $(window).on("unload",function() {
                  $(".button.canload").removeClass("loading");
              });

      // fix menu when passed
      $('.masthead')
        .visibility({
          once: false,
          onBottomPassed: function() {
            $('.fixed.menu').transition('fade in');
          },
          onBottomPassedReverse: function() {
            $('.fixed.menu').transition('fade out');
          }
        })
      ;

      // create sidebar and attach to menu open
      $('.ui.sidebar')
        .sidebar('attach events', '.toc.item')
      ;

    })
  ;
  </script>
</head>
<body>

<!-- Following Menu -->
<div class="ui large top fixed hidden menu">
  <div class="ui container">
    <div class="right menu">
      <div class="item">
        <a href="<spring:url value='mainpage' />" class="ui button canload">Вход</a>
      </div>
        <sec:authorize access="isAnonymous()">
            <div class="item">
              <a href="<spring:url value='user/register' />" class="ui primary button canload">Регистрация</a>
            </div>
        </sec:authorize>
    </div>
  </div>
</div>

<!-- Sidebar Menu -->
<div class="ui vertical inverted sidebar menu">
  <a href="<spring:url value='mainpage' />" class="item">Вход</a>
  <sec:authorize access="isAnonymous()">
     <a href="<spring:url value='user/register' />" class="item">Регистрация</a>
  </sec:authorize>
</div>


<!-- Page Contents -->
<div class="pusher">
  <div class="ui inverted vertical masthead center aligned segment" >

    <div class="ui container">
      <div class="ui large secondary inverted pointing menu">
        <a class="toc item">
          <i class="sidebar icon"></i>
        </a>
        <div class="right item">
          <a href="<spring:url value='mainpage' />" class="ui inverted button canload">Вход</a>
          <sec:authorize access="isAnonymous()">
               <a href="<spring:url value='/user/register' />" class="ui inverted button canload">Регистрация</a>
          </sec:authorize>
        </div>
      </div>
    </div>

    <div class="ui text container" >
      <h1 class="ui inverted header">
        Учет научных работ НПР
      </h1>
      <h2>Информационная система учета научных работ НПР.</h2>
      <a href="mainpage" class="ui huge primary button canload">Вход<i class="right arrow icon"></i></a>
    </div>

  </div>

  <div class="ui vertical stripe segment">
    <div class="ui middle aligned stackable grid container">
      <div class="row">
        <div class="eight wide column">
          <h3 class="ui header">Заполните свои данные</h3>
          <p>Правильное и корректное заполнение данных способствует объективному формированию уровню Вашего рейтинга.</p>
          <h3 class="ui header">Загрузите список своих публикаций</h3>
          <p>Загруженные в базу данных электронные копии публикаций автоматически учитываются при расчете рейтинга.</p>
        </div>
        <div class="six wide right floated column">
          <img src="${kfu_photo_url}" class="ui large bordered rounded image">
        </div>
      </div>
      <div class="row">
        <div class="center aligned column">
          <a class="ui huge button">Проверяйте корректность внесенных данные</a>
        </div>
      </div>
    </div>
  </div>


  <div class="ui vertical stripe quote segment">
    <div class="ui equal width stackable internally celled grid">
      <div class="center aligned row">
        <div class="column">
          <h3>Информационна система обладает свойствами адаптивности</h3>
          <p>Для заполнения данных в информационной системе можно использовать любыекомпьютерные средства. В том числа планшеты и смартфоны.</p>
        </div>
        <div class="column">
          <h3>Данные вносятся как лично автором так и централизовано."</h3>
          <%--
		  <p>
		  
            <img src="assets/images/avatar/nan.jpg" class="ui avatar image"> <b>Гибкость</b> Сведения в информационной системе появляются из двух источников: вносятмся лично автором и централизовано специально уполномоченными лицами
          </p>
		  --%>
        </div>
      </div>
    </div>
  </div>

  <div class="ui vertical stripe segment">
    <div class="ui text container">
      <h3 class="ui header">Автоматический обмен данными</h3>
      <p>Информационная система разработана с учетом возможности обмена данных с другими существующими информационными системами.</p>
      <a class="ui large button">Детали</a>
      <h4 class="ui horizontal header divider">
        <a href="#">Подробнее..</a>
      </h4>
      <h3 class="ui header">Масшабируемость</h3>
      <p>Для обеспечения спобности работы в условиях с ростом количества пользователей, а также числа документов информационная система построена на принципах поддержки масштабируемости.</p>
      <a class="ui large button">Подробнее...</a>
    </div>
  </div>


  <div class="ui inverted vertical footer segment">
    <div class="ui container">
      <div class="ui stackable inverted divided equal height stackable grid">
        <div class="three wide column">
          <h4 class="ui inverted header">О системе</h4>
          <div class="ui inverted link list">
            <a href="#" class="item">Контакты</a>
          </div>
        </div>
        <div class="seven wide column">
          <h4 class="ui inverted header">КФУ 2017</h4>
          <p>Информационная система для сбора данных и формирования списка научных работ НПР.</p>
        </div>
      </div>
    </div>
  </div>
</div>

</body>

</html>
