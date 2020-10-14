<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.woshidaniu.util.base.MessageUtil"%>
<%
	String stylePath = MessageUtil.getText("system.stylePath");;
	String systemPath = request.getContextPath();
	String jsPath = request.getContextPath();
%>	
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Pragma" http-equiv="no-cache" />
<meta http-equiv="Cache-Control" http-equiv="no-cache, must-revalidate" />
<meta http-equiv="Expires" http-equiv="0" />
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<style type="text/css">
.btn-small {
    line-height: 1 !important;
    padding: 7px 10px !important;
}
</style>
</head>
<body>
<form id="tempForm" role="form" class="form-horizontal sl_all_form"  style="min-height: 200px;">
	<div class="row" >	
		<div class="col-md-12 col-sm-12">
			<div class="alert alert-warning alert-dismissible red" role="alert">
		  		<strong>提示:</strong>&nbsp;使用鼠标拖拽实现优先级排序(针对显示记录进行排序)
			</div>
		</div>
		<div class="col-md-12 col-sm-12">
			<table class="table table-bordered table-striped table-hover tab-bor-col-1 tab-td-padding-5">
				<thead>
					<tr>
						<th class="align-center ">&nbsp;</th>
						<th class="align-center ">优先级</th>
						<th class="align-center ">排序方式</th>
					</tr>
				</thead>
				<tbody id="sort_table_body">
					<s:if test="ywsjPxxxList==null or ywsjPxxxList.size() == 0 ">
						<tr>
							<td colspan="3" class="align-center">排序数据未初始化!</td>
						</tr>
					</s:if>
					<s:else>
						<s:iterator value="ywsjPxxxList" status="stat">
						<tr class="sort-item" data-sort="<s:property value="zdmc"/>" data-order="<s:property value="pxfs"/>">
							<td class="sort-item <s:if test="#stat.index ==0">item-blue</s:if><s:elseif test="#stat.index%2==1">item-green</s:elseif><s:else>item-red</s:else> align-center"><s:property value="yxj"/></td>
							<td class="sort-item align-center"><s:property value="zdms"/></td>
							<td class="align-center">
								<div data-toggle="buttons" class="btn-group btn-group-sm" >
									<s:if test=" pxfs == 'asc' ">
										<label class="btn btn-default btn-primary  btn-sm active btn-small" data-order="asc">
									  		<i class="glyphicon glyphicon-ok smaller-90 show  pull-left"></i>
									    	<input type="radio" value="asc" checked="checked">&nbsp;正序
									  	</label>
									  	<label class="btn btn-default btn-sm btn-small" data-order="desc">
									    	<input type="radio" value="desc" >&nbsp;倒序
									  	</label>
									</s:if>
								  	<s:else>
								  		<label class="btn btn-default btn-sm btn-small" data-order="asc">
									    	<input type="radio" value="asc" >&nbsp;正序
									  	</label>
									  	<label class="btn btn-default btn-primary btn-sm active btn-small" data-order="desc">
									  		<i class="glyphicon glyphicon-ok smaller-90 show  pull-left"></i>
									    	<input type="radio" value="desc"  checked="checked">&nbsp;倒序
									  	</label>
								  	</s:else>
								</div>
							</td>
						</tr>
						</s:iterator>
					</s:else>
				</tbody>
			</table>
		</div>
	</div>
</form>
</body>
<script type="text/javascript">
jQuery(function($){
	//排序方式切换事件
	$("#sort_table_body").off("click","label']").on("click","label",function(){
		var radio = $(this).find(":radio");
		var actived = $(radio).filter("input[value='"+$(radio).val()+"']");
	 	var buttons = $(actived).closest('[data-toggle="buttons"]');
	 	$(buttons).find("label.btn").removeClass("btn-primary").find("i.glyphicon").remove();
	 	$(actived).closest("label.btn").addClass("btn-primary").prepend('<i class="glyphicon glyphicon-ok smaller-90 show  pull-left"></i>');
	 	//$(tr).data("order",$(this).val());
	 	//这里不能直接使用tr变量，因为拖拽后，对象不变化导致下面的取值问题
	 	$(radio).closest("tr.sort-item").data("order",$(actived).val());
	});
	
	$('label.btn').button();

	
	//上下拖动排序
	$("#sort_table_body").dragsort({
		itemSelector: "tr",
		dragSelector: "td.sort-item", 
		dragBetween: false, 
		dragEnd: function(){
			$("tr.sort-item").each(function(i){
				//重置序号
				$(this).find("td.sort-item:eq(0)").text(i+1);
				//解决拖拽后值发生变化
				$(this).find("label.btn").each(function(){
					$(this).find("radio:eq(0)").val($(this).data("order"));
				});
			});
		}, 
		placeHolderTemplate: '<tr></tr>',
		scrollSpeed: 5
	});

});
</script>
</html>
