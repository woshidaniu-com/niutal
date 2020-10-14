/* 此JS提供数字,字符等基础数据的格式验证的工具库.*/

// JS方法注释格式须包含以下几点说明：
// 1.方法的使用说明
// 2.参数说明(可选)
// 3.方法返回类型说明 (可选)
// 4.作者与时间
// 例如：


/**
 * 验证输入必须为数字
 * @param id为主键值
 * @return 返回TRUE或FALSE
 * lt 2011.12.15
 */

/**
 * 验证输入必须为数字或还小数点的数字
 * @param obj为输入的标签对象
 * @return 返回TRUE
 * lt 2011.12.15
 */
function onlyNumber(obj){
	obj.value = obj.value.replace(/[^(\d|\.)]/g,'');
	return true;
}

/**
 * 验证输入必须为整数
 * @param obj为输入的标签对象
 * @return TRUE
 * lt 2011.12.15
 */
function onyInt(obj){
	obj.value = obj.value.replace(/[^(\d]/g,'');
	return true;
}

/**
 * 验证输入不能为中文
 * @param obj为输入的标签对象
 * @return TRUE
 * lt 2011.12.15
 */
function isNotChar(obj){
	obj.value = obj.value.replace(/[^\u0000-\u00ff]/g,'');
	return true;
}


/*
 *限制只能输入数字或字母 
 */
function onlyNumWords(obj){
	obj.value = obj.value.replace(/[^\da-zA-Z]/g,"");
	return true;
}

/**
 * 验证电话或传真格式
 * @param phone
 * @returns
 */
function isTel(phone){
	var p =/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
	return phone=="" || p.exec(phone);
}



/**
 * 验证URL
 * @param url
 * @returns {Boolean}
 */
function isUrl(url){
	var p = /^((https?|ftp|news):\/\/)?([a-z]([a-z0-9\-]*[\.])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\/[a-z0-9_\-\.~]+)*(\/([a-z0-9_\-\.]*)(\?[a-z0-9+_\-\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$/;
	return url=="" || p.test(url);
}



/**
 * 邮编验证
 * @param s
 */
function isPostalCode(s) {
	var patrn = /^[0-9 ]{6}$/;
	return s=="" || patrn.exec(s);
}  


/**
 * 验证email的录入格式
 * @param sEmail
 * @return
 */
function isEmail(sEmail){
    var p = /^[_\.0-9a-z-]+@([0-9a-z][0-9a-z-]+\.){1,4}[a-z]{2,3}$/i;
    return sEmail=="" || p.test(sEmail);
}


//简单密码判断
function checkJdmm(str){ 
	var b = true;
	var c = str.split("");
		for (var i = 0; i < c.length - 1; i++) {
			if (c[i + 1] - c[i] != 0) {
				b = false;
				break;
			}
		}
		if (!b) {
			b = true;
			for (var i = 0; i < c.length - 1; i++) {
				if (c[i + 1] - c[i] != 1) {
					b = false;
					break;
				}
			}
		}
	return b;
}


//检测文本框的最大长度
function checkTextareaLength(fieldId, fieldExplain, length){
	var fildV = jQuery("#" + fieldId).val();
	if(fildV != null && fildV != ""){
     	if(fildV.length > length/2){
     		//	alertInfo(fieldExplain + "不能大于" + parseInt(length/2) + "个字！");
     		showDownError(jQuery('#'+fieldId+''),fieldExplain + "不能大于" + parseInt(length/2) + "个字！");
      		return false;
   		 }
	}
	return true;
}

/**
 * 验证输入是否为空
 * @param inputStr
 *            格式：!! 多个字符之间必须用 !!
 * @return
 */
function checkInputNotNull(inputStr){
	var input = inputStr.split("!!");
	var returnflag = true;
	var focusItem = undefined;
	//一下两个属性是为了兼容隐藏域的必填验证
	var hiddenAlert = false;   
	var notHiddenAlert = false;
	for(var i=0;i<input.length;i++){
		var temp = jQuery("#"+input[i]);
		var tempvalue = jQuery.trim(jQuery("#"+input[i]).val());
		if(temp==null || tempvalue==null || tempvalue==""){
			if(focusItem == undefined && temp.css("display") != "none"){focusItem = temp;}
			if(temp.css("display") != "none"){
				checkItemNotNull(temp);
				notHiddenAlert = true;
			}else{
				hiddenAlert = true;
			}
			returnflag = false;
		}
	}
	if(focusItem != undefined){
		focusItem.focus();
	}
	if(!notHiddenAlert && hiddenAlert){
		alert("请将必填信息，填写完整！");
	}
	
	return returnflag;
}

/**
 * 验证输入是否为空
 * @param obj
 *        
 * @return
 */
function checkItemNotNull(obj){
	if(jQuery(obj).data("addcheckNullFunFlag") == "true"){
		//不加载输入验证非空
	}else{
		jQuery(obj).data("backgroundColorStyle",jQuery(obj).css("background-color"));
		jQuery(obj).css("background-color","#FBE7E7");
		jQuery(obj).keyup(function(){
			if(jQuery.trim(jQuery(this).val()) == ""){
				jQuery(this).css("background-color","#FBE7E7");
			}else{
				jQuery(this).css("background-color",jQuery(this).data("backgroundColorStyle"));
			}
		});
		jQuery(obj).change(function(){
			if(jQuery.trim(jQuery(this).val()) == ""){
				jQuery(this).css("background-color","#FBE7E7");
			}else{
				jQuery(this).css("background-color",jQuery(this).data("backgroundColorStyle"));
			}
		});
		jQuery(obj).data("addcheckNullFunFlag","true");
	}
	
}

/**
 * 只能输入半角字符
 * @param obj
 * @return
 */
function onlyCaseChar(obj) {
	var str = obj.value;
	var result = "";
	for ( var i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) == 12288) {
			result += String.fromCharCode(str.charCodeAt(i) - 12256);
			continue;
		}
		if (str.charCodeAt(i) > 65280 && str.charCodeAt(i) < 65375)
			result += String.fromCharCode(str.charCodeAt(i) - 65248);
		else
			result += String.fromCharCode(str.charCodeAt(i));
	}
	obj.value = result;
}

/**
 * 只能输入大写
 * @param obj
 * @return
 */
function onlyUpperCase(obj){
	obj.value = obj.value.toUpperCase();
}

/**
 * 只能数据小写
 * @param obj
 * @return
 */
function onlyLowerCase(obj){
	obj.value = obj.value.toLowerCase();
}