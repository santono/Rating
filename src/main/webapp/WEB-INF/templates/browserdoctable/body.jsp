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

<%--
<link rel="stylesheet" type="text/css" href="${notybuttonscss_url}"/>
<link rel="stylesheet" type="text/css" href="${notyanimatecss_url}"/>
<link rel="stylesheet" type="text/css" href="${notyfontawesomecss_url}"/>

<script type="text/javascript" src="${notyjs_url}"></script>
<!--<script type="text/javascript" src="${notynotificationjs_url}"></script>   -->
--%>

<script type="text/javascript" src="${inputmask_url}">
    <jsp:text/>
</script>

<script type="text/javascript" src="/ad/resources/js/bootstrap-confirmation.min.js">
    <jsp:text/>
</script>


<div class="body">
    <div class="modal fade" id="recModal" tabindex="-1" role="dialog" aria-labelledby="recForm" aria-hidden="true">
        <%@ include file="../../jsp/tRDocFormBootstrap.jsp" %>
    </div>

    <div id="data_grid">
        <%@ include file="../../jsp/tRDocGrid.jsp" %>

    </div>

</div>


<%-- Start PhotoSwipe --%>
<script>
var initPhotoSwipeFromDOM = function(gallerySelector) {

// parse slide data (url, title, size ...) from DOM elements
// (children of gallerySelector)
//var parseThumbnailElements = function(el,figuresElements) {
var parseThumbnailElements = function(figuresElements) {
//var thumbElements = el.childNodes,
var thumbElements = figuresElements,
    numNodes = thumbElements.length,
    items = [],
    figureEl,
    linkEl,
    size,
    item;

    for(var i = 0; i < numNodes; i++) {
       figureEl = thumbElements[i]; // <figure> element

       // include only element nodes
       if (figureEl.nodeType !== 1) {
          continue;
       }

       linkEl = figureEl.children[0]; // <a> element

       size = linkEl.getAttribute('data-size').split('x');

// create slide object
       item = {
               src: linkEl.getAttribute('href'),
               w: parseInt(size[0], 10),
               h: parseInt(size[1], 10)
       };
       if (figureEl.children.length > 1) {
          // <figcaption> content
          item.title = figureEl.children[1].innerHTML;
       }

       if (linkEl.children.length > 0) {
// <img> thumbnail element, retrieving thumbnail url
          item.msrc = linkEl.children[0].getAttribute('src');
       }

       item.el = figureEl; // save link to element for getThumbBoundsFn
      items.push(item);
   }
   return items;
};

// find nearest parent element
    var closest = function closest(el, fn) {
        return el && ( fn(el) ? el : closest(el.parentNode, fn) );
    };
// find all children figures element
    var getChildrenFigures = function getChilderFigures(el, fn) {
        var childs=[];
        function getChilds(el,fn) {
            var childNodes=el.childNodes;
            if (!childNodes) return;
            if (childNodes.length<1) return;
            if (childNodes) {
                for (var i=0;i<childNodes.length;i++) {
                    if (childNodes[i].nodeType !== 1) {
                        continue;
                    }
                     if (fn(childNodes[i])) {
                        childs.push(childNodes[i]);
                     } else {
                         getChilds(childNodes[i],fn);
                     }
                }
            }

        }
        getChilds(el,fn);
        return childs;
    };

// triggers when user clicks on thumbnail
var onThumbnailsClick = function(e) {
    e = e || window.event;
    var eTestA = e.target || e.srcElement;
    if (eTestA)
    if (eTestA.className.toUpperCase()==='NOTIMAGE')
        return true;
    e.preventDefault ? e.preventDefault() : e.returnValue = false;

    var eTarget = e.target || e.srcElement;

// find root element of slide
    var clickedListItem = closest(eTarget, function(el) {
       return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
    });

    if (!clickedListItem) {
       return;
    }

// find index of clicked item by looping through all child nodes
// alternatively, you may define index via data- attribute
  //  var clickedGallery = clickedListItem.parentNode,
    var clickedGallery = closest(eTarget, function(el) {
        return (el.tagName && el.tagName.toUpperCase() === 'DIV'
                && el.className && el.className.toUpperCase() ==='MY-GALLERY');
    });

 //   var childNodes = clickedListItem.parentNode.childNodes,
    var childNodes=getChildrenFigures(clickedGallery,function(el) {
          return (el.tagName && el.tagName.toUpperCase() === 'FIGURE');
            });

    var numChildNodes = childNodes.length,
        nodeIndex = 0,
        index;

    for (var i = 0; i < numChildNodes; i++) {
        if (childNodes[i].nodeType !== 1) {
           continue;
        }

        if (childNodes[i] === clickedListItem) {
           index = nodeIndex;
           break;
        }
        nodeIndex++;
    }
    if(index >= 0) {
      // open PhotoSwipe if valid index found
       openPhotoSwipe( index, clickedGallery, childNodes );
    }
    return false;
};

// parse picture index and gallery index from URL (#&pid=1&gid=2)
var photoswipeParseHash = function() {
    var hash = window.location.hash.substring(1),
    params = {};

    if (hash.length < 5) {
       return params;
    }

    var vars = hash.split('&');
    for (var i = 0; i < vars.length; i++) {
        if (!vars[i]) {
           continue;
        }
        var pair = vars[i].split('=');
        if (pair.length < 2) {
           continue;
        }
        params[pair[0]] = pair[1];
    }
    if (params.gid) {
       params.gid = parseInt(params.gid, 10);
    }
    return params;
};

var openPhotoSwipe = function(index, galleryElement, figureNodes,disableAnimation, fromURL) {
    var pswpElement = document.querySelectorAll('.pswp')[0],
        gallery,
        options,
        items;
    //  items = figureNodes || parseThumbnailElements(galleryElement);
      items = parseThumbnailElements(figureNodes);

// define options (if needed)
    options = {
// define gallery index (for URL)
         galleryUID: galleryElement.getAttribute('data-pswp-uid'),

         getThumbBoundsFn: function(index) {
// See Options -> getThumbBoundsFn section of documentation for more info
              var thumbnail = items[index].el.getElementsByTagName('img')[0], // find thumbnail
              pageYScroll = window.pageYOffset || document.documentElement.scrollTop,
              rect = thumbnail.getBoundingClientRect();
              return {x:rect.left, y:rect.top + pageYScroll, w:rect.width};
         }

     };

// PhotoSwipe opened from URL
     if (fromURL) {
        if (options.galleryPIDs) {
// parse real index when custom PIDs are used
// http://photoswipe.com/documentation/faq.html#custom-pid-in-url
           for (var j = 0; j < items.length; j++) {
               if (items[j].pid == index) {
                  options.index = j;
                  break;
               }
           }
        } else {
// in URL indexes start from 1
          options.index = parseInt(index, 10) - 1;
        }
} else {
     options.index = parseInt(index, 10);
}

// exit if index not found
     if (isNaN(options.index) ) {
        return;
     }

     if(disableAnimation) {
          options.showAnimationDuration = 0;
     }

// Pass data to PhotoSwipe and initialize it
     gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
     gallery.init();
};

// loop through all gallery elements and bind events
var galleryElements = document.querySelectorAll( gallerySelector );

for(var i = 0, l = galleryElements.length; i < l; i++) {
galleryElements[i].setAttribute('data-pswp-uid', i+1);
galleryElements[i].onclick = onThumbnailsClick;
}

// Parse URL and open gallery if it contains #&pid=3&gid=1
var hashData = photoswipeParseHash();
if(hashData.pid && hashData.gid) {
openPhotoSwipe( hashData.pid ,  galleryElements[ hashData.gid - 1 ], true, true );
}
};
</script>


