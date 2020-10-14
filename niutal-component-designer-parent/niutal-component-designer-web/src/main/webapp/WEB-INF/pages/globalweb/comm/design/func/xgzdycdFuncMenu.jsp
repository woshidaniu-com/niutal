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
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/designFunc_xgzdycdFuncMenuData.html" theme="simple">
	<s:hidden id="func_guid" name="func_guid"></s:hidden>
	<s:hidden id="func_code" name="func_code"></s:hidden>
	<s:hidden id="opt_code" name="opt_code"></s:hidden>
	<div class="row">
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>父级菜单名称
				</label>
				<div class="col-md-8 col-sm-8">
					<p class="form-control-static"><s:property value="parent_func_name"/></p>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					功能模块代码
				</label>
				<div class="col-md-8 col-sm-8">
					<p class="form-control-static"><s:property value="func_code"/></p>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>功能模块名称
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield  cssClass="form-control" id="func_name" name="func_name"  placeholder="功能模块名称；将作为功能菜单名称" validate="{required:true,stringMaxLength:50}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>功能类型
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
						    <input validate="{required:true}" type="radio" id="func_release_<s:property value="#stat.index"/>" name="func_release" <s:if test="key == func_release "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>功能是否可见
				</label>
				<div class="col-md-8 col-sm-8">
					<s:iterator value="isNot" status="stat">
						<label class="radio-inline">
						    <input validate="{required:true}"  type="radio" id="func_displayed_<s:property value="#stat.index"/>" name="func_displayed" <s:if test="key == func_displayed "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>功能所属对象
				</label>
				<div class="col-md-8 col-sm-8">
					<s:iterator value="funcRoleList" status="stat">
						<label class="radio-inline">
						    <input validate="{required:true}" type="radio" id="func_target_<s:property value="#stat.index"/>" name="func_target" <s:if test="key == func_target "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red" id="query_type">*</span>筛选条件类型
				</label>
				<div class="col-md-8 col-sm-8">
					<s:iterator value="queryTypeList" status="stat">
						<label class="radio-inline">
						    <input type="radio" <s:if test="2 == func_type "> disabled="disabled" class="disabled" </s:if> id="query_type_<s:property value="#stat.index"/>" name="query_type" <s:if test="key == query_type "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>显示顺序
				</label>
				<div class="col-md-8 col-sm-8">
					<input type="text" class="form-control" id="func_ordinal" value="<s:property value="func_ordinal"/>"  name="func_ordinal" placeholder="菜单显示顺序；默认值已经是字段计算好的值" validate="{required:true,PositiveInteger:true,stringMaxLength:2}"/>
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
	</div>
</s:form>
</body>
<script language="javascript">
//你可以在这里继续使用$作为别名...
jQuery(function($) {

	$("input[name='func_type']").change(function(){
		var val = $("input[name='func_type']:checked").val();
		if('2' == val){
			$('#query_type').hide();
			$("input[name='query_type']").addClass("disabled").prop("disabled",true);
		}else{
			$('#query_type').show();
			$("input[name='query_type']").removeClass("disabled").prop("disabled",false);
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
