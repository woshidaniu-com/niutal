jQuery(function($) {
	var xsGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#title-h3",
		pager: "#pager1", //分页工具栏  
		rowNum : 8, // 每页显示记录数
		rowList : [8 ,20, 30, 50, 100], // 可调整每页显示的记录数
	    url: _path+'/xtgl/xwgl_cxJsxx.html', //这是Action的请求地址  
	    colModel:[
		     {label:'jgh_id',name:'jgh_id', index:'jgh_id', key:true, hidden:true},
		     {label:'教工号',name:'jgh', index: 'jgh',align:'left',width:"180px"},
		     {label:'姓名',name:'xm', index: 'xm',align:'left',width:"140px"},
	      	 {label:'性别',name:'xbmc', index: 'xbmc',align:'left',width:"120px"},
		     {label:'职称',name:'zcmc', index: 'zcmc',width:"150px",align:'left'}
		    
		] 
	});
	
	$("#tabGrid1").loadJqGrid(xsGrid);
});

//查询
function queryTeachResult(){
	if($("#szdjs").is(':checked')){
		if(!$.founded($("#xnm").val())){
			$.alert("请选择学年");
			return false;
		}
		if(!$.founded($("#xqm").val())){
			$.alert("请选择学期");
			return false;
		}
		var map = {};
			map["xnm"]  = $("#xnm").val();
			map["xqm"]   = $("#xqm").val();
			map["xqh_id"]  = $("#xqh_id").val();
			map["jg_id"] = $("#jg_id").val();
			map["kkbm_id"]   = $("#kkbm_id").val();
			map["xbm"]     = $("#xbdm").val();
			map["doType"] = "query";
		search('tabGrid1',map);
	}else{
		$("#tabGrid1" ).clearGridData();
	}
}