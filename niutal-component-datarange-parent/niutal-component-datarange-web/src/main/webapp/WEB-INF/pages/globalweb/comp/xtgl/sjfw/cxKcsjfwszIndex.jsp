<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
<style>
.ui-jqgrid tr.jqgrow td {
  	vertical-align:middle;
  	text-align: center;
}
.sjfw_table td{
  	height:auto;
  	border: 0px;
}

.lisnavBox{
	clear: both;
}

.lisnavBox li{
	min-height: 23px;
	line-height: 23px;
	list-style: none;
	list-style-type: none;
	overflow: hidden;
	-moz-background-clip: padding;
	-webkit-background-clip: padding-box;
	background-clip: padding-box;
}

.lisnavBox li p{
	background: none;
	padding-bottom: 0;
	margin: 0;
}

.item-list>li {
	padding: 5px 9px;
	margin-top: -1px;
	position: relative;
	clear: both;
}
.item-list>li .left{width: 20%;}
.item-list>li .right{width: 80%;line-height: 20px;}
.item-list .col-xs-12{
	word-break:break-all;	
	border-bottom: 1px solid #DDD;
}
.item-list .col-xs-12 {
	word-break:break-all;	
	border-bottom: 1px solid #DDD;
}
.item-list .item-more{
	padding: 3px;
	text-align: right;
	float: right;
}

#tabGrid2_sjfwList{
	text-align: left;
}
.ui-jqgrid tr.jqgrow td{
	white-space:normal;
}
.ui-jqgrid tr.jqgrow td:FIRST-CHILD{
	padding: 0px 10px !important;
}
</style>
</head>
<body>
<!-- tool bar-start  -->
<div class="row sl_add_btn">
    <div class="col-sm-12">
   	 	<!-- 加载当前菜单栏目下操作    : N010210  -->
     	<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
		<!-- 加载当前菜单栏目下操作 -->
    </div>
</div>
<!-- tool bar-end  -->
<form class="form-horizontal sl_all_form" id="sl_all_form">
<s:hidden id="kzdx" name="kzdx" value="kc"></s:hidden>
<div class="row">
	<div class="col-md-4 col-sm-4">
	   <div class="form-group">
	     	<label for="" class="col-sm-4 control-label"><span style="color:red;">*</span>所属角色</label>
	     	<div class="col-sm-8">
			    <s:select list="jsxxList" listKey="jsdm" listValue="jsmc" cssClass="form-control width-80 chosen-select" id="jsdm_query"></s:select>
			    <SCRIPT type="text/javascript">
		    		jQuery('#jsdm_query').trigger("chosen");
		    	</SCRIPT>
	    	</div>
	  	</div>
	</div>
	<div class="col-md-4 col-sm-4">
	  	<div class="form-group">
	    	<label for="" class="col-sm-4 control-label">所属机构</label>
	    	<div class="col-sm-8">
			   	<s:select list="jgdmsList" listKey="jg_id" listValue="jgmc" headerKey="" headerValue="全部" cssClass="form-control width-80 chosen-select" id="jgdm_query"></s:select>
	    	</div>
	    	<SCRIPT type="text/javascript">
	    		jQuery('#jgdm_query').trigger("chosen");
	    	</SCRIPT>
	  	</div>
	</div>
	<div class="col-md-4 col-sm-4">
	   <div class="form-group">
	     	<label for="" class="col-sm-4 control-label">用户名/姓名</label>
	     	<div class="col-sm-8">
	     		<input value=""  class="form-control width-80" id="yhm_query" data-toggle="autocomplete" data-autocomplete-type="xs" data-set-value="value" data-real-value="key"    placeholder="输入用户名或姓名">
	    	</div>
	  	</div>
	</div>
  </div>
</form>
<!-- btn-start  -->
<div class="row sl_aff_btn" id="searchResult">
	<div class="col-sm-12">
		<button type="button" class="btn btn-primary btn-sm" id="search_go"> 查 询 </button>
	</div>
</div>
<!-- btn-end  -->
<div class="formbox"  style="">
	<table id="tabGrid2" class="tabGrid"></table>
	<div id="pager"></div>
</div>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/tooltips.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/autocomplete.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/wdatePicker.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/simplevalidate.ini"%>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comp/xtgl/sjfw/dataRangeIndex.js?ver=<%=jsVersion%>"></script>
<!-- 子页面的js -->
<%@ include file="/WEB-INF/pages/globalweb/head/datarange.ini"%>
</body>
</html>
