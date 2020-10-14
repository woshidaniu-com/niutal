<#if parameters.funcModel?exists>
	<#lt/>
	
	<#if parameters.button_list?exists && parameters.button_list?size != 0><#rt/>
	/*====================================按钮事件绑定开始====================================*/
	
	<#list parameters.button_list as buttonModel> 
	<#if buttonModel.button_visible == '1' && buttonModel.bindFunc?exists>
	
	//绑定${buttonModel.button_text?default("")}按钮事件
	jQuery("#${buttonModel.button_id?string}").unbind("click").click(function () {
		var tabGrid =  $("#tabGrid_${parameters.funcModel.func_guid?string}");
		var keys = $(tabGrid).getKeys();
		<#-- 【系统自定义功能】功能按钮点击前JQGrid列表选择数据行检查类型：默认：否；0：不检查，1：只能选一行，2：至少选择一行 -->
		<#if buttonModel.bindFunc.require_type == '1'>
		if(keys.length != 1){
		   $.alert('请先选定一条记录!');
		   return;
		}
		<#elseif buttonModel.bindFunc.require_type == '2' || (buttonModel.bindFunc.require_type == '0' && buttonModel.bindFunc.func_type == '6')>
		if(keys.length < 1){
		   $.alert('请先至少选定一条记录；可多选!');
		   return;
		}
		</#if>
		var requestMap = {
			"func_guid" : '${buttonModel.bindFunc.func_guid?string}'
		};
		var colModel = $.defined(jqGrid_${parameters.funcModel.func_guid?string})? jqGrid_${parameters.funcModel.func_guid?string}.colModel||[] : [];
		var row_index = 0;
		//获取JQGrid组件选中的数据行
		$(keys||[]).each(function(i,row_id){
			//获取每行数据
			var row = $(tabGrid).jqGrid('getRowData', row_id);
			//根据colModel来确定要获取的参数
			$(colModel||[]).each(function(j,col){
				if(!$.defined(col["formatter"]) && (col.key == true || col.requestable == true )){
					//组织请求参数条件：因为可能有中文，这里需要转码
					requestMap["requestList["+row_index+"].row."+col["name"]] = encodeURIComponent(row[col["name"]]||"");
				}
			});
			row_index = row_index +1;
		});
		//序列化与JQGrid组件关联的查询参数
		var related_guid = $(tabGrid).data("related_guid");
		if($.founded(related_guid) && $("#searchbox_"+related_guid).size() == 1){
			//这里注意：没有进行转码，因为在拼装参数时进行了转码操作
			var searchMap = $("#searchbox_"+related_guid).serializeJSON(false) ;
			//去除空的查询参数
			$.each(searchMap||{}, function(key, val){
				if(!($.founded(key)&&$.founded(val)&&(!$.isNumeric(key)))){
					delete searchMap[key];
				}
			});
			//拼装查询参数：因为可能有中文，这里需要转码
			$.each(searchMap||{}, function(key, val){
				requestMap["mapRow.row."+key] = encodeURIComponent(val ||"");
			});
		}
		
		<!-- 系统自定义功能类型 数据展示; 可选值 ：1:''数据展示'',2:''数据维护'',3:''预览打印'',41:''快速打印(Applet模式)'',42:''快速打印(Flash模式)'',43:''快速打印(PDF模式)'',5:''数据导出'',6:''数据删除'-->
		<#if buttonModel.bindFunc.func_type == '2'>
		<#-- 2:数据维护 -->
		//功能界面
		$.showDialog(_path+"/design/viewFunc_cxDesignFuncSubIndex.html",'${buttonModel.bindFunc.func_name?default("数据维护")}',$.extend({},modifyConfig,{
			"width" : ${buttonModel.bindFunc.func_width?default('($("#yhgnPage").innerWidth() - 100)')} +"px",
			"data"	: requestMap
		}));
		<#elseif  buttonModel.bindFunc.func_type == '3' || buttonModel.bindFunc.func_type == '41' || buttonModel.bindFunc.func_type == '42' || buttonModel.bindFunc.func_type == '43' >
			<#if buttonModel.bindFunc.report_guid?exists>
				<#-- 3:预览打印 ;41,/42/43:快速打印：打印格式：PDF,FLASH,APPLET-->
				if($.fn.validateForm && $.fn.valid &&  !$('#queryForm_${parameters.funcModel.func_guid}').valid()){
					return false;
				}
				<#-- 3:预览打印 -->
				<#if buttonModel.bindFunc.func_type == '3'>
			 	//构建form,报表预览
				$.buildForm("reportViewForm", _path+"/design/viewReport_cxFineReportViewIndex.html?_t"+new Date().getTime(),requestMap).submit();
				<#elseif buttonModel.bindFunc.func_type == '41'>
				<#-- 41:快速打印(Applet模式) -->
				requestMap["_format"] = "APPLET";
				delete requestMap["func_guid"];
				//构建form,单报表文件批量打印
				$.buildForm("batchPrintForm",  _path + "/design/viewReport_cxFineReportBatchOut.html?func_guid=${buttonModel.bindFunc.func_guid?string}&_t"+new Date().getTime(),$.extend({},requestMap,searchMap)).submit();
				<#elseif buttonModel.bindFunc.func_type == '42'>
				<#-- 42:快速打印(Flash模式) -->
				requestMap["_format"] = "FLASH";
				delete requestMap["func_guid"];
				//构建form,单报表文件批量打印
				$.buildForm("batchPrintForm",  _path + "/design/viewReport_cxFineReportBatchOut.html?func_guid=${buttonModel.bindFunc.func_guid?string}&_t"+new Date().getTime(),$.extend({},requestMap,searchMap)).submit();
				<#elseif buttonModel.bindFunc.func_type == '43' >
				<#-- 43:快速打印(PDF模式) -->
				requestMap["_format"] = "PDF";
				delete requestMap["func_guid"];
				<#--创建iframe -->
				if($("#${buttonModel.button_id?string}PrintFrame").size()<=0){
					$(document).find("body").append('<iframe id="${buttonModel.button_id?string}PrintFrame" name="${buttonModel.button_id?string}PrintFrame" style="display:block;heigth:1px;" frameborder="0" width="100%" height="1px" ></iframe>');
				}else{
					$("#${buttonModel.button_id?string}PrintFrame").empty();
				}
				//构建form,单报表文件批量打印
				$.buildForm("batchPrintForm",  _path + "/design/viewReport_cxFineReportBatchOut.html?func_guid=${buttonModel.bindFunc.func_guid?string}&_t"+new Date().getTime(),$.extend({},requestMap,searchMap),"${buttonModel.button_id?string}PrintFrame").submit();
				</#if>
			<#else>
				$.alert('未关联有效的报表信息!');
		  	 	return;
			</#if>
		<#elseif  buttonModel.bindFunc.func_type == '5'>
		<#-- 5:数据导出 -->
		if($.fn.validateForm && $.fn.valid &&  !$('#queryForm_${parameters.funcModel.func_guid}').valid()){
			return false;
		}
		//导出处理编号 规则:JW_模块编号_业务名称 如：JW_N155002_JSJXRLCX
		var dcclbh = $("#export_dcclbh").val();
		//序列化查询参数
		var related_guid = $(tabGrid).data("related_guid");
		var searchMap = {};
		if($.founded(related_guid) && $("#searchbox_"+related_guid).size() == 1){
			searchMap = $("#searchbox_"+related_guid).serializeJSON(false) ;
		}else{
			searchMap = $("#searchbox_${parameters.funcModel.func_guid}").serializeJSON(false) ;
		}
		//去除空的查询参数
		$.each(searchMap||{}, function(key, val){
			if(!($.founded(key)&&$.founded(val)&&(!$.isNumeric(key)))){
				delete searchMap[key];
			}
		});
		//扩展请求参数
		requestMap = $.extend(true,{},requestMap||{},searchMap||{});
		//用于查询要导出的数据
		$.exportDialog(_path + '/design/funcData_dcFuncDataList.html?func_widget_guid='+$(tabGrid).data("func_widget_guid"),dcclbh,requestMap ,colModel,"#tabGrid_${parameters.funcModel.func_guid?string}");
		<#elseif buttonModel.bindFunc.func_type == '6'>
		<#-- 6:数据删除 -->
		</#if>
		
	});
	
	</#if>						
	</#list>
	/*====================================按钮事件绑定结束====================================*/
	</#if>
</#if>