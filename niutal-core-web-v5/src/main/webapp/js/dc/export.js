/**
 *公用自定义导出功能相关JS 
 */
var isModify = false;
var defval;
var defvalID;

//拖动后
function saveOrder() {
	isModify = true;
	$("#unselectUl").find(":input").attr("name","unselectCol");
	
	var unspan = jQuery("#unselectUl").find(".choose_yx");
	unspan.parent().append("<span class='choose_wx'></span>");
	unspan.remove();
	
	var span = jQuery("#selectUl").find(".choose_wx");
	span.parent().append("<span class='choose_yx'></span>");
	span.remove();
};


//保存配置
function saveConfig(){
	var selectZd = $("#selectUl").find(":input");
	var unselectZd = $("#unselectUl").find(":input");
	var selectCol = new Array();
	var unselectCol = new Array();
	
	for (var i = 0 ; i < selectZd.length ; i++){
		selectCol[i]=selectZd.eq(i).val();
	}
	
	for (var i = 0 ; i < unselectZd.length ; i++){
		unselectCol[i]=unselectZd.eq(i).val();
	}
	
	if (selectCol.length == 0){
		$.alert("请选择您要导出的列！");
		return false;
	}
	var $prompt = $.prompt(function(data) {
		if($.founded(data)){
			jQuery.post(
					_path+"/niutal/dc/export/saveCustomConfig.zf",
					{
						"dcclbh":$("#dcclbh").val(),
					 	"selectZd":selectCol.toString(),
					 	"unselectZd":unselectCol.toString(),
					 	"exportPHID":defvalID,
					 	"exportPHMC":data
					 },
					function(data){
						if ($.founded(data.status)){
							$.alert(data.message);
						} else {
							$('#niutal_dc_ph_ul').find("#"+data.exportPHID).remove();
							
							var added = '<div class="btn-group btn-group-sm" style="margin-top:7px;" id="' + data.exportPHID + '">';
							added += '<button type="button" class="btn button_ph" export-phid="'+ data.exportPHID +'">' + data.exportPHMC + '</button>';
							added += '<button type="button" class="btn dropdown-toggle" data-toggle="dropdown">';
							added += '<span class="caret"></span>';
							added += '<span class="sr-only">Toggle Dropdown</span>';
							added += '</button>';
							added += '<ul class="dropdown-menu" role="menu">';
							added += '<li><a href="javascript:void(0)" onclick="deletePh(\'' + data.exportPHID + '\',\'' + data.exportPHMC + '\',this);"> 删 除 </a></li>';
							added += '</ul>';
							added += '</div>';
							
							var $added = $(added);
							$('#niutal_dc_ph_ul').append($added);
						}
					}
			);
		}
	}, {title: '快照名称'});
	//触发事件，使其居中
	$prompt.trigger("resized.bs.modal");
}

//直接导出
function doExport(){
	$("#dc-component-main #selectUl").find(":input").attr("name","selectCol");
	if ($("#dc-component-main #selectUl").find(":input").length == 0){
		$.alert("请选择您要导出的列！");
		return false;
	}
	return $("#dc-component-main #selectUl").find(":input").clone();
}

//点击加号
function select(obj){
	var li = jQuery(obj).parent();
	$(obj).parent().appendTo($("#selectUl"));
	$(obj).remove();
	li.append("<span class='choose_yx'></span>");
	saveOrder();
}

//点击减号
function unselect(obj){
	var li = $(obj).parent();
	$(obj).parent().appendTo($("#unselectUl"));
	$(obj).remove();
	li.append("<span class='choose_wx'></span>");
	saveOrder();
}

//删除偏好设置
function deletePh(id,mc,$this){
	$.confirm("确认要删除 ‘<b>"+mc+"</b>’ 快捷设置？",function(result){
		if(result){
			$.post(_path+"/niutal/dc/export/deleteCustomConfig.zf?_t="+new Date().getTime(),{"exportPHID":id},function(data){
				if (data.status == 'fail'){
					$.alert(data.message);
				}else{
					$("#"+id).remove();
				}
			},'json');
		}
	});
}
function addSelectPhBackground(btn){
	var $btn = $(btn);
	$btn.addClass("button_ph_choose");
}

function removeAllSelectPhBackground(){
	$("#exportPHID").val("");
	var $btns = $(".button_ph_choose");
	$btns.removeClass("button_ph_choose");
}
//选择偏好设置
function selectPh(id,$this){
	removeAllSelectPhBackground();
	addSelectPhBackground($this);
	defval = $($this).text();
	defvalID = id;
	$("#exportPHID").val(id);
	$('.kzlist ul li a').removeClass('cur');
	$($this).parent('a').addClass('cur');
	$.post(_path+"/niutal/dc/export/cxConfigPhZdList.zf",
			{"id": id},
			function(data){
				var $spans = $('#selectUl li span');
				$spans.each(function(index,element){
					unselect(element);
				});
				//遍历偏好设置
				$.each(data,function(i,n){
					var zd = n['zd'];
					var zdmc = n['zdmc'];
					var el = jQuery('#unselectUl').find('input[name="unselectCol"]'+'[value="' + zd + '!_##_!' + zdmc + '"]');
					if(el.size() > 0){
						var $span = el.parent().next('.choose_wx:first');
						var obj = $span.get();
						select(obj);
					}
				});
			},
			'json');
}

$(function() {
	
	setTimeout(function(){
		$("#unselectUl, #selectUl").dragsort({
			dragSelector : "label",
			dragBetween : true,
			dragEnd : saveOrder,
			placeHolderTemplate : "<li><label class='defdblclick college_li college_checkbox' style='border:#155FBE 1px dotted;background:#CBE4F8;height:20px;line-height:20px!important;*height:28px;width:90px;padding:3px 0px;text-indent: 15px;'></label></li>"
		});
	},200); 
	
	//默认全部选中
	//$('#unselectUl li span').click();
	
	$('.defdblclick').dblclick(function(){
		$(this).parent().find('span').click();
	});
	
	$('#niutal_dc_ph_ul .btn-ph').unbind();
	
	$(document).off('click', '.button_left').on('click', '.button_left', function(event) {
		var $spans = $('#unselectUl li span');
		$spans.each(function(index,element){
			select(element);
		});
		
		removeAllSelectPhBackground();
	}).off('click', '.button_right').on('click', '.button_right', function(event) {
		var $spans = $('#selectUl li span');
		$spans.each(function(index,element){
			unselect(element);
		});
		
		removeAllSelectPhBackground();
	}).off('click','#niutal_dc_ph_ul .button_ph').on('click','#niutal_dc_ph_ul .button_ph',function(event){
		var $this = $(this);
		var phid = $this.attr("export-phid");
		selectPh(phid,this);
	}).off('click','#unselectUl .choose_wx').on('click','#unselectUl .choose_wx',function(event){
		select(this);
		
		removeAllSelectPhBackground();
	}).off('click','#selectUl .choose_yx').on('click','#selectUl .choose_yx',function(event){
		unselect(this);
		
		removeAllSelectPhBackground();
	});
});