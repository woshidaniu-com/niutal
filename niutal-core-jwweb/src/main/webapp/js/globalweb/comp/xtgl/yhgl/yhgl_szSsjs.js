//初始化页面
jQuery(function($) {
	var ajaxForm = jQuery("#ajaxForm");
	var YhjsGrid  = $.extend({},BaseJqGrid,{  
		resizeHandle: ajaxForm,
		pager: "#YhjsGridPager", //分页工具栏  
		width:parseInt($("#ajaxForm").actual('innerWidth'))-5,
		rowNum : 100, // 每页显示记录数
		rowList : [100], // 可调整每页显示的记录数
	    url: 'yhgl_cxYhjs.html', //这是Action的请求地址  
	    gridview:false,
		treeGrid: true,//启用树型Grid功能
		treeGridModel: 'adjacency',//表示返回数据的读取类型，分为两种：和adjacency	
	    ExpandColClick: true,//设置为true，点击行时将会展开/收缩属性表格，而不仅限于点击图标
	    ExpandColumn: 'jsmc',//树型结构在哪列显示
	    multiselect : true, // 是否支持多选
	    autowidth : false, // 自动调整宽度
	    colModel:[
			{   label : '',
				name : '',
				index : '',
				sortable : false, 
				resizable : false, 
				width:'35px',
				formatter:function (cellvalue, options, rowObject){
					var res="";
					res = '<input type="checkbox" name="jsdm_id"  style="margin-left: 5px;" value="' + rowObject.jsdm + '" />';
					return res;
				}
			},
             {label:'职工号',name:'jgh', index: 'jgh',hidden:true, sortable : false},
		     {label:'角色代码',name:'jsdm', index: 'jsdm',key:true,hidden:true, sortable : false},
		     {label:'角色名称',name:'jsmc', index: 'jsmc',align:'left', width:'300px' ,sortable : false},
		     {label:'角色类型',name : 'jslxmc',index : 'jslxmc',align : 'center',width:'100px', sortable : false},
		     {label:'角色说明',name : 'jssm',index : 'jssm',align : 'left',width:'290px', sortable : false}
		],
		//treeIcons:{plus:'ui-icon-triangle-1-e',minus:'ui-icon-triangle-1-s',leaf:'ui-icon-radio-5'},
		treeReader : {
		    level_field: 'jsjb',
		    parent_id_field: 'fjsdm',
		    leaf_field: 'leaf',
		    expanded_field: 'expanded'
		},
		/*触发选中事件*/
		onSelectRow: function(rowid,status){
			var rowTr = $("#"+rowid);
			//获得当前行的checkbox
			var currentCheckbox = rowTr.find("input[name='jsdm_id']");
			var rowObj = jQuery("#YhjsGrid").jqGrid('getRowData',rowid);
			if(status || !$(currentCheckbox).prop("checked")){
				if($(currentCheckbox).prop("checked")){
					rowTr.removeClass('ui-state-highlight');
					ajaxForm.find('#cbvjsxx_'+rowObj.jsdm).remove();
					$(currentCheckbox).prop("checked",false);
				}else{
					//添加新的选中效果
					rowTr.addClass('ui-state-highlight');
					$(currentCheckbox).prop("checked",true);
					ajaxForm.append('<input type="hidden" name="cbvjsxx" id="cbvjsxx_'+rowObj.jsdm+'" value="'+rowObj.jsdm+'" />');
				}
				//防止点击新的选项原选中效果丢失
		   		$("#YhjsGrid").find("input[name='jsdm_id']:checked").each(function(){
		   			$(this).closest("tr").addClass('ui-state-highlight');
		   		});
			}else{
				$(currentCheckbox).prop("checked",false);
				rowTr.removeClass('ui-state-highlight');
				ajaxForm.find('#cbvjsxx_'+rowObj.jsdm).remove();
			}
			return false;
	   	},
	 	gridComplete:function(){
			var jsdm_arr = jQuery("input[name='cbvjsxx']");
			if(jsdm_arr.size()>0){
				var id_arr = jQuery("#YhjsGrid").getDataIDs();
				jQuery.each(id_arr,function(index,id){
					var row = jQuery("#YhjsGrid").jqGrid('getRowData', id);
					jQuery.each(jsdm_arr,function(index2,input){
						if(jQuery(input).val()==row.jsdm){
							$("#"+row.jsdm).addClass('ui-state-highlight').find("input[name='jsdm_id']").prop("checked", true);
						}
					});
				});
			}
			
			return true;
		}
	});

	/*委托点击事件：实现自动绑定*/
	$(document).on('click.widget.data-api', "input[name='jsdm_id']", function (event) {
		var jsdmInput = jQuery("#ajaxForm").find('#cbvjsxx_'+$(this).val());
		if($(this).prop("checked")){
			$(this).closest("tr").addClass('ui-state-highlight');
			//如果选中
			if(!$(jsdmInput).founded()){
				jQuery("#ajaxForm").append('<input type="hidden" name="cbvjsxx" id="cbvjsxx_'+$(this).val()+'" value="'+$(this).val()+'" />');
			}
		}else{
			jQuery(jsdmInput).remove();
			$(this).closest("tr").removeClass('ui-state-highlight');
		}
	});
	
	$("#YhjsGrid").loadJqGrid(YhjsGrid);
	
});
