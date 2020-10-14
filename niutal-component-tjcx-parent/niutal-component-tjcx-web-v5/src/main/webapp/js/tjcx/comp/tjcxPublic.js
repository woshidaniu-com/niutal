var tjxmList;// 统计项目
var tjbbPzModel;// 某一项目下的统计报表各数据
var allYsfList;// 所有运算符
var delayTime = 100;// 弹层延迟时间
var DYH = "_DYHBS_";// 单引号
var KG = "_KGBS_";// 空格
var IS_NULL = "is null";
var NULL_VALUE = "表示该值为空";
var IS_NOT_NULL = "is not null";
var NOT_NULL_VALUE = "表示该值不为空";

function onShowTjxm() {
	var gnmk = jQuery('#gnmk').val();
	jQuery.ajax( {
		type : "post",
		url : _path + "/niutal/tjcx/tjxm/cxxx.zf?timestamp=" + new Date().getTime()+"&gnmk="+gnmk,
		dataType : "json",
		success : function(data) {
			tjxmList = data;
			setTjxm();// 设置统计项目
			if (jQuery("#fhbs").length > 0 && jQuery("#fhbs").val() == "1") {// 由上一个页面返回状态
				var xmdm = jQuery("#xmdm").val();
				if (xmdm != null && xmdm != "") {// 默认项目不为空，如：统计查询返回的情况
					var _curXmdm = jQuery("#tjxmUl li a").has(
							"[name='curXmdm'][value='" + xmdm + "']");
					if (_curXmdm != null && _curXmdm.length > 0) {
						_curXmdm.click();
					}
				}
			} else {// /默认显示第一个项目
				if(jQuery("#tjxmUl li a").length > 0){
					jQuery("#tjxmUl li a").get(0).click();
				}
			}
		}
	});
}

// 设置统计项目
function setTjxm() {
	var sHtml = "";
	var kzlx = jQuery("#kzlx").val();
	for ( var i = 0; i < tjxmList.length; i++) {
		var o = tjxmList[i];
		var qyfw = o.qyfw;
		if(qyfw == "1"){//仅统计查询
			if(kzlx == "2"){
				continue;
			}
		}else if(qyfw == "2"){//仅统计报表
			if(kzlx == "1"){
				continue;
			}
		}
		
		
		sHtml += "<li>";
		sHtml += "<a href='#tab"+i+"' onclick='tjxm(this);return false;' role='tab' data-toggle='tab'>";
		sHtml += "<input type='hidden' name='curXmdm' value='" + o.xmdm + "'/>";
		sHtml += "<input type='hidden' name='curXmxsms' value='" + o.xmxsms + "'/>";
		sHtml += o.xmmc;
		sHtml += "</a>";
		sHtml += "</li>";
	}
	jQuery("#tjxmUl").html(sHtml);
}

/**
 * 点击统计项目
 * 
 */
function tjxm(obj) {
	jQuery("#tjxmUl a").removeClass("active");
	jQuery(obj).addClass("active");

	var xmdm = jQuery(obj).find("[name='curXmdm']").val();
	if (xmdm == null || xmdm == "") {
		alert("请选择统计项目！");
		return;
	}
	var kzlx = jQuery("#kzlx").val();
	jQuery("#xmdm").val(xmdm);
	jQuery("#del").html("删除");
	setTjxmByXmdm(xmdm,kzlx);
}

/**
 * 
 * @param xmdm 
 * @param kzlx
 * @return
 */
function setTjxmByXmdm(xmdm,kzlx) {
	var zdxsms = jQuery("#zdxsms").val();
	jQuery.getJSON(_path + "/niutal/tjcx/tjbb/getSjByXmdm.zf?kzlx=" + kzlx + "&timestamp=" + new Date().getTime(), {
		"xmdm" : xmdm,"zdxsms" : zdxsms
	}, function(data) {
		tjbbPzModel = data;
		allYsfList = tjbbPzModel['allYsfList'];
		var xmxsms = jQuery("#tjxmUl li a.cur input[name='curXmxsms']").val();

		setInit(xmxsms);

		if (jQuery("#fhbs").length > 0 && jQuery("#fhbs").val() == "1") {// 由上一个页面返回状态
				var gltj = jQuery("#gltj").val();
				if (gltj != null && gltj != "") {// //默认过滤条件不为空，如：统计查询返回的情况
				kzzsGltj(gltj);// 设置过滤条件

			jQuery("#gltj").val("0");// 返回标识重置，由上一个页面返回状态只设置一次
		}
	}

}	);
}


/**
 * 统计项目相关设置重置，如：查询快照修改时
 * 
 * @return
 */
function tjxmReset() {
	var xmdm = jQuery("#xmdm").val();
	if (xmdm == null || xmdm == "") {
		return;
	}
	jQuery.getJSON(_path + "/niutal/tjcx/tjbb/getSjByXmdm.zf?timestamp=" + new Date().getTime(),{
		"xmdm" : xmdm
	}, function(data) {
		tjbbPzModel = data;
	});
}

// 字段的显示设置
function setCxzd() {
	var sHtml = createCxzdHtml();
	jQuery("#cxzdDiv").html(sHtml);
}

// 增加查询字段条件块
function addCxzdTjk() {
	var xmdm = jQuery("#xmdm").val();
	if (xmdm == null || xmdm == "") {
		alert("请选择统计项目！");
		return;
	}

	var sHtml = createCxzdHtml();

	jQuery("#cxzdDiv").append(sHtml);
}

