<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-chgpwd-form">
    <div class="ui middle aligned center aligned grid">
        <div class="row"></div>
        <div class="row"></div>
        <div class="row"></div>
        <div class="four wide column"></div>
        <div class="eight wide column">
            <h2 class="ui teal image header">
                <!--   <img src="assets/images/logo.png" class="image"> -->
                <div class="content">
                    Изменение пароля пользователя
                </div>
            </h2>

            <form class="ui large form" acceptCharset="UTF-8" id="formchgpwd">
                <div class="ui stacked segment"  style="background-color: #DADADA">
                    <div class="required field">
                        <label>Новый пароль </label>
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <input type="password" name="upwd" placeholder="Новый пароль" v-model="editingrec.password" maxlength="16" />
                        </div>
                    </div>
                    <div class="required field">
                        <label>Повторный ввод</label>
                        <div class="ui left icon input">
                            <i class="lock icon"></i>
                            <input type="password" name="upwd1" placeholder="Повторный ввод" v-model="editingrec.secondpassword" maxlength="16">
                        </div>
                    </div>
                    <div class="ui container">
                         &nbsp;
                         <div class="ui deny right floated button" v-on:click="deny">Выход</div>
                         <div class="ui positive right floated button" v-on:click="approved" v-show="cansave">Сохранить</div>
                    </div>
                </div>
            </form>
            <div class="ui message" v-if="hasErrors">{{message}}/></div>
            <div class="ui error message"></div>
        </div>
        <div class="four wide column"></div>
    </div>
</template>
<script>
var chgpwdform=Vue.extend({
    template: '#template-chgpwd-form',
    data:function() {
        return {
            userid        : 0         ,
            editingrec    : {}        ,
            hasErrors     : false     ,
            errlist       : null      ,
            action        : 0         ,
            message       : null

        }
    },
    methods: {
        changepwd: function() {
            this.hasErrors=false;
            this.savePwd();
            return false;
        },
        changepwdFinish:function() {
            this.localShow = false;
            this.$emit('eshowmodal',false,this.editingrec);
            if (this.control) {
                this.control.modal('hide');
            }
        },
        deny: function() {
//            console.log('chfpwd:inside deny');
//                this.$emit('eshowmodal',false);
            this.hasErrors = false;
            this.message   = null;
            $("#startcard").show();
            this.$router.back();
            return true;
        },
        approved: function() {
            if (!($('#formchgpwd').form('is valid'))) {
                return false;
            }
            this.hasErrors=false;
            this.savePwd();
            return false;
        },
        approvedFinish: function() {
//            alert('approvedFinish');
            this.hasErrors=false;
            this.message=null;
            $("#startcard").show();
            this.$router.back();
            return true;
        },

        savePwd:function() {
<%--
            <c:url value     = "/util/pwd/save" var="uri2" />
            var uri      = "${uri2}"//+"/"+this.userid;
--%>
            var uri = this.$root.rootPath+"/util/pwd/save";
            var savingRec= _.clone(this.editingrec);
            var objRec   = {"iduser":this.userid,"pwd":this.editingrec.password.trim()};
            var vm       = this;
            axios.post(uri, objRec,
                    {headers:{
                        'Content-Type': 'application/json'}}
            )
                    .then(function(response){
                        if (response.data) {
                            if ((response.data.length==1) && (response.data[0].name=='Ok')) {
                                vm.approvedFinish();
                                vm.$notify("alert", "Запись сохранена.");
                            } else {
                                if (response.data.length>0) {
                                    var z=$('#modalpokaz').css('z-index');
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
                alert("Ошибка сохранения нового пароля. "+error);
                vm.$notify("alert", "Ошибка сохранения нового пароля. "+error, "error");
            });
        },
        fillEmptyRec:function() {
            this.hasErrors = false;
            this.message   = null;

            this.editingrec={
                password         :  "" ,
                secondpassword   :  ""
            }
        }
    },
    computed:{
        cansave:function() {
            var retVal=false;
            if (this.editingrec)
                if (this.editingrec.password)
                    if (this.editingrec.secondpassword)
                        if (this.editingrec.password.trim().length>0)
                            if (this.editingrec.secondpassword.trim().length>0)
                                if (this.editingrec.password.trim().length<17)
                                    if (this.editingrec.secondpassword.trim().length<17)
                                        if (this.editingrec.password===this.editingrec.secondpassword)
                                            retVal = true;
            return retVal;
        }
    },

    mounted:function() {

        this.$nextTick(function () {
            $('.ui.sidebar').sidebar('toggle');
            $("#startcard").hide();
            $('.ui.form')
                    .form({
                        on     : 'blur',
                        fields: {
                            name: {
                                identifier  : 'upwd',
                                rules: [
                                    {
                                        type   : 'empty',
                                        prompt : 'Пожалуйста, введите пароль'
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
                            },
                            password: {
                                identifier  : 'upwd1',
                                rules: [
                                    {
                                        type   : 'empty',
                                        prompt : 'Пожалуйста, введите пароль повторно'
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


        });
        <%--
                    console.log('chgpwdform: mounted');
                    var vm=this;
                    if (!this.control)
                      this.$nextTick(function () {
                          $('.coupled.modal')
                                  .modal({
                                      allowMultiple: true
                                  });
                          vm.control = $('#modalchgpwd').modal({
                             onApprove: function() {return vm.approved()}.bind(vm),
                             onDeny   : function() {return vm.deny()}.bind(vm),
                             onHidden : function() {vm.localShow = false;vm.$emit('eshowmodal',false)}.bind(vm)
                          });
                          vm.control.modal('show');
        //                  this.controltab=$('.menu .item');

                      })
                    this.fillEmptyRec();
        --%>

    },
    created: function() {
        console.log('chgpwdform: created');
        this.userid   = this.$route.params.id;
        console.log('chgpwdform: created. id='+this.$route.params.id);
        var vm=this;
        console.log('chgpwdform: created. inside not this.control');
        this.$nextTick(function () {
           $('.coupled.modal')
              .modal({
                    allowMultiple: true
             });
        });
        console.log('chgpwdform: before. fillEmptyRec.');
        this.fillEmptyRec();
    }
});
</script>