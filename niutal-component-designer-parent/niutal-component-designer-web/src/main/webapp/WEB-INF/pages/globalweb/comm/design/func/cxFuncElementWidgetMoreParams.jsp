<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
	<style type="text/css">
	#moreSettingForm .tooltip{
		z-index: 888888;
	}
	</style>
</head>
<body>
	<s:form cssClass="form-horizontal sl_all_form" role="form" id="moreSettingForm" theme="simple">
		<div class="row">
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-md-4 col-sm-4 control-label">
						对齐方式
					</label>
					<div class="col-md-8 col-sm-8">
						<s:select list="#{'left':'居左','center':'居中','right':'居右'}"
							validate="{required:true}" cssClass="form-control input-sm"
							listKey="key" listValue="value" name="jqgridModel.field_align"
							id="field_align">
						</s:select>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-md-4 col-sm-4 control-label">
						是否可排序
					</label>
					<div class="col-md-7 col-sm-7" >
						<select data-toggle="tooltip" data-toggle="tooltip" data-placement="top" title="设置列是否可以进行数据排序;默认：是" 
						class="form-control input-sm"  name="field_sortable" id="field_sortable" validate="{required:true}" >
						<s:iterator value="#{1:'是',0:'否'}">
							<option <s:if test="key == jqgridModel.field_sortable"> selected="selected" </s:if> value="<s:property value="key"/>" ><s:property value="value"/></option>
						</s:iterator>
						</select>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-md-4 col-sm-4 control-label">
						宽度可调整
					</label>
					<div class="col-md-8 col-sm-8">
						<select data-toggle="tooltip" data-placement="top" title="设置列是否可以变更尺寸;默认：可调整" 
						class="form-control input-sm"  name="field_resizable" id="field_resizable" validate="{required:true}" >
						<s:iterator value="#{1:'可调整',0:'不可调整'}">
							<option <s:if test="key == jqgridModel.field_resizable"> selected="selected" </s:if> value="<s:property value="key"/>" ><s:property value="value"/></option>
						</s:iterator>
						</select>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-md-4 col-sm-4 control-label">
						宽度是否固定
					</label>
					<div class="col-md-7 col-sm-7">
						<select data-toggle="tooltip" data-placement="top" title="列宽度是否要固定不可变;默认：否"
						class="form-control input-sm"  name="field_fixed" id="field_fixed" validate="{required:true}" >
						<s:iterator value="#{0:'否',1:'是'}">
							<option <s:if test="key == jqgridModel.field_fixed"> selected="selected" </s:if> value="<s:property value="key"/>" ><s:property value="value"/></option>
						</s:iterator>
						</select>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-md-4 col-sm-4 control-label">
						编辑状态
					</label>
					<div class="col-md-8 col-sm-8">
						<select data-toggle="tooltip" data-toggle="tooltip" data-placement="top" title="单元格是否可编辑;默认：可编辑"
						class="form-control input-sm"  name="field_editable" id="field_editable" validate="{required:true}" >
						<s:iterator value="#{1:'可编辑',0:'不可编辑'}">
							<option <s:if test="key == jqgridModel.field_editable"> selected="selected" </s:if> value="<s:property value="key"/>" ><s:property value="value"/></option>
						</s:iterator>
						</select>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-md-4 col-sm-4 control-label">
						编辑类型
					</label>
					<div class="col-md-7 col-sm-7">
						<s:select data-toggle="tooltip" data-placement="top" title="可以编辑的类型。可选值：text, textarea, select, checkbox, password, button, image, file"
							list="#{'text':'text','textarea':'textarea','select':'select','checkbox':'checkbox','password':'password','button':'button','image':'image','file':'file'}"
							cssClass="form-control input-sm" listKey="key" listValue="value"
							name="jqgridModel.field_edittype" id="field_edittype"
							validate="{required:true}">
						</s:select>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-2 col-sm-2 control-label">
						编辑参数
					</label>
					<div class="col-md-8 col-sm-8">
						<s:textfield cssClass="form-control input-sm"
							data-toggle="tooltip" data-placement="top"
							title="编辑状态下参数选项。如：   1、动态从服务器端获取数据: {dataUrl:'/admin/deplistforstu.action'}  2、静态数据： {value:'zy:专业;dl:大类'} 或 {value:{'zy':'专业','dl':'大类'}}"
							id="field_editoptions" name="jqgridModel.field_editoptions"
							validate="{stringMaxLength:500}"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-2 col-sm-2 control-label">
						编辑规则
					</label>
					<div class="col-md-8 col-sm-8">
						<s:textfield cssClass="form-control input-sm"
							data-toggle="tooltip" data-placement="top"
							title="编辑状态下规则选项。如： {edithidden:true,required:true,number:true,minValue:10,maxValue:100}}表示设定年龄的最大值为100，最小值为10，而且为数字类型，并且为必输字段 "
							id="field_editrules" name="jqgridModel.field_editrules"
							validate="{stringMaxLength:500}"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-2 col-sm-2 control-label">
						扩展参数
					</label>
					<div class="col-md-8 col-sm-8">
						<s:textfield cssClass="form-control input-sm" id="field_cellattr"
							name="jqgridModel.field_cellattr"
							validate="{stringMaxLength:500}"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-12 col-sm-12">
				<div class="form-group">
					<label for="" class="col-md-2 col-sm-2 control-label">
						格式化
					</label>
					<div class="col-md-8 col-sm-8">
						<s:textfield cssClass="form-control input-sm"
							data-toggle="tooltip" data-placement="top"
							title="预设类型或用来格式化该列的自定义函数名。常用预设格式有：integer、date、currency、number等 ,select,自定义字符串（如果字符串表示函数名称则以函数调用）; "
							id="field_formatter" name="jqgridModel.field_formatter"
							validate="{stringMaxLength:500}"></s:textfield>
					</div>
				</div>
			</div>

		</div>
	</s:form>
</body>
<script language="javascript">
//你可以在这里继续使用$作为别名...
jQuery(function($) {

	$('#moreSettingForm').validateForm( {
	//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			return false;
		}
	});

	//触发tooltip组件
	$('#moreSettingForm').find('[data-toggle="tooltip"]').tooltip('destroy').tooltip();
});
</script>
</html>
