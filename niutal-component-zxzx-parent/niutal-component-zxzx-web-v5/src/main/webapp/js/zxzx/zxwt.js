$(function(){
	var options = {
			 url:_path+'/zxzx/zxwt/cxZxwtList.zf',
			 uniqueId: "zxid",
			 toolbar:  '#but_ancd',
			 columns: [
	            {checkbox: true }, 
	            {field: 'zxid', title: 'zxid',visible:false, hidden: true}, 
//	            {field: 'kzdt', title: '咨询主题',sortable:true}, 
	            {field: 'zxnr', title: '咨询内容',formatter:function(value,row,index){
	                var text = value;
	                var length = text.length;
	                if(length > 40){
	                	text = '<a title="' + value + '" href="javascript:void(0);" >' + text.substr(0,40) + '...' + '</a>';
	                }
	                return text;
	            }}, 
	            {field: 'bkmc',title: '咨询版块',sortable:true,
	            	formatter:function(value,row,index){
		            	return row['kzdkModel']['bkmc'];
		            }
	            },
	            {field: 'xm',title: '咨询人',sortable:true,formatter:function(value,row,index){
	            	return row['zxrModel']['xm'];
	            }}, 
	            {field: 'zxsj',title: '咨询时间',sortable:false}, 
	            {field: 'hfzt',title: '回复状态',sortable:true,formatter:function(value,row,index){
	            	return (value=='1')?"<span class='text-success'>已回复</span>":"<span class='text-danger'>未回复</sapn>";
	            }},
	            {field: 'hfsj',title: '回复时间',sortable:true,formatter:function(value,row,index){
	            	return row['zxhfModel']['hfsj'];
	            }},
	            {field: 'cjwt',title: '常见问题',sortable:true,formatter:function(value,row,index){
	            	return (value=='1')?"<span class='text-success'>是</span>":"<span class='text-danger'>否</sapn>";
	            }}
           ],
           searchParams:function(){
        	var map = $.search.getSearchMap();
           	return map;
           }
		};
		$('#tabGrid').loadGrid(options);
		
		
		(function(){
			
					$(":button[name=search_button]").bind("click",function(){
						$('#tabGrid').refreshGrid();
					});
					
					var btnhf=jQuery("#btn_hf");
					var btnsc=jQuery("#btn_sc");
					var btnszcjwt=jQuery("#btn_szcjwt");
					var btnck=jQuery("#btn_ck");
					var btndc=jQuery("#btn_dc");
					var btndr=jQuery("#btn_dr");
					
					if(btnhf!=null){
						btnhf.click(function () {
							var ids = $("#tabGrid").getKeys();
							if (ids.length != 1) {
								$.alert('请选定一条记录!');
								return;
							}
							$.showDialog('hfZxwt.zf?zxid='+ids[0],'咨询回复', modifyConfig);
						});
					}
					
					if(btnsc!=null){
						btnsc.click(function () {
							var ids = $("#tabGrid").getKeys();
							if (ids.length == 0){
								$.alert('请选择您要删除的记录！');
							} else {
								$.confirm('您确定要删除选择的记录吗？',function(result) {
									if(result){
										//提示用户需要级联删除常见信息表中的数据
										var needDelteCascade = false;
										var ajaxParam = {zxid:ids.toString(),delCjwt:'0'};
										for (var i = 0; i < ids.length; i++) {
											var selRowData = jQuery("#tabGrid").getRow(ids[i]);
											if(selRowData["cjwt"] == '1'){
												needDelteCascade = true;
												break;
											}
										}
										
										if(needDelteCascade){
											$.confirm('是否需要同时删除常见问题表中的数据？', function(result){
												if(result){
													ajaxParam["delCjwt"] = "1";
												}
											});
										}else{
											ajaxParam["delCjwt"] = "0";
										}
										
										jQuery.post('scBcZxwt.zf', ajaxParam, function(responseText) {
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
					
					if(btnszcjwt != null){
						btnszcjwt.click(function(){
							var ids = $("#tabGrid").getKeys();
							if (ids.length != 1) {
								$.alert('请选定一条记录!');
								return;
							}
							
							var rowdata = jQuery('#tabGrid').getRow(ids[0]);
							if(rowdata['hfzt'] != '1'){
								$.alert("该问题暂未回复,不能设置为常见问题!");
								return false;
							}
							if(rowdata['cjwt'] == '1'){
								$.alert("该问题已设置为常见问题!");
								return false;
							}
							
							$.confirm('确认要设置为常见问题?',function(result) {
								if(result){
									jQuery.post('zsBcCjwt.zf', {
										"zxid" : ids[0]
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
							
						});
					}
					
					if(btnck!=null){
						btnck.click(function () {
							var ids = $("#tabGrid").getKeys();
							if (ids.length != 1) {
								$.alert('请选定一条记录!');
								return;
							}
							$.showDialog('ckZxwt.zf?zxid='+ids[0],'查看咨询回复', $.extend({}, viewConfig, {width:'770px'}));
							//showWindow('咨询回复',770,600,url);
							return false;
						});
					}


					btndc.click(function(){
						var url = _path+'/zxzx/zxwt_export.html';
						$.customExport("zxzx_zxwt",_path+'/zxzx/zxwt/export.zf',function(){
							//setSearchTag();
							//jQuery("#form1").attr("action",url);
							//jQuery("#form1").submit();
						},{});
					});
					
					btndr.click(function(){
						$.showDialog('batchZxwt.zf','批量回复', $.extend({}, viewConfig, {width:'600px'}));
						return false;
					});

				}
		)();
		
});

