<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-ntr-table">
    <div>
        <div class="ui breadcrumb" id="breadcrumbh">

        </div>
        <h3>Перечень научно-технических работ</h3>
		<p v-if="isadmin">{{namepre}}</p>
        <table class="ui selectable celled striped very compact table">
            <thead>
            <tr>
                <th v-on:click.prevent="sortntr('id')"># <i class="chevron up icon" v-if="currentSort=='id' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='id' && currentSortDir=='desc'"></i></th>
                <th v-if="isadmin" v-on:click.prevent="sortntr('auth')">Авторы <i class="chevron up icon" v-if="currentSort=='auth' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='auth' && currentSortDir=='desc'"></i></th>
                <th v-if="isnpr"   v-on:click.prevent="sortntr('coauth')">Соавторы <i class="chevron up icon" v-if="currentSort=='coauth' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='coauth' && currentSortDir=='desc'"></i></th>
                <th v-on:click.prevent="sortntr('name')">Название <i class="chevron up icon" v-if="currentSort=='name' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='name' && currentSortDir=='desc'"></i></th>
<%--            <th>Издание</th>       --%>
                <th v-if="isadmin" v-on:click.prevent="sortntr('predp')">Предпр. <i class="chevron up icon" v-if="currentSort=='predp' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='predp' && currentSortDir=='desc'"></i></th>
                <th>Х-ка</th>
                <th v-on:click.prevent="sortntr('approve')">Пдтв. <i class="chevron up icon" v-if="currentSort=='approve' && currentSortDir=='asc'"></i><i class="chevron down icon" v-if="currentSort=='approve' && currentSortDir=='desc'"></i></th>
                <th>Док.</th>
                <th>
                    <div v-on:click="addrec" class="ui icon button" data-tooltip="Добавить работу" data-position="bottom right">
                        <i class="add icon"></i>
                    </div>
                </th>
            </tr>
            </thead>
            <tbody id="idtbodyntr">

            <tr is="ntrrow" v-for="(item,index) in ntrlist"
                            v-bind:rowrec       = "item"
                            :index              = "index"
                            :key                = "item.id"
                            v-on:editrec        = "editrec"
                            v-on:delrec         = "deleterec"
                            v-on:approvedrec    = "approvedrec"
                            v-on:disapprovedrec = "disapprovedrec" >
            </tr>

            </tbody>
        </table>

        <ntrform v-bind:receivedrec    = 'selectedrec'
                 v-bind:show.sync      = "showModal"
                 v-bind:receivedaction = "action"
                 v-on:eshowmodal       = "setEShowModal"
                 v-on:edelrec          = "eDelRec" >

        </ntrform>
    </div>
