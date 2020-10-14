//查询
function searchResult(){
	var map = {};
	map["zt"] = jQuery('#zt').val();
	map["fskssj"] = jQuery('#fskssj').val();
	map["fsjssj"] = jQuery('#fsjssj').val();
	map["fsrzgh"] = jQuery('#fsrzgh').val();
	map["jsrydbj"] = jQuery('#jsrydbj').val();
	
	map["jsrmc"] = jQuery('#jsrmc').val();
	map["sfzd"] = jQuery('#sfzd').val();

	search('tabGrid',map);
}
//查看收件箱
function onView_sjx(){
	var id = getChecked();

	if(id.length != 1){
		alert('请选择一条您要查看的记录!');
		return;
	}
	//设置jqgrid超链接样式
	setTabGridLinkStyle("tabGrid");
	
	var row = jQuery("#tabGrid").jqGrid('getRowData', id);
	var url= _path+"/zfxg!plugins/znxgl_ckSjxZnx.html"; 
	url+="?";
	
	url+="fsbh="+row.fsbh;
	showWindow('',850,650,url);
}

//查看发件箱
function onView_fjx(){
	var id = getChecked();

	if(id.length != 1){
		alert('请选择一条您要查看的记录!');
		return;
	}
	var row = jQuery("#tabGrid").jqGrid('getRowData', id);
	var url= _path+"/zfxg!plugins/znxgl_ckFjxZnx.html"; 
	url+="?";
	
	url+="fsbh="+row.fsbh;
	showWindow('',850,650,url);
}

/*
 * 绑定操作按钮_收件箱
 */
function bdan_sjx(){
	var btn_xx=jQuery("#btn_xx");//写信
	var btn_hf=jQuery("#btn_hf");//回复
	var btn_ck=jQuery("#btn_ck");//查看
	var btn_bjyd=jQuery("#btn_bjyd");//标记已读
	var btn_sc=jQuery("#btn_sc");//删除

	if(btn_xx!=null){
		btn_xx.click(function () {
			//showWindow('',650,360,_path+'/zfxg!plugins/znxgl_zjZnx.html');
			refRightContent(_path+'/zfxg!plugins/znxgl_zjZnx.html');
			return false;
		});
	}
	
	if(btn_ck!=null){
		btn_ck.click(function () {
			onView_sjx();
		});
	}
	
	if(btn_hf!=null){
		btn_hf.click(function () {
			var id = getChecked();

			if(id.length != 1){
				alert('请选择一条您要查看的记录!');
				return;
			}
			//设置jqgrid超链接样式
			setTabGridLinkStyle("tabGrid");
			
			var row = jQuery("#tabGrid").jqGrid('getRowData', id);
			var url= _path+"/zfxg!plugins/znxgl_hfZnx.html"; 
			url+="?";
			
			url+="fsbh="+row.fsbh;
			showWindow('',850,650,url);
		});
	}

	if(btn_bjyd!=null){
		btn_bjyd.click(function () {
			plcz(_path+'/zfxg!plugins/znxgl_bjydZnx.html','标记已读');
		});
	}
	
	if(btn_sc!=null){
		btn_sc.click(function () {
			plcz(_path+'/zfxg!plugins/znxgl_scJsrZnx.html','删除');
		});
	}
	
}

/*
 * 绑定操作按钮_收件箱
 */
function bdan_dwSjx(){
	var btn_hf=jQuery("#btn_hf");//回复
	var btn_sc=jQuery("#btn_sc");//删除
	
	if(btn_hf!=null){
		btn_hf.click(function () {
			var id=jQuery("#tabGrid").getDataGrid().getChecked();

			if(id.length != 1){
				alert('请选择一条您要查看的记录!');
				return;
			}
			//设置jqgrid超链接样式
			setTabGridLinkStyle("tabGrid");
			
			var row = jQuery("#tabGrid").getDataGrid().getRowData(id);
			var url= _path+"/zfxg!plugins/znxgl_hfZnx.html"; 
			
			url+="?";
			url+="fsbh="+row.fsbh;
			showWindow('',650,450,url);
		});
	}

	if(btn_sc!=null){
		btn_sc.click(function () {
			plcz(_path+'/zfxg!plugins/znxgl_scJsrZnx.html','删除');
		});
	}
	
}

/*
 * 绑定操作按钮
 */
function bdan(){
	var btn_xx=jQuery("#btn_xx");//写信
	var btn_ck=jQuery("#btn_ck");//查看_发件箱
	var btn_zd=jQuery("#btn_zd");//置顶
	var btn_qxzd=jQuery("#btn_qxzd");//取消置顶
	var btn_sc=jQuery("#btn_sc");//删除
	//绑定写信点击事件
	if(btn_xx!=null){
		btn_xx.click(function () {
			//showWindow('',650,360,_path+'/zfxg!plugins/znxgl_zjZnx.html');
			refRightContent(_path+'/zfxg!plugins/znxgl_zjZnx.html');
			return false;
		});
	}

	if(btn_ck!=null){
		btn_ck.click(function () {
			onView_fjx();
		});
	}

	if(btn_zd!=null){
		btn_zd.click(function () {
			plcz(_path+'/zfxg!plugins/znxgl_zdZnx.html','置顶');
		});
	}
	
	if(btn_qxzd!=null){
		btn_qxzd.click(function () {
			plcz(_path+'/zfxg!plugins/znxgl_qxzdZnx.html','取消置顶');
		});
	}
	
	if(btn_sc!=null){
		btn_sc.click(function () {
			plcz(_path+'/zfxg!plugins/znxgl_scFsrZnx.html','删除');
		});
	}
}

//设置超链接样式
function setTabGridLinkStyle(id){
	var cbox = jQuery("#"+id+" input[type=checkbox]");
	var aLink = jQuery("#"+id+" a[class=jsrydbjClass]");
	cbox.each(function(index,obj){
		var jqObj=jQuery(obj);
		if(jqObj.attr("checked") == "checked"){
			aLink.eq(index).html(aLink.eq(index).text());
		}
	});
}