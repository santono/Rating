bodyCRUD={
    var active;
    var savinginprogress;
    var formvaluessave;
    var notification_html = [];
}
bodyCRUD.addRec=function() {
    jQuery('.modal-title').text('Реквизиты записи');
    jQuery('#id').attr('value', '0');
    jQuery('#name').attr('value', 'Запись');
    setNumericMask();
    jQuery('#recModal').modal();
}
bodyCRUD.editRec=function(id) {
    var url = 'rd/getrec';
    var sendData = {id:id};
    jQuery("#recModal").empty();
    jQuery('.modal-title').text('Реквизиты записи');
    jQuery("#recModal").load(url, sendData, function () {
                setNumericMask();
                setSubmitAjaxForRecForm();
                jQuery("#recModal").modal();
                formvaluessave = jQuery('#recForm').serialize();
            }
    );
}

bodyCRUD editCols=function() {
    jQuery('.modal-title').text('Список колонок');
    setSubmitAjaxForColForm();
    jQuery("#colModal").modal();
    forcolvaluessave = jQuery('#colForm').serialize();
}

bodyCRUD deleteRecf=function (id, nam) {
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

bodyCRUD.resetDialog=function (form) {

    form.find("input").val("");
}

bodyCRUD.setSubmitForRecForm= function () {
    jQuery('#recForm').on('submit', function () {
                var str = jQuery('#recForm').serialize();
                jQuery('#recModal').modal('hide');
                jQuery('#recForm').submit();
                return false;
            }
    )
}
bodyCRUD.setSubmitAjaxForRecForm=function() {
    jQuery('#recForm').on('submit', function (event) {
                event.preventDefault();
                if (savinginprogress) {
                    return false;
                }
                savinginprogress = true
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
                    if (content[0].shifr == 'Ok') {
                        formvaluessave = str;
                        generateNoty('information', notification_html[2]);
                        setTimeout(function () {
                            jQuery('#recModal').modal('hide');
                            window.location.reload();
                        }, 1000);
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
                savinginprogress = false;
                return false;
            }
    )
}

bodyCRUD.setSubmitAjaxForColForm= function() {
    jQuery('#colForm').on('submit', function (event) {
                event.preventDefault();
                if (savinginprogress) {
                    return false;
                }
                savinginprogress = true
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
                    savinginprogress = false;

                });
                savinginprogress = false;
            }
    )
}

bodyCRUD.setNumericMask=function() {
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


bodyCRUD.ajaxLoader=function () {
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

bodyCRUD.ajaxFinished=function() {
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

        bodyCRUD.notification_html[0] = '<div class="activity-item"> <i class="fa fa-tasks text-warning"></i> <div class="activity"> There are <a href="#">6 new tasks</a> waiting for you. Don\'t forget! <span>About 3 hours ago</span> </div> </div>',
        bodyCRUD.notification_html[1] = '<div class="activity-item"> <i class="fa fa-check text-success"></i> <div class="activity"> Данные сохранены </div> </div>',
        bodyCRUD.notification_html[2] = '<div class="activity-item"> <i class="fa text-danger"></i> <div class="activity"> Данные сохранены </div> </div>',
        bodyCRUD.notification_html[3] = '<div class="activity-item"> <i class="fa fa-shopping-cart text-success"></i> <div class="activity"> <a href="#">Eugene</a> ordered 2 copies of <a href="#">OEM license</a> <span>14 minutes ago</span> </div> </div>';

bodyCRUD.generateNoty=function (type, text) {

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

bodyCRUD.generateExit=function (layout) {
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


bodyCRUD.askExit=function (event) {
    event.preventDefault();
    if (!active) {
        active = true;
        generateExit('center');
    }
}


