function refreshCode(){
	$("#yzmPic").attr("src", _path + '/kaptcha?time=' + new Date().getTime());
}

jQuery(function($){
	
	$('#mm,#yzm').empty();
	
	$("#mm").bind("copy paste cut",function(){
		return false;
	});
	
	//获得焦点
	$("#yhm").focus();
	//设置回车登录事件	
	$('#yhm,#mm,#yzm').unbind("keydown").bind("keydown", function (e) {
		var event = $.event.fix(e);
		//回车自动查询
		if( event.keyCode == 13){
			//取消浏览器默认行为
			event.preventDefault();
			//点击登录
			$('#dl').click();
		}
		//取消事件冒泡
		event.stopPropagation();
		//阻止剩余的事件处理函数执行并且防止事件冒泡到DOM树上
		event.stopImmediatePropagation();
	});
	
	
	var modulus,exponent;
	
	$.getJSON(_path+"/xtgl/login_getPublicKey.html?time="+new Date().getTime(),function(data){
		modulus = data["modulus"];
		exponent = data["exponent"];
	});
	
	$("#dl").click(function(){
		var ts = '<span class="glyphicon glyphicon-minus-sign"></span>';
		if(!$.founded($("#yhm").val())){
			$("#tips").empty().append(ts + $.i18n.niutal["login"]["user_empty"]);
			$("#tips").show();
			return false;
		}
		if(!$.founded($("#mm").val())){
			///$("#tips").removeClass("alert-danger").addClass("alert-warning");
			$("#tips").empty().append(ts + $.i18n.niutal["login"]["pwd_empty"]);
			$("#tips").show();
			return false;
		}
		if($("#yzmDiv:visible").size()>0){
			if(!$.founded($("#yzm").val())){
				$("#tips").empty().append(ts + $.i18n.niutal["login"]["yzm_empty"]);
				$("#tips").show();
				return false;
			}
		}
		
		var rsaKey = new RSAKey();
		rsaKey.setPublic(b64tohex(modulus), b64tohex(exponent));
		var enPassword = hex2b64(rsaKey.encrypt($("#mm").val()));
		$("#mm").val(enPassword);
		$("#hidMm").val(enPassword);   //页面上放了一个隐藏的password类型输入框，name也是mm，防止密码自动填充，在提交的时候把内容设置成跟输入的密码一致
		
//		var isSuccess = false;
//		$.ajax({
//			url		:_path+'/xtgl/login_cxCheckYh.html',
//			async	: false,
//			type	:"post",
//			dataType:"json",
//			data	:{"yhm":$("#yhm").val(), "mm":$("#mm").val(), "yzm" :$("#yzm").val()},					
//			success	:function(data){
//				if(data["status"]=="success"){
//					isSuccess = true;
//				}else{
//					//输错三次 则需输验证码
//					if((data["dlCount"]||0) * 1 >= 3){
//						$("#yzmDiv").show();
//						refreshCode();
//					}
//					refreshCode();
//					//提示
//					$("#tips").empty().append(ts + data["message"]);
//					$("#tips").show();
//					isSuccess = false;
//				}
//			}
//		});
//		if(isSuccess){
			document.forms[0].submit();
//		}else{
//			return false;
//		}
	});
	 
	/*//pwStrength函数   用于验证
	//当用户放开键盘或密码输入框失去焦点时,根据不同的级别显示提示
	function passStrength(password) {
		var jqYhmmdj=jQuery("#yhmmdj");
		if (password == null || password == '') {
			hideErrMsg();
		} else {
			var S_level = checkStrong(password);
			//设置密码强度
			jqYhmmdj.val(S_level);
			switch (S_level) {
			case 0:
				showErrMsg("密码太短,请及时修改!");
				strong = false;
				return false;
			case 1:
				showErrMsg("密码强度太弱,请及时修改!");
				strong = false;
				return false;
			default:
				strong = true;
				hideErrMsg();
			}
		}

		//验证用户名是否相同
		if(!checkYhmAndMm()){
			//设置密码强度 ,定死设定弱密码
			jqYhmmdj.val("1");
		}
	}*/	
/*	//保存Cookie
	function SetCookie(){
		var username = jQuery("#yhm").val();
		var Then 	 = new Date();
		Then.setTime(Then.getTime() + 1866240000000);
		document.cookie ="yhm="+username+"%%;expires="+ Then.toGMTString() ;
	}*/
/*	//读取Cookie
	function GetCookie(){ 
		var nmpsd;
		var nm;
		var cookieString = new String(document.cookie);
		var cookieHeader = "yhm=";
		var beginPosition = cookieString.indexOf(cookieHeader);
		cookieString = cookieString.substring(beginPosition);
		var ends=cookieString.indexOf(";");
		if (ends!=-1){
		   cookieString = cookieString.substring(0,ends);
		}
		if (beginPosition>-1){
		   nmpsd = cookieString.substring(cookieHeader.length);
		   if (nmpsd!=""){
		    beginPosition = nmpsd.indexOf("%%");
		    nm=nmpsd.substring(0,beginPosition);
		    document.getElementById("yhm").value=nm;
		   } 
		}
	}*/
});