// 生成查询字段代码,一个条件块
function createCxzdHtml() {
	var sHtml = "";
	var cxzdList = tjbbPzModel['cxzdList'];
	if (cxzdList == null || cxzdList.length == 0) {// 无配置字段
		return sHtml;
	}

	// 查询字段显示
	sHtml += '<div class="panel panel-gray gltj1">';
	sHtml += '<a href="javascript:;" onclick="cxzdTjkYc(this);return false;" class="position_a  pull-right p-r-10">';
	sHtml += '<i class="fa fa-remove" title="删除过滤条件"></i></a>';
	sHtml += '<div class="panel-body">';
	sHtml += '<ul class="tag-list">';

	for ( var i = 0; i < cxzdList.length; i++) {
		var o = cxzdList[i];
		
		sHtml += "<li><a href='javascript:;' data-toggle='modal' data-target='#myModal' name='" + o.zdmc
				+ "' onclick='cxzdDiv(this,\"" + o.zdmc
				+ "\");return false;'>" + o.zdsm + " <i class='fa fa-cog'></i></a></li>";
	}
	sHtml += "</ul>";
	sHtml += "<div class='clearfix'></div>";
	sHtml += "</div>";

	// 已选条件
	sHtml += '<div class="panel-footer gltj1_2">';
	sHtml += '<span class="float_l padding-t10 fwhite">已选条件</span>';
	sHtml += '<ul class="tag-list tag-list1">';
	sHtml += "</ul>";
	sHtml += '<div class="clearfix"></div>';
	sHtml += "</div>";
	
	sHtml += "</div>";

	return sHtml;
}

// 查询字段弹层
function cxzdDiv(obj, zdmc) {
	jQuery(".hover").removeClass("hover");
	jQuery(obj).addClass("hover");
	
	var _cxzdTjk = jQuery(obj).parents(".gltj1");

	var xmdm = jQuery("#xmdm").val();
	if (xmdm == null || xmdm == "") {
		$.alert("请选择统计项目！");
		return;
	}
	flzdz = {};// 父类字段值,已选择的所有条件。{data:[{tjdm:'wjqk',tjgx:'='},{tjdm:'pjcj',tjgx:'='}]}
	flzdz.data = [];
	_cxzdTjk.find(".panel-footer ul li[name='yxtj']").each(
			function(index) {
				var zdJsonObj = {};
				zdJsonObj.zdmc = jQuery(this).find("[name='zdmc']").val();
				zdJsonObj.cxzdViewDms = jQuery(this).find(
						"[name='cxzdViewDms']").val();
				flzdz.data.push(zdJsonObj);
			});

	jQuery.post(_path + "/niutal/tjcx/cxzd/cxzd.zf?timestamp=" + new Date().getTime(),
					{
						"zdmc" : zdmc,
						"xmdm" : xmdm,
						"flzdz" : JSON.stringify(flzdz)
					},
					function(data) {
						var cxzdModel = data;
						var zdsm = cxzdModel.zdsm;
						var flzdmc = cxzdModel.flzdmc;
						var sHtml = "";
						if (flzdmc != null && !flzdmc == "") {// 父类字段名称
							var _yxtjLi = _cxzdTjk
									.find(".panel-footer ul li[name='yxtj']:has([name='zdmc'][value='"
											+ flzdmc + "'])");
							var cxzdViewDms = _yxtjLi.find("[name='cxzdViewDms']")
									.val();
							if (cxzdViewDms == undefined) {
								var flzdsm = jQuery("a[name='" + flzdmc + "']").eq(
										0).text();
								sHtml = "请先选择[" + flzdsm + "]";
							} else {
								cxzdModel.flzdz = cxzdViewDms;
								sHtml = createCxzdDivHtml(cxzdModel);
							}
	
						} else {
							sHtml = createCxzdDivHtml(cxzdModel);
						}

					//jQuery(".gltj1").css("z-index", "1");
					//jQuery(".gltj1").css("position", "relative");
					//jQuery(".set").hide(delayTime);
					
					jQuery("#modalTitle").html(zdsm);
					jQuery("#modalBody").html(sHtml);
					//TODO 呵呵
					//_cxzdTjk.find(".set .set1").html(zdsm);// 设置头显示
//					_cxzdTjk.find(".set ul").html(sHtml);// 设置列表
					setDivInit(obj, cxzdModel);// 弹层设置初值
					//_cxzdTjk.find(".set").show();
					//_cxzdTjk.css("z-index", "2");
					
					//绑定运算符事件
					_cxzdTjk.find("[name='ysfdm']").change(function(){
						var ysfdm = jQuery(this).val();
						var _curInput = _cxzdTjk.find("[name='cxzdViewDm']");

						if(ysfdm == IS_NULL){
							_curInput.val(NULL_VALUE);
							_curInput.attr("disabled",true);
						}else if(ysfdm == IS_NOT_NULL){
							_curInput.val(NOT_NULL_VALUE);
							_curInput.attr("disabled",true);
						}else{
							_curInput.attr("disabled",false);
							if(_curInput.val() == NULL_VALUE || _curInput.val() == NOT_NULL_VALUE){
								_curInput.val("");
							}
						}
					});
					_cxzdTjk.find("[name='ysfdm']").change();
				});
}

/*
 * 弹层设置初值
 */
function setDivInit(obj, cxzdModel) {
	var _cxzdTjk = jQuery(obj).parents(".gltj1");// 条件块
//	_cxzdTjk.find(".set .set3").hide();//隐藏全选按钮
	jQuery("#checkAll").hide();

	var qzlx = cxzdModel.qzlx;
	var zdmc = cxzdModel.zdmc;
	if (qzlx == "1" || qzlx == "2" || qzlx == "3" || qzlx == "15") {// 复选框格式
		var cxzdViewDms = _cxzdTjk.find("[name='zdmc'][value='" + zdmc + "']")
				.nextAll("[name='cxzdViewDms']").val();
		if (cxzdViewDms != null && cxzdViewDms != "") {
			var arr = cxzdViewDms.split(",");
			jQuery.each(arr, function(index, value) {
				jQuery("#modalBody input:checkbox[name='cxzdViewDm'][value='"
								+ value + "']").attr("checked", "checked");
			});
		}
		if(jQuery("#modalBody input:checkbox").length > 0){//包含选择项
			jQuery("#checkAll").show();//显示全选按钮
			jQuery("#checkAll :input").attr("checked",false);//全选按钮置成未选择状态
		}
	}
}

/*
 * 查询字段弹层，全选按钮
 * obj:全选按钮
 */
function cxzdDivQxBtn(obj){
	var checked = jQuery(obj).prop("checked");
	
	jQuery("#modalBody input:checkbox").prop("checked",checked);
}

