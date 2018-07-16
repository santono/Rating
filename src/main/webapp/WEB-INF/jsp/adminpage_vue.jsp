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
<%--
    <spring:url value="/resources/js/vue/vue.min.js" var="vue_js_url"/>
--%>
    <spring:url value="/resources/js/vue/vue.js" var="vue_js_url"/>
    <spring:url value="/resources/js/vue/axios.min.js" var="axios_js_url"/>
    <spring:url value="/resources/css/semanticui/semantic.min.css" var="semantic_css_url"/>
    <spring:url value="/resources/js/vue/vue-router.min.js" var="vue_router_js_url"/>
    <spring:url value="/resources/js/photoswipe.min.js" var="photoswipe_js_url"/>
    <spring:url value="/resources/css/photoswipe.css" var="photoswipe_css_url"/>
    <spring:url value="/resources/css/default-skin.css" var="default-skin_css_url"/>
    <!-- Site Properties -->
    <title>Учет научных работ НПР</title>
<%--
    <script src="https://unpkg.com/vue-router/dist/vue-router.js"></script>
--%>

    <link rel="stylesheet" type="text/css" href="${semantic_css_url}" />
    <link rel="stylesheet" type="text/css" href="${photoswipe_css_url}" />
    <link rel="stylesheet" type="text/css" href="${default-skin_css_url}" />
    <script src="${jquery_js_url}"></script>
    <script src="${semantic_js_url}"></script>
    <script src="${photoswipe_js_url}"></script>
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
    </style>
    <style>
        .vmodal-mask {
            position: fixed;
            z-index: 9998;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, .5);
            display: table;
            transition: opacity .3s ease;
        }

        .vmodal-wrapper {
            display: table-cell;
            vertical-align: middle;
        }

        .vmodal-container {
            width: 80%;
            margin: 0px auto;
            padding: 20px 30px;
            background-color: #fff;
            border-radius: 2px;
            box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
            transition: all .3s ease;
            font-family: Helvetica, Arial, sans-serif;
        }

        .vmodal-header h3 {
            margin-top: 0;
            color: #42b983;
        }

        .vmodal-body {
            margin: 20px 0;
        }

        .vmodal-default-button {
            float: right;
        }

            /*
            * The following styles are auto-applied to elements with
            * transition="modal" when their visibility is toggled
            * by Vue.js.
            *
            * You can easily play with the modal transition by editing
            * these styles.
            */

        .vmodal-enter {
            opacity: 0;
        }

        .vmodal-leave-active {
            opacity: 0;
        }

        .vmodal-enter .vmodal-container,
        .vmodal-leave-active .vmodal-container {
            -webkit-transform: scale(1.1);
            transform: scale(1.1);
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
                $('body').removeClass( "pushable" );
                $('body.pushable').css( "background-color","#FFFFFF" );
            });
        });
        //https://api.mlab.com/api/1/databases/rating/collections/users?apiKey=kaUDFzJwz5GfBtAeUnriufsAYkJLyfLf
        //mlab.com santono ar1737
        //https://api.mlab.com/api/1/databases/rating/collections/predps?q={"id": "100000"}&apiKey=kaUDFzJwz5GfBtAeUnriufsAYkJLyfLf
    </script>

</head>

<body style="background-color: #FFFFFF">
<div id="app">
<div id="sbar" class="ui inverted labeled icon left inline vertical sidebar menu" style="">
    <router-link to="/univ" class="item"><i class="block layout icon"></i>Подразделения </router-link>
    <a class="item">
        <i class="block layout icon"></i>
        ППС
    </a>
    <a class="item">
        <i class="smile icon"></i>
        Своды
    </a>
    <a class="item">
        <i class="calendar icon"></i>
        Ученые степени
    </a>
    <a class="item">
        <i class="calendar icon"></i>
        Ученые звания
    </a>
</div>
<div class="ui top fixed inverted menu">
    <div class="ui container">
        <div id="show-sidebar" class="button toggler">
            <a class="header item">
                <i class="sidebar icon"></i>
                Меню
            </a>
        </div>
        <a href="#" class="item">Home</a>
        <div class="header item center">
            {{ message }}
        </div>
        <div class="right menu">
            <a class="header item">Выход</a>
        </div>

    </div>
</div>


<!-- 
  <div class="ui bottom attached segment pushable">
-->
<div class="main text container pusher">
    <div class="ui basic segment">
        <router-view></router-view>
        <h3 class="ui header">Application Content</h3>
        <p>Строка 1</p>
        <p>Строка 2</p>
        <p>Строка 3</p>
        <p>Строка 4</p>
    </div>
</div>
</div>

<template id="template-univ-row">
    <tr>
        <td>
            {{univ.id}}
        </td>
        <td>
            <span>{{univ.name}}</span>
        </td>
        <td>
            <span>{{univ.shortName}}</span>
        </td>
        <td>
            <div class="ui icon buttons">
                <button v-on:click="edituniv" class="ui button"><i class="edit icon"></i></button>
                <button class="ui button"><i class="remove icon"></i></button>
            </div>
        </td>
    </tr>
