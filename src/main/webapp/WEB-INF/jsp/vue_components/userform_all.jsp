<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-user-form">
    <div class="ui first coupled modal" v-show="show" id="modaluser">
        <i class="close icon"></i>
        <div class="header">
            Реквизиты записи.
        </div>
        <div class="content" id="modalcontent">
            <form class="ui form" id="formuser" acceptCharset="UTF-8" id="userForm">
         		<div class="ui top attached tabular menu">
                    <a class="item active" data-tab="first" id="firsttab">Личные данные</a>
                    <a class="item" data-tab="second">Наукометрические</a>
                    <a class="item" data-tab="third" v-if="isAdmin">Служебные данные</a>
                    <a class="item" data-tab="four" v-if="isAdmin">Администрирование</a>
                </div>
		        <div class="ui bottom attached tab segment active" data-tab="first">
                    <div class="three fields">
                        <div class="required field">
                            <label>Логин (входа в систему)</label>
                            <input v-model="editingrec.login" type="text"  placeholder="Логин входа в систему" maxlength="12" id="loginField" />
                        </div>
                        <div class="required field">
                            <label>Пароль</label>
                            <input v-model="editingrec.password" type="text"  placeholder="Пароль входа в систему" maxlength="12" id="passwordField" />
                        </div>
                       <div class="required field">
                           <label>E-mail</label>
                           <input v-model="editingrec.email" type="email"  placeholder="Адрес электронной почты" pattern="[a-zA_Z0-9._%+-]+@[a-zA_Z0-9.-]+\.[a-zA_Z]{2,3}$" maxlength="16" id="emailField"/>
                       </div>
                    </div>

                    <div class="three fields">
                        <div class="required field">
			                <label>Фамилия</label>
                            <input v-model="editingrec.fam" type="text"  placeholder="Фамилия" path="fam" maxlength="32"/>
                        </div>
                        <div class="required field">
			               <label>Имя</label>
                           <input v-model="editingrec.nam" type="text"  placeholder="Имя"  path="nam" maxlength="16"/>
                        </div>
                        <div class="required field">
			               <label>Отчество</label>
                           <input v-model="editingrec.otc" type="text"  placeholder="Отчество"  path="otc" maxlength="16"/>
                        </div>
                    </div>
                    <div class="three fields">
                        <div class="field">
		        	       <label>Ученое звание</label>
                           <select v-model="editingrec.uzwan" class="ui dropdown"  path="uzwan">
			                   <option disabled value="0">-- нет --</option>
			                   <option value="1">доцент</option>
			                   <option value="2">профессор</option>
			                   <option value="3">м.н.с.</option>
			                   <option value="4">с.н.с.</option>
			               </select>
                        </div>
                        <div class="field">
                            <label>Ученая степень</label>
                            <select v-model="editingrec.ustep" class="ui dropdown">
                                <option value="0" data-id="0">без степени </option>
                                <option v-for="option in ustepslist" v-bind:value="option.id">
                                    {{ option.shifr }}
                                </option>
                            </select>
                        </div>
                        <div v-show="editingrec.ustep>0" class="ui bottom attached message">
                            {{nameustep}}
                        </div>

					</div>
                    <div class="field">
                        <label>Дополнительные личные данные</label>
                        <input v-model="editingrec.dopInf" type="text" placeholder="Дополнительные личные данные" maxlength="128"/>
                    </div>

                </div>
                <div class="ui bottom attached tab segment" data-tab="second">
                    <div class="field">
                        <label>Фамилия и Имя на английском языке:</label>
                        <input v-model="editingrec.engFio" type="text" placeholder="Фамилия и Имя на английском языке" maxlength="32" pattern="[A-Za-z ]+"/>
                    </div>
                    <div class="two fields">
                        <div class="field">
                            <label>AuthorID (elibrary.ru): *</label>
                            <input v-model="editingrec.authorIdEliblaryRu" type="text" placeholder="AuthorID (elibrary.ru)"  maxlength="32"/>
                        </div>
                        <div class="field">
                            <label>Список публикаций автора (elibrary.ru): *</label>
                            <input v-model="editingrec.hrefElibraryRu" type="text" placeholder="Укажите интернет-ссылку на страницу" path="hrefElibraryRu" maxlength="64"/>
                        </div>

                    </div>
                    <div class="two fields">
                        <div class="field">
                            <label>ORCID (scopus.com):</label>
                            <input v-model="editingrec.orcIdScopusCom" type="text" placeholder="ORCID (scopus.com):"  maxlength="32"/>
                        </div>
                        <div class="field">
                            <label>Профиль автора (scopus.com):</label>
                            <input v-model="editingrec.hrefScopusCom" type="text" placeholder="укажите интернет-ссылку на страницу"  maxlength="64"/>
                        </div>
                    </div>
                    <div class="two fields">
                        <div class="field">
                            <label>ResearcherID (apps.webofknowledge.com):</label>
                            <input v-model="editingrec.reseacherIdWebOfKnoledgeCom" type="text" name="reseacherIdWebOfKnoledgeCom" placeholder="ResearcherID (apps.webofknowledge.com):"  maxlength="32"/>
                        </div>
                        <div class="field">
                            <label>Профиль автора (apps.webofknowledge.com):</label>
                            <input v-model="editingrec.hrefWebOfKnoledgeCom" type="text" placeholder="укажите интернет-ссылку на страницу"  maxlength="64"/>
                        </div>
                    </div>
                    <div class="field">
                        <label>Дополнительные личные данные:</label>
                        <input v-model="editingrec.dopInfForSearch" type="text"  placeholder="дополнительные личные данные" maxlength="128"/>
                    </div>
                </div>
                <div class="ui bottom attached tab segment" data-tab="third" v-if="isAdmin">
                    <div class="two fields">
                        <div class="field">
                            <label>Категория персонала: *</label>
                            <select v-model.number="editingrec.shifrKat" class="ui dropdown" id="shifrkat">
                                <option value="0">-- Не указано --</option>
                                <option value="1">ППС</option>
                                <option value="2">НР</option>
                                <option value="3">РП</option>
                                <option value="4">ИПТ</option>
                                <option value="5">АХП</option>
                                <option value="4">УВП</option>
                            </select>
                        </div>
                        <div class="field">
                            <label>Должность: *</label>
