<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-univ-row">
    <tr>
        <td>
            {{univ.id}}
        </td>
        <td>
            <span>{{univ.name}}</span>
        </td>
        <td>
            <span>{{univ.shortName}}</span>
        </td>
        <td>
            <div class="ui icon buttons">
                <button v-on:click="edituniv" class="ui button"><i class="edit icon"></i></button>
                <button v-on:click="deleteuniv" class="ui button" v-if="univ.canbedeleted"><i class="remove icon"></i></button>
                <button v-on:click="browsepodrdet" class="ui button" v-if="univ.level<3"><i class="align justify icon"></i></button>
<%--
                <button v-on:click="browsepodrdet" class="ui button" v-else="!univ.canbedeleted"><i class="align justify icon"></i></button>
--%>
            </div>
        </td>
    </tr>
</template>
<script>
var univrow=Vue.extend({
    template: '#template-univ-row',
    props: ['univ','index'],
    methods: {
        edituniv:function() {
            this.$emit('editu',this.univ,this.index);
        },
        browsepodrdet:function() {
            var path='/univ/'+this.univ.id;

            this.$router.push(path);
        },
        deleteuniv: function(univ){
            this.$emit('deltu',this.univ,this.index);
        }
    }
});
</script>

