var parentPage;
var KzxgGrid;

jQuery(function() {
	parentPage = getParentDialogWin();
	var kzlx = parentPage.jQuery("#kzlx").val();
	var xmdm = parentPage.jQuery("#xmdm").val();
	KzxgGrid = Class.create(BaseJqGrid,{  
		caption : "快照设置",
		pager: "pager", //分页工具栏  
		height : "auto",
	    postData: {
			"model.xmdm":xmdm,
			"model.kzlx":kzlx
		},
		rowNum : 10, // 每页显示记录数
	    url: _path+'/zfxg/tjcx/kzsz/kzcx.zf', //这是Action的请求地址  
	    colModel:[
	    	 {label:'主键id',name:'kzszid', index: 'kzszid',key:true,hidden:true,editable:true},
	    	 {label:'项目代码',name:'xmdm', index: 'xmdm',hidden:true,editable:true},
	    	 {label:'快照类型',name:'kzlx', index: 'kzlx',hidden:true},
		     {label:'名称',name:'szmc', index: 'szmc', editable:true,editoptions:{size:30,maxlength:20},
	             editrules:{required:true},edittype:'text'},
		     {label:'是否共享',name:'sfgy', index: 'sfgy',formatter:viewSfgy,editable:true,edittype:'select',editoptions:{value:"0:私有;1:共享"}},
		     {label:'快照接收人',name:'kzjsr', index: 'kzjsr',editable:true,edittype:'textarea',editoptions:{rows: "8", cols: "25"}}
		],
		sortname: 'szmc', //首次加载要进行排序的字段 
	 	sortorder: "",
	 	viewrecords: true,
	 	editurl: _path+'/zfxg/tjcx/kzsz/operation.zf'
	});
	
	var kzxgGrid = new KzxgGrid();
	setTimeout(function(){
		jQuery("#tabGrid").jqGrid(kzxgGrid).navGrid("#pager", {
			del : false,
			add : false,
			edit : false,
			search : false,
			refresh : true
		});
		bdan();
	},100);
});


function viewSfgy(cellvalue, options, rowObject){
	var viewSfgy = "私有";
	if(cellvalue == "1"){
		viewSfgy = "共享";
	}
	return  viewSfgy;
}

/*
 * 绑定操作按钮
 */
function bdan(){
	var btn_xg=jQuery("#btn_xg");//按钮ID  命名规则  btn_xx（xx:操作）
	var btn_sc = jQuery("#btn_sc");
	//绑定全部初始化事件
	if(btn_xg!=null){
		btn_xg.click(function () {
			var ids = getChecked();
			if(ids.length != 1){
				alert('请先选定一条记录!');
				return;
			}
			var id = getChecked();
			var row = jQuery("#tabGrid").jqGrid('getRowData', id);
			var url = _path+'/zfxg/tjcx/kzsz/operation.zf'
			var url=_path+"/zfxg/tjcx/kzsz/kzszUpdate.zf?kzszid=" + row['kzszid']; 
			showWindow('修改快照',400,250,url);
	       // jQuery("#tabGrid").jqGrid('editGridRow',ids[0],{width:400,height:280,reloadAfterSubmit:true,closeOnEscape:true,closeAfterEdit:true});
		});
	}
	if(btn_sc!=null){
		btn_sc.click(function () {
			var ids = getChecked();

			if (ids.length == 0){
				alert('请选择您要删除的记录！');
			} else {
				var url = _path+"/zfxg/tjcx/kzsz/kzszDelete.zf";
				var _do = function(){
					jQuery.ajaxSetup({async:false});
					jQuery.post(url,{ids:ids.toString()},function(data){
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
}