<%--
                            <input v-model="editingrec.shifrDol" type="text" placeholder="Должность из справочника" maxlength="32" id="shifrdol"/>
--%>
                            <select class="ui dropdown" v-model="editingrec.shifrDol" class="ui dropdown" id="shifrdol">
                                <option value="0" data-id="0">-- не указана --</option>
                                <option v-for="option in dolglist" v-bind:value="option.id">
                                    {{ option.name }}
                                </option>
                            </select>

                        </div>
                    </div>
                    <div >
                        <podrselector ref='podrslctr' v-bind:podrid  = "shifrpodr"
                                                      v-on:eselpodr  = "ePodrSelected"
                                                      v-on:enamepodr = "eNamePodr"></podrselector>
                    </div>
                    <div class="ui message">
                        <p v-cloak>{{namePodr}}</p>
                    </div>

                    <div class="two fields">
                        <div class="field">
                            <label>Вид найма: </label>
                            <select v-model="editingrec.shifrWr" class="ui dropdown" placeholder="штат / внутреннее совместительство / внешнее совместительство" >
                                <option value="0">-- Укажите вид найма --</option>
                                <option value="1">штат</option>
                                <option value="2">внутреннее совместительство</option>
                                <option value="3">внешнее совместительство</option>
                            </select>
                        </div>
                        <div class="field" v-if="editingrec.shifrWr==2 || editingrec.shifrWr==3">
                            <label>Должность и основное место работы для совместителей из категории персонал – НПР:</label>
                            <input v-model="editingrec.dolgOsnMr" type="text" placeholder="Должность и основное место работы для совместителей" maxlength="128"/>
                        </div>
                    </div>
                </div>
                <div class="ui bottom attached tab segment" data-tab="four" v-if="isAdmin">
                    <div v-if="editingrec.statusCode==0">
                        <div class="ui message">
                            <div class="header">
                                Сведения о регистрации
                            </div>
                            <p>Пользователь зарегистрировался {{editingrec.dataCreate}} и еще не верифицирован.</p>
                        </div>
                        <div class="fields">
                             <div class="ui yellow button field" v-bind:class="{loading:isVerification}" v-on:click="eVerificate">Верифицировать</div>
                                 <div class="inline field">
                                     <div class="ui checkbox">
                                         <input class="hidden" type="checkbox" v-model="needSendEmail">
                                         <label>Послать уведомление по EMail</label>
                                 </div>
                             </div>
                        </div>
                    </div>
                    <div v-else-if="editingrec.statusCode==1">
                        <p class="ui green label">Верификация пройдена {{dataVerification}}</p>
                    </div>
                    <div v-else>
                        <p class="ui green label">Другое значение {{editingrec.statusCode}}</p>
                    </div>
                    <div>
