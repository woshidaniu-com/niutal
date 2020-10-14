//http://app.baidu.com/webkits在线压缩

var zfValidate = {};
zfValidate.Util = {};
zfValidate.f={};

zfValidate.i18Req = "该项为必填项，请输入!";
zfValidate.i18Req1 = "请选择{0}!";
zfValidate.i18Email = "{0}不是一个合法的邮箱!";
zfValidate.i18Int = "{0}必须为整数!";
zfValidate.i18Datetime = "{0}不是一个合法的日期时间! 正确的格式为{1}";

//  num range start
zfValidate.i18NumRange = "{0}必须在{1}和{2}之间!";
zfValidate.i18NumRangeMin = "{0}必须小于等于{1}!";
zfValidate.i18NumRangeMax = "{0}必须大于等于{1}!";
zfValidate.i18NumRangeExp = "{0}合法范围：{1}!";
//  num range end

zfValidate.i18LenMin = "{0}最多只能输入{1}个字符，您已超出{2}个字符!";
zfValidate.i18Len = "{0}最少要输入{1}个字符，你还需要输入{2}个字符!";//{0}的长度不应大于3! //{0}的长度不应小于3!
zfValidate.i18LenExp = "{0}长度合法范围：{1}!";
zfValidate.i18LenTip = "您已输入{0}个字符，还可以输入{1}个字符!";

zfValidate.i18Num = "{0}必须为数字!";
zfValidate.i18Regex = "{0}{1}";
zfValidate.i18Ip = "{0}不是一个合法的IP!";
zfValidate.i18Postcode = "{0}不是一个合法的邮政编码!";
zfValidate.i18Tel = "合法的格式形如：0734-1234567或 13265895632";
zfValidate.i18Idcard = "{0}不是一个合法的身份证号码!";

zfValidate.i18SelectCountMin = "{0}最多只能选中{1}项，您已多选{2}项!";
zfValidate.i18SelectCount = "{0}至少要选中{1}项，你还需要选择{2}项!";
zfValidate.i18SelectCount_1 = "{0}至少要选中{1}项!";
zfValidate.i18SelectCountExp = "{0}选中个数的合法范围：{1}!";
//正确提示信息
zfValidate.i18DftOk = 'OK';

//-----  compare validator
zfValidate.i18Compare = "{0}!";
zfValidate.i18StrCompare = "{0}应该{1}{2}{3}!";
zfValidate.i18NumCompare = "{0}应该{1}{2}{3}!";
zfValidate.i18StrValueCompare = "{0}必须{1}{2}!";
zfValidate.i18NumValueCompare = "{0}必须{1}{2}!";
zfValidate.operMap = [];
zfValidate.operMap["<"] = "小于";
zfValidate.operMap["<="] = "小于或等于";
zfValidate.operMap["=="] = "等于";
zfValidate.operMap["!="] = "不等于";
zfValidate.operMap[">"] = "大于";
zfValidate.operMap[">="] = "大于或等于";

zfValidate.close = "关闭";

/**
 * tanyaowu:此段代码是几年前摘自网上，网址已经找不到了
 */
(function() {
	var initializing = false, fnTest = /xyz/.test(function() {
				xyz;
			}) ? /\b_super\b/ : /.*/;
	this.zfValidate.C = function() {
	};
	zfValidate.C.ext = function(prop) {
		var _super = this.prototype;
		initializing = true;
		var prototype = new this();
		initializing = false;
		for (var name in prop) {
			prototype[name] = typeof prop[name] == "function"
					&& typeof _super[name] == "function"
					&& fnTest.test(prop[name]) ? (function(name, fn) {
				return function() {
					var tmp = this._super;
					this._super = _super[name];
					var ret = fn.apply(this, arguments);
					this._super = tmp;
					return ret;
				};
			})(name, prop[name]) : prop[name];
		}
		function _c() {
			// 在类的实例化时，调用原型方法init
			if (!initializing && this.init){this.init.apply(this, arguments);}
				
		}
		_c.prototype = prototype;
		_c.constructor = _c;
		_c.ext = arguments.callee;
		return _c;
	};
})();


zfValidate.Conf = {
	ver: '2.1.1',
	
	errorStyle : 'text',     //默认提供：['text', 'alert']
	tipStyle : 'tip',        //默认提供：['tip']
	
	clearOtherError : false, // 当验证某一元素时，是否隐藏其它字段的错误提示。true 隐藏其它字段的错误提示
	//'keyup', 'focus', 'change'
	validateOn : ['change','blur'], // 触发验证的事件类型。 
	
	clrSpace : true,  //验证时，是否清空输入值两端的空格
	
	
	//---------  下面的配置项不建议修改  ----------------
	proNameOfMsgId : 'ttTalentMsgId',
	proNameOfReqStarId : 'ttTalentReqStarId',
	
	eventId : 'talentEventId',
	
	errCls: "talentErrMsg",         //错误提示信息的样式
	tipCls: "talentTipMsg",         //错误提示信息的样式
	errInputCls: "talentErrInput",  //验证不通过时，输入框的样式
	reqStarCls: "talentReqStar"     // 必须输入项后面紧跟着一个星号的样式
};
/**
 * 
 * @param {}
 *            target
 * @param {}
 *            type such as "click"
 * @param {}
 *            handler
 */
zfValidate.Util.addEventHandler = function(o, type, handler) {
	f = arguments.length == 4;
	if (o.addEventListener) {
		if (f) {
			o.removeEventListener(type, handler, false);
		} else {
			o.addEventListener(type, handler, false);
		}
	} else if (o.attachEvent) {

		if (f) {
			o.detachEvent("on" + type, handler);
		} else {
			o.attachEvent("on" + type, handler);
		}
	} else {
		if (f) {
		} else {
			o["on" + type] = handler;
		}
	}
};

