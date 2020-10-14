//初始化页面
jQuery(function($) {
	var YhjsGrid  = Class.create(BaseJqGrid,{  
		caption : "用户角色列表",
		//pager: "pager", //分页工具栏  
		rowNum : 2147483647, // 每页显示记录数
		//rowList : [15,30,50, 100,500], // 可调整每页显示的记录数
	    url: 'yhgl_cxYhjs.html?zgh='+jQuery("#zgh").val(), //这是Action的请求地址  
	    multiselect : true, // 是否支持多选
	    autowidth : false, // 自动调整宽度
	    colModel:[
		     {label:'角色代码',name:'jsdm', index: 'jsdm',align:'center',key:true,hidden:true},
		     {label:'职工号',name:'zgh', index: 'zgh',hidden:true},
		     {label:'角色名称',name:'jsmc', index: 'jsmc',align:'center',width: "110px"},
		     {label:'操作',name:'cz', index: 'cz',align:'center',width: "75px",
		    	formatter:function(cellvalue, options, rowObject){
		    	 	return '<a href="javascript:void(0);" class="sjsqBt" js_id="'+rowObject.jsdm+'">数据授权</a>';
		  	 	}
		     },
	         {label:'当前选择账号该角色已有数据范围',name:'sjfwzmc', index: 'sjfwzmc',align:'left',width: "550px",
		    	 formatter:function(cellvalue, options, rowObject){
			    	 if(cellvalue){
			    		 var sjfwzmc_arr = cellvalue.split(";");
			    		 var html = [];
			    		 html.push('<ul class="lisnavBox" id="lisnavBox">');
			    		 $.each(sjfwzmc_arr,function(index,sjfwzmc){
			    			 html.push('<li style="text-align: left;border-bottom: 1px solid #ccc;">');
				    		 html.push('<p>'+sjfwzmc+'</p>');
				    		 html.push("</li>");
			    		 });
			    		 html.push("</ul>");
			    		 return html.join("");
			    	 }
		  	 	   return cellvalue;
		  	 	}
	         }
		],
		shrinkToFit:false,
		sortname: 'jsdm', //首次加载要进行排序的字段 
	 	sortorder: "desc",
	 	gridComplete:function(){
		
			var jsdm_arr = jQuery("input[name=jsdm]");
			if(jsdm_arr.size()>0){
				var id_arr = jQuery("#tabGrid").getDataIDs();
				jQuery.each(id_arr,function(index,id){
					var row = jQuery("#tabGrid").jqGrid('getRowData', id);
					jQuery.each(jsdm_arr,function(index2,input){
						if(jQuery(input).val()==row.jsdm){
							jQuery("#tabGrid").setSelection(id);
						}
					});
				});
			}
			
			//绑定数据授权连接的click事件
			jQuery("a.sjsqBt").each(function(index,a){
				jQuery(a).click(function(){
					jQuery("#tabGrid").setSelection(jQuery(this).attr("js_id"));
					var params = [];
					params.push("js_id=" + jQuery(this).attr("js_id"));
					params.push("zghList[" + 0 + "]=" + jQuery("#zgh").val());
					var url =  'yhsjfwgl_cxSjfwszSetting.html?' + params.join("&");
					showDialog('设置数据授权', 920, 600, url, {
						close : function() {
							refershGrid("tabGrid");
							return true;
						}
					});
				});
			});
			
			jQuery("ul.lisnavBox").each(function(index,ul){
				jQuery(ul).find("li:last").css("border-bottom","0");
			});
			
			return true;
		}
	});
	
	var yhjsGrid = new YhjsGrid();
	loadJqGrid("#tabGrid", "#pager", yhjsGrid);
});

//保存
function saveForm() {
	var ids = getChecked();
	if (ids.length > 0) {
		var map = [];
		map.push("zgh="+jQuery("#zgh").val());
		jQuery.each(ids,function(index,id){
			var row = jQuery("#tabGrid").jqGrid('getRowData', id);
			map.push("cbvjsxx=" + row.jsdm);
		});
		 //ajax请求
		jQuery.ajax({
			url:  _path + "/xtgl/yhgl_szssjsSaveYh.html", 	//访问服务器地址
			type: "post",
			dataType: 'html',                    			//返回类型
			data: map.join("&"),										
			async:false,									
			success:function(data){
				if(data){
					data = data.replace(new RegExp(/\"/g),'');
				}
				alert(data,'',{'clkFun':function(){iFClose();}});
			}
		});
		return false;
	} else {
		alert('请至少选择一个角色！');
		return false;
	}
}

//查看更多数据范围
function ckSj(jsdm) {
	var div_id = "id:" + jsdm;
	//openTipsWindown("已授权范围",div_id,"500","200","true","","true","id")
	alert(jQuery("#" + jsdm).html(), 500, 200, "查看更多数据范围")
}