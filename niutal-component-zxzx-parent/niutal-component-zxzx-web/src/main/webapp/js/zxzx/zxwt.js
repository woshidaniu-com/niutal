var ZxwtGrid = Class.create(BaseJqGrid,{  
		caption : "咨询问题列表",
		pager: "pager", 
        url:_path+'/zxzx/zxwt_cxZxwt.html?doType=query',
        colModel:[
        	 {label:'主键',name:'zxid', index: 'zxid',key:true,hidden:true},
        	 {label:'咨询主题',name:'kzdt', index: 'wtbt',align:'left'},
        	 {label:'咨询版块',name:'kzdkModel.bkmc', index: 'bkmc',align:'center'},
        	 {label:'咨询人',name:'zxrModel.xm', index: 'zxrxm',align:'center'},
        	 {label:'咨询时间',name:'zxsj', index: 'zxsj',align:'center'},
		     {
        		 label:'回复状态',
        		 name:'hfzt', 
        		 width:100, 
        		 index: 'hfzt',
        		 align:'center',
        		 formatter:'select', 
        		 editoptions:{value:"1:已回复;0:未回复"}
        	 },
		     {
		    	 label:'回复时间',
		    	 name:'zxhfModel.hfsj', 
		    	 index: 'hfsj',
		    	 align:'center'
		     },
		     {
        		 label:'常见问题',
        		 name:'cjwt', 
        		 width:100, 
        		 index: 'cjwt',
        		 align:'center',
        		 formatter:'select', 
        		 editoptions:{value:"1:是;0:否"}
        	 }
		],
		sortname: 'hfzt',
     	sortorder: "asc"
	});

function searchResult(){
	var map = getSearchMap(true);
	search('tabGrid',map);
}

function bdan(){
	var btnhf=jQuery("#btn_hf");
	var btnsc=jQuery("#btn_sc");
	var btnszcjwt=jQuery("#btn_szcjwt");
	var btnck=jQuery("#btn_ck");
	var btndc=jQuery("#btn_dc");
	
	if(btnhf!=null){
		btnhf.click(function () {
			var id=getChecked();
			if(id.length!=1){
				alert("请先选定一条需要回复的咨询!");
				return ;
			}
			var url = _path+'/zxzx/zxwt_hfZxwt.html?zxid='+id;
			showWindow('咨询回复',770,550,url);
		});
	}
	
	if(btnsc!=null){
		btnsc.click(function () {
			var url =_path+'/zxzx/zxwt_scBcZxwt.html';
			var ids = getChecked();
			if (ids.length == 0){
				alert('请选择您要删除的记录！');
			} else {
				var ajaxParam = {zxid:ids.toString(),delCjwt:'0'};
				var _do = function(){
					jQuery.ajaxSetup({async:false});
					jQuery.post(url,ajaxParam,function(data){
						setTimeout(function(){
							refershGrid('tabGrid')
							alertInfo(data);
						},1);
						
					},'json');
					jQuery.ajaxSetup({async:true});
				}
				//提示用户需要级联删除常见信息表中的数据
				var needDelteCascade = false;
				for (var i = 0; i < ids.length; i++) {
					var selRowData = jQuery("#tabGrid").jqGrid('getRowData',ids[i]);
					if(selRowData["cjwt"] == '1'){
						needDelteCascade = true;
						break;
					}
				}
				
				if(needDelteCascade){
					ajaxParam["delCjwt"] = "1";
					alertConfirm("是否需要同时删除常见问题表中的数据？",function(){
						_do();
					});
				}else{
					showConfirmDivLayer('您确定要删除选择的记录吗？',{'okFun':_do})
				}
			}
		});
	}
	
	if(btnszcjwt != null){
		btnszcjwt.click(function(){
			var id=getChecked();
			if(id.length!=1){
				alert("请先选定一条记录!");
				return ;
			}
			var rowdata = jQuery('#tabGrid').jqGrid('getRowData',id[0]);
			if(rowdata['hfzt'] != '1'){
				alertInfo("该问题暂未回复,不能设置为常见问题!");
				return false;
			}
			if(rowdata['cjwt'] == '1'){
				alertInfo("该问题已设置为常见问题!");
				return false;
			}
			var url = _path+'/zxzx/zxwt_zsBcCjwt.html';
			alertConfirm("确认要设置为常见问题?",function(){
				jQuery.post(url,{'zxid':id[0]},function(data){
					refershGrid('tabGrid')
					alertInfo(data);
				});
			});
		});
	}
	
	if(btnck!=null){
		btnck.click(function () {
			var id=getChecked();
			if(id.length!=1){
				alert("请先选定一条记录!");
				return ;
			}
			var url = _path+'/zxzx/zxwt_ckZxwt.html?zxid='+id;
			showWindow('咨询回复',770,600,url);
			return false;
		});
	}

	if(btndc != null){
		btndc.click(function(){
			var url = _path+'/zxzx/zxwt_export.html';
			customExport(700,450,_path+'/zxzx/zxwt_customExport.html',function(){
				setSearchTag();
				jQuery("#form1").attr("action",url);
				jQuery("#form1").submit();
			});
		});
	}
}
