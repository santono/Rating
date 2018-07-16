<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-report-pokaz-table">
    <div>
        <p>Перечень показателей</p>
        <p v-cloak>{{namepre}}</p>
        <div class="ui centered loader" v-bind:class="{active:isLoading}">Генерация свода</div>
        <table class="ui selectable celled striped very compact large table" v-bind:class="{loading:isLoading}">
            <thead>
            <tr>
                <th>#</th>
                <th>Название</th>
                <th>Подтв</th>
                <th>Не подтв</th>
                <th>
                    <div class="mini ui icon button" data-tooltip="Печать свода" v-on:click="printSwod">
                        <i class="print icon"></i>
                    </div>
<%--
                    <a href="javascript:this.getHref();" class="mini ui icon button" data-tooltip="Печать свода" target="_blank">
                        <i class="print icon"></i>
                    </a>    
--%>
                </th>
            </tr>
            </thead>
            <tbody  v-bind:class="{loading:isLoading}">

            <tr v-for="(item,index) in pokazlist" :index="index" :key="item.lineno" >
                <td>{{item.lineno}}</td>
                <td>{{item.name}}</td>
                <td>{{item.amnt}}</td>
                <td>{{item.amntn}}</td>
                <td><a class="mini ui button" v-on:click="show(item)" v-if="canshowrow(item)" data-tooltip="Разбивка по подразделениям"><i class ="align justify icon"></i></a></td>
            </tr>

            </tbody>
        </table>
    </div>
</template>
<script>
    var reportpokaztable=Vue.extend({
        template: '#template-report-pokaz-table',
        data:function() {
            return {pokazlist     :  null,
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
                var url = this.$root.rootPath+"/util/reportPokaz/"+this.shifrpre;
//                const url = window.URL.createObjectURL(new Blob([response.data]));
                const link = document.createElement('a');
                link.href = url;
                link.target = "_blank";
          //      link.setAttribute('download', fileName);
                document.body.appendChild(link);
                link.click();
            },
            getHref:function() {
                var url = this.$root.rootPath+"/util/reportPokaz/"+this.shifrpre;
                return url;
            },
            getPokazList:function() {
                var uri = this.$root.rootPath+"/util/reppokazs/"+this.shifrpre;

                var vm       = this;
                vm.isLoading=true;
//                $('#rpcontainer').dimmer('show');
                axios.get(uri, {
                })
                        .then(function (response) {
                            vm.pokazlist=response.data;
                            vm.pokazlist=_.orderBy(vm.pokazlist,'lineno','asc');
                            for (var i=0;i<vm.pokazlist.length;i++){
                                if (vm.pokazlist[i].amnt<0.001)
                                    vm.pokazlist[i].amnt='';
                                if (vm.pokazlist[i].amntn<0.001)
                                    vm.pokazlist[i].amntn='';
                            }
                            vm.isLoading=false;
//                            $('#rpcontainer').dimmer('hide');
//                            alert('shifrpre='+vt.shifrpre+' amnt of user '+vt.userslist.length);
                        })

                        .catch(function (error) {
                    vm.isLoading=false;
//                    $('#rpcontainer').dimmer('hide');
                   alertify.alert('Ошибка','error reading pokazlist='+error);
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
                vm.getPokazList();

            });
        }
    });

</script>