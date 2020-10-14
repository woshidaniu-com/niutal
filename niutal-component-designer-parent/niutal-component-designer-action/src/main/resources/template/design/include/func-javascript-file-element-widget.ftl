<#if func_element.element_widget?exists>  
<#assign widget_name='${func_element.element_widget.widget_name?lower_case}'/>
<#if (widget_name?index_of('jqgrid') > -1) > 
<#if func_element.element_widget.columnList?exists && func_element.element_widget.columnList?size != 0> 
<#list func_element.element_widget.columnList as columnModel>
	<#if columnModel.field_key == 'true'>
		<#assign widget_key='${columnModel.field_index?string}'/>
		<#break>
	</#if>
</#list>
</#if>
<#if parameters.opt_code == 'cx'>
var jqGrid_${parameters.funcModel.func_guid?string} = <#rt/>
<#else>
var jqGrid_${func_element.element_widget.widget_guid?string} = <#rt/>
</#if> $.extend({<#if parameters.opt_code == 'cx'>minHeight:220</#if>},BaseJqGrid,${func_element.element_widget.widget_params?default('{}')},{ 
	<#if func_element.element_widget.widget_loadAtOnce?exists && func_element.element_widget.widget_loadAtOnce != '1'>
	datatype: "local",
	</#if>
	//这是Action的请求地址
	<#if func_element.element_widget.widget_data_url?exists>
	url		: _path + '${func_element.element_widget.widget_data_url?string}', 
	<#else>
	url		: _path + '/design/funcData_cxFuncDataList.html?func_widget_guid=${func_element.element_widget.func_widget_guid?string}',
	</#if>
	//宽度自适应
	<#if parameters.opt_code == 'cx'>
	resizeHandle:"#searchbox_${parameters.funcModel.func_guid?string}",
	<#else>
	resizeHandle:"#searchbox_${func_element.element_widget.widget_guid?string}",
	</#if>
	<#if func_element.element_widget.widget_pageable?exists && func_element.element_widget.widget_pageable == '1'>
	/* 定义是否在Pager Bar显示所有记录的总数  */
	viewrecords : true,
	scroll 		: false,
	//分页工具栏
	<#if parameters.opt_code == 'cx'>
	pager	: "#pager_${parameters.funcModel.func_guid?string}", 
	<#else>
	pager	: "#pager_${func_element.element_widget.widget_guid?string}",
	</#if>
	<#else>
	rowNum 	: 100000, 
	</#if>
	<#if func_element.element_widget.columnList?exists && func_element.element_widget.columnList?size != 0>  
    colModel:[
    	<#if func_element.element_widget.widget_paramMap?exists>
		<#if func_element.element_widget.widget_paramMap.treeGrid?exists && func_element.element_widget.widget_paramMap.treeGrid == 'true'>
		{   label : '',
			name : '',
			index : '',
			sortable : false, 
			resizable : false, 
			width:'35px',
			formatter:function (cellvalue, options, rowObject){
				return '<input type="checkbox" name="${widget_key?string}"  style="margin-left: 5px;" value="' + rowObject.${widget_key?string} + '" />';
			}
		},
		</#if><#rt/>
		</#if><#rt/>
    	<#list func_element.element_widget.columnList as columnModel>
    	{<#rt/>
    	<#lt/>align:'${columnModel.field_align?default('center')}',<#rt/>
    	<#--判断是否可编辑-->
    	<#if columnModel.field_editable?exists && columnModel.field_editable == '1'>
		<#lt/>editable:<#if (columnModel.field_editable?trim) == '1'>true<#else>false</#if>,<#rt/>
    	<#-- 
    		如： { editable:true,edittype:’select’,editoptions: {dataUrl:”${ctx}/admin/deplistforstu.action”}},这个是演示动态从服务器端获取数据 
			或 { editable:true,edittype:’select’,editoptions: {value:"zy:专业;dl:大类"}},这个静态数据
    	--><#rt/>
    	<#if columnModel.field_editoptions?exists && columnModel.field_editoptions?length gt 0><#rt/>
    	<#lt/>editoptions:${columnModel.field_editoptions?default('')},<#rt/>
    	</#if><#rt/>
    	<#--编辑的规则;如：{editable:true,editrules: {edithidden:true,required:true,number:true,minValue:10,maxValue:100}},
    		设定年龄的最大值为100，最小值为10，而且为数字类型，并且为必输字段
    	--><#rt/>
    	<#if columnModel.field_editrules?exists && columnModel.field_editrules?length gt 0><#rt/>
    	<#lt/>editrules:${columnModel.field_editrules?default('')},<#rt/>
    	</#if><#rt/>
    	<#--可以编辑的类型。可选值：text, textarea, select, checkbox, password, button, image, file--><#rt/>
    	<#if columnModel.field_edittype?exists && columnModel.field_edittype?length gt 0><#rt/>
    	<#lt/>edittype:'${columnModel.field_edittype?default('')}',<#rt/>
    	</#if><#rt/>
    	</#if><#rt/>
    	<#lt/>fixed:<#if (columnModel.field_fixed?trim) == '1'>true<#else>false</#if>,<#rt/>
    	<#lt/>hidden:<#if (columnModel.field_hidden?trim) == '1'>false<#else>true</#if>,<#rt/>
    	<#lt/>index:'${columnModel.field_index?string}',<#rt/>
    	<#lt/>key:${((columnModel.field_key?trim) == '1')?string('true','false')},<#rt/>
    	<#lt/>label:'${columnModel.field_label?string}',<#rt/>
    	<#lt/>name:'${columnModel.field_name?string}',<#rt/>
    	<#lt/>resizeable:<#if (columnModel.field_resizable?trim) == '1'>true<#else>false</#if>,<#rt/>
    	<#lt/>sortable:<#if (columnModel.field_sortable?trim) == '1'>true<#else>false</#if>,<#rt/>
    	<#--此功能属性添加到细胞的过程中创造的数据 - 即动态。例如表格单元格的所有有效的属性，可以使用或与不同性质的风格属性。函数应该返回字符串--><#rt/>
    	<#if columnModel.field_cellattr?exists && columnModel.field_cellattr?length gt 0><#rt/>
    	<#lt/>cellattr:(jQuery.isFunction(${columnModel.field_cellattr?html}) ? function(rowId, tv, rawObject, cm, rdata) {
    		return ${columnModel.field_cellattr?html}.call(this,rowId, tv, rawObject, cm, rdata);
    	} : '${columnModel.field_cellattr?html}'),<#rt/>
    	</#if><#rt/>
    	<#--【JQGrid组件】预设类型或用来格式化该列的自定义函数名。常用预设格式有：integer、date、currency、number等 ,
    		select,自定义字符串;如： {name:’myname’, edittype:’select’, formatter:’select’, editoptions:{value:"1:One;2:Two"}}--><#rt/>
    	<#if columnModel.field_formatter?exists && columnModel.field_formatter?length gt 0><#rt/>
    	<#lt/>formatter:(jQuery.isFunction(${columnModel.field_formatter?html}) ? function(cellvalue, options, rowObject){
    		return ${columnModel.field_formatter?html}.call(this,cellvalue, options, rowObject);
    	} : '${columnModel.field_formatter?html}'),<#rt/> 
    	</#if><#rt/>
    	<#lt/>width:'${columnModel.field_width?default('auto')}',<#rt/>
    	<#lt/>requestable:<#if (columnModel.field_param?trim) == '1'>true<#else>false</#if><#rt/>
    	<#lt/>}<#if columnModel_has_next>,</#if><#lt/>
    	</#list>
	],
	<#else>
	colModel:[],
	</#if>
	/*当选择行时触发此事件。rowid：当前行id；status：选择状态，当multiselect 为true时此参数才可用*/
	onSelectRow		: function(rowid,status){
		<#if func_element.element_widget.widget_paramMap?exists>
			<#if func_element.element_widget.widget_paramMap.treeGrid?exists && func_element.element_widget.widget_paramMap.treeGrid == 'true'>
			
			</#if>
		</#if>
	},
	/*向服务器发起请求时会把数据进行序列化，用户自定义数据也可以被提交到服务器端*/
	serializeGridData: function(postData){
		return postData;
	},
	/* 当表格所有数据都加载完成而且其他的处理也都完成时触发此事件，排序，翻页同样也会触发此事件 */
	gridComplete 	: function(){
		<#if parameters.funcModel.func_editable?exists && parameters.funcModel.func_editable == '1' >
		if($.fn.adapt){
			$("#element_${func_element.element_id}").adapt({
				buttons:[{
					btn_class	: "width-50",
					btn_text	:	"编辑JQGrid组件",
					/*按钮单击事件*/
					onBtnClick	: function(e){
						//弹窗
						$.showDialog(_path+"/design/designWidget_cxFuncElementWidgetIndex.html?func_widget_guid=${func_element.element_widget.func_widget_guid?string}",'编辑JQGrid组件',$.extend(true,{},modifyConfig,{
							"width"		: $("#yhgnPage").innerWidth() +"px",
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
													$("#topButton").click();
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
												$("#topButton").click();
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
	}
});
<#if parameters.opt_code == 'cx'>
$("#tabGrid_${parameters.funcModel.func_guid?string}").loadJqGrid(jqGrid_${parameters.funcModel.func_guid?string});
<#else>
$("#tabGrid_${func_element.element_widget.widget_guid?string}").loadJqGrid(jqGrid_${func_element.element_widget.widget_guid?string});
</#if>

<#if func_element.element_widget.widget_paramMap?exists>
<#if func_element.element_widget.widget_paramMap.treeGrid?exists && func_element.element_widget.widget_paramMap.treeGrid == 'true'>
/*委托点击事件：实现自动绑定*/
$(document).on('click.widget.data-api', "input[name='${widget_key?string}']", function (event) {
	//重置选中状态
	<#if parameters.opt_code == 'cx'>
	$("#tabGrid_${parameters.funcModel.func_guid?default("cx")}").resetSelection();
	<#else>
	$("#tabGrid_${func_element.element_widget.widget_guid?default("cx")}").resetSelection();
	</#if>
	//设置当前input选中
	$(this).prop("checked",true);
	//设置高亮样式
	$(this).closest("tr").addClass('ui-state-highlight');
	//选中当前行
	<#if parameters.opt_code == 'cx'>
	$("#tabGrid_${parameters.funcModel.func_guid?string}").setSelection($(this).val());
	<#else>
	$("#tabGrid_${func_element.element_widget.widget_guid?string}").setSelection($(this).val());
	</#if>
	//阻止冒泡
	//event.stopPropagation();
}).on('change.widget.data-api', "input[name='${widget_key?string}']", function (event) {
	if($(this).prop("checked")){
		$(this).closest("tr").addClass('ui-state-highlight');
	}
});
</#if>
</#if>
</#if> 
</#if>