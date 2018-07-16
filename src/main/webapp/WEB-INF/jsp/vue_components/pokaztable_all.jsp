<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-pokaz-table">
    <div>
        <div class="ui breadcrumb" id="breadcrumbh">

        </div>
        <p>Перечень показателей</p>
        <table class="ui selectable celled striped very compact large table">
            <thead>
            <tr>
                <th>#</th>
                <th>Название</th>
                <th>Краткое название</th>
                <th>Входит в</th>
                <th>
                    <div v-on:click="addrec" class="ui icon button" data-tooltip="Добавить показатель" data-position="bottom right">
                        <i class="add icon"></i>
                    </div>
                </th>
            </tr>
            </thead>
            <tbody >

            <tr is="pokazrow" v-for="(item,index) in pokazlist" v-bind:rowrec="item" :index="index" :key="item.id" v-on:editrec="editrec" v-on:delrec="deleterec" >
            </tr>

            </tbody>
        </table>

        <pokazform v-bind:receivedrec='selectedrec' v-bind:show.sync="showModal" v-bind:receivedaction="action" v-on:eshowmodal="setEShowModal" v-on:edelrec="eDelRec" >

        </pokazform>
    </div>
</template>
<script>
    var pokaztable=Vue.extend({
        template: '#template-pokaz-table',
        data:function() {
            return {pokazlist     :  null,
                    selectedrec   :    {},
                    selectedindex :    -1,
                    showModal     : false,
                    action        : 0
            };

        },
        components: {
            'pokazrow' : pokazrow  ,
            'pokazform': pokazform
        },

        methods: {
            setEShowModal : function(newVal,newRec) {
                this.showModal=newVal;
                if (newRec) {
                    if (!_.isEqual(this.selectedrec,newRec)) {
                        this.$nextTick(function () {
                            this.selectedrec=_.clone(newRec);
                            if (this.selectedindex>=0)
                                Vue.set(this.pokazlist,this.selectedindex,this.selectedrec);
                        });
                    }
                }
            },
            eAddRec:function(newRec) {
                if (newRec) {
                    this.pokazlist.push(newRec);
                    this.performSortingPokaz();
                }
            },
            eDelRec:function(newRec) {
                if (this.selectedindex!=undefined)
                    if (this.selectedindex>=0) {
                    this.pokazlist.splice(this.selectedindex,1);
                    this.performSortingPokazat();
                }
            },
            editrec:function(currrec,index) {
//                alert(JSON.stringify(currrec));
                this.selectedrec  = currrec;
                this.selectedindex = index;
                this.action        = 2;
                this.showModal     = true;
            },
            deleterec:function(currrec,index) {

                this.selectedrec  = currrec;
                this.selectedindex = index;
                this.action        = 3;
                this.showModal     = true;
            },
            addrec:function() {
                this.selectedrec            = {};
                this.selectedrec.id         =  0;
                this.selectedrec.name       = "";
                this.selectedrec.shortname  = "";
                this.selectedrec.idowner    = 0;
                this.selectedindex          = null;
                this.action                 = 1;
                this.showModal              = true;
            },

            deleteRec: function(item){
                vm.pokazlists.$remove(item)
            },
            performComparePokaz: function(pokaz1,pokaz2) {
                var retVal=0;
                if (pokaz1.lineno>pokaz2.lineno) retVal=1;
                else 
                if (pokaz1.lineno<pokaz2.lineno) retVal=-1;
                return retVal;
            },
            performSortingPokaz:function() {
               if (this.pokazlist)
               if (Array.isArray(this.pokazlist))
                  this.pokazlist.sort(this.performComparePokaz)
            },
            getPokazList:function() {
<%--
                <c:url value = "/util/pokazs" var="uri2" />
                var uri3     = "${uri2}";
                var uri      = uri3;
--%>
                var uri = this.$root.rootPath+"/util/pokazs";
                var vm       = this;
                var finished = false;
                axios.get(uri, {
                })
                        .then(function (response) {
                            finished=true;
                            vm.pokazlist=response.data;
							vm.performSortingPokaz();
//                            alert('shifrpre='+vt.shifrpre+' amnt of user '+vt.userslist.length);
                        })

                        .catch(function (error) {
                         alert('error reading pokazlist='+error);
                          finished=true;
                });

            }
        },
        mounted:function () {
            this.$nextTick(function () {
                $('.ui.sidebar').sidebar('toggle');
                $("#startcard").hide();

            });
        },
        beforeRouteUpdate:function(to, from, next) {
            var vm=this;
            this.$nextTick(function () {
              vm.getPokazList();
            });
            next();
         },
        created: function() {
            this.getPokazList();
        },
        beforeCreate:function() {
            var l=$( "#modalpokaz" ).length;
//            console.log('univrable: beforeCreate amnt of modal univ='+l);
            if (l>0) {
                $( "#modalpokaz" ).remove();
            }

        }

    });

</script>