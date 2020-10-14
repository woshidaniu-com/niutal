/*
 * 本次更新内容
 * 放弃ymPrompt弹出层（有弹出层无法关闭bug）
 * 使用lhgdialog弹出层
 * 放弃 window.alert = showAlertDivLayer 浏览器alert不能占用
 * @user:943
 * @date:2013-9-10 12:00:00
 */


/**初始化参数*/

var config = {
		// 注意，此配置参数只能在这里使用全局配置，在调用窗口的传参数使用无效
		lock : true,
		fixed : true,
		max : false,
		min :false
};
/* 扩展窗口外部方法 */ 
var notice = function( options ) 
{ 
    var opts = options || {}, 
        api, aConfig, hide, wrap, top, 
        duration = opts.duration || 800; 
         
    var config = { 
        id: 'Notice', 
        left: '100%', 
        top: '100%', 
        fixed: true, 
        drag: false, 
        resize: false, 
        max : false,
		min :false,
        init: function(here){ 
            api = this; 
            aConfig = api.config; 
            wrap = api.DOM.wrap; 
            top = parseInt(wrap[0].style.top); 
            hide = top + wrap[0].offsetHeight; 
                         
            wrap.css('top', hide + 'px') 
            .animate({top: top + 'px'}, duration, function(){ 
                opts.init && opts.init.call(api, here); 
            }); 
        }, 
        close: function(here){ 
            wrap.animate({top: hide + 'px'}, duration, function(){ 
                opts.close && opts.close.call(this, here); 
                aConfig.close = $.noop; 
                api.close(); 
            }); 
                         
            return false; 
        } 
    }; 
         
    for(var i in opts) 
    { 
        if( config[i] === undefined ) config[i] = opts[i]; 
    } 
         
    return jQuery.dialog( config ); 
}; 


/**
 * 在弹出iframe窗口中刷新父页面，并关闭窗口
 */
function refershParent(){
	if(frameElement && frameElement.api){
		var api = frameElement.api,W = api.opener;
		W.jQuery('#search_go').click();
		closeDialog();
	}
}

/**
 * 关闭模态窗口
 */
function closeDialog(dialogId){
	var api = frameElement.api; 
	if(dialogId && api.get(dialogId)){
		api.get(dialogId,1).close();
	}else{
		api.close();
	}
	api.zindex();
}
/**
 * 关闭所有窗口
 */
function closeAllDialog(){
	if(frameElement && frameElement.api){
		var api = frameElement.api,W = api.opener;
		var list = W.lhgdialog.list; 
		for( var i in list ){ 
		    list[i].close(); 
		}
	}
	
}
/**弹出窗口
 * @param message 消息内容
 * @param width 宽度
 * @param height 高度
 * @param urls URL
 * @param other 其它参数 json 格式
 * @return
 */
function showDialog(message,width,height,url,other){
	var params={
		title: message,
		content: "url:"+url,
		width: getAdapterWidth(width),
		height: getAdapterHeight(height)
	}
	params = jQuery.extend(params, config);
	if(frameElement&& frameElement.api){
		var api = frameElement.api, W = api.opener;
		var childDialog = api.get("childDialog");
		if(childDialog!=null){
			params["id"]="childDialog2";
			params["parent"]=api;
			params = jQuery.extend(params, other);
			return W.jQuery.dialog(params);
		}
		params["id"]="childDialog";
		params["parent"]=api;
		params = jQuery.extend(params, other);
		return W.jQuery.dialog(params);
	}else{
		params["id"]="parentDialog";
		params["parent"]=window;
		params = jQuery.extend(params, other);
		return jQuery.dialog(params);
	}

    
}


//获取父类窗口window对象 
function getParentDialogWin(){
	if(frameElement && frameElement.api){
		var api = frameElement.api, Win = api.content;
		if(api.get("childDialog2") != null && api.get("childDialog2") != Win){
			return api.get("childDialog2");
		}else if(api.get("childDialog") != null && api.get("childDialog") != Win){
			return api.get("childDialog");
		}else if(api.get("parentDialog") != null && api.get("parentDialog") != Win){
			return api.get("parentDialog");
		}else{
			return api.opener;
		}
	}else{
		return window.parent;
	}
}

/**消息弹出层
 * @param message 内容
 * @param width 宽度
 * @param height 高度
 * @param title 标题
 * @param other 其它参数 支持json
 * @return
 */
function alertDialog(message,width,height,title,other){
	var params={
		title: title,
		content: message,
		width: width,
		height: height
	}
	params = jQuery.extend(params, config);
	params = jQuery.extend(params, other);
	if(frameElement&& frameElement.api){
		var api = frameElement.api, W = api.opener;
		params["parent"]=api;
		return W.jQuery.dialog(params);
	}else{
		params["parent"]=window;
		return jQuery.dialog(params);
	}	
}
/**保存结果提示框
 * @param message
 * @param callback
 * @param title
 * @return
 */
