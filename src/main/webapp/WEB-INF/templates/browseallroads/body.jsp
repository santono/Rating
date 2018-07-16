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
    <div class="modal fade" id="roadModal" tabindex="-1" role="dialog" aria-labelledby="recForm" aria-hidden="true">
        <div class="container-fluid">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">&times;</span>
                            <span class="sr-only">Закрыть</span>
                        </button>
                    </div>
                    <div id="map"></div>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                </div>
                <!-- end of modal content-->
            </div>
            <!-- end of modal dialog-->
        </div>
    </div>

    <div class="modal fade" id="formModal" tabindex="-1" role="dialog" aria-labelledby="recForm" aria-hidden="true">
        <div class="container-fluid">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal">
                            <span aria-hidden="true">&times;</span>
                            <span class="sr-only">Закрыть</span>
                        </button>
                    </div>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Закрыть</button>
                </div>
                <!-- end of modal content-->
            </div>
            <!-- end of modal dialog-->
        </div>
    </div>

    <!--  Just create a div and give it an ID -->

    <div id="group_bs_grid"></div>

</div>
<script type="text/javascript">
jQuery(document).ready(function () {

    //     jQuery('[data-toggle=confirmation]').confirmation();
    //     jQuery('[data-toggle=confirmation-singleton]').confirmation({ singleton:true });
    //     jQuery('[data-toggle=confirmation-popout]').confirmation({ popout: true });

    //     setSubmitForRoadForm();
    setNumericMask();
    setSubmitAjaxForRecForm();

    jQuery('.delrecordbutton').on('click',

            function () {
                var id = jQuery(this).attr('idroad');
                var nam = jQuery(this).attr('nameroad');
                if (confirm('Удалить дорогу ' + nam + '?')) {
                    var url = 'rp/delrecord';
                    var sendData = {id:id};
                    var AJAXadr = "rp/delrecord/" + id;
                    jQuery.get(AJAXadr, function (result) {
                        location.reload();
                    });
                }
            }
    )

    active = false;
    savinginprogress = false;

    <%-- bs_grid  --%>
    jQuery("#group_bs_grid").bs_grid({

        ajaxFetchDataURL:"roads/getgrid",
        row_primary_key:"id",
        columns:[
            {field:"id", header:"Id", visible:"no"},
            {field:"kod", header:"Номер"},
            {field:"menur", header:"Режимы"},
            {field:"code", header:"Код"},
            {field:"name", header:"Нaзвание"},
            {field:"dlina", header:"Протяженность, км."},
            {field:"kodtype", header:"Вид", visible:"no"}
        ],

        sorting:[
            {sortingName:"Номер", field:"kod", order:"ascending"},
            {sortingName:"Длина", field:"dlina", order:"ascending"}
        ],

        filterOptions:{
            filters:[
                {
                    filterName:"nameCity", "filterType":"text", field:"name", filterLabel:"Населенный пункт",
                    excluded_operators:["in", "not_in"],
                    filter_interface:[
                        {
                            filter_element:"input",
                            filter_element_attributes:{"type":"text"}
                        }
                    ]
                },

                {
                    filterName:"VipFilte", "filterType":"number", "numberType":"integer", field:"kodtype", filterLabel:"Вид дороги",
                    excluded_operators:["equal", "not_equal", "less", "less_or_equal", "greater", "greater_or_equal"],
                    filter_interface:[
                        {
                            filter_element:"input",
                            filter_element_attributes:{type:"checkbox"}
                        }
                    ],
                    lookup_values:[
                        {lk_option:"Региональные", lk_value:"1"},
                        {lk_option:"Межмуниципальные", lk_value:"2", lk_selected:"yes"}
                    ]
                }

            ]
        },
        onDisplay:function () {

            jQuery('.delrecbutton').on('click',

                    function () {
                        var id = jQuery(this).attr('idroad');
                        var nam = jQuery(this).attr('nameroad');
                        if (confirm('Удалить запись ' + nam + '?')) {
                            var url = 'rp/delrecord';
                            var sendData = {id:id};
                            var AJAXadr = "rp/delrecord/" + id;
                            jQuery.get(AJAXadr, function (result) {
                                location.reload();
                            });
                        }
                    }
            );
            jQuery('.browseRoadPoint').on('click',

                    function () {
                        var id = jQuery(this).attr('idroad');
                        browseRoadPoints(id);
                    }
            )
            jQuery('.browseRoadDet').on('click',

                    function () {
                        var id = jQuery(this).attr('idroad');
                        browseRoadDets(id);
                    }
            )
            jQuery('.browseRoadMap').on('click',

                    function (event) {
                        var id = jQuery(this).attr('idroad');
                        var dlina = jQuery(this).attr('dlina');
                        editRoadMap(event, id, dlina);
                    }
            )
        }
    })
    <%-- end of bs_grid --%>
});
function addRecord() {
    jQuery('.modal-title').text('Реквизиты дороги');
    setNumericMask();
    jQuery('#roadSelect').SumoSelect();
    jQuery('#formModal').modal();
}

