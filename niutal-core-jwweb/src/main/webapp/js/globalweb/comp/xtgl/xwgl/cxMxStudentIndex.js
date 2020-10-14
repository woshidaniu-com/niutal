jQuery(function($) {
	var xsGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#title-h3",
		pager: "#pager1", //分页工具栏  
		rowNum : 8, // 每页显示记录数
		rowList : [8 ,20, 30, 50, 100], // 可调整每页显示的记录数
	    url: _path+'/xtgl/xwgl_cxXsxx.html', //这是Action的请求地址  
	    colModel:[
		     {label:'xh_id',name:'xh_id', index:'xh_id', key:true, hidden:true},
		     {label:'学号',name:'xh', index: 'xh',align:'left',width:"180px"},
		     {label:'姓名',name:'xm', index: 'xm',align:'left',width:"140px"},
	      	 {label:'性别',name:'xbmc', index: 'xbmc',align:'left',width:"60px"},
		     {label:'年级',name:'njmc', index: 'njmc',width:"60px",align:'left'},
		     {label:'学院',name:'jgmc', index: 'jgmc',width:"140px",align:'left'},
		     {label:'专业',name:'zymc', index: 'zymc',width:"140px",align:'left'},
		     {label:'班级',name:'bj', index: 'bj',width:"125px",align:'left'}
		] 
	});
	$("#tabGrid1").loadJqGrid(xsGrid);
	//绑定级联查询事件
	$.bindChangeEvent("#jg_id","","#zyh_id","","#bh_id","#njdm_id"); 
});

//查询
function queryStudentResult(){
	if($("#szdxs").is(':checked')){
		var map = {};
		map["xqh_id"]  = $("#xqh_id").val();
		map["jg_id"]   = $("#jg_id").val();
		map["zyh_id"]  = $("#zyh_id").val();
		map["njdm_id"] = $("#njdm_id").val();
		map["bh_id"]   = $("#bh_id").val();
		map["xbm"]     = $("#xbdm").val();
		map["xslbdm"]  = $("#xslbdm").val();
		map["pyccdm"]  = $("#pyccdm").val();
		map["doType"] = "query";
		search('tabGrid1',map);
	}else{
		$("#tabGrid1" ).clearGridData();
	}
}