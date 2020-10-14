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
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/baseAuto_zjAutoCompleteFieldData.html" theme="simple">
	<div class="row">
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>自动完成字段标题
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="field_text"  name="field_text" placeholder="页面元素前的文字标题" validate="{required:true,stringMaxLength:50}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>自动完成字段名称
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="field_name"  name="field_name" placeholder="页面元素name属性值" validate="{required:true,fieldType:true,stringMaxLength:50}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>触发事件需要文字数量
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="field_minLength"  name="field_minLength" placeholder="当前文本输入框中字符串达到该属性值时才进行匹配处理" validate="{required:true,PositiveInteger:true,stringMaxLength:10}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>自动完成查询记录数
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="field_items"  name="field_items" placeholder="自动完成提示的最大结果集数量" validate="{required:true,PositiveInteger:true,stringMaxLength:10}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>触发延时时间（毫秒）
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="field_delay"  name="field_delay" validate="{required:true,PositiveInteger:true,stringMaxLength:10}" placeholder="指定延时毫秒数后，才正真向后台请求数据，以防止输入过快导致频繁向后台请求"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>设置到文本框的值
				</label>
				<div class="col-md-7 col-sm-7">
					<s:select cssClass="form-control chosen-select"  id="field_setvalue"  name="field_setvalue" validate="{required:true}" list="#{'1':'key','2':'value'}" listKey="key" listValue="value" value="2" ></s:select>
					<script type="text/javascript">
			    		jQuery('#field_setvalue').trigger("chosen");
			    	</script>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>自动完成真实取值
				</label>
				<div class="col-md-7 col-sm-7">
					<s:select cssClass="form-control chosen-select"  id="field_realvalue"  name="field_realvalue" validate="{required:true}" list="#{'1':'key','2':'value'}" listKey="key" listValue="value" value="1" ></s:select>
					<script type="text/javascript">
			    		jQuery('#field_realvalue').trigger("chosen");
			    	</script>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>自动完成元素显示格式
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="field_format"  name="field_format" validate="{required:true,stringMaxLength:50}" value="value（key）" placeholder="此值在你需进行特殊的显示时，改变这个值可以改变组件每个元素显示的组合效果，默认是value（key）"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>自动完成数据查询路径
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="field_action"  name="field_action" validate="{required:true,stringMaxLength:200}" placeholder=" 自动完成数据查询路径"></s:textfield>
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
