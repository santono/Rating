<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="Список видов покрытий автодорог." var="cpt" />

<div class="panel panel-default">

    <div class="panel-heading text-center"><c:out value="${cpt}"/></div>
    <%--
     <div class="panel-body">
         <p>...</p>
     </div>
    --%>
    <!-- Table -->
    <table class="table table-condensed table-striped table-hover">
        <thead>
        <tr><th></th><th>Код</th><th>Название</th><th>Стоимость м.кв.</th></tr>
        </thead>
        <tbody>
        <c:forEach items="${wcList}" var="wсItem">
            <tr>
                <td> <nobr>
                    <button type="button" class="btn btn-primary btn-xs" data-toggle="tooltip" data-placement="top" title="Редактирование записи" onclick="editRec(${wсItem.id});"><span class="glyphicon glyphicon-pencil"></span></button>
                    <button type="button" class="btn btn-primary btn-xs delrecbutton" data-toggle="tooltip" data-placement="top" title="Удалить запись" idRec="${wсItem.id}" nameRec="${wсItem.name}" ><span class="glyphicon glyphicon-remove"></span></button>
                </nobr>
                </td>
                <td>
                    <c:out value="${wсItem.id}" />
                </td>
                <td>
                    <c:out value="${wсItem.name}" />
                </td>
                <td>
                    <c:out value="${wсItem.price}" />
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
