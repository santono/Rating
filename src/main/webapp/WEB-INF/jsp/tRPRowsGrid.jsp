<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="panel panel-default">

    <div class="panel-heading">
        <div class="pull-right"></div>
        <c:out value="${nameRoad}"/>

    </div>
    <table class="table table-condensed table-bordered">
        <thead>
        <tr>
            <th></th>
            <th class="text-center">Номер<br>п.п.</th>
            <th class="text-center">Широта</th>
            <th class="text-center">Долгота</th>
            <th class="text-center">Название</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${detlist}" var="trow">
            <tr>
                <td>
                    <nobr>
                        <button type="button" class="btn btn-primary btn-xs" onclick="editRec(${trow.id});"><span
                                class="glyphicon glyphicon-pencil"></span></button>
                        <button type="button" class="btn btn-primary btn-xs delrecbutton" idrec="${trow.id}"
                                namerec="${trow.balloon}"><span class="glyphicon glyphicon-remove"></span></button>
                    </nobr>
                </td>
                <td class="text-center">
                    <fmt:formatNumber type="number" pattern="00" maxFractionDigits="0" value="${trow.lineno}"/>
                </td>
                <td class="text-center">
                    <fmt:formatNumber type="number" pattern="#0.000000" maxFractionDigits="6" value="${trow.xx}"/>
                </td>
                <td class="text-center">
                    <fmt:formatNumber type="number" pattern="#0.000000" maxFractionDigits="6" value="${trow.yy}"/>
                </td>
                <td><c:out value="${trow.balloon}"/></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>