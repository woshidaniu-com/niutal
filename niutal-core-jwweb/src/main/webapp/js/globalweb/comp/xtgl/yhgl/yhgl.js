jQuery(function($){
	
	
	$("#tabGrid").jqGridWrap({
		resizeHandle:"#searchBox2",
		paper:"#pager",
		remoteParams:{"zd_fzdm":"yhgl2"},
		shrinkToFit: true,
	    url: _path+'/xtgl/yhgl_cxYhxx.html?doType=query', //这是Action的请求地址  
	    colModel:[
			{label:'用户名',name:'yhm', index: 'yhm',key:true,align:'left',width:"200px"},
			{label:'姓名',name:'xm', index: 'xm',align:'left',width:"200px"},
			{label:'所属机构',name:'jgmc', index: 'jgmc',align:'left',width:"200px"},
			{label:'联系电话',name:'sjhm', index: 'sjhm',align:'left',width:"150px"},
			{label:'邮箱',name:'dzyx', index: 'dzyx',align:'left',width:"170px"},
			{label:'是否启用',name:'sfqy', index: 'sfqy',align:'center',width:"80px",formatter:'select',editoptions:{value:{"1":"是","0":"否"}}},
			{label:'拥有角色',name:'jsmc', index: 'jsmc',align:'left',width:"350px",
				formatter: function(cellvalue, options, rowObject){
	        	 	return   $.founded(cellvalue) ? '<p style="white-space: normal;">' +cellvalue+ '</p>' : "";
	         	}
			}
			/*
			
			{label:'拥有角色数',name:'jss', index: 'jss',align:'center',width:"150px",formatter:function(cellvalue, options, rowObject){
				return "<a href='javascript:ckYh(\""+rowObject.yhm+"\",\""+cellvalue+"\")' >"+cellvalue+"</a>";
			}}*/
		],
		sortname: 'yhm', //首次加载要进行排序的字段 
	 	sortorder: "desc"
	});
	
	 
	/*绑定默认高级查询*/
	$("#searchBox2").searchBox({
		autoSearch:true,
		onSearchClick:function(paramMap){
			search('tabGrid',paramMap);
		},
		filters:[{"jgmc":"所属机构"},{"yhm":"用户名"},{"xm":"姓名"},{"jsmc":"角色名"}],
		model:[
	       {"index":"sf_list","text":"是否启用",options:[{"1":"是"},{"0":"否"}]},
	       {"index":"jg_id_list","text":"所属机构",url:_path+"/xtgl/comm_cxJgdmPaged.html",mapper:{"key":"jg_id","text":"jgmc"},sort:"jgdm",order:"desc",gridType:"8"}
		]
	});
	
	
	/*====================================================绑定按钮事件====================================================*/
	
	//绑定增加点击事件
	jQuery("#btn_zj").click(function () {
		/*alert($("#tabGrid").getKeys());
		$.showSelectDialog("1",{"multiselect":false},function(result_arr,delete_arr){
			alert(result_arr);
		});
		//$("#testID").loadSelectPanel("1");*/
		$.showDialog(_path + '/xtgl/yhgl_zjYhxx.html','增加用户',$.extend({},addConfig,{"width":"900px"}));
	});
	
	//绑定删除事件
	jQuery("#btn_sc").click(function () {
		plcz(_path + '/xtgl/yhgl_scYhxx.html','删除');
	});
	
	//绑定修改事件
	jQuery("#btn_xg").click(function () {
		var ids = getChecked();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', ids[0]);
		$.showDialog(_path + "/xtgl/yhgl_xgYhxx.html",'修改用户',$.extend({
			data:{"yhm":encodeURIComponent(row.yhm)}
		},modifyConfig,{"width":"900px"}));
	});
	
	//查看
	jQuery("#btn_ck").click(function () {
		var ids = getChecked();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', ids[0]);
		$.showDialog(_path + "/xtgl/yhgl_ckYhxx.html",'查看用户',$.extend({
			data:{"yhm":encodeURIComponent(row.yhm)}
		},viewConfig,{"width":"700px"}));
	});
	
	//设置所属角色
	jQuery("#btn_szssjs").click(function () {
		var ids = getChecked();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', ids[0]);
		$.showDialog(_path + "/xtgl/yhgl_szssjsYh.html",'设置所属角色',$.mergeObj(addConfig,{
			data	: {"yhm":encodeURIComponent(row.yhm)},
			width	: "800px"
		}));
		
	});
	
	//密码初始化
	jQuery("#btn_mmcsh").click(function () {
		var ids = getChecked();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', ids[0]);
		plcz(_path + "/xtgl/yhgl_mmcsh.html?pkValue="+encodeURIComponent(row.yhm),'初始化');
	});
	
});

function ckYh(yhm,num){
	if(num > 0){
		$.showDialog(_path + "/xtgl/yhgl_ckYhjsxx.html?yhm="+encodeURIComponent(yhm),'查看用户角色',$.mergeObj(viewConfig,{"width":"700px"}));
	}
}

//查询
function searchResult(){
	var map = {};
	map["jg_id"] = jQuery('#jg_id').val();
	map["yhm"] = jQuery('#yhm').val();
	map["xm"] = jQuery('#xm').val();
	map["sfqy"] = jQuery('#sfqy').val();
	map["jgdm"] = jQuery('#jgdm').val();
	search('tabGrid',map);
}