<%--
                        <select v-model="roles" id="roles" class="ui multiple dropdown">
                            <option v-for="role in roleslist" v-bind:value="role.id">
                                 {{role.description}}
                            </option>
                        </select>
--%>
                        <p></p>
                        <div class="ui multiple selection dropdown" id="rdd">
                            <!-- This will receive comma separated value like OH,TX,WY !-->
<%--
                            <input name="roles" type="hidden" v-model="roles">
--%>
                            <input name="roles" type="hidden">
                            <i class="dropdown icon"></i>
                            <div class="default text">Роли пользователя</div>
                            <div class="menu">
                                <div class="item" v-for="role in roleslist" v-bind:data-value="role.id" >{{role.description}}</div>
                            </div>    
                         </div>
                         <p></p>
    <%--
        <div id="saverolesbutton" class="ui yellow button" v-bind:class="{ loading: isRoleLoading}"  v-on:click="eSaveRoles" >Сохранить роли</div>
    --%>
                             <div id="saverolesbutton" class="ui yellow button" v-bind:class="{ loading: isRoleLoading}"  v-on:click="eSaveRoles" v-show="cansaverolescomputed">Сохранить роли</div>
                    </div>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>
                    <p></p>

                </div>
<%--
                <div class="field" v-show="action>1">
                    <label>Код</label>

                    <p>{{editingrec.id}}</p>
                </div>
                <div class="field">
                    <label>ФИО</label>
                    <input v-if="action!=3" v-model="editingrec.FIO"  name="fio" placeholder="название" type="text" />
                    <p v-else> {{editingrec.FIO}}</p>
                </div>
                <div class="field">
                    <label>Статус</label>
                    <input v-if ="action!=3" v-model="editingrec.statusName"  name="statusname" placeholder="сокращенное название" type="text" />
                    <p v-else> {{editingser.statusName}}</p>
                </div>
