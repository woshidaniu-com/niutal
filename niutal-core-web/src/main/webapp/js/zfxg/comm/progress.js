var myBar;
/**
 * 加载进度条
 * 
 * @param barkey
 *            唯一key
 * @param calback
 *            回调方法
 * @return
 */
function loadBar(barkey,calback,time){
	// 默认时间
	if(!time){
		time=1000;
	}
	myBar=setInterval(function(){
		var url =_path+"/niutal/drdc/progress_getProgressBar.html";
	 	jQuery.ajax({
			url:url,
			data:{barkey:barkey},
			type:"post",
			dataType:"json",
			async:true,
			success:function(data){
				if(!data){
		 			alert("执行超时！");
		 			clearInterval(myBar);
		 			return false;
		 		}
				//更改进度条进度显示
				jQuery("#bar").css("width",data.rate+"%");
				jQuery("#bl").text(data.rate+"%");
				//如果完成则结束获取
				if(data.finish){
					clearInterval(myBar);
				}
				// 如果完成则结束
				if(calback&&!calback(data)){
					clearInterval(myBar);
				}else{
					jQuery("#progress").show();
				}
			}
		});	
	},time);
}
function showProgress(){
	jQuery("#progress").show();
}
function finishBar(barkey){
	var url =_path+"/niutal/drdc/progress_finish.html";
 	jQuery.ajax({
		url:url,
		data:{barkey:barkey},
		type:"post",
		dataType:"json",
		async:true
	});	
}
// 进度条提示切关闭当前页面，刷新父页面
function barAlter(message){
	alert(message);
}
/**
 * 停止进度条
 * 
 * @return
 */
function stopBar(){
	clearInterval(myBar);
	jQuery("#progress").hide();
}
/**
 * 锁定按钮(用于提交数据后防止多次提交)
 * 
 * @return
 */
function lock(){
	jQuery("button").each(function(){
		jQuery(this).attr("disabled",true);
		jQuery(this).css({color:"#cccccc"});
	});
}
/**
 * 解除锁定
 * 
 * @return
 */
function unlock(){
	jQuery("button").each(function(){
		jQuery(this).removeAttr("disabled");
		jQuery(this).css({color:"#ffffff"});
	});
}