jQuery(function($){
	var Grid = $.extend({},BaseJqGrid,{
			resizeHandle:"#searchResult",
			pager : "#pager", // 分页工具栏
			height : "auto",
			rowNum : 5000, // 每页显示记录数
			rowList : [5000], // 可调整每页显示的记录数
			//rowList : [15, 30, 50, 100], // 可调整每页显示的记录数			
			url : _path + '/xtgl/timeSetting_cxTimeSettingIndex.html?doType=query', // 这是Action的请求地址
			gridview:false,
			treeGrid: true,//启用树型Grid功能
			treeGridModel: 'adjacency',//表示返回数据的读取类型，分为两种：和adjacency	
		    ExpandColClick: true,//设置为true，点击行时将会展开/收缩属性表格，而不仅限于点击图标
		    ExpandColumn: 'gnmkmc',//树型结构在哪列显示
		    multiselect : true, // 是否支持多选
		    autowidth : false, // 自动调整宽度
			colModel : [ 
				{   label : '',
					name : '',
					index : '',
					sortable : false, 
					resizable : false, 
					width:'35px',
					formatter:function (cellvalue, options, rowObject){
						var res="";
						res = '<input type="checkbox" name="gnmkdm"  style="margin-left: 5px;" value="' + rowObject.gnmkdm + '" />';
						return res;
					}
				},
				{label:'功能模块代码',name : 'gnmkdm', index : 'gnmkdm', key : true ,  hidden : true ,sortable : false},
				{label:'功能模块名称',name : 'gnmkmc',index : 'gnmkmc',align:'left',width:'300px' , sortable : false},
				{label:'操作名称',name : 'czmc',index : 'czmc',align : 'center',width:'100px' , resizeable:false,sortable : false},
				{label:'开始时间',name : 'kssj',index : 'kssj',align : 'center',width:'100px' , resizeable:false,sortable : false}, 
				{label:'结束时间',name : 'jssj',index : 'jssj',align : 'center',width:'100px' , resizeable:false,sortable : false}, 
				{label:'是否使用',name : 'sfsy', index : 'sfsy',sortable : false  ,resizeable:false, formatter:function (cellvalue, options, rowObject){
					return (rowObject.sfksc==1)?"使用":"停用";
				}},
				{label:'提示信息',name : 'tsxx',index : 'tsxx',align : 'left',width:'420px' , sortable : false},
				{label:'备注',name : 'bz',index : 'bz',align : 'left',width:'420px' , sortable : false},
			],
			treeReader : {
			    level_field: 'cdjb',
			    parent_id_field: 'fjgndm',
			    leaf_field: 'leaf',
			    expanded_field: 'expanded'
			},
	});
	$("#tabGrid").loadJqGrid(Grid);
});