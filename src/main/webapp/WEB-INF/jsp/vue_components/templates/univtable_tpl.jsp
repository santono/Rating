<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-univ-table">
    <div>
        <p>{{ownername}}</p>
        <table class="ui selectable celled striped very compact table">
            <thead>
            <tr>
                <th>#</th>
                <th>Название</th>
                <th>Краткое название</th>
                <th>
                    <div v-on:click="adduniv" class="ui icon button" data-tooltip="Добавить структурное подразделение или филиал" data-position="bottom right">
                        <i class="add icon"></i>
                    </div>
<%--
                    <button class="ui button" data-content="Добавить структурное подразделение или филиал"><i class="plus icon"></i></button>
--%>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr is="univrow" v-for="(univ,index) in universities" :univ="univ" :index="index" :key="univ.id" v-on:editu="editrec" >
            </tr>
            </tbody>
        </table>

        <univform v-bind:univ='selecteduniv' v-bind:show.sync="showModal" v-on:eshowmodal="setEShowModal" >

        </univform>
    </div>
</template>
