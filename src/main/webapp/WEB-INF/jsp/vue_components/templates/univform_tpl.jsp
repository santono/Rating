<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-univ-form">
    <div class="ui first coupled modal" v-show="show" id="modaluniv">
        <i class="close icon"></i>
        <div class="header">
            Реквизиты записи.
        </div>
        <div class="content" id="modalcontent">
            <form class="ui form" id="formuniv">
                <div class="field">
                    <label>Код</label>
<%--
                    <input v-model="editingUniv.id"  name="id" placeholder="код" type="number" />
--%>
                    <p>{{editingUniv.id}}</p>
                </div>
                <div class="field">
                    <label>Название</label>
                    <input v-model="editingUniv.name"  name="name" placeholder="название" type="text" />
                </div>
                <div class="field">
                    <label>Сокращенное название</label>
                    <input v-model="editingUniv.shortName"  name="shortname" placeholder="сокращенное название" type="text" />
                </div>
                <div class="ui error message">
                    <ul v-show="hasErrors">
                        <li v-for="error in errlist">{{error.message}}</li>
                    </ul>
                </div>
            </form>
        </div>
        <div class="actions">
            <div class="ui positive button" v-show="cansave">Сохранить</div>
            <div class="ui black deny button">Выход</div>
        </div>
    </div>
</template>
