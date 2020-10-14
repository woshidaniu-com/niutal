/**
 * 
 * 高级查询页面引入该脚本<br>
 * @author xiaokang
 * @author 1036
 * 
 */

/**
 * 条件查询项点击事件
 * 
 * @param obj
 * @param only
 *            是否单选
 */
function bindSelectOption(obj,only){
	var _self = jQuery(obj);
	var _type = jQuery(obj).attr("typekey");
	var _value = jQuery(obj).attr("value");
	var selectId = _type +"_"+jQuery(obj).attr("value");
	var selctClassName = jQuery(obj).attr("class");
	
	// 如果该项已经选中再次点击后起效（通过样式判断是否已经选中）
	if (selctClassName == "selectedValue"){
		// 从已经选中条件中将此项删除
		jQuery("#"+selectId).remove();
		// 移除已经选中的标识样式
		jQuery("a[typekey="+_type+"][value="+_value+"]").removeClass("selectedValue");
		// 如果没有已经选中的条件则隐藏已经选中样式
		if (jQuery("#dl_choice dd").length == 0){
			jQuery("#div_choice").css("display","none");
		}
		
	}else {
		
		if (only){
			jQuery("#dl_choice dd[name=select_"+_type+"]").click();
		}
		
		// 点击某选择项后，创建已选条件 内容
		var selectContent = "<dd id='"+selectId+"' name='select_"+_type+"' value='"+_value+"'>";
		selectContent+= "<a href='javascript:void(0);'>";
		selectContent+= "<h5>";
		selectContent+= jQuery(obj).attr("typelabel");
		selectContent+= "</h5>";
		selectContent+= jQuery(obj).attr("label");
		selectContent+= "<span class='close-icon' title='取消'></span>";
		selectContent+= "</a>";
		selectContent+= "</dd>";
		var selectDd = jQuery(selectContent);	
		
		// 为已选条件绑定点击事件
		selectDd.bind("click",function(){
			_self.click();
		});
		jQuery("#dl_choice").append(selectDd);
		// 显示已选条件 栏
		jQuery("#div_choice").css("display","inline");
		// 为该选择项增加已经选中样式
		jQuery(obj).addClass("selectedValue");
	}
}

/**
 * 重置已选条件（联动后某些选择项已经不存在，但是已选条件中有该选择项 此函数的目的是，将已选条件中不存在的选择项删除。 ）
 */
function resetSelectOption(){
	var selectDd = jQuery("#dl_choice dd");
	for (var i = 0 ; i < selectDd.length ; i++){
		var arr = jQuery(selectDd.eq(i)).attr("id").split("_");
		var aLink = jQuery("a[typekey="+arr[0]+"][value="+arr[1]+"]");
		if (aLink.length == 0){
			selectDd.eq(i).click();
		} else {
			aLink.attr("class","selectedValue");
		}
	}
}

/**
 * 单类选择项的收起与更多
 * 
 * @param obj
 * @param liName
 * @param displayType
 */
function flexMore(obj,liName,displayType){
	 var className = jQuery(obj).attr("class");
	 
	 if (className == "more_down"){
		 jQuery(obj).text("收起");
		 jQuery(obj).removeClass("more_down");
		 jQuery(obj).addClass("more_up");
		 jQuery(obj).parent().find("li[name="+liName+"]").css("display",displayType||"inline");
		 jQuery(obj).parent().find("a[name=a_hidden]").css("display","block");
		 
	 } else {
		 jQuery(obj).text("更多");
		 jQuery(obj).removeClass("more_up");
		 jQuery(obj).addClass("more_down");
		 jQuery(obj).parent().find("li[name="+liName+"]").css("display","none");
		 jQuery(obj).parent().find("a[name=a_hidden]").css("display","none");
	 }
}



/**
 * 高级查询获取初始化数据
 * @return
 */
