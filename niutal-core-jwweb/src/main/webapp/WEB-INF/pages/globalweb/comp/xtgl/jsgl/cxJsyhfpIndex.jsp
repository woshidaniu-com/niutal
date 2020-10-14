<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/v5_url.ini"%>
	<script type="text/javascript">
		var jsdm="${jsdm}";	
	</script>
</head>
<body >
  <s:form  cssClass="form-horizontal sl_all_form " role="form" name="form" id="ajaxForm"  method="post" action="xscfxxwh_zjBcXscfxx.html" theme="simple" onsubmit="return false;">
  	<div class="row">
		<input type="hidden" name="jsdm" value="${model.jsdm }" id="jsdm_modal"/>   
		<input type="hidden" name="jsmc" value="${model.jsmc}" id="jsmc_modal"/>  
	    <input type="hidden" name="jsyhStr" id="jsyhStr" value="${jsyhStr }" id="jsyhStr_modal"/>
		<div class="col-md-3 col-sm-3">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">所属部门</label>
		      <div class="col-sm-8">
		   		  <s:select id="jg_id_fp" name="jg_id" list="xyList" listKey="jg_id" listValue="jgmc"  headerKey="" headerValue="全部" 
		   		  cssClass="form-control chosen-select"/>
		      	  <SCRIPT type="text/javascript">
		    	  	jQuery('#jg_id_fp').trigger("chosen");
		    	  </SCRIPT>	
		      </div>
		    </div>
		  </div>
		  <div class="col-md-3 col-sm-3">
		    <div class="form-group">
		      <label for="" class="col-sm-4 control-label">用户</label>
		      <div class="col-sm-8">
		   		  <input type="text" class="form-control" id="yhm_xm" value=""  placeholder="输入用户名或者姓名！">
		      </div>
		    </div>
		  </div>
		  <div class="col-md-4 col-sm-4">
		    <div class="form-group form-offset">
			 	<label class="radio-inline">
       				<input type="radio" value="0" name="cxlb"/>全部
       			</label>
       			<label class="radio-inline">
       				<input type="radio" value="1" name="cxlb" checked="checked"/>查询未分配列表
       			</label>
       			<label class="radio-inline">
       				<input type="radio" value="2" name="cxlb"/>查询已分配列表
       			</label>
		    </div>
		  </div>
		   <div class="col-md-2 col-sm-2">
		     <button type="button" class="btn btn-primary btn-sm pull-right"  style="margin-right: 15px;" onclick="searchResult();return false;">&nbsp;查&nbsp;&nbsp;询&nbsp;</button>
		   </div>
		</div>
	</s:form>
		<div class="row">
  		<div class="col-md-6 col-sm-12" >
  			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"  id="leftGrid" style="padding: 0px" >
	  			<table id="wfpTabGrid"></table>
				<div id="wfpPager"></div>
  			</div>
  		</div>
  		<div class="col-md-1 col-sm-12"  style="padding-top: 200px">
	  		<div class="btn-group-vertical  width-100">
				<button type="button" class="btn btn-default" id="checkedWfp" style="height: 60px;">&gt;&gt;</button>
				<button type="button" class="btn btn-default" id="clearYfp" style="height: 60px;">&lt;&lt;</button>
			</div>
  		</div>
  		<div class="col-md-5 col-sm-12" style="padding-top: 0px" >
  			<div class="alert alert-danger" role="alert"><strong>说明：</strong> 可双击"未分配用户列表"中的记录至"已分配用户列表",或按钮移动</div>
  			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"  id="rightGrid"  style="padding: 0px" >
	  			<table id="yfpTabGrid"></table>
				<div id="pagerYfp"></div>
  			</div>
  		</div>
	</div>
</body>
<script type="text/javascript" src="<%=systemPath %>/js/globalweb/comp/xtgl/jsgl/jsgl_fpyh.js?ver=<%=jsVersion%>"></script>
</html>