</template>

<template id="template-univ-form">
<transition name="vmodal">
    <div class="vmodal-mask">
        <div class="vmodal-wrapper">
            <div class="vmodal-container">
                <div class="vmodal-header">
                    <slot name="header">
                        Реквизиты записи
                    </slot>
                </div>

                <div class="vmodal-body">
                    <slot name="body">
                        default body
                    </slot>
                </div>

                <div class="vmodal-footer">
                    <slot name="footer">
                        default footer
                        <button class="vmodal-default-button" @click="$emit('close')">
                        OK
                        </button>
                    </slot>
                </div>
            </div>
        </div>
    </div>
</transition>
</template>
<%--
<template id="template-univ-form">
    <div class="ui modal" v-show="univ" id="modaluniv">
        <i class="close icon"></i>
        <div class="header">
            Реквизиты записи.
        </div>
        <div class="content" id="modalcontent">
            <form class="ui form" >
                <div class="field">
                    <label>Код</label>
                    <input v-model="univ.id"  name="id" placeholder="код" type="text" />
                </div>
                <div class="field">
                    <label>Название</label>
                    <input v-model="univ.name"  name="name" placeholder="название" type="text" />
                </div>
                <div class="field">
                    <label>Сокращенное название</label>
                    <input v-model="univ.shortName"  name="shortname" placeholder="сокращенное название" type="text" />
                </div>
            </form>
        </div>
        <div class="actions">
            <div class="ui button ok">Сохранить</div>
        </div>
    </div>
</template>
--%>
<template id="template-univ-table">
    <div>

    <table class="ui selectable celled striped table">
        <thead>
        <tr>
            <th>#</th>
            <th>Название</th>
            <th>Краткое название</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <tr is="univrow" v-for="univ in universities" :univ="univ" v-on:editu="editrec" >
        </tr>
        </tbody>
    </table>

    <univform v-bind:univ='selecteduniv' v-if="showModal" v-on:close="showModal = false" >
        <h3 slot="header">Реквизиты записи</h3>
        <form slot="body" class="ui form" >
            <div class="field">
                <label>Код</label>
                <input v-model="univ.id"  name="id" placeholder="код" type="text" />
            </div>
            <div class="field">
                <label>Название</label>
                <input v-model="univ.name"  name="name" placeholder="название" type="text" />
            </div>
            <div class="field">
                <label>Сокращенное название</label>
                <input v-model="univ.shortName"  name="shortname" placeholder="сокращенное название" type="text" />
            </div>
        </form>

    </univform>
    </div>
</template>

<%--
  </div>
--%>
<%--
------------
<body>
    <div id="app">
        Message : {{ message }} <BR>
        Length : {{ jsonSettings.length }} <BR>
        <ul>
            <li v-for="setting in jsonSettings">
                {{ setting.resume }}
            </li>
        </ul>
        <button v-on:click="getData()">Load data</button>
        <button v-on:click="printData()">Print data</button>
    </div>
</body>

data: {
        message: 'Hello Vue!',
        jsonSettings: []
    },
--------
--%>

<script src="${vue_js_url}"></script>
<script src="${vue_router_js_url}"></script>
<script src="${axios_js_url}"></script>

