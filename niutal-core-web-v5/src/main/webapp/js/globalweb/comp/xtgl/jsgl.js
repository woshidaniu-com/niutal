jQuery(function($){
	var options = {
		 url: _path + '/xtgl/jsgl/getJsxxList.zf',
		 uniqueId: "jsdm",
		 toolbar:  '#but_ancd',
		 columns: [
            {checkbox: true }, 
            {field: 'jsmc', title: '角色名称',sortable:true}, 
            {field: 'yhs',title: '用户数',sortable:true}, 
            {field: 'jsdj',title: '角色等级',sortable:true,visible:false}, 
            {field: 'qyzt',title: '启用状态',align:'center',sortable:true,formatter:function(value,row,index){
            	var ret;
            	if(value == '1'){
            		 ret = '<span class="text-success">启用</span>';	
            	}else {
            		 ret = '<span class="text-danger">停用</span>';
            	}
            	return ret;
            }}, 
            {field: 'jssm',title: '角色描述',sortable:true}
        ],
        searchParams:function(){
        	var map = $.search.getSearchMap();
        	return map;
        }
	};
	
	$('#tabGrid').loadGrid(options);
	
	
	// 绑定增加点击事件
	$("#btn_zj").click(function() {
		$.showDialog('zjJsxx.zf','增加角色',$.extend({},addConfig,{
			"width":"700px",
			buttons : {
				success:{
					label:"确定",
					className : "btn-primary",
					callback:function(){
						var $this = this;
						var opts = $this["options"]||{};
						var jsdm = $("#ajaxForm :input[name=jsdm]").val();
						var jsmc = $("#ajaxForm :input[name=jsmc]").val();
						
						submitForm("ajaxForm",function(responseData,statusText){
							if($.isPlainObject(responseData)){
							   if(responseData["status"] == "success"){
								   $.closeModal("addModal");
								   $.showDialog('gnsq.zf?jsdm='+jsdm,'角色【'+jsmc+'】功能授权',{
										modalName:"modifyModal",
										width: "1200px",
										buttons : {
											success:{
												label:"确定",
												className : "btn-primary",
												callback:function(){
													var $this = this;
													var opts = $this["options"]||{};
													var jsdm = $("#ajaxForm :input[name=jsdm]").val();
													submitForm("ajaxForm",function(responseData,statusText){
														if($.isPlainObject(responseData)){
														   if(responseData["status"] == "success"){
																$.success(responseData["message"]);
															}else if(responseData["status"] == "error"){
																$.error(responseData["message"]);
															}else{
																$.alert(responseData["message"]);
															}
														}
													});
													return false;
												}
											},
											cancel:{
												label : "取 消",
												className : "btn-default"
											}
										}
									});
									$("#tabGrid").reloadGrid();
								}else if(responseData["status"] == "error"){
									$.error(responseData["message"]);
								}else{
									$.alert(responseData["message"]);
								}
							}
						});
						return false;
					}
				},
				cancel:{
					label : "取 消",
					className : "btn-default",
					callback:function(){
						
					}
				}
			}
		}));
        return false;
	});
	
	// 绑定删除事件
	$("#btn_sc").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return false;
		}
		
		var row = jQuery("#tabGrid").getRow(ids[0]);
		if (row["jsdj"] == "0"){
			$.alert('系统内置角色不能删除！');
			return false;
		}
		
		var msg = row["yhs"] > 0 ? "该角色已分配用户，若删除将影响下属用户权限。您确定要继续吗？" : "您确定要删除选择的记录吗？" ;
		
		$.confirm(msg ,function(result) {
			if(result){
				$.post('scJsxx.zf', {"id" : ids[0]}, function(data) {
					if(data["status"] == "success"){
						$.success(data["message"],function() {
							$('#tabGrid').refreshGrid();
						});
					}else if(data["status"] == "fail"){
						$.error(data["message"]);
					} else{
						$.alert(data["message"]);
					}
				}, 'json');
			}
		});
	});
	
	// 绑定修改事件
	$("#btn_xg").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog('xgJsxx.zf?jsdm=' + ids[0],'修改角色',$.mergeObj(modifyConfig,{
			"width":"700px"
		}));
		return false;
	});
	
	
	//查看
	jQuery("#btn_ck").click(function () {
		var ids = $("#tabGrid").getKeys();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog("ckJsxx.zf?jsdm="+row.jsdm,'查看角色',$.extend({},viewConfig,{"width":"700px"}));
	});
	
	// 功能授权
	$("#btn_gnsq").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog('gnsq.zf?jsdm='+ids[0],'角色【'+row["jsmc"]+'】功能授权',{
				modalName:"modifyModal",
				width: "1200px",
				buttons : {
					success:{
						label:"确定",
						className : "btn-primary",
						callback:function(){
							var $this = this;
							var opts = $this["options"]||{};
							var jsdm = $("#ajaxForm :input[name=jsdm]").val();
							submitForm("ajaxForm",function(responseData,statusText){
								if($.isPlainObject(responseData)){
								   if(responseData["status"] == "success"){
										$.success(responseData["message"]);
									}else if(responseData["status"] == "error"){
										$.error(responseData["message"]);
									}else{
										$.alert(responseData["message"]);
									}
								}
							});
							return false;
						}
					},
					cancel:{
						label : "取 消",
						className : "btn-default"
					}
				}
			}
		);
		return false;
	});
	
	// 数据授权
	$("#btn_sjsq").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog('sjsq.zf?jsdm='+ids[0],'角色【'+row["jsmc"]+'】数据授权',{
			modalName:"modifyModal",
			width: "1200px",
			fullScreen:false,
			buttons : {
				success:{
					label:"确定",
					className : "btn-primary",
					callback:function(){
						var $this = this;
						var opts = $this["options"]||{};
						var jsdm = $("#ajaxForm :input[name=jsdm]").val();
						submitForm("ajaxForm",function(responseData,statusText){
							if($.isPlainObject(responseData)){
							   if(responseData["status"] == "success"){
									$.success(responseData["message"]);
								}else if(responseData["status"] == "error"){
									$.error(responseData["message"]);
								}else{
									$.alert(responseData["message"]);
								}
							}
						});
						return false;
					}
				},
				cancel:{
					label : "取 消",
					className : "btn-default"
				}
			}
		});
		return false;
	});
	
	// 角色分配用户
	$("#btn_fpyh").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog('fpyh.zf?jsdm='+ids[0],'角色【'+row["jsmc"]+'】分配用户',$.extend({},modifyConfig,{
			modalName:"modifyModal",
			width: "1200px",
			fullScreen:false,
			onHidden:function(){
				searchResult();
			}
		}));
		return false;
	});
	$(":button[name=search_button]").bind("click",searchResult);
});

// 查询
function searchResult() {
	$('#tabGrid').refreshGrid();
}

