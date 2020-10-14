var sjfwdxList;// 数据范围对象
var IS_QX = false;
var SJFWDX_ID_QX = "1";
var SJFWDX_ID_XY = "2";
var SJFWDX_ID_ZY = "3";
var SJFWDX_ID_BJ = "4";
var SJFWDX_QUERY_NJ = "nj";
// var SJFWDX_QUERY_JG = "jg";

var sjfwListJson = {};// 数据范围列表
jQuery(window).load(function(){
	setInit();
});

function setInit() {
	var js_id = jQuery("#js_id").val();
	
	var zgh = "";
	if(jQuery("input[name='zgh']").length == 1){
		zgh = jQuery("input[name='zgh']").map(function() {
			  return jQuery(this).val();
		}).get().join(',');
	}
	var url = _path + "/xtgl/yhjsSjfwgl_cxSjfwdx.html";
	jQuery.ajax( {
		type : "post",
		url : url,
		data : {js_id:js_id,zgh:zgh},
		dataType : "json",
		success : function(data) {
			sjfwdxList = data;
			setQxBs();
			setSjfwdx();
			bindSjfwdx();
			bindSjfwQueryZy();
			bindSjfwQuery();
			if(IS_QX){
				jQuery("#sjfwdxUl>li a:eq(0)").click();
			}else{
				jQuery("#sjfwdxUl>li a[data-sjfwdx='"+SJFWDX_ID_XY+"']").click();
			}
		}
	});
}

/*
 * 设置数据范围对象
 */
function setSjfwdx() {
	if (sjfwdxList == null || sjfwdxList.length == 0) {
		return;
	}
	for ( var i = 0; i < sjfwdxList.length; i++) {
		var o = sjfwdxList[i];
		var zwmc = o.zwmc;
		var bm = o.bm;
		var sjfwdx_id = o.sjfwdx_id;
		var sHtml = "";
		sHtml += "<li>";
		sHtml += "<a href='javascript:;' data-sjfwdx='" + sjfwdx_id + "'>按" + zwmc
				+ "</a>";
		sHtml += "</li>";
		jQuery("#sjfwdxUl").append(sHtml);

		sHtml = "";
		if (jQuery("#sjfw_" + sjfwdx_id).length == 0) {
			sHtml += "<div id='sjfw_" + sjfwdx_id + "' >";

			sHtml += "<div class='searchtab'>";
			sHtml += "<table width='100%' border='0' >";
			sHtml += "<tbody>";
			sHtml += "<tr>";
			if(sjfwdx_id == SJFWDX_ID_ZY || sjfwdx_id == SJFWDX_ID_BJ ){
				sHtml += "<th>学院</th>";
				sHtml += "<td>";
				sHtml += "<select data-querysjfw='" + SJFWDX_ID_XY + "'>";
				sHtml += "<option value=''>--请选择--</option>";
				sHtml += "</select>";
				sHtml += "</td>";
			}

			if(sjfwdx_id == SJFWDX_ID_BJ ){
				sHtml += "<th>专业</th>";
				sHtml += "<td>";
				sHtml += "<select data-querysjfw='" + SJFWDX_ID_ZY + "'>";
				sHtml += "<option value=''>--请选择--</option>";
				sHtml += "</select>";
				sHtml += "</td>";
				sHtml += "</tr>";
				
				sHtml += "<tr>";
				sHtml += "<th>年级</th>";
				sHtml += "<td>";
				sHtml += "<select data-querysjfw='" + SJFWDX_QUERY_NJ + "'>";
				sHtml += "<option value=''>--请选择--</option>";
				sHtml += "</select>";
				sHtml += "</td>";
			}

			sHtml += "<th>名称</th>";
			sHtml += "<td>";
			sHtml += "<input type='text' name='queryName' maxlength='20' placeholder='名称或拼音首字母'>";
			sHtml += "</td>";
			
			sHtml += "</tr>";
			sHtml += "</tbody>";
			sHtml += "</table>";
			sHtml += "</div>";
			
			sHtml += "<div class='dataRangeBox' id='sjfwdx_listdiv_"+sjfwdx_id+"'>";
			sHtml += "<br/><p style='text-align:center;'>正在查询，请稍等...</p>";
			sHtml += "</div>";
			sHtml += "</div>";
		}
		jQuery("#sjfwlb").append(sHtml);
	}
}

