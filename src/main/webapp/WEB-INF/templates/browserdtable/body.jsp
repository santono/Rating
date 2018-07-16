<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<spring:url value="/resources/js/jquery.inputmask.bundle.min.js" var="inputmask_url"/>
<spring:url value="/resources/css/noty/buttons.css" var="notybuttonscss_url"/>
<spring:url value="/resources/css/noty/animate.css" var="notyanimatecss_url"/>
<spring:url value="/resources/css/noty/font-awesome/css/font-awesome.min.css" var="notyfontawesomecss_url"/>
<spring:url value="/resources/js/noty/packaged/jquery.noty.packaged.min.js" var="notyjs_url"/>
<spring:url value="/resources/js/noty/notification_html.js" var="notynotificationjs_url"/>
<spring:url value="/resources/js/jquery.sumoselect.min.js" var="sumoselect_url" />
<spring:url value="/resources/css/sumoselect.css" var="sumoselect_css" />


<c:url var="actionUrlCol" value="rd/setcols"/>


<%--
<link rel="stylesheet" type="text/css" href="${notybuttonscss_url}"/>
<link rel="stylesheet" type="text/css" href="${notyanimatecss_url}"/>
<link rel="stylesheet" type="text/css" href="${notyfontawesomecss_url}"/>

<script type="text/javascript" src="${notyjs_url}"></script>
<!--<script type="text/javascript" src="${notynotificationjs_url}"></script>   -->
--%>
<link rel="stylesheet" type="text/css" media="screen" href="${sumoselect_css}" />


<script type="text/javascript" src="${inputmask_url}">
    <jsp:text/>
</script>

<script type="text/javascript" src="/ad/resources/js/bootstrap-confirmation.min.js">
    <jsp:text/>
</script>
<script type="text/javascript" src="${sumoselect_url}"></script>


<div class="body">

    <div class="modal fade" id="colModal" tabindex="-1" role="dialog" aria-labelledby="colForm" aria-hidden="true">
        <%@ include file="../../jsp/markColsFormBootstrap.jsp" %>
<%--
       <div class="container-fluid">
         <div class="modal-dialog modal-lg">
             <div class="modal-content">
                 <div class="modal-header">
                     <button type="button" class="close" data-dismiss="modal">
                         <span aria-hidden="true">&times;</span>
                        <span class="sr-only">Закрыть</span>
                     </button>
                 </div>
                 <form:form role="form" class="form-horizontal" acceptCharset="UTF-8" id="colForm" commandName="colsDTO" method="post" action="${actionUrlCol}">
                      <div class="modal-body">

                         <div class="form-group">
                             <label for="colSelect" class="col-sm-2 control-label">Укажите колонки</label>
                             <div class="col-sm-6">
                                <form:select class="form-control" path="cols" id="colSelect" title="Выбор колонок" multiple="true" size="10">
                                     <c:forEach items="${listRDCols}" var="item">
                                        <form:option value="${item.id}">
                                            <c:out value="${item.name}" />
                                        </form:option>
                                     </c:forEach>
                                </form:select>
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


             </div> <!-- end of modal content-->
         </div> <!-- end of modal diallog-->
       </div>  <!-- end of fluid continer-->
--%>
    </div>  <!-- /modal-->

    
    

    
    
    <div class="modal fade" id="recModal" tabindex="-1" role="dialog" aria-labelledby="recForm" aria-hidden="true">
        <%@ include file="../../jsp/tRDRowFormBootstrap.jsp" %>
    </div>






    <div id="data_grid">
        <%@ include file="../../jsp/tRDRowsGrid.jsp" %>

    </div>

</div>
<script type="text/javascript">
jQuery(document).ready(function () {

    jQuery.fn.center = function () {
        this.css("position", "absolute");
        this.css("top", ((jQuery(window).height() - this.outerHeight()) / 2) + jQuery(window).scrollTop() + "px");
        this.css("left", ((jQuery(window).width() - this.outerWidth()) / 2) + jQuery(window).scrollLeft() + "px");
        return this;
    }


    jQuery('#print').on('click',
            function () {
                jQuery('#loadingText').text("Подготовка документа к печати.");
                jQuery('#loadingDiv').show();
                return true;
            }
    )

    jQuery('#wait').on('click',
            function () {
                jQuery('#loadingText').text("Формирование документа.");
                jQuery('#loadingDiv').show();
                return true;
            }
    )

    setSubmitAjaxForRecForm();
    setSubmitAjaxForColForm();
 //   setGetCoorditabtes();
 //   setDecodeCoordinates();


    //      setNumericMask();

    jQuery('.delrecbutton').on('click',

            function () {
                var id = jQuery(this).attr('idrec');
                var nam = jQuery(this).attr('namerec');
                if (confirm('Удалить строку ' + nam + '?')) {
                    var url = 'rd/delrec';
                    var sendData = {id:id};
                    var AJAXadr = "rd/delrec/" + id;
                    jQuery.get(AJAXadr, function (result) {
                        jQuery('#finishedText').text(result);
                        jQuery('#finishedDiv').show();
                        var idt = setTimeout(function () {
                                    jQuery('#finishedDiv').hide();
                                    clearTimeout(idt);
                                }
                                , 500 * 3);

                        location.reload();
                    });
                }
            }
    )

    <%-- bs_grid  --%>
    //      jQuery("#data_grid").showgrid({

    //          ajaxFetchDataURL: "table/getgrid"

    //      })
    <%-- end of bs_grid --%>

    ajaxLoader();
    ajaxFinished();
    jQuery(window).on('unload', function () {
        jQuery('#loadingDiv').hide();
        jQuery('#finishedDiv').hide();
        //     return "Handler for .unload() called.";
    });

    active = false;
    savinginprogress = false;

    jQuery('#colSelect').SumoSelect();

    <%-- Конец инициализации jQuery--%>
});

