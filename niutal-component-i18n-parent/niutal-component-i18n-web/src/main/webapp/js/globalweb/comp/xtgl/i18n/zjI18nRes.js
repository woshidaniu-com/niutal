jQuery(function($) {
		
	var lastSelectId, $tabGrid = $("#tabGridI18n");
	var TempGrid = $.extend({},BaseJqGrid,{  
		pager: null,
		rowNum: 9999,
		height: 200,
		//multiselect: false,
		multiboxonly: true,
		resizeHandle:"#gridI18nHandle",	
		postData: {res_oid: $("#res_oid").val()},
	    url: _path + '/i18n/i18n_cxResourceI18n.html?doType=query',   
	    colModel:[
			{label : '资源内容id', name : 'res_oid', index : 'res_oid',	key : true,	hidden : true,}, 
		    {label : '资源主键', name: 'res_key', index: 'res_key', width: '150', align: 'left',},
		    {label : '中文描述', name: 'zh_cn',index: 'zh_cn', width: '300', align: 'left', editable: true},
		    {label : '英文描述', name: 'en_us', index: 'en_us',width: '300',  align: 'left', editable: true}
		],
		beforeSelectRow: function(id) {	
			if (lastSelectId !== id) {
				$tabGrid.jqGrid('saveRow', lastSelectId);
			}
			return true;
		},
		onSelectRow : function(id) {	
			$tabGrid.jqGrid('editRow', id, false, function(key) {			
				$("#" + key + "_zh_cn, #" + key + "_en_us").css({height: "auto"});
			});	
			lastSelectId = id;
		},
		loadonce: true
	});	
	
	$tabGrid.loadJqGrid(TempGrid);
	
	$tabGrid.off("click", "button.res_remove").on("click", "button.res_remove", function() {
		$tabGrid.jqGrid("delRowData", $(this).attr("res_oid"));
	});
	
	$("#addResI18n").click(function() {
		$.showDialog(_path + "/i18n/i18n_cxI18nSelected.html", 
			"选择资源内容信息", {	
			width: "750px",
			buttons: {
				success: {
					label : "确 定",
					className : "btn-primary",
					callback : function() {			
						var arrRow = this.content.getResultArr();
						if (arrRow && $.isArray(arrRow)) {		
							for (var i = 0, iLen = arrRow.length; i < iLen; i++) {
								var rowObj = arrRow[i];
								if (!$.founded($tabGrid.jqGrid("getRowData", rowObj.res_oid))) {
									$tabGrid.jqGrid("addRowData", rowObj.res_oid, rowObj);
								}
							}
							return true;
						}					
						return false;
					}						
				},
				cancel : {
					label : "关 闭",
					className : "btn-default"
				}
			}
		});
	});
	
	$("#delResI18n").click(function() {
		var ids = $tabGrid.getKeys();
		if (ids.length == 0) {
			$.alert('请选择您要删除的记录！');
			return false;
		}
		for (var i = ids.length; i > 0; i--) {
			$tabGrid.jqGrid("delRowData", ids[i-1]);
		}
	});
	
	//console.log("zji18nres");
	$('#ajaxForm').validateForm({
		beforeValidated: function() {
			if ($.founded(lastSelectId)) {
				$tabGrid.jqGrid('saveRow', lastSelectId);	
			}
			var inputHtmls = [],
				arrRowData = $tabGrid.jqGrid("getRowData");
			for (var i = 0, iLen = arrRowData.length; i < iLen; i++) {
				var rowData = arrRowData[i];
				if ($.founded(rowData["res_key"])) {
					inputHtmls.push($("<input type=\"hidden\" name=\"res_key\">").val(rowData["res_key"])[0].outerHTML);
					inputHtmls.push($("<input type=\"hidden\" name=\"zh_cn\">").val(rowData["zh_cn"])[0].outerHTML);
					inputHtmls.push($("<input type=\"hidden\" name=\"en_us\">").val(rowData["en_us"])[0].outerHTML);					
				}
			}
			$("#I18nDiv").html(inputHtmls.join(""));
			return true;
		}
	});

	
	$("#res_code").data("autocomplete-action",_path + '/i18n/i18n_cxResCodeList.html');
	$("#res_code").data("autocomplete-format", 'key(value)');
	$("#res_code").data("autocomplete-items", '20');
	$("#res_code").data("autocomplete-setvalue", "key");
	$("#res_code").data("autocomplete-realvalue", "value");
});