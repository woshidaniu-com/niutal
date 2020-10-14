function refreshCode(){
//    document.getElementById("yzmPic").src = _path + '/xtgl/login_code.html?time=' + new Date().getTime();
	if ($("#yzm").size() > 0){
		$("#yzmPic").attr("src", _path + '/kaptcha?time=' + new Date().getTime());
	}
}

jQuery(function($){

	var niutal_CSRF_TOKEN = $("#niutal_CSRF_TOKEN").val() || "";
	
	//用户名输入焦点
	$("#yhm").focus();
	
	refreshCode();
	
	var _loginFun = function (e) {
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
	}
	if ($("#yzm").size() > 0){
		$('#yzm').bind("keydown", _loginFun);
	} else {
		$('#mm').bind("keydown", _loginFun);
	}
	
	var modulus,exponent;
	
    $.ajax({
        headers: {
        	niutal_CSRF_TOKEN:niutal_CSRF_TOKEN
        },
        dataType:"JSON",
        async:false,
        url:_path+ "/xtgl/login/getPublicKey.zf?time="+new Date().getTime(),
        type: "GET",
        success: function (data) {
        	modulus = data["modulus"];
        	exponent = data["exponent"];        	
        }
    });
	
	$("#dl").click(function(){
		$("#systips").hide();
		var ts = '<span class="glyphicon glyphicon-minus-sign"></span>';
		if(!$.founded($("#yhm").val())){
			$("#tips").empty().append(ts + "用户名不能为空！");
			$("#tips").show();
			return false;
		}
		if(!$.founded($("#mm").val())){
			///$("#tips").removeClass("alert-danger").addClass("alert-warning");
			$("#tips").empty().append(ts + "密码不能为空！");
			$("#tips").show();
			return false;
		}
		
		if($("#yzm").size() > 0 && !$.founded($("#yzm").val())){
			console.log(1)
			$("#tips").empty().append(ts + "验证码不能为空！");
			$("#tips").show();
			return false;
		}
		
		var rsaKey = new RSAKey();
		rsaKey.setPublic(b64tohex(modulus), b64tohex(exponent));
		var enPassword = hex2b64(rsaKey.encrypt(jQuery("#mm").val()));
		jQuery("#mm").val(enPassword);
		/*var isUnique = false;
		$.ajax({
			url:_path+'/xtgl/login_cxCheckYh.html',
			async: false,
			type:"post",
			dataType:"json",
			data:{yhm:$("#yhm").val(),mm:enPassword,yzm:$("#yzm").val()},					
			success:function(data){
				if(data.ts=="success"){
					isUnique = true;
				}else{
					//输错三次 则需输验证码
					if(data.dlCount * 1 >= 3){
						$("#yzmDiv").show();
						refreshCode();
					}
					refreshCode();
					//提示
					$("#tips").empty().append(ts + data.ts);
					$("#tips").show();
					isUnique = false;
				}
			}
		});
		if(isUnique){
			document.forms[0].submit();
		}else{
			return false;
		}*/
		document.forms[0].submit();
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
