var YhglGrid = Class.create(BaseJqGrid,{  
				caption : "用户列表",
				pager: "pager", //分页工具栏  
				height : "auto",
		        url: _path+'/xtgl/yhgl_cxYhxx.html?doType=query', //这是Action的请求地址  
		        colModel:[
		        	 {label:'用户名',name:'zgh', index: 'zgh',key:true,align:'center'},
				     {label:'姓 名',name:'xm', index: 'xm',align:'center'},
				     {label:'所属机构',name:'jgdm', index: 'jgdm',align:'center'},
			      	 {label:'联系电话',name:'lxdh', index: 'lxdh',align:'center'},
			      	 {label:'邮箱',name:'dzyx', index: 'dzyx',align:'center'},
			      	 {label:'是否启用',name:'sfqy', index: 'sfqy',align:'center'},
			      	 {label:'拥有角色数',name:'jss', index: 'jss',align:'center',formatter:viewYh}
				],
				sortname: 'zgh', //首次加载要进行排序的字段 
	         	sortorder: "desc"
	    	});

var JgdmGrid = Class.create(BaseJqGrid,{  
	caption : "机构代码列表",
	pager: "pager", //分页工具栏  
	height : "auto",
	rowNum : 15, // 每页显示记录数
	rowList : [15, 30, 50, 100], // 可调整每页显示的记录数
    url: _path+'/xtgl/yhgl_cxJgdms.html?doType=query', //这是Action的请求地址  
    colModel:[
    	 {label:'部门Id',name:'bmdm_id', index: 'bmdm_id',key:true,hidden:true},
	     {label:'部门名称',name:'bmmc', index: 'bmmc',align:'center'},
	     {label:'部门名称',name:'cydm_id_bmmc', index: 'cydm_id_bmmc',key:true,hidden:true},
	     {label:'部门类别',name:'cydm_id_bmlb', index: 'cydm_id_bmlb',align:'center',edittype:'select', formatter:'select', editoptions:{value:setBmlbFormat()}}
	],
	sortname: 'bmdm_id', //首次加载要进行排序的字段 
 	sortorder: "desc"
});

function setBmlbFormat(){
	var bmlbForma = "1:教学院系;2:科研机构;3:公共服务;4:党务部门;5:行政机构;6:附属单位;7:后勤部门;8:校办产业;9:其他";
	return bmlbForma;
}

//查询
function searchResult(){
	var map = {};
	map["bmdm_id"] = jQuery('#bmdm_id').val();
	map["zgh"] = jQuery('#zgh').val();
	map["xm"] = jQuery('#xm').val();
	map["sfqy"] = jQuery('#sfqy').val();
	map["jgdm"] = jQuery('#jgdm').val();
	search('tabGrid',map);
}

//机构代码查询
function searchResultJgdms(){
	var map = {};
	map["bmmc"] = jQuery('#bmmc').val();
	search('tabGridJgdms',map);
}

function viewYh(cellvalue, options, rowObject){
	var zgh = rowObject.zgh;
	var html = "<a href='javascript:ckYh(\""+zgh+"\")' >"+cellvalue+"</a>";
	return  html;
}

function ckYh(obj){
	var url="yhgl_ckYhxx.html?zgh="+obj; 
	showWindow('查看用户',650,360,url);
}

/**
 * 数据授权
 * @param type 
 * @return
 */
function sjsq() {	
	var id = getChecked();
	if (id.length != 1) {
		alert('请选定一条记录!');
		return;
	}
	var row = jQuery("#tabGrid").jqGrid('getRowData', id);
	document.location.href='sjfw_cxSjsq.html?yh_id='+id;
}

/**
 * 批量数据授权
 * @param type 
 * @return
 */
function plsjsq() {	
 
}


/*
 * 绑定操作按钮
 */
function bdan(){
	var btnzj=jQuery("#btn_zj");
	var btnsc=jQuery("#btn_sc");
	var btnxg=jQuery("#btn_xg");
	var btnck=jQuery("#btn_ck");
	var btnszssjs=jQuery("#btn_szssjs");
	var btnmmcsh=jQuery("#btn_mmcsh");
	var btnsjsq = jQuery("#btn_sjsq");
	var btnplsjsq = jQuery("#btn_plsjsq");
	var btndr = jQuery("#btn_dr");
	var btnqyty = jQuery("#btn_qyty");
	//绑定增加点击事件
	if(btnzj!=null){
		btnzj.click(function () {
			showWindow('增加用户',750,500,'yhgl_zjYhxx.html');
		});
	}
	
	//绑定删除事件
	if(btnsc!=null){
		btnsc.click(function () {
		plcz('yhgl_scYhxx.html','删除');
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
		var row = jQuery("#tabGrid").jqGrid('getRowData', id);
		var url="yhgl_xgYhxx.html?zgh="; 
		url+=id;
		showWindow('修改用户',750,500,url);
		});
	}
	
	//查看
	if(btnck!=null){
		btnck.click(function () {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', id);
	
		var url="yhgl_ckYhxx.html?zgh="; 
		url+=id;
		showWindow('查看用户',650,360,url);
		});
	}
	
	//设置所属角色
	if(btnszssjs!=null){
		btnszssjs.click(function () {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', id);
		var url="yhgl_szssjsYh.html?zgh="; 
		url+=row.zgh;
		//refRightContent(url);
		showWindow('设置所属角色',830,680,url);

		});
	}
	
	//密码初始化
	if(btnmmcsh!=null){
		btnmmcsh.click(function () {
		var ids = getChecked();
		if(ids.length == 0){
			//alert('请选择要初始化密码的用户!');
			//return;
			//根据用户选择的条件批量初始化密码
			showConfirmDivLayer("确定需要初始化？",{'okFun' : function(){
				var options = {
						url:'yhgl_mmcsh.html?cshlx=all',
						type:'post',
						dataType:'json',
						success:function(message){
									refershGrid('tabGrid')
									alertInfo(message);
								}
		                };
					jQuery('#cxyhForm').ajaxSubmit(options);
				}});
		}else{
			var url="yhgl_mmcsh.html?pkValue="+ids.toString(); 
			plcz(url,'初始化');	
		}
		});
	}
	
	// 数据授权
	if (btnsjsq != null) {
		btnsjsq.click(function() {
			sjsq();
			return false;
		});
	}
	
	// 批量数据授权
	if (btnplsjsq != null) {
		btnplsjsq.click(function() {
			var url = 'yhsjfwgl_cxSjfwszIndex.html';
			refRightContent(url);
		});
	}
	
	//启用/停用
	if(btnqyty!=null){
		btnqyty.click(function () {
		var ids = getChecked();
		if(ids.length == 0){
			alert('请选择要启用或停用的用户!');
			return;
		}
		var url="yhgl_qyty.html?pkValue="+ids.toString(); 
		showWindow('启用/停用',440,120,url);
		});
	}
	
}