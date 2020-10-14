//绑定导入事件
function sjfwsz(){
	var jsdm = jQuery('#jsdm').val();
	if (jsdm == '') {
		alert('请选择角色！');
		return false;
	}
	var ids = getChecked();
	if (ids.length < 1) {
		alert('请选定用户记录!');
		return false;
	}
	var params = [];
	jQuery.each(ids, function(index, id) {
		var row = jQuery("#tabGrid").jqGrid('getRowData', id);
		params.push("zghList[" + index + "]=" + row.zgh);
	});
	params.push("js_id=" + jsdm);
	// 批量数据授权
	showDialog('数据授权', 1000, 600, 'yhsjfwgl_cxSjfwszIndex.html?' + params.join("&"));
	
};
