[#assign q=JspTaglibs["/niutal-search-tags"] /]
<!doctype html>
<html>
	<head>
		
	</head>

	<body>
			<div class="row sl_add_btn" id="but_ancd">
			    <div class="col-sm-12">
					<div class="btn-toolbar" role="toolbar" style="float:left;">
						<div class="btn-group" >

								<button type="button"  href="javascript:void(0);" class="btn btn-info" id="btn_add_yh">
									<i class="bigger-100 glyphicon glyphicon-plus"></i>
									添加用户
								</button>
								
								<button type="button"  href="javascript:void(0);" class="btn btn-danger" id="btn_del_yh">
									<i class="bigger-100 glyphicon glyphicon-remove"></i>
									删除用户
								</button>

						</div>
					</div>
			    </div>
			</div>
			
			<!--查询条件  开始 -->
			[@q.panel theme="default" id="fpBkyh_search"] 
			     [@q.input list="#(zgh:用户名,xm:姓名)"/] 
			     [@q.multi name="jgdm" text="所属机构" list="jgdmsList" listKey="bmdm_id" listValue="bmmc" hasBlank=false/]
			[/@q.panel]
			<!--查询条件  结束  -->
			<!-- table-start  -->
			<table id="fpBkyhTabGrid"></table>
			<!-- table-end  -->
			
			<script type="text/javascript">
				$(function(){
				var options = {
						url:_path+'/zxzx/yhbk/cxYfpYhxxList.zf?bkdm=${model.bkdm}',
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
				         	var map = $('#fpBkyh_search').getSearchMap();
				         	return map;
				         	}	
						};
				
				$('#fpBkyhTabGrid').loadGrid(options);
			
				$("#fpyhModel :button[name=search_button]").bind("click",function(){
						$('#fpBkyhTabGrid').refreshGrid();
				});
				
				$('#btn_add_yh').click(function(){
					$.showDialog('zjBkyh.zf?bkdm=${model.bkdm}', '添加用户', $.extend({},modifyConfig,
						{
						modalName	: "zjfpyhModel",
						width: "1100px",
						buttons		: {
							success : {
								label : "保 存",
								className : "btn-primary",
								callback : function() {
									var $this = this;
									var opts = $this["options"]||{};
									var ids = $("#zjBkyhTabGrid").getKeys();
									if (ids.length == 0){
										$.alert('请选择用户!！');
									} else {
										$.confirm('您确定添加选中用户？',function(result) {
											if(result){
												jQuery.post('zjBcBkyh.zf', {
													"zgh" : ids.join(","),
													"bkdm" : '${model.bkdm}'
												}, function(responseText) {
													if(responseText["status"] == "success"){
														$.success(responseText["message"],function() {
															$('#fpBkyhTabGrid').refreshGrid();
															$('#zjBkyhTabGrid').refreshGrid();
															$('#tabGrid').refreshGrid();
														});
													}else if(responseText["status"] == "fail"){
														$.error(responseText["message"]);
													} else{
														$.alert(responseText["message"]);
													}
													
													$.closeModal(opts["modalName"]||"modifyModal");
													
												}, 'json');
											}
										});
										
									}
									
									return false;
								}
							},
							cancel : {
								label : "关 闭",
								className : "btn-default"
							}
						}
					}));
				});

				$('#btn_del_yh').click(function(){
				
					var ids = $("#fpBkyhTabGrid").getKeys();
					if (ids.length == 0){
						$.alert('请选择您要删除的记录！');
					} else {
						$.confirm('您确定要删除选中用户?',function(result) {
							if(result){
								jQuery.post('scBcBkyh.zf', {
									"zgh" : ids.join(","),
									"bkdm" : '${model.bkdm}'
								}, function(responseText) {
									if(responseText["status"] == "success"){
										$.success(responseText["message"],function() {
											$("#fpBkyhTabGrid").reloadGrid();
											$('#tabGrid').refreshGrid();
										});
									}else if(responseText["status"] == "fail"){
										$.error(responseText["message"]);
									} else{
										$.alert(responseText["message"]);
									}
								}, 'json');
							}
						});
					}
				});

			});
			</script>
	</body>
</html>
