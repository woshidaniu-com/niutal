 
jQuery(function($) {

	var resourceGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#resource_container",
	    url: _path + "/design/designResource_cxFuncResourceIndex.html?doType=query", //这是Action的请求地址  
	    shrinkToFit: true,
	    rownumbers	: true, 
	    multiselect : false, 
	    minHeight	: 150,
	    rownumWidth	: 30,
	    colModel:[
		     {label:'ID',name:'resource_guid', index:'resource_guid', key:true, hidden:true},
		     {label:'ID_Text',name:'resource_guid_text', index: 'resource_guid',hidden:true,resizeable:false,sortable : false,
				 formatter:function (cellvalue, options, rowObject){
					return '<input type="text" class="form-control input-sm width-100" name="resourceList[0].resource_guid"  validate="{required:true,stringMaxLength:300}" value="'+(rowObject["resource_guid"]||"")+'"/>';
				}
			 },
		     {label:'资源名称',name:'resource_name', index: 'resource_name',align:'center',width:"100px",resizeable:false,sortable : false},
			 {label:'auto_insert',name:'auto_insert', index: 'auto_insert', hidden:true},
			 {label:'是否手动引用',name:'auto_insert_text', index: 'auto_insert',align:'center',width:"80px",resizeable:false,sortable : false,
				 formatter:function (cellvalue, options, rowObject){
					return rowObject["auto_insert"] == "1" ? "程序关联" : "手动引入";
				}
			 },
		     {label:'操作',name:'czms', index: 'czms',align:'center',width:"60px",resizeable:false,sortable : false,
				formatter:function (cellvalue, options, rowObject){
				 var html1 = [];	
				 	if(rowObject["auto_insert"] != "1" ){
			    	 	html1.push('<div class="btn-group">');
					 		html1.push('<button type="button" class="btn btn-default btn-xs btn-remove" role="button" data-resource_guid="'+rowObject["resource_guid"]+'">移除</button>');
				 		html1.push('</div> ');
				 	}
				 	return html1.join("");
				}
			 }
		],
		/* 当插入每行时触发。rowid插入当前行的id；rowdata插入行的数据，格式为name: value，name为colModel中的名字*/
		loadComplete 	: function(xhr){
			//循环行
			$("#tabGrid_resource").resetIndex();
			$("#tabGrid_resource").clearTitle();
			$("#tabGrid_resource").resetOrdinal(function(i,tr){
				$(tr).find("input.field_ordinal").val(i + 1);
			});
		},
		/*向服务器发起请求时会把数据进行序列化，用户自定义数据也可以被提交到服务器端*/
		serializeGridData: function(postData){
			$.extend(postData,{
				"func_guid"	: jQuery('#func_guid').val()
			});
			return postData;
		}
	});
	
	$("#tabGrid_resource").loadJqGrid(resourceGrid);
	
	
	/*$("#widget_sql").on("paste",function(){  
    	alert('paste behaviour detected!');  
    }).on( "copy" , function(){  
		alert('copy behaviour detected!');  
    }).on ("cut" , function(){  
    	alert('cut behaviour detected!');  
    });  
	*/
	//监听移除按钮点击事件
	$(document).off("click","#old_pane button.btn-remove ").on("click","#old_pane button.btn-remove ",function(event){
		event.stopImmediatePropagation(); 
		var resource_guid = $(this).data("resource_guid");
		if($.founded(resource_guid)){
			$('#ajaxForm').clearValid();
			$("#tabGrid_resource").jqGrid("delRowData",resource_guid);
			resetOrdinal();
		}
	});
	
	$('#ajaxForm').validateForm({
		//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			//返回false阻止提交
			return true;
		}
	});
	
});

