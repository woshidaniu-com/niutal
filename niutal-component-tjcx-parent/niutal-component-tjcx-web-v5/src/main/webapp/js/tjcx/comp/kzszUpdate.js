var DYH = "_DYHBS_";// 单引号

jQuery(function() {
	setDefaultCxkz();// 设置默认查询快照弹层显示，超级管理员可进行共享
});

/*
 * 设置默认查询快照弹层显示，超级管理员可进行共享
 */
function setDefaultCxkz() {
	var parentPage = getParentDialogWin();
	var czy = parentPage.jQuery("#czy").val();
	var cjglys = cjgly.split(",");
	for ( var i = 0; i < cjglys.length; i++) {
		if (cjglys[i] == czy) {
			jQuery("#sfgy").show();
			break;
		}
	}
	
	jQuery("input:radio[name='sfgy']").change(function(){
		var curSfqy = jQuery(this).val();
		if(curSfqy == "1"){
			jQuery("#kzjsrTr").show();
		}else{
			jQuery("#kzjsrTr").hide();
		}
	});
}

/**
 * 修改快照
 * @return
 */
function updateCxkz(){
	var szmc = jQuery.trim(jQuery('#szmc').val());
	if (szmc == "") {
		alertMessage("请输入快照名称！");
		return false;
	}
	var sfgy = jQuery("input[name='sfgy']:checked").val();// 是否共享
	var kzjsr = jQuery("#kzjsr").val();
	if(sfgy != "1"){
		jQuery('#kzjsr').val('');
		
	}
	subForm('kzsz_saveUpdate.html')
}
