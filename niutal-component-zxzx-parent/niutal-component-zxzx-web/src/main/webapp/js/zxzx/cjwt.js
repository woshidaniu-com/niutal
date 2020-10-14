var CjwtGrid = Class.create(BaseJqGrid,{  
		caption : "常见问题列表",
		pager: "pager", 
        url:_path+'/zxzx/cjwt_cxCjwt.html?doType=query',
        colModel:[
        	 {label:'主键',name:'wtid', index: 'wtid',key:true,hidden:true},
        	 {label:'咨询主题',name:'wtbt', index: 'wtbt',align:'center'},
        	 {label:'咨询版块',name:'bkmc', index: 'bkmc',align:'center'},
		     {
        		 label:'启用状态',
        		 name:'sffb', 
        		 width:100, 
        		 index: 'sffb',
        		 align:'center',
        		 formatter:'select', 
        		 editoptions:{value:"1:启用;0:关闭"}
        	 },
		     {
		    	 label:'创建时间',
		    	 name:'cjsj', 
		    	 index: 'cjsj',
		    	 align:'center'
		     },
		     {
		    	 label:'数据来源',
		    	 name:'zxid', 
		    	 index: 'zxid',
		    	 align:'center',
		    	 formatter:sjlyFun
		     }
		],
		sortname: 'sffb',
     	sortorder: "desc"
	});

function sjlyFun(cellVal){
	if(cellVal != null || cellVal != undefined || jQuery.trim(cellVal) != ""){
		return "咨询"
	}else{
		return "";
	}
}

//查询
function searchResult(){
	var map = {};
	map["wtbt"] = jQuery('#wtbt').val();
	map["bkdm"] = jQuery('#bkdm').val();
	map["sffb"] = jQuery('#sffb').val();
	search('tabGrid',map);
}

/*
 * 绑定操作按钮
 */
function bdan(){
	var btnzj=jQuery("#btn_zj");
	var btnsc=jQuery("#btn_sc");
	var btnxg=jQuery("#btn_xg");
	var btnqyty=jQuery("#btn_qyty");
	//绑定增加点击事件
	if(btnzj!=null){
		btnzj.click(function () {
			showWindow('增加常见问题',800,440,_path+'/zxzx/cjwt_zjCjwt.html');
		});
	}
	
	//绑定删除事件
	if(btnsc!=null){
		btnsc.click(function () {
			var url =_path+'/zxzx/cjwt_scBcCjwt.html';
			var ids = getChecked();
			if (ids.length == 0){
				alert('请选择您要删除的记录！');
			} else {
				var _do = function(){
					jQuery.ajaxSetup({async:false});
					jQuery.post(url,{wtid:ids.toString()},function(data){
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
		var url=_path + "/zxzx/cjwt_xgCjwt.html?wtid="+id; 
		showWindow('修改常见问题',800,440,url);
		});
	}
	
	if(btnqyty!=null){
		btnqyty.click(function () {
			var id = getChecked();
			if(id.length != 1){
				alert('请选定一条记录!');
				return;
			}
			showWindow('常见问题【启用/停用】',500,250,_path+'/zxzx/cjwt_qytyCjwt.html?wtid='+id);
		});
	}
}