var target;
jQuery(window).scroll(function (){
	if(target != undefined){
		//closeBigSelectDiv();
	}
});
function closeBigSelectDiv(node){
	jQuery("#bigSelectPluginsDiv").remove();
	setTimeout(function (){
		//开启右键鼠标右键
		document.oncontextmenu="";
	},100);
}
function initBigCombo(options){
	var settings = {
		targetid:"",		//加载下拉框的id
		dataSourceId:"",    //数据源id
		hasInitial:"true",  //默认带首字母
		tipText:"",   	 	//title提示文字
		setText:"",			//设置名称显示
		setValue:"",		//设置代码
		initEvent:"focus", //启动事件("click")
		readonly:"true",   //只读设置
		selectType:"normalType",//普通("xzqType")
		withParentXzq:"true",   //带父级行政区
		
		selectXzqRightClick:"true",   //右键直接选择
		selectXzqRightClickLimit:"",
		
		xzqStyle:"sheng",  //行政区类型("shi","xian")
		parentName:"",     //对应数据源里的查询字段名
		parentId:"",		//对应页面上的字段id
		secondparentName:"",//对应数据源里的查询字段名
		secondParentId:"",  //对应页面上的字段id
		withoutCheck:"true",
		callback:function(){//回调函数
           
        }
	};
	jQuery.extend(settings, options);
	if(settings.readonly=="true"){
		jQuery("#"+settings.targetid).attr("readonly","readonly");
	}
	jQuery("#"+settings.targetid).css("background-image","url('"+_path+"/images/select.png')");
	jQuery("#"+settings.targetid).css("background-position","bottom right");
	jQuery("#"+settings.targetid).css("background-repeat","no-repeat");
	if(settings.initEvent == "focus"){
		jQuery("#"+settings.targetid).focus(function(){
			if(settings.selectType == "normalType"){
				openSelectDiv(settings);
			}else if(settings.selectType == "xzqType"){
				openXzqSelectDiv(settings);
			}
		});
	}else if(settings.initEvent == "click"){
		jQuery("#"+settings.targetid).click(function(){
			if(settings.selectType == "normalType"){
				openSelectDiv(settings);
			}else if(settings.selectType == "xzqType"){
				openXzqSelectDiv(settings);
			}
		});
	}
}

function openSelectDiv(settings){
	jQuery("#bigSelectPluginsDiv").removeData();
	jQuery("#bigSelectPluginsDiv").remove();
	target = jQuery("#"+settings.targetid);
	jQuery.ajaxSetup( {
		async : false
	});
	var selectDiv = jQuery("<div id='bigSelectPluginsDiv'><div>").load(_path+"/zfxg!plugins/bigComboSelectAjax_loadNormalTypePage.html");
	jQuery.ajaxSetup( {
		async : true
	});
	jQuery("body").append(selectDiv);
	var marginRight = jQuery(document).width()-target.offset().left;
	var offsetRight = 0;
	if(marginRight < 370){
		offsetRight = 370 - marginRight + 5;
	}
	jQuery("#bigSelectDiv").css("top",target.offset().top+target.outerHeight());
	jQuery("#bigSelectDiv").css("left",target.offset().left - offsetRight);
	jQuery("#bigSelectPluginsDiv").data(settings);
	var url = _path + "/zfxg!plugins/bigComboSelectAjax_getBigComboDataList.html";
	loadAjaxData(url,"true","",settings);
}
function loadAjaxData(url,initialFlag,initial,settings){
	jQuery.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{
			hasInitial:settings.hasInitial,initialFlag:initialFlag,initial:initial,bigComboId:settings.dataSourceId,
			parentName:settings.parentName,parentId:jQuery("#"+settings.parentId).val(),
			secondParentId:jQuery("#"+settings.secondParentId).val(),
			secondparentName:settings.secondparentName
		},
		success:function(redata){
			if(settings.hasInitial=="true" && redata!=null && redata.initial!=null && redata.initial!=""){
				jQuery.each( redata.initial.split(","), function(i, initial){
					jQuery("#initialPanel").append("<li labelvalue='"+initial+"'>"+initial+"</li>");
				});
				jQuery(".more_block_ul li").unbind( "click" );
				jQuery(".more_block_ul li").click(function(){initialLiClick(this,url,settings);});
			}
			if(settings.hasInitial=="false"){
				jQuery("#initialPanelDiv").remove();
				jQuery("#selectDataPanel").css("border-top","0px");
			}
			jQuery("#selectDataPanel a").remove();
			if(redata.data!=null && redata.data.length != 0){
				jQuery.each( redata.data, function(i, data){
					jQuery("#selectDataPanel").append("<a name=\"a_more_"+data.initialname+"\" href=\"javascript:void(0)\" onclick=\"checkBigComboItem(this,'"+settings.setText+"','"+settings.setValue+"');\" selectvalue=\""+data.dm+"\" selecttext=\""+data.mc+"\">"+data.mc+"</a>");
				});
			}
			jQuery("#bigComboTipText").text(settings.tipText);
		}
	});
}