function getSearchInitialMap(){
	//var defaultConditionData = jQuery('#dl_choice').data('default-condition');
	var defaultConditionData = SuperSearchDefaultConditionData;
	if(defaultConditionData == undefined || defaultConditionData == null || jQuery.trim(defaultConditionData) == ''){
		return null;
	}
	//[{cxzd:'nj',defaultValue:['2010','2012','2014']},{cxzd:'bjdm',defaultValue:['213123','213123']}]
	//{"nj":["2012","2010","2013","2008","2003"],"mhcx":{"mhcxValue":"","mhcxType":"-1"}}
	var param = {};
	jQuery.each(defaultConditionData , function(i , n){
		var searchName = n['cxzd'];
		var defaultValueArr = n['defaultValue'];
		param[searchName] = defaultValueArr;
	});
	//initialSuperSearchDefautlCondition(jQuery('#dl_choice').data('default-condition'));
	return {"searchModel.superSearchData":JSON.stringify(param) , "searchModel.searchSign" : SS.Config.getConfigId()};
}


/**
 * 高级查询选择或输入的条件对象
 * 
 * @param b（是否将选择型条件收起）
 * @returns {___anonymous4078_4079}
 * 
 * @author xiaokang
 * 
 */
function getSearchMap(b){
	var searchMap = {};
	//点击查询选中数据
	var selectedData=SS.DataUtil.getSelectedDataWithOutConfigObject();
	//区间输入数据
	var qjcxData=SS.DataUtil.getQjcxData();
	//模糊查询数据
	var mhcxData=SS.DataUtil.getMhcxData();
	
	jQuery.extend(searchMap , selectedData , qjcxData , {"mhcx" : mhcxData})
	
	//收起高级查询区域
	if(b){
		jQuery('#div_searchContent').slideUp('fast');
		var _self = jQuery("#a_id_more");
		if (_self.attr("class") == "up"){
			_self.attr("class","down");
			_self.text("展开");
		}
	}
	
	return {"searchModel.superSearchData" : JSON.stringify(searchMap) , "searchModel.searchSign" : SS.Config.getConfigId()};
	/*var searchArray = [];
	var selectDd = jQuery("#dl_choice dd");
	
	for (var i = 0 ; i < selectDd.length ; i++){
		var id = selectDd.eq(i).attr("id");
		var value = jQuery("#"+id).attr("value");
		var temp = id.replace("_"+value,"");
		
		searchArray.push(temp+"!search!"+value);
	}
	// 已选条件
	searchMap["searchModel.selectOptions"] = searchArray.join("!selectOption!");

	if (jQuery("#blurType").val() == ""){
		var typeArray = jQuery("div[name=blurType]");
		var types = [];
		
		for (var i = 0 ; i < typeArray.length ; i++){
			types.push(typeArray.eq(i).attr("value"));
		}
		
		searchMap["searchModel.blurType"]= types.join("!blurType!");
	} else {
		searchMap["searchModel.blurType"]= jQuery("#blurType").val();
	}
	// 模糊查询条件
	searchMap["searchModel.blurValue"]= jQuery("#blurValue").val() == "请输入关键词" ? "" : jQuery("#blurValue").val();
	
	// 收起选择项
	if (jQuery("#a_id_more").attr("class") == "up" && b){
		jQuery("#a_id_more").click();
	}
	
	// 数字区间类型,20131111
	var szqjcxValue = "";
	jQuery("ul[name='szqjcx']").each(function(index){
		var cxzd = jQuery(this).find("input[name='cxzd']").val();// 查询字段
		var start = jQuery.trim(jQuery(this).find("input[name='start']").val());// 开始
		var end = jQuery.trim(jQuery(this).find("input[name='end']").val());// 结束
		if(start != "" || end != ""){
			if(szqjcxValue != ""){
				szqjcxValue += ",";
			}
			// type 1:start,2:end
			szqjcxValue += "{cxzd:'" + cxzd + "',type:'1',value:'" + start + "'}";
			szqjcxValue += ",{cxzd:'" + cxzd + "',type:'2',value:'" + end + "'}";
		}
	});
	if(szqjcxValue != ""){
		szqjcxValue = "{data:[" + szqjcxValue + "]}";
	}
	searchMap["searchModel.szqjcxValue"]= szqjcxValue;
		
	// 时间区间类型
	var sjqjcxValue = "";
	jQuery("ul[name='sjqjcx']").each(function(index){
		var cxzd = jQuery(this).find("input[name='cxzd']").val();// 查询字段
		var sjly = jQuery(this).find("input[name='sjly']").val();// 数据来源
		var start = jQuery.trim(jQuery(this).find("input[name='start']").val());// 开始
		var end = jQuery.trim(jQuery(this).find("input[name='end']").val());// 结束
		if(start != "" || end != ""){
			if(sjqjcxValue != ""){
				sjqjcxValue += ",";
			}
			// type 1:start,2:end
			sjqjcxValue += "{cxzd:'" + cxzd + "',sjly:'"+sjly+"',type:'1',value:'" + start + "'}";
			sjqjcxValue += ",{cxzd:'" + cxzd + "',sjly:'"+sjly+"',type:'2',value:'" + end + "'}";
		}
	});
	if(sjqjcxValue != ""){
		sjqjcxValue = "{data:[" + sjqjcxValue + "]}";
	}
	searchMap["searchModel.sjqjcxValue"]= sjqjcxValue;
		
	// 字符时间区间类型
	var zfsjqjcxValue = "";
	jQuery("ul[name='zfsjqjcx']").each(function(index){
		var cxzd = jQuery(this).find("input[name='cxzd']").val();// 查询字段
		var start = jQuery.trim(jQuery(this).find("input[name='start']").val());// 开始
		var end = jQuery.trim(jQuery(this).find("input[name='end']").val());// 结束
		if(start != "" || end != ""){
			if(zfsjqjcxValue != ""){
				zfsjqjcxValue += ",";
			}
			// type 1:start,2:end
			zfsjqjcxValue += "{cxzd:'" + cxzd + "',type:'1',value:'" + start + "'}";
			zfsjqjcxValue += ",{cxzd:'" + cxzd + "',type:'2',value:'" + end + "'}";
		}
	});
	if(zfsjqjcxValue != ""){
		zfsjqjcxValue = "{data:[" + zfsjqjcxValue + "]}";
	}
	searchMap["searchModel.zfsjqjcxValue"]= zfsjqjcxValue;*/
	
	
}

