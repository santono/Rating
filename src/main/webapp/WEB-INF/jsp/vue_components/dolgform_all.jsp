<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-dolg-form">
    <div class="ui first coupled modal" v-show="show" id="modaldolg">
        <i class="close icon"></i>
        <div class="header">
            Реквизиты записи.
        </div>
        <div class="content" id="modalcontent">
            <form class="ui form" id="formdolg" acceptCharset="UTF-8">
                <div class="two fields">
                    <div class="required field">
                        <label>Уник.код.</label>
                        <input v-model="editingrec.id" type="number"  placeholder="Уникальный код" min="1" max="999" />
                    </div>
                    <div class="field">
                        <label>Тип должности</label>
<%--
                        <input v-model="editingrec.kind" type="number"  placeholder="Тип должности" min="0" max="999" />
--%>
<%--
                        <select v-model.number="editingrec.kind" class="ui dropdown">
                            <option value="0">-- Не указано --</option>
                            <option value="1">ППС</option>
                            <option value="2">НР</option>
                            <option value="3">РП</option>
                            <option value="4">ИПТ</option>
                            <option value="5">АХП</option>
                            <option value="6">УВП</option>
                        </select>
--%>
                        <div class="ui selection dropdown">
<%--
                            <input name="kind" type="hidden" v-model.number="editingrec.kind">
--%>
                            <input name="kind" type="hidden">
                            <i class="dropdown icon"></i>
                            <div class="default text">-- Не указана --</div>
                            <div class="menu">
                                <div class="item" data-value="0">-- Не указана --</div>
                                <div class="item" data-value="1">ППС</div>
                                <div class="item" data-value="2">НР</div>
                                <div class="item" data-value="3">РП</div>
                                <div class="item" data-value="4">ИПТ</div>
                                <div class="item" data-value="5">АХП</div>
                                <div class="item" data-value="6">УВП</div>
                            </div>
                        </div>

                    </div>
                </div>

                <div class="required field">
                    <label>Название должности</label>
                    <input v-model="editingrec.name" type="text"  placeholder="Название должности"  maxlength="256"/>
                </div>
                <div class="required field">
                    <label>Сокращенное название должности</label>
                    <input v-model="editingrec.shortname" type="text"  placeholder="Краткое название должности"  maxlength="128"/>
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
    var dolgform=Vue.extend({
        template: '#template-dolg-form',
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
                if (this.action==3) {
                    this.deleteRec()
                } else {
                  this.saveRec();
                }
                return false;
            },
            approvedFinish:function() {
                this.localShow = false;
                if (this.action==1) {
                    this.$emit('eadddolg',this.editingrec);
                }
                else
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
                <c:url value="/util/dolg/save" var="uri2" />
                var uri="${uri2}";
--%>
                var uri = this.$root.rootPath+"/util/dolg/save";
                var kind=$('.ui.dropdown')
                        .dropdown('get value')
                ;
                kind=Number(kind);
//                console.log("kind="+kind+' rec.kind='+this.editingrec.kind);
                this.editingrec.kind=kind;
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
//                                    if (vm.action==1) {
//                                        vm.editingrec.id=parseInt(response.data[0].id);
//                                    }
                                    vm.approvedFinish();
                                    vm.$notify("alert", "Запись сохранена.");
                                } else {
                                    if (response.data.length>0) {
                                        var z=$('#modaldolg').css('z-index');
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
            deleteRec: function(){
<%--
                <c:url value="/util/dolg/delete" var="uri2" />
                var id=this.editingrec.id;
                var uri="${uri2}"+"/"+id;
--%>
                var id=this.editingrec.id;
                var uri=this.$root.rootPath+"/util/dolg/delete/"+id;
                var vm       = this;
                axios.get(uri, {
                })
                        .then(function(response){
                            if (response.data) {
                                if ((response.data.length==1) && (response.data[0].name=='Ok')) {
                                    vm.deletedFinish();
                                    vm.$notify("alert", "Запись удалена.");
                                } else {
                                    if (response.data.length>0) {
                                        var z=$('#modaldolg').css('z-index');
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
                            vm.$notify("alert", "Ошибка удаления записи. "+error, "error");
                });
            },
            fillEmptyRec:function() {
                this.editingrec={
                    id               :  0,
                    name             : "",
                    shortname        : "",
                    kind             : 0
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
                    var value=this.editingrec.kind;
                    if (value && (Number(value)>0)) {
                        $('.ui.dropdown')
                               .dropdown('set value',value)
                         ;
                    }
                    $('.ui.dropdown')
                            .dropdown({
                                action: 'activate'
                            })
                    ;
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

                    this.control = $('#modaldolg').modal({
                        onApprove: function() {return this.approved()}.bind(this),
                        onDeny   : function() {return this.deny()}.bind(this),
                        onHidden : function() {this.localShow = false;this.$emit('eshowmodal',false)}.bind(this)
                    });
                    this.controltab=$('.menu .item');

                })
        }


    });
</script>