var exportConfigList;
jQuery(function() {
	onShow();
});

function onShow() {
	setCurrentMenu1();//设置当前位置
	setInitTjlbGltj();//设置过滤条件
	setFhButton();
	setDcButton();
	setSxButton();
}

function setInit(){
	var sHtml = "";
	sHtml += "<table id='tabGrid'></table>";
	sHtml += "<div id='pager'></div>";
	jQuery(".formbox").html(sHtml);	
	
	var xmdm = jQuery("#xmdm").val();	
	jQuery.ajax({
	     type: "post",
	//     async: false,
	     url:  _path+"/zfxg/tjcx/tjcx_tjcxlb.html?doType=query&timestamp=" + new Date().getTime(),
	     data: {
				xmdm : xmdm
			},
	     dataType: "json",
	     success: function (data) {
			exportConfigList = data;
			if(exportConfigList == null || exportConfigList.length == 0){
				alert("字段未进行相关配置，请联系管理员！");
				return ;
			}
			tjcxsj();
	     }
	});	
	
}

function tjcxsj(){
	var xmdm = jQuery("#xmdm").val();
	var gltj = jQuery("#gltj").val();
	var allGltj = jQuery("#allGltj").val();
	var newGltj = jQuery("#newGltj").val();
	
	if(newGltj != ""){
		newGltj = "(" + newGltj + ")";
	}
	
	if(gltj == "" && newGltj != ""){
		allGltj = newGltj;
	}else if(gltj != "" && newGltj != ""){
		allGltj = gltj + " and (" + newGltj + ")";
	}else{
		allGltj = gltj;
	}
	
	jQuery("#allGltj").val(allGltj);
	
	var url = _path+'/zfxg/tjcx/tjcx_tjcxsj.html?doType=query';
	url += "&timestamp=" + new Date().getTime();

	var TjcxGrid = Class.create(BaseJqGrid,{
		caption : "信息列表",
		pager: "pager", //分页工具栏  
	    url:  url,
	    postData: {
			xmdm:xmdm,
			gltj:allGltj
		},
	    autoencode:true,
	    multiselect:false,
	    shrinkToFit:false
	});
	
	var tjcxGrid = new TjcxGrid();
	tjcxGrid.colModel = eval(createColModelJson());
	tjcxGrid.sortname = getSortname();
	
	loadJqGrid("#tabGrid","#pager",tjcxGrid);
}

/*
 * 生成colModel的json格式
 */
function createColModelJson(){
	var str = "[";
	for ( var i = 0; i < exportConfigList.length; i++) {
		var o = exportConfigList[i];
		var zd = o.zd;
		var zdmc = o.zdmc;
//		if(i > 5){
//			break;
//		}
		if(i > 0){
			str += ",";
		}
		str += "{";
		str += "label:'"+zdmc+"'";
		str += ",name:'"+zd.toUpperCase()+"'";
		str += ",index:'"+zd.toUpperCase()+"'";
		str += "}";
	}
	str += "]";
	return str;
}

/*
 * 获取排序列
 */
function getSortname(){
	var str = "";
	for ( var i = 0; i < exportConfigList.length; i++) {
		var o = exportConfigList[i];
		var zd = o.zd;
		var zdmc = o.zdmc;
		if(i > 0){
			str += ",";
		}
		str += zd;
		if(i > 2){
			break;
		}
	}
	return str;
}



function setDcButton(){
	var xmdm = jQuery("#xmdm").val();
	var btn_dc=jQuery("#btn_dc");//导出
	if(btn_dc != null){
		btn_dc.click(function () {
			
		    //var records = jQuery("#tabGrid").jqGrid('getGridParam','records');
			//if(parseInt(records) > 10000){
			//	alert("导出数据过多，超出10000条，请增加查询条件后进行导出！");
			//	return;
			//}
		    
			var url = _path+'/zfxg/tjcx/tjcx_export.html'+ '?timestamp=" + new Date().getTime()';
			customExport(780,500,_path+'/zfxg/tjcx/tjcx_customExport.html?xmdm=' + xmdm,function(){
				jQuery("#form1").attr("action",url);
				jQuery("#form1").submit();
			});
		});
	}
}

function setFhButton(){
	var btn_fh=jQuery("#btn_fh");//返回
	if (btn_fh != null) {		
		var tjcxlbxq = jQuery("#tjcxlbxq").val();
		if(tjcxlbxq == "1"){
			btn_fh.parent().remove();
		}
		btn_fh.click(function () {
	//		refRightContent(_path+'/zfxg/tjcx/tjcx_tjcx.html');
			var url = _path+"/zfxg/tjcx/tjcx_tjcx.html";
			url += "?timestamp=" + new Date().getTime();

			if(jQuery("#rightContent").length > 0){
				ajaxSubForm("form1", url);
			}else{
				subForm(url);
			}
		});
	}
}

//刷新按钮
function setSxButton(){
	var btn_sx=jQuery("#btn_sx");//
	if (btn_sx != null) {
		btn_sx.click(function () {
			setInit();
		});
	}
}



//点击统计查询按钮
function tjcx(){
	var gltj = getTjlbGltjValue();
	jQuery("#newGltj").val(gltj);
	setInit();
}

