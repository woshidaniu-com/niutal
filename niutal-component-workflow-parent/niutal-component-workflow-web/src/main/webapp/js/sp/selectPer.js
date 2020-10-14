//
function toLeft(){
	var option = jQuery("#fromRole option:selected");
	var yxzd = jQuery("#toRole option");
	
	for(var i=0 ;i<option.length;i++){
		for(var j=0;j<yxzd.length;j++){
			if(option[i].value==yxzd[j].value){
				$.alert('选择的记录已存在!');
				return false;
			}
		}
	}
	
	jQuery("#toRole").append(option.clone());
	//option.appendTo("#toRole");
	//$("#removeAll").click(function(){					
	//$("#select2 option").appendTo("#select1");
}

function toRight(){
	var option = jQuery("#toRole option:selected");
	//option.appendTo("#fromRole");
	option.remove();
}

function toSave(funcCallback){
	var hdleix = jQuery("#toRole option").map(function(){
		return jQuery(this).text();
	}).get().join(",");
	
	var perIds = jQuery("#toRole option").map(function(){
		return jQuery(this).val();
	}).get().join(",");
	
	if(hdleix.length==0){
		$.alert("请至少选择一个!");
		return false;
	}
	
	var obj = {};
	obj.persons = hdleix;
	obj.personIds = perIds;
	funcCallback.call(this,obj);
}

function queryUser(){
	var aType = jQuery("input:radio[name='condition1']:checked").val();
	var aValue = "";
	if(aType=='abm'){
		aValue = jQuery("#bm").val();
	}else if(aType=='ajs'){
		aValue = jQuery("#js").val();
	}else {
		aValue = jQuery("#ghxm").val();
	}
	
	if(aValue==''){
		$.alert('请至少选择或者输入一个条件值');
		return false;
	}
	
	var url = _path+'/sp/spSetting_cxUserList.html';
	jQuery.post(url,{aType:aType,aValue:aValue},function(data){
		jQuery('#fromRole').empty();
		jQuery('#fromRole').append(data);
	});
}

function showSpan(aValue){
	if(aValue=='abm'){
		jQuery('#bmspan').show();
		jQuery('#jsspan').hide();
		jQuery('#ghxmspan').hide();
	}else if(aValue=='ajs'){
		jQuery('#bmspan').hide();
		jQuery('#jsspan').show();
		jQuery('#ghxmspan').hide();
	}else {
		jQuery('#bmspan').hide();
		jQuery('#jsspan').hide();
		jQuery('#ghxmspan').show();
	}
	jQuery('#bm').val("");
	jQuery('#js').val("");
	jQuery('#ghxm').val("");
}