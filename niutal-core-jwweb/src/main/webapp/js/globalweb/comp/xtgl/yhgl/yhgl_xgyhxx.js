// 扩展自己的方法
jQuery(function($) {
	//根据职工号加载用户已有角色信息,并显示选中状态
	var cbvjsxxArray = $("input[name='cbvjsxx']");
	var jsdm = document.getElementById("jsdm").value;
	var data = jsdm.split(",");
	$.each(cbvjsxxArray,function(j,n) {
		for(var i=0;i<data.length;i++){
			if(data[i]==$(cbvjsxxArray[j]).attr("value")){
				cbvjsxxArray[j].checked = true;
			}
		}
	});
	
	

	$("#jg_id").change(function(){
		$("#jg_mc").val($(this).getSelected().text());
	});
	
});



function save(){
	if (checkEmail(jQuery("#dzyx")) && checkInputNotNull('xm')){ 
		 var jgdm = jQuery('#jgdm').val();
		 var jgmc = jQuery('#jgmc').val();
		 if(jgdm==''){
		 	alert('请选择所属机构！');
		 	return false;
		 }
		 if(jgmc==''){
		 	alert('请选择所属机构！');
		 	return false;
		 }
		 subForm(_path + '/xtgl/yhgl_xgBcYhxx.html');
	}
}
