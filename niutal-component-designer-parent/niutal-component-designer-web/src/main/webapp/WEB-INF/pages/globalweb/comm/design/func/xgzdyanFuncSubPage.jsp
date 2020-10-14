<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<style type="text/css">
	#input-group .chosen-single{
		border-radius: 4px 0 0 4px;
		border-right: 0px;
	}
	</style>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
</head>
<body>
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/designFunc_xgzdyanFuncSubPageData.html" theme="simple">
	<div class="row">
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>功能按钮名称
				</label>
				<div class="col-md-8 col-sm-8">
					<s:hidden id="func_guid" name="func_guid"></s:hidden>
					<s:hidden id="func_code" name="func_code"></s:hidden>
					<s:hidden id="opt_code" name="opt_code"></s:hidden>
					<p class="form-control-static"><s:property value="name_text"/></p>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					功能类型
				</label>
				<div class="col-md-8 col-sm-8">
					<!-- 系统自定义功能类型 数据展示; 可选值 ：1:''数据展示'',2:''数据维护'',3:''预览打印'',41:''快速打印(Applet模式)'',42:''快速打印(Flash模式)'',43:''快速打印(PDF模式)'',5:''数据导出'',6:''数据删除'-->
					<s:iterator value="funtTypeList" status="stat">
						<label class="radio-inline">
						    <input validate="{required:true}" type="radio" id="func_type_<s:property value="#stat.index"/>" name="func_type" <s:if test="key == func_type "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>前置条件检查
				</label>
				<div class="col-md-8 col-sm-8">
					<!-- 系统自定义功能:功能按钮点击前JQGrid列表选择数据行检查类型：默认：否；0：不检查，1：只能选一行，2：至少选择一行 -->
					<s:iterator value="preCheckList" status="stat">
						<label class="radio-inline">
						    <input validate="{required:true}"  type="radio" id="require_type_<s:property value="#stat.index"/>" name="require_type" <s:if test="key == require_type "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12" style="margin-top: 2px;">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>功能窗口宽度
				</label>
				<div class="col-md-8 col-sm-8">
					<div class="input-group input-group-sm">
						<input type="text" class="form-control" data-toggle="float" id="func_width" name="func_width" placeholder="作为按钮绑定的自定义功能时，弹窗宽度值；单位px" validate="{required:true,stringMaxLength:10,number:true,min:0}" value="<s:property value="func_width"/>"/>
						<span class="input-group-addon">像素(px)(值为0则取主窗口内容宽度值)</span>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red" id="report_related" style="display: none;">*</span>关联报表
				</label>
				<div class="col-md-8 col-sm-8">
					<s:select cssClass="form-control chosen-select disabled" list="reportList" listKey="report_guid" listValue="report_name"  
					id="report_guid"  name="report_guid" validate="{reportRequired:true}" headerKey="" headerValue="--请选择--"></s:select>
					<script type="text/javascript">
			    		jQuery('#report_guid').trigger("chosen");
			    	</script>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>自定义功能名称
				</label>
				<div class="col-md-8 col-sm-8">
					<input type="text" class="form-control" id="func_name" name="func_name" placeholder="自定义功能名称；将作为弹窗的标题" validate="{required:true,stringMaxLength:50}" value="<s:property value="func_name"/>"/>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					功能模块描述
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textarea cssStyle="height: 50px;" cssClass="form-control" id="func_desc" name="func_desc" validate="{stringMaxLength:200}" >
					
					</s:textarea>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>功能编辑状态
				</label>
				<div class="col-md-8 col-sm-8">
			    	<s:iterator value="editStatusList" status="stat">
						<label class="radio-inline">
						    <input validate="{required:true}" type="radio" id="func_editable_<s:property value="#stat.index"/>" name="func_editable" <s:if test="key == func_editable "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>功能是否发布
				</label>
				<div class="col-md-8 col-sm-8">
					<s:iterator value="isNot" status="stat">
						<label class="radio-inline">
						    <input validate="{required:true}"  type="radio" id="func_release_<s:property value="#stat.index"/>" name="func_release" <s:if test="key == func_release "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
		<!-- 系统自定义功能类型；默认：数据展示; 可选值 ：1:'数据展示',2:'数据维护',3:'预览打印',4:'快速打印',5:'数据导出',6:'数据删除' -->
		<s:if test=" func_type == 1 or func_type == 3 ">
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>筛选条件类型
				</label>
				<div class="col-md-8 col-sm-8">
					<s:iterator value="queryTypeList" status="stat">
						<label class="radio-inline">
						    <input type="radio" id="query_type_<s:property value="#stat.index"/>" name="query_type" <s:if test="key == query_type "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
		</s:if>
	</div>
</s:form>
</body>
<script language="javascript">
//你可以在这里继续使用$作为别名...
jQuery(function($) {

	$.validator.addMethod("reportRequired", function(value, element, param) {
		var val = $("input[name='func_type']:checked").val();
		return  ((val == '3' || val == '4')  ? $.founded($(element).val()) : true);
	}, "请选择与功能关联的报表!");
	
	$("input[name='func_type']").change(function(){
		var val = $("input[name='func_type']:checked").val();
		if('3' == val || '41' == val|| '42' == val|| '43' == val){
			$('#report_related,#query_type').show();
			$("#report_guid").focus().blur().removeClass("disabled").prop("disabled",false).trigger("chosen:updated");
			$("input[name='query_type']").removeClass("disabled").prop("disabled",false);
		}else{
			$('#report_related,#query_type').hide();
			$("#report_guid").focus().blur().addClass("disabled").prop("disabled",true).trigger("chosen:updated");
			$("input[name='query_type']").addClass("disabled").prop("disabled",true);
		}
	});
	
	$('#ajaxForm').validateForm( {
		//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			return true;
		}
	});
	
});
</script>
</html>
