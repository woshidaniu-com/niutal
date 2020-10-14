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
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/designWidget_xgFuncElementWidgetData.html" theme="simple">
	<s:hidden id="element_guid" name="element_guid" />
	<s:hidden id="func_widget_guid" name="func_widget_guid" />
	<div class="row">
		<div class="col-md-12 col-sm-12" style="padding:5px 25px;">
			<ul class="nav nav-tabs" role="tablist" id="widget_tab">
				<s:if test="widget_name == 'jqgrid' ">
			  	<li class="active"><a href="#jqgrid_pane" role="tab"  data-toggle="tab" style="line-height: 1;"> JQGrid组件列信息 </a></li>
			  	</s:if>
			  	<li><a href="#base_pane" role="tab" data-toggle="tab" style="line-height: 1;"> 组件基本参数 </a></li>
			  	<s:if test="parameterList!=null && parameterList.size() > 0">
		  		<li><a href="#parameter_pane" role="tab" data-toggle="tab" style="line-height: 1;"> 组件扩展参数 </a></li>
			  	</s:if>
			</ul>
			<div class="tab-content" id="widget_tab_content">
				<s:if test="widget_name == 'jqgrid' ">
			  	<div class="tab-pane  active " id="jqgrid_pane" style="padding:5px 0px;min-height: 400px;">
			  		<div style="padding: 4px;text-align: left; " class="clearfix ">
		  				<div class="blue" style="float:left;margin: 0px !important;padding: 5px !important;margin-left: 15px !important" role="alert" class="alert ">
		  					<span id="removed_tip" style="color:red;display:none;"><strong>注意：</strong>列表中被红色标注行表示查询返回数据列中已无此列；查询将产生错误请移除此列!</span>
		  				</div>
			  			<div style="float:right;" role="toolbar" class="btn-toolbar">
							<div class="btn-group">
								<s:if test="widget_sql!=null and widget_sql.length() > 0">
								<button id="btn_copy_row" href="javascript:void(0);" class="btn btn-default" type="button">
								<i class="bigger-100 glyphicon glyphicon-random"></i>&nbsp;移到右侧</button>
								</s:if>
								<button id="btn_add_row" href="javascript:void(0);" class="btn btn-default" type="button">
								<i class="bigger-100 glyphicon glyphicon-plus"></i>&nbsp;增加列信息</button>
							</div> 
						</div>
					</div>
				  	<s:if test="widget_sql!=null and widget_sql.length() > 0">
				  		<div class="col-md-3 col-sm-3"  style="padding-left: 0px;">
				  			<div class="col-md-12 col-sm-12"  id="source_container"   style="padding:0px;">
					  			<table id="tabGrid_source"></table>
					  		</div>
				  		</div>
				  		<div class="col-md-9 col-sm-9" style="padding: 0px;" id="jqgird_container">
				  			<table id="tabGrid_widget"></table>
				  		</div>
				  	</s:if>
				  	<s:else>
						<div class="clearfix " id="jqgird_container"   style="padding:0px;">
				  			<table id="tabGrid_widget"></table>
							<div id="pager_widget"></div>
						</div>
				  	</s:else>
			  	</div>
			  	</s:if>
			  	<div class="tab-pane clearfix" id="base_pane" style="padding:5px 0px;min-height: 400px;">
		  			<div class="col-md-6 col-sm-6">
						<div class="form-group">
							<label for="" class="col-md-4 col-sm-4 control-label">
								<span class="red">*</span>功能组件名称
							</label>
							<div class="col-md-7 col-sm-7">
								<s:textfield cssClass="form-control" id="widget_title"  name="widget_title" placeholder="功能组件名称（如：jqgrid列表）" validate="{required:true,stringMaxLength:50}"></s:textfield>
							</div>
						</div>
					</div>
					<div class="col-md-6 col-sm-6">
						<div class="form-group">
							<label for="" class="col-md-4 col-sm-4 control-label">
								<span class="red">*</span>数据是否分页
							</label>
							<div class="col-md-7 col-sm-7" >
								<s:iterator value="#{0:'不分页',1:'分页'}" var="stat">
									<label class="radio-inline">
									    <input type="radio" id="widget_pageable_<s:property value="#stat.index"/>" name="widget_pageable" <s:if test="key == widget_pageable"> checked="checked" </s:if>  value="<s:property value="key"/>" /><s:property value="value"/>
								  	</label>
								</s:iterator>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label for="" class="col-md-2 col-sm-2 control-label">
								<span class="red">*</span>功能组件描述
							</label>
							<div class="col-md-8 col-sm-8" >
								<s:textfield cssClass="form-control" id="widget_desc"  name="widget_desc" placeholder="功能组件描述" validate="{required:true,stringMaxLength:100}"></s:textfield>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label for="" class="col-md-2 col-sm-2 control-label">
								<span class="red">*</span>是否立即加载数据
							</label>
							<div class="col-md-8 col-sm-8" >
								<s:iterator value="#{0:'否',1:'是'}"  var="stat">
									<label class="radio-inline">
									    <input type="radio" id="widget_loadAtOnce_<s:property value="#stat.index"/>" name="widget_loadAtOnce" <s:if test="key == widget_loadAtOnce"> checked="checked" </s:if>  value="<s:property value="key"/>" /><s:property value="value"/>
								  	</label>
								</s:iterator>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label for="" class="col-md-2 col-sm-2 control-label">
								<span class="red">*</span>数据查询URL
							</label>
							<div class="col-md-8 col-sm-8" >
								<s:textfield cssClass="form-control" id="widget_data_url"  name="widget_data_url" placeholder="请录入数据查询URL" validate="{widgetRequired:true,stringMaxLength:200}"></s:textfield>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12" >
						<div class="form-group">
							<label for="" class="col-md-2 col-sm-2 control-label">
								<span class="red">*</span>数据查询SQL
							</label>
							<div class="col-md-8 col-sm-8" >
								<s:textarea cssClass="form-control" cssStyle="height: 350px;" id="widget_sql"  name="widget_sql" placeholder="请录入数据查询SQL" validate="{columnEffected:true,widgetRequired:true}"></s:textarea>
							</div>
						</div>
					</div>
				</div>
				<s:if test="parameterList!=null && parameterList.size() > 0">
				<div class="tab-pane  clearfix" id="parameter_pane" style="padding:5px 0px;min-height: 480px;">
					<s:iterator value="parameterList" status="stat">
						<div class="col-md-4 col-sm-4">
							<div class="form-group">
								<label for="" class="col-md-6 col-sm-6 control-label">
									<s:property value="param_name"/>
									<input type="hidden" id="<s:property value="param_name"/>" name="parameterList[<s:property value="#stat.index"/>].param_name" value="<s:property value="param_name"/>"/>
								</label>
								<div class="col-md-6 col-sm-6">
									<input type="text" data-toggle="tooltip" data-toggle="tooltip" data-placement="top" title="<s:property value="param_desc"/>"
									id="<s:property value="param_name"/>" name="parameterList[<s:property value="#stat.index"/>].param_value" value="<s:property value="param_default"/>"/>
								</div>
							</div>
						</div>
					</s:iterator>
				</div>
			  	</s:if>
			</div>
		</div>
	</div>
</s:form>
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/design/design-func-widget.js?ver=<%=jsVersion%>"></script>
</html>
