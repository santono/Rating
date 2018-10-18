<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-diagram">
    <div class="ui grid">
       <div class="one wide column">
          <div v-on:click="showfilter" class="compact mini ui icon button" data-tooltip="Установить фильтр записей" data-position="bottom left">
              <i class="filter icon"></i>
          </div>
       </div>
       <div class="eleven wide column">
            <div class="ui centered loader" v-bind:class="{active:isLoading}">Генерация диаграммы</div>
            <div id="containerstu" style="min-width: 310px; height: 600px; margin: 0 auto"></div>
       </div>
        <filterform ref="filter"
                    propid="2"
                    v-on:eresetfilter = "reSetFilter"
                    v-on:esetfilter   = "setFilter"

                ></filterform>

    </div>

</template>
<script>
    var diagram=Vue.extend({
        template: '#template-diagram',
        data:function() {
            return {title     :  '',
                subtitle      :  '',
                categories    :  null,
                series        :  null,
                shifrpre      :     1,
                namepre       :    "",
                isLoading     : false,
                fioNprFilter  : '',
                nameDetFilter : '',
                ntrFilter:{
                    needYear   : false     ,
                    yearFr     : 1970      ,
                    yearTo     : 2018      ,
                    needNPR    : false     ,
                    shifrNpr   : 0         ,
                    needPredp  : false     ,
                    shifrPredp : 0         ,
                    namePredp  : ''        ,
                    needDet    : false     ,
                    shifrDet   : 0
                },
                ntrFilterInitialized:false

            };

        },
        components: {
            'filterform'   : filterform
        },
        computed: {
            yearFr:{
                get: function () {
                    return this.ntrFilter.yearFr;
                },
                set: function (v) {
                    this.ntrFilter.yearFr = v;
                }
            },
            yearTo:{
                get: function () {
                    return this.ntrFilter.yearTo;
                },
                set: function (v) {
                    this.ntrFilter.yearTo = v;
                }
            },
            needYear:{
                get: function () {
                    return this.ntrFilter.needYear;
                },
                set: function (v) {
                    this.ntrFilter.needYear = v;
                }
            },
            shifrNpr:{
                get: function () {
                    return this.ntrFilter.shifrNpr;
                },
                set: function (v) {
                    this.ntrFilter.shifrNpr = v;
                }
            },
            needNPR:{
                get: function () {
                    return this.ntrFilter.needNPR;
                },
                set: function (v) {
                    this.ntrFilter.needNPR = v;
                }
            },
            shifrPredp:{
                get: function () {
                    return this.ntrFilter.shifrPredp;
                },
                set: function (v) {
                    this.ntrFilter.shifrPredp = v;
                }
            },
            needPredp:{
                get: function () {
                    return this.ntrFilter.needPredp;
                },
                set: function (v) {
                    this.ntrFilter.needPredp = v;
                }
            },
            namePodr:{
                get: function () {
                    return this.ntrFilter.namePredp;
                },
                set: function (v) {
                    this.ntrFilter.namePredp = v;
                }
            },
            needDet: {
                get: function () {
                    return this.ntrFilter.needDet;
                },
                set: function (v) {
                    this.ntrFilter.needDet = v;
                }
            },
            shifrDet: {
                get: function () {
                    return this.ntrFilter.shifrDet;
                },
                set: function (v) {
                    this.ntrFilter.shifrDet = v;
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
        methods: {
            makeBarByYear:function() {
                Highcharts.chart('containerstu', {
                    chart: {
                        type: 'column',
                        options3d: {
                            enabled: true,
                            alpha: 10,
                            beta: 25,
                            depth: 70
                        }
                    },
                    title: {
                        text: this.title
                    },
                    subtitle: {
                        text: this.subtitle
                    },
                    plotOptions: {
                        column: {
                            depth: 25
                        }
                    },
                    xAxis: {
                        categories: this.categories
                    },
                    yAxis: {
                      title: {
                            text: null
                      }
                    },
                    series: this.series
                });
            },
            setFilter:function(nameDetFilter,fioNprFilter) {
                var needRefresh=false;
                if (this.needYear
                        ||
                        (this.needNPR && this.shifrNpr>0)
                        ||
                        (this.needDet && this.shifrDet>0)
                        )
                    needRefresh=true;
                if (this.needNPR && this.shifrNpr>0) {
                    this.fioNprFilter=fioNprFilter;
                }
                if (this.needDet && this.shifrDet>0) {
                    this.nameDetFilter=nameDetFilter;
                }
                if (needRefresh)
                    this.getPokazBarByYear();

            },
            reSetFilter:function() {
                var needRefresh=false;
                if (this.needYear) {
                    this.needYear=false;
                    needRefresh=true;
                }
                if (this.needNPR) {
                    this.needNPR=false;
                    needRefresh=true;
                }
                if (this.needDet) {
                    this.needDet=false;
                    needRefresh=true;
                }
                if (needRefresh)
                    this.getPokazBarByYear();

            },
            showfilter:function() {
                var vm=this;
                this.$nextTick(function () {
                     vm.$refs.filter.showfilter();
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

            getPokazBarByYear:function() {
                var currentYear = new Date().getUTCFullYear();
                var yto=currentYear;
                var yfr=yto-4;
                if (this.needYear && this.yearFr>1960 && (this.yearTo>=this.yearFr)) {
                    yfr=this.yearFr;
                    yto=this.yearTo;
                }
                var shifrNpr=0;
                if (this.needNPR && this.shifrNpr>0) {
                    shifrNpr=this.shifrNpr;
                }
                var shifrDet=0;
                if (this.needDet && this.shifrDet) {
                    shifrDet=this.shifrDet;
                }
                var uri = this.$root.rootPath+"/util/diapokcolumn/"+this.shifrpre+"/"+yfr+"/"+yto+"/"+shifrNpr+"/"+shifrDet;

                var vm       = this;
                vm.isLoading=true;
//                $('#rpcontainer').dimmer('show');
                axios.get(uri, {
                        })
                        .then(function (response) {
                            var resp      = response.data;
                            vm.title      = resp.title;
                            vm.subtitle   = resp.subtitle;
                            vm.categories = resp.categories;
                            vm.series     = resp.series;
                            vm.isLoading  = false;
                            vm.makeBarByYear();
                        })

                        .catch(function (error) {
                    vm.isLoading=false;
//                    $('#rpcontainer').dimmer('hide');
                    alertify.alert('Ошибка','error getPokazBarByYear='+error);
                });

            },

            getPreName:function() {
                var uri = this.$root.rootPath+"/util/univ/"+this.shifrpre;
                var vm       = this;
                axios.get(uri, {
                })
                        .then(function (response) {
                            finished=true;
                            vm.namepre=response.data.name;
                        })

                        .catch(function (error) {
                    alertify.alert('Ошибка','Ошибка получение названия подразделения '+error());
                });
            },
            show:function() {

            }

        },
        mounted:function () {
            if (!this.$root.user)
                window.location.assign("/r");

            var vm=this;
            this.shifrpre=this.$root.user.shifrPodr;
            this.$nextTick(function () {
                $('.ui.sidebar').sidebar('toggle');
                $("#startcard").hide();
                vm.getPreName();
                vm.getPokazBarByYear();
            });
        },
        created: function() {
            this.shifrpre = this.$route.params.id;
        }
    });

</script>