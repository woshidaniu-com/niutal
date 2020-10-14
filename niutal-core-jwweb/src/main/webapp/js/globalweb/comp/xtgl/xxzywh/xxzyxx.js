jQuery(function($){
	
	var TempGrid = $.extend(true,{},BaseJqGrid,{  
		resizeHandle:"#autogrid",
		pager: "#pager", //分页工具栏  
	    url: _path+'/xtgl/xxzy_cxXxzyxx.html?doType=query', //这是Action的请求地址
	    postData : function(){return {"zydm":$.trim($('#cx_zyh').val()),"pcdm":$("#cx_pcdm").val()};},
	    colModel:[
	  	        {label:'xxzydmb_id',name:'xxzydmb_id',index: 'xxzydmb_id',key:true,hidden:true},
		        {label:'批次代码',name:'pcdm', width:'100px',index: 'pcdm',align:'center',hidden:true},
		        {label:'批次',name:'pcmc', width:'100px',index: 'pcmc',align:'left'},
		        {label:'专业代码',name:'zydm', width:'100px',index: 'zydm',align:'left'},
		        {label:'专业名称',name:'zymc', width:'100px',index: 'zymc',align:'left'},
		        {label:'学科代码',name:'xkdm', width:'100px',index: 'xkdm',align:'left'},
		        {label:'学科名称',name:'xkmc', width:'100px',index: 'xkmc',align:'left'},
		        {label:'门类代码',name:'mldm', width:'100px',index: 'mldm',align:'left'},
		        {label:'门类名称',name:'mlmc', width:'100px',index: 'mlmc',align:'left'}
		],
		sortname: 'pcmc,zydm', //首次加载要进行排序的字段 
	 	sortorder: "desc"
	});
	
	$("#tabGrid").loadJqGrid(TempGrid);
	

	/*
	 * 绑定操作按钮
	 */
	$("#btn_zj").click(function() {
		$.showDialog(_path+'/xtgl/xxzy_zjXxzyxx.html','增加',addConfig);
	    return false;
	});		

	// 绑定修改事件
	$("#btn_xg").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		$.showDialog(_path+'/xtgl/xxzy_xgXxzyxx.html?xxzydmb_id=' + ids[0],'修改',modifyConfig);
		return false;
	});

	// 绑定删除事件
	$("#btn_sc").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length == 0) {
			$.alert('请选择您要删除的记录！');
			return false;
		}
		$.post(_path+'/xtgl/xxzy_cxCheckZy.html',{"xxzydmb_id":ids.join(",")},function(data){
			if(data>0){
				$.alert("选择的专业已使用，不允许删除");
				return false;
			}else{
				$.confirm('您确定要删除选择的记录吗？',function(isBoolean){
					if(isBoolean){
						jQuery.ajaxSetup({
							async : false
						});
						$.post(_path+'/xtgl/xxzy_scZyxx.html', {
							xxzydmb_id : ids.join(",")
						}, function(responseText) {
							if(responseText.indexOf("成功") > -1){
								$.success(responseText,function() {
									if($("#tabGrid").size() > 0){
										refershGrid("tabGrid");
									}
								});
							}else if(responseText.indexOf("失败") > -1){
								$.error(responseText,function() {
									
								});
							} else{
								$.alert(responseText,function() {
									
								});
							}
						}, 'json');
						$.ajaxSetup({
							async : true
						});
					}
				 });
			}
			
		});
	});
	
	$("#searchResult").click(function(){
		//查询
		var map = {};
			map["zydm"] = $.trim($('#cx_zydm').val());
			map["pcdm"] = jQuery('#cx_pcdm').val();
		search('tabGrid', map);
	});
	
});