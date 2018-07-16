<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:url var="actionUrl" value="rd/setcols" />
<div class="panel panel-default">
<button id="bselcol" class="btn btn-default" onclick="editCols();">Колонки</button>

    <div class="panel-heading">
        <div class="pull-right"></div>
        <c:out value="${nameRoad}"/>

    </div>

    <!-- Выбор колонок -->
<%--
    <form:form role="form" class="form-inline" acceptCharset="UTF-8" id="groupForm" commandName="colsDTO" method="post" action="${actionUrl }">
    <div class="form-group">
        <label for="colSelect">Укажите колонки</label>
   <form:select class="form-control" path="cols" id="colSelect" title="Выбор колонок" multiple="true" size="10">
                <c:forEach items="${listRDCols}" var="item">
                        <form:option value="${item.id}">
                            <c:out value="${item.name}" />
                        </form:option>
                </c:forEach>
    </form:select>
    </div>
        <button type="submit" class="btn btn-default">Выполнить</button>
    </form:form>
--%>    
    <!-- Таблица -->
    <table class="table table-condensed table-bordered">
        <thead>
        <tr>
            <th rowspan="2"></th>
            <th class="text-center">Наименование дороги</th>
            <c:if test="${mapneed['3']=='1'}">
            <th class="text-center">Всего<br>дорог</th>
            </c:if>
            <c:if test="${mapneed['4']=='1'}">
            <th class="text-center">В т.ч.<br>с тв.<br>покр.</th>
            </c:if>
            <c:if test="${mapneed['5']=='1'}">
            <th class="text-center">%</th>
            </c:if>
            <c:if test="${mapneed['6']=='1'}">
            <th class="text-center">цем.<br>бетон.</th>
            </c:if>
            <c:if test="${mapneed['7']=='1'}">
            <th class="text-center">асф.<br>бетон.</th>
            </c:if>
            <c:if test="${mapneed['8']=='1'}">
            <th class="text-center">черн.<br>шоссе</th>
            </c:if>
            <c:if test="${mapneed['9']=='1'}">
            <th class="text-center">белое<br>шоссе</th>
            </c:if>
            <c:if test="${mapneed['10']=='1'}">
            <th class="text-center">брус<br>чатка</th>
            </c:if>
            <c:if test="${mapneed['11']=='1'}">
            <th class="text-center">дегте<br>грунт.</th>
            </c:if>
            <c:if test="${mapneed['12']=='1'}">
                <th class="text-center">грун<br>товые</th>
            </c:if>
            <c:if test="${mapneed['13']=='1'}">
                <th class="text-center">Покр.<br>1 кат.</th>
            </c:if>
            <c:if test="${mapneed['14']=='1'}">
                <th class="text-center">Покр.<br>2 кат.</th>
            </c:if>
            <c:if test="${mapneed['15']=='1'}">
                <th class="text-center">Покр.<br>3 кат.</th>
            </c:if>
            <c:if test="${mapneed['16']=='1'}">
                <th class="text-center">Покр.<br>4 кат.</th>
            </c:if>
            <c:if test="${mapneed['17']=='1'}">
                <th class="text-center">Покр.<br>5 кат.</th>
            </c:if>
            <c:if test="${mapneed['18']=='1'}">
                <th class="text-center">Мосты<br>шт.</th>
            </c:if>
            <c:if test="${mapneed['19']=='1'}">
                <th class="text-center">Мосты<br>п.м.</th>
            </c:if>
            <c:if test="${mapneed['20']=='1'}">
                <th class="text-center">Дерев.<br>шт.</th>
            </c:if>
            <c:if test="${mapneed['21']=='1'}">
                <th class="text-center">Дерев.<br>п.м.</th>
            </c:if>
            <c:if test="${mapneed['22']=='1'}">
                <th class="text-center">Мосты<br>шт.</th>
            </c:if>
            <c:if test="${mapneed['23']=='1'}">
                <th class="text-center">Мосты<br>п.м.</th>
            </c:if>
        </tr>
		<tr>
            <th class="text-center">2</th>
            <c:if test="${mapneed['3']=='1'}">
            <th class="text-center">3</th>
            </c:if>
            <c:if test="${mapneed['4']=='1'}">
            <th class="text-center">4</th>
            </c:if>
            <c:if test="${mapneed['5']=='1'}">
            <th class="text-center">5</th>
            </c:if>
            <c:if test="${mapneed['6']=='1'}">
            <th class="text-center">6</th>
            </c:if>
            <c:if test="${mapneed['7']=='1'}">
            <th class="text-center">7</th>
            </c:if>
            <c:if test="${mapneed['8']=='1'}">
            <th class="text-center">8</th>
            </c:if>
            <c:if test="${mapneed['9']=='1'}">
            <th class="text-center">9</th>
            </c:if>
            <c:if test="${mapneed['10']=='1'}">
            <th class="text-center">10</th>
            </c:if>
            <c:if test="${mapneed['11']=='1'}">
            <th class="text-center">11</th>
            </c:if>
            <c:if test="${mapneed['12']=='1'}">
                <th class="text-center">12</th>
            </c:if>
            <c:if test="${mapneed['13']=='1'}">
                <th class="text-center">13</th>
            </c:if>
            <c:if test="${mapneed['14']=='1'}">
                <th class="text-center">14</th>
            </c:if>
            <c:if test="${mapneed['15']=='1'}">
                <th class="text-center">15</th>
            </c:if>
            <c:if test="${mapneed['16']=='1'}">
                <th class="text-center">16</th>
            </c:if>
            <c:if test="${mapneed['17']=='1'}">
                <th class="text-center">17</th>
            </c:if>
            <c:if test="${mapneed['18']=='1'}">
                <th class="text-center">18</th>
            </c:if>
            <c:if test="${mapneed['19']=='1'}">
                <th class="text-center">19</th>
            </c:if>
            <c:if test="${mapneed['20']=='1'}">
                <th class="text-center">20</th>
            </c:if>
            <c:if test="${mapneed['21']=='1'}">
                <th class="text-center">21</th>
            </c:if>
            <c:if test="${mapneed['22']=='1'}">
                <th class="text-center">22</th>
            </c:if>
            <c:if test="${mapneed['23']=='1'}">
                <th class="text-center">23</th>
            </c:if>
        </tr>
        </thead>
        <tbody>

        <c:forEach items="${detlist}" var="trow">
            <tr>
                <td>
                    <nobr>
                        <c:out value="${trow.menur}" escapeXml="false"	/>
                    </nobr>
                </td>
                <td>
                    <nobr>
                        <c:out value="${trow.name}"/>
                    </nobr>
                </td>
                <c:if test="${not empty trow.comment}">
                    <td class="text-left" colspan="${colspan}" >
                        <c:out value="${trow.comment}" />
                    </td>
                </c:if>
                <c:if test="${empty trow.comment}">
                <c:if test="${mapneed['3']=='1'}">
                <td class="text-center">
                    <c:if test="${trow.wsegodorog>0}">
                        <fmt:formatNumber type="number" pattern="#0" maxFractionDigits="0" value="${trow.wsegodorog}"/>
                    </c:if>
                </td>
                </c:if>
                <c:if test="${mapneed['4']=='1'}">
                <td class="text-center">
                    <c:if test="${trow.wsegodoroghardcover>0}">
                       <fmt:formatNumber type="number" pattern="#0" maxFractionDigits="0" value="${trow.wsegodoroghardcover}"/>
                    </c:if>
                </td>
                </c:if>
                <c:if test="${mapneed['5']=='1'}">
                <td class="text-center">
                    <c:if test="${trow.procent>0}">
                       <fmt:formatNumber type="number" pattern="#0" maxFractionDigits="0" value="${trow.procent}"/>
                    </c:if>
                </td>
                </c:if>
                <c:if test="${mapneed['6']=='1'}">
                <td class="text-center">
                    <c:if test="${trow.cementbeton>0}">
                       <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.cementbeton}"/>
                    </c:if>
                </td>
                </c:if>
                <c:if test="${mapneed['7']=='1'}">
                <td class="text-center">
                    <c:if test="${trow.asfaltbeton>0}">
                       <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.asfaltbeton}"/>
                    </c:if>
                </td>
                </c:if>
                <c:if test="${mapneed['8']=='1'}">
                <td class="text-center">
                    <c:if test="${trow.chernshosse>0}">
                       <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.chernshosse}"/>
                    </c:if>
                </td>
                </c:if>
                <c:if test="${mapneed['9']=='1'}">
                <td class="text-center">
                    <c:if test="${trow.beloeshosse>0}">
                       <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.beloeshosse}"/>
                    </c:if>
                </td>
                </c:if>
                <c:if test="${mapneed['10']=='1'}">
                   <td class="text-center">
                       <c:if test="${trow.bruschatka>0}">
                          <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.bruschatka}"/>
                       </c:if>
                   </td>
                </c:if>
                <c:if test="${mapneed['11']=='1'}">
                <td class="text-center">
                    <c:if test="${trow.degtegrunt>0}">
                       <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.degtegrunt}"/>
                    </c:if>
                </td>
                </c:if>
                <c:if test="${mapneed['12']=='1'}">
                <td class="text-center">
                    <c:if test="${trow.gruntovye>0}">
                       <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.gruntovye}"/>
                    </c:if>
                </td>
                </c:if>
                <c:if test="${mapneed['13']=='1'}">
                     <td class="text-center">
                         <c:if test="${trow.pokrkat1>0}">
                             <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.pokrkat1}"/>
                         </c:if>
                     </td>
                </c:if>
                    <c:if test="${mapneed['14']=='1'}">
                        <td class="text-center">
                            <c:if test="${trow.pokrkat2>0}">
                                <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.pokrkat2}"/>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${mapneed['15']=='1'}">
                        <td class="text-center">
                            <c:if test="${trow.pokrkat3>0}">
                                <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.pokrkat3}"/>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${mapneed['16']=='1'}">
                        <td class="text-center">
                            <c:if test="${trow.pokrkat4>0}">
                                <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.pokrkat4}"/>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${mapneed['17']=='1'}">
                        <td class="text-center">
                            <c:if test="${trow.pokrkat5>0}">
                                <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.pokrkat5}"/>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${mapneed['18']=='1'}">
                        <td class="text-center">
                            <c:if test="${trow.mostsht>0}">
                                <fmt:formatNumber type="number" pattern="#0" maxFractionDigits="0" value="${trow.mostsht}"/>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${mapneed['19']=='1'}">
                        <td class="text-center">
                            <c:if test="${trow.mostpm>0}">
                                <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.mostpm}"/>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${mapneed['20']=='1'}">
                        <td class="text-center">
                            <c:if test="${trow.mostshtder>0}">
                                <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.mostshtder}"/>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${mapneed['21']=='1'}">
                        <td class="text-center">
                            <c:if test="${trow.mostpmder>0}">
                                <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.mostpmder}"/>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${mapneed['22']=='1'}">
                        <td class="text-center">
                            <c:if test="${trow.trubysht>0}">
                                <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.trubysht}"/>
                            </c:if>
                        </td>
                    </c:if>
                    <c:if test="${mapneed['23']=='1'}">
                        <td class="text-center">
                            <c:if test="${trow.trubypm>0}">
                                <fmt:formatNumber type="number" pattern="#0.0" maxFractionDigits="1" value="${trow.trubypm}"/>
                            </c:if>
                        </td>
                    </c:if>






                </c:if>
            </tr>

        </c:forEach>

        </tbody>
    </table>

</div>