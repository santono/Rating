<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-ntr-form">
    <div class="ui first coupled modal" v-show="show" id="modalntr">
        <i class="close icon"></i>
        <div class="header">
            Реквизиты записи.
        </div>
        <div class="content" id="modalcontent">
            <form class="ui form" id="formntr" acceptCharset="UTF-8">
                <div class="ui top attached tabular menu">
                    <a class="item active" data-tab="first">Характеристика</a>
                    <a class="item" data-tab="second">Авторы</a>
                    <a class="item" data-tab="third">Документы</a>
                    <a class="item" data-tab="four" v-if="isadmin">Администрирование</a>
                </div>
                <div class="ui bottom attached tab segment active" data-tab="first">
                     <div class="required field">
                         <label>Уник.код.</label>
                         <p>{{editingrec.id}}</p>
<%--
                         <input v-model="editingrec.id" type="number"  placeholder="Уникальный код" min="0" max="999" />
--%>
                     </div>
                     <div class="required field">
			             <label>Название</label>
                         <p v-if="approvedrec">{{editingrec.name}}</p>
                         <div class="ui container" v-else>
                         <div class="ui search" >
                              <input class="prompt" type="text" v-model="editingrec.name" placeholder="Название работы"  maxlength="256" v-on:change="validatentrname">
                              <div class="results"></div>
                         </div>
                         </div>
<%--
                         <input v-else v-model="editingrec.name" type="text"  placeholder="Название работы"  maxlength="256" v-on:change="validatentrname"/>
--%>                     </div>
                    <div class="required field">
                        <label>Издательство</label>
                        <p v-if="approvedrec">{{editingrec.parametry}}</p>
                        <input v-else v-model="editingrec.parametry" type="text"  placeholder="Название издательства"  maxlength="256"/>
                    </div>
                    <div class="required field">
                        <label>Дата публикации</label>
                        <p v-if="approvedrec">{{editingrec.datepubl}}</p>
                        <vue-datepicker-local v-else v-model="editingrec.datepubl" clearable format="YYYY-MM"></vue-datepicker-local>
                    </div>
                </div>
                <div class="ui bottom attached tab segment" data-tab="second">
               <%--     <div>  --%>
                         <label>Авторы</label>
                    <%--     <p>{{editingrec.autrhors}}</p>  --%>
               <%--     </div> --%>
                    <authorstable v-bind:savecmd  = "savecmd"
                                  v-bind:approved = "approvedrec"
                                  v-on:esaveauthfinished="approvedSaveAuhorsFinished"
                                  v-on:authlistchanged="authlistchanged">

                    </authorstable>
                </div>
                <div class="ui bottom attached tab segment" data-tab="third">
<%--                    <div style="display:flex;">         --%>
                    <div>
                    <label>Характеристики работы</label>
                    <div>
                        <div v-if="!approvedrec" class="ui multiple selection dropdown" id="ddp">
                            <!-- This will receive comma separated value like OH,TX,WY !-->
                            <input name="states" type="hidden" >
                            <i class="dropdown icon"></i>
                            <div class="default text">Характеристики работы</div>
                            <div class="menu">
                                <div class="item" v-for="pokaz in pokazlist" v-bind:data-value="pokaz.id">{{pokaz.shortname}}</div>
                            </div>
                        </div>
                        <div v-else>
                            <ul>
                                <li v-for="p in ntrpokazlist">{{_.find(pokazlist,['id',p]).shortname}}</li>
                            </ul>
                        </div>

<%--                        
                        <p>{{editingrec.pokaz}}</p>
--%>
                    </div>
                    </div>    
                    <%--<div class="ui divider" ></div> --%>
<%--
                    <div class="fields">
                        <div class="six wide field">
                            <input type="text" autocomplete="off"
                                   v-model="newNtrDocTitle"
                                   placeholder="Описание">
                        </div>
                        <div class="six wide field">
                            <input type="file"
                                   v-model="newNtrDocfilename"
                                   placeholder="Файл документа">
                            <input type="file" id="fileElem" multiple style="display:none" onchange="handleFiles(this.files)">
                            <button class="ui icon button" id="fileSelect" v-on="">
                                <i class="cloud icon"></i>
                            </button>
                        </div>
                        <div class="four wide field">
                            <div class="ui button" v-show="this.newNtrDocfilename.length>0" v-on:click="addNtrDoc" >Добавить</div>
                        </div>
                    </div>
--%>
                    <div> <%-- second container for flex --%>
                    <div class="field" v-if="canselectfiles">
                        <input type="file" id="fileElem" multiple style="display:none" v-on:change.prevent="handleFiles">
                        <button class="ui icon button" id="fileSelect" v-on:click.prevent="clickAttach">
                            <i class="attach icon"></i>
                        </button>
                    </div>
                    <div v-if="isprogressloadingfiles">
                        <div class="ui indicating progress" data-value="1" data-total="200" id="progressupload">
                            <div class="bar">
                                <div class="progress"></div>
                            </div>
                            <div class="label"></div>
                        </div>
                    </div>

                    <div class="main" v-show="ntrdoclist.length>0" v-cloak>
                        <div class="text container">
                        <div class="ui middle aligned divided list">
                            <div class="item" v-for="doc in ntrdoclist" :key="doc.id">
<%--
                                <div class="right floated content">
                                    <div class="mini ui button"  v-on:click="removeDoc(doc)"><i class="remove icon"></i></div>
--%>
                                <div class="right floated content" v-if="!approvedrec">
                                     <a class="ui label" v-on:click.prevent="removeNtrDoc(doc)">
                                       <i class="remove icon"></i>
                                     </a>
                                </div>
                                <div class="right floated content">
                                    <a class="ui label" v-on:click="showNtrDoc(doc)">
                                       <i v-bind:class="classNtrDoc(doc)"></i>
                                    </a>
                                </div>
                                <div class="content">
                                    {{doc.filename}}
                                </div>
                            </div>
                        </div>
                        </div>
