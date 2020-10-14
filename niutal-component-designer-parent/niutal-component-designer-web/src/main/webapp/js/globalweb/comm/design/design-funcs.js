jQuery(function($) {
	
	var btns = $("#tab_btns").find(".btn");
	$(btns).click(function(){
		var btn = this;
		btns.removeClass("btn-primary");
		$(btn).addClass("btn-primary");
	
		var tab_pane = $("#tab_content").find("div.tab-pane:eq("+$(this).index()+")");
		//计算位置
		var margin_top	=	($(tab_pane).innerHeight() - 200)/2;
			margin_top	= 	(margin_top>0) ? margin_top : 0;
		var func_code = $(btn).data("func_code");
		$(tab_pane).html('<p id="loading_status" class="text-center header smaller lighter" style="margin-top:'+margin_top+'px;"><i class="icon-spinner icon-spin orange  bigger-500"></i></br> 网页正在载入数据中.请等待....</p>');
		window.setTimeout(function(){
			//加载页面
			$(tab_pane).load( _path + "/design/designFunc_cxDesignFuncDisplay.html",{"func_code":func_code},function(responseText, textStatus, xmlhttprequest){
				//this;在这里this指向的是当前的DOM对象，即$(".ajax.load")[0] 
				//alert(responseText);//请求返回的内容
				//alert(textStatus);//请求状态：success，error
				//alert(XMLHttpRequest);//XMLHttpRequest对象
				//$(tab_pane).find('[data-toggle="tooltip"]').tooltip("destroy").tooltip({"container" : tab_pane});
				$(tab_pane).bootstrapTree("destroy").bootstrapTree();
			
				$("button.btn-define-edit").unbind("click").click(function(event){
					event.stopImmediatePropagation(); 
					var func_code = $(this).data("func_code");
					var opt_code = $(this).data("opt_code");
					var btn = this;
					if($.founded(func_code)&&$.founded(opt_code)){
						//弹窗
						$.showDialog(_path+"/design/designFunc_xgzdycdMenu.html","修改功能菜单",$.extend(true,{},modifyConfig,{
							"width"		: "700px",
							"modalName"	: "editDesign",
							"data"		: {
								"func_guid"	: $(btn).data("func_guid"),
								"func_code"	: func_code,
								"opt_code"	: opt_code
							},
							"buttons"	: {
								success : {
									label : "确 定",
									className : "btn-primary",
									callback : function() {
										var $this = this;
										//this.close();
										submitForm("ajaxForm",function(responseText,statusText){
											// responseText 可能是 xmlDoc, jsonObj, html, text, 等等...
											// statusText 	描述状态的字符串（可能值："No Transport"、"timeout"、"notmodified"---304 "、"parsererror"、"success"、"error"
											if(responseText.indexOf("成功") > -1){
												$("#tab_btns").find("button.btn-primary").trigger("click");
												$.success(responseText,function() {
													$.closeModal("editDesign");
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
				});
				$("button.btn-design-edit").unbind("click").click(function(event){
					event.stopImmediatePropagation(); 
					var func_code = $(this).data("func_code");
					var opt_code = $(this).data("opt_code");
					var btn = this;
					if($.founded(func_code)&&$.founded(opt_code)){
						var func_url 	= _path+"/design/designFunc_xgzdyanFuncOpt.html";
						var func_title	= "修改功能按钮";
						var width 		= "700px";
						if(opt_code == "cx"){
							func_url 	= _path+"/design/designFunc_xgzdycdFuncMenu.html";
							func_title	= "修改自定义功能";
							width 		= "850px";
						}
						//弹窗
						$.showDialog(func_url,func_title,$.extend(true,{},modifyConfig,{
							"width"		: width,
							"modalName"	: "editDesign",
							"data"		: {
								"func_guid"	: $(btn).data("func_guid"),
								"func_code"	: func_code,
								"opt_code"	: opt_code
							},
							"buttons"	: {
								success : {
									label : "确 定",
									className : "btn-primary",
									callback : function() {
										var $this = this;
										//this.close();
										submitForm("ajaxForm",function(responseText,statusText){
											// responseText 可能是 xmlDoc, jsonObj, html, text, 等等...
											// statusText 	描述状态的字符串（可能值："No Transport"、"timeout"、"notmodified"---304 "、"parsererror"、"success"、"error"
											if(responseText.indexOf("成功") > -1){
												$("#tab_btns").find("button.btn-primary").trigger("click");
												$.success(responseText,function() {
													$.closeModal("editDesign");
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
				});
				$("button.btn-design-bind").unbind("click").click(function(event){
					event.stopImmediatePropagation(); 
					var func_code = $(this).data("func_code");
					var opt_code = $(this).data("opt_code");
					var btn = this;
					if($.founded(func_code)&&$.founded(opt_code)){
						var func_guid = $(this).data("func_guid");
						if($.founded(func_guid)){
							//弹窗
							$.showDialog(_path+"/design/designFunc_xgzdyanFuncSubPage.html","编辑按钮自定义功能",$.extend(true,{},modifyConfig,{
								"width"		: "900px",
								"modalName"	: "funcMenuDesign",
								"offAtOnce"	: true,
								"data"		: { 
									"func_guid"			: $(btn).data("func_guid"),
									"func_name"			: $(btn).data("func_name"),
									"btn_text"			: $(btn).data("btn_text")
								},
								buttons:{
									success : {
										label : "确 定",
										className : "btn-primary",
										callback : function() {
											var $this = this;
											//this.close();
											submitForm("ajaxForm",function(responseText,statusText){
												// responseText 可能是 xmlDoc, jsonObj, html, text, 等等...
												// statusText 	描述状态的字符串（可能值："No Transport"、"timeout"、"notmodified"---304 "、"parsererror"、"success"、"error"
												if(responseText.indexOf("成功") > -1){
													$("#tab_btns").find("button.btn-primary").trigger("click");
													$.success(responseText,function() {
														$.closeModal("funcMenuDesign");
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
						}else{
							//弹窗
							$.showDialog(_path+"/design/designFunc_zjzdyanFuncSubPage.html","绑定按钮自定义功能",$.extend(true,{},modifyConfig,{
								"width"		: "900px",
								"modalName"	: "funcMenuDesign",
								"offAtOnce"	: true,
								"data"		: {
									"func_code"			: func_code,
									"opt_code"			: opt_code,
									"func_name"			: $(btn).data("func_name"),
									"btn_text"			: $(btn).data("btn_text")
								},
								buttons:{
									success : {
										label : "确 定",
										className : "btn-primary",
										callback : function() {
											var $this = this;
											//this.close();
											submitForm("ajaxForm",function(responseText,statusText){
												// responseText 可能是 xmlDoc, jsonObj, html, text, 等等...
												// statusText 	描述状态的字符串（可能值："No Transport"、"timeout"、"notmodified"---304 "、"parsererror"、"success"、"error"
												if(responseText.indexOf("成功") > -1){
													$("#tab_btns").find("button.btn-primary").trigger("click");
													$.success(responseText,function() {
														$.closeModal("funcMenuDesign");
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
					}
				});
				$("button.btn-design-unbind").unbind("click").click(function(event){
					event.stopImmediatePropagation(); 
					var func_guid = $(this).data("func_guid");
					var btn = this;
					if($.founded(func_guid)){
						$.confirm('继续将删除功能按钮上绑定的功能页面数据！是否继续？',function(result) {
							if(result){
								$.ajax({
									type	: "POST",
									url		: _path+"/design/designFunc_sczdyanFuncSubPageData.html",
									async	: false ,
									data	: { "func_guid"	: func_guid },
									success	: function(responseText){
										
										if(responseText.indexOf("成功") > -1){
											$.success(responseText,function() {
												$("#tab_btns").find("button.btn-primary").trigger("click");
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
				});
				$("button.btn-design-globe").unbind("click").click(function(event){
					event.stopImmediatePropagation(); 
					var func_code = $(this).data("func_code");
					var func_name = $(this).data("func_name");
					var opt_code = $(this).data("opt_code");
					//构建form
					/*var form = $.buildForm("globeForm",'../xtgl/init_cxGnPage.html',{
						'gnmkdm'	: func_code,
						'dyym'		: '/design/viewFunc_cxDesignFuncPageIndex.html',
						'gnmkmc'	: encodeURIComponent(func_name),
						'sfgnym'	: "1"
					});*/
					
					var form = $.buildForm("funcForm",'../design/viewFunc_cxDesignFuncPageIndex.html',{
						"layout"	: "default",
						'gnmkdm'	: func_code,
						'dyym'		: '/design/viewFunc_cxDesignFuncPageIndex.html',
						'gnmkmc'	: encodeURIComponent(func_name),
						'sfgnym'	: "1"
					});
					$(form).submit();
				});
				$("button.btn-design-sqlscript").unbind("click").click(function(event){
					event.stopImmediatePropagation(); 
					var btn = this;
					//构建form
					var form = $.buildForm("sqlscriptForm",_path+"/design/designFunc_xzzdycdFuncSQLScript.html",{
						"func_guid"	: $(btn).data("func_guid"),
						'func_code'	: $(btn).data("func_code"),
						'opt_code'	: $(btn).data("opt_code"),
						'func_name'	: encodeURIComponent($(btn).data("func_name"))
					});
					$(form).submit();
				});
				$("button.btn-design-repeat").unbind("click").click(function(event){
					event.stopImmediatePropagation(); 
					var func_guid = $(this).data("func_guid");
					var btn = this;
					if($.founded(func_guid)){
						$.confirm('继续将重新生成该自定义功能页面！是否继续？',function(result) {
							if(result){
								$.ajax({
									type	: "POST",
									url		: _path+"/design/designFunc_sczdyymFuncMenu.html",
									async	: false,
									data	: {
										"menuModel.func_guid" : func_guid
									},
									success	: function(responseText){
										
										if(responseText.indexOf("成功") > -1){
											$.success(responseText,function() {
												
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
				});
				$("button.btn-define-remove,button.btn-design-remove").unbind("click").click(function(event){
					event.stopImmediatePropagation(); 
					var func_code = $(this).data("func_code");
					var opt_code = $(this).data("opt_code");
					if($.founded(func_code)&&$.founded(opt_code)){
						$.confirm('继续将删除所有有关该自定义功能的数据！是否继续？',function(result) {
							if(result){
								var func_url = _path+"/design/designFunc_sczdycdFuncMenu.html";
								if(opt_code != "cx"){
									func_url = _path+"/design/designFunc_sczdyanFuncOpt.html";
								}
								$.ajax({
									type	: "POST",
									url		: func_url,
									async	: false ,
									data	: {
										"func_code"	: func_code,
										"opt_code"	: opt_code
									},
									success	: function(responseText){
										
										if(responseText.indexOf("成功") > -1){
											$.success(responseText,function() {
												$(btn).click();
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
				});
			},"html");
		}, 1000);
		
	});
	$(btns[0]).click();
	
	//监听更多按钮点击事件
	$(document).off("click", "span.btn-define-plus").on("click","span.btn-define-plus",function(event){
		event.stopImmediatePropagation(); 
		var parent_code = $(this).data("parent_code");
		var opt_code = $(this).data("opt_code");
		var btn = this;
		if($.founded(parent_code)){
			var isSaved = false;
			//弹窗
			$.showDialog(_path+"/design/designFunc_zjzdycdMenu.html","增加子菜单",$.extend(true,{},modifyConfig,{
				"width"		: "700px",
				"modalName"	: "menuDesign",
				"offAtOnce"	: true,
				"data"		: {
					"parent_func_code"	: parent_code,
					"parent_func_name"	: $(btn).data("parent_name"),
					"func_level"		: $(btn).data("func_level"),
					"max_ordinal"		: $(btn).data("max_ordinal"),
					"opt_code"			: ($.founded(opt_code) ? opt_code : "cx")
				},
				buttons:{
					success : {
						label : "确 定",
						className : "btn-primary",
						callback : function() {
							var $this = this;
							//this.close();
							submitForm("ajaxForm",function(responseText,statusText){
								// responseText 可能是 xmlDoc, jsonObj, html, text, 等等...
								// statusText 	描述状态的字符串（可能值："No Transport"、"timeout"、"notmodified"---304 "、"parsererror"、"success"、"error"
								if(responseText.indexOf("成功") > -1){
									$("#tab_btns").find("button.btn-primary").trigger("click");
									$.success(responseText,function() {
										$.closeModal("menuDesign");
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
	}).off("click", "span.btn-design-plus").on("click","span.btn-design-plus",function(event){
		event.stopImmediatePropagation(); 
		var parent_code = $(this).data("parent_code");
		var opt_code = $(this).data("opt_code");
		var btn = this;
		if($.founded(parent_code)){
			var isSaved = false;
			//弹窗
			$.showDialog(_path+"/design/designFunc_zjzdycdFuncMenu.html","增加自定义功能",$.extend(true,{},modifyConfig,{
				"width"		: "850px",
				"modalName"	: "funcMenuDesign",
				"offAtOnce"	: true,
				"data"		: {
					"parent_func_code"	: parent_code,
					"parent_func_name"	: $(btn).data("parent_name"),
					"func_level"		: $(btn).data("func_level"),
					"max_ordinal"		: $(btn).data("max_ordinal"),
					"opt_code"			: ($.founded(opt_code) ? opt_code : "cx")
				},
				buttons:{
					success : {
						label : "确 定",
						className : "btn-primary",
						callback : function() {
							var $this = this;
							//this.close();
							submitForm("ajaxForm",function(responseText,statusText){
								// responseText 可能是 xmlDoc, jsonObj, html, text, 等等...
								// statusText 	描述状态的字符串（可能值："No Transport"、"timeout"、"notmodified"---304 "、"parsererror"、"success"、"error"
								if(responseText.indexOf("成功") > -1){
									$("#tab_btns").find("button.btn-primary").trigger("click");
									$.success(responseText,function() {
										$.closeModal("funcMenuDesign");
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
	}).off("click", "span.btn-opt-plus").on("click","span.btn-opt-plus",function(event){
		event.stopImmediatePropagation(); 
		var parent_code = $(this).data("parent_code");
		var opt_code = $(this).data("opt_code");
		var btn = this;
		if($.founded(parent_code)){
			var isSaved = false;
			//弹窗
			$.showDialog(_path+"/design/designFunc_zjzdyanFuncOpt.html","增加功能按钮",$.extend(true,{},modifyConfig,{
				"width"		: "700px",
				"modalName"	: "funcOptDesign",
				"offAtOnce"	: true,
				"data"		: {
					"parent_func_code"	: parent_code,
					"parent_func_name"	: $(btn).data("parent_name"),
					"func_level"		: $(btn).data("func_level"),
					"max_ordinal"		: $(btn).data("max_ordinal"),
					"opt_code"			: ($.founded(opt_code) ? opt_code : "cx")
				},
				buttons:{
					success : {
						label : "确 定",
						className : "btn-primary",
						callback : function() {
							var $this = this;
							//this.close();
							submitForm("ajaxForm",function(responseText,statusText){
								// responseText 可能是 xmlDoc, jsonObj, html, text, 等等...
								// statusText 	描述状态的字符串（可能值："No Transport"、"timeout"、"notmodified"---304 "、"parsererror"、"success"、"error"
								if(responseText.indexOf("成功") > -1){
									$("#tab_btns").find("button.btn-primary").trigger("click");
									$.success(responseText,function() {
										$.closeModal("funcOptDesign");
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
	});
	
});