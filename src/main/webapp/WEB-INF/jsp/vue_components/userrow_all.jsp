<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-user-row">
    <tr>
        <td>
            {{rowuser.id}}
        </td>
        <td>
            <span>{{rowuser.fio}}</span>
        </td>
        <td>
            <span v-bind:data-tooltip="rowuser.namePodr" data-position="top center" data-variation="basic">{{rowuser.shortNamePodr}}</span>
        </td>
        <td>
            <span>{{rowuser.nameDol}}</span>
        </td>
        <td>
            <span  v-bind:data-tooltip="rowuser.dataVerification" data-position="top right">{{rowuser.statusName}}</span>
        </td>
<%--
        <td>
            <span>{{rowuser.dataVerification}}</span>
        </td>
--%>
        <td>
            <div class="ui icon buttons">
                <button v-on:click="edituser" class="ui button"><i class="edit icon"></i></button>
                <button v-on:click="deleteuser" class="ui button" v-if="rowuser.canbedeleted"><i class="remove icon"></i></button>
            </div>
        </td>
    </tr>
</template>
<script>
var userrow=Vue.extend({
    template: '#template-user-row',
    props: ['rowuser','index'],
    methods: {
        edituser:function() {
            this.$emit('edituser',this.rowuser,this.index);
        },
        deleteuser: function(rowuser){
            this.$emit('deluser',this.rowuser,this.index);
        }
    }
});
</script>

