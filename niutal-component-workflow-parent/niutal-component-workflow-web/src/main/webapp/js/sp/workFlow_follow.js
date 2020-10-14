jQuery(function($){
	$(".zt1").each(function(){
    	var height=$(this).children().height();
		if(height>60){
			$(this).children().addClass("height60");
			$(this).children().children(".more").show();
			
			/*鼠标移动事件*/
			var ID=$(this).children().attr("id");
			$("#"+ID).hover(function() {
				$("#"+ID).addClass("content1");
				$(this).children(".more").hide();
				$("#"+ID).removeClass("height60");
			},function(){
				$("#"+ID).removeClass("content1");
				$(this).children(".more").show();
				$("#"+ID).addClass("height60");
			});
		}
	})
	
	$(".step ul li").each(function(){
		var classname	=	$(this).attr("class");
		if(classname	==	"yzz"){
			$(".step .text").html("结束[不通过]");
			return false;
		}else if(classname	==	"shz"){
			$(".step .text").html("审核中");
			return false;
		}else{
			$(".step .text").html("结束[通过]");
		}
	});
	
	/*给content赋值宽度*/
	var widthall	=	parseInt($(".zt1").actual('innerWidth'));// $(".zt1").width();
//	$(".zt1 .content").css("width",widthall-77);
	
	/*给li赋值宽度*/
	var len	=	parseInt($(".step").actual('innerWidth'))-10;//$(".step").width()
	/*最后一个li的实际宽度*/
	var len1	=	parseInt($(".step ul li").last().find(".user").actual('innerWidth')) + 30;
	/*当前状态的实际宽度*/
	var len2	=	parseInt($(".step .text").actual('innerWidth'))+30;
	var len_ul	=	len	-	len1	-	len2;
	var num	=	$(".step ul li").length - 1;
	var li_width	=	len_ul/num;
//	$(".step ul li").css("width",li_width);
	
//	$(".step ul li").last().children(".num").css("width","19px");
//	$(".step ul li").last().css("width",len1-30);
//	$(".step ul li .user").css("margin-left","-25px");
	
	setTimeout(function(){
		var contentWidth=$('.step').actual('outerWidth');
		var shWidth=$('.step .text').actual('outerWidth')
		var ulWidth=contentWidth-shWidth-120;
		var size=$('.step ul li').size();
		//console.log(contentWidth);
		//console.log(ulWidth);
		//console.log(size);
		$(".step ul li").css({
			width:ulWidth/size,
		})
	},200);
	
	$(".step ul .shz").prevAll("li").addClass("ysh");
	$(".step ul .yzz").prevAll("li").addClass("ysh");
	$(".step ul .shz").nextAll("li").addClass("wsh");
	$(".step ul .yzz").nextAll("li").addClass("wsh");
});