function initialLiClick(node,url,settings){
	var initial = jQuery(node).attr("labelvalue");
	loadAjaxData(url,"false",initial,settings);
}

function checkBigComboItem(node, setText, setValue){
	var conf = jQuery("#bigSelectPluginsDiv");
	if(setText != ""){
		var setTextVar = jQuery(node).attr("selecttext");
		var preAlls = jQuery(".bigComboTab li.curr").prevAll();
		if(conf.data("selectType") == "xzqType" && conf.data("withParentXzq") == "true"){
			if(conf.data("xzqStyle") == "shi" || conf.data("xzqStyle") == "xian"){
				for(var i =0; i< preAlls.length; i++){
					setTextVar = jQuery(preAlls[i]).find("a:first").find("em:first").attr("title")+setTextVar;
				}
			}
		}
		if(conf.data("withoutCheck") == "true" && jQuery("#"+setText).data("addcheckNullFunFlag") == "true"){
			jQuery("#"+setText).css("background-color",jQuery("#"+setText).data("backgroundColorStyle"));
			jQuery("#"+setText).data("addcheckNullFunFlag","false");
		}
		jQuery("#"+setText).val(setTextVar);
	}
	if(setValue != ""){
		jQuery("#"+setValue).val(jQuery(node).attr("selectvalue"));
	}
	
	var callback_ = conf.data("callback");
	if(callback_ && jQuery.isFunction(callback_)){
		callback_();
	}
	closeBigSelectDiv(node);
}
function openXzqSelectDiv(settings){
	jQuery("#bigSelectPluginsDiv").removeData();
	jQuery("#bigSelectPluginsDiv").remove();
	target = jQuery("#"+settings.targetid);
	jQuery.ajaxSetup( {
		async : false
	});
	var selectDiv = jQuery("<div id='bigSelectPluginsDiv'><div>").load(_path+"/zfxg!plugins/bigComboSelectAjax_loadXzqTypePage.html");
	jQuery.ajaxSetup( {
		async : true
	});
	jQuery("body").append(selectDiv);
	var marginRight = jQuery(document).width()-target.offset().left;
	var offsetRight = 0;
	if(marginRight < 370){
		offsetRight = 370 - marginRight + 5;
	}
	jQuery("#bigSelectDiv").css("top",target.offset().top+target.outerHeight());
	jQuery("#bigSelectDiv").css("left",target.offset().left - offsetRight);
	
	jQuery("#bigComboTipSpan img").attr("src",_path+"/images/help.gif");
	jQuery("#bigComboTipSpan").css("display","none");
	if(settings.selectXzqRightClick=="true"){
		if(settings.selectXzqRightClickLimit == "" || settings.selectXzqRightClickLimit.indexOf("sheng") != -1){
			jQuery("#bigComboTipSpan").css("display","");
		}
	}
	var url = _path + "/zfxg!plugins/bigComboSelectAjax_getXzqBigComboDataList.html";
	jQuery("#bigSelectPluginsDiv").data(settings);
	jQuery("#bigSelectPluginsDiv").data("url",url);
	loadXzqAjaxData(url,"sheng",settings);
}

