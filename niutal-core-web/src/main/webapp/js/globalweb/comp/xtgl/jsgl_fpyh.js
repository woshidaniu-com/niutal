//未分配用户列表
function wfp(rowId,states){
	var zgh=jQuery("#tabGrid").jqGrid("getCell", rowId,"zgh"); 
	var arrIds = jQuery("#yfpTabGrid").jqGrid('getDataIDs');
	var yfp_zgh = "";var flag = false;
	jQuery.ajaxSetup({async:false});
	jQuery.each(arrIds,function(i,n) {
		yfp_zgh = jQuery("#yfpTabGrid").jqGrid("getCell", n,"zgh");
		if(zgh==yfp_zgh){
			alert('该记录已存在！');
			flag = true;
		}
	});
	jQuery.ajaxSetup({async:true});
    if(!flag){
        jQuery.getJSON( _path+"/xtgl/jsgl_fpyhCxYhxx.html",{"zgh":zgh},function(data){
        	var arrIds = jQuery("#yfpTabGrid").jqGrid('getDataIDs');
            var i = arrIds.length + 1; 
            jQuery('#yfpTabGrid').jqGrid('addRowData', i, data);
        });
    }
}

//已分配用户列表查询
function searchYfpResult(){
	var map = {};
	map["zgh"] = jQuery('#zgh').val();
	map["xm"] = jQuery('#xm').val();
	map["jgdm"] = jQuery('#jgdm').val();
	search('yfpTabGrid',map);
}

//未分配用户列表查询
function searchWfpResult(){
	var map = {};
	map["zgh"] = jQuery('#zgh').val();
	map["xm"] = jQuery('#xm').val();
	map["jgdm"] = jQuery('#jgdm').val();
	search('wfpGrid',map);
}


//重新加载页面
function loadJsyhxx(jsdm){
	refRightContent('jsgl_fpyhJs.html?jsdm='+jsdm);
}

//角色用户授权
function jsyhSq(){
	var url = _path+'/xtgl/jsgl_jsyhSq.html?jsdm='+jQuery('input[name="jsdm"]').val();
	showWindow('角色用户授权',740,520,url);
}


//角色用户授权提交操作
function jsyhSqSubmit(){
	var serverData, url=_path+'/xtgl/jsgl_fpyhSaveJs.html?jsdm='+jQuery('input[name="jsdm"]').val(),
	ids = jQuery("#wfpGrid").jqGrid('getGridParam', 'selarrrow'),
	_submitlFn;
	if (ids.length  == 0) {
		alert('请选择要授权的用户！');
		return;
	}else{
		serverData = {'yhCbv': ids.join(',')};
		_submitlFn = function(){
			jQuery.post(url,serverData,function(data){
				//刷新未分配用户列表
				refershGrid('wfpGrid');
				//刷新已分配用户列表
				if(frameElement && frameElement.api){
					var api = frameElement.api,W = api.opener;
					W.jQuery("#yfpTabGrid").jqGrid().trigger('reloadGrid');
				}
				alert(data);
			},"json");
		};
		alertConfirm("确认提交?", _submitlFn);
		return;
	}
}

//撤销角色用户
function cancelJsyhSq(){
	var serverData, url=" _path+'/xtgl/jsgl_fpyhCancelJs.html?jsdm="+jQuery('input[name="jsdm"]').val(),
	ids = jQuery("#yfpTabGrid").jqGrid('getGridParam', 'selarrrow'),
	_cancelFn;
	if (ids.length  == 0) {
		alert('请选择要撤销的用户！');
		return;
	}else{
		serverData = {'yhCbv': ids.join(',')};
		_cancelFn = function(){
			jQuery.post(url,serverData,function(data){
				//刷新未分配用户列表
				refershGrid('yfpTabGrid');
				alert(data);
			},"json");
		};
		alertConfirm("确认提交?", _cancelFn);
		return;
	}
}

//已分配用户列表
function yfp(rowId,states){
	var zgh=jQuery("#yfpTabGrid").jqGrid("getCell", rowId,"zgh"); 
	jQuery("#yfpTabGrid").jqGrid("delRowData", rowId); 
}

var YfpGrid = Class.create(BaseJqGrid,{  
	caption : "已授权用户",
	rowNum : 30, // 每页显示记录数
	multiselect : true, // 是否支持多选
	pgbuttons:true,//是否显示用于翻页的按钮
	pginput:true,
	rowList : [30, 50, 100], // 可调整每页显示的记录数	
    pager: "pagerYfp", //分页工具栏  
    url: _path+'/xtgl/jsgl_fpyhYfpYhxx.html?jsdm='+jQuery('input[name="jsdm"]').val(), //这是Action的请求地址  
    colModel:[
    	 {label:'用户名',name:'zgh', index: 'zgh',key:true,align:'center'},
	     {label:'姓 名',name:'xm', index: 'xm',align:'center'},
	     {label:'联系电话',name:'lxdh', index: 'lxdh',align:'center'},
    	 {label:'所属机构',name:'jgmc', index: 'jgmc',align:'center'}
	],
	sortname: 'zgh', //首次加载要进行排序的字段 
 	sortorder: "desc"
});