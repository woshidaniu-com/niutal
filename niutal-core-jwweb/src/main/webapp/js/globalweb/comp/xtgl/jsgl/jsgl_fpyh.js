//为select option 增加title属性提示
jQuery(function(){
	
	//未分配用户列表
	//未分配Grid
	var WfpGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#leftGrid",
		multiselect : true, // 是否支持多选
		pager: "#wfpPager", //分页工具栏  
	    url:  _path+'/xtgl/jsgl_cxFpyhWfpYhxx.html?jsdm='+jsdm, //这是Action的请求地址  
	    colModel:[
	    	 {label:'用户名',name:'yhm', index: 'yhm',key:true,align:'left',width:"110px",sortable:false},
	    	 {label:'类 型',name:'lxmc', index: 'lxmc',align:'center',width:"50px",sortable:false},
		     {label:'姓 名',name:'xm', index: 'xm',align:'left',width:"110px",sortable:false},
		     {label:'所属部门',name:'jgmc', index: 'jgmc',align:'left',width:"130px",sortable:false},
	      	 {label:'联系电话',name:'lxdh', index: 'lxdh',align:'left',width:"100px",sortable:false},
	      	 {label:'邮箱',name:'dzyx', index: 'dzyx',align:'left',width:"100px",sortable:false}
		],
		sortname: 'yhm', //首次加载要进行排序的字段 
	 	sortorder: "desc",
	 	height	: "465",
	 	ondblClickRow:function(row_id,states){
		    //保存角色分配用户信息
		    zjJsyhfpxx([row_id]);
		}
	});
	
	$("#wfpTabGrid").loadJqGrid(WfpGrid);


	//已分配用户列表
	var YfpGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#rightGrid",
		pager: "#yfpPager", //分页工具栏  
		caption : "已分配用户列表",
		height	: "380",
		multiselect : true, // 是否支持多选
		rowNum 	: 12, // 每页显示记录数
		rowList : [12,20,50,100], // 可调整每页显示的记录数	
	    pager: "#pagerYfp", //分页工具栏  
	    url: _path+'/xtgl/jsgl_cxFpyhYfpYhxx.html?jsdm='+jsdm, //这是Action的请求地址  
	    colModel:[
	    	 {label:'用户名',name:'yhm', index: 'yhm',key:true,align:'left',width:"130px",sortable:false},
	    	 {label:'类 型',name:'lxmc', index: 'lxmc',align:'center',width:"50px",sortable:false},
		     {label:'姓 名',name:'xm', index: 'xm',align:'left',width:"150px",sortable:false},
		     {label:'所属部门',name:'jgmc', index: 'jgmc',align:'left',width:"180px",sortable:false}
		],
		sortname: 'yhm', //首次加载要进行排序的字段 
	 	sortorder: "desc",
	 	ondblClickRow:function(row_id,states){
			//删除角色已分配用户信息
	    	scJsyhfpxx([row_id]);
		}
	});
	
	$("#yfpTabGrid").loadJqGrid(YfpGrid);
	
	
	/*选择事件*/
	$("#checkedWfp").click(function(){
		var keys = [].concat($("#wfpTabGrid").getKeys());
		if(keys.length>0){
			//保存角色分配用户信息
			zjJsyhfpxx(keys);
		}else{
			$.alert("请选择记录!");
			return false;
		}
	});
	
	/*清除事件*/
	$("#clearYfp").click(function(){
		var keys = [].concat($("#yfpTabGrid").getKeys());
		if(keys.length>0){
			//删除角色已分配用户信息
			scJsyhfpxx(keys);
		}else{
			$.alert("请选择记录!");
			return false;
		}
	});

});

