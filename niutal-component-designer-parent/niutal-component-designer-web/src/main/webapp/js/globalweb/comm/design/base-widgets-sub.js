jQuery(function($) {

	$('#parameter_pane').find("div.table-responsive").scrollTop(0);
	$('#resource_pane').find("div.table-responsive").scrollTop(0);
	
	var html1 = [];
		html1.push('<tr>');
			html1.push('<td class="align-center " valign="middle" style="padding: 4px;height: 18px;">');
				html1.push('<span  data-toggle="tooltip" data-placement="top" title="如果该名称为 类似 treeReader.level_field结构则表示该参数是JSON对象中的一个值" >');
					html1.push('<input type="text" class="form-control input-sm" name="parameters[0].param_name" placeholder="参数名称" validate="{required:true,stringMaxLength:300}"/>');
				html1.push('</span>');
			html1.push('</td>');
			html1.push('<td class="align-center " valign="middle" style="padding: 4px;height: 18px;">');
				html1.push('<span><input type="text" class="form-control input-sm" name="parameters[0].param_default" placeholder="参数默认值" validate="{stringMaxLength:300}"/></span>');
			html1.push('</td>');
			html1.push('<td class="align-center " valign="middle" style="padding: 4px;height: 18px;border-right: 0px;">');
				html1.push('<span><input type="text" class="form-control input-sm" name="parameters[0].param_desc" placeholder="组件参数描述" validate="{required:true,stringMaxLength:300}"/></span>');
			html1.push('</td>');
			html1.push('<td class="align-center " style="padding: 4px;height: 18px;border-left: 0px;"><button type="button" class="btn btn-default btn-sm btn-remove" role="button">删除</button></td>');
		html1.push('</tr>');

	$("#add_parameter").killFocus().click(function(e){
		
		//拷贝第一行的元素，并清空原有数据
		var copy = $(html1.join("")).clearForm();
		var tbody = $('#parameter_pane').find("tbody");
		$(tbody).find("tr.empty_row").remove();
		$(tbody).append(copy);
		//触发tooltip组件
		$('#parameter_pane').find('[data-toggle="tooltip"]').tooltip();
		//重新编号
		$(tbody).find("tr").each(function(i){
			$(this).find("td:eq(0)").find("input").attr("name","parameters["+i+"].param_name");
			$(this).find("td:eq(1)").find("input").attr("name","parameters["+i+"].param_default");
			$(this).find("td:eq(2)").find("input").attr("name","parameters["+i+"].param_desc");
		});
	});


	var html2 = [];
		html2.push('<tr>');
			html2.push('<td class="align-center " valign="middle" style="padding: 4px;height: 18px;">');
				html2.push('<span data-toggle="tooltip" data-placement="top" title="【组件js/css】来源;默认 0, 0：系统内（应用程序内）,1：系统外(v5样式服务)" >');
				html2.push('<select class="form-control input-sm" name="resources[0].resource_from"  validate="{required:true}">');
					html2.push('<option value="0" selected="selected">系统内（应用程序内）</option>');
					html2.push('<option value="1">系统外(v5样式服务)</option>');
				html2.push('</select>');
				html2.push('</span>');
			html2.push('</td>');
			html2.push('<td class="align-center " valign="middle" style="padding: 4px;height: 18px;">');
				html2.push('<span><input type="text" class="form-control input-sm" name="resources[0].resource_desc" placeholder="js/css资源描述" validate="{stringMaxLength:200}"/></span>');
			html2.push('</td>');
			html2.push('<td class="align-center " valign="middle" style="padding: 4px;height: 18px;border-right: 0px;">');
			html2.push('<input type="hidden" name="resources[0].resource_ordinal" value="1"/>');
				html2.push('<span><input type="text" class="form-control input-sm" name="resources[0].resource_href" placeholder="js/css资源请求路径;如：/js/jquery/validation/messages_zh.js" validate="{required:true,stringMaxLength:200}"/></span>');
			html2.push('</td>');
			html2.push('<td class="align-center " style="padding: 4px;height: 18px;border-left: 0px;"><button type="button" class="btn btn-default btn-sm btn-remove" role="button">删除</button></td>');
		html2.push('</tr>');
			
		
	$("#add_resource").killFocus().click(function(e){
		//拷贝第一行的元素，并清空原有数据
		var copy = $(html2.join("")).clearForm();
		var tbody = $('#resource_pane').find("tbody");

		$(tbody).find("tr.empty_row").remove();
		
		$(tbody).append(copy);
		//触发tooltip组件
		$('#resource_pane').find('[data-toggle="tooltip"]').tooltip();
		//重新编号
		$(tbody).find("tr").each(function(i){
			$(this).find("td:eq(0)").find("select").val("0").attr("name","resources["+i+"].resource_from");
			$(this).find("td:eq(1)").find("input").attr("name","resources["+i+"].resource_desc");
			$(this).find("td:eq(2)").find("input:eq(0)").attr("name","resources["+i+"].resource_ordinal").val( i + 1);
			$(this).find("td:eq(2)").find("input:eq(1)").attr("name","resources["+i+"].resource_href");
		});
	});
	
	$(document).off("click","#widget_tab_content button.btn-remove ").on("click","#widget_tab_content button.btn-remove ",function(e){
		var tbody = $(this).closest("tbody");
		$(this).closest("tr").remove();
		//如果没有行记录
		if($(tbody).find("tr").size() == 0){
			$(tbody).append('<tr class="empty_row"><td class="align-center " valign="middle" style="padding: 4px;height: 18px;line-height: 30px;" colspan="4">无记录!</td></tr>');
		}
		
	});
	
	$('#ajaxForm').validateForm( {
		//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			return true;
		}
	});
	
});