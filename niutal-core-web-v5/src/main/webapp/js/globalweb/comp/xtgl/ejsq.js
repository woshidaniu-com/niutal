$(function(){

	var tempGridOptions = {
		url:  'getEjsqList.zf',
		uniqueId: "zgh",
		columns: [
            {checkbox: true }, 
            {field: 'zgh', title: '用户名',sortable:true}, 
            {field: 'xm', title: '姓名',sortable:true}, 
            {field: 'lxdh',title: '联系电话',sortable:true},
            {field: 'dzyx',title: '邮箱',sortable:true},
            {field: 'jgmc',title: '部门'},
            {field: 'ejsq',title: '状态',align:'center',sortable:true,formatter:function(v,r,i){
            	return v == "1" ? "已分配" : "未分配";
            }},
            {field: 'id',title: '操作',align:'center',sortable:false,formatter:function(v,r,i){
            	return  "<button class='btn btn-warning btn-xs' onclick='fpqx(\""+r["zgh"]+"\");'> 分配权限 </span>";
            }}
    	],
	 	toolbar:'#but_ancd',
	 	sortName: 'zgh',
		sortOrder: "desc",
	 	searchParams:function(){
	 		var map = $.search.getSearchMap();
			return map;
	 	}
	};
	
	$("#tabGrid").loadGrid(tempGridOptions);
	
	$(":button[name=search_button]").bind("click",searchResult);
});

function fpqx(zgh){
	$.showDialog('ejsq.zf?zgh='+zgh,'二级授权',$.mergeObj(modifyConfig,{
			width: "1000px"
		}
	));
}

//查询
function searchResult(){
	$("#tabGrid").refreshGrid();
}

