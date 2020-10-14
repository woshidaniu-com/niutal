<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="z" uri="/niutal-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagefunc_v5.ini"%>
	<script type="text/javascript">
	
		 function Interpret(){
			var ySql  = $("#ySql").val();//原始SQL
			var parameter =  $("#parameter").val();
			var arrays  = wz(ySql);
			var pmArrays  = parameterArrays(parameter);
			if(arrays.length==pmArrays.length){
			 	var sqlArray = new Array();
				$.each(ySql.split("?"),function (i,v){
					if(i==(ySql.split("?").length-1)){
						sqlArray.push(v);
					}else{
						sqlArray.push(v+"'"+pmArrays[i].trim()+"'");
					}
				});
				$("#resultSql").val(sqlArray.join(""));
			}else{
				$.alert("请检查传参个数,结果仅供参考");
				return ;
			}
		 }
		

		function wz(strSql){
			var obj = {};
			var arrays  = new Array();
			for(var i=0,k; i<strSql.length; i++) {
				k = strSql.charAt(i);
				if(obj[k]) {
					obj[k].push(i);
				}else {
					obj[k] = [];
					obj[k].push(i);
				}
			}
			for(var o in obj) {
				if(o == "?") {
					for(var i=0; i<obj[o].length; i++) {
						arrays.push(obj[o][i]); 
					}
				}
			}
			return arrays;
		}

		function parameterArrays(pString){
		    var _pString  = pString+","
		    _pString =  _pString.replace(/\(StringReader\)/g,'(String)');
		    _pString =   _pString.replace(/null,/g,'null(String),');
			 var arrays  = new Array();
			 $.each(_pString.split("(String),"),function(i,v){
				   if((_pString.split("(String),").length-1)!=i){
					   arrays.push(v); 
					 }
			});

			 return arrays;
		}
		 
		
	</script>
</head>
<body>
<!-- top -->
<header class="navbar-inverse top2">
  <div class="container">
    <div class="navbar-header">
      <button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".bs-navbar-collapse"> 
	  <span class="sr-only">我是大牛SQL在线翻译</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span> </button>
      <a href="../" class="navbar-brand">我是大牛SQL在线翻译</a> </div>
  </div>
</header>
<!-- top-end -->

<div class="container sl_all_bg">
  <Div class="row">
    <div class="col-sm-12">
        <div class="panel-body">
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title">原SQL</h4>
            </div>
            <div class="panel-footer">
              <textarea id="ySql" name="ySql" class="form-control" rows="2"></textarea>
            </div>
          </div>
          <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title">参数</h4>
            </div>
            <div class="panel-footer">
              <textarea id="parameter" name="parameter"  class="form-control" rows="2"></textarea>
            </div>
          </div>
		<div class="form-group">
				<button type="button" onclick="Interpret()" class="btn btn-lg btn-primary">翻译</button>
		</div>
		  <div class="panel panel-default">
            <div class="panel-heading">
              <h4 class="panel-title">结果</h4>
            </div>
            <div class="panel-footer">
              <textarea  id="resultSql" name="resultSql" class="form-control" rows="2"></textarea>
            </div>
          </div>

        </div>
 
    </div>
  </Div>
</div>
<!-- footer -->
<jsp:include page="/WEB-INF/pages/globalweb/bottom.jsp" />
<!-- footer-end -->
</body>
<script type="text/javascript">
var ua =  window.navigator.userAgent;
$("#ySql").val( ua);
$("#resultSql").val( $.param(jQuery.uaMatch(ua)));
</script>
</html>
