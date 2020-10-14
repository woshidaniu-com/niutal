<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>问卷调查</title>
    <base href="<%=basePath%>">
   
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v4.ini"%>
	
<!--	<link rel="stylesheet" href="http://10.71.19.19:82/zfstyle_v4/css/public.css" type="text/css" media="all" />-->
<!--	<link rel="stylesheet" href="http://10.71.19.19:82/zfstyle_v4/css/website.css" type="text/css" media="all" />-->
<!--	<link rel="stylesheet" href="http://10.71.19.19:82/zfstyle_v4/css/global.css" type="text/css" media="all" />-->
<!--[if IE 6]> 
<script src="http://10.71.19.19:82/zfstyle_v4/js/ie6comm.js"></script> 
<script> 
DD_belatedPNG.fix('img,.mainbody,.topframe,.mainframe,.botframe,.menu,.type_mainframe'); 
</script> 
<![endif]-->

<%--<script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery-1.6.2.min.js"></script>--%>
<script type="text/javascript" src="<%=stylePath %>/js/lhgdialog/lhgdialog.min.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wj_model.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wj_jieXi.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wj_cs.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wj_oneSt.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wj_main.js?_rv_=<%=resourceVersion%>"></script>


</head>
<body class="body_dcd">
<form action="zfxg/wjdc/stgl_saveEditStxx.html" method="post">
<input type="hidden" id="wjid" name="wjModel.wjid" value="${wjModel.wjid}"/>
<input type="hidden" id="wjlx" value="${wjModel.wjlx}"/>
<input type="hidden" id="wjzf" value=""/>
<input type="hidden" id="djrid" value="${wjModel.djrid}"/>
<input type="hidden" id="wjzt" value="${wjModel.wjzt}"/>
<input type="hidden" id="fblx" value="${wjModel.fblx}"/>
<input type="hidden" id="djzt" value="${wjModel.djzt}"/>
<input type="hidden" id="sfgq" value="${wjModel.sfgq}"/>
<input type="hidden" id="result" value="${result}"/>
<input type="hidden" id="hidden_autoaddstbh" value="${wjModel.autoaddstbh}"/>
<input type="hidden" name="source" id="source" value="${source}"/>

<div class="main_dcd">
  <div class="single_wjdc">
      <h3 id="wjmc_header">${wjModel.wjmc}</h3>
      <div class="text">
          ${wjModel.jsy}
      </div>
	  
      <fieldset id="wjview" class="question_con">
      	<legend>问卷内容</legend>
      </fieldset>
      <div class="text">
          ${wjModel.jwy}
      </div>
      <div style="width: 100%" align="center">
		  <input id="but_tj" type="button" value="提交" onclick="wjSubmitCheck()"/>
	      <input type="button" value="关闭" onclick="window.close()"/>
      </div>
      
  </div>
</div>


</form>
</body>
</html>
<script type="text/javascript">
	if(jQuery("#djzt").val()=="已答卷"||jQuery("#wjzt").val()!="运行" || jQuery("#sfgq").val()=="1" || jQuery("#djrid").val() != "<s:property  value='#session.user.yhm' />"){
		jQuery("#but_tj").remove();
	}

	//如果是匿名问卷，如要显示该问卷的访问地址
	if(jQuery('#fblx').val() == '1' && jQuery('#source').val() != 'tj'){
		var dest = window.location.href.replace(/stgl_showStxx/, "nmwj_nmdj");
		var dest_a = jQuery('<a target="_blank" style="font-size:12px">答卷链接：' + dest + '</a>');
		dest_a.css({'display': 'block', 'font-weight': 'normal', 'line-height': '14px'});
		dest_a.attr('href' , dest);
		dest_a.appendTo(jQuery('#wjmc_header'));
	}
	
	
	if(jQuery("#result").val()!=""){
		alert('${result}','',{"clkFun":function(){
			window.opener.document.location.reload();
			window.close();
		}});
	}
</script>