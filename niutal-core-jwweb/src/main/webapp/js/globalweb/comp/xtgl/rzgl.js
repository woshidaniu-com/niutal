jQuery(function($){
	var LogGrid = $.extend({},BaseJqGrid,{
		resizeHandle:"#searchBox",
		pager: "pager", //分页工具栏
		multiselect : false, // 是否支持多选
	    url: _path+'/xtgl/rzgl_cxRz.html?doType=query', //这是Action的请求地址  
		colModel:[
		     {label:'主键',name:'czbh', index:'czbh', key:true, hidden:true},
		     {label:'详情',name:'', index: '',align:'center',width:"100px",
		    	 formatter:function(cellvalue, options, rowObject){
		    	   var html = [];
				 	html.push('<a class="blue" href="#" onclick="ck(\''+rowObject["czbh"]+'\')"><i class="glyphicon glyphicon-zoom-in bigger-110 blue" data-toggle="tooltip" data-placement="top" title="查看"></i></a>');
					return html.join("");
				}
		     },
		     {label:'业务模块',name:'czmk', index: 'czmk',align:'left',width:160},
		     {label:'操作类型',name:'czlx', index: 'czlx',align:'left',width:80},
	      	 {label:'操作用户',name:'yhm', index: 'yhm',align:'left',width:90},
		     {label:'操作日期',name:'czrq', index: 'czrq',align:'left',width:180},
		     {label:'IP地址',name:'ipdz', index: 'ipdz',align:'left',width:120},
		     {label:'主机名',name:'zjm', index: 'zjm',align:'left',width:120},
		     {label:'操作描述',name:'czms', index: 'czms',align:'left',width:460}
		     
		],
		sortname: 'czrq', //首次加载要进行排序的字段 
	 	sortorder: "desc"
	});
	
	$("#tabGrid").loadJqGrid(LogGrid);
	
	/*====================================================绑定按钮事件====================================================*/
	
	//绑定增加点击事件
	jQuery("#btn_ck").click(function () {
		var ids = getChecked();
		if(ids.length != 1){
			$.alert('请选择一条您要查看的记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', ids[0]);
		$.showDialog(_path+"/xtgl/rzgl_ckRzxx.html?czbh="+row.czbh,'查看',$.extend(viewConfig,{"width":"700px"}));
	});
	
	//绑定导出操作
	$("#btn_dc").click(function () {
		
		if(jQuery('#czkssj').val()!='' && jQuery('#czjssj').val()!=''){
			var czkssj_date=new Date((jQuery("#czkssj").val()).replace(/-/g,"/"));
			var czjssj_date=new Date((jQuery("#czjssj").val()).replace(/-/g,"/"));
			if(czkssj_date>czjssj_date){
				$.alert("开始时间不能大于结束时间！");
				return false;
			}
		}
		
		//导出处理编号 规则:JW_模块编号_业务名称 如：JW_N1006_BJXXWH
		var dcclbh = $("#dcclbh").val();
		//页面高级查询条件 若不是高级查询 则把查询条件组装成map传递
		//var requestMap = $("#searchBox").searchBox("getConditions");
		var requestMap = paramMap();
		//TempGrid必须是全局对象
		$.exportDialog(_path+'/xtgl/rzgl_cxRzList.html',dcclbh,requestMap ,LogGrid.colModel,'#tabGrid');
	});
});

function ck(czbh){
	$.showDialog( _path+"/xtgl/rzgl_ckRzxx.html?czbh="+czbh,'查看',$.extend(viewConfig,{"width":"700px"}));
}


//查询参数
function paramMap(){
	return {
		"yhm"	: jQuery('#czr').val(), 
		"czkssj": jQuery('#czkssj').val(), 
		"czjssj": jQuery('#czjssj').val(), 
		"czmk"	: jQuery('#czmk').val(), 
		"czlx"	: jQuery('#czlx').val()
	};
}

//查询
function searchResult(){
	if(jQuery('#czkssj').val()!='' && jQuery('#czjssj').val()!=''){
		var czkssj_date=new Date((jQuery("#czkssj").val()).replace(/-/g,"/"));
		var czjssj_date=new Date((jQuery("#czjssj").val()).replace(/-/g,"/"));
		if(czkssj_date>czjssj_date){
			$.alert("开始时间不能大于结束时间！");
			return false;
		}
	}
	search('tabGrid',paramMap());
}
