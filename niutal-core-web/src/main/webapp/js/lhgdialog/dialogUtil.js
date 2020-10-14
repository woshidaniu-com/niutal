

function showDialog(t, width, height, url, other) {
	var setting = {
		title : t,
		width : width,
		height : height,
		lock : true,
		fixed : true,
		focus : true,
		min : false,
		max : false,
		content : 'url:' + url
	};

	var params = jQuery.extend(setting, other || {});
	if (frameElement && frameElement.api) {
		var api = frameElement.api;
		var W = api.opener;
		params["id"] = "childDialog";
		params["parent"] = api;
		return W.lhgdialog(params);
	} else {
		params["id"] = "parentDialog";
		params["parent"] = window;
		return lhgdialog(params);
	}
}

/**
 * 
 */
function refershParent(){
	var api = frameElement.api;
	var W = api.opener;
	jQuery("#searchButton",W.document).click();
	closeDialog();
}


function closeDialog() {
	var api = frameElement.api;
	api.close();
}

var alert = function(content,callBack){
	jQuery.dialog.setting.zIndex=9999;
	jQuery.dialog.setting.width=200;
	jQuery.dialog.alert(content,callBack,window);
};

var tips = function(content,callBack,time){
//	var time = time || 3;
//	jQuery.dialog.setting.zIndex=9999;
//	jQuery.dialog.tips(content,time,'succ.png',callBack);
	return lhgdialog({
		title: '温馨提示',
		id: 'Alert',
		zIndex: 9999,
		icon: 'succ.png',
		fixed: true,
		lock: true,
		content: content,
		width:'240px',
		ok: true,
		resize: false,
		close: callBack
	});
};

var confirm = function(content,okFun,canFun){
	jQuery.dialog.setting.zIndex=9999;
	jQuery.dialog.confirm(content,okFun,canFun);
};
