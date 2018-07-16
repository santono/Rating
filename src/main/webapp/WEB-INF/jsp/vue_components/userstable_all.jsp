<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-users-table">
    <div>
        <div class="ui breadcrumb" id="breadcrumbh">

        </div>
        <p>{{prename}}</p>
        <table class="ui selectable celled striped very compact table">
            <thead>
            <tr>
                <th v-on:click.prevent="sortuser('id')"># <i class="chevron up icon" v-if="currentSort=='id' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='id' && currentSortDir=='desc'"></i></th>
                <th v-on:click.prevent="sortuser('FIO')">ФИО <i class="chevron up icon" v-if="currentSort=='FIO' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='FIO' && currentSortDir=='desc'"></i></th>
                <th v-on:click.prevent="sortuser('podr')">Подразделение <i class="chevron up icon" v-if="currentSort=='podr' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='podr' && currentSortDir=='desc'"></i></th>
                <th v-on:click.prevent="sortuser('dolg')">Должность <i class="chevron up icon" v-if="currentSort=='dolg' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='dolg' && currentSortDir=='desc'"></i></th>
                <th v-on:click.prevent="sortuser('status')">Статус <i class="chevron up icon" v-if="currentSort=='status' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='status' && currentSortDir=='desc'"></i></th>
<%--
                <th>Дата<br>верификации</th>
--%>
                <th>
                    <div v-on:click="adduser" class="ui icon button" data-tooltip="Добавить пользователя" data-position="bottom right">
                        <i class="add icon"></i>
                    </div>
                </th>
            </tr>
            </thead>
            <tbody  v-bind:class="{loading:isLoading}">

            <tr is="userrow" v-for="(useritem,index) in userslist" v-bind:rowuser="useritem" :index="index" :key="useritem.id" v-on:edituser="edituserrec" v-on:deluser="deleteuserrec" >
            </tr>

            </tbody>
            <tfoot v-if="pager.amntofpage>1">
               <tr>
                   <th colspan="6">
                      <div class="ui right floated pagination menu">
                           <a class="icon item" v-on:click.prevent="goPrevPage()" v-bind:class="{disabled:pager.leftangle.disabled}" v-if="pager.leftangle.visible">
                              <i class="left chevron icon"></i>
                           </a>
                           <a class="item" v-on:click.prevent="goPage(pageitem.page)" v-for="(pageitem,index) in pager.pages" v-bind:class="{disabled:pageitem.disabled}" >
                               {{pageitem.page}}
                           </a>
<%--
                           <a class="item">1</a>
                           <a class="item">2</a>
                           <a class="item">3</a>
                           <a class="item">4</a>
--%>
                           <a class="icon item" v-on:click.prevent="goNextPage()" v-bind:class="{disabled:pager.rightangle.disabled}" v-if="pager.rightangle.visible">
                              <i class="right chevron icon"></i>
                           </a>
                      </div>
                   </th>
               </tr>
            </tfoot>
        </table>

        <userform  v-bind:receivedaction="action" v-bind:receivedrec='selecteduser' v-bind:show.sync="showModal" v-on:eshowmodal="setEShowModal" v-on:edeluser="eDelUser" v-on:ereaduser="refreshstatuscode" >

        </userform>
    </div>