//查询
function searchResult() {
	var map = {};
	map["yhm"] = $.trim(jQuery('#yhm_xm').val());
	map["jg_id"] = jQuery('#jg_id_fp').val();
	var cxlb = $("input[name='cxlb']:checked").val();
	if(cxlb=="1"){//查询未分配列表
		search('wfpTabGrid', map);
	}else if(cxlb=="2"){//查询已分配列表
		search('yfpTabGrid', map);
	}else{//查询全部列表
		search('wfpTabGrid', map);
		search('yfpTabGrid', map);
	}
	//search('wfpTabGrid', map);
}
//给角色分配用户
function zjJsyhfpxx(array) {
	
	if (array != null && array.length > 0) {

		var requestMap = {
			"jsdm"		: 	$("#jsdm_modal").val(),
			"jsmc"		: 	$("#jsmc_modal").val(),
			"jsyhStr"	: 	$("#jsyhStr_modal").val()
		};
		jQuery.each(array,function(i,row_id) {
			requestMap["yhm_list["+i+"]"] = row_id;
		});
		
		jQuery.ajax({
			url		: _path + "/xtgl/jsgl_fpyhForJsyhzj.html",
			type	: "post",
			dataType: "json",
			data	: requestMap,
		    async	: false,
			success	:function(responseText){
				if(responseText == "1"){
					var cxlb = $("input[name='cxlb']:checked").val();
					if(cxlb=="1"){
						/*刷新未分配列表*/
						$("#wfpTabGrid").refershGrid({
							"yhm":$.trim($("#yhm_xm").val()),"jg_id":jQuery('#jg_id_fp').val()
						});
						/*刷新已分配列表*/
						$("#yfpTabGrid").refershGrid({});
					}else if(cxlb=="2"){
						/*刷新未分配列表*/
						$("#wfpTabGrid").refershGrid({});
						/*刷新已分配列表*/
						$("#yfpTabGrid").refershGrid({
							"yhm":$.trim($("#yhm_xm").val()),"jg_id":jQuery('#jg_id_fp').val()
						});
					}else{
						/*刷新未分配列表*/
						$("#wfpTabGrid").refershGrid({
							"yhm":$.trim($("#yhm_xm").val()),"jg_id":jQuery('#jg_id_fp').val()
						});
						
						/*刷新已分配列表*/
						$("#yfpTabGrid").refershGrid({
							"yhm":$.trim($("#yhm_xm").val()),"jg_id":jQuery('#jg_id_fp').val()
						});
					}
					
				}
			}
		});	
	} 
}

//删除角色已分配用户
function scJsyhfpxx(array) {
	
	if (array != null && array.length > 0) {

		var requestMap = {
			"jsdm"		: 	$("#jsdm_modal").val(),
			"jsyhStr"	: 	$("#jsyhStr_modal").val()
		};
		jQuery.each(array,function(i,row_id) {
			requestMap["yhm_list["+i+"]"] = row_id;
		});
		
		jQuery.ajax({
			url		: _path + "/xtgl/jsgl_fpyhForJsyhsc.html",
			type	: "post",
			dataType: "json",
			data	: requestMap,
		    async	: false,
			success	: function(responseText){
				if(responseText == "1"){
					var cxlb = $("input[name='cxlb']:checked").val();
					if(cxlb=="1"){
						/*刷新未分配列表*/
						$("#wfpTabGrid").refershGrid({
							"yhm":$.trim($("#yhm_xm").val()),"jg_id":jQuery('#jg_id_fp').val()
						});
						/*刷新已分配列表*/
						$("#yfpTabGrid").refershGrid({});
					}else if(cxlb=="2"){
						/*刷新未分配列表*/
						$("#wfpTabGrid").refershGrid({});
						/*刷新已分配列表*/
						$("#yfpTabGrid").refershGrid({
							"yhm":$.trim($("#yhm_xm").val()),"jg_id":jQuery('#jg_id_fp').val()
						});
					}else{
						/*刷新未分配列表*/
						$("#wfpTabGrid").refershGrid({
							"yhm":$.trim($("#yhm_xm").val()),"jg_id":jQuery('#jg_id_fp').val()
						});
						
						/*刷新已分配列表*/
						$("#yfpTabGrid").refershGrid({
							"yhm":$.trim($("#yhm_xm").val()),"jg_id":jQuery('#jg_id_fp').val()
						});
					}
					
				}
			}
		});	
	} 
}