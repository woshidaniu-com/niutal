var BjdmGrid = Class.create(BaseJqGrid,{  
	caption : "班级列表",
	pager: "pager", //分页工具栏  
    url: _path+'/zfxg/bjdm/cxBjdmList.html', //这是Action的请求地址  
    colModel:[
    	 {label:'bjdm_id',name:'bjdm_id', index: 'bjdm_id',key:true,hidden:true},
	     {label:'班级代码',name:'bjdm', index: 'bjdm',align:'center'},
	     {label:'班级名称',name:'bjmc', index: 'bjmc',align:'center'},
	     {label:'年级',name:'njdm_id', index: 'njdm_id',align:'center'},
	     {label:'学院',name:'bmmc', index: 'bmdm_id',align:'center'},
	     {label:'专业',name:'zymc', index: 'zydm_id',align:'center'}
	],
	sortname: 'bjdm', //首次加载要进行排序的字段 
 	sortorder: "asc"
});

//查询
function searchResult(){
	var map = {};
	map["bjdm"] = jQuery('#bjdm').val();
	map["bjmc"] = jQuery('#bjmc').val();
	map["bmdm_id"] = jQuery('#bmdm_id_lsbm').val();
	map["zydm_id"] = jQuery('#zydm_id').val();
	map["njdm_id"] = jQuery('#njdm_id').val();
	search('tabGrid',map);
}

/*
 * 绑定操作按钮
 */
function bdan(){
	//绑定增加点击事件
	jQuery("#btn_zj").unbind().bind("click",function () {
		showWindow('增加班级',500,300,_path+"/zfxg/bjdm/zjBjdm.html");
	});
	
	//绑定删除事件
	jQuery("#btn_sc").unbind().bind("click",function () {
		plcz(_path+"/zfxg/bjdm/scBjdm.html","删除");
	});
	
	//绑定修改事件
	jQuery("#btn_xg").unbind().bind("click",function () {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', id);
		var url=_path+"/zfxg/bjdm/xgBjdm.html?bjdm_id="; 
		url+=id;
		showWindow('修改班级',400,300,url);
	});
	
	
	jQuery("#btn_dc").unbind().bind("click",function () {
		var url = _path+'/zfxg/bjdm/export.html';
		customExport(650,450,_path+'/zfxg/bjdm/customExport.html',function(){
			jQuery("#form1").attr("action",url);
			jQuery("#form1").submit();
		});
	});
	
	jQuery("#btn_dr").unbind().bind("click",function () {
		importData("IMPORT_N010800_BJDM");
		return false;
	});
}