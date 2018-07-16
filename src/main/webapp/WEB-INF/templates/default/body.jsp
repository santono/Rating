<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="body">
    <h1>Учебные планы</h1>

    <table class="visibletbl">
        <tr>
            <th class="vth" width="50">Id</th>
            <th class="vth" width="150">Год приема</th>
            <th class="vth" width="150">ОКР</th>
            <th class="vth" width="150">Форма обучения</th>
            <th class="vth" width="150">Направление</th>
        </tr>
        <c:forEach items="${uPlans}" var="plan">
            <tr>
                <td class="vtd"><a href="<c:url  value="plan?id=${plan.id}" />"><c:out value="${plan.id}"/></a></td>
                <td class="vtd"><c:out value="${plan.yearPri}"/></td>
                <td class="vtd"><c:out value="${plan.okren.name}"/></td>
                <td class="vtd"><c:out value="${plan.formEducation.name}"/></td>
                <td class="vtd"><c:out value="${plan.naprav.name}"/></td>
            </tr>
        </c:forEach>
    </table>

</div>

