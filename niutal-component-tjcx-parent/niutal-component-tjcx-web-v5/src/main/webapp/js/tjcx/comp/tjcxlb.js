var exportConfigList;
jQuery(function() {
	onShow();
});


function onShow() {
	setInitTjlbGltj();//设置过滤条件
	setDcButton();
	setSxButton();
}


function _getGltj(){
	var xmdm = jQuery("#xmdm").val();
	var newGltj = getTjlbGltjValue();
	jQuery("#newGltj").val(newGltj);
	var gltj = jQuery("#gltj").val();
	var allGltj = jQuery("#allGltj").val();

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
	
	return allGltj;
}

function setInit(){
	var sHtml = "";
	sHtml += "<table id='tabGrid'></table>";
	//sHtml += "<div id='pager'></div>";
	jQuery("#data-row").html(sHtml);
	var xmdm = jQuery("#xmdm").val();
	jQuery.ajax({
	     type: "post",
	     url:  _path+"/niutal/tjcx/tjcx/tjcxlb.zf?doType=query&timestamp=" + new Date().getTime(),
	     data: {
				xmdm : xmdm
			},
	     dataType: "json",
	     success: function (data) {
			exportConfigList = data;
			if(exportConfigList == null || exportConfigList.length == 0){
				$.alert("字段未进行相关配置，请联系管理员！");
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
	
	var url = _path+'/niutal/tjcx/tjcx/tjcxsj.zf?doType=query';
	url += "&timestamp=" + new Date().getTime();
	
	
	var tjcxGrid = {
			 url: url,
			 classes:'table table-condensed',
			 striped: false,
			 toolbar: '#buttonbox',
//			 columns: [
//	            {checkbox: true }, 
//	            {field: 'id', title: 'ID',width:'80px', sortable:false,align:'center',visible:false, hidden:true},
//	            {field: 'name',title: ' 流 程 名 称 ',sortable:false,width:'150px',align:'center'},
//	            {field: 'key',title: ' 流 程 标 识 ',align:'center',sortable:false,width:'100px',visible:false, hidden:true},
//	            {field: 'category', title: ' 流 程 类 别 ',sortable:false,width:'80px',align:'center'}, 
//	            {field: 'version', title: ' 版 本 ', sortable:false, width:'30px'}, 
//	            {field: 'description',title: ' 流 程 描 述 ',align:'center',sortable:false,width:'200px',visible:false, hidden:true},
//	            
//	            {field: 'state',title: ' 状 态 ',align:'center',sortable:false,width:'50px',formatter:function(value,row,index){
//	            	var ret;
//	            	if(value == '1'){
//	            		 ret = '<span class="text-success"> 启  用 </span>';	
//	            	}else if(value == '2'){
//	            		 ret = '<span class="text-danger"> 停  用 </span>';
//	            	}
//	            	return ret;
//	            }},
//	            {field: 'deploymentId',title: '流 程 部 署 ID',visible:false,hidden:true}
//          ],
          searchParams:function(){
	   	    var map = {};
	   		map["xmdm"] = xmdm;
	   		map["gltj"] = _getGltj();
	        return map;
	        }
		};
	
//	var tjcxGrid = $.extend({},BaseJqGrid,{  
//		caption : "信息列表",
//		pager: "#pager", //分页工具栏  
//	    url: url, //这是Action的请求地址  
//	    autoencode:true,
//	    autowidth:true,
//	    multiselect:false,
//	    resizeHandle:"#data-row",
//	 	postData: {
//			xmdm:xmdm,
//			gltj:allGltj
//		},
//	});

	tjcxGrid.columns = eval(createColModelJson());
	//tjcxGrid.sortname = getSortname();
	
	//$("#tabGrid").loadJqGrid(tjcxGrid);
	$('#tabGrid').loadGrid(tjcxGrid);
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
		if(i > 0){
			str += ",";
		}
		str += "{";
		str += "title:'"+zdmc+"'";
		str += ",field:'"+zd.toUpperCase()+"'";
		str += ",align:'center'";
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
			var url = _path+'/niutal/tjcx/tjcx/export.zf'+ '?timestamp=" + new Date().getTime()';
			$.customExport('tjcx_' + xmdm,url,function(){},{"formName":"form1"});
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
	//var gltj = getTjlbGltjValue();
	//jQuery("#newGltj").val(gltj);
	//setInit();
	$('#tabGrid').refreshGrid();
}

