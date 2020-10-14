<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<!-- tool bar-start  -->
	<div class="row sl_add_btn">
	    <div class="col-sm-12">
	    	<!-- 加载当前菜单栏目下操作    : N011001  -->
	     	<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
			<!-- 加载当前菜单栏目下操作 -->
	    </div>
	</div>
	<!-- tool bar-end  -->
	<!-- form -start  -->
	<s:form  cssClass="form-horizontal sl_all_form" role="form" name="form" method="post" action="/xtgl/xwgl_cxXw.html" theme="simple" onsubmit="return false;">
	  <div id="searchBox" class="row">
		  <div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">通知标题</label>
		      <div class="col-sm-8">
		        <input type="text" class="form-control" name="xwbt" id="xwbt" placeholder="请输出标题进行模糊筛选">
		      </div>
		    </div>
		  </div>
		  
		 <div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">是否发布</label>
		      <div class="col-sm-8">
		   		<s:select name="sffb" list="sfList" listKey="key" cssClass="form-control chosen-select" listValue="value" id="sffb" headerKey="" headerValue="全部">
				</s:select>
				<SCRIPT type="text/javascript">
		    		jQuery('#sffb').trigger("chosen");
		    	</SCRIPT>
		      </div>
		    </div>
		  </div>
		  
		  <div class="col-md-4 col-sm-6">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">是否置顶</label>
		      <div class="col-sm-8">
		        <s:select cssClass="form-control chosen-select" id="sfzd" name="sfzd" list="sfList" listKey="key" listValue="value" headerKey="" headerValue="全部">
				</s:select>
				<SCRIPT type="text/javascript">
		    		jQuery('#sfzd').trigger("chosen");
		    	</SCRIPT>
		      </div>
		    </div>
		  </div>
	  </div>
	</s:form>
	<!-- form -end  -->
	<!-- btn-start  -->
	<div class="row sl_aff_btn">
		<div class="col-sm-12">
			<button type="button" class="btn btn-primary btn-sm" id="search_go"	onclick="searchResult();return false;"> 查 询 </button>
		</div>
	</div>
	<!-- btn-end  -->
	<!-- table-start  -->
	<table id="tabGrid"></table>
	<div id="pager"></div>
	<!-- table-end  -->
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/xwgl/xwgl.js?ver=<%=jsVersion%>"></script>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/wdatePicker.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/kindeditor.ini"%>
</html>