function setSearchTagWithForm(formid){
	var searchMap = getSearchMap(false);
	var superSearchData = jQuery("<input type='hidden' name='searchModel.superSearchData' value='"+searchMap['searchModel.superSearchData']+"'/>");
	var searchSign = jQuery("<input type='hidden' name='searchModel.searchSign' value='"+searchMap['searchModel.searchSign']+"'/>");
	var form = jQuery("#"+formid);
	if (jQuery("input[name*=searchModel]",form).size() > 0){
		jQuery("input[name*=superSearchData][name*=searchModel]",form).val(searchMap['searchModel.superSearchData']);
		jQuery("input[name*=searchSign][name*=searchModel]",form).val(searchMap['searchModel.searchSign']);
	} else {
		form.append(superSearchData);
		form.append(searchSign);
	}
}

function setSearchTag(){
	var searchMap = getSearchMap(false);
	
	var superSearchData = jQuery("<input type='hidden' name='searchModel.superSearchData' value='"+searchMap['searchModel.superSearchData']+"'/>");
	var searchSign = jQuery("<input type='hidden' name='searchModel.searchSign' value='"+searchMap['searchModel.searchSign']+"'/>");

	var form = jQuery("form:not([id='teaPageForm'])").eq(0);
	
	if (jQuery("input[name*=searchModel]").size() > 0){
		jQuery("input[name*=superSearchData][name*=searchModel]").val(searchMap['searchModel.superSearchData']);
		jQuery("input[name*=searchSign][name*=searchModel]").val(searchMap['searchModel.searchSign']);
	} else {
		form.append(superSearchData);
		form.append(searchSign);
	}
}

