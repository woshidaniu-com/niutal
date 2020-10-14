jQuery(function($){

	var tempGridOptions = {
		url:  'getYhxxList.zf',
		uniqueId: "zgh",
		columns: [
            {checkbox: true }, 
            {field: 'zgh', title: '用户名',sortable:true,width:"100px"}, 
            {field: 'xm', title: '姓名',sortable:true,width:"150px"}, 
            {field: 'lxdh',title: '联系电话',sortable:true,width:"200px"},
            {field: 'dzyx',title: '邮箱',sortable:true,width:"300px"},
            {field: 'jgmc',title: '部门',sortable:true,width:"300px"},
            {field: 'sfqy',title: '启用状态',sortable:true,width:"80px",align:'center',formatter:function(value,row,index){
            	var ret;
            	if(value == '1'){
            		 ret = '<span class="text-success">启用</span>';	
            	}else {
            		 ret = '<span class="text-danger">停用</span>';
            	}
            	return ret;
            }},
            {field: 'jsxx',title: '角色',sortable:true,width:"300px"}
    	],
	 	toolbar:'#but_ancd',
	 	sortName: 'zgh',
		sortOrder: "desc",
	 	searchParams:function(){
	 		var map = $.search.getSearchMap();
			return map;
	 	}
	};
	
	$("#tabGrid").loadGrid(tempGridOptions);
	
	
	/*====================================================绑定按钮事件====================================================*/
	
	jQuery("#btn_dc").click(function () {
		$.customExport("yhgl_yhdc", "doExport.zf", function(exportForm){

			//移除上次导出留下的高级查询input标签
			exportForm.find("input[name^='searchModel']").remove();
			//导出带上高级查询条件
			exportForm.append($.search.getSearchHtml());
		}, {});
	});

	//绑定增加点击事件
	jQuery("#btn_zj").click(function () {
		$.showDialog('zjYhxx.zf','增加用户',$.extend({},addConfig,{"width":"700px"}));
	});
	
	//绑定删除事件
	jQuery("#btn_sc").click(function () {
		$("#tabGrid").plcz('scYhxx.zf','删除');
	});
	
	//绑定修改事件
	jQuery("#btn_xg").click(function () {
		var ids = $("#tabGrid").getKeys();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog("xgYhxx.zf?zgh="+row["zgh"],'修改用户',$.extend({},modifyConfig,{"width":"700px"}));
	});
	
	//查看
	jQuery("#btn_ck").click(function () {
		var ids = $("#tabGrid").getKeys();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog("ckYhxx.zf?zgh="+row.zgh,'查看用户',$.extend({},viewConfig,{"width":"700px"}));
	});
	
	//设置所属角色
	jQuery("#btn_szssjs").click(function () {
		var ids = $("#tabGrid").getKeys();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog("szssjsYh.zf?zgh="+row.zgh,'设置所属角色',$.mergeObj(modifyConfig,{"width":"800px"}));
	});
	
	//密码初始化
	jQuery("#btn_mmcsh").click(function () {
		var ids = $("#tabGrid").getKeys();
		if(ids.length == 0){
			$.alert('请选择您要操作的记录!');
			return;
		}
		$.showDialog("mmcsh.zf?ids="+ids.toString(),'密码初始化',$.extend({},modifyConfig,{"width":"700px"}));
	});
	
	//密码批量初始化
	jQuery("#btn_mmplcsh").click(function () {
		$.confirm('您确定要修改查询记录中所有用户的密码吗？',function(result) {
			if(result){
				$.showDialog("mmplcsh.zf",'密码批量初始化',$.extend({},modifyConfig,{
					"width":"700px",
				 }
				));
			}
		});
	});
	
	//数据资源授权
	jQuery("#btn_sjsq").click(function () {
		var ids = $("#tabGrid").getKeys();
		if(ids.length == 0){
			$.alert('请选择您要操作的记录!');
			return;
		}
		$.showDialog("yhsjfw.zf?ids="+ids.toString(),'用户数据授权',$.extend({},modifyConfig,{"width":"800px"}));
	});
	
	//启用/停用
	jQuery("#btn_qy").click(function () {
		$("#tabGrid").plcz("qyty.zf?sfqy=1",'启用');
	});
	
	jQuery("#btn_ty").click(function () {
		$("#tabGrid").plcz("qyty.zf?sfqy=0",'停用');
	});
	
	$(":button[name=search_button]").bind("click",searchResult);
});


//查询
function searchResult(){
//	var map = {};
//	map["zgh"] = jQuery('#zgh').val();
//	map["xm"] = jQuery('#xm').val();
//	map["sfqy"] = jQuery('#sfqy').val();
//	map["jgdm"] = jQuery('#jgdm').val();
	$("#tabGrid").refreshGrid();
}

//回车键查询
//$('#xm').bind("keydown", "return", function (ev) {
//	searchResult()   
//})
