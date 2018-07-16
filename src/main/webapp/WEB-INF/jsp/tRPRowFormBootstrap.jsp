<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="actionUrl" value="rp/save"/>
<div class="container-fluid">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Закрыть</span>
                </button>
            </div>
            <c:if test="${not empty tDet}">
                <form:form role="form" class="form-horizontal" acceptCharset="UTF-8" id="recForm" commandName="tDet"
                           method="post" action="${actionUrl }">
                    <div class="modal-body">
                        <div class="form-group">
                            <label for="lineno" class="col-sm-2 control-label">Номер </label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="lineno" id="lineno" title="Номер по порядку"
                                            type="number" min="0" max="25" step="1"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="xx" class="col-sm-2 control-label">Широта</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="xx" id="xx" title="Географическая широта"
                                            type="number" min="0" max="360" step="0.00000000000000001"/>
                            </div>
                            <label for="yy" class="col-sm-2 control-label">Долгота</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="yy" id="yy" title="Географическая долгота"
                                            type="number" min="0" max="360" step="0.0000000000000001"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="balloon" class="col-sm-2 control-label">Название</label>

                            <div class="col-sm-10">
                                <form:input class="form-control" path="balloon" id="balloon" title="Название"/>
                            </div>
                        </div>
                    </div>
                    <%-- end of modal body --%>
                    <div class="modal-footer">
                        <!--    <div class="col-sm-offset-5 col-sm-4"> -->
                        <button type="submit" class="btn btn-primary" id="butsubmit">Сохранить</button>
                        <button type="button" class="btn btn-primary" id="butcoord">Запросить коорлинаты</button>
                        <button type="button" class="btn btn-primary" id="decodecoord">Декодировать коорлинаты</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        <!--    </div>    -->
                    </div>
                    <form:hidden path="idroad" value="${tDet.idroad}"/>
                    <form:hidden path="id" value="${tDet.id}"/>

                </form:form>
            </c:if>
        </div>
        <!-- end of modal content-->
    </div>
    <!-- end of modal dialog-->
</div>