function editRoad(id) {
    var url = 'roads/getrec';
    var sendData = {id:id};
    jQuery("#formModal").empty();
    jQuery("#formModal").load(url, sendData, function () {
                //    currNapr=0;
                //    loadSpeci();
                setNumericMask();
                setSubmitAjaxForRecForm();
                //       jQuery('#roadSelect').SumoSelect();
                jQuery("#formModal").modal();
            }
    );
}

function editRoadMap(event, id, dlina) {
    event.stopPropagation();
    if (!globalMap) {
    } else {
        if (globalMap instanceof ymaps.Map) {
            globalMap.destroy();
        }
    }
    var url = "roads/getcoords";
    var posting = jQuery.post(url, "idroad=" + id);
    posting.done(function (data) {
        var content = data;
        if (!jQuery.isArray(data)) {
            alert('Ошибка получения координат');
            return;
        }
        jQuery('#map').width('100%');
        jQuery('#map').height(800);
        jQuery("#roadModal").modal();

        var xx = [];
        var yy = [];
        var i;
        var maxxx, minxx, maxyy, minyy;
        maxxx = 0;
        maxyy = 0;
        minxx = 90;
        minyy = 90;
        for (i = 0; i < content.length; i++) {
            xx[i] = content[i].xx;
            yy[i] = content[i].yy;
            if (parseFloat(xx[i]) > maxxx) maxxx = parseFloat(xx[i]);
            if (parseFloat(xx[i]) < minxx) minxx = parseFloat(xx[i]);
            if (parseFloat(yy[i]) > maxyy) maxyy = parseFloat(yy[i]);
            if (parseFloat(yy[i]) < minyy) minyy = parseFloat(yy[i]);
        }
        meanxx = (maxxx + minxx) / 2.0;
        meanyy = (maxyy + minyy) / 2.0;
        var route1 = [];
        for (i = 0; i < xx.length; i++) {
            route1[i] = [parseFloat(yy[i]), parseFloat(xx[i])];
        }
        var zoom = 10;
        if (dlina) {
            if (dlina > 0) {
                if (dlina < 3) {
                    zoom = 13
                } else
                if (dlina < 5) {
                    zoom = 12
                } else
                if (dlina < 8) {
                    zoom = 11
                } else
                if (dlina < 20) {
                    zoom = 11
                } else
                if (dlina > 190) {
                    zoom = 8
                }
                if (dlina > 100) {
                    zoom = 9
                }
            }
        }
        ymaps.ready(init(meanyy, meanxx, route1, zoom));

    });


} // end of editRoadMap
function browseRoadPoints(id) {
    location.href = "rp?idroad=" + id;
}
function browseRoadDets(id) {
    location.href = "rd?idroad=" + id;
}

function browseRoadDocs(id) {
    location.href = "rdoc?idroad=" + id;
}


function deleteRecord(id, nam) {
    if (confirm('Удалить дорогу ' + nam + '?')) {
        var url = 'roads/delrecord';
        var sendData = {id:id};
        var AJAXadr = "roads/delrecord/" + id;
        jQuery.get(AJAXadr, function (result) {
            location.reload();
        });
    }
}

function resetDialog(form) {

    form.find("input").val("");
}

function setSubmitForRoadForm() {
    jQuery('#roadForm').on('submit', function () {
                var str = jQuery('#roadForm').serialize();
                jQuery('#roadModal').modal('hide');
                jQuery('#roadForm').submit();
                return false;
            }
    )
}


function setSubmitAjaxForRecForm() {
    jQuery('#recForm').on('submit', function (event) {
                event.preventDefault();
                if (savinginprogress) {
                    return false;
                }
                savinginprogress = true
                //    this.disabled=true;
                //           jQuery("#butsubmit").attr('disabled','true');
                var str = jQuery('#recForm').serialize();
                if (str == formvaluessave) {
                    var mese = '<div class="activity-item"> <i class="fa fa-tasks text-danger"></i> <div class="activity">Данные строки не менялись</div> </div>'
                    generateNoty('information', mese);
                    savinginprogress = false;
                    return false;
                }
                // Get some values from elements on the page:
                var dform = jQuery(this),
                        url = dform.attr("action");
                // Send the data using post
                var posting = jQuery.post(url, str);
                // Put the results in a div
                posting.done(function (data) {
                    //       var content = jQuery.parseJSON(data);
                    var content = data;
                    if (jQuery.isArray(data)) {
                        console.log(' array length=' + data.length);
                        console.log('shifr=' + data[0].shifr)
                    } else {
                        console.log = 'data is not array';
                    }
//                        if (content.shifr) {
//                            console.log(content.shifr);
//                        } else {
//                            console.log(content);
//                        }
                    if (content[0].shifr == 'Ok') {
                        formvaluessave = str;
                        generateNoty('information', notification_html[2]);
                        setTimeout(function () {
                            //                          generateAll();
                            jQuery('#recModal').modal('hide');
                            window.location.reload();
                        }, 1000);
                        //     window.location.reload();
                    } else
                    if (content.length > 0) {
                        for (i = 0; i < content.length; i++) {
                            var mes = '<div class="activity-item"> <i class="fa fa-tasks text-warning"></i> <div class="activity"> ' + content[i].name.trim() + '</div> </div>'
                            setTimeout(function () {
                                generateNoty('error', mes);
                            }, 500);
                        }
                    }
                    //            jQuery( "#result" ).empty().append( content );
                    savinginprogress = false;

                });
                //                  jQuery('#recForm').submit();
                savinginprogress = false;
                return false;
            }
    )
}

