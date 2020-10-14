//等待文档准备就绪
jQuery(function($){
	
	/*----------------初始化列表----------------*/
	
	var TempGrid = $.extend({},BaseJqGrid,{  
		pager: "#pager", //分页工具栏  
		resizeHandle:"#cxsqBox",
	    url: _path+'/jlsgl/xssqcxjlsxmsh_cxXssqcxjlsxmsh.html?doType=query', //这是Action的请求地址  
	    colModel:[						
 		     {label:'交流地区',name:'xsjldqmc', index: 'xsjldqmc',align:'left',width:'100px'},
 		     {label:'交流学校',name:'xsjlxx', index: 'xsjlxx',align:'left', width:'100px'},
 		     {label:'交流学院',name:'xsjlxy', index: 'xsjlxy',align:'left', width:'150px'},
 		     {label:'交流期限',name:'xsjlqx', index: 'xsjlqx',align:'left', width:'150px'},
 		     {label:'交流开始时间',name:'xsjlksrq', index: 'xsjlksrq',align:'center', width:'150px'},
 		     {label:'交流结束时间',name:'xsjljsrq', index: 'xsjljsrq',align:'center',width:'150px'}
 		]
		//sortname: '', //首次加载要进行排序的字段 
	 	//sortorder: 'desc'
	});
	 
	$('#tabGrid').loadJqGrid(TempGrid);
	
	/*----------------绑定事件----------------*/
	
	$(document).off("click touchend", "#btn_cs").on("click touchend", "#btn_cs", function(e) {
		 
		//是否选择记录的判断逻辑
		var keys = $("#tabGrid").getKeys();
		if(keys.length != 1 ){
			$.alert("请选择一条记录！");
			return false;
		}
		//获取选择行数据对象
		var rowData = jQuery("#tabGrid").jqGrid("getRowData", keys[0]);
		//业务逻辑代码
		
		
		
	}).off("click touchend", "#btn_cs2").on("click touchend", "#btn_cs2", function(e) {
	 
		//是否选择记录的判断逻辑
		var keys = $("#tabGrid").getKeys();
		if(keys.length != 1 ){
			$.alert("请选择一条记录！");
			return false;
		}
		//获取选择行数据对象
		var rowData = jQuery("#tabGrid").jqGrid("getRowData", keys[0]);
		//业务逻辑代码
		
		
		
	});	
 
});	

/*----------------全局函数----------------*/

function func(){
	//是否选择记录的判断逻辑
	var keys = $("#tabGrid").getKeys();
	if(keys.length != 1 ){
		$.alert("请选择一条记录！");
		return false;
	}
	//业务逻辑代码
	
}

function func2(){
	//是否选择记录的判断逻辑
	var keys = $("#tabGrid").getKeys();
	if(keys.length != 1 ){
		$.alert("请选择一条记录！");
		return false;
	}
	//业务逻辑代码
	
} 