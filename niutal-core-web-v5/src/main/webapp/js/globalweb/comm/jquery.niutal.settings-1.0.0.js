;(function($) {
	
	//指定预处理参数选项的函数 。如果自定义abortOnRetry选项被设置为true，那么调用$.ajax()会自动中止请求相同的URL：
	var currentRequests = {};
	/*设置全局Ajax处理*/
	$.ajaxSetup({
		//设置全局过滤参数为true,防止重复请求
		abortOnRetry: true,
		//不启用缓存
		cache		: false,
		contentType	: "application/x-www-form-urlencoded;charset=utf-8",
		statusCode	: {
			//HTTP Status 400（错误请求） 
	        400: function () {
				currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	/*//提示信息
	        	$.alert("错误请求,请检查参数是否正确! ",function(){
	        	},{modalName:"statusModal"});*/
	        },
	        //HTTP Status 401（未授权） 
	        401: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("访问被拒绝，试图未经授权访问受密码保护的页面!",function(){
	        		window.close();
	            },{modalName:"statusModal"});
	        },
	        //HTTP Status 404（未找到）
	        404: function () {
	        	currentRequests  = {};
				if(console && console.error){
					console.error("请求不存在的资源或网页!");
				}
	        },
	        //HTTP Status 408（请求超时） 
	        408: function () {
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("服务器等候请求时发生超时,请检查网络连接是否正确!",function(){
	        	},{modalName:"statusModal"});
	        },
	        //HTTP Status 500（服务器内部错误） 
	        500: function () {
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("服务器发生错误，无法完成请求!",function(){
	        	},{modalName:"statusModal"});
	        },
	        //HTTP Status 502（错误网关） 
	        502: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("服务器作为网关或代理，从上游服务器收到无效响应!",function(){
	        	},{modalName:"statusModal"});
	        },
	        //HTTP Status 503（服务不可用）
	        503: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert(" 服务器目前无法使用（由于超载或停机维护）。请服务启动后再次尝试!",function(){
	        	},{modalName:"statusModal"});
	        },
	        //HTTP Status 504（网关超时） 
	        504: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("服务器作为网关或代理，请求上游服务器超时!",function(){
	        	},{modalName:"statusModal"});
	        },
	        /*--------------自定义响应状态码-----------------*/
	        //HTTP Status 901（HTTP 会话过期）    -> 会话过期或者注销。
	        901: function () {
	       		//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("当前登录用户已退出登录或者会话已过期,请重新登录!",function(){
	        		top.location.href = _systemPath + "/logout";
	            },{modalName:"statusModal"});
	        },
	        //HTTP Status 902（未授权） ->请求要求身份验证。 对于需要登录的网页，服务器可能返回此响应。
	        902: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("当前用户登录的角色无此功能权限!",function(){
	        		window.close();
	            },{modalName:"statusModal"});
	        },
	        //HTTP Status 903（HTTP 浏览器会话变更）    -> 当前浏览器同一会话被其他用户登录，导致session变化。
	        903: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("同一浏览器中只允许一个用户登录!",function(){
	        		window.close();
	            },{modalName:"statusModal"});
	        },
	        //HTTP Status 904（HTTP 恶意刷新）    -> 相同的两个请求频率超过限制阀值时的状态提醒。
	        904: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("请勿频繁刷新或者点击菜单!~",function(){
	        		
	            },{modalName:"statusModal"});
	        },
	        //HTTP Status 905（HTTP 不一致）    -> 会话用户与指定参数值一致性校验结果不一致，服务器可能返回此响应。
	        905: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("当前请求路径启用参数值一致性校验过滤，您无权访问非自己权限内的数据!",function(){
	        		
	            },{modalName:"statusModal"});
	        },
	        //HTTP Status 906（HTTP 非法IP访问）    -> 请求客户端IP地址不在允许的IP白名单内，服务器可能返回此响应。
	        906: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("当前请求路径启用IP地址白名单过滤，发现非法IP客户端访问!",function(){
	        		
	            },{modalName:"statusModal"});
	        },
	        //HTTP Status 907（HTTP 非法MAC访问）    -> 请求客户端MAC地址不在允许的MAC白名单内，服务器可能返回此响应。
	        907: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("当前请求路径启用Mac地址白名单过滤，发现非法Mac客户端访问!",function(){
	        		
	            },{modalName:"statusModal"});
	        },
	        //HTTP Status 908（HTTP 危险来源）    -> 请求来源不明，服务器为了安全会对象范围来源进行逻辑判断，如果不符合要求则服务器可能返回此响应。
	        908: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("当前请求路径启用危险访问来源过滤，发现非法请求来源!",function(){
	        		
	            },{modalName:"statusModal"});
	        },
	        //HTTP Status 909（HTTP Action未定义）    -> 处理请求的Action对象未定义，则服务器可能返回此响应。
	        909: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("处理http请求的Action对象未初始化!",function(){
	        		
	            },{modalName:"statusModal"});
	        },
	        //HTTP Status 910（HTTP 方法未定义）    -> 请求的后台方法未定义，则服务器可能返回此响应。
	        910: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("请求的方法在Action对象中未定义!",function(){
	        		
	            },{modalName:"statusModal"});
	        },
	        //HTTP Status 911（HTTP 运行异常）    -> 应用程序运行期间发生错误，则服务器可能返回此响应。
	        911: function () {
	        	currentRequests  = {};
	        	//如果已经异常提醒，不再提示
	        	if($("#statusModal").size() > 0){return;}
	        	//提示信息
	        	$.alert("应用程序运行期间发生错误，请联系管理员查看异常日志!",function(){
	        		
	            },{modalName:"statusModal"});
	        }
	    }
	});

	//注册一个典型使用$.ajaxPrefilter()的预过滤器
	$.ajaxPrefilter(function( options, originalOptions, jqXHR ) {
		// Modify options, control originalOptions, store jqXHR, etc
		// options是请求的选项；对象 包括accepts、crossDomain、contentType、url、async、type、headers、error、dataType等许多参数选项
	    // originalOptions 值作为提供给Ajax方法未经修改的选项，因此，没有ajaxSettings设置中的默认值，如： { url: "/index.php" }
	    // jqXHR对象 就是经过jQuery封装的XMLHttpRequest对象(保留了其本身的属性和方法)
		if ( options.abortOnRetry ) {
			var params = $.param(originalOptions.data||{});
			var tmpURL =  $.founded(params) ? ((options.url||"") + ((options.url||"").indexOf("?") > -1 ? "&" : "?" ) +  params) : (options.url||"");
			var urlKey = (typeof $.md5 != 'undefined') ? $.md5(tmpURL) : tmpURL;
			var ajaxXHR = currentRequests[ urlKey ];
			if ( $.defined(ajaxXHR) ) {
				try{
					if(ajaxXHR.readyState!= 4){
						//取消 Ajax 请求 
						jqXHR.abort();
						//如果已经异常提醒，不再提示
						if(console && console.error){
							console.error("已存在与当前请求路径和参数相同的请求且未完成!");
						}
					}else{
						currentRequests[ urlKey ] = jqXHR;
					}
				}catch (e) {
					delete currentRequests[ urlKey ];
					console.error(e);
				}
			}else{
				currentRequests[ urlKey ] = jqXHR;
			}
		}
	});
	
	function clearXHR(options){
		if ( options.abortOnRetry ) {
			var params = $.isPlainObject(options.data) ? $.param(options.data||{}) : "";
			var tmpURL =  $.founded(params) ? ((options.url||"") + ((options.url||"").indexOf("?") > -1 ? "&" : "?" ) +  params) : (options.url||"");
			var urlKey = (typeof $.md5 != 'undefined') ? $.md5(tmpURL) : tmpURL;
			currentRequests[ urlKey ] = null;
			delete currentRequests[ urlKey ];
		}
	}
	
	/*设置Ajax请求发出前的处理函数*/
	$(document).ajaxSend( function(event,xhr,options){
    	if($.matchURL(options.url)){
			//js,css等资源文件 不做处理
    	}else{
			// event - 包含 event 对象
		    // xhr - 包含 XMLHttpRequest 对象
		    // options - 包含 AJAX 请求中使用的选项
		    
			//自动添加功能代码
			if(jQuery("#gnmkdmKey").size() == 1 && jQuery("#gnmkdmKey").founded() ){
				//如果有data
				if($.defined(options.data)){
					//如果是{}类型对象参数;在对象中追加或者替换
					if($.isPlainObject(options.data)){
						options.data["gnmkdmKey"] = jQuery("#gnmkdmKey").val();
					}else if($.defined(options.url)){
						//在url上追加
						if(options.url.indexOf("?") > -1){
							options.url = options.url + "&gnmkdmKey=" + jQuery("#gnmkdmKey").val();
						}else{
							options.url = options.url + "?gnmkdmKey=" + jQuery("#gnmkdmKey").val();
						}
					}
				}else if($.defined(options.url)){
					//在url上追加
					if(options.url.indexOf("?") > -1){
						options.url = options.url + "&gnmkdmKey=" + jQuery("#gnmkdmKey").val();
					}else{
						options.url = options.url + "?gnmkdmKey=" + jQuery("#gnmkdmKey").val();
					}
				}
			}
			//自动添加用户名
			if(jQuery("#sessionUserKey").size() == 1 && jQuery("#sessionUserKey").founded() ){
				//如果有data
				if($.defined(options.data)){
					//如果是{}类型对象参数;在对象中追加或者替换
					if($.isPlainObject(options.data)){
						options.data["sessionUserKey"] = jQuery("#sessionUserKey").val();
					}else if($.defined(options.url)){
						//在url上追加
						if(options.url.indexOf("?") > -1){
							options.url = options.url + "&sessionUserKey=" + jQuery("#sessionUserKey").val();
						}else{
							options.url = options.url + "?sessionUserKey=" + jQuery("#sessionUserKey").val();
						}
					}
				}else if($.defined(options.url)){
					//在url上追加
					if(options.url.indexOf("?") > -1){
						options.url = options.url + "&sessionUserKey=" + jQuery("#sessionUserKey").val();
					}else{
						options.url = options.url + "?sessionUserKey=" + jQuery("#sessionUserKey").val();
					}
				}
			}
    	}
	})
	/*设置Ajax请求发生错误的处理函数*/
	.ajaxError(function(event,xhr,options){
	    //event - 包含 event 对象
	    //xhr - 包含 XMLHttpRequest 对象
	    //options - 包含 AJAX 请求中使用的选项
	    //exc - 包含 JavaScript exception
		
		clearXHR(options);
		
		//加载过于频繁提醒
		if(xhr.status == 904 && $("#loading_status").size() > 0){

			var message = "请勿频繁刷新或者点击菜单,请<label id='times' data-time='"+_refreshInterval+"'>"+_refreshInterval+"</label>秒后再操作!~";
			$("#loading_status").replaceWith('<div class="red " style="font-size: 20px;margin-top: 150px;text-align: center;" role="alert"  id="messageTip">'+message+'</div>');

			var time = parseInt($("#times").data("time"));
			var	interval = window.setInterval(function(){
				time -= 1;
				if(time <= 0){
					window.clearInterval(interval);
					$("#messageTip").html("请重新刷新页面或者点击按钮!").removeClass("red2").addClass("green");
				}
				$("#times").text(time);
			}, 1000);
		}
	})
	/*设置Ajax请求完成后的处理函数【失败或者成功都会调用】*/
	.ajaxComplete(function(event,xhr,options){
		clearXHR(options);
	});
	
})(jQuery);