<%-- End PhotoSwipe   --%>

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


    //      setNumericMask();

    jQuery('.delrecbutton').on('click',

            function () {
                var id = jQuery(this).attr('idrec');
                var nam = jQuery(this).attr('namerec');
                if (confirm('Удалить строку ' + nam + '?')) {
                    var url = 'rdoc/delrec';
                    var sendData = {id:id};
                    var AJAXadr = "rdoc/delrec/" + id;
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

    initUploadDocument();
    initPhotoSwipeFromDOM('.my-gallery');

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
    var url = 'rdoc/getrec';
    var sendData = {id:id};
    jQuery("#recModal").empty();
    jQuery('.modal-title').text('Реквизиты записи');
    jQuery("#recModal").load(url, sendData, function () {
                //    currNapr=0;
                //    loadSpeci();

                setSubmitAjaxForRecForm();
                initUploadDocument();


                //          setSubmitForRecForm();
                //             setSubmitAjaxForRecForm()ForRecForm();


                jQuery("#recModal").modal();
                formvaluessave = jQuery('#recForm').serialize();
            }
    );
}


function deleteRecf(id, nam) {
    if (confirm('Удалить запись ' + nam + '?')) {
        var AJAXadr = "rdoc/delrec/" + id;
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
  <%--     return; --%>
    jQuery('#recForm').on('submit', function (event) {
                event.preventDefault();
                if (savinginprogress) {
                    return false;
                }
                savinginprogress = true
                //    this.disabled=true;
                //           jQuery("#butsubmit").attr('disabled','true');
                var str = jQuery('#recForm').serialize();
    //            var dform = jQuery(this)[0];
                var dform = jQuery(this);
                var formData = new FormData(jQuery(this)[0]);
          //      formData.append('document', jQuery('input[type=file]')[0].files[0]);
                var url = dform.attr("action");
       //         var url = "http://posttestserver.com/post.php";
    //            var url = jQuery(this).attr("action")
                console.log('Before ajax');
                jQuery.ajax({
                    url: url,
                    type: 'POST',
                    data: formData,
                    async: true,
                    cache: false,
                    contentType: false,
                    processData: false,
                    success: function (returndata) {
                        console.log('Entered in success function');
                        var content = returndata;
                        if (jQuery.isArray(returndata)) {
                            console.log(' array length=' + returndata.length);
                            if (returndata.length>0)
                               console.log('shifr=' + returndata[0].shifr);
                        } else {
                            console.log = 'data is not array';
                        }
                        if (returndata.length==0) {
                            var mes = '<div class="activity-item"> <i class="fa fa-tasks text-warning"></i> <div class="activity"> Сервер не вернул данных</div> </div>'
                            setTimeout(function () {
                                generateNoty('error', mes);
                            }, 500);

                            return;
                        }
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
                        console.log('Before exit from success function');
                        savinginprogress = false;
                    }
                });

<%--
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
--%>
                //                  jQuery('#recForm').submit();
                savinginprogress = false;
                console.log('Before returning from save');
                return false;
            }
    )
}


function setNumericMask() {
    jQuery('#value').inputmask("numeric", {
        groupSeparator:",",
        placeholder:"0",
        autoGroup:false
    });
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

function initUploadDocument() {
    jQuery('#needDocumentImport').prop('checked',false);
    jQuery("#importPanel").hide();
    jQuery('#needDocumentImport').change(function() {
        if(jQuery(this).is(":checked")) {
            jQuery("#importPanel").show();
            return;
        }
        jQuery("#importPanel").hide();
    })

}

var active;
var savinginprogress;


//    ymaps.ready(init);


</script>