function loadXzqAjaxData(url,searchType,settings){
	jQuery.ajax({
		url:url,
		type:'post',
		dataType:'json',
		data:{
			searchType:searchType,parentId:settings.parentId
		},
		success:function(redata){
			jQuery("#selectDataPanel a").remove();
			if(redata!=null && redata.length != 0){
				jQuery.each( redata , function(i, data){
					var item = jQuery("<a href=\"javascript:void(0)\" onmouseup=\"checkXzqType(event,this,'"+searchType+"','"+url+"');\" selectvalue=\""+data.dm+"\" selecttext=\""+data.mc+"\">"+data.mc+"</a>");
					item.data(settings);
					jQuery("#selectDataPanel").append(item);
				});
			}else{
				if(searchType=="shi" || searchType=="xian"){
					var setTextVar = "";
					var preAlls = jQuery(".bigComboTab li.curr").prevAll();
					var conf = jQuery("#bigSelectPluginsDiv");
					if(conf.data("withParentXzq") == "true"){
						if(conf.data("xzqStyle") == "shi" || conf.data("xzqStyle") == "xian"){
							for(var i =0; i< preAlls.length; i++){
								setTextVar = jQuery(preAlls[i]).find("a:first").find("em:first").attr("title")+setTextVar;
							}
						}
						if(settings.withoutCheck == "true" && jQuery("#"+settings.setText).data("addcheckNullFunFlag") == "true"){
							jQuery("#"+settings.setText).css("background-color",jQuery("#"+settings.setText).data("backgroundColorStyle"));
							jQuery("#"+settings.setText).data("addcheckNullFunFlag","false");
						}
						jQuery("#"+settings.setText).val(setTextVar);
					}
					jQuery("#"+settings.setValue).val(settings.parentId);
					
					var callback_ = conf.data("callback");
					if(callback_ && jQuery.isFunction(callback_)){
						callback_();
					}
					closeBigSelectDiv();
				}
			}
			jQuery("#bigComboTipText").text(settings.tipText);
		}
	});
}
function checkXzqType(event,node,searchType,url){
	if(jQuery(node).attr("selectvalue") == "810000" || jQuery(node).attr("selectvalue") == "820000" || jQuery(node).attr("selectvalue") == "710000"){
		checkBigComboItem(node,jQuery(node).data("setText"),jQuery(node).data("setValue"));
	}
	if(jQuery(node).data("selectXzqRightClick") == "true"){
		if(jQuery(node).data("selectXzqRightClickLimit") == "" || jQuery(node).data("selectXzqRightClickLimit").indexOf(searchType) != -1){
			var btnNum = event.button;
			if (btnNum==2){
				checkBigComboItem(node,jQuery(node).data("setText"),jQuery(node).data("setValue"));
				return false;
			}
		}
	}
	
	if(searchType == "sheng"){
		if(jQuery(node).data("selectXzqRightClick")=="true"){
			if(jQuery(node).data("selectXzqRightClickLimit") == "" || jQuery(node).data("selectXzqRightClickLimit").indexOf("shi") != -1){
				jQuery("#bigComboTipSpan").css("display","");
			}else{
				jQuery("#bigComboTipSpan").css("display","none");
			}
		}
		jQuery("#shengSelectTab em").text(limitWordsByNum(jQuery(node).attr("selecttext"),6));
		jQuery("#shengSelectTab em").attr("title",jQuery(node).attr("selecttext"));
		jQuery("#shengSelectTab #checkShengId").val(jQuery(node).attr("selectvalue"));
		if(jQuery(node).data("xzqStyle") == "shi" || jQuery(node).data("xzqStyle") == "xian"){
			jQuery("#shengSelectTab").removeClass("curr");
			jQuery("#shengSelectTab a").removeClass("hover");
			jQuery("#shiSelectTab").css("display","list-item").addClass("curr");
			jQuery("#shiSelectTab a").addClass("hover");
			jQuery("#shiSelectTab em").text("请选择");
			jQuery("#shiSelectTab em").attr("title","请选择");
			jQuery("#shiSelectTab #checkShiId").val("");
			jQuery(node).data("parentId",jQuery("#checkShengId").val());
			loadXzqAjaxData(url,"shi",jQuery(node).data());
		}
	}else if(searchType == "shi"){
		if(jQuery(node).data("selectXzqRightClick")=="true"){
			if(jQuery(node).data("selectXzqRightClickLimit") == "" || jQuery(node).data("selectXzqRightClickLimit").indexOf("xian") != -1){
				jQuery("#bigComboTipSpan").css("display","");
			}else{
				jQuery("#bigComboTipSpan").css("display","none");
			}
		}
		if(jQuery("#checkShengId").val() == "110000" || jQuery("#checkShengId").val() == "120000" 
			|| jQuery("#checkShengId").val() == "310000" || jQuery("#checkShengId").val() == "500000"){
			checkBigComboItem(node,jQuery(node).data("setText"),jQuery(node).data("setValue"));
		}
		jQuery("#shiSelectTab em").text(limitWordsByNum(jQuery(node).attr("selecttext"),6));
		jQuery("#shiSelectTab em").attr("title",jQuery(node).attr("selecttext"));
		jQuery("#shiSelectTab #checkShiId").val(jQuery(node).attr("selectvalue"));
		if(jQuery(node).data("xzqStyle") == "xian"){
			jQuery("#shiSelectTab").removeClass("curr");
			jQuery("#shiSelectTab a").removeClass("hover");
			jQuery("#xianSelectTab").css("display","list-item").addClass("curr");
			jQuery("#xianSelectTab a").addClass("hover");
			jQuery("#xianSelectTab em").text("请选择");
			jQuery("#xianSelectTab em").attr("title","请选择");
			jQuery(node).data("parentId",jQuery("#checkShiId").val());
			loadXzqAjaxData(url,"xian",jQuery(node).data());
		}
	}
	if(jQuery(node).data("xzqStyle") == "sheng" && searchType == "sheng"){
		checkBigComboItem(node,jQuery(node).data("setText"),jQuery(node).data("setValue"));
	}
	if(jQuery(node).data("xzqStyle") == "shi" && searchType == "shi"){
		checkBigComboItem(node,jQuery(node).data("setText"),jQuery(node).data("setValue"));
	}
	if(jQuery(node).data("xzqStyle") == "xian" && searchType == "xian"){
		checkBigComboItem(node,jQuery(node).data("setText"),jQuery(node).data("setValue"));
	}
}

