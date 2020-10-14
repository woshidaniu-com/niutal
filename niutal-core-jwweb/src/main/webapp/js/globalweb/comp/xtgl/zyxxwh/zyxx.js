jQuery(function($){
	
	var TempGrid = $.extend(true,{},BaseJqGrid,{  
		resizeHandle	: "#autogrid",
		pager			: "#pager", //分页工具栏  
	    url				: _path+'/xtgl/zyxx_cxZyxx.html?doType=query', //这是Action的请求地址
	    postData		: {"sfty":$("#cx_sfty").val()},
	    colModel:[
	  	        {label:'专业id',name:'zyh_id',index: 'zyh_id',key:true,hidden:true,align:'center'},
		        {label:'专业代码',name:'zyh', width:'100px',index: 'zyh',align:'center'},
		        {label:'专业名称',name:'zymc', width:'100px',index: 'zymc',align:'left'},
		        {label:'专业简称',name:'zyjc', width:'100px',index: 'zyjc',align:'left'},
		        {label:'专业英文名称',name:'zyywmc', width:'100px',index: 'zyywmc',align:'left'},
		        {label:'国标本专科专业',name:'bzkzymc', width:'100px',index: 'bzkzymc',align:'left'},
		        {label:'国标研究生专业',name:'yjszymc', width:'100px',index: 'yjszymc',align:'center'},
		        {label:'学制',name:'xz', width:'100px',index: 'xz',align:'center'},
		        {label:'层次名称',name:'ccmc', width:'100px',index: 'ccmc',align:'center'},
		        {label:'学科门类',name:'xkmlmc', width:'100px',index: 'xkmlmc',align:'center'},
		        {label:'所属学院',name:'jgmc', width:'100px',index: 'jgmc',align:'left'},
		        {label:'建立年月',name:'jlny', width:'100px',index: 'jlny',align:'center'},
		        {label:'优先级',name:'yxj', width:'100px',index: 'yxj',align:'left',formatter:'select',editoptions:{value:"1:特别;2:优先;3:普通;4:最后"}},
		        {label:'是否停用',name:'sfty', width:'100px',index: 'sfty',align:'center',formatter:'select',editoptions:{value:"0:否;1:是"}},
		        {label:'',name:'zyjj', width:'200px',index: 'zyjj',align:'left',hidden:true	},
		        {label:'专业简介',name:'zyjj_cx', width:'200px',index: 'zyjj_cx',align:'left',	 
		        	cellattr: function(rowId, tv, rowObject, cm, rdata) {
		        		var zyjj = $.defined(rowObject.zyjj)?rowObject.zyjj:"";
			        	return 'title=\"'+zyjj+'\"';
		    	 	}	
		        },
		        {label:'大类标识',name:'dlbs', width:'100px',index: 'dlbs',align:'center',formatter:'select',hidden:true,editoptions:{value:"zy:专业;dl:大类;"}},
		        {label:'备注',name:'bz', width:'200px',index: 'bz',align:'left'}
		],
		sortname: 'zyh', //首次加载要进行排序的字段 
	 	sortorder: "desc"
	});
	
	$("#tabGrid").loadJqGrid(TempGrid);
	
	/*
	 * 绑定操作按钮
	 */
	$("#btn_zj").click(function() {
		$.showDialog(_path+'/xtgl/zyxx_zjZyxx.html','增加',$.extend({},addConfig,{
			width: ($("#yhgnPage").innerWidth() - 100)+"px"
		}));
        return false;
	});		
	
	// 绑定修改事件
	$("#btn_xg").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		$.showDialog(_path+'/xtgl/zyxx_xgZyxx.html?zyh_id=' + ids[0],'修改',$.extend({},modifyConfig,{
			width: ($("#yhgnPage").innerWidth() - 100)+"px"
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
		$.post(_path+'/xtgl/zyxx_cxCheckZy.html',{"zyh_id":ids.join(",")},function(data){
			if(data>0){
				$.alert("选择的专业已使用，不允许删除");
				return false;
			}else{
				$.confirm('您确定要删除选择的记录吗？',function(isBoolean){
					if(isBoolean){
						$.ajaxSetup({ async : false });
						$.post(_path+'/xtgl/zyxx_scZyxx.html', {
							zyh_id : ids.join(",")
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
			}
			
		});
	});
	
	// 绑定停用事件
	$("#btn_ty").click(function() {
		var ids = getChecked();
		if (ids.length == 0) {
			$.alert('请选择您要停用的记录！');
			return false;
		}
		$.confirm('您确定要停用选择的记录吗?',function(isBoolean){
			if(isBoolean){
				$.ajaxSetup( {
					async : false
				});
				$.post(_path+'/xtgl/zyxx_tyZyxx.html', {
					zyh_id : ids.join(","),sfty:"1"
				}, function(data) {
					$.success(data.toString(),function(){refershGrid('tabGrid');});
				}, 'json');
				$.ajaxSetup( {
					async : true
				});
			}
		 });
	});
	
	// 绑定启用事件
	$("#btn_qy").click(function() {
		var ids = getChecked();
		if (ids.length == 0) {
			$.alert('请选择您要启用的记录！');
			return false;
		}
		$.confirm('您确定要启用选择的记录吗？',function(isBoolean){
			if(isBoolean){
				$.ajaxSetup( {
					async : false
				});
				$.post(_path+'/xtgl/zyxx_qyZyxx.html', {
					zyh_id : ids.join(","),sfty:"0"
				}, function(data) {
					$.success(data.toString(),function(){refershGrid('tabGrid');});
				}, 'json');
				$.ajaxSetup( {
					async : true
				});
			}
		 });
	});
	
	$("#searchResult").click(function(){
		//查询
		var map = {
			"jg_id"	: jQuery('#cx_jg_id').val(),
			"zyh"	: $.trim($('#cx_zyh').val()),
			"dlbs"	: jQuery('#cx_dlbs').val(),
			"sfty"	: jQuery('#cx_sfty').val()
		};
		search('tabGrid', map);
	});
	
});