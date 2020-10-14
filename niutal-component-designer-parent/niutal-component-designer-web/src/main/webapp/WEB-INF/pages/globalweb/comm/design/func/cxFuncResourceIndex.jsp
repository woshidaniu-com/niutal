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
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/designFunc_bjfunczyResourceData.html" theme="simple">
	<s:hidden id="func_code" name="func_code" />
	<s:hidden id="opt_code" name="opt_code" />
	<s:hidden id="func_guid" name="func_guid" />
	<div class="row">
		<div class="col-md-12 col-sm-12" style="padding:5px 25px;">
			<ul class="nav nav-tabs" role="tablist" id="widget_tab">
			  	<li class="active"><a href="#new_pane" role="tab"  data-toggle="tab" style="line-height: 1;">新（js/css）资源 </a></li>
			  	<li><a href="#old_pane" role="tab" data-toggle="tab" style="line-height: 1;">已有（js/css）资源 </a></li>
			</ul>
			<div class="tab-content" id="widget_tab_content">
			  	<div class="tab-pane  active " id="new_pane" style="padding:25px 0px;min-height: 150px;">
			  		<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label for="" class="col-md-3 col-sm-3 control-label">
								引用组件
							</label>
							<div class="col-md-7 col-sm-7">
								<select class="form-control chosen-select"  id="widget_guid"  name="resourceModel.widget_guid"  >
									<option value="">使用自定义资源</option>
									<s:iterator value="widgetList">
										<option <s:if test="widgetModel.widget_guid == widget_guid">selected="selected"</s:if> value="<s:property value="widget_guid"/>"><s:property value="widget_name"/></option>
									</s:iterator>
								</select>
								<SCRIPT type="text/javascript">
						    		jQuery('#widget_guid').trigger("chosen");
						    	</SCRIPT>
							</div>
						</div>
					</div>
					<div class="col-md-12 col-sm-12">
						<div class="form-group">
							<label for="" class="col-md-3 col-sm-3 control-label">
								自定义脚本/样式
							</label>
							<div class="col-md-7 col-sm-7">
								<input type="file" name="resource" id="widget_resource" style="width: 330px;" />
							    <script type="text/javascript">
							    jQuery(function($) {
							    	$('#widget_resource').filestyle("destroy");
							    	$('#widget_resource').filestyle({
										buttonText : ' 选择文件',
										buttonName : 'btn-primary'
									});
							    });
							    </script>
							</div>
						</div>
					</div>
			  	</div>
			  	<div class="tab-pane clearfix" id="old_pane" style="padding:5px 0px;min-height: 150px;">
		  			<div class="clearfix " id="resource_container" style="padding:0px;">
			  			<table id="tabGrid_resource"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</s:form>
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/design/design-func-resource.js?ver=<%=jsVersion%>"></script>
</html>
