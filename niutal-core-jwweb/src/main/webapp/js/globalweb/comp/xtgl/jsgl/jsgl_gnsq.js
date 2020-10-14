jQuery(function($){
	
	var gnmkdmList = [];
	var css1 = ['col-md-3 col-sm-3','col-md-3 col-sm-3','col-md-4 col-sm-4'];
    var css2 = ['col-md-9 col-sm-9','col-md-9 col-sm-9','col-md-8 col-sm-8'];
	function initChild(index_c,rowObj){
		var tab_html = [];
			tab_html.push('<div class="col-md-12 col-sm-12 list_00" style="padding-left: 0px;background-color: #FFFFFF;">');
				//底层功能按钮
				var btnList = rowObj["btnList"];
				tab_html.push('<div class="'+(index_c == 1  ?  css1[1] : ($.founded(btnList) ? css1[2] : css1[1] ))+' list_0'+index_c+'" data-gnmkdm="'+rowObj["gnmkdm"]+'">');
				tab_html.push('<label class="control-label label_01">');
					if($.founded(rowObj["childList"])||$.founded(rowObj["btnList"])){
						tab_html.push('<input type="checkbox" name="gnmkdm" value="'+rowObj["gnmkdm"]+'"  />&nbsp;');
					}
				tab_html.push(rowObj["gnmkmc"] + '</label>');
				//有按钮子节点表示已经是最底层的菜单
				if($.founded(btnList)){
					tab_html.push('&nbsp;<label class="label_gnfx"><button type="button" class="btn btn-default btn-xs red2 ">[反选]</button></label>');
				}
				tab_html.push('</div>');
				
				tab_html.push('<div class="col-child '+(index_c == 1 ?  css2[1] : ($.founded(btnList) ? css2[2] : css2[1] ))+'" style="padding: 0px;">');
				//子级功能模块
				var childList = rowObj["childList"];
				//判断子级功能模块是否存在
				if($.founded(childList)){
					//循环子级功能模块
					$.each(childList||[],function(index,rowObj_2){
						tab_html.push(initChild(index_c+1,rowObj_2));
					});
				}else{
					//判断是否有按钮
					if($.founded(btnList)){
						tab_html.push('<ul data-gnmkdm="'+rowObj["gnmkdm"]+'">');
						//循环功能按钮
						$.each(btnList||[],function(index,rowObj_2){
							tab_html.push('<li ><div class="checkbox"><label class="label_02">');
							tab_html.push('<input type="checkbox" name="czdm" value="'+rowObj_2["czdm"]+'" '+((parseInt(rowObj_2["ischecked"]||'0') == 1) ? ' checked="checked" ' : "")+' />&nbsp;'+rowObj_2["czmc"]);
							tab_html.push("</label></div></li>");
						});
						
							
				    	tab_html.push('</ul>');
					}
				}
				tab_html.push('</div>');
			tab_html.push('</div>');
		return tab_html.join("");
	}
	
	function initTabContent(tab_pane){
		if($.founded(gnmkdmList)){
			//当前tab的顶级功能模块
			var gnmkdm = $(tab_pane).data("gnmkdm");
			var tab_html = [];
			var tab_flag = false;
			//循环功能菜单
			$.each(gnmkdmList,function(i,rowObj_1){
				//匹配当前一级功能模块
				if( gnmkdm == rowObj_1["gnmkdm"]){
					//二级功能模块
					var childList = rowObj_1["childList"];
					//判断二级功能模块是否存在
					if($.founded(childList)){
						//循环子级功能模块
						$.each(childList||[],function(index,rowObj_2){
							tab_html.push(initChild(1,rowObj_2));
						});
						tab_flag = true;
					}else{
						
					}
				}
			});
			if(tab_flag == false){
				tab_html.push('<div class="col-md-12 col-sm-12 align-center bigger-125"  style="padding: 50px;">无功能权限！</div>');
			}
			$(tab_pane).empty().append(tab_html.join(""));
			$(tab_pane).data("initialized",true);
			
			//如果全了则选中tab中的checkbox
			if($(tab_pane).find(":checkbox").size()!=0 && $(tab_pane).find(":checkbox").size() == $(tab_pane).find("input:checked").size()){
				$("li[data-gnmkdm='"+gnmkdm+"']").find(":checkbox").prop("checked",true);
			}
			$(tab_pane).find("ul").each(function(){
				//递归逐级向上查找改变选择状态
				(function(tab_right){
					while ($(tab_right).size() > 0 && !$(tab_right).hasClass("tab-pane")) {
						
						var tab_left = $(tab_right).prev();
						if($(tab_right).find("input:checked").size() > 0){
							$(tab_left).find(":checkbox").prop("checked",true);
						}else{
							$(tab_left).find(":checkbox").prop("checked",false);
						}
						//父级作为新的右侧div	
						tab_right = $(tab_left).parent().parent();
					}
				})($(this).closest(".col-child"));
			});
		}else{
			$(tab_pane).empty().html('<div class="col-md-12 col-sm-12 align-center bigger-125"  style="padding: 50px;">无功能权限！</div>');
		}
	}
	
	
	$("#tabContent").find("div:eq(0)").html('<div class="col-md-12 col-sm-12 align-center bigger-125"  style="padding: 50px;"><p class="text-center header smaller lighter"><i class="icon-spinner icon-spin orange  bigger-300"></i></br> 正在载入功能权限数据中.请等待....</p></div>');
	
	//查询功能代码
	$.getJSON( _path + "/xtgl/jsgnmk_cxJsgnmkDisplay.html?jsdm="+$("#jsdm").val(), {"gnmkdm":""}, function(data){
		//判断有无数据
		if($.isArray(data)){
			gnmkdmList = data;
			//初始化tab content
			$("#tabContent").find("div.tab-pane").each(function(i,tab_pane){
				initTabContent(tab_pane);
			});
			//默认显示第一个有选择的tab
			$('#nav_tabs li').each(function(){
				var li_item = this;
				var tab_pane = $(""+$(li_item).find("a:eq(0)").attr("href"));
				if($(tab_pane).find(":checkbox").size() > 0 ){
					// 切换模块
					$(li_item).find("a:eq(0)").tab('show');
					
					//初始化tab content
					$(tab_pane).children().each(function(i,row){
						$(row).css({'border-bottom': '1px dashed gray'});
						if($(row).children().eq(1).children().size() > 0){
							$(row).children().eq(1).children().last().children().eq(0).css({'border-bottom': '0px'});
							$(row).children().eq(0).height($(row).actual('innerHeight') -2);
						}
					});
					
					return false;
				}
			});
		}else{
			gnmkdmList = [];
			$("#tabContent").find("div.tab-pane").each(function(i,tab_pane){
				$(tab_pane).empty().html('<div class="col-md-12 col-sm-12 align-center bigger-125"  style="padding: 50px;">无功能权限！</div>');
			});
		}
	});
	
	//给每个tab多选框添加事件
	$('#nav_tabs li').each(function(){
		var li_item = this;
		var tab_pane = $(""+$(this).find("a:eq(0)").attr("href"));
		
		$(this).find(":checkbox").unbind().click(function(event){
			
			event.stopPropagation();   
			
			// 切换模块
			$(li_item).find("a:eq(0)").tab('show');
			
			//判断是否已经初始化过
	    	var initialized = $(tab_pane).data("initialized");
			if(initialized != true){
				//初始化tab content
				initTabContent(tab_pane)
			}
			
			$(tab_pane).find(":checkbox").prop("checked",$(this).prop("checked"));
		});
		
	});
	
	$('#nav_tabs a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
		  //e.target // activated tab
		  //e.relatedTarget // previous tab
		var tab_pane = $($(this).attr("href"));
		//判断是否已经初始化过
    	var initialized = $(tab_pane).data("initialized");
		if(initialized){
			//初始化tab content
			$(tab_pane).children().each(function(i,row){
				$(row).css({'border-bottom': '1px dashed gray'});
				if($(row).children().eq(1).children().size() > 0){
					$(row).children().eq(1).children().last().children().eq(0).css({'border-bottom': '0px'});
					$(row).children().eq(0).height($(row).actual('innerHeight') -2);
				}
			});
		}
	});
	
	//子模块勾选事件
	$(document).off('click', 'input[name="gnmkdm"]').on('click', 'input[name="gnmkdm"]', function (e) {
		
		var input = this;
		var tab_left = $(input).parent().parent();
		var tab_right = $(tab_left).next();
		//右侧子节点跟随该选择状态变化
		$(tab_right).find(":checkbox").prop("checked",$(input).prop("checked"));
		
		
		//大于两级节点时：
		var tab_parent = tab_left.parent().parent();
		if(!tab_parent.hasClass("tab-pane")){
			var tab_parent_left_checkbox = $(tab_parent).prev().find(":checkbox");
			$(tab_parent_left_checkbox).data("child-trigger",1);
			if($(tab_parent).find(":checkbox").size()!=0 &&  $(tab_parent).find(":checkbox").size() == $(tab_parent).find("input:checked").size()){
				$(tab_parent_left_checkbox).prop("checked",true);
			}else{
				$(tab_parent_left_checkbox).prop("checked",false);
			}
		}
		
		//当前tab卡片选项input同步选择逻辑
		var tab_pane = $("#tabContent").find("div.active");
		//当前tab的顶级功能模块
		var gnmkdm = $(tab_pane).data("gnmkdm");
		//如果全了则选中tab中的checkbox
		if($(tab_pane).find('input[name="gnmkdm"]').size()!=0 &&  $(tab_pane).find('input[name="gnmkdm"]').size() == $(tab_pane).find('input[name="gnmkdm"]:checked').size()){
			$("li[data-gnmkdm='"+gnmkdm+"']").find(":checkbox").prop("checked",true);
		}else{
			$("li[data-gnmkdm='"+gnmkdm+"']").find(":checkbox").prop("checked",false);
		}
	})
    //操作按钮勾选事件
	.off('click', 'input[name="czdm"]').on('click', 'input[name="czdm"]', function (event) {
		var input = this;
		//递归逐级向上查找改变选择状态
		(function(tab_right){
			while ($(tab_right).size() > 0 && !$(tab_right).hasClass("tab-pane")) {
				var tab_left = $(tab_right).prev();
				if($(tab_right).find("input:checked").size() > 0){
					$(tab_left).find(":checkbox").prop("checked",true);
				}else{
					$(tab_left).find(":checkbox").prop("checked",false);
				}
				//父级作为新的右侧div	
				tab_right = $(tab_left).parent().parent();
			}
		})($(input).closest(".col-child"));
		
		//当前tab卡片选项input同步选择逻辑
		var tab_pane = $("#tabContent").find("div.active");
		//当前tab的顶级功能模块
		var gnmkdm = $(tab_pane).data("gnmkdm");
		//如果全了则选中tab中的checkbox
		if($(tab_pane).find(":checkbox").size()!=0 &&  $(tab_pane).find(":checkbox").size() == $(tab_pane).find("input:checked").size()){
			$("li[data-gnmkdm='"+gnmkdm+"']").find(":checkbox").prop("checked",true);
		}else{
			$("li[data-gnmkdm='"+gnmkdm+"']").find(":checkbox").prop("checked",false);
		}
	})
	 //反选事件
	.off('click', 'label.label_gnfx').on('click', 'label.label_gnfx', function (event) {
		var checkboxs = $(this).parent().next().find(":checkbox");
		$(checkboxs).each(function(){
			if($(this).prop("checked")){
				$(this).prop("checked",false);
			}else{
				$(this).prop("checked",true);
			}
		});
	});

	
	$("#btn_qx").click(function(){
		$(":checkbox").filter("input[name!='jsdm_id']").each(function(index,checkbox){
			if($(checkbox).prop("disabled") == false){
				$(checkbox).prop("checked",true);
			}
		});
	});
	
	$("#btn_cz").click(function(){
		$(":checkbox").filter("input[name!='jsdm_id']").each(function(index,checkbox){
			if($(checkbox).prop("disabled") == false){
				$(checkbox).prop("checked",false);
			}
		});	
	});
	
	
	
});

//保存功能授权信息
function bcJsgnsqxx(callback){
	
	var requestMap = {
		"jsdm" : $("#jsdm").val()
	};
	
	var index = 0;
	$("#tabContent").find("div.tab-pane").each(function(i,tab_pane){
		//取得所有的功能操作组,并且进行循环
		$(tab_pane).find("ul").each(function(i2,ul_element){
			//判断当前组是否有选择的操作按钮
			if($(ul_element).find("input:checked").size() > 0){
				//当前功能操作组的功能模块代码
				requestMap["childList["+index+"].gnmkdm"] = $(ul_element).data("gnmkdm");
				//已选操作按钮
				$(ul_element).find("input:checked").each(function(input_index,input){
					requestMap["childList["+index+"].gnmkdm_list["+input_index+"]"] = $(input).val();
					
				});
                index ++;
			}
		});
	});
	//提交请求
	jQuery.ajaxSetup({async:true});
	jQuery.post( _path + "/xtgl/jsgnmk_jsgnsqUpdate.html", requestMap, function(message){
		callback.call(this,message);
	}, "json",{
		 //显示提交进度的状态条元素
		 progressElement	: "#bootboxStatus"
	});
}

