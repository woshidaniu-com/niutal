jQuery(function($) {

    var options = {
	url : 'listData.zf',
	uniqueId : "id",
	toolbar : '#but_ancd',
	classes : 'table table-condensed',
	striped : false,
	columns : [ {
	    checkbox : true
	}, {
	    field : 'id',
	    title : 'ID',
	    width : '40px',
	    sortable : false,
	    align : 'center',
	    visible : false,
	    hidden : true
	}, {
	    field : 'name',
	    title : ' 流 程 业 务名 称 ',
	    sortable : false,
	    width : '150px',
	    align : 'center'
	}, {
	    field : 'description',
	    title : ' 流 程 业 务 描 述 ',
	    align : 'center',
	    sortable : false,
	    width : '300px'
	} ],
	searchParams : function() {
	    var map = {};
	    map["name"] = jQuery('#name').val();
	    return map;
	}
    };
    $('#tabGrid').loadGrid(options);

    /* ====================================================绑定按钮事件==================================================== */

    // 绑定查看事件
    jQuery("#btn_ck").click(
	    function() {
		var ids = $('#tabGrid').getKeys();
		if (ids.length != 1) {
		    $.alert('请选定一条记录!');
		    return;
		}
		var rowData = $('#tabGrid').getRow(ids[0]);
		var url = _path + "/processBiz/" + rowData["id"] + "/ck.zf";
		$.showDialog(url, '流程业务对象查看', $
			.extend({}, viewConfig, {
			    "width" : ($(window).width() * 0.7)
			}));
	    });

});

// 查询
function searchResult() {
    $('#tabGrid').refreshGrid();
}

// 回车键查询
$('#name').bind("keydown", "return", function(ev) {
    searchResult()
})
