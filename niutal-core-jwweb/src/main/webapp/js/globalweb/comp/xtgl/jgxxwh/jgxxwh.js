jQuery(function($){
	$("#tabGrid").jqGridWrap({
		remoteParams:{"zd_fzdm":"jgxx"},
		resizeHandle:"#autogrid",
		pager: "#pager", //分页工具栏  
		autowidth: true,  
	    url: _path+'/xtgl/jgxx_cxJgxx.html?doType=query', //这是Action的请求地址
	    colModel:[
	  	        {label:'机构id',name:'jg_id',index: 'jg_id',key:true,hidden:true,align:'center'},
		        {label:'机构代码',name:'jgdm', width:'100px',index: 'jgdm',align:'left'},
		        {label:'机构名称',name:'jgmc', width:'100px',index: 'jgmc',align:'left'},
		        {label:'机构英文名称',name:'jgywmc', width:'100px',index: 'jgywmc',align:'left'},
		        {label:'机构简称',name:'jgjc', width:'100px',index: 'jgjc',align:'left'},
		        {label:'机构简拼',name:'jgjp', width:'100px',index: 'jgjp',align:'left'},
		        {label:'机构地址',name:'jgdz', width:'100px',index: 'jgdz',align:'left'},
		        {label:'隶属上级机构名称',name:'lssjjgmc', width:'150px',index: 'lssjjgmc',align:'left'},
		        {label:'隶属校区名称',name:'lsxqmc', width:'150px',index: 'lsxqmc',align:'left'},
		        {label:'是否有效',name:'jgyxbs', width:'80px',index: 'jgyxbs',align:'left',formatter:'select',editoptions:{value:"1:是;0:否"}},
		        {label:'是否教学部门',name:'sfjxbm', width:'80px',index: 'sfjxbm',align:'left',formatter:'select',editoptions:{value:"1:是;0:否"}},
		        {label:'是否实体',name:'sfst', width:'80px',index: 'sfst',align:'left',formatter:'select',editoptions:{value:"1:是;0:否"}},
		        {label:'建立年月',name:'jlny', width:'100px',index: 'jlny',align:'left'},
		        {label:'机构邮政编码',name:'jgyzbm', width:'100px',index: 'jgyzbm',align:'left'},
		        {label:'负责人',name:'xm', width:'100px',index: 'xm',align:'left'},
		        {label:'开课学院',name:'kkxy', width:'150px',index: 'kkxy',align:'left',formatter:'select',editoptions:{value:"1:开课学院;2:学生学院;3:既开课学院又学生学院"}},
		        {label:'机构类别',name:'jglb', width:'100px',index: 'jglb',align:'left',formatter:'select',editoptions:{value:"1:教学院系;2:科研机构;3:公共服务;4:党务部门;5:行政机构;6:附属单位;7:后勤部门;8:校办产业;9:其他"}},
		        {label:'机构简介',name:'jgjj', width:'200px',index: 'jgjj',align:'left',hidden:true},
		        {label:'机构简介',name:'jgjj_cx', width:'200px',index: 'jgjj_cx',align:'left',	 
		        	cellattr: function(rowId, tv, rowObject, cm, rdata) {
		        		var jgjj = $.defined(rowObject.jgjj)?rowObject.jgjj:"";
			        	return 'title=\"'+jgjj+'\"';
		    	 	}	
		        }
		],
		sortname: 'jg_id', //首次加载要进行排序的字段 
	 	sortorder: "desc"
	});


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
		$.showDialog(_path+'/xtgl/jgxx_xgJgxx.html?jg_id=' + ids.join(","),'修改',$.extend({},modifyConfig,{
			width: ($("#yhgnPage").innerWidth() - 100)+"px"
		}));
		return false;
	});
	
	// 绑定修改事件
	$("#btn_zj").click(function() {
		$.showDialog(_path+'/xtgl/jgxx_zjJgxx.html','增加',$.extend({},addConfig,{
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

		$.confirm('您确定要删除选择的记录吗？',function(isBoolean){
			if(isBoolean){
				$.ajaxSetup({ async : false });
				$.post(_path+'/xtgl/jgxx_scJgxx.html', {
					jg_id : ids.join(",")
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
			map["jgdm"] = jQuery('#jgdm_cx').val();
		search('tabGrid', map);
	});
	
});