--%>
                <div class="ui error message">
                    <ul v-show="hasErrors">
                        <li v-for="error in errlist">{{error.message}}</li>
                    </ul>
                </div>

                 <div id="mesRoleSaved" class="ui floating visible message" v-bind:class="{hidden:!showMesRoleSaved,visible:showMesRoleSaved}">
                     <p>Список ролей сохранен!</p>
                 </div>

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
    var userform=Vue.extend({
        template: '#template-user-form',
        data:function() {
            return {
                   control       : null,
                   controltab    : null,
                   editingrec    : this.receivedrec ,
                   namePodr      : ""        ,
                   savedrecord   : null      ,
                   localShow     : this.show ,
                   hasErrors     : false     ,
                   errlist       : null      ,
                   ustepslist    : null      ,
                   univlist      : null      ,
                   action        : 0         ,
                   roleslist     : null      ,
                   dolglist      : null      ,
                   roles         : null      ,
                   savedRoles    : ""        ,
                   currentRoles  : ""        ,
                   showMesRoleSaved : false  ,
                   dataVerification : ""     ,
                   isRoleLoading : false     ,
//                   user          : null      ,
                   needSendEmail : true      ,
                   isVerification : false

<%--
                   podrtree      : null
--%>
//                   canSaveRoles  : false   ,

            }
        },
        props: {receivedrec:null,

                 show: {
                        type     : Boolean ,
                        required : true    ,
                        twoWay   : true
                 },
                 receivedaction : 0

        },
        components: {
            'podrselector' : podrselector
        },
        methods: {
            isselectedfac:function(id) {
              var retval=false;
//              console.log('isselectedfac. id='+id+' shifrFac='+this.editingrec.shifrFac);
              if (id==this.editingrec.shifrFac) {
                  retval=true;
              }
              return retval;
            },
            approved: function() {
//                alert('approved');
                this.hasErrors=false;
                this.saveRec();
                return false;
//                return true;
            },
            approvedSaveRoles:function(){
                var r;
                r=$('#rdd')
                        .dropdown('get value');
                ;

                if (!(r&&r.length>0)) {
                    r="";
                }
                this.saveRoles(r,1);
            },
            approvedFinish:function() {
//                alert('approvedFinish');
                this.localShow = false;
                this.$emit('eshowmodal',false,this.editingrec);
//                this.localShow=val;
//                alert('this control 00');
                if (this.control) {
//                    alert('this control');
                    this.control.modal('hide');
                    this.action=0;
                }
            },
            deletedFinish:function() {
//                alert('deletedFinish');
                this.localShow = false;
                this.$emit('edeluser',this.editingrec);
                if (this.control) {
//                    alert('this control');
                    this.control.modal('hide');
                    this.action=0;
                }
            },
            deny: function() {
                this.localShow = false;
                this.$emit('eshowmodal',false);
                return true;
            },
            eVerificate:function() {
                this.sendVerificate();
            },
            ePodrSelected:function(id) {
//                 console.log('userform ePodrSelected id='+id);
                 if (id>0 && this.editingrec.shifrPodr!=id) {
//                    console.log('userform inside ePodrSelected this.editingrec.shifrPodr'+id);
                    this.editingrec.shifrPodr=id;

//                    this.getPodrCompoundName(id);
                    this.$refs.podrslctr.getPodrCompoundName(id);
                    this.getUnivFacKafForShifrPodr(id);
                 }
            },
            eNamePodr:function(nameVal) {
//               console.log('eNamePodr nameVal='+nameVal);
               if (nameVal && _.isString(nameVal))
                  this.namePodr=nameVal;
            },
            saveRec:function() {

                var vm=this;
                result=this.userFormValidator();
                if (!result.result) {
                    if (result.errlist && result.errlist.length>0) {
                       var z=$('#modaluser').css('z-index');
                       var cont='<div class="ui error message"><ul>';
                       for (var i=0; i<result.errlist.length;i++) {
                          var mes=result.errlist[i];
                          cont=cont+"<li>"+mes+"</li>";
                       }
                       cont=cont+"</ul></div>"
                       $('#secondmodalcontent').html(cont);
                       $('#secondmodal').css('z-index',z+10);
                       $('#secondmodal').modal('show');
                       $('#secondmodal').modal('refresh');
                    }
                    return false;
                }
                    var uri=this.$root.rootPath+"/util/user/save";
                    var savingRec=_.clone(this.editingrec);
//                    console.log(JSON.stringify(savingRec));
                    delete savingRec.dataCreate;
                    delete savingRec.dataDelete;
                    delete savingRec.statusCode;
                    delete savingRec.dataVerification;
                    delete savingRec.shifrIdSup;
                    delete savingRec.verifiedSupFIO;
                    delete savingRec.sEntity;

                    var objRec  = JSON.stringify(savingRec);
                    axios.post(uri, objRec,
                            {headers:{
                                'Content-Type': 'application/json'}}
                    )
                            .then(function(response){
                                if (response.data) {
                                    if ((response.data.length==1) && (response.data[0].name=='Ok')) {
                                        if (vm.action==1) {
                                            vm.editingrec.id=parseInt(response.data[0].id);
//                                            console.log('saveRec. id='+vm.editingrec.id);
                                        }
                                        vm.approvedSaveRoles();
                                        vm.$notify("alert", "Запись сохранена.");
                                    } else {
                                        if (response.data.length>0) {
                                            var z=$('#modaluser').css('z-index');
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
                                vm.$notify("alert", "","error");
                                vm.$notify("alert", "Ошибка сохранения записи. "+error, "error");
                    });
      //              return retVal;
            },
            deleteRec: function(rec){
            },


            eSaveRoles:function() {
                this.isRoleLoading=true;
                var r;
                r=$('#rdd')
                     .dropdown('get value');
                ;

                if (!(r&&r.length>0)) {
                    r="";
                }
                this.saveRoles(r);
                this.savedRoles=r;
 //               alert("r="+r);
            },
            setCheckboxOnForm:function() {
                if (this.controltab)
                    if (this.localShow) {
                        this.$nextTick(function() {
//                            console.log('amount of checkboxes='+$('.ui.checkbox').length);
                            $('.ui.checkbox')
                               .checkbox()
                            ;
                        });
                    }
            },
            setRolesOnForm:function() {
//                this.canSaveRoles=false;
                if (this.controltab)
                if (this.localShow) {
                    var iCount=0;
                   $('#rdd').dropdown();
//                            var ar=['1','4','7'];
                   var ar=[];
                   $('#rdd').dropdown('clear');
//                   console.log('dd roles clear passed. this.action='+this.action);
                   if (this.action && this.action!=1 && this.roles)
                   if (this.roles.length>0) {
                       _.forEach(this.roles,function(value) {
                         ar.push(value.toString());
                       });
//                       alert("ar="+ar[0]);
                       $('#rdd').dropdown('clear').dropdown('set selected', ar);
//                                    .dropdown('set exactly', ['1','4','7']).dropdown();
                        //.dropdown('refresh').dropdown('change values', '1');
                    } else {
                       $('#rdd').dropdown('clear');
                    }
                    this.savedRoles   = $('#rdd').dropdown('get value');
                    this.currentRoles = this.savedRoles;
                    var vm=this;
                    $('#rdd').dropdown({
                                onChange: function(value, text, $selectedItem) {
                                    iCount++;
//                                    if ((iCount>0) && (savedValue!=value)) {
                                    vm.$nextTick(function() {
//                                       vm.currentRoles=$('#rdd').dropdown('get value');
                                        vm.currentRoles=$('#rdd').dropdown('get value');
//                                       console.log('onChange savedRoles='+vm.savedRoles+" currentRoles="+vm.currentRoles+" vm.cansaverolescomputed="+vm.canSaveRoles());
                                    });
                                }}
                    );


//                            var a;
//                a=$('#rdd').dropdown('get value');
//                            alert('a='+a);
//                            this.eSaveRoles();
                }
            },
            getUStepsList: function() {
                var uri=this.$root.rootPath+"/util/usteps";
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
                            vm.ustepslist=response.data;
                        })

                        .catch(function (error) {
                            alertify.alert("Ошибка","error getUStepsList="+error);
                        });
            },
            getDolgList: function() {
<%--
                <c:url value="/util/dolgs" var="uri2" />
                var uri3="${uri2}";
                var uri=uri3;
--%>
                var uri=this.$root.rootPath+"/util/dolgs";
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
                            vm.dolglist=response.data;
                        })

                        .catch(function (error) {
                    alert("error getDolgList="+error);
                });
            },
            getRolesList: function() {
                var uri=this.$root.rootPath+"/util/roles";
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
                            vm.roleslist=response.data;
                        })

                        .catch(function (error) {
                    alert("error getRoleList="+error);
                });
            },
            getUnivFacKafForShifrPodr: function(id) {
                var idpodr=this.editingrec.shifrPodr;
                if (arguments.length==1 && _.isNumber(arguments[0]))
                    idpodr = Number(arguments[0]);
                var uri=this.$root.rootPath+"/util/preunifackaf/"+idpodr;
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
//                            alert('inside then getUserRolesList');
//                            vm.savedRoles=[];
                            if (_.isArray(response.data) && response.data.length==3) {
                                vm.editingrec.shifrUni=response.data[0].id;
                                vm.editingrec.shifrFac=response.data[1].id;
                                vm.editingrec.shifrKaf=response.data[2].id;
                            } else {
                                alertify.alert("Ошибка","error getUniFacKaf respons.data="+JSON.stringify(response.data));
                            }
                        })

                        .catch(function (error) {
                              alertify.alert("Ошибка","error getUniFacKaf="+error);
                        });
            },
            getUserRolesList: function() {
                var uri = this.$root.rootPath+"/util/user/roles/"+this.editingrec.id;
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
//                            alert('inside then getUserRolesList');
                            vm.roles=[];
//                            vm.savedRoles=[];
                            if (_.isArray(response.data))
                                if (response.data.length>0)
                                    if (response.data[0].id>0)
                                        _.forEach(response.data, function(value) {
                                            vm.roles.push(parseInt(value.id))
                                        });
//                            vt.savedRoles=_.clone(vt.roles);
                            vm.setRolesOnForm();
//                            alert("get roles. cnt="+vt.roles.length);
                        })

                        .catch(function (error) {
                    alertify.alert("Ошибка","error getUserRoleList="+error);
                });
            },

            getEntity: function() {
                var uri=this.$root.rootPath+"/util/user/"+this.receivedrec.id;
                var vm=this;
                axios.get(uri, {
                })
                        .then(function (response) {
                            var rrec=JSON.stringify(response.data);
                            vm.editingrec=JSON.parse(rrec);
//                            console.log('getEntity: editing rec='+JSON.stringify(vm.editingrec));
//                            console.log('getEntity: shifrKaf='+vm.editingrec.shifrKaf+' shifrFac='+vm.editingrec.shifrFac+' shifrUni='+vm.editingrec.shifrUni+' shifrPodr='+vm.editingrec.shifrPodr);
                            vm.savedrecord=_.clone(vm.editingrec);
                            vm.$nextTick(function () {
//                                console.log('inside nextTick getEntity: shifrKaf='+vm.editingrec.shifrKaf+' shifrFac='+vm.editingrec.shifrFac+' shifrUni='+vm.editingrec.shifrUni+' shifrPodr='+vm.editingrec.shifrPodr);
                                vm.namePodr="";
//                                alert('before getUserRolesList');
                                vm.getUserRolesList();
                                vm.setCheckboxOnForm();

                                if (vm.editingrec.shifrPodr && _.isInteger(vm.editingrec.shifrPodr) && vm.editingrec.shifrPodr>0)
                                   vm.$refs.podrslctr.getPodrCompoundName(vm.editingrec.shifrPodr);
                            });
                        })

                        .catch(function (error) {
                           alertify.alert("Ошибка","error getEntity=",error);
                });
            },
