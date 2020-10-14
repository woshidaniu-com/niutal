jQuery(function($){
	
	$.validator.addMethod("fieldType", function(value, element, param) {
		return this.optional(element) || (/^[a-zA-Z]+([a-zA-Z0-9_]*)$/.test(value)) ;
	}, "只能输入数字、字母或者它们的组合,且必须以字母开头");
	
	
	var xtczmsGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#searchResult",
		pager: "pager", //分页工具栏  
	    url: _path+'/design/baseField_cxQueryFieldBaseIndex.html?doType=query', //这是Action的请求地址  
	    //shrinkToFit: true,
	    minHeight:200,
	    shrinkToFit	: false,
	    colModel:[
		     {label:'ID',name:'field_guid', index:'field_guid', key:true, hidden:true},
		     /*{label:'操作',name:'czms', index: 'czms',align:'center',width:"60px",resizeable:false,sortable : false,
				formatter:function (cellvalue, options, rowObject){
					return '<a onclick="cxFieldInfo(\''+rowObject["field_guid"]+'\')" href="#" class="blue"><i title="" data-placement="top" data-toggle="tooltip" class="icon-zoom-in bigger-120 blue" data-original-title="详情"></i></a>';
				}
			 },*/
			 {label:'字段使用范围',name:'field_scope', index: 'field_scope',align:'center',width:"150px",resizeable:false,sortable : false},
			 {label:'字段标题',name:'field_text', index: 'field_text',align:'center',width:"150px",resizeable:false,sortable : false},
		     {label:'字段ID',name:'field_id', index: 'field_id',align:'center',width:"120px",resizeable:false,sortable : false},
		     {label:'字段名称',name:'field_name', index: 'field_name',align:'center',width:"120px",resizeable:false,sortable : false},
		     {label:'字段数据取值索引',name:'field_list', index: 'field_list',align:'center',width:"150px",resizeable:false,sortable : false},
		     {label:'字段key取值索引',name:'field_listKey', index: 'field_listKey',align:'center',width:"150px",resizeable:false,sortable : false},
		     {label:'字段value取值索引',name:'field_listValue', index: 'field_listValue',align:'center',width:"150px",resizeable:false,sortable : false},
		     {label:'字段值来源',name:'field_source_type', index: 'field_source_type',align:'center',width:"150px",resizeable:false,sortable : false,
		    	 formatter : 'select',editoptions : {value : "APP:程序设置,SQL:数据库,XML:XML数据,Spring:Spring集合对象,Fixed:固定值"}
		     }
		],
		/*向服务器发起请求时会把数据进行序列化，用户自定义数据也可以被提交到服务器端*/
		serializeGridData: function(postData){
			$.extend(postData,{
				"field_scope"	: jQuery('#field_scope_query').val(),
				"field_name"	: jQuery('#field_name_query').val(),
				"field_text"	: jQuery('#field_text_query').val()
			});
			return postData;
		},
		sortname: 'field_name', //首次加载要进行排序的字段 
	 	sortorder: "asc"
	});
	
	$("#tabGrid").loadJqGrid(xtczmsGrid);

	/*====================================================绑定按钮事件====================================================*/
	
	//增加
	jQuery("#btn_zj").click(function () {
		//业务判断
		$.showDialog(_path+"/design/baseField_zjBaseQueryField.html",'增加基础字段信息',$.extend({},modifyConfig,{
			"width": ($("#yhgnPage").innerWidth() - 100) +"px"
		}));
	});
	
	//修改
	jQuery("#btn_xg").click(function () {
		var keys = $("#tabGrid").getKeys();
		if(keys.length != 1){
			$.alert('请先选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', keys[0]);
		//业务判断
		$.showDialog(_path+"/design/baseField_xgBaseQueryField.html?field_guid="+row.field_guid,'修改基础字段信息',$.extend({},modifyConfig,{
			"width": ($("#yhgnPage").innerWidth() - 100) +"px"
		}));
	});
	
	//删除
	jQuery("#btn_sc").click(function () {
		var keys = $("#tabGrid").getKeys();
		if (keys.length == 0){
			$.alert('请选择您要删除的记录！');
			return;
		} else {
			$.confirm('您确定要删除选择的记录吗？',function(result){
				if(result){
					jQuery.ajaxSetup({async:false});
					jQuery.post( _path + "/design/baseField_scBaseQueryFieldData.html",{"ids":keys.join(",")},function(responseText){
						setTimeout(function(){
							if(responseText.indexOf("成功") > -1){
								$.success(responseText,function() {
									if($("#tabGrid").size() > 0){
										$("#tabGrid").trigger('reloadGrid');
									}
								});
							}else if(responseText.indexOf("失败") > -1){
								$.error(responseText);
							} else{
								$.alert(responseText);
							}
						},10);
					},'json');
					jQuery.ajaxSetup({async:true});
				}
			});
		}
	});
	
	
	jQuery("#search_go").click(function () {
		$("#tabGrid").trigger('reloadGrid');
	});
	
	
});


function cxFieldInfo(field_guid){
	$.showDialog(_path+"/design/baseField_cxQueryFieldBase.html?field_guid="+field_guid,'基础字段信息详情',$.extend({},viewConfig,{
		"width": ($("#yhgnPage").innerWidth() - 100) +"px"
	}));
}
