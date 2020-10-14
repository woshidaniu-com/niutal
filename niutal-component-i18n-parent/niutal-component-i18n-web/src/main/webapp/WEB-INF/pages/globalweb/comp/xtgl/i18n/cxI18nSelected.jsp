<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
</head>
<body>
	<s:form cssClass="form-horizontal sl_all_form" role="form" name="xzform"
		method="post" action="" theme="simple" onsubmit="return false;">
		<div class="row" id="autogrid">
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">资源内容</label>
					<div class="col-sm-8">
						<s:textfield id="res_key_xz" name="res_key_xz" cssClass="form-control"
							placeholder="按内容中文或英文模糊查询"></s:textfield>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="" class="col-sm-4 control-label">&nbsp;</label>
					<div class="col-sm-8">
						<button type="button" class="btn btn-primary btn-sm" id="search_xz_go">查询</button>
					</div>
				</div>
			</div>
		</div>
		<div class="ui-grid-handle" id="xzHandle">&nbsp;</div>
		<table id="xztabGrid"></table>
		<div id="xzpager"></div>
	</s:form>
	
</body>
<script type="text/javascript">
	jQuery(function($) {	
		var TempGrid = $.extend({}, BaseJqGrid, {
			postData : paramDataMap(),
			rownumbers : false,
			//multiboxonly: true,
			pager : "xzpager", 
			autowidth: true,			
		    shrinkToFit: true,
			resizeHandle : "#xzHandle",
			url : _path + '/i18n/i18n_cxI18nSelected.html?doType=query',
			colModel : [ 
			    {label : '资源内容id', name : 'res_oid', index : 'res_oid',	key : true,	hidden : true,}, 
			    {label : '资源主键', name: 'res_key', index: 'res_key',width: '100', align: 'left',},
			    {label : '中文描述', name: 'zh_cn',index: 'zh_cn', align: 'left'},
			    {label : '英文描述', name: 'en_us', index: 'en_us', align: 'left'}
			]		
		});

		$("#xztabGrid").loadJqGrid(TempGrid);
		
		function paramDataMap() {
			return {
				res_key: $("#res_key_xz").val()
			};
		}

		console.log("search_xz_go");
		$("#search_xz_go").click(function() {
			//search('xztabGrid', paramDataMap());
			console.log("adsf...");
			$("#xztabGrid").refershGrid(paramDataMap());
		});
	});
	
	function getResultArr() {		
		var $tabGrid = $("#xztabGrid"),
			ids = $tabGrid.getKeys();
		if (ids.length == 0) {
			$.alert("请至少选定一条记录！")
			return false;	
		}		
		
		var arrRowData = [];
		$.each(ids, function(i, item) {			
			arrRowData.push($tabGrid.jqGrid('getRowData',item));			
		});		
		return arrRowData;
	}
</script>
</html>