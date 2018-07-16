<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<template id="template-podrselector">
    <a id="one-node-selectable" class="ui black basic button" >Подразделения (Выбрано 0) </a>
</template>
<script>
var podrselector=Vue.extend({
    template: '#template-podrselector',
    props: ['podrid']
    ,
    data: function() {
          return {
              podrlist:null,
              savedrootpodr:0
          }
    },
    watch:{
        podrid:function(newpodrid,oldpodrid) {
//            console.log('inside watcher computed newpodrid ='+newpodrid+' oldpodrid='+oldpodrid);
            if (!newpodrid)
               return;
            if (!_.isInteger(newpodrid))
               return;
            var v=Number(newpodrid);
            if (v<0) {
//               console.log('inside watcher_0 newpodrid==0');
//               this.getPodrList(value);
            } else {
//                console.log('inside watcher_1. Before getPodrList');
                var vv=this.$root.user.shifrPodr;
//                console.log('inside watcher_2. vv='+vv);
                if (this.savedrootpodr!=vv) {
                    this.savedrootpodr=vv;
//                    console.log('inside watcher_3.');
                    this.getPodrList(vv);
                } else {
//                    console.log('inside watcher_4.');
                    this.showTreePicker(1);
                }
            }
        }


    },
    methods: {
        editrec:function() {
            this.$emit('editrec',this.rowrec,this.index);
        },
        deleterec: function(rowrec){
            this.$emit('delrec',this.rowrec,this.index);
        },
        makePredpAccordion:function (id) {
             this.showTreePicker(0);
<%--
            return;
            var str;
            str=this.buildAccordion(this.podrlist,0);
            console.log('strlen='+str.length+" "+str);
           this.$nextTick(function(){
               console.log('amnt='+$('#accordionpredpmenu').length);
               $('#accordionpredpcontainer').html(str);
               $('.accordion').accordion().accordion('refresh');
           });
--%>
        },
        showTreePicker:function(mode) {
            var vm=this;
//            console.log('inside showTreePicker this.podrlist='+this.podrlist+" vm.podrid="+this.podrid+" amnt of elem="+$('#one-node-selectable').length+' '+JSON.stringify(this.podrlist));
            if (mode==0) {
               this.convertPodrList(vm.podrlist);
            }
            var treeData=[];
            treeData.push(vm.podrlist);
//            console.log('treeData vm.podrid='+vm.podrid);
            this.$nextTick(function() {
//                  console.log('inside nextTick treeData vm.podrid='+vm.podrid);
                  $('#one-node-selectable').removeAttr('data-picked-ids');
                  $('#one-node-selectable').treePicker({
                     data: treeData,
                     name: 'Подразделение',
                     singlePick: true,
//                childrenKey:'childs',
                picked:[vm.podrid],
                     onSubmit: function(nodes) {
//                        console.log('onSubmit='+JSON.stringify(nodes));
                        vm.$emit('eselpodr',nodes[0].id);
//                        console.log(nodes);
                     }
                 });
            });
        },
        getPodrList: function (id) {
            if (!id || id<1)
                id=1;
            var uri=this.$root.rootPath+"/util/podrsrec/"+id;
            var vm=this;
            axios.get(uri,{})
                .then(function(response) {
//                       vm.podrlist=JSON.stringify(respons.data);
                       var rrec=response.data;
//                        if (_.isArray(rrec)) {
                        if (rrec) {
                            vm.podrlist=JSON.parse(JSON.stringify(response.data));
//                          console.log('getUserName: rrec='+rrec+" name="+name+" id="+id);
                            vm.makePredpAccordion(id);
                        } else {
                            alertify.alert("wrong podrlist getting "+rrec);
                        }
                    }
            )
            .catch(function(error) {
                alertify.alert("error reading podrlist "+error);
            }
            )
        },
        findInPodr:function(podr,id) {
             var plist=[];
             if (!podr) return plist;
             if (podr.podrEntityDTO.id==id) {
                 if (podr.childs && podr.childs.length>0)
                    plist=podr.childs;
             }
             else {
                   if (!podr.childs) return plist;
                   if (podr.childs.length<1) return plist;

                   for (var i=0;i<podr.childs.length;i++) {
                       if (podr.childs[i].podrEntityDTO.id==id) {
                          if (podr.childs[i].childs && podr.childs[i].childs.length>0)
                              plist=podr.childs[i].childs;
                              break;
                          }
                       else
                           plist=this.findInPodr(podr.childs[i]);
                   }
             }
             return plist;

        },
        calcpodrlist:function(id) {
            var plist=[];
//            console.log('inside calcpodrlist id='+id+' this.podrlist='+this.podrlist);
            if (this.podrlist) {
//               console.log('podrlist='+JSON.stringify(this.podrlist));
                plist=this.findInPodr(this.podrlist,id);
            }
//            console.log('plist='+JSON.stringify(plist));
            return plist;

        },
        buildAccordion:function(plist,level) {
            if (!this.podrlist) return "";
            if (!plist) return "";
            var accstr="";
//            console.log('inside buildAccordion plist='+plist);
            if (plist.podrEntityDTO.id>0)  {
                if (plist.childs && plist.childs.length>0) {
                   if (level==0)
<%--
                       accstr=accstr+'<div class="ui styled accordion">';
--%>
                       accstr=accstr+'<div class="ui vertical fluid accordion text menu">\r\n';
                   else
<%--
                       accstr=accstr+'<div class="accordion transition hidden">';
--%>
                       accstr=accstr+'<div class="ui vertical fluid accordion text menu item">\r\n';
                   }
                if (plist.childs && plist.childs.length>0) {
                    accstr=accstr+'<div class="title">\r\n';
                } else {
                    accstr=accstr+'<div class="item">\r\n';
                }
                accstr=accstr+plist.podrEntityDTO.name
                if (plist.childs && plist.childs.length>0) {
                    accstr=accstr+'<i class="dropdown icon"></i>\r\n';
                }
                accstr=accstr+'</div>\r\n';
                if (plist.childs && plist.childs.length>0) {
                    accstr=accstr+'<div class="content menu">\r\n'
                    for (var i=0;i<plist.childs.length;i++) {
                        accstr=accstr+this.buildAccordion(plist.childs[i],level+1);
                    }
                    accstr=accstr+'</div>\r\n';
                }
                if (plist.childs && plist.childs.length>0) {
                   accstr=accstr+'</div>\r\n';
                }
            }
            return accstr;
        },
        convertPodrList:function(plist) {
            if (!plist) return;
            if (plist.podrEntityDTO.id>0)  {
                plist.id   = plist.podrEntityDTO.id;
                plist.name = plist.podrEntityDTO.name;
                delete plist.podrEntityDTO;
                if (_.isNull(plist.nodes)) {
                    delete plist.nodes;
                }
                else
                if (plist.nodes && plist.nodes.length>0) {
                    for (var i=0;i<plist.nodes.length;i++) {
                        if (plist.nodes[i])
                           this.convertPodrList(plist.nodes[i]);
                    }
                }
            }
            return;
        },
        getPodrCompoundName: function(id) {
            var uri = this.$root.rootPath+"/util/prcname/"+id;
            var vm  = this ;
            axios.get(uri, {
            })
                    .then(function (response) {
                        var rrec = JSON.stringify(response.data) ;
                        var r    = JSON.parse(rrec) ;
//                            console.log('r[0].id='+r[0].id);
                        if (r[0].id==0) {
//                            vm.namePodr=r[0].name;
//                            console.log('podrsel: getCompound Name namePodr='+r[0].name);
                            vm.$emit('enamepodr',r[0].name);
                        } else {
                            alertify.alert("Ошибка","Ошибка получения имени подразделения");
                        }
                    })

                    .catch(function (error) {
//                    alert("error getEntity="+error);
                alertify.alert("Ошибка","Ошибка получения имени подразделения "+error);
            });
        }
    },
    created:function() {
  //          this.getPodrList(1);
    }
});
</script>

