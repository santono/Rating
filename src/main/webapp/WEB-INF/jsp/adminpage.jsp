<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html>
<head>
    <!-- Standard Meta -->
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">

    <spring:url var= "logoutUrl" value= "logout" />
    <spring:url var= "mainPageUrl" value= "/" />

    <spring:url value="/resources/js/jquery/jquery-3.2.1.min.js" var="jquery_js_url"/>
    <spring:url value="/resources/js/semanticui/semantic.min.js" var="semantic_js_url"/>
    <spring:url value="/resources/js/semanticui/semantic-ui-tree-picker-alertify.js" var="semantic_treepicker_js_url"/>
    <spring:url value="/resources/js/alertify/alertify.min.js" var="alertify_js_url"/>
<%--
    <spring:url value="/resources/js/vue/vue.min.js" var="vue_js_url"/>
--%>
    <spring:url value="/resources/js/vue/vue.js" var="vue_js_url"/>
    <spring:url value="/resources/js/vue/axios.min.js" var="axios_js_url"/>
    <spring:url value="/resources/css/semanticui/semantic.min.css" var="semantic_css_url"/>
    <spring:url value="/resources/css/semanticui/semantic-ui-tree-picker.css" var="semantic_treepicker_css_url"/>
    <spring:url value="/resources/js/vue/vue-router.min.js" var="vue_router_js_url"/>
    <spring:url value="/resources/js/vue/lodash.min.js" var="lodash_js_url"/>
    <spring:url value="/resources/js/vue/vs-notify.min.js" var="vs_notify_js_url"/>
    <spring:url value="/resources/js/vue/vue-datepicker-local.js" var="vue_datepicker_js_url"/>
    <spring:url value="/resources/css/vue/vue-datepicker-local.css" var="vue_datepicker_css_url"/>
    <spring:url value="/resources/js/photoswipe.min.js" var="photoswipe_js_url"/>
    <spring:url value="/resources/js/photoswipe-ui-default.min.js" var="photoswipe_ui_default_js_url"/>
    <spring:url value="/resources/css/photoswipe.css" var="photoswipe_css_url"/>
    <spring:url value="/resources/css/default-skin.css" var="default_skin_css_url"/>
    <spring:url value="/resources/css/alertify/alertify.min.css" var="alertify_css_url" />
    <spring:url value="/resources/css/alertify/themes/semantic.min.css" var="alertify_semantic_css_url" />
    <spring:url value="/resources/js/PDFViewer.js" var="pdfviewer_js_url"/>

    <!-- Site Properties -->
    <title>Учет научных работ НПР</title>
<%--
    <script src="https://unpkg.com/vue-router/dist/vue-router.js"></script>
--%>

    <link rel="stylesheet" type="text/css" href="${semantic_css_url}" />
    <link rel="stylesheet" type="text/css" href="${semantic_treepicker_css_url}" />
    <link rel="stylesheet" type="text/css" href="${vue_datepicker_css_url}" />
    <link rel="stylesheet" type="text/css" href="${photoswipe_css_url}" />
    <link rel="stylesheet" type="text/css" href="${default_skin_css_url}" />
    <link rel="stylesheet" type="text/css" href="${alertify_css_url}" />
    <link rel="stylesheet" type="text/css" href="${alertify_semantic_css_url}" />
    <script src="${jquery_js_url}"></script>
    <script src="${semantic_js_url}"></script>
    <script src="${semantic_treepicker_js_url}"></script>

    <script src="${alertify_js_url}"></script>

    <script src="${photoswipe_js_url}"></script>
    <script src="${photoswipe_ui_default_js_url}"></script>
    <script src="${pdfviewer_js_url}"></script>
    <style type="text/css">
        body {
            background-color: #FFFFFF;
        }
        .ui.menu .item img.logo {
            margin-right: 1.5em;
        }
        .main.container {
            margin-top: 2em;
        }
        .wireframe {
            margin-top: 2em;
        }
        .ui.footer.segment {
            margin: 5em 0em 0em;
            padding: 5em 0em;
        }
        body.pushable {
            background-color: #FFFFFF; !important
        }