</template>
<script>
    var ntrtable=Vue.extend({
        template: '#template-ntr-table',

        data:function() {
            return {ntrlist       :  null,
                    selectedrec   :    {},
                    selectedindex :    -1,
                    showModal     : false,
					namepre       :    "", 
					shifrpre      :     0,
                    shifrnpr      :     0,
                    action        :     0,
                    isnpr         :     false,
                    isadmin       :     false,
                    currentSort     : 'auth'  ,
                    currentSortDir  : 'asc'  ,
                    currentSortCode : 1
            };

        },
        components: {
            'ntrrow' : ntrrow  ,
            'ntrform': ntrform
        },

        methods: {
            setEShowModal : function(newVal,newRec) {
//                console.log('ntrtable: inside setEShowModal');
                this.showModal=newVal;
                if (newRec) {
                    if (!_.isEqual(this.selectedrec,newRec)) {
                        this.selectedrec=_.clone(newRec);
                        if (this.isnpr) {
                            this.getNtrList(1,this.shifrnpr);
                        }
                        else {
                            this.getNtrList(0,this.shifrpre);
                        }

//                        console.log(JSON.stringify(this.selectedrec));
                    }
//                    if (this.selectedindex>=0)
//                        this.getAuthorListForNtr(this.selectedrec.id);
//                    if (this.action==1)
//                        this.eAddRec(newRec);

                }
            },
            finishEShowModalForPokaz:function(value) {
//                console.log('amntOfImages='+this.selectedrec.amntOfImages+" amntOfDocs="+this.selectedrec.amntOfDocs);
                this.selectedrec.authors=value;
                this.getPokazListForNtr(this.selectedrec.id);
            },
            finishEShowModal:function(value) {
//                console.log('amntOfImages='+this.selectedrec.amntOfImages+" amntOfDocs="+this.selectedrec.amntOfDocs);
                this.selectedrec.pokaz=value;
//                this.selectedrec.amntOfImages=this.countImages();
                var vm=this;
                this.$nextTick(function () {
                    if (vm.selectedindex>=0)
                        Vue.set(vm.ntrlist,vm.selectedindex,vm.selectedrec);
                });
            },
<%--
            setEShowModalFinish : function(value) {
                this.$nextTick(function () {
                    this.selectedrec.authors=value;
                    if (this.selectedindex>=0)
                         Vue.set(this.ntrlist,this.selectedindex,this.selectedrec);
                    });
            },
--%>
            eAddRec:function(newRec) {
                if (newRec) {
                    this.ntrlist.push(newRec);
                    this.performSortingNtr();
                }
            },
            eDelRec:function(newRec) {
//                if (this.selectedindex!=undefined)
//                    if (this.selectedindex>=0) {
//                    this.ntrlist.splice(this.selectedindex,1);
//                    this.performSortingNtr();
//                }
                if (this.isnpr) {
                    this.getNtrList(1,this.shifrnpr);
                }
                else {
                    this.getNtrList(0,this.shifrpre);
                }

            },
            editrec:function(currrec,index) {
//                console.log('ntrtable: editrec ='+JSON.stringify(currrec));
                this.selectedrec   = null;
                this.selectedrec   = currrec;
                this.selectedindex = index;
                this.action        = 2;
                this.showModal     = true;
            },
            deleterec:function(currrec,index) {

                this.selectedrec   = currrec;
                this.selectedindex = index;
                vm=this;
                if (currrec) {
                    alertify.confirm('Запрос на удаление', 'Удалить запись НТР '+currrec.id
                            , function(){ vm.deleteEntity(currrec.id); }
                            , function(){ /*alertify.error('Cancel')*/})
                            .set('labels', {ok:'Удалить', cancel:'Нет'})
                            .set({transition:'flipy'});
                }
            },
            addrec:function() {
                this.selectedrec            = {};
                this.selectedrec.id         =  0;
                this.selectedrec.authors    = "";
                this.selectedrec.name       = "";
                this.selectedrec.parametry  = "";
                this.selectedrec.amntOfImages = 0;
                this.selectedrec.amntOfDocs   = 0;
//                this.selectedrec.ownerid    = 0;
                this.selectedindex          = null;
                this.action                 = 1;
                this.showModal              = true;
            },

            deleteRec: function(item){
                if (this.isnpr) {
                    this.getNtrList(1,this.shifrnpr);
                }
                else {
                    this.getNtrList(0,this.shifrpre);
                }

            },
            finishDelete:function() {
                this.deleteRec();
            },

            approvedrec:function(currrec,index,dataapproved) {
                this.selectedrec  = currrec;
                this.selectedindex = index;
                this.selectedrec.approved='Подтверждено';
                this.selectedrec.dataapproved=dataapproved;
//                console.log('inside ntrtable. approvedrec='+JSON.stringify(this.selectedrec));
                if (this.selectedindex>=0) {
//                    Vue.set(this.ntrlist,this.selectedindex,this.selectedrec);
                   var vm=this;
                   this.$nextTick(function () {
                       if (vm.selectedindex>=0)
                          vm.ntrlist[vm.selectedindex]=this.selectedrec;
   //                       Vue.set(vm.ntrlist,vm.selectedindex,vm.selectedrec);
                   });
                }

            },
            disapprovedrec:function(currrec,index) {
//                console.log('inside ntrtable. disapprovedrec='+JSON.stringify(currrec));
                this.selectedrec  = currrec;
                this.selectedindex = index;
                this.selectedrec.approved='';
                this.selectedrec.dataapproved='';
                if (this.selectedindex>=0)
                    Vue.set(this.ntrlist,this.selectedindex,this.selectedrec);
            },


            performCompareNtr: function(ntr1,ntr2) {
                var retVal=0;
                if (ntr1.lineno>ntr2.lineno) retVal=1;
                else 
                if (ntr1.lineno<ntr2.lineno) retVal=-1;
                return retVal;
            },
            performSortingNtr:function() {
               if (this.ntrlist)
               if (Array.isArray(this.ntrlist))
                  this.ntrlist.sort(this.performCompareNtr)
            },
            getNtrList:function(mode,shifritem) {
                var uri2=this.$root.rootPath+"/util";
                var uri3;
                if (!mode || mode==0) {
                    uri3     = uri2+"/ntrs";
                }
                else {
                    uri3     = uri2+"/ntrsnpr";
                }
                var uri4     = uri3+"/"+shifritem;
                var uri      = uri4;
                var vm       = this;
                var finished = false;
                vm.$nextTick(function () {
                    window.app.isLoadingContent=true;
                });

                axios.get(uri, {
                })
                        .then(function (response) {
                            finished=true;
                            vm.ntrlist=response.data;
							vm.performSortingNtr();
                            vm.$nextTick(function () {
                               window.app.isLoadingContent=false;
                            });
//                            for(var i=0;i<vt.ntrlist.length;i++)
//                               if (vt.ntrlist[i].id==4)
//                                  console.log('ntr 4='+JSON.stringify(vt.ntrlist[i]));
//                            alert('shifrpre='+vt.shifrpre+' amnt of user '+vt.userslist.length);
                        })

                        .catch(function (error) {
                            vm.$nextTick(function () {
                               window.app.isLoadingContent=false;
                            });
                    alertify.alert("Ошибка",'error reading ntrlist='+error);
                          finished=true;
                });

            },
            deleteEntity: function(id) {
                var uri=this.$root.rootPath+"/util/ntr/del/"+id;
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
                                alertify.alert("Ошибка", "Ошибка удаления НТР.");
                            }
                        })

                        .catch(function (error) {
                            alertify.alert("Ошибка", "error deleteEntity="+error);
                });
            },
            getPreName:function() {
                var uri = this.$root.rootPath+"/util/univ/"+this.shifrpre;
                var vm       = this;
                var finished = false;
                axios.get(uri, {
                })
                        .then(function (response) {
                            finished=true;
                            vm.namepre=response.data.name;
                        })

                        .catch(function (error) {
                    finished=true;
                });
                var i;
                while (finished) {
                    i=1;
                }
            },
            getAuthorListForNtr:function(id) {
                if (
                    (id==undefined) ||
                    (id<1)
                   ) {
                   this.finishEShowModalForPokaz("");
                   return;
                }
                var uri         = this.$root.rootPath+"/util/authorntr/"+id;
                var vm          = this;
                var authorsname = "";
                axios.get(uri, {
                })
                        .then(function (response) {
                            authorsname=response.data[0].name;
                            if (authorsname=='Empty')
                                authorsname="";
//                            console.log("inside success getaulist for ntr "+authorsname);
                            vm.finishEShowModalForPokaz(authorsname);
                        })
                        .catch(function (error) {
                              alertify.error("getAuthorListForNtr error="+error)
                     });

            },
            getPokazListForNtr:function(id) {
                if (
                        (id==undefined) ||
                                (id<1)
                        ) {
                    this.finishEShowModal("");
                    return;
                }
                var uri = this.$root.rootPath+"/util/pokazsntr/"+id;
                var vm         = this;
                var pokazsname = "";
                axios.get(uri, {
                })
                        .then(function (response) {
                            pokazsname=response.data[0].name;
                            if (pokazsname=='Empty')
                                pokazsname="";
//                            console.log("inside success getaulist for ntr "+authorsname);
                            vm.finishEShowModal(pokazsname);
                        })
                        .catch(function (error) {
                    alertify.error("getPokazListForNtr error="+error)
                });

            },
            sortntr:function(s) {
                //if s == current sort, reverse
//                console.log('sortuser s='+s+' this.currentSort='+this.currentSort+' this.currentSortDir='+this.currentSortDir);
                if(s === this.currentSort) {
                    this.currentSortDir = this.currentSortDir==='asc'?'desc':'asc';
                }
                this.currentSort = s;
                if (this.currentSort=='auth' && this.currentSortDir=='asc') {
                    this.currentSortCode=1;
                    this.ntrlist=_.orderBy(this.ntrlist, 'authors', 'asc');
                }
                else
                if (this.currentSort=='auth' && this.currentSortDir=='desc') {
                    this.currentSortCode=11;
                    this.ntrlist=_.orderBy(this.ntrlist, 'authors', 'desc');
                }
                else
                if (this.currentSort=='coauth' && this.currentSortDir=='asc') {
                    this.currentSortCode=2;
                    this.ntrlist=_.orderBy(this.ntrlist, 'authors', 'asc');
                }
                else
                if (this.currentSort=='coauth' && this.currentSortDir=='desc') {
                    this.currentSortCode=12;
                    this.ntrlist=_.orderBy(this.ntrlist, 'authors', 'desc');
                }
                else
                if (this.currentSort=='name' && this.currentSortDir=='asc') {
                    this.currentSortCode=3;
                    this.ntrlist=_.orderBy(this.ntrlist, 'name', 'asc');
                }
                else
                if (this.currentSort=='name' && this.currentSortDir=='desc') {
                    this.currentSortCode=13;
                    this.ntrlist=_.orderBy(this.ntrlist, 'name', 'desc');
                }
                else
                if (this.currentSort=='predp' && this.currentSortDir=='asc') {
                    this.currentSortCode=4;
                    this.ntrlist=_.orderBy(this.ntrlist, 'namepre', 'asc');
                }
                else
                if (this.currentSort=='predp' && this.currentSortDir=='desc') {
                    this.currentSortCode=14;
                    this.ntrlist=_.orderBy(this.ntrlist, 'namepre', 'desc');
                }
                else
                if (this.currentSort=='approve' && this.currentSortDir=='asc') {
                    this.currentSortCode=5;
                    this.ntrlist=_.orderBy(this.ntrlist, 'approved', 'asc');
                }
                else
                if (this.currentSort=='approve' && this.currentSortDir=='desc') {
                    this.currentSortCode=15;
                    this.ntrlist=_.orderBy(this.ntrlist, 'approved', 'desc');
                }
                else
                if (this.currentSort=='id' && this.currentSortDir=='asc') {
                    this.currentSortCode=6;
                    this.ntrlist=_.orderBy(this.ntrlist, 'id', 'asc');
                }
                else
                if (this.currentSort=='id' && this.currentSortDir=='desc') {
                    this.currentSortCode=16;
                    this.ntrlist=_.orderBy(this.ntrlist, 'id', 'desc');
                }
                else
                if (currentSortDir=='asc')
                    this.currentSortCode=0;
                else
                    this.currentSortCode=10;

            }

        },
        mounted:function () {
            this.$nextTick(function () {
                $('.ui.sidebar').sidebar('toggle');
                $("#startcard").hide();
                var h = window.innerHeight;
                $('#idtbodyntr').css('height',h);
                $('#idtbodyntr').css('overflowY','scroll');
            });
        },
