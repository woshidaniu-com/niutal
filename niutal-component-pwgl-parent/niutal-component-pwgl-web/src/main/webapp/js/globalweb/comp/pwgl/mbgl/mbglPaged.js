jQuery(function($){
	var options = {
		url: _path + '/pwgl/mbgl/getPagedListAjax.zf',
		uniqueId: "id",
		toolbar: '#but_ancd',
		columns:[
			{checkbox: true },
			{field: 'mkmc', title: '模块名称', sortable:true},
			{field: 'drmbmc', title: '导入模版名称', sortable:true},
			{field: 'drpath', title: '操作', sortable:false, clickToSelect:false, formatter:function(v, r, i){
				var _html = new Array();
				_html.push("<a href='javascript:void(0);' data-id='");
				_html.push(r['id']);
				_html.push("'class='mb-upload green'>");
				_html.push("<i class='glyphicon glyphicon-cloud-upload green'></i>");
				_html.push("<u class='mb-text'>模版上传</u></a>");
				if(null != v && v != ''){
					_html.push("&#8195;|&#8195;<a href='javascript:void(0);' data-id='");
					_html.push(r['id']);
					_html.push("' class='mb-download light-blue'>");
					_html.push("<i class='glyphicon glyphicon-cloud-download light-blue'></i>");
					_html.push("<u class='mb-text'>模版下载</u></a>");
				}
				return _html.join("");
			}}
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