function clickBigComboSelectTab(searchType){
	if(searchType == "sheng"){
		jQuery("#shiSelectTab em").text("请选择");
		jQuery("#shiSelectTab em").attr("title","请选择");
		jQuery("#shiSelectTab #checkShiId").val("");
		
		jQuery("#shengSelectTab").addClass("curr");
		jQuery("#shengSelectTab a").addClass("hover");
		jQuery("#shengSelectTab em").text("请选择");
		jQuery("#shengSelectTab em").attr("title","请选择");
		jQuery("#shiSelectTab").css("display","none").removeClass("curr");
		jQuery("#shiSelectTab a").removeClass("hover");
		jQuery("#xianSelectTab").css("display","none").removeClass("curr");
		jQuery("#xianSelectTab a").removeClass("hover");
		loadXzqAjaxData(jQuery("#bigSelectPluginsDiv").data("url"),"sheng",jQuery("#bigSelectPluginsDiv").data());
	}else if(searchType == "shi"){
		jQuery("#shengSelectTab").removeClass("curr");
		jQuery("#shengSelectTab a").removeClass("hover");
		jQuery("#xianSelectTab").css("display","none").removeClass("curr");
		jQuery("#xianSelectTab a").removeClass("hover");
		jQuery("#shiSelectTab").css("display","list-item").addClass("curr");
		jQuery("#shiSelectTab a").addClass("hover");
		jQuery("#shiSelectTab em").text("请选择");
		jQuery("#shiSelectTab em").attr("title","请选择");
		jQuery("#shiSelectTab #checkShiId").val("");
		jQuery("#bigSelectPluginsDiv").data("parentId",jQuery("#checkShengId").val());
		loadXzqAjaxData(jQuery("#bigSelectPluginsDiv").data("url"),"shi",jQuery("#bigSelectPluginsDiv").data());
	}
	if(jQuery("#bigSelectPluginsDiv").data("selectXzqRightClick")=="true"){
		if(jQuery("#bigSelectPluginsDiv").data("selectXzqRightClickLimit") == "" || jQuery("#bigSelectPluginsDiv").data("selectXzqRightClickLimit").indexOf(searchType) != -1){
			jQuery("#bigComboTipSpan").css("display","");
		}else{
			jQuery("#bigComboTipSpan").css("display","none");
		}
	}
}

function limitWordsByNum(str,num){
	if(str.length>num){
		return str.substring(0,num)+"...";
	}
	return str;
}