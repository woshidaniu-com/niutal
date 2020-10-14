jQuery(function($){
	var options = {
			 url: 'getLogsList.zf',
			 uniqueId: "czbh",
			 toolbar:  '#but_ancd',
			 sortName: 'czrq',
			 sortOrder: "desc",
			 classes:'table table-condensed',
			 showToggle:true,
		 	 showRefresh:true,
		 	 showColumns:true,
			 columns: [
	            {field: 'czmk', title: '业务模块',sortable:true, visible:true,width:'10%'}, 
	            {field: 'czlx', title: '操作类型',sortable:true,visible:true,width:'7%'}, 
	            {field: 'czr',title: '操作用户',sortable:true,width:'20%'}, 
	            {field: 'czrq',title: '操作日期',sortable:true,width:'10%'},
	            {field: 'ipdz',title: 'IP地址',width:'9%'},
	            {field: 'zjm',title: '主机名',sortable:false,width:'9%'},
	            {field: 'czms',title: '操作描述',sortable:false,width:'35%',cellStyle:function(v,r,i){
	            	return {
	            		css:{
	            			'word-break':'break-all'
	            		}
	            	}
	            }}
          ],
          searchParams:function(){
        	var map = $.search.getSearchMap();
          	return map;
          }
		};
	
		$('#tabGrid').loadGrid(options);
	
//	var LogGrid = $.extend({},BaseJqGrid,{
//		resizeHandle:"#searchBox",
//		pager: "pager", //分页工具栏
//		multiselect : false, // 是否支持多选
//	    url: 'getLogsList.zf', //这是Action的请求地址  
//	    colNames: ['ID','业务模块','操作类型','操作用户','操作日期','IP地址','主机名','操作描述'],
//		colModel:[
//		     {name:'czbh', index:'czbh', key:true, hidden:true},
//		     {name:'czmk', index: 'czmk',align:'left',width:70},
//		     {name:'czlx', index: 'czlx',align:'left',width:70},
//	      	 {name:'czr', index: 'czr',align:'left',width:220},
//		     {name:'czrq', index: 'czrq',align:'left',width:150},
//		     {name:'ipdz', index: 'ipdz',align:'left',width:80},
//		     {name:'zjm', index: 'zjm',align:'left',width:80},
//		     {name:'czms', index: 'czms',align:'left',width:550}
//		     
//		],
//		sortname: 'czrq', //首次加载要进行排序的字段 
//	 	sortorder: "desc"
//	});
//	
//	$("#tabGrid").loadJqGrid(LogGrid);
	
	/*====================================================绑定按钮事件====================================================*/
	
	//绑定增加点击事件
	jQuery("#btn_ck").click(function () {
		var ids = $('#tabGrid').getKeys();
		if(ids.length != 1){
			$.alert('请选择一条您要查看的记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		var url ="ckRzxx.zf?czbh="+row.czbh;
		$.showDialog(url,'查看',$.extend(viewConfig,{"width":"700px"}));
		 
	});
	$(":button[name=search_button]").bind("click",searchResult);
});

function ck(czbh){
	var url = "ckRzxx.zf?czbh="+czbh;
	$.showDialog(url,'查看',$.extend(viewConfig,{"width":"700px"}));
}
//查询
function searchResult(){
//	if(jQuery('#czkssj').val()!='' && jQuery('#czjssj').val()!=''){
//		var czkssj_date=new Date((jQuery("#czkssj").val()).replace(/-/g,"/"));
//		var czjssj_date=new Date((jQuery("#czjssj").val()).replace(/-/g,"/"));
//		if(czkssj_date>czjssj_date){
//			alert("开始时间不能大于结束时间！");
//			return false;
//		}
//	}
	$('#tabGrid').refreshGrid();
}

//回车键查询
//$('#searchBox input[name="czmk"],#searchBox input[name="czr"]').bind("keydown", "return", function (ev) {
//	searchResult()   
//})