<%--
                        <div class="ui label">Документы</div>
                        <table class="ui collapsing compact table">
                            <thead>
                               <tr><th>Название</th><th>Файл</th><th></th></tr>
                            </thead>
                            <tbody>
                               <tr v-for="doc in ntrdoclist" :key="doc.id">
                                  <td>{{doc.title}}</td>
                                  <td>{{doc.filename}}</td>
                                  <td>
                                      <div class="ui button"  v-on:click="uploadDoc(doc)" ><i class="upload icon"></i></div>
                                      <div class="ui button"  v-on:click="removeDoc(doc)" ><i class="remove icon"></i></div>
                                  </td>
                               </tr>
                            </tbody>
                        </table>
--%>
                    </div>
                    </div>  <%-- end of second div in flex--%>
<%--                    </div>--%>  <%-- end of flex--%>
                </div>
                <div class="ui bottom attached tab segment" data-tab="four" v-if="isadmin">
                    <div>
                        <label>Характеристики</label>
                        <div >
                            <podrselector ref='podrslctr' v-bind:podrid  = "shifrpodr"
                                          v-on:eselpodr  = "ePodrSelected"
                                          v-on:enamepodr = "eNamePodr"></podrselector>

                        </div>
                        <div class="ui message">
                            <p v-cloak>{{namePodr}}</p>
                        </div>

                    </div>
                </div>
            <%--
                <div class="ui search selection dropdown" id="add">
                    <input name="author" type="hidden">
                    <i class="dropdown icon"></i>
                    <div class="default text">Список авторов</div>
                    <input type="text" class="search">
                </div>
--%>


            </form>
        </div>
        <div class="actions">
            <div class="ui positive button" v-show="cansave">Сохранить</div>
            <div class="ui yellow approve button" v-if="action==3">Удалить</div>
            <div class="ui black deny button">Выход</div>
        </div>
    </div>
