<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>

<body>
	<!--操作按钮 开始 -->
	<div class="row sl_add_btn">
	    <div class="col-sm-12">
	      	<!-- 加载当前菜单栏目下操作     -->
	      		<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
			<!-- 加载当前菜单栏目下操作 -->
	    </div>
	</div>
	<!--操作按钮 结束  -->
	<s:form  cssClass="form-horizontal sl_all_form" role="form" name="form"  method="post" action="" theme="simple" onsubmit="return false;">
		<div class="row" id="autogrid">
			  <div class="col-md-4 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">批次</label>
			      <div class="col-sm-8">
			          <s:select id="cx_pcdm" name="cx_pcdm" list="njList" listKey="njdm_id" listValue="njmc" headerKey="" 
			          cssClass="form-control chosen-select" headerValue="全部" value="%{synj}"/>
			          <SCRIPT type="text/javascript">
			    	  	jQuery('#cx_pcdm').trigger("chosen");
			    	  </SCRIPT>
			      </div>
			    </div>
			  </div>
			  <div class="col-md-4 col-sm-6">
			    <div class="form-group">
			      <label for="" class="col-sm-4 control-label">专业</label>
			      <div class="col-sm-8">
			   		  <s:textfield id="cx_zydm" name="cx_zydm" cssClass="form-control" placeholder="按专业代码或专业名称查询"></s:textfield>
			      </div>
			    </div>
		      </div>
		</div>
	</s:form>
	<!--查询按钮  开始-->
	<div class="row sl_aff_btn">
	   <div class="col-sm-12">
	     	<button type="button" class="btn btn-primary btn-sm" id="searchResult" >查询</button>
	   </div>
	</div>
	<!--查询按钮  结束-->
	<table id="tabGrid"></table>
	<div id="pager"></div>
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/selectPanel.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/wdatePicker.ini"%>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/xxzywh/xxzyxx.js?ver=<%=jsVersion%>"></script>
</html>