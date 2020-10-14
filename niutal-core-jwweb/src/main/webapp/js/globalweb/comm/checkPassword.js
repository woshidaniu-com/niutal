/***********************根据系统设置内容************************/
//pwStrength函数   用于验证
//当用户放开键盘或密码输入框失去焦点时,根据不同的级别显示提示
function passStrength(password) {
	if (password == null || password == '') {
		hideErrMsg();
	} else {
		var S_level = checkStrong(password);
		switch (S_level) {
		case 0:
			showErrMsg("密码太短,请及时修改!");
			strong = false;
			break;
		case 1:
			showErrMsg("密码强度太弱,请及时修改!");
			strong = false;
			break;
//		case 2:
//			showErrMsg("密码强度中等,请及时修改!");
//			strong = false;
//			break;
		default:
			strong = true;
			hideErrMsg();
		}
	}
	
}

//显示错误信息
function showErrMsg(errorMsg){
	var mm = document.getElementById("message");
	mm.innerHTML=errorMsg;
}

//隐藏错误信息
function hideErrMsg(){
	var mm = document.getElementById("message");
	mm.innerHTML="";
}

/***************************主体验证内容不用修改***********************************/
//CharMode函数  
//测试某个字符是属于哪一类.  
var strong = true;
function CharMode(iN) {
	if (iN >= 48 && iN <= 57) // 数字
		return 1;
	if (iN >= 65 && iN <= 90) // 大写字母
		return 2;
	if (iN >= 97 && iN <= 122) // 小写 字母
		return 4;
	else
		return 8; // 特殊字符
}

// bitTotal函数
// 计算出当前密码当中一共有多少种模式
function bitTotal(num) {
	var modes = 0;
	for ( var i = 0; i < 4; i++) {
		if (num & 1)
			modes++;
		num >>>= 1;
	}
	return modes;
}

// CharMode函数
// 测试某个字符是属于哪一类.
function CharMode(iN) {
	if (iN >= 48 && iN <= 57) // 数字
		return 1;
	if (iN >= 65 && iN <= 90) // 大写字母
		return 2;
	if (iN >= 97 && iN <= 122) // 小写
		return 4;
	else
		return 8; // 特殊字符
}

// checkStrong函数
// 返回密码的强度级别
function checkStrong(sPW) {
	if (sPW.length < 6)
		return 0; // 密码太短
	var Modes = 0;
	for (i = 0; i < sPW.length; i++) {
		// 测试每一个字符的类别并统计一共有多少种模式.
		Modes |= CharMode(sPW.charCodeAt(i));
	}
	return bitTotal(Modes);
}