function bindSjfwdx() {
	jQuery("#sjfwdxUl>li>a").click(function(index) {
		jQuery("#sjfwdxUl>li>a").removeClass("currentDiv");
		jQuery(this).addClass("currentDiv");
		var sjfwdx = jQuery(this).data("sjfwdx");
		setSjfwList(sjfwdx);
	});
}

/*
 * 绑定数据范围查询条件事件
 */
function bindSjfwQuery() {
	var _searchTable = jQuery("#sjfwlb div.searchtab table");
	_searchTable.find("select,input[name='queryName']").change(function() {
		var curSjfwdx = getCurSjfwdx();// 当前被选中的数据范围对象
		var _dataRangeBoxDiv = jQuery("#sjfw_" + curSjfwdx + " .dataRangeBox");
		var _curSearchTable = jQuery("#sjfw_" + curSjfwdx + " div.searchtab table");
		
		var _sjfwdx_listul = jQuery("#sjfwdx_listul_" + curSjfwdx);
		var _tempLi = _sjfwdx_listul.find("li");
		_tempLi.hide();
		if(curSjfwdx == SJFWDX_ID_ZY || curSjfwdx == SJFWDX_ID_BJ){
			// 学院
			var bmdm = _curSearchTable.find("select[data-querysjfw='" + SJFWDX_ID_XY + "']").children('option:selected').val();
			bmdm = bmdm || "";			
			if(bmdm != ""){
				 _tempLi = _tempLi.filter("[ls_bmdm='"+bmdm+"']");
			}
		}
		if(curSjfwdx == SJFWDX_ID_BJ){
			// 专业
			var zydm = _curSearchTable.find("select[data-querysjfw='" + SJFWDX_ID_ZY + "']").children('option:selected').val();
			zydm = zydm || "";
			if(zydm != ""){
				 _tempLi = _tempLi.filter("[ls_zydm='"+zydm+"']");
			}

			// 年级
			var njdm_id = _curSearchTable.find("select[data-querysjfw='" + SJFWDX_QUERY_NJ + "']").children('option:selected').val();
			njdm_id = njdm_id || "";
			if(njdm_id != ""){
				 _tempLi = _tempLi.filter("[ls_njdm='"+njdm_id+"']");
			}
			
		}
		var queryStr = _curSearchTable.find("input[name='queryName']").val();// 文本查询框
		queryStr = queryStr || "";
		if(queryStr != ""){
			queryStr = queryStr.toUpperCase();
			_tempLi = _tempLi.has("label>span:contains('"+queryStr+"'),p.selector-name:contains('"+queryStr+"')");
		}
		
		_tempLi.removeAttr("style");
		initDataRangeEvent(curSjfwdx);
	});
}


/*
 * 绑定数据范围查询条件事件,班级中学院专业联动
 */
function bindSjfwQueryZy() {
	var _searchTable = jQuery("#sjfw_" + SJFWDX_ID_BJ +" div.searchtab table");
	_searchTable.find("select[data-querysjfw='" + SJFWDX_ID_XY + "']").change(function() {
		var bmdm = jQuery(this).children('option:selected').val();
		
		var _querysjfwZy = _searchTable.find("select[data-querysjfw='" + SJFWDX_ID_ZY + "']");
		_querysjfwZy.find("option:gt(0)").remove();
		
		var sjfwList = getSjfwList(SJFWDX_ID_ZY);
		var sHtml = "";
		for ( var i = 0; i < sjfwList.length; i++) {
			var o = sjfwList[i];
			var zddm = o.zddm;
			var zdmc = o.zdmc;
			var ls_bmdm = o.ls_bmdm;
			if(bmdm != ls_bmdm){
				continue;
			}
			var zwpy = o.zwpy;
			if(zwpy != null && zwpy != ""){
				zwpy = zwpy.substr(0,1);
				zdmc = zwpy + "-" +zdmc;
			}
			sHtml += "<option value='"+zddm+"'>"+zdmc+"</option>";
		}
		_querysjfwZy.append(sHtml);
		
	});
}

