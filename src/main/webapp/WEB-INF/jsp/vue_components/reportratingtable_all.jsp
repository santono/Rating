<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-report-rating-table">
    <div>
        <p>Рейтинг НПР</p>
        <p v-cloak>{{namepre}}</p>
        <div class="ui centered loader" v-bind:class="{active:isLoading}">Генерация рейтинга</div>
        <table class="ui selectable celled striped very compact large table" v-bind:class="{loading:isLoading}">
            <thead>
            <tr>
                <th>#</th>
                <th>Фамилия</th>
                <th>Кол-во</th>
            </tr>
            </thead>
            <tbody  v-bind:class="{loading:isLoading}">

            <tr v-for="(item,index) in ratinglist" :index="index" :key="item.lineno" >
                <td>{{item.lineno}}</td>
                <td>{{item.fio}}</td>
                <td>{{item.amnt}}</td>
            </tr>

            </tbody>
        </table>
    </div>
</template>
<script>
    var reportratingtable=Vue.extend({
        template: '#template-report-rating-table',
        data:function() {
            return {ratinglist     :  null,
                selectedrec   :    {},
                selectedindex :    -1,
                shifrpre      :     0,
                namepre       :    "",
                isLoading     : false
            };

        },
        components: {
        },
        methods: {
            printSwod:function() {
                var url = this.$root.rootPath+"/util/reportRating/"+this.shifrpre;
                const link = document.createElement('a');
                link.href = url;
                link.target = "_blank";
                document.body.appendChild(link);
                link.click();
            },
            getHref:function() {
                var url = this.$root.rootPath+"/util/reportRating/"+this.shifrpre+"/1960/2020";
                return url;
            },
            getRatingList:function() {
                var currentYear = new Date().getUTCFullYear();

                var uri = this.$root.rootPath+"/util/reprating/"+this.shifrpre+"/"+currentYear+"/"+currentYear;

                var vm       = this;
                vm.isLoading=true;
//                $('#rpcontainer').dimmer('show');
                axios.get(uri, {
                })
                        .then(function (response) {
                            vm.ratinglist=response.data;
                            vm.ratinglist=_.orderBy(vm.ratinglist,'lineno','asc');
                            vm.isLoading=false;
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
            canshowrow:function(item) {
                var retVal;
                retVal=true;
//                if (item && item.amnt)
                return retVal;
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
                vm.getRatingList();

            });
        }
    });

</script>