// 生成查询字段弹层内容
function createCxzdDivHtml(cxzdModel) {
	var qzlx = cxzdModel.qzlx;
	var qzfw = cxzdModel.qzfw;
	var zdsm = cxzdModel.zdsm;
	var zdmc = cxzdModel.zdmc;
	var flzdz = cxzdModel.flzdz;// 父类字段值
	var ysfList = cxzdModel.ysfList;

	if (qzfw == "") {
		return "无相关数据";
	}

	var sHtml = "";
	sHtml += "<input type='hidden' name='zdsm' value='" + zdsm + "'/>";
	sHtml += "<input type='hidden' name='zdmc' value='" + zdmc + "'/>";
	sHtml += "<input type='hidden' name='qzlx' value='" + qzlx + "'/>";
	sHtml += "<input type='hidden' name='qzfw' value='" + qzfw + "'/>";

	/*
	 * '取值类型： 1.固定值，格式为：1:男,2:女。显示格式：复选框 2:数据库取值,“表名:代码,名称”,。显示格式：复选框
	 * 3:类名全称#方法名|参数:代码,名称，其中，若有参数，则参数仅支持一个String类型。显示格式：复选框
	 * 
	 * 11,普通文本框; 12,数字文本框; 13,日期选择框;';
	 */
	if (qzlx == "1" || qzlx == "2" || qzlx == "3" || qzlx == "15") {
		if (qzfw != null) {
			var qzfws = qzfw.split(",");
			if (qzfws != null) {
				for ( var i = 0; i < qzfws.length; i++) {
					if (qzfws[i] == null) {
						continue;
					}
					var dm = qzfws[i].split(":")[0];
					var mc = qzfws[i].split(":")[1];
					if (mc == null || mc == "null" || mc == "") {
						mc = dm;
					}

					sHtml += '<label class="radio-inline margin-r10">';
					sHtml += '<input type="checkbox" name="cxzdViewDm" value="'+dm+'">';
					sHtml += mc;
					sHtml += "<input type='hidden' name='cxzdViewMc' value='"
					+ mc + "' >";
					sHtml += '</label>';
//					sHtml += "<label>";
//					sHtml += "<input type='checkbox' name='cxzdViewDm' value='"
//							+ dm + "' >";
//					sHtml += mc;
//					sHtml += "<input type='hidden' name='cxzdViewMc' value='"
//							+ mc + "' >";
//					sHtml += "</label>";
				}
			}
		}
	} else if (qzlx == "11") {// 11,普通文本框;
		sHtml += createCxzdDivYsf(ysfList);
		sHtml += "<input type='text' name='cxzdViewDm' maxlength='50' onblur='limitZf(this)' onkeyup='limitZf(this)' onafterpaste='limitZf(this)' allowtransparency='false'   value='' >";
	} else if (qzlx == "12") {// 12,数字文本框;
		sHtml += createCxzdDivYsf(ysfList);
		sHtml += "<input type='text' name='cxzdViewDm' maxlength='10' onblur='limitSz(this)' onkeyup='limitSz(this)' onafterpaste='limitSz(this)' allowtransparency='false'  value='' >";
	} else if (qzlx == "13") {// 13,日期选择框;，数据库字段为字符格式；
		sHtml += createCxzdDivYsf(ysfList);
		sHtml += "<input type='text' name='cxzdViewDm' onfocus='WdatePicker({dateFmt:\""
				+ qzfw + "\"})' value='' >";
	} else if (qzlx == "14") {// 14.日期选择框，数据库字段为日期格式；
		sHtml += createCxzdDivYsf(ysfList);
		sHtml += "<input type='text' name='cxzdViewDm' onfocus='WdatePicker({dateFmt:\""
				+ qzfw + "\"})' value='' >";
	}
	return sHtml;
}

/*
 * 生成查询条件块运算符下拉框
 */
function createCxzdDivYsf(ysfList) {
	var sHtml = "";
	sHtml += "<select name='ysfdm' class=''>";
	for ( var i = 0; i < ysfList.length; i++) {
		var o = ysfList[i];
		sHtml += "<option value='" + o.ysfdm + "'>" + o.ysfmc + "</option>";
	}
	sHtml += "</select>";
	return sHtml;
}

/*
 * 查询字段弹层取消
 */
//function cxzdDivQx(obj) {
//	jQuery(obj).parents(".set").hide(delayTime);
//
//	jQuery(obj).parents(".gltj1").css("z-index", "1");
//
//}

/*
 * 查询字段弹层确定
 */
function cxzdDivQd(obj) {
	var _cxzdTjk = jQuery(".hover").parents(".gltj1");// 条件块
	var _cxzdDiv = jQuery("#modalBody");
	var zdsm = _cxzdDiv.find("[name='zdsm']").val();
	var zdmc = _cxzdDiv.find("[name='zdmc']").val();
	var qzlx = _cxzdDiv.find("[name='qzlx']").val();
	//var _cxzdDiv = jQuery(obj).parents(".set");
	var _cxzdYxtjUl = _cxzdTjk.find(".panel-footer ul");
	var sHtml = createCxzdkHtml(obj);
	var flag = false;
	// 删除原来的已选 条件块
	if (qzlx == "1" || qzlx == "2" || qzlx == "3" || qzlx == "15") {
		_cxzdYxtjUl.find("[name='zdmc'][value='" + zdmc + "']").each(
				function(index) {
					flag = true;
					jQuery(this).parents("li[name='yxtj']").after(sHtml);// 在原条件块后插入，不改变位置
					var _yxtjk = _cxzdTjk.find(".panel-footer");
					cxzdYxtjScDg(_yxtjk, zdmc);// 递归删除子类
					jQuery(this).parents("li[name^='yxtj']").remove();
				});
	}
	
	if (sHtml != "") {
		if (!flag) {
			_cxzdYxtjUl.append(sHtml);// 增加新的条件块
		}
	}
	jQuery("#myModal").modal('hide');
	//_cxzdDiv.hide(delayTime);
}

/*
 * 生成一个查询字段块代码html obj为确定链接a，已选条件
 */
