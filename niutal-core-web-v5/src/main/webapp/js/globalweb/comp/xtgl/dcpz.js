jQuery(function($){

	var tempGridOptions = {
		url:  'getDcpzList.zf',
		uniqueId: "id",
		columns: [
            {checkbox: true },
            {field: 'dcclbh', title: '配置编号',sortable:false,width:"80px"}, 
            {field: 'dcclmc', title: '配置名称',sortable:false,width:"100px"}, 
            {field: 'zd',title: '字段代码',sortable:false,width:"80px"},
            {field: 'zdmc',title: '字段名称',sortable:false,width:"100px"},
            {field: 'xssx',title: '显示顺序',sortable:false,width:"50px"},
            {field: 'zt',title: '启用状态',sortable:false,width:"50px",align:'center',formatter:function(value,row,index){
            	var ret;
            	if(value == '1'){
            		 ret = '<span class="text-success">启用</span>';	
            	}else {
            		 ret = '<span class="text-danger">停用</span>';
            	}
            	return ret;
            }},
            {field: 'sfmrzd',title: '是否默认字段',sortable:false,width:"50px",align:'center',formatter:function(value,row,index){
            	var ret;
            	if(value == '1'){
            		 ret = '<span class="text-success">是</span>';	
            	}else {
            		 ret = '<span class="text-danger">否</span>';
            	}
            	return ret;
            }},
            {field: 'zgh',title: '职工号',sortable:false,align:'center',width:"100px"},
            {field: 'sqzMcStrList',title: '分配角色',sortable:false,width:"300px"}
    	],
	 	toolbar:'#but_ancd',
	 	searchParams:function(){
	 		var map = $.search.getSearchMap();
			return map;
	 	}
	};
	
	$("#tabGrid").loadGrid(tempGridOptions);
	
	
	/*====================================================绑定按钮事件====================================================*/
	
	//绑定修改事件
	jQuery("#btn_xg").click(function () {
		var ids = $("#tabGrid").getKeys();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog("xgDcpz.zf?dcclbh="+row["dcclbh"]+"&zd="+row["zd"],'修改导出配置',$.extend({},modifyConfig,{"width":"700px"}));
	});
	
	$(":button[name=search_button]").bind("click",searchResult);
});


//查询
function searchResult(){
	$("#tabGrid").refreshGrid();
}