function alertMessage(message,callback,title){
	if(message!=null && message.length>0){
		var index_cg= message.indexOf('成功');
		var index_sb= message.indexOf('失败');
		if(index_cg>0){
			alertSuccess(message, callback, title);
		}else if (index_sb>0){
			alertFail(message, callback, title);
		}
	}
	alertInfo(message, callback, title);
}
/**警告弹出层
 * @param message 消息内容
 * @param title 标题
 * @param callback 回调
 * @return
 */
function alertInfo(message,callback,title,other){
	/**系统管理  alert('${result}', '', { 'clkFun' : function() { 	refershParent(); } }); 无耻的方法做下兼容 ******/
	if(title!=null && title!=""){
		if(title.clkFun!=null && title.clkFun!=""){
			title=null;
			callback=function(){
				this.close();
				var api = frameElement.api, W = api.opener;
				W.jQuery("#search_go").click();
				api.close();
			}
		}
	}
	/*****************************end************************************************************/
	var param={icon:'alert.gif',id:'alertDialog',ok:default_callback(callback)}
	alertDialog(message,220,100,default_title(title),jQuery.extend(param,other));
}
/**失败弹出层
 * @param message 消息内容
 * @param title 标题
 * @param callback 回调
 * @return
 */
function alertFail(message,callback,title,other){
	var param={icon:'error.gif',id:'alertDialog',ok:default_callback(callback)}
	alertDialog(message,220,100,default_title(title),jQuery.extend(param,other));
}
/**成功弹出层
 * @param message 消息内容
 * @param title 标题
 * @param callback 回调
 * @return
 */
function alertSuccess(message,callback,title,other){
	var param={icon:'success.gif',id:'alertDialog',ok:default_callback(callback)};
	alertDialog(message,220,100,default_title(title),jQuery.extend(param,other));
}
/**确认弹出
 * @param message 消息内容
 * @param title 标题
 * @param callback 回调
 * @return
 */
function alertConfirm(message,yes,cancle,title){
	alertDialog(message,220,100,default_title(title),{icon:'prompt.gif',id:'alertDialogConfirm',ok:default_callback(yes),cancelVal:'取消',cancel:true});
}
function default_title(title){
	if(title==null||title.length==0){
		return "温馨提示";
	}
	return title;
}
function default_callback(callback){
	if(callback==null){
		return function(){
			if(frameElement&& frameElement.api){
				var api = frameElement.api; 
				api.zindex();
			}
			this.close();
			return false;
		};
	}else if(callback==false){
		return null;
	}
	return callback;
}
/**右下脚弹出提示
 * @param message 提示消息
 * @param time 显示时长
 * @param title 标题
 * @param other 其它参数
 * @return
 */
function alertNotice(message,time,title,other){
	var param = { 
		    title: default_title(title), 
		    width: 220,  /*必须指定一个像素宽度值或者百分比，否则浏览器窗口改变可能导致lhgDialog收缩 */
		    height:100,
		    content: message, 
		    time: time
		};
	notice(jQuery.extend(param,other));
}
/**输入文本框
 * @param message 消息内容
 * @param callback 回调
 * @param title 标题
 * @param defval 默认值
 * @param other 其它参数
 * @return
 */
function alertPrompt(message,callback,title,defval,other){
	var params={
		title: title,
		content:"<span style='font-weight:bold;'>" + message + "</span><input type='text' id='alertPrompt_text' style='margin-top:10px;width:250px;height:30px;' value='"+defval+"'>",
		width: 300,
		//icon:"prompt.gif",
		height: 100,
		ok:function (){
				callback(frameElement.api.opener.jQuery("#alertPrompt_text").val());
			},
		cancelVal: '关闭',
        cancel: true /*为true等价于function(){}*/
	}
	params = jQuery.extend(params, config);
	params = jQuery.extend(params, other);
	if(frameElement&& frameElement.api){
		var api = frameElement.api, W = api.opener;
		params["parent"]=api;
		return W.jQuery.dialog(params);
	}else{
		params["parent"]=window;
		return jQuery.dialog(params);
	}	
	
	
}

/**延迟加载加载
 * @param time 多长时间关闭
 * @param callback 回调
 * @param message 消息内容 默认 数据加载中
 * @param other 其它参数
 * @return
 */
function alertTips(time,callback,message){
	if(!message){
		message='数据加载中...';
	}
	var params={
		id: 'alertTips',
		title: false,
		content:message,
		width: 150,
		height: 50,
		icon: 'loading.gif',
		callback:callback
	}
	var rr ;
	params = jQuery.extend(params, config);
	if(frameElement&& frameElement.api){
		var api = frameElement.api, W = api.opener;
		params["parent"]=api;
		rr =  W.jQuery.dialog(params);
	}else{
		params["parent"]=window;
		rr = jQuery.dialog(params);
	}	
	rr.time(time);
	return rr;
}