function createCxzdkHtml(obj) {
	var _cxzdDiv = jQuery("#modalBody");
	var _cxzdTjk = jQuery(obj).parents(".panel-primary");
	var zdsm = _cxzdDiv.find("[name='zdsm']").val();
	var zdmc = _cxzdDiv.find("[name='zdmc']").val();
	var qzlx = _cxzdDiv.find("[name='qzlx']").val();
	var qzfw = _cxzdDiv.find("[name='qzfw']").val();

	var sHtml = "";

	var cxzdViewDms = "";
	var cxzdViewMcs = "";
	var ysfdm = "";
	var flag = false;
	var sSql;
	var bitandModel={};//类型为15的对象
	bitandModel.zdmc=zdmc;
	if (qzlx === "1" || qzlx === "2" || qzlx === "3") {
		// 选中项
		_cxzdDiv.find("[name='cxzdViewDm']:checked").each(
				function(index) {
					if (flag) {
						cxzdViewDms += ",";
						cxzdViewMcs += ",";
					} else {
						flag = true;
					}
					cxzdViewDms += DYH + jQuery(this).val() + DYH;
					cxzdViewMcs += jQuery(this).parent().find(
							"[name='cxzdViewMc']").val();
				});
		sSql = zdmc + " in (" + cxzdViewDms + ")";
	} else {
		cxzdViewDms = _cxzdDiv.find("[name='cxzdViewDm']").val();
		ysfdm = _cxzdDiv.find("[name='ysfdm']").val();
		if(ysfdm == IS_NULL){
			sSql = zdmc + " " + IS_NULL;// 
		}else if(ysfdm == IS_NOT_NULL){
			sSql = zdmc + " " + IS_NOT_NULL;// 
		} else{
			if (qzlx == "11") {// 包含关系
				if (ysfdm == "like") {
					sSql = zdmc + " " + ysfdm + " " + DYH + "%" + cxzdViewDms + "%"
							+ DYH;// ////////////////////////////////////
				} else {
					sSql = zdmc + " " + ysfdm + " " + DYH + cxzdViewDms + DYH;// 
				}
			} else if (qzlx == "12") {// 数字类型
				sSql = "to_number(" + zdmc + ") " + ysfdm + " " + DYH + ""
						+ cxzdViewDms + "" + DYH;// ////////////////////////////////////
			} else if (qzlx == "13") {// ,日期选择框，数据库字段为字符格式；
				sSql = zdmc + " " + ysfdm + " " + DYH + cxzdViewDms + DYH;
			} else if (qzlx == "14") {// 14.日期选择框，数据库字段为日期格式；
				var sjgs = changeSjgs(qzfw);// 时间格式，转化成oracle形式
				sSql = "to_char(" + zdmc + "," + DYH + sjgs + DYH + ") " + ysfdm
						+ " " + DYH + cxzdViewDms + DYH;
			} else if (qzlx =="15"){//15二进制 存储数据
				cxzdViewDms="";
				flag = false;
				var cxzdViewDmsX="";
				_cxzdDiv.find("[name='cxzdViewDm']:checked").each(
						
						function(index) {
							if (flag) {
								cxzdViewDms += " or ";
								cxzdViewMcs += ",";
								cxzdViewDmsX +=",";
							} else {
								flag = true;
							}
							cxzdViewDmsX += jQuery(this).val();
							cxzdViewDms += "bitand("+zdmc+","+DYH + jQuery(this).val() + DYH+")>0";
							cxzdViewMcs += jQuery(this).parent().find(
									"[name='cxzdViewMc']").val();
						});
				bitandModel.cxzdViewDmsX=cxzdViewDmsX;
				bitandModel.cxzdViewMcs=cxzdViewMcs;
				sSql = "("+cxzdViewDms + ")";
			}
		}

	}
	if(qzlx =="15"){//二进制的特殊处理
		sHtml += createCxzdkHtmlForSql2(sSql, bitandModel);
	}else{
		sHtml += createCxzdkHtmlForSql(sSql, cxzdViewMcs);
	}
	return sHtml;
}

/*
 * 根据sql生成条件块
 */
function createCxzdkHtmlForSql(oldSql, cxzdViewMcs) {
	var sSql = oldSql;
	var cxzdList = tjbbPzModel['cxzdList'];
	if (sSql == null || sSql == "") {
		return "";
	}
	var reg = new RegExp(DYH, "g");
	sSql = sSql.replace(reg, "");// 去掉单引号标识，以便显示
	var sHtml = "";
	var zdmc = "";
	var ysfdm = "";
	var ysfmc = "";
	var zdsm = "";
	var cxzdViewDms = "";
	zdmc = sSql;
	zdmc = jQuery.trim(zdmc);
	if (zdmc.indexOf("to_char(") > -1) {
		zdmc = zdmc.split(")")[0];
	} else {
		zdmc = zdmc.split(" ")[0];
	}
	sSql = jQuery.trim(sSql);
	sSql = sSql.substring(zdmc.length + 1);// 去掉字段名称
	sSql = jQuery.trim(sSql);
	// 截取运算符代码，运算符名称
	if(sSql == IS_NULL){
		ysfdm = IS_NULL;
		ysfmc = "为空";
	}else if(sSql == IS_NOT_NULL){
		ysfdm = IS_NOT_NULL;
		ysfmc = "不为空";
	} else{
		for ( var i = 0; i < allYsfList.length; i++) {
			var o = allYsfList[i];
			var dm = o.ysfdm;
			var mc = o.ysfmc;
			if (sSql != null && sSql.split(" ")[0] == dm) {
				ysfdm = dm;
				ysfmc = mc;
				break;
			}
		}
	}


	sSql = jQuery.trim(sSql);
	if (ysfdm != null && ysfdm != "") {
		sSql = sSql.substring(ysfdm.length);// 去掉运算符代码
	}
	cxzdViewDms = jQuery.trim(sSql);
	if (ysfdm != IS_NULL && ysfdm != IS_NOT_NULL && (cxzdViewDms == "" || cxzdViewDms == "()" || cxzdViewDms == "%%")) {//
		return "";
	}

	// 替换%，以便显示。用于模糊查询方式
	reg = new RegExp("\%", "g");
	cxzdViewDms = cxzdViewDms.replace(reg, "");

	if (ysfdm == "in") {
		cxzdViewDms = cxzdViewDms.replace("(", "").replace(")", "");
		ysfmc = "";
	}
	
	if (cxzdViewMcs == null || cxzdViewMcs == "") {// 弹层中点击确定，此值不为null。复选框模式，不为空串。快照或查询返回，均为null
		cxzdViewMcs = changeZdSql(cxzdViewDms, zdmc);
	}
	var zdmcSjk = getZdmcsjk(zdmc);// 数据库实际字段名，去掉to_number,to_char等
	zdsm = getZdsmByZdmc(zdmcSjk);
	if (ysfdm == "in") {// in包含类型，将多个选项分离成块
		sHtml += "<li name='yxtj' > ";// 总查询块隐藏
		sHtml += "<a href='javascript:void(0);'  style='font-size:11px;'>" +  zdsm + ":"  +  cxzdViewMcs + " ";
	} else {// 普通运算符类型
		sHtml += "<li name='yxtj'> ";
		sHtml += "<a href='javascript:void(0);' style='font-size:11px;'>" +  zdsm + ":" + ysfmc + " " + cxzdViewMcs + " ";
	}
	sHtml += "<i class='glyphicon glyphicon-trash' onclick='cxzdYxtjSc(this);return false;'></i>";
	sHtml += "</a>";
	sHtml += "<input type='hidden' name='zdmc' value='" + zdmc + "'/>";// 查询字段块，代码
	sHtml += "<input type='hidden' name='cxzdViewDms' value='" + cxzdViewDms
			+ "'/>";// 查询字段块，代码值
	sHtml += "<input type='hidden' name='cxzdkValSqlMc' value='&lt;a&gt;" + zdsm + " " + ysfmc + " " + cxzdViewMcs + "&lt;/a&gt;'/>";// 查询字段块，显示范围
	
	sHtml += "<input type='hidden' name='cxzdkValSql' value='" + oldSql + "'/>";// 查询字段块，值代码
	sHtml += "</li>";

	return sHtml;
}

