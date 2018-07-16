<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="actionUrl" value="groups/save"/>
<div class="container-fluid">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span
                        class="sr-only">Закрыть</span></button>
            </div>
            <form:form role="form" class="form-horizontal" acceptCharset="UTF-8" id="groupForm" commandName="group"
                       method="post" action="${actionUrl }">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">Название</label>

                        <div class="col-sm-2">
                            <form:input class="form-control" path="name" id="name" title="Название группы"/>
                        </div>
                        <label for="nmbofstu" class="col-sm-2 control-label">Кол-во</label>

                        <div class="col-sm-2">
                            <form:input class="form-control" path="nmbofstu" id="nmbofstu"
                                        title="Количество студентов в группе"/>
                        </div>
                        <label for="yearPri" class="col-sm-2 control-label">Год приема</label>

                        <div class="col-sm-2">
                            <form:input class="form-control" path="yearpri" id="yearPri" title="Год приема"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="okrSelect" class="col-sm-2 control-label">Уровень подготовки</label>

                        <div class="col-sm-2">
                            <form:select class="form-control" path="okr" items="${okrList}" itemValue="id"
                                         itemLabel="name" id="okrSelect" title="Образовательный уровнь"/>
                        </div>
                        <label for="formaSelect" class="col-sm-1 control-label">Форма обучения</label>

                        <div class="col-sm-2">
                            <form:select class="form-control" path="shifrf" items="${formaList}" itemValue="id"
                                         itemLabel="name" id="formaSelect" title="Форма обучения"/>
                        </div>
                        <label for="kurs" class="col-sm-1 control-label">Курс</label>

                        <div class="col-sm-1">
                            <form:input class="form-control" path="kurs" id="kurs" title="Курс"/>
                        </div>
                        <label for="nmbOfStawok" class="col-sm-1 control-label">Ставок</label>

                        <div class="col-sm-2">
                            <form:input class="form-control" path="nmbOfStawok" id="nmbOfStawok"
                                        title="Количество ставок"/>
                        </div>
                            <%--
                                                <div class="col-sm-2">
                                                     <form:errors path="okr" cssClass="error" class="control-label" />
                                                </div>
                                                <div class="col-sm-2">
                                                     <form:errors path="shifrf" cssClass="error" class="control-label" />
                                                </div>
                            --%>
                    </div>
                        <%--
                                        <div class="form-group">
                                             <label for="kafSelect" class="col-sm-2 control-label">Кафедра</label>
                                             <div class="col-sm-8">
                                                  <form:select class="form-control" path="shifrkaf" items="${kafList}" itemValue="id" itemLabel="name" id="kafSelect" title="Кафедра" />
                                             </div>
                                             <div class="col-sm-2">
                                                  <form:errors path="shifrkaf" cssClass="error" class="control-label" />
                                             </div>
                                        </div>
                        --%>

                    <div class="form-group">
                        <label for="kafSelect" class="col-sm-2 control-label">Кафедры</label>

                        <div class="col-sm-8">
                                <%--           <form:select class="form-control" path="shifrkaf" items="${kafList}" itemValue="id" itemLabel="name" id="kafSelect" title="Кафедра, которая читает дисциплину" />  --%>
                            <form:select class="form-control" path="shifrykaf" id="kafSelect"
                                         title="Кафедры, которыея читают дисциплину" multiple="true" size="10">
                                <%--    <form:option value="0"> --Укажите--</form:option>  --%>
                                <c:forEach items="${facultetList}" var="fac">
                                    <optgroup label='"${fac.name}"'>
                                        <c:forEach items="${kafList}" var="item">
                                            <c:if test="${item.shifrIdFac==fac.id}">
                                                <form:option value="${item.id}">
                                                    <c:out value="${item.name}"/>
                                                </form:option>
                                            </c:if>
                                        </c:forEach>
                                    </optgroup>
                                </c:forEach>
                            </form:select>
                        </div>
                        <div class="col-sm-2">
                            <form:errors path="shifrykaf" cssClass="error" class="control-label"/>
                        </div>

                    </div>

                    <div class="form-group">
                        <label for="galuzSelect" class="col-sm-2 control-label">Укрупненное направление
                            подготовки</label>

                        <div class="col-sm-8">
                            <form:select class="form-control" path="shifrgaluz" id="galuzSelect"
                                         title="Укрупненное направление подготовки">
                                <form:option value="0"> --Укажите--</form:option>
                                <c:forEach items="${galuzList}" var="item">
                                    <form:option value="${item.id}">
                                        <c:out value="${item.kod} ${item.name}"/>
                                    </form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                        <div class="col-sm-2">
                            <form:errors path="shifrgaluz" cssClass="error" class="control-label"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="naprSelect" class="col-sm-2 control-label">Направление подготовки</label>

                        <div class="col-sm-8">
                            <form:select class="form-control" path="shifrnapr" id="naprSelect"
                                         title="Направление подготовки">
                                <form:option value="0"> --Укажите--</form:option>
                                <c:forEach items="${naprList}" var="item">
                                    <form:option value="${item.id}">
                                        <c:out value="${item.kod} ${item.name}"/>
                                    </form:option>
                                </c:forEach>
                            </form:select>
                        </div>
                        <div class="col-sm-2">
                            <form:errors path="shifrnapr" cssClass="error" class="control-label"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="speciSelect" class="col-sm-2 control-label">Специализация</label>

                        <div class="col-sm-8">
                            <form:select class="form-control" path="shifrspeci" id="speciSelect" title="Специализация">
                                <form:option value="0" label="-- Нет специализации --"/>
                                <form:options items="${speciList}" itemValue="id" itemLabel="name"/>
                            </form:select>
                        </div>
                        <div class="col-sm-2">
                            <form:errors path="shifrspeci" cssClass="error" class="control-label"/>
                        </div>
                    </div>


                </div>
                <%-- end of modal body --%>
                <div class="modal-footer">
                    <!--    <div class="col-sm-offset-5 col-sm-4"> -->
                    <form:hidden path="id"/>
                    <button type="submit" class="btn btn-primary">Сохранить</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                    <!--    </div>    -->
                </div>
            </form:form>
        </div>
        <!-- end of modal content-->
    </div>
    <!-- end of modal dialog-->
</div>