<script>
    //        Vue.component('univrow',{
    var univform=Vue.extend({
        template: '#template-univ-form',
        props: ['univ'],
        methods: {
//            showModal: function() {
//                     $('ui.modal').modal('show');
//            },
            deleteUniv: function(univ){
                vm.universities.$remove(univ);
            }
        }
    <%--
        ,
        mounted:function() {
            alert('in form mounted amnt of modal='+$('#modaluniv').length);
            alert('in form mounted amnt of modal1='+$('.ui.modal').length);

        },
        created:function() {
            alert('in form created amnt of modal='+$('#modaluniv').length);
            alert('in form created amnt of modal1='+$('.ui.modal').length);

        },
        ready:function() {
            alert('in form ready amnt of modal='+$('#modaluniv').length);
            alert('in form ready amnt of modal1='+$('.ui.modal').length);

        }
     --%>
    });
    //        Vue.component('univrow',{
    var univrow=Vue.extend({
        template: '#template-univ-row',
        props: ['univ'],
        methods: {
            edituniv:function() {
                 this.$emit('editu',this.univ);
            },

            deleteUniv: function(univ){
                vm.universities.$remove(univ);
            }
        }
    });

 //     Vue.component('univtable',{
    var univtable=Vue.extend({
        template: '#template-univ-table',
        //   props: ['univ'],
        data:function() {
                    return {universities: null,
                            univ:'',
                            selecteduniv:'',
                            showModal:false,
                            modalDom:null};
//            return {'universities':[
//                {'id':1,'name':'университет 1','shortname':'у 1'},
//                {'id':2,'name':'университет 2','shortname':'у 2'},
//                {'id':3,'name':'университет 3','shortname':'у 3'},
//                {'id':4,'name':'университет 4','shortname':'у 4'},
//                {'id':5,'name':'университет 5','shortname':'у 5'},
//                {'id':6,'name':'университет 6','shortname':'у 6'},
//                {'id':7,'name':'университет 7','shortname':'у 7'},
//                {'id':8,'name':'университет 8','shortname':'у 8'},
//                {'id':9,'name':'университет 9','shortname':'у 9'},
//                {'id':10,'name':'университет 10','shortname':'у 10'}
//                    ]};
        },
        components: {
            'univrow':univrow,
            'univform':univform
        },

        methods: {
            editrec:function(curruniv) {
//                alert('curruniv '+curruniv.name);
                this.selecteduniv=curruniv;
//                $('.ui.modal').modal('show');
                this.showModal=true;
         //       this.openModal();
            },
            deleteUniv: function(univ){
                vm.universities.$remove(univ)
            },
            getUnivList:function() {
                <c:url value="/util/univs" var="uri2" />
                var uri="${uri2}";
                var vt=this;
                var finished=false;
                axios.get(uri, {

                })
                        .then(function (response) {
                            finished=true;
                            vt.universities=response.data;
                        })

                        .catch(function (error) {
                    finished=true;
                });
                var i;
                while (finished) {
                    i=1;
                };

            },
            openModal: function() {
                if (!this.modalDom) {
                    alert('this.modalDom is null');
                } else
                this.modalDom.modal('show');
            },
            closeModal: function() {
                this.modalDom.modal('hide');
            }
        },
//        beforeRouteEnter:function(to, from, next) {
//           getPost(to.params.id, function(err, post) {
//               next(vm => vm.setData(err, post))
//           })
//                ,
// если путь изменяется, а компонент уже отображён,
// логика будет немного иной

        created: function() {
           //     console.log('Created called me!')
                this.getUnivList();
//                this.$http({url: '/api/stories', method: 'GET'}).then(function (response) {
//                    this.$set('stories', response.data)
//Or we as we did before
//vm.stories = response.data
//                })

            }
<%--
        ,
        mounted: function () {
            alert('amnt of modal='+$('#modaluniv').length);
            alert('amnt of modal1='+$('.ui.modal').length);
            this.modalDom = $('#modaluniv').modal({closable: false });
        },
        ready: function () {
              alert('amnt in ready of modal='+$('#modaluniv').length);
                alert('amnt in ready of modal1='+$('.ui.modal').length);
                this.modalDom = $('#modaluniv').modal({closable: false });
        }
  --%>
    });

  //  const Univ = { template: '<div>Univ</div>' }
    const Fac  = { template: '<div>Fac</div>' }
    const Kaf  = { template: '<div>Kaf</div>' }
    const routes = [
        { path: '/univ', component: univtable },
        { path: '/fac', component: Fac },
        { path: '/kaf', component: Kaf }
    ];
    const router = new VueRouter({
        'routes':routes // short for `routes: routes`
    })
    var user={};
    function performGetRequestForUser() {
//        var url='https://api.mlab.com/api/1/databases/rating/collections/users';
        <c:url value="/util/user" var="uri2" />
        var uri="${uri2}";
     //   var vm=this;

        axios.get(uri, {

        })
                .then(function (response) {
                    user.name=response.data.name;
                    if (app) {
                        app.message=user.name;
                    }
                })
                .catch(function (error) {
            user.name=error.message;
            if (app) {
                app.message=user.name;
            }
        });
    }


    const app = new Vue({
        'router':router,
      //  el: '#app',
        data: {
            message: user.name,
            showModal: false
        },
        components: {
           'univtable':univtable
        },
        beforeCreate:function () {
            performGetRequestForUser();
        },

        mounted: function() {
            $('.ui.sidebar')
                    .sidebar({
                        // this has to be set
                        context: '#app'
                    })
//                    .sidebar('toggle')
            ;
        },
        methods: {
            getData : function () {
                axios.post('https://httpbin.org/post', [
                    { id: 0, resume: 'test 0'},
                    { id: 1, resume: 'test 1'},
                    { id: 2, resume: 'test 2'}
                ])
                        .then(function(response) {
                this.jsonSettings = response.data.json;
                this.message = "getData - I get objects : " + this.jsonSettings.length + " lines ";
            })
            .catch(function(error) { this.message = "getData - I get null"});
    },

    printData: function () {
        this.message =  "printData - jsonSettings.length  = [" + this.jsonSettings.length + "]";

     //   this.jsonSettings.map((item, i) => console.log('Index:', i, 'Id:', item.resume));

    }
    }

    }).$mount('#app')
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
                <!-- element will get class pswp__preloader--active when preloader is running -->
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
