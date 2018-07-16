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
    <div class="my-gallery" itemscope itemtype="http://schema.org/ImageGallery">
    <table class="table table-condensed table-bordered">
        <thead>
        <tr>
            <th></th>
            <th class="text-center">Описание</th>
        <%--    <th class="text-center">Файл</th>   --%>
            <th class="text-center">Тип</th>
            <th class="text-center">Загружен</th>
        </tr>
        </thead>
        <tbody>
   <%--
        <div id="demo-test-gallery" class="demo-gallery">
    --%>
        <c:forEach items="${detlist}" var="trow">
            <tr>
                <td>
                    <nobr>
                        <button type="button" class="btn btn-primary btn-xs" onclick="editRec(${trow.id});"><span class="glyphicon glyphicon-pencil"></span></button>
                        <button type="button" class="btn btn-primary btn-xs delrecbutton" idrec="${trow.id}"
                                namerec="${trow.comment}"><span class="glyphicon glyphicon-remove"></span></button>
                    </nobr>
                </td>
                <td class="text-center">
                    <c:out value="${trow.comment}"/>
                </td>
             <%--
                <td class="text-center">
                    <c:out value="${trow.filename}"/>
                </td>
              --%>
                <td class="text-center">
<%--
                    <c:if test="${trow.dateUploadStr.length()>3} ">
                    <c:if test="${not empty trow.dateUploadStr} ">
--%>
                   <c:choose>
                     <c:when test="${trow.itIsImage==1}">
               <%--      <a href="/ad/resources/images/${trow.imageName}" data-size="1600x1600" data-med="/ad/resources/images/${trow.imageName}" data-med-size="1024x1024" data-author="${trow.title}"  class="demo-gallery__img--main"> --%>

                         <figure itemprop="associatedMedia" itemscope itemtype="http://schema.org/ImageObject">
                             <a href="rdoc/image/${trow.id}" itemprop="contentUrl" data-size="1600x1600" data-med="rdoc/image/${trow.imageName}" data-med-size="1024x1024">
                                 <img src="/ad/resources/images/${trow.imageName}" height="32" width="32" itemprop="thumbnail" alt="${trow.filename}" />
                             </a>
                             <figcaption itemprop="caption description">${trow.filename}</figcaption>
                         </figure>

                <%--
                     <a href="rdoc/image/${trow.id}" data-size="1600x1600" data-med="rdoc/image/${trow.imageName}" data-med-size="1024x1024" data-author="${trow.id}"  class="demo-gallery__img--main">
                        <img src="/ad/resources/images/${trow.imageName}" alt="${trow.title}" height="32" width="32" />
                --%>
                         <%-- <figure>${trow.title}</figure>--%>
                <%--
                     </a>
                --%>
                     </c:when>
                     <c:otherwise>
                        <a href="rdoc/doc/${trow.id}" class="notImage">
                           <img src="/ad/resources/images/${trow.imageName}" alt="${trow.filename}" height="32" width="32" class='notImage'/>
                        </a>
                     </c:otherwise>
<%--
                    </c:if>
--%>               </c:choose>
                </td>
                <td class="text-center">
                    <c:out value="${trow.dateUploadStr}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    </div>   <%-- end of demo gallery --%>

</div>