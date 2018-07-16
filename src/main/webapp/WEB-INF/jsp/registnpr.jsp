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

  <spring:url value="/resources/js/jquery/jquery-3.2.1.min.js" var="jquery_js_url"/>
  <spring:url value="/resources/js/semanticui/semantic.min.js" var="semantic_js_url"/>
  <spring:url value="/resources/js/semanticui/Semantic-UI-Alert.js" var="semantic_UI_Alert_js_url"/>
  <spring:url value="/resources/js/vue/vue.js" var="vue_js_url"/>
  <spring:url value="/resources/js/vue/axios.min.js" var="axios_js_url"/>
  <spring:url value="/resources/js/vue/lodash.min.js" var="lodash_js_url"/>
  <spring:url value="/resources/js/vue/vs-notify.min.js" var="vs_notify_js_url"/>
  <spring:url value="/resources/css/semanticui/semantic.min.css" var="semantic_css_url"/>
  <spring:url value="/resources/css/semanticui/Semantic-UI-Alert.css" var="semantic_UI_Alert_css_url"/>


  <title>Регистрация пользователя</title>

  <link rel="stylesheet" type="text/css" href="${semantic_css_url}" />
  <link rel="stylesheet" type="text/css" href="${semantic_UI_Alert_css_url}" />
  <style type="text/css">
  body {
    background-color: #FFFFFF;
  }
  .ui.menu .item img.logo {
    margin-right: 1.5em;
  }
  .main.container {
    margin-top: 7em;
  }
  .wireframe {
    margin-top: 2em;
  }
  .ui.footer.segment {
    margin: 5em 0em 0em;
    padding: 5em 0em;
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


  </style>

</head>
<c:url var="actionUrl" value="/user/save" />

<body>

  <div class="ui fixed inverted menu">
    <div class="ui container">
      <div class="header item center">
        Регистрация нового пользователя
      </div>	
    <div class="right menu">
      <div class="item">
        <a href="<c:url value='/'/>" class="ui button">Выход</a>
      </div>
    </div>
	</div>
  </div>
  <div id="app">
  <chkmailform v-bind:show.sync="showModal" v-on:eshowmodal="setEShowModal" >
  </chkmailform>
  <div class="ui main container fluid">
  <div class="ui segment">
  Уважаемый пользователь! Пожалуйста, заполните все поля форм информацией о себе и нажмите на кнопку Отправить, чтобы руководитель подразделения, в котором Вы работаете, мог верифицировать Ваши данные. Поля форм, помеченные * (звездочкой), являются обязательными для заполнения!
После верификации Ваших данных Вам станут доступны все сервисы ИАС "Рейтинг НПР" в соответствии установленными правами доступа.
  </div>
  <form:form role="form" method="post" action="${actionUrl}" class="ui form" acceptCharset="UTF-8" id="recForm" commandName="userEntity" >
      <div class="ui stacked segment">
      <div class="ui two column grid">
        <div class="column">
         <h4 class="ui top attached block header">
              Личные данные
         </h4>
         <div class="ui bottom attached segment">
             <div class="two fields">
                 <div class="required field">
                     <label>Логин (логин для входа в систему)</label>
                     <form:input type="text" v-model="login" placeholder="Логин входа в систему" path="login" maxlength="12" id="loginField" />
<%--
                     <div class="ui left pointing red label" id="loginLabel"><i class="ban icon" id="loginIcon"></i></div>
--%>
                 </div>
                 <div class="required field">
                     <label>E-mail</label>
                     <form:input type="email" v-model="email" placeholder="Адрес электронной почты" path="email" pattern="[a-zA_Z0-9._%+-]+@[a-zA_Z0-9.-]+\.[a-zA_Z]{2,3}$" maxlength="16" id="emailField" readonly="true" />
                 </div>
             </div>
             <div class="required field">
			   <label>Фамилия</label>
               <form:input type="text" v-model="fam" placeholder="Фамилия" path="fam" maxlength="32" id="famField" />
             </div>
             <div class="required field">
			   <label>Имя</label>
               <form:input type="text"  placeholder="Имя"  path="nam" maxlength="16" id="namField"/>
             </div>
             <div class="required field">
			   <label>Отчество</label>
               <form:input type="text"  placeholder="Отчество" path="otc" maxlength="16" id="otcField"/>
             </div>
             <div class="field">
			   <label>Ученое звание</label>
               <form:select class="ui dropdown"  path="uzwan">
			     <option value="0">-- нет --</option>
			     <option value="1">доцент</option>
			     <option value="2">профессор</option>
			     <option value="3">м.н.с.</option>
			     <option value="4">с.н.с.</option>
			   </form:select>
             </div>
             <div class="field">
			   <label>Ученая степень</label>
               <form:select v-model="ustep"  path="ustep" class="ui dropdown">
                 <form:option value="0" data-id="0">без степени </form:option>
                 <c:if test="${not empty uStepList}">
                     <c:forEach var="p" items="${uStepList}">
                         <form:option value="${p.id}" data-id="${p.id}">${p.name}</form:option>
                     </c:forEach>
                 </c:if>

               </form:select>

             </div>
             <div v-if="ustep>0" class="ui bottom attached message">
                 {{nameUStep}}
             </div>
             <div class="field">
			   <label>Дополнительные личные данные</label>
               <form:input type="text" placeholder="Дополнительные личные данные" path="dopInf" maxlength="128"/>
             </div>
         </div>
        </div>
        <div class="column">
         <h4 class="ui top attached block header">
              Данные для поиска в наукометрических базах
         </h4>
         <div class="ui bottom attached segment">
             <div class="field">
			   <label>Фамилия и Имя на английском языке:</label>
               <form:input type="text" placeholder="Фамилия и Имя на английском языке" path="engFio" maxlength="32" pattern="[A-Za-z ]+"/>
             </div>
             <div class="field">
			   <label>AuthorID (elibrary.ru): *</label>
               <form:input type="text" placeholder="AuthorID (elibrary.ru)" path="authorIdEliblaryRu" maxlength="32"/>
             </div>
             <div class="field">
			   <label>Список публикаций автора (elibrary.ru): *</label>
               <form:input type="text" placeholder="Укажите интернет-ссылку на страницу" path="hrefElibraryRu" maxlength="64"/>
             </div>
             <div class="field">
			   <label>ORCID (scopus.com):</label>
               <form:input type="text" placeholder="ORCID (scopus.com):" path="orcIdScopusCom" maxlength="32"/>
             </div>
             <div class="field">
			   <label>Профиль автора (scopus.com):</label>
               <form:input type="text" placeholder="укажите интернет-ссылку на страницу" path="hrefScopusCom" maxlength="64"/>
             </div>
             <div class="field">
			   <label>ResearcherID (apps.webofknowledge.com):</label>
               <form:input type="text" name="reseacherIdWebOfKnoledgeCom" placeholder="ResearcherID (apps.webofknowledge.com):" path="reseacherIdWebOfKnoledgeCom" maxlength="32"/>
             </div>
             <div class="field">
			   <label>Профиль автора (apps.webofknowledge.com):</label>
               <form:input type="text" placeholder="укажите интернет-ссылку на страницу" path="hrefWebOfKnoledgeCom" maxlength="64"/>
             </div>
             <div class="field">
			   <label>Дополнительные личные данные:</label>
               <form:input type="text"  placeholder="дополнительные личные данные" path="dopInfForSearch" maxlength="128"/>
             </div>
         </div>
        </div>
      </div>
         <h4 class="ui top attached block header">
              Служебные данные
         </h4>
         <div class="ui bottom attached segment">
             <div class="field">
			   <label>Категория персонала: *</label>
               <form:select class="ui dropdown" path="shifrKat">
			   <form:option value="0">-- Не указано --</form:option>
			   <form:option value="1">ППС</form:option>
			   <form:option value="2">НР</form:option>
			   <form:option value="3">РП</form:option>
			   <form:option value="4">ИПТ</form:option>
			   <form:option value="5">АХП</form:option>
			   <form:option value="4">УВП</form:option>
			   </form:select>
             </div>
             <div class="field">
			   <label>Должность: *</label>
<%--
               <form:input type="text" placeholder="Должность из справочника" path="shifrDol" maxlength="32"/>
--%>
               <form:select class="ui dropdown shifrDol" v-model="shifrDol"  path="shifrDol" >
                   <form:option value="0" data-id="0">-- Укажите должность --</form:option>
                   <c:if test="${not empty dolgList}">
                       <c:forEach var="dol" items="${dolgList}">
                         <form:option value="${dol.id}" data-id="${dol.id}">${dol.name}</form:option>
                       </c:forEach>
                   </c:if>
               </form:select>
             </div>
             <div class="field">
			   <label>Структурное подразделение (Филиал): *</label>
                 <form:select class="ui dropdown shifruni" v-model="shifrUni"  path="shifrUni" >
                     <form:option value="1" data-id="1">КФУ </form:option>
                     <c:if test="${not empty univList}">
                         <optgroup label="Структурные подразделения">
                             <c:forEach var="uns" items="${univList}">
                                 <c:if test="${uns.id<2400000}">
                                     <form:option value="${uns.id}" data-id="${uns.id}">${uns.name}</form:option>
                                 </c:if>
                             </c:forEach>
                         </optgroup>
                         <optgroup label="Филиалы">
                             <c:forEach var="unf" items="${univList}">
                                 <c:if test="${unf.id>=2400000}">
                                     <form:option value="${unf.id}" data-id="${unf.id}">${unf.name}</form:option>
                                 </c:if>
                             </c:forEach>
                         </optgroup>
                      </c:if>
                 </form:select>
             </div>
             <div class="field" v-if="shifrUni>0">
			   <label>Институт (Факультет): *</label>
<%--
               <form:input type="text" placeholder="Институт (Факультет)" path="shifrFac" />
--%>
               <form:select class="ui dropdown shifrfac" v-model="shifrFac"  path="shifrFac" id="faclistcontainer">
                     <form:option value="0" data-id="0">-- нет -- </form:option>
                     <c:if test="${not empty facList}">
                         <c:forEach var="fi" items="${facList}">
                            <form:option value="${fi.id}" data-id="${fi.id}">${fi.name}</form:option>
                         </c:forEach>
                     </c:if>
               </form:select>

             </div>
             <div class="field" v-if="shifrFac>0">
			   <label>Кафедра (Отдел): *</label>
<%--
               <form:input type="text" placeholder="Кафедра (Отдел)" path="shifrKaf" />
--%>
               <form:select class="ui dropdown shifrkaf" v-model="shifrKaf"  path="shifrKaf"  id="kaflistcontainer">
                   <form:option value="0" data-id="0">-- нет -- </form:option>
                   <c:if test="${not empty kafList}">
                       <c:forEach var="ki" items="${kafList}">
                           <form:option value="${ki.id}" data-id="${ki.id}">${ki.name}</form:option>
                       </c:forEach>
                   </c:if>
               </form:select>
             </div>
<%--
             <div class="inline fields">
                  <label for="shifrWr">Вид найма:</label>
                  <div class="field">
                      <div class="ui radio checkbox">
                          <form:radiobutton path="shifrWr" checked="" tabindex="0" class="hidden"  value="1" />
                          <label>штат</label>
                      </div>
                  </div>
                  <div class="field">
                      <div class="ui radio checkbox">
                          <form:radiobutton path="shifrWr" tabindex="0" class="hidden" value="2" />
                          <label>внутреннее совместительство</label>
                      </div>
                  </div>
                  <div class="field">
                      <div class="ui radio checkbox">
                          <form:radiobutton path="shifrWr" tabindex="0" class="hidden" value="3" />
                          <label>внешнее совместительство</label>
                      </div>
                  </div>
             </div>
--%>
             <div class="field">
			   <label>Вид найма: </label>
               <form:select v-model="shifrWr" class="ui dropdown" placeholder="штат / внутреннее совместительство / внешнее совместительство" path="shifrWr" >
                   <form:option value="0">-- Укажите вид найма --</form:option>
                   <form:option value="1">штат</form:option>
                   <form:option value="2">внутреннее совместительство</form:option>
                   <form:option value="3">внешнее совместительство</form:option>
               </form:select>
             </div>
             <div class="field" v-if="shifrWr==2 || shifrWr==3">
			   <label>Должность и основное место работы для совместителей из категории персонал – НПР:</label>
               <form:input type="text" placeholder="Должность и основное место работы для совместителей" path="dolgOsnMr" maxlength="128"/>
             </div>
             <div class="field">
			   <label>Дополнительные служебные данные:</label>
               <form:input type="text" placeholder="дополнительные служебные данные:" path="dopSlInfo" maxlength="128"/>
             </div>
         </div>
         <div class="ui fluid large teal submit button" v-show="canbesaved">Отправить</div>
         <div class="ui error message"></div>
		 </div>    <%-- ui stacked segment --%>
         <form:hidden path="id" />
<%--
         <form:hidden path="dataCreate" />
         <form:hidden path="dataDelete" />
--%>
         <form:hidden path="active" />
         <form:hidden path="userCode" />
         <form:hidden path="statusCode" />
<%--
         <form:hidden path="dataVerification" />
--%>
         <form:hidden path="shifrIdSup" />
  </form:form>
	  
  </div> <%-- End of container fluid --%>
  <vs-notify group="alert"></vs-notify>
  <vs-notify group="basic"></vs-notify>
  <vs-notify group="single"   position="top center"></vs-notify>
  <vs-notify group="reversed" position="bottom left"   :reverse="true"></vs-notify>
  <vs-notify group="fade"     position="bottom center" :reverse="true" transition="ntf-fade"></vs-notify>

  <vs-notify group="style1" position="top left"   :reverse="true" transition="ntf-top" :duration="1200"></vs-notify>
  <vs-notify group="style2" position="center bottom" transition="ntf-bottom" :reverse="true"></vs-notify>



  </div> <%-- End of APP --%>


  <div class="ui inverted vertical footer segment">
    <div class="ui center aligned container">
      <div class="ui stackable inverted divided grid">
        <div class="seven wide column">
<%--
          <h4 class="ui inverted header">Footer Header</h4>
--%>
          <p>После подтвеждения отвественным лицом указанных данных, Вам будет послано уведомление по электронной почте.</p>
        </div>
      </div>
    </div>
  </div>

  <div class="ui modal" id="resultModal">
      <i class="close icon"></i>
      <div class="header">
          Данные сохранены.
      </div>
      <div class="content" id="modalcontent">
          <p> Заполненные Вами данные для регистрации
              успешно сохранены в базе данных.
          </p><p>
              После проверки данных уполномоченным администратом Вам
              по электронной почте будут отправлены данные для входа
              в информационную систему.
          </p>
      </div>
      <div class="actions">
          <div class="ui button ok">Ок</div>
      </div>
  </div>
  <script src="${jquery_js_url}"></script>
  <script src="${semantic_js_url}"></script>
  <script src="${semantic_UI_Alert_js_url}"></script>
  <script src="${vue_js_url}"></script>
  <script src="${axios_js_url}"></script>
  <script src="${lodash_js_url}"></script>
  <script src="${vs_notify_js_url}"></script>

  <%@ include file="vue_components/chkmailform_all.jsp"%>

</body>
<script>

    $(document).ready(function () {
        $('select.dropdown')
                .dropdown();
        ;
        $('.ui.radio.checkbox')
                .checkbox()
        ;
        setSubmitAjaxForRecForm();
        setChangeEventForLogin();

        $('.ui.modal')
          .modal('setting', 'transition', 'horizontal flip')
        ;
        $('.ui.form').form({
           inline : true,
           on     : 'blur',

           fields: {
                    login: {
                      identifier  : 'login',
                            rules: [
                                {
                                    type   : 'empty',
                                    prompt : 'Пожалуйста введите логин'
                                },
                                {
                                    type : 'regExp[/^[a-z0-9_-]{4,16}$/]',
                                    prompt: 'Введите корректный логин'
                                }
                            ]
                        },
                    email: {
                        identifier  : 'email',
                            rules: [
                                {
                                    type   : 'empty',
                                    prompt : 'Пожалуйста введите адрес электронной почты'
                                },
                                {
                                    type   : 'email',
                                    prompt : 'Введите корректный email'
                                }
                            ]
                        },
                    fam : {
                          identifier: 'fam',
                          rules: [
                              {
                               type:'empty',
                               prompt:'Заполните поле Фамилия'
                              },
                              {
                               type:'maxLength[32]',
                               prompt:'Фамилия не длинее 32 символов'
                              },
                              {
                               type:'regExp[/^[А-ЯЁа-яё-]{2,32}$/]',
                               prompt:'В поле Фамилия допускаются только символы кириллицы'
                              }
                          ]
                    },
                    nam : {
                          identifier: 'nam',
                          rules: [
                              {
                               type:'empty',
                               prompt:'Заполните поле Имя'
                              },
                              {
                               type:'maxLength[16]',
                               prompt:'Имя не длинее 16 символов'
                              },
                              {
                               type:'regExp[/^[А-ЯЁа-яё-]{2,16}$/]', <%--//'regExp[/^([А-ЯЁ][а-яё]+[\-\s]?){2,}$/]', [А-ЯЁ][а-яё]+[\-\s]?){3,}   --%>
                               prompt:'В поле Имя допускаются только символы кириллицы'
                              }
                          ]
                    },
                    shifrUni:{
                        identifier: 'shifrUni',
                        rules: [
                            {
                                type:'empty',
                                prompt:'Заполните поле Структурное подразделение'
                            }
                        ]
                    }
                   }
                })
        ;
<%--
        $('.ui.form')
                .form({
                    fields : validationRules,
                    inline : true,
                    on     : 'blur'
                })
        ;
--%>
        <%-- Конец инициализации jQuery--%>

    });
    <c:url value="/" var="uri2" />
    var rootPath="${uri2}";
    if (rootPath.length>2)
        rootPath=rootPath.substr(0,rootPath.length-1);

    var app = new Vue({
        el: '#app',
        data: {
            message   : 'Hello World!',
            shifrUni  : ${userEntity.shifrUni},
            shifrFac  : ${userEntity.shifrFac},
            shifrKaf  : ${userEntity.shifrKaf},
            ustep     : '${userEntity.ustep}',
            shifrDol  : '${userEntity.shifrDol}',
            nameUStep : '',
            shifrWr   : '${userEntity.shifrWr}',
            login     : '${userEntity.login}',
            email     : '${userEntity.email}',
            fam       : '${userEntity.fam}',
            badLogin  : true,
            okLogin   : false,
            showModal : false,
            rootPath:'/r'

        },
        components: {
            'chkmailform' : chkmailform
        },
        watch: {
            ustep:function (val,oldVal) {
                if (val!=oldVal) {
                    this.getShortNameUStep();
                }
            },
            shifrUni:function (val,oldVal) {
                if (val!=oldVal) {
                    this.getFacListForUni();
                }
            },
            shifrFac:function (val,oldVal) {
                if (val!=oldVal) {
                    this.getKafListForFac();
                }
            }
        },
        computed:{
            canbesaved:function() {
                        var is=this.login && this.email && this.fam && _.isString(this.login) && _.isString(this.email) && _.isString(this.fam);
                        if (is && this.login.trim().length>1 && this.email.trim().length>3 && this.fam.trim().length>2)
                           return true;
                        else
                           return false;
            }

        },
        methods: {
            onSubmit: function() {
                 alert('onSubmit');
            },
            fillfaclist: function () {
                this.show = true;
            },
            ECheckLogin:function (event){
                  console.log('*** ECheckLogin ***');
                  this.loginCheck(event);
            },
            loginCheck:function(event) {
                  console.log('inside loginCheck '+this.login);
                  if (this.login)
                     this.ajaxCheckLogin();
            },
            markGoodLogin:function() {
                  this.okLogin=true;
                  this.badLogin=false;
            },
            markBadLogin:function() {
                this.okLogin=false;
                this.badLogin=true;
            },
            ajaxCheckLogin:function() {
                console.log('inside ajaxCheckLogin');
                <c:url value="/util/chklogin/" var="uri2" />
                var uri1="${uri2}";
                var uri=uri1+this.login;
                var vm=this;
                axios.get(uri)
                        .then(function (response) {
                            if (response.data.shifr==1) {
                                vm.markGoodLogin();
                            }
                            else {
                                vm.markBadLogin();
                            }
                        })
                        .catch(function (error) {
                           alert("Ошибка проверки поля login "+error());
                });

           },
            getShortNameUStep: function () {
                <c:url value="/util/ustep/" var="uri2" />
                var uri1="${uri2}";
                var uri=uri1+this.ustep;
                var vm=this;
                axios.get(uri)
                   .then(function (response) {
                                vm.nameUStep = response.data.name.trim();
                       })
                   .catch(function (error) {
                       vm.nameUStep = '';
                    });

            },
            getFacListForUni: function () {
                <c:url value="/util/getfaclist/" var="uri2" />
                var uri1="${uri2}";
                var uri=uri1+this.shifrUni;
                var vm=this;
                axios.get(uri)
                        .then(function (response) {
                            generateSuccessHTMLOutputFac(response);
                        })
                        .catch(function (error) {
                         vm.nameUStep = '';
                });

            },
            getKafListForFac: function () {
                <c:url value="/util/getfaclist/" var="uri2" />
                var uri1="${uri2}";
                var uri=uri1+this.shifrFac;
                var vm=this;
                axios.get(uri)
                        .then(function (response) {
                            generateSuccessHTMLOutputKaf(response);
                        })
                        .catch(function (error) {
                    vm.nameUStep = '';
                });

            },
            setEShowModal : function(newVal,newEmail) {
//                console.log('setEShowModal')
                this.showModal=newVal;
                if (!newEmail) {
//                    console.log('setEShowModal !newEMail');
                    var emailval=$("#emailField").val();
                    this.email=emailval;
                    var isStr=false;
                    if (emailval) {
                        isStr=_.isString(emailval);
                    }
//                    console.log('email='+emailval);
                    if (!(emailval && isStr && emailval.trim().length>3))
                       window.history.go(-1);
                    else
                       return;
                }
             //   this.showModal=false;
                if (newEmail.length>3) {
//                        $("#emailField").val(newEmail);
                        this.email=newEmail;
//                        console.log(' new mail setted '+newEmail);
//                        console.log(JSON.stringify(this.selectedrec));
                } else {
//                  console.log('setEShowModal-1 !newEMail');
                  window.history.go(-1);
                }
//                console.log('near exit setEShowModal');
            }
        },
        mounted : function () {
            this.$nextTick(function () {
                var id="${userEntity.id}";
                if (id<1) {
                    this.showModal = true;
                }
            });
            this.rootPath=window.rootPath;

        }

    });
    function generateSuccessHTMLOutputFac(response) {
        var resultElement = document.getElementById('faclistcontainer');

        var retval = '<option value="0">-- нет --</option>';
        for (var i=0;i<response.data.length;i++) {
            retval = retval+'<option value='+JSON.stringify(response.data[i].id)+'>'+response.data[i].name.trim()+'</option>';
        }
        resultElement.innerHTML=retval;
        $('select.dropdown.shifrfac')
                .dropdown();
        ;

    }
    function generateSuccessHTMLOutputKaf(response) {
        var resultElement = document.getElementById('kaflistcontainer');

        var retval = '<option value="0">-- нет --</option>';
        for (var i=0;i<response.data.length;i++) {
            retval = retval+'<option value='+JSON.stringify(response.data[i].id)+'>'+response.data[i].name.trim()+'</option>';
        }
        resultElement.innerHTML=retval;
        $('select.dropdown.shifrkaf')
                .dropdown();
        ;

    }
    function formValidate() {
        var retVal=true;
        var login=$('#loginField').val();
        if (!login) return false;
        if (!_.isString(login))    return false;
        if (login.trim().length<3) return false;
        if (login.trim().length>16) return false;
        var emailfld=$('#emailField').val();
        if (!emailfld) return false;
        if (!_.isString(emailfld))    return false;
        if (emailfld.trim().length<3) return false;
        if (emailfld.trim().length>16) return false;
        var famfld=$('#famField').val();
        if (!famfld) return false;
        if (!_.isString(famfld))    return false;
        if (famfld.trim().length<1) return false;
        if (famfld.trim().length>32) return false;
        return retVal;
    }
    function setSubmitAjaxForRecForm() {
        formvaluessave = $('#recForm').serialize();

        $('#recForm').on('submit', function (event) {
                    event.preventDefault();
                    if( !($('.ui.form').form('is valid'))) {
                        return false;
                    }
                    if (!formValidate()) {
                        return false;
                    }
                    if (savingInProgress) {
                        return false;
                    }
                    savingInProgress = true
                    var str = $('#recForm').serialize();
                    // Get some values from elements on the page:
                    var dform = $(this),
                    url = dform.attr("action");
                    var posting = $.post(url, str);
                    posting.done(function (data) {
                        var content = data;
                        if (!$.isArray(data)) {
                            console.log = 'data is not array';
                        }
                        if (content[0].shifr == 'Ok') {
                            formvaluessave = str;
                            var urlRoot='<c:url value="/" />';
                            $('#resultModal').modal({
                                closable  : false,
                                transition : 'horizontal flip',
                                onApprove : function() {
//                                    alert(urlRoot);
                                    window.location.assign(urlRoot);
                                },
                                onDeny   : function() {window.location.assign(urlRoot);},
                                onHidden : function() {window.location.assign(urlRoot);}

                            })
                                    .modal('show')
//                        }
//                            generateNoty('information', notification_html[2]);
//                            window.location.reload();
                        } else
                        if (content.length > 0) {
                            var mes='<ol>'
                            for (i = 0; i < content.length; i++) {
                                mes = mes+'<li>'+content[i].name.trim() + '</li>';
                            mes = mes+'</ol>';
                            var resultElement=document.getElementById('modalcontent');

                            resultElement.innerHTML=mes;
                                $('#resultModal').modal({
                                     closable  : false,
                                     transition : 'horizontal flip',
                                     onApprove : function() {
                                         return true;
                                 //       window.alert('Approved!')
                                     }
                                     })
                                     .modal('show')
    //                            }                                ;

                            }
                        }
                        savingInProgress = false;

                    });
                    //                  jQuery('#recForm').submit();
                    savingInProgress = false;
                    return false;
                }
        )
    }
    function setChangeEventForLogin() {
        console.log('inside setChangeEventForLogin');
            $('#loginField').on('change', function (event) {
                console.log('inside Change Event for loginField');
                <c:url value="/util/chklogin/" var="uri2" />
                var uri1="${uri2}";
                var login=$('#loginField').val();
                console.log('inside Change Event for loginField. Tested login='+login);
                var uri=uri1+login;
                axios.get(uri)
                        .then(function (response) {
                            if (response.data[0].id==1) {
                                $.uiAlert({
                                    textHead: "Корректный логин", // header
                                    text: 'Указанный логин можно использовать!', // Text
                                    bgcolor: '#19c3aa', // background-color
                                    textcolor: '#fff', // color
                                    position: 'top-center',// position . top And bottom ||  left / center / right
                                    icon: 'checkmark box', // icon in semantic-UI
                                    time: 3, // time                                    time: 3 // time
                                })

<%--
                                $('#loginLabel').removeClass('red');
                                $('#loginLabel').addClass('green');
                                $('#loginIcon').removeClass('ban');
                                $('#loginIcon').addClass('check');
--%>
                            }
                            else {
                                  /// Danger center
                                    $.uiAlert({
                                        textHead: "Неверный логин", // header
                                        text: 'Указанный логин уже используется. Выберите другой логин', // Text
                                        bgcolor: '#DB2828', // background-color
                                        textcolor: '#fff', // color
                                        position: 'top-center',// position . top And bottom ||  left / center / right
                                        icon: 'remove circle', // icon in semantic-UI
                                        time: 3 // time
                                    })
<%--
                                $('#loginLabel').removeClass('red');
                                $('#loginLabel').addClass('green');
                                $('#loginIcon').removeClass('check');
                                $('#loginIcon').addClass('ban');
--%>
                            }
                        })
                        .catch(function (error) {
                    alert("Ошибка проверки поля login "+error());
                });
            });
    }
    var savingInProgress;
    var formvaluessave;
    savingInProgress=false;
    formvaluessave='';
</script>
</html>
