jQuery(function($){
	
	var plxgGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#searchResult",
		pager: "pager", //分页工具栏  
	    url: _path+'/xtgl/plxg_cxPlxgIndex.html?doType=query', //这是Action的请求地址  
	    shrinkToFit: true,
	    minHeight:300,
	    colModel:[
		     {label:'功能模块代码',name:'gnmkdm', index:'gnmkdm', key:true,width:"100px"},
		     {label:'功能模块',name:'gnmkmc', index: 'gnmkmc',align:'center',width:"200px",resizeable:false,sortable : false}
		]
	});
	
	$("#tabGrid").loadJqGrid(plxgGrid);

	/*====================================================绑定按钮事件====================================================*/
	
	//设置 启用 停用状态
	jQuery("#btn_sz").click(function () {
		var keys = $("#tabGrid").getKeys();
		if(keys.length != 1){
			$.alert('请先选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', keys[0]);
		//业务判断
		$.showDialog(_path+"/xtgl/plxg_cxPlxg.html?gnmkdm="+row.gnmkdm,'设置批量数据修改信息',$.extend({},modifyConfig,{
			"width":  "600px"
		}));
	});
	
	jQuery("#search_go").click(function () {
		$("#tabGrid").searchGrid({
			"gnmkdm"	: jQuery('#gnmkdm_query').val()
		});
	});
	
	
});
