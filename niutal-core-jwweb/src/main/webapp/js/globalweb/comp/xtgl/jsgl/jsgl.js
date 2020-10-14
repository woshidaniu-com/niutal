jQuery(function($){
	
	var TempGrid = $.extend(true,{},BaseJqGrid,{  
		resizeHandle:"#searchResult",
		pager : "#pager", // 分页工具栏
		height : "auto",
		rowNum : 5000, // 每页显示记录数
		rowList : [5000], // 可调整每页显示的记录数
		//rowList : [15, 30, 50, 100], // 可调整每页显示的记录数			
		url : _path + '/xtgl/jsgl_cxJsxx.html?doType=query', // 这是Action的请求地址
		gridview:false,
		treeGrid: true,//启用树型Grid功能
		treeGridModel: 'adjacency',//表示返回数据的读取类型，分为两种：和adjacency	
	    ExpandColClick: true,//设置为true，点击行时将会展开/收缩属性表格，而不仅限于点击图标
	    ExpandColumn: 'jsmc',//树型结构在哪列显示
	    multiselect : true, // 是否支持多选
	    autowidth : false, // 自动调整宽度
		colModel : [ 
			{   label : '',
				name : '',
				index : '',
				sortable : false, 
				resizable : false, 
				width:'35px',
				formatter:function (cellvalue, options, rowObject){
					var res="";
					res = '<input type="radio" name="jsdm_id"  style="margin-left: 5px;" value="' + rowObject.jsdm + '" />';
					return res;
				}
			},
			{label:'角色代码',name : 'jsdm', index : 'jsdm', key : true ,  hidden : true ,sortable : false},
			{label:'是否二级授权',name : 'sfejsq', index : 'sfejsq',  hidden : true ,sortable : false},
			{label:'角色名称',name : 'jsmc',index : 'jsmc',align:'left',width:'300px' , sortable : false, 
				formatter:function (cellvalue, options, rowObject){
					var html = [cellvalue];
					//不能进行二级功能授权
					if(rowObject.sfejsq=='1'){
						html.push("&nbsp;<em class='red2 bolder'>[二级授权]</em>");
					}
					return html.join("");
			}},
			{label:'角色级别',name : 'jsjb',index : 'jsjb',align : 'center',width:'100px' , resizeable:false,sortable : false},
			{label:'角色类型',name : 'jslxmc',index : 'jslxmc',align : 'center',width:'100px' , resizeable:false,sortable : false},
			{label:'用户数',name : 'yhnum',index : 'yhnum',align : 'center',width:'100px' , resizeable:false,sortable : false}, 
			{label:'系统默认',name : 'sfmrjs',index : 'sfmrjs',align : 'center',width:'100px' , resizeable:false,sortable : false,
				formatter:function (cellvalue, options, rowObject){
					return (rowObject.sfksc!=1)?'<span class="label label-primary">是</span>':'<span class="label label-default">否</span>';
				}
			}, 
			{label:'sfksc',name : 'sfksc',index : 'sfksc',hidden : true},
			{label:'是否可删除',name : 'sfksc_text', index : 'sfksc_text',align : 'center',sortable : false  ,resizeable:false, 
				formatter:function (cellvalue, options, rowObject){
					return (rowObject.sfksc==1)?'<span class="label label-primary">是</span>':'<span class="label label-default">否</span>';
				}
			},
			{label:'私有角色',name : 'jsgybj_text', index : 'jsgybj_text',align : 'center',sortable : false  ,resizeable:false, formatter:function (cellvalue, options, rowObject){
				return (rowObject.jsgybj!=1)?'<span class="label label-primary">是</span>':'<span class="label label-default">否</span>';
			}},
			{label:'角色说明',name : 'jssm',index : 'jssm',align : 'left',width:'360px' , sortable : false}
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
			//取消其他选中效果
			$("input[name='jsdm_id']").prop("checked", false);
			//得到当前行input
			var input = $("#"+rowid).find("input[name='jsdm_id']");
			//添加高亮样式
			$(this).closest("tr").addClass('ui-state-highlight');
			//设置选中
			$(input).prop("checked", true);
			//判断角色代码，级联按钮状态
			if($(input).val() == 'xs'){
				$("#btn_fpyh").disabled();
			}else{
				$("#btn_fpyh").enabled();
			}
			return false;
	   	},
	   	loadComplete:function(){
	   		$("#btn_fpyh").enabled();
	   		return true;
	   	},
	   	gridComplete:function(){
	   		return true;
	   	}
	});
	$("#tabGrid").loadJqGrid(TempGrid);
	
	/*委托点击事件：实现自动绑定*/
	$(document).on('click.widget.data-api', "input[name='jsdm_id']", function (event) {
		//重置选中状态
		$("#tabGrid").resetSelection();
		//设置当前input选中
		$(this).prop("checked",true);
		//设置高亮样式
		$(this).closest("tr").addClass('ui-state-highlight');
		//选中当前行
		$("#tabGrid").setSelection($(this).val());
		//判断角色代码，级联按钮状态
		if($(this).val() == 'xs'){
			$("#btn_fpyh").disabled();
		}else{
			$("#btn_fpyh").enabled();
		}
		//阻止冒泡
		//event.stopPropagation();
	}).on('change.widget.data-api', "input[name='jsdm_id']", function (event) {
		if($(this).prop("checked")){
			$(this).closest("tr").addClass('ui-state-highlight');
		}
	});
	
	/*====================================================绑定按钮事件====================================================*/
	

	//重写公用getCheckedJsdm方法，取得选中的checkbox的value,注意这里的值必须是jqgrid组件每行的id
	function getCheckedJsdm(){
		var ids = [];
		jQuery("input[name='jsdm_id']:checked").each(function(){
			ids.push(jQuery(this).val());
		});
		return ids;
	}
	
	// 绑定增加点击事件
	jQuery("#btn_zj").click(function() {
		$.showDialog('jsgl_zjJsxx.html','增加角色',$.mergeObj(addConfig,{"width":"700px"}));
        return false;
	});
	
	// 绑定删除事件
	jQuery("#btn_sc").click(function() {
		var ids = getCheckedJsdm();
		if (ids.length == 0) {
			$.alert('请选择您要删除的记录！');
			return false;
		}
		var canDelete = true;
		$.each(ids,function(index,id){
			var row = jQuery("#tabGrid").jqGrid('getRowData', id);
			if(row["sfksc"]=='0'){
				$.alert("角色"+row["jsmc"]+"不可删除角色，请重新选择！");
				canDelete = false;
			}else if(parseInt(row["yhnum"]||"0") > 0){
				$.alert("角色"+row["jsmc"]+"已有用户不可删除，请重新选择！");
				canDelete = false;
			}
			return canDelete;
		});
		if(canDelete){
			$.confirm('您确定要删除选择的记录吗？',function(result) {
				if(result){
					jQuery.ajaxSetup( {
						async : false
					});
					jQuery.post(_path + '/xtgl/jsgl_scJsxx.html', {
						"ids" : ids.join(",")
					}, function(responseText) {
						if(responseText.indexOf("成功") > -1){
							$.success(responseText,function() {
								if($("#tabGrid").size() > 0){
									refershGrid("tabGrid");
								}
							});
						}else if(responseText.indexOf("失败") > -1){
							$.error(responseText);
						} else{
							$.alert(responseText);
						}
					}, 'json');
					jQuery.ajaxSetup( {
						async : true
					});
				}
			});
		}
	});
	
	// 绑定修改事件
	jQuery("#btn_xg").click(function() {
		var ids = getCheckedJsdm();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		var row = jQuery("#tabGrid").jqGrid('getRowData', ids[0]);
		$.showDialog(_path + '/xtgl/jsgl_xgJsxx.html?jsdm=' + ids[0],'修改角色',$.mergeObj(modifyConfig,{
			"width":"700px",
			"afterRender":function(){
				//var yhnum = $("#ajaxForm").find("input[name=yhnum]").val();
				//if(yhnum&&yhnum>0){
					//this.button({
	                //    id:'success',
	                //    disabled: true
	                //});
				//}
			}
		}));
		return false;
	});
	
	// 功能授权
	jQuery("#btn_gnsq").click(function() {
		var ids = getCheckedJsdm();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		
		if(ids[0]=='xs'||ids[0]=='js'){
			$.showDialog(_path + '/xtgl/jsgnmk_cxJsmkIndex.html?jsdm='+ids[0] + "&gnmkdm=",'功能授权',$.mergeObj(modifyConfig,{
				width: $("#yhgnPage").innerWidth()+"px",
				//是否点击按钮时，锁定按钮点击状态 
			    "btnlock" 	: true,
				buttons:{
					success : {
						label : "授	权",
						callback : function() {
							var $this = this;
							var requestMap = {
								"jsdm" : ids[0]
							};
							var index = 0;
							$("#container_fluid").find("li.ejgndm_li").each(function(i,li_pane){
								//所有的功能操作代码
								var gnczdms = $(li_pane).find("input:checked");
								//判断当前组是否有选择的操作按钮
								if( gnczdms.size() > 0 ){
									//当前功能操作组的功能模块代码
									requestMap["childList["+index+"].gnmkdm"] = $(li_pane).data("gnmkdm");
									//已选操作按钮
									gnczdms.each(function(input_index,input){ 
										requestMap["childList["+index+"].gnmkdm_list["+input_index+"]"] = $(input).val();
						 			});
									index ++;
								}
							});
							 
							
							//提交请求
							jQuery.post( _path + "/xtgl/jsgnmk_jsgnsqUpdate.html", requestMap, function(responseText){
								$this.reset();
								if(responseText.indexOf("成功") > -1){
									$.success(responseText,function() {
										$.closeModal("modifyModal");
									});
								}else if(responseText.indexOf("失败") > -1){
									$.error(responseText,function() {
										
									});
								} else{
									$.alert(responseText,function() {
										
									});
								}
							}, "json");
							return false;
						}
					}
				}
			}));
		}else{
			$.showDialog(_path + '/xtgl/jsgnmk_cxJsgnsqIndex.html?jsdm='+ids[0] + "&gnmkdm=",'功能授权',$.mergeObj(modifyConfig,{
				width: $("#yhgnPage").innerWidth()+"px",
				//是否需要进度条
				"progress"	: true,
				//是否点击按钮时，锁定按钮点击状态 
			    "btnlock" 	: true,
				buttons:{
					success : {
						label : "授	权",
						callback : function() {
							var $this = this;
							bcJsgnsqxx(function(responseText){
								$this.reset();
								if(responseText.indexOf("成功") > -1){
									$.success(responseText,function() {
										$.closeModal("modifyModal");
										refershGrid("tabGrid");
									});
								}else if(responseText.indexOf("失败") > -1){
									$.error(responseText,function() {
										
									});
								} else{
									$.alert(responseText,function() {
										
									});
								}
							});
							return false;
						}
					}
				}
			}));
		}
		return false;
	});
	
	// 角色分配用户
	jQuery("#btn_fpyh").click(function() {
		var ids = getCheckedJsdm();
		if (ids.length != 1) {
			$.alert('请选定一条记录!');
			return;
		}
		$.showDialog(_path + '/xtgl/jsgl_cxJsyhfpIndex.html?jsdm='+ids[0],'角色分配用户',{
			width: $("#yhgnPage").innerWidth()+"px",
			modalName: "viewModal",
			buttons:{
				cancel : {
					label : "关 闭",
					className : "btn-default",
					callback : function() {
						searchResult();
					}
				}
			}
		});
		return false;
	});
	
	//增加子角色
	jQuery("#btn_xzzjs").click(function() {
		var ids = getCheckedJsdm();
		if (ids.length != 1 || !$.founded(ids[0])) {
			$.alert('请选定一条记录!');
			return;
		}
		$.showDialog(_path + '/xtgl/jsgl_xzzjsJsxx.html?fjsdm='+ids[0],'增加子角色',$.mergeObj(addConfig,{"width":"650px"}));
        return false;
	});
	
	// 查询
	jQuery("#search_go").click(function() {
		var map = {};
			map["sffpyh"] = jQuery('#sffpyh').val();
			map["jsmc"]   = jQuery('#jsmc').val();
			map["gnmkdm"] = jQuery('#gnmkdm').val();
		search('tabGrid', map);
        return false;
	});
	
});