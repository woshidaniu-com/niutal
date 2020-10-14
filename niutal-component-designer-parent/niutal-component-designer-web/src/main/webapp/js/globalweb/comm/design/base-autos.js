jQuery(function($){
	
	$.validator.addMethod("fieldType", function(value, element, param) {
		return this.optional(element) || (/^[a-zA-Z]+([a-zA-Z0-9_]*)$/.test(value)) ;
	}, "只能输入数字、字母或者它们的组合,且必须以字母开头");
	
	
	var xtczmsGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#searchResult",
		pager: "pager", //分页工具栏  
	    url: _path+'/design/baseAuto_cxAutoCompleteFieldIndex.html?doType=query', //这是Action的请求地址  
	    //shrinkToFit: true,
	    minHeight:200,
	    shrinkToFit	: false,
	    colModel:[
		     {label:'ID',name:'auto_guid', index:'auto_guid', key:true, hidden:true},
		     {label:'字段标题',name:'field_text', index: 'field_text',align:'center',width:"120px",resizeable:false,sortable : false},
			 {label:'字段名称',name:'field_name', index: 'field_name',align:'center',width:"80px",resizeable:false,sortable : false},
			 {label:'触发事件需要文字数量',name:'field_minLength', index: 'field_minLength',align:'center',width:"150px",resizeable:false,sortable : false},
		     {label:'查询记录数',name:'field_items', index: 'field_items',align:'center',width:"100px",resizeable:false,sortable : false},
		     {label:'触发延时时间（毫秒）',name:'field_delay', index: 'field_delay',align:'center',width:"150px",resizeable:false,sortable : false},
		     {label:'设置到文本框的值',name:'field_setvalue', index: 'field_setvalue',align:'center',width:"150px",resizeable:false,sortable : false,
		    	 formatter : 'select',editoptions : {value : {'1':'key','2':'value'}}
		     },
		     {label:'真实取值',name:'field_realvalue', index: 'field_realvalue',align:'center',width:"150px",resizeable:false,sortable : false,
		    	 formatter : 'select',editoptions : {value : {'1':'key','2':'value'}}
		     },
		     {label:'元素显示格式',name:'field_format', index: 'field_format',align:'center',width:"150px",resizeable:false,sortable : false},
		     {label:'数据查询路径',name:'field_action', index: 'field_action',align:'center',width:"210px",resizeable:false,sortable : false}
		],
		/*向服务器发起请求时会把数据进行序列化，用户自定义数据也可以被提交到服务器端*/
		serializeGridData: function(postData){
			$.extend(postData,{
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
		$.showDialog(_path+"/design/baseAuto_zjAutoCompleteField.html",'增加自动完成字段信息',$.extend({},modifyConfig,{
			"width": "900px"
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
		$.showDialog(_path+"/design/baseAuto_xgAutoCompleteField.html?auto_guid="+row.auto_guid,'修改自动完成字段信息',$.extend({},modifyConfig,{
			"width": "900px"
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
					jQuery.post( _path + "/design/baseAuto_scAutoCompleteFieldData.html",{"ids":keys.join(",")},function(responseText){
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
