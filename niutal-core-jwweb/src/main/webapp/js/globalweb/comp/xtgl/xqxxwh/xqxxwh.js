jQuery(function($){
	
	var TempGrid = $.extend(true,{},BaseJqGrid,{  
		resizeHandle:"#autogrid",
		pager: "#pager", //分页工具栏  
	    url: _path+'/xtgl/xqxx_cxXqxx.html?doType=query', //这是Action的请求地址
	    colModel:[
  	        {label:'校区号id',name:'xqh_id',index: 'xqh_id',key:true,hidden:true,align:'center'},
  	        {label:'可跨校区合班id',name:'kkhbxqh_id',hidden:true,index: 'kkhbxqh_id'},
  	      	{label:'可跨校区选课id',name:'kkxkxq_id',hidden:true,index: 'kkxkxq_id'},
	        {label:'校区号',name:'xqh', width:'100px',index: 'xqh',align:'center'},
	        {label:'校区名称',name:'xqmc', width:'200px',index: 'xqmc',align:'left'},
	        {label:'校区地址',name:'xqdz', width:'300px',index: 'xqdz',align:'left'},
	        {label:'增加人',name:'zjr', index:'zjr',align:'left',hidden:true},
	        {label:'增加时间',name:'zjsj', index: 'zjsj',align:'left',hidden:true},
	        {label:'修改人',name:'xgr', index: 'xgr',align:'left',hidden:true},
	        {label:'修改时间',name:'xgsj',index: 'xgsj',align:'left',hidden:true},
	        {label:'校区邮政编码',name:'xqyzbm', width:'200x',index: 'xqyzbm',align:'left'},
	        {label:'可跨校区合班',name:'kkhbxqhmc', width:'240px',index: 'kkhbxqhmc',align:'left'},
	        {label:'可跨校区选课',name:'kkxkxqmc', width:'240px',index: 'kkxkxqmc',align:'left'}
		],
		sortname: 'xqh_id', //首次加载要进行排序的字段 
	});
	
	$("#tabGrid").loadJqGrid(TempGrid);

	/*
	 * 绑定操作按钮
	 */
	//绑定修改事件
	$("#btn_xg").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return false;
		}
		$.showDialog(_path+'/xtgl/xqxx_xgXqxx.html?xqh_id=' + ids.join(","),'修改',$.extend({},modifyConfig,{
			width: ($("#yhgnPage").innerWidth() - 200)+"px"
		}));
		return false;
	});
	
	// 绑定增加事件
	$("#btn_zj").click(function() {
		$.showDialog(_path+'/xtgl/xqxx_zjXqxx.html','增加',$.extend({},addConfig,{
			width: ($("#yhgnPage").innerWidth() - 200)+"px"
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

		$.confirm('您确定要删除选择的记录吗？',function(isBoolean){
			if(isBoolean){
				$.ajaxSetup({ async : false });
				$.post(_path+'/xtgl/xqxx_scXqxx.html', {
					xqh_id : ids.join(",")
				}, function(responseText) {
					if(responseText.indexOf("成功") > -1){
						$.success(responseText,function() {
							if($("#tabGrid").size() > 0){
								$("#tabGrid").refershGrid();
							}
						});
					}else if(responseText.indexOf("失败") > -1){
						$.error(responseText);
					} else{
						$.alert(responseText);
					}
				}, 'json');
				$.ajaxSetup({ async : true });
			}
		 });

	});
	
	$("#searchResult").click(function(){
		//查询
		var map = {};
			map["xqh"] = jQuery('#xqh_cx').val();
		search('tabGrid', map);
	});
	
});