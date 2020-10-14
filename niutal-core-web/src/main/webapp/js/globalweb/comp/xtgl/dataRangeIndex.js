var SjfwGrid  = Class.create(BaseJqGrid,{  
	caption : "用户角色数据范围列表",
	pager: "pager", //分页工具栏  
	height : "auto",
	rowNum : 15, // 每页显示记录数
	rowList : [15,30,50, 100,500], // 可调整每页显示的记录数
    url: _path + '/xtgl/yhsjfwgl_cxSjfwsz.html', //这是Action的请求地址  
    autowidth : false,
    multiselect : true, // 是否支持多选
    colModel:[
         {label:'所属机构',name:'jgmc', index: 'jgmc',align:'left',width:'120px'},
    	 {label:'职工号',name:'zgh', index: 'zgh',align:'center',width:'80px'},
	     {label:'姓 名',name:'xm', index: 'xm',align:'center',width:'100px'},
	     {label:'角色名称',name:'jsmc', index: 'jsmc',align:'center',width:'100px'},
         {label:'已有数据范围',name:'sjfw', index: 'sjfw',align:'left',width:'346px',
	    	 formatter:function(cellvalue, options, rowObject){
		    	 if(cellvalue&&cellvalue!='null'){
		    		 var sjfwzmc_arr = cellvalue.split(";");
		    		 if(sjfwzmc_arr.length>0){
		    			 var html = [];
			    		 html.push('<ul class="lisnavBox" id="lisnavBox">');
			    		 jQuery.each(sjfwzmc_arr,function(index,sjfwzmc){
			    			 html.push('<li style="text-align: left;border-bottom: 1px solid #ccc;">');
				    		 html.push('<p>'+sjfwzmc+'</p>');
				    		 html.push("</li>");
			    		 });
			    		 html.push("</ul>");
			    		 return html.join("");
		    		 }else{
		    			 return cellvalue;
		    		 }
		    	 }else{
	    			 return "无";
		    	 }
	  	 	}
         }
	],
	sortname: 'jgdm,jsdm,zgh', //首次加载要进行排序的字段 
 	sortorder: "desc",
 	gridComplete:function(){
		jQuery("ul.lisnavBox").each(function(index,ul){
			jQuery(ul).find("li:last").css("border-bottom","0");
		});
		return true;
	}
});

function searchResult(){
	var map = {};
	map["jsdm"] = jQuery('#jsdm').val();
	map["jgdm"] = jQuery('#jgdm').val();
	search('tabGrid',map);
}

function selectJsdm(){
	var map = {};
	map["jsdm"] = jQuery('#jsdm').val();
	search('tabGrid',map);
}

function selectJgdm(){
	var map = {};
	map["jgdm"] = jQuery('#jgdm').val();
	search('tabGrid',map);
}

//数据范围设置
function sjfwsz(){
	var jsdm = jQuery('#jsdm').val();
	if (jsdm == '') {
		alert('请选择角色！');
		return false;
	}
	var ids = getChecked();
	if (ids.length < 1) {
		alert('请选定用户记录!');
		return false;
	}
	var params = [];
	jQuery.each(ids, function(index, id) {
		var row = jQuery("#tabGrid").jqGrid('getRowData', id);
		params.push("zghList[" + index + "]=" + row.zgh);
	});
	params.push("js_id=" + jsdm);
	var url = 'yhsjfwgl_cxSjfwszSetting.html?' + params.join("&");
	// 批量数据授权
	showDialog('设置数据授权', 950, 600, url, {
		close : function() {
			refershGrid("tabGrid");
			return true;
		}
	});
};