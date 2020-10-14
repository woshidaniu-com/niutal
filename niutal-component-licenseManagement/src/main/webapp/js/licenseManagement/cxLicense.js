var LicenseGrid = Class.create(BaseJqGrid,{  
		caption : "授权信息列表",
		pager: "pager", 
        url:_path+'/license-management/authdata_cxLicense.html?doType=query&testId=123',
        colModel:[
        	 {label:'授权代码',name:'authId', index: 'authId',key:true},
        	 {label:'机构代码',name:'authUserCode', index: 'authUserCode',align:'center'},
        	 {label:'机构名称',name:'authUser', index: 'authUser',align:'center'},
        	 {label:'业务系统',name:'productName', index: 'productName',align:'center'},
        	 {label:'授权日期',name:'authDate', index: 'authDate',align:'center'},
        	 {label:'开始日期',name:'startDate', index: 'startDate',align:'center'},
        	 {label:'结束日期',name:'expireDate', index: 'expireDate',align:'center'},
        	 {label:'可用(天)',name:'usage', index: 'usage',align:'center'},
        	 {label:'已用(天)',name:'usageCount', index: 'usageCount',align:'center'},
        	 {label:'到期提醒(天)',name:'alert', index: 'alert',align:'center'},
        	 {label:'开发模式',name:'devMode', index: 'devMode',align:'center',editable:false,
        		 edittype:'select', 
        		 formatter:'select', 
        		 editoptions:{value:"1:是;0:否"}}
		],
		sortname: 'AUTH_DATE',
     	sortorder: "desc"
	});

//查询
function searchResult(){
	var map = {};
	map["authUserCode"] = jQuery('#authUserCode').val();
	map["authUser"] = jQuery('#authUser').val();
	search('tabGrid',map);
}

/*
 * 绑定操作按钮
 */
function bdan(){
	var btnzj=jQuery("#btn_zj");
	var btnsc=jQuery("#btn_sc");
	var btnxg=jQuery("#btn_xg");
	var btnck=jQuery("#btn_ck");
	var btndl=jQuery("#btn_license-dl");
	//绑定增加点击事件
	if(btnzj!=null){
		btnzj.click(function () {
			showWindow('增加授权信息',700,300,_path+'/license-management/authdata_zjLicense.html');
		});
	}
	
	//绑定删除事件
	if(btnsc!=null){
		btnsc.click(function () {
			var url =_path+'/license-management/authdata_scBcLicense.html';
			var ids = getChecked();
			if (ids.length != 1){
				alert('请选择一条要删除的记录！');
			} else {
				var _do = function(){
					jQuery.ajaxSetup({async:false});
					jQuery.post(url,{authId:ids.toString()},function(data){
						setTimeout(function(){
							refershGrid('tabGrid')
							alertInfo(data);
						},1);
						
					},'json');
					jQuery.ajaxSetup({async:true});
				}
				showConfirmDivLayer('您确定要删除选择的记录吗？',{'okFun':_do})
			}
		});
	}

	//绑定修改事件
	if(btnxg!=null){
		btnxg.click(function () {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		var url=_path + "/license-management/authdata_xgLicense.html?authId="+id; 
		showWindow('修改授权信息',700,300,url);
		});
	}
	
	//绑定修改事件
	if(btnck!=null){
		btnck.click(function () {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		var url=_path + "/license-management/authdata_ckLicense.html?authId="+id; 
		showWindow('查看授权信息',700,300,url);
		});
	}
	
	
	//绑定授权文件下载事件
	if(btndl!=null){
		btndl.click(function () {
			var id = getChecked();
			if(id.length != 1){
				alert('请选定一条记录!');
				return;
			}
			
			var jqFrom = jQuery("#license-management-form");
			var url = _path + "/license-management/authdata_dlLicense.html?authId="+id; 
			jqFrom.attr("action", url);
			jqFrom.submit();
		});
	}
}