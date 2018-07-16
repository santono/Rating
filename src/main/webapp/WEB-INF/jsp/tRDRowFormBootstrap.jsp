<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="actionUrl" value="rd/save"/>
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
                            <label for="idpodr" class="col-sm-1 control-label">ДРСУ </label>

                            <div class="col-sm-5">
                               <form:select class="form-control" path="idpodr" id="idpodr" title="Дорожное предприятие">
                                   <form:option value="0" data-id="  ">-- Укажите ДРСУ --</form:option>

                                <c:if test="${not empty podrlist}">
                                    <c:forEach var="p" items="${podrlist}">
                                        <form:option value="${p.id}" data-id="${p.id}"><c:out value="${p.name}" /> </form:option>
                                    </c:forEach>
                                </c:if>
                                </form:select>
							
							
                            </div>
                            <label for="posstart" class="col-sm-1 control-label">Начало</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="posstart" id="posstart" title="Начало участка"
                                            type="number" min="0" max="10000" step="0.001"/>
                            </div>
                            <label for="posend" class="col-sm-1 control-label">Конец</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="posend" id="posend" title="Конец участка"
                                            type="number" min="0" max="10000" step="0.001"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="comment" class="col-sm-2 control-label">Примечание</label>

                            <div class="col-sm-10">
                                <form:input class="form-control" path="comment" id="comment" title="Примечение"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="wsegodorog" class="col-sm-2 control-label">Всего дорог</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="wsegodorog" id="wsegodorog" title="Всего дорог"
                                            type="number" min="0" max="1000" step="1"/>
                            </div>
                            <label for="wsegodoroghardcover" class="col-sm-2 control-label">С твердым покрытием</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="wsegodoroghardcover" id="wsegodoroghardcover" title="Дорог с твердым покрытием"
                                            type="number" min="0" max="1000" step="1"/>
                            </div>
                            <label for="procent" class="col-sm-2 control-label">Процент</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="procent" id="procent" title="Процент дорог с твердым покрытием"
                                            type="number" min="0" max="100" step="0.1"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="cementbeton" class="col-sm-1 control-label">Цементно-бетонное покрытие</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="cementbeton" id="cementbeton" title="C цементно-бетонным покрытим"
                                            type="number" min="0" max="10000" step="0.1"/>
                            </div>
                            <label for="asfaltbeton" class="col-sm-1 control-label">Асфальто-бетонное покрытие</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="asfaltbeton" id="asfaltbeton" title="C асфальто-бетонным покрытим"
                                            type="number" min="0" max="10000" step="0.1"/>
                            </div>
                            <label for="chernshosse" class="col-sm-1 control-label">Черное шоссе</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="chernshosse" id="chernshosse" title="Черное шоссе"
                                            type="number" min="0" max="10000" step="0.1"/>
                            </div>
                            <label for="cementbeton" class="col-sm-1 control-label">Белое шоссе</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="beloeshosse" id="beloeshosse" title="Белое шоссе"
                                            type="number" min="0" max="10000" step="0.1"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="bruschatka" class="col-sm-2 control-label">Брусчатка</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="bruschatka" id="bruschatka" title="Брусчатка"
                                            type="number" min="0" max="10000" step="0.1"/>
                            </div>
                            <label for="degtegrunt" class="col-sm-2 control-label">Дегтегрунт</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="degtegrunt" id="degtegrunt" title="Дегтегрунтовое покрытие"
                                            type="number" min="0" max="10000" step="0.1"/>
                            </div>
                            <label for="gruntovye" class="col-sm-2 control-label">Грунтовые</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="gruntovye" id="gruntovye" title="Дорог с грунтовым покрытием"
                                            type="number" min="0" max="10000" step="0.1"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="pokrkat1" class="col-sm-3 control-label">Категория покрытия 1</label>
                            <label for="pokrkat2" class="col-sm-2 control-label">2</label>
                            <label for="pokrkat3" class="col-sm-2 control-label">3</label>
                            <label for="pokrkat4" class="col-sm-2 control-label">4</label>
                            <label for="pokrkat5" class="col-sm-2 control-label">5</label>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-2 col-sm-offset-2">
                               <form:input class="form-control" path="pokrkat1" id="pokrkat1" title="1 категория"
                                        type="number" min="0" max="10000" step="0.1"/>
                            </div>
                            <div class="col-sm-2">
                               <form:input class="form-control" path="pokrkat2" id="pokrkat2" title="2 категория"
                                        type="number" min="0" max="10000" step="0.1"/>
                            </div>
                            <div class="col-sm-2">
                               <form:input class="form-control" path="pokrkat3" id="pokrkat3" title="3 категория"
                                        type="number" min="0" max="10000" step="0.1"/>
                            </div>
                            <div class="col-sm-2">
                                <form:input class="form-control" path="pokrkat4" id="pokrkat4" title="4 категория"
                                        type="number" min="0" max="10000" step="0.1"/>
                            </div>
                            <div class="col-sm-2">
                                <form:input class="form-control" path="pokrkat5" id="pokrkat5" title="5 категория"
                                        type="number" min="0" max="10000" step="0.1"/>
                             </div>
                        </div>

                        <div class="form-group">
                            <label for="mostsht" class="col-sm-2 control-label">Мосты шт.</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="mostsht" id="mostsht" title="Всего мостов - шт."
                                            type="number" min="0" max="100" step="1"/>
                            </div>
                            <label for="mostpm" class="col-sm-2 control-label">п.м.</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="mostpm" id="mostpm" title="Мостов погонных метров"
                                            type="number" min="0" max="10000" step="0.1"/>
                            </div>
                            <label for="mostshtder" class="col-sm-2 control-label">Деревянных - шт.</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="mostshtder" id="mostshtder" title="Деревянных шт."
                                            type="number" min="0" max="100" step="1"/>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="mostpmder" class="col-sm-2 control-label">Деревянных - пог.метров</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="mostpmder" id="mostpmder" title="Деревянных мостов погонных метров"
                                            type="number" min="0" max="10000" step="0.1"/>
                            </div>
                            <label for="trubysht" class="col-sm-2 control-label">Трубы штук</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="trubysht" id="trubysht" title="Труб штук"
                                            type="number" min="0" max="100" step="1"/>
                            </div>
                            <label for="trubypm" class="col-sm-2 control-label">Труб пог.метров</label>

                            <div class="col-sm-2">
                                <form:input class="form-control" path="trubypm" id="trubypm" title="Труб погонных метров"
                                            type="number" min="0" max="10000" step="0.1"/>
                            </div>
                        </div>


                    </div>
                    <%-- end of modal body --%>
                    <div class="modal-footer">
                        <!--    <div class="col-sm-offset-5 col-sm-4"> -->
                        <button type="submit" class="btn btn-primary" id="butsubmit">Сохранить</button>
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
