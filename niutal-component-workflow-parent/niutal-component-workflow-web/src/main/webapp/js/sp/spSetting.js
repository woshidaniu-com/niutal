jQuery(function() {
	var DictionaryGrid = $.extend(true, {}, BaseJqGrid, {
		resizeHandle:"#fixWidth",
		pager : "#pager", //分页工具栏  
		url : _path + "/sp/spSetting_cxSpList.html?doType=query", //这是Action的请求地址  
		colNames : [ 'pid', '业务名称', '流程描述', '步骤个数', '启用状态'],
		colModel : [ {name : 'PID',index : 'PID',key : true,hidden : true}, 
					 {name : 'BUSINESSNAME',index : 'BUSINESSNAME',width:'300px'}, 
					 {name : 'PDESC',index : 'PDESC',width:'300px'}, 
					 {name : 'NODECOUNTS',index : 'NODECOUNTS',align : 'center',width:'250px'}, 
					 {name : 'PSTATUS',index : 'PSTATUS',align : 'center',width:'250px',
							formatter : 'select',editoptions : {value : "0:停用;1:启用"}}
				   ],
		sortname : '', //首次加载要进行排序的字段 
		sortorder : "asc" //排序方式
	});
	$("#tabGrid").loadJqGrid(DictionaryGrid);

	jQuery("#btn_zj").click(function() {
		$.showDialog(_path + '/sp/spSetting_zjSp.html','新增流程配置',modifyConfig);
        return false;
	});
	
	jQuery("#btn_xg").click(function() {
		var ids = $("#tabGrid").getKeys();			
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		} 
		$.showDialog( _path + '/sp/spSetting_zjSp.html?pid=' + ids[0],'修改流程配置',modifyConfig);
		return false;
	});
	
	jQuery("#btn_sc").click(function() {
		plcz(_path+'/sp/spSetting_scSp.html','删除');
	});
	
	jQuery("#btn_ck").click(function() {
		var ids = $("#tabGrid").getKeys();		
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		$.showDialog(_path+'/sp/spSetting_ckSp.html?pid=' + ids[0],'查看流程配置',$.extend({},viewConfig,{
			width		: "900px"
		}));
		return false;
	});
});

function searchResult() {
	var map = {};
		map["ywdl"] = jQuery('#selectYwdl').val();
		map["businessName"] = jQuery('#selectBusinessName').val();
	search('tabGrid', map);
}
