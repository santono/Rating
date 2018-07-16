<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-pokaz-row">
    <tr>
        <td>
            {{rowrec.id}}
        </td>
        <td>
            <span>{{rowrec.name}}</span>
        </td>
        <td>
            <span>{{rowrec.shortname}}</span>
        </td>
        <td>
            <span v-show="rowrec.idowner>0">{{rowrec.idowner}}</span>
        </td>
        <td>
            <div class="ui icon buttons">
                <button v-on:click="editrec" class="ui button"><i class="edit icon"></i></button>
                <button v-on:click="deleterec" class="ui button" ><i class="remove icon"></i></button>
            </div>
        </td>
    </tr>
</template>
<script>
var pokazrow=Vue.extend({
    template: '#template-pokaz-row',
    props: ['rowrec','index'],
    methods: {
        editrec:function() {
            this.$emit('editrec',this.rowrec,this.index);
        },
        deleterec: function(rowrec){
            this.$emit('delrec',this.rowrec,this.index);
        }
    }
});
</script>