/**
 * 从缓存中获取更多html
 * 
 * @param searchName
 * @return
 */
function getMoreHtml(searchName){
	var sHtml = "";
	var moreBs = "<input type='hidden' name='moreBs' value='1'>";
	var _moreDiv = jQuery("#more_options_" + searchName + " .search_advanced .prop-item");
	if(_moreDiv.find("input[name='moreBs']").val() != "1"){
		jQuery.ajax( {
			type : "post",
			async : false,
			url : _path + "/zfxg/search/superSearch_getMoreHtml.html?timestamp=" + new Date().getTime(),
			data : {
				"searchName" : searchName
			},
			dataType : "json",
			success : function(data) {
				var isMore = data['isMore'];
				if(isMore == true){
					sHtml = data['data'];
					sHtml += moreBs;
					_moreDiv.html(sHtml);
					_moreDiv.find("li,a").show();
				}else{
					_moreDiv.append(moreBs);
				}
			},
			error : function(err) {
			}
		});

	}
}

/**
 * 数字区间类型验证
 * 
 * @param type
 * @return
 */
function szqjcxCheck(obj){
	jQuery(obj).val(jQuery(obj).val().replace(/[^\d]/g, ''));
}

/* 更多中的更多弹出层 */
function tipsSearchMore(title, content, width, height) {
	if (content.indexOf("id:") > -1){
		var id = "#"+content.split("id:")[1];
		content = jQuery(id).html();
		jQuery(id).html("");
	}
	
	ymPrompt.win({
			message:content,
			title:title,
			width:width,
			height:height,
			useSlide:true,
			maskAlphaColor:"#FFFFFF",
			winPos:'t',
			maskAlpha:0.3,
			handler:function(type){
				if(type == "close"){
					jQuery(id).append(jQuery(".search_advanced",jQuery("#ym-window")));
				}
			}
		}
	);
}



// ===============================================================================//

// 全局参数
var getWebContentPath = function(){return jQuery('#searchtab').attr('contextpath')}

// 获取当前高级查询的配置名
var getSearchConfigId = function(){return jQuery('#search_go').data('search_config_id')}
// 获取具体存储数据的元素
var getSeachDataCache = function(searchname){return jQuery('dl[data-search-name = "' + searchname + '"]');}


/**
 * 创建空数据提示HTML元素
 */
function createEmptyInfo(){
	var span = jQuery('<span>').addClass('no-data-msg');
	span.css({'display': 'block' , 'font-size': '12px' , 'color':'rgb(202, 79, 79)', 'text-align': 'center'});
	span.html('--查询无数据--');
	return span;
}





/**
 * 根据配置组件id初始化高级查询骨架
 */
