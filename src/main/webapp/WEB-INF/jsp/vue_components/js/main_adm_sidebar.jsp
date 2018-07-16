<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
    var appSidebar = new Vue({
        data: {
            user      : null,
            isLoading : true
        },
        methods:{
            browsepodr:function() {
                window.app.browsepodr();
            },
            browseuserslist:function() {
                window.app.browseuserslist();
            },
            browsentrlist:function() {
                window.app.browsentrlist();
            },
            browsepokazlist:function() {
                window.app.browsepokazlist();
            },
            browsedolglist:function() {
                window.app.browsedolglist();
            },
            browsereppokaz:function() {
                window.app.browsereppokaz();
            },
            activatechgpwdform:function() {
                window.app.activatechgpwdform();
            },
            changeuserdataform:function() {
                window.app.changeuserdataform();
            }

        },
        computed:{
            isadmin:function () {
                if (!this.user) return false;
                if (!this.user.isAdmin) return false;
                if (!_.isFunction(this.user.isAdmin)) return false;
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
            },
            visiblechgpwd:function() {
                var retval=false;
                if (this.$router)
                if (this.$router.currentRoute)
                if (this.$router.currentRoute.name)
                if (this.$router.currentRoute.name=='chgpwn')
                if (this.isnpr)
                   retval=true;
                return retval;
            }
        },
        mounted: function() {
            if (!this.user)
                if (window.user) {
                    this.user=window.user;
                };
        //    $('#ddnprmenu').dropdown({direction:'downward'});
        //    console.log('l='+$('#ddnprmenu').length);
        }
    }).$mount('#appSidebar');

</script>