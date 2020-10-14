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
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/designFunc_xgzdyanFuncOptData.html" theme="simple">
	<s:hidden id="func_code" name="func_code"></s:hidden>
	<s:hidden id="opt_code" name="opt_code"></s:hidden>
	<input type="hidden" name="old_opt_code" value="<s:property value="opt_code"/>"/>
	<div class="row">
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>所属功能菜单
				</label>
				<div class="col-md-8 col-sm-8">
					<s:hidden id="parent_func_code" name="parent_func_code" data-namemapper="gnmkdm" ></s:hidden>
					<p class="form-control-static"><s:property value="parent_func_name"/></p>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>操作按钮代码
				</label>
				<div class="col-md-8 col-sm-8">
					<div class="input-group">
						<input type="text" value="<s:property value="opt_code"/>" class="form-control btn_code" id="btn_code" name="btn_code" data-namemapper="czdm" validate="{required:true,multiUnique:['V0310',['#btn_code','#parent_func_code'],['<s:property value="opt_code"/>','<s:property value="parent_func_code"/>'],'已经存在；违反唯一约束!'],stringMaxLength:10}" />
						<div class="input-group-btn">
					        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">选择 <span class="caret"></span></button>
					        <ul class="dropdown-menu dropdown-menu-right dropdown-opt"  role="menu" style="max-height:200px;overflow-x:auto">
					          	<s:iterator value="optList" var="opt">
					        	<li><a class="opt-item" href="javascript:void(0);" data-icon="<s:property value="#opt.btn_icon"/>" data-code="<s:property value="#opt.btn_code"/>" data-text="<s:property value="#opt.btn_text"/>"><s:property value="#opt.btn_text"/></a></li>
					        	</s:iterator>
					        </ul>
					    </div>
     				</div>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>操作按钮名称
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control" id="btn_text" name="btn_text" validate="{required:true,stringMaxLength:50}" ></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					<span class="red">*</span>操作按钮图标
				</label>
				<div class="col-md-8 col-sm-8">
					<s:textfield cssClass="form-control btn_icon" id="btn_icon" name="btn_icon" validate="{required:true,stringMaxLength:50}" ></s:textfield>
				</div>
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<div class="form-group">
				<label for="" class="col-md-3 col-sm-3 control-label">
					按钮是否显示
				</label>
				<div class="col-md-8 col-sm-8">
			    	<s:iterator value="isNot" status="stat">
						<label class="radio-inline">
						    <input validate="{required:true}" type="radio" id="btn_displayed_<s:property value="#stat.index"/>" name="btn_displayed" <s:if test="key == btn_displayed "> checked="checked"</s:if> value="<s:property value="key"/>" /><s:property value="value"/>
					  	</label>
					</s:iterator>
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
	
	$(document).off("click",".dropdown-opt a.opt-item").on("click",".dropdown-opt a.opt-item",function(event){
		$("#btn_code").val($(this).data("code")).focus().blur();
		$("#btn_icon").val($(this).data("icon"));
		$("#btn_text").val($(this).data("text"));
	});
	
});
</script>
</html>
