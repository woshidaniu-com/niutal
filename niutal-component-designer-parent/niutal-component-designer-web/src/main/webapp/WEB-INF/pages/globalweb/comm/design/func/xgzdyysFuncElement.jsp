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
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/designFunc_xgzdyysFuncElementData.html" theme="simple">
	<s:hidden  id="func_code" name="elementModel.func_code" ></s:hidden>
	<s:hidden  id="opt_code" name="elementModel.opt_code" ></s:hidden>
	<s:hidden  id="func_guid" name="elementModel.func_guid" ></s:hidden>
	<s:hidden  id="element_guid" name="elementModel.element_guid" ></s:hidden>
	<div class="row">
		<s:if test="elementModel.report_guid != null">
			 <input validate="{required:true}" type="hidden" id="element_type" name="elementModel.element_type" value="1" />
		</s:if>
		<s:else>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>元素类型
				</label>
				<div class="col-md-7 col-sm-7">
					<!-- 1:查询条件,2:脚本控件,3:普通字段 -->
					<s:iterator value="elementTypeList" status="stat">
						<label class="radio-inline">
						    <input  type="radio" id="element_type_<s:property value="#stat.index"/>" name="elementModel.element_type" <s:if test="key == elementModel.element_type "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
				</div>
			</div>
		</div>
		</s:else>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>栅格占比
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="element_width" name="elementModel.element_width"  placeholder="总数12，可选[1-12]的整数" validate="{required:true,PositiveInteger:true,range:[1,12],stringMaxLength:2}"></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>元素ID
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="element_id" name="elementModel.element_id"  validate="{required:true,stringMaxLength:50}"></s:textfield>
				</div>
			</div>
		</div>
		<s:if test="elementModel.report_guid == null">
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					联动元素
				</label>
				<div class="col-md-7 col-sm-7">
					<select class="form-control chosen-select"  id="element_related_guid"  name="elementModel.element_related_guid">
						<option value="">无联动元素</option>
						<s:iterator value="elementList" var="element">
							<option <s:if test="elementModel.element_related_guid == #element.element_guid">selected="selected" </s:if> value="<s:property value="element_guid"/>">
							<s:property value="element_desc"/></option>
						</s:iterator>
					</select>
					<script type="text/javascript">
			    		jQuery('#element_related_guid').trigger("chosen");
			    	</script>
				</div>
			</div>
		</div>
		</s:if>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>元素描述信息
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textarea id="element_desc"  name="elementModel.element_desc" validate="{required:true,stringMaxLength:500}"
					 cssStyle="height: 80px;" cssClass="form-control">
					
					</s:textarea>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>显示顺序
				</label>
				<div class="col-md-7 col-sm-7">
					<s:textfield cssClass="form-control" id="element_ordinal" name="elementModel.element_ordinal" placeholder="菜单显示顺序；默认值已经是字段计算好的值" validate="{required:true,UnNegativeInteger:true,stringMaxLength:2}"></s:textfield>
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
