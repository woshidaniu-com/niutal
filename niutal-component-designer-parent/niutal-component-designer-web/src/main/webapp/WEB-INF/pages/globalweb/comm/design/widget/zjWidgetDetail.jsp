<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/baseWidget_zjWidgetDetailData.html" theme="simple">
	<div class="row">
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					<span class="red">*</span>功能组件名称
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control" id="widget_name"  name="widget_name" placeholder="功能组件名称（如：jqgrid列表）" validate="{required:true,stringMaxLength:50}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12" >
			<div class="form-group">
				<label for="" class="col-md-2 col-sm-2 control-label">
					<span class="red">*</span>功能组件描述
				</label>
				<div class="col-md-8 col-sm-8" >
					<s:textarea cssClass="form-control" cssStyle="height: 80px;" id="widget_desc"  name="widget_desc" placeholder="功能组件详细描述" validate="{required:true,stringMaxLength:300}"></s:textarea>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12" style="padding:5px 25px;">
			<ul class="nav nav-tabs" role="tablist" id="widget_tab">
			  	<li class="active"><a href="#parameter_pane" role="tab" data-toggle="tab">组件参数</a></li>
			  	<li><a href="#resource_pane" role="tab" data-toggle="tab">组件引用js/css资源</a></li>
			</ul>
			<div class="tab-content" id="widget_tab_content">
			  	<div class="tab-pane fade in active" id="parameter_pane" style="padding:5px 0px;">
			  		<div class="clearfix table-responsive"  style="padding:0px;overflow-x: auto;max-height: 300px;">
			  			<table class="table table-bordered table-striped table-hover tab-bor-col-1 tab-td-padding-1" style="text-align:center">
							<thead>
								<tr>
									<td class="align-center " width="120px" style="padding: 4px;height: 18px;line-height: 18px;"><label style="margin-bottom: 0px;">参数名称</label></td>
									<td class="align-center " width="120px" style="padding: 4px;height: 18px;"><label style="margin-bottom: 0px;">参数默认值</label></td>
									<td class="align-center " style="padding: 4px;height: 18px;border-right: 0px;"><label style="margin-bottom: 0px;">参数描述</label></td>
									<td class="align-center " width="60px" style="padding: 4px;height: 18px;border-left: 0px;">&nbsp;</td>
								</tr>
							</thead>
							<tbody>
								<tr class="empty_row">
									<td class="align-center " valign="middle" style="padding: 4px;height: 18px;line-height: 30px;" colspan="4">
										 无记录!
									</td>
								</tr>
							</tbody>	
						</table>
					</div>
					<div id="parameter_btn"  style="padding: 4px;text-align: center; ">
						<button id="add_parameter" class="btn btn-default btn-sm btn_design width-30" type="button"  hidefocus="true">
							增加组件参数
						</button>
					</div>
				</div>
			  	<div class="tab-pane fade" id="resource_pane" style="padding:5px 0px;">
			  		<div class="clearfix table-responsive"  style="padding:0px;overflow-x: auto;max-height: 300px;">
				  		<table class="table table-bordered table-striped table-hover tab-bor-col-1 tab-td-padding-1" style="text-align:center">
							<thead>
								<tr>
									<td class="align-center " width="160px" style="padding: 4px;height: 18px;line-height: 18px;"><label style="margin-bottom: 0px;">【js/css】来源</label></td>
									<td class="align-center " width="150px" style="padding: 4px;height: 18px;"><label style="margin-bottom: 0px;">【js/css】描述</label></td>
									<td class="align-center " style="padding: 4px;height: 18px;border-right: 0px;"><label style="margin-bottom: 0px;">【js/css】请求路径</label></td>
									<td class="align-center " width="60px" style="padding: 4px;height: 18px;border-left: 0px;">&nbsp;</td>
								</tr>
							</thead>
							<tbody>
								<tr class="empty_row">
									<td class="align-center " valign="middle" style="padding: 4px;height: 18px;line-height: 30px;" colspan="4">
										 无记录!
									</td>
								</tr>
							</tbody>	
						</table>
					</div>
		  			<div id="resource_btn"  style="padding: 4px;text-align: center; ">
						<button id="add_resource" class="btn btn-default btn-sm btn_design width-30" type="button"  hidefocus="true">
							增加组件脚本/样式
						</button>
			  		</div>
			  	</div>
			</div>
		</div>
	</div>
</s:form>
</body>
<script type="text/javascript" src="<%=systemPath%>/js/globalweb/comm/design/base-widgets-sub.js?ver=<%=jsVersion%>"></script>
</html>
