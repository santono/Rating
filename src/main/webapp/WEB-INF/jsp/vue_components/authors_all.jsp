<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<template id="template-authors-table">

<section class="authorsapp">
  <header class="header" v-if="!approved">
    <div class="ui label">Авторы:</div>
    <div class="inline field">
        <div class="ui checkbox" id="cb">
            <input tabindex="0" class="hidden" type="checkbox" v-model="isExternalAuthor">
            <label>внешний сотрудник</label>
        </div>
    </div>	
	<div  v-show="isExternalAuthor">
        <div class="fields">
            <div class="twelve wide field">
                 <input type="text" autocomplete="off"
                        placeholder="Укажите фамилию, имя, отчество автора"
                        v-model="newExternalAuthor"
                        v-on:keyup.enter="addExternalAuthor" >
            </div>
            <div class="four wide field">
                <div class="ui button" v-show="this.newExternalAuthor.length>0" v-on:click="addExternalAuthor" >Добавить</div>
            </div>
        </div>
	</div>
    <div v-show="!isExternalAuthor" >
       <div class="fields">
            <div class="ui search selection dropdown twelve wide field" id="add">
               <input v-on:blur="addAuthor(author)"
                v-on:keyup.enter="addAuthor(author)"
                name="author" type="hidden" v-model="newAuthorId">
                <i class="dropdown icon"></i>
                <div class="default text">Список авторов</div>
                <input type="text" class="search">
            </div>
            <div class="four wide field" >
                <div class="ui button"  v-on:click="addAuthor" >Добавить</div>
            </div>
       </div>
    </div>
	
  </header>
  <section class="main" v-show="authors.length" v-cloak>
      <div class="ui two column grid">
      <div class="twelve wide column">
      <table class="ui collapsing compact table">
      <tr v-for="author in authors"  :key="author.id">
          <td>{{ author.name }}</td>
          <td v-on:click="getprocent(author)">{{author.procent}}%</td>
        <td v-if="!approved">
            <div class="ui button"  v-on:click="getprocent(author)" >%</div>
            <div class="ui button"  v-on:click="removeAuthor(author)" ><i class="remove icon"></i></div>
        </td>
      </tr>
    </table>
    </div>
    <div class="four wide column">
        <div class="ui animated button" tabindex="0" v-on:click="calculateprocentforall" v-if="!approved">
            <div class="visible content">Расчет процентов</div>
            <div class="hidden content">В равных долях</div>
        </div>
    </div>
    </div>
  </section>
<%--
  <footer class="footer" v-show="authors.length" v-cloak>
    <span class="author-count">
	    <strong>{{ authors.length }}</strong>
     </span>
  </footer>
--%>
</section>

</template>

<script>


