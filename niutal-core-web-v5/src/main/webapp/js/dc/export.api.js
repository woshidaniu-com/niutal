;
(function($){
	$.extend({
		'customExport' : function(dcclbh, url, callback, options) {
			if(!$.founded(dcclbh)){
				$.error("参数错误【导出编号不能为空】");
				return;
			}
			if(!$.founded(url)){
				$.error("参数错误【导出地址不能为空】");
				return;
			}
			var exportConfig = {
					width		: "900px",
					modalName	: "exportModel",
					formName	: "exportForm",
					buttons		: {
						saveConfig:{
							label: "保 存 设 置",
							className:"btn-default",
							callback: function(){
								this.content.saveConfig();
								return false;
							}
						},
						success : {
							label : "导 出  E X C E L",
							className : "btn-primary",
							callback : function() {
								var $this = this;
								var opts = $this["options"]||{};
								var selectCol = doExport();
								if(selectCol){
									$('#exportWjgs').attr("name","exportWjgs");
									$('#exportWjgs').val("xls");
									var exportForm = $('#'+opts['formName'])
									exportForm.attr('action', url);
									exportForm.attr('method', 'post');
									exportForm.attr('target', '_blank');
									if($.isFunction(callback)){
										callback(exportForm);
									}
									exportForm.submit();
								}
								return false;
							}
						},
						exportDbf : {
							label : "导 出  DBF文件",
							className : "btn-primary",
							callback : function() {
								var $this = this;
								var opts = $this["options"]||{};
								var selectCol = doExport();
								if(selectCol){
									$('#exportWjgs').attr("name","exportWjgs");
									$('#exportWjgs').val("dbf");
									var exportForm = $('#'+opts['formName'])
									exportForm.attr('action', url);
									exportForm.attr('method', 'post');
									exportForm.attr('target', '_blank');
									if($.isFunction(callback)){
										callback(exportForm);
									}
									exportForm.submit();
								}
								return false;
							}
						}
					}
				};
			
			var _url = _path + '/niutal/dc/export/exportConfig.zf?dcclbh='+dcclbh;
			var _options = $.extend({},exportConfig,options||{});
			$.showDialog(_url,'自定义导出', _options);
		}
	});
})(jQuery);