//    function activateSelect(event){
//        event.stopPropagation();
//        return false;
//    }
function addRec() {
    jQuery('.modal-title').text('Реквизиты записи');
    jQuery('#id').attr('value', '0');
    jQuery('#name').attr('value', 'Запись');
    setNumericMask();
    jQuery('#recModal').modal();
}

function editRec(id) {
    var url = 'rd/getrec';
    var sendData = {id:id};
    jQuery("#recModal").empty();
    jQuery('.modal-title').text('Реквизиты записи');
    jQuery("#recModal").load(url, sendData, function () {
                //    currNapr=0;
                //    loadSpeci();

                setNumericMask();
                setSubmitAjaxForRecForm();
           //     setGetCoorditabtes();
           //     setDecodeCoordinates();


                //          setSubmitForRecForm();
                //             setSubmitAjaxForRecForm()ForRecForm();


                jQuery("#recModal").modal();
                formvaluessave = jQuery('#recForm').serialize();
            }
    );
}


function editCols() {
   // var url = 'rd/setcols';

 //   jQuery("#colModal").empty();
    jQuery('.modal-title').text('Список колонок');
    setSubmitAjaxForColForm();
    jQuery("#colModal").modal();
//    jQuery('#recModal').modal();

    forcolvaluessave = jQuery('#colForm').serialize();


}


function deleteRecf(id, nam) {
    if (confirm('Удалить запись ' + nam + '?')) {
        var AJAXadr = "rd/delrec/" + id;
        jQuery.get(AJAXadr, function (result) {
            jQuery('#finishedText').text(result);
            jQuery('#finishedDiv').show();
            var idt = setTimeout(function () {
                        jQuery('#finishedDiv').hide();
                        clearTimeout(idt);
                    }
                    , 500 * 3);

            location.reload();
        });
    }
}

function resetDialog(form) {

    form.find("input").val("");
}

function setSubmitForRecForm() {
    jQuery('#recForm').on('submit', function () {
                var str = jQuery('#recForm').serialize();
                jQuery('#recModal').modal('hide');
                jQuery('#recForm').submit();
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

function setSubmitAjaxForColForm() {
    jQuery('#colForm').on('submit', function (event) {
                event.preventDefault();
                if (savinginprogress) {
                    return false;
                }
                savinginprogress = true
                //    this.disabled=true;
                //           jQuery("#butsubmit").attr('disabled','true');
                var str = jQuery('#colForm').serialize();
                if (str == forcolvaluessave) {
                    var mese = '<div class="activity-item"> <i class="fa fa-tasks text-danger"></i> <div class="activity">Данные не менялись</div> </div>'
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
                            jQuery('#colModal').modal('hide');
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

        //        return false;
            }
    )
}

function setNumericMask() {
    jQuery('#value').inputmask("numeric", {
        groupSeparator:",",
        placeholder:"0",
        autoGroup:false
    });
    <!--
      jQuery('#kurs').inputmask("numeric",{
          groupSeparator: ",",
          placeholder: "0",
          autoGroup: false
      })
      jQuery('#nmbofstu').inputmask("numeric",{
          groupSeparator: ",",
          placeholder: "0",
          autoGroup: false
      })
      jQuery('#nmbOfStawok').inputmask("numeric",{
          groupSeparator: ",",
          placeholder: "0",
          autoGroup: false,
          digits: 2,
          digitsOptional: true

      })
    -->
}


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

function ajaxFinished() {
    jQuery('body').append('<div id="finishedDiv"></div>');

    jQuery('#finishedDiv')
            .append('<p id="finishedText"></p>')
        // src     .css('background', 'url(' + Xrm.Page.context.getServerUrl() + '/WebResources/new_ajax.gif) no-repeat 50% 25%')
        //  correct     .css('background', 'url(resources/images/ajaxwait.gif) no-repeat 50% 25%')
            .css('padding-top', '90px')
            .css('background-color', '#F5F5F5')
            .css('border', '3px solid #00008B')
            .css('height', '160px')
            .css('width', '300px')
            .center()
            .hide(); // изначально скрываем сообщение

    jQuery('#finishedText')
            .css('text-align', 'center')
            .css('font', '20px bolder')
            .css('font-family', 'Segoe UI, Tahoma, Arial');
}


// notification body's can be any html string or just string
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


//    ymaps.ready(init);


</script>


