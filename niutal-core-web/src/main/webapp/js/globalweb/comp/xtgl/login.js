function refreshCode(){
// 			    document.getElementById("yzmPic").src = _path + '/xtgl/login_code.html?time=' + new Date().getTime();
	document.getElementById("yzmPic").src = _path + '/kaptcha?time=' + new Date().getTime();
}
function isEmpty(dataid){
	if($('#' + dataid).val() != null && $('#' + dataid).val() != ''){
		return false;
	}else{
		return true;
	}
}


//显示错误信息
function showErrMsg(errorMsg){
	var mm = document.getElementById("login_errorMsg");
	mm.style.display="block";
	mm.innerHTML=errorMsg;
}

//隐藏错误信息
function hideErrMsg(){
	var mm = document.getElementById("login_errorMsg");
	mm.style.display="none";
	mm.innerHTML="";
}

//验证用户名称和密码
function checkYhmAndMm(){
	var jqYhm = jQuery("#yhm");
	var jqMm = jQuery("#mm"); 
	
	var yhm=jqYhm.val();
	var mm=jqMm.val();
	if(yhm == mm){
		showErrMsg("用户名和密码不能相同,请修改!");
		return false;
	}else{
		hideErrMsg();
		return true;
	}
}

//pwStrength函数   用于验证
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
}	