// app Vue instance
var authorstable=Vue.extend({
    template: '#template-authors-table',
	props: {
      savecmd  : 0,   //Команда Сохранить список авторов
      approved : false
	},

//var app = new Vue({
  // app initial state
  data: function() {return {
    authors      :    [],
    newAuthor    :    '',
	newExternalAuthor: '',
    newAuthorId  :     0,
    watchDog     :     0,
    editedAuthor :  null,
    visibility   : 'all',
    tempKey      :     0,
	isExternalAuthor:false
  }},

  watch: {
    authors: {
      handler: function (authors) {
//          console.log('--before emit '+JSON.stringify(authors));
//          console.log('--this.authors= '+JSON.stringify(this.authors));
//          this.$emit('esaveauthors',this.authors);
      },
      deep: true
    },
    savecmd:function(value) {
        console.log('inside watcher savecmd. value= '+value);
        if (value)
        if (value==1) {
            if (this.$parent.editingrec.id>0)
               this.saveAuthorsName();
        }
    },
    ntrid:function(value) {
          console.log('inside watcher computed ntrid value='+value+' this.watchDog='+this.watchDog);
          if (value==0) {
              if (this.watchDog!=1) {
                 this.watchDog=0;
                 console.log('authors. this.ntrid='+this.ntrid+' this.isnpr='+this.isnpr);
                 if (this.ntrid==0 && this.isnpr) {
//                     console.log('authors addSelfToAuthor');
                     this.addSelfToAuthor();
                 }
                 else {
//                   console.log('authors getAuthorsList');
                   this.getAuthorsList();
                 }
              }
          } else {
//              console.log('authors getAuthorsList watchDog==1');
              this.getAuthorsList();
              this.watchDog=1;
          }
    }

  },
  computed:{
      ntrid:function() {
          var retVal;
          retVal = this.$parent.editingrec.id;
          return retVal;
      },
      isnpr:function() {
          var retVal;
          retVal = this.$parent.isnpr;
          return retVal;
      },
      isadmin:function() {
          var retVal;
          retVal = this.$parent.isadmin;
          return retVal;
      }
  },

  methods: {
    addAuthor: function () {
      var id=$('#add').dropdown("get value");
      if (!id || (id<1)) return;

      var value = id;
      if (!value) {
        return
      }
	  this.getUserName(value); 
      this.newAuthorId = 0;
    },
    addAuthorFinish: function (id,name) {
//      console.log('entered in addAuthorFinish id='+id+" name="+name);
      if (!(id	&& name)) return;
      var procentcalc=0;
      if (!this.authors)
          procentcalc=100;
      else
      if (this.authors.length==0)
          procentcalc=100;
      this.authors.push({
        id    : id,
        name  : name.trim(),
        amode : 0,
        procent:procentcalc
      })
      this.newAuthor = '';
//      console.log('authors: emit authlistchanged - addauthor');
      this.$emit('authlistchanged');
    },
    addExternalAuthor: function () {
//      console.log('Just entered addExternalAuthor: amnt='+this.authors.length);
      var value = this.newExternalAuthor && this.newExternalAuthor.trim()
      if (!value) {
        return
      }
      this.tempKey++;
        var procentcalc=0;
        if (!this.authors)
            procentcalc=100;
        else
        if (this.authors.length==0)
            procentcalc=100;
      this.authors.push({
        id    : this.tempKey,
        amode : 1,
        name  : value,
        procent:procentcalc
      });
//      console.log('addExternalAuthor: amnt='+this.authors.length);
      this.newExternalAuthor = ''
//      console.log('authors: emit authlistchanged - addexternalauthor');
      this.$emit('authlistchanged');
    },
    saveAuthorsFinish : function() {
        this.$emit('esaveauthfinished');
        alertify.set('notifier','position', 'top-center');
        alertify.success("Список авторов сохранен.");

    },


    removeAuthor: function (author) {
//      console.log('removeAuthor: amnt='+this.authors.length);

      this.authors.splice(this.authors.indexOf(author), 1);
//      console.log('authors: emit authlistchanged - removeauthor');

      this.$emit('authlistchanged');
    },

    setAuthorsDD:function() {
          if ($('#add').length>0) {
              var uri = this.$root.rootPath
                      + "/util/semanticui/dropdown/tags/{query}";
              $('#add').dropdown({
                  apiSettings: {
                      // this url parses query server side and returns filtered results
                      url: uri
                  }
              })
              ;
              if ($('#cb').length>0) {
                  $('#cb')
                   .checkbox()
                  ;                  
              }
          }
    },
      getprocent:function(author) {
        if (!author) {
            alertify.error('Не указан автор!');
            return false;
        }
        alertify.prompt('Введите процент (0-100) автора '+author.name,author.procent, function(evt, value) {
                    if (value) {
                        var ans=_.toNumber(value);
                        if ((ans>=0) && (ans<=100)) {
                            author.procent=value;
                            this.$emit('authlistchanged');
                        }
                    }
                })
                .set('type', 'number')
                .setHeader('<em> Процент </em> ');
                return false;
      },
      calculateprocentforall:function() {
          if (!this.authors) return;
          if (!_.isArray(this.authors)) return;
          if (this.authors.length<1) return;
          var curr    = 0;
          var procent = 100 / this.authors.length;
          procent     = _.toInteger(procent);
          for (var i=0;i<this.authors.length;i++) {
              if (i==this.authors.length-1) {
                  this.authors[i].procent=_.toInteger(100-curr);
              } else {
                  this.authors[i].procent=procent;
                  curr=curr+procent;
                  if (curr>100) curr=100;
              }
          }
          this.$emit('authlistchanged');
      },
      getUserName: function(id) {
          var uri=this.$root.rootPath+"/util/user/name/"+id;
          var vm=this;
          axios.get(uri, {
          })
                  .then(function (response) {
                      //  var rrec=JSON.stringify(response.data);
                      var rrec=response.data;
                      if (_.isArray(rrec)) {
                          var name=rrec[0].name.trim();
//                          console.log('getUserName: rrec='+rrec+" name="+name+" id="+id);
                          vm.addAuthorFinish(id,name);
                      } else {
                          alert("wrong user name getting "+rrec);
                      }
                  })

                  .catch(function (error) {
              alertify.alert("Ошибка","error getUserName="+error);
          });
      },
      getAuthorsList: function() {
//          console.log('enter in getAuthorsList');
          if (this.ntrid==undefined) {
              return
          }
          if (this.ntrid<1) {
              return
          }
          var uri = this.$root.rootPath+"/util/author/"+this.ntrid;
          var vm=this;
          axios.get(uri, {
          })
                  .then(function (response) {
                      //  var rrec=JSON.stringify(response.data);
                      var rrec=response.data;
                      if (_.isArray(rrec)) {
                          if (rrec[0].shifr=='Err') {}
                          else
                          if (rrec[0].shifr=='Empty') {
                              vm.authors=[];
                          }
                          else   {
                             vm.authors=[];
                             if (rrec.length>0) {
                                 for (var i=0;i<rrec.length;i++) {
                                     var author={};
                                     author.id      = rrec[i].id;
                                     author.name    = rrec[i].name;
                                     author.procent = rrec[i].shifr;
                                     if (author.id>0) author.amode=0;
                                     else author.amode=1;
                                     vm.authors.push(author);
//                                     console.log('id='+author.id+' name='+author.name+' amode='+author.amode);
                                 }
                             }
//                             console.log('nmb of authors received='+rrec.length);
//                             console.log('authors ='+JSON.stringify(vt.authors));
                          }
                      } else {
                          alert("error getting author list getting ");
                      }
                  })

                  .catch(function (error) {
                    alert("error gettingauthor list="+error);
          });
      },
      saveAuthorsName: function() {
          var uri = this.$root.rootPath+"/util/authors/save";
          var vm    = this ;
          var param = {};
          param.id=this.ntrid;
          if (_.isArray(this.authors))
             if (this.authors.length>0)
                this.authors.forEach(function(item){
                    if (item.amode==1) item.id=0;
                });
          param.authors=this.authors;
          var paramJSON=JSON.stringify(param);
          axios.post(uri,paramJSON,
          {headers:{
              'Content-Type': 'application/json'}}

          )
                  .then(function (response) {
                      //  var rrec=JSON.stringify(response.data);
                      if (response.data) {
                         if ((response.data.length==1)
                          && (response.data[0].name=='Ok')) {
                             vm.saveAuthorsFinish();
//                             vm.$notify("alert", "Записи сохранена.");
                         } else {
                             if (response.data.length>0) {
                                 var z=$('#modalntr').css('z-index');
//                                     vm.hasErrors=true;
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

                  .catch(function (error) {
              alertify.alert("Ошибка","error saveAuthorsName="+error);
          });
      },
      addSelfToAuthor:function() {
          if (this.$parent.editingrec.id<1 && this.isnpr) {
              var author     = {};
              author.id      = this.$root.user.id;
              author.name    = this.$root.user.fio;
              author.procent = 100;
              author.amode   = 0;
              this.authors.push(author);
          }
      }

  },

  mounted:function() {
//        console.log('mounted getAuthorsList');
//        this.getAuthorsList();
        this.setAuthorsDD();
//        console.log('authorstable: approved='+this.approved);
  },
    created:function() {
//        console.log('authors created getAuthorsList');
        this.getAuthorsList();
//        this.setAuthorsDD();
//        console.log('authorstable: approved='+this.approved);
    }
})


</script>