</template>
<script>
    //     Vue.component('userstable',{
    var userstable=Vue.extend({
        template: '#template-users-table',
        //   props: ['user'],
//        props: {
//                currentshifrpre:0
//        },
        data:function() {
            return {userslist : null,
                pre              : '',
                selecteduser     : '',
                selectedindex    : -1,
                showModal        : false,
                shifrpre         : 0,
                prename          : "",
                action           : 0 ,
                isLoading        : false,
                pager            : {
                    pagesize     : 10,
                    pageno       : 1,
                    amntofpage   : 1,
                    startvisiblepage    : 1,
                    amntofvisiblepages  : 5,
                    leftangle    : {visible:false,disabled:true} ,
                    rightangle   : {visible:false,disabled:true} ,
                    pages        : []
                },
                currentSort     : 'FIO'  ,
                currentSortDir  : 'asc'  ,
                currentSortCode : 1

            };

        },
        components: {
            'userrow' : userrow  ,
            'userform': userform
        },

        methods: {
            setEShowModal : function(newVal,newUser) {
//                alert('setEShowModal '+newUser);
<%--
                if ((this.selecteduser) && (newUser)) {
                    var currrec=_.clone(this.selecteduser);
                    currrec.fio             = newUser.fam.trim()+' '+newUser.nam.trim()+' '+newUser.otc.trim();
//                    currrec.nameDol         =
                    currrec.statusName      = newUser.statusCode==1?"Зарегистирован":"Не зарегистирован";
                    currrec.dataVerification= newUser.dataVerification;
                    console.log("curruser created "+currrec.toString());
                }
--%>
                this.showModal=newVal;
                if (newUser) {
                    this.getUsersList();
//                    if (!_.isEqual(this.selecteduser,currrec)) {
//                        this.$nextTick(function () {
//                            this.selecteduser=_.clone(currrec);
//                            if (this.selectedindex>=0)
//                                Vue.set(this.userslist,this.selectedindex,this.selecteduser);
//                        });
//                    }
                }
            },
            eAddUser:function(newUser) {
                if (newUser) {
                    console.log('usesrTable Add user '+newUser);
//                    this.userslist.push(newUser);
                    this.getUsersList();
                    this.performSortingUsers();
                }
            },
            eDelUser:function(newUser) {
                if (this.selectedindex!=undefined)
                    if (this.selectedindex>=0) {
//                         this.userslist.splice(this.selectedindex,1);
                         this.getUsersList();
//                         this.performSortingUsers();
                }
            },
            edituserrec:function(curruser,index) {

                this.selecteduser  = curruser;
                this.selectedindex = index;
                this.action        = 2;
                this.showModal     = true;
            },
            deleteuserrec:function(curruser,index) {

                this.selecteduser  = curruser;
                this.selectedindex = index;
                vm=this;
                if (curruser) {
                    alertify.confirm('Запрос на удаление', 'Удалить пользователя '+curruser.id+' '+curruser.fio
                            , function(){ vm.deleteEntity(curruser.id); }
                            , function(){ /*alertify.error('Cancel')*/})
                            .set('labels', {ok:'Удалить', cancel:'Нет'})
                            .set({transition:'flipy'});

<%--
                    if (confirm('Удалить пользователя '+curruser.id+' '+curruser.fio)) {
                       this.deleteEntity(curruser.id);
                    }
--%>
                }
//                this.action        = 3;
//                this.showModal     = true;
            },
            finishDelete:function() {
                this.eDelUser();
            },
            adduser:function() {
                this.selecteduser           = {};
                this.selecteduser.id        =  0;
                this.selecteduser.fio       = this.shifrpre;
                this.selectedindex          = null;
                this.action                 = 1;
                this.showModal              = true;
            },

            deleteUser: function(useritem){
                vm.userslists.$remove(useritem)
            },
            refreshstatuscode:function(id1,statuscode,dataverification) {
                var i=_.findIndex(this.userslist, function(o) { return o.id == id1; });
        //        alert("refreshstatuscode i="+i+" id="+id1+" status code="+statuscode+ "dataver="+dataverification);
                this.userslist[i].statusName=statuscode==1?"Зарегистрирован":"Не зарегистрирован";
                if (statuscode==1) {
                    this.userslist[i].dataVerification=dataverification;
                } else {
                    this.userslist[i].dataVerification="";
                }
            },
            goPage:function(pageNo) {
                 if (pageNo == this.pager.pageno)
                    return;
                 if (pageNo<1) return;
                 if (pageNo>this.pager.amntofpage) return;
                 this.pager.pageno = pageNo;
                 this.getUsersList();
            },
            goPrevPage:function() {
                if (this.pager.pageno<2) return;
                var wantedPage;
                wantedPage = this.pager.pageno-1;
                this.goPage(wantedPage);
            },
            goNextPage:function() {
                if (this.pager.pageno>=this.pager.amntofpage) return;
                var wantedPage;
                wantedPage = this.pager.pageno+1;
                this.goPage(wantedPage);
            },
            sortuser:function(s) {
                //if s == current sort, reverse
//                console.log('sortuser s='+s+' this.currentSort='+this.currentSort+' this.currentSortDir='+this.currentSortDir);
                if(s === this.currentSort) {
                    this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
                }
                this.currentSort = s;
                if (this.currentSort=='FIO' && this.currentSortDir=='asc')
                   this.currentSortCode=1;
                else
                if (this.currentSort=='FIO' && this.currentSortDir=='desc')
                    this.currentSortCode=11;
                else
                if (this.currentSort=='dolg' && this.currentSortDir=='asc')
                    this.currentSortCode=2;
                else
                if (this.currentSort=='dolg' && this.currentSortDir=='desc')
                    this.currentSortCode=12;
                else
                if (this.currentSort=='status' && this.currentSortDir=='asc')
                    this.currentSortCode=3;
                else
                if (this.currentSort=='status' && this.currentSortDir=='desc')
                    this.currentSortCode=13;
                else
                if (this.currentSort=='podr' && this.currentSortDir=='asc')
                    this.currentSortCode=4;
                else
                if (this.currentSort=='podr' && this.currentSortDir=='desc')
                    this.currentSortCode=14;
                else
                if (currentSortDir=='asc')
                    this.currentSortCode=0;
                else
                    this.currentSortCode=10;

                this.getUsersList();
            },
            sortFIO:function() {
                if (this.sorting.fioasc) {
                    this.sorting.fioasc  = false;
                    this.sorting.fiodesc = true;
//                    console.log("sortFIO: case 1");
                }
                else
                if (this.sorting.fiodesc) {
                    this.sorting.fioasc  = true;
                    this.sorting.fiodesc = false;
//                    console.log("sortFIO: case 2");
                }
                else
                if (!(this.sorting.fioasc || this.sorting.fiodesc)) {
                    this.sorting.fioasc  = true;
                    this.sorting.fiodesc = false;
//                    console.log("sortFIO: case 3");
                }
                this.getUsersList();
            },
            getPreName:function() {
                var uri3     = this.$root.rootPath+"/util/univ";
                var uri      = uri3+"/"+this.shifrpre;
                var vt       = this;
                var finished = false;
                axios.get(uri, {
                })
                        .then(function (response) {
                            finished=true;
                            vt.prename=response.data.name;
                        })

                        .catch(function (error) {
                             finished=true;
                        });
                var i;
                while (finished) {
                    i=1;
                }
            },
            performCompareUsers: function(user1,user2) {
                var retVal=0;
                retVal=user1.fio.toUpperCase().trim().localeCompare(user2.fio.toUpperCase().trim());
                return retVal;
            },
            performSortingUsers:function() {
               if (this.userslist)
               if (Array.isArray(this.userslist))
                  this.userslist.sort(this.performCompareUsers)
            },
            setTableFooter:function() {
<%--
                if (this.pager.pageno<4)
                    this.pager.startvisiblepage=1;
                else
                    this.pager.startvisiblepage=this.pager.pageno-2;
                if (this.pager.amntofpage>4)
                    this.pager.amntofvisiblepages=5;
                else
                    this.pager.amntofvisiblepages=this.pager.amntofpage;
                if (this.startvisiblepage>1) this.pager.leftangle=true;
--%>
//                console.log('setTableFooter: this.pager.amntofpage='+this.pager.amntofpage);
                this.pager.pages=[];
                this.pager.leftangle.visible   = false;
                this.pager.leftangle.disabled  = true;
                this.pager.rightangle.visible  = false;
                this.pager.rightangle.disabled = true;
                if (this.pager.amntofpage<2) {
                    return;
                }
                if (this.pager.amntofpage<6) {
//                    console.log('setTableFooter1: this.pager.amntofpage='+this.pager.amntofpage);
                    this.pager.leftangle.visible=true;
                    this.pager.leftangle.disabled=false;
                    this.pager.rightangle.visible=true;
                    this.pager.rightangle.disabled=false;
                    for (var i=0;i<this.pager.amntofpage;i++) {
                        this.pager.pages.push({page:i+1,disabled:i+1==this.pager.pageno?true:false});
                    }
//                    console.log('setTableFooter2: ');
//                    for (var i=0;i<this.pager.pages.length;i++)
//                        console.log(JSON.stringify(this.pager.pages[i]));
                    return
                }
                if (this.pager.pageno<4) {
                    this.pager.startvisiblepage=1;
                } else {
                    this.pager.startvisiblepage=this.pager.pageno-2;
                }
                if (this.pager.amntofpage-this.pager.pageno<3) {
                    this.pager.amntofvisiblepages=this.pager.amntofpage;
                } else {
                    this.pager.amntofvisiblepages=5;
                }
                for (var i=0;i<this.pager.amntofvisiblepages;i++) {
                    this.pager.pages.push({page:this.pager.startvisiblepage+i,disabled:this.pager.startvisiblepage+i==this.pager.pageno?true:false});
                }
                return;
            },
            getUsersList:function() {
                var uri3     = this.$root.rootPath+"/util/users";

                var order    = this.currentSortCode;

                var uri      = uri3+"/"+this.shifrpre+"/"+this.pager.pageno+"/"+this.pager.pagesize+"/"+order;
                var vm       = this;
                var finished = false;
                vm.isLoading=true;
                axios.get(uri, {
                })
                        .then(function (response) {
                            finished=true;
                            vm.userslist=response.data;
//                            console.log('userslist='+JSON.stringify(vm.userslist));
                            if ((vm.userslist) || (vm.userslist.length>0))
                               for (var i=0;i<vm.userslist;i++) {
                                   var b=vm.userslist[i].canbedeleted=='true'?true:false;
                                   vm.userslist[i].canbedeleted=b;
                               }
                            vm.getAmntOfPages();
                            vm.isLoading=false;
//                            alert('shifrpre='+vt.shifrpre+' amnt of user '+vt.userslist.length);
                        })

                        .catch(function (error) {
                         alert('error reading userslist='+error);
                         vm.isLoading=false;
                          finished=true;
                });

            },

            getAmntOfPages:function() {
                var uri3     = this.$root.rootPath+"/util/apusers";
                var uri      = uri3+"/"+this.shifrpre;
                var vm       = this;
                var finished = false;
                axios.get(uri, {
                })
                        .then(function (response) {
                            finished=true;
                            vm.pager.amntofpage=response.data;
                            var ps=Math.floor (vm.pager.amntofpage / vm.pager.pagesize);
                            if ((vm.pager.amntofpage % vm.pager.pagesize)>0) {
                                ps++;
                            }
                            vm.pager.amntofpage=ps;
                            vm.setTableFooter();
//                            alert('shifrpre='+vt.shifrpre+' amnt of user '+vt.userslist.length);
                        })

                        .catch(function (error) {
                    alert('error reading userslist='+error);
                    finished=true;
                });

            },
            deleteEntity: function(id) {
                var uri3=this.$root.rootPath+"/util/user/del/"+id;
                var uri=uri3;
                var vm=this;
                axios.post(uri, {
                },{})
                        .then(function (response) {
                            var rec=JSON.stringify(response.data);
                            var rrec=JSON.parse(rec);
                            if (rrec[0].shifr=='Ok') {
                                vm.$nextTick(function () {
                                   vm.finishDelete();
                                   vm.$notify("alert", "Запись удалена.");
                                });
                            }
                            else {
                                vm.$notify("alert", "Ошибка удаления пользователя.");
                            }
                        })

                        .catch(function (error) {
                    alert("error deleteEntity="+error);
                });
            }

        },
//        watch: {
//          currentshifrpepre:function(val) {
//              this.shifrpre = val;
//              this.getPreName();
//          }
//        },
        mounted:function () {
            this.$nextTick(function () {
                $('.ui.sidebar').sidebar('toggle');
                $("#startcard").hide();
            });
        },
        beforeRouteUpdate:function(to, from, next) {
            this.$nextTick(function () {
              this.shifrpre = this.$route.params.id;
              this.getPreName();
              this.getUsersList();
              this.performSortingUsers();
//              this.getBreadCrumbFromServer();
            });
            next();
         },
        created: function() {
            this.shifrpre = this.$route.params.id;

            this.getPreName();
            this.getUsersList();
            this.performSortingUsers();
//            this.getBreadCrumbFromServer();
        },
        beforeCreate:function() {
            var l=$( "#modaluser" ).length;
//            console.log('usertable: beforeCreate amnt of modal univ='+l);
            if (l>0) {
                $( "#modaluser" ).remove();
            }
        }

    });

</script>