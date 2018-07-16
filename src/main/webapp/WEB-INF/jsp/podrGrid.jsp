<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="panel panel-default">

    <div class="panel-heading text-center">Список подразделений.</div>
    <%--
     <div class="panel-body">
         <p>...</p>
     </div>
    --%>
    <!-- Table -->
    <table class="table table-condensed table-striped table-hover">
        <thead>
        <tr><th></th><th>Код</th><th>Название</th><th>Сокр.название</th></tr>
        </thead>
        <tbody>
        <c:forEach items="${podrList}" var="pl">
            <tr>
                <td> <nobr>
                    <button type="button" class="btn btn-primary btn-xs" data-toggle="tooltip" data-placement="top" title="Редактирование записи" onclick="editRec(${pl.id});"><span class="glyphicon glyphicon-pencil"></span></button>
                    <button type="button" class="btn btn-primary btn-xs delrecbutton" data-toggle="tooltip" data-placement="top" title="Удалить запись" idRec="${pl.id}" nameRec="${pl.name}" ><span class="glyphicon glyphicon-remove"></span></button>
                    </nobr>
                </td>
                <td>
                    <c:out value="${pl.id}" />
                </td>
                <td>
                    <c:out value="${pl.name}" />
                </td>
                <td>
                    <c:out value="${pl.shortName}" />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
