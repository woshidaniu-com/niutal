jQuery(function($){
	
	$('#dataTable').loadGrid({
		 url		: _path + '/pwdmgr/setting/strategyList.zf',  
		 uniqueId	: "id", 
		 pagination	: false,
		 toolbar	: '#but_ancd',
		 showColumns: true,
		 showToggle	: true,
		 showRefresh: true,
		 mobileResponsive:true,
		 classes:'table table-condensed',
		 columns: [
		 	{field: 'id', title: '主 键',visible:false},  
            {field: 'status',title: '状态',width:'10%',formatter:function(cellvalue,rowObject,index){
	            	if("-1" == cellvalue){
	            		return '<span class="text-muted">不可用（待开发）</span>';
	            	};
            	 	var html = [];
	        	 	html.push('<select data-placement="right" data-id="' + rowObject.id + '" name="status_'+cellvalue+'" class="form-control chosen-select"  validate="{required:true}" val="'+cellvalue+'">');
					//循环数据
					$.each([{"key":"1","value":"启用"},{"key":"0","value":"停用"}],function(i,item){
						html.push('<option '+(cellvalue ==  item["key"] ? ' selected="selected" ' : "")+' value="'+item["key"]+'">'+item["value"]+'</option>');
					});
	        		html.push('</select>');
	        	 	return html.join("");
            	} 
            }, 
            {field: 'name', title: '找回方式',width:'20%'},
            {field: 'desc', title: '找回方式描述',width:'70%'}
         ],
         onPostBody:		function(){
         	$(this).find("select.chosen-select").trigger("chosen");
         }
	});
	
	$(document).off("change", "select.chosen-select").on("change", "select.chosen-select", function(e) {
		
		jQuery.post(_path + '/pwdmgr/setting/strategyUpdate.zf', {
			"id"	: $(this).data("id"),
			"status"		: $(this).getSelected().val()
		}, function(data){
    		//没有通过检查
    		if ("success" != data["status"]){
    			$.error(data["message"]);
    		}else{
    			$.success(data["message"]);
    		}
		}, "json");
		
	});
	
});