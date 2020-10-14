$(function(){
	
	var options = {
			 url:_path+'/zxzx/kzdk/cxkzdkList.zf',
			 uniqueId: "bkdm",
			 toolbar:  '#but_ancd',
			 columns: [
	            {checkbox: true }, 
	            {field: 'bkdm', title: 'bkdm',visible:false, hidden:true}, 
	            {field: 'bkmc', title: '板块名称',sortable:true}, 
	            {field: 'sfqy',title: '启用状态',sortable:true,formatter:function(value,row,index){
	            	return (value=='1')?"<span class='text-success'>启用</span>":"<span class='text-danger'>停用</sapn>";
	            }},
	            {field: 'xsxs',title: '显示顺序',formatter:function(value,row,index){
	            	return "<span class='text-info'>" + (++index) + "</span>";
	            }}
          ],
          searchParams:function(){
          	var map = {};
          	map["bkmc"] = jQuery('#bkmc').val();
        	map["sfqy"] = jQuery('#sfqy').val();
          	return map;
          }
		};
		$('#tabGrid').loadGrid(options);
		
		/*
		 * 绑定操作按钮
		 */
		(function(){
			
			//回车键查询
			$('#bkmc').bind("keydown", "return", function (ev) {
				$('#tabGrid').refreshGrid();
			});
			
			$('#search_go').click(function(){
				$('#tabGrid').refreshGrid();
			});
			
			var btnzj=jQuery("#btn_zj");
			var btnsc=jQuery("#btn_sc");
			var btnxg=jQuery("#btn_xg");
			var btntz=jQuery("#btn_tz");
			var btnqyty=jQuery("#btn_qyty");
			//绑定增加点击事件
			if(btnzj!=null){
				btnzj.click(function () {
					$.showDialog('zjkzdk.zf','增加咨询板块', $.extend({},addConfig,{width: "500px"}));
				});
			}
			
			//绑定删除事件
			if(btnsc!=null){
				btnsc.click(function () {
					var ids = $("#tabGrid").getKeys();
					if (ids.length != 1){
						$.alert('请选择一条要删除的记录！');
					} else {
						$.confirm('您确定要删除选择的记录吗？',function(result) {
							if(result){
								jQuery.post('scBckzdk.zf', {
									"bkdm" : ids.join(",")
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
					}
				});
			}

			//绑定修改事件
			if(btnxg!=null){
				btnxg.click(function () {
					var ids = $("#tabGrid").getKeys();
					if (ids.length != 1) {
						$.alert('请选定一条记录!');
						return;
					}
					$.showDialog('xgkzdk.zf?bkdm='+ids[0],'修改咨询板块', $.extend({},modifyConfig,{width: "500px"}));
				});
			}
			
			//绑定启用停用事件
			if(btnqyty!=null){
				btnqyty.click(function () {
					var ids = $("#tabGrid").getKeys();
					if (ids.length != 1) {
						$.alert('请选定一条记录!');
						return;
					}
					$.showDialog('qytykzdk.zf?bkdm='+ids[0], '常见问题【启用/停用】', $.extend({},modifyConfig,{width: "400px"}));
				});
			}
			
			if(btntz!=null){
				btntz.click(function () {
					
					var dialogConfig = $.extend({},modifyConfig,{
						width: "600px",
						buttons		: {
							success : {
								label : "保 存",
								className : "btn-primary",
								callback : function() {
									var $this = this;
									var postData = {};
									$("#ajaxForm_szsxkzdk #selectUl li").each(function(i){
										var dm = $(this).attr("data-bkdm");
										var index = i+1;
										postData['postData[\''+dm+'\']'] = index;
									});
									$.post("sxszBckzdk.zf",postData,function(responseText){
										if(responseText["status"] == "success"){
											$.success(responseText["message"],function() {
												$('#tabGrid').refreshGrid();
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
						}
					});
					
					$.showDialog('sxszkzdk.zf', '显示顺序设置', dialogConfig);
				});
			}
			
		})();
	
});