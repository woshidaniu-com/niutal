var tables = ["SCHOOL","niutal_XTGL_BMDMB","niutal_XTGL_ZYDMB","niutal_XTGL_BJDMB"];
// 你可以在这里继续使用$作为别名...
jQuery(function($) {
	//下拉菜单切换状态记录；解决两下拉菜单有附属关系，一个改变另个同时改变情况下，多次调用change事件的问题
	//0：无下拉框被改变；1：有下拉框在改变，且数据范围重新加载
	var changeStatus = 0;
	//页面数据范围对象缓存，解决页面按不同方式选择数据范围时，来回切换状态的同步
	var dataRanges = {};
	var isHaveDataRange = false;
	//Ajax请求选择用户的数据范围：在选择单个用户时，才有数据返回
	ajaxDataRangeSync();
	
	//绑定数据范围对象切换事件
	$("#displayBox").find("li").each(function(index,liElement){
		//绑定点击事件
		$(liElement).click(function(event){
			//1.初始化切换选中效果
			jQuery(liElement).parent("ul").find("li").each(function(index,li){
				jQuery(li).find("a").removeClass("currentDiv");
			});
			jQuery(this).find("a").addClass("currentDiv");
			
			var a =  $($(this).find("a")[0]);
			var bm = $(a).attr("bm");
			//2.隐藏每个数据范围明细区域
			jQuery("div.dataRangeDetail").each(function(i,n) {
				$(n).hide();
			});
			//取得数据范围区域元素
			var dataRangeDetail = jQuery("div.dataRangeDetail[name="+bm+"]");
			// 显示当前数据范围明细区域
			jQuery(dataRangeDetail).show();
			jQuery(dataRangeDetail).find(".dataRangeBox").show();
			//切换卡片，重置状态标识
			changeStatus = 0;
			//初始化默认数据范围
			initDataRange(dataRangeDetail,bm);
		});
	});
	
	//全校按钮click事件
	$("#checkAll_qx").click(function(index,checkbox){
		var checked = $(this)[0].checked;
		//已经选择：
		if(checked){
			$(this).next("span").addClass("highlight");
			dataRanges[tables[0]] = [{"bmdm_id":"-1","sfqx":"1"}];
		}else{
			$(this).next("span").removeClass("highlight");
			dataRanges[tables[0]] = [];
		}
	});
	
	//初始化数据范围
	function initDataRange(dataRangeDetail,tableName){
		//是否全校已经选择
		var isSchool = isQxChecked();
		
		//隐藏没数据的提示
		$(dataRangeDetail).find(".ln-no-match").hide();
		//获取数据范围区域的div
		var dataRangeBox = jQuery(dataRangeDetail).find("div.dataRangeBox");
	    $(dataRangeBox).find("ul").empty();
	    
	    //查询状态
	    var __waiting = $("#__waiting").clone();
	    $(__waiting).attr("id","").attr("class","__waiting");
	    $(dataRangeBox).append(__waiting);
	    $(__waiting).show();
		
		if(tables[0]==tableName){//"SCHOOL"
			//是否全校:但是本地的全校未选中，表示第一次初始化：进行选中操作
			if(isSchool&&!$("#checkAll_qx")[0].checked){
				$("#checkAll_qx")[0].checked = true;
			}
			//初始化学院，专业，班级选择字体样式的变化
			initCheckedStyle(jQuery(dataRangeDetail).find("div.dataRangeBox1"));
		} else if(tables[1]==tableName){//"niutal_XTGL_BMDMB"
			//判断是否已经全部学院数据范围；如果是，则学院默认全选
			var isAllXy = isXyAllChecked();
			var bmlb = $("#bmdm_bmlb").val();
			var checkVal = $("#bmdm_njdm_id").val();
		    var checkText = $("#bmdm_njdm_id").find("option:selected").text();
		    var map = {
	    		"zddm":'bmdm_id', // 学院Id
				"zdmc":'bmmc',	// 学院名称
				"bm":'niutal_xtgl_bmdmb' // 查询数据库表
			}
	 		if(bmlb!='全部'){
				map["cydm_id_bmlb"] = bmlb;              // 部门类别
			}
			//ajax请求
			jQuery.ajax({
				url: _path+'/xtgl/sjdx_cxSjfwdx.html', //访问服务器地址
				type: "post",
				dataType: 'json',                    //返回类型
				data:map,
				async:false,
				success:function(data){
					$(__waiting).remove();
					//判断部门信息是否不为空
					if(data.items != null && (data.items).length>0){
						var bmdms = data.items;
						//循环部门信息，组织html
						var html = [];
						html.push('<ul class="lisnavBox" id="lisnavBox">');
					    for(var i=0;i<bmdms.length;i++){
					    	//查询选中学院
					    	var xy_arr = getDataChecked("niutal_XTGL_BMDMB");
					    	//判断当前学院是否已经选择过
					    	var is_xy_checked = isXyChecked(xy_arr,bmdms[i]["zddm"]);
					    	var xyCheckedStr = "";
					    	if(isSchool==true){
					    		xyCheckedStr = 'checked="checked" disabled="disabled" ';
					    	}else if(isAllXy==true||is_xy_checked==true){
					    		xyCheckedStr = 'checked="checked" ';
					    	}
					    	html.push('<li style="text-align: left;border-bottom: 1px solid #ccc;">');
					    	html.push('<p class="selector-name">'+bmdms[i]["zwpy"]+'</p>');
							if(checkVal=='全部'){
								html.push('<table><tr>');
								html.push("<th><input name='bmdm_id' value='"+bmdms[i]["zddm"]+"' text='"+bmdms[i]["zdmc"]+"' type='checkbox' "+xyCheckedStr+"/><span>"+bmdms[i]["zdmc"]+"</span></th>");
								html.push("<td>");
								jQuery.each(njdmsJson,function(n,value) { 
									//判断此学院对应的年级是否已经选择
									var is_xy_nj_checked = isXyNjChecked(xy_arr,bmdms[i]["zddm"],value["njdm_id"]);
									var njCheckedStr = "";
							    	if(isSchool==true){
							    		njCheckedStr = ' checked="checked" disabled="disabled" ';
							    	}else if(isAllXy==true||is_xy_checked==true){
							    		njCheckedStr = ' checked="checked" ';
							    	}
									html.push("<input name='njdm_id' value='"+value["njdm_id"]+"' text='"+value["njmc"]+"' type='checkbox' "+njCheckedStr+"/><span>"+value["njmc"]+"</span>");
								});
								html.push("</td>");
								html.push('</tr></table>');
							}else{
								//判断此学院对应的年级是否已经选择
								var is_xy_nj_checked = isXyNjChecked(xy_arr,bmdms[i]["zddm"],checkText);
								var njCheckedStr = "";
						    	if(isSchool==true){
						    		njCheckedStr = ' checked="checked" disabled="disabled" ';
						    	}else if(isAllXy==true||is_xy_checked==true){
						    		njCheckedStr = ' checked="checked" ';
						    	}
								html.push('<table><tr>');
								html.push("<th><input name='bmdm_id' value='"+bmdms[i]["zddm"]+"'  text='"+bmdms[i]["zdmc"]+"' type='checkbox' "+xyCheckedStr+"/><span>"+bmdms[i]["zdmc"]+"</span></th>");
								html.push("<td><input name='njdm_id' value='"+checkVal+"'  text='"+checkText+"' type='checkbox' "+njCheckedStr+"/><span>"+checkText+"</span></td>");
								html.push('</tr></table>');
		     				}
							html.push("</li>");
					    }
					    html.push("</ul>");
						//将拼装后的html放置在数据范围区域显示
						$(dataRangeBox).html(html.join(""));	
					}else{
						$(dataRangeBox).find("ul").empty();
					}
					//初始化学院或者专业选择时候默认选择所有的年级
					initRightCheckedEvent(dataRangeBox,tableName);
					//初始化学院-年级，专业-年级，班级选择状态改变字体样式事件
					initCheckedEvent(dataRangeBox,tableName);
					//初始化学院，专业，班级选择字体样式的变化
					initCheckedStyle(dataRangeBox);
				}
		   });
		}else if(tables[2]==tableName){//"niutal_XTGL_ZYDMB"
			
			//判断是否已经全部学院数据范围；如果是，则专业默认全选，且不可选择
			var isAllXy = isXyAllChecked();
			var checkVal = jQuery("#njdm_zydm_id").val();
			var checkText = jQuery("#njdm_zydm_id").find("option:selected").text();
		    var bmdm_id = jQuery("#bmdm_zydm_id").val();
		    var bmdm_text = jQuery("#bmdm_zydm_id").find("option:selected").text();
	 		var map = {
				"zddm":'zydm_id', // 专业Id
				"zdmc":'zymc',	// 专业名称
				"bm":'niutal_xtgl_zydmb' // 查询数据库表
			}
	 		if(bmdm_id!='全部'){
				map["bmdm_id_lsbm"] = bmdm_id;              // 所属部门
			}
		    //ajax请求
			 jQuery.ajax({
				url: _path+'/xtgl/sjdx_cxSjfwdx.html', //访问服务器地址
				type: "post",
				dataType: 'json',                    //返回类型
				data:map,
				async:false,
				success:function(data){
				 	$(__waiting).remove();
					//判断专业信息是否不为空
					if(data.items != null && (data.items).length>0){
						//查询选中学院
						var xy_arr = getDataChecked("niutal_XTGL_BMDMB");
				    	var zy_arr = getDataChecked("niutal_XTGL_ZYDMB");
						var zydms = data.items;
						//循环专业信息，组织html
						var html = [];
						html.push('<ul class="lisnavBox" id="lisnavBox">');
					    for(var i=0;i<zydms.length;i++){
					    	html.push('<li style="text-align: left;border-bottom: 1px solid #ccc;">');
					    	html.push('<p class="selector-name">'+zydms[i]["zwpy"]+'</p>');
					    	//当前专业所有的学院是否选中
					    	var is_xy_checked = isXyChecked(xy_arr,zydms[i]["ls_bmdm"]);
					    	var is_zy_checked = isZyChecked(zy_arr,zydms[i]["zddm"]);
					    	var zyCheckedStr = "";
					    	if(isSchool==true||isAllXy==true||is_xy_checked==true){
					    		zyCheckedStr = ' checked="checked" disabled="disabled" ';
					    	}else if(is_zy_checked==true){
					    		zyCheckedStr = ' checked="checked" ';
					    	}
							if(checkVal=='全部'){
								html.push('<table><tr>')
								html.push("<th><input name='bmdm_id' value='"+zydms[i]["zddm"]+"' text='"+zydms[i]["zdmc"]+"' ls_bmdm='"+zydms[i]["ls_bmdm"]+"' type='checkbox' "+zyCheckedStr+"/><span>"+zydms[i]["zdmc"]+"</span></th>");
								html.push("<td>");
								jQuery.each(njdmsJson,function(n,value) { 
									//当前专业所有的学院下某年级是否选中
									var is_xy_nj_checked = isXyNjChecked(xy_arr,zydms[i]["ls_bmdm"],value["njdm_id"]);
									var is_zy_nj_checked = isZyNjChecked(zy_arr,zydms[i]["zddm"],value["njdm_id"]);
									var njCheckedStr = "";
									if(isSchool==true||isAllXy==true||is_xy_nj_checked==true){
										njCheckedStr = ' checked="checked" disabled="disabled" ';
							    	}else if(is_zy_nj_checked==true){
							    		njCheckedStr = ' checked="checked" ';
							    	}
									html.push("<input name='njdm_id' value='"+value["njdm_id"]+"' text='"+value["njmc"]+"' type='checkbox' "+njCheckedStr+"/><span>"+value["njmc"]+"</span>");
								});
								html.push("</td>");
								html.push('</tr></table>');
							}else{
								//当前专业所有的学院下某年级是否选中
						    	var is_xy_nj_checked = isXyNjChecked(xy_arr,zydms[i]["ls_bmdm"],checkVal);
								var is_zy_nj_checked = isZyNjChecked(zy_arr,zydms[i]["zddm"],checkVal);
								var njCheckedStr = "";
								if(isSchool==true||isAllXy==true||is_xy_nj_checked==true){
									njCheckedStr = ' checked="checked" disabled="disabled" ';
						    	}else if(is_zy_nj_checked==true){
						    		njCheckedStr = ' checked="checked" ';
						    	}
								html.push('<table><tr>');
								html.push("<th><input name='bmdm_id' value='"+zydms[i]["zddm"]+"' text='"+zydms[i]["zdmc"]+"' ls_bmdm='"+zydms[i]["ls_bmdm"]+"' type='checkbox' "+zyCheckedStr+"/><span>"+zydms[i]["zdmc"]+"</span></th>");
								html.push("<td><input name='njdm_id' value='"+checkVal+"' text='"+checkText+"' type='checkbox' "+njCheckedStr+"/><span>"+checkText+"</span></td>");
								html.push('</tr></table>');
		     				}
							html.push("</li>");
					    }
					    html.push("</ul>");
						//将拼装后的html放置在数据范围区域显示
					    $(dataRangeBox).html(html.join(""));	
					}else{
						$(dataRangeBox).find("ul").empty();
					}
					//初始化学院或者专业选择时候默认选择所有的年级
					initRightCheckedEvent(dataRangeBox,tableName);
					//初始化学院-年级，专业-年级，班级选择状态改变字体样式事件
					initCheckedEvent(dataRangeBox,tableName);
					//初始化学院，专业，班级选择字体样式的变化
					initCheckedStyle(dataRangeBox);
				}
			});	
		}else if(tables[3]==tableName){//"niutal_XTGL_BJDMB"
			//判断是否已经全部学院数据范围；如果是，则班级默认全选，且不可选择
			var isAllXy = isXyAllChecked();
			var bmdm_id = jQuery("#bjdm_bmdm_id").val();
			var bmdm_mc = jQuery("#bjdm_bmdm_id").find("option:selected").text();
		    var zydm_id = jQuery("#bjdm_zydm_id").val();
		    var zydm_mc = jQuery("#bjdm_zydm_id").find("option:selected").text();
		    var njdm_id = jQuery("#bjdm_njdm_id").val();
		    var njdm_mc = jQuery("#bjdm_njdm_id").find("option:selected").text();
			// 按部门代码分页查询班级代码列表
	 		var map = {
				"zddm":'bjdm_id', // 班级Id
				"zdmc":'bjmc',	// 班级名称
				"bm":'niutal_xtgl_bjdmb' // 查询数据库表
			};
			if(bmdm_id!='全部'){
				map["ls_bmdm"] = bmdm_id;              // 所属部门
			}
			if(zydm_id!='全部'){
				map["ls_zydm"] = zydm_id;              // 所属专业
			}
			if(njdm_id!='全部'){
				map["ls_njdm"] = njdm_id;              // 所属年级
			}
		    //ajax请求
			jQuery.ajax({
				url: _path+'/xtgl/sjdx_cxSjfwdx.html', //访问服务器地址
				type: "post",
				dataType: 'json',                    //返回类型
				data:map,
				async:false,
				success:function(data){
					$(__waiting).remove();
					//判断班级信息是否不为空
					if(data.items != null && (data.items).length>0){
						//查询选中的学院，专业，班级
				    	var xy_arr = getDataChecked("niutal_XTGL_BMDMB");
				    	var zy_arr = getDataChecked("niutal_XTGL_ZYDMB");
				    	var bj_arr = getDataChecked("niutal_XTGL_BJDMB");
						var bjdms = data.items;
						//循环班级信息，组织html
						var html = [];
						html.push('<ul class="lisnavBox lisnavBox_bj" id="lisnavBox">');
					    for(var i=0;i<bjdms.length;i++){
					    	
					    	//当前班级所属的学院和年级是否选中
					    	var is_xy_nj_checked = isXyNjChecked(xy_arr,bjdms[i]["ls_bmdm"],bjdms[i]["ls_njdm"]);
					    	//当前班级所属的专业和年级是否选中
					    	var is_zy_nj_checked = isZyNjChecked(zy_arr,bjdms[i]["ls_zydm"],bjdms[i]["ls_njdm"]);
					    	//当前班级是否选中
					    	var is_bj_checked = isBjChecked(bj_arr,bjdms[i]["zddm"]);
					    	//是否选中str
					    	var checkedStr = "";
					    	if(isSchool==true||isAllXy==true||is_xy_nj_checked==true||is_zy_nj_checked){
					    		checkedStr = ' checked="checked" disabled="disabled" ';
					    	}else if(is_bj_checked==true){
					    		checkedStr = ' checked="checked" ';
					    	}
				    		html.push('<li style="text-align: left;border-bottom: 1px solid #ccc;">');
					    	html.push('<p class="selector-name">'+bjdms[i]["zwpy"]+'</p>');
							html.push('<table><tr>')
							html.push("<td style='border-right: 1px solid #ccc;width:290px;'>");
							html.push("<input name='bmdm_id' value='"+bjdms[i]["zddm"]+"' text='"+bjdms[i]["zdmc"]+"' ls_bmdm='"+bjdms[i]["ls_bmdm"]+"' ls_zydm='"+bjdms[i]["ls_zydm"]+"' ls_njdm='"+bjdms[i]["ls_njdm"]+"' type='checkbox' "+checkedStr+"/>");
							html.push("<span>"+bjdms[i]["zdmc"]+"</span></td>");
							
							html.push('</tr></table>');
							html.push("</li>");
					    }
					    html.push("</ul>");
						//将拼装后的html放置在数据范围区域显示
					    $(dataRangeBox).html(html.join(""));	
					}else{
						$(dataRangeBox).find("ul").empty();
					}
					//初始化学院-年级，专业-年级，班级选择状态改变字体样式事件
					initCheckedEvent(dataRangeBox,tableName);
					//初始化学院，专业，班级选择字体样式的变化
					initCheckedStyle(dataRangeBox);
				}
		   });
		}
		//初始化A-Z字符筛选组件
		initDataRangeEvent(dataRangeBox);
	}
	
	//绑定数据范围A-Z筛选事件
	function initDataRangeEvent(dataRangeBox){
		var letter = $(dataRangeBox).data("letter")||"";
		//初始化A-Z字符筛选组件
		$(dataRangeBox).listnav({
		    includeAll: true,
		    initLetter: letter,
		    allText:'全部',
		    listSelector: 'li',//受索引控制的显示隐藏的区域
		    filterSelector: '.selector-name',//用于排序的引用
		    includeNums: true,
		    noMatchText: '没有数据.',
		    includeOther: true,
		    otherText:"其他",
		    incudeMatch: true,
		    matchSelector: ':checkbox:checked',//
	        matchText: "已选",
		    removeDisabled: false,
		    onClick:function(letter){
				$(dataRangeBox).data("letter","letter");
			}
		});
		
		if($(dataRangeBox).find("ul.lisnavBox li").size()==0){
			$(dataRangeBox).find(".ln-no-match").show();
		}
		//change事件完成，数据范围加载完毕，字母分页完毕，重置状态标识
		changeStatus = 0;
	}
	
	//绑定学院或者专业选择时候默认选择所有的年级
	function initRightCheckedEvent(dataRangeBox,tableName){
		$(dataRangeBox).find("ul li").each(function(index,item){
			//查找table
			var table = $(item).children("table:eq(0)");
			//查找左侧的th:只有 按部门，按专业的时候才有
			$(table).find("th:eq(0)").find(":checkbox").click(function(){
				var checked = $(this)[0].checked;
				var td = $(this).parent().next("td");
				//已经选择：
				if(checked){
					$(this).next("span").addClass("highlight");
					$(td).find(":checkbox").each(function(i,checkbox){
						$(this).attr("checked","checked");
						$(this).next("span").addClass("highlight");
					});
				}else{
					$(this).next("span").removeClass("highlight");
					$(td).find(":checkbox").each(function(i,checkbox){
						$(this).removeAttr("checked");
						$(this).next("span").removeClass("highlight");
					});
				}
				localDataRangeSync(tableName);
			});
		});
	}
	
	//初始化年级，班级选择时候文字默认选择样式
	function initCheckedEvent(dataRangeBox,tableName){
		$(dataRangeBox).find("ul li").each(function(index,item){
			//查找table
			var table = $(item).children("table:eq(0)");
			//查找所有的checkbox
			$(table).find("td").find(":checkbox").click(function(index,checkbox){
				var checked = $(this)[0].checked;
				//已经选择：
				if(checked){
					$(this).next("span").addClass("highlight");
				}else{
					$(this).next("span").removeClass("highlight");
				}
				//如果找到th，表示是 按部门或者按专业
				var th = $(table).find("th:eq(0)");
				if(th.size()>0){
					if(checked){
						$(th).find(":checkbox").attr("checked","checked");
						$(th).find(":checkbox").next("span").addClass("highlight");
					}else{
						var checked_size = $(table).find("td").find(":checkbox:checked").size();
						if(checked_size==0){
							$(th).find(":checkbox").removeAttr("checked");
							$(th).find(":checkbox").next("span").removeClass("highlight");
						}
					}
				}
				localDataRangeSync(tableName);
			});
		});
	}
	
	//初始化学院，专业，班级选择时候文字默认选择样式
	function initCheckedStyle(dataRangeBox){
		$(dataRangeBox).find("ul li").each(function(index,item){
			//查找所有的checkbox
			$(item).find(":checkbox").each(function(index,checkbox){
				var checked = $(this)[0].checked;
				//已经选择：
				if(checked){
					$(this).next("span").addClass("highlight");
				}else{
					$(this).next("span").removeClass("highlight");
				}
			});
			
		});
	}
	
	//ajax请求数据库中保存的数据范围
	function ajaxDataRangeSync(){
		var zgh_arr = [];
		$("input[name=zgh]").each(function(index,input){
			//多个用户
			zgh_arr.push($(input).val());
		});
		 //ajax请求用户数据范围
		jQuery.ajax({
			url:"yhsjfwgl_cxSjfwszList.html", //访问服务器地址
			type: "post",
			dataType: 'json',                   //返回类型
			data:{
				"js_id":$("#js_id").val(),
				"yh_id":zgh_arr.join(",")
			},
			async:false,
			success:function(data){
				dataRanges = data;
				//是否有数据范围
				$.each(dataRanges,function(table,rangeList){
					if(rangeList&&rangeList.length>0){
						isHaveDataRange = true;
						return false;
					}else{
						isHaveDataRange = false;
					}
				});
			}
		});
	}
	
	//同步浏览器本地全局数据范围缓存对象中当前模式下的数据范围数据
	function localDataRangeSync(tableName){
		var dataRangeDetail = jQuery("div.dataRangeDetail[name="+tableName+"]");
		//获取数据范围区域的div
		var dataRangeBox = jQuery(dataRangeDetail).find("div.dataRangeBox");
		if(tables[0]==tableName){
			dataRanges[tableName] = [{"bmdm_id":"-1","sfqx":"1"}];
		}else if(tables[1]==tableName){//按部门
			var checked = [];
			$(dataRangeBox).find("ul li").each(function(index,item){
				//查找table
				var table = $(item).children("table:eq(0)");
				//查找左侧的th:只有 按部门，按专业的时候才有
				var checkbox = $(table).find("th:eq(0)").find(":checkbox")[0];
				if(checkbox.checked){
					var list_arr = [];
					//循环每行的每个年级元素
					$(table).find("td:eq(0)").find(":checkbox:checked").each(function(){
						list_arr.push($(this).val());
					});
					checked.push({
						"bmdm_id":$(checkbox).val(),
						"list":list_arr
					});
				}
			});
			dataRanges[tableName] = checked;
		}else if(tables[2]==tableName){//按专业
			var checked = [];
			$(dataRangeBox).find("ul li").each(function(index,item){
				//查找table
				var table = $(item).children("table:eq(0)");
				//查找左侧的th:只有 按部门，按专业的时候才有
				var checkbox = $(table).find("th:eq(0)").find(":checkbox")[0];
				if(checkbox.checked){
					var list_arr = [];
					//循环每行的每个年级元素
					$(table).find("td:eq(0)").find(":checkbox:checked").each(function(){
						list_arr.push($(this).val());
					});
					checked.push({
						"zydm_id":$(checkbox).val(),
						"list":list_arr
					});
				}
			});
			dataRanges[tableName] = checked;
		}else if(tables[3]==tableName){//按班级
			var checked = [];
			$(dataRangeBox).find("ul li").each(function(index,item){
				//查找table
				var table = $(item).children("table:eq(0)");
				//查找选中的td
				var checkbox = $(table).find("td:eq(0)").find(":checkbox")[0];
				if(checkbox.checked){
					checked.push({"bjdm_id":$(checkbox).val()});
				}
			});
			dataRanges[tableName] = checked;
		}
	}
	
	/*
	 * 1.获得已经选择的学院:为了在按专业，按班级时自动选中已该学院下的专业和班级
	 * 2.获得已经选择的专业：为了在按班级时自动选中已该专业下的班级
	 * 3.获得已经选择的班级
	 */
	function getDataChecked(tableName){
		//获得已选的数据范围
		var checkedArray = dataRanges[tableName];
		/*
		 * 1.页面第一次加载，默认加载了已经保存的学院的数据范围；
		 * 2.在操作过后数据范围同步到了全局dataRanges属性
		 */
		if(checkedArray!=undefined&&checkedArray.length>0){
			return checkedArray;
		}else{
			return [];
		}
	}
	
	//是否全校数据范围
	function isQxChecked(){
		if($("#checkAll_qx")[0].checked){
			return true;
		}else{
			if(dataRanges[tables[0]]&&dataRanges[tables[0]][0]){
				var sfqx = dataRanges[tables[0]][0]["sfqx"];
				if("1"==sfqx){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
	}
	
	//获得当前区域所有的部门，所有的年级是否都已经选择
	function isXyAllChecked(){
		//数据范围对象表名
		var dataRangeDetail = jQuery("div.dataRangeDetail[name=niutal_XTGL_BMDMB]");
		//获取数据范围区域的div
		var dataRangeBox = jQuery(dataRangeDetail).find("div.dataRangeBox");
		var lisSize = $(dataRangeBox).find("ul li").size();
		if(lisSize>0){
			var bmlb = $("#bmdm_bmlb").val();
			var njdm_id = $("#bmdm_njdm_id").val();
			//如果机构类型与年级都是全部，则判断【是否选择了所有学院的所有年级】
			if(bmlb=='全部'&&njdm_id=='全部'){
				//除了全部元素之外的年级下拉选项数
				var njdm_id_size = $("#bmdm_njdm_id").find("option").size() - 1;
				//逐行循环；判断当前区域所有的部门，所有的年级是否都已经选择
				var bmdm_checked_num = 0,njdm_checked_num = 0;
				$(dataRangeBox).find("ul li").each(function(index,item){
					//查找table
					var table = $(item).children("table:eq(0)");
					//查找左侧的th:只有 按部门，按专业的时候才有
					var checkbox = $(table).find("th:eq(0)").find(":checkbox")[0];
					if(checkbox&&checkbox.checked){
						bmdm_checked_num ++;
					}
					njdm_checked_num += $(table).find("td:eq(0)").find(":checkbox:checked").size();
				});
				return (bmdm_checked_num == lisSize)&&(lisSize*njdm_id_size == njdm_checked_num);
			}else{
				return false;
			}
		}else{
			if(dataRanges[tables[1]]&&dataRanges[tables[1]][0]){
				var sfqxy = dataRanges[tables[1]][0]["sfqxy"];
				if("1"==sfqxy){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}
	}
	
	//判断目标学院ID是否在选中的集合中
	function isXyChecked(xy_arr,bmdm_id){
		var is_xy_checked = false;
    	$.each(xy_arr,function(index,row){
    		if(row["bmdm_id"]==bmdm_id){
    			is_xy_checked = true;
    			return false;
    		}
    	});
    	return is_xy_checked;
	}
	
	//判断目标学院ID下的某年级是否在选中的集合中
	function isXyNjChecked(xy_arr,bmdm_id,njdmb_id){
		var is_xy_nj_checked = false;
		$.each(xy_arr,function(index,row){
    		if(row["bmdm_id"]==bmdm_id){
    			var current_xy_nj_list = row["list"]||[];
    			$.each(current_xy_nj_list,function(index,xy_njdm_id){
    				//判断专业选项的年级在部门页面对应的学院所在行的年级是否选中
    	    		if(xy_njdm_id==njdmb_id){
    	    			is_xy_nj_checked = true;
    	    			return false;
    	    		}
    			});
    			return false;
    		}
    	});
    	return is_xy_nj_checked;
	}
	
	
	//判断目标专业ID是否在选中的集合中
	function isZyChecked(zy_arr,target_id){
		var is_zy_checked = false;
    	$.each(zy_arr,function(index,row){
    		if(row["zydm_id"]==target_id){
    			is_zy_checked = true;
    			return false;
    		}
    	});
    	return is_zy_checked;
	}
	
	//判断目标专业ID下的某年级是否在选中的集合中
	function isZyNjChecked(zy_arr,zydm_id,njdmb_id){
		var is_zy_nj_checked = false;
		$.each(zy_arr,function(index,row){
    		if(row["zydm_id"]==zydm_id){
    			var current_zy_nj_list = row["list"]||[];
    			$.each(current_zy_nj_list,function(index,zy_njdm_id){
    				//判断专业选项的年级在部门页面对应的学院所在行的年级是否选中
    	    		if(zy_njdm_id==njdmb_id){
    	    			is_zy_nj_checked = true;
    	    			return false;
    	    		}
    			});
    			return false;
    		}
    	});
    	return is_zy_nj_checked;
	}
	
	//判断目标班级ID是否在选中的集合中
	function isBjChecked(bj_arr,target_id){
		var is_bj_checked = false;
    	$.each(bj_arr,function(index,row){
    		if(row["bjdm_id"]==target_id){
    			is_bj_checked = true;
    			return false;
    		}
    	});
    	return is_bj_checked;
	}
	
	// 按部门-机构类型change事件
	jQuery("#bmdm_bmlb").change(function() {
		if(changeStatus==0){
		    //获取选择框所在的 .dataRangeDetail 的div 
			var dataRangeDetail = $(this).parent("td").parent("tr").parent("tbody").parent("table").parent("div").parent("div.dataRangeDetail")
			//初始化数据范围选择区域
			initDataRange(dataRangeDetail,tables[1]);
		}
	});
	
	// 按部门-年级change事件
	jQuery("#bmdm_njdm_id").change(function() {
		if(changeStatus==0){
		    //获取选择框所在的 .dataRangeDetail 的div 
			var dataRangeDetail = $(this).parent("td").parent("tr").parent("tbody").parent("table").parent("div").parent("div.dataRangeDetail")
			//初始化数据范围选择区域
			initDataRange(dataRangeDetail,tables[1]);
		}
	});
	
	// 按专业-学院change事件
	jQuery("#bmdm_zydm_id").change(function() {
		if(changeStatus==0){
			//获取选择框所在的 .dataRangeDetail 的div 
			var dataRangeDetail = $(this).parent("td").parent("tr").parent("tbody").parent("table").parent("div").parent("div.dataRangeDetail")
			//初始化数据范围选择区域
			initDataRange(dataRangeDetail,tables[2]);
		}
	});
	
	// 按专业-年级change事件
	jQuery("#njdm_zydm_id").change(function() {
		if(changeStatus==0){
			//获取选择框所在的 .dataRangeDetail 的div 
			var dataRangeDetail = $(this).parent("td").parent("tr").parent("tbody").parent("table").parent("div").parent("div.dataRangeDetail")
			//初始化数据范围选择区域
			initDataRange(dataRangeDetail,tables[2]);
		}
	});
	
	// 按班级-学院change事件
	jQuery("#bjdm_bmdm_id").change(function() {
		if(changeStatus==0){
			//标记切换状态
			changeStatus = 1;
		    var bjdm_zydm_id = jQuery("#bjdm_zydm_id");
			jQuery.ajax({
				url:"comm_cxZydm.html",
				type: "post",
				dataType:"json",
				async:false,
				data:{"bmdm_id":jQuery("#bjdm_bmdm_id").val()},
				success:function(zydms){
					var html = "<option value='全部'>全部</option>";
					if(zydms.length>0){
						jQuery(bjdm_zydm_id).html("");
						for(var i=0;i<zydms.length;i++){
							html = html + "<option value="+zydms[i]["zydm_id"]+">"+zydms[i]["zymc"]+"</option>";
						}
			 		} 
			  		jQuery(bjdm_zydm_id).html(html);
				}
			});	
				
			//获取选择框所在的 .dataRangeDetail 的div 
			var dataRangeDetail = $(this).parent("td").parent("tr").parent("tbody").parent("table").parent("div").parent("div.dataRangeDetail")
			//初始化数据范围选择区域
			initDataRange(dataRangeDetail,tables[3]);
		}
	});
	
	// 按班级-专业change事件
	jQuery("#bjdm_zydm_id").change(function() {
		if(changeStatus==0){
			//获取选择框所在的 .dataRangeDetail 的div 
			var dataRangeDetail = $(this).parent("td").parent("tr").parent("tbody").parent("table").parent("div").parent("div.dataRangeDetail")
			//初始化数据范围选择区域
			initDataRange(dataRangeDetail,tables[3]);
		}
	});
	
	// 按班级-年级change事件
	jQuery("#bjdm_njdm_id").change(function() {
		if(changeStatus==0){
			//获取选择框所在的 .dataRangeDetail 的div 
			var dataRangeDetail = $(this).parent("td").parent("tr").parent("tbody").parent("table").parent("div").parent("div.dataRangeDetail")
			//初始化数据范围选择区域
			initDataRange(dataRangeDetail,tables[3]);
		}
	});
	
	

	//绑定click事件
	$("#dataRangeSaveBt").click(function(e){
		
		function saveDataRange(){
			//循环每个区域，组织保存的对象
			var dataRangeMap = {};
			var isSchool = $("#checkAll_qx")[0].checked;
			var isAllXy = isXyAllChecked();
			//是否全校已经选择
			if(isSchool){
				//全校的数据范围时，仅仅保存全校数据范围标识sfqx=1
				dataRangeMap["sfqx"] = '1';
			}else if(isAllXy){
				//全学院的数据范围选择时候,仅仅保存全学院数据范围标识sfqxy=1,全部学院即全校的数据范围
				dataRangeMap["sfqxy"] = '1';
			}else{
				//全局下标index
				var dataRangeIndex = 0;
				jQuery("div.dataRangeDetail").each(function(index,dataRangeDetail) {
					//数据范围对象表名
					var tableName = $(dataRangeDetail).attr("name");
					//获取数据范围区域的div
					var dataRangeBox = jQuery(dataRangeDetail).find("div.dataRangeBox");
					if(tables[1]==tableName){//按部门
						$(dataRangeBox).find("ul li").each(function(index,item){
							//查找table
							var table = $(item).children("table:eq(0)");
							//查找左侧的th:只有 按部门，按专业的时候才有
							var checkbox = $(table).find("th:eq(0)").find(":checkbox")[0];
							if(checkbox.checked){
								
								//循环每行的每个年级元素
								var njdm_id_list = [],njdm_mc_list = [];
								$(table).find("td:eq(0)").find(":checkbox:checked").each(function(){
									njdm_id_list.push($(this).val());
									njdm_mc_list.push($(this).attr("text"));
								});
								
								dataRangeMap["dataRanges["+dataRangeIndex+"].row.bmdm_id"]=$(checkbox).val();
								dataRangeMap["dataRanges["+dataRangeIndex+"].row.bmdm_mc"]=$(checkbox).attr("text");
								//有年级选择的时候
								if(njdm_id_list.length>0){
									dataRangeMap["dataRanges["+dataRangeIndex+"].row.njdm_id"]=njdm_id_list.join(",");
									dataRangeMap["dataRanges["+dataRangeIndex+"].row.njdm_mc"]=njdm_mc_list.join(",");
								}
								dataRangeIndex ++;
							}
						});
					}else if(tables[2]==tableName){
						var xy_arr = getDataChecked("niutal_XTGL_BMDMB");
				    	var zy_arr = getDataChecked("niutal_XTGL_ZYDMB");
						/*
						 * 按专业：
						 * 1、需要判断所属学院是否已经选择：如果未选择才能加入数据范围对象
						 */
						$(dataRangeBox).find("ul li").each(function(index,item){
							//查找table
							var table = $(item).children("table:eq(0)");
							//查找左侧的th:只有 按部门，按专业的时候才有
							var checkbox = $(table).find("th:eq(0)").find(":checkbox")[0];
							if(checkbox.checked){
								var zydm_id = $(checkbox).val();
								
								//循环每行的每个年级元素
								var njdm_id_list = [],njdm_mc_list = [];
								$(table).find("td:eq(0)").find(":checkbox:checked").each(function(){
									var njdm_id = $(this).val();
									var ls_bmdm = $(this).attr("ls_bmdm");
									//判断当前专业所属学院对应年级数据范围是否选择
									var is_xy_nj_checked = isXyNjChecked(xy_arr,ls_bmdm,njdm_id);
									//如果未选择加入数据范围对象
									if(!is_xy_nj_checked){
										njdm_id_list.push($(this).val());
										njdm_mc_list.push($(this).attr("text"));
									}
								});
								
								dataRangeMap["dataRanges["+dataRangeIndex+"].row.zydm_id"]=$(checkbox).val();
								dataRangeMap["dataRanges["+dataRangeIndex+"].row.zydm_mc"]=$(checkbox).attr("text");
								//有年级选择的时候
								if(njdm_id_list.length>0){
									dataRangeMap["dataRanges["+dataRangeIndex+"].row.njdm_id"]=njdm_id_list.join(",");
									dataRangeMap["dataRanges["+dataRangeIndex+"].row.njdm_mc"]=njdm_mc_list.join(",");
								}
								dataRangeIndex ++;
							}
						});
					}else if(tables[3]==tableName){
						/*
						 * 按班级：
						 * 1、需要判断所属学院是否已经选择：如果未选择才能加入数据范围对象
						 * 2、需要判断所属专业是否已经选择：如果未选择才能加入数据范围对象
						 */
						//查询选中的学院，专业，班级
				    	var xy_arr = getDataChecked("niutal_XTGL_BMDMB");
				    	var zy_arr = getDataChecked("niutal_XTGL_ZYDMB");
						$(dataRangeBox).find("ul li").each(function(index,item){
							//查找table
							var table = $(item).children("table:eq(0)");
							//循环每个班级元素
							$(table).find("td:eq(0)").find(":checkbox:checked").each(function(){
								var ls_bmdm = $(this).attr("ls_bmdm");
								var ls_zydm = $(this).attr("ls_zydm");
								var ls_njdm = $(this).attr("ls_njdm");
								//当前班级所属的学院和年级是否选中
						    	var is_xy_nj_checked = isXyNjChecked(xy_arr,ls_bmdm,ls_njdm);
						    	//当前班级所属的专业和年级是否选中
						    	var is_zy_nj_checked = isZyNjChecked(zy_arr,ls_zydm,ls_njdm);
						    	//如果未选择加入数据范围对象
						    	if(!is_xy_nj_checked&&!is_zy_nj_checked){
						    		dataRangeMap["dataRanges["+dataRangeIndex+"].row.bjdm_id"]=$(this).val();
						    		dataRangeMap["dataRanges["+dataRangeIndex+"].row.bjdm_mc"]=$(this).attr("text");
						    		dataRangeIndex ++;
						    	}
							});
						});
					}
				});
			}
			var zgh_arr = [];
			$("input[name=zgh]").each(function(index,input){
				//多个用户
				zgh_arr.push($(input).val());
			});
			dataRangeMap["js_id"] = $("#js_id").val();
			dataRangeMap["yh_id"] = zgh_arr.join(",");           
			
			//保存数据范围
			jQuery.ajax({
				 url:"yhsjfwgl_cxSjfwszSave.html",
				 type:"post",	 
				 dataType:"html",
				 data:dataRangeMap,//.join("&")
				 success:function(data){
					alert(data,'',{'clkFun':function(){iFClose();}});
			     }
			});
		}
		
		if(isHaveDataRange){
			//提示信息
			showConfirmDivLayer('保存将清除已有数据范围，将选择用户设置为当前数据范围！是否继续操作？', {
				'okFun' : function(){
					saveDataRange();
				}
			});
		}else{
			saveDataRange();
		}
	});
	
	$("#displayBox").find("li[name=niutal_XTGL_BMDMB]").click();
	
});