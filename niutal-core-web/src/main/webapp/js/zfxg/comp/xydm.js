var XydmGrid = Class.create(BaseJqGrid,{  
	caption : "学院列表",
	pager: "pager", //分页工具栏  
    url: _path+'/zfxg/xydm/cxXydmList.html', //这是Action的请求地址  
    colModel:[
    	 {label:'学院代码',name:'bmdm_id', index: 'bmdm_id',key:true,align:'center'},
	     {label:'学院名称',name:'bmmc', index: 'bmmc',align:'center'},
    	 {label:'部门类别',name:'bmlxmc', index: 'bmlxmc',align:'center'}
	],
	sortname: 'bmdm_id', //首次加载要进行排序的字段 
 	sortorder: "asc"
});

//查询
function searchResult(){
	var map = {};
	map["bmdm_id"] = jQuery('#bmdm_id').val();
	map["bmmc"] = jQuery('#bmmc').val();
	map["bmlx"] = jQuery('#bmlx').val();
	search('tabGrid',map);
}

/*
 * 绑定操作按钮
 */
function bdan(){
	//绑定增加点击事件
	jQuery("#btn_zj").unbind().bind("click",function () {
		showWindow('增加学院',400,300,_path+"/zfxg/xydm/zjXydm.html");
	});
	
	//绑定删除事件
	jQuery("#btn_sc").unbind().bind("click",function () {
		plcz(_path+"/zfxg/xydm/scXydm.html","删除");
	});
	
	//绑定修改事件
	jQuery("#btn_xg").unbind().bind("click",function () {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', id);
		var url=_path+"/zfxg/xydm/xgXydm.html?bmdm_id="; 
		url+=id;
		showWindow('修改学院',400,300,url);
	});
	
	jQuery("#btn_dc").unbind().bind("click",function () {
		var url = _path+'/zfxg/xydm/export.html';
		customExport(650,450,_path+'/zfxg/xydm/customExport.html',function(){
			jQuery("#form1").attr("action",url);
			jQuery("#form1").submit();
		});
	});
	
	jQuery("#btn_dr").unbind().bind("click",function () {
		importData("IMPORT_N010800_XYDM");
		return false;
	});
}