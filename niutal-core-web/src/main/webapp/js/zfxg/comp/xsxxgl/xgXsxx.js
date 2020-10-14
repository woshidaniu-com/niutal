var gndm = "jcsj_xsxxgl_xgxsxx";// 功能代码
var dataJson;
jQuery(function() {
	var url = _path + '/zfxg/xsxxgl/ckXsxxAjax.html';
	jQuery.ajax( {
		type : "post",
		async : false,
		url : url,
		data : {
			xh : jQuery("#curXh").val()
		},
		dataType : "json",
		success : function(data) {
			dataJson = data;
			zdybdInit(gndm, dataJson);
			setInit();
		}
	}); 
})


function setInit() {
	var _bjdm_id = jQuery("#bjdm_id");
	var bjdm_id = _bjdm_id.val();
	_bjdm_id.parent().html("<input type='hidden' name='bjdm_id' id='bjdm_id' value='"+bjdm_id+"'>");
	
	_bjdm_id = jQuery("#bjdm_id");
	_bjdm_id
			.after("<span id='bjView'></span><button type='button' onclick='selectBj();'>选择</button>");
	showResult(dataJson);
}

function selectBj() {
	showDialog('选择班级', 700, 400, _path + '/zfxg/bjdm/cxBjdmForSelect.html');
}

function showResult(obj) {
	var bjdm_id = obj["bjdm_id"];
	var bjdm = obj["bjdm"];
	var njdm_id = obj["njdm_id"];
	var bjmc = obj["bjmc"];
	var bmmc = obj["bmmc"];
	var zymc = obj["zymc"];
	if (njdm_id == null) {
		njdm_id = "";
	}
	if (bmmc == null) {
		bmmc = "";
	}

	if (zymc == null) {
		zymc = "";
	}

	if (bjmc == null) {
		bjmc = "";
	}
	var view = njdm_id + " " + bmmc + " " + zymc + " " + bjmc;
	jQuery("#bjView").html(view);
	jQuery("#bjdm_id").val(bjdm_id);
}


function xgXsxx() {
	if (!zdybdCheckAlert(gndm)) {
		return false;
	}
	var url = _path + '/zfxg/xsxxgl/xgXsxxAjax.html';

	ajaxSubFormWithFun("inputForm",url,{},function (data){
		var message = data.message;
		var success = data.success;
		
		if (success == true) {
			alertMessage(message, function() {
				this.close();
				refershParent();
				return false;
			});
		} else {
			alertMessage(message, function() {
				this.close();
				return false;
			});
		}
	});

}