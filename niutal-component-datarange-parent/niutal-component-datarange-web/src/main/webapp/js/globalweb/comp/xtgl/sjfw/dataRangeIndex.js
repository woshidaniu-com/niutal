//初始化页面
jQuery(function($) {
	
	
	var dataRangeGrid = jQuery.extend(true,{},BaseJqGrid,{  
		resizeHandle:"#sl_all_form",
	    url: _path + "/datarange/yhjssjfw_cxSjfwsz.html", //这是Action的请求地址  
	    postData:{"kzdx":$("#kzdx").val()},
	    datatype : "local",
	    multiselect : false,
	    colModel:[
	    	 {label:'用户名',name:'yhm', index: 'yhm',align:'left',width:'200px',key:true},
		     {label:'姓 名',name:'xm', index: 'xm',align:'center',width:'120px'},
		     {label:'所属机构',name:'jgmc', index: 'jgmc',align:'left',width:'200px'},
	         {label:'&nbsp;<input type="checkbox" id="allDataRange" />&nbsp;&nbsp;用户角色数据范围',
		    	 name:'sjfwList', index: '',align:'left',sortable:false,width:'775px',
		    	 formatter:function(cellvalue, options, rowObject){
		    	 	 if($.isArray(cellvalue)&&cellvalue.length>0){
		    	 		 var html = [];
			    		 html.push('<ul class="item-list">');
		    	 		 $.each(cellvalue,function(i,map){
		    	 			 html.push('<li>');
		    	 			 	html.push('<div class="row">');
		    	 			 		html.push('<div class="col-xs-2">');
		    	 			 			html.push('<label class="inline left">');
		    	 			 			html.push('<input type="checkbox" name="yhm_jsdm" value="'+map["jsdm"]+'" data-yhm="'+rowObject.yhm+'" />&nbsp;');
		    	 			 			html.push(map["jsmc"]);
		    	 			 			html.push('</label>');
				    	 			html.push('</div>');
				    	 			html.push('<div class="col-xs-10">');
				    	 			if($.founded(map["sjfwmcList"])){
				    	 				var length = map["sjfwmcList"].length;
				    	 				if(length==1){
				    	 					html.push(map["sjfwmcList"][0]);
				    	 				}else{
				    	 					$.each(map["sjfwmcList"]||[],function(i,sjfwmc){
				    	 						html.push('<div class="col-xs-12 '+(i>0 ? "hide" :"")+'" title="'+sjfwmc+'">');
					    	 					html.push(sjfwmc);
					    	 					html.push("</div>");
					    	 				});
				    	 					if(length>1){
				    	 						html.push('<div class="col-xs-12 item-more" >');
					    	 					html.push('<button type="button" class="btn btn-default btn-xs icon-chevron-up">更多</button>');
					    	 					html.push("</div>");
				    	 					}
				    	 				}
				    	 			}else{
				    	 				html.push("未设置数据范围!");
				    	 			}
				    	 			html.push('</div>');
		    	 			 	html.push('</div>');
				    		 html.push("</li>");
		    	 		 });
		    	 		 html.push("</ul>");
			    		 return html.join("");
		    	 	 }else{
		    			 return "";
			    	 }
		  	 	}
	         }
		],
		sortname: 'yhm', //首次加载要进行排序的字段 
	 	sortorder: "desc",
	 	gridComplete:function(){
			$("#tabGrid2").find(".jqgrow").each(function(){
				
				$(this).find("td").removeAttr("title");
				//绑定更多的事情
				$(this).find(".item-more").each(function(i,item_more){
					
					$(item_more).find("button:eq(0)").click(function(event){
						event.stopPropagation();
						//已经显示
						if($(item_more).prevAll(".hide").size()==0){
							$(this).removeClass("icon-chevron-down").addClass("icon-chevron-up");
							$(item_more).prevAll(".show").removeClass("show").addClass("hide");
						}else{
							$(this).removeClass("icon-chevron-up").addClass("icon-chevron-down");
							/*$("#tabGrid2").find(".jqgrow").each(function(){
								$(this).find(".col-xs-12.show").removeClass("show").addClass("hide");
							});*/
							$(item_more).prevAll(".hide").removeClass("hide").addClass("show");
						}
					});
					
				});
				
			});
			
			$.each($("#tabGrid2").getDataIDs(),function(i,rowid){
				if($("#"+rowid).find("ul").size()>0){
					var size = $("#"+rowid).find("ul > li").size();
					$("#"+rowid).find("td").css("padding","0px");
					$("#"+rowid).find("ul > li:last").css("border-bottom","0px");
					$("#"+rowid).find("ul > li").each(function(index,li){
						$(li).click(function(event){
							
							var status =  $(this).find(":checkbox").first().prop("checked");
							if(status){
								$(li).removeClass("active");
								$(this).find(":checkbox").prop("checked",false);
							}else{
								$(li).addClass("active");
								$(this).find(":checkbox").prop("checked",true);
							}
							var checkedSize = $("#"+rowid).find(":checked").size();
							//触发选择当前行的事件
							if(size==0){
								$("#tabGrid2").setSelection(rowid);
							}
							if(checkedSize == 0){
								$("#"+rowid).removeClass("ui-state-highlight");
							}else if(checkedSize == size){
								$("#"+rowid).addClass("ui-state-highlight");
							}
							event.stopPropagation();
						});
						$(li).find("label").click(function(event){
							
							var status =  $(this).find(":checkbox").first().prop("checked");
							if(status){
								$(li).removeClass("active");
								$(this).find(":checkbox").prop("checked",false);
							}else{
								$(li).addClass("active");
								$(this).find(":checkbox").prop("checked",true);
							}
							//触发选择当前行的事件
							if(size==0){
								$("#tabGrid2").setSelection(rowid);
							}
							var checkedSize = $("#"+rowid).find(":checked").size();
							if(checkedSize == 0){
								$("#"+rowid).removeClass("ui-state-highlight");
							}else if(checkedSize == size){
								$("#"+rowid).addClass("ui-state-highlight");
							}
						});
						$(li).find(":checkbox").click(function(event){
							var checkedSize = $("#"+rowid).find(":checked").size();
							var status =  $(this).prop("checked");
							if(status){
								$(li).addClass("active");
							}else{
								$(li).removeClass("active");
							}
							//触发选择当前行的事件
							if(size==0){
								$("#tabGrid2").setSelection(rowid);
							}
							if(checkedSize == 0){
								$("#"+rowid).removeClass("ui-state-highlight");
							}else if(checkedSize == size){
								$("#"+rowid).addClass("ui-state-highlight");
							}
							event.stopPropagation();
						});
					});
				}
			});
			$("#allDataRange").prop("checked",false);
			$("#allDataRange").click(function(event){
				var status =  $(this).prop("checked");
				$("#tabGrid2").resetSelection();
				$.each($("#tabGrid2").getDataIDs(),function(i,rowid){
					var size = $("#"+rowid).find("ul > li").size();
					if(status){
						$("#"+rowid).find("ul > li").addClass("active");
					}else{
						$("#"+rowid).find("ul > li").removeClass("active");
					}
					$("#"+rowid).find(":checkbox").prop("checked",status);
					//触发选择当前行的事件
					if(size==0){
						$("#tabGrid2").setSelection(rowid);
					}
				});
				event.stopPropagation();
			});
			
			
			return true;
		},
		/*触发选中事件*/
		onSelectRow: function(rowid,status){
			$("#"+rowid).find(":checkbox").prop("checked", true);
	   	}
	});

	$("#tabGrid2").loadJqGrid(dataRangeGrid);

	/*====================================================绑定按钮事件====================================================*/
	
	var urlMap = {
		"xs":_path + "/datarange/yhjssjfw_cxSjfwszSetting.html",
		"kc":_path + "/datarange/yhjssjfw_cxKcsjfwszSetting.html",
		"js":_path + "/datarange/yhjssjfw_cxJsbmSjfwszSetting.html",
		"cd":_path + "/datarange/yhjssjfw_cxCdtgbmSjfwszSetting.html"
	};
	
	//数据范围设置
	$("#btn_sjsq").click(function(){
		var jsdm = jQuery('#jsdm_query').val();
		if (!$.founded(jsdm)) {
			$("#jsdm_query").errorClass("请选择所属角色!","top","left");
			return;
		}
		if($("input[name=yhm_jsdm]").size() == 0){
			return ;
		}
		var checkeds = $("input[name=yhm_jsdm]:checked");
		if(checkeds.size() == 0){
			$.alert('请选择用户角色！');
			return ;
		}
		if(checkeds.size() > 1){
			$.alert('请选择单个用户角色！');
			return ;
		}
		var params = [];
		$(checkeds).each(function(index,checked){
			params.push("yhmList[" + index + "]=" + $(checked).data("yhm"));
		});
		params.push("js_id=" + jsdm);
		
		// 批量数据授权
		$.showDialog(urlMap[$("#kzdx").val()] + '?' + params.join("&"),'数据授权',{ 
			width: $("#yhgnPage").innerWidth()+"px",
			modalName:"modifyModal",
			//是否需要进度条
			progress	: true,
			buttons:{
				success : {
					label : "授	权",
					className : "btn-primary",
					callback : function() {
						this.content.submitDataRange(function(responseText){
							if(responseText.indexOf("成功") > -1){
								$.success(responseText,function() {
									$.closeModal("modifyModal");
									$("#tabGrid2").searchGrid(paramMap());
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
		});
		
	});
	
	//批量数据范围设置
	$("#btn_plsjsq").click(function(){
		var jsdm = jQuery('#jsdm_query').val();
		if (!$.founded(jsdm)) {
			$("#jsdm_query").errorClass("请选择所属角色!");
			return;
		}
		if($("input[name=yhm_jsdm]").size() == 0){
			return ;
		}
		var checkeds = $("input[name=yhm_jsdm]:checked");
		if(checkeds.size() == 0){
			$.alert('请选择用户角色！');
			return ;
		}
		var params = [];
		$(checkeds).each(function(index,checked){
			params.push("yhmList[" + index + "]=" + $(checked).data("yhm"));
		});
		params.push("js_id=" + jsdm);
		// 批量数据授权
		$.showDialog(urlMap[$("#kzdx").val()] + '?'  + params.join("&"),'批量数据授权', {
			width: $("#yhgnPage").innerWidth()+"px",
			modalName:"modifyModal",
			//是否需要进度条
			progress	: true,
			buttons:{
				success : {
					label : "授	权",
					className : "btn-primary",
					callback : function() {
						this.content.submitDataRange(function(message){
							$.alert(message,function(){
								$.closeModal("modifyModal");
							});
							$("#tabGrid2").searchGrid(paramMap());
						});
						return false;
					}
				},
				cancel : {
					label : "关 闭",
					className : "btn-default"
				}
			}
		});
		
	});
	
	function paramMap(){
		return {
			"kzdx" 	:	$("#kzdx").val(),
			"jsdm"  : 	$('#jsdm_query').val(),
			"jgdm"  : 	$('#jgdm_query').val(),
			"yhm"  : 	$('#yhm_query').val()
		};
	}
	
	$("#search_go").click(function(){
		var jsdm = jQuery('#jsdm_query').val();
		if (!$.founded(jsdm)) {
			$("#jsdm_query").errorClass("请选择所属角色!");
			return;
		}
		$("#jsdm_query").successClass();
		$("#tabGrid2").searchGrid(paramMap());
	});
	
});