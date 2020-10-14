var XsxxGrid = Class.create(BaseJqGrid, {
	caption : "学生列表",
	pager : "pager", // 分页工具栏
	url : _path + '/zfxg/xsxxgl/cxXsxxAjax.html', // 这是Action的请求地址
	postData : {'queryModel.sortNamePro':'xh'},
	colModel : [ {
		label : '学号',
		name : 'xh',
		index : 'xh',
		key : true,
		align : 'center'
	}, {
		label : '姓名',
		name : 'xm',
		index : 'xm',
		align : 'center' 
	}, {
		label : '性别',
		name : 'xbmc',
		index : 'xbmc',
		align : 'center'
	},  {
		label : '年级',
		name : 'njmc',
		index : 'njmc',
		align : 'center'
	}, {
		label : '学院 ',
		name : 'bmmc',
		index : 'bmmc',
		align : 'center'
	}, {
		label : '专业',
		name : 'zymc',
		index : 'zymc',
		align : 'center'
	}, {
		label : '班级',
		name : 'bjmc',
		index : 'bjmc',
		align : 'center'
	} , {
		label : '政治面貌',
		name : 'zzmmmc',
		index : 'zzmmmc',
		align : 'center'
	}, {
		label : '身份证号',
		name : 'sfzh',
		index : 'sfzh',
		align : 'center'
	}, {
		label : '银行卡号',
		name : 'kzzd4',
		index : 'kzzd4',
		align : 'center'
	}, {
		label : '学籍状态',
		name : 'xjztmc',
		index : 'xjztmc',
		align : 'center'
	}],
	sortname : 'njdm_id,bmdm_id,zydm,bjdm,xh' // 首次加载要进行排序的字段
});

jQuery(function(){
	var xsxxGrid = new XsxxGrid();
	loadJqGrid("#tabGrid","#pager",xsxxGrid);
	bdan();
});

function searchResult(){
	var map = getSearchMap(true);
	search('tabGrid',map);
}

function bdan(){
	var btnzj=jQuery("#btn_zj");// 按钮ID 命名规则 btn_xx（xx:操作）
	var btnsc=jQuery("#btn_sc");
	var btnxg=jQuery("#btn_xg");
	var btnck=jQuery("#btn_ck");
	var btnpldrzp=jQuery("#btn_pldrzp");
	// 绑定增加点击事件
	if(btnzj!=null){
		btnzj.click(function () {
			var url = _path+'/zfxg/xsxxgl/zjXsxx.html';
			showDialog('增加学生信息',750,500,url,{okVal:'保存',ok:function(){this.content.zjXsxx();return false;},cancelVal:'取消',cancel:true});
		});
	}
	
	// 绑定删除事件
	if(btnsc!=null){
		btnsc.click(function () {
			
			plcz(_path+'/zfxg/xsxxgl/scXsxxAjax.html','删除');
			return false;
		});
	}

	// 绑定修改事件
	if(btnxg!=null){
		btnxg.click(function () {
			var id=getChecked();
			if(id.length!=1){
				alert("请先选定一条记录!");
				return ;
			}
			var url=_path+'/zfxg/xsxxgl/xgXsxx.html';
			url+="?";
			url+="xh="+id;
			showDialog('修改学生信息',750,500,url,{okVal:'保存',ok:function(){this.content.xgXsxx();return false;},cancelVal:'取消',cancel:true});
			return false;
		});
	}
	

	// 绑定查看事件
	if(btnck!=null){
		btnck.click(function () {
			var id=getChecked();
			if(id.length!=1){
				alert("请先选定一条记录!");
				return ;
			}
			var url=_path+'/zfxg/xsxxgl/ckXsxx.html';
			url+="?";
			url+="xh="+id;
			showDialog('查看学生信息',750,450,url,{cancelVal:'取消',cancel:true});
			return false;
		});
	}
	
	
	jQuery("#btn_dc").unbind().bind("click",function () {
		var url = _path+'/zfxg/xsxxgl/export.html';
		customExport(700,450,_path+'/zfxg/xsxxgl/customExport.html',function(){
			setSearchTag();
			jQuery("#form1").attr("action",url);
			jQuery("#form1").submit();
		});
	});

	jQuery("#btn_dr").unbind().bind("click",function () {
		importData("IMPORT_N010802_XSXX");
		return false;
	});
	
	jQuery("#btn_yhkgx").unbind().bind("click",function () {
		importData("IMPORT_N010803_XSXX");
		return false;
	});
	
	
	// 绑定批量导入照片
	if(btnpldrzp!=null){
		btnpldrzp.click(function () {
			var url=_path+'/zfxg/xsxxgl/pldrzpXsxx.html';
			showDialog('批量导入照片',530,350,url,{okVal:'导入',ok:function(){this.content.pldrzp();return false;},cancelVal:'取消',cancel:true});
			return false;
		});
	}

}