<%--
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
--%>
            sendVerificate: function() {
                var uri3=this.$root.rootPath+"/util/user/setverified/"+this.receivedrec.id;
                var needEMail=0;
                if (this.needSendEmail)
                   needEMail=1;
                var uri4 = uri3+"/"+needEMail;
                var uri  = uri4;
                var vm   = this;
                vm.isVerification=true;
                axios.get(uri, {
                })
                        .then(function (response) {
                            var rrec=JSON.stringify(response.data);
                            var jrec=JSON.parse(rrec);
                            if (jrec[0].id==1) {
                                vm.$nextTick(function () {
                                   vm.editingrec.statusCode=1;
                                   vm.editingrec.dataVerification=new Date();
                                   if  (vm.needSendEmail) {
                                       vm.editingrec.password=jrec[0].shifr;
                                   }
                                   var d=vm.editingrec.dataVerification;
                                   var datestring = ("0" + d.getDate()).slice(-2) + "-" + ("0"+(d.getMonth()+1)).slice(-2) + "-" +
                                            d.getFullYear() + " " + ("0" + d.getHours()).slice(-2) + ":" + ("0" + d.getMinutes()).slice(-2);
                                   vm.editingrec.dataVerification=datestring;
                       //             alert("emit ereaduser id="+vt.editingrec.id);
                                   vm.$emit('ereaduser',vm.editingrec.id,vm.editingrec.statusCode,vm.editingrec.dataVerification);
                                });
                                vm.savedrecord=_.clone(vm.editingrec);
                            } else {
                               alertify.alert('Ошибка верификации '+jrec[0].id+' '+jrec[0].name);
                            }
                            vm.isVerification=false;
                        })

                        .catch(function (error) {
                                vm.isVerification=false;
                                alertify.alert("Ошибка","error sendVerificate="+error);
                        });
            },
            saveRoles: function(r,modeContext) {
                var uri=this.$root.rootPath+"/util/user/roles";
                var vm   = this;
                var param='id='+this.editingrec.id+'&roles='+r;
                axios.post(uri, param,
                        {})
                        .then(function (response) {
                            var rrec=JSON.stringify(response.data);
                            var jrec=JSON.parse(rrec);
                            if (jrec[0].id==1) {
                                vm.showMesRoleSaved=true;
                                setTimeout(function(){
                                  vm.showMesRoleSaved=false;
                                },1000);
                                if (modeContext)
                                if (modeContext==1) {
                                   vm.approvedFinish();
                                }
                            } else {
                                alert('Ошибка сохранения списка ролей '+jrec[0].id+' '+jrec[0].name+' id='+vm.editingrec.id);
                            }

//                            $('#saverolesbutton').removeClass("loading");
                            vm.isRoleLoading=false;
                        })
                        .catch(function (error) {
                            alert("error saveRoles="+error);
//                            $('#saverolesbutton').removeClass("loading");
                            vm.isRoleLoading=false;
                });
            },
            fillEmptyRec:function() {
                this.editingrec={
                     id               :  0,
                     name             : "",
                     dataCreate       : null,
                     dataDelete       : null,
                     active           : false,
                     login            : "",
                     password         : "",
                     tabno            : 0,
                     shifrPodr        : 0,
                     email            : "",
                     fam              : "",
                     nam              : "",
                     otc              : "",
                     uzwan            : 0,
                     ustep            : 0,
                     dopInf           : "",
                     engFio           : "",
                     authorIdEliblaryRu : "",
                     hrefElibraryRu   : "",
                     orcIdScopusCom   : "",
                     hrefScopusCom    : "",
                     reseacherIdWebOfKnoledgeCom : "",
                     hrefWebOfKnoledgeCom : "",
                     dopInfForSearch  : "",
                     shifrKat         : 0,
                     shifrDol         : 0,
                     shifrUni         : 0,
                     shifrFac         : 0,
                     shifrKaf         : 0,
                     shifrWr          : 0,
                     dolgOsnMr        : null,
                     dopSlInfo        : "",
                     userCode         : 0,
                     statusCode       : 0,
                     dataVerification : null,
                     shifrIdSup       : 0,
                     verifiedSupFIO   : "",
                     sEntity          : null
                }
//                console.log('fill empty roles');
                this.roles=[];
            },
            userFormValidator:function() {
                  var retVal;
                  retVal=true;
                  var errors=[];
                  var l;
                  if (!this.editingrec.login) {
                      errors.push('Не заполнен логин');
                  }  else {
                     l=this.editingrec.login.trim().length;
                     if (l==0) {
                         errors.push('Не указано логин');
                     } else {
                          if (l<1 || l>16) {
                             errors.push('Неверная длина логина');
                          }
                     }
                  }
                  if (!this.editingrec.password) {
                    errors.push('Не заполнен пароль');
                  }  else {
                     l=this.editingrec.password.trim().length;
                     if (l==0) {
                        errors.push('Не указан пароль');
                     } else {
                       if (l<1 || l>16) {
                          errors.push('Неверная длина пароля');
                       }
                     }
                  }
                  if (!this.editingrec.email) {
                    errors.push('Не заполнен адрес электронной почты');
                  }  else {
                    l=this.editingrec.email.trim().length;
                    if (l==0) {
                        errors.push('Не указан адрес электронной почты');
                    } else {
                        if (l<1 || l>16) {
                            errors.push('Не верная длина адреса электронной почты');
                        } else {
                            if (this.editingrec.email.trim().indexOf('@')<0) {
                                errors.push('Не верный адрес электронной почты');
                            }
                        }
                    }
                  }
                  if (!this.editingrec.shifrDol) {
                      errors.push('Не заполнен код код должности');
                  }  else {
                      var kod=+this.editingrec.shifrDol;
                      if (!kod) {
                          errors.push('Неверно указан код должности');
                      } else {
                          if (kod<1 || kod>10000) {
                             errors.push('Неверно указан код должности');
                          }
                      }
                  }

                  if (errors.length>0) {
                      retVal=false;
                  }

                  return {
                      result:retVal,
                      errlist:errors
                  }

            },
            setValidateFormCode:function () {
                var form=$('#userForm');
                if (!form) return;
                form
                        .form({
                            on     : 'blur',
                            fields: {
                                login: {
                                    identifier  : 'loginField',
                                    rules: [
                                        {
                                            type   : 'empty',
                                            prompt : 'Пожалуйста, введите логин'
                                        },
                                        {
                                            type   : 'maxLength[16]',
                                            prompt : 'Логин должен содержать не более 16 символов'
                                        },
                                        {
                                            type   : 'minLength[1]',
                                            prompt : 'Логин должен содержать не менее 1 символа'
                                        }
                                    ]
                                },
                                password: {
                                    identifier  : 'passwordField',
                                    rules: [
                                        {
                                            type   : 'empty',
                                            prompt : 'Пожалуйста, введите пароль'
                                        },
                                        {
                                            type   : 'maxLength[16]',
                                            prompt : 'Пароль должен содержать не более 16 символов'
                                        },
                                        {
                                            type   : 'minLength[1]',
                                            prompt : 'Пароль должен содержать не менее 1 символа'
                                        }
                                    ]
                                },
                                email: {
                                    identifier  : 'emailField',
                                    rules: [
                                        {
                                            type   : 'empty',
                                            prompt : 'Пожалуйста, введите адрес электронной почты'
                                        },
                                        {
                                            type   : 'maxLength[16]',
                                            prompt : 'Адрес электронной почты должен содержать не более 16 символов'
                                        },
                                        {
                                            type   : 'minLength[5]',
                                            prompt : 'Адрес электронной почты должен содержать не менее 5 символов'
                                        }
                                    ]
                                }
                            }
                        })
                ;

            },
            convertdropdowns:function() {
//                  console.log("activate dropdowns length="+$('#shifrkat').length);
//                  $('#shifrkat')
//                    .dropdown();
            }
        },
        computed:{
            cansave:function() {
//                console.log('inside cansave');
                var retval=!_.isEqual(this.editingrec,this.savedrecord);
                var retval1=this.cansaverolescomputed;
                var rv=retval || retval1;
                return retval;
            },
            cansaverolescomputed:function() {
//                console.log('inside cansaverolescomputed');
                var retVal=false;
                var c="",s="";

                if (this.control) {
                    s=_.toString(this.savedRoles).trim();
                    c=_.toString(this.currentRoles).trim();
                    retVal=(c.localeCompare(s)!=0);
                }
                if (this.action!=2)
                    retVal=false;
//                console.log('c='+c+" s="+s+" retVal="+retVal);

                return retVal;

            },
            nameustep:function() {
                var retVal;
                retVal="";
                if (this.editingrec)
                    if (this.editingrec.ustep)
                        if (this.editingrec.ustep>0)
                           if (this.ustepslist) {
                               var vm=this;
                               var i=_.findIndex(this.ustepslist, function(o) { return o.id== vm.editingrec.ustep; });
                               if (i>=0) retVal=this.ustepslist[i].name;
                           }

                return retVal;
            },
            shifrUni:function() {
                var retval=this.editingrec.shifrUni?this.editingrec.shifrUni:0;
                return retval;
            },
            shifrpodr:function() {
                var retval=0;
                if (this.editingrec.shifrPodr && this.editingrec.shifrPodr>0)
                    retval = _.toInteger(this.editingrec.shifrPodr);
                else
                if (this.editingrec.shifrKaf && this.editingrec.shifrKaf>0)
                    retval = _.toInteger(this.editingrec.shifrKaf);
                else
                if (this.editingrec.shifrFac && this.editingrec.shifrFac>0)
                    retval = _.toInteger(this.editingrec.shifrFac);
                else
                if (this.editingrec.shifrUni && this.editingrec.shifrUni>0)
                    retval = _.toInteger(this.editingrec.shifrUni);
                else
                    retVal=1;
                return retval;
            },
            rootpodrid:function() {
//                console.log('inside calculate rootpodrid');
                var retval=0;
                if (this.$root.user.shifrPodr && this.$root.user.shifrPodr>0)
                    retval = _.toInteger(this.$root.user.shifrPodr);
                return retval;
            },
            isAdmin:function() {
                var retVal=this.$root.isadmin;
                return retVal;
            },
            isNPR:function() {
                var retVal=this.$root.isnpr;
                return retVal;
            },
            isInserting:function() {
                var retVal=false;
                if (this.action && this.action==1) {
                    retVal=true;
                }
                return retVal;
            }
        },
        watch: {
            receivedaction:function(val){
//                console.log('form: receivedaction val='+val);
                this.action=val;
                if (this.action==1) {
//                    console.log('before set clear roles');
                    this.setRolesOnForm();
                }
//                alert(' action='+this.action);
            },

            receivedrec:function(val) {
//                console.log('userform: inside watcher receivedrec val='+val);
                if (val) {
//                    this.editingrec=_.clone(val);
//                    alert('watch');
                    if (val.dataVerification)
                    if (val.dataVerification.trim().length>3)
                        this.dataVerification=val.dataVerification;
//                    this.getEntity();
                    if (this.action==0)
                       this.action=val.id<1?1:2;
                    if (this.action==1) {

                        this.fillEmptyRec();
                        if (val.fio) {
                           this.editingrec.shifrPodr=val.fio;
                           if (this.editingrec.shifrPodr>1) {
                              this.getUnivFacKafForShifrPodr();
                           }
                        }
                    } else {
                        this.getEntity();
//                        this.setCheckboxOnForm();
                        if ((this.editingrec.shifrPodr>1) &&
                            (this.editingrec.shifrUni<1) &&
                            (this.editingrec.shifrFac<1) &&
                            (this.editingrec.shifrKaf<1)) {
                               this.getUnivFacKafForShifrPodr();
                        }
                    }
                }

            },

            show:function(val) {
//              alert('watch show '+val);
//              console.log('userform: inside show watch.');
                this.localShow=val;
            },
            localShow: function(val) {
//               console.log('userform: inside localShow watch.');
               var vm=this; 
               if (this.control) {
                if (val) {
                    this.$nextTick(function () {
                      vm.control.modal('show');
                        if (vm.controltab) {
                            vm.controltab.tab();
                            vm.setValidateFormCode();
                            vm.setRolesOnForm();
                            vm.convertdropdowns();
                        }
                    })
                }
               } else {
                  alertify.alert("Ошибка",'in show this.control is undefined');
               }
            }
        },

        mounted:function() {
            var vm=this;
            if (!this.control)
              this.$nextTick(function () {
                  $('.coupled.modal')
                          .modal({
                              allowMultiple: true
                          });

                  vm.control = $('#modaluser').modal({
                     onApprove: function() {return this.approved()}.bind(vm),
                     onDeny   : function() {return this.deny()}.bind(vm),
                     onHidden : function() {vm.localShow = false;vm.$emit('eshowmodal',false)}.bind(vm)
                  });
                  vm.controltab=$('.menu .item');
              });
//            this.user=window.user;
        },
        created:function() {
               this.getUStepsList();
               this.getDolgList();
               this.getRolesList();

        }


    });
</script>