/**
 * 将htmlElement插入到srcElement元素后面
 * 
 * @param srcElement
 * @param htmlElement
 */
zfValidate.insertAfter = function(src, e) {
	zfValidate.insertHtml('afterend', src, e);
};

zfValidate.insertHtml = function(where, el, html) {
	where = where.toLowerCase();
	if (el.insertAdjacentHTML) {
		switch (where) {
			case "afterend" :
				el.insertAdjacentHTML('AfterEnd', html);
				return el.nextSibling;
		}
	} else {
		var range = el.ownerDocument.createRange();
		var frag;
		switch (where) {
			case "afterend" :
				range.setStartAfter(el);
				frag = range.createContextualFragment(html);
				el.parentNode.insertBefore(frag, el.nextSibling);
				return el.nextSibling;
		}
	}
};
/**
 * 为element添加className样式
 * 
 * @param element
 *            被操作的元素
 * @param className
 *            样式名
 * @return
 */
zfValidate.addCls = function(target, _className) {
	tClassName = target.className;
	tClassName = " " + tClassName + " ";
	if (tClassName.indexOf(" " + _className + " ") == -1) {
		target.className = tClassName + _className;
	}
};
/**
 * 为element删除className样式
 * 
 * @param element
 *            被操作的元素
 * @param className
 *            样式名
 * @return
 */
zfValidate.rmCls = function(target, name) {
	if (!target || !target.className) {
		return;
	}
	var oClass = target.className;
	var reg = "/\\b" + name + "\\b/g";
	target.className = oClass ? oClass.replace(eval(reg), '') : '';
};

/**
 * 删除某一个元素
 * 
 * @param element
 * @return
 */
zfValidate.rmEle = function(e) {
	if (e && e.parentNode) {
		e.parentNode.removeChild(e);
	}
};

/**
 * 相当于string的trim
 * 
 * @param str
 * @return
 */
zfValidate.trim = function(s, m) {
	if (!s) {
		return "";
	}
	r = /(^\s*)|(\s*$)/g;
	if (m) {
		if (m == "l") {
			r = /(^\s*)/g;
		} else if (m == "r") {
			r = /(\s*$)/g;
		}
	}
	return s.replace(r, "");
};

/**
 * 根据类名实例化js对象
 * 
 * @param {}
 *            clazz
 * @return {}
 */
zfValidate.instanceByClass = function(c) {
	eval("var r = new " + c + "();");
	return r;
};

/**
 * 
 * @param {}
 *            v comparedValue
 * @param {}
 *            exp expression
 * @return {}
 */
zfValidate.parRngExp = function(v, exp) {
	var map = {
		'(' : '>',
		'[' : '>='
	};
	var expArr = [];
	var m1 = {
		"{" : "(",
		"}" : ")",
		"|" : "||",
		"&" : "&&"
	};
	for (i = 0; i < exp.length; i++) {
		c = exp.charAt(i);

		if (c == '(' || c == '[') {
			compareOper1 = map[c];

			index1 = exp.indexOf(')');
			index2 = exp.indexOf(']');
			_index = index1;
			compareOper2 = '<';
			if (index1 == -1 && index2 == -1) {
				alert('expression is invalid, not found ] or )!');
				return null;
			} else if (index1 == -1 || (index1 > index2 && index2 != -1)) {
				_index = index2;
				compareOper2 = '<=';
			}
			var singleExp = exp.substring(i + 1, _index);

			var numArr = singleExp.split(',');
			numArr[0] = zfValidate.trim(numArr[0]);

			if (numArr.length == 1) {
				numArr[1] = zfValidate.trim(numArr[0]);
			} else if (numArr.length == 2) {
				numArr[1] = zfValidate.trim(numArr[1]);
			} else {
				alert(singleExp + ' is error!');
				return null;
			}

			expArr.push("(");
			if (numArr[0] != '') {
				expArr.push(v);
				expArr.push(compareOper1);
				expArr.push(numArr[0]);
			}
			if (numArr[0] != '' && numArr[1] != '') {
				expArr.push(' && ');
			}
			if (numArr[1] != '') {
				expArr.push(v);
				expArr.push(compareOper2);
				expArr.push(numArr[1]);
			}

			expArr.push(")");

			exp = exp.substring(_index + 1, exp.length);
			i = 0;
			continue;
		} else if (m1[c]) {
			expArr.push(m1[c]);
		}
	}
	return expArr.join('');
};
/**
 * zfValidate.getI18S("my name is {0}, your name is {1}",["kebo","smis"], 0);
 * zfValidate.getI18S("my name is {1}, your name is {2}",["kebo","smis"], 1);
 */
zfValidate.getI18S = function() {
	ret = arguments[0];
	if (arguments.length > 1) {
		si = 0; // startIndex
		if (arguments.length == 3) {
			si = arguments[2];
		}
		for (i = 0; i < arguments[1].length; i++) {
			ret = ret.replace("{" + si + "}", arguments[1][i]);
			si++;
		}
	}
	return ret;
};

/**
 * 
 * @param {}
 *            e
 * @return {} true:包含
 */
Array.prototype.ttCons = function(e) {
	i = 0;
	for (; i < this.length && this[i] != e; i++);
	return !(i == this.length);
};

zfValidate.getStrLen = function(s) {
	var len = 0;
	var c = -1;
	for (var i = 0; i < s.length; i++) {
		c = s.charCodeAt(i);
		if (c >= 0 && c <= 128)
			len += 1;
		else
			len += 2;
	}
	return len;
};
zfValidate.getById = function(id) {
	return document.getElementById(id);
};

/**
 * 获取元素的位置信息
 * 
 * @param {}
 *            e
 * @return {} {"t":t,'l':l,"b":b,'r':r};
 */
