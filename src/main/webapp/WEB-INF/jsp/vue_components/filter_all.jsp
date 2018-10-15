<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-filter-form">
    <div class="ui large modal" id="filtermodal">
        <div class="ui header">
            Фильтр научных работ
        </div>
        <div class="content">
            <div class="ui form" id="filterform">
                <div class="inline fields">
                    <label v-show="needYear">C</label>
                    <div class="field" v-show="needYear">
                        <input v-model="yearFr" placeholder="Начальный год диапазона" type="number" step="1" max="2018" min="1960">
                    </div>
                    <label v-show="needYear">по</label>
                    <div class="field" v-show="needYear">
                        <input v-model="yearTo" placeholder="Конечный год диапазона" type="number" step="1" max="2018" min="1960">
                    </div>
                    <div class="field">
                        <div class="ui checkbox" id="cbNeedYear">
                            <input v-model="needYear" class="hidden" type="checkbox">
                            <label>Установить диапазон дат</label>
                        </div>
                    </div>
                </div>

                <div v-show="isadmin" class="inline fields">
                    <%--
                                            <div v-show="needNPR" class="ui search selection dropdown twelve wide field" id="authorddb">
                    --%>
                    <div v-show="needNPR" class="ui search selection dropdown field " id="authorddb">
                        <input v-on:blur="addAuthor(author)"
                               v-on:keyup.enter="addAuthor(author)"
                               name="author" type="hidden" v-model="shifrNpr">
                        <i class="dropdown icon"></i>
                        <div class="default text">Автор</div>
                        <input type="text" class="search">
                    </div>
                    <%--
                                            <div class="four wide field" >
                    --%>
                    <div class="field" >
                        <div class="ui checkbox" id="cbNeedNPR">
                            <input v-model="needNPR" class="hidden" type="checkbox">
                            <label>Выбрать автора</label>
                        </div>
                    </div>
                </div>

                <div v-if="isadmin" class="inline fields">
                    <%--
                                            <div class="field" v-show="needPredp">
                    --%>
                    <div class="field">
                        <div>
                            <podrselector ref='podrslctr' v-bind:podrid  = "shifrPredp"
                                          v-on:eselpodr  = "ePodrSelected"
                                          v-on:enamepodr = "eNamePodr"></podrselector>

                            <%--
                                                    </div>
                                                    <div class="ui message">
                            --%>
                            <p v-cloak>{{namePodr}}</p>
                        </div>
                    </div>
                    <div class="field" >
                        <div class="ui checkbox" id="cbNeedPredp">
                            <input v-model="needPredp" class="hidden" type="checkbox">
                            <label>Выбрать подразделение</label>
                        </div>
                    </div>
                </div>
                <div class="inline fields">
                    <label v-show="needDet">Вид работы</label>
                    <div class="ui dropdown field" id="ddDet" v-show="needDet">
                        <input name="shifrDet" type="hidden" v-model="shifrDet">
                        <i class="dropdown icon"></i>
                        <div class="default text">Не выбран вид работы</div>
                        <div class="menu">
                            <div class="item" data-value="2">Научные статьи</div>
                            <div class="item" data-value="7">Публикации РИНЦ</div>
                            <div class="item" data-value="12">Публикации Web of Science</div>
                            <div class="item" data-value="14">Публикации SCOPUS</div>
                            <div class="item" data-value="37">Учебники и учебные пособия</div>
                            <div class="item" data-value="42">Патенты России</div>
                            <div class="item" data-value="65">Докторские диссертации</div>
                            <div class="item" data-value="66">Кандидатские диссертации</div>
                        </div>
                    </div>
                    <div class="field">
                        <div class="ui checkbox" id="cbNeedDet">
                            <input v-model="needDet" class="hidden" type="checkbox">
                            <label>Установить вид работы</label>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="actions">
            <div class="ui positive right labeled icon button">
                Установить фильтр
                <i class="checkmark icon"></i>
            </div>
            <div class="ui negative button">Сбросить фильтр</div>
        </div>
    </div>
