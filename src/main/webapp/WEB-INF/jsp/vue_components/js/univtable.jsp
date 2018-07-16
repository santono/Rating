<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
    //     Vue.component('univtable',{
    var univtable=Vue.extend({
        template: '#template-univ-table',
        //   props: ['univ'],
        props: {
                currentOwner:0
        },
        data:function() {
            return {universities: null,
                univ:'',
                selecteduniv:'',
                selectedindex:-1,
                showModal:false,
                owner:0,
                ownername:""
            };

        },
        components: {
            'univrow':univrow,
            'univform':univform
        },

        methods: {
            setEShowModal:function(newVal,newUniv) {
//                alert('setEShowModal '+newVal);
                this.showModal=newVal;
                if (newUniv) {
                    if (!_.isEqual(this.selecteduniv,newUniv)) {
                        this.$nextTick(function () {
//                             this.universities[0].name="проба апдейта";
                            this.selecteduniv=_.clone(newUniv);
                            if (this.selectedindex>=0)
                                Vue.set(this.universities,this.selectedindex,newUniv);
                        });
                    }
                }
            },
            editrec:function(curruniv,index) {

                this.selecteduniv=curruniv;
                this.selectedindex=index;
                this.showModal=true;
            },
            adduniv:function() {
                this.selecteduniv=null;
                this.selecteduniv.id=0;
                this.selectedindex=null;
                this.showModal=true;
            },

            deleteUniv: function(univ){
                vm.universities.$remove(univ)
            },
            getUnivName:function() {
                <c:url value="/util/univ" var="uri2" />
                var uri3="${uri2}";
                var uri=uri3+"/"+this.owner;
                var vt=this;
                var finished=false;
                axios.get(uri, {
                })
                        .then(function (response) {
                            finished=true;
                            vt.ownername=response.data.name;
                        })

                        .catch(function (error) {
                    finished=true;
                });
                var i;
                while (finished) {
                    i=1;
                }

            },

            getUnivList:function() {
                <c:url value="/util/univs" var="uri2" />
                var uri3="${uri2}";
                var uri=uri3+"/"+this.owner;
                var vt=this;
                var finished=false;
                axios.get(uri, {
                })
                        .then(function (response) {
                            finished=true;
                            vt.universities=response.data;
                        })

                        .catch(function (error) {
                    finished=true;
                });
                var i;
                while (finished) {
                    i=1;
                }

            }
        },
        watch: {
          currentOwner:function(val) {
              this.owner=val;
              this.getUnivName();
          },
          '$route':function(to, from) {
              alert('to='+to+' from='+from);
        // обработка изменений параметров пути...
          }
        },
//        beforeRouteEnter:function(to, from, next) {
//           getPost(to.params.id, function(err, post) {
//               next(vm => vm.setData(err, post))
//           })
//                ,
// если путь изменяется, а компонент уже отображён,
// логика будет немного иной
        mounted:function () {
            this.$nextTick(function () {
                $('.ui.sidebar').sidebar('toggle');
            });
        },
        beforeRouteUpdate:function(to, from, next) {
          alert('beforeRouteUpdate to='+to+' from='+from)
        // обработка изменений параметров пути...
        // не забудьте вызывать next()
         },
        created: function() {
//            alert('created '+this.$route.params.id);
            this.owner=this.$route.params.id;
            this.getUnivName();
            this.getUnivList();
        }

    });

</script>