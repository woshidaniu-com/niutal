<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="com.woshidaniu.globalweb.action.IndexAction"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
	<!-- tool bar-start  -->
	<div class="row sl_add_btn">
	    <div class="col-sm-12">
	    	<!-- 加载当前菜单栏目下操作    :   -->
	     	<%=IndexAction.cxButtons(request.getParameter("gnmkdm"))%>
			<!-- 加载当前菜单栏目下操作 -->
	    </div>
	</div>
	<!-- tool bar-end  -->
	<!-- btn-start  -->
	<s:if test="model.table=='niutal_xtgl_njdmb'">
		<s:hidden id="tableName" value="niutal_xtgl_njdmb"></s:hidden>
		<s:form  cssClass="form-horizontal sl_all_form" role="form" name="form"  method="post" action="/dlzydzgl/dlzydzgl_cxDlzydzxx.html" theme="simple" onsubmit="return false;">
			<div class="row" id="autogrid">
				 <div class="col-md-4 col-sm-6">
				    <div class="form-group">
				      <label for="" class="col-sm-4 control-label">是否使用</label>
				      <div class="col-sm-8">
				   		  <s:select name="sfsy_cx" list="#{'1':'是','0':'否'}" cssClass="form-control chosen-select" id="sfsy_cx" headerKey="" headerValue="--请选择--"></s:select>
						  <SCRIPT type="text/javascript">
					    	jQuery('#sfsy_cx').trigger("chosen");
					      </SCRIPT>	
				      </div>
				    </div>
			     </div>
			     <div class="col-md-4 col-sm-6">
				    <div class="form-group">
				      <label for="" class="col-sm-4 control-label">年级</label>
				      <div class="col-sm-8">
				   		<s:textfield id="njmc_cx" name="njmc_cx" cssClass="form-control" placeholder="按年级代码、年级名称模糊查询"></s:textfield>
				      </div>
				    </div>
			      </div>
			</div>
		</s:form>
		
		<div class="row sl_aff_btn">
			<!-- btn-start -->
			<div class="col-sm-12">
				<button  type="button" class="btn btn-primary btn-sm" id="search_go" 
				onclick="searchResult();return false;">
					查 询
				</button>
			</div>
			<!-- btn-end -->
		</div>
		
	</s:if>
	<s:elseif test="model.table=='niutal_xtgl_yjfszhxxb'">
		<s:hidden id="tableName" value="niutal_xtgl_yjfszhxxb"></s:hidden>
		<s:form  cssClass="form-horizontal sl_all_form" role="form" name="form"  method="post" action="/dlzydzgl/dlzydzgl_cxDlzydzxx.html" theme="simple" onsubmit="return false;">
			<div class="row" id="autogrid">
			     <div class="col-md-4 col-sm-6">
				    <div class="form-group">
				      <label for="" class="col-sm-4 control-label">邮箱账号</label>
				      <div class="col-sm-8">
				   		<s:textfield id="yjzh_cx" name="yjzh_cx" cssClass="form-control" placeholder="按邮箱账号模糊查询"></s:textfield>
				      </div>
				    </div>
			      </div>
			      <div class="col-md-4 col-sm-6 hidden">
				    <div class="form-group">
				      <label for="" class="col-sm-4 control-label">发送类型</label>
				      <div class="col-sm-8">
				   		  <s:select name="fslx_cx" list="#{'cjmmfs':'成绩密码发送','tzfs':'通知发送'}" cssClass="form-control chosen-select" id="fslx_cx" headerKey="" headerValue="--请选择--"></s:select>
						  <SCRIPT type="text/javascript">
					    	jQuery('#fslx_cx').trigger("chosen");
					      </SCRIPT>	
				      </div>
				    </div>
			     </div>
			</div>
		</s:form>
		
		<div class="row sl_aff_btn">
			<!-- btn-start -->
			<div class="col-sm-12">
				<button  type="button" class="btn btn-primary btn-sm" id="search_go" 
				onclick="searchResult();return false;">
					查 询
				</button>
			</div>
			<!-- btn-end -->
		</div>
	</s:elseif>
	<s:else>
		<!-- btn-start -->
		<div  style="display:none;" class="row sl_aff_btn">
			<div class="col-sm-12">
				<button  type="button" class="btn btn-primary btn-sm" id="search_go" onclick="searchResult();return false;">
					查 询
				</button>
			</div>
		</div>
		<!-- btn-end  -->
	</s:else>
	<div class="panel panel-default">
		<div id="searchBox"  class="panel-heading">
			<h3 class="panel-title">${caption}</h3>
		</div>
		<div class="panel-body" style="padding:0px;">
			<table id="tabGrid"></table>
			<div id="pager"></div>
		</div>
	</div>
</body>
<%@ include file="/WEB-INF/pages/globalweb/head/jqGrid4.6.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/validation.ini"%>
<%@ include file="/WEB-INF/pages/globalweb/head/wdatePicker.ini"%>
<script type="text/javascript">
//var caption   = "${caption}";
var sortname  = "${sortname}";
var sortorder = "${sortorder}";
var colModel  = eval("${colModel}");	
// 是否支持多选
var multiselect = $.toBoolean("${multiselect}"); 
var primary_key = "${primary_key}";	
var table		= "${table}";
var width		= '${width}';
</script>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/jcdm/cxJcdmIndex.js?ver=<%=jsVersion%>"></script>
</html>
