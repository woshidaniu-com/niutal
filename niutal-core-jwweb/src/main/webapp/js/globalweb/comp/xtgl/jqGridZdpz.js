var api = window.api;

function saveZdpz(callBackFunc){
	var arrayObj = {};
	jQuery.each(jQuery("#zdpzTabGrid").getDataIDs()||[],function(index,zd_index){
		jQuery.each(api.data.params||{},function(key,value){
			arrayObj["list["+index+"]."+key] = value;
		});
		var colModel = jQuery('#zdpzTabGrid').jqGrid('getRowData',zd_index);
		jQuery.each(colModel||{},function(key,value){
			if(key.indexOf("_str")<=0){
				arrayObj["list["+index+"]."+key] = value;
			}
		});
	});
	
	jQuery.ajax({
		dataType :"json",
		type : "POST",
		url : _path+"/xtgl/zdpz_xgZdpz.html",
		data : arrayObj,
		async : false,
		timeout : 6E4,
		contentType : "application/x-www-form-urlencoded;charset=UTF-8",
		success : function(message) {
			$.alert(message,function(){
				callBackFunc.call(arrayObj);
			}); 
		}
	});
}

jQuery(function($){

	$(document).off("change","select.zdpz").on("change","select.zdpz",function(){
		var id = $(this).attr("zd_index");
		$('#zdpzTabGrid').setCell(id,"zd_align",$(this).val());
	});	
	
	function bindEvent(){
		
		$.each($("#zdpzTabGrid").getDataIDs()||[],function(i,id){
			
			//是否可见
			$(":radio[name=zd_hidden_"+id+"]").unbind().change(function(){
				var zd_index = $(this).attr("zd_index");
				$('#zdpzTabGrid').setCell(zd_index,"zd_hidden",$(this).val());
			});

			//是否可改宽度
			$(":radio[name=zd_resizable_"+id+"]").unbind().change(function(){
				var zd_index = $(this).attr("zd_index");
				$('#zdpzTabGrid').setCell(zd_index,"zd_resizable",$(this).val());
			});

			//是否可排序
			$(":radio[name=zd_sortable_"+id+"]").unbind().change(function(){
				var zd_index = $(this).attr("zd_index");
				$('#zdpzTabGrid').setCell(zd_index,"zd_sortable",$(this).val());
			});

			//置顶
			$("span.ui-icon-top").each(function(index,top){
				$(top).unbind().click(function(){
					var zd_index = $(this).attr("zd_index");
					var currentRow = jQuery('#zdpzTabGrid').jqGrid('getRowData',zd_index);
					var current_zd_number = parseInt(currentRow.zd_number);
					var arrayObj = jQuery('#zdpzTabGrid').jqGrid('getRowData');
					var arrayNew = [];
					//当前行索引为0
					currentRow.zd_number = 0;
					arrayNew.push(currentRow);
					$.each(arrayObj,function(index,colModel){
						var zd_number = parseInt(colModel.zd_number);
						if(zd_index != colModel.zd_index  && zd_number < current_zd_number){
							colModel.zd_number = zd_number + 1;
						}
						if(zd_index != colModel.zd_index ){
							arrayNew.push(colModel);
						}
					});
					$('#zdpzTabGrid').refershLocalGrid(arrayNew);
				});
			});

			//上移
			$("span.ui-icon-up").each(function(index,up){
				$(up).unbind().click(function(){
					var zd_index = $(this).attr("zd_index");
					
					var currentRow = jQuery('#zdpzTabGrid').jqGrid('getRowData',zd_index);
					var current_zd_number = parseInt(currentRow.zd_number);
					if(current_zd_number>0){
						var arrayNew = [];
						//先将非[当前行和当前行的前一行]的数据放到数组
						var prvArray = [];
						var nextArray = [];
						var prv_zd_index = null;
						var arrayObj = jQuery('#zdpzTabGrid').jqGrid('getRowData');
						//先将非[当前行和当前行的下一行]的数据放到数组
						$.each(arrayObj,function(i,colModel){
							var zd_number = parseInt(colModel.zd_number);
							//当前记录之前数据
							if(zd_number < (current_zd_number-1)){
								prvArray.push(colModel);
							}
							//当前数据下一行之后数据
							if(zd_number > current_zd_number){
								nextArray.push(colModel);
							}

							if((current_zd_number-1) == zd_number){
								prv_zd_index =  colModel.zd_index;
							}
						});
						
						var arrayNew = [];
						//添加当前行之前数据
						$.each(prvArray,function(i,prvItem){
							arrayNew.push(prvItem);
						});
						//添加当前行与前一行数据zd_number对换
						var prvRow = jQuery('#zdpzTabGrid').jqGrid('getRowData',prv_zd_index);
						prvRow.zd_number = current_zd_number;
						currentRow.zd_number = current_zd_number - 1 ;
						arrayNew.push(currentRow);
						arrayNew.push(prvRow);
						//添加当前行之后数据
						$.each(nextArray,function(i,nextItem){
							arrayNew.push(nextItem);
						});
						$('#zdpzTabGrid').refershLocalGrid(arrayNew);
						
					}
				});
			});
			
			//下移
			$("span.ui-icon-down").each(function(index,top){
				$(top).unbind().click(function(){
					
					var zd_index = $(this).attr("zd_index");
					var currentRow = jQuery('#zdpzTabGrid').jqGrid('getRowData',zd_index);
					var current_zd_number = parseInt(currentRow.zd_number);
					
					var arrayObj = jQuery('#zdpzTabGrid').jqGrid('getRowData');
					if(current_zd_number<arrayObj.length){
						var prvArray = [];
						var nextArray = [];
						var next_zd_index = null;
						//先将非[当前行和当前行的下一行]的数据放到数组
						$.each(arrayObj,function(i,colModel){
							var zd_number = parseInt(colModel.zd_number);
							//当前记录之前数据
							if(zd_number < current_zd_number){
								prvArray.push(colModel);
							}
							//当前数据下一行之后数据
							if(zd_number > (current_zd_number+1)){
								nextArray.push(colModel);
							}

							if((current_zd_number+1) == zd_number){
								next_zd_index =  colModel.zd_index;
							}
						});
						var arrayNew = [];
						//添加当前行之前数据
						$.each(prvArray,function(i,prvItem){
							arrayNew.push(prvItem);
						});
						
						var nextRow = jQuery('#zdpzTabGrid').jqGrid('getRowData',next_zd_index);
						//添加当前行与下一行数据zd_number对换
						nextRow.zd_number = current_zd_number;
						currentRow.zd_number = current_zd_number + 1 ;
						arrayNew.push(nextRow);
						arrayNew.push(currentRow);
						//添加当前行之后数据
						$.each(nextArray,function(i,nextItem){
							arrayNew.push(nextItem);
						});
						
						$('#zdpzTabGrid').refershLocalGrid(arrayNew);
					}
				});
			});
			
			//置底
			$("span.ui-icon-root").each(function(index,top){
				$(top).unbind().click(function(){
					var zd_index = $(this).attr("zd_index");
					var currentRow = jQuery('#zdpzTabGrid').jqGrid('getRowData',zd_index);
					var current_zd_number = parseInt(currentRow.zd_number);
					var arrayObj = jQuery('#zdpzTabGrid').jqGrid('getRowData');
					var arrayNew = [];
					$.each(arrayObj,function(index,colModel){
						var zd_number = parseInt(colModel.zd_number);
						if( zd_index != colModel.zd_index && zd_number > current_zd_number){
							colModel.zd_number = zd_number - 1;
						}
						if(zd_index != colModel.zd_index ){
							arrayNew.push(colModel);
						}
					});
					currentRow.zd_number = arrayObj.length - 1;
					arrayNew.push(currentRow);
					$('#zdpzTabGrid').refershLocalGrid(arrayNew);
				});
			});
		});

		var firstTr = $("#zdpzTabGrid").find("tbody tr:eq(1)");
		$(firstTr).find("span.ui-icon-top").unbind().addClass("ui-icon-top-disable");
		$(firstTr).find("span.ui-icon-up").unbind().addClass("ui-icon-up-disable");
		var lastTr = $("#zdpzTabGrid").find(" tbody tr:last");
		$(lastTr).find("span.ui-icon-down").unbind().addClass("ui-icon-down-disable");
		$(lastTr).find("span.ui-icon-root").unbind().addClass("ui-icon-root-disable");
		
	}
	
	
	var initialization = api.data.initialization||false;

	var baseOptions = {
		shrinkToFit: false,
		multiselect : false, // 是否支持多选
		width:$("#zdpzForm").closest(".bootbox-body").actual('innerWidth') - 2,
		colModel:[
			 {label:'字段配置ID',name:'zdpz_id', index: 'zdpz_id',hidden:true},
			 {label:'字段索引',name:'zd_index', index: 'zd_index',key:true,hidden:true},
		     {label:'字段代码',name:'zd_name', index: 'zd_name',hidden:true},
		     {label:'是否Key',name:'zd_key', index: 'zd_key',hidden:true},

		     {label:'字段名称',name:'zd_label', index: 'zd_label',align:'center',width:"100px"},
		     {label:'字段显示位置-值',name:'zd_align', index: 'zd_align',align:'center',hidden:true},
	    	 {label:'字段显示位置',name:'zd_align_str', index: 'zd_align_str',align:'center',width:"100px",formatter:function(cellvalue, options, rowObject){
				var html = [];
					html.push('<select class="zdpz" style="width: 90px;" zd_index="'+rowObject.zd_index+'">');
					html.push('<option value="left" '+((rowObject.zd_align == "left") ? ' selected="selected" ' :"")+'>居左</option>');
					html.push('<option value="center" '+((rowObject.zd_align == "center") ? ' selected="selected" ' :"")+'>居中</option>');
					html.push('<option value="right" '+((rowObject.zd_align == "right") ? ' selected="selected" ' :"")+'>居右</option>');
	    			html.push('</select>');
		      		return html.join("");
	      	 }},
	      	 {label:'是否显示-值',name:'zd_hidden', index: 'zd_hidden',hidden:true},
		     {label:'是否显示',name:'zd_hidden_str', index: 'zd_hidden_str',align:'center',width:"130px",formatter:function(cellvalue, options, rowObject){
		      	var html = [];
	      		html.push('<input class="zdpz" zd_index="'+rowObject.zd_index+'" type="radio" value="1" name="zd_hidden_'+rowObject.zd_index+'" '+((parseInt(rowObject.zd_hidden) == 1) ? ' checked="checked" ' :"")+' />是&nbsp;');
	      		html.push('<input class="zdpz" zd_index="'+rowObject.zd_index+'" type="radio" value="0" name="zd_hidden_'+rowObject.zd_index+'" '+((parseInt(rowObject.zd_hidden) == 0) ? ' checked="checked" ' :"")+' />否');
	      		return html.join("");
	      	 }},
	      	 {label:'是否可调整宽度-值',name:'zd_resizable', index: 'zd_resizable',hidden:true},
	      	 {label:'是否可调整宽度',name:'zd_resizable_str', index: 'zd_resizable_str',align:'center',width:"130px",formatter:function(cellvalue, options, rowObject){
	      		var html = [];
	      		html.push('<input class="zdpz" zd_index="'+rowObject.zd_index+'" type="radio" value="1" name="zd_resizable_'+rowObject.zd_index+'" '+((parseInt(rowObject.zd_resizable) == 1) ? ' checked="checked" ' :"")+' />是&nbsp;');
	      		html.push('<input class="zdpz" zd_index="'+rowObject.zd_index+'" type="radio" value="0" name="zd_resizable_'+rowObject.zd_index+'" '+((parseInt(rowObject.zd_resizable) == 0) ? ' checked="checked" ' :"")+' />否');
	      		return html.join("");
	      	 }},
	      	 {label:'是否可排序-值',name:'zd_sortable', index: 'zd_sortable',hidden:true},
	      	 {label:'是否可排序',name:'zd_sortable_str', index: 'zd_sortable_str',align:'center',width:"130px",formatter:function(cellvalue, options, rowObject){
	      		var html = [];
	      		html.push('<input class="zdpz" zd_index="'+rowObject.zd_index+'" type="radio" value="1" name="zd_sortable_'+rowObject.zd_index+'" '+((parseInt(rowObject.zd_sortable) == 1) ? ' checked="checked" ' :"")+' />是&nbsp;');
	      		html.push('<input class="zdpz" zd_index="'+rowObject.zd_index+'" type="radio" value="0" name="zd_sortable_'+rowObject.zd_index+'" '+((parseInt(rowObject.zd_sortable) == 0) ? ' checked="checked" ' :"")+' />否');
	      		return html.join("");
	      	 }},
	      	 {label:'显示顺序-值',name:'zd_number', index: 'zd_number',hidden:true},
	      	 {label:'显示顺序',name:'zd_number_str', index: 'zd_number_str',align:'center',width:"120px",formatter:function(cellvalue, options, rowObject){
	      		var html = [];
	      		html.push('<span zd_index="'+rowObject.zd_index+'" title="置顶" style="margin-left:15px;" class="ui-icon-top">&nbsp;</span>&nbsp;');
	      		html.push('<span zd_index="'+rowObject.zd_index+'" title="上移" class="ui-icon-up">&nbsp;</span>&nbsp;');
	      		html.push('<span zd_index="'+rowObject.zd_index+'" title="下移" class="ui-icon-down">&nbsp;</span>&nbsp;');
	      		html.push('<span zd_index="'+rowObject.zd_index+'" title="置底" class="ui-icon-root">&nbsp;</span>');
	      		return html.join("");
	      	 }}
		],
		gridComplete:function(){
			bindEvent();
			return true;
		}/*,
		sortname: 'zd_number', //首次加载要进行排序的字段 
	 	sortorder: "asc"*/
	};

	var localData = [];
	//本地数据模式
	if(initialization==false){
		var zd_number = 0;
		$.each(api.data.colModel||[],function(i,colModel){
			if($.trim(colModel.label).length>0&&$.trim(colModel.index).length>0&&colModel.hidden!=true){
				var row = {
					"zd_index":colModel.index,
					"zd_name":colModel.name,
					"zd_key":(colModel.key?"1":"0"),
					"zd_label":colModel.label,
					"zd_align":colModel.align||"center",
					"zd_hidden":(colModel.hidden==false?"0":"1"),
					"zd_resizable":(colModel.resizable==false?"0":"1"),
					"zd_sortable":(colModel.sortable==false?"0":"1"),
					"zd_number":zd_number
				};
				localData.push(row);
				zd_number += 1;
			}
		});
	}else{
		$.ajax({
			dataType :"json",
			type : "POST",
			url : _path+"/xtgl/zdpz_cxZdpzList.html",
			data : api.data.params||{},
			async : false,
			timeout : 6E4,
			contentType : "application/x-www-form-urlencoded;charset=UTF-8",
			success : function(data) {
				if($.defined(data)&&data.length>0){
					$.each(data,function(index,colRow){
						localData.push(colRow);
					});
				}
			}
		});
	}
	//创建本地数据型JQGrid
	var ZdpzGrid = $.extend({},BaseJqGrid,$.extend({},baseOptions,{  
		pager: null,
		datatype:"local",
		height : 500,
		data:localData,
		rowNum : localData.length // 每页显示记录数
	}));

	$("#zdpzTabGrid").loadJqGrid(ZdpzGrid);
	
	

});