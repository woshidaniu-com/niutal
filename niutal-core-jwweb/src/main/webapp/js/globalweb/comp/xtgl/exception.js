var times_1 = 0;
var intervalFunc = function(){
	if( null != jQuery &&  undefined != jQuery){
		jQuery(function($) {

			$.fn.textPrint = function(text,speed,callback){
				var content= (text||"").split("");
				return this.each(function(i,elem){
					if(content.length > 0){
						$(elem).empty();
						var i = 0;
						callback = callback || $.noop;
						window.setInterval(function(){
							if( i < content.length){		
								$(elem).append(content[i]);
								i++;
							}else{
								callback.call(elem);
							};
						},speed || 50);
					}	
				});
			};
			
			//计算高度
			window.onresize = function(){  
				var body_height	=	$(window).height();
				var error_height	=	$(".error_v5").height() + 50;
				var margin_top	=	(body_height - error_height)/2;
				var margin_top	= 	(margin_top>0) ? margin_top : 0;
				$(".error_v5").css("margin-top",margin_top);
			} 
			$(window).resize();
			//文字打印效果
			$(".error_text").textPrint($(".error_text0").text(),50);
		});
	}else{
		if(times_1 <= 10){
			window.setTimeout(intervalFunc, 300);
		}
	}
	times_1 += 1;
};
window.setTimeout(intervalFunc,100);