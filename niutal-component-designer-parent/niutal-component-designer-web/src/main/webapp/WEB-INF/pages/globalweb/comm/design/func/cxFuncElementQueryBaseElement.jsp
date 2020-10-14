<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/WEB-INF/pages/globalweb/head/pagehead_v5.ini"%>
</head>
<body>
<div class="form-horizontal sl_all_form"  id="basefield_container">
	<div class="row">
		<div class="col-md-12 col-sm-12" >
  			<table id="tabGrid_basefield"></table>
  		</div>
	</div>
</div>
</body>
<script language="javascript">
//你可以在这里继续使用$作为别名...
jQuery(function($) {

	var fieldGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#basefield_container",
	    url: _path + "/design/designQuery_cxFuncElementQueryBaseElement.html?doType=query", //这是Action的请求地址  
	    multiselect : false, 
	    shrinkToFit	: true,
	    minHeight	: 200,
	    colModel:[
	         {label:'ID',name:'field_guid', index:'field_guid', key:true, hidden:true},
	         {label:'field_id',name:'field_id', index:'field_id', hidden:true},
		     {label:'字段名称',name:'field_name', index: 'field_name',align:'center',width:"80px"},
		     {label:'字段标题',name:'field_text', index: 'field_text',align:'center',width:"100px"},
		     {label:'字段描述',name:'field_desc', index: 'field_desc',align:'left',width:"100px"}
		],
		/*双击行时触发。rowid：当前行id；iRow：当前行索引位置；iCol：当前单元格位置索引；e:event对象*/
		ondblClickRow	: function(rowid,iRow,iCol,e){
			var table_guid = '${queryFieldModel.table_guid}';
			if($.founded(table_guid)){
				var rowData = $("#tabGrid_query").jqGrid("getRowData",table_guid);
				var baseRow = $("#tabGrid_basefield").jqGrid("getRowData",rowid);
				var updateRow = $.extend(true,{},rowData,{
					"field_guid"			:  rowid,
					"field_filtertype"		:  "1",
					"field_text"			:  $.founded(rowData["field_text"]) ? rowData["field_text"] : baseRow["field_text"],
					"field_id"				:  $.founded(rowData["field_id"]) ? rowData["field_id"] : baseRow["field_id"],
					"field_name"			:  $.founded(rowData["field_name"]) ? rowData["field_name"] : baseRow["field_name"]
				});
				$("#tabGrid_query").jqGrid().setRowData(table_guid,updateRow);
				clearTitle();
				resetIndex();
				$.closeModal("bindSetting");
			}
		},
		/*当从服务器返回响应时执行，xhr：XMLHttpRequest 对象*/
		loadComplete 	: function(xhr){
			var field_guid = '${queryFieldModel.field_guid}';
			if($.founded(field_guid)){
				$("#tabGrid_basefield").jqGrid("setSelection",field_guid,false);
			}
		}
	});
	
	$("#tabGrid_basefield").loadJqGrid(fieldGrid);
	
	 
});
</script>
</html>
