<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
<style type="text/css">
.btn-small {
    line-height: 1 !important;
    padding: 7px 10px !important;
}
</style>
</head>
<body>
<s:form cssClass="form-horizontal sl_all_form" role="form" id="ajaxForm" method="post" action="/xtgl/plxg_szZdplxg.html" theme="simple"  style="min-height: 200px;">
	<s:hidden name="gnmkdm"></s:hidden>
	<div class="row" >	
		<div class="col-md-12 col-sm-12">
			<table class="table table-bordered table-striped table-hover tab-bor-col-1 tab-td-padding-5">
				<thead>
					<tr>
						<th class="align-center ">&nbsp;</th>
						<th class="align-center ">操作代码</th>
						<th class="align-center ">操作名称</th>
						<th class="align-center ">启用/停用</th>
					</tr>
				</thead>
				<tbody id="sort_table_body">
					<s:iterator value="modelList" status="stat">
					<tr class="sort-item">
						<td class="sort-item <s:if test="#stat.index ==0">item-blue</s:if><s:elseif test="#stat.index%2==1">item-green</s:elseif><s:else>item-red</s:else> align-center"><s:property value="#stat.index + 1"/></td>
						<td class="sort-item align-center"><s:property value="zddm"/> <input type="hidden" name="list[<s:property value="#stat.index"/>].zddm"  value="<s:property value="zddm"/>"/>  </td>
						<td class="sort-item align-center"><s:property value="zdmc"/></td>
						<td class="align-center">
							<div data-toggle="buttons" class="btn-group btn-group-sm" >
								<s:if test=" sfqy == 1 ">
									<label class="btn btn-default btn-primary  btn-sm active  btn-small" >
								  		<i class="glyphicon glyphicon-ok smaller-90 show  pull-left"></i>
								    	<input type="radio" name="list[<s:property value="#stat.index"/>].sfqy"  value="1" checked="checked">&nbsp;启用
								  	</label>
								  	<label class="btn btn-default btn-sm btn-small">
								    	<input type="radio" name="list[<s:property value="#stat.index"/>].sfqy"  value="0" >&nbsp;停用
								  	</label>
								</s:if>
							  	<s:else>
							  		<label class="btn btn-default btn-sm btn-small">
								    	<input type="radio" name="list[<s:property value="#stat.index"/>].sfqy"  value="1" >&nbsp;启用
								  	</label>
								  	<label class="btn btn-default btn-primary btn-sm active btn-small">
								  		<i class="glyphicon glyphicon-ok smaller-90 show  pull-left "></i>
								    	<input type="radio" value="0" name="list[<s:property value="#stat.index"/>].sfqy"  checked="checked">&nbsp;停用
								  	</label>
							  	</s:else>
							</div>
						</td>
					</tr>
					</s:iterator>
				</tbody>
			</table>
		</div>
	</div>
</s:form>
</body>
<script type="text/javascript">
jQuery(function($){

	$('#ajaxForm').validateForm( {
		//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			return true;
		}
	});
	
	//排序方式切换事件
	$("tr.sort-item").each(function(i){

		$(this).find(":radio").unbind().change(function(){
			
			var actived = $(this).filter("input[value='"+$(this).val()+"']");
		 	var buttons = $(actived).closest('[data-toggle="buttons"]');

		 	$(buttons).find("label.btn").removeClass("btn-primary").find("i.glyphicon").remove();
		 	$(actived).closest("label.btn").addClass("btn-primary").prepend('<i class="glyphicon glyphicon-ok smaller-90 show  pull-left"></i>');
		
		 	
		});
	});
	
	$('label.btn').button();
	
});
</script>
</html>
