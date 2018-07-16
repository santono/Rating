<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
    var appTop = new Vue({
        data: {
            message   : "",
            user      : null,
            isLoading : true,
            isExiting : false
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
            }
        },
        methods:{
           markExiting:function() {
               this.isExiting=true;
               return true;
           }
        },
        mounted: function() {
            if (!this.user)
                if (window.user) {
                    this.user=window.user;
                };
        }
    }).$mount('#appTop');

</script>