<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<spring:url value="/resources/js/jquery.inputmask.bundle.min.js" var="inputmask_url"/>
<script type="text/javascript" src="${inputmask_url}">
    <jsp:text/>
</script>

<!-- PAGINATION plugin -->
<spring:url value="/resources/css/bs_pagination/jquery.bs_pagination.min.css" var="pagination_css_url"/>
<spring:url value="/resources/js/bs_pagination/jquery.bs_pagination.min.js" var="pagination_js_url"/>
<spring:url value="/resources/js/bs_pagination/localization/ru.min.js" var="pagination_loc_url"/>

<link rel="stylesheet" media="screen" type="text/css" href="${pagination_css_url}"/>
<script type="text/javascript" src="${pagination_js_url}"></script>
<script type="text/javascript" src="${pagination_loc_url}"></script>

<!-- FILTERS plugin -->
<spring:url value="/resources/css/jui_filter_rules/jquery.jui_filter_rules.bs.min.css" var="jui_filter_css_url"/>
<spring:url value="/resources/js/jui_filter_rules/jquery.jui_filter_rules.js" var="jui_filter_js_url"/>
<spring:url value="/resources/js/jui_filter_rules/localization/ru.js" var="jui_filter_loc_url"/>
<spring:url value="/resources/js/jui_filter_rules/localization/ru.js" var="jui_filter_loc_url"/>
<spring:url value="/resources/js/jquery.sumoselect.min.js" var="sumoselect_url"/>
<spring:url value="/resources/css/sumoselect.css" var="sumoselect_css"/>

<link rel="stylesheet" media="screen" type="text/css" href="${jui_filter_css_url}"/>
<link rel="stylesheet" type="text/css" media="screen" href="${sumoselect_css}"/>
<script type="text/javascript" src="${jui_filter_js_url}"></script>
<script type="text/javascript" src="${jui_filter_loc_url}"></script>
<script type="text/javascript" src="${sumoselect_url}"></script>


<!-- required from filters plugin -->
<spring:url value="/resources/js/moment/moment.min.js" var="moment_js_url"/>

<script type="text/javascript" src="${moment_js_url}"></script>

<!-- DATAGRID plugin -->
<spring:url value="/resources/css/bs_grid/jquery.bs_grid.min.css" var="bs_grid_css_url"/>
<spring:url value="/resources/js/bs_grid/jquery.bs_grid.js" var="bs_grid_js_url"/>
<spring:url value="/resources/js/bs_grid/localization/ru.js" var="bs_grid_loc_url"/>
<spring:url value="/resources/js/bootstrap-confirmation.min.js" var="boot_conf_url"/>

<link rel="stylesheet" media="screen" type="text/css" href="${bs_grid_css_url}"/>
<script type="text/javascript" src="${bs_grid_js_url}"></script>
<script type="text/javascript" src="${bs_grid_loc_url}"></script>

<script type="text/javascript" src="${boot_conf_url}">
    <jsp:text/>
</script>
<script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>


<c:set value="Список дорог" var="cpt"/>

<div class="body">
    <div class="row">
        <select id="road">
            <option value="0">--- Укажите дорогу ---</option>
            <c:forEach items="${rList}" var="item">
                <option value="${item.id}"><c:out value="${item.name}"/></option>
            </c:forEach>
        </select>

    </div>
    <div class="row">
        <div id="map" style="width:100%;height:700px"></div>
    </div>
    <!--  Just create a div and give it an ID -->


</div>
<script type="text/javascript">
    var globalMap;
    var globalRoute;

    jQuery(document).ready(function () {

        //     jQuery('[data-toggle=confirmation]').confirmation();
        //     jQuery('[data-toggle=confirmation-singleton]').confirmation({ singleton:true });
        //     jQuery('[data-toggle=confirmation-popout]').confirmation({ popout: true });

        //     setSubmitForRoadForm();
        jQuery("#road").on('change', function () {
            var id;
            id = jQuery("#road").val();
            editRoadMap(id);
        });

        //  ymaps.ready(initMap());

    });

    ymaps.ready(function initMap() {
//    jQuery('#map').width('100%');
//    jQuery('#map').height(800);
        var url = "roads/getcoords";
        var posting = jQuery.post(url, "idroad=5");
        posting.done(function (data) {
            var content = data;
            if (!jQuery.isArray(data)) {
                alert('Ошибка получения координат');
                return;
            }
            var i;
            var xx = [];
            var yy = [];
            for (i = 0; i < content.length; i++) {
                xx[i] = content[i].xx;
                yy[i] = content[i].yy;
            }
            var route1 = [];
            for (i = 0; i < xx.length; i++) {
                route1[i] = [parseFloat(yy[i]), parseFloat(xx[i])];
            }
            ymaps.ready(init(route1));

        });


    });


    function editRoadMap(id) {
//    event.stopPropagation();
        if (!globalRoute) {
        } else {
            //       if (globalMap instanceof ymaps.Map.) {
            //    globalMap.GeoObjectCollection.remove(globalRoute);
            globalMap.geoObjects.remove(globalRoute);
            globalRoute = null;
            //       }
        }
        var url = "roads/getcoords";
        var posting = jQuery.post(url, "idroad=" + id);
        posting.done(function (data) {
            var content = data;
            if (!jQuery.isArray(data)) {
                alert('Ошибка получения координат');
                return;
            }
            jQuery("#roadModal").modal();
            var i;

            var xx = [];
            var yy = [];
            for (i = 0; i < content.length; i++) {
                xx[i] = content[i].xx;
                yy[i] = content[i].yy;
            }
            var route1 = [];
            for (i = 0; i < xx.length; i++) {
                route1[i] = [parseFloat(yy[i]), parseFloat(xx[i])];
            }
            ymaps.ready(init(route1));

        });


    } // end of editRoadMap


    function init(route1) {
        //    controls = jQuery.parseJSON('["fullscreenControl","geolocationControl","rulerControl","searchControl","typeSelector","zoomControl"]'),
        //    controls = jQuery.parseJSON('["fullscreenControl","rulerControl","searchControl","typeSelector","zoomControl"]'),
        //     controls = jQuery.parseJSON('["fullscreenControl","rulerControl","typeSelector","zoomControl"]'),
        controls = jQuery.parseJSON('["fullscreenControl","rulerControl","typeSelector","zoomControl"]');

        if (!globalMap) {
            globalMap = new ymaps.Map("map", {
                center:[45.9882, 34.5526],
                zoom:9,
                controls:controls
            });
        }
        ymaps.route(route1, {mapStateAutoApply:true,  wayPointVisible:false}).then(function (route) {
            globalRoute = route;
            route.getPaths().options.set({
                // в балуне выводим только информацию о времени движения с учетом пробок
                // можно выставить настройки графики маршруту
                strokeColor:'ff00ffff',
                opacity:1.0,
                strokeWidth:10

            });
            route.getPaths().options.set('hasBalloon', false);
            globalMap.geoObjects.add(route);
            var points = route.getWayPoints(),
                    lastPoint = points.getLength() - 1;
            // Задаем стиль метки - иконки будут красного цвета, и
            // их изображения будут растягиваться под контент.
            points.options.set('preset', 'islands#redStretchyIcon');
            // Задаем контент меток в начальной и конечной точках.
            points.get(0).properties.set('iconContent', 'Точка отправления');
            points.get(lastPoint).properties.set('iconContent', 'Точка прибытия');

        }, function (error) {
            alert('Возникла ошибка: ' + error.message);
        });
    }
    ;


</script>


