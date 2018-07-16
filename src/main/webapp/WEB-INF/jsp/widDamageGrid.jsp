<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="panel panel-default">

    <div class="panel-heading text-center">Список видов повреждений.</div>
    <%--
     <div class="panel-body">
         <p>...</p>
     </div>
    --%>
    <!-- Table -->
    <table class="table table-condensed table-striped table-hover">
        <thead>
        <tr><th></th><th>Код</th><th>Название</th></tr>
        </thead>
        <tbody>
        <c:forEach items="${wdList}" var="wl">
            <tr>
                <td> <nobr>
                    <button type="button" class="btn btn-primary btn-xs" data-toggle="tooltip" data-placement="top" title="Редактирование записи" onclick="editRec(${wl.id});"><span class="glyphicon glyphicon-pencil"></span></button>
                    <button type="button" class="btn btn-primary btn-xs delrecbutton" data-toggle="tooltip" data-placement="top" title="Удалить запись" idRec="${wl.id}" nameRec="${wl.name}" ><span class="glyphicon glyphicon-remove"></span></button>
                    </nobr>
                </td>
                <td>
                    <c:out value="${wl.id}" />
                </td>
                <td>
                    <c:out value="${wl.name}" />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