<%--
        beforeRouteUpdate:function(to, from, next) {
            var vm=this;
            this.$nextTick(function () {
                vm.shifrpre = this.$route.params.id;
                vm.isnpr    = window.user.isNPR();
                vm.isadmin  = window.user.isAdmin();
                vm.getPreName();
//                vm.getNtrList();
                console.log('--- B A D --- setted isadmin in beforeRouteUpdate');
            });
            next();
         },
--%>
        created: function() {
            this.shifrpre = this.$route.params.id;
            this.userid   = this.$route.params.iduser;
            var userRole  = this.$route.params.idrole;
            this.getPreName();
            if (userRole==1) {
                this.isnpr    = true;
                this.shifrnpr = this.userid;
                this.isadmin  = false;
            }
            else
            if (userRole==2) {
                this.isnpr    = false;
                this.shifrnpr = 0;
                this.isadmin  = true;
            }
            else {
                this.isnpr    = false;
                this.shifrnpr = 0;
                this.isadmin  = false;
            }

            if (this.isnpr) {
                this.getNtrList(1,this.shifrnpr);
            }
            else {
                this.getNtrList(0,this.shifrpre);
            }
        },
        beforeCreate:function() {
            var l=$( "#modalntr" ).length;
            if (l>0) {
                $( "#modalntr" ).remove();
            }

        }

    });

</script>