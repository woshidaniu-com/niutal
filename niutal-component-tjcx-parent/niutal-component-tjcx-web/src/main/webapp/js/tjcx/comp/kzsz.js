var DYH = "_DYHBS_";// 单引号
var editor;//编辑器
jQuery(function() {
	editor = KindEditor.create('textarea[name="kzms"]', simpleOption); //初始化编辑器
	
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
	
	jQuery("input:radio[name='sfgyView']").change(function(){
		var curSfqy = jQuery(this).val();
		if(curSfqy == "1"){
			jQuery("#kzjsrTr").show();
		}else{
			jQuery("#kzjsrTr").hide();
		}
	});
}

/*
 * 保存查询快照
 */
function saveCxkz() {
	var parentPage = getParentDialogWin();
	
	var xmdm = parentPage.jQuery("#xmdm").val();
	var szmcView = parentPage.jQuery.trim(jQuery("input[name=szmcView]").last().val());
	if (szmcView == "") {
		szmcView = parentPage.jQuery.trim(jQuery("input[name=szmcView]").val());
	}
	if (szmcView == "") {
		alertMessage("请输入快照名称！");
		return false;
	}
	var sfgyView = jQuery("input[name='sfgyView']:checked").val();// 是否共享
	parentPage.setCxkzValue();

	var gltj = parentPage.jQuery("#gltj").val();
	var bbhxl = parentPage.jQuery("#bbhxl").val();
	var bbzxl = parentPage.jQuery("#bbzxl").val();
	var tsx = parentPage.jQuery("#tsx").val();
	var kzlx = parentPage.jQuery("#kzlx").val();
	var czy = parentPage.jQuery("#czy").val();

	var kzjsr = jQuery("#kzjsr").val();

	if(sfgyView != "1"){
		kzjsr = "";
	}
	
	//快照描述
	var kzms = editor.html();
	
	jQuery.ajax( {
		type : "post",
		// async: false,
		url : _path + "/zfxg/tjcx/kzsz_save.html?timestamp=" + new Date().getTime(),
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
		dataType : "json",
		success : function(data) {
			var kzszid = data;
			parentPage.tjxmReset();
			alertMessage("操作成功!",function(){
				this.close();
				var li = parentPage.createCxkzLiHtml(kzszid, szmcView, sfgyView, czy);
				parentPage.jQuery("#cxkzUl").prepend(li);
				parentPage.jQuery("#cxkzUl li a").removeClass("cur");
				iFClose();
			});
		}
	});
}