<%--
        #accordionmenu {
            margin-left:0     !important;
            margin-right:0     !important;
            font-size:14px    !important;
            color:rgb(255, 255, 255) !important;
        }
        #accordionmenuadmin {
            margin-left:0;     !important;
            margin-right:0;     !important;
            font-size:14px;    !important;
            color:rgb(255, 255, 255) !important;
        }
--%>
        .ui.vertical.menu .item >.menu {
<%--
        .ui .vertical .fluid .accordion .text .menu {
--%>
            margin-left:0            !important;
            margin-right:0           !important;
            font-size:14px           !important;
            color:rgb(255, 255, 255) !important;
        }
        .ui.vertical.menu .item >.menu >.item >.active.title {
<%--
        .ui .vertical .fluid .accordion .text .menu .active .title{
--%>
           font-size:14px           !important;
           color: rgb(255, 255, 255) !important;
        }
        .ui.vertical.menu .item >.menu >.item >.content.menu >.item {
            font-size:14px           !important;
        }


        .vs-notify h4{ font-weight: bold; font-size:16px; margin:0; margin-bottom:4px; }

            /* Singleton */

        .vs-notify.single .ntf{ border:none; border:1px solid green; text-align:center; background:#5a5; }

            /* Custom notifications style */

        .vs-notify.custom .ntf
        {
            border:none; border-radius: 7px; font-size: 14px; padding: 10px; color: #5A6B81; background: #E5EBF1; cursor:default;
        }

        .vs-notify.custom .title{ position: relative; color: white; font-size: 12px; font-weight: 800; text-transform: uppercase; letter-spacing: 2px; background: #17f; padding: 8px; }

        .vs-notify.custom .close
        {
            width: 20px; height: 20px; line-height: 20px; position: absolute; top: 6px; right: 5px; cursor: pointer; color: red; font-weight: 900; border-radius: 4px;

            background: red; color: white; text-align: center;
        }

        .vs-notify.custom .content{ padding:14px 2px 6px 2px; background: #EAE7ED; }

            /* Style 1 */

        .vs-notify.style1 { width:180px; }

        .vs-notify.style1 .ntf
        {
            color: #8898A5; padding: 12px; background: #FCFDFF; border: 1px solid #eaeefb; margin:8px;
            box-shadow: 0 0 5px 0 rgba(232, 237, 250, 0.6), 0 3px 8px 0 rgba(232, 237, 250, 0.5);
        }
        .vs-notify.style1 h4 { color:red; font-weight: 600; }

            /* Style 2 */

        .vs-notify.style2 { width:80%; }

        .vs-notify.style2 .ntf
        {
            background: #444; border: 2px solid #fe0; border-radius:12px;
            box-shadow: 0 0 9px 0 rgba(55, 55, 55, 0.7); font-size:16px; height:90px; margin:8px; margin-top:8px; padding:0;
        }
        .vs-notify.style2 .cnt{ padding:12px;}
        .vs-notify.style2 h4 { color:#fe0; font-weight: 600; }

            /* Let's override ntf-bottom animation */

        .vs-notify.style2 .ntf-bottom-enter-active, .ntf-bottom-leave-active { transition: all 0.4s; }
        .vs-notify.style2 .ntf-bottom-enter   { opacity:0; transform: none; height:0; margin-top:-12px; }
        .vs-notify.style2 .ntf-bottom-leave-to{ opacity:0; transform: translateY(-95px); }

        [v-cloak] { display: none; }

        [v-cloak] .v-cloak--block {
            display: block!important;
        }

        [v-cloak] .v-cloak--inline {
            display: inline!important;
        }

        [v-cloak] .v-cloak--inlineBlock {
            display: inline-block!important;
        }

        [v-cloak] .v-cloak--hidden {
            display: none!important;
        }

        [v-cloak] .v-cloak--invisible {
            visibility: hidden!important;
        }

        .v-cloak--block,
        .v-cloak--inline,
        .v-cloak--inlineBlock {
            display: none!important;
        }


    </style>
    <%--
        Логин:y50163q3
        ID Пользователя:598338
        Персональные данные:Изменить
        Тарифный план:FreeHosting
        Количество файлов:0 из 25000
        ----------------

        Адрес ПУА: https://cp.beget.com
        Имя пользователя: y50163q3
        Пароль: CLwFG2jd
        Сервер: free24

    Тех. информация

        Сервер:y50163q3.beget.tech
    --%>
    <script type="text/javascript">
        // using context

        //$('.ui.sidebar')
        //  .sidebar({
        //    context: $('.bottom.segment')
        //  })
        //  .sidebar('attach events', '.menu .item')
        //;
        //$('.left.sidebar').first()
        //  .sidebar('attach events', '.sidebar.icon','show')
        //;
        //$('.sidebar.icon')
        //  .removeClass('disabled')
        //;
        $(function() {
            $('#show-sidebar').click(function() {
                //  $('#show-sidebar').hide();
                $('.ui.sidebar').sidebar('toggle');
                $('.ui.accordion').accordion({ exclusive: false });
              //  $('#ddnprmenu').dropdown({direction:'downward'});
            });
//            $('.ui.sidebar')
//                    .sidebar({
//                        onHide: function() {
//                            console.log('on hidden');
//                        $('body').css( "background-color","#FFFFFF" );
//                            $('body.pushable').css( "background-color","#FFFFFF" );
//                        }
//                    });
            $('#sbar').hide(function() {
                $('body').css( "background-color","#FFFFFF" );
//                $('body').removeClass( "pushable" );
                $('body.pushable').css( "background-color","#FFFFFF" );

            });
        });
        //https://api.mlab.com/api/1/databases/rating/collections/users?apiKey=kaUDFzJwz5GfBtAeUnriufsAYkJLyfLf
        //mlab.com santono ar1737
        //https://api.mlab.com/api/1/databases/rating/collections/predps?q={"id": "100000"}&apiKey=kaUDFzJwz5GfBtAeUnriufsAYkJLyfLf
    </script>

</head>

<body style="background-color: #FFFFFF">
    <div class="ui top fixed inverted menu" id="appTop">
           <div class="ui container">
               <div id="show-sidebar" class="button toggler">
                   <a class="header item">
                       <i class="sidebar icon"></i>
                       Меню
                   </a>
               </div>
<%--
               <div class="ui dropdown menu" v-if="isnpr" v-cloak>
                   <a class="item">
                       <i class="sidebar icon"></i>
                       Личные данные
                   </a>
                   <a class="item">
                       <i class="sidebar icon"></i>
                       Смена пароля
                   </a>
               </div>
--%>
               <a href="${mainPageUrl}" class="header item tiny ui button">На главную</a>
               <div class="header item center">
                   <div class="ui tiny inline loader" v-bind:class="{ active: isLoading }" id="hloader"></div>
                   <div v-cloak>{{ message }}</div>
               </div>
               <div class="right menu">
                   <a href="${logoutUrl}" class="header item tiny ui button" v-on:click="markExiting()" v-bind:class="{loading:isExiting}">Выход</a>
               </div>

           </div>
    </div>


<div id="sbar" class="ui inverted labeled icon left inline vertical sidebar menu" style="">
    <div id="appSidebar" >
<%--
    <router-link v-show="isadmin" to="/univ/1" class="item" ><i class="block layout icon"></i>Подразделения </router-link>
--%>
    <a class="item" v-show="isadmin || isdataadmin" v-on:click="browsentrlist" v-cloak>
        <i class="list icon"></i>
        Н-Т работы
    </a>
<%--
    <a class="item" v-show="isadmin || isdataadmin" v-cloak>
        <i class="smile icon"></i>
        Своды

    </a>
--%>
    <div class="item" v-if="isadmin  || isdataadmin">
        <div class="ui vertical fluid accordion text menu" id="accordionmenuswodyadmin"  >
            <div class="item">
                <a class="active title" >
                    Своды
                    <i class="dropdown icon"></i>
                </a>
                <div class="content menu">
                    <a class="item" v-show="isadmin || isdataadmin" v-on:click="browsereppokaz" v-cloak >
                        <i class="block layout icon"></i>
                        Показатели
                    </a>
                </div>
            </div>
        </div>
     </div>

    <a class="item" v-show="isnpr" v-on:click="browsentrlist" v-cloak>
        <i class="list icon"></i>
        Н-Т работы
    </a>

    <div class="item" v-if="isnpr">
        <div class="ui vertical fluid accordion text menu" id="accordionmenu"  >
            <div class="item">
                <a class="active title">
                    Л.кабинет
                    <i class="dropdown icon"></i>
                </a>
                <div class="content menu">
                    <a class="item"  v-if="isnpr && false" v-on:click="changeuserdataform" v-cloak>
                        <i class="address card icon"></i>
                        Личн.данные
                    </a>
                    <a class="item"  v-if="isnpr && !visiblechgpwd" v-on:click="activatechgpwdform" v-cloak>
                        <i class="user secret icon"></i>
                        Смена пароля
                    </a>
                </div>
            </div>
        </div>

<%--
        <div class="header">Личный кабинет</div>
        <div class="menu">
            <a class="item"  v-if="isnpr" v-cloak>
                <i class="address card icon"></i>
                Личные данные
            </a>
            <a class="item"  v-if="isnpr && !visiblechgpwd" v-on:click="activatechgpwdform" v-cloak>
                <i class="user secret icon"></i>
                Смена пароля
            </a>
        </div>
--%>
    </div>
    <div class="item" v-if="isadmin">
        <div class="ui vertical fluid accordion text menu" id="accordionmenuadmin"  >
            <div class="item">
                <a class="active title" >
                    Справочники
                    <i class="dropdown icon"></i>
                </a>
                <div class="content menu">
                    <a class="item" v-show="isadmin || isdataadmin" v-on:click="browsepodr" v-cloak >
                        <i class="site map icon"></i>
                        Подразделения
                    </a>
                    <a class="item" v-show="isadmin  || isdataadmin" v-on:click="browseuserslist" v-cloak >
                        <i class="address card icon"></i>
                        ППС
                    </a>
                    <a class="item" v-show="isadmin" v-on:click="browsepokazlist" v-cloak v-cloak>
                        <i class="block layout icon"></i>
                        Показатели
                    </a>
                    <a class="item" v-show="isadmin" v-on:click="browsedolglist" v-cloak v-cloak>
                        <i class="block layout icon"></i>
                        Должности
                    </a>
                    <a class="item"  v-show="isadmin" v-cloak v-cloak>
                        <i class="list icon"></i>
                        Ученые степени
                    </a>
                    <a class="item"  v-show="isadmin" v-cloak v-cloak>
                        <i class="list icon"></i>
                        Ученые звания
                    </a>
                </div>
            </div>
        </div>

        <%--
                <div class="header">Справочники</div>
                <div class="menu">
                   <a class="item" v-show="isadmin || isdataadmin" v-on:click="browsepodr" v-cloak>
                      <i class="site map icon"></i>
                      Подразделения
                   </a>
                   <a class="item" v-show="isadmin  || isdataadmin" v-on:click="browseuserslist" v-cloak>
                      <i class="address card icon"></i>
                      ППС
                   </a>
                   <a class="item" v-show="isadmin" v-on:click="browsepokazlist" v-cloak>
                      <i class="block layout icon"></i>
                       Показатели
                   </a>
                    <a class="item"  v-show="isadmin" v-cloak>
                        <i class="list icon"></i>
                        Ученые степени
                    </a>
                    <a class="item"  v-show="isadmin" v-cloak>
                        <i class="list icon"></i>
                        Ученые звания
                    </a>
               </div>
        --%>
    </div>

<%--
        <div class="ui dropdown item" id="ddnprmenu">
            Language <i class="dropdown icon"></i>
            <div class="menu">
                <a class="item">English</a>
                <a class="item">Russian</a>
                <a class="item">Spanish</a>
            </div>
        </div>
--%>
<%--
    <a class="item ui dropdown " v-show="isnpr" v-cloak>
         <i class="block layout icon"></i>
         <span class="text">Личные данные</span>
         <i class="dropdown icon"></i>
         <div class="menu" >
             <a class="item" v-on:click="browsentrlist" v-cloak>
                 Личные данные
             </a>
             <a class="item" v-on:click="browsentrlist" v-cloak>
                 Сменая пароля
             </a>
         </div>
    </a>
--%>
    </div> <%-- end of appSidebar --%>
</div>


<!--
  <div class="ui bottom attached segment pushable">  bad
-->
<div class="main text container pusher">
    <div id="app">
    <div class="ui basic segment" v-bind:class="{ loading: isLoadingContent }">
        <router-view></router-view>
        <h3 class="ui header"></h3>
        <div class="ui raised centered card" id="startcard">
            <div class="content">
                <div class="header">Выбор режима работы</div>
                <div class="description">
                    Нажмите на кнопку меню <i class="sidebar icon"></i>и выбeрите режим работы
                </div>
            </div>
        </div>
    </div>
    <vs-notify group="alert" position="bottom right"></vs-notify>

    </div> <!-- end of app -->
</div>

<%-- Second Modal --%>
<div class="ui second coupled mini modal" id="secondmodal">
    <i class="close icon"></i>
    <div class="header">
        При сохранении данных обнаружены следующие ошибки.
    </div>
    <div class="content" id="secondmodalcontent">
        Пример контента!
    </div>
    <div class="actions">
        <div class="ui black deny button">Выход</div>
    </div>
</div>

<%-- Third TreePicker Modal --%>
    <div class="ui tree-picker third coupled modal">
        <i class="close icon"></i>
        <div class="header">
            Подразделения
            <div class="ui menu">
                <a class="active tree item">
                    <i class="list icon"></i>
                    Выбрать
                </a>
                <a class="picked item">
                    <i class="checkmark icon"></i>
                    Выбранные <span class="count"></span>
                </a>
            </div>
        </div>
        <div class="ui search form">
            <div class="field">
                <div class="ui icon input">
                    <input type="text" placeholder="Поиск">
                    <i class="search icon"></i>
                </div>
            </div>
        </div>
        <div class="content">
            <div class="ui active inverted dimmer">
                <div class="ui text loader">Загрузка данных</div>
            </div>
            <div class="tree-tab">
                <div style="height: 400px"></div>
            </div>
            <div class="search-tab">
            </div>
            <div class="picked-tab">
            </div>
        </div>
        <div class="actions">
            <a class="pick-search">
                <i class="checkmark icon"></i>
                Выбрать все
            </a>
            <a class="unpick-search">
                <i class="remove icon"></i>
                Убрать все
            </a>
            <a class="unpick-picked">
                <i class="remove icon"></i>
                Убрать все
            </a>
            <a class="ui blue button accept">Принять</a>
            <a class="ui button close">Отмена</a>
        </div>
    </div>


<%--
<%@ include file="vue_components/templates/univrow_tpl.jsp"%>
<%@ include file="vue_components/templates/univform_tpl.jsp"%>
<%@ include file="vue_components/templates/univtable_tpl.jsp"%>
--%>




<script src="${vue_js_url}"></script>
<script src="${vue_router_js_url}"></script>
<script src="${axios_js_url}"></script>
<script src="${lodash_js_url}"></script>
<script src="${vs_notify_js_url}"></script>
<script src="${vue_datepicker_js_url}"></script>

<%@ include file="vue_components/univrow_all.jsp"%>
<%@ include file="vue_components/univform_all.jsp"%>
<%@ include file="vue_components/univtable_all.jsp"%>

<%@ include file="vue_components/podrsel_all.jsp"%>
<%@ include file="vue_components/userrow_all.jsp"%>
<%@ include file="vue_components/userform_all.jsp"%>
<%@ include file="vue_components/userstable_all.jsp"%>

<%@ include file="vue_components/pokazrow_all.jsp"%>
<%@ include file="vue_components/pokazform_all.jsp"%>
<%@ include file="vue_components/pokaztable_all.jsp"%>

<%@ include file="vue_components/dolgrow_all.jsp"%>
<%@ include file="vue_components/dolgform_all.jsp"%>
<%@ include file="vue_components/dolgtable_all.jsp"%>


<%@ include file="vue_components/authors_all.jsp"%>
<%@ include file="vue_components/ntrrow_all.jsp"%>
<%@ include file="vue_components/ntrform_all.jsp"%>
<%@ include file="vue_components/ntrtable_all.jsp"%>


<%@ include file="vue_components/chgpwdform_all.jsp"%>
<%@ include file="vue_components/udataform_all.jsp"%>
<%@ include file="vue_components/reportpokaztable_all.jsp"%>

<%--
<%@ include file="vue_components/js/univform.jsp"%>
<%@ include file="vue_components/js/univrow.jsp"%>
<%@ include file="vue_components/js/univtable.jsp"%>
--%>

<%@ include file="vue_components/js/main_adm_top.jsp"%>
<%@ include file="vue_components/js/main_adm_sidebar.jsp"%>
<%@ include file="vue_components/js/main_admp.jsp"%>


<script>

    $(document).ready(function() {
        // initialize all modals
        $('.coupled.modal')
                .modal({
                    allowMultiple: true
                });
        });

    alertify.defaults.transition = "zoom";
    alertify.defaults.theme.ok = "ui positive button";
    alertify.defaults.theme.cancel = "ui black button";

</script>
<!-- Photoswipe region-->

<!-- Root element of PhotoSwipe. Must have class pswp. -->
<div class="pswp" tabindex="-1" role="dialog" aria-hidden="true">

    <!-- Background of PhotoSwipe.
         It's a separate element as animating opacity is faster than rgba(). -->
    <div class="pswp__bg"></div>

    <!-- Slides wrapper with overflow:hidden. -->
    <div class="pswp__scroll-wrap">

        <!-- Container that holds slides.
            PhotoSwipe keeps only 3 of them in the DOM to save memory.
            Don't modify these 3 pswp__item elements, data is added later on. -->
        <div class="pswp__container">
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
            <div class="pswp__item"></div>
        </div>

        <!-- Default (PhotoSwipeUI_Default) interface on top of sliding area. Can be changed. -->
        <div class="pswp__ui pswp__ui--hidden">

            <div class="pswp__top-bar">

                <!--  Controls are self-explanatory. Order can be changed. -->

                <div class="pswp__counter"></div>

                <button class="pswp__button pswp__button--close" title="Закрыть (Esc)"></button>

                <button class="pswp__button pswp__button--share" title="Поделиться"></button>

                <button class="pswp__button pswp__button--fs" title="Полный экран"></button>

                <button class="pswp__button pswp__button--zoom" title="Zoom in/out"></button>

                <!-- Preloader demo http://codepen.io/dimsemenov/pen/yyBWoR -->
                <!-- element will get class pswp__preloader- -active when preloader is running -->
                <div class="pswp__preloader">
                    <div class="pswp__preloader__icn">
                        <div class="pswp__preloader__cut">
                            <div class="pswp__preloader__donut"></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="pswp__share-modal pswp__share-modal--hidden pswp__single-tap">
                <div class="pswp__share-tooltip"></div>
            </div>

            <button class="pswp__button pswp__button--arrow--left" title="Предыдущий (стрелка влево)">
            </button>

            <button class="pswp__button pswp__button--arrow--right" title="Следующий (стрелка вправо)">
            </button>

            <div class="pswp__caption">
                <div class="pswp__caption__center"></div>
            </div>

        </div>

    </div>

</div>

<!-- end of photoswipe region -->

</body>
</html>