function initialSuperSearchConfig(){
	var url = "/zfxg/search/superSearch_getConfig.html";
	var searchSign = SS.Config.getConfigId();
	var responseData = SS.ReqUtil.send(url, {"searchSign" : searchSign}, null);
	// 模糊查询初始化============特殊处理=======================
	jQuery("#mhcx_options").hover(function(){
		jQuery("#mhcx_options dd,#mhcx_options b").show();
	}, function() {
		jQuery("#mhcx_options dd").hide();
	});
	
	jQuery("#mhcx_options ,#mhcx_options div").hover(function(){
		jQuery(this).addClass("hover");
	},function(){
		jQuery(this).removeClass("hover");
	});
	// 模糊查询输入框光标移入清除提示
	jQuery("#blurValue").bind("focus",function(){
		if (jQuery("#blurValue").val() == "请输入关键词"){
			jQuery("#blurValue").val("");
			jQuery("#blurValue").css('color' , '#000');
		}
	});
	
	// 模糊查询输入框光标离开，若文本框内容为空加上提示
	jQuery("#blurValue").bind("blur",function(){
		if (jQuery("#blurValue").val() == ""){
			jQuery("#blurValue").val("请输入关键词");
			jQuery("#blurValue").css('color' , '#999');
		}
	});
	
	// 模糊查询文本框回车查询
	jQuery("#blurValue").bind("keyup",function(e){
		if (e.keyCode == 13){
			jQuery("#search_go").click();
		}
	});
	
	var mhcxDIVRef = jQuery('#dd_mhcx_lx');
	var mhcxHTML = jQuery('<div name="blurType" value="">全部</div>').click(function(){
		jQuery("#text").val('全部');
		jQuery("#blurType").val('');
		jQuery("#mhcx_options dd").hide();
	});
	mhcxDIVRef.append(mhcxHTML);
	jQuery.each(responseData , function(index , item){
		if(item['searchType'] === 'mhcx'){
			var mhcxSearchName = item['searchName'];
			var mhcxSearchLabel = item['searchLabel'];
			var mhcxHTML = jQuery('<div name="blurType" value="' + mhcxSearchName + '">' + mhcxSearchLabel + '</div>').click(function(){
					jQuery("#text").val(mhcxSearchLabel);
					jQuery("#blurType").val(mhcxSearchName);
					jQuery("#mhcx_options dd").hide();
				});
			mhcxDIVRef.append(mhcxHTML);
		}
	});
	// 模糊查询初始化============特殊处理=============================

	// 其他配置项HTML骨架代码生成=====================================
	 // 过滤掉模糊查询配置项目
	 jQuery.grep(responseData , function(item , index){
			return item['searchType'] == 'mhcx';
			
	  } , true);
	 jQuery('#div_searchContent').attr('data-status' , 'init');
	 jQuery.each(responseData, function(index , item){
		var searchType = item['searchType'];
		var searchLabel = item['searchLabel'];
		var searchName = item['searchName'];
		// 根据类型调用对应处理器
		if(searchType == 'tjcx'){
			var dl = jQuery('<dl></dl>');
			dl.attr('data-status' , 'init');
			dl.attr('id' , 'search_name_' + searchName);
			dl.attr('search_name' , searchName);
			///////////////////////////////
			dl.data('config', item);
			//////////////////////////////
			var dd = jQuery('<dd id = "dd-data-' + searchName + '"></dd>');
			dl.append(jQuery('<dt>' + searchLabel + ': </dt>')).append(dd);
			jQuery('#div_searchContent').append(dl);
		}else if(searchType == 'sjqjcx'){
			//时间区间（数据库字段为时间时间类型）
			var dl = jQuery('<dl></dl>');
			dl.attr('data-status' , 'loaded');
			dl.attr('id' , 'search_name_' + searchName);
			dl.attr('search_name' , searchName);
			///////////////////////////////
			dl.data('config', item);
			//////////////////////////////
			var dd = jQuery('<dd id = "dd-data-' + searchName + '"></dd>');
			var dd_data_ul = jQuery('<ul>').appendTo(dd);
			var _fmt = (item['valueSource'] == undefined || item['valueSource'] == '') ? 'yyyy-MM-dd' : item['valueSource'];
			var _start = jQuery("<input type=\"text\" onfocus=\"WdatePicker({dateFmt:'"+jQuery.trim(_fmt) + "'})\" />");
			_start.attr('name' , "start_value");
			var _end = jQuery("<input type=\"text\" onfocus=\"WdatePicker({dateFmt:'"+jQuery.trim(_fmt) + "'})\" />");
			_end.attr('name' , "end_value");
			//注册事件函数
			_start.focusout(function(){
				var _val = jQuery(this).val();
				var _qj_data ;
				if(jQuery('#dl_choice').data('qj-data') == undefined){
					jQuery('#dl_choice').data('qj-data' , {});
				}
				if(jQuery('#dl_choice').data('qj-data')[searchName] == undefined){
					jQuery('#dl_choice').data('qj-data')[searchName] =  {"start" : _val};
				}else{
					_qj_data = jQuery('#dl_choice').data('qj-data')[searchName]['start'] = _val;
				}
			});
			_end.focusout(function(){
				var _val = jQuery(this).val();
				var _qj_data ;
				if(jQuery('#dl_choice').data('qj-data') == undefined){
					jQuery('#dl_choice').data('qj-data' , {});
				}
				if(jQuery('#dl_choice').data('qj-data')[searchName] == undefined){
					jQuery('#dl_choice').data('qj-data')[searchName] =  {"end" : _val};
				}else{
					_qj_data = jQuery('#dl_choice').data('qj-data')[searchName]['end'] = _val;
				}
			});
			//注册事件函数
			var dd_data_start = jQuery('<li>').append(_start).appendTo(dd_data_ul);
			var dd_data_end = jQuery('<li>').append(' 至 ').append(_end).appendTo(dd_data_ul);
			dl.append(jQuery('<dt>' + searchLabel + ': </dt>')).append(dd);
			jQuery('#div_searchContent').append(dl);
		}else if(searchType == 'szqjcx'){
			//<input type='text' name='start' style='width:100px;' maxlength='10'  onblur='szqjcxCheck(this)' onkeyup='szqjcxCheck(this)'>
			var dl = jQuery('<dl></dl>');
			dl.attr('data-status' , 'loaded');
			dl.attr('id' , 'search_name_' + searchName);
			dl.attr('search_name' , searchName);
			///////////////////////////////
			dl.data('config', item);
			//////////////////////////////
			var dd = jQuery('<dd id = "dd-data-' + searchName + '"></dd>');
			var dd_data_ul = jQuery('<ul>').appendTo(dd);
			var _start = jQuery("<input  style=\"width:100px;\" maxlength=\"10\"  type=\"text\" onblur=\"szqjcxCheck(this)\" onkeyup=\"szqjcxCheck(this)\"/>");
			_start.attr('name' ,  "start_value");
			var _end = jQuery("<input  style=\"width:100px;\" maxlength=\"10\"  type=\"text\" onblur=\"szqjcxCheck(this)\" onkeyup=\"szqjcxCheck(this)\"/>");
			_end.attr('name' , "end_value");
			//注册事件函数
			_start.focusout(function(){
				var _val = jQuery(this).val();
				var _qj_data ;
				if(jQuery('#dl_choice').data('qj-data') == undefined){
					jQuery('#dl_choice').data('qj-data' , {});
				}
				if(jQuery('#dl_choice').data('qj-data')[searchName] == undefined){
					jQuery('#dl_choice').data('qj-data')[searchName] =  {"start" : _val};
				}else{
					_qj_data = jQuery('#dl_choice').data('qj-data')[searchName]['start'] = _val;
				}
			});
			_end.focusout(function(){
				var _val = jQuery(this).val();
				var _qj_data ;
				if(jQuery('#dl_choice').data('qj-data') == undefined){
					jQuery('#dl_choice').data('qj-data' , {});
				}
				if(jQuery('#dl_choice').data('qj-data')[searchName] == undefined){
					jQuery('#dl_choice').data('qj-data')[searchName] =  {"end" : _val};
				}else{
					_qj_data = jQuery('#dl_choice').data('qj-data')[searchName]['end'] = _val;
				}
			});
			//注册事件函数
			var dd_data_start = jQuery('<li>').append(_start).appendTo(dd_data_ul);
			var dd_data_end = jQuery('<li>').append(' 至 ').append(_end).appendTo(dd_data_ul);
			dl.append(jQuery('<dt>' + searchLabel + ': </dt>')).append(dd);
			jQuery('#div_searchContent').append(dl);
			//数字区间
		}else if(searchType == 'zfsjqjcx'){
			//时间区间（数据库字段为字符类型）
			//时间区间（数据库字段为时间时间类型）
			var dl = jQuery('<dl></dl>');
			dl.attr('data-status' , 'loaded');
			dl.attr('id' , 'search_name_' + searchName);
			dl.attr('search_name' , searchName);
			///////////////////////////////
			dl.data('config', item);
			//////////////////////////////
			var dd = jQuery('<dd id = "dd-data-' + searchName + '"></dd>');
			var dd_data_ul = jQuery('<ul>').appendTo(dd);
			var _fmt = (item['valueSource'] == undefined || item['valueSource'] == '') ? 'yyyy-MM-dd' : item['valueSource'];
			var _start = jQuery("<input type=\"text\" onfocus=\"WdatePicker({dateFmt:'"+jQuery.trim(_fmt) + "'})\" />");
			_start.attr('name' , "start_value");
			var _end = jQuery("<input type=\"text\" onfocus=\"WdatePicker({dateFmt:'"+jQuery.trim(_fmt) + "'})\" />");
			_end.attr('name' , "end_value");
			//注册事件函数
			_start.focusout(function(){
				var _val = jQuery(this).val();
				var _qj_data ;
				if(jQuery('#dl_choice').data('qj-data') == undefined){
					jQuery('#dl_choice').data('qj-data' , {});
				}
				if(jQuery('#dl_choice').data('qj-data')[searchName] == undefined){
					jQuery('#dl_choice').data('qj-data')[searchName] =  {"start" : _val};
				}else{
					_qj_data = jQuery('#dl_choice').data('qj-data')[searchName]['start'] = _val;
				}
			});
			_end.focusout(function(){
				var _val = jQuery(this).val();
				var _qj_data ;
				if(jQuery('#dl_choice').data('qj-data') == undefined){
					jQuery('#dl_choice').data('qj-data' , {});
				}
				if(jQuery('#dl_choice').data('qj-data')[searchName] == undefined){
					jQuery('#dl_choice').data('qj-data')[searchName] =  {"end" : _val};
				}else{
					_qj_data = jQuery('#dl_choice').data('qj-data')[searchName]['end'] = _val;
				}
			});
			//注册事件函数
			var dd_data_start = jQuery('<li>').append(_start).appendTo(dd_data_ul);
			var dd_data_end = jQuery('<li>').append(' 至 ').append(_end).appendTo(dd_data_ul);
			dl.append(jQuery('<dt>' + searchLabel + ': </dt>')).append(dd);
			jQuery('#div_searchContent').append(dl);
		}
	});
	// 其他配置项HTML骨架代码生成=====================================
}