function setSjfwList(sjfwdx) {

	var _sjfw = jQuery("#sjfw_" + sjfwdx);
	jQuery("#sjfwlb>div[id^='sjfw_']:not([id='sjfw_" + sjfwdx + "'])").hide();
	jQuery("#sjfw_" + sjfwdx).show();
	if (_sjfw.find("li").length > 0) {
		return;
	}
	
	// 判断最新选中的对象，若已更改，则直接跳出
	var curSjfwdx = getCurSjfwdx();
	if(sjfwdx != curSjfwdx){
		return;
	}
	var tips = alertTips(null,null,"正在查询，请稍等...");
	setTimeout(function(){
		setSjfwQuerySelect(sjfwdx);// 设置查询条件
		setSjfwListHtml(sjfwdx);
		tips.close();
	},100);
}

/*
 * 设置查询条件
 */
function setSjfwQuerySelect(curSjfwdx) {
	if(curSjfwdx == SJFWDX_ID_ZY || curSjfwdx == SJFWDX_ID_BJ){
		setSjfwQuerySelectHtml(SJFWDX_ID_XY);
	}
	if(curSjfwdx == SJFWDX_ID_BJ){
	// setSjfwQuerySelectHtml(SJFWDX_ID_ZY);
		setSjfwQuerySelectHtml(SJFWDX_QUERY_NJ);
	}
}

function setSjfwQuerySelectHtml(sjfwdx) {
	var sjfwList = getSjfwList(sjfwdx);
	var _querySelect = jQuery("#sjfwlb div.searchtab table select[data-querysjfw='" + sjfwdx + "']");
	if(_querySelect.find("option") <= 1){
		return;
	}

	var sHtml = "";
	for ( var i = 0; i < sjfwList.length; i++) {
		var o = sjfwList[i];
		var zddm = o.zddm;
		var zdmc = o.zdmc;
		
		if(sjfwdx == SJFWDX_ID_XY){
			var zwpy = o.zwpy;
			if(zwpy != null && zwpy != ""){
				zwpy = zwpy.substr(0,1);
				zdmc = zwpy + "-" +zdmc;
			}
		}
		
		sHtml += "<option value='"+zddm+"'>"+zdmc+"</option>";
	}
	
	_querySelect.append(sHtml);
}

function setSjfwListHtml(sjfwdx) {
	var sjfwList = getSjfwList(sjfwdx);

	var sHtml = "";
//	sHtml += "<div class='dataRangeBox' id='sjfwdx_listdiv_"+sjfwdx+"'>";
	sHtml += "<ul class='lisnavBox' id='sjfwdx_listul_"+sjfwdx+"'>";
	for ( var i = 0; i < sjfwList.length; i++) {
		var o = sjfwList[i];
		var zddm = o.zddm;
		var zdmc = o.zdmc;
		var zwpy = o.zwpy;
		var cydm_id_bmlb = o.cydm_id_bmlb;
		var ls_bmdm = o.ls_bmdm;
		var ls_zydm = o.ls_zydm;
		var ls_njdm = o.ls_njdm;
		cydm_id_bmlb = cydm_id_bmlb || "";
		ls_bmdm = ls_bmdm || "";
		ls_zydm = ls_zydm || "";
		ls_njdm = ls_njdm || "";
		zwpy = zwpy || "";
		sHtml += "<li ";
		if(sjfwdx == SJFWDX_ID_XY){
			sHtml += " cydm_id_bmlb='"+cydm_id_bmlb+"'";
		}else if(sjfwdx == SJFWDX_ID_ZY){
			sHtml += " ls_bmdm='"+ls_bmdm+"'";
		}else if(sjfwdx == SJFWDX_ID_BJ){
			sHtml += " ls_bmdm='"+ls_bmdm+"'";
			sHtml += " ls_zydm='"+ls_zydm+"'";
			sHtml += " ls_njdm='"+ls_njdm+"'";
		}
		
		sHtml += ">";
		sHtml += "<p class='selector-name'>"+zwpy+"</p>";
		sHtml += "<label>";
		sHtml += "<input type='checkbox' value='"+zddm+"'>";
		sHtml += "<span>"+zdmc+"</span></label>";
		sHtml += "</li>";
	}
	sHtml += "</ul>";
//	sHtml += "</div>";

	// 判断最新选中的对象，若先更改，则不操作
	var curSjfwdx = getCurSjfwdx();
	if(sjfwdx == curSjfwdx){
		jQuery("#sjfwdx_listdiv_" + sjfwdx).html(sHtml);
		initDataRangeEvent(sjfwdx);
		initValue(sjfwdx);
	}
}

