$(function(){
	var options = {
			 url:_path+'/zxzx/cjwt/cxCjwtList.zf',
			 uniqueId: "wtid",
			 toolbar:  '#but_ancd',
			 columns: [
	            {checkbox: true }, 
	            {field: 'wtid', title: 'wtid',visible:false}, 
	            {field: 'wtnr', title: '咨询内容',sortable:false,formatter:function(value,row,index){
	                var text = value;
	                var length = text.length;
	                if(length > 40){
	                	text = '<a title="' + value + '" href="javascript:void(0);" >' + text.substr(0,40) + '...' + '</a>';
	                }
	                return text;
	            }}, 
	            {field: 'bkmc',title: '咨询版块',sortable:true}, 
	            {field: 'sffb',title: '启用状态',sortable:true,formatter:function(value,row,index){
	            	return (value=='1')?"<span class='text-success'>启用</span>":"<span class='text-danger'>关闭</sapn>";
	            }},
	            {field: 'cjsj',title: '咨询时间',sortable:true}, 
	            {field: 'zxid',title: '数据来源',sortable:true,formatter:function(value,row,index){
	            	if(value != null || value != undefined || $.trim(value) != ""){
	            		return "咨询"
	            	}else{
	            		return "";
	            	}
	            }}
           ],
           searchParams:function(){
           	var map = {};
           	map["bkdm"] = jQuery('#bkdm').val();
           	map["sffb"]   = jQuery('#sffb').val();
//           	map["wtbt"] = jQuery('#wtbt').val();
           	return map;
           }
		};
		$('#tabGrid').loadGrid(options);
		/*
		 * 绑定操作按钮
		 */
		(function(){
			
			$('#search_go').click(function(){
				$('#tabGrid').refreshGrid();
			});
			
			var btnzj=jQuery("#btn_zj");
			var btnsc=jQuery("#btn_sc");
			var btnxg=jQuery("#btn_xg");
			var btnqyty=jQuery("#btn_qyty");
			//绑定增加点击事件
			if(btnzj!=null){
				btnzj.click(function () {
					//showWindow('增加常见问题',800,440,_path+'/zxzx/cjwt_zjCjwt.html');
					$.showDialog('zjCjwt.zf','增加常见问题', addConfig);
				});
			}
			
			//绑定删除事件
			if(btnsc!=null){
				btnsc.click(function () {
					var url =_path+'/zxzx/cjwt/scBcCjwt.zf';
					
					var ids = $("#tabGrid").getKeys();
					if (ids.length == 0){
						$.alert('请选择您要删除的记录！');
					} else {
						$.confirm('您确定要删除选择的记录吗？',function(result) {
							if(result){
								jQuery.post('scBcCjwt.zf', {
									"wtid" : ids.join(",")
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
				$.showDialog('xgCjwt.zf?wtid='+ids[0],'修改常见问题', modifyConfig);
				});
			}
			
			if(btnqyty!=null){
				btnqyty.click(function () {
					var ids = $("#tabGrid").getKeys();
					if (ids.length != 1) {
						$.alert('请选定一条记录!');
						return;
					}
					$.showDialog('qytyCjwt.zf?wtid='+ids[0], '常见问题【启用/停用】', $.extend({}, modifyConfig, {width:'400px'}));
				});
			}
		})();
		
});

