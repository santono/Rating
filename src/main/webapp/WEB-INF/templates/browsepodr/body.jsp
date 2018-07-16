<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<spring:url value="resources/js/jquery.inputmask.bundle.min.js" var="inputmask_url" />
<script type="text/javascript" src="${inputmask_url}"><jsp:text /></script>
<!--
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal.userDTO.namePodr" var="nkaf" />
</sec:authorize>
-->
<script type="text/javascript" src="resources/js/bootstrap-confirmation.min.js"><jsp:text /></script>


<c:set value="Список структурных подразделений." var="cpt" />

<div class="body">
    <div class="modal fade" id="recModal" tabindex="-1" role="dialog" aria-labelledby="recForm" aria-hidden="true">
        <%@ include file="../../jsp/podrFormBootstrap.jsp"%>
    </div>

    <!--  Just create a div and give it an ID -->

    <div id="data_grid">
        <%@ include file="../../jsp/podrGrid.jsp"%>
    </div>

</div>
<script  type="text/javascript">
    jQuery(document).ready(function() {


//        setSubmitForRecForm();
        setSubmitAjaxForRecForm();


        setNumericMask();

        jQuery('.delrecbutton').on('click',

                function () {
                    var id  = jQuery(this).attr('idRec');
                    var nam = jQuery(this).attr('nameRec');
                    if (confirm('Удалить запись '+nam+'?')) {
                        var AJAXadr="podr/delrec/" + id;
                        jQuery.get(AJAXadr, function(result) {
                            var mes='<div class="activity-item"> <i class="fa text-danger"></i> <div class="activity">Запись '+nam.trim()+' уделена</div> </div>';
                            generateNoty('information', mes);
                            setTimeout(function() {
                                window.location.reload();
                            }, 1000);


                            location.reload();
                        });
                    }
                }
        )

        <%-- bs_grid  --%>
        <%--
        jQuery("#data_grid").showgrid({

            ajaxFetchDataURL: "university/getgrid"

        })
        --%>
        <%-- end of bs_grid --%>
        active = false;
        savinginprogress = false;

    });
    function addRec() {
        editRec(0);
<%--
        jQuery('.modal-title').text('Реквизиты записи');
        jQuery('#id').attr('value','0');
        jQuery('#name').attr('value','');
        setNumericMask();
        jQuery('#recModal').modal();
--%>
    }

    function editRec(id) {
        var url='podr/getrec';
        var sendData={id:id};
        jQuery("#recModal").empty();
        jQuery('.modal-title').text('Реквизиты записи');
        jQuery("#recModal").load(url,sendData,function() {
                    //    currNapr=0;
                    //    loadSpeci();
                    setNumericMask();
            //        setSubmitForRecForm();
                    setSubmitAjaxForRecForm();

                    jQuery("#recModal").modal();
                }
        );
    }



    function deleteRec(id,nam) {
        if (confirm('Удалить запись '+nam+'?')) {
            var sendData={id:id};
            var AJAXadr="podr/delrec/" + id;
            jQuery.get(AJAXadr, function(result) {
                location.reload();
            });
        }
    }

    function resetDialog(form) {

        form.find("input").val("");
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


/*
    function setSubmitForRecForm() {
        jQuery('#recForm').on('submit',function() {
                    var str = jQuery('#recForm').serialize();
                    jQuery('#recModal').modal('hide');
                    jQuery('#recForm').submit();
                    return false;
                }
        )
    }
*/
    function setNumericMask() {
        <!--
          jQuery('#yearPri').inputmask("numeric",{
              groupSeparator: ",",
              placeholder: "0",
              autoGroup: false
          })
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

    var formvaluessave;
    //   var savinginprogress;
    var notification_html = [];

    notification_html[0] = '<div class="activity-item"> <i class="fa fa-tasks text-warning"></i> <div class="activity"> There are <a href="#">6 new tasks</a> waiting for you. Don\'t forget! <span>About 3 hours ago</span> </div> </div>',
            notification_html[1] = '<div class="activity-item"> <i class="fa fa-check text-success"></i> <div class="activity"> Данные сохранены </div> </div>',
            notification_html[2] = '<div class="activity-item"> <i class="fa text-danger"></i> <div class="activity"> Данные сохранены </div> </div>',
            notification_html[3] = '<div class="activity-item"> <i class="fa fa-shopping-cart text-success"></i> <div class="activity"> <a href="#">Eugene</a> ordered 2 copies of <a href="#">OEM license</a> <span>14 minutes ago</span> </div> </div>';



    function generateNoty(type, text) {

        var n = noty({
            text        : text,
            type        : type,
            dismissQueue: true,
            layout      : 'topLeft',
            closeWith   : ['click'],
            theme       : 'relax',
            maxVisible  : 10,
            animation   : {
                open  : 'animated bounceInLeft',
                close : 'animated bounceOutLeft',
                easing: 'swing',
                speed : 500
            }
        });
        //   console.log('html: ' + n.options.id);
    }

    var active;
    var savinginprogress;

</script>


