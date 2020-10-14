/**
 * bpm工作流前端
 */

(function($){
    
    	var UTILS = {
    		/**
    		 * 创建可退回节点表格
    		 */
    		_createFallbackActivityTable : function(data){
    		    var $table = $("<table>").addClass("table table-condensed table-bordered").prop("id", "bpm-task-process-form-fallback-table");
    		    var $thead = $("<thead><tr><th width='40%'>节点</th><th width='15%'>操作人</th><th width='25%'>操作时间</th><th width='20%'>#</th></tr></thead>").appendTo($table);
    		    var $tbody = $("<tbody>").appendTo($table);
    		    
    		    if(data == undefined || data.length == 0){
    			var $$emptyTr = $("<tr>").append("<td colspan='4'>暂无可退回节点！</td>");
    			$tbody.append($$emptyTr);
    			return $table;
    		    }
    		    
    		    $.each(data,function(i,n){
    			var $$tr = $("<tr class='" + (n["activityType"] == "startEvent"?'fallback-activity-initiator':'fallback-activity-task') + "'>").prop("id", n["activityId"])
          		   .append($("<td>").html(n["activityType"] == "startEvent"? "<span class='label label-danger'>流程发起人</span>" : "<span class='label label-primary'>" + n["activityName"] + "</span>"))
          		   .append($("<td>").html(n["assignee"]))
          		   .append($("<td>").html(n["endTime"]));
    			
    			var $$actionTd = $("<td>");
    			var $$detailLink = $("<button type=\"button\" class=\"btn btn-xs btn-link hidden\">详 情</button>").on("click", function(){
        		    
        		});
    			
        		var $$setupLink = $("<button name=\"select-btn\" type=\"button\" class=\"btn btn-default btn-xs\">选 择</button>").on("click", function(){
        		    $('[data-toggle*="bpm-process-fallback-event"]').trigger("bpm-process-fallback-changed", n['id']);
        		   
        		    $tbody.find("tr").removeClass("warning");
        		    $tbody.find("button[name='select-btn']").empty().html("选 择");
        		    $$tr.addClass("warning");
        		    
        		    $(this).empty().append("<i class=\"glyphicon glyphicon-ok\">选择</i>")
        		});
        		
        		$$actionTd.append($$detailLink).append($$setupLink);
        		$$tr.append($$actionTd);
        		$tbody.append($$tr);
		    });
    		    
    		    return $table;
    		}
    	};
    	/**
	 * 审核对象
	 */
    	var ProcessDecisionModel = function() {
    		this.isPass = function(){
    		    return this.pdecision == 'PASS';
    		},
    	    	this.isNoPass = function(){
    	    	    return this.pdecision == 'NOPASS';
    	    	},
    	    	this.isFallback = function(){
    	    	    return this.pdecision == 'FALLBACK';
    	    	},
    	    	this.getFallbackActivityId = function(){
    	    	    return this.fallbackActivityId;
    	    	},
    	    	this.getProcessDecision = function(){
    	    	    return this.pdecision;
    	    	},
    	    	this.getDecisionMessage = function(){
    	    	    return this.pdecisionMessage;
    	    	}
    	};
    	
        var bpmProcessConfig = {
		processData	:{},
		width		: "1200px",
		modalName	: "bpmProcessModal",
		formName	: "ajaxForm",
		gridName	: "tabGrid",
		offAtOnce	: true,
		buttons		: {
			success : {
				label : "提 交",
				className : "btn-success",
				callback : function() {
					var $this = this;
					var opts = $this["options"]||{};
					submitForm("ajaxForm",function(responseText,statusText){
						$this.reset();
						if(responseText["status"] == "success"){
							$.success(responseText["message"],function() {
								if(opts.offAtOnce){
								    $.closeModal(opts["modalName"]||"bpmProcessModal");
								}
								var tabGrid = $("#" + opts["gridName"]||"tabGrid");
								if($(tabGrid).size() > 0){
									$(tabGrid).refreshGrid();
								}
							});
						}else if(responseText["status"] == "fail"){
							$.error(responseText["message"]);
						}else{
							$.alert(responseText["message"]);
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
	};
	
	// 批量修改弹窗
	$.bpmDialog = function(href, title, options){
		if($("#statusModal").size() > 0){return;}
		var opts = $.extend(true, {}, bpmProcessConfig,options||{});
		// 弹窗
		$.dialog($.extend(opts,{
			"href": href||"",
			"title": title,
		    	/* 回调 */
			"onLoaded": function(){
			    // console.debug(
			    // _path+"/processInstance/"+opts["processData"]["processInstanceId"]+"/"+opts["processData"]["taskInstanceId"]+"/getTaskProcessForm.zf");
			    $.post(
				    _path+"/processInstance/"+
				    opts["processData"]["processInstanceId"]+"/"+
				    opts["processData"]["taskInstanceId"]+"/getTaskProcessForm.zf", 
				    {}, 
				    function(html){
					$("#"+ opts["formName"]).append(html);
					//常用意见加载事件绑定
					$("#"+ opts["modalName"]).find("#bpm-task-process-common-messages").on("click", function(){
					    var $this = $(this);
					    if((!$this.attr("data-loaded")) || $this.attr("data-loaded") == 'false'){
						var assignee = $("#"+ opts["modalName"]).find("#bpm-task-process-form input[name='taskAssignee']").val();
						$.getJSON(_path+"/processCommonMessage/" + assignee + "/getUserCommonMessageList.zf", function(commonMessageData){
						    if(commonMessageData && commonMessageData.length > 0){
							$("#"+ opts["modalName"]).find("#bpm-task-process-common-messages-list table tbody").empty();
							$.each(commonMessageData, function(i,n){
							    var tr = $("<tr data-id='" +n["id"]+ "'></tr>");
							    var msgTd = $("<td width='90%'></td>")
							    var msgLink = $("<a href='javascript:void(0);'>" + n["message"] + "</a>") .on("click", function(){
								$("#"+ opts["modalName"]).find("#p_decision_message").val(n["message"]);
							    });
							    var actionTd = $("<td width='10%'></td>");
							    var delBtn = $("<button type='button' class='btn btn-danger btn-xs'>删 除</button>").on("click", function(){
								var $$this = this;
								$.post(_path + "/processCommonMessage/" + n["id"] + "/deleteCommonMessage.zf", function(deletMessage){
								    $$this.closest("tr[data-id='" + n["id"] + "']").remove();
								});
							    });
							    msgTd.append(msgLink);
							    actionTd.append(delBtn);
							    tr.append(msgTd).append(actionTd);
							    $("#"+ opts["modalName"]).find("#bpm-task-process-common-messages-list table tbody").append(tr);
							});
							$this.attr("data-loaded", true);
						    }
						  
						});
					    }
					});
					
					$("#"+ opts["modalName"]).find("#p_decision_message").keyup(function(){
					    if($.trim($(this).val()).length > 0){
						$("#"+ opts["modalName"]).find("#commonMessageAddEl").removeClass("hide");
					    }else{
						$("#"+ opts["modalName"]).find("#commonMessageAddEl").addClass("hide");
					    }
					    
					});
					
					$("#"+ opts["modalName"]).find("#commonMessageAddEl a").click(function(){
					    var $this = this;
					    var assignee = $("#"+ opts["modalName"]).find("#bpm-task-process-form input[name='taskAssignee']").val();
					    var message = $("#"+ opts["modalName"]).find("#bpm-task-process-form textarea[name='decision.decisionMessage']").val();
					    $.post(_path+"/processCommonMessage/addCommonMessage.zf", {userId: assignee, message: message},function(data){
						if(data["status"] == "success"){
						    var successSign = $("<span class='text-success'><i class='glyphicon glyphicon-ok'></i> 保存成功！</span>");
						    $("#"+ opts["modalName"]).find("#commonMessageAddEl").append(successSign);
						    $("#"+ opts["modalName"]).find("#bpm-task-process-common-messages").attr("data-loaded", false);
						    setTimeout(function(){
							successSign.fadeOut("slow", function(){
							    $(this).remove();
							});
						    }, 2000);
						}else{
						    var failSign = $("<span class='text-danger'><i class='glyphicon glyphicon-remove'></i> 保存失败！</span>");
						    $("#"+ opts["modalName"]).find("#commonMessageAddEl").append(failSign);
						    setTimeout(function(){
							failSign.fadeOut("slow", function(){
							    $(this).remove();
							});
						    }, 2000);
						}
					    });
					});
					
					// 标记元素
					var elements = $("#"+ opts["modalName"]).find('[data-toggle*="bpm-process-event"]');

					$("#"+ opts["modalName"]).off('bpm-process-fallback-changed','[data-toggle*="bpm-process-fallback-event"]').
						on('bpm-process-fallback-changed', '[data-toggle*="bpm-process-fallback-event"]', function (event,data) {
						   $(this).val(data);
		    			});
					
					// 触发事件
					$("#"+ opts["modalName"]).on("change","[name='decision.decision']",function(){
					    var processModel = new ProcessDecisionModel();
					    var pdecesion = $("[name='decision.decision']:checked", "#"+ opts["modalName"]).val();
					    var pdecisionMessage = $("[name='decision.decisionMessage']", "#"+ opts["modalName"]).val();
					    processModel['processInstanceId'] = opts["processData"]["processInstanceId"];
					    processModel['taskInstanceId'] = opts["processData"]["taskInstanceId"];
					    processModel['pdecision'] = pdecesion;
					    processModel['pdecisionMessage'] = pdecisionMessage;
					    elements.trigger("bpm-process-decision-changed",processModel);
					    
					    if(processModel.isFallback()){
						$("#bpm-task-process-form-fallback","#"+ opts["modalName"]).removeClass("hidden");
						$("#bpm-task-process-form-fallback","#"+ opts["modalName"]).append("<i id=\"loading-spin\" class=\"fa fa-spinner fa-spin fa-2x fa-fw\"></i>")
						$.get(_path+"/processInstance/"+
							opts["processData"]["processInstanceId"]+"/"+
							opts["processData"]["taskInstanceId"]+"/getFallbackableActivityList.zf",
							{},
							function(data){
							    var $fallbackActivityTable = UTILS._createFallbackActivityTable(data);
							    $("#bpm-task-process-form-fallback-table", "#bpm-task-process-form-fallback","#"+ opts["modalName"]).remove();
							    $("#bpm-task-process-form-fallback","#"+ opts["modalName"]).append($fallbackActivityTable);
							    $("#loading-spin","#bpm-task-process-form-fallback","#"+ opts["modalName"]).remove();
							    
							    if($("input[name='p_act_id']").val()!=null){
								    var t_act_line = null;
								    var t_act_ids = [];
								    var p_act_ids = [];
								    $("#bpm-task-process-form-fallback-table tbody tr").each(function(index,item){
								    	t_act_ids.push($(item).prop("id"));
									});
								    for(var i=t_act_ids.length-1;i>=0;i--){
								    	if(i==t_act_ids.length-1){
								    		p_act_ids.push(t_act_ids[i]);
								    		t_act_line=t_act_ids[i];
								    	}else if(t_act_line.indexOf(t_act_ids[i])==-1){
								    		p_act_ids.push(t_act_ids[i]);
								    		t_act_line=t_act_line+"~"+t_act_ids[i];
								    	}
								    }
								    var p_act_id = $("#p_act_id").val();
									//$("[id='"+p_act_id+"']").css("display","none");
									//var jdList=["task_gjjyxy","task_zkzxy","task_zcxy","task_zrxy","task_jwc"];
									var flag = false;
									$.each(p_act_ids,function(index,item){
										if(flag){
											$("[id='"+item+"']").css("display","none");
										}else if(item==p_act_id){
											flag=true;
											$("[id='"+p_act_id+"']").css("display","none");
										}
									});
									var jdLine = "";
									$("#bpm-task-process-form-fallback-table tbody tr").each(function(index,item){
										var act_id = $(item).prop("id");
										if(index==0){
											jdLine = act_id;
										}else if(jdLine.indexOf(act_id)==-1){
											jdLine = jdLine + act_id;
										}else{
											$(item).css("display","none");
										}
									});
							    }
							});
					    }else{
					    	$("#bpm-task-process-form-fallback","#"+ opts["modalName"]).addClass("hidden");
					    }
					});
				    });
			}
		}));
	};
})(jQuery);