</template>
<script>
    var filterform=Vue.extend({
        template: '#template-filter-form',
        data:function() {
            return {
                nameDetFilter : '' ,
                fioNprFilter  : '' ,
                initialized   : false
            }
        },
        components: {
            'podrselector' : podrselector
        },

        methods: {
            setFilter:function() {
                if (this.needNPR) {
                    this.addAuthor();
                    this.getFioNPR(this.shifrNpr);
                }
                else
                   this.finishGetFioNPR();

            },
            finishGetFioNPR:function() {
                if (this.needDet)
                    this.addShifrDet();

                this.$emit('esetfilter',this.nameDetFilter,this.fioNprFilter);

            },
            reSetFilter:function() {
                this.$emit('eresetfilter');
            },
            showfilter:function() {
                var vm=this;
                this.$nextTick(function() {
                    $('#filtermodal')
                           .modal({
                               closable  : true,
                               onDeny    : function(){
                                   vm.reSetFilter();
                               },
                               onApprove : function() {
                                   vm.setFilter();
                               }
                           })
                           .modal('show')
                    ;
<%--
                    vm.setAuthorsDD();
                    vm.setDetDropDown();
                    l=$('#cbNeedYear').length;
                    console.log('#cbNeedYear l='+l);
                    $('#cbNeedYear')
                            .checkbox()
                    ;
                    l=$('#cbNeedNPR').length;
                    console.log('#cbNeedNPR l='+l);
                    $('#cbNeedNPR')
                            .checkbox()
                    ;
                    l=$('#cbNeedPredp').length;
                    console.log('#cbNeedPredp l='+l);
                    $('#cbNeedPredp')
                            .checkbox()
                    ;
                    l=$('#cbNeedDet').length;
                    console.log('#cbNeedDet l='+l);
                    $('#cbNeedDet')
                            .checkbox()
                    ;
--%>
                });
            },
            ePodrSelected:function(id) {
                if (id && id>0 && this.shifrPredp!=id) {
                    this.shifrPredp=id;
                    this.$refs.podrslctr.getPodrCompoundName(id);
                }
            },
            eNamePodr:function(nameVal) {
                if (nameVal && _.isString(nameVal))
                    this.namePodr=nameVal;
            },
            addAuthor: function () {
                var id=$('#authorddb').dropdown("get value");
//                alertify.alert('id='+id);
                if (!id || (id<1)) return;

                var value = id;
                if (!value) {
                    return
                }
                this.shifrNpr=value;
            },
            addShifrDet: function() {
                var id=$('#ddDet').dropdown("get value");
                if (!id || (id<1)) return;
                var name=$('#ddDet').dropdown("get text");

                var value = id;
                if (!value) {
                    return
                }
                this.shifrDet      = value;
                this.nameDetFilter = name?name:"";
            },

            setAuthorsDD:function() {
                var l=$('#authorddb').length;
//                alertify.alert('inside init setAuthorsDD l='+l);
//                console.log('inside init setAuthorsDD l='+l);
                if (l>0) {
//                    alertify.alert('inside setAuthorsDD ');
//                    console.log('inside setAuthorsDD ');
                    var uri = this.$root.rootPath
                            + "/util/semanticui/dropdown/tags/{query}";
                    $('#authorddb').dropdown({
                        apiSettings: {
                            // this url parses query server side and returns filtered results
                            url: uri
                        }
                    })
                    ;
//                    if ($('#cb').length>0) {
//                        $('#cb')
//                                .checkbox()
//                        ;
//                    }
                }
            },
            setDetDropDown:function() {
                var l=$("#ddDet").length;
//                alertify.alert('inside setDetDropDown.l='+l);
//                console.log('inside setDetDropDown.l='+l);
                $("#ddDet")
                        .dropdown();
            },
            getFioNPR: function(shifrid) {
                var uri=this.$root.rootPath+"/util/user/fio/"+shifrid;
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
                            var rrec=response.data[0];
                            vm.$nextTick(function () {
                                vm.fioNprFilter = rrec.name;
                                vm.finishGetFioNPR();
                            });
                        })
                        .catch(function (error) {
                    alertify.alert("Ошибка","error getFioNPR=",error);
                });
            }
        },
        computed:{
            isadmin:function() {
                return this.$root.isadmin;
            },
            isnpr:function() {
                return this.$root.isnpr;
            },
            yearFr:{
                get: function () {
                    return this.$root.ntrFilter.yearFr;
                },
                set: function (v) {
                    this.$root.ntrFilter.yearFr = v;
                }
            },
            yearTo:{
                get: function () {
                    return this.$root.ntrFilter.yearTo;
                },
                set: function (v) {
                    this.$root.ntrFilter.yearTo = v;
                }
            },
            needYear:{
                get: function () {
                    return this.$root.ntrFilter.needYear;
                },
                set: function (v) {
                    this.$root.ntrFilter.needYear = v;
                }
            },
            shifrNpr:{
                get: function () {
                    return this.$root.ntrFilter.shifrNpr;
                },
                set: function (v) {
                    this.$root.ntrFilter.shifrNpr = v;
                }
            },
            needNPR:{
                get: function () {
                    return this.$root.ntrFilter.needNPR;
                },
                set: function (v) {
                    this.$root.ntrFilter.needNPR = v;
                }
            },
            shifrPredp:{
                get: function () {
                    return this.$root.ntrFilter.shifrPredp;
                },
                set: function (v) {
                    this.$root.ntrFilter.shifrPredp = v;
                }
            },
            needPredp:{
                get: function () {
                    return this.$root.ntrFilter.needPredp;
                },
                set: function (v) {
                    this.$root.ntrFilter.needPredp = v;
                }
            },
            namePodr:{
                get: function () {
                    return this.$root.ntrFilter.namePredp;
                },
                set: function (v) {
                    this.$root.ntrFilter.namePredp = v;
                }
            },
            needDet: {
                get: function () {
                    return this.$root.ntrFilter.needDet;
                },
                set: function (v) {
                    this.$root.ntrFilter.needDet = v;
                }
            },
            shifrDet: {
                get: function () {
                    return this.$root.ntrFilter.shifrDet;
                },
                set: function (v) {
                    this.$root.ntrFilter.shifrDet = v;
                }
            },
            isFilter:function() {
                var retVal=false;
                if (this.needNPR || this.needYear || this.needDet) {
                    retVal=true;
                }
                return retVal;
            }
        },
        mounted:function() {
                 if (!this.initialized) {
                     this.initialized=true;
                     console.log('filter initializatiod');
                    this.setAuthorsDD();
                    this.setDetDropDown();

                    $('#cbNeedYear')
                       .checkbox()
                    ;
                    $('#cbNeedNPR')
                       .checkbox()
                    ;
                    $('#cbNeedPredp')
                       .checkbox()
                    ;
                    $('#cbNeedDet')
                       .checkbox()
                    ;
            }

        }
    });
</script>