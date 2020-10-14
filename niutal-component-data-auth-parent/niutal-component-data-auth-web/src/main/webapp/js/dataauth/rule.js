jQuery(function($){
	
	/**权限组列表*/
	$('#ruleGrid').bootstrapTable({
		 url: _path + '/dataauth/data/rule/ruleList.zf',uniqueId: 'ruleId',
		 method : 'post',contentType : 'application/x-www-form-urlencoded',
		 showToggle:false,showRefresh:false,showColumns:false,pageList : [5,10,15,50],
		 sidePagination: 'server',queryParamsType : 'limit',pageSize: 15,pagination: true,
		 classes : 'table table-striped table-hover table-condensed table-api text-center',
		 columns: [{
			 title : '序号',formatter : function(value,row,index){return (index + 1);}
		 },{
			 field: 'ruleId', title: '规则ID',visible:false
		 },{
			 field: 'groupId', title: '规则组ID',visible:false
		 },{
			 field: 'methodRegexs',title: '拦截方法',formatter : function(value,row,index){
				 if(value == null){value = '';}
				 return '<div class="tbcontent">'+value+'</div>';
			 }
		 },{
			 field: 'prepositionSql',title: '前置SQL',formatter : function(value,row,index){
				 if(value == null){value = '';}
				 return '<div class="tbcontent">'+value+'</div>';
			 }
		 },{
			 field: 'postpositionSql',title: '后置SQL',formatter : function(value,row,index){
				 if(value == null){value = '';}
				 return '<div class="tbcontent">'+value+'</div>';
			 }
		 },{
			 field: 'replaceRegexs',title: '替换规则',formatter : function(value,row,index){
				 if(value == null){value = '';}
				 return '<div class="tbcontent">'+value+'</div>';
			 }
		 },{
			 field: 'replaceSqls',title: '替换SQL',formatter : function(value,row,index){
				 if(value == null){value = '';}
				 return '<div class="tbcontent">'+value+'</div>';
			 }
		 },{
			 field: 'ruleEnable',title: '启用状态',formatter : function(value,row,index){
				 if(value == 'true'){
					 return '<span class="label label-success">启用</span>'
				 }else if(value == 'false'){
					 return '<span class="label label-danger">停用</span>'
				 }
				 return value;
			 }
		 },{
	    	title : '操作',formatter : function(value,row,index){
	    		var html = [];
    			if(editBtn){
    				if(row['ruleEnable'] == 'true'){
    					html.push('<a href="javascript:void(0)" class="zf-btn btn-primary btn-c-disable" data-id="'+row['ruleId']+'" style="padding:3px;color:#FFF;">停用</a>');
    				}else if(row['ruleEnable'] == 'false'){
    					html.push('<a href="javascript:void(0)" class="zf-btn btn-primary btn-c-enable" data-id="'+row['ruleId']+'" style="padding:3px;color:#FFF;">启用</a>');
    				}
    				html.push('<a href="javascript:void(0)" class="zf-btn btn-primary btn-c-edit" data-id="'+row['ruleId']+'" style="padding:3px;color:#FFF;">编辑</a>');
        		}
    			if(deleteBtn){
    				html.push('<a href="javascript:void(0)" class="zf-btn btn-primary btn-c-del" data-id="'+row['ruleId']+'" style="padding:3px;color:#FFF;">删除</a>');
    			}
        	return html.join(' ');
	    	}
	    }],
	    queryParams:function(obj){
	    	return {
	    		limit : obj.limit,offset : obj.offset,
	    		groupId : groupId,
	    		methodRegexs : $('#c_text_search').val()
	    	};
	    }
	});
	
	/**增加按钮事件*/
	$('#btn_c_add').on('click',function(){
		if(groupId == ''){$.alert('请在权限定义中点击“设计”按钮');return;}
		$.showDialog(_path + '/dataauth/data/rule/form.zf?groupId='+groupId,'新增规则', $.extend({},modifyConfig,{
	    	customScrollbar : false,gridName : 'ruleGrid'
		}));
	});
	
	$(document).off('click','a.btn-c-edit').on('click','a.btn-c-edit',function(event){
		var ruleId = $(this).data('id');
		$.showDialog(_path + '/dataauth/data/rule/form.zf?groupId='+groupId+'&ruleId=' + ruleId,'修改规则', $.extend({},modifyConfig,{
	    	customScrollbar : false,gridName : 'ruleGrid'
		}));
	}).off('click','a.btn-c-del').on('click','a.btn-c-del',function(event){
		var ruleId = $(this).data('id');
		$.confirm('您确定要删除当前规则么，是否继续?',function(res){
			if(res){
				$.post(_path + '/dataauth/data/rule/delete.zf?ruleId=' + ruleId ,function(data){
		    		if(data){
		    			if(data["status"] == "success"){
							$.success(data["message"],function(){
								$('#ruleGrid').refreshGrid();
							});
						}else if(data["status"] == "error"){
							$.error(data["message"]);
						}else{
							$.alert(data["message"]);
						}
		    		}
		    	});
			}
		});
	}).off('click','a.btn-c-disable').on('click','a.btn-c-disable',function(event){
		var ruleId = $(this).data('id');
		$.confirm('重新设置相关权限后生效，是否继续?',function(res){
			if(res){
				$.post(_path + '/dataauth/data/rule/disable.zf?ruleId=' + ruleId ,function(data){
		    		if(data){
		    			if(data["status"] == "success"){
							$.success(data["message"],function(){
								$('#ruleGrid').refreshGrid();
							});
						}else if(data["status"] == "error"){
							$.error(data["message"]);
						}else{
							$.alert(data["message"]);
						}
		    		}
		    	});
			}
		});
	}).off('click','a.btn-c-enable').on('click','a.btn-c-enable',function(event){
		var ruleId = $(this).data('id');
		$.confirm('重新设置相关权限后生效，是否继续?',function(res){
			if(res){
				$.post(_path + '/dataauth/data/rule/enable.zf?ruleId=' + ruleId ,function(data){
		    		if(data){
		    			if(data["status"] == "success"){
							$.success(data["message"],function(){
								$('#ruleGrid').refreshGrid();
							});
						}else if(data["status"] == "error"){
							$.error(data["message"]);
						}else{
							$.alert(data["message"]);
						}
		    		}
		    	});
			}
		});
	});
	
	/**查询按钮事件*/
	$('#c_btn_search').on('click',function(){
		$('#ruleGrid').refreshGrid();
	});
	
	/**查询框回车事件*/
	$('#c_text_search').on('keypress',function(e){
		if(e.keyCode == 13){
			$('#ruleGrid').refreshGrid();
		}
	});
	
});