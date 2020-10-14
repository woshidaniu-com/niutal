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
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/designFunc_xgzdycdMenuData.html" theme="simple">
	<s:hidden id="func_guid" name="func_guid"></s:hidden>
	<s:hidden id="func_code" name="func_code"></s:hidden>
	<div class="row">
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>父级菜单名称
				</label>
				<div class="col-md-7 col-sm-7">
					<p class="form-control-static"><s:property value="parent_func_name"/></p>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					功能模块代码
				</label>
				<div class="col-md-7 col-sm-7">
					<p class="form-control-static"><s:property value="func_code"/></p>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>功能模块名称
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield  cssClass="form-control" id="func_name" name="func_name" validate="{required:true,stringMaxLength:50}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>功能所属对象
				</label>
				<div class="col-md-7 col-sm-7">
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
					<span class="red">*</span>是否显示
				</label>
				<div class="col-md-7 col-sm-7">
					<s:iterator value="isSfxs" status="stat">
						<label class="radio-inline">
						    <input validate="{required:true}" type="radio" id="func_displayed_<s:property value="#stat.index"/>" name="func_displayed" <s:if test="key == func_displayed "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
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
				<div class="col-md-7 col-sm-7">
					<input type="text" class="form-control" id="func_ordinal" value="<s:property value="func_ordinal"/>"  name="func_ordinal" placeholder="菜单显示顺序；默认值已经是字段计算好的值" validate="{required:true,PositiveInteger:true,stringMaxLength:2}"/>
				</div>
			</div>
		</div>
	</div>
</s:form>
</body>
<script language="javascript">
//你可以在这里继续使用$作为别名...
jQuery(function($) {
	
	$('#ajaxForm').validateForm( {
		//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			return true;
		}
	});
	
});
</script>
</html>
