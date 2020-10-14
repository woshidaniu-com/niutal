/* 此JS提供验证输入框输入内容，如只能输入数字，日期或字母 等等.*/

/**只能输入字符数字,传入事件*/
function chkCharNumEvent(e) {
	var key = window.event ? e.keyCode : e.which;
	if(((key>=48)&&(key<=57)) || ((key>=65)&&(key<=90)) || ((key>=97)&&(key<=122)) || (key==45) || (key==46) || (key==8)) {
	    return true;
	} else {
	    return false;
	}
}

/**验证字符数字,传入字符*/
function chkCharNum(key) {
	if(((key>=48)&&(key<=57)) || ((key>=65)&&(key<=90)) || ((key>=97)&&(key<=122)) || (key==45) || (key==46)|| (key==8)) {
	    return true;
	} else {
	    return false;
	}
}

/**只能输入数字,传入事件*/
function chkNumEvent(e) {
	var key = window.event ? e.keyCode : e.which;
	if(((key>=48)&&(key<=57)) || (key == 46) ||(key==45)|| (key==8)) {
	    return true;
	} else {
	    return false;
	}
}

/**验证数字,传入数字*/
function chkNum(key) {
	if(((key>=48)&&(key<=57)) || (key == 46) ||(key==45)|| (key==8)) {
	    return true;
	} else {
	    return false;
	}
}

/**
 * 验证正整数
 * 
 * @param key
 * @return
 */
function chkIntEvent(e) {
	var key = e.which || e.keyCode;
	if((key>=48 && key<=57) || (key==8) || (key==46)) {
	    return true;
	} else {
	    return false;
	}
}

/**
 * 验证正整数
 * 
 * @param key
 * @return
 */
function chkInt(key) {
	if((key>=48)&&(key<=57) || (key==8)) {
	    return true;
	} else {
	    return false;
	}
}

/**
* 用于只读框,无法输入字符,值能删除
* @param key
* @return
*/
function chkBackspace(e) {
	var key = window.event ? e.keyCode : e.which;
	if(key == 0x8) {
		return true;
	} else {
		return false;
	}
}

/**
* 验证时间格式
* @param time '12:00:00'
*/
function chkTime(time) {
	var reg = /^(20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d$/;
	return reg.test(time);
}

/**
 * 正整数

 * @param integer
 * @return
 */
function isPlusInt(integer){
	var regex = /^[0-9]*[1-9][0-9]*$/;
	if(regex.exec(integer)){
	 	return true;
	} else {
		return false;
	}
}