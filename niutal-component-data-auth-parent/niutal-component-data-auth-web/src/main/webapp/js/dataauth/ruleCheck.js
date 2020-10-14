jQuery(function($){
	
	/**规则检查列表*/
	$('#ruleCheckGrid').bootstrapTable({
		 url: 'checkList.zf',uniqueId: 'ruleId',
		 method : 'post',contentType : 'application/x-www-form-urlencoded',
		 showToggle:false,showRefresh:false,showColumns:false,
		 classes : 'table table-striped table-hover table-condensed table-api text-center',
		 columns: [{
			 title : '序号',formatter : function(value,row,index){return (index + 1);}
		 },{
			 field: 'ruleId', title: '规则ID',visible:false
		 },{
			 field: 'groupId', title: '权限组ID',visible:false
		 },{
			 field: 'groupCode',title: '权限代码',visible:false
		 },{
			 field: 'groupName',title: '权限名称'
		 },{
			 field: 'groupType',title: '权限类型',formatter : function(value,row,index){
				 if(value == 'user'){
					 return '用户';
				 }else if(value == 'role'){
					 return '角色';
				 }
				 return value;
			 }
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
				 if(value == true){
					 return '<span class="label label-success">启用</span>'
				 }else if(value == false){
					 return '<span class="label label-danger">停用</span>'
				 }
				 return value;
			 }
		 }],
	    queryParams:function(obj){
	    	return {
	    		mapperId : $('#ck_text_search').val()
	    	};
	    }
	});
	
	/**查询按钮事件*/
	$('#ck_btn_search').on('click',function(){
		$('#ruleCheckGrid').refreshGrid();
	});
	
	/**查询框回车事件*/
	$('#ck_text_search').on('keypress',function(e){
		if(e.keyCode == 13){
			$('#ruleCheckGrid').refreshGrid();
		}
	});
	
});