/*
 * 设置已经保存的值
 */
function initValue(sjfwdx){
	var valueArr ;
	for ( var i = 0; i < sjfwdxList.length; i++) {
		var o = sjfwdxList[i];
		var bm = o.bm;
		var sjfwdx_id = o.sjfwdx_id;
		if(sjfwdx == sjfwdx_id){
			valueArr = o.sjfwList;
			break;
		}
	}
	if(IS_QX && (sjfwdx == SJFWDX_ID_XY || sjfwdx == SJFWDX_ID_ZY || sjfwdx == SJFWDX_ID_BJ )){
	//	jQuery("#sjfwdx_listul_"+sjfwdx+" input:checkbox").attr("checked","checked");
	}else{
		if(valueArr != null && valueArr.length > 0){
			$.each( valueArr, function(i, value){
				  jQuery("#sjfwdx_listul_"+sjfwdx+" input:checkbox[value='"+value+"']").attr("checked","checked");
				});
		}
	}

}

/*
 * 设置是否全校标识
 */
function setQxBs(){
	for ( var i = 0; i < sjfwdxList.length; i++) {
		var o = sjfwdxList[i];
		var bm = o.bm;
		var sjfwdx_id = o.sjfwdx_id;
		
		if(sjfwdx_id == SJFWDX_ID_QX) {//全校
			var qxValueArr = o.sjfwList;
			if(qxValueArr != null && qxValueArr.length > 0){
				IS_QX = true;				
				jQuery("#sjfwdx_listul_"+SJFWDX_ID_QX+" li input:checkbox").attr("checked","checked");
				break;
			}
		}
	}
}

/*
 * 初始化字母列表控件
 */
function initDataRangeEvent(sjfwdx){
	
	var _dataRangeBoxDiv = jQuery("#sjfwdx_listdiv_" + sjfwdx );
	
	// 重置控件
	_dataRangeBoxDiv.prev(".listNav").remove();
	jQuery("#sjfwdx_listul_" + sjfwdx + ">li").removeClass();
	
	// 初始化A-Z字符筛选组件
	_dataRangeBoxDiv.listnav({
	    includeAll: true,
	    allText:'全部',
	    listSelector: 'ul>li:visible',// 受索引控制的显示隐藏的区域
	    filterSelector: '.selector-name',// 用于排序的引用
	    includeNums: true,
	    noMatchText: '没有数据.',
	    includeOther: true,
	    otherText:"其他",
	    incudeMatch: true,
	    matchSelector: ':checkbox:checked',//
        matchText: "已选",
	    removeDisabled: false,
	    onClick:function(letter){
		}
	});
}

/**
 * 根据数据范围对象获取范围列表
 * 
 * @param sjfwdx
 * @return
 */
function getSjfwList(sjfwdx){	
	if(sjfwListJson[sjfwdx]){
		return sjfwListJson[sjfwdx];
	}
	var sjfwdxObj = {};
	
	if(sjfwdx == SJFWDX_QUERY_NJ){
		sjfwdxObj["bm"] = "niutal_xtgl_njdmb";
		sjfwdxObj["zddm"] = "njmc";
		sjfwdxObj["zdmc"] = "njmc";
	}else{
		for ( var i = 0; i < sjfwdxList.length; i++) {
			var o = sjfwdxList[i];
			var bm = o.bm;
			var sjfwdx_id = o.sjfwdx_id;
			if (sjfwdx_id == sjfwdx) {
				sjfwdxObj["bm"] = o.bm;
				sjfwdxObj["zddm"] = o.zddm;
				sjfwdxObj["zdmc"] = o.zdmc;
				break;
			}
		}
		if (!sjfwdxObj) {
			return;
		}
	}
	
	var url = _path + "/xtgl/yhjsSjfwgl_cxSjfwList.html";
	jQuery.ajax( {
		type : "post",
		url : url,
		data : sjfwdxObj,
		async : false,
		dataType : "json",
		success : function(data) {
			var sjfwList = data;			
			sjfwListJson[sjfwdx] = sjfwList;
		}
	});
	
	return sjfwListJson[sjfwdx];
}

/*
 * 获取当前被选中的数据范围对象
 */
function getCurSjfwdx(){
	var curSjfwdx = jQuery("#sjfwdxUl>li a.currentDiv").data("sjfwdx");
	return curSjfwdx;
}

