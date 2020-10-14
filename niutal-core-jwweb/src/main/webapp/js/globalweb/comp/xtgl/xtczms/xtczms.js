jQuery(function($){
	
	var xtczmsGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#searchResult",
		pager: "#pager", //分页工具栏  
	    url: _path+'/xtgl/xtczms_cxXtczmsIndex.html?doType=query', //这是Action的请求地址  
	    shrinkToFit: true,
	    minHeight:300,
	    colModel:[
		     {label:'ID',name:'xtczms_id', index:'xtczms_id', key:true, hidden:true},
		     {label:'操作',name:'czms', index: 'czms',align:'center',width:"60px",resizeable:false,sortable : false,
				formatter:function (cellvalue, options, rowObject){
					return '<a data-xtczms_id="'+rowObject["xtczms_id"]+'" href="#" class="xtczms blue"><i title="" data-placement="top" data-toggle="tooltip" class="glyphicon glyphicon-zoom-in bigger-110 blue" data-original-title="详情"></i></a>';
				}
			 },
			 {label:'启用状态',name : 'sfsy', index : 'sfsy',align:'center',width:"80px",resizeable:false,sortable : false,formatter : 'select', editoptions : {value : {'1':'启用','0':'停用'}}},
		     {label:'功能模块',name:'gnmkmc', index: 'gnmkmc',align:'center',width:"120px",resizeable:false,sortable : false},
		     {label:'页面',name:'czmc', index: 'czmc',align:'center',width:"80px",resizeable:false,sortable : false},
	      	 {label:'备注',name:'bz', index: 'bz',align:'left',width:"400px",resizeable:false,sortable : false}
		],
		sortname: 'gnmkdm', //首次加载要进行排序的字段 
	 	sortorder: "desc"
	});
	
	$("#tabGrid").loadJqGrid(xtczmsGrid);

	/*====================================================绑定按钮事件====================================================*/
	
	//修改操作描述
	$(document).off("click touchend", "#btn_xg").on("click touchend", "#btn_xg", function(e) {
		
		var keys = $("#tabGrid").getKeys();
		if(keys.length != 1){
			$.alert('请先选定一条记录!');
			return;
		}
		var row = $("#tabGrid").jqGrid('getRowData', keys[0]);
		//业务判断
		$.showDialog(_path+"/xtgl/xtczms_xgXtczms.html?xtczms_id="+row.xtczms_id,'修改系统操作描述',$.extend({},modifyConfig,{
			"width": ($("#yhgnPage").innerWidth() - 100) +"px"
		}));
	})
	//点击查询
	.off("click touchend", "#search_go").on("click touchend", "#search_go", function(e) {
		
		$("#tabGrid").searchGrid({
			"gndm"		: $('#gnmkdm_query').val(),
			"czdm"		: $('#czdm_query').val()
		});
	})
	//预览操作描述
	.off("click touchend", "a.xtczms").on("click touchend", "a.xtczms", function(e) {
		var xtczms_id = $(this).data("xtczms_id");
		$.showDialog(_path+"/xtgl/xtczms_cxXtczms.html?xtczms_id="+xtczms_id,'系统操作描述详情',$.extend({},viewConfig,{
			"width": ($("#yhgnPage").innerWidth() - 100) +"px"
		}));
	});
	
});