function createCxzdkHtmlForSql2(oldSql, model) {
	
	var zdmcSjk = getZdmcsjk(model.zdmc);// 数据库实际字段名，去掉to_number,to_char等
	var zdsm = getZdsmByZdmc(zdmcSjk);
	var ysfmc="";
	var sHtml="";
	sHtml += "<li name='yxtj' > ";// 总查询块隐藏
	sHtml += "<a href='javascript:void(0);'>" +  zdsm + ":"  +  model.cxzdViewMcs + " ";
	sHtml += "<i class='glyphicon glyphicon-trash' onclick='cxzdYxtjSc(this);return false;'></i>";
	sHtml += "</a>";
	sHtml += "<input type='hidden' name='zdmc' value='" + model.zdmc + "'/>";// 查询字段块，代码
	sHtml += "<input type='hidden' name='cxzdViewDms' value='" + model.cxzdViewDmsX
			+ "'/>";// 查询字段块，代码值
	sHtml += "<input type='hidden' name='cxzdkValSqlMc' value='&lt;a&gt;" + zdsm + " " + ysfmc + " " + model.cxzdViewMcs + "&lt;/a&gt;'/>";// 查询字段块，显示范围
	
	sHtml += "<input type='hidden' name='cxzdkValSql' value='" + oldSql + "'/>";// 查询字段块，值代码
	sHtml += "</li>";

	return sHtml;
}
/*
 * 将sql中字段值，转变成显示字符
 */
function changeZdSql(sSql, zdmc) {
	var qzfw = getQzfwByZdmc(sSql, zdmc);
	if (qzfw != null && qzfw != "null" && qzfw != "") {
		var qzfws = qzfw.split(",");
		if (qzfws != null) {
			for ( var i = 0; i < qzfws.length; i++) {
				if (qzfws[i] == null) {
					continue;
				}
				var dm = qzfws[i].split(":")[0];
				var mc = qzfws[i].split(":")[1];
				if(mc != null && mc != ""){
					sSql = sSql.replace(dm, mc);
				}
			}
		}
	}

	return sSql;
}

// 根据字段名称获取取值范围
function getQzfwByZdmc(sSql, zdmc) {
	var xmdm = jQuery("#xmdm").val();
	if (xmdm == null || xmdm == "") {
		alert("请选择统计项目！");
		return;
	}
	var qzfw = "";
	jQuery.ajax( {
		type : "post",
		async : false,
		url : _path + "/niutal/tjcx/cxzd/cxzd.zf?timestamp=" + new Date().getTime(),
		data : {
			"zdmc" : zdmc,
			"xmdm" : xmdm,
			"cxtjdm" : sSql
		},
		dataType : "json",
		success : function(data) {
			var cxzdModel = data;
			qzfw = cxzdModel.qzfw;
		},
		error : function(err) {
		}
	});
	return qzfw;

}

// 根据字段名称获取字段说明
function getZdsmByZdmc(zdmc) {
	zdmc = getZdmcsjk(zdmc);
	var zdsm = zdmc;
	var cxzdList = tjbbPzModel['cxzdList'];
	for ( var i = 0; i < cxzdList.length; i++) {
		var o = cxzdList[i];
		if (o.zdmc == zdmc) {
			zdsm = o.zdsm;
			break;
		}
	}
	return zdsm;
}

/*
 * 得到数据库实际字段名称 zdmc，可能包含to_number,to_char等
 */
function getZdmcsjk(zdmc) {
	if (zdmc.indexOf("to_number(") > -1) {
		zdmc = zdmc.replace("to_number(", "").replace(")", "");
	}
	if (zdmc.indexOf("to_char(") > -1) {
		zdmc = zdmc.replace("to_char(", "");
		zdmc = zdmc.split(",")[0];
	}
	return zdmc;
}

/*
 * 查询字段条件块移除
 */
function cxzdTjkYc(obj) {
	jQuery(obj).parents(".gltj1").remove();
}

/*
 * 查询字段已选条件删除
 */
function cxzdYxtjSc(obj) {
	var _yxtjk = jQuery(obj).parents("div.gltj1_2");
	var zdmc = jQuery(obj).parents("li[name='yxtj']").find("[name='zdmc']")
			.val();
	var _yxtj_xs = jQuery(obj).parents("li[name='yxtj_xs']");
	if (_yxtj_xs.length > 0) {
		zdmc = jQuery(obj).parents("li[name='yxtj_xs']").find("[name='zdmc']")
				.val();
		cxzdYxtjScDg(_yxtjk, zdmc);
	}
	var _yxtj_xs = jQuery(obj).parents("li[name='yxtj_xs']");
	if (_yxtj_xs.length > 0) {
		cxzdYxtjScLd(_yxtj_xs);
	}

	jQuery(obj).parents("li[name='yxtj']").remove();
}

