<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-udata-form">
    <userform v-bind:receivedrec='selecteduser' v-bind:show.sync="showModal" v-bind:receivedaction="action" v-on:eshowmodal="setEShowModal"  >
    </userform>
</template>
<script>
var udataform=Vue.extend({
    template: '#template-udata-form',
    data:function() {
        return {
            selecteduser  : {}        ,
            hasErrors     : false     ,
            errlist       : null      ,
            action        : 0         ,
            message       : null      ,
            userid        : null      ,
            showModal     : false
        }
    },
    components: {
        'userform': userform
    },

    methods: {
        setEShowModal: function() {
//            console.log('udata:inside deny');
//                this.$emit('eshowmodal',false);
            this.hasErrors = false;
            this.message   = null;
            $("#startcard").show();
            this.$router.back();
            return true;
        },
        edituserrec:function(curruser,index) {
            var vm=this;
            this.$nextTick(function () {
                vm.action        = 2;
                vm.showModal     = true;
            });
        },



        fillEmptyRec:function() {
            this.hasErrors = false;
            this.message   = null;

            this.selecteduser={
                id         :  this.userid
            }
        }
    },

    mounted:function() {
//        console.log('udataform: inside mounted');
        var vm=this;
        this.$nextTick(function () {
            $('.ui.sidebar').sidebar('toggle');
            $("#startcard").hide();
            vm.fillEmptyRec();
            vm.edituserrec();
//            vm.showModal=true;

        });

    },
    created: function() {
//        console.log('udataform: created');
        this.userid   = this.$route.params.id;
//        console.log('udataform: created. id='+this.$route.params.id);
        var vm=this;
//        console.log('udataform: created. inside not this.control');
        this.$nextTick(function () {
            $('.coupled.modal')
                    .modal({
                        allowMultiple: true
                    });
        });
//        console.log('udataform: before. fillEmptyRec.');
//        this.fillEmptyRec();
//        this.showModal=true;
    }
});
</script>