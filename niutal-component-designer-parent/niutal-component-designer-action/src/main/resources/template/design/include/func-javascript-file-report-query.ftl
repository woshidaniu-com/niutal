<#if func_element.element_query?exists>  

	<#--  【 自定义高级报表】查询条件类型;默认 0,可选值 ：0：无查询条件,1：普通查询,2：高级查询  -->
	<#if parameters.funcModel.query_type == '1'>
	/*====================================查询区域js脚本开始====================================*/
	//绑定查询按钮事件
	$("#btn_search_${func_element.element_guid?string}").click(function (e) {
		var searchAble = true;
		$("#reportSearchForm_${parameters.funcModel.func_guid?string}").find(":input").each(function(){
			var required = $(this).data("required");
			$(this).closest(".form-group").removeClass("has-error");
		    var bootbox = $(this).closest(".bootbox");
			var search_tip = bootbox.size() > 0 ? bootbox.find("#search_tip") : $("#yhgnPage").find("#search_tip")
			$(search_tip).empty();
			if("true" == required || true == required){
				var founded = ($(this).is(':radio') || $(this).is(':checkbox')) ?  ($("input[name='"+$(this).attr("name")+"']").filter(":checked").size() > 1) : $(this).founded();
				if(founded){
					if($.fn.successClass){
						if(($(this).is(':radio') || $(this).is(':checkbox'))){
							$("input[name='"+$(this).attr("name")+"']").successClass();
						}else{
							$(this).successClass();
						}
					}
					searchAble = true;
				}else{
					if($.fn.errorClass){
						if($(this).is('select')){
							$(this).errorClass("必须选择!","top","right");
						}else if($(this).is(':radio') || $(this).is(':checkbox')){
							$($("input[name='"+$(this).attr("name")+"']").last()).errorClass("必须选择!","top","right");
						}else{
							$(this).errorClass("必须填写!","top","right");
						}
					}else{
						$(this).closest(".form-group").addClass("has-error");
						if($(this).is('select')){
							$(search_tip).text( $(this).data("text") + "必须选择!");
						}else if($(this).is(':radio') || $(this).is(':checkbox')){
							$(search_tip).text( $($("input[name='"+$(this).attr("name")+"']").last()).data("text") + "必须选择!");
						}else{
							$(search_tip).text( $(this).data("text") + "必须填写!");
						}
					}
					searchAble = false;
				}
			}else{
				if($.fn.successClass){
					if(($(this).is(':radio') || $(this).is(':checkbox'))){
						$("input[name='"+$(this).attr("name")+"']").successClass();
					}else{
						$(this).successClass();
					}
				}
				searchAble = true;
			}
			return searchAble;
		});
		if(searchAble){
			loadFrame();
			$("#reportSearchForm_${parameters.funcModel.func_guid?string}").submit();
		}
	});
	
	
	//定义级联Map：存储需要级联的字段关系
	var concatenationMap_${func_element.element_guid}  = {};
	$("#searchbox_${func_element.element_guid}").find("select.form-control").each(function(){
		var data_parent = $(this).data("parent")||"";
		//如果当前元素有级联父级对象
		if($.founded(data_parent)){
			var data_child = $(this).attr("id");
			//分割字符串，解析出多个相同
			var field_parents = (data_parent+"").split(",");
			$.each(field_parents || [],function(i,field_parent){
				if($.founded(field_parent)){
					var parent_item = concatenationMap_${func_element.element_guid}[field_parent];
					if(parent_item && jQuery.isArray(parent_item)){
						var isContain = false;
						for (var index = 0; index < parent_item.length; index++) {
							if (data_parent == parent_item[index]) {
								isContain = true;
								break;
							}
						}
						//判断是否已经包含
						if(!isContain){
							concatenationMap_${func_element.element_guid}[field_parent].push(data_child);
						}
					}else {
						concatenationMap_${func_element.element_guid}[field_parent] = [data_child];
					}
				}
			});
		}
	});
	//根据上面解析结果进行级联数据查询事件绑定
	$.each(concatenationMap_${func_element.element_guid}||{},function(parent_id,child_arr){
		//绑定change事件
		$("#" + parent_id).change(function(){
			if(child_arr.length > 0){
				//对child_arr进行排序：原因在于这些子级关联元素，也有可能有相互直接的级联
				child_arr.sort(function(x,y){
					var x_parent = $("#" + x ).data("parent");
					var y_parent = $("#" + y ).data("parent")
					if(y_parent.indexOf(x_parent) > 0) {
						return 1;
					}else{
						return -1
					}
				}); 
			}
			$.each(child_arr || [],function(i,child_id){
				//被关联元素
				var child_element = $("#" + child_id );
				//被关联元素data-parent值
				var data_parent = $(child_element).data("parent");
				if($.founded(data_parent)){
				
					//被关联元素数据更新地址
					var data_updata_url = $(child_element).data("updata-url");
					//被关联元素旧值
					var old_val = $(child_element).val();
					
					//被关联元素默认选项值
					var headerKey = $(child_element).data("header-key");
					//被关联元素默认选项名称
					var headerValue = $(child_element).data("header-value");
					//被关联元素取key值的索引
					var listKey = $(child_element).data("list-key");
					//被关联元素取value值的索引
					var listValue = $(child_element).data("list-value");
						
					//定义参数变量
					var dataMap = {};
					var parent_ids = data_parent.split(",");
					//组织父级参数
					$.each(parent_ids||[],function(i,parent_id){
						var parent_element = $("#" + parent_id);
						var parent_name = $(parent_element).attr("name");
						var data_mapper = $(parent_element).data("mapper");
						dataMap[$.founded(data_mapper) ? data_mapper : (parent_name||parent_id)] = $(parent_element).val();
					});
					//去除空的参数
					$.each(dataMap||{}, function(key, val){
						if(!($.founded(key)&&$.founded(val)&&(!$.isNumeric(key)))){
							delete dataMap[key];
						}
					});
					//组织field_mapper
					var html = [];
						if($.founded(headerValue)){
				   			html.push('<option value="'+headerKey +'">'+headerValue+'</option>');
						}
					//远程获取数据
				    jQuery.ajaxSetup({async:false});
					$.getJSON(_path + data_updata_url , dataMap , function(data){
						if($.founded(data)){
							$.each(data,function(i,rowObj){
								var selectedStr = ""; 
								if($.founded(old_val) && old_val== rowObj[listKey]){
									selectedStr = ' selected="selected" ';
								}
								html.push('<option value="'+rowObj[listKey]+'" '+selectedStr+'>'+rowObj[listValue]+'</option>');
							});
						}
					});
					$(child_element).empty().append(html.join(""));
					$(child_element).trigger("chosen:updated");
					jQuery.ajaxSetup({async:true});
				}
			});
		});
	});
	 
	/*====================================查询区域js脚本结束====================================*/
	</#if>
	
	<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
	if($.fn.adapt){
		$("#element_${func_element.element_id}").adapt({
		buttons:[{
				btn_class	: "width-50",
				btn_text	:	"编辑查询条件区域",
				/*按钮单击事件*/
				onBtnClick	: function(e){
					//弹窗
					$.showDialog(_path+"/design/designQuery_cxFuncElementQueryIndex.html?query_guid=${func_element.element_query.query_guid?string}",'编辑查询条件区域',$.extend(true , {},modifyConfig,{
						"width"		: $("#yhgnPage").innerWidth()  +"px",
						"data"		: {
							"report_guid"	: "${parameters.funcReport.report_guid?default('')}"
						},
						buttons		: {
							success : {
								label : "确 定",
								className : "btn-primary",
								callback : function() {
									submitForm("ajaxForm",function(responseText,statusText){
										// responseText 可能是 xmlDoc, jsonObj, html, text, 等等...
										// statusText 	描述状态的字符串（可能值："No Transport"、"timeout"、"notmodified"---304 "、"parsererror"、"success"、"error"
										if(responseText.indexOf("成功") > -1){
											$.success(responseText,function() {
												$.closeModal("modifyModal");
												if($("#ReportModal").size() > 0){
													$("#ReportModal").reloadDialog();
												}else{
													$("#topButton").click();
												}
											});
										}else if(responseText.indexOf("失败") > -1){
											$.error(responseText,function() {
											});
										} else{
											$.alert(responseText,function() {
											});
										}
									});
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
			},{
				btn_class	: "width-50",
				btn_text	:	"移除元素实体对象",
				/*按钮单击事件*/
				onBtnClick	: function(e){
					e.stopImmediatePropagation(); 
					$.confirm('继续将删除元素绑定的实体数据！是否继续？',function(result) {
						if(result){
							$.ajax({
								type	: "POST",
								url		: _path+"/design/designFunc_scysstFuncElementEntityData.html",
								async	: false ,
								data	: {
									"elementModel.func_code"	: '${parameters.func_code?string}',
									"elementModel.opt_code"		: '${parameters.opt_code?string}',
									"elementModel.func_guid"	: '${parameters.funcModel.func_guid?string}',
									"elementModel.element_guid"	: '${func_element.element_guid?string}'
								},
								success	: function(responseText){
									if(responseText.indexOf("成功") > -1){
										$.success(responseText,function() {
											if($("#ReportModal").size() > 0){
												$("#ReportModal").reloadDialog();
											}else{
												$("#topButton").click();
											}
										});
									}else if(responseText.indexOf("失败") > -1){
										$.error(responseText,function() {
											//$.closeModal("addModal");
										});
									} else{
										$.alert(responseText,function() {
											//$.closeModal("addModal");
										});
									}
								}
							});
						}
					});
				}
			}],
			afterRender : function(){
				//这个方法是初始化后的回调函数，在需要做一些事情的时候重写即可
			
			}
		});
	}
	</#if>
</#if>