zfValidate.getPos = function(e) {
	var rect = e.getBoundingClientRect();
	var scrollTop = 0;
	var scrollLeft = 0;
	var temp = e;
	while (temp = temp.offsetParent) {
		scrollTop += temp.scrollTop;
		scrollLeft += temp.scrollLeft;
	}

	var t = rect.top + scrollTop;
	var l = rect.left + scrollLeft;
	var r = rect.right + scrollLeft;
	var b = rect.bottom + scrollTop;
	return {
		"t" : t,
		'l' : l,
		"b" : b,
		'r' : r
	};
};
/**
 * 将srcE移到targetE后面
 * 
 * @param {}
 *            srcE
 * @param {}
 *            targetE
 */
zfValidate.moveToPos = function(srcE, targetE) {
	var targetpostion = zfValidate.getPos(targetE);
	srcE.style.zIndex = 9;
	//srcE.style.position = "absolute";
	srcE.style.display="inline";
	srcE.style.top = targetpostion.t - 3;// -
											// srcE.currentStyle.borderTopWidth
											// - srcE.style.marginTop;
	srcE.style.left = targetpostion.r + 8;// - srcE.currentStyle.borderLeftWidth -
										// srcE.style.marginLeft;
};
zfValidate.getSelectedCount = function(j, es) {
	if (!es) {
		return 0;
	}
	
	var types = zfValidate.inputType(es[j]);
	var c = 0;

	if (types.isSelect) {
		for (var i = 0; i < es[j].options.length; i++) {
			if (es[j].options[i].selected) {
				c++;
			}
		}
		return c;
	} else {
		for (var i = 0; i < es.length; i++) {
			if (es[i].checked) {
				c++;
			}
		}
		return c;
	}
	return c;

};

/**
 * 信息提示框的关闭按钮动作处理类
 * 
 * @param {}
 *            obj
 * @param {}
 *            closeObj
 */
zfValidate.closeHandler = function(obj, closeObj) {
	this.h = function() {
		zfValidate.rmEle(obj);
	};
};

/**
 * 
 * @param {}
 *            e element
 * @return {}
 */
zfValidate.inputType = function(e) {
	return {
		'isSelect' : e.tagName == "SELECT",
		'isCheckbox' : e.tagName == "INPUT" && e.type == 'checkbox',
		'isRadio' : e.tagName == "INPUT" && e.type == 'radio'
	};
};

zfValidate.setVfP = function(clrSpace) {
	zfValidate.vf.clrSpace = clrSpace;
};
/**
 * validate all
 */
zfValidate.validate = function() {
	zfValidate.setVfP(true);
	zfValidate.vf.vType = "a";
	return zfValidate.vf.vBf(new zfValidate.f.Dft());
};
/**
 * validate form eg:zfValidate.validateForm('formname1', 'formname2', ... );
 */
zfValidate.validateForm = function () {
	zfValidate.setVfP(true);
	zfValidate.vf.vType = "f";
	return zfValidate.vf.vBf(new zfValidate.f.Form(arguments));
};
/**
 * validate element eg:zfValidate.validateElement(element1, element2, ... );
 */
zfValidate.validateElement = function() {
	zfValidate.setVfP(false);
	zfValidate.vf.vType = "e";
	return zfValidate.vf.vBf(new zfValidate.f.Ele(arguments));
};
/**
 * validate id eg:zfValidate.validateId('id1', 'id2', ... );
 */
zfValidate.validateId = function() {
	zfValidate.setVfP(true);
	zfValidate.vf.vType = "i";
	return zfValidate.vf.vBf(new zfValidate.f.Id(arguments));
};
/**
 * validate name eg:zfValidate.validateName('name1', 'name2', ... );
 */
zfValidate.validateName = function() {
	zfValidate.setVfP(true);
	zfValidate.vf.vType = "n";
	return zfValidate.vf.vBf(new zfValidate.f.Name(arguments));
};
/**
 * remove all validator form validatorFactory
 */
zfValidate.removeAll = function() {
	zfValidate.vf.rmAll();
};
/**
 * baseHandler
 */
zfValidate.bh = zfValidate.C.ext({
	setV:function(v)
	{
		this.v = v;
		return this;
	},
	setE:function(e)
	{
		this.e = e;
		return this;
	},
	setF:function(f)
	{
		this.f = f;
		return this;
	},
	setVal:function(val)
	{
		this.val = val;
		return this;
	},
	setIndex:function(index)
	{
		this.index = index;
		return this;
	},
	needHandle:function()
	{
		if (this.e.style.display == 'none' || this.e.disabled)//对于不可见的元素,不处理
        {
            return false;
        }
        return true;
	},
	render:function(cls, msg, closeCls) {
			var types = zfValidate.inputType(this.e);
			var div = document.createElement("div");
			
			var msgId = this.f.getMsgId(this.e);
			if (msgId) {
				zfValidate.getById(msgId).appendChild(div);
			} else {
				if (types.isCheckbox || types.isRadio) {
					zfValidate.moveToPos(div, this.f.es[this.f.es.length - 1]);
				} else {
					zfValidate.moveToPos(div, this.e);
				}
				this.e.parentNode.appendChild(div);
				zfValidate.vf.msgs.push({"msg":div,"ele":this.e});
			}
			
			div.id = this.e[zfValidate.Conf.proNameOfMsgId];
			div.className = cls;
			div.innerHTML = msg;
			
			//var close = document.createElement("div");
			//div.appendChild(close);
			
			//close.className = closeCls;
			//close.innerHTML = "X";
			//close.title = zfValidate.close;
			
			//zfValidate.Util.addEventHandler(close, "click", new zfValidate.closeHandler(div, close).h);
	}
});
zfValidate.text = zfValidate.bh.ext({
	h:function()
	{
		if (this.needHandle()) {
			this.render(zfValidate.Conf.errCls, this.v.getI18(this.f.label), "talentClose");
			zfValidate.addCls(this.e, zfValidate.Conf.errInputCls);
		}
	}
});
zfValidate.alert = zfValidate.bh.ext({
	h:function()
	{
		if (this.needHandle()){
			zfValidate.addCls(this.e, zfValidate.Conf.errInputCls);
	        alert(this.v.getI18(this.f.label));
		}
	}
});
zfValidate.tip = zfValidate.bh.ext({
	h:function()
	{
		var tipMsg = this.v.getTip(this.e,this.f,this.v,this.val,this.index);
		if (this.needHandle() && tipMsg) {
			this.render(zfValidate.Conf.tipCls, tipMsg, "talentClose talentCloseTip");
		}
	}
});
/**
 * @author Tanyaowu
 * @version 2.0.0
 * @date 2011-8-13
 * BaseValidator
 */
