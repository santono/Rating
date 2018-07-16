<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-univ-table">
    <div>
        <div class="ui breadcrumb" id="breadcrumbh">
<%--
            <a class="section">Home</a>
            <div class="divider"> / </div>
            <a class="section">Store</a>
            <div class="divider"> / </div>
            <div class="active section">T-Shirt</div>
--%>
        </div>
        <p>{{ownername}}</p>
        <table class="ui selectable celled striped very compact table">
            <thead>
            <tr>
                <th>#</th>
                <th>Название</th>
                <th>Краткое название</th>
                <th>
                    <div v-on:click="adduniv" class="ui icon button" data-tooltip="Добавить структурное подразделение или филиал" data-position="bottom right">
                        <i class="add icon"></i>
                    </div>
<%--
                    <button class="ui button" data-content="Добавить структурное подразделение или филиал"><i class="plus icon"></i></button>
--%>
                </th>
            </tr>
            </thead>
            <tbody>

            <tr is="univrow" v-for="(univ,index) in universities" :univ="univ" :index="index" :key="univ.id" v-on:editu="editrec" v-on:deltu="deleterec" >
            </tr>

            </tbody>
        </table>
        <univform v-bind:univ='selecteduniv' v-bind:show.sync="showModal" v-bind:receivedaction="action" v-on:eshowmodal="setEShowModal" v-on:eaddpodr="eAddPodr" v-on:edelpodr="eDelPodr">
        </univform>


    </div>
</template>
<script>
    //     Vue.component('univtable',{
    var univtable=Vue.extend({
        template: '#template-univ-table',
        //   props: ['univ'],
//        props: {
//                currentOwner:0
//        },
        data:function() {
            return {universities : null,
                univ             : '',
                selecteduniv     : null,
                selectedindex    : -1,
                showModal        : false,
                owner            : 0,
                ownername        : "",
                action           : 0
            };

        },
        components: {
            'univrow':univrow,
            'univform':univform
        },

        methods: {
            setEShowModal:function(newVal,newUniv) {
//                alert('setEShowModal '+newVal);
                this.showModal=newVal;
                if (newUniv) {
                    if (!_.isEqual(this.selecteduniv,newUniv)) {
                        var vm=this;
                        this.$nextTick(function () {
//                             this.universities[0].name="проба апдейта";
                            vm.selecteduniv=_.clone(newUniv);
                            if (vm.selectedindex>=0)
                                Vue.set(this.universities,this.selectedindex,newUniv);
                        });
                    }
                }
            },
            eAddPodr:function(newPodr) {
//                alert('eAddPodr id='+newPodr.id+' '+newPodr.name+' '+newPodr.shortName+' '+newPodr.owner+' '+newPodr.canbedeleted);
                if (newPodr) {
//                    if (newPodr.id<=0){
                    this.universities.push(newPodr);
                    this.performSortingUnivs();
                }
            },
            eDelPodr:function(newPodr) {
//                alert('eAddPodr index='+this.selectedindex);
                if (this.selectedindex!=undefined)
                    if (this.selectedindex>=0) {
//                    if (newPodr.id<=0){
                    this.universities.splice(this.selectedindex,1);
                    this.performSortingUnivs();
                }
            },
            editrec:function(curruniv,index) {
                console.log("univtable: inside editrec.curruniv="+JSON.stringify(curruniv));
                this.selecteduniv  = _.clone(curruniv);
                this.selectedindex = index;
                this.action        = 2;
                this.showModal     = true;
            },
            deleterec:function(curruniv,index) {

                this.selecteduniv  = curruniv;
                this.selectedindex = index;
                this.action        = 3;
                this.showModal     = true;
            },
            adduniv:function() {
//                alert('adduniv 1');
                this.selecteduniv={};
//                alert('adduniv 2');
                this.selecteduniv.id        =  0;
                this.selecteduniv.name      = '';
                this.selecteduniv.shortName = '';
                this.selecteduniv.canbedeleted = true;
//                this.selecteduniv.owner     = this.owner;    //this.currentOwner;
                this.selecteduniv.shifrIdOwner = this.owner;
//                alert('adduniv 3');
                this.selectedindex=null;
//                alert('adduniv 4');
                this.action = 1;
                this.showModal=true;
            },

            deleteUniv: function(univ){
                vm.universities.$remove(univ)
            },
            getUnivName:function() {
                var uri=this.$root.rootPath+"/util/univ/"+this.owner;
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
                            vm.ownername=response.data.name;
                        })

                        .catch(function (error) {
                          alertify.alert('Ошибка.','Ошибка получения названия подразделения '+error);
                });

            },
            performCompareUnivs: function(univ1,univ2) {
                var retVal=0;
                retVal=univ1.name.toUpperCase().trim().localeCompare(univ2.name.toUpperCase().trim());
                return retVal;
            },
            performSortingUnivs:function() {
               if (this.universities)
               if (Array.isArray(this.universities))
                  this.universities.sort(this.performCompareUnivs)
            },
            getBreadCrumbFromServer:function () {

                var uri=this.$root.rootPath+"/util/univ/bc/"+this.owner+"/"+this.$root.user.shifrPodr;
                var vm=this;
                var finished=false;
                axios.get(uri, {
                })
                        .then(function (response) {
//                          alert("response "+response.data);
                            finished=true;
                            vm.$nextTick(function () {
                               $("#breadcrumbh").html(response.data);
                            });
//                            vm.universities=response.data;
                        })

                        .catch(function (error) {
                        alertify.alert('Ошибка',"Ошибка получения breadcrump "+error);
                    finished=true;
                });
            },
            getUnivList:function() {

                var uri=this.$root.rootPath+"/util/univs/"+this.owner;
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
                            vm.universities=response.data;
                        })

                        .catch(function (error) {
                          alertify.alert("Ошибка","Ошибка получения списка подразделений "+error);
                });

            }
        },
//        watch: {
//          currentOwner:function(val) {
//              this.owner=val;
//              this.getUnivName();
//          },
//          '$route':function(to, from) {
// //             alert('to='+to+' from='+from);
//        // обработка изменений параметров пути...
//          }
//        },
//        beforeRouteEnter:function(to, from, next) {
//           getPost(to.params.id, function(err, post) {
//               next(vm => vm.setData(err, post))
//           })
//                ,
// если путь изменяется, а компонент уже отображён,
// логика будет немного иной
        mounted:function () {
            this.$nextTick(function () {
                $('.ui.sidebar').sidebar('toggle');
                $("#startcard").hide();
            });
        },
        beforeRouteUpdate:function(to, from, next) {
//          console.log('beforeRouteUpdate to='+to.toString()+' from='+from.toString()+' next='+next+'-- '+next.toString()+' this.$route.params.id'+this.$route.params.id);
        // обработка изменений параметров пути...
        // не забудьте вызывать next()
            var vm=this;
            this.$nextTick(function () {
              vm.owner=this.$route.params.id;
              vm.getUnivName();
              vm.getUnivList();
              vm.performSortingUnivs();
              vm.getBreadCrumbFromServer();
            });
            next();
         },
        created: function() {
            if (!this.$root.user)
               window.location.assign("/r");
//            console.log('created '+this.$route.params.id);
            this.owner=this.$route.params.id;
            this.getUnivName();
            this.getUnivList();
            this.performSortingUnivs();
            this.getBreadCrumbFromServer();
        },
        beforeCreate:function(){
            var l=$( "#modaluniv" ).length;
//            console.log('univrable: beforeCreate amnt of modal univ='+l);
            if (l>0) {
                $( "#modaluniv" ).remove();
            }

        }

    });

</script>