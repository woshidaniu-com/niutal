<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>
</head>
<body>
<s:if test="sfejsqFlag == 0">
<div class="row" style="padding-top: 0px;">
	<div class="col-md-12 col-sm-12">
		<div class="alert alert-error align-center bigger-180 red " style="line-height: 450px;">
	    	当前登录角色无二级授权权限，如有多个角色，可切换其他角色再次尝试！
	    </div>
	</div>
</div>
</s:if>
<s:else>		
	<!-- ################以下是相应tab区域的条件，组件根据名称自动拷贝到组件中，并自动删除原html################################ -->
	<!-- 开课部门列表 -->
	<div aria-labelledby="view_xtgl_kkbm"  style="display: none;" class="row">
	
	</div>	
	<s:hidden id="js_id" name="js_id"/>
	<s:hidden id="kzdx" name="kzdx"></s:hidden>
	<s:iterator value="yhmList">
		<input type="hidden" name="yhm" value="<s:property/>" />
	</s:iterator>
	<div id="dataRangeBox">
		
	</div>
	<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/sjfw/kc_sjfwsz.js?ver=<%=jsVersion%>"></script>
</s:else>
</body>
</html>
