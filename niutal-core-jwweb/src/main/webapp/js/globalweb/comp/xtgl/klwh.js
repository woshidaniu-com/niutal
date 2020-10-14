jQuery(function($){
	
	var KlwhGrid = $.extend(true , {},BaseJqGrid,{
		resizeHandle:"#searchBox2",
		paper:"#pager",
		shrinkToFit: true,
	    url: _path+'/xtgl/klwh_cxYhxx.html?doType=query', //这是Action的请求地址  
	    colModel:[
			{label:'用户名',name:'yhm', index: 'yhm',key:true,align:'left',width:"200px"},
			{label:'姓名',name:'xm', index: 'xm',align:'left',width:"200px"},
			{label:'所属机构',name:'jgmc', index: 'jgmc',align:'left',width:"200px"},
			{label:'联系电话',name:'sjhm', index: 'sjhm',align:'left',width:"200px"},
			{label:'邮箱',name:'dzyx', index: 'dzyx',align:'left',width:"300px"},
			{label:'是否启用',name:'sfqy', index: 'sfqy',align:'center',width:"100px"},
			{label:'拥有角色数',name:'jss', index: 'jss',align:'center',width:"150px",formatter:function(cellvalue, options, rowObject){
				return "<a href='javascript:ckYh(\""+rowObject.yhm+"\",\""+cellvalue+"\")' >"+cellvalue+"</a>";
			}}
		],
		sortname: 'yhm', //首次加载要进行排序的字段 
	 	sortorder: "desc"
	});
	
	$("#tabGrid").loadJqGrid(KlwhGrid);
	
	/*====================================================绑定按钮事件====================================================*/
	
	

	//密码初始化
	jQuery("#btn_mmcsh").click(function () {
		var ids = getChecked();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', ids[0]);
		plcz("yhgl_mmcsh.html?pkValue="+row.yhm,'初始化');
	});
	
	
	// 批量数据授权
	jQuery("#btn_plsjsq").click(function() {
		
	});
	
});

//查询
function searchResult(){
	var map = {};
	map["xh"] = jQuery('#xh').val();
	map["xm"] = jQuery('#xm').val();
	search('tabGrid',map);
}