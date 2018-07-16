<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="container-fluid">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Закрыть</span></button>
            </div>
            <c:if test="${gksList.size()<=0}">
                <p class="bg-warning">Расчет по группе отсутствует</p>
            </c:if>
            <c:if test="${gksList.size()>0}">
                <p class="bg-warning">Расчет по группе <c:out value="${group.name}"/> (<c:out value="${group.kurs}"/>
                    курс) за <c:out value="${yearza}"/>-<c:out value="${yearza+1}"/> уч.год (Cтудентов - <c:out
                            value="${group.nmbofstu}"/>. Ставок - <fmt:formatNumber value="${group.nmbOfStawok}"
                                                                                    pattern="#0.00"/>)</p>
                <c:set var="lineno" value="0"/>
                <table class="table table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th>№</th>
                        <th>Кафедра</th>
                        <th>Дисциплина</th>
                        <th>%</th>
                        <th>Ставок</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="kl" items="${gksList}">
                        <tr>
                            <c:set var="lineno" value="${lineno+1}"/>
                            <td>
                                <c:out value="${lineno}"/>
                            </td>
                            <td>
                                <c:out value="${kl.namekaf}"/>
                            </td>
                            <td>
                                <c:out value="${kl.named}"/>
                            </td>
                            <td class="text-right">
                                <fmt:formatNumber value="${kl.procclk}" pattern="#0.00"/>
                            </td>
                            <td class="text-right">
                                <fmt:formatNumber value="${kl.nmbofstaforkaf}" pattern="#0.00"/>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
        <!-- end of modal content-->
    </div>
    <!-- end of modal dialog-->
</div>