var formvaluessave;
//   var savinginprogress;
var notification_html = [];

notification_html[0] = '<div class="activity-item"> <i class="fa fa-tasks text-warning"></i> <div class="activity"> There are <a href="#">6 new tasks</a> waiting for you. Don\'t forget! <span>About 3 hours ago</span> </div> </div>',
        notification_html[1] = '<div class="activity-item"> <i class="fa fa-check text-success"></i> <div class="activity"> Данные сохранены </div> </div>',
        notification_html[2] = '<div class="activity-item"> <i class="fa text-danger"></i> <div class="activity"> Данные сохранены </div> </div>',
        notification_html[3] = '<div class="activity-item"> <i class="fa fa-shopping-cart text-success"></i> <div class="activity"> <a href="#">Eugene</a> ordered 2 copies of <a href="#">OEM license</a> <span>14 minutes ago</span> </div> </div>';

function generateNoty(type, text) {

    var n = noty({
        text:text,
        type:type,
        dismissQueue:true,
        layout:'topLeft',
        closeWith:['click'],
        theme:'relax',
        maxVisible:10,
        animation:{
            open:'animated bounceInLeft',
            close:'animated bounceOutLeft',
            easing:'swing',
            speed:500
        }
    });
    //   console.log('html: ' + n.options.id);
}



function setNumericMask() {
    jQuery('#kod').inputmask("numeric", {
        groupSeparator:",",
        placeholder:"0",
        autoGroup:false
    })
    jQuery('#dlina').inputmask("numeric", {
        groupSeparator:",",
        placeholder:"0",
        autoGroup:false,
        digits:2,
        digitsOptional:true

    })
}


function init(meanyy, meanxx, route1, zoom) {
    //    controls = jQuery.parseJSON('["fullscreenControl","geolocationControl","rulerControl","searchControl","typeSelector","zoomControl"]'),
    //    controls = jQuery.parseJSON('["fullscreenControl","rulerControl","searchControl","typeSelector","zoomControl"]'),
    //     controls = jQuery.parseJSON('["fullscreenControl","rulerControl","typeSelector","zoomControl"]'),
    controls = jQuery.parseJSON('["fullscreenControl","rulerControl","typeSelector","zoomControl"]');

    globalMap = new ymaps.Map("map", {
        center:[meanyy, meanxx],
        zoom:zoom,
        controls:controls
    });
    ymaps.route(route1, {mapStateAutoApply:true}).then(function (route) {
        route.getPaths().options.set({
            // в балуне выводим только информацию о времени движения с учетом пробок
            // можно выставить настройки графики маршруту
            strokeColor:'ff00ffff',
            opacity:1.0,
            strokeWidth:10

        });
        globalMap.geoObjects.add(route);
    }, function (error) {
        alert('Возникла ошибка: ' + error.message);
    });
};

var globalMap;
function generateExit(layout) {
    var n = noty({
        text:'Вы хотите выйти из программы?',
        type:'alert',
        dismissQueue:true,
        layout:layout,
        theme:'bootstrapTheme',
        buttons:[
            {addClass:'btn btn-primary', text:'Да', onClick:function (e_noty) {
                e_noty.close();
                noty({dismissQueue:true, force:true, layout:layout, theme:'bootstrapTheme', text:'Выходим из программы', type:'success'});
                window.location.href = 'logout';
                active = false;
                return true;
            }
            },
            {addClass:'btn btn-danger', text:'Нет', onClick:function (e_noty) {
                e_noty.close();
                noty({dismissQueue:true, force:true, layout:layout, theme:'bootstrapTheme', text:'Остаемся работать', type:'success'});
                active = false;
                return false;
            }
            }
        ]
    });
}


function askExit(event) {
    event.preventDefault();
    if (!active) {
        active = true;
        generateExit('center');
    }
}


var active;
var savinginprogress;


</script>


