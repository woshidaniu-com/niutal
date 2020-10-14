var gndm = "jcsj_xsxxgl_ckxsxx";// 功能代码
var dataJson;
jQuery(function() {
	var url=_path+'/zfxg/xsxxgl/ckXsxxAjax.html';
	jQuery.ajax( {
		type : "post",
		async : false,
		url : url,
		data : {
			xh : jQuery("#xh").val()
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
	var _bjdm_id = jQuery("#content_"+gndm+" td[name='zdybdcon_td_bjdm_id']");
	_bjdm_id.html("");
	
	_bjdm_id.html("<span id='bjView'></span>");
	showResult(dataJson);
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
}