function checkAll(obj){
	var curSjfwdx = getCurSjfwdx();
	var checkAttr = jQuery(obj).attr("checked");
	if(checkAttr == undefined){
		checkAttr = false;
	}
	var _allCheck = jQuery("#sjfwdx_listul_" + curSjfwdx + ">li:visible input:checkbox").attr("checked",checkAttr);
}

function getSjfwDataArr(sjfwdxObj){
	var sjfwdx = sjfwdxObj.sjfwdx_id;
	var sjfwArr = sjfwdxObj.sjfwList;	
	var sjfwdxDataArr = [];	
	jQuery.each(sjfwArr,function(i,n){
		var key = n;
		var value = ""; 
		var sjfwList=getSjfwList(sjfwdx);
		for ( var i = 0; i < sjfwList.length; i++) {
			var o = sjfwList[i];
			var zddm = o.zddm;
			var zdmc = o.zdmc;
			if (zddm == key) {
				value = zdmc;
				break;
			}
		}
		var keyvalueObj = {k:key,v:value};
		sjfwdxDataArr.push(keyvalueObj);
	});	
	return sjfwdxDataArr;
	
	
}

function saveSjfw(){
	var tips = alertTips(null,null,"数据处理中，请稍后...");
	setTimeout(function(){
		var dataArr = [];
		var _qxCheck = jQuery("#sjfwdx_listul_" + SJFWDX_ID_QX + ">li input:checkbox:checked");
		var qxObj = {sjfwdx:SJFWDX_ID_QX,zddm:"bmdm_id",data:[{k:"-2",v:"全校"}]};
		if(_qxCheck.length > 0){
			dataArr.push(qxObj);
		}else{
			for ( var i = 0; i < sjfwdxList.length; i++) {
				var o = sjfwdxList[i];
				var zddm = o.zddm;
				var sjfwdx = o.sjfwdx_id;
				var sjfwArr = o.sjfwList;
				if(sjfwdx == SJFWDX_ID_QX){
					continue;
				}
				
				if(jQuery("#sjfwdx_listul_" + sjfwdx + ">li").length == 0 && sjfwArr != null && sjfwArr.length>0){//未进行加载，取用原来已保存的数据
					var sjfwdxDataArr = getSjfwDataArr(o);				
					var obj = {sjfwdx:sjfwdx,zddm:zddm,data:sjfwdxDataArr};
					dataArr.push(obj);				
					continue;
				}
				
				var _listli = jQuery("#sjfwdx_listul_" + sjfwdx + ">li:has(input:checkbox:checked)");
				
				if(sjfwdx == SJFWDX_ID_XY || sjfwdx == SJFWDX_ID_ZY || sjfwdx == SJFWDX_ID_BJ ){
					var _listli_notcheck = jQuery("#sjfwdx_listul_" + sjfwdx + ">li:has(input:checkbox:not(:checked))");				
					if(_listli.length > 0 && _listli_notcheck.length == 0){
						dataArr = [];
						dataArr.push(qxObj);
						break;
					}
				}
				var sjfwdxDataArr = [];
	
				zddm = zddm.toLowerCase();
				_listli.each(function(index){
					var _check = jQuery(this).find("input:checkbox:checked");
					var key = _check.val();
					var value = jQuery(this).find("label span").html();
					var keyvalueObj = {k:key,v:value};
					sjfwdxDataArr.push(keyvalueObj);
				});
				
				if(sjfwdxDataArr.length > 0){
					var obj = {sjfwdx:sjfwdx,zddm:zddm,data:sjfwdxDataArr};
					dataArr.push(obj);
				}
			}
		}
	
		var jsonValue=JSON.stringify(dataArr);
		var js_id = jQuery("#js_id").val();
		var zgh = jQuery("input[name='zgh']").map(function() {
			  return jQuery(this).val();
		}).get().join(',');
		
		var url = _path + "/xtgl/yhjsSjfwgl_bcSjfw.html";
		jQuery.ajax( {
			type : "post",
			url : url,
			data : {dataValue:jsonValue,zgh:zgh,js_id:js_id},
			dataType : "json",
			success : function(data) {
				tips.close();
				alert(data,'',{'clkFun':function(){iFClose();}});
			//	alertMessage(data,function(){
			//		iFClose();
			//	});
			}
		});
	},100);

}