/*
 * 查询字段递归删除子类代码块，如：删除“学院”，将自动删除级联的“专业，班级”
 */
function cxzdYxtjScDg(_yxtjk, zdmc) {
	var xmdm = jQuery("#xmdm").val();
	if (xmdm == null || xmdm == "") {
		alert("请选择统计项目！");
		return;
	}
	jQuery.ajax( {
		type : "post",
		async : false,
		url : _path + "/niutal/tjcx/cxzd/getChildrenList.zf?timestamp=" + new Date().getTime(),
		data : {
			"zdmc" : zdmc,
			"xmdm" : xmdm
		},
		dataType : "json",
		success : function(data) {
			var cxzdModelList = data;
			for ( var i = 0; i < cxzdModelList.length; i++) {
				var o = cxzdModelList[i];
				var zdmc = o.zdmc;
				_yxtjk.find(
						"li[name^='yxtj']:has([name='zdmc'][value='" + zdmc
								+ "'])").remove();
			}
		},
		error : function(err) {
		}
	});
}

/*
 * 查询字段已选条件删除,子块联动删除总的查询块中内容 _yxtj_bs，当前点击的子查询块,li
 */
function cxzdYxtjScLd(_yxtj_xs) {
	var zdmc = _yxtj_xs.find("[name='zdmc']").val();// 当前点击的字段名称
	var cxzdViewDm = _yxtj_xs.find("[name='cxzdViewDm']").val();// 当前点击的字段代码

	var _yxtj = _yxtj_xs.parent().find(
			"li[name='yxtj']:has(input[name='zdmc'][value='" + zdmc + "'])");// 与当前点击的相对应的总查询块,li

	var _cxzdViewDmsObj = _yxtj.find("[name='cxzdViewDms']");// 总查询块的字段代码
	var _cxzdkValSqlMcObj = _yxtj.find("[name='cxzdkValSqlMc']");// 总查询块的字段名称
	var _cxzdkValSqlObj = _yxtj.find("[name='cxzdkValSql']");// 总查询块的字段值代码
	var _cxzdkTitleObj = _yxtj.find("span.title");// title
	var cxzdViewDms = _cxzdViewDmsObj.val();
	var cxzdkValSqlMc = _cxzdkValSqlMcObj.val();
	var cxzdkValSql = _cxzdkValSqlObj.val();
	var cxzdkTitle = _cxzdkTitleObj.text();
	_yxtj_xs.remove();// 删除当前块
	// ///////对总查询块进行处理，删除掉当前块//////////////////////
	// 查询字段代码处理
	var cxzdViewDmsChange = "";
	var cxzdViewDmsArr = "";
	if (cxzdViewDms != null && cxzdViewDms.indexOf(",") > -1) {// 包含多项
		cxzdViewDmsArr = cxzdViewDms.split(",");
		var cxzdViewDmsFlag = false;
		for ( var i = 0; i < cxzdViewDmsArr.length; i++) {
			if (cxzdViewDmsArr[i] == cxzdViewDm) {
				continue;
			}
			if (cxzdViewDmsFlag) {
				cxzdViewDmsChange += ",";
			} else {
				cxzdViewDmsFlag = true;
			}
			cxzdViewDmsChange += cxzdViewDmsArr[i];
		}
		var cxzdViewMcsChange = changeZdSql(cxzdViewDmsChange, zdmc);
		var cxzdkValSqlMcChange = cxzdkValSqlMc.replace(cxzdkTitle,
				cxzdViewMcsChange);
		var cxzdkValSqlDmsOld = "";// 去除当前字段前
		var cxzdkValSqlDmsChange = "";// 去除当前字段后
		if (cxzdkValSql.indexOf(DYH) > -1) {// 包含单引号
			for ( var i = 0; i < cxzdViewDmsArr.length; i++) {
				if (i > 0) {
					cxzdkValSqlDmsOld += ",";
				}
				cxzdkValSqlDmsOld += DYH + cxzdViewDmsArr[i] + DYH;
			}
			var cxzdViewDmsChangeArr = cxzdViewDmsChange.split(",");
			for ( var i = 0; i < cxzdViewDmsChangeArr.length; i++) {
				if (i > 0) {
					cxzdkValSqlDmsChange += ",";
				}
				cxzdkValSqlDmsChange += DYH + cxzdViewDmsChangeArr[i] + DYH;
			}
		} else {
			cxzdkValSqlDmsOld = cxzdViewDms;
			cxzdkValSqlDmsChange = cxzdViewDmsChange;
		}
		var cxzdkValSqlChange = "";
		cxzdkValSqlChange = cxzdkValSql.replace(cxzdkValSqlDmsOld,
				cxzdkValSqlDmsChange);
		_cxzdkValSqlObj.val(cxzdkValSqlChange);//
		_cxzdViewDmsObj.val(cxzdViewDmsChange);// 总查询块的字段代码
		_cxzdkTitleObj.html(cxzdViewMcsChange);// title显示名称
		_cxzdkValSqlMcObj.val(cxzdkValSqlMcChange);// 名称
	} else {// ///////仅包含一项，直接删除
		_yxtj.remove();
	}
}

// 查询快照设置
function setCxkz() {
	var sHtml = createCxkzHtml();
	jQuery("#cxkzUl").html(sHtml);

	if(jQuery("#cxkzUl li").length == 0){
		jQuery("#cxkz1").hide();
	}
	//jQuery(".tjxm_1 .more a.del").html("删除").attr("title", "删除");
	// cxkzTdxg();
}

//查询快照设置2
function setCxkz2() {
	var sHtml = createCxkzHtml();
	jQuery("#cxkzUl").html(sHtml);
}

