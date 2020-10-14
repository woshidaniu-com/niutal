$(function(){
	var grid = {
		uniqueId: "zgh",
	 	showToggle:false,
	 	showRefresh:false,
	 	showColumns:false,
	 	height:500,
	 	pageSize:10,
	 	searchParams:function(){
	 		var map = $("#fpyh").getSearchMap();
			map["jsdm"] = $("#jsdm").val();
			return map;
	 	}
	};
	var wfpGrid = {url:  'getWfpyhList.zf',
		columns: [
            {checkbox: true }, 
            {field: 'zgh', title: '用户名'}, 
            {field: 'xm', title: '姓 名'}, 
//            {field: 'lxdh',title: '联系电话'},
            {field: 'jgmc',title: '部门'},
            {field: 'sfqy',title: '启用状态',align:'center',formatter:function(value,row,index){
            	var ret;
            	if(value == '1'){
            		 ret = '<span class="text-success">启用</span>';	
            	}else {
            		 ret = '<span class="text-danger">停用</span>';
            	}
            	return ret;
            }},
            {field: 'zgh', title: '<button class="btn btn-success btn-xs" onclick="fpyh();">批量分配</button>',align:'center', formatter:function(value,row,index){
            	return "<button class='btn btn-warning btn-xs' onclick='fpyh(\""+value+"\");'> 分配 </span>";
            }}
	]};
	var yfpGrid =  {url:  'getYfpyhList.zf',
		columns: [
            {checkbox: true }, 
            {field: 'zgh', title: '用户名'}, 
            {field: 'xm', title: '姓 名'}, 
//            {field: 'lxdh',title: '联系电话'},
            {field: 'jgmc',title: '部门'},
            {field: 'sfqy',title: '启用状态',align:'center',formatter:function(value,row,index){
            	var ret;
            	if(value == '1'){
            		 ret = '<span class="text-success">启用</span>';	
            	}else {
            		 ret = '<span class="text-danger">停用</span>';
            	}
            	return ret;
            }},
            {field: 'zgh', title: '<button class="btn btn-success btn-xs" onclick="cxfp();">批量取消</button>',align:'center', formatter:function(value,row,index){
            	return "<button class='btn btn-warning btn-xs' onclick='cxfp(\""+value+"\");'> 取消 </span>";
            }}
	]};
	wfpGrid=$.extend(wfpGrid,{},grid);
	yfpGrid=$.extend(yfpGrid,{},grid);
	
	$("#wfpTabGrid").loadGrid(wfpGrid);
	$("#yfpTabGrid").loadGrid(yfpGrid);
	
	$(":button[name=search_button]").bind("click",function(){
		if($('#wfpCheck').is(':checked')){
			$('#wfpTabGrid').refreshGrid();
		}
		if($('#yfpCheck').is(':checked')){
			$('#yfpTabGrid').refreshGrid();
		}
	});
	$("#modifyModal #btn_success").hide();
});

function fpyh(zgh){
	var jsdm =  $("#jsdm").val();
	var ids = zgh == undefined ? $("#wfpTabGrid").getKeys().join(",") : zgh;
	if (ids.length == 0) {
		$.alert("请选择您要分配的用户");
		return false;
	}
	$.post("inertYhfp.zf",{jsdm:jsdm,ids:ids},function(data){
		$('#wfpTabGrid').refreshGrid();
		$('#yfpTabGrid').refreshGrid();
	});
}

function cxfp(zgh){
	var jsdm =  $("#jsdm").val();
	var ids = zgh == undefined ? $("#yfpTabGrid").getKeys().join(",") : zgh;
	if (ids.length == 0) {
		$.alert("请选择您要取消的用户");
		return false;
	}
	$.post("deleteYhfp.zf",{jsdm:jsdm,ids:ids},function(data){
		$('#wfpTabGrid').refreshGrid();
		$('#yfpTabGrid').refreshGrid();
	});
}