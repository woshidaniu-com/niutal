/*window.onload = function(){
	//页面滚动问题
	var myscroll=new iScroll("wrapper");
};*/

/*jQuery(function(){
	var myscroll=new iScroll("wrapper");
});
*/
//确认提示框
function addConfirm(text, callback){
	var closeLink = jQuery("<a href='javascript:void(0);'>取消</a>").bind("click", function(){removeerrorCall();});
	var okLink = jQuery("<a href='javascript:void(0);'>提交</a>").bind("click", function(){callback();});
	var block = jQuery("<div>", {"class": "loadingDialog"})
			.append(jQuery("<p>", {"class": "title", text: "提示"}))
			.append(jQuery("<p>", {"class": "con", text: text}))
			.append(jQuery("<p>", {"class": "links"}).append(closeLink).append(okLink));
	jQuery(document.body).append(block).append(jQuery("<div class='fishDialogMask'></div>"));
	jQuery(".fishDialogMask").animate({opacity:"0.5"});
	jQuery(".loadingDialog").animate({opacity:"1"});
}

//错误提示代码
function adderror(text){
	$(document.body).append("<div class='loadingDialog'><p class='title'>提示</p><p class='con'>"+ text +"</p><p class='close'><a href='javascript:;' onclick='removeerror()'>关闭</a></p></div><div class='fishDialogMask'></div>");
	$(".fishDialogMask").animate({opacity:"0.5"});
	$(".loadingDialog").animate({opacity:"1"});
	
//	setTimeout(function() {
//		removeerror();
//	}, 1500);
}
function adderrorCall(text){
	$(document.body).append("<div class='loadingDialog'><p class='title'>提示</p><p class='con'>"+ text +"</p><p class='close'><a href='javascript:;' onclick='removeerrorCall()'>确定</a></p></div><div class='fishDialogMask'></div>");
	$(".fishDialogMask").animate({opacity:"0.5"});
	$(".loadingDialog").animate({opacity:"1"});
	
//	setTimeout(function() {
//		removeerror();
//	}, 1500);
}
function removeerrorCall(){
	if($(".loadingDialog").length > 0){
	//	$(".loadingDialog").animate({top:"0"});
		$(".loadingDialog").remove();		
	}
	if($(".fishDialogMask").length > 0){
		$(".fishDialogMask").remove();
	}
	refresh();
}
function alertTips(){
	removeerror();
	var html="";
	html+="<p class='title'> <font color='red'>数据处理中</font></p>";
	html+="<p class='con'  >";
	html+="<img src='/ydyx/style/loading.gif'/>";
	html+="</p>";
	$(document.body).append("<div class='loadingDialog'>"+html+"</div><div class='fishDialogMask'></div>");
	$(".fishDialogMask").animate({opacity:"0.5"});
	$(".loadingDialog").animate({opacity:"1"});
}

//移除错误提示代码
function removeerror(){
	if($(".loadingDialog").length > 0){
	//	$(".loadingDialog").animate({top:"0"});
		$(".loadingDialog").remove();		
	}
	if($(".fishDialogMask").length > 0){
		$(".fishDialogMask").remove();
	}
}