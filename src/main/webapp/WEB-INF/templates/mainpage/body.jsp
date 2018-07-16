<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<spring:url value="/resources/images/crimea_3.jpg" var="crimeaMapUrl" htmlEscape="true"/>

<div class="container-fluid">
    <img src="${crimeaMapUrl}" width="100%" height="100%">
    <%--
        <div class="row">
            <div class="col-md-2 continer-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                 РайДРСУ
                            </div>
                            <div class="panel-body">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <div class="panel-title">Методички</div>
                            </div>
                            <div class="panel-body">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-10 continer-fluid">
                <div class="row">
                    <div class="col-md-12">
                    </div>
                </div>
            </div>

        </div>

    --%>
</div>


<script>
jQuery(function () {




    <%-- old my from accordion --%>
    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", ((jQuery(window).height() - this.outerHeight()) / 2) + jQuery(window).scrollTop() + "px");
        this.css("left", ((jQuery(window).width() - this.outerWidth()) / 2) + jQuery(window).scrollLeft() + "px");
        return this;
    }
    ajaxLoader();
    //       ajaxFinished();
    jQuery(window).on('unload', function () {
        jQuery('#loadingDiv').hide();
        jQuery('#finishedDiv').hide();
        //     return "Handler for .unload() called.";
    });
    jQuery('.ajaxwait').on('click',
            function () {
                jQuery('#loadingText').text("Загрузка таблицы.");
                jQuery('#loadingDiv').show();
                return true;
            }
    )

    active = false;


});
<%-- end of document ready --%>

function ajaxLoader() {
    jQuery('body').append('<div id="loadingDiv"></div>');

    jQuery('#loadingDiv')
            .append('<p id="loadingText"></p>')
        // src     .css('background', 'url(' + Xrm.Page.context.getServerUrl() + '/WebResources/new_ajax.gif) no-repeat 50% 25%')
            .css('background', 'url(resources/images/ajaxwait.gif) no-repeat 50% 25%')
            .css('padding-top', '90px')
            .css('background-color', '#F5F5F5')
            .css('border', '3px solid #00008B')
            .css('height', '160px')
            .css('width', '300px')
            .css('opacity', '0.7')
            .center()
            .hide(); // изначально скрываем сообщение

    jQuery('#loadingText')
            .css('text-align', 'center')
            .css('font', '20px bolder')
            .css('font-family', 'Segoe UI, Tahoma, Arial');
}
function viewTbl(id) {
    var url = 'table?id=' + id;
    jQuery('#loadingText').text("Загрузка таблицы.");
    jQuery('#loadingDiv').show();
    window.location.href = url;
}

function viewSpr(hre) {
    var url = hre;
    jQuery('#loadingText').text("Загрузка данных.");
    jQuery('#loadingDiv').show();
    window.location.href = url;
}

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

<sec:authorize access="hasAnyRole('ADMIN_FED','USER_FED','USER_DEP_1','USER_DEP_4')">
<c:if test="${shifrUni==0}">

function performCalcAjaxProc() {
    if (savinginprogress) {
        return false;
    }
    savinginprogress = true
    var url = jQuery('#execsum').attr('href');
    var getting = jQuery.get(url);
    // Put the results in a div
    getting.done(function (data) {
        //       var content = jQuery.parseJSON(data);
        var content = data;
        if (jQuery.isArray(data)) {
            console.log(' array length=' + data.length);
            console.log('shifr=' + data[0].shifr)
        } else {
            console.log = 'data is not array';
        }
        if (content[0].shifr == 'Ok') {
            generateNoty('information', 'Суммирование выполнено');
            setTimeout(function () {
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
        savinginprogress = false;

    });
    //                  jQuery('#recForm').submit();
    savinginprogress = false;
    return false;
}


function generateCalc(layout) {
    var n = noty({
        text:'Вы хотите суммировать данные из таблиц структурных подразделений?',
        type:'alert',
        dismissQueue:true,
        layout:layout,
        theme:'bootstrapTheme',
        buttons:[
            {addClass:'btn btn-primary', text:'Да', onClick:function (e_noty) {
                e_noty.close();
                noty({dismissQueue:true, force:true, layout:layout, theme:'bootstrapTheme', text:'Выполнение суммирования', type:'success'});
                performCalcAjaxProc();
                active = false;
                return false;
            }
            },
            {addClass:'btn btn-danger', text:'Нет', onClick:function (e_noty) {
                e_noty.close();
                noty({dismissQueue:true, force:true, layout:layout, theme:'bootstrapTheme', text:'Отказ от суммирования', type:'success'});
                active = false;
                return false;
            }
            }
        ]
    });
}


function askCalc(event) {
    event.preventDefault();
    if (!active) {
        active = true;
        generateCalc('center');
    }
}
</c:if>
</sec:authorize>
var savinginprogress;


</script>


