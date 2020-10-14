jQuery(function($){
	
	var options = {
			 url: 'getJcsjList.zf',
			 uniqueId: "pkValue",
			 toolbar:  '#but_ancd',
			 sortName: 'lxmc',
			 sortOrder: "asc",
			 classes:'table table-condensed',
			 showToggle:true,
		 	 showRefresh:true,
		 	 showColumns:true,
			 columns: [
	            {checkbox: true }, 
	            {field: 'pkValue', title: '主键',visible:false}, 
	            {field: 'lx', title: '类型',visible:false}, 
	            {field: 'lxmc',title: '类型',sortable:true,width:'350px'}, 
	            {field: 'dm',title: '代码',sortable:true,width:'250px'},
	            {field: 'mc',title: '名称',sortable:true, width:'300px'},
	            {field: 'xtjb',title: '级别',sortable:true,width:'100px',formatter:function(value,row,index){
	            	if(value == 'xt')
	            		return '系统';
	            	if(value == 'yw')
	            		return '业务';
	            	return '';
	            }},
	            {field: 'zt',title: '启用状态',sortable:true,width:'100px',align:'center',formatter:function(value,row,index){
	            	if(value == '0')
	            		return '<span class="text-danger"> 停 用 </span>';
	            	if(value == '1')
	            		return '<span class="text-success"> 启 用 </span>';
	            	return '';
	            }}
           ],
           searchParams:function(){
    	    var map =  $.search.getSearchMap();
//    		map["lxdm"] = jQuery('#cx_lxdm').val();
//    		map["dm"] = jQuery('#cx_dm').val();
//    		map["mc"] = jQuery('#cx_mc').val();
//    		map["zt"] = jQuery('#cx_zt').val();
           	return map;
           }
		};
	
		$('#tabGrid').loadGrid(options);
	
	
//	var JcsjGrid =jQuery.extend(true,{},BaseJqGrid,{  
//		resizeHandle:"#sl_all_form",
//		pager: "#pager", //分页工具栏  
//	    url:'getJcsjList.zf', //这是Action的请求地址  
//	    colModel:[
//	    	 {label:'主键',name:'pkValue', index: 'pkValue',key:true,hidden:true,align:'center'},
//	    	 {label:'lx',name:'lx', index: 'lx',hidden:true},
//		     {label:'类型',name:'lxmc', index: 'lxmc',align:'left',width:'350px'},
//		     {label:'代码',name:'dm', index: 'dm',align:'center',width:'250px'},
//	      	 {label:'名称',name:'mc', index: 'mc',align:'left',width:'300px'},
//	      	 {label:'级别',name:'xtjb', index: 'xtjb',align:'center',width:'100px',formatter:'select',editoptions:{value:"xt:系统;yw:业务"}}
//		],
//		sortname: 'lxmc',
//	 	sortorder: "asc",
//	 	
//	});
//	
//	$("#tabGrid").loadJqGrid(JcsjGrid);
	
	/*====================================================绑定按钮事件====================================================*/
	
	var addJCSJConfig = {
			width		: "500px",
			modalName	: "addjcsjModal",
			formName	: "ajaxForm",
			gridName	: "tabGrid",
			offAtOnce	: false,
			buttons		: {
				success : {
					label : "确 定",
					className : "btn-primary",
					callback : function() {
						var $this = this;
						var opts = $this["options"]||{};
						submitForm(opts["formName"]||"ajaxForm",function(responseText,statusText){
							$this.reset();
							if(responseText["status"] == 'success'){
								$.success(responseText["message"],function() {
									if(opts.offAtOnce){
										$.closeModal(opts.modalName);
									}
									var tabGrid = $("#" + opts["gridName"]||"tabGrid");
									if($(tabGrid).size() > 0){
										$(tabGrid).reloadGrid();
									}
								});
							}else if(responseText["status"] == "fail"){
								$.error(responseText["message"],function() {
								});
							} else{
								$.alert(responseText["message"],function() {
								});
							}
						});
						return false;
					}
				},
				cancel : {
					label : "关 闭",
					className : "btn-default"
				}
			}
		};
		
		
	//绑定增加点击事件
	jQuery("#btn_zj").click(function () {
		$.showDialog("zjJcsj.zf",'增加',addJCSJConfig);
	});
	
	//绑定删除事件
	jQuery("#btn_sc").click(function () {
		var ids = $('#tabGrid').getKeys();
		if (ids.length == 0 ) {
			$.alert('请至少选定一条记录!');
			return;
		}
		var isB = true
		var lxs = [];
		var dms = [];
		$.each(ids,function(index,id){
			var row = jQuery("#tabGrid").getRow(id);
			lxs.push(row['lx']);
			dms.push(row['dm']);
			if(row["xtjb"]=='xt'){
				isB  = false;
				return;
			}
		});
		
		if(isB){
			if (ids.length == 0){
				$.alert('请选择您要删除的记录！');
			} else {
				$.confirm('您确定要删除选择的记录吗？',function(result){
					if(result){
						jQuery.ajaxSetup({async:false});
						jQuery.post("scJcsj.zf",{"ids":ids.join(","), "lxs": lxs.join(","), "dms": dms.join(",")},function(responseText){
							setTimeout(function(){
								if(responseText.status == 'success'){
									$.success(responseText.message,function() {
										$('#tabGrid').refreshGrid();
									});
								}else if(responseText.status == 'fail'){
									$.error(responseText.message);
								} else{
									$.alert(responseText.message);
								}
							},1);
						},'json');
						jQuery.ajaxSetup({async:true});
					}
				});
			}
			
		}else{
			$.alert("系统级别数据不能删除！");
		}
		
		
	});

	//绑定修改事件
	jQuery("#btn_xg").click(function () {
		var ids = $('#tabGrid').getKeys();
		if(ids.length != 1){
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		if(row["xtjb"]=='xt'){
			$.alert("系统级别数据不能修改！");
			return false;
		}
		$.showDialog("xgJcsj.zf?pkValue="+row.pkValue,'修改',$.extend({},modifyConfig,{"width":500}));
	});
	
	$(":button[name=search_button]").bind("click",searchResult);
});

//查询
function searchResult(){
	$('#tabGrid').refreshGrid();
}
