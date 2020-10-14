jQuery(function($){
	var options = {
		url: _path + '/pwgl/sjygl/getPagedListAjax.zf',
		uniqueId: "id",
		toolbar: '#but_ancd',
		columns:[
			{checkbox: true },
			{field: 'mkmc', title: '模块名称', sortable:true},
			{field: 'col', title: '字段值', sortable:true},
			{field: 'com', title: '字段名', sortable:true},
			{field: 'key', title: '主键标识', sortable:true, formatter:function(v, r, i){
				if(v == '1'){
					return '是';
				}else{
					return '否';
				}
			}},
			{field: 'zt', title: '启用', sortable:true, formatter:function(v, r, i){
				if(v == '1'){
					return '是';
				}else{
					return '否';
				}
			}},
			{field: 'filename', title: '文件名标识', sortable:true, formatter:function(v, r, i){
				if(v == '1'){
					return '是';
				}else{
					return '否';
				}
			}},
			{field: 'xssx', title: '显示顺序', sortable:true}
		],
		searchParams: function(){
			return $.search.getSearchMap();
		}
	};
	$('#tabGrid').loadGrid(options);
	$("button[name='search_button']").bind("click", searchResult);
});

//查询
function searchResult(){
	$('#tabGrid').refreshGrid();
}