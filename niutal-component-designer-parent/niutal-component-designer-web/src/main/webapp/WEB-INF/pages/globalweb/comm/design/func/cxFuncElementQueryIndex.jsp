<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<style type="text/css">
	.btn_design {
	    border: 2px dashed gray;
	    line-height: 1;
	    padding: 5px;
	}
	</style>
</head>
<body>
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/designQuery_xgFuncElementQueryData.html" theme="simple">
	<s:hidden id="func_code" name="func_code" />
	<s:hidden id="opt_code" name="opt_code" />
	<s:hidden id="element_guid" name="element_guid" />
	<s:hidden id="query_guid" name="query_guid" />
	<s:hidden id="report_guid" name="report_guid" />
	<s:hidden id="element_related_guid" name="element_related_guid" />
	
	<div class="row">
		<div class="col-md-12 col-sm-12" style="padding:5px 25px;">
			<ul class="nav nav-tabs" role="tablist" id="widget_tab">
			  	<li class="active"><a href="#detail_pane" role="tab" data-toggle="tab" style="line-height: 1;"> 查询条件明细信息 </a></li>
			  	<li><a href="#base_pane" role="tab" data-toggle="tab" style="line-height: 1;"> 基本查询条件信息 </a></li>
			</ul>
			<div class="tab-content" id="widget_tab_content">
			  	<div class="tab-pane active clearfix" id="detail_pane" style="padding:5px 0px;min-height: 400px;">
			  		<div style="padding: 4px;text-align: left; " class="clearfix ">
		  				<div class="blue" style="float:left;margin: 0px !important;padding: 5px !important;margin-left: 15px !important" role="alert" class="alert ">
		  					<span id="removed_tip" style="color:red;display:none;"><strong>注意：</strong>列表中被红色标注行表示查询返回数据列中已无此列；查询将产生错误请移除此列!</span>
		  				</div>
			  			<div style="float:right;" role="toolbar" class="btn-toolbar">
							<div class="btn-group">
								<button id="btn_copy_row" href="javascript:void(0);" class="btn btn-default" type="button">
								<i class="bigger-100 glyphicon glyphicon-random"></i>&nbsp;移到右侧</button>
								<button id="btn_add_row" href="javascript:void(0);" class="btn btn-default" type="button">
								<i class="bigger-100 glyphicon glyphicon-plus"></i>&nbsp;增加列信息</button>
							</div> 
						</div>
					</div>
					<div style="padding: 0px; " class="clearfix ">
				  		<div class="col-md-2 col-sm-2" style="padding-left: 0px;">
				  			<div class="col-md-12 col-sm-12"  id="source_container"   style="padding:0px;">
					  			<table id="tabGrid_source"></table>
					  		</div>
				  		</div>
				  		<div class="col-md-10 col-sm-10" style="padding: 0px;" id="jqgird_container">
				  			<table id="tabGrid_query"></table>
				  		</div>
			  		</div>
			  	</div>
			  	<div class="tab-pane  clearfix" id="base_pane" style="padding:5px 0px;min-height: 400px;">
		  			<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label for="" class="col-md-2 col-sm-2 control-label">
								<span class="red">*</span>查询功能名称
							</label>
							<div class="col-md-3 col-sm-3">
								<s:textfield cssClass="form-control" id="query_name"  name="query_name" placeholder="查询功能名称（如：个人信息校验）" validate="{required:true,stringMaxLength:50}"></s:textfield>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label for="" class="col-md-2 col-sm-2 control-label">
								<span class="red">*</span>每行条件数量
							</label>
							<div class="col-md-3 col-sm-3" >
								<s:textfield cssClass="form-control" id="query_column"  name="query_column" placeholder="即每行可容纳条件字段数量" validate="{required:true,PositiveInteger:true,stringMaxLength:2}"></s:textfield>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label for="" class="col-md-2 col-sm-2 control-label">
								<span class="red">*</span>查询功能描述
							</label>
							<div class="col-md-8 col-sm-8" >
								<s:textarea cssClass="form-control" cssStyle="height: 300px;"  id="query_desc"  name="query_desc" placeholder="查询功能描述" validate="{stringMaxLength:500}"></s:textarea>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</s:form>
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/design/design-func-query.js?ver=<%=jsVersion%>"></script>
</html>
