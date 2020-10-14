jQuery(function($){
	
	var options = {
			 url: 'listData.zf',
			 uniqueId: "id",
			 toolbar:  '#but_ancd',
			 classes:'table table-condensed',
			 striped: false,
			 columns: [
	            {checkbox: true }, 
	            {field: 'id', title: 'ID',width:'80px', sortable:false,align:'center',visible:false,hidden:true},
	            {field: 'name',title: ' 表 单 名 称 ',sortable:false,width:'100px',align:'center'},
	            {field: 'createTime',title: ' 创 建 时 间  ',align:'center',sortable:false,width:'200px', formatter:function(value,row,index){
	            	var d = new Date(value);    //根据时间戳生成的时间对象
	            	var date = (d.getFullYear()) + "-" + 
	            	           (d.getMonth() + 1) + "-" +
	            	           (d.getDate()) + " " + 
	            	           (d.getHours()) + ":" + 
	            	           (d.getMinutes()) + ":" + 
	            	           (d.getSeconds());
	            	return date;
	            }},
	            {field: 'lastUpdateTime',title: ' 最 后 更 新 时 间 ',align:'center',sortable:false,width:'200px',formatter:function(value,row,index){
	            	var d = new Date(value);    //根据时间戳生成的时间对象
	            	var date = (d.getFullYear()) + "-" + 
	            	           (d.getMonth() + 1) + "-" +
	            	           (d.getDate()) + " " + 
	            	           (d.getHours()) + ":" + 
	            	           (d.getMinutes()) + ":" + 
	            	           (d.getSeconds());
	            	return date;
	            }},
	            {field: 'deploymentId',title: ' 部 署 状 态 ', align:'center',sortable:false,width:'80px',formatter:function(value,row,index){
	            	var ret;
	            	if($.founded(value)){
	            		 ret = '<span class="text-success">已部署</span>';	
	            	}else{
	            		 ret = '<span class="text-danger">未部署</span>';
	            	}
	            	return ret;
	            }}
           ],
           queryParams:function(params){
	        	var queryMap = {};
				queryMap["showCount"] = params["pageSize"];
				queryMap["currentPage"] = params["pageNumber"];
				return queryMap;
	        },
           searchParams:function(){
    	    var map = {};
    		map["searchModelName"] = jQuery('#searchModelName').val();
    		map["searchDeploymentState"] = jQuery('#searchDeploymentState').val();
           	return map;
           }
		};
		$('#tabGrid').loadGrid(options);
	
	/* ====================================================绑定按钮事件==================================================== */
	
		
		
	var formModulerConfig = {
				width		: "700",
				modalName	: "formModulerModal",
				formName	: "ajaxForm",
				gridName	: "tabGrid",
				offAtOnce	: true,
				buttons		: {
					success : {
						label : "保 存",
						className : "btn-primary",
						callback : function() {
							var $this = this;
							submitForm("ajaxForm",function(responseText,statusText){
								$this.reset();
								
								if(responseText["status"] == "success"){
								    	$.success(responseText["message"],function() {
								    	    $('#tabGrid').refreshGrid();
								    	    $.closeModal("formModulerModal");
								    	});
									$.closeModal('formModulerModal');
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
		
		
	// 绑定增加事件
	jQuery("#btn_zj").click(function () {
		var url = _path + "/form/moduler/add.zf";
		$.showDialog(url,'新增模型',$.extend({},formModulerConfig,{}));
	});	
		
	
	// 绑定查看事件
	jQuery("#btn_ck").click(function () {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选择您要修改的记录！');
			return false;
		}
		var rowData = $('#tabGrid').getRow(ids[0]);
		var editorUrl = "view.zf?modelId="+ids[0];
		$.showDialog(editorUrl,'查看模表单模型',$.extend({},viewConfig,{"width":'900px'}));

	});
	
	// 绑定设计事件
	jQuery("#btn_sj").click(function(){
	    	var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选择一条您要设计的表单模型！');
			return false;
		}
		var editorUrl = _path+"/form-build-app/modeler.html?modelId="+ids[0]+"&context="+_path;
		window.open(editorUrl);
	});
	
	// 绑定修改事件
	jQuery("#btn_xg").click(function () {
		var ids = $("#tabGrid").getKeys();
		if (ids.length != 1) {
			$.alert('请选择一条您要修改的表单模型！');
			return false;
		}
		var rowData = $('#tabGrid').getRow(ids[0]);
		var editorUrl = _path + "/form/moduler/" + ids[0] + "/update-metainfo.zf";
		$.showDialog(editorUrl,'修 改',$.extend({},addConfig,{"width":'700',buttons:{
				success : {
					label : "保存",
					className : "btn-primary",
					callback : function() {
						var $this = this;
						var opts = $this["options"]||{};
						if(!$('#ajaxForm').valid()){
							return false;
						}
						submitForm(opts["formName"]||"ajaxForm",function(responseText,statusText){
							$this.reset();
							
							if(responseText["status"] == "success"){
								$.success(responseText["message"],function() {
									$('#tabGrid').refreshGrid();
									$.closeModal(opts.modalName);
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
		}}));
	});	
	
	// 绑定删除事件
	jQuery("#btn_sc").click(function() {
		var ids = $("#tabGrid").getKeys();
		if (ids.length == 0) {
			$.alert('请选择您要删除表单模型！');
			return false;
		}
		var canDelete = true;
		$.each(ids, function(i,n){
			var rowData = $('#tabGrid').getRow(ids[0]);
			if($.founded(rowData['deploymentId'])){
				canDelete = false;
				return false;
			}
		});
		if(!canDelete){
			$.alert("所选的表单模型中存在已部署数据，请确认！");
			return false;
		}
		$.confirm('您确定要删除选择的记录吗？',function(result) {
			if(result){
				jQuery.post('del.zf', {
					"modelIds" : ids.join(",")
				}, function(responseText) {
					if(responseText["status"] == "success"){
						$.success(responseText["message"],function() {
							if($("#tabGrid").size() > 0){
								$("#tabGrid").reloadGrid();
							}
						});
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					} else{
						$.alert(responseText["message"]);
					}
				}, 'json');
			}
		});
	});
	
	// 绑定copy事件
	jQuery("#btn_fz").click(function () {
		var ids = $('#tabGrid').getKeys();
		if (ids.length != 1 ) {
			$.alert('请选定一条记录!');
			return;
		}
		var rowData = $('#tabGrid').getRow(ids[0]);
		var url = _path + "/form/moduler/" + ids[0] + "/copy.zf";
		$.showDialog(url,'表单模型复制',$.extend({},modifyConfig,{"width":600}));

	});
	
	// 绑定导出事件
	jQuery("#btn_dc").click(function () {
		var ids = $('#tabGrid').getKeys();
		if (ids.length != 1 ) {
			$.alert('请选定一条记录!');
			return;
		}
		var rowData = $('#tabGrid').getRow(ids[0]);
		var exportModelForm = $("#model-export-form");
		var exportModelForm_url =  _path + "/form/moduler/" + ids[0] + "/export.zf";
		exportModelForm.attr("action", exportModelForm_url);
		exportModelForm.submit();
	});
	
	// 绑定部署事件
	jQuery("#btn_bs").click(function () {
		var ids = $('#tabGrid').getKeys();
		if (ids.length != 1 ) {
			$.alert('请选定一条记录!');
			return;
		}
		var rowData = $('#tabGrid').getRow(ids[0]);
		var serverData = {'id': ids[0]};
		var url = ids[0] + "/deployData.zf";
		$.confirm('您确定部署？',function(result) {
			if(result){
				jQuery.post(url, serverData, function(responseText) {
					if(responseText["status"] == "success"){
						$.success(responseText["message"],function() {
							$('#tabGrid').refreshGrid();
						});
					}else if(responseText["status"] == "fail"){
						$.error(responseText["message"]);
					}else{
						$.alert(responseText["message"]);
					}
				}, 'json');
			}
		});
	});

	
});

// 查询
function searchResult(){
	$('#tabGrid').refreshGrid();
}

//回车键查询
$('#searchModelName').bind("keydown", "return", function (ev) {
	searchResult()   
})
