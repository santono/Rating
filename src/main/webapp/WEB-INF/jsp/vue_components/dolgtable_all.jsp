<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-dolg-table">
    <div>
        <div class="ui breadcrumb" id="breadcrumbh">

        </div>
        <p>Справочник должностей</p>
        <table class="ui selectable celled striped very compact large table">
            <thead>
            <tr>
                <th v-on:click.prevent="sortdolg('id')"        >#<i class="chevron up icon" v-if="currentSort=='id' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='id' && currentSortDir=='desc'"></i></th>
                <th v-on:click.prevent="sortdolg('name')"      >Название<i class="chevron up icon" v-if="currentSort=='name' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='name' && currentSortDir=='desc'"></i></th>
                <th v-on:click.prevent="sortdolg('shortname')" >Краткое название<i class="chevron up icon" v-if="currentSort=='shortname' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='shortname' && currentSortDir=='desc'"></i></th>
<%--
                <th>Тип</th>
--%>
                <th>
                    <div v-on:click="addrec" class="ui icon button" data-tooltip="Добавить должность" data-position="bottom right">
                        <i class="add icon"></i>
                    </div>
                </th>
            </tr>
            </thead>
            <tbody>

            <tr is="dolgrow" v-for="(item,index) in sorteddolglist" v-bind:rowrec="item" :index="index" :key="item.id" v-on:editrec="editrec" v-on:delrec="deleterec" >
            </tr>

            </tbody>
        </table>

        <dolgform v-bind:receivedrec='selectedrec' v-bind:show.sync="showModal" v-bind:receivedaction="action" v-on:eshowmodal="setEShowModal" v-on:edelrec="eDelRec" v-on:eadddolg="eAddRec">

        </dolgform>
    </div>
</template>
<script>
    var dolgtable=Vue.extend({
        template: '#template-dolg-table',
        data:function() {
            return {dolglist    :  null ,
                selectedrec     :    {} ,
                selectedindex   :    -1 ,
                showModal       : false ,
                currentSort     : 'id'  ,
                currentSortDir  : 'asc' ,
                action        : 0
            };

        },
        components: {
            'dolgrow' : dolgrow  ,
            'dolgform': dolgform
        },

        methods: {
            setEShowModal : function(newVal,newRec) {
                this.showModal=newVal;
                if (newRec) {
                    if (!_.isEqual(this.selectedrec,newRec)) {
                        this.$nextTick(function () {
                            this.selectedrec=_.clone(newRec);
                            if (this.selectedindex>=0)
                                Vue.set(this.dolglist,this.selectedindex,this.selectedrec);
                        });
                    }
                }
            },
            eAddRec:function(newRec) {
                if (newRec) {
                    this.dolglist.push(newRec);
                    console.log('before sorting');
                    this.performSortingDolg();
                    console.log('after sorting');
                }
            },
            eDelRec:function(newRec) {
                if (this.selectedindex!=undefined)
                    if (this.selectedindex>=0) {
                        this.dolglist.splice(this.selectedindex,1);
                        this.performSortingDolg();
                    }
            },
            editrec:function(currrec,index) {
//                alert(JSON.stringify(currrec));
                this.selectedrec   = currrec;
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
                this.selectedrec.kind       = 0;
                this.selectedindex          = null;
                this.action                 = 1;
                this.showModal              = true;
            },

            deleteRec: function(item){
                vm.dolglists.$remove(item)
            },
            performCompareDolg: function(dolg1,dolg2) {
                var retVal=0;
                if (dolg1.id>dolg2.id) retVal=1;
                else
                if (dolg1.id<dolg2.id) retVal=-1;
                return retVal;
            },
            performSortingDolg:function() {
                if (this.dolglist)
                    if (Array.isArray(this.dolglist)) {
                        var vm=this;
                        this.$nextTick(function () {
                            _.orderBy(vm.dolglist, ['id'],['asc']);
                        });
                    }
//                        this.dolglist.sort(this.performCompareDolg)
            },
            sortdolg:function(s) {
                //if s == current sort, reverse
                console.log('sortdolg s='+s+' this.currentSort='+this.currentSort+' this.currentSortDir='+this.currentSortDir);
                if(s === this.currentSort) {
                    this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
                }
                this.currentSort = s;
            },
            getDolgList:function() {
<%--
                <c:url value = "/util/dolgs" var="uri2" />
                var uri3     = "${uri2}";
                var uri      = uri3;
--%>
                var uri      = this.$root.rootPath+"/util/dolgs";
                var vm       = this;
                var finished = false;
                axios.get(uri, {
                })
                        .then(function (response) {
                            finished=true;
                            vm.dolglist=response.data;
                            vm.performSortingDolg();
//                            alert('shifrpre='+vt.shifrpre+' amnt of user '+vt.userslist.length);
                        })
                        .catch(function (error) {
                    alert('error reading dolglist='+error);
                    finished=true;
                });
            }
        },
        computed:{
            sorteddolglist:function() {
                var vm=this;
                if (this.dolglist==null)
                    return this.dolglist;
                else
                return this.dolglist.sort(function(dolg1,dolg2)  {
                    var modifier = 1;
                    if(vm.currentSortDir === 'desc') modifier = -1;
                    if(dolg1[vm.currentSort] < dolg2[vm.currentSort]) return -1 * modifier;
                    if(dolg1[vm.currentSort] > dolg2[vm.currentSort]) return 1 * modifier;
                return 0;
                });

//                return  _.orderBy(this.dolglist, ['id'],['asc']);
            }
        },
        mounted:function () {
            this.$nextTick(function () {
                $('.ui.sidebar').sidebar('toggle');
                $("#startcard").hide();
            });
        },
        beforeRouteUpdate:function(to, from, next) {
            this.$nextTick(function () {
                this.getDolgList();
            });
            next();
        },
        created: function() {
            this.getDolgList();
        },
        beforeCreate:function() {
            var l=$( "#modaldolg" ).length;
//            console.log('univrable: beforeCreate amnt of modal univ='+l);
            if (l>0) {
                $( "#modaldolg" ).remove();
            }
        }
    });

</script>