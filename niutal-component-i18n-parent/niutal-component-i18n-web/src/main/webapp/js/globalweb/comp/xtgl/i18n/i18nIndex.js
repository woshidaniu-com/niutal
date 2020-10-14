jQuery(function($) {
	
	var TempGrid = $.extend({}, BaseJqGrid, {
		postData : paramMap(),
		rownumbers : false,
		multiboxonly: true,
		pager : "pager", 
		resizeHandle : "#searchBox",
		url : _path + '/i18n/i18n_cxI18nIndex.html?doType=query',
		colModel : [ 
		    {label : '资源内容id', name : 'res_oid', index : 'res_oid',	key : true,	hidden : true,}, 
		    {label : '资源主键', name: 'res_key', index: 'res_key',	width: '150px', align: 'left',},
		    {label : '中文描述', name: 'zh_cn',index: 'zh_cn', width: '350px', align: 'left'},
		    {label : '英文描述', name: 'en_us', index: 'en_us', width: '350px', align: 'left'}
		]		
	});
	$("#tabGrid").loadJqGrid(TempGrid);

	$("#btn_xg").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return false;
		}
		$.showDialog(_path + '/i18n/i18n_xgI18n.html?res_oid=' + ids.join(","), '修改', $.extend({}, modifyConfig, {
			width : "600px"
		}));
		return false;
	});

	// 绑定增加事件
	$("#btn_zj").click(function() {
		$.showDialog(_path + '/i18n/i18n_zjI18n.html', '增加', $.extend({}, addConfig, {
			width : "600px"
		}));
		return false;
	});

	// 绑定删除事件
	$("#btn_sc").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length == 0) {
			$.alert('请选择您要删除的记录！');
			return false;
		}
		$.confirm('您确定要删除选择的记录吗？', function(isBoolean) {
			if (isBoolean) {
				$.ajax({
					type: "POST",
					async: false,					
					url: _path + '/i18n/i18n_scI18n.html',
					dataType: "json",
					data : {"res_oid":ids.join(",")},
					success: function(data, statusText, xhr){
					   // data 可能是 xmlDoc, jsonObj, html, text, 等等;本函数中data是JSON对象
					   if(data["status"] == "success"){
							$.success(data["message"],function() {
								if ($("#tabGrid").size() > 0) {
									$("#tabGrid").refershGrid();
								}
							});
						}else  if(data["status"] == "error"){
							$.error(data["message"]);
						}else{
							$.alert(data["message"]);
						}
					},
					error:function(jqXHR, textStatus, errorThrown){
						   
					}
				});
			}
		});

	});

	$("#search_go").click(function() {
		search('tabGrid', paramMap());
	});

	function paramMap() {
		return {
			"res_key" : $("#res_key_cx").val()
		};
	}
});