function watermark(settings) {
  //默认设置
  var defaultSettings={
    watermark_txt:"text",
    watermark_x:120,//水印起始位置x轴坐标
    watermark_y:120,//水印起始位置Y轴坐标
    watermark_rows:1,//水印行数
    watermark_cols:3,//水印列数
    watermark_x_space:50,//水印x轴间隔
    watermark_y_space:50,//水印y轴间隔
    watermark_color:'rgb(198, 205, 214)',//水印字体颜色
    watermark_alpha:0.5,//水印透明度
    watermark_fontsize:'58px',//水印字体大小
    watermark_font:'微软雅黑',//水印字体
    watermark_width:350,//水印宽度
    watermark_height:80,//水印长度
    watermark_angle:0//水印倾斜度数
  };
  //采用配置项替换默认值，作用类似jquery.extend
  if(arguments.length===1&&typeof arguments[0] ==="object" )
  {
    var src=arguments[0]||{};
    for(key in src)
    {
      if(src[key]&&defaultSettings[key]&&src[key]===defaultSettings[key])
        continue;
      else if(src[key])
        defaultSettings[key]=src[key];
    }
  }
  var oTemp = document.createDocumentFragment();
  //获取页面最大宽度
  var page_width = Math.max(document.body.scrollWidth,document.body.clientWidth);
  //获取页面最大长度
  var page_height = Math.max(document.body.scrollHeight,document.body.clientHeight);
  //如果将水印列数设置为0，或水印列数设置过大，超过页面最大宽度，则重新计算水印列数和水印x轴间隔
  if (defaultSettings.watermark_cols == 0 || (parseInt(defaultSettings.watermark_x + defaultSettings.watermark_width *defaultSettings.watermark_cols + defaultSettings.watermark_x_space * (defaultSettings.watermark_cols - 1)) > page_width)) {
    defaultSettings.watermark_cols = parseInt((page_width-defaultSettings.watermark_x+defaultSettings.watermark_x_space) / (defaultSettings.watermark_width + defaultSettings.watermark_x_space));
    defaultSettings.watermark_x_space = parseInt((page_width - defaultSettings.watermark_x - defaultSettings.watermark_width * defaultSettings.watermark_cols) / (defaultSettings.watermark_cols - 1));
  }
  //如果将水印行数设置为0，或水印行数设置过大，超过页面最大长度，则重新计算水印行数和水印y轴间隔
  if (defaultSettings.watermark_rows == 0 || (parseInt(defaultSettings.watermark_y + defaultSettings.watermark_height * defaultSettings.watermark_rows + defaultSettings.watermark_y_space * (defaultSettings.watermark_rows - 1)) > page_height)) {
    defaultSettings.watermark_rows = parseInt((defaultSettings.watermark_y_space + page_height - defaultSettings.watermark_y) / (defaultSettings.watermark_height + defaultSettings.watermark_y_space));
    defaultSettings.watermark_y_space = parseInt(((page_height - defaultSettings.watermark_y) - defaultSettings.watermark_height * defaultSettings.watermark_rows) / (defaultSettings.watermark_rows - 1));
  }
  var x;
  var y;
  for (var i = 0; i < defaultSettings.watermark_rows; i++) {
    y = defaultSettings.watermark_y + (defaultSettings.watermark_y_space + defaultSettings.watermark_height) * i;
    for (var j = 0; j < defaultSettings.watermark_cols; j++) {
      x = defaultSettings.watermark_x + (defaultSettings.watermark_width + defaultSettings.watermark_x_space) * j;

      var mask_div = document.createElement('div');
      mask_div.id = 'mask_div' + i + j;
      mask_div.setAttribute("name",'water-mask');
      mask_div.appendChild(document.createTextNode(defaultSettings.watermark_txt));
      //设置水印div倾斜显示
      mask_div.style.webkitTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
      mask_div.style.MozTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
      mask_div.style.msTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
      mask_div.style.OTransform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
      mask_div.style.transform = "rotate(-" + defaultSettings.watermark_angle + "deg)";
      mask_div.style.visibility = "";
      mask_div.style.position = "absolute";
      mask_div.style.left = x + 'px';
      mask_div.style.top = y + 'px';
      mask_div.style.overflow = "hidden";
      mask_div.style.zIndex = "9999";
      mask_div.style.opacity = defaultSettings.watermark_alpha;
      mask_div.style.fontSize = defaultSettings.watermark_fontsize;
      mask_div.style.fontFamily = defaultSettings.watermark_font;
      mask_div.style.color = defaultSettings.watermark_color;
      mask_div.style.textAlign = "center";
      mask_div.style.width = defaultSettings.watermark_width + 'px';
      mask_div.style.height = defaultSettings.watermark_height + 'px';
      mask_div.style.display = "block";
      mask_div.ondblclick = function(){
    	 this.style.display = "none";
      };  
     oTemp.appendChild(mask_div);
    };
  };
  
  if(document.getElementById("mask_div00")==null && document.getElementsByName("water-mask").length == 0){
	  document.body.appendChild(oTemp);
  }else{
	  for(var i = 0; i < document.getElementsByName("water-mask").length; i++){
		  document.getElementsByName("water-mask")[i].style.display = 'block';
	  }
  }
  
}