/**
 * 绑定页面HTML元素事件处理函数
 * 
 * @return
 */
function bindSearchHtmlEventListener(){
	// 1.更多搜索条件的收起与展开
	jQuery("#a_id_more").bind("click",function(){
		// 1.1显示和隐藏高级查询组件
		//jQuery('#div_searchContent').slideToggle('fast' , function(){jQuery('#div_searchContent').mask("" , 0);});
		var _self = jQuery("#a_id_more");
		// 1.2 更多收起事件---------------------------------------------开始
		if (_self.attr("class") == "up"){
			jQuery('#div_searchContent').slideUp('fast');
			_self.attr("class","down");
			_self.text("展开");
		// 1.2 更多收起事件---------------------------------------------结束
		}else{
			_self.attr("class","up");
			_self.text("收起");
			// 如果数据已经被加载，直接返回
			if(jQuery('#div_searchContent').attr('data-status') == 'loaded'){
				jQuery('#div_searchContent').slideDown('fast');
				return ;
			}else if(jQuery('#div_searchContent').attr('data-status') == 'init'){
				// 查询高级选项
				jQuery.ajax({
					url : SS.Config.getContextPath() + '/zfxg/search/superSearch_getInitData.html?timestamp=' + new Date().getTime(),
					async: true, cache: false, dataType: 'json',timeout: 10000, type: 'POST',
					beforeSend: function(){jQuery('#div_searchContent').show(); jQuery('#div_searchContent').mask("" , 0);},
					complete: function(){jQuery('#div_searchContent').unmask();},
					data : {searchSign : SS.Config.getConfigId(), linkageValue: parseAllSelectedData(SS.DataUtil.getSelectedData())},
					success : function(data, textStatus, jqXHR){
						// 根据返回的数据构建相应HTML代码块
						jQuery.each(data , function(index , searchdata){
							var searchName = searchdata['searchName'];
							var dataList = searchdata['sourceDataItemList'];
							var $dd_data = jQuery('#dd-data-' + searchName);
							// 添加数据
							SS.DataUtil.getHoldEl(searchName).data('data' , {initial_data : dataList , 
																		 total_count : searchdata['totalCount'] , 
																		 initial_count: searchdata['currentCount'] , 
																		 has_more : searchdata['hasMore'] ,
																		 more_data_status: 'init'});
							//
							var dd_data_ul = createShowBlock({intial_label: SS.DataUtil.getConfigObject(searchName)['useInitial']}, dataList , searchName).appendTo($dd_data);
							//
							if(searchdata['hasMore']){
								jQuery('<a name="a_more" href="javascript:void(0);" class="more_down">更多</a>')
										.bind('click' , {searchName : searchName , dd_data_ul: dd_data_ul},moreLinkClickHandler).appendTo($dd_data);
							}
						});
						// 设置高级查询为loaded状态
						jQuery('#div_searchContent').attr('data-status' ,'loaded');
					}
				});
			}
		}
	});
}

