<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
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
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/design/designFunc_bjzdyanzFuncOptData.html" theme="simple">
	<div class="row">
		<div class="col-md-12 col-sm-12" style="padding:5px 25px;">
			<ul class="nav nav-tabs" role="tablist" id="widget_tab">
			  	<li class="active"><a href="#parameter_pane" role="tab" data-toggle="tab">组件参数</a></li>
			  	<li><a href="#resource_pane" role="tab" data-toggle="tab">组件引用js/css资源</a></li>
			</ul>
			<div class="tab-content" id="widget_tab_content">
			  	<div class="tab-pane fade in active" id="parameter_pane" style="padding:5px 0px;">
			  		<div class="clearfix table-responsive"  style="padding:0px;overflow-x: auto;max-height: 300px;">
			  			
					</div>
				</div>
			  	<div class="tab-pane fade" id="resource_pane" style="padding:5px 0px;">
			  		<div class="clearfix table-responsive"  style="padding:0px;overflow-x: auto;max-height: 300px;">
				  		
					</div>
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
