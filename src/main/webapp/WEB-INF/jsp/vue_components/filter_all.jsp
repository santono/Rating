<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-filter-form">
    <div class="ui large modal filterclass" v-bind:id="filtermodalid">
        <div class="ui header">
            Фильтр научных работ
        </div>
        <div class="content">
            <div class="ui form" v-bind:id="filterformid">
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
                        <div class="ui checkbox" v-bind:id="cbneedyearid">
                            <input v-model="needYear" class="hidden" type="checkbox">
                            <label>Установить диапазон дат</label>
                        </div>
                    </div>
                </div>

                <div v-show="isadmin" class="inline fields">
                    <%--
                                            <div v-show="needNPR" class="ui search selection dropdown twelve wide field" id="authorddb">
                    --%>
                    <div v-show="needNPR" class="ui search selection dropdown field " v-bind:id="authorddbid">
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
                        <div class="ui checkbox" v-bind:id="cbneednprid">
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
                        <div class="ui checkbox" v-bind:id="cbneedpredpid">
                            <input v-model="needPredp" class="hidden" type="checkbox">
                            <label>Выбрать подразделение</label>
                        </div>
                    </div>
                </div>
                <div class="inline fields">
                    <label v-show="needDet">Вид работы</label>
                    <div class="ui dropdown field" v-bind:id="dddetid" v-show="needDet">
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
                        <div class="ui checkbox" v-bind:id="cbneeddetid">
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
        props : {
            propid:String
        },
        data:function() {
            return {
                nameDetFilter : '' ,
                fioNprFilter  : ''
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
                    var namefilter=this.filtermodalid;
                    $('#'+namefilter)
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
                var nameauthddb=this.authorddbid;
                var id=$('#'+nameauthddb).dropdown("get value");
//                alertify.alert('id='+id);
                if (!id || (id<1)) return;

                var value = id;
                if (!value) {
                    return
                }
                this.shifrNpr=value;
            },
            addShifrDet: function() {
                var namedetddb=this.dddetid;
                var id=$('#'+namedetddb).dropdown("get value");
                if (!id || (id<1)) return;
                var name=$('#'+namedetddb).dropdown("get text");

                var value = id;
                if (!value) {
                    return
                }
                this.shifrDet      = value;
                this.nameDetFilter = name?name:"";
            },

            setAuthorsDD:function() {
                var nameauthddb=this.authorddbid;
//                var l=$('#authorddb').length;
                var l=$('#'+nameauthddb).length;
//                alertify.alert('inside init setAuthorsDD l='+l);
//                console.log('inside init setAuthorsDD l='+l);
                if (l>0) {
//                    alertify.alert('inside setAuthorsDD ');
//                    console.log('inside setAuthorsDD ');
                    var uri = this.$root.rootPath
                            + "/util/semanticui/dropdown/tags/{query}";
//                    $('#authorddb').dropdown({
                    $('#'+nameauthddb).dropdown({
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
                var namedddet=this.dddetid;
//                var l=$("#ddDet").length;
                var l=$("#"+namedddet).length;
//                alertify.alert('inside setDetDropDown.l='+l);
//                console.log('inside setDetDropDown.l='+l);
//                $("#ddDet")
                $("#"+namedddet)
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
            filtermodalid:function() {
                var retval;
                retval="filtermodal"+this.propid;
                return retval;
            },
            filterformid:function() {
                var retval;
                retval="filter"+this.propid;
                return retval;
            },
            cbneedyearid:function() {
                var retval;
                retval="cbNeedYear"+this.propid;
                return retval;
            },
            cbneednprid:function() {
                var retval;
                retval="cbNeedNpr"+this.propid;
                return retval;
            },
            cbneedpredpid:function() {
                var retval;
                retval="cbNeedPredp"+this.propid;
                return retval;
            },
            cbneeddetid:function() {
                var retval;
                retval="cbNeedDet"+this.propid;
                return retval;
            },
            authorddbid:function() {
                var retval;
                retval="authorddb"+this.propid;
                return retval;
            },
            dddetid:function() {
                var retval;
                retval="dddet"+this.propid;
                return retval;
            },
            isadmin:function() {
                return this.$root.isadmin;
            },
            isnpr:function() {
                return this.$root.isnpr;
            },
            yearFr:{
                get: function () {
                    return this.$parent.ntrFilter.yearFr;
                },
                set: function (v) {
                    this.$parent.ntrFilter.yearFr = v;
                }
            },
            yearTo:{
                get: function () {
                    return this.$parent.ntrFilter.yearTo;
                },
                set: function (v) {
                    this.$parent.ntrFilter.yearTo = v;
                }
            },
            needYear:{
                get: function () {
                    return this.$parent.ntrFilter.needYear;
                },
                set: function (v) {
                    this.$parent.ntrFilter.needYear = v;
                }
            },
            shifrNpr:{
                get: function () {
                    return this.$parent.ntrFilter.shifrNpr;
                },
                set: function (v) {
                    this.$parent.ntrFilter.shifrNpr = v;
                }
            },
            needNPR:{
                get: function () {
                    return this.$parent.ntrFilter.needNPR;
                },
                set: function (v) {
                    this.$parent.ntrFilter.needNPR = v;
                }
            },
            shifrPredp:{
                get: function () {
                    return this.$parent.ntrFilter.shifrPredp;
                },
                set: function (v) {
                    this.$parent.ntrFilter.shifrPredp = v;
                }
            },
            needPredp:{
                get: function () {
                    return this.$parent.ntrFilter.needPredp;
                },
                set: function (v) {
                    this.$parent.ntrFilter.needPredp = v;
                }
            },
            namePodr:{
                get: function () {
                    return this.$parent.ntrFilter.namePredp;
                },
                set: function (v) {
                    this.$parent.ntrFilter.namePredp = v;
                }
            },
            needDet: {
                get: function () {
                    return this.$parent.ntrFilter.needDet;
                },
                set: function (v) {
                    this.$parent.ntrFilter.needDet = v;
                }
            },
            shifrDet: {
                get: function () {
                    return this.$parent.ntrFilter.shifrDet;
                },
                set: function (v) {
                    this.$parent.ntrFilter.shifrDet = v;
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
                  var l=$(".filterclass").length;
                  var ll=$("#cbNeedYear").length;
                  var n=this.filterformid;
                  var lll=$("#"+n).length;
                  var namecbneedyear  = this.cbneedyearid;
                  var namecbneednpr   = this.cbneednprid;
                  var namecbneedpredp = this.cbneedpredpid;
                  var namecbneeddet   = this.cbneeddetid;
                 console.log('mounted: l='+l+" ll="+ll+" n="+n+" lll="+lll);
                 if (!this.$parent.ntrFilterInitialized) {
                     this.$parent.ntrFilterInitialized=true;
//                     console.log('filter initialization');
                    this.setAuthorsDD();
                    this.setDetDropDown();

//                    $('#cbNeedYear')
                    $('#'+namecbneedyear)
                       .checkbox()
                    ;
//                    $('#cbNeedNPR')
                    $('#'+namecbneednpr)
                       .checkbox()
                    ;
//                    $('#cbNeedPredp')
                    $('#'+namecbneedpredp)
                       .checkbox()
                    ;
//                    $('#cbNeedDet')
                    $('#'+namecbneeddet)
                       .checkbox()
                    ;
            }

        }
    });
</script>