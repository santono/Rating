<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:url var="actionUrl" value="rdoc/save"/>
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
                   method="post" action="${actionUrl }" enctype="multipart/form-data">
            <div class="modal-body">
                <div class="form-group">
                    <label for="comment" class="col-sm-1 control-label">Описание </label>

                    <div class="col-sm-8">
                        <form:input class="form-control" path="comment" id="comment" title="Описание документа"/>
<%--
                        <form:select class="form-control" path="idpodr" id="idpodr" title="Дорожное предприятие">
                            <form:option value="0" data-id="  ">-- Укажите ДРСУ --</form:option>

                            <c:if test="${not empty podrlist}">
                                <c:forEach var="p" items="${podrlist}">
                                    <form:option value="${p.id}" data-id="${p.id}"><c:out value="${p.name}" /> </form:option>
                                </c:forEach>
                            </c:if>
                        </form:select>
--%>


                    </div>
                </div>
<%--
                <div class="form-group">
                    <label for="comment" class="col-sm-2 control-label">Примечание</label>

                    <div class="col-sm-10">
                        <input class="form-control" type="file" title="Документ" name="documanet" />
                    </div>
                </div>
--%>
                <div class="form-group">
                    <label for="needDocumentImport" class="col-sm-4 control-label">Импортировать документ</label>
                    <div class="col-sm-2">
<%--
                    <form:checkbox path="needExcelImport" id="needDocumentImport" title="Укажите необходимость импорта подготовленного учебного плана из Excel" />
--%>
                        <input type="checkbox" name="needDocumentImport" id="needDocumentImport" title="Укажите необходимость импорта подготовленного учебного плана из Excel" />
                    </div>
                </div>

                <div id="importPanel">
                    <div class="form-group">
                        <label for="document" class="col-sm-2 control-label">
                            Файл с документом
                        </label>
                        <div class="col-sm-8">
                            <input class="input-file" type="file" title="Документ" name="document" id="document" />
                        </div>
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
            <form:hidden path="id"/>
            <form:hidden path="iddet"/>
            <form:hidden path="idroad"/>
            <form:hidden path="idwidblob"/>
            <form:hidden path="filename"/>
            <form:hidden path="shifrwrk"/>

        </form:form>
    </c:if>
</div>
<!-- end of modal content-->
</div>
<!-- end of modal dialog-->
</div>
