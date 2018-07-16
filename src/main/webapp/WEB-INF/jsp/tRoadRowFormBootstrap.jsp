<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="actionUrl" value="roads/save"/>
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
                            <label for="kod" class="col-sm-2 control-label">Номер </label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="kod" id="kod" title="Номер по порядку"
                                            type="number" min="0" max="1000" step="1" required="true"/>
                            </div>
                            <label for="code" class="col-sm-2 control-label">Шифр</label>

                            <div class="col-sm-5">
                                <form:input class="form-control" path="code" id="code" title="Шифр дороги"
                                            maxlength="32"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-1 control-label">Тип</label>

                            <div class="col-sm-5">
                                <form:radiobutton path="kodtype" value="1"/>Региональные
                                <form:radiobutton path="kodtype" value="2"/>Межмуниципальные
                                    <%--
                                                                    <form:select class="form-control" path="kodtype" id="kodtype" title="тип дороги" >
                                                                        <c:set value="false" var="sel"/>
                                                                        <c:if test="${tDet.kodtype!=2}">
                                                                            <c:set value="true" var="sel"/>
                                                                        </c:if>
                                                                        <form:option value="1" selected="${sel}" >Региональная </form:option>
                                                                        <c:set value="false" var="sel"/>
                                                                        <c:if test="${tDet.kodtype==2}">
                                                                            <c:set value="true" var="sel"/>
                                                                        </c:if>
                                                                        <form:option value="2" selected="${sel}">Межмуниципальная</form:option>
                                                                    </form:select>
                                    --%>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="name" class="col-sm-2 control-label">Название</label>

                            <div class="col-sm-6">
                                <form:input class="form-control" path="name" id="name" title="Название дороги"
                                            maxlength="256" required="true"/>
                            </div>
                            <label for="dlina" class="col-sm-1 control-label">Длина</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="dlina" id="dlina"
                                            title="Длина дороги в километрах" type="number" min="0" max="360" step="0.1"
                                            required="true"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="shifridwr" class="col-sm-1 control-label">Вид дороги </label>

                            <div class="col-sm-5">
                                <form:select class="form-control" path="shifridwr" id="shifridwr" title="Дорожное предприятие">
                                    <form:option value="0" data-id="  ">-- Укажите вид дороги --</form:option>

                                    <c:if test="${not empty wrlist}">
                                        <c:forEach var="w" items="${wrlist}">
                                            <form:option value="${w.id}" data-id="${w.id}"><c:out value="${w.name}" /> </form:option>
                                        </c:forEach>
                                    </c:if>
                                </form:select>


                            </div>
                            <label for="index" class="col-sm-1 control-label">Индекс дороги</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="index" id="index"
                                            title="Индекс дороги для сводов" />
                            </div>
                        </div>
                    </div>
                    <%-- end of modal body --%>
                    <div class="modal-footer">
                        <!--    <div class="col-sm-offset-5 col-sm-4"> -->
                        <button type="submit" class="btn btn-primary" id="butsubmit">Сохранить</button>
                        <button type="button" class="btn btn-primary" id="butcoord">Запросить коорлинаты</button>
                        <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                        <!--    </div>    -->
                    </div>
                    <form:hidden path="id" value="${tDet.id}"/>

                </form:form>
            </c:if>
        </div>
        <!-- end of modal content-->
    </div>
    <!-- end of modal dialog-->
</div>
