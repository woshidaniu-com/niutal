jQuery(function($){
	
	var NewsGrid = $.extend({},BaseJqGrid,{  
		resizeHandle:"#searchBox",
		pager: "pager", //分页工具栏  
	    url: _path+'/xtgl/xwgl_cxXw.html?doType=query', //这是Action的请求地址  
	    shrinkToFit: true,
	    colModel:[
		     {label:'ID',name:'xwbh', index:'xwbh', key:true, hidden:true},
		     {label:'通知标题',name:'xwbt', index: 'xwbt',align:'left',width:"440px"},
		     {label:'发布时间',name:'fbsj', index: 'fbsj',align:'left',width:"180px"},
	      	 {label:'发布人',name:'fbr', index: 'fbr',align:'left',width:"120px"},
		     {label:'发布对象',name:'fbdx', index: 'fbdx',width:"120px",align:'center'},
		     {label:'是否发布',name:'sffb', index: 'sffb',hidden:true},
		     {label:'是否发布',name:'sffbmc', index: 'sffbmc',width:"120px",align:'center'},
		     {label:'是否置顶',name:'sfzd', index: 'sfzd',hidden:true},
		     {label:'是否置顶',name:'sfzdmc', index: 'sfzdmc',width:"120px",align:'center'}
		],
		sortname: 'fbsj', //首次加载要进行排序的字段 
	 	sortorder: "desc"
	});
	
	$("#tabGrid").loadJqGrid(NewsGrid);

	/*====================================================绑定按钮事件====================================================*/
	
	
	//绑定增加点击事件
	jQuery("#btn_zj").click(function () {
		$.showDialog(_path+'/xtgl/xwgl_zjXw.html','增加', $.extend({},addConfig,{
			 //是否使用模拟滚动条
		    customScrollbar : false
		}));
	});

	//修改
	jQuery("#btn_xg").click(function () {
		var ids = getChecked();
		if(ids.length != 1){
			$.alert('请先选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', ids[0]);
		//业务判断
		if(row.sffb=='1'){
			$.alert('不能对已发布的记录进行修改！');
		}else{
			$.showDialog(_path+"/xtgl/xwgl_xgXw.html?xwbh="+row.xwbh,'修改',$.extend({},modifyConfig,{
				 //是否使用模拟滚动条
			    customScrollbar : false
			}));
		}
	});

	jQuery("#btn_sc").click(function () {
		var selectedRows = $("#tabGrid").getSelectedRows();
		var deleteAble = true;
		$.each(selectedRows||[],function(i,row){
			if(row.sffb == 1){
				deleteAble = false;
			}
			return deleteAble;
		});
		if(deleteAble){
			plcz(_path+'/xtgl/xwgl_scXw.html','删除');
		}else{
			$.alert("已发布的通知不允许删除!");
		}
	});
	
	jQuery("#btn_ck").click(function () {
		var ids = getChecked();
		if(ids.length != 1){
			$.alert('请选择一条您要查看的记录!');
		}else{
			var row = jQuery("#tabGrid").jqGrid('getRowData', ids[0]);
			$.openWin( _path+"/xtgl/xwgl_ckXw.html?xwbh="+row.xwbh);
		}
	});
	
	//发布
	jQuery("#btn_fb").click(function () {
		var selectedRows = $("#tabGrid").getSelectedRows();
		var fbAble = true;
		$.each(selectedRows||[],function(i,row){
			//console.log("row.sffb:" + row.sffb);
			if(row.sffb == 1){
				fbAble = false;
			}
			return fbAble;
		});
		if(fbAble){
			plcz(_path+'/xtgl/xwgl_fbXw.html','发布');
		}else{
			$.alert("已发布的通知无需再次发布!");
		}
	});
	
	//取消发布
	jQuery("#btn_qxfb").click(function () {
		var selectedRows = $("#tabGrid").getSelectedRows();
		var cancelAble = true;
		$.each(selectedRows||[],function(i,row){
			if(row.sffb == 0){
				cancelAble = false;
			}
			return cancelAble;
		});
		if(cancelAble){
			plcz(_path+'/xtgl/xwgl_qxfbXw.html','取消发布');
		}else{
			$.alert("未发布的通知无需取消发布!");
		}
	});
	
	//置顶
	jQuery("#btn_zd").click(function () {
		var selectedRows = $("#tabGrid").getSelectedRows();
		var zdAble = false;
		$.each(selectedRows||[],function(i,row){
			if(row.sfzd == 1){
				zdAble = true;
			}
			return !zdAble;
		});
		if(zdAble){
			$.alert("已置顶的通知无需再次置顶!");
		}else{
			plcz(_path+'/xtgl/xwgl_zdXw.html','置顶');
		}
	});
	
	//取消置顶
	jQuery("#btn_qxzd").click(function () {
		var selectedRows = $("#tabGrid").getSelectedRows();
		var zdAble = false;
		$.each(selectedRows||[],function(i,row){
			if(row.sfzd == 1){
				zdAble = true;
			}
			return !zdAble;
		});
		if(zdAble){
			plcz(_path+'/xtgl/xwgl_qxzdXw.html','取消置顶');
		}else{
			$.alert("未置顶的通知无需取消置顶!");
		}
		
	});
	
});


//查询
function searchResult(){
	var map = {};
	map["xwbt"] = jQuery('#xwbt').val();
	map["sffb"] = jQuery('#sffb').val();
	map["sfzd"] = jQuery('#sfzd').val();
	search('tabGrid',map);
}
function selectObj(obj){
	$("#groupId").val("");
	$("#_mc").val("");
	if(obj.value!='0'){
		$("#xzdx-div").show();
	}else{
		$("#xzdx-div").hide();
	}
}

var mxdxConfig = {
		width : "900px",
		modalName : "mxdxModal",
		buttons : {
			success : {
				label : "保存",
				className : "btn-primary",
				callback : function() {
				var groupId = $("#xzlb").val();
					groupId +="#"+($.founded($("#xqh_id").val())==true?$("#xqh_id").val():"n");
					groupId +="#"+($.founded($("#jg_id").val())==true?$("#jg_id").val():"n");
					groupId +="#"+($.founded($("#zyh_id").val())==true?$("#zyh_id").val():"n");
					groupId +="#"+($.founded($("#njdm_id").val())==true?$("#njdm_id").val():"n");
					groupId +="#"+($.founded($("#bh_id").val())==true?$("#bh_id").val():"n");
					groupId +="#"+($.founded($("#xbdm").val())==true?$("#xbdm").val():"n");
					groupId +="#"+($.founded($("#xslbdm").val())==true?$("#xslbdm").val():"n");
					groupId +="#"+($.founded($("#pyccdm").val())==true?$("#pyccdm").val():"n");
				var checkedDate = jQuery("#tabGrid1").jqGrid('getGridParam', 'selarrrow');
				var mxId = [];
				if(checkedDate.length>0){
					$.each(checkedDate,function(i,val){
						mxId.push(groupId+"#"+val);
					});
				}else{
					mxId.push(groupId+"#n");
				}
				$("#_mc").val($("#xzlb").find("option:selected").text());
				$("#groupId").val(mxId.join());
				jQuery.closeModal("mxdxModal");
				return false;
			}
			},
			cancel : {
				label : "关 闭",
				className : "btn-default"
			}
		}
};

var mxdxConfig2 = {
		width : "900px",
		modalName : "mxdxModal",
		buttons : {
			success : {
				label : "保存",
				className : "btn-primary",
				callback : function() {
				if(!$.founded($("#xnm").val())){
					$.alert("请选择学年");
					return false;
				}
				if(!$.founded($("#xqm").val())){
					$.alert("请选择学年");
					return false;
				}
				
				var groupId = $("#xzlb").val();
					groupId +="#"+($.founded($("#xnm").val())==true?$("#xnm").val():"n");
					groupId +="#"+($.founded($("#xqm").val())==true?$("#xqm").val():"n");
					groupId +="#"+($.founded($("#xqh_id").val())==true?$("#xqh_id").val():"n");
					groupId +="#"+($.founded($("#jg_id").val())==true?$("#jg_id").val():"n");
					groupId +="#"+($.founded($("#kkbm_id").val())==true?$("#kkbm_id").val():"n");
					groupId +="#"+($.founded($("#xbdm").val())==true?$("#xbdm").val():"n");
					
				var checkedDate = jQuery("#tabGrid1").jqGrid('getGridParam', 'selarrrow');
				var mxId = [];
				if(checkedDate.length>0){
					$.each(checkedDate,function(i,val){
						mxId.push(groupId+"#"+val);
					});
				}else{
					mxId.push(groupId+"#n");
				}
				$("#_mc").val($("#xzlb").find("option:selected").text());
				$("#groupId").val(mxId.join());
				jQuery.closeModal("mxdxModal");
				return false;
			}
			},
			cancel : {
				label : "关 闭",
				className : "btn-default"
			}
		}
};


var mxdxConfig3 = {
		width : "900px",
		modalName : "mxdxModal",
		buttons : {
			success : {
				label : "保存",
				className : "btn-primary",
				callback : function() {
				var groupId = $("#xzlb").val();
					groupId +="#"+($.founded($("#jsdm").val())==true?$("#jsdm").val():"n");
					 
					
				var checkedDate = jQuery("#tabGrid1").jqGrid('getGridParam', 'selarrrow');
				var mxId = [];
				if(checkedDate.length>0){
					$.each(checkedDate,function(i,val){
						mxId.push(groupId+"#"+val);
					});
				}else{
					mxId.push(groupId+"#n");
				}
				$("#_mc").val($("#xzlb").find("option:selected").text());
				$("#groupId").val(mxId.join());
				jQuery.closeModal("mxdxModal");
				return false;
			}
			},
			cancel : {
				label : "关 闭",
				className : "btn-default"
			}
		}
};