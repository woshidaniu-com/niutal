var ZydmGrid = Class.create(BaseJqGrid,{  
	caption : "专业列表",
	pager: "pager", //分页工具栏  
    url: _path+"/zfxg/zydm/cxZydmList.html", //这是Action的请求地址  
    colModel:[
         {label:'专业代码',name:'zydm_id', index: 'zydm_id',key:true,align:'center',hidden:true},
    	 {label:'专业代码',name:'zydm', index: 'zydm',align:'center'},
	     {label:'专业名称',name:'zymc', index: 'zymc',align:'center'},
	     {label:'所属学院',name:'bmmc', index: 'bmmc',align:'center'},
	     {label:'学制',name:'xz', index: 'xz',align:'center'},
	     {label:'国家专业代码',name:'cydm_id_gbzydm', index: 'cydm_id_gbzydm',align:'center'}
	],
	sortname: 'zydm', //首次加载要进行排序的字段 
 	sortorder: "asc"
});

//查询
function searchResult(){
	var map = {};
	map["zydm"] = jQuery('#zydm').val();
	map["zymc"] = jQuery('#zymc').val();
	map["bmdm_id_lsbm"] = jQuery('#bmdm_id_lsbm').val();
	search('tabGrid',map);
}

/*
 * 绑定操作按钮
 */
function bdan(){
	
	//绑定增加点击事件
	jQuery("#btn_zj").unbind().bind("click",function () {
		showWindow('增加专业',500,300,_path+"/zfxg/zydm/zjZydm.html");
	});
	
	//绑定删除事件
	jQuery("#btn_sc").unbind().bind("click",function () {
		plcz(_path+"/zfxg/zydm/scZydm.html","删除");
	});
	
	//绑定修改事件
	jQuery("#btn_xg").unbind().bind("click",function () {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', id);
		var url=_path+"/zfxg/zydm/xgZydm.html?zydm_id="; 
		url+=id;
		showWindow('修改专业',500,300,url);
	});
	
	
	jQuery("#btn_dc").unbind().bind("click",function () {
		var url = _path+'/zfxg/zydm/export.html';
		customExport(650,450,_path+'/zfxg/zydm/customExport.html',function(){
			jQuery("#form1").attr("action",url);
			jQuery("#form1").submit();
		});
	});
	
	jQuery("#btn_dr").unbind().bind("click",function () {
		importData("IMPORT_N010800_ZYDM");
		return false;
	});
}