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
<form action="zfxg/wjdc/nmwj_saveEditStxx.html" method="post">
<input type="hidden" id="wjid" name="wjModel.wjid" value="${wjModel.wjid}"/>
<input type="hidden" id="wjlx" value="${wjModel.wjlx}"/>
<input type="hidden" id="fblx" value="${wjModel.fblx}"/>
<input type="hidden" id="wjzf" value=""/>
<input type="hidden" id="djrid" value="${wjModel.djrid}"/>
<input type="hidden" id="wjzt" value="${wjModel.wjzt}"/>
<input type="hidden" id="djzt" value="${wjModel.djzt}"/>
<input type="hidden" id="result" value="${result}"/>
<input type="hidden" id="hidden_autoaddstbh" value="${wjModel.autoaddstbh}"/>

<div class="main_dcd">
  <div class="single_wjdc">
      <h3>${wjModel.wjmc}</h3>
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
		  <input id="but_tj" type="button" value="提交" onclick="nmwjSubmitCheck(this)"/>
	      <input type="button" value="关闭" onclick="window.close()"/>
      </div>
      
  </div>
</div>


</form>
</body>
</html>