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
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/designFunc_zjysstFuncElementEntityData.html" theme="simple">
	<s:hidden  id="func_code" name="elementModel.func_code" ></s:hidden>
	<s:hidden  id="opt_code" name="elementModel.opt_code" ></s:hidden>
	<s:hidden  id="func_guid" name="elementModel.func_guid" ></s:hidden>
	<s:hidden  id="element_guid" name="elementModel.element_guid" ></s:hidden>
	<s:hidden  id="element_type" name="elementModel.element_type" ></s:hidden>
	<div class="row">
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>条件名称
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control" id="query_name" name="elementEueryModel.query_name"  placeholder="条件名称（如：个人信息校验）" validate="{required:true,stringMaxLength:50}"></s:textfield>
				</div>
			</div>
		</div> 
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>显示列数
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control" id="query_column" name="elementEueryModel.query_column"  placeholder="总数12，可选[1-12]的整数" validate="{required:true,PositiveInteger:true,range:[1,12],stringMaxLength:2}"></s:textfield>
				</div>
			</div>
		</div> 
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					条件描述信息
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textarea id="query_desc"  name="elementEueryModel.query_desc" validate="{stringMaxLength:500}" cssStyle="height: 100px;" cssClass="form-control">
					
					</s:textarea>
				</div>
			</div>
		</div>
	</div>
</s:form>
</body>
<script type="text/javascript">
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
