$(function(){
	
	var options = {
			 url:_path+'/zxzx/yhbk/cxYhbkList.zf',
			 uniqueId: "bkdm",
			 toolbar:  '#but_ancd',
			 columns: [
				{checkbox: true }, 
	            {field: 'bkdm', title: 'bkdm',visible:false, hidden:true}, 
	            {field: 'bkmc', title: '版块',sortable:true}, 
	            {field: 'yhs',title: '用户数'}
	            ],
         searchParams:function(){
         	var map = {};
         	return map;
         	}
		};
	
		$('#tabGrid').loadGrid(options);
		
		/*
		 * 绑定操作按钮
		 */
		(function(){
			var btn_fpyh=$("#btn_fpyh");
			if(btn_fpyh!=null){
				btn_fpyh.click(function () {
					var ids = $("#tabGrid").getKeys();
					if (ids.length != 1) {
						$.alert('请选定一条记录!');
						return;
					}
					$.showDialog('fpBkyh.zf?bkdm='+ids[0],'分配用户', $.extend({},viewConfig,{modalName	: "fpyhModel", width: "1100px"}));
				});
			}
		})();
	
})

/*var YhbkGrid = Class.create(BaseJqGrid,{  
		caption : "已分配用户",
		pager: "pager", 
        url:_path+'/zxzx/yhbk_cxYhbk.html?doType=query',
        rowNum : 20,
        sortname: 'bkmc',
     	sortorder: "asc",
        colModel:[
        	 {label:'主键',name:'bkdm', index: 'bkdm',key:true,hidden:true},
        	 {label:'版块',name:'bkmc', index: 'bkmc',align:'center',width:300},
		     {
        		 label:'用户数',
        		 name:'yhs', 
        		 width:100, 
        		 index: 'yhs',
        		 align:'center'
        	 }
		],
		viewrecords: true,
		multiselect: false,
		subGrid: true,
		subGridRowExpanded:function(subgrid_id, row_id){
			var subgrid_table_id, pager_id;
			subgrid_table_id = subgrid_id+"_t";
			pager_id = "p_"+subgrid_table_id;
			jQuery("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
			jQuery("#"+subgrid_table_id).jqGrid({
				url:_path+'/zxzx/yhbk_cxBkyhxxAjax.html?bkdm='+row_id,
				datatype: "json",
				mtype:"POST",
				autoencode:true,
				jsonReader: {      
					root: "items",
					page: "currentPage",
					total: "totalPage",
					records: "totalResult",    
					repeatitems : false      
				},
				prmNames : {
					page : "queryModel.currentPage",
					rows : "queryModel.showCount",
					order : "queryModel.sortOrder",
					sort : "queryModel.sortName"
				},
				colModel: [
					{label:"用户名",name:"zgh",index:"zgh",width:100,key:true},
					{label:"姓名",name:"xm",index:"xm",width:150},
					{label:"联系电话",name:"lxdh",index:"lxdh",width:150},
					{label:"所属部门",name:"jgmc",index:"jgmc",width:200},
				],
			   	rowNum:20,
			   	pager: pager_id,
			   	sortname: 'xm',
			    sortorder: "asc",
			    height: '100%'
			});
			jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false,search:false})
		},
		subGridRowColapsed:function(subgrid_id, row_id){
		}
	});*/

