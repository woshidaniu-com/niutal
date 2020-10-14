jQuery(function($) {
	
	//计算tabs高度
	var pageHeight = $(window).height() - $('#tabs').height() - $('#footer').height()-$('#navbar').height();
	var boxHeight = $(window).height() - $('#footer').height();
	$('#boxed').height(boxHeight);
	
	// 监听自定义菜单加载完成事件
	$(document).off('menuReady').on('menuReady',function(){
		console.log("menuReady");
		//$("#iframe_home").attr("src", _path + '/apimgr/api/index/list?layout=default&th='+pageHeight);
		
	});

});