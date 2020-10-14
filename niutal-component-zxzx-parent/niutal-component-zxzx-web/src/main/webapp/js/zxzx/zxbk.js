var lastSel;
var editparameters = {
		"keys" : true,
		"oneditfunc" : null,
		"successfunc" : null,
		"url" : _path+"/zxzx/kzdk_xgBckzdk.html",
	    "extraparam" : {},
		"aftersavefunc" : function(){
			alert("保存成功！");
		},
		"errorfunc": function(){
			alert("保存失败！");
		},
		"afterrestorefunc" : null,
		"restoreAfterError" : true,
		"mtype" : "POST"
	};

var kzdkGrid = Class.create(BaseJqGrid,{  
		caption : "咨询板块列表",
		pager: "pager", 
        url:_path+'/zxzx/kzdk_cxkzdk.html?doType=query',
        rowNum : 50,
        onSelectRow: function(id){
            if(id && id!==lastSel){ 
               jQuery('#tabGrid').restoreRow(lastSel); 
               lastSel=id; 
            }
            jQuery('#tabGrid').jqGrid('editRow',id,  editparameters);
          },
        colModel:[
        	 {label:'主键',name:'bkdm', index: 'bkdm',key:true,hidden:true},
        	 {label:'板块名称',name:'bkmc', index: 'bkmc',align:'center'},
		     {
        		 label:'是否启用',
        		 name:'sfqy', 
        		 width:100, 
        		 index: 'sfqy',
        		 align:'center',
        		 editable:false,
        		 edittype:'select', 
        		 formatter:'select', 
        		 editoptions:{value:"1:启用;0:关闭"}
        	 },
		     {
		    	 label:'显示顺序',
		    	 name:'xsxs', 
		    	 index: 'xsxs',
		    	 width:100,
		    	 align:'center'
		     }
		],
		sortname: 'xsxs',
     	sortorder: "asc"
	});

//查询
function searchResult(){
	var map = {};
	map["bkmc"] = jQuery('#bkmc').val();
	map["sfqy"] = jQuery('#sfqy').val();
	search('tabGrid',map);
}

/*
 * 绑定操作按钮
 */
function bdan(){
	var btnzj=jQuery("#btn_zj");
	var btnsc=jQuery("#btn_sc");
	var btnxg=jQuery("#btn_xg");
	var btntz=jQuery("#btn_tz");
	var btnqyty=jQuery("#btn_qyty");
	//绑定增加点击事件
	if(btnzj!=null){
		btnzj.click(function () {
			showWindow('增加咨询板块',500,250,_path+'/zxzx/kzdk_zjkzdk.html');
		});
	}
	
	//绑定删除事件
	if(btnsc!=null){
		btnsc.click(function () {
			var url =_path+'/zxzx/kzdk_scBckzdk.html';
			var ids = getChecked();
			if (ids.length != 0){
				alert('请选择一条要删除的记录！');
			} else {
				var _do = function(){
					jQuery.ajaxSetup({async:false});
					jQuery.post(url,{bkdm:ids.toString()},function(data){
						setTimeout(function(){
							refershGrid('tabGrid')
							alertInfo(data);
						},1);
						
					},'json');
					jQuery.ajaxSetup({async:true});
				}
				showConfirmDivLayer('您确定要删除选择的记录吗？',{'okFun':_do})
			}
		});
	}

	//绑定修改事件
	if(btnxg!=null){
		btnxg.click(function () {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		var url=_path + "/zxzx/kzdk_xgkzdk.html?bkdm="+id; 
		showWindow('修改咨询板块',500,250,url);
		});
	}
	
	//绑定启用停用事件
	if(btnqyty!=null){
		btnqyty.click(function () {
		var id = getChecked();
		if(id.length != 1){
			alert('请选定一条记录!');
			return;
		}
		var url=_path + "/zxzx/kzdk_qytykzdk.html?bkdm="+id; 
		showWindow('启用/停用 咨询板块',500,250,url);
		});
	}
	
	if(btntz!=null){
		btntz.click(function () {
			showWindow('显示顺序设置',668,505,_path+'/zxzx/kzdk_sxszkzdk.html');
		});
	}
}