// 生成查询快照,html
function createCxkzHtml() {
	var sHtml = "";
	var kzszList = tjbbPzModel['kzszList'];
	if (kzszList == null || kzszList.length == 0) {// 无配置
		return sHtml;
	}
	for ( var i = 0; i < kzszList.length; i++) {
		var o = kzszList[i];
		sHtml += createCxkzLiHtml(o.kzszid, o.szmc, o.sfgy, o.czy);
	}
	return sHtml;
}

// 生成查询快照一个li,html
// sfgy:1为公有
function createCxkzLiHtml(kzszid, szmc, sfgy, czy) {
	var sHtml = "";
	var sfgyTip = "";
	var yxsc = "";// 是否允许删除，若为其他用户创建的公有快照 ，不允许删除
	if (sfgy == "1") {// 共享
		if (jQuery("#czy").val() != czy) {// 其他用户创建的公有快照
			yxsc = "1";
			sfgyTip = "<font color='red'>【共享】</font>";
		} else {
			sfgyTip = "<font color='red'>【共享】</font>";
		}
	}
	sHtml += "<li>";
	sHtml += "<a href='javascript:;' onclick='cxkz(this);return false;' >"
			+ sfgyTip + szmc;
	sHtml += "<input type='hidden' name='kzszid' value='" + kzszid + "'/>";
	sHtml += "<input type='hidden' name='yxsc' value='" + yxsc + "'/>";
	sHtml += " <i class='glyphicon glyphicon-trash hidden'></i>";
	sHtml += "</a>";
	sHtml += "</li>";
	return sHtml;
}

/*
 * 点击查询快照删除
 */
function cxkzScBtn(obj) {
	if (jQuery(obj).html() == "删除") {
		if (jQuery("#cxkzUl li").length > 0) {
			jQuery("#cxkzUl li a .glyphicon").removeClass("hidden");
			jQuery(obj).html("取消");
		}
	} else {
		jQuery("#cxkzUl li a .glyphicon").addClass("hidden");
		jQuery(obj).html("删除");
	}
}

/*
 * 点击查询快照
 */
function cxkz(obj) {
	if (obj == null) {
		return;
	}
	var xmxsms = jQuery("#tjxmUl li a.active input[name='curXmxsms']").val();
	if(jQuery(".tjxm_1 .more .del").length == 0 && xmxsms == "1"){
		//调用统计或查询按钮
		if(jQuery("#tjBtn").length > 0){
			jQuery("#tjBtn").click();
		}else if(jQuery("#cxBtn").length > 0){
			jQuery("#cxBtn").click();
		}		
		return;
	}
	
	var kzszid = jQuery(obj).find("[name='kzszid']").val();
	var yxsc = jQuery(obj).find("[name='yxsc']").val();// 1为不允许删除，其他用户创建的公有快照

	if (jQuery("#del").html() == "删除") {
		jQuery("#cxkzUl li a").removeClass("active");
		jQuery(obj).addClass("active");
		// /////设置值///////////
		var kzszList = tjbbPzModel['kzszList'];
		if (kzszList != null && kzszList.length > 0) {// 无配置
			for ( var i = 0; i < kzszList.length; i++) {
				var o = kzszList[i];
				if (o.kzszid == kzszid) {
					setInitCxkz(o);
					break;
				}
			}
		}
		//调用统计或查询按钮
		if(jQuery("#tjBtn").length > 0){
			jQuery("#tjBtn").click();
		}else if(jQuery("#cxBtn").length > 0){
			jQuery("#cxBtn").click();
		}
	} else {// 删除状态
		if (yxsc == "1") {
			$.alert("共享的快照，不允许删除！");
			return;
		}
		
		jQuery.confirm("您确定要删除吗？",function(r) {
			if (r){
				delCxkz(kzszid);
				if(jQuery("#cxkzUl li").length == 0){
					jQuery("#cxkz1").hide();
				}
			}
		});
		
	}

}

/*
 * 删除查询快照
 */
function delCxkz(kzszid) {
	if (kzszid == null || kzszid == "") {
		return;
	}
	jQuery.ajax( {
		type : "post",
		url : _path + "/niutal/tjcx/kzsz/delete.zf?timestamp=" + new Date().getTime(),
		data : {
			kzszid : kzszid
		},
		dataType : "json",
		success : function(data) {
			if (data == true) {
				jQuery(
						"#cxkzUl li:has([name='kzszid'][value='" + kzszid
								+ "'])").remove();
			}
		}
	});

}

/*
 * 快照设置过滤条件
 */
function kzzsGltj(gltj) {
	jQuery("#cxzdDiv .gltj1_2>ul").empty();
	if (gltj == null || gltj == "") {
		return;
	}
	var reg = new RegExp("'", "g");
	gltj = gltj.replace(reg, DYH);// 去掉单引号标识，以便显示
	jQuery("#cxzdDiv .gltj1:gt(0)").remove();
	var gltjks = gltj.split("or");
	for ( var i = 0; i < gltjks.length; i++) {
		var gltjk = gltjks[i];
		if (i > 0) {
			addCxzdTjk();
		}
		var cxzdkSqls = gltjk.split("and");
		for ( var j = 0; j < cxzdkSqls.length; j++) {
			jQuery(jQuery(".gltj1").get(i)).find(".gltj1_2 ul").append(
					createCxzdkHtmlForSql(cxzdkSqls[j]));
		}
	}
}

/*
 * 查询快照保存弹出框
 */
function cxkzWindow() {
	var xmdm = jQuery("#xmdm").val();
	if (xmdm == null || xmdm == "") {
		$.alert("请选择统计项目！");
		return;
	}

	var kzlx = jQuery("#kzlx").val();
	if(kzlx == "1" || checkTjlb()){
		var title = "快照保存";
		var url = _path + "/niutal/tjcx/kzsz/kzsz.zf?timestamp=" + new Date().getTime();
		$.showDialog(url, title,$.mergeObj(addConfig,{"width":"700px", "height":"300px",
		"modalName":"kzszModel",
		buttons : {
			success : {
				label : "保存",
				className : "btn-primary",
				callback : function() {
					saveCxkz();
					return false;
				}
			},
			cancel : {
				label : "关 闭",
				className : "btn-default"
			}
		}
		}));
		}
}

