<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-chkmail-form">
    <div class="ui first coupled modal" v-show="show" id="modalchkmail">
        <i class="close icon"></i>
        <div class="header">

            <div class="ui three ordered top attached steps">
                <div class="step" v-bind:class="classObjectStep1" id="step1">
                    <div class="content">
                        <div class="title">Ввод email</div>
                        <div class="description">Укажите адрес электронной почты</div>
                    </div>
                </div>
                <div class="step" v-bind:class="classObjectStep2" id="step2">
                    <div class="content">
                        <div class="title">Получение PIN кода</div>
                        <div class="description">Получить по указанному электронному адресу сгенерированный PIN код</div>
                    </div>
                </div>
                <div class="step" v-bind:class="classObjectStep3" id="step3">
                    <div class="content">
                        <div class="title">Ввод PIN кода</div>
                        <div class="description">Ввести код, полученный по электронной почте</div>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui attached segment content" id="modalcontent">
            <form class="ui form" id="formchk" acceptCharset="UTF-8">
                <div class="three fields">
                    <div class="required field">
                        <label>email</label>
                        <input v-model="editingrec.email" type="email"  placeholder="Адрес электронной почты" />
                    </div>
                    <div class="field">
                         <div class="ui button canload" v-show="cansendpin" v-on:click="sendMail" id="sendButton">Получить код</div>
                    </div>
                    <div class="required field">
                        <label>Полученный PIN код</label>
                        <input v-model="editingrec.pin" type="number"  placeholder="Код активации, полученный на указанный Вами адрес электронной почты." min="10000" max="100000" />
                    </div>
                </div>
            </form>
        </div>
        <div class="actions">
            <div class="ui positive button" v-show="cansave">Продолжить</div>
            <div class="ui black deny button">Выход</div>
        </div>
    </div>
</template>
<script>
var chkmailform=Vue.extend({
    template: '#template-chkmail-form',
    props: {
        show: {
            type     : Boolean ,
            required : true    ,
            twoWay   : true
        }

    },

    data:function() {
        return {
            editingrec    : {}        ,
            savedrecord   : null      ,
            localShow     : this.show ,
            hasErrors     : false     ,
            errlist       : null      ,
            codesended    : false

        }
    },
    methods: {
        approved: function() {
            console.log("approved mail");
            this.hasErrors=false;
            var email=this.editingrec.email;
            console.log("approved mail this.editingrec.email="+this.editingrec.email);
            if (email && (email.length>3) && this.validateEmail()) {
                this.$emit('eshowmodal',true,email);
            } else {
                this.$emit('eshowmodal',false);
            }
            this.localShow = false;
        //    this.saveRec();
            return true;
        },
        deny: function() {
            this.localShow = false;
            this.$emit('eshowmodal',false);
            return true;
        },

        validateEmail:function () {
            if (!this.editingrec.email) return false;
            var mail=this.editingrec.email;
            var retVal=false;
            if (/^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,3})+$/.test(mail)) {
              retVal=true;
            }
            return retVal;
        },

        sendMail:function() {
            if (!this.validateEmail()) return;
<%--
            <c:url value="/util/mail/send" var="uri2" />
            var uri="${uri2}";
--%>
            var uri=this.$root.rootPath+"/util/mail/send";
//            console.log("sendMail uri="+uri);
            var mail=this.editingrec.email;
            var mailRec={
                email:mail
            };
            var objRec  = JSON.stringify(mailRec);
//            console.log("sendMail uri="+uri+" objRec="+objRec);
            var vm       = this;
            axios.post(uri, objRec,
                    {headers:{
                        'Content-Type': 'application/json'}}
            )
                    .then(function(response){
                        console.log("success sending post");
                        if (response.data) {
                            if ((response.data.length==1) && (response.data[0].name=='Ok')) {
                                vm.editingrec.generatedpin=parseInt(response.data[0].shifr);
                                console.log("pin="+vm.editingrec.generatedpin);
//                                console.log("send mail code="+response.data[0].shortname);
                                $("#sendButton").removeClass("loading");
                                vm.$notify("alert", "Пин код послан по указанному адресу. Проверьте электронный почтовый ящик");
                                vm.codesended=true;
                            } else {
                                if (response.data.length>0) {
                                    var z=$('#modalchkmail').css('z-index');
//                                    vm.hasErrors=true;
                                    var cont='<div class="ui error message"><ul>';
                                    for (var i=0; i<=response.data.length-1;i++) {
                                        var mes=response.data[i].name;
                                        vm.$nextTick(function () {
                                            vm.$notify("alert",mes,"error");
                                        });
                                        cont=cont+"<li>"+mes+"</li>";
//                                        vm.errlist.push({'message':response.data[i].name});
                                    }
                                    cont=cont+"</ul></div>"
                                    $('#secondmodalcontent').html(cont);
                                    $('#secondmodal').css('z-index',z+10);
                                    $('#secondmodal').modal('show');
                                    $('#secondmodal').modal('refresh');
                                }
                            }
                        }
                    })
                    .catch (function(error){
                      vm.$notify("alert", "Ошибка работы с почтой. "+error, "error");
                      $("#sendButton").removeClass("loading");

            });
        },
        fillEmptyRec:function() {
            this.editingrec={
                email            :  null,
                pin              :  0,
                generatedpin     :  0,
                codesended       : false
            }
        }
    },
    computed:{
        cansave:function() {
            var retVal=false;
            if (this.editingrec)
            if (this.editingrec.email)
            if (this.editingrec.generatedpin)
            if (this.editingrec.email.trim().length>3)
                if (this.editingrec.generatedpin>10000)
                    if (this.editingrec.generatedpin<100000)
                        if (this.editingrec.generatedpin==this.editingrec.pin) {
                           retVal=true;
                        }
            return retVal;
        },
        cansendpin:function() {
            var retVal=false;
            if (this.validateEmail()) {
                retVal=true;
            }
            return retVal;
        },
        classObjectStep1: function () {
            return {
                active: !this.cansendpin,
                completed: this.cansendpin
            }
        },
        classObjectStep2: function () {
            return {
                active: this.cansendpin && !this.codesended,
                completed: this.codesended
            }
        },
        classObjectStep3: function () {
            return {
                active: this.codesended
            }
        }
    },
    watch: {

        show:function(val) {
            this.localShow=val;
        },
        localShow: function(val) {
            if (this.control) {
                if (val) {
                    this.$nextTick(function () {
                                this.fillEmptyRec();
                                this.control.modal('show');
                            }
                    )
                }
            } else {
                alert('in show this.control is undefined');
            }
        }
    },

    mounted:function() {
        if (!this.control)
            this.$nextTick(function () {
                var vm=this;
                $('.coupled.modal')
                        .modal({
                            allowMultiple: true
                        });

                this.control = $('#modalchkmail').modal({
                    onApprove: function() {return vm.approved()}.bind(vm),
                    onDeny   : function() {return vm.deny()}.bind(vm),
                    onHidden : function() {this.localShow = false;vm.$emit('eshowmodal',false)}.bind(vm)
                });
                this.controltab=$('.menu .item');

            })
        this.$nextTick(function () {
              console.log("set loading");
              $("#sendButton").click(function(){
                      $("#sendButton").addClass("loading");
              });
        })
    }


});
</script>