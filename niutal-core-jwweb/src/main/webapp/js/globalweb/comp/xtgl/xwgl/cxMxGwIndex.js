jQuery(function($) {
	var xsGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#title-h3",
		pager: "#pager1", //分页工具栏  
		rowNum : 8, // 每页显示记录数
		rowList : [8 ,20, 30, 50, 100], // 可调整每页显示的记录数
	    url: _path+'/xtgl/xwgl_cxGwxx.html', //这是Action的请求地址  
	    colModel:[
		     {label:'教工号',name:'yhm', index: 'yhm',align:'left',width:"180px",key:true},
		     {label:'姓名',name:'xm', index: 'xm',align:'left',width:"140px"},
	      	 {label:'学院',name:'jgmc', index: 'jgmc',align:'left',width:"120px"}
		] 
	});
	$("#tabGrid1").loadJqGrid(xsGrid);
	
});

//查询
function queryResult(){
	if($("#szdjs").is(':checked')){
		var map = {};
		map["jsdm"]  = $("#jsdm").val();
		map["doType"] = "query";
		search('tabGrid1',map);
	}else{
		$("#tabGrid1" ).clearGridData();
	}
}