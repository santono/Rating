<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-diagram">
    <div>
        <div class="ui centered loader" v-bind:class="{active:isLoading}">Генерация диаграммы</div>
        <div id="containerstu" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
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
                isLoading     : false
            };

        },
        components: {
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
            getHref:function() {
                var url = this.$root.rootPath+"/util/reportRating/"+this.shifrpre+"/1960/2020";
                return url;
            },
            getPokazBarByYear:function() {
                var currentYear = new Date().getUTCFullYear();
                var yto=currentYear;
                var yfr=yto-4;
                var uri = this.$root.rootPath+"/util/diapokcolumn/"+this.shifrpre+"/"+yfr+"/"+yto;

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
                    alertify.alert('Ошибка','error reading ratinglist='+error);
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