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

<script type="text/javascript" src="<%=systemPath %>/js/jquery/jquery-1.6.2.min.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=stylePath %>/js/lhgdialog/lhgdialog.min.js?_rv_=<%=resourceVersion%>"></script> 
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wj_model.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wj_jieXi.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wj_cs.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wj_oneSt.js?_rv_=<%=resourceVersion%>"></script>
<script type="text/javascript" src="<%=systemPath %>/js/zfxg/comp/wjdc/wj_main.js?_rv_=<%=resourceVersion%>"></script>

<script type="text/javascript">

	jQuery(function(){
		var result = jQuery("#result").val();
		if(result!=""){
			alertInfo(result);
		}
	});
	
</script>

</head>
<body class="body_dcd">
<form action="zfxg/wjdc/stgl_saveEditStxx.html" method="post">
<!--问卷id：<input type="text" name="wjModel.wjid" value="1"/>-->
<input type="hidden" id="wjid" name="wjModel.wjid" value="${wjModel.wjid}"/>
<input type="hidden" id="wjlx" value="${wjModel.wjlx}"/>
<input type="hidden" id="wjzf" value="${wjModel.wjzf}"/>
<input type="hidden" id="fblx" value="${wjModel.fblx}"/>
<input type="hidden" id="djrid" value="${wjModel.djrid}"/>
<input type="hidden" id="result" name="result" value="${result}"/>
<input type="hidden" id="fblx" name="fblx" value="${wjModel.fblx}"/>
<input type="hidden" id="hidden_autoaddstbh" value="${wjModel.autoaddstbh}"/>
<div class="main_dcd">
  <div class="single_wjdc">
  	  <div class="time"><span>状态：${wjModel.wjzt}</span><span>时间：${wjModel.cjsj}</span></div>
      <h3>${wjModel.wjmc}</h3>
      <div class="text">
          ${wjModel.jsy}
      </div>
	  
	  
	  
	  <div style="padding: 10px; margin: 5px;" class="ui-tabs ui-widget ui-widget-content ui-corner-all">
	  			<div style="display: none;">
	  				选项格式是否空格拆分:
	  				<select id="format_spaceSplitXxxx" name="format_spaceSplitXxxx" onchange="WjModel_Parameter.format_spaceSplitXxxx=(this.value=='true'?true:false)">
	  					<option value="true">是</option>
	  					<option value="false">否</option>
	  				</select>
	  			</div>
                <div style="display: none;">
                    <input type="button" value="格式化" onclick="wjFormat()"/>
                    <input type="button" value="答卷提交" onclick="wjSubmitCheck()"/>
<!--                    <input type="button" value="获取问卷" onclick="getWjstxx()"/>-->
                    <button type="button" onclick="wjTmEditSaveCheck();">提交</button>
                    <label>
                        题目配置：
                    </label>
                    <input type="button" value="单选题" onclick="wjjx.addConfig('单选题')"/>
					<input type="button" value="多选题" onclick="wjjx.addConfig('多选题')"/>
					<input type="button" value="单选组合" onclick="wjjx.addConfig('单选组合')"/>
					<input type="button" value="多选组合" onclick="wjjx.addConfig('多选组合')"/>
					<input type="button" value="单行文本" onclick="wjjx.addConfig('单行文本')"/>
					<input type="button" value="多行文本" onclick="wjjx.addConfig('多行文本')"/>
					<a target="wbtjsysm" href="/ecupl-wsdx-main-1.0.0/static/wenjuan/wbtjsysm.html" style="float: right;">使用说明</a>
                </div>
                <div style="width: 100%" align="right">
                	<a target="wbtjsysm" href="download/wjdc_wjst_template_${wjModel.wjlx}.txt" style="float: right;">
                	<font color="red" size="3">模板下载</font>
                	</a>
                	<button type="button" onclick="wjTmEditSaveCheck();" style="float: right">提交</button>
                	<button type="button" onclick="wjFormat();" style="float: right">格式化</button>
                	题目是否自动编号:
	  				<select id="autoAddStbh" name="wjModel.autoaddstbh" onchange="WjModel_Parameter.autoAddStbh=(this.value=='true'?true:false)">
	  					<option value="true">是</option>
	  					<option value="false">否</option>
	  				</select>
                </div>
                <textarea style="width: 100%;"  rows="10" id="textInput" name="textInput"></textarea>
            </div>
	  
	  
      <fieldset id="wjview" class="question_con">
      	<legend>问卷内容</legend>
      </fieldset>
      
      <div class="text">
          ${wjModel.jwy}
      </div>
      
  </div>
</div>


</form>
</body>
</html>
