jQuery(function($){
	
	var TempGrid1 = $.extend(true,{},BaseJqGrid,{  
		pager: "#pager1", // 分页工具栏
		resizeHandle:"#searchForm",
		autowidth: true,
	    shrinkToFit: true,
	    multiselect : false,
	    rownumbers:false,
	    url: _path+'/xtgl/xwck_cxMoreXwList.html?doType=query', // 这是Action的请求地址
	    colModel:[
		  	        {label:'新闻编号',name:'xwbh',index: 'xwbh',key:true,hidden:true,align:'center'},
		  	        {label:'新闻标题',name:'xwbt',index: 'xwbt' ,width:'550px',
		  	        	formatter:function(cellvalue, options, rowObject){
		  	        		var html = [];
		  	        		
		  	        		if(rowObject.fbdz !='' && rowObject.fbdz !=null){
		  	        			html.push('<a href="'+rowObject.fbdz+'" class="clj" target="_blank">');
		  	        		}else{
		  	        			html.push('<a href="' + _path + '/xtgl/xwck_ckXw.html?xwbh='+rowObject.xwbh+'&doType=save" class="clj"  target="_blank">');
		  	        		}
		  	        		html.push('<span class="title">');
	  	        			if(rowObject.sfzd == "1"){
		  	        			html.push('<font color="red">【置顶】</font>');
		  	        		}
	  	        			html.push(cellvalue);
	  	        			html.push('</span>');
		  	        		if(rowObject.sfyd == "0"){
		  	        			html.push('<i class="index_png new">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</i>');
		  	        		}
		  	        		html.push('</a>');
		  	        		return html.join("");
			        }},
		  	      	{label:'发布单位/人',name:'fbr',index: 'fbr',width:'300px'},
			        {label:'发布时间',name:'fbsj', width:'200px',index: 'fbsj',align:'center'}
		],	
		sortname: 'fbsj', // 首次加载要进行排序的字段
     	sortorder: "desc"
	});
	
	$("#tabGrid1").loadJqGrid(TempGrid1);
	
	$('#myTab a').click(function (e) {
		  $(this).tab('show');
	});
	
	
	 $("#xwbt").bind('keydown', 'return',function (evt){
		 var map = {};
			map["xwbt"] = $(this).val();
			search("tabGrid1",map);
		 return false; 
	 }).val("");
	
	
});	
	

