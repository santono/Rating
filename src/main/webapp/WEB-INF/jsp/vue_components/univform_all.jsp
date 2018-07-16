<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-univ-form">
    <div class="ui first coupled modal" v-show="show" id="modaluniv">
        <i class="close icon"></i>
        <div class="header">
            Реквизиты записи.
        </div>
        <div class="content" id="modalcontent">
            <form class="ui form" id="formuniv">
                <div class="field" v-show="action>1">
                    <label>Код</label>
<%--
                    <input v-model="editingUniv.id"  name="id" placeholder="код" type="number" />
--%>
                    <p v-show="editingUniv">{{editingUniv.id}}</p>
                </div>
                <div class="field" v-show="editingUniv">
                    <label>Название</label>
                    <input v-if="action!=3" v-model="editingUniv.name"  name="name" placeholder="название" type="text" />
                    <p v-else> {{editingUniv.name}}</p>
                </div>
                <div class="field" v-show="editingUniv">
                    <label>Сокращенное название</label>
                    <input v-if ="action!=3" v-model="editingUniv.shortName"  name="shortname" placeholder="сокращенное название" type="text" />
                    <p v-else> {{editingUniv.shortName}}</p>
                </div>
                <div class="ui error message">
                    <ul v-show="hasErrors">
                        <li v-for="error in errlist">{{error.message}}</li>
                    </ul>
                </div>
            </form>
        </div>
        <div class="actions">
            <div class="ui positive button" v-show="cansave">Сохранить</div>
            <div class="ui yellow approve button" v-if="action==3">Удалить</div>
            <div class="ui black deny button">Выход</div>
        </div>
    </div>
