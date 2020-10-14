//获取全局的表名
var tableName = $("#tableName").val();
//查询
function searchResult(){
	var map = {};
		if (tableName == "niutal_xtgl_njdmb") {
			map["njdmModel.sfsy"] = jQuery('#sfsy_cx').val();
			map["njdmModel.njmc"] = jQuery('#njmc_cx').val();
		} else if (tableName == "niutal_xtgl_yjfszhxxb") {
			map["yjfszhxxModel.fslx"] = jQuery('#fslx_cx').val();
			map["yjfszhxxModel.yjzh"] = jQuery('#yjzh_cx').val();
		}
	search('tabGrid',map);
}


jQuery(function($){
	
	var JcdmGrid = jQuery.extend(true,{},BaseJqGrid,{  
		resizeHandle:"#searchBox",
		pager: "#pager", //分页工具栏  
        url: _path+'/cygn/jcdm_cxJcdmList.html?table='+table, //这是Action的请求地址  
        colModel:colModel,
        shrinkToFit: true,
		sortname: sortname, //首次加载要进行排序的字段 
       	sortorder: sortorder,
       	rownumbers: true,
       	multiselect: $.defined(multiselect) ? multiselect : true
	});
	
	$("#tabGrid").loadJqGrid(JcdmGrid);

	/*
	 * 绑定操作按钮
	 */
	//绑定增加事件
	jQuery("#btn_zj").click(function () {
		$.showDialog(_path+'/cygn/jcdm_zjJcdm.html?table='+table,'增加',$.extend({},addConfig,{
			"width": Math.max(parseInt(width),500)
		}));
	});

	//绑定修改事件
	jQuery("#btn_xg").click(function () {
		var ids = $("#tabGrid").getKeys();
		if(ids.length != 1){
			jQuery.alert('请选定一条记录!');
			return;
		}
		if(!$.founded(primary_key)){
			jQuery.alert('未找到主键字段!');
			return;
		}
		var isValidate = true;
		jQuery.ajax({
			url		:_path+'/cygn/jcdm_cxValidate.html?doType=update',
			data	: {"table" : table,"ids":ids[0]},
			async	: false,
			type	: "post",
			dataType: "json",
			success	: function(data){
				isValidate  =  data;
				if(!data){
	  			  	jQuery.alert("对不起，您选择的数据被使用不能修改。");
				}
			}
		});
		if(isValidate){	
			$.showDialog(_path+'/cygn/jcdm_xgJcdm.html','修改',$.extend({},modifyConfig,{
				"width": Math.max(parseInt(width),500),
				"data" : {
					"table" : table,
					"primary_key" : primary_key,
					"primary_value" : ids[0]
				 }
			}));
		}
	});

	//绑定删除事件
	jQuery("#btn_sc").click(function () {
		var ids= $("#tabGrid").getKeys();
		if (ids.length == 0){
			jQuery.alert('请选择您要删除的记录！');
		} else {
			var dataMap = {
				"table" 		: table,
				"primary_key" 	: primary_key,
				"ids" 			: ids.join(",")
			};
			var isValidate = true;
			jQuery.ajax({
				url		: _path+'/cygn/jcdm_cxValidate.html?doType=delete',
				async	: false,
				type	: "post",
				dataType: "json",
				data	: dataMap,					
				success	: function(data){
				  	isValidate  =  data;
				  	if(!data){
				  		jQuery.alert("对不起，您选择的数据被使用不能删除。");
				  	}
				}
			 });
			 if(isValidate){
				$.confirm('您确定要删除选择的记录吗？',function(isBoolean){
					if(isBoolean){
						jQuery.ajax({
							url		: _path+'/cygn/jcdm_scJcdm.html',
							async	: false,
							type	: "post",
							dataType: "json",
							data	: dataMap,					
							success	: function(responseText){
								if(responseText.indexOf("成功") > -1){
									$.success(responseText,function() {
										if($("#tabGrid").size() > 0){
											refershGrid("tabGrid");
										}
									});
								}else if(responseText.indexOf("失败") > -1){
									$.error(responseText);
								} else{
									$.alert(responseText);
								}
							}
						});
					}
				 });
			 }
		}
	});
	
	//绑定查看密码事件
	jQuery("#btn_ckmm").click(function () {
		var ids = $("#tabGrid").getKeys();
		if(ids.length != 1){
			jQuery.alert('请选定一条记录!');
			return;
		}
		$.showDialog(_path+'/cygn/jcdm_ckmmJcdm.html','查看密码',$.extend({},viewConfig,{
			"width": Math.max(parseInt(width),500),
			"data" : {
				"table" : table,
				"primary_key" : primary_key,
				"primary_value" : ids[0]
			 }
		}));
	});
	
});