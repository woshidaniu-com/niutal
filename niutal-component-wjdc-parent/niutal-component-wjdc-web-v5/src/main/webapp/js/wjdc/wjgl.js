
$(function(){
	var ops = {
		 url: 'getWjxxList.zf',		//请求后台的URL
		 uniqueId: "wjid",     		//每一行的唯一标识，一般为主键列
		 cookieIdTable:'wjdc-wjgl',
		 toolbar:  '#but_ancd',
		 cookie:true,
		 columns: [
            {checkbox: true }, 
            {field: 'wjmc', title: '问卷名称',sortable:true}, 
            {field: 'wjzt', title: '问卷状态',sortable:true,formatter:function(v,r,i){
            	var text;
            	switch(v){
            		case "DRAFT" :
            			text = "草稿";
            			break;
            		case "RUN" :
            			text = "运行";
            			break;
            		case "STOP" :
            			text = "停止";
            			break;
            	}
            	return text;
            }}, 
//            {field: 'fblx',title: '发布类型',sortable:true,formatter:function(v,r,i){
//            	var text = v == "0"  ? "普通" : "匿名";
//            	return text;
//            }}, 
            {field: 'cjrmc',title: '创建人',sortable:true},
            {field: 'cjsj',title: '创建时间',sortable:true},
            {field: 'gqsj',title: '过期时间',sortable:true},
            {field: 'ffzt',title: '发放状态',sortable:true,formatter:function(v,r,i){
            	var text = v==0 ? "未发放" : "已发放";
            	return text;
            }}
         ],
         searchParams:function(){
        	 return $.search.getSearchMap();
         }
	};
	$('#tabGrid').loadGrid(ops);
	
	$("#search_button").bind("click",searchResult);
	
	//绑定增加点击事件
	jQuery("#btn_zj").click(function () {
		$.showDialog('zjWjxx.zf','创建问卷',$.extend({},addConfig,{"width":"800px","buttons":{
			success : {
				label : "下一步",
				className : "btn-primary",
				callback : function() {
					var $this = this;
					var opts = $this["options"]||{};
					submitForm(opts["formName"]||"ajaxForm",function(responseData,statusText){
						var wjid = $("#wjid").val();
						var type = $(".choose-template a").index($(".choose-template .active"));
						
						if(responseData["status"] == "success"){
							$.closeModal("addModal");
							//刷新列表数据
							var tabGrid = $("#" + opts["gridName"]||"tabGrid");
							//清除页面元素
							if($(tabGrid).size() > 0){
								$(tabGrid).reloadGrid();
							}
							$.showDialog("editWjst/"+wjid+".zf?type="+type,"设计问卷",{
								modalName:"modifyModal",
								width:$("#bodyContainer").width()*0.7+"px",
								buttons : {
									success:{
										label:"确定",
										className : "btn-primary",
										callback:function(){
											var $this = this;
											var opts = $this["options"]||{};
											submitForm("ajaxForm",function(responseData,statusText){
												   if(responseData["status"] == "success"){
														$.success(responseData["message"],function() {
															$.closeModal("modifyModal");
														});
													}else if(responseData["status"] == "error"){
														$.error(responseData["message"]);
													}else{
														$.alert(responseData["message"]);
													}
												});
												return false;
										}
									},
									cancel:{
										label : "取 消",
										className : "btn-default"
									}
								}
							});
						}else if(responseData["status"] == "error"){
							$.error(responseData["message"]);
						}else{
							$.alert(responseData["message"]);
						}
					});
					return false;
				}
			},
			cancel : {
				label : "关 闭",
				className : "btn-default"
			}
		}}));
	});
	
	// 绑定修改事件
	jQuery("#btn_xg").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}	
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog('xgWjxx.zf?wjid=' + ids[0],'修改问卷',$.mergeObj(modifyConfig,{
			"width":"800px"
		}));
		return false;
	});
	
	jQuery("#btn_ffwj").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		var wjid = ids[0];
		var row = jQuery("#tabGrid").getRow(wjid);
		
		var wjffConfig = {
			"width":"900px",
			"buttons":{
				success : {
					label : "分发",
					callback : function() {
						
						function startWating(){
							jQuery("#modifyModal :button").attr("disabled","disabled");
							jQuery("#modifyModal :checkbox").attr("disabled","disabled");
							jQuery(".loadBanner").addClass("loadBanner_show");
						}
						
						function stopWating(){
							jQuery("#modifyModal :button").removeAttr("disabled");
							jQuery("#modifyModal :checkbox").removeAttr("disabled");
							jQuery(".loadBanner").removeClass("loadBanner_show");
						}
						
						var $this = this;
						
						if ($("#modifyModal :checked[name=dxid]").size() <= 0){
							$.error("请选择问卷分发对象");
							return false;
						}

						var ffdx = [];
						$("#modifyModal :input[name=dxid]").each(function(x,y){
							if ($(y).prop("checked")){
								if ($(y).parents("td").next().find(".active").size()>0){
									$(y).parents("td").next().find(".content .active").each(function(i,n){
										var dxid = $(n).data("dxid");
										var tjid = $(n).data("tjid");
										var tjz = $(n).data("key");
										ffdx.push({ffdx:dxid,dxtj:tjid,tjz:tjz.toString()});
									});
								} else {
									ffdx.push({ffdx:$(":input[name=dxid]").eq(x).val()});
								}
							}
						});
						
						var ffdxParam = JSON.stringify(ffdx);
						$("#ffdx").val(ffdxParam);
						
						var ywgn = [];
						$("#modifyModal .tab-cnt .active").each(function(i,n){
							ywgn.push($(n).data("gnid"));
						});
						
						var ywgnParam = ywgn.join(",");
						$("#gnid").val(ywgnParam);
						
						startWating();
						submitForm("ajaxForm",function(responseData,statusText){
							
							$this.reset();
							stopWating();
							
							if(responseData["status"] == "success"){
								stopWating();
								$.success(responseData["message"],function() {
									$.closeModal("modifyModal");
									$("#tabGrid").reloadGrid({});
								});
							}else if(responseData["status"] == "error"){
								stopWating();
								$.error(responseData["message"]);
							}else{
								stopWating();
								$.alert(responseData["message"]);
							}
						});
						return false;
					}
				},
				cancel : {
					label : "取消",
				}
			}
		};
		
		//判断是否已经分发过此问卷了
		var list = null;
		var url = _path +"/wjdc/wjgl/getWjffList.zf";
	     $.ajax({
	         type: "POST",
	         url: url,
	         data: {
	        	 "wjid":wjid,
	         },
	         async:false,
	         dataType: "json",
	         success: function(responseData){
				list = responseData;
	         }
	     })
		//以前没有分发过问卷
		if(list == null || list.length <= 0){
			$.showDialog(_path+'/wjdc/wjgl/wjff.zf?wjid=' + wjid,'分发问卷【'+row["wjmc"]+"】",$.mergeObj(modifyConfig,wjffConfig));
		}else{//以前有分发过问卷
			$.confirm('此问卷在此之前已经分发过，再次分发将导致已存在的用户回答被删除，您确定要重新分发吗？',function(result) {
				if(result){
					$.showDialog(_path+'/wjdc/wjgl/wjff.zf?wjid=' + wjid,'分发问卷【'+row["wjmc"]+"】",$.mergeObj(modifyConfig,wjffConfig));
				}else{
					//取消
				}
			});
		}
	});
	
	// 绑定修改事件
	jQuery("#btn_ztxg").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog('updateWjzt.zf?wjid=' + ids[0],'修改问卷状态',$.mergeObj(modifyConfig,{
			"width":"450px"
		}));
		return false;
	});
	
	
	// 绑定问卷设计事件
	jQuery("#btn_wjsj").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		var wjid = ids[0];
		//判断是否已经分发过此问卷了
		var list = null;
		var url = _path +"/wjdc/wjgl/getWjffList.zf";
	     $.ajax({
	         type: "POST",
	         url: url,
	         data: {
	        	 "wjid":wjid,
	         },
	         async:false,
	         dataType: "json",
	         success: function(responseData){
				list = responseData;
	         }
	     })
		//以前没有分发过问卷
		if(list == null || list.length <= 0){
			$.showDialog("editWjst/"+wjid+".zf?type=0",'设计问卷',$.extend({},modifyConfig,{"width":$("#bodyContainer").width()*0.7+"px"}));
		}else{//以前有分发过问卷
			$.confirm('此问卷已分发，再次设计并保存此问卷将有可能导致已存在的用户回答被删除，您确定要重新设计问卷吗？',function(result) {
				if(result){
					$.showDialog("editWjst/"+wjid+".zf?type=0",'设计问卷',$.extend({},modifyConfig,{"width":$("#bodyContainer").width()*0.7+"px"}));
				}else{
					//取消
				}
			});
		}
	});
	
	// 绑定问卷预览事件
	jQuery("#btn_wjyl").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}	
		//$.showDialog("ckWjxx/"+ids[0]+".zf",'预览问卷',$.extend({},viewConfig,{"width":$("#bodyContainer").width()*0.7+"px"}));
		//预览新方式，打开新标签页
		var url = _path + "/wjdc/wjgl/ckWjxx.zf?wjid="+ids[0];
		window.open(url, "_blank");
	});
	
	$("#btn_djtj").click(function(){
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		$.showDialog("xxtj.zf?wjid="+ids[0] ,'答卷统计',$.extend({},viewConfig,
				{
					"width":$("#bodyContainer").width()*0.7+"px",
					"buttons":{
						success : {
							label : "导出",
							className : "btn-primary",
							callback : function() {
								var url = _path + "/wjdc/wjgl/exportXxtj.zf?wjid="+ids[0];
								//window.open(url, "_blank");
								window.location.href=url;
								return false;
							}
						}/**,
						cancel : {
							label : "导出详情",
							className : "btn-primary",
							callback : function() {
								var url = _path + "/wjdc/wjgl/exportDtxqCustom.zf?wjid="+ids[0];
								//window.open(url, "_blank");
								window.location.href=url;
								return false;
							}
						}**/
					}
				}
		));
		return false;
	});
	
	$("#btn_wjdjxq").click(function(){
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		window.open("exportDtxq.zf?wjid="+ids[0]);
		return false;
	});
	
	//绑定删除事件
	jQuery("#btn_sc").click(function () {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}	
		$("#tabGrid").plcz('scWjxx.zf','删除');
	});
	
	//复制事件
	jQuery("#btn_fz").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		$.showDialog('copyWjxx.zf?wjid=' + ids[0],'复制问卷',$.mergeObj(modifyConfig,{
			"width":"800px"
		}));
		return false;
	});
	
	jQuery("#btn_ck").click(function(){
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		$.showDialog("ckWjBaseXx.zf?wjid="+ids[0] ,'查看问卷基本信息',$.extend({},viewConfig,{"width":$("#bodyContainer").width()*0.7+"px"}));
	});
	
	$(":button[name=search_button]").bind("click",searchResult);
});

function searchResult(){
	$('#tabGrid').bootstrapTable('refresh' );
}