jQuery(function($){
	var options = {
			url: _path + '/xtgl/dymbgl/getDymbListAjax.zf',
			uniqueId: "id",
			toolbar:  '#but_ancd',
			columns: [
				{checkbox: true },
				{field: 'id', title: 'id', visible: false},
				{field: 'mc', title: '模板名称', sortable:true},
				{field: 'mblxdm', title: '模板类型代码', sortable:true, visible:false},
				{field: 'mblxmc', title: '模板类型', sortable:true},
				{field: 'qyzt', title: '启用状态',sortable:true, formatter:function(value,row,index){
					var ret;
					if(value == '1'){
						ret = '<span class="text-success">启用</span>';
					}else {
						ret = '<span class="text-danger">停用</span>';
					}
					return ret;
				}}
			],
			searchParams:function(){
				var map = $.search.getSearchMap();
				return map;
			}
		};
		$('#tabGrid').loadGrid(options);

	/*====================================================绑定按钮事件====================================================*/

	// 绑定增加点击事件
	jQuery("#btn_zj").click(function() {
		//$.showDialog('zjDymb.zf', '增加打印模板', $.mergeObj(addConfig, {"width":"700px"}));
		window.open("zjDymb.zf");
		return false;
	});

	// 绑定删除事件
	jQuery("#btn_sc").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length == 0) {
			$.alert('请选择您要删除的记录！');
			return false;
		}
		$.confirm('您确定要删除选择的记录吗？',function(result) {
			if(result){
				$.ajax({
					url: "scDymbgl.zf",
					type: "post",
					dataType: "json",
					async: false ,
					data: {"ids": ids.join(",")},
					success: function(data){
						if(data.status == "SUCCESS" || data.status == "success"){
							$.success(data.message,function() {
								$('#tabGrid').refreshGrid();
							});
						}else{
							$.alert(data.message);
							return false;
						}
					}
				});
			}
		});
	});

	// 绑定修改事件
	jQuery("#btn_xg").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		window.open("xgDymb.zf?id="+row['id']);
		return false;
	});

	// 绑定启用点击事件
	jQuery("#btn_qy").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选择一条您要启用的记录！');
			return false;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.ajax({
			url: "getAcceptEnableFlagDymbByDymblxAjax.zf",
			type: "post",
			dataType: "json",
			async: false ,
			data: {"id": row['id'], mblxdm: row['mblxdm']},
			success: function(data){
				if(data.data){
					$.ajax({
						url: "qyDymb.zf",
						type: "post",
						dataType: "json",
						async: false ,
						data: {"id": row['id']},
						success: function(data){
							if(data.status == "SUCCESS" || data.status == "success"){
								$.success(data.message,function() {
									$('#tabGrid').refreshGrid();
								});
							}else{
								$.alert(data.message);
								return false;
							}
						}
					});
				}else{
					$.alert(data.message, function(){
						this.close();
						$("#mblxdm").val("");
					});
					return false;
				}
			}
		});
	});

	// 绑定停用点击事件
	jQuery("#btn_ty").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选择一条您要停用的记录！');
			return false;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.ajax({
			url: "tyDymb.zf",
			type: "post",
			dataType: "json",
			async: false ,
			data: {"id": row['id']},
			success: function(data){
				if(data.status == "SUCCESS" || data.status == "success"){
					$.success(data.message,function() {
						$('#tabGrid').refreshGrid();
					});
				}else{
					$.alert(data.message);
					return false;
				}
			}
		});
	});

	$(":button[name=search_button]").bind("click", searchResult);
});

// 查询
function searchResult() {
	$('#tabGrid').refreshGrid();
}

//回车键查询
$('#searchBox input[name="jsmc"]').bind("keydown", "return", function (ev) {
	searchResult();
});