</template>
<script>
    var univform=Vue.extend({
        template: '#template-univ-form',
        data:function() {
            return {
                   control     : null,
                   editingUniv : {},
                   localShow   : false,
                   hasErrors   : false,
                   errlist     : null,
                   action:0
            }
        },
        props: {univ:null,
                 show: {
                        type     : Boolean,
                        required : true,
                        twoWay   : true
                 },
                 receivedaction:0

        },
        methods: {
            approved: function() {
//                alert('approved');
                this.hasErrors=false;
                if (this.action==3) {
                   this.deleteUniv();
                } else {
                  this.saveUniv();
                }
                return false;
//                return true;
            },
            approvedFinish:function() {
//                alert('approvedFinish');
                this.localShow = false;
                this.$emit('eshowmodal',false,this.editingUniv);
//                this.localShow=val;
//                alert('this control 00');
                if (this.action==1) {
                    this.$emit('eaddpodr',this.editingUniv);
                }
                if (this.control) {
//                    alert('this control');
                    this.control.modal('hide');
                }
            },
            deletedFinish:function() {
//                alert('deletedFinish');
                this.localShow = false;
                this.$emit('edelpodr',this.editingUniv);
                if (this.control) {
//                    alert('this control');
                    this.control.modal('hide');
                }
            },
            deny: function() {
                this.localShow = false;
                this.$emit('eshowmodal',false);
                return true;
            },
            saveUniv:function() {
<%--
                <c:url value="/util/univ/save" var="uri2" />
                var uri="${uri2}";
--%>
                var uri=this.$root.rootPath+"/util/univ/save";
//                var uri="/r/util/univ/save";
                var savingUniv=_.clone(this.editingUniv);
//                    console.log('saveUniv:this.editingUniv='+JSON.stringify(this.editingUniv));


//                savingUniv.shifrIdOwner=savingUniv.owner;
                delete savingUniv.canbedeleted;
                delete savingUniv.owner;
                delete savingUniv.shortname;
                delete savingUniv.level;

                var objUniv  = JSON.stringify(savingUniv);
//                alert(objUniv);
                var vm       = this;
                var finished = false;
                var retVal   = false;
//                console.log('saveuniv:objUniv='+JSON.stringify(objUniv));
                axios.post(uri, objUniv,
                        {headers:{
                    'Content-Type': 'application/json'}}
                )
                        .then(function(response){
                            if (response.data) {
                               if ((response.data.length==1) && (response.data[0].name=='Ok')) {
//                                  alert('response OK');
                                  if (vm.action==1) {
                                      vm.editingUniv.id=parseInt(response.data[0].id);
                                  }
                                  vm.approvedFinish();
                                  vm.$notify("alert", "Запись сохранена.");
                                  retVal=true;
                               } else {
                                 if (response.data.length>0) {
                                     var z=$('#modaluniv').css('z-index');
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
                            finished=true;
                        })
                        .catch (function(error){
//                             alert('error='+error);
                             vm.$notify("alert", "Ошибка сохранения записи. "+error, "error");
                               finished=true;
                         });
                return retVal;

            },
            deleteUniv: function(univ){
<%--
                <c:url value="/util/univ/del" var="uri2" />
                var uri="${uri2}"+"/"+this.editingUniv.id;
--%>
                var uri = this.$root.rootPath+"/util/univ/del/"+this.editingUniv.id;
                var deletingUniv=this.editingUniv;

                var vm=this;
                axios.post(uri, {})
                        .then(function(response){
                            if (response.data) {
                                if ((response.data.length==1) && (response.data[0].name=='Ok')) {
//                                  alert('response OK');
                                    vm.deletedFinish();
                                    vm.$nextTick(function () {
                                        vm.$notify("alert", "Запись удалена.");
                                    });
//                                    vm.$notify("alert", "Запись удалена.");
                                } else {
                                    if (response.data.length>0) {
                                        var z=$('#modaluniv').css('z-index');
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
//                             alert('error='+error);
                               vm.$notify("alert", "Ошибка удаления записи. "+error, "error");
                         });
                return retVal;
            },
            setValidateForm:function() {
                $('#formuniv')
                        .form({
                            //   inline : true,
                            on     : 'blur',

                            fields: {
                                name: {
                                    identifier  : 'name',
                                    rules: [
                                        {
                                            type   : 'empty',
                                            prompt : 'Пожалуйста, введите название подразделения'
                                        },
                                        {
                                            type   : 'maxLength[96]',
                                            prompt : 'Название должно содержать не более 96 символов'
                                        },
                                        {
                                            type   : 'minLength[1]',
                                            prompt : 'Название должно содержать не менее 1 символа'
                                        }
                                    ]
                                },
                                shortname: {
                                    identifier  : 'shortname',
                                    rules: [
                                        {
                                            type   : 'empty',
                                            prompt : 'Пожалуйста, введите сокращенное название'
                                        },
                                        {
                                            type   : 'maxLength[16]',
                                            prompt : 'Краткое название должно содержать не более 16 символов'
                                        },
                                        {
                                            type   : 'minLength[1]',
                                            prompt : 'Краткое название должно содержать не менее 1 символа'
                                        }
                                    ]
                                }
                            }
                        })
                ;

            }
        },
        computed:{
            cansave:function() {
                return !_.isEqual(this.editingUniv,this.univ);
            }
        },
        watch: {

            receivedaction:function(val){
                this.action=val;
                console.log('ntrform: receivedaction action='+this.action);
            },
            univ:function(val) {
                if (val) {
                    var vm=this;
                    this.$nextTick(function(){
                         vm.editingUniv=_.clone(val);
                         console.log('ntrform: watch univ vm.editingUniv='+JSON.stringify(vm.editingUniv));
                         if (vm.action==0)  {
                            vm.action=vm.editingUniv.id<1?1:2;
//                            if (vm.action==1) {
//                                vm.editingUniv.shifrIdOwner=
//                            }
                         }
                    });
                }

            },

            show:function(val) {
//                alert('watch show '+val);
                this.localShow=val;
            },
            localShow: function(val) {
                var vm=this;
                if (this.control) {
                if (val) {
                    this.$nextTick(function () {
                        var l=$('#modaluniv').length;
                        console.log('formuniv:show amnt of control='+l);
//                        $('#modaluniv').modal('setting', 'transition', 'Vertical Flip');
                      vm.control.modal('setting', 'transition', 'vertical flip').modal('show');
//                      vm.control.modal('setting', 'transition', 'Vertical Flip')
//                        $('#modaluniv').modal('show');
                      vm.setValidateForm();
                    })
                }
               } else {
                  alert('in show this.control is undefined');
               }
            }
        },

        mounted:function() {
            console.log('univform: mounted');
            if (!this.control)
                console.log('univform: mounted inside !this.control');
                var vm=this;
                this.$nextTick(function () {
                  $('.coupled.modal')
                          .modal({
                              allowMultiple: true
                          });

                  vm.control = $('#modaluniv').modal({
                     onApprove: function() {return vm.approved()}.bind(vm),
                     onDeny   : function() {return vm.deny()}.bind(vm),
                     onHidden : function() {vm.localShow = false;vm.$emit('eshowmodal',false)}.bind(vm)
                  });
              })
        },
        created:function() {
            console.log("univform created");
        }

    });
</script>