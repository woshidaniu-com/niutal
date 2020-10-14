
function submitDataRange(callbackFunc){
	var zgh_arr = [];
	$("input[name=yhm]").each(function(index,input){
		//多个用户
		zgh_arr.push($(input).val());
	});
	//调用提交方法
	$("#dataRangeBox").datarange("submit",{
		//显示提交进度的状态条元素
		progressElement	: "#bootboxStatus",
		url:_path + "/datarange/yhjssjfw_sjsqXgSjfwsz.html", //访问服务器地址
		data:{
			"jsdm":$("#js_id").val(),
			"js_id":$("#js_id").val(),
			"kzdx":$("#kzdx").val(),
			"yhm":zgh_arr.join(",")
		},
		success:function(data){
			callbackFunc.call(this,data);
	    }
	});
}

//你可以在这里继续使用$作为别名...
jQuery(function($) {

	var zgh_arr = [];
	$("input[name=yhm]").each(function(index,input){
		//多个用户
		zgh_arr.push($(input).val());
	});
	
	$("#dataRangeBox").datarange({
		//用户数据范围JSON数据
		href :_path + "/datarange/yhjssjfw_cxSjfwszList.html", //访问服务器地址
		//关联组合
		attrs:{	"niutal_xtgl_jgdmb":[{"mapper":{"key":"njdm_id","text":"njmc"},"elementID" : "#bmdm_njdm_id" ,"list":njdmsJson}],
				"niutal_xtgl_zydmb":[{"mapper":{"key":"njdm_id","text":"njmc"},"elementID" : "#zydm_njdm_id" ,"list":njdmsJson,"parentAttr" : "niutal_xtgl_jgdmb" }]
        },
        data :{
        	"jsdm":$("#js_id").val(),
			"js_id":$("#js_id").val(),
			"kzdx":$("#kzdx").val(),
			"yhm":zgh_arr.join(",")
		},
		//tab区域参数
		tablist:{
			href: _path + "/datarange/sjfwdx_cxSjfwdxInfo.html", //访问服务器地址
			data:{"kzdx":"xs"},
			mapper:{"key":"bm","text":"zwmc"},
			active:"niutal_xtgl_jgdmb"
		},
		//a-z区域参数
		droplist:{
			href:_path+'/datarange/sjfwdx_cxSjfwdxList.html', //访问服务器地址
			data:{"kzdx":"xs"}
		},
		//判断条件
		switches:{
            //是否全局选中：这里判断全校是否选择
        	isGlobalChecked:function(element,options){
        		var qxEl = $("#checkAll_qx");
	        	if($(qxEl).size() > 0 && $(qxEl).prop("checked")){
	        		//已经选择：
					$(qxEl).next("span").addClass("highlight");
					options.dataRanges["SCHOOL"] = [{"jg_id":"-1","sfqx":"1"}];
	    			return true;
	    		}else{
	    			if($.founded(options.dataRanges["SCHOOL"])){
	    				var sfqx = options.dataRanges["SCHOOL"][0]["sfqx"];
	    				if("1"==sfqx){
	    					//已经选择：
	    					$(qxEl).prop("checked",true);
	    					$(qxEl).next("span").addClass("highlight");
							options.dataRanges["SCHOOL"] = [{"jg_id":"-1","sfqx":"1"}];
	    					return true;
	    				}else{
	    					$(qxEl).next("span").removeClass("highlight");
							options.dataRanges["SCHOOL"] = [];
	    					return false;
	    				}
	    			}else{
	    				return false;
	    			}
	    		}
        	},
	      	//获得按部门 区域所有的部门，所有的年级是否都已经选择
			isTabChecked:function(element,options,table){
				//按部门不进行此判断
				if(table=="niutal_xtgl_xqdmb" || table=="niutal_xtgl_jgdmb"){
					return false;
				}
        		var content = $("#niutal_xtgl_jgdmb").find(".droplist-content");
        		
				var lisSize = $(content).find("ul.item-droplist li").size();
				if(lisSize>0){
					var bmlb = $("#bmdm_bmlb").val();
					var njdm_id = $("#bmdm_njdm_id").val();
					//如果机构类型与年级都是全部，则判断【是否选择了所有学院的所有年级】
					if(!$.founded(bmlb)&&!$.founded(njdm_id)){
						
						//除了全部元素之外的年级下拉选项数
						var njdm_id_size = $("#bmdm_njdm_id").find("option").size() - 1;
						//逐行循环；判断当前区域所有的部门，所有的年级是否都已经选择
						var bmdm_checked_num = 0,njdm_checked_num = 0;
						$(content).find("ul.item-droplist li").each(function(index,item){
							//查找table
							var table = $(item).children("table:eq(0)");
							//查找左侧的th:只有 按部门，按专业的时候才有
							var checkbox = $(table).find(":checkbox:eq(0)");
							if(checkbox.prop("checked")){
								bmdm_checked_num ++;
							}
							njdm_checked_num += $(table).find("td:eq(0)").find(":checkbox:checked").size();
						});
						return (bmdm_checked_num == lisSize)&&(lisSize*njdm_id_size == njdm_checked_num);
					}else{
						return false;
					}
				}else{
					if($.founded(options.dataRanges["niutal_xtgl_jgdmb"])){
						var sfqxy = options.dataRanges["niutal_xtgl_jgdmb"][0]["sfqxy"];
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
        },
		afterRender:function(element,options){
        	
        	
			//全校按钮click事件
			$("#checkAll_qx").click(function(e){
				//已经选择：
				if($(this).prop("checked")){
					$(this).next("span").addClass("highlight");
					options.dataRanges["SCHOOL"] = [{"jg_id":"-1","sfqx":"1"}];
				}else{
					$(this).next("span").removeClass("highlight");
					options.dataRanges["SCHOOL"] = [];
				}
			});
			
			//=========================按部门=================================================
			
			// 按部门-机构类型change事件
			$("#bmdm_bmlb").change(function(e) {
				if(options.changeStatus==0){
					$("#dataRangeBox").datarange("reload",{
						"cydm_id_bmlb":$("#bmdm_bmlb").val()
					});
				}
			});
			
			// 按部门-年级change事件
			$("#bmdm_njdm_id").change(function(e) {
				if(options.changeStatus==0){
					var selecteds = $(this).getSelected();
					var attr = {"niutal_xtgl_jgdmb":[{
							"mapper":{"key":"njdm_id","text":"njmc"},
							"elementID" : "#bmdm_njdm_id",
							"list":[{"njdm_id":$(selecteds[0]).val(),"njmc":$(selecteds[0]).text()}]
						}]
					};
					if(!$.founded($(this).val())){
						attr["niutal_xtgl_jgdmb"][0]["list"] = njdmsJson;
					}
					$("#dataRangeBox").datarange("reload",{
						"cydm_id_bmlb":$("#bmdm_bmlb").val()
					},attr);
				}
			});
			
			//===========================按专业===============================================
			
			// 按专业-学院change事件
			$("#zydm_jg_id").change(function(e) {
				if(options.changeStatus==0){
					$("#dataRangeBox").datarange("reload",{
						"ls_bmdm":$("#zydm_jg_id").val()
					},{});
				}
			});
			
			// 按专业-年级change事件
			$("#zydm_njdm_id").change(function(e) {
				if(options.changeStatus==0){
					//触发重新加载数据
					var selecteds = $(this).getSelected();
					//初始化年级选项
					var attr = {"niutal_xtgl_zydmb":[{
							"mapper":{"key":"njdm_id","text":"njmc"},
							"elementID" : "#zydm_njdm_id",
							"list":[{"njdm_id":$(selecteds[0]).val(),"njmc":$(selecteds[0]).text()}]
						}]
					};
					if(!$.founded($(this).val())){
						attr["niutal_xtgl_zydmb"][0]["list"] = njdmsJson;
					}
					$("#dataRangeBox").datarange("reload",{
						"ls_bmdm":$("#zydm_jg_id").val()
					},attr);
				}
			});
			
			//=======================按班级===================================================
			
			if($("#bjdm_jg_id").size() > 0 ){
				
				//绑定级联查询事件：所属部门，所属专业，所属班级，所属年级
				$.bindChangeEvent("#bjdm_jg_id","","#bjdm_zyh_id","","","#bjdm_njdm_id",{
					//映射级联查询key与实际key的关系
					mapper : {"ls_bmdm":"jg_id","ls_zydm":"zyh_id","ls_bjdm":"bh_id","ls_njdm":"njdm_id"}
				},function(map){
					
				});
				
				$("#bjdm_searchCheck").click(function(e) {
					if(options.changeStatus==0){
						//选择(所属部门，所属专业)
						if($("#bjdm_jg_id").founded() && $("#bjdm_zyh_id").founded()){
							//重新加载A-Z组件数据
							$("#dataRangeBox").datarange("reload",$("div[aria-labelledby='niutal_xtgl_bjdmb']").serializeJSON());
						}else{
							$.alert("由于班级数据较多，易造成浏览器崩溃! 请选着具体 (所属部门，专业 ！");
						}
					}
				});
			}
			
			/**
			// 按班级-学院change事件
			$("#bjdm_jg_id").change(function(e) {
				if(options.changeStatus==0){
					//标记切换状态
					options.changeStatus = 1;
					$.ajax({
						url:_path+"/xtgl/comm_cxZydmList.html",
						type: "post",
						dataType:"json",
						async:false,
						data:{"jg_id":$("#bjdm_jg_id").val()},
						success:function(zydms){
							var html = "<option value=''>--全部--</option>";
							if(zydms.length>0){
								$("#bjdm_zyh_id").empty();
								for(var i=0;i<zydms.length;i++){
									html = html + "<option value="+zydms[i]["zyh_id"]+">"+zydms[i]["zymc"]+"</option>";
								}
					 		} 
							$("#bjdm_zyh_id").html(html);
							//标记切换状态
							options.changeStatus = 0;
						}
					});	
						
					$("#dataRangeBox").datarange("reload",[{
						"ls_bmdm":$("#bjdm_jg_id").val(),// 所属部门
						"ls_zydm":$("#bjdm_zyh_id").val(),// 所属专业
						"ls_njdm":$("#bjdm_njdm_id").val()// 所属年级
					}]);
				}
			});
			
			// 按班级-专业change事件
			$("#bjdm_zyh_id").change(function(e) {
				if(options.changeStatus==0){
					$("#dataRangeBox").datarange("reload",[{
						"ls_bmdm":$("#bjdm_jg_id").val(),// 所属部门
						"ls_zydm":$("#bjdm_zyh_id").val(),// 所属专业
						"ls_njdm":$("#bjdm_njdm_id").val()// 所属年级
					}]);
				}
			});
			
			// 按班级-年级change事件
			$("#bjdm_njdm_id").change(function(e) {
				if(options.changeStatus==0){
					$("#dataRangeBox").datarange("reload",[{
						"ls_bmdm":$("#bjdm_jg_id").val(),// 所属部门
						"ls_zydm":$("#bjdm_zyh_id").val(),// 所属专业
						"ls_njdm":$("#bjdm_njdm_id").val()// 所属年级
					}]);
				}
			});
			**/
			//==========================按学生================================================
			
			if($("#xs_jg_id").size() > 0 ){
				
				//绑定级联查询事件：所属部门，所属专业，所属班级，所属年级
				$.bindChangeEvent("#xs_jg_id","","#xs_zyh_id","","#xs_bh_id","#xs_njdm_id",{
					//映射级联查询key与实际key的关系
					mapper : {"ls_bmdm":"jg_id","ls_zydm":"zyh_id","ls_bjdm":"bh_id","ls_njdm":"njdm_id"}
				},function(map){
					
				});
				
				$("#xs_searchCheck").click(function(e) {
					if(options.changeStatus==0){
						//选择(所属部门，所属专业，所属年级) 或者 填写了学生号/名称
						if($("#xs_xh").founded() || ( $("#xs_jg_id").founded() && $("#xs_zyh_id").founded()  && $("#xs_njdm_id").founded() )){
							//重新加载A-Z组件数据
							$("#dataRangeBox").datarange("reload",$("div[aria-labelledby='view_xjgl_xsjbxxb']").serializeJSON());
						}else{
							$.alert("由于学生数据较多，易造成浏览器崩溃! 请选着具体 (所属部门，专业，年级) 或者 填写指定学生的学号/名称 ！");
						}
					}
				});
				
				$("#xs_xh").bind('keydown', 'return',function (event){
					//取消事件冒泡
					event.stopPropagation();
					if($("#xs_xh").founded()){
						//重新加载A-Z组件数据
						$("#dataRangeBox").datarange("reload",$("div[aria-labelledby='view_xjgl_xsjbxxb']").serializeJSON());
					}
				});
				
			}

			jQuery('select.chosen-select').trigger("chosen");
		}
	});
	
});