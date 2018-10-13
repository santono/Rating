<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-dolg-row">
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
<%--
        <td>
            <span v-show="rowrec.idowner>0">{{rowrec.idowner}}</span>
        </td>
--%>
        <td>
            <div class="ui icon buttons">
                <button v-on:click="editrec" class="ui button"><i class="edit icon"></i></button>
                <button v-on:click="deleterec" class="ui button" ><i class="remove icon"></i></button>
            </div>
        </td>
    </tr>
</template>
<script>
    var dolgrow=Vue.extend({
        template: '#template-dolg-row',
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

