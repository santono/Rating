<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url var="actionUrl" value="widcover/save" />
<div class="container-fluid">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Закрыть</span>
                </button>
            </div>
            <form:form role="form" class="form-horizontal" acceptCharset="UTF-8" id="recForm" commandName="wc" method="post" action="${actionUrl }">
                <div class="modal-body">
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">Шифр для ссылок (внутренний)</label>
                        <div class="col-sm-3">
                            <c:out value="${wc.id}" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">Название</label>
                        <div class="col-sm-7">
                            <form:input class="form-control" path="name" id="name" title="Название покрытия"  />
                        </div>
                        <div class="col-sm-3">
                            <form:errors path="name" cssClass="error" class="control-label" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="price" class="col-sm-2 control-label">Стоимость м.кв.</label>
                        <div class="col-sm-3">
                            <form:input class="form-control" path="price" id="price" title="Стоимость квадратного метра покрытия"  />
                        </div>
                        <div class="col-sm-3">
                            <form:errors path="price" cssClass="error" class="control-label" />
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <!--    <div class="col-sm-offset-5 col-sm-4"> -->
                    <form:hidden path="id"        value="${id}"         />
                    <button type="submit" class="btn btn-primary" >Сохранить</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                    <!--    </div>    -->
                </div>
            </form:form>
        </div>  <!-- end of modal content-->
    </div> <!-- end of modal dialog-->
</div>
