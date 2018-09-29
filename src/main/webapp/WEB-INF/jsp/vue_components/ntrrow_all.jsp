<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-ntr-row">
    <tr>
        <td>
<%--
            {{rowrec.id}}
--%>
            {{rowrec.lineno}}
        </td>
        <td>
            <span>{{rowrec.authors}}</span>
        </td>
        <td>
            <span>{{rowrec.name}}</span>
        </td>
<%--
        <td>
            <span>{{rowrec.parametry}}</span>
        </td>
--%>
        <td v-if="isadmin">
            <span>{{rowrec.namepre}}</span>
        </td>
        <td>
            <span>{{rowrec.pokaz}}</span>
        </td>
        <td>
            <span v-if="rowrec.approved && rowrec.approved.trim().length>1" v-bind:data-tooltip="rowrec.fioapproved+' '+rowrec.dataapproved">{{rowrec.approved}}</span>
        </td>
        <td>
            <button class="compact mini ui button" v-on:click="swipeimages" v-if="rowrec.amntOfImages>0" data-tooltip="Просмотр изображений" data-position="top right"><i class="file image outline icon"></i></button>
            <span v-if="rowrec.amntOfDocs>0 && rowrec.amntOfImages<=0"><i class="window restore icon"></i></span>
        </td>
<%--
        <td>
            <span v-show="rowrec.idowner>0">{{rowrec.idowner}}</span>
        </td>
--%>
        <td>
            <div class="ui icon buttons">
                <button v-on:click="approventr" class="compact mini ui button"  v-if="canApprove" data-tooltip="Подтвердить работу" data-position="top right"><i class="checkmark box icon"></i></button>
                <button v-on:click="dismissapproventr" class="compact mini ui button"  v-if="canDismissApprove" data-tooltip="Отменить подтверждение работы" data-position="top right"><i class="square outline icon"></i></button>
                <button v-on:click="editrec"    class="compact mini ui button" data-tooltip="Просмотр и редактирование записи" data-position="top right"><i class="edit icon"></i></button>
                <button v-on:click="deleterec"  class="compact mini ui button" v-if="canDelete" data-tooltip="Удаление записи" data-position="top right"><i class="remove icon"></i></button>
            </div>
        </td>
    </tr>