/**
 * 初始化高级查询默认条件设置
 * @return
 */
function initialSuperSearchDefautlCondition(defauleCondition){
	if(defauleCondition == null){
		return ;
	}
	jQuery.ajax({
		url : SS.Config.getContextPath() + '/zfxg/search/superSearch_getDefaultConditionData.html?timestamp=' + new Date().getTime(),
		async: false, cache: false, dataType: 'json',timeout: 10000, type: 'POST',
		beforeSend: function(){jQuery('#div_searchContent').mask("" , 0);},
		complete: function(){jQuery('#div_searchContent').unmask();},
		data : {searchSign : SS.Config.getConfigId() , defaultCondition: JSON.stringify(defauleCondition)},
		success : function(data, textStatus, jqXHR){
			// 根据返回的数据构建相应HTML代码块
			jQuery.each(data , function(index , searchdata){
				var searchName = searchdata['searchName'];
				var dataList = searchdata['sourceDataItemList'];
				var configObject = SS.DataUtil.getConfigObject(searchName);
				//创建已选元素 //保存数据
				jQuery.each(dataList , function(index , value){
					var params = {searchName:searchName, columnInitialValue: value['columnInitialValue'], 
									columnKeyValue: value['columnKeyValue'], 
									columnLabelValue: value['columnLabelValue'], 
									configObject: configObject};
					SS.DataUtil.addSData(params);
					var select_dd = jQuery('<dd>' , {'id': 'select_' + params['searchName'] , 'searchname': params['searchName'] , 'value': params['columnKeyValue'] , 'label' : params['columnLabelValue']}).appendTo(jQuery('#dl_choice'));
					var select_dd_a = jQuery('<a>' , {'href' : "javascript:void(0);"}).
								append(jQuery('<h5>' , {'text': params['configObject']['searchLabel']})).append(params['columnLabelValue']).append(jQuery('<span class="close-icon" title="删除条件"></span>'));
					select_dd_a.one('click' , function(){
						//如果没有加载更多选项,则下面代码忽略
						if(jQuery(this).parent().data('sourceRef') != undefined){
							jQuery(this).parent().data('sourceRef').removeClass('item-selected');
							jQuery(this).parent().data('sourceRef').attr('select-status' , '0');
						}
						jQuery(this).parent().remove();
						SS.DataUtil.removeSData(params);
						if(jQuery('#dl_choice').find('dd').length > 0){
							jQuery('#search_selected').css('display', 'block');
						}else {
							jQuery('#search_selected').css('display', 'none');
						}
					});
					select_dd_a.appendTo(select_dd);
					select_dd.appendTo(jQuery('#dl_choice'));
				});
			});
			
			if(jQuery('#dl_choice').find('dd').length > 0){
				jQuery('#search_selected').css('display', 'block');
			}else {
				jQuery('#search_selected').css('display', 'none');
			}
		}
	});
}
