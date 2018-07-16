<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-pokaz-form">
    <div class="ui first coupled modal" v-show="show" id="modalpokaz">
        <i class="close icon"></i>
        <div class="header">
            Реквизиты записи.
        </div>
        <div class="content" id="modalcontent">
            <form class="ui form" id="formpokaz" acceptCharset="UTF-8">
                <div class="three fields">
                    <div class="required field">
                       <label>Уник.код.</label>
                       <input v-model="editingrec.id" type="number"  placeholder="Уникальный код" min="1" max="999" />
                    </div>
                    <div class="required field">
                       <label>Номер строки</label>
                       <input v-model="editingrec.lineno" type="number"  placeholder="Номер строки в отчете" min="1" max="999" />
                    </div>
                    <div class="field">
                       <label>Входит в</label>
                       <input v-model="editingrec.idowner" type="number"  placeholder="Это показатель входит в другой с номером строки" min="0" max="999" />
                    </div>
                </div>

                <div class="required field">
			        <label>Название показателя</label>
                    <input v-model="editingrec.name" type="text"  placeholder="Название показателя"  maxlength="256"/>
                </div>
                <div class="required field">
			        <label>Сокращенное название показателя</label>
                    <input v-model="editingrec.shortname" type="text"  placeholder="Краткое название показателя"  maxlength="128"/>
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
    var pokazform=Vue.extend({
        template: '#template-pokaz-form',
        data:function() {
            return {
                   editingrec    : {}        ,
                   savedrecord   : null      ,
                   localShow     : this.show ,
                   hasErrors     : false     ,
                   errlist       : null      ,
                   action        : 0         

            }
        },
        props: {receivedrec:null,

                 show: {
                        type     : Boolean ,
                        required : true    ,
                        twoWay   : true
                 },
                 receivedaction : 0

        },
        methods: {
            approved: function() {
                this.hasErrors=false;
                this.saveRec();
                return false;
            },
            approvedFinish:function() {
                this.localShow = false;
                this.$emit('eshowmodal',false,this.editingrec);
                if (this.control) {
                    this.control.modal('hide');
                }
            },
            deletedFinish:function() {
                this.localShow = false;
                this.$emit('edelrec',this.editingrec);
                if (this.control) {
                    this.control.modal('hide');
                }
            },
            deny: function() {
                this.localShow = false;
                this.$emit('eshowmodal',false);
                return true;
            },
            saveRec:function() {
<%--
                <c:url value="/util/pokaz/save" var="uri2" />
                    var uri="${uri2}";
--%>
                    var uri=this.$root.rootPath+"/util/pokaz/save";
                    var savingRec=_.clone(this.editingrec);
//                    console.log(JSON.stringify(savingRec));

                    var objRec  = JSON.stringify(savingRec);
                    var vm       = this;
                    axios.post(uri, objRec,
                            {headers:{
                                'Content-Type': 'application/json'}}
                    )
                            .then(function(response){
                                if (response.data) {
                                    if ((response.data.length==1) && (response.data[0].name=='Ok')) {
                                        if (vm.action==1) {
                                            vm.editingrec.id=parseInt(response.data[0].id);
                                        }
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
                                vm.$notify("alert", "Ошибка сохранения записи. "+error, "error");
                    });
            },
            deleteRec: function(rec){
            },
            fillEmptyRec:function() {
                this.editingrec={
                     id               :  0,
                     lineno           :  0,
                     name             : "",
                     shortname        : "",
                     idowner          : null,
                }
            }
        },
        computed:{
            cansave:function() {
                var retVal=false;
                if ((this.action==1) || (this.action==2))
                   retVal = !_.isEqual(this.editingrec,this.savedrecord);
                return retVal;
            }
        },
        watch: {
            receivedaction:function(val){
                this.action=val;
            },
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

            show:function(val) {
                this.localShow=val;
            },
            localShow: function(val) {
               if (this.control) {
                if (val) {
                    this.$nextTick(function () {
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
                  var vc=this;
                  $('.coupled.modal')
                          .modal({
                              allowMultiple: true
                          });

                  this.control = $('#modalpokaz').modal({
                     onApprove: function() {return this.approved()}.bind(this),
                     onDeny   : function() {return this.deny()}.bind(this),
                     onHidden : function() {this.localShow = false;this.$emit('eshowmodal',false)}.bind(this)
                  });
                  this.controltab=$('.menu .item');

              })
        }


    });
</script>