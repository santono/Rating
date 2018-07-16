<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:url var="actionUrl" value="rd/setcols" />
<div class="container">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">&times;</span>
                    <span class="sr-only">Закрыть</span>
                </button>
            </div>
            <form:form role="form" class="form-horizontal" acceptCharset="UTF-8" id="colForm" commandName="colsDTO" method="post" action="${actionUrl }">
                <div class="modal-body">
                    <div class="form-group">
                        <div class="col-sm-10 col-sm-offset-1">
                        <%-- http://stackoverflow.com/questions/7421346/spring-binding-listobject-to-formcheckboxes--%>
                            <form:checkboxes items="${listRDCols}" path="cols" itemLabel="idName" itemValue="id" delimiter="<br/>"/>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <!--    <div class="col-sm-offset-5 col-sm-4"> -->
                    <button type="submit" class="btn btn-primary" id="butsbmcol">Сохранить</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                    <!--    </div>    -->
                </div>
            </form:form>
        </div>  <!-- end of modal content-->
    </div> <!-- end of modal dialog-->
</div>
