jQuery(function($){
	var regu = /[{]{1}[a-z]+.[a-z]+[}]{1}/g;
	var re = new RegExp(regu);
	var matchs = $("#nr").val().match(re);
	var _qrHtml = _path + "/comm/common/zsxt/getQrcode.zf";
	var _tzzpHtml = _path + "/tzgl/xssjgl/ckXszp.zf";
	var _wbzpHtml = _path + "/zzzsgl/bmxxgl/ckXszp.zf";
	var source = matchs.slice(0);
	for(var v = 0; v < matchs.length; v++){
		matchs[v] = matchs[v].replace(matchs[v].substring(matchs[v].lastIndexOf("."), matchs[v].length - 1)
				, matchs[v].substring(matchs[v].lastIndexOf("."), matchs[v].length - 1).toUpperCase());
	}
	var list = eval($("#yhglList").val());
	//$("#yhglList").remove();
	for(var i = 0; i < list.length; i ++){
		var model = list[i];
		var html = $("#nr").val();
		for(var j = 0; j < matchs.length; j ++){
			var value = eval('(' + matchs[j].substring(1, matchs[j].length-1) + ')');
			//条形码特殊处理
			if(matchs[j].substring(1, matchs[j].length-1) == "model.TXM"){
				html = html.replace(source[j], eval('(' + matchs[j].substring(1, matchs[j].length-1) + ')') + "_txm");
			}else if(matchs[j].substring(1, matchs[j].length-1) == "model.EWM"
				|| matchs[j].substring(1, matchs[j].length-1) == "model.QRCODE"){
				html = html.replace(source[j], eval('(' + matchs[j].substring(1, matchs[j].length-1) + ')') + "_qrcode");
			}else if(matchs[j].substring(1, matchs[j].length-1) == "model.TZZP"){
				html = html.replace(source[j], eval('(' + matchs[j].substring(1, matchs[j].length-1) + ')') + "_tzzp");
			}else if(matchs[j].substring(1, matchs[j].length-1) == "model.WBZP"){
				html = html.replace(source[j], eval('(' + matchs[j].substring(1, matchs[j].length-1) + ')') + "_wbzp");
			}else{
				html = html.replace(source[j], eval('(' + matchs[j].substring(1, matchs[j].length-1) + ')'));
			}
		}
		$("#contenttext").append(html);
		if(i != list.length - 1){
			$("#contenttext").append("<div style='page-break-after: always;'></div>");
		}
	}
	$(".edit-block.ui-sortable").each(function(i, o){
		$(o).find(".field.data").each(function(ii, oo){
			$(oo).closest(".text-module").css("color", "black");
			let $obj = $(oo).closest(".text-module").find("input[name=dm]");
			if($obj.val() != "undefined" && $obj.val() != undefined){
				//条形码特殊处理
				if($obj.val().indexOf("_txm") != -1) {
					var txm = $obj.val().replace("_txm", "");
					$(oo).html('<img alt="' + txm + '" src="' + _path + '/comm/common/zsxt/scTxm.zf?bmh=' + txm + '" style="width:300px;"/>');
				} else if($obj.val().indexOf("_qrcode") != -1) {
					var qrcode = $obj.val().replace("_qrcode", "");
					var h = $(this).closest("div.text-module").css('max-height') == "none"
						? $(this).closest("div.text-module").height() : parseInt($(this).closest("div.text-module").css("max-height"));
					var w = $(this).closest("div.text-module").css('max-width') == "none"
						? $(this).closest("div.text-module").width() : parseInt($(this).closest("div.text-module").css("max-width"));
					var _qrUrl = _qrHtml + "?qr=" + qrcode;
					if(!isNaN(h)){
						_qrUrl += "&h=" + h;
					}
					if(!isNaN(w)){
						_qrUrl += "&w=" + w;
					}
					$(oo).html('<img alt="' + qrcode + '" src="' + _qrUrl + '"/>');
				} else if($obj.val().indexOf("_tzzp") != -1) {
					var tzzp = $obj.val().replace("_tzzp", "");
					var h = $(this).closest("div.text-module").css("max-height") == "none"
						? $(this).closest("div.text-module").height() + "px" : $(this).closest("div.text-module").css("max-height");
					var w = $(this).closest("div.text-module").css("max-width") == "none"
						? $(this).closest("div.text-module").width() + "px" : $(this).closest("div.text-module").css("max-width");
					var _tzzpCss = 'style="width:' + w + '; height:' + h + '"';
					var _tzzpUrl = _tzzpHtml + "?ksh=" + tzzp;
					$(oo).html('<img alt="' + qrcode + '" src="' + _tzzpUrl + '"' + _tzzpCss + '/>');
				} else if($obj.val().indexOf("_wbzp") != -1) {
					var wbzp = $obj.val().replace("_wbzp", "");
					var h = $(this).closest("div.text-module").css("max-height") == "none"
						? $(this).closest("div.text-module").height() + "px" : $(this).closest("div.text-module").css("max-height");
					var w = $(this).closest("div.text-module").css("max-width") == "none"
						? $(this).closest("div.text-module").width() + "px" : $(this).closest("div.text-module").css("max-width");
					var _wbzpCss = 'style="width:' + w + '; height:' + h + '"';
					var _wbzpUrl = _wbzpHtml + "?ksh=" + wbzp;
					$(oo).html('<img alt="' + qrcode + '" src="' + _wbzpUrl + '"' + _wbzpCss + '/>');
				} else {
					$(getYoungest(oo)).text($obj.val());
				}
			}else{
				$(oo).text("");
			}
		});
	});
	jQuery("#printBut").fadeIn("slow");
	jQuery(window).scroll(function(){
		var scrTop = jQuery(document).scrollTop();
		if(scrTop > 200){
			jQuery("#gotoTop").fadeIn("slow");
		}else{
			jQuery("#gotoTop").fadeOut("slow");
		}
	});
	PageSetup_Null();
});

function goToUp(){
	jQuery(window).scrollTop(0);
}

function printlxd(){
	window.print();
}

//通用打印事件
function Print() {
	document.all.eprint.orientation=1; //1:纵向，2：横向
	document.all.eprint.header = "";
	document.all.eprint.footer = "";
	document.all.eprint.Print(true);//直接打印
	window.close();
}

var HKEY_Root, HKEY_Path, HKEY_Key;
HKEY_Root = "HKEY_CURRENT_USER";
HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
//设置网页打印的页眉页脚为空
function PageSetup_Null() {
	try {
		var Wsh = new ActiveXObject("WScript.Shell");
		HKEY_Key = "header";
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
		HKEY_Key = "footer";
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
		HKEY_Key = "margin_bottom";
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.4");
		HKEY_Key = "margin_left";
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
		HKEY_Key = "margin_right";
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
		HKEY_Key = "margin_top";
		Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.4");
	} catch (e) {
		//alert(e);
	}
}

function getYoungest(){
	let arg = $(arguments[0]);
	let _re;
	if(arg.children().length > 0){
		_re = getYoungest(arg.children());
	}else{
		_re = arg;
	}
	return _re;
}