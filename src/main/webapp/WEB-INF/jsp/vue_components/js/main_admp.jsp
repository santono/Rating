<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<script>
    //  const Univ = { template: '<div>Univ</div>' }
    const Fac  = { template: '<div>Fac</div>' }
    const routes = [
        { path: '/univ/:id', component: univtable },
        { path: '/users/:id', component: userstable },
        { path: '/ntr/:id/:iduser/:idrole', component: ntrtable },
        { path: '/pokaz', component: pokaztable },
        { path: '/chgpwd/:id', component: chgpwdform,name:'chgpwd' },
        { path: '/udata/:id', component: udataform,name:'udata' },
        { path: '/dolg', component: dolgtable },
        { path: '/reppokaz', component:reportpokaztable}
    ];
    const router = new VueRouter({
        'routes':routes // short for `routes: routes`
    });

    var user={};
    function performGetRequestForUser() {
//        var url='https://api.mlab.com/api/1/databases/rating/collections/users';
<%--
    <c:url value="/util/user" var="uri2" />
        var uri="${uri2}";
--%>

        //   var vm=this;
        var uri=window.rootPath+"/util/user";
        axios.get(uri, {

        })
                .then(function (response) {
//                    user.name=response.data.name;
                    user=JSON.parse(JSON.stringify(response.data));
//                    user.id=response.data.id;
//                    user.name=response.data.fio;
//                    user.shifrPodr=response.data.shifrPodr;
//                    user.namePodr=response.data.namePodr;
//                    console.log('user='+JSON.stringify(user));
                    if ((!user.shifrPodr) || (user.shifrPodr<1)){
                        user.shifrPodr=1;
                    }
                    user.hasRole=function(roleName) {
                        var retval=false;
                        if (!roleName) {
                            return false;
                        }
                        var i;
                        if (this.roles)
                            if (Array.isArray(this.roles))
                                if (this.roles.length>0)  {
                                    for (i=0;i<this.roles.length;i++) {
                                        if (this.roles[i].name)
                                            if (this.roles[i].name.toUpperCase().indexOf(roleName)>=0) {
                                                retval=true;
                                                break;
                                            }
                                    }
                                }
                        return retval;
                    }
                    user.isAdmin=function() {
                        var retval=this.hasRole('ADMIN');
                        return retval;
                    }
                    user.isNPR=function() {
                        var retval=this.hasRole('NPR');
                        return retval;
                    }
                    user.isDataAdmin=function() {
                        var retval=this.hasRole('ADDATA');
                        return retval;
                    }
                    user.isSUP=function() {
                        var retval=this.hasRole('SUP');
                        return retval;
                    }
                    user.isDOD=function() {
                        var retval=this.hasRole('DOD');
                        return retval;
                    }
                    user.isDNID=function() {
                        var retval=this.hasRole('DNID');
                        return retval;
                    }
//                    alert(JSON.stringify(user)+' isAdmin='+user.isAdmin());
                    if (app) {
//                        app.message=user.fio;
                        app.user             = user;
                        app.isLoading        = false;
                        app.isLoadingContent = false;
                    }
                    if (appTop) {
                        appTop.message   = user.fio;
                        appTop.user      = user;
                        appTop.isLoading = false;
                        app.isExiting        = false;
                    }
                    if (appSidebar) {
                        appSidebar.user      = user;
                        appSidebar.isLoading = false;
                    }
                    getUniFacKafByPre(user.shifrshifrPodr);
                })
                .catch(function (error) {
                   user.fio=error.message;
                   if (app) {
//                      app.message=user.fio;
                        app.isLoading=false;
                        app.isLoadingContent=false;
                   }
                   if (appTop) {
                      appTop.message=user.fio;
                      appTop.isLoading=false;
                   }
                   if (appSidebar) {
                      appSidebar.user=user;
                      appSidebar.isLoading=false;
                   }
               });
    }

    function getUniFacKafByPre(shifrPre) {
         if (!shifrPre || shifrPre<1) return;
<%--
    <c:url value="/preunifackaf" var="uri2" />
        var uri="${uri2}"+'/'+shifrPre;
--%>
        var uri=window.rootPath+"/preunifackaf/"+shifrPre;        var uri=window.rootPath+"/preunifackaf/"+
        axios.get(uri, {
        })
          .then(function (response) {
            var answer=JSON.parse(JSON.stringify(response.data));
            if (answer && _.isArray(answer) && answer.length==3) {
                user.shifrUni=answer[0].shifr;
                user.shifrFac=answer[1].shifr;
                user.shifrKaf=answer[2].shifr;
            }
          })
        .catch(function (error) {
           alert('Ошибка преобразования предприятия в университет, факультет, кафедру '+error);
        });


    }


    <c:url value="/" var="uri2" />
    var rootPath="${uri2}";
    if (rootPath.length>2)
        rootPath=rootPath.substr(0,rootPath.length-1);

    var app = new Vue({
        'router':router,
        //  el: '#app',
        data: {
            message: user.fio,
            showModal: false,
            showcard:true,
            user:null,
            isLoading:true,
            isLoadingContent:false,
            currentRoute: window.location.pathname,
            rootPath:'/r',
            ntrFilter:{
                needYear   : false,
                yearFr     : 1970,
                yearTo     : 2018,
                needNPR    : false,
                shifrNpr   : 0,
                needPredp  : false,
                shifrPredp : 0,
                namePredp  : ''
            }
        },
        components: {
            'univtable'  : univtable  ,
            'usertable'  : userstable ,
            'pokaztable' : pokaztable ,
            'ntrtable'   : ntrtable   ,
            'chgpwdform' : chgpwdform ,
            'udataform'  : udataform  ,
            'dolgtable'  : dolgtable  ,
            'reportpokaztable' : reportpokaztable

        },
        computed:{
            isadmin:function () {
//                alert('1');
              if (!this.user) return false;
//                alert('2');
              if (!this.user.isAdmin) return false;
//                alert('3');
              if (!_.isFunction(this.user.isAdmin)) return false;
//                alert('4 '+this.user.isAdmin());

              return this.user.isAdmin();
            },
            isnpr:function () {
                if (!this.user) return false;
                if (!this.user.isNPR) return false;
                if (!_.isFunction(this.user.isNPR)) return false;
                return this.user.isNPR();
            },
            isdataadmin:function () {
                if (!this.user) return false;
                if (!this.user.isDataAdmin) return false;
                if (!_.isFunction(this.user.isDataAdmin)) return false;
                return this.user.isDataAdmin();
            },
            issup:function () {
                if (!this.user) return false;
                if (!this.user.isSUP) return false;
                if (!_.isFunction(this.user.isSUP)) return false;
                return this.user.isSUP();
            },
            isdod:function () {
                if (!this.user) return false;
                if (!this.user.isDOD) return false;
                if (!_.isFunction(this.user.isDOD)) return false;
                return this.user.isDOD();
            },
            isdnid:function () {
                if (!this.user) return false;
                if (!this.user.isDNID()) return false;
                if (!_.isFunction(this.user.isDNID)) return false;
                return this.user.isDNID();
            }
        },
        beforeCreate:function () {
            performGetRequestForUser();
        },
        methods: {
            browsepodr:function() {
                if (this.user)
                    if (this.user.shifrPodr) {
                        if (this.user.shifrPodr<1) {
                            this.user.shifrPodr=1;
                        }
                        this.clearDOM();
                        var path='/univ/'+this.user.shifrPodr;
                        this.$router.push(path);
                    }
            },
            browseuserslist:function() {
                if (this.user)
                    if (this.user.shifrPodr) {
                        if (this.user.shifrPodr<1) {
                            this.user.shifrPodr=1;
                        }
                        this.clearDOM();
                        var path='/users/'+this.user.shifrPodr;
                        this.$router.push(path);
                    }
            },
            browsentrlist:function() {
                if (this.user)
                    if (this.user.shifrPodr) {
                        if (this.user.shifrPodr<1) {
                            this.user.shifrPodr=1;
                        }
                        var userRole;
                        if (this.isnpr)
                            userRole=1;
                        else
                        if (this.isadmin)
                            userRole=2;
                        else
                            userRole=3;
                        this.clearDOM();
                        var path='/ntr/'+this.user.shifrPodr+'/'+this.user.id+'/'+userRole;
                        this.$router.push(path);
                    }
            },
            browsepokazlist:function() {
                if (this.user)
                    if (this.user.shifrPodr) {
                        var path='/pokaz/';
                        this.clearDOM();
                        this.$router.push(path);
                    }
            },
            browsedolglist:function() {
                if (this.user)
                    if (this.user.shifrPodr) {
                        var path='/dolg/';
                        this.clearDOM();
                        this.$router.push(path);
                    }
            },
            activatechgpwdform:function() {
                if (this.user)
                    if (this.user.id) {
                        if (this.user.id>0) {
                            var path='/chgpwd/'+this.user.id;
                            this.clearDOM();
                            this.$router.push(path);
                        }
                    }
            },
            changeuserdataform:function() {
                if (this.user)
                    if (this.user.id) {
                        if (this.user.id>0) {
                            var path='/udata/'+this.user.id;
                            this.clearDOM();
                            this.$router.push(path);
                        }
                    }
            },
            browsereppokaz:function() {
               if (this.user)
                  if (this.user.shifrPodr) {
                      var path='/reppokaz';
                      this.clearDOM();
                       this.$router.push(path);
                  }
            },
            clearDOM:function() {
                var names=['#modaluniv','#modalntr','#modalpokaz','#modaluser','#modaldolg'];
                _.forEach(names, function(value) {
                    var l;
                    l=$(value).length;
//                    console.log('clearDOM: value='+value+' l='+l);
                    if (l>0) {
                        $(value).remove();
                        l=$(value).length;
//                        console.log('clearDOM: removed value='+value+' l='+l);
                    }
                });
<%--
                var l;
                for (n in names) {
                     l=$(n).length;
                     if (l>0) {
                         $(n).remove();
                     }
                }
--%>
            }

        },
        mounted: function() {

            $('.ui.sidebar')
//                    .sidebar({
//                        // this has to be set
//                        context: '#app'
//                    })
//                    .sidebar('setting', 'transition', 'push')
//        $('.ui.sidebar').sidebar({ context: $('#app') }).sidebar('setting', 'transition', 'overlay');
        $('.ui.sidebar').sidebar('setting', 'transition', 'overlay');


//                    .sidebar('toggle')
            ;
            if (!this.user)
            if (user) {
                this.user=user;
            };
<%--
            if (window.rootPath)
               console.log('rootPath exist '+window.rootPath);
            else
                console.log('rootPath does not exist');
            <c:url value="/" var="uri2" />
            var p="${uri2}";
            if (p.length>2)
               this.rootPath=p.substr(0,p.length-1);
            else
               this.rootPath=p;
--%>
            this.rootPath=window.rootPath;
//            console.log('app rootPath='+this.rootPath);
        }

    }).$mount('#app');

</script>