</template>
<script>
var ntrrow=Vue.extend({
    template: '#template-ntr-row',
    props: ['rowrec','index'],
    data : function() {
        return {
            showicons:true,
            imagehrefs:[]
        }
    },

    methods: {
        approventr:function() {
            var uri=this.$root.rootPath+"/util/ntr/approve/"+this.rowrec.id;
            var vm=this;
//            console.log("inside approve ntr approve uri="+uri);
            axios.get(uri, {
            })
                    .then(function (response) {
//                        console.log("inside then approve ntr approve uri="+uri);
                        var correctAnswer=true;
                        var rrec=response.data;
                        if (_.isArray(response.data)) {
                            if (rrec.length==1) {
                                if (
                                    (rrec[0].name.trim()=="Error")
                                    ||
                                    (rrec[0].name.trim()=="Err")
                                    ||
                                    (rrec[0].name.trim()=="Empty")
                                   )
                                   {
                                    correctAnswer=false;
                                   }
                                }
                        } else {
//                            console.log("rrec is not array");
                            correctAnswer=false;
                        }
//                        console.log("correctAnswer="+correctAnswer);
                        if (correctAnswer) {
                            vm.$nextTick(function () {
//                                console.log("inside approve nextTick");
                                vm.rowrec.approved="Подтверждено";
                                vm.rowrec.dataapproved=rrec[0].shifr;
                                vm.rowrec.fioapproved=rrec[0].name;
                                vm.$emit('approvedrec',vm.rowrec,vm.index,rrec[0].shifr);

                            });
                        }
                    })

                    .catch(function (error) {
                        alert("error approve ntr="+error);
                   });

        },
        dismissapproventr:function() {
            var uri = this.$root.rootPath+"/util/ntr/dismissapprove/"+this.rowrec.id;
            var vm=this;
//            console.log("inside get imagehreas uri="+uri);
            axios.get(uri, {
            })
                    .then(function (response) {
                        var correctAnswer=true;
                        var rrec=response.data;
                        if (_.isArray(response.data)) {
                            if (rrec.length==1) {
                                if (
                                        (rrec[0].name.trim()=="Error")
                                                ||
                                                (rrec[0].name.trim()=="Err")
                                                ||
                                                (rrec[0].name.trim()=="Empty")
                                        )
                                {
                                    correctAnswer=false;
                                }
                            }
                        } else {
//                            console.log("rrec is not array");
                            correctAnswer=false;
                        }
//                        console.log("correctAnswer="+correctAnswer);
                        if (correctAnswer) {
                            vm.$nextTick(function () {
                                vm.rowrec.approved="";
                                vm.rowrec.dataapproved="";
                                vm.rowrec.fioapproved="";
                                vm.$emit('disapprovedrec',this.rowrec,this.index);

                            });
                        }
                    })

                    .catch(function (error) {
                alert("error approve ntr="+error);
            });
        },
        editrec:function() {
//            console.log('amntOfImages='+this.rowrec.amntOfImages+' amntOfDocs='+this.rowrec.amntOfDocs);
//            console.log('rowrec approved='+this.rowrec.approved);
            this.$emit('editrec',this.rowrec,this.index);
        },
        deleterec: function(){
            this.$emit('delrec',this.rowrec,this.index);
        },
        swipeimages:function() {
            if (this.rowrec.id>0) {
                this.imagehrefs=[];
                this.getImageHrefs();
            }
        },
        finishswipeimages:function() {
            if (this.imagehrefs.length<1) {
                return;
            }
//                for (var i=0;i<this.imagehrefs.length;i++) {
//                    console.log("i="+i+" href="+this.imagehrefs[i]);
//                }
//            } else {
//                console.log("emty imagehrefs array");
//            }
            var pswpElement = document.querySelectorAll('.pswp')[0];
            var items = [];
            vm=this;
            for (var i=0;i<this.imagehrefs.length;i++) {
                item={
                    src:vm.imagehrefs[i],
                    w:1600,
                    h:1600
                };
                items.push(item);
            }
            var options = {
                // optionName: 'option value'
                // for example:
                index: 0 // start at first slide
            };

// Initializes and opens PhotoSwipe
            window.gallery = new PhotoSwipe( pswpElement, PhotoSwipeUI_Default, items, options);
            window.gallery.init();

        },
        getImageHrefs:function() {
<%--
            <c:url value="/util/ntrdochrefs" var="uri2" />
            var uri3="${uri2}"+"/"+this.rowrec.id;
            var uri=uri3;
--%>
            var uri=this.$root.rootPath+"/util/ntrdochrefs/"+this.rowrec.id;
            var vm=this;
//            console.log("inside get imagehreas uri="+uri);
            axios.get(uri, {
            })
                    .then(function (response) {
//                        console.log("inside then function.");
                        var correctAnswer=true;
//                        var rrec=JSON.stringify(response.data);
                        var rrec=response.data;
//                        console.log("rrec="+rrec);
                        if (_.isArray(response.data)) {
                            if (rrec.length==1) {
                                if (rrec[0].trim().length==0) {
                                    correctAnswer=false;
                                }
                            }
                        } else {
//                            console.log("rrec is not array");
                            correctAnswer=false;
                        }
//                        console.log("correctAnswer="+correctAnswer);
                        if (correctAnswer) {
                            vm.$nextTick(function () {
                                vm.imagehrefs=rrec;
//                                vt.imagehrefs=JSON.parse(rrec);
//                                console.log('received imagehrefs='+vt.imagehrefs.toString());
//                                alert('before getUserRolesList');
                                vm.finishswipeimages();
                            });
                        }
                    })

                    .catch(function (error) {
                       alert("error getImageHrefs="+error);
            });

        }
    },
    computed:{
        canApprove:function() {
//            console.log("canApprove: id="+this.rowrec.id);
            var retval;
            retval=false;
            if (this.isnpr) {
                return false;
            }
            var approved=this.rowrec.approved&&this.rowrec.approved.trim().length>1?true:false;
//            console.log("canApprove: approved="+approved);
            var admin=this.$parent.isadmin;
            if (admin && !approved)
                retval=true;
//            if (this.rowrec.id==4) {
//                console.log("canApprove: admin="+admin+" approved="+approved+" retval="+retval);
//            }
            return retval;
        },
        canDismissApprove:function() {
//            console.log("canDismissApprove: id="+this.rowrec.id);
            var retval;
            retval=false;
            if (this.isnpr) {
                return false;
            }
            var approved=this.rowrec.approved&&this.rowrec.approved.trim().length>1?true:false;
            var admin=this.$parent.isadmin;
            if (admin && approved)
                retval=true;
//            if (this.rowrec.id==4) {
//                console.log("canDismissApprove: admin="+admin+" approved="+approved+" retval="+retval);
//            }
            return retval;
        },
        canDelete:function() {
//            console.log("canApprove: id="+this.rowrec.id);
            var retval;
            retval=false;
            var approved=this.rowrec.approved&&this.rowrec.approved.trim().length>1?true:false;
            if (this.isnpr && !approved) {
                return true;
            }
            var admin=this.$parent.isadmin;
            if (admin && !approved)
                retval=true;
//            if (this.rowrec.id==4) {
//                console.log("canApprove: admin="+admin+" approved="+approved+" retval="+retval);
//            }
            return retval;
        },
        isnpr:function() {
            var retval;
            retval = this.$parent.isnpr;
            return retval;
        },
        isadmin:function() {
            var retval;
            retval = this.$parent.isadmin;
            return retval;
        }

    },
    watch: {
            rowrec:function(val){
//                console.log('ntrrow: rowrec watch. rowrec='+JSON.stringify(this.rowrec));
//                console.log('ntrrow: rowrec watch. val='+JSON.stringify(val));
            }
    }
});
</script>