</template>
<script>
    var ntrform=Vue.extend({
        template: '#template-ntr-form',
        data:function() {
            return {
                   savecmd       : 0         ,
                   editingrec    : {}        ,
                   namePodr      : ""        ,
                   savedrecord   : null      ,
                   localShow     : this.show ,
                   hasErrors     : false     ,
                   errlist       : null      ,
                   pokazlist     : []        ,
                   ntrpokazlist  : []        ,
                   ntrpokazlisthaschanged : false,
                   savedNtrPokazList : ''    ,
                   currentNtrPokazList: ''   ,
                   ntrdoclist    : []        ,
                   ntrdoclistforupload : []  ,
                   ntrdoclisthaschanged : false  ,
                   newNtrDocTitle    :   "",
                   newNtrDocfilename :   "",
                   savedntrdoclist : []      ,
                   isprogressloadingfiles : false,
                   fileCount	   : 0,
                   totalFileLength : 0,
                   totalUploaded   : 0,
                   filesUploaded   : 0,

                   action          : 0         ,
                   datePubl        : new Date() ,
                   range           : [new Date(),new Date()],
                   emptyTime       : '',
                   emptyRange      : [],
                   authorlisthaschanged : false,
                   ntrNameValidated : false

                }
            },
            props: {receivedrec:null,

                 show: {
                        type     : Boolean ,
                        required : true    ,
                        twoWay   : true
                 },
                 receivedaction : 0,

        },
        components: {
            'authorstable' : authorstable,
            'podrselector' : podrselector

<%--,
            'vue-datepicker-local':vue-date-picker-local
--%>
        },

        methods: {
            clickAttach:function() {
                  $('#fileElem').click();
            },
            handleFiles: function () {
                var files=window.document.getElementById("fileElem").files;
                var $progress       = $('#progressupload');
                if (!files) return;
                this.isprogressloadingfiles = true;
                this.fileCount	            = files.length;
                this.totalFileLength	    = 0;

                var vm=this;
                this.$nextTick(function() {
//                    vm.startProgress();
                    var $progress       = $('#progressupload');
                    $progress.progress('reset');
                });
                this.ntrdoclistforupload=[];
                for (var i = 0; i < files.length; i++) {
                     var file = files[i];
                     var f=file;
  //                   if (!file.type.startsWith('image/')){ continue }
                     this.totalFileLength += file.size;
                    var ntrDocEntity={
                        id         : 0                 ,
                        title      : file.name         ,
                        filename   : file.name         ,
                        comment    : file.type         ,
                        mimetype   : file.type         ,
                        imageName  : ""                ,
                        dateUploadStr : ""             ,
                        dateUpload : ""                ,
                        itIsImage  : 0                 ,
                        file       : f
                    }
                    this.ntrdoclistforupload.push(ntrDocEntity);

//                     file.name;
//                     file.size;
//                     file.type;
                }
                this.totalUploaded	=	0;
                this.filesUploaded	=	0;
                $progress.progress('reset');
                this.uploadNextAxios();

            },
            isDocImage:function(mime) {
                var retVal=false;
                if (mime)
                    if (mime.trim().length>0) {
                        if (mime.trim().toUpperCase().indexOf('IMAGE')!=-1) {
                            retVal=true;
                        }
                    }
                return retVal;
            },
            isDocPDF:function(mime) {
                var retVal=false;
                if (mime)
                    if (mime.trim().length>0) {
                        if (mime.trim().toUpperCase().indexOf('PDF')!=-1) {
                            retVal=true;
                        }
                    }
                return retVal;
            },
            finishHandleFiles:function() {
                this.isprogressloadingfiles=false;
                var cntImage=0;
                vm = this;
                for (var i=0;i<this.ntrdoclistforupload.length;i++) {
                    var isImage=vm.isDocImage(vm.ntrdoclistforupload[i].mimetype);
                    if (isImage) cntImage++;
                    var ntrDocEntity={
                        id         : vm.ntrdoclistforupload[i].id        ,
                        title      : vm.ntrdoclistforupload[i].title     ,
                        filename   : vm.ntrdoclistforupload[i].filename  ,
                        comment    : vm.ntrdoclistforupload[i].comment   ,
                        mimetype   : vm.ntrdoclistforupload[i].mimetype  ,
                        imageName  : ""                ,
                        dateUploadStr : ""             ,
                        dateUpload : new Date          ,
                        itIsImage  : isImage?1:0
                    }
                    this.ntrdoclist.push(ntrDocEntity)
                }
                this.editingrec.amntOfImages = 0 ;
                this.editingrec.amntOfDocs   = 0 ;
                for (var i=0;i<this.ntrdoclist.length;i++) {
                    this.editingrec.amntOfDocs++;
                    if (this.isDocImage(this.ntrdoclist[i].mimetype)) {
                       this.editingrec.amntOfImages++;
                    }
                }
            },
            uploadNextAxios:function() {
                var idntr = this.editingrec.id;
                var uri=this.$root.rootPath
                       +"/util/ntrdocfile/save/"
                       +idntr;
                var data  = new FormData();
                var	file  = this.ntrdoclistforupload[this.filesUploaded].file;

     //           data.append('idntr', idntr);
                data.append('file', file);
                var vm = this;
                var config = {
                    onUploadProgress: function(progressEvent) {
                        if (progressEvent.lengthComputable) {
                            var percentComplete = parseInt(
                                    (progressEvent.loaded + vm.totalUploaded) * 100.00
                                                          / vm.totalFileLength) ;
             //               var	bar	=	document.getElementById('progressupload');
                            var	bar	=$('#progressupload');
                            var cmd='set percent';
//                            console.log('amnt of progress '+$('#progressupload').length+' cmd='+cmd);
                            bar.progress(cmd,percentComplete);
                        } else {
                            console.log('error:unable to compute progress %');
                        }
                    }
                };
                axios.post(uri, data, config)
                        .then(function (res) {
                            if (_.isArray(res.data))
                               if (res.data.length==1) {
                                  var id=res.data[0].id;
                                  vm.ntrdoclistforupload[vm.filesUploaded].id=id;
                               }
                            vm.onUploadComplete();
                        })
                        .catch(function (err) {
                          alert('error uploading file '+err+" "+err.message);
                        });
            },
         	onUploadProgress:function(e) {
                if (e.lengthComputable) {
                   var percentComplete = parseInt(
                       (e.loaded + this.totalUploaded) * 100
                            /	this.totalFileLength);
                   var	bar	=	document.getElementById('progressupload');
                   bar.progress('set percent('+percentComplete+')');
                } else {
                  console.log('error:unable to compute progress %');
                }
            },
            onUploadComplete:function (e) {
                this.totalUploaded += this.ntrdoclistforupload[this.filesUploaded].file.size;
                this.filesUploaded++;
//                console.log('complete '+this.filesUploaded+" of "+this.fileCount);
//                console.log('totalUploaded: '+this.totalUploaded);
                if (this.filesUploaded<this.fileCount) {
                   this.uploadNextAxios();
                } else {
                    var	bar	=$('#progressupload');
                    var cmd='set percent';
                    var percentComplete=100;
//                    console.log('amnt onUploadComplete of progress '+$('#progressupload').length+' cmd='+cmd);
                    bar.progress(cmd,percentComplete);

                   this.finishHandleFiles();
                }
            },

            startProgress:function() {
                 var
                    $progress       = $('#progressupload');
                 var  cnt=$('#progressupload').length;
                 var amntOfTry=0;
                    // restart to zero
                 var vm=this;
                 this.initProgress();
                 if (window.fakeProgress)
                    clearInterval(window.fakeProgress);
                 $progress.progress('reset');
                 // updates every 10ms until complete
                 window.fakeProgress = setInterval(function() {
                     var
                             $progress       = $('#progressupload');
                      var  cnt=$('#progressupload').length;
                     if (cnt==1) {
                         $progress.progress('increment');
                         var value=$progress.progress('get value').toString();
                         var total=$progress.progress('get total').toString();
                      // stop incrementing when complete
                         if($progress.progress('is complete')) {
                            clearInterval(window.fakeProgress);
                            vm.finishHandleFiles();
                         }
                     } else {
                         amntOfTry++;
                         if (amntOfTry>10) {
//                             console.log('false try > 100');
                            clearInterval(window.fakeProgress);
                         }
                     }

                 }, 10);
            },
            initProgress:function() {
                $('#progressupload')
                 .progress({
                   duration : 200,
                   total    : 200,
                   text     : {
                          active: '{value} из {total} загружено'
                   }
                 });
            },
            showNtrDoc:function(doc) {
                if (this.isDocImage(doc.mimetype)) {
                    this.startPhotoSwipe(doc);
                    return false;
                }
                else
//                if (this.isDocPDF(doc.mimetype)) {
//  //                 this.startPDFViewer(doc);
//                    return true;
//                } else {
                    this.downloadFile(doc);
//                }
                return false
            },
            startPhotoSwipe:function(doc) {
                if (!this.ntrdoclist)
                    return;
                if (this.ntrdoclist.length<1)
                    return;

                var url=this.$root.rootPath
                       +"/util/ntrdoc/image/";
                var imagehrefs =[];
                var j=-1;
                var wantedIndex=0;
                for (var i=0; i<this.ntrdoclist.length; i++) {
                     if (this.isDocImage(this.ntrdoclist[i].mimetype)) {
                         j++;
                         imagehrefs.push(url+this.ntrdoclist[i].id);
                         if (this.ntrdoclist[i].id==doc.id) {
                             wantedIndex=j;
                         }
                     }
                }
                if (imagehrefs.length<1) {
                     return;
                }
                var pswpElement = document.querySelectorAll('.pswp')[0];
                var items = [];
                vm=this;
                for (var i=0;i<imagehrefs.length;i++) {
                    item={
                          src:imagehrefs[i],
                          w:1600,
                          h:1600
                    };
                   items.push(item);
                }
                var options = {
                        // optionName: 'option value'
                        // for example:
                    index: wantedIndex // start at first slide
                };

// Initializes and opens PhotoSwipe
               window.gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
               window.gallery.init();
            },
            startPDFViewer:function(doc) {
//                console.log('inside startPDFViewer');
                if (viewPDF==undefined)
//                   console.log('undefined viewPDF');
                if (!doc)
                    return;
                if (!doc.id)
                    return;
                if (doc.id<1)
                    return;
                var url=this.$root.rootPath
                       +"/util/ntrdoc/doc/"+doc.id;
                window.viewPDF(url);
            },
            downloadFile:function(doc) {
                var uri=this.$root.rootPath
                       +"/util/ntrdoc/doc/"+doc.id;
                var vm=this;
                var fileName=doc.filename;

               axios({
                    url: uri,
                    method: 'get',
                    responseType: 'blob' // important
                })
                  .then(function (response) {
                     const url = window.URL.createObjectURL(new Blob([response.data]));
                     const link = document.createElement('a');
                     link.href = url;
                     link.setAttribute('download', fileName);
                     document.body.appendChild(link);
                     link.click();
                }).catch(function(error){
                    console.log("error downloading file "+error);
                });
            },
            disabledDate: function (time) {
                    var day = time.getDay();
                    return day === 0 || day === 6;
            },
            authlistchanged:function() {
//                console.log("ntrform: authlistchanged")
                this.authorlisthaschanged=true;
            },
            approved: function() {
                this.hasErrors=false;
                this.saveRec();
                return false;
            },
            approvedSaveAuhors:function() {
                console.log(' inside approvedSaveAuhors');
                this.savecmd=1;
            },
            approvedSaveAuhorsFinished:function() {
                this.savecmd=0;
//                this.approvedFinish();
                this.saveNtrDocListForRec();
            },
            approvedSaveNtrDocFinished:function() {
                this.approvedFinish();
            },
            removeNtrDoc:function(doc) {
                vm = this;
                if (doc)
                if (doc.id>0) {
//                if (confirm('Удалить '+doc.filename)) {
//                    this.removeNtrDocOnServer(doc.id);
//                }
                    vm=this;
                    alertify.confirm('Запрос на удаление', 'Удалить '+doc.filename
                        , function(){ vm.removeNtrDocOnServer(doc.id); }
                        , function(){ /*alertify.error('Cancel')*/})
                        .set('labels', {ok:'Удалить', cancel:'Нет'})
                        .set({transition:'flipy'});
                }


            },
            removeNtrDocFinished:function(id) {
                if (this.ntrdoclist)
                if (this.ntrdoclist.length>0)
                for(var i=0;i<this.ntrdoclist.length;i++) {
                   if (this.ntrdoclist[i].id==id)
                       this.ntrdoclist.splice(i,1);
                }
                this.editingrec.amntOfDocs   = 0;
                this.editingrec.amntOfImages = 0;
                for (var i=0;i<this.ntrdoclist.length;i++) {
                    this.editingrec.amntOfDocs++;
                    if (this.isDocImage(this.ntrdoclist[i].mimetype)) {
                        this.editingrec.amntOfImages++;
                    }
                }

            },
            approvedFinish:function() {
                console.log('approvedFinish');
                this.savecmd   = 0   ;
                this.localShow = false;
                this.$emit('eshowmodal',false,this.editingrec);
                if (this.control) {
                    this.control.modal('hide');
                }
                alertify.set('notifier','position', 'top-center');
                alertify.success("Запись сохранена.");
            },
            savePokazListForRecFinish:function() {
                alertify.set('notifier','position', 'top-center');
                alertify.success("Список показателей н-т. работы сохранен.");
//                this.approvedFinish();
//                this.$notify("alert", "Список показателей работы сохранен.");
                this.approvedSaveAuhors();
            },
            deletedFinish:function() {
                this.localShow = false;
                this.$emit('edelrec',this.editingrec);
                if (this.control) {
                    this.control.modal('hide');
                }

            },
            deny: function() {
                this.localShow = false;
                this.$emit('eshowmodal',false);
                return true;
            },
            formatDate:function (date) {
                  var d = new Date(date),
                  month = '' + (d.getMonth() + 1),
                    day = '' + d.getDate(),
                   year = d.getFullYear();

                  if (month.length < 2) month = '0' + month;
                  if (day.length < 2) day = '0' + day;
                  return [year, month, day].join('-');
            },
            ePodrSelected:function(id) {
                //            alertify.alert('id='+id+' e='+e);
<%--
                if (id>0 && this.editingrec.shifrPre!=id) {
                    this.editingrec.shifrPre=id;

                    this.getPodrCompoundName(id);
//                    this.getUnivFacKafForShifrPodr(id);
                }
--%>
//                console.log('ntrform ePodrSelected id='+id);
                if (id>0 && this.editingrec.shifrPre!=id) {
//                    console.log('ntrform inside ePodrSelected this.editingrec.shifrPre'+id);
                    this.editingrec.shifrPre=id;

//                    this.getPodrCompoundName(id);
                    this.$refs.podrslctr.getPodrCompoundName(id);
                }


            },
            eNamePodr:function(nameVal) {
//                console.log('eNamePodr nameVal='+nameVal);
                if (nameVal && _.isString(nameVal))
                    this.namePodr=nameVal;
            },
            setPokazOnForm:function() {
//                console.log('enter in setPokazOnForm this.ntrpokazlist.length='+this.ntrpokazlist.length);
                if (this.controltab)
                    if (this.localShow) {
                        var vm=this;
                        var iCount=0;
                        $('#ddp').dropdown();
                        var ar=[];
                        if (this.ntrpokazlist.length>0) {
                            _.forEach(this.ntrpokazlist,function(value) {
                                ar.push(value.toString());
                            });
                            $('#ddp').dropdown('clear').dropdown('set selected', ar);
                        } else {
                            $('#ddp').dropdown('clear');
                        }
                        if (this.approvedrec)
                            $('#ddp').dropdown('hide');
                        this.savedNtrPokazList=$('#ddp').dropdown('get value');
                        this.currentNtrPokazList=this.savedNtrPokazList;
                        $('#ddp').dropdown({
                                    onChange: function(value, text, $selectedItem) {
                                        iCount++;
//                                    if ((iCount>0) && (savedValue!=value)) {
                                        vm.$nextTick(function() {
//                                       vm.currentRoles=$('#rdd').dropdown('get value');
                                            vm.currentNtrPokazList=$('#ddp').dropdown('get value');
                                            vm.ntrpokazlisthaschanged=true;
//                                            console.log('onChange savedPokazList='+vt.savedNtrPokazList+" currentNtrPokazList="+vt.currentNtrPokazList);
                                        });
//                                    if (vt.savedRoles!=value) {
//                                        vt.canSaveRoles=true;
//                                    }
//                                    savedValue=value;
//                                    alert("value="+value+" text="+text+" $selectedItem"+$selectedItem+" iCount="+iCount);
                                        // custom action
                                    }}
                        );


//                            var a;
//                a=$('#rdd').dropdown('get value');
//                            alert('a='+a);
//                            this.eSaveRoles();
                    }
            },
            validatentrname:function() {
              this.ntrNameValidated=false;
              this.checkNameAjax();
            },
            finishnamechecking:function(id,name,authors) {
                this.ntrNameValidated=true;
                if (arguments.length!=3) return;
                if ((id>0)
                    && (this.editingrec.id>0)
                    && (id!=this.editingrec.id)) {
                    this.ntrNameValidated=false;
                    var mes='В БД есть н-т работа под названием <b>'+name.trim()+'</b>';
                    if (authors && authors.length>2)
                        mes=mes+' Авторы '+authors;
                    else
                        mes=mes+' Авторство не указано';
                    alertify.alert('Ошибка',mes);
                }
                return;
            },
            saveRec:function() {
                    var uri=this.$root.rootPath+"/util/ntr/save";
                    var savingRec=_.clone(this.editingrec);
                    var d="";
                    if (savingRec.datepubl)
                    if (_.isDate(savingRec.datepubl))
                    if (savingRec.datepubl.getFullYear()>2000) {
                        d=this.formatDate(savingRec.datepubl);
                    }
                    delete savingRec.datepubl;
                    delete savingRec.amntOfImages;
                    delete savingRec.dataapproved;
                    delete savingRec.fioapproved;
                    savingRec.datepubl=d;
//                    console.log(JSON.stringify(savingRec));

                    var objRec  = JSON.stringify(savingRec);
                    var vm       = this;
                    axios.post(uri, objRec,
                            {headers:{
                                'Content-Type': 'application/json'}}
                    )
                            .then(function(response){
                                if (response.data) {
                                    if ((response.data.length==1) && (response.data[0].name=='Ok')) {
                                        if (vm.action==1) {
                                            vm.editingrec.id=parseInt(response.data[0].id);
                                        }
//                                        vm.approvedFinish();
                                          vm.savePokazListForRec();
                                          vm.ntrpokazlisthaschanged=false;
//                                        vm.$notify("alert", "Запись сохранена.");
                                    } else {
                                        if (response.data.length>0) {
                                            var z=$('#modalntr').css('z-index');
//                                    vm.hasErrors=true;
                                            var cont='<div class="ui error message"><ul>';
                                            for (var i=0; i<=response.data.length-1;i++) {
                                                var mes=response.data[i].name;
                                                vm.$nextTick(function () {
                                                    vm.$notify("alert",mes,"error");
                                                });
                                                cont=cont+"<li>"+mes+"</li>";
//                                        vm.errlist.push({'message':response.data[i].name});
                                            }
                                            cont=cont+"</ul></div>"
                                            $('#secondmodalcontent').html(cont);
                                            $('#secondmodal').css('z-index',z+10);
                                            $('#secondmodal').modal('show');
                                            $('#secondmodal').modal('refresh');
                                        }
                                    }
                                }
                            })
                            .catch (function(error){
                                vm.$notify("alert", "Ошибка сохранения записи. "+error, "error");
                    });
            },
            savePokazListForRec:function() {
//                console.log('inside savePokazListForRec');
<%--
                <c:url value="/util/ntrpokaz/save" var="uri2" />
                var uri = "${uri2}";
--%>                
                var uri = this.$root.rootPath
                        + "/util/ntrpokaz/save"; 
                var pl  = $("#ddp").dropdown("get value");
//                console.log('pl='+pl);
                if (!(pl&&pl.length>0)) {
                    pl="";
                }
//                console.log('pl second='+pl);
//                console.log(JSON.stringify(savingRec));
//                var objRec  = JSON.stringify(savingRec);
                var vm       = this;
//                console.log('vt passed id='+this.editingrec.id);
                var param    = 'id='+this.editingrec.id+'&pokaz='+pl;
//                console.log('before axios param='+param);
                axios.post(uri, param,
                        {})
                        .then(function(response){
//                            console.log('inside then'+param);
                            if (response.data) {
                                if ((response.data.length==1)
                                        && (response.data[0].name=='Ok')) {
                                    vm.savePokazListForRecFinish();
                                } else {
                                    if (response.data.length>0) {
                                        var z=$('#modalntr').css('z-index');
                                        var cont='<div class="ui error message"><ul>';
                                        for (var i=0; i<=response.data.length-1;i++) {
                                            var mes=response.data[i].name;
                                            vm.$nextTick(function () {
                                                vm.$notify("alert",mes,"error");
                                            });
                                            cont=cont+"<li>"+mes+"</li>";
                                        }
                                        cont=cont+"</ul></div>"
                                        $('#secondmodalcontent').html(cont);
                                        $('#secondmodal').css('z-index',z+10);
                                        $('#secondmodal').modal('show');
                                        $('#secondmodal').modal('refresh');
                                    }
                                }
                            }
                        })
                        .catch (function(error){
                           alert("Ошибка сохранения списка характеристик работы. "+error, "error");
                });
            },
            checkNameAjax:function() {
                var uri=this.$root.rootPath+"/util/ntr/chk";
                var nameRec={id   : this.editingrec.id,
                             name : this.editingrec.name
                };

                var objRec  = JSON.stringify(nameRec);
                var vm       = this;
                axios.post(uri, objRec,
                        {headers:{
                            'Content-Type': 'application/json'}}
                )
                        .then(function(response){
                            var err=true;
                            if (response.data) {
                                if ((response.data.length==1) && (response.data[0].id>0)) {
                                    err=false;
                                    vm.finishnamechecking(response.data[0].id,response.data[0].name,response.data[0].shifr);
                                }
                                else
                                if ((response.data.length==1) && (response.data[0].id==0)) {
                                    err=false;
                                    vm.finishnamechecking();
                                }
                            }
                            if (err) {
                                alertify.set('notifier','position', 'top-center');
                                alertify.error('Ошибка проверка корректности названия работы ',0);
                            }
                        })

                        .catch (function(error){
                    alertify.set('notifier','position', 'top-center');
                    alertify.error('Ошибка проверка корректности названия работы '+error,0);
                });
            },

            saveNtrDocListForRec:function() {
//                console.log('inside savePokazListForRec');
<%--
                <c:url value="/util/ntrdoclist/save" var="uri2" />
                var uri = "${uri2}";
--%>                
                var uri=this.$root.rootPath
                       +"/util/ntrdoclist/save";
                var pl  = "";
                if (this.ntrdoclist)
                   for (var i=0;i<this.ntrdoclist.length;i++) {
                       if (this.ntrdoclist[i].id)
                          if (this.ntrdoclist[i].id>0) {
                             if (i>0) pl=pl+',';
                             pl=pl+this.ntrdoclist[i].id;
                          }
                   }
                var vm       = this;
//                console.log('vt passed id='+this.editingrec.id);
                var param    = 'id='+this.editingrec.id+'&docids='+pl;
//                console.log('before axios param='+param);
                axios.post(uri, param,
                        {})
                        .then(function(response){
//                            console.log('inside then'+param);
                            if (response.data) {
                                if ((response.data.length==1)
                                        && (response.data[0].name=='Ok')) {
                                    vm.approvedSaveNtrDocFinished();
                                } else {
                                    if (response.data.length>0) {
                                        var z=$('#modalntr').css('z-index');
                                        var cont='<div class="ui error message"><ul>';
                                        for (var i=0; i<=response.data.length-1;i++) {
                                            var mes=response.data[i].name;
                                            vm.$nextTick(function () {
                                                vm.$notify("alert",mes,"error");
                                            });
                                            cont=cont+"<li>"+mes+"</li>";
                                        }
                                        cont=cont+"</ul></div>"
                                        $('#secondmodalcontent').html(cont);
                                        $('#secondmodal').css('z-index',z+10);
                                        $('#secondmodal').modal('show');
                                        $('#secondmodal').modal('refresh');
                                    }
                                }
                            }
                        })
                        .catch (function(error){
                           alert("Ошибка сохранения номеров записей списка документов работы. "+error, "error");
                });
            },
            removeNtrDocOnServer:function(iddoc) {
<%--
                <c:url value="/util/ntrdoc/del" var="uri2" />
                var uri1 = "${uri2}";
                var uri = uri1+"/"+iddoc;
--%>
                var uri = this.$root.rootPath
                        + "/util/ntrdoc/del/"+iddoc;
                var vm  = this;
                var saviddoc = iddoc;
                axios.post(uri, {},
                        {})
                        .then(function(response){
                            if (response.data) {
                                if ((response.data.length==1)
                                        && (response.data[0].name=='Ok')) {
                                    vm.removeNtrDocFinished(saviddoc);
                                } else {
                                    if (response.data.length>0) {
                                        var z=$('#modalntr').css('z-index');
                                        var cont='<div class="ui error message"><ul>';
                                        for (var i=0; i<=response.data.length-1;i++) {
                                            var mes=response.data[i].name;
                                            vm.$nextTick(function () {
                                                vm.$notify("alert",mes,"error");
                                            });
                                            cont=cont+"<li>"+mes+"</li>";
                                        }
                                        cont=cont+"</ul></div>"
                                        $('#secondmodalcontent').html(cont);
                                        $('#secondmodal').css('z-index',z+10);
                                        $('#secondmodal').modal('show');
                                        $('#secondmodal').modal('refresh');
                                    }
                                }
                            }
                        })
                        .catch (function(error){
                             alert("Ошибка удаления документа для статьи.  "+error, "error");
                });
            },

            getPokazList:function() {
//                console.log("enter in getPokazList()");
<%--
                <c:url value = "/util/pokazs" var="uri2" />
                var uri3     = "${uri2}";
                var uri      = uri3;
--%>
                var uri = this.$root.rootPath
                        + "/util/pokazs";
                var vm       = this;
                var finished = false;
                axios.get(uri, {
                })
                        .then(function (response) {
                            finished=true;
                            vm.pokazlist=response.data;
//                            vm.performSortingPokaz();
//                            alert('shifrpre='+vt.shifrpre+' amnt of user '+vt.userslist.length);
                        })

                        .catch(function (error) {
                    alert('error reading pokazlist='+error);
                    finished=true;
                });

            },
            getNtrPokazList: function() {
                if (this.editingrec.id==undefined) {
                    return
                }
                if (!this.editingrec) {
                    return
                }
                if (!this.editingrec.id) {
                    return
                }
<%--
                <c:url value="/util/ntrpokazlist" var="uri2" />
                var uri3="${uri2}"+"/"+this.editingrec.id;
                var uri=uri3;
--%>
                var uri = this.$root.rootPath
                        + "/util/ntrpokazlist/"
                        + this.editingrec.id;
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
                            //  var rrec=JSON.stringify(response.data);
                            var rrec=response.data;
                            if (_.isArray(rrec)) {
                                if (rrec[0].shifr=='Err') {}
                                else   {
                                    vm.ntrpokazlist=[];
//                                    console.log('rrec='+JSON.stringify(rrec));
                                    if (rrec.length>0)
                                       if (rrec[0].shifr!="Empty")
                                       for (var i=0;i<rrec.length;i++) {
                                           vm.ntrpokazlist.push(rrec[i].id);
                                       }
//                                    console.log('vt.ntrpokazlist.length='+vt.ntrpokazlist.length);
                                    vm.setPokazOnForm();
                                }
                            } else {
                                alert("error getting pokaz list for ntr ");
                            }
                        })

                        .catch(function (error) {
                    alert("error getting pokaz list for ntr="+error);
                });
            },
            addNtrDoc:function() {
                var id1        = this.editingrec.id;
                var title1     = this.newNtrDocTitle;
                var filename1 = this.newNtrDocfilename;
                ntrDocEntity={
                   id         : id1               ,
                   title      : title1            ,
                   filename   : filename1         ,
                   comment    : ""                ,
                   imageName  : ""                ,
                   dateUploadStr : ""             ,
                   dateUpload : ""                ,
                   itIsImage  : 0
                }
                this.ntrdoclist.push(ntrDocEntity);
                newNtrDocTitle    = "";
                newNtrDocfilename = "";
            },
            getNtrDocList: function() {
                if (this.editingrec.id==undefined) {
                    return
                }
                if (!this.editingrec) {
                    return
                }
                if (!this.editingrec.id) {
                    return
                }
<%--
                <c:url value="/util/ntrdoclist" var="uri2" />
                var uri3="${uri2}"+"/"+this.editingrec.id;
                var uri=uri3;
--%>
                var uri = this.$root.rootPath
                        + "/util/ntrdoclist/"
                        + this.editingrec.id;
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
                            //  var rrec=JSON.stringify(response.data);
                            var rrec=response.data;
                            if (_.isArray(rrec)) {
                                if (rrec[0].comment=='Error') {}
                                else   {
                                    vm.ntrdoclist=[];
//                                    console.log('rrec='+JSON.stringify(rrec));
                                    if (rrec.length>0)
                                        if (rrec[0].comment!="Empty")
                                            for (var i=0;i<rrec.length;i++) {
                                                vm.ntrdoclist.push(rrec[i]);
                                            }
//                                    console.log('vt.ntrdoclist.length='+vt.ntrdoclist.length);
                                }
                            } else {
                                alert("error getting doc list for ntr ");
                            }
                        })

                        .catch(function (error) {
                    alert("error getting doc list for ntr="+error);
                });
            },

            getEntity: function() {
                var uri = this.$root.rootPath
                        + "/util/ntr/"+this.receivedrec.id;
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
                            var rrec=JSON.stringify(response.data);
                            vm.$nextTick(function () {
                                vm.editingrec=JSON.parse(rrec);
                                vm.namePodr="";
                                vm.ntrNameValidated=true;
                                if (vm.editingrec.datepubl) {
                                    if (vm.editingrec.datepubl.trim().length==0) {
                                        vm.editingrec.datepubl=null;
                                    } else {
                                        var s=vm.editingrec.datepubl;
                                        var dt=new Date(s);
                                        vm.editingrec.datepubl=new Date(s);
                                    }
                                } else {
                                    vm.editingrec.datepubl=null;
                                }
                                delete vm.editingrec.datePublJava;
//                                console.log('received ntrRec='+vt.editingrec.toString());
                                vm.savedrecord=_.clone(vm.editingrec);
//                                alert('before getUserRolesList');
                                vm.getNtrPokazList();
                                vm.getNtrDocList();
                                if (vm.editingrec.shifrPre && _.isInteger(vm.editingrec.shifrPre) && vm.editingrec.shifrPre>0)
                                   vm.$refs.podrslctr.getPodrCompoundName(vm.editingrec.shifrPre);
                            });
                        })

                        .catch(function (error) {
                    alert("error getEntity="+error);
                });
            },
            getPodrCompoundName: function(id) {
                var uri = this.$root.rootPath+"/util/prcname/"+id;
                var vm  = this ;
                axios.get(uri, {
                })
                        .then(function (response) {
                            var rrec=JSON.stringify(response.data);
                            var r=JSON.parse(rrec);
//                            console.log('r[0].id='+r[0].id);
                            if (r[0].id==0) {
                                vm.namePodr=r[0].name;
                            } else {
                                alertify.alert("Ошибка","Ошибка получения имени подразделения");
                            }
                        })

                        .catch(function (error) {
//                    alert("error getEntity="+error);
                    alertify.alert("Ошибка","Ошибка получения имени подразделения "+error);
                });
            },

            deleteRec: function(rec){
            },
            fillEmptyRec:function() {
                this.editingrec={
                     id               : 0  ,
                     authors          : "" ,
                     name             : "" ,
                     parametry        : "" ,
                     datepubl         : new Date() ,
                     amntOfImages     : 0,
                     amntOfDocs       : 0,
                     shifrPre         : 0
                },
                this.ntrpokazlist           = [];
                this.ntrpokazlisthaschanged = false;
                this.authorlisthaschanged   = false;
                this.ntrdoclist             = [];
                this.ntrdoclisthaschanged   = false;
                this.ntrNameValidated       = false;
            },
            classNtrDoc: function (d) {
                if (!d.filename) {
                    return {};
                }
                if (!d) {
//                   console.log("doc is empty");
                }
                else {
                   var dd=d;
                   var ds=JSON.stringify(dd);
//                   console.log("doc= is present "+ds);
                }
                var outObj={};
                if (
                    dd.filename.toUpperCase().endsWith('JPEG') ||
                    dd.filename.toUpperCase().endsWith('JPG')  ||
                    dd.filename.toUpperCase().endsWith('GIF')  ||
                    dd.filename.toUpperCase().endsWith('PNG')
                   ) {
                       outObj={'file image outline icon':true};
                }
                else
                if (
                    dd.filename.toUpperCase().endsWith('PDF')
                   ) {
//                       console.log('isPDF');
                       outObj={'file pdf outline icon':true};
                }
                else
                if (
                     dd.filename.toUpperCase().endsWith('DOCX') ||
                     dd.filename.toUpperCase().endsWith('DOC')
                   ) {
                       outObj={'file word outline icon':true};
                }
                else
                if (
                     dd.filename.toUpperCase().endsWith('XLSX') ||
                     dd.filename.toUpperCase().endsWith('XLS')
                    ) {
                        outObj={'file excel outline icon':true};
                }
                else
                if (
                     dd.filename.toUpperCase().endsWith('TXT')
                   ) {
                        outObj={'file text outline icon':true};
                }
                else
                    outObj={'file outline icon':true};
                return outObj;

            },
            initstartingrec:function() {
//                alert(JSON.stringify(val));

//                console.log('ntrform: initstartingrec this.action='+this.action+' received action='+this.receivedaction);
//                if (val) {
                    if (this.action==0)
                        this.action=val.id<1?1:2;
                    if (this.action==1) {
                        this.fillEmptyRec();
                    } else {
//                        this.editingrec=_.clone(val);
//                        console.log('ntrform: before getEntity');
                        this.getEntity();

                    }
                    this.savedrecord=_.clone(this.editingrec);
                    this.authorlisthaschanged   = false;
                    this.ntrpokazlisthaschanged = false;
                    this.ntrdoclisthaschanged   = false;
//                }
            }


        },
        computed:{
            cansave:function() {
//                console.log('ntrform computed cansave');
                var retVal=false;
                if (!this.ntrNameValidated)
                    return retVal;
                if ((this.action==1) || (this.action==2))
                   retVal = !_.isEqual(this.editingrec,this.savedrecord);
//                console.log('ntrform computed retVal='+retVal+'this.auhorlisthaschanged='+this.authorlisthaschanged);
                if (!retVal) {
//                    console.log('ntrform computed this.auhorlisthaschanged='+this.authorlisthaschanged);
                    if (this.authorlisthaschanged) {
                        retVal=true;
                    }
                }
                if (!retVal) {
//                    console.log('ntrform computed this.ntrpokazlisthaschanged='+this.ntrpokazlisthaschanged);
                    if (this.ntrpokazlisthaschanged) {
                        retVal=true;
                    }
                }
                if (this.receivedrec) {
                    var approved=this.receivedrec.approved&&this.receivedrec.approved.trim().length>1?true:false;
                    if (approved)
                       retVal=false;
                }
                return retVal;
            },
            canselectfiles:function() {
                if (this.approvedrec) return false;
                if (this.isprogressloadingfiles)
                   return false;
                else
                   return true;
            },
            approvedrec:function() {
                 var retVal;
                 retVal=false;
                 if (this.receivedrec)  {
//                     console.log('computed this.receivedrec passed');
//                     console.log('computed this.receivedrec.approved='+this.receivedrec.approved);
//                     console.log('computed '+JSON.stringify(this.receivedrec));

                 if (this.receivedrec.approved&&this.receivedrec.approved.trim().length>1)
                     retVal=true;
                 }
//                 console.log('computed approved='+retVal);
                 return retVal;
            },
            shifrpodr:function() {
                var retval=0;
                if (this.editingrec.shifrPre && this.editingrec.shifrPre>0)
                    retval = _.toInteger(this.editingrec.shifrPre);
                else
                    retVal=1;
                return retval;
            },

            isnpr:function() {
                var retval;
                retval=this.$parent.isnpr;
                return retval;
            },
            isadmin:function() {
                var retval;
                retval=this.$parent.isadmin;
                return retval;
            }
        },
        watch: {
            receivedaction:function(val){
                this.action=val;
            },
            receivedrec:function(val) {
//                alert(JSON.stringify(val));

//                console.log('ntrform: recievedrec this.action='+this.action+' received action='+this.receivedaction);
                if (val) {
                    if (this.action==0)
                       this.action=val.id<1?1:2;
                    if (this.action==1) {
                        this.fillEmptyRec();
                    } else {
//                        this.editingrec=_.clone(val);
//                        console.log('ntrform: before getEntity');
//                        this.getEntity();

                    }
                    this.savedrecord=_.clone(this.editingrec);
                    this.authorlisthaschanged   = false;
                    this.ntrpokazlisthaschanged = false;
                    this.ntrdoclisthaschanged   = false;
                }

            },
            editingrec :function(val){
//                console.log('watch changed editingrec='+val);
            },
            show:function(val) {
                this.localShow=val;
            },
            localShow: function(val) {
//               console.log('ntrform: localShow val='+val);
               if (this.control) {
                if (val) {
                    this.initstartingrec();
                    var vm=this;
                    this.$nextTick(function () {
                           vm.control.modal('show');
                        if (vm.controltab) {
                            vm.controltab.tab();
                        }
//                           vm.setAuthorsDDOnForm();
                        }
                    )
                } else {
//                   this.receivedrec={};
                   this.editingrec.id=this.editingrec.id=Math.floor(Math.random()*(10000000-1+1)+1);
                }
               } else {
                  alert('in show this.control is undefined');
               }
            }
        },

        mounted:function() {
            if (!this.control)
              this.$nextTick(function () {
                  var vc=this;
                  $('.coupled.modal')
                          .modal({
                              allowMultiple: true
                          });
                  vm=this;
                  this.control = $('#modalntr').modal({
                     onApprove: function() {return vm.approved()}.bind(vm),
                     onDeny   : function() {return vm.deny()}.bind(vm),
                     onHidden : function() {vm.localShow = false;vm.$emit('eshowmodal',false)}.bind(vm)
                  });
                  this.controltab=$('.menu .item');
//                  console.log("before getPokazList()");
                  this.getPokazList();
                  $('#ddp')
                    .dropdown()
                  ;
                  var uri=this.$root.rootPath
                          +"/util/semanticui/search?q={query}";


                  $('.ui .search').search({
                              apiSettings: {
//                                  url: '//api.github.com/search/repositories?q={query}'
                                  url: uri
                              },
                              fields: {
                                  results     : 'items',
                                  title       : 'title',
                                  description : 'description'
                              },
                              minCharacters : 3,
                              fullTextSearch: false,
                              error: {
                                  noResults: 'В БД нет н-т работ, название которых начинается с указанной Вами фразы.'
                              },
                              onSelect:function(result, response) {
                                  return false;
                              }
                          }

                  );
              })
        }


    });
</script>