function cxkzSzBtn(){
	var title = "快照设置";
	var url = _path + "/niutal/tjcx/kzsz/kzxg.zf?timestamp=" + new Date().getTime();
	showDialog(title, 600, 400,url);
}

/*
 * 设置查询快照的值
 */
function setCxkzValue() {
	var bbhxl = jQuery("#demo1 [name='bblzddm']").map(function() {
		return jQuery(this).val();
	}).get().join(",");
	
	var bbzxl = jQuery("#demo2 [name='bblzddm']").map(function() {
		return jQuery(this).val();
	}).get().join(",");

	// 统计项
	var tsx = jQuery("#tjxTbody input:checkbox:checked").map(function() {
		return jQuery(this).val();
	}).get().join(",");

	
	var gltj = getTjlbGltjValue();
	jQuery("#gltj").val(gltj);
	
	jQuery("#bbhxl").val(bbhxl);
	jQuery("#bbzxl").val(bbzxl);
	jQuery("#tsx").val(tsx);

}

/*
 * 得到过滤条件值
 */
function getTjlbGltjValue(){
	// 取过滤条件
	var gltj = "";
	var flag = false;
	jQuery("#cxzdDiv").each(function(index) {
		var tjk = jQuery(this).find("[name='cxzdkValSql']").map(function() {
			return jQuery(this).val();
		}).get().join(" and ");
		if (tjk != null && tjk != "") {
			if (flag) {
				gltj += " or ";
			} else {
				flag = true;
			}
			gltj += tjk;
		}
	});

	var reg = new RegExp(DYH, "g");
	gltj = gltj.replace(reg, "'");// 去掉单引号标识，以便显示
	return gltj;
}

/*
 * 设置查询字段名称
 */
function setGltjmc(){
	
	var gltjmc = "";
	jQuery("#cxzdDiv").each(function(index){
		var gltjmcTemp = "";
		jQuery(this).find("li[name='yxtj']>[name='cxzdkValSqlMc']").each(function(index){
			if(gltjmcTemp != ""){
				gltjmcTemp += " and ";
			}
			if(jQuery(this).val() != null){
				gltjmcTemp += jQuery(this).val();
			}
		});
		if(gltjmcTemp != null && gltjmcTemp != ""){
			if(gltjmc != ""){
				gltjmc += " or ";
			}
			gltjmc += gltjmcTemp;
		}	
	});		
	jQuery("#gltjmc").val(gltjmc);
	
}

/**
 * 是否超级管理员
 * @return
 */
function checkIsCjgly(){
	var result = false;
	var czy = jQuery("#czy").val();
	var cjglys = "," + cjgly + ",";
	if(cjglys.indexOf("," + czy + ",") > -1){
		result = true;
	}
	return result;
}

/*
 * 项目显示模式，各界面元素的隐藏显示 
 */
function setXmxsmsView(xmxsms){
	
	var isCjgly = checkIsCjgly();//是否超级管理员
	if(!isCjgly){
		if(xmxsms == "1"){
			jQuery(".tjxm .tjxm_2,.tjxm_3,.text-right").hide();
			jQuery("#cxkz1 p.more").hide();
			jQuery("#cxkz1").show();
		}else{
			jQuery("#cxkz1 p.more a[name='cxkzszA']").hide();
			jQuery("#cxkz1 p.more").show();
			jQuery(".tjxm #cxkz1,.tjxm_2,.tjxm_3,.text-right").show();
		}
	}else{
		jQuery("#cxkz1 p.more").show();
		jQuery("#cxkz1 p.more a").show();
		jQuery(".tjxm #cxkz1,.tjxm_2,.tjxm_3,.text-right").show();
	}
}

/************统计查询列表，统计列表*****************************************************************************88*/

/*
 * 生成固定显示的条件块代码
 */
function createGdxsTjk(){
	var gltjmc = jQuery("#gltjmc").val();
	if(gltjmc == null || gltjmc == ""){
		return;
	}
	var sHtml = "";
	var gltjmcs = gltjmc.split("or");
	
	if(gltjmcs == null ){
		return;
	}
	var flag = false;
	for ( var i = 0; i < gltjmcs.length; i++) {
		var gltjmcTmp = gltjmcs[i];
		if(gltjmcTmp == null){
			continue;
		}
		var gltjmcTmps =  gltjmcTmp.split("and");
		if(gltjmcTmps == null){
			continue;
		}
		if(flag){
			sHtml +="<li>";
			sHtml +="<a href='javascript:;' >";
			sHtml +="<span><font color='#155fbe'>或者</font></span>&nbsp;";
			sHtml +="</a></li>";
		}else{
			flag = true;
		}
		for ( var j = 0; j < gltjmcTmps.length; j++) {
			var gltjk = jQuery.trim(gltjmcTmps[j]);
			sHtml +="<li>";
			//sHtml +="<span class='title'>";
			sHtml +=gltjk + "&nbsp;";
			//sHtml +="</span>";
			sHtml +="</li>";
		}
	}	

	jQuery("#yxtj").html(sHtml);
	
}

function setInitTjlbGltj(){
	var xmdm = jQuery("#xmdm").val();
	setTjxmByXmdm(xmdm);
	createGdxsTjk();
	if(jQuery("#yxtj li").length > 0){
		jQuery("#oldYxtjDiv").show();
	}
	
//	jQuery("#curTjxmDiv .tjxm_2_title span").click(function(){
//		openCloseTjlbGltj();
//	});
//	
//	jQuery("#tjlbZkgd").click(function(){
//		openCloseTjlbGltj();
//	});	
}

/*
 * 打开、关闭过滤条件
 */
function openCloseTjlbGltj(){
	var addTjk = jQuery("#tjxmAddTjk");
	if(addTjk.css("display") == "none"){
		addTjk.show();
		jQuery(".tjxm .tjxm_4").show();
		jQuery("#cxzdDiv").show();
		jQuery("#tjlbZkgd").removeClass().addClass("more1");
		
		if(jQuery("#curTjxmDiv .gltj1").length == 0){
			addCxzdTjk();
		}
		
	}else{
		addTjk.hide();
		jQuery(".tjxm .tjxm_4").hide();
		jQuery("#cxzdDiv").hide();
		jQuery("#tjlbZkgd").removeClass().addClass("more");
	}
}

