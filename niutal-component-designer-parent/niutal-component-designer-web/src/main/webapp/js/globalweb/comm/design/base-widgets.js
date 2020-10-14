jQuery(function($){
	
	$.validator.addMethod("fieldType", function(value, element, param) {
		return this.optional(element) || (/^[a-zA-Z]+([a-zA-Z0-9_]*)$/.test(value)) ;
	}, "只能输入数字、字母或者它们的组合,且必须以字母开头");
	
	
	var xtczmsGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#searchResult",
		pager: "pager", //分页工具栏  
	    url: _path+'/design/baseWidget_cxWidgetDetailIndex.html?doType=query', //这是Action的请求地址  
	    //shrinkToFit: true,
	    minHeight:200,
	    shrinkToFit	: true,
	    colModel:[
		     {label:'ID',name:'widget_guid', index:'widget_guid', key:true, hidden:true},
		     {label:'功能组件名称',name:'widget_name', index: 'widget_name',align:'center',width:"150px",resizeable:false,sortable : false},
			 {label:'功能组件描述',name:'widget_desc', index: 'widget_desc',align:'left',width:"350px",resizeable:false,sortable : false},
		     {label:'是否引用资源',name:'widget_hasResource', index: 'widget_hasResource',align:'center',width:"100px",resizeable:false,sortable : false,
		    	 formatter : 'select',editoptions : {value : {'1':'是','0':'否'}}
		     },
		     {label:'是否有初始参数',name:'widget_hasParameter', index: 'widget_hasParameter',align:'center',width:"100px",resizeable:false,sortable : false,
		    	 formatter : 'select',editoptions : {value : {'1':'是','0':'否'}}
		     },
		     {label:'是否被引用',name:'widget_used', index: 'widget_used',align:'center',width:"100px",resizeable:false,sortable : false,
		    	 formatter : 'select',editoptions : {value : {'1':'是','0':'否'}}
		     }
		],
		/*向服务器发起请求时会把数据进行序列化，用户自定义数据也可以被提交到服务器端*/
		serializeGridData: function(postData){
			$.extend(postData,{
				"widget_name"	: jQuery('#widget_name_query').val()
			});
			return postData;
		},
		sortname: 'widget_name', //首次加载要进行排序的字段 
	 	sortorder: "asc"
	});
	
	$("#tabGrid").loadJqGrid(xtczmsGrid);

	/*====================================================绑定按钮事件====================================================*/
	
	//增加
	jQuery("#btn_zj").click(function () {
		//业务判断
		$.showDialog(_path+"/design/baseWidget_zjWidgetDetail.html",'增加功能组件信息',$.extend({},modifyConfig,{
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
		$.showDialog(_path+"/design/baseWidget_xgWidgetDetail.html?widget_guid="+row.widget_guid,'修改功能组件信息',$.extend({},modifyConfig,{
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
					jQuery.post( _path + "/design/baseWidget_scWidgetDetailData.html",{"ids":keys.join(",")},function(responseText){
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
