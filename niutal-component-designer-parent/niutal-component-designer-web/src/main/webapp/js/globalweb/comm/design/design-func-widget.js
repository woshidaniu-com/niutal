jQuery(function($) {

	$('#parameter_pane').find("div.table-responsive").scrollTop(0);

	$.validator.addMethod("widgetRequired", function(value, element, param) {
		return $("#widget_sql").founded() || $("#widget_data_url").founded();
	}, "组件数据[查询SQL]或[查询URL]必须有一个必填!");
	
	$.validator.addMethod("uniqueKey", function(value, element, param) {
		var other_keys = $("#tabGrid_widget").find("select.field_key");
		var other_sum = 0;
		$(other_keys).each(function(){
			if ($(this).find("option:selected").val() == '1'){
				other_sum += 1;
			}
		});
		return other_sum == 1;
	}, "JQGrid组件只能有一个数据列为主键列!");
	
	//有效性验证：根据sql验证是否sql对应的列与设置的查询列解析信息不一致
	$.validator.addMethod("columnEffected", function(value, element, param) {
		if($("#jqgrid_pane").size() > 0 ){
			if(!$(element).founded()){
				
				$("#tabGrid_widget").find('tr').css({"border":""});
				 $("#removed_tip").hide();
				
				return this.optional(element) || true;
			}else{
				var isEffected = true;
				$.ajax({
					type:"POST",
				   	url: _path + "/design/designWidget_cxParserColumnWhithSQL.html",
				   	async: false ,
				   	data: {
						"widget_sql"		: encodeURIComponent(jQuery('#widget_sql').val()),
						"func_widget_guid"	: jQuery('#func_widget_guid').val()
					},
					success: function(columnList){
						 if($.founded(columnList)){
							 //判断是否是数组
							 if($.isArray(columnList)){
								 var rows = [].concat($("#tabGrid_widget").jqGrid('getRowData'));
								 //循环已有列
								 $.each(rows,function(i,row){
									 var isRowEffected = true;
									 //循环SQL解析出的列
									 $.each(columnList,function(j,column){
										 //进行对比
										 if( row["field_name"] == column["field_name"]){
											 isRowEffected = true;
											 return false;
										 }else{
											 isRowEffected = false;
										 }
									 });
									 //如果列是无效的
									 if(!isRowEffected){
										 $("#tabGrid_widget").find('tr[id="'+row["field_guid"]+'"]').css({"border":"2px solid red"});
										 $("#removed_tip").show();
										 isEffected = false;
									 }else{
										 $("#tabGrid_widget").find('tr[id="'+row["field_guid"]+'"]').css({"border":""});
									 }
								 });
								 if(isEffected){
									 $("#removed_tip").hide();
								 }else{
									 $("#removed_tip").show();
								 }
							 }else{
								 $(element).data("tip",columnList);
								 isEffected = false;
							 }
						 }
					}
				});
				return this.optional(element) || isEffected  ;
			}
		}else{
			return true;
		}
	}, function(param,element){
		var tip  = $(element).data("tip");
		if($.founded(tip)){
			return tip;
		}
		return "SQL解析出的数据列与设置的数据查询解析列信息不一致";
	});
	
	if($("#jqgrid_pane").size() > 0 ){
		if($("#tabGrid_source").size() > 0){
			var sourceGrid = $.extend({},BaseJqGrid,{  
				resizeHandle:"#source_container",
			    url: _path + "/design/designWidget_cxSQLParserColumnIndex.html?doType=query", //这是Action的请求地址  
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
			         {label:'表ID',name:'field_guid', index:'field_guid', key:true, hidden:true},
				     {label:'可选字段',name:'field_name', index: 'field_name',align:'left',width:"80px",resizeable:false,sortable : false},
				     {label:'字段备注',name:'field_comment', index: 'field_comment',align:'left',width:"100px",resizeable:false,sortable : false}
				],
				/*向服务器发起请求时会把数据进行序列化，用户自定义数据也可以被提交到服务器端*/
				serializeGridData: function(postData){
					$.extend(postData,{
						"func_widget_guid"	: jQuery('#func_widget_guid').val()
					});
					return postData;
				}
			});
			
			$("#tabGrid_source").loadJqGrid(sourceGrid);
			
			$("#btn_copy_row").killFocus().click(function(e){
				var field_guids   = [].concat($("#tabGrid_source").getKeys());
				$.each(field_guids,function(i,field_guid){
					var index = jQuery("#tabGrid_widget").getGridParam("records") + 1;
					var field_row = $("#tabGrid_source").getRow(field_guid);
					jQuery("#tabGrid_widget").jqGrid("addRowData","field" + index,{
						"field_guid"		: "field" + index,
						"field_label"		: (field_row.field_comment||""),
						"field_name"		: field_row.field_name,
						"field_width"		: "150px",
						"field_ordinal"		: index,
						"field_key"			: "0",
						"field_align" 		: "left",
						"field_sortable" 	: "1",
						"field_hidden" 		: "1",
						"field_resizable" 	: "1",
						"field_fixed"	 	: "0",
						"field_cellattr" 	: "",
						"field_edittype" 	: "text",
						"field_editable" 	: "1"
					},"last");
					$("#tabGrid_source").jqGrid("delRowData",field_guid);
				});
				$("#tabGrid_widget").resetIndex();
				$("#tabGrid_widget").clearTitle(); 
			});
		}
		 
		
		$("#btn_add_row").killFocus().click(function(e){
			var index = jQuery("#tabGrid_widget").getGridParam("records") + 1;
			jQuery("#tabGrid_widget").jqGrid("addRowData","field" + index,{
				"field_guid"		: "field" + index,
				"field_index"		: "",
				"field_width"		: "150px",
				"field_ordinal"		: index,
				"field_key"			: "0",
				"field_align" 		: "left",
				"field_sortable" 	: "1",
				"field_hidden" 		: "1",
				"field_resizable" 	: "1",
				"field_fixed"	 	: "0",
				"field_cellattr" 	: "",
				"field_edittype" 	: "text",
				"field_editable" 	: "1"
			},"last");
			$("#tabGrid_widget").resetIndex();
			$("#tabGrid_widget").clearTitle(); 
		});
		
		var rowIndex = 0;
		var widgetGrid = $.extend({},BaseJqGrid,{  
			resizeHandle:"#jqgird_container",
		    url: _path + "/design/designWidget_cxFuncElementWidgetIndex.html?doType=query", //这是Action的请求地址  
		    shrinkToFit: true,
		    rownumbers	: true, 
		    multiselect : false, 
		    minHeight	: 400,
		    rownumWidth	: 30,
		    /*
			 *每页显示记录数;用于设置Grid中一次显示的行数，默认值为15。
			 *正是这个选项将参数rows（prmNames中设置的）通过url选项设置的链接传递到Server。
			 *注意如果Server返回的数据行数超过了rowNum的设定，则Grid也只显示rowNum设定的行数。 
			 */
			rowNum 		: 1500, 
		    colModel:[
			     {label:'ID',name:'field_guid', index:'field_guid', key:true, hidden:true},
			     {label:'ID_Text',name:'field_guid_text', index: 'field_guid',hidden:true,resizeable:false,sortable : false,
					 formatter:function (cellvalue, options, rowObject){
						return '<input type="text" class="form-control input-sm width-100 " name="columnList[0].field_guid" validate="{required:true,stringMaxLength:300}" value="'+(rowObject["field_guid"]||"")+'"/>';
					}
				 },
			     {label:'field_removed',name:'field_removed', index: 'field_removed', hidden:true},
			     {label:'field_label',name:'field_label', index: 'field_label', hidden:true},
			     {label:'列名称',name:'field_label_text', index: 'field_label',align:'center',width:"100px",resizeable:false,sortable : false,
					 formatter:function (cellvalue, options, rowObject){
					 	var html1 = [];	
						 	html1.push('<span title="列名称，当jqGrid的colNames选项数组为空时，为各列指定题头。如果colNames和此项都为空时，则name选项值会成为题头" >');
								html1.push('<input data-target="field_label" data-field_guid="'+rowObject.field_guid+'" type="text" class="form-control input-sm width-100 auto-update" name="columnList[0].field_label" validate="{required:true,stringMaxLength:300}" value="'+(rowObject["field_label"]||"")+'"/>');
							html1.push('</span>');
						
						return html1.join("");
					}
				 },
				 {label:'field_name',name:'field_name', index: 'field_name', hidden:true},
				 {label:'字段名称',name:'field_name_text', index: 'field_name',align:'center',width:"80px",resizeable:false,sortable : false,
					 formatter:function (cellvalue, options, rowObject){
					 	var html1 = [];	
						 	html1.push('<span class="width-100" title="设置排序时所使用的索引名称，这个index名称会作为sidx参数（prmNames中设置的）传递到Server" >');
								html1.push('<input data-target="field_name" data-field_guid="'+rowObject.field_guid+'" type="text" class="form-control input-sm width-100 auto-update" name="columnList[0].field_name" validate="{required:true,stringMaxLength:300}" value="'+(rowObject["field_name"]||"")+'"/>');
							html1.push('</span>');
						
						return html1.join("");
					}
				 },
				 {label:'field_width',name:'field_width', index: 'field_width', hidden:true},
				 {label:'宽度',name:'field_width_text', index: 'field_width',align:'center',width:"70px",resizeable:false,sortable : false,
			    	 formatter:function (cellvalue, options, rowObject){
						var html1 = [];	
						 	html1.push('<div class="input-group input-group-sm"  >');
						 		html1.push('<input data-target="field_width" data-field_guid="'+rowObject.field_guid+'" type="text" class="form-control input-sm width-100 auto-update" name="columnList[0].field_width" validate="{required:true,stringMaxLength:300}" value="'+(rowObject["field_width"]||"")+'"/>');
							html1.push('</div>');
						return html1.join("");
					}
			     },
			     {label:'field_ordinal',name:'field_ordinal', index: 'field_ordinal', hidden:true},
			     {label:'显示顺序',name:'field_ordinal_text', index: 'field_ordinal',align:'center',width:"65px",resizeable:false,sortable : false,
					 formatter:function (cellvalue, options, rowObject){
						return '<span><input data-target="field_ordinal" data-field_guid="'+rowObject.field_guid+'" type="text" class="form-control input-sm field_ordinal auto-update" name="columnList[0].field_ordinal" validate="{required:true}" value="'+(rowObject["field_ordinal"]||"")+'"/></span>';
					}
				 },
				 {label:'field_key',name:'field_key', index: 'field_key', hidden:true},
				 {label:'是否主键',name:'field_key_text', index: 'field_key',align:'center',width:"65px",resizeable:false,sortable : false,
			    	 formatter:function (cellvalue, options, rowObject){
					 	var value = $.defined( rowObject["field_key"]) ?  rowObject["field_key"] : "0";
				    	var html1 = [];	
						 	html1.push('<span title="设置列是否主键列；默认：否">');
						 	html1.push('<select data-target="field_key" data-field_guid="'+rowObject.field_guid+'" name="columnList[0].field_key" class="form-control input-sm field_key  width-100 auto-update" validate="{required:true,uniqueKey:true}">');
						 	html1.push('<option value="0" '+( value == '0' ? ' selected="selected" ' : "")+'>否</option>');
						 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>是</option>');
						 	html1.push('</select>');
							html1.push('</span>');
						return html1.join("");
					}
			     },
			     {label:'field_param',name:'field_param', index: 'field_param', hidden:true},
				 {label:'是否条件字段',name:'field_param_text', index: 'field_param',align:'center',width:"65px",resizeable:false,sortable : false,
			    	 formatter:function (cellvalue, options, rowObject){
					 	var value = $.defined( rowObject["field_param"]) ?  rowObject["field_param"] : "0";
				    	var html1 = [];	
						 	html1.push('<span title="设置列是否作为弹窗条件值，如报表的弹窗会把选中行的该列作为参数传递；默认：否">');
						 	html1.push('<select data-target="field_param" data-field_guid="'+rowObject.field_guid+'" name="columnList[0].field_param" class="form-control input-sm  width-100 auto-update" validate="{required:true,uniqueKey:true}">');
						 	html1.push('<option value="0" '+( value == '0' ? ' selected="selected" ' : "")+'>否</option>');
						 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>是</option>');
						 	html1.push('</select>');
							html1.push('</span>');
						return html1.join("");
					}
			     },
			     {label:'field_hidden',name:'field_hidden', index: 'field_hidden', hidden:true},
			     {label:'显示或隐藏',name:'field_hidden_text', index: 'field_hidden',align:'center',width:"80px",resizeable:false,sortable : false,
			    	 formatter:function (cellvalue, options, rowObject){
			    	    var value = $.defined( rowObject["field_hidden"]) ?  rowObject["field_hidden"] : "1";
				    	var html1 = [];	
						 	html1.push('<select data-target="field_hidden" data-field_guid="'+rowObject.field_guid+'" readonly="readonly" name="columnList[0].field_hidden" class="form-control input-sm width-100 auto-update" validate="{required:true}">');
						 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>显示</option>');
						 	html1.push('<option value="0" '+( value == '0' ? ' selected="selected" ' : "")+'>隐藏</option>');
						 	html1.push('</select>');
						return html1.join("");
					}
			     },
			     {label:'操作',name:'czms', index: 'czms',align:'center',width:"60px",resizeable:false,sortable : false,
					formatter:function (cellvalue, options, rowObject){
			    	 	var html1 = [];	
				    	 	html1.push('<div class="btn-group">');
						 		html1.push('<button type="button" class="btn btn-default btn-xs btn-remove" role="button" data-field_guid="'+rowObject.field_guid+'">移除</button>');
						 		html1.push('<button type="button" class="btn btn-default btn-xs btn-detail" role="button" data-field_guid="'+rowObject.field_guid+'">更多</button>');
					 		html1.push('</div> ');
					 		return html1.join("");
					}
				 },
				 {label:'field_align',name:'field_align', index: 'field_align', hidden:true},
				 {label:'对齐方式',name:'field_align_text', index: 'field_align',align:'center',width:"65px",resizeable:false,sortable : false, hidden:true,
					 formatter:function (cellvalue, options, rowObject){
					 	 var value = $.defined( rowObject["field_align"]) ?  rowObject["field_align"] : "left";
				    	 var html1 = [];	
						 	html1.push('<select  readonly="readonly" name="columnList[0].field_align" class="form-control input-sm field_align width-100" validate="{required:true}">');
							 	html1.push('<option value="left" '+( value == 'left' ? ' selected="selected" ' : "")+'>居左</option>');
							 	html1.push('<option value="center" '+( value == 'center' ? ' selected="selected" ' : "")+'>居中</option>');
							 	html1.push('<option value="right" '+( value == 'right' ? ' selected="selected" ' : "")+'>居右</option>');
						 	html1.push('</select>');
						return html1.join("");
					}
				 },
				 {label:'field_sortable',name:'field_sortable', index: 'field_sortable', hidden:true},
			     {label:'是否可排序',name:'field_sortable_text', index: 'field_sortable',align:'center',width:"80px",resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
					    var value = $.defined( rowObject["field_sortable"]) ?  rowObject["field_sortable"] : "1";
				    	var html1 = [];	
						 	html1.push('<select  readonly="readonly" name="columnList[0].field_sortable" class="form-control input-sm width-100" validate="{required:true}">');
						 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>是</option>');
						 	html1.push('<option value="0" '+( value == '0' ? ' selected="selected" ' : "")+'>否</option>');
						 	html1.push('</select>');
						return html1.join("");
					}
			     },
			     {label:'field_resizable',name:'field_resizable', index: 'field_resizable', hidden:true},
			     {label:'宽度可调整',name:'field_resizable_text', index: 'field_resizable',align:'center',width:"80px",resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
			    	    var value = $.defined( rowObject["field_resizable"]) ?  rowObject["field_resizable"] : "1";
				    	var html1 = [];	
						 	html1.push('<select readonly="readonly"  name="columnList[0].field_resizable" class="form-control input-sm width-100" validate="{required:true}">');
						 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>可调整</option>');
						 	html1.push('<option value="0" '+( value == '0' ? ' selected="selected" ' : "")+'>不可调整</option>');
						 	html1.push('</select>');
						return html1.join("");
					}
			     },
			     {label:'field_fixed',name:'field_fixed', index: 'field_fixed', hidden:true},
			     {label:'宽度是否固定',name:'field_fixed_text', index: 'field_fixed',align:'center',width:"65px",resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
			    	    var value = $.defined( rowObject["field_fixed"]) ?  rowObject["field_fixed"] : "0";
				    	var html1 = [];	
						 	html1.push('<select  readonly="readonly" name="columnList[0].field_fixed" class="form-control input-sm width-100" validate="{required:true}">');
						 	html1.push('<option value="0" '+( value == '0' ? ' selected="selected" ' : "")+'>否</option>');
						 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>是</option>');
						 	html1.push('</select>');
						return html1.join("");
					}
			     },
			     {label:'field_cellattr',name:'field_cellattr', index: 'field_cellattr', hidden:true},
			     {label:'扩展参数',name:'field_cellattr_text', index: 'field_cellattr',align:'center',width:"100px",resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
						return '<input type="text"  readonly="readonly" class="form-control input-sm width-100" name="columnList[0].field_cellattr" validate="{stringMaxLength:500}"  value="'+(rowObject["field_cellattr"]||"")+'"/>';
					}
			     },
			     {label:'field_editable',name:'field_editable', index: 'field_editable', hidden:true},
			     {label:'编辑状态',name:'field_editable_text', index: 'field_editable',align:'center',width:"80px",resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
			    	 	var value = $.defined( rowObject["field_editable"]) ?  rowObject["field_editable"] : "0";
				    	var html1 = [];	
						 	html1.push('<select name="columnList[0].field_editable" readonly="readonly" class="form-control input-sm width-100" >');
						 	html1.push('<option value="1" '+( value == '1' ? ' selected="selected" ' : "")+'>可编辑</option>');
						 	html1.push('<option value="0" '+( value == '0' ? ' selected="selected" ' : "")+'>不可编辑</option>');
						 	html1.push('</select>');
						return html1.join("");
					}
			     },
			     {label:'field_edittype',name:'field_edittype', index: 'field_edittype', hidden:true},
			     {label:'编辑类型',name:'field_edittype_text', index: 'field_edittype',align:'center',width:"80px",resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
			    	 	var value = $.defined( rowObject["field_edittype"]) ?  rowObject["field_edittype"] : "text";
				    	var html1 = [];	
						 	html1.push('<select name="columnList[0].field_edittype" readonly="readonly" class="form-control input-sm width-100">');
						 	html1.push('<option value="text" '+( value == 'text' ? ' selected="selected" ' : "")+'>text</option>');
						 	html1.push('<option value="textarea" '+( value == 'textarea' ? ' selected="selected" ' : "")+'>textarea</option>');
						 	html1.push('<option value="select" '+( value == 'select' ? ' selected="selected" ' : "")+'>select</option>');
						 	html1.push('<option value="checkbox" '+( value == 'checkbox' ? ' selected="selected" ' : "")+'>checkbox</option>');
						 	html1.push('<option value="password" '+( value == 'password' ? ' selected="selected" ' : "")+'>password</option>');
						 	html1.push('<option value="button" '+( value == 'button' ? ' selected="selected" ' : "")+'>button</option>');
						 	html1.push('<option value="image" '+( value == 'image' ? ' selected="selected" ' : "")+'>image</option>');
						 	html1.push('<option value="file" '+( value == 'file' ? ' selected="selected" ' : "")+'>file</option>');
						 	html1.push('</select>');
						return html1.join("");
					}
			     },
			     {label:'field_editoptions',name:'field_editoptions', index: 'field_editoptions', hidden:true},
			     {label:'编辑参数',name:'field_editoptions_text', index: 'field_editoptions',align:'center',width:"100px",resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
				    	 /**
				    	  * 【JQGrid组件】编辑的一系列选项。如： 
				    	  * 	1、动态从服务器端获取数据:{editoptions: {dataUrl:”${ctx}/admin/deplistforstu.action”}}  
						  *		2、静态数据：{editoptions: {value:"zy:专业;dl:大类"}}
				    	  */
						return '<input type="text"  readonly="readonly"  class="disabled form-control input-sm width-100" name="columnList[0].field_editoptions" validate="{stringMaxLength:500}"  value="'+(rowObject["field_editoptions"]||"")+'"/>';
					}
			     },
			     {label:'field_editrules',name:'field_editrules', index: 'field_editrules', hidden:true},
			     {label:'编辑规则',name:'field_editrules_text', index: 'field_editrules',align:'center',width:"100px",resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
				    	 /**
				    	  * 【JQGrid组件】编辑的规则;如：{editrules: {edithidden:true,required:true,number:true,minValue:10,maxValue:100}},
				    	  *  设定年龄的最大值为100，最小值为10，而且为数字类型，并且为必输字段
				    	  */
						return '<input type="text"  readonly="readonly" class="disabled form-control input-sm width-100" name="columnList[0].field_editrules" validate="{stringMaxLength:500}"  value="'+(rowObject["field_editrules"]||"")+'"/>';
					}
			     },
			     {label:'field_formatter',name:'field_formatter', index: 'field_formatter', hidden:true},
			     {label:'格式化',name:'field_formatter_text', index: 'field_formatter',align:'center',width:"100px",resizeable:false,sortable : false, hidden:true,
			    	 formatter:function (cellvalue, options, rowObject){
				    	 /**
				    	  * 预设类型或用来格式化该列的自定义函数名。常用预设格式有：integer、date、currency、number等 ,select,自定义字符串（如果字符串表示函数名称则以函数调用）;
				    	  */
						return '<input type="text" readonly="readonly" class="form-control input-sm width-100" name="columnList[0].field_formatter" validate="{stringMaxLength:500}" value="'+(rowObject["field_formatter"]||"")+'"/>';
					}
			     }
			],
			/* 当插入每行时触发。rowid插入当前行的id；rowdata插入行的数据，格式为name: value，name为colModel中的名字*/
			afterInsertRow	: function(rowid,rowdata,row,elem){
				if( '1' == rowdata["field_removed"]){
					$("#tabGrid_widget").find('tr[id="'+rowid+'"]').css({"border":"2px solid red"});
					$("#removed_tip").show();
				}
				rowIndex += 1;
			},
			/*向服务器发起请求时会把数据进行序列化，用户自定义数据也可以被提交到服务器端*/
			serializeGridData: function(postData){
				$.extend(postData,{
					"func_widget_guid"	: jQuery('#func_widget_guid').val()
				});
				return postData;
			},
			/* 当插入每行时触发。rowid插入当前行的id；rowdata插入行的数据，格式为name: value，name为colModel中的名字*/
			loadComplete 	: function(xhr){
				//循环行
				$("#tabGrid_widget").resetIndex();
				$("#tabGrid_widget").clearTitle(); 
			},
			sortname: 'field_label', //首次加载要进行排序的字段 
		 	sortorder: "asc"
		});
		
		$("#tabGrid_widget").loadJqGrid(widgetGrid);
		
		
		/*$("#widget_sql").on("paste",function(){  
        	alert('paste behaviour detected!');  
        }).on( "copy" , function(){  
			alert('copy behaviour detected!');  
	    }).on ("cut" , function(){  
        	alert('cut behaviour detected!');  
	    });  
		*/
		//监听widget_sql文本框的粘贴和剪切事件
		$(document).off("paste","#widget_sql").on("paste","#widget_sql",function(event){
			window.setTimeout(function(){
				$("#tabGrid_source").refershGrid({
					"widget_sql"		: encodeURIComponent($('#widget_sql').val()),
				});
				$('#widget_sql').blur();
			}, 100);
		}).off("cut","#widget_sql").on("cut","#widget_sql",function(event){
			window.setTimeout(function(){
				$("#tabGrid_source").refershGrid({
					"widget_sql"		: encodeURIComponent($('#widget_sql').val()),
				});
				$('#widget_sql').blur();
			}, 100);
		})
		//监听输入文本框失去焦点事件：自动更新行输入框对应的字段值
		.off("blur", "#jqgrid_pane input.auto-update").on("blur","#jqgrid_pane input.auto-update",function(event){
			event.stopImmediatePropagation(); 
			var field_guid = $(this).data("field_guid");
			if($.founded(field_guid)){
				var target = $(this).data("target");
				var updateRow = $("#tabGrid_widget").jqGrid("getRowData",field_guid);
					updateRow[target] = $(this).val();
				$("#tabGrid_widget").jqGrid().setRowData(field_guid,updateRow);
				$("#tabGrid_widget").resetIndex();
				$("#tabGrid_widget").clearTitle(); 
			}
		})
		//监听下拉改变事件：自动更新行下拉框对应的字段值
		.off("change", "#jqgrid_pane select.auto-update").on("change","#jqgrid_pane select.auto-update",function(event){
			event.stopImmediatePropagation(); 
			var field_guid = $(this).data("field_guid");
			if($.founded(field_guid)){
				var target = $(this).data("target");
				var updateRow = $("#tabGrid_widget").jqGrid("getRowData",field_guid);
				updateRow[target] = $(this).find("option:selected").val();
				$("#tabGrid_widget").jqGrid().setRowData(field_guid,updateRow);
				$("#tabGrid_widget").resetIndex();
				$("#tabGrid_widget").clearTitle(); 
			}
		})
		//监听选择主键下拉框change事件
		.off("change","#jqgrid_pane select.field_key").on("change","#jqgrid_pane select.field_key",function(event){
			event.stopImmediatePropagation(); 
			var lastVal = $(this).find("option:selected").val();
			if( '1' == lastVal){
				$("#tabGrid_widget").find("select.field_key").not(this).each(function(){
					$(this).val('0');
				});
			}
		})
		//监听移除按钮点击事件
		.off("click","#jqgrid_pane button.btn-remove ").on("click","#jqgrid_pane button.btn-remove ",function(event){
			event.stopImmediatePropagation(); 
			var field_guid = $(this).data("field_guid");
			if($.founded(field_guid)){
				$('#ajaxForm').clearValid();
				$("#tabGrid_widget").jqGrid("delRowData",field_guid);
				$("#tabGrid_widget").resetIndex();
				$("#tabGrid_widget").clearTitle(); 
			}
		})
		//监听更多按钮点击事件
		.off("click", "#jqgrid_pane button.btn-detail").on("click","#jqgrid_pane button.btn-detail",function(event){
			event.stopImmediatePropagation(); 
			var field_guid = $(this).data("field_guid");
			var btn = this;
			if($.founded(field_guid)){
				var rowData = $("#tabGrid_widget").jqGrid("getRowData",field_guid);
				var paramMap = {};
				$("#tabGrid_widget").find('tr[id="'+field_guid+'"]').find(":text,select,:hidden").each(function(){
					//这里只需要input和select
					if($(this).is("input") || $(this).is("select")){
						var name = $(this).attr("name");
						paramMap[name.replace(/columnList\[\d+\]/, "jqgridModel") ] = $(this).val();
					}
				});
				//弹窗
				$.showDialog(_path+"/design/designWidget_cxFuncElementWidgetMoreParams.html",'更多组件参数',$.extend(true,{},modifyConfig,{
					"width"		: "700px",
					"modalName"	: "moreSetting",
					"data"		: paramMap,
					"buttons"	: {
						success : {
							label : "确 定",
							className : "btn-primary",
							callback : function() {
								
								var updateRow = $.extend(true,{},rowData,{
									"field_align"		: $("#field_align").find("option:selected").val(),
									"field_sortable"	: $("#field_sortable").find("option:selected").val(),
									"field_resizable"	: $("#field_resizable").find("option:selected").val(),
									"field_fixed"	 	: $("#field_fixed").find("option:selected").val(),
									"field_editable" 	: $("#field_editable").find("option:selected").val(),
									"field_edittype" 	: $("#field_edittype").find("option:selected").val(),
									"field_editoptions" : $("#field_editoptions").val(),
									"field_editrules" 	: $("#field_editrules").val(),
									"field_cellattr" 	: $("#field_cellattr").val(),
									"field_formatter" 	: $("#field_formatter").val()
								});
					
								$("#tabGrid_widget").jqGrid().setRowData(field_guid,updateRow);
								
								//处理formatter导致的td上title显示异常
								$("#tabGrid_widget").resetIndex();
								$("#tabGrid_widget").clearTitle(); 
							    
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
		//提交表单后,未通过验证的表单(第一个或提交之前获得焦点的未通过验证的表单)会获得焦点
		focusInvalid 		: false,
		//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			$.each(formData||[],function(i,itemData){
				if(itemData["name"] == 'widget_sql' ){
					itemData["value"] = encodeURIComponent(itemData["value"]);
				}
			});
			//返回false阻止提交
			return true;
		}
	});
	
});