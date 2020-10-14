jQuery(function($){
	
	var options = {
			 url: 'listData.zf',
			 uniqueId: "id",
			 toolbar:  '#but_ancd',
			 classes:'table table-condensed',
			 striped: false,
			 columns: [
	            {checkbox: true }, 
	            {field: 'id', title: 'ID',width:'80px', sortable:false,align:'center',visible:false, hidden:true},
	            {field: 'name',title: ' 表 单 名 称 ',sortable:false,width:'150px',align:'center'},
	            {field: 'key',sortable:false,width:'100px',visible:false, hidden:true},
	            {field: 'version', title: ' 版 本 ', sortable:false, width:'30px'}, 
	            {field: 'suspensionState',title: ' 状 态 ',align:'center',sortable:false,width:'50px',formatter:function(value,row,index){
	            	var ret;
	            	if(value == '1'){
	            		 ret = '<span class="text-success"> 启  用 </span>';	
	            	}else if(value == '2'){
	            		 ret = '<span class="text-danger"> 停  用 </span>';
	            	}
	            	return ret;
	            }},
	            {field: 'deploymentId',title: '表 单 部 署 ID',visible:false,hidden:true}
           ],
           searchParams:function(){
    	    var map = {};
    		map["name"] = jQuery('#name').val();
    		map["state"] = jQuery('#state').val();
           	return map;
           }
		};
		$('#tabGrid').loadGrid(options);
	
	/* ====================================================绑定按钮事件==================================================== */
	
	// 绑定查看事件
	jQuery("#btn_ck").click(function () {
		var ids = $('#tabGrid').getKeys();
		if (ids.length != 1 ) {
			$.alert('请选定一条记录!');
			return;
		}
		var rowData = $('#tabGrid').getRow(ids[0]);
		var url = _path + "/form-service/form-definition/preview.zf" ;
		$.showDialog(url,'表单预览  [版本:'+rowData['version']+']',$.extend({},viewConfig,{"width":($(window).width()*0.9),data:{'formDefinitionId':ids[0]}}));
	});

	
	jQuery("#btn_qy").click(function () {
		var ids = $('#tabGrid').getKeys();
		if (ids.length != 1 ) {
			$.alert('请选定一条记录!');
			return;
		}
		var url = "active.zf";
		$.confirm('您确定启用？',function(result) {
			if(result){
				jQuery.post(url, {'formDefinitionId':ids[0]}, function(responseText) {
					if(responseText["status"] == "success"){
						$.success(responseText["message"],function() {
							$('#tabGrid').refreshGrid();
						});
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					}else{
						$.alert(responseText["message"]);
					}
				}, 'json');
			}
		});
	});
	
	jQuery("#btn_ty").click(function () {
		var ids = $('#tabGrid').getKeys();
		if (ids.length != 1 ) {
			$.alert('请选定一条记录!');
			return;
		}
		var url = "suspend.zf";
		$.confirm('您确定停用？',function(result) {
			if(result){
				jQuery.post(url, {'formDefinitionId':ids[0]}, function(responseText) {
					if(responseText["status"] == "success"){
						$.success(responseText["message"],function() {
							$('#tabGrid').refreshGrid();
						});
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					}else{
						$.alert(responseText["message"]);
					}
				}, 'json');
			}
		});
	});
	
	jQuery("#btn_sc").click(function () {
		var ids = $('#tabGrid').getKeys();
		if (ids.length != 1 ) {
			$.alert('请选定一条记录!');
			return;
		}
		var url = "del.zf";
		$.confirm('您确定删除？',function(result) {
			if(result){
				jQuery.post(url, {'formDefinitionId':ids[0]}, function(responseText) {
					if(responseText["status"] == "success"){
						$.success(responseText["message"],function() {
							$('#tabGrid').refreshGrid();
						});
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					}else{
						$.alert(responseText["message"]);
					}
				}, 'json');
			}
		});
	});
	
});

//查询
function searchResult(){
	$('#tabGrid').refreshGrid();
}

//回车键查询
$('#name').bind("keydown", "return", function (ev) {
	searchResult()   
})
