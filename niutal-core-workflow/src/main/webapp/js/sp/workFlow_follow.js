jQuery(function($){
	
	$(".step ul li").each(function(){
		var classname	=	jQuery(this).attr("class");
		if(classname	==	"yzz"){
			jQuery(".step .text").html("结束[不通过]");
			return false;
		}else if(classname	==	"shz"){
			jQuery(".step .text").html("审核中");
			return false;
		}else if(classname	==	"wsh"){
			jQuery(".step .text").html("已退回申请人");
			return false;
		}else{
			jQuery(".step .text").html("结束[通过]");
		}
	});
	
	/*给content赋值宽度*/
	jQuery(".zt1 .content").css("width","400px");
	
	/*给li赋值宽度*/
	var len	=	jQuery(".step").width()-10;
	/*最后一个li的实际宽度*/
	var len1	=	jQuery(".step ul li").last().width();
	/*当前状态的实际宽度*/
	var len2	=	jQuery(".step .text").width()+30;
	var len_ul	=	len	-	len1	-	len2;
	var num	=	jQuery(".step ul li").length-1;
	var li_width	=	len_ul/num;
	jQuery(".step ul li").css("width",li_width*2.1);
	
	jQuery(".step ul li").last().children(".num").css("width","19px");
	jQuery(".step ul li").last().css("width",len1);
	jQuery(".step ul .shz").prevAll("li").addClass("ysh");
	jQuery(".step ul .yzz").prevAll("li").addClass("ysh");
	jQuery(".step ul .shz").nextAll("li").addClass("wsh");
	jQuery(".step ul .yzz").nextAll("li").addClass("wsh");
});