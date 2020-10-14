jQuery(function($) {
	
	var TempGrid = $.extend({}, BaseJqGrid, {
		postData : paramMap(),
		rownumbers : false,
		multiboxonly: true,
		pager : "pager", 
		resizeHandle : "#searchBox",
		url : _path + '/i18n/i18n_cxI18nResIndex.html?doType=query',
		colModel : [ 
		    {label : '资源文件id', name : 'res_oid', index : 'res_oid',	key : true,	hidden : true,}, 
		    {label : '文件代码', name: 'res_code', index: 'res_code',	width: '150px', align: 'left',},
		    {label : '文件名称', name: 'res_name', index: 'res_name', width: '150px', align: 'left', hidden: true},
		    {label : '文件类型', name: 'res_type', index: 'res_type', width: '150px', align: 'left'},
		    {label : '文件路径', name: 'res_path', index: 'res_path', width: '350px', align: 'left'},
		    {label : '文件备注', name: 'res_bz', index: 'res_bz', width: '350px', align: 'left'}
		]		
	});
	$("#tabGrid").loadJqGrid(TempGrid);

	$("#btn_xg").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return false;
		}
		$.showDialog(_path + '/i18n/i18n_xgI18nRes.html?res_oid=' + ids.join(","), '修改', $.extend({}, modifyConfig, {
			width : "800px"
		}));
		return false;
	});

	// 绑定增加事件
	$("#btn_zj").click(function() {
		$.showDialog(_path + '/i18n/i18n_zjI18nRes.html', '增加', $.extend({}, modifyConfig, {
			width : "800px"
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
					url: _path + '/i18n/i18n_scI18nRes.html',
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
	
	$("#btn_xz").click(function() {
		$.openWin(_path + "/i18n/i18n_xzI18nRes.html");
	});	
	
	$("#btn_fb").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length == 0) {
			$.alert('请选择您要发布的记录！');
			return false;
		}
		var publishResFunc = function() {
			$.ajax({
				type: "POST",
				async: false,					
				url: _path + "/i18n/i18n_fbI18nRes.html",
				data : {"res_oid":ids.join(",")},
				dataType: "json",
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
		};
	
		$.confirm('系统将生成并发布资源文件到服务器，确定继续操作吗？',function(result){
			if(result){
				$.closeModal("confirmModal");
				$.confirm('系统将覆盖服务器中的资源文件，再次确认要发布数据库中资源信息吗？' ,function(result){
					if (result) {
						publishResFunc();
					}					
				}, {modalName: "confirmModal2"});
			}
		}, {modalName: "confirmModal"});
	});	

	$("#search_go").click(function() {
		search('tabGrid', paramMap());
	});

	function paramMap() {
		return {
			"res_type" : $("#res_type_cx").val(),
			"res_code" : $("#res_code_cx").val(),
			"res_name" : $("#res_name_cx").val()
		};
	}
});