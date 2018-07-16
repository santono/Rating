<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-chgpwd-d-form">
    <div class="ui first coupled modal" v-show="localShow" id="modalchgpwd">
        <i class="close icon"></i>
        <div class="header">
            Смена пароля.
        </div>
        <div class="content" id="modalcontent">
            <form class="ui form" id="formchgpwd" acceptCharset="UTF-8">
                <div class="required field">
                    <label>Новый пароль </label>
                    <input v-model="editingrec.password" type="password"  placeholder="Новый пароль" maxlength="16" />
                </div>
                <div class="required field">
                    <label>Повторный ввод</label>
                    <input v-model="editingrec.secondpassword" type="password"  placeholder="Повторный ввод" maxlength="16" />
                </div>
            </form>
        </div>
        <div class="actions">
            <div class="ui positive button" v-show="cansave">Сохранить пароль</div>
            <div class="ui black deny button">Выход</div>
        </div>
    </div>
</template>
<script>
    var chgpwdform_d=Vue.extend({
        template: '#template-chgpwd-d-form',
        data:function() {
            return {
				   userid        : 0         , 
                   editingrec    : {}        ,
                   savedrecord   : null      ,
                   localShow     : false     ,
                   hasErrors     : false     ,
                   errlist       : null      ,
                   action        : 0         ,
                   control       : null				   

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
                console.log('chfpwd:inside deny');
                console.log('chfpwd:route.name='+this.$route.name);
                this.localShow = false;
//                this.$emit('eshowmodal',false);
                this.$router.back();
                return true;
            },
            approved: function() {
                this.hasErrors=false;
                this.savePwd();
                return false;
            },
            approvedFinish: function() {
                alert('approvedFinish');
                this.hasErrors=false;
                $("#startcard").show();
                this.localShow = false;
//                this.$emit('eshowmodal',false);
                this.$router.back();
                return true;
            },

            savePwd:function() {
<%--
                <c:url value     = "/util/pwd/save" var="uri2" />
                    var uri      = "${uri2}"//+"/"+this.userid;
--%>
                    var uri = this.$root.rootPath + "/util/pwd/save";
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
        watch: {
            receivedrec:function(val) {
//                alert(JSON.stringify(val));

                if (val) {
                    if (this.action==0)
                       this.action=this.val.id<1?1:2;
                    if (this.action==1) {
                        this.fillEmptyRec();
                    } else {
                        this.editingrec=_.clone(val);
                    }
                    this.savedrecord=_.clone(this.editingrec);
                }

            },
<%--
            show:function(val) {
                this.localShow=val;
            },
--%>
            localShow: function(val) {
               console.log('chgpwd inside watch localShow='+val);
               if (this.control) {
                if (val) {
                    var vm=this;
                    this.$nextTick(function () {
                           vm.control.modal('show');
                        }
                    )
                }
               } else {
                  alert('in show this.control is undefined');
               }
            }
        },

        mounted:function() {

            this.$nextTick(function () {
                $('.ui.sidebar').sidebar('toggle');
                $("#startcard").hide();
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
            if (!this.control) {
                console.log('chgpwdform: created. inside not this.control');
              this.$nextTick(function () {
                  $('.coupled.modal')
                          .modal({
                              allowMultiple: true
                          });
                  console.log('chgpwd amnt of modal='+$('#modalchgpwd').length);
                  vm.control = $('#modalchgpwd').modal({
                     onApprove: function() {return vm.approved()}.bind(vm),
                     onDeny   : function() {return vm.deny()}.bind(vm),
                     onHidden : function() {vm.localShow = false;vm.$emit('eshowmodal',false)}.bind(vm)
                  });
                  vm.localShow=true;

//                  this.controltab=$('.menu .item');

              });
            }
            console.log('chgpwdform: before. fillEmptyRec.');
			this.fillEmptyRec();
        }
    });
</script>