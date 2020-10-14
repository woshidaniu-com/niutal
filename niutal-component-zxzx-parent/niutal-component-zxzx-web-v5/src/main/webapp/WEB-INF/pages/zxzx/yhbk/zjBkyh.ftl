[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!doctype html>
<html>
	<head>

	</head>
	<body>
		
		<!--查询条件  开始 -->
		[@q.panel theme="default" id="zjfpBkyh_search"] 
		     [@q.input list="#(zgh:用户名,xm:姓名)"/] 
		     [@q.multi name="jgdm" text="所属机构" list="jgdmsList" listKey="bmdm_id" listValue="bmmc" hasBlank=false/]
		[/@q.panel]

		<!-- table-start  -->
		<table id="zjBkyhTabGrid"></table>
		<!-- table-end  -->

		<script type="text/javascript">
			jQuery(function(){
				var options = {
						url:_path+'/zxzx/yhbk/cxWfpYhxxList.zf?bkdm=${model.bkdm}',
						uniqueId: "zgh",
						showToggle:	false,
						showColumns: false,
				        showRefresh: false,
						columns: [
								{checkbox: true }, 
								{field: 'bkdm', title: 'bkdm', visible:false, hidden:true}, 
								{field: 'zgh', title: '用户名', width:'100px'}, 
					            {field: 'xm', title: '姓名', width:'200px'}, 
					            {field: 'lxdh',title: '联系电话', width:'200px'},
					            {field: 'jgmc',title: '所属部门', width:'200px'}
				        	],
				        searchParams:function(){
				         	var map = $('#zjfpBkyh_search').getSearchMap();
				         	return map;
				         	}	
						};
				
				$('#zjBkyhTabGrid').loadGrid(options);
				
				$("#zjfpyhModel :button[name=search_button]").bind("click",function(){
						$('#zjBkyhTabGrid').refreshGrid();
				});
				
			});
		</script>
	</body>
</html>