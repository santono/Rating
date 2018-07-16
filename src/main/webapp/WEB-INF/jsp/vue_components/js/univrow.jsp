<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<script>
var univrow=Vue.extend({
    template: '#template-univ-row',
    props: ['univ','index'],
    methods: {
        edituniv:function() {
            this.$emit('editu',this.univ,this.index);
        },

       deleteUniv: function(univ){
          vm.universities.$remove(univ);
       }
    }
});
</script>