zfValidate.BV = zfValidate.C.ext({
	init:function() {
		this.fs = [];
		this.i18ps = [];
		this.isInFactory = false;// 本验证器是否已经在验证器工厂中了.false:不在工厂中;true:已在工厂中.
		this.clrSpace = zfValidate.Conf.clrSpace;
	},
	initI18n : function(m) {
		!this.i18n ? this.i18n = m : null;
		return this;
	},
	setI18 : function(m) {
		this.i18n = m;
		return this;
	},
	setClrSpace:function(c){
		this.clrSpace = c;
	},
	
	/**
	 * 
	 * @param {} s 需要被验证的串，根据配置此串有可能清除了两边的空格
	 * @param {} i index 当前元素序号，从0开始
	 * @param {} es elements
	 * @param {} f Field
	 * @return {Boolean} true:验证通过
	 */
	v : function(s, i, es, f) {
		return true;
	},
	/**
	 * 
	 * @param {} f Field
	 */
	doAfterAdd : function(f) {
	},
	/**
	 * 当移除后做些事情,子类视情况实现该函数,如Required验证器,需要去掉后面的红星号
	 */
	doBeforeRm : function(f) {
		this.clrFErr(f);
	},
	/**
	 * 
	 * @param {} fl filter
	 * @return {Boolean}
	 */
	vBf : function(fl) {
		var ret = true;
		for (var i = 0; i < this.fs.length; i++) {
			var es = this.fs[i].es;
			if (es) {
				for (j = 0; j < es.length; j++) {
					if (zfValidate.vf.invalidEs.ttCons(es[j])){  //没有通过前面的验证器
						continue;
					}
					
					/** 不需要验证或者验证通过则继续下一个元素的处理 */
					if (!fl.f(es[j])) {  //被过滤了，不需要验证
						if (zfValidate.Conf.clearOtherError){
							this.clrEleErr(es[j]);
						}
						continue;
					}
					
					var types = zfValidate.inputType(es[j]);
					
					var sv = es[j].value;
					
					if (this.clrSpace && (!types['isSelect'] && !types['isCheckbox'] && !types['isRadio'])) {
						sv = zfValidate.trim(sv);
						if(!["e"].ttCons(zfValidate.vf.vType)) {
							es[j].value = sv;
						}
					}
					
					if (types['isRadio'] || types['isCheckbox']){
						this.clrFErr(this.fs[i]);
					}else{
						this.clrEleErr(es[j]);
					}
					if(this.v(sv, j, es, this.fs[i])) {  //验证通过
						this.handTip(es[j], this.fs[i], sv, j);
						continue;
					} else {
						zfValidate.vf.invalidEs.push(es[j]);
						this.handErr(es[j], this.fs[i], sv, j);
						ret = false;
					}
				}
			}
		}
		return ret;
	},

	/**
	 * 移除字段 用法:xx.rm("name1", "name2", "name3"...);
	 */
	rm : function() {
		return this._rm("name", arguments);
	},
	/**
	 * 移除字段 用法:xx.rmId("id1", "id2", "id3"...);
	 */
	rmId : function() {
		return this._rm("id", arguments);
	},
	_rm:function(type, args){
		for (var i = 0; i < args.length; i++) {
			for (var j = 0; j < this.fs.length; j++) {
				var f = false;
				if (typeof args[i] != "string"){
					f = (this.fs[j] && this.fs[j] == args[i]);
				} else {
					f = (this.fs[j] && this.fs[j][type] == args[i]);
				}
				
				if (f) {
					this.doBeforeRm(this.fs[j]);
					this.fs[j] = null;
				}
			}
		}
		this._rmNull();
		return this;
	},
	
	/**
	 * 移除所有字段 用法:xxValidator.rmAll();
	 */
	rmAll : function() {
		this.fs = [];
	},
	_rmNull : function(){
		var temp = [];
		for (var i = 0; i < this.fs.length; i++) {
			if (this.fs[i] != null ) {
				temp.push(this.fs[i]);
			}
		}
		
		this.fs = temp;
	},
	/**
	 * 将要验证的字段加到验证器中 用法:xx.add("name1", "name2", "name3"...);
	 */
	add : function() {
		return this._addF('name', arguments);
	},
	/**
	 * 将要验证的字段加到验证器中 用法:xx.addId("id1", "id2", "id3"...);
	 */
	addId : function() {
		return this._addF('id', arguments);
	},
	_addF : function(type, arg){
		for (var i = 0; i < arg.length; i++) {
			var f = null;
			if (type == 'id'){
				if (this._c('name',zfValidate.getById(arg[i]).name) && this._c('id', arg[i])) {
					f = new zfValidate.Field("", null, arg[i]);
				}
			} else {
				isStr = (typeof arg[i] == 'string');
				var fg = false;
				if (isStr){
					fg = this._c('name', arg[i]);
				}else{
					fg = (this._c('name', arg[i].name) && this._c('id', arg[i].id));
				}
				if (fg) {
					if (isStr) {
						f = new zfValidate.Field("", arg[i]);
					} else {
						f = arg[i];
					}
				}
			}
			
			if (f != null){
				this.fs[this.fs.length] = f;
				for (var j = 0; j < f.es.length; j++) {
					if (f.es[j].tt_addedEvent){
						continue;
					}
					f.es[j].tt_addedEvent = true;
					this.attachE(f.es[j]);
				}
				
				if (!this.isInFactory)// 必要时添加验证器到工厂中
				{
					zfValidate.vf.add(this);
					this.isInFactory = true;
				}
				this.doAfterAdd(f);
			}
		}
		return this;
	},
	/**
	 * 
	 * @param {} proName
	 * @param {} value
	 * @return {Boolean}
	 */
	_c:function(proName, value) {
		if (!value) {
			return true;
		}
		
		for (var i = 0; i < this.fs.length; i++) {
			if (proName){
				if (this.fs[i][proName] == value) {
					return false;
				}
			}else{
				if (this.fs[i] == value) {
					return false;
				}
			}
			
		}
		return true;
	},
	/**
	 * 处理没有验证通过的对象,例如对这个对象进行选中,将焦点转到该对象,修改该对象的样式等
	 */
	handErr : function(e, f, val, j) {
		var h = zfValidate.instanceByClass("zfValidate." + zfValidate.Conf.errorStyle);
		h.setV(this).setE(e).setF(f).setVal(val).setIndex(j).h();
	},
	handTip : function(e, f, val, j) {
		var h = zfValidate.instanceByClass("zfValidate." + zfValidate.Conf.tipStyle);
		h.setV(this).setE(e).setF(f).setVal(val).setIndex(j).h();
	},
	/**
	 * 验证不通过时，获取提示给用户的信息
	 * 
	 * @param label
	 */
	getI18 : function(label) {
		ret = zfValidate.getI18S(this.i18n, [label], 0);
		return zfValidate.getI18S(ret, this.i18ps, 1);
	},
	
	/**
	 * 
	 * @param {} e element
	 * @param {} f field
	 * @param {} v validator
	 * @param {} val
	 * @param {} index
	 * @return {}
	 */
	getTip : function(e,f,v,val,index) {
		return zfValidate.i18DftOk;
	},
	/**
	 * 清空field的错误
	 */
	clrFErr : function(f) {
		var es = f.es;
		for (i =0; i< es.length; i++) {
			this.clrEleErr(es[i]);
		}
	},
	clrEleErr:function(e){
		if (e){
			zfValidate.rmCls(e, zfValidate.Conf.errInputCls);
			zfValidate.rmEle(zfValidate.getById(e[zfValidate.Conf.proNameOfMsgId]));
		}
		
	},
	/**
	 * 获取本验证器所验证的所有element
	 */
	getEs : function(){
		es = [];
		for (i =0; i< this.fs.length; i++) {
			for (var j=0;j<this.fs[i].es.length;j++){
				es = es.concat(this.fs[i].es[j]);
			}
		}
		return es;
	},
	/**
	 * 对html element作些额外的处理，如加验证事件
	 * @param {} e html element
	 */
	attachE:function(e){
		if (zfValidate.Conf.errorStyle == 'alert') {
			return;
		}
		var _hr = function(e) {
			this.h = function() {
				if (zfValidate.Conf.errorStyle == 'alert') {
					return;
				}
				zfValidate.validateElement(e);
			},
			this.talent_getId = function(){
				return zfValidate.Conf.eventId;
			};
		};
		
		var types = zfValidate.inputType(e);
		var hd = new _hr(e).h;
		for (var x in zfValidate.Conf.validateOn) {
			var evt = zfValidate.Conf.validateOn[x];
			if (types.isCheckbox || types.isRadio) {
				if (evt == 'change'){
					zfValidate.Util.addEventHandler(e, evt, hd);
					break;
				}
				continue;
			}
			
			if (types.isSelect && e.multiple != true) {
				if (evt == 'change'){
					zfValidate.Util.addEventHandler(e, 'blur', hd);
					break;
				}
				continue;
			}
			
			zfValidate.Util.addEventHandler(e, evt, hd);
		}
	}
});
zfValidate.Field = zfValidate.C.ext({
	init : function(label, name, id, e) {
		this.label = label;
		this.name = name == '' ? null : name;
		this.id = null;
		this.msgId = null;
		es = [];
		
		if(e){
			es.push(e);
		} 
		if (name) {
			es = document.getElementsByName(name);
		}
		if (id) {
			es = [];
			this.id = id;
			e = zfValidate.getById(id);
			if (e) {
				es.push(e);
			}
		}
		for (var i=0;i<es.length;i++) {
			 seq = zfValidate.vf.seq++;
			 !es[i][zfValidate.Conf.proNameOfMsgId] ? es[i][zfValidate.Conf.proNameOfMsgId] = zfValidate.Conf.proNameOfMsgId + seq : null;
			 !es[i][zfValidate.Conf.proNameOfReqStarId] ? es[i][zfValidate.Conf.proNameOfReqStarId] = zfValidate.Conf.proNameOfReqStarId + seq : null;
		}
		this.es = es;
		
	},
	setMsgId:function(id) {
		this.msgId = id;
		return this;
	},
	getMsgId:function(e) {
		return this.msgId;
	}
});
zfValidate.f.Dft = zfValidate.C.ext({
	init:function()
	{
		this.p = [];
		var a = arguments;
		if(a.length != 0){
			for (var i =0; i < a[0].length; i++){
				this.p.push(a[0][i]);
			}
		}
	},
	/**
	 * @return true:表示需要验证,false:表示被过滤了,不需要验证.
	 */
	f:function(e)
	{
		return true;//默认都需要验证
	}
});
zfValidate.f.Form = zfValidate.f.Dft.ext({
	f:function(e)
	{
		return e.form && this.p.ttCons(e.form.name);
	}
});
zfValidate.f.Ele = zfValidate.f.Dft.ext({
	f:function(e)
	{
		return this.p.ttCons(e);
	}
});
zfValidate.f.Id = zfValidate.f.Dft.ext({
	f:function(e)
	{
		return this.p.ttCons(e.id);
	}
});
zfValidate.f.Name = zfValidate.f.Dft.ext({
	f:function(e)
	{
		return this.p.ttCons(e.name);
	}
});
zfValidate.RV = zfValidate.BV.ext({
set : function(regex, i18n) {
	this.regex = regex;
	this.i18ps[0] = i18n;
	return this;
},

v : function(s) {
	this.initI18n(zfValidate.i18Regex);
	return (!s) || this.regex.test(s);
}
});
zfValidate.ReqV = zfValidate.BV.ext({
v : function(s, i, es, f) {
	this.setI18(zfValidate.i18Req);
	if (es[i].tagName && es[i].tagName == "SELECT") {
		this.setI18(zfValidate.i18Req1);
	}
	return (s);
},
/**
 * 在字段后面加上星号
 */
doAfterAdd : function(f) {
	this._super(f);
	//this.addStar(f);
},
/**
 * 当注销后做些事情,子类视情况实现该函数,如Required验证器,需要去掉后面的红星号
 */
doBeforeRm : function(f) {
	this._super(f);
	this.clearStar(f); // 清空星号
},
/**
 * 添加星号
 */
addStar : function(f) {
	for (var i = 0; i < f.es.length; i++) {
		zfValidate.insertAfter(f.es[i], zfValidate.getI18S("<span id='{0}' class='{1}'>*</span>", [f.es[i][zfValidate.Conf.proNameOfReqStarId], zfValidate.Conf.reqStarCls]));
	}
},
/**
 * 清空星号
 */
clearStar : function(f) {
	for (i = 0; i< f.es.length; i++) {
		zfValidate.rmEle(zfValidate.getById(f.es[i][zfValidate.Conf.proNameOfReqStarId]));
	}
}
});
zfValidate.DV = zfValidate.BV.ext({
    set:function(pattern)
    {
    	this.pattern = pattern;
    	this.i18ps[0] = pattern;
    	return this;
    },
    /**
     * 说明:日期的验证代码是直接摘自validation-framework.js,但略作修改
     */
    v:function(s)
    {
        if (!s)
        {
           return true;//不验证为空的串
        }
        this.initI18n(zfValidate.i18Datetime);
        
        var dateP = this.pattern;//params[0];
        var MONTH = "mm";
        var DAY = "dd";
        var YEAR = "yyyy";
        var orderMonth = dateP.indexOf(MONTH);
        var orderDay = dateP.indexOf(DAY);
        var orderYear = dateP.indexOf(YEAR);
        var f = true;
        var dateReg = null;
        
        if ((orderDay < orderYear && orderDay > orderMonth)) {
            var iDelim1 = orderMonth + MONTH.length;
               var iDelim2 = orderDay + DAY.length;
               var delim1 = dateP.substring(iDelim1, iDelim1 + 1);
               var delim2 = dateP.substring(iDelim2, iDelim2 + 1);
               if (iDelim1 == orderDay && iDelim2 == orderYear) {
                dateReg = /^(\\d{2})(\\d{2})(\\d{4})$/;
               } else if (iDelim1 == orderDay) {
                dateReg = new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$");
               } else if (iDelim2 == orderYear) {
                dateReg = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$");
               } else {
                dateReg = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$");
               }
        
               var matched = dateReg.exec(s);
               if(matched != null) {
                if (!this._c(matched[2], matched[1], matched[3])) {
                       f =  false;
                }
               } else {
                   f =  false;
               }
           } else if ((orderMonth < orderYear && orderMonth > orderDay)) { 
            var iDelim1 = orderDay + DAY.length;
               var iDelim2 = orderMonth + MONTH.length;
               var delim1 = dateP.substring(iDelim1, iDelim1 + 1);
               var delim2 = dateP.substring(iDelim2, iDelim2 + 1);
               if (iDelim1 == orderMonth && iDelim2 == orderYear) {
                dateReg = /^(\\d{2})(\\d{2})(\\d{4})$/;
               } else if (iDelim1 == orderMonth) {
                dateReg = new RegExp("^(\\d{2})(\\d{2})[" + delim2 + "](\\d{4})$");
               } else if (iDelim2 == orderYear) {
                dateReg = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})(\\d{4})$");
               } else {
                dateReg = new RegExp("^(\\d{2})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{4})$");
               }
               var matched = dateReg.exec(s);
            if(matched != null) {
                if (!this._c(matched[1], matched[2], matched[3])) {
                    f = false;
                   }
               } else {
                f = false;
               }
           } else if ((orderMonth > orderYear && orderMonth < orderDay)) {
            var iDelim1 = orderYear + YEAR.length;
               var iDelim2 = orderMonth + MONTH.length;
               var delim1 = dateP.substring(iDelim1, iDelim1 + 1);
        
               var delim2 = dateP.substring(iDelim2, iDelim2 + 1);
               if (iDelim1 == orderMonth && iDelim2 == orderDay) {
                dateReg = /^(\\d{4})(\\d{2})(\\d{2})$/;
               } else if (iDelim1 == orderMonth) {
                dateReg = new RegExp("^(\\d{4})(\\d{2})[" + delim2 + "](\\d{2})$");
               } else if (iDelim2 == orderDay) {
                dateReg = new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})(\\d{2})$");
               } else {
                dateReg = new RegExp("^(\\d{4})[" + delim1 + "](\\d{2})[" + delim2 + "](\\d{2})$");
               }
            var matched = dateReg.exec(s);
               if(matched != null) {
                if (!this._c(matched[3], matched[2], matched[1])) {
                       f =  false;
                   }
               } else {
                   f =  false;
               }
           } else {
               f =  false;
           }
        return f;
    },
    _c:function(d, m, y)
    {
		if (d < 1 || d > 31)
		{
			return false;
		}
		if (m < 1 || m > 12)
		{
			return false;
		}
		
		if ([4,6,9,11].ttCons(m) && (d > 30))
		{
			return false;
		}
		if (m == 2)
		{
			var leap = (y % 4 == 0 && (y % 100 != 0 || y % 400 == 0));
			if (d>29 || (d == 29 && !leap))
			{
				return false;
			} 
	    }
		return true;
	}
});
zfValidate.NV = zfValidate.BV.ext({
	v:function(s)
	{
		this.initI18n(zfValidate.i18Num);
		return this.isNum(s);
	},
	isNum:function(s)
	{
		return (!s) || (!isNaN(s) && (s.indexOf('.') != (s.length - 1)) );
	}
});
zfValidate.IV = zfValidate.NV.ext({
	v:function(s)
	{
		this.initI18n(zfValidate.i18Int);
		return this.isInt(s);
	},
	isInt:function(s)
	{
		if (this.isNum(s))
		{
			var t = (s % 10) + "";
			return (!s) || t.indexOf(".") == -1;
		}
		return false;
	}
});
zfValidate.NRV = zfValidate.NV.ext({
	init:function(){
		this._super();
		this.needAddNumV = true;
	},
	/**
	 * 设置最小值,"--"表示无穷小
	 */
	setMin:function(min)
	{
		this.min = min;
		if (min == '--')//无穷小
		{
			this.i18n = zfValidate.i18NumRangeMin;
			this.i18ps[0] = this.max;
			
		}else
		{
			this.i18ps[0] = min;
		}
	},
	/**
	 * 设置最大值,"++"表示无穷大
	 */
	setMax:function(max)
	{
		this.max = max;
		if (max == '++')
		{
			this.i18n = zfValidate.i18NumRangeMax;
			this.i18ps[0] = this.min;
		}
		else
		{
			if (this.min == "--")
			{
				this.i18ps[0] = max;
			}
			else
			{
				this.i18ps[1] = max;
			}
		}
	},
	set:function(min, max)
	{
		if(arguments.length == 2) {
			this.setMin(min);
			this.setMax(max);
		} else {
			this.exp = min;
			this.i18n = zfValidate.i18NumRangeExp;
			this.i18ps[0] = this.exp;
		}
		return this;
	},
	_rm:function(type,args){
		this.numV ? this.numV._rm(type,args) : null;
		this.numV = null;
		return this._super(type,args);
	},
	v:function(s)
	{
		this.initI18n(zfValidate.i18NumRange);
		if (!this.numV && this.needAddNumV){
			this.numV = new zfValidate.NV();
			for (i=0;i<this.fs.length; i++) {
				this.numV.add(this.fs[i]);
			}
		}
		
		if (!s || !this.isNum(s))//如果不是数字，就让数字验证器来验证
		{
			return true;
		}
		if (this.exp){
			return eval(zfValidate.parRngExp(s, this.exp));
		}
		
		try 
		{
			if (this.max == "++" && this.min == "--")
			{
				return true;
			}
			
			
			if (this.max == "++")
			{
				if (eval(s) < eval(this.min))
				{
					return false;
				}else 
				{
					return true;
				}
			}
			
			if (this.min == "--")
			{
				if (eval(s) > eval(this.max))
				{
					return false;
				}else 
				{
					return true;
				}
			}
			
			if (eval(s) > eval(this.max) || eval(s) < eval(this.min))
			{
				return false;
			}
			return true;
		}
		catch (e)
		{
			return false;
		}
	}
});
zfValidate.LV = zfValidate.NRV.ext({
	init:function(){
		this._super();
		this.needAddNumV = false;
	},
	getTip:function(e,f,v,val,index) {
		var len = zfValidate.getStrLen(val);
		if(this.max != '++' && !this.exp && !['f','a'].ttCons(zfValidate.vf.vType)) {
			return zfValidate.getI18S(zfValidate.i18LenTip, [len, this.max - len]);
		} else {
			return zfValidate.i18DftOk;
		}
	},
	v:function(s)
	{
		var len = zfValidate.getStrLen(s);
		var r = this._super(len+"");
		if(!this.exp){
			v = len - this.max;
			if (v > 0) {  //超长了
				this.i18n = zfValidate.i18LenMin;
				this.i18ps[0] = this.max;
				this.i18ps[1] = v;
			} else if ((v = this.min - len) > 0){ //长度不够
				this.i18n = zfValidate.i18Len;
				this.i18ps[0] = this.min;
				this.i18ps[1] = v;
			}
		} else {
			this.i18n = zfValidate.i18LenExp;
			this.i18ps[0] = this.exp;
		}
		return r;
	}
});
zfValidate.SCV = zfValidate.NRV.ext({
	init:function(){
		this._super();
		this.needAddNumV = false;
	},
	getTip:function(e,f,v,val,index){
//		if (f.es.length > index + 1 &&  !['e','i'].ttCons(zfValidate.vf.vType)) {
//			return null;
//		}
		return this._super(e,f,v,val,index);
	},
	v:function(s, j, es, f)
	{
		var types = zfValidate.inputType(es[j]);
		
		if (es.length > j + 1 && !['e','i'].ttCons(zfValidate.vf.vType) && (types.isCheckbox || types.isRadio)) {
			return true; //只判断一次
		}
		
		var count = zfValidate.getSelectedCount(j, es);
		var r = this._super(count+"");
		if(!this.exp){
			v = count - this.max;
			if (v > 0) {  //选 多了
				this.i18n = zfValidate.i18SelectCountMin;
				this.i18ps[0] = this.max;
				this.i18ps[1] = v;
			} else if ((v = this.min - count) > 0){ //少选了
				this.i18n = zfValidate.i18SelectCount;
				if (this.min == 1){
					this.i18n = zfValidate.i18SelectCount_1;
				}
				
				this.i18ps[0] = this.min;
				this.i18ps[1] = v;
			}
		} else {
			this.i18n = zfValidate.i18SelectCountExp;
			this.i18ps[0] = this.exp;
		}
		return r;
	}
});
zfValidate.CV = zfValidate.BV.ext({
	/**
	 * @param cmpType
	 *            比较类型 'n':数字比较; 'v':字符串比较
	 * @param oper
	 *            比较符,可以为'<','<=','==','!=','>','>='
	 * @param fOrV
	 *            被比较的字段或值
	 * @param showCmpVal
	 *            是否显示被比较的值 举例: var field1 = new zfValidate.Field("用户名", "loginName"); new
	 *            zfValidate.CV().set('n','>',field1);//要求添加此验证器的字段必须大于field1的值
	 */
	set : function(cmpType, oper, fOrV, showCmpVal) {
		this.cmpType = cmpType;
		this.oper = oper;
		this.cmpF = null;   //comparedField
		this.cmpV = null;
		this.i18ps[0] = zfValidate.operMap[this.oper];
		this.showCmpVal = true;  //默认为true
		if (arguments.length == 4) {
			this.showCmpVal = showCmpVal;
		}
		if (["string",'number'].ttCons(typeof fOrV)) {
			this.cmpV = fOrV;
			if (typeof fOrV == 'number'){
				this.cmpType = 'n';
			}else if (typeof fOrV == "string" ){
				this.cmpType = 'v';
			}
		} else {
			this.cmpF = fOrV;
			this.i18ps[1] = fOrV.label;
		}
		
		if (!this.showCmpVal) {
			this.i18n = zfValidate.i18Compare;
		} else if (cmpType == 'n' && this.cmpF) {
			this.i18n = zfValidate.i18NumCompare;
		} else if (cmpType == 'v' && this.cmpF) {
			this.i18n = zfValidate.i18StrCompare;
		} else if (cmpType == 'n' && this.cmpV) {
			this.i18n = zfValidate.i18NumValueCompare;
		} else if (cmpType == 'v' && this.cmpV) {
			this.i18n = zfValidate.i18StrValueCompare;
		} else {
			alert("error occured:talent-validate'zfValidate.CV not support the compare type '" + cmpType + "'");
		}
		return this;
	},
	v : function(str, index) {
		if (!str) {
			return true;
		}

		var cmpV;
		if (this.cmpV) {
			cmpV = this.cmpV;
			if (this.showCmpVal) {
				this.i18ps[1] = "<span class='talentComparedValue'>"
						+ cmpV + "</span>";
			} else {
				this.i18ps[2] = "";
			}
		} else {
			var es = this.cmpF.es;
			if (es.length == 0){
				return true;
			}
			
			cmpV = (es[index])
					? es[index].value
					: es[es.length - 1].value;
			if (cmpV == null || cmpV == "") {
				return true;
			}
		
			if (this.showCmpVal) {
				this.i18ps[2] = "<span class='talentComparedValue'>" + cmpV + "</span>";
			} else {
				this.i18ps[2] = "";
			}
		}

		var s;
		if (this.cmpType == "n")// 是数字比较
		{
			var numV = zfValidate.vf.num;
			if ((!numV.v(str)) || (!numV.v(cmpV+"")))// 不是数字
			{
				return true;  //不是数字则留给数字验证器去验证
			}

			s = str + this.oper + cmpV;
		} else if (this.cmpType == "v")// 是字符串比较
		{
			s = "\"" + str + "\"" + this.oper + "\"" + cmpV + "\"";
		}
		return eval(s) == true;
	}
});
zfValidate.VF = zfValidate.C.ext({
	init : function() {
		this.vArr = [];  //validatorArray
		this.ip = new zfValidate.RV().set(/^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])$/).setI18(zfValidate.i18Ip);
		this.email = new zfValidate.RV().set(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).setI18(zfValidate.i18Email);
		this.postcode = new zfValidate.RV().set(/^[1-9]\d{5}(?!\d)$/).setI18(zfValidate.i18Postcode);
		this.tel = new zfValidate.RV().set(/^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/).setI18(zfValidate.i18Tel);
		this.idcard = new zfValidate.RV().set(/^\d{15}$|^\d{17}[\d,x,X]{1}$/).setI18(zfValidate.i18Idcard);
		this.req = new zfValidate.ReqV();
		this.int = new zfValidate.IV();  
		this.num = new zfValidate.NV();
		
		this.msgs = [];
		this.seq = 0;            //序列号
	},
	/**
	 * 移除所有验证器
	 */
	rmAll : function() {
		for ( var i = 0; i < this.vArr.length; i++) {
			this.vArr[i].isInFactory = false;
			this.vArr[i].rmAll();
		}
		this.vArr = [];
	},
	/**
	 * 用法:zfValidate.vf.add(validator1, validator2,validator3... ...
	 * validatorx);
	 */
	add : function() {
		var startIndex = this.vArr.length;
		for ( var i = 0; i < arguments.length; i++) {
			this.vArr[i + startIndex] = arguments[i];
		}
	},
	vBf : function(f) {
		var ret = true;
		this.invalidEs = [];
		this.msgs = [];
		for ( var i = 0; i < this.vArr.length; i++) {
			if (!this.vArr[i].vBf(f)) {
				ret = false;
			}
		}
		return ret;
	},
	resizeWindow : function() {
		for (var i = 0; i < zfValidate.vf.msgs.length; i++) {
			zfValidate.moveToPos(zfValidate.vf.msgs[i].msg, zfValidate.vf.msgs[i].ele);
		}
	}
});
zfValidate.vf = new zfValidate.VF();
zfValidate.Util.addEventHandler(window, "resize", zfValidate.vf.resizeWindow);
