jQuery(function($) {				
	var DictionaryGrid = $.extend(true,{},BaseJqGrid, {
		resizeHandle:"#childFixWidth",
		multiselect:false,//显示复选框
		shrinkToFit: true,
		url : _path + "/sp/spSetting_cxSelectBusiness.html?doType=query", //这是Action的请求地址  	
		mType:'post',
		postData:paramMap(),
		//caption : "列表",
		pager : "#childPager", //分页工具栏  
		colModel : [ {
			label : '业务代码',
			name : 'YWDM',
			index : 'YWDM',
			key : true
		}, {
			label : '业务名称',
			name : 'YWMC',
			index : 'YWMC',
		}, {
			label : '业务大类',
			name : 'YWDL',
			index : 'YWDL',
		}, {
			label : '业务大类名称',
			name : 'YWDLMC',
			index : 'YWDLMC',
		} ],
		sortname : 'YWDL,YWDM', //首次加载要进行排序的字段 
		sortorder : 'asc'
	});
	$("#childGrid").loadJqGrid(DictionaryGrid);
});			

function paramMap(){
	var map = {};
		map["ywdl"] = jQuery('#ywdlSelect').val();
		map["businessName"] = jQuery('#businessNameSelect').val();
		map["cxAll"] = jQuery('#cxAll').val();
	return map;
}

function searchChild(){
	search('childGrid',paramMap());
}

function getResult(){
	var rows = $("#childGrid").getSelectedRows();
	if (rows.length != 1) {
		$.alert('请选定一条记录!');
		return false;
	}
	return rows[0];
}