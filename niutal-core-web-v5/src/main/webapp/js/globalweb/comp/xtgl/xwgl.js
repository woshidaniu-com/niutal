jQuery(function($){
	
	var options = {
			 url: 'getXwxxList.zf',
			 uniqueId: "xwbh",
			 toolbar:  '#but_ancd',
			 sortName: 'fbsj',
			 sortOrder: "desc",
			 classes:'table table-condensed',
			 showToggle:true,
		 	 showRefresh:true,
		 	 showColumns:true,
			 columns: [
	            {checkbox: true }, 
	            {field: 'xwbh', title: '新闻编号',visible:false,width:'70px'}, 
	            {field: 'xwbt', title: '通知标题',sortable:true, visible:true,width:'400px',cellStyle:function(v,r,i){
	            	return {
	            		css:{
	            			'word-break':'break-all'
	            		}
	            	}
	            }}, 
	            {field: 'fbsj', title: '发布时间',sortable:true,visible:true,width:'180px'}, 
	            {field: 'fbr',title: '发布人',sortable:true,width:'150px'}, 
	            {field: 'fbdxmcs',title: '发布对象',sortable:true, width:'180px'},
	            {field: 'ydqxmc',title: '阅读权限',sortable:true,width:'80px'},
	            {field: 'sffb',title: '是否发布',sortable:true,width:'80px'},
	            {field: 'sfzd',title: '是否置顶',sortable:true,width:'80px'}
         ],
         searchParams:function(){
        	var map =  $.search.getSearchMap();
         	return map;
         }
		};
	
		$('#tabGrid').loadGrid(options);

	/*====================================================绑定按钮事件====================================================*/
	
	
	//绑定增加点击事件
	jQuery("#btn_zj").click(function () {
		$.showDialog('zjXw.zf','增加', addConfig);
	});

	//修改
	jQuery("#btn_xg").click(function () {
		var ids = $('#tabGrid').getKeys();
		if(ids.length != 1){
			$.alert('请先选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").getRow(ids[0]);
		//业务判断
		if(row.sffb=='是'){
			$.alert('不能对已发布的记录进行修改！');
		}else{
			$.showDialog("xgXw.zf?xwbh="+row.xwbh,'修改',modifyConfig);
		}
	});

	jQuery("#btn_sc").click(function () {
		var selectedRows = $("#tabGrid").getRows();
		var deleteAble = true;
		$.each(selectedRows||[],function(i,row){
			if(row.sffb == 1){
				deleteAble = false;
			}
			return deleteAble;
		});
		if(deleteAble){
			$('#tabGrid').plcz('scXw.zf','删除');
		}else{
			$.alert("已发布的通知不允许删除!");
		}
	});
	
	jQuery("#btn_ck").click(function () {
		var ids = $('#tabGrid').getKeys();
		if(ids.length != 1){
			$.alert('请选择一条您要查看的记录!');
		}else{
			var row = jQuery("#tabGrid").getRow(ids[0]);
			window.open("ckXw.zf?xwbh="+row.xwbh);
		}
	});
	
	//发布
	jQuery("#btn_fb").click(function () {
		var selectedRows = $("#tabGrid").getRows();
		var fbAble = true;
		$.each(selectedRows||[],function(i,row){
			//console.log("row.sffb:" + row.sffb);
			if(row.sffb == 1){
				fbAble = false;
			}
			return fbAble;
		});
		if(fbAble){
			$('#tabGrid').plcz('fbXw.zf','发布');
		}else{
			$.alert("已发布的通知无需再次发布!");
		}
	});
	
	//取消发布
	jQuery("#btn_qxfb").click(function () {
		var selectedRows = $("#tabGrid").getRows();
		var cancelAble = true;
		$.each(selectedRows||[],function(i,row){
			if(row.sffb == 0){
				cancelAble = false;
			}
			return cancelAble;
		});
		if(cancelAble){
			$('#tabGrid').plcz('qxfbXw.zf','取消发布');
		}else{
			$.alert("未发布的通知无需取消发布!");
		}
	});
	
	//置顶
	jQuery("#btn_zd").click(function () {
		var selectedRows = $("#tabGrid").getRows();
		var zdAble = true;
		$.each(selectedRows||[],function(i,row){
			if(row.sfzd == 1){
				zdAble = false;
			}
			return zdAble;
		});
		if(zdAble){
			$('#tabGrid').plcz('zdXw.zf','置顶');
		}else{
			$.alert("已置顶的通知无需再次置顶!");
		}
	});
	
	//取消置顶
	jQuery("#btn_qxzd").click(function () {
		var selectedRows = $("#tabGrid").getRows();
		var zdAble = true;
		$.each(selectedRows||[],function(i,row){
			if(row.sfzd == 0){
				zdAble = false;
			}
			return zdAble;
		});
		if(zdAble){
			$('#tabGrid').plcz('qxzdXw.zf','取消置顶');
		}else{
			$.alert("未置顶的通知无需取消置顶!");
		}
		
	});
	
	$(":button[name=search_button]").bind("click",searchResult);
});


//查询
function searchResult(){
	$('#tabGrid').refreshGrid();
}

//回车键查询
//$('#searchBox input[name="xwbt"]').bind("keydown", "return", function (ev) {
//	searchResult()   
//})

function selectObj(obj){
	$("#groupId").val("");
	$("#_mc").val("");
	if(obj.value!='0'){
		$("#xzdx-div").show();
	}else{
		$("#xzdx-div").hide();
	}
}

