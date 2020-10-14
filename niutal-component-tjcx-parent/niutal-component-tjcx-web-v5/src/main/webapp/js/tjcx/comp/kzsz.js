/*
 * 设置默认查询快照弹层显示，超级管理员可进行共享
 */
function setDefaultCxkz() {
	var czy = jQuery("#czy").val();
	var cjglys = cjgly.split(",");
	for ( var i = 0; i < cjglys.length; i++) {
		if (cjglys[i] == czy) {
			jQuery("#sfgy").show();
			break;
		}
	}
	
	jQuery("input:radio[name='sfgyView']").change(function(){
		var curSfqy = jQuery(this).val();
		if(curSfqy == "1"){
			jQuery("#kzjsrTr").removeClass("hide");
		}else{
			jQuery("#kzjsrTr").addClass("hide");
		}
	});
}

/*
 * 保存查询快照
 */
function saveCxkz() {

	var xmdm = jQuery("#xmdm").val();
	var szmcView = jQuery.trim(jQuery("input[name=szmcView]").last().val());
	if (szmcView == "") {
		szmcView = jQuery.trim(jQuery("input[name=szmcView]").val());
	}
	if (szmcView == "") {
		$.alert("请输入快照名称！");
		return false;
	}
	var sfgyView = jQuery("input[name='sfgyView']:checked").val();// 是否共享
	setCxkzValue();

	var gltj = jQuery("#gltj").val();
	var bbhxl = jQuery("#bbhxl").val();
	var bbzxl = jQuery("#bbzxl").val();
	var tsx = jQuery("#tsx").val();
	var kzlx = jQuery("#kzlx").val();
	var czy = jQuery("#czy").val();

	var kzjsr = jQuery("#kzjsr").val();

	if(sfgyView != "1"){
		kzjsr = "";
	}
	
	//快照描述
	var kzms = editor.html();
	
	jQuery.ajax( {
		type : "post",
		url : _path + "/niutal/tjcx/kzsz/save.zf?timestamp=" + new Date().getTime(),
		data : {
			xmdm : xmdm,
			szmc : szmcView,
			gltj : gltj,
			bbhxl : bbhxl,
			bbzxl : bbzxl,
			tsx : tsx,
			kzlx : kzlx,
			sfgy : sfgyView,
			kzjsr : kzjsr,
			kzms : kzms
		},
		dataType : "text",
		success : function(data) {
			var kzszid = data;
			tjxmReset();
			$.success("操作成功!",function(){
				var li = createCxkzLiHtml(kzszid, szmcView, sfgyView, czy);
				jQuery("#cxkzUl").prepend(li);
				jQuery("#cxkzUl li a").removeClass("active");
				
				jQuery("#cxkz1").show();
				jQuery.closeModal("kzszModel");
			});
		}
	});
}
