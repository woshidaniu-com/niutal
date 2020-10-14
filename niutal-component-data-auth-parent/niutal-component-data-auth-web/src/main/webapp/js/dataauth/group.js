jQuery(function($){
	
	$('#groupType').chosen({
		allow_single_deselect: true,no_results_text: "没有匹配结果",search_contains: false,disable_search:true
	}).change(function(e,s){
		$('#ruleGroupGrid').refreshGrid();
	});
	
	/**权限组列表*/
	$('#ruleGroupGrid').bootstrapTable({
		 url: 'groupList.zf',uniqueId: 'groupId',
		 method : 'post',contentType : 'application/x-www-form-urlencoded',
		 showToggle:false,showRefresh:false,showColumns:false,pageList : [5,10,15,50],
		 sidePagination: 'server',queryParamsType : 'limit',pageSize: 15,pagination: true,
		 classes : 'table table-striped table-hover table-condensed table-api text-center',
		 columns: [{
			 title : '序号',formatter : function(value,row,index){return (index + 1);}
		 },{
			 field: 'groupId', title: '权限组ID',visible:false
		 },{
			 field: 'groupCode',title: '权限代码'
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
			 field: 'selectItem',title: '子选项SQL'
		 },{
	    	title : '操作',formatter : function(value,row,index){
	    		var html = [];
	    		html.push('<a href="javascript:void(0)" class="zf-btn btn-primary btn-design" data-id="'+row['groupId']+'" style="padding:3px;color:#FFF;">设计</a>');
    			if(editBtn){
    				html.push('<a href="javascript:void(0)" class="zf-btn btn-primary btn-edit" data-id="'+row['groupId']+'" style="padding:3px;color:#FFF;">编辑</a>');
        		}
    			if(deleteBtn){
    				html.push('&nbsp;<a href="javascript:void(0)" class="zf-btn btn-primary btn-del" data-id="'+row['groupId']+'" style="padding:3px;color:#FFF;">删除</a>');
    			}
        	return html.join(' ');
	    	}
	    }],
	    queryParams:function(obj){
	    	return {
	    		limit : obj.limit,offset : obj.offset,
	    		groupType : $('#groupType').val(),
	    		groupName : $('#text_search').val()
	    	};
	    }
	});
	
	/**增加按钮事件*/
	$('#btn_add').on('click',function(){
		$.showDialog('form.zf','新增权限', $.extend({},modifyConfig,{
	    	customScrollbar : false,gridName : 'ruleGroupGrid'
		}));
	});
	
	$(document).off('click','a.btn-design').on('click','a.btn-design',function(event){
		groupId = $(this).data('id');
		var row = $('#ruleGroupGrid').getRow(groupId);
		$('#l_groupName').text('权限名称:' + row['groupName']);
		$('#rule_tab').click();
		$('#ruleGrid').refreshGrid();
	}).off('click','a.btn-edit').on('click','a.btn-edit',function(event){
		var id = $(this).data('id');
		$.showDialog('form.zf?groupId=' + id,'修改权限', $.extend({},modifyConfig,{
	    	customScrollbar : false,gridName : 'ruleGroupGrid'
		}));
	}).off('click','a.btn-del').on('click','a.btn-del',function(event){
		var groupId = $(this).data('id');
		$.confirm('您确定要删除当前权限么，此操作会删除所有相关数据，是否继续?',function(res){
			if(res){
				$.post(_path + '/dataauth/data/rule/group/delete.zf?groupId=' + groupId ,function(data){
		    		if(data){
		    			if(data["status"] == "success"){
							$.success(data["message"],function(){
								$('#ruleGroupGrid').refreshGrid();
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
	$('#btn_search').on('click',function(){
		$('#ruleGroupGrid').refreshGrid();
	});
	
	/**查询框回车事件*/
	$('#text_search').on('keypress',function(e){
		if(e.keyCode == 13){
			$('#ruleGroupGrid').refreshGrid();
		}
	});
	
});