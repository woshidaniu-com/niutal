jQuery(function($){
	
	var JcsjGrid =jQuery.extend(true,{},BaseJqGrid,{  
		resizeHandle:"#sl_all_form",
		pager: "#pager", //分页工具栏  
	    url:_path+'/xtgl/jcsj_cxJcsj.html?doType=query', //这是Action的请求地址  
	    colModel:[
	    	 {label:'主键',name:'pkValue', index: 'pkValue',key:true,hidden:true,align:'center'},
	    	 {label:'lx',name:'lx', index: 'lx',hidden:true},
		     {label:'类型',name:'lxmc', index: 'lxmc',align:'left',width:'350px'},
		     {label:'代码',name:'dm', index: 'dm',align:'center',width:'250px'},
	      	 {label:'名称',name:'mc', index: 'mc',align:'left',width:'300px'},
	      	 {label:'英文名称',name:'ywmc', index: 'ywmc',align:'left',width:'300px'},
	      	 {label:'级别',name:'xtjb', index: 'xtjb',align:'center',width:'100px',formatter:'select',editoptions:{value:"xt:系统;yw:业务"}}
		],
		sortname: 'lxmc,dm,mc', //首次加载要进行排序的字段 
	 	sortorder: "asc",
	 	
	});
	
	$("#tabGrid").loadJqGrid(JcsjGrid);
	
	/*====================================================绑定按钮事件====================================================*/
	
	//绑定增加点击事件
	jQuery("#btn_zj").click(function () {
		$.showDialog(_path+"/xtgl/jcsj_zjJcsj.html",'增加',$.extend({},addConfig,{"width":500}));
	});
	
	//绑定删除事件
	jQuery("#btn_sc").click(function () {
		var ids = getChecked();
		if (ids.length == 0 ) {
			$.alert('请至少选定一条记录!');
			return;
		}
		var isB = true
		$.each(ids,function(index,id){
			var row = jQuery("#tabGrid").jqGrid('getRowData', id);
			if(row["xtjb"]=='xt'){
				isB  = false;
				return;
			}
		});
		
		if(isB){
			plcz('jcsj_scJcsj.html','删除');
		}else{
			$.alert("系统级别数据不能删除！");
		}
		
		
	});

	//绑定修改事件
	jQuery("#btn_xg").click(function () {
		var ids = $("#tabGrid").getKeys();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', ids[0]);
		if(row["xtjb"]=='xt'){
			$.alert("系统级别数据不能修改！");
			return false;
		}
		$.showDialog(_path+"/xtgl/jcsj_xgJcsj.html?pkValue="+row.pkValue,'修改',$.extend({},modifyConfig,{"width":500}));
	});
	
});

//查询
function searchResult(){
	var map = {};
	map["xtjb"] = jQuery('#cx_xtjb').val();
	map["lxdm"] = jQuery('#cx_lxdm').val();
	map["dm"] = jQuery('#cx_dm').val();
	map["mc"] = jQuery('#cx_mc').val();
	search('tabGrid',map);
}