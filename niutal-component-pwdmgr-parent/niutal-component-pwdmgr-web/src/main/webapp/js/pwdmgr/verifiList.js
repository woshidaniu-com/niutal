jQuery(function($){

	$('#dataTable').loadGrid({
		 url		: _path + '/pwdmgr/setting/verifiList.zf',  
		 uniqueId	: "id", 
		 pagination	: false,
		 toolbar	: '#but_ancd',
		 showColumns: true,
		 showToggle	: true,
		 showRefresh: true,
		 mobileResponsive:true,
		 classes:'table table-condensed',
		 columns: [
           {checkbox: true,width:'5%' }, 
		 	{field: 'id', title: '主 键',visible:false},  
            {field: 'name', title: '字段名称',width:'25%',
            	formatter:function(cellvalue,rowObject,index){
            		var html = [];
	            		html.push('<input name="list['+index+'].id" type="hidden" value="' + rowObject.id + '" class="form-control" validate="{required:true}"/>');
	            		html.push('<input name="list['+index+'].name" type="text" value="' + (cellvalue || "") + '"  placeholder="该字段需要是学生/教师个人信息表中存在且同名的列名" class="form-control" validate="{required:true}"/>');
		        	return html.join("");
            	}
            },
            {field: 'label', title: '字段Label',width:'10%',
            	formatter:function(cellvalue,rowObject,index){
		        	return '<input name="list['+index+'].label" type="text" value="' + (cellvalue || "") + '" placeholder="字段在页面显示的名称" class="form-control" validate="{required:true}"/>';
            	}
            },
            {field: 'rules',title: '校验规则',width:'20%',
            	formatter:function(cellvalue,rowObject,index){
		        	return '<input name="list['+index+'].rules" type="text" value="' + (cellvalue || "{required:true}") + '"  class="form-control" validate="{required:true}"/>';
            	}
            },
            {field: 'desc', title: '字段描述',width:'20%',
            	formatter:function(cellvalue,rowObject,index){
		        	return '<input name="list['+index+'].desc" type="text" value="' + (cellvalue || "") + '"  class="form-control" validate="{required:true}"/>';
            	}
            },
            {field: 'required',title: '是否必填',width:'10%',formatter:function(cellvalue,rowObject,index){
	        	 	var html = [];
	        	 	html.push('<select data-id="' + rowObject.id + '" name="list['+index+'].required" class="form-control chosen-select"  validate="{required:true}" val="'+cellvalue+'">');
					//循环数据
					$.each([{"key":"1","value":"是"},{"key":"0","value":"否"}],function(i,item){
						html.push('<option '+(cellvalue ==  item["key"] ? ' selected="selected" ' : "")+' value="'+item["key"]+'">'+item["value"]+'</option>');
					});
	        		html.push('</select>');
	        	 	return html.join("");
	        	} 
	        }, 
            {field: 'status',title: '状态',width:'10%',formatter:function(cellvalue,rowObject,index){
	            	if("-1" == cellvalue){
	            		return '<span class="text-muted">不可用（待开发）</span>';
	            	};
	        	 	var html = [];
	        	 	html.push('<select data-id="' + rowObject.id + '" name="list['+index+'].status" class="form-control chosen-select" validate="{required:true}" val="'+cellvalue+'">');
					//循环数据
					$.each([{"key":"1","value":"启用"},{"key":"0","value":"停用"}],function(i,item){
						html.push('<option '+(cellvalue ==  item["key"] ? ' selected="selected" ' : "")+' value="'+item["key"]+'">'+item["value"]+'</option>');
					});
	        		html.push('</select>');
	        	 	return html.join("");
	        	} 
	        }
         ],
         onPostBody:		function(){
         	$(this).find("select.chosen-select").trigger("chosen");
         }
	});
	
	//增加操作仅页面增加一行记录，需要填写后进行保存
	$(document).off("click touched", "#btn_zj").on("click touched", "#btn_zj", function(e) {
		$('#dataTable').appendRow({
			"id"		: "R-" + $('#dataTable').getRowCount(),
			"required"	: "1",
			"status"	: "1"
		});
	})
	//保存操作
	.off("click touched", "#btn_bc").on("click touched", "#btn_bc", function(e) {
		submitForm("ajaxForm",function(responseData,statusText){
			if(responseData["status"] == "success"){
				$.success(responseData["message"],function(){
    				$('#dataTable').refreshGrid();
    			});
			}else{
				$.error(responseData["message"]);
			}
		});
	})
	//删除操作
	.off("click touched", "#btn_sc").on("click touched", "#btn_sc", function(e) {
		
		var keys = $('#dataTable').getKeys();
		if(keys.length == 0){
			$.alert("请选择要删除的记录！");
			return ;
		}
		var ids = [];
		$.each(keys,function(i,key){
			if(key.indexOf("R-") > -1){
				$('#dataTable').removeRow(key);
			}else{
				ids.push(key);
			}
		});
		if(ids.length == 0){
			return ;
		}
		jQuery.post(_path + '/pwdmgr/setting/delVerifi.zf', {
			"id"	: keys.join(",")
		}, function(data){
    		//没有通过检查
    		if ("success" != data["status"]){
    			$.error(data["message"]);
    		}else{
    			$.success(data["message"],function(){
    				$('#dataTable').refreshGrid();
    			});
    		}
		}, "json");
		
	});
	
	
});