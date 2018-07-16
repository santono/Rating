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
                <button class="ui button"><i class="remove icon"></i></button>
            </div>
        </td>
    </tr>
</template>
