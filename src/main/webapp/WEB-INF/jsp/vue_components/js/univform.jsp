<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
    var univform=Vue.extend({
        template: '#template-univ-form',
        data:function() {
            return {
                   editingUniv:this.univ,
                   localShow:this.show,
                   hasErrors:false,
                   errlist:null
            }
        },
        props: {univ:null,
                 show: {
                        type: Boolean,
                        required: true,
                        twoWay: true
                 }
        },
        methods: {
            approved: function() {
                this.hasErrors=false;
                this.saveUniv();
                return false;
//                return true;
            },
            approvedFinish:function() {
                this.localShow = false;
                this.$emit('eshowmodal',false,this.editingUniv);
                this.localShow=val;
                if (this.control)
                   this.control.modal('hide');
            },
            deny: function() {
                this.localShow = false;
                this.$emit('eshowmodal',false);
                return true;
            },
            saveUniv:function() {
                <c:url value="/util/univ/save" var="uri2" />
                var uri="${uri2}";
//                var uri="/r/util/univ/save";
                var objUniv=JSON.stringify(this.editingUniv);
                var vm=this;
                var finished=false;
                var retVal=false;
                axios.post(uri, objUniv,
                        {headers:{
                    'Content-Type': 'application/json'}}
                )
                        .then(function(response){
                            if (response.data) {
                               if ((response.data.length==1) && (response.data[0].name='Ok')) {
                                  vm.$notify("alert", "Запись сохранена.");
                                  retVal=true;
                                  vm.approvedFinish();
                               } else {
                                 if (response.data.length>0) {
                                     var z=$('#modaluniv').css('z-index');
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
                            finished=true;
                        })
                        .catch (function(error){
                             vm.$notify("alert", "Ошибка сохранения записи. . "+error, "error");
                               finished=true;
                         });
                return retVal;

            },
            deleteUniv: function(univ){
                vm.universities.$remove(univ);
            },
            setValidateForm:function() {
                $('#formuniv')
                        .form({
                            //   inline : true,
                            on     : 'blur',

                            fields: {
                                name: {
                                    identifier  : 'name',
                                    rules: [
                                        {
                                            type   : 'empty',
                                            prompt : 'Пожалуйста, введите название подразделения'
                                        },
                                        {
                                            type   : 'maxLength[96]',
                                            prompt : 'Название должно содержать не более 96 символов'
                                        },
                                        {
                                            type   : 'minLength[1]',
                                            prompt : 'Название должно содержать не менее 1 символа'
                                        }
                                    ]
                                },
                                shortname: {
                                    identifier  : 'shortname',
                                    rules: [
                                        {
                                            type   : 'empty',
                                            prompt : 'Пожалуйста, введите сокращенное название'
                                        },
                                        {
                                            type   : 'maxLength[16]',
                                            prompt : 'Краткое название должно содержать не более 16 символов'
                                        },
                                        {
                                            type   : 'minLength[1]',
                                            prompt : 'Краткое название должно содержать не менее 1 символа'
                                        }
                                    ]
                                }
                            }
                        })
                ;

            }
        },
        computed:{
            cansave:function() {
                return !_.isEqual(this.editingUniv,this.univ);
            }
        },
        watch: {

            univ:function(val) {
                if (val) {
                    this.editingUniv=_.clone(val);
                }

            },

            show:function(val) {
                this.localShow=val;
            },
            localShow: function(val) {
               if (this.control) {
                if (val) {
                    this.$nextTick(function () {
                      this.control.modal('show');
                      this.setValidateForm();
                    })
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

                  this.control = $('#modaluniv').modal({
                     onApprove: function() {return this.approved()}.bind(this),
                     onDeny   : function() {return this.deny()}.bind(this),
                     onHidden : function() {this.localShow = false;this.$emit('eshowmodal',false)}.bind(this)
                  });

              })
        }

    });
</script>