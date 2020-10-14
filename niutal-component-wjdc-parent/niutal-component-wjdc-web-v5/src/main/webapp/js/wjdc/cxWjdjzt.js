$(function(){
	var ops = {
		 url: 'getDjztList.zf',//请求后台的URL
		 uniqueId: "ZJZ",     			 //每一行的唯一标识，一般为主键列
		 toolbar:  '#but_ancd',
		 showToggle:false,
		 showRefresh:false,
		 showColumns:false,
		 columns: [
			{checkbox: true }, 
			{field: 'WJID', title: '问卷id',sortable:false},
			{field: 'WJMC', title: '问卷名称',sortable:false},
            {field: 'XH', title: '学号',sortable:false},
			{field: 'XM', title: '姓名',sortable:false},
			{field: 'ZYMC', title: '专业名称',sortable:false},
			{field: 'BMMC', title: '部门名称',sortable:false},
			{field: 'NJMC', title: '年级名称',sortable:false},
            {field: 'BJMC', title: '班级名称',sortable:false},
			{field: 'XBMC', title: '性别',sortable:false},
            {field: 'DJZT', title: '答卷状态',sortable:false},
			{field: 'WJFZ', title: '问卷得分',sortable:false}
         ],
         searchParams:function(){
        	 var map = $("#defaultId2").getSearchMap();
        	 return map;
         }
	};
	$('#tabGrid_djzt').loadGrid(ops);
	
	$(":button[name=search_button]").bind("click",searchResult);
	
	function searchResult(){
		$('#tabGrid_djzt').bootstrapTable('refresh' );
	}
	
	//查询答卷状态
	jQuery("#btn_dc").click(function(){
		jQuery("#export").submit();
	});
});