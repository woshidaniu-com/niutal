jQuery(function($) {
	
	if($("#detail_pane").size() > 0 ){
		
		if($("#tabGrid_source").size() > 0){
			var sourceGrid = $.extend({},BaseJqGrid,{  
				resizeHandle:"#source_container",
			    url: _path + "/design/designQuery_cxSQLParserColumnIndex.html?doType=query", //这是Action的请求地址  
			    multiselect : true, 
			    shrinkToFit	: true,
			    minHeight	: 400,
			    /*
				 *每页显示记录数;用于设置Grid中一次显示的行数，默认值为15。
				 *正是这个选项将参数rows（prmNames中设置的）通过url选项设置的链接传递到Server。
				 *注意如果Server返回的数据行数超过了rowNum的设定，则Grid也只显示rowNum设定的行数。 
				 */
				rowNum 		: 1500, 
			    colModel:[
				     {label:'可选条件',name:'field_name', index: 'field_name',align:'left',width:"80px",key:true,resizeable:false,sortable : false},
				     {label:'条件名称',name:'field_comment', index: 'field_comment',align:'left',width:"100px",resizeable:false,sortable : false}
				],
				/*向服务器发起请求时会把数据进行序列化，用户自定义数据也可以被提交到服务器端*/
				serializeGridData: function(postData){
					$.extend(postData,{
						"element_guid"			: jQuery('#element_guid').val(),
						"query_guid"			: jQuery('#query_guid').val(),
						"report_guid"			: jQuery('#report_guid').val(),
						"element_related_guid"	: jQuery('#element_related_guid').val()
					});
					return postData;
				}
			});
			
			$("#tabGrid_source").loadJqGrid(sourceGrid);
			
			$("#btn_copy_row").killFocus().click(function(e){
				var table_guids   = [].concat($("#tabGrid_source").getKeys());
				$.each(table_guids,function(i,table_guid){
					var index = jQuery("#tabGrid_query").getGridParam("records") + 1;
					var field_row = $("#tabGrid_source").getRow(table_guid);
					jQuery("#tabGrid_query").jqGrid("addRowData","field" + index,{
						"table_guid"			: "field" + index,
						"field_guid"			: null,
						"field_text"			: (field_row.field_comment||""),
						"field_id"				: field_row.field_name,
						"field_name"			: field_row.field_name,
						"field_alias"			: "",
						"field_name_width"		: "4",
						"field_parent"			: "",
						"field_ordinal"			: index,
						"field_class"			: "",
						"field_attr"			: "",
						"field_value" 			: "",
						"field_mapper"			: "",
						"field_placeholder"	 	: "",
						"field_shape" 			: '2',
						"field_type" 			: '0',
						"field_chosen" 			: '1',
						"field_filtertype" 		: '2',
						"field_required" 		: '0',
						"field_autocomplete" 	: '1'
					},"last");
					$("#tabGrid_source").jqGrid("delRowData",table_guid);
				});
				$("#tabGrid_query").resetIndex();
		 		$("#tabGrid_query").clearTitle();
			});
		}
		 
		$("#btn_add_row").killFocus().click(function(e){
			var index = jQuery("#tabGrid_query").getGridParam("records") + 1;
			jQuery("#tabGrid_query").jqGrid("addRowData","field" + index,{
				"table_guid"			: "field" + index,
				"field_guid"			: null,
				"field_text"			: "",
				"field_id"				: "",
				"field_name"			: "",
				"field_alias"			: "",
				"field_name_width"		: "4",
				"field_parent"			: "",
				"field_ordinal"			: index,
				"field_class"			: "",
				"field_attr"			: "",
				"field_value" 			: "",
				"field_mapper"			: "",
				"field_placeholder"	 	: "",
				"field_shape" 			: '2',
				"field_type" 			: '0',
				"field_chosen" 			: '1',
				"field_filtertype" 		: '2',
				"field_required" 		: '0',
				"field_autocomplete" 	: '1'
			},"last");
			$("#tabGrid_query").resetIndex();
	 		$("#tabGrid_query").clearTitle();
		});
		
		var rowIndex = 0;
		var widgetGrid = $.extend({},BaseJqGrid,{  
			resizeHandle:"#jqgird_container",
		    url: _path + "/design/designQuery_cxFuncElementQueryIndex.html?doType=query", //这是Action的请求地址  
		    shrinkToFit	: true,
		    rownumbers	: true,
		    multiselect : false,
		    rownumWidth	: 30,
		    /*
			 *每页显示记录数;用于设置Grid中一次显示的行数，默认值为15。
			 *正是这个选项将参数rows（prmNames中设置的）通过url选项设置的链接传递到Server。
			 *注意如果Server返回的数据行数超过了rowNum的设定，则Grid也只显示rowNum设定的行数。 
			 */
			rowNum 		: 1500, 
		    colModel:[
			     {label:'ID',name:'table_guid', index:'table_guid', key:true, hidden:true},
			     {label:'ID_Text',name:'table_guid_text', index: 'table_guid',hidden:true,resizeable:false,sortable : false,
					 formatter:function (cellvalue, options, rowObject){
						return '<input type="text" class="form-control input-sm width-100" name="query_field_list[0].table_guid"  validate="{required:true,stringMaxLength:300}" value="'+(rowObject["table_guid"]||"")+'"/>';
					}
				 },
				 {label:'field_guid',name:'field_guid', index: 'field_guid', hidden:true},
				 {label:'关联基础字段',name:'field_guid_text', index: 'field_guid',align:'center',width:"110px",resizeable:false,sortable : false,
			    	 formatter:function (cellvalue, options, rowObject){
					 	var html1 = [];	
						 	html1.push('<div class="input-group input-group-sm" title="条件名称:作为页面元素前的文字标题使用" style="width: 120px;">');
								html1.push('<input type="hidden"  class="form-control input-sm" name="query_field_list[0].field_guid" value="'+(rowObject["field_guid"]||"")+'"/>');
								html1.push('<input type="text" style="padding: 5px !important;width: 50px;"  disabled="disabled" class="disabled form-control input-sm '+($.founded( rowObject["field_guid"]) ?  "green" : "red")+'" name="query_field_list[0].field_guid_text" value="'+($.founded( rowObject["field_guid"]) ?  "已关联" : "无关联")+'"/>');
								html1.push('<div class="input-group-btn input-group-btn-sm">');
									html1.push('<button style="padding: 5px;" class="btn btn-default btn-setting" type="button" data-table_guid="'+rowObject.table_guid+'">绑定</button>');
									html1.push('<button style="padding: 5px;" class="btn btn-default btn-clear" type="button" data-table_guid="'+rowObject.table_guid+'">解除</button>');
								html1.push('</div>');
							html1.push('</div>');
						return html1.join("");
					}
			     },
			     {label:'field_text',name:'field_text', index: 'field_text', hidden:true},
			     {label:'条件简称',name:'field_text_text', index: 'field_text',align:'center',width:"70px",resizeable:false,sortable : false,
					 formatter:function (cellvalue, options, rowObject){
					 	var html1 = [];	
						 	html1.push('<span title="条件名称:作为页面元素前的文字标题使用" >');
								html1.push('<input data-target="field_text" data-table_guid="'+rowObject.table_guid+'" type="text" class="form-control input-sm width-100 auto-update"  name="query_field_list[0].field_text" validate="{required:true,stringMaxLength:50}" value="'+(rowObject["field_text"]||"")+'"/>');
							html1.push('</span>');
						
						return html1.join("");
					}
				 },
				 {label:'field_id',name:'field_id', index: 'field_id', hidden:true},
				 {label:'字段ID',name:'field_id_text', index: 'field_id',align:'center',width:"70px",resizeable:false,sortable : false,
					 formatter:function (cellvalue, options, rowObject){
					 	var html1 = [];	
						 	html1.push('<span class="width-100" title="字段ID，作为页面元素ID使用" >');
								html1.push('<input data-target="field_id" data-table_guid="'+rowObject.table_guid+'" type="text" class="form-control input-sm width-100 auto-update"  name="query_field_list[0].field_id" validate="{required:true,stringMaxLength:50}" value="'+(rowObject["field_id"]||"")+'"/>');
							html1.push('</span>');
						
						return html1.join("");
					}
				 },
				 {label:'field_name',name:'field_name', index: 'field_name', hidden:true},
				 {label:'字段名称',name:'field_name_text', index: 'field_name',align:'center',width:"70px",resizeable:false,sortable : false,
					 formatter:function (cellvalue, options, rowObject){
					 	var html1 = [];	
						 	html1.push('<span class="width-100" title="字段名称:作为页面元素Name使用" >');
								html1.push('<input data-target="field_name" data-table_guid="'+rowObject.table_guid+'"  type="text" class="form-control input-sm width-100 auto-update"  name="query_field_list[0].field_name" validate="{required:true,stringMaxLength:50}" value="'+(rowObject["field_name"]||"")+'"/>');
							html1.push('</span>');
						
						return html1.join("");
					}
				 },
				 {label:'field_alias',name:'field_alias', index: 'field_alias', hidden:true},
				 {label:'字段别名',name:'field_alias_text', index: 'field_alias',align:'center',width:"70px",resizeable:false,sortable : false,
					 formatter:function (cellvalue, options, rowObject){
					 	var html1 = [];	
						 	html1.push('<span class="width-100" title="字段别名：在字段生成查询条件SQL时用作别名" >');
								html1.push('<input data-target="field_alias" data-table_guid="'+rowObject.table_guid+'" type="text" class="form-control input-sm width-100 auto-update" name="query_field_list[0].field_alias" validate="{stringMaxLength:50}" value="'+(rowObject["field_alias"]||"")+'"/>');
							html1.push('</span>');
						return html1.join("");
					}
				 },
				 {label:'field_name_width',name:'field_name_width', index: 'field_name_width', hidden:true},
				 {label:'简称宽度比',name:'field_name_width_text', index: 'field_name_width',align:'center',width:"60px",resizeable:false,sortable : false,
			    	 formatter:function (cellvalue, options, rowObject){
						var html1 = [];	
						 	html1.push('<span title="字段名称元素占比：总数12，可选[1-12]的整数" >');
						 		html1.push('<input data-target="field_name_width"  data-table_guid="'+rowObject.table_guid+'" type="text" data-toggle="float" data-fixed="0"  class="form-control input-sm width-100 auto-update" name="query_field_list[0].field_name_width" validate="{required:true,PositiveInteger:true,stringMaxLength:2}" value="'+(rowObject["field_name_width"]||"")+'"/>');
							html1.push('</span>');
						return html1.join("");
					}
			     },
				 {label:'field_parent',name:'field_parent', index: 'field_parent', hidden:true},
				 {label:'父级字段ID',name:'field_parent_text', index: 'field_parent',align:'center',width:"80px",resizeable:false,sortable : false,
					 formatter:function (cellvalue, options, rowObject){
					 	var html1 = [];	
						 	html1.push('<span class="width-100" title="功能对应字段级联父级索引，如果有多个以","分割" >');
								html1.push('<input data-target="field_parent"  data-table_guid="'+rowObject.table_guid+'" type="text" class="form-control input-sm width-100 auto-update" name="query_field_list[0].field_parent" validate="{stringMaxLength:50}" value="'+(rowObject["field_parent"]||"")+'"/>');
							html1.push('</span>');
						
						return html1.join("");
					}
				 },
			     {label:'field_ordinal',name:'field_ordinal', index: 'field_ordinal', hidden:true},
			     {label:'显示顺序',name:'field_ordinal_text', index: 'field_ordinal',align:'center',width:"50px",resizeable:false,sortable : false,
					 formatter:function (cellvalue, options, rowObject){
						return '<span><input data-target="field_ordinal"  data-table_guid="'+rowObject.table_guid+'" type="text" class="form-control input-sm field_ordinal auto-update"  name="query_field_list[0].field_ordinal" validate="{required:true}" value="'+(rowObject["field_ordinal"]||"")+'"/></span>';
					}
				 },
				 {label:'field_shape',name:'field_shape', index: 'field_shape', hidden:true},
			     {label:'元素形态',name:'field_shape_text', index: 'field_shape',align:'center',width:"60px",resizeable:false,sortable : false,
			    	 formatter:function (cellvalue, options, rowObject){
			    	 	var value = $.defined( rowObject["field_shape"]) ?  rowObject["field_shape"] : "2";
				    	var html1 = [];	
				    		html1.push('<span class="width-100" title="字段展示形态;默认:下拉框,可选值 ：1：select(下拉框),2：input(文本框),3：textarea(文本域)" >');
							 	html1.push('<select data-target="field_shape"  data-table_guid="'+rowObject.table_guid+'" name="query_field_list[0].field_shape" class="form-control input-sm width-100 auto-update">');
							 	if($.founded( rowObject["field_guid"])){
							 		html1.push('<option value="1" selected="selected">下拉框</option>');
								 	html1.push('<option value="2">文本框</option>');
								 	html1.push('<option value="3">文本域</option>');
							 	}else{
							 		html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>下拉框</option>');
								 	html1.push('<option value="2" '+( value == '2' ? ' selected="selected" ' : "")+'>文本框</option>');
								 	html1.push('<option value="3" '+( value == '3' ? ' selected="selected" ' : "")+'>文本域</option>');
							 	}
							 	html1.push('</select>');
						 	html1.push('</span>');
						return html1.join("");
					}
			     },
			     {label:'field_type',name:'field_type', index: 'field_type', hidden:true},
			     {label:'元素类型',name:'field_type_text', index: 'field_type',align:'center',width:"60px",resizeable:false,sortable : false, 
			    	 formatter:function (cellvalue, options, rowObject){
			    	 	var value = $.defined( rowObject["field_type"]) ?  rowObject["field_type"] : "0";
				    	var html1 = [];	
				    		html1.push('<span class="width-100" title="字段元素类型：字段作为input元素时的类型;可选值 ：1：text(文本输入框),2：radio(单选框),3：checkbox(复选框),4：hidden(隐藏输入框)" >');
							 	html1.push('<select data-target="field_type"  data-table_guid="'+rowObject.table_guid+'" name="query_field_list[0].field_type" class="form-control input-sm width-100 auto-update" >');
							 	html1.push('<option value="" '+( value == '0' ? ' selected="selected" ' : "")+'></option>');
							 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>文本输入框</option>');
							 	html1.push('<option value="2" '+( value == '2' ? ' selected="selected" ' : "")+'>单选框</option>');
							 	html1.push('<option value="3" '+( value == '3' ? ' selected="selected" ' : "")+'>复选框</option>');
							 	html1.push('<option value="4" '+( value == '4' ? ' selected="selected" ' : "")+'>隐藏输入框</option>');
							 	html1.push('</select>');
						 	html1.push('</span>');
						return html1.join("");
					}
			     },
			     {label:'field_filtertype',name:'field_filtertype', index: 'field_filtertype', hidden:true},
			     {label:'筛选类型',name:'field_filtertype_text', index: 'field_filtertype',align:'center',width:"60px",resizeable:false,sortable : false, 
			    	 formatter:function (cellvalue, options, rowObject){
			    	 	var value = $.defined( rowObject["field_filtertype"]) ?  rowObject["field_filtertype"] : "1";
				    	var html1 = [];	
				    		html1.push('<span class="width-100" title="功能对应字段作为筛选条件时的筛选类型：默认：等值筛选，可选：1：等值筛选;2:全模糊筛选;3:前缀模糊筛选;4:后缀模糊筛选" >');
							 	html1.push('<select data-target="field_filtertype"  data-table_guid="'+rowObject.table_guid+'" name="query_field_list[0].field_filtertype" class="form-control input-sm width-100 auto-update" >');
							 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>等值筛选</option>');
							 	html1.push('<option value="2" '+( value == '2' ? ' selected="selected" ' : "")+'>全模糊筛选</option>');
							 	html1.push('<option value="3" '+( value == '3' ? ' selected="selected" ' : "")+'>前缀模糊筛选</option>');
							 	html1.push('<option value="4" '+( value == '4' ? ' selected="selected" ' : "")+'>后缀模糊筛选</option>');
							 	html1.push('</select>');
						 	html1.push('</span>');
						return html1.join("");
					}
			     },
			     {label:'操作',name:'czms', index: 'czms',align:'center',width:"70px",resizeable:false,sortable : false,
					formatter:function (cellvalue, options, rowObject){
			    	 	var html1 = [];	
				    	 	html1.push('<div class="btn-group">');
						 		html1.push('<button type="button" class="btn btn-default btn-xs btn-remove" role="button" data-table_guid="'+rowObject.table_guid+'">移除</button>');
						 		html1.push('<button type="button" class="btn btn-default btn-xs btn-detail" role="button" data-table_guid="'+rowObject.table_guid+'">更多</button>');
					 		html1.push('</div> ');
					 		return html1.join("");
					}
				 },
				 //以下为扩展参数
				 {label:'field_class',name:'field_class', index: 'field_class', hidden:true},
			     {label:'扩展样式名称',name:'field_class_text', index: 'field_class',align:'center',width:"100px",resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
						return '<input type="text" readonly="readonly" class="form-control input-sm width-100" name="query_field_list[0].field_class" validate="{stringMaxLength:100}"  value="'+(rowObject["field_class"]||"")+'"/>';
					}
			     },
			     {label:'field_attr',name:'field_attr', index: 'field_attr', hidden:true},
			     {label:'自定义属性',name:'field_attr_text', index: 'field_attr',resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
						return '<input type="text" readonly="readonly" class="form-control input-sm width-100" name="query_field_list[0].field_attr" validate="{stringMaxLength:100}"  value="'+(rowObject["field_attr"]||"")+'"/>';
					}
			     },
			     {label:'field_value',name:'field_value', index: 'field_value', hidden:true},
			     {label:'默认值',name:'field_value_text', index: 'field_value',resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
			    	 	//	功能对应默认值:可以是固定的值，也可以是OGNL表达式，方便从struts上下文获取，如：#{dqxn}
						return '<input type="text" readonly="readonly" class="form-control input-sm width-100" name="query_field_list[0].field_value" validate="{stringMaxLength:50}"  value="'+(rowObject["field_value"]||"")+'"/>';
					}
			     },
			     {label:'field_mapper',name:'field_mapper', index: 'field_mapper', hidden:true},
			     {label:'字段映射名称',name:'field_mapper_text', index: 'field_mapper',resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
			    	 	//功能对应字段名称的映射名称:在作为其他字段父级条件，【name属性值】与级联字段父级条件【参数名称】不统一时作为参数名称使用
						return '<input type="text" readonly="readonly" class="form-control input-sm width-100" name="query_field_list[0].field_mapper" validate="{stringMaxLength:50}"  value="'+(rowObject["field_mapper"]||"")+'"/>';
					}
			     },
				 {label:'field_placeholder',name:'field_placeholder', index: 'field_placeholder', hidden:true},
			     {label:'提示信息',name:'field_placeholder_text', index: 'field_placeholder',align:'center',width:"100px",resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
						return '<input type="text" readonly="readonly" class="form-control input-sm width-100" name="query_field_list[0].field_placeholder" validate="{stringMaxLength:100}"  value="'+(rowObject["field_placeholder"]||"")+'"/>';
					}
			     }, 
				 {label:'field_chosen',name:'field_chosen', index: 'field_chosen', hidden:true},
				 {label:'使用Chosen美化组件',name:'field_chosen_text', index: 'field_chosen',resizeable:false,sortable : false, hidden:true,
					 formatter:function (cellvalue, options, rowObject){
					 	//字段作为select元素是否使用chosen字段美化组件;默认 1,可选值 ：1：使用,0：不使用
					 	 var value = $.defined( rowObject["field_chosen"]) ?  rowObject["field_chosen"] : "1";
				    	 var html1 = [];	
						 	html1.push('<select readonly="readonly" name="query_field_list[0].field_chosen" class="form-control input-sm field_align width-100" validate="{required:true}">');
							 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>使用</option>');
							 	html1.push('<option value="0" '+( value == '0' ? ' selected="selected" ' : "")+'>不使用</option>');
						 	html1.push('</select>');
						return html1.join("");
					}
				 },
				 {label:'field_autocomplete',name:'field_autocomplete', index: 'field_autocomplete', hidden:true},
			     {label:'使用自动完成组件',name:'field_autocomplete_text', index: 'field_autocomplete',resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
					 	//字段作为文本输入框时是否使用自动完成组件;默认 0,可选值 ：1：使用,0：不使用
					    var value = $.defined( rowObject["field_autocomplete"]) ?  rowObject["field_autocomplete"] : "1";
				    	var html1 = [];	
						 	html1.push('<select  readonly="readonly" name="query_field_list[0].field_autocomplete" class="form-control input-sm width-100" validate="{required:true}">');
						 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>使用</option>');
						 	html1.push('<option value="0" '+( value == '0' ? ' selected="selected" ' : "")+'>不使用</option>');
						 	html1.push('</select>');
						return html1.join("");
					}
			     },
				 {label:'field_required',name:'field_required', index: 'field_required', hidden:true},
			     {label:'字段要求',name:'field_required_text', index: 'field_required',resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
					 	//功能对应字段是否必填;默认 0,可选值 ：1：必填,0：非必填
					    var value = $.defined( rowObject["field_required"]) ?  rowObject["field_required"] : "0";
				    	var html1 = [];	
						 	html1.push('<select  readonly="readonly" name="query_field_list[0].field_required" class="form-control input-sm width-100" validate="{required:true}">');
						 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>必填</option>');
						 	html1.push('<option value="0" '+( value == '0' ? ' selected="selected" ' : "")+'>非必填</option>');
						 	html1.push('</select>');
						return html1.join("");
					}
			     }
			],
			/* 当插入每行时触发。rowid插入当前行的id；rowdata插入行的数据，格式为name: value，name为colModel中的名字*/
			afterInsertRow	: function(rowid,rowdata,row,elem){
				rowIndex += 1;
			},
			/*向服务器发起请求时会把数据进行序列化，用户自定义数据也可以被提交到服务器端*/
			serializeGridData: function(postData){
				$.extend(postData,{
					"element_guid"			: jQuery('#element_guid').val(),
					"query_guid"			: jQuery('#query_guid').val(),
					"element_related_guid"	: jQuery('#element_related_guid').val()
				});
				return postData;
			},
			/* 当插入每行时触发。rowid插入当前行的id；rowdata插入行的数据，格式为name: value，name为colModel中的名字*/
			loadComplete 	: function(xhr){
				//循环行
				$("#tabGrid_query").resetIndex();
		 		$("#tabGrid_query").clearTitle();
			},
			sortname: 'field_name', //首次加载要进行排序的字段 
		 	sortorder: "asc"
		});
		
		$("#tabGrid_query").loadJqGrid(widgetGrid);
		
		
		
		//监听输入文本框失去焦点事件：自动更新行输入框对应的字段值
		$(document).off("blur", "#detail_pane input.auto-update").on("blur","#detail_pane input.auto-update",function(event){
			event.stopImmediatePropagation(); 
			var table_guid = $(this).data("table_guid");
			var btn = this;
			if($.founded(table_guid)){
				var target = $(this).data("target");
				var updateRow = $("#tabGrid_query").jqGrid("getRowData",table_guid);
					updateRow[target] = $(this).val();
				$("#tabGrid_query").jqGrid().setRowData(table_guid,updateRow);
				//处理formatter导致的td上title显示异常
				$("#tabGrid_query").resetIndex();
		 		$("#tabGrid_query").clearTitle();
			}
		})
		//监听下拉改变事件：自动更新行下拉框对应的字段值
		.off("change", "#detail_pane select.auto-update").on("change","#detail_pane select.auto-update",function(event){
			event.stopImmediatePropagation(); 
			var table_guid = $(this).data("table_guid");
			var btn = this;
			if($.founded(table_guid)){
				var target = $(this).data("target");
				var updateRow = $("#tabGrid_query").jqGrid("getRowData",table_guid);
					updateRow[target] = $(this).find("option:selected").val();
				$("#tabGrid_query").jqGrid().setRowData(table_guid,updateRow);
				//处理formatter导致的td上title显示异常
				$("#tabGrid_query").resetIndex();
		 		$("#tabGrid_query").clearTitle();
			}
		})
		//监听更多按钮点击事件
		.off("click", "#detail_pane button.btn-setting").on("click","#detail_pane button.btn-setting",function(event){
			event.stopImmediatePropagation(); 
			var table_guid = $(this).data("table_guid");
			var btn = this;
			if($.founded(table_guid)){
				var rowData = $("#tabGrid_query").jqGrid("getRowData",table_guid);
				//弹窗
				$.showDialog(_path+"/design/designQuery_cxFuncElementQueryBaseElement.html",'关联基础字段',$.extend(true,{},modifyConfig,{
					"width"		: "700px",
					"modalName"	: "bindSetting",
					"data"		: {
						"queryFieldModel.table_guid"	: table_guid,
						"queryFieldModel.field_guid"	: rowData["field_guid"]
					},
					"onHidden"	: function(){
						clearTitle();
					},
					"buttons"	: {
						success : {
							label : "确 定",
							className : "btn-primary",
							callback : function() {
								var field_guid = $("#tabGrid_basefield").jqGrid("getGridParam", "selrow");
								var baseRow = $("#tabGrid_basefield").jqGrid("getRowData",field_guid);
								if($.founded(field_guid)){
									var updateRow = $.extend(true,{},rowData,{
										"field_guid"			:  field_guid,
										"field_filtertype"		:  "1",
										"field_text"			:  $.founded(rowData["field_text"]) ? rowData["field_text"] : baseRow["field_text"],
										"field_id"				:  $.founded(rowData["field_id"]) ? rowData["field_id"] : baseRow["field_id"],
										"field_name"			:  $.founded(rowData["field_name"]) ? rowData["field_name"] : baseRow["field_name"]
									});
									$("#tabGrid_query").jqGrid().setRowData(table_guid,updateRow);
									//处理formatter导致的td上title显示异常
									$("#tabGrid_query").resetIndex();
							 		$("#tabGrid_query").clearTitle();
								}
								$.closeModal("bindSetting");
								return false;
							}
						},
						cancel : {
							label : "关 闭",
							className : "btn-default" 
						}
					}
				}));
			}
		})
		.off("click", "#detail_pane button.btn-clear").on("click","#detail_pane button.btn-clear",function(event){
			event.stopImmediatePropagation(); 
			var table_guid = $(this).data("table_guid");
			var btn = this;
			if($.founded(table_guid)){
				var rowData = $("#tabGrid_query").jqGrid("getRowData",table_guid);
				var updateRow = $.extend(true,{},rowData,{
					"field_guid"			:  null
				});
				$("#tabGrid_query").jqGrid().setRowData(table_guid,updateRow);
				//处理formatter导致的td上title显示异常
				$("#tabGrid_query").resetIndex();
		 		$("#tabGrid_query").clearTitle();
			}
		})
		//监听移除按钮点击事件
		.off("click","#detail_pane button.btn-remove").on("click","#detail_pane button.btn-remove",function(event){
			event.stopImmediatePropagation(); 
			var table_guid = $(this).data("table_guid");
			if($.founded(table_guid)){
				$('#ajaxForm').clearValid();
				$("#tabGrid_query").jqGrid("delRowData",table_guid);
				$("#tabGrid_query").resetIndex();
		 		$("#tabGrid_query").clearTitle();
			}
		})
		//监听更多按钮点击事件
		.off("click", "#detail_pane button.btn-detail").on("click","#detail_pane button.btn-detail",function(event){
			event.stopImmediatePropagation(); 
			var table_guid = $(this).data("table_guid");
			var btn = this;
			if($.founded(table_guid)){
				var rowData = $("#tabGrid_query").jqGrid("getRowData",table_guid);
				var paramMap = {};
				$("#tabGrid_query").find('tr[id="'+table_guid+'"]').find(":text,select,:hidden").each(function(){
					//这里只需要input和select
					if($(this).is("input") || $(this).is("select")){
						var name = $(this).attr("name");
						paramMap[name.replace(/query_field_list\[\d+\]/, "queryFieldModel") ] = $(this).val();
					}
				});
				//弹窗
				$.showDialog(_path+"/design/designQuery_cxFuncElementQueryMoreParams.html",'更多条件参数',$.extend(true,{},modifyConfig,{
					"width"		: "700px",
					"modalName"	: "moreSetting",
					"data"		: paramMap,
					"buttons"	: {
						success : {
							label : "确 定",
							className : "btn-primary",
							callback : function() {
								
								var updateRow = $.extend(true,{},rowData,{
									"field_class"			: $("#field_class").val(),
									"field_attr"			: $("#field_attr").val(),
									"field_value" 			: $("#field_value").val(),
									"field_mapper"			: $("#field_mapper").val(),
									"field_placeholder"	 	: $("#field_placeholder").val(),
									"field_chosen" 			: $("input[name='field_chosen']:checked").val(),
									"field_autocomplete" 	: $("input[name='field_autocomplete']:checked").val(),
									"field_required" 		: $("input[name='field_required']:checked").val()
								});
					
								$("#tabGrid_query").jqGrid().setRowData(table_guid,updateRow);
								
								//处理formatter导致的td上title显示异常
								$("#tabGrid_query").resetIndex();
						 		$("#tabGrid_query").clearTitle();
							    
								$.closeModal("moreSetting");
								return false;
							}
						},
						cancel : {
							label : "关 闭",
							className : "btn-default" 
						}
					}
				}));
			}
		});
	}
	
	$('#ajaxForm').validateForm( {
		//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			//返回false阻止提交
			return true;
		}
	});
	
});