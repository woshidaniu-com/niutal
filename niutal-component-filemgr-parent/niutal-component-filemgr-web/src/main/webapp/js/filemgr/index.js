jQuery(function($){
	
	//全局函数有提供format方法
    String.prototype.format = function()  {
        var args = (arguments.length === 1 && $.isArray(arguments[0])) ? arguments[0] : arguments;
        var formattedString = this;
        for (var i = 0; i < args.length; i++)
        {
            var pattern = new RegExp("\\{" + i + "\\}", "gm");
            formattedString = formattedString.replace(pattern, args[i]);
        }
        return formattedString;
    };
    
    Array.prototype.contains = function(item, from) {
		var bl = false, from = from || 0;
		for (var index = from; index < this.length; index++) {
			if (item == this[index]) {
				bl = true;
				break;
			}
		}
		return bl;
	};
	
    
	var fileTypes = {
		"folder" 					: '<i class="fa fa-folder" aria-hidden="true"></i>',
		"file" 						: '<i class="fa fa-file-o" aria-hidden="true"></i>',
		"java|ftl|jsp" 				: '<i class="fa fa-file-code-o" aria-hidden="true"></i>',
		"mp3|mp4|wav|wma|wmv|mid" 	: '<i class="fa fa-file-audio-o" aria-hidden="true"></i>',
		"gif|jpg|jpeg|png|bmp" 		: '<i class="fa fa-file-image-o" aria-hidden="true"></i>',
		"avi|mpg|asf|rm|rmvb" 		: '<i class="fa fa-file-video-o" aria-hidden="true"></i>',
		"doc|docx" 					: '<i class="fa fa-file-word-o" aria-hidden="true"></i>',
		"xls|xlsx" 					: '<i class="fa fa-file-excel-o" aria-hidden="true"></i>',
		"ppt|pptx" 					: '<i class="fa fa-file-powerpoint-o" aria-hidden="true"></i>',
		"pdf" 						: '<i class="fa fa-file-pdf-o" aria-hidden="true"></i>',
		"txt" 						: '<i class="fa fa-file-text-o" aria-hidden="true"></i>',
		"zip,rar,gz,bz2"			: '<i class="fa fa-file-zip-o" aria-hidden="true"></i>'
	};
	
	function getIco(item){
		var icoHtml = fileTypes["file"];
		$.each(fileTypes,function(key, ico){
			if(key.split("|").contains(item["type"].toLowerCase())){
				icoHtml = ico;
				return false;
			}
		});
		return icoHtml;
	}
	
	function listFiles(parent){
		/*列举文件夹内容*/
		$.ajax({ 
			type	: "POST",
			url		: _path + "/filemgr/list.zf",
			async	: false,
			cache	: false,
			data	: {
				"parent"	: parent
	    	}
		}).done(function (data){
			if(data){
				var html = [];
				$.each(data||[],function(i, item){
					html.push('<div class="col-xs-6 col-sm-2 col-md-1 col-lg-1 ">');
						html.push('<div class="thumbnail" data-name="' + item["name"] + '">');
							html.push('<span class="glyphicon glyphicon-ok"></span>');
							html.push('<p class="text-center">'+ getIco(item) + '</p>');
								html.push('<div class="caption">');
										html.push('<p class="text-center">' + item["name"] + '</p>');
								html.push('</div>');
							html.push('</div>');
					html.push('</div>');
				});
				$("#filemgr_container").empty().append(html.join(""));
				$("#filemgr_container").show();
			} else{
				$("#filemgr_container").hide();
				$("#filemgr_tip").show();
			}
		});
	}
	/*
	.off("mouseenter", "div.thumbnail").on("mouseenter", "div.thumbnail", function(e) {
		console.log(this);
		
	}).off("mouseleave", "div.thumbnail").on("mouseleave", "div.thumbnail", function(e) {
		console.log(this);
		$(this).removeClass("selected");
	})
	*/
	$("#filemgr_container").off("click touchend", "span.glyphicon-ok").on("click touchend", "span.glyphicon-ok", function(e) {
		$(this).parent().addClass("selected");
		
		var selectCount = $("#filemgr_container").find("div.thumbnail.selected").size();
		
		if(selectCount > 0 ){
			
			
			$("#btn_download")
		}
		
		console.log("size:" + selectCount);
		
		e.stopPropagation();
	}).off("click touchend", "div.thumbnail").on("click touchend", "div.thumbnail", function(e) {
		console.log(this);
		listFiles($(this).data("name"));
		//<span class="glyphicon glyphicon-ok"></span>
	});
	
	
	function fileInfo(uid){
		/*列举文件夹内容*/
		$.ajax({ 
			type	: "POST",
			url		: _path + "/filemgr/info.zf",
			async	: false,
			cache	: false,
			data	: {
				"uid"	: uid
	    	}
		}).done(function (data){
			 
		});
	}
	
	//显示根节点文件夹
	listFiles("root");
	
    
    $("#previousStep").click(function(){
    	var btn = $(this)
        btn.button('loading')
    	form.steps("previous");
    	btn.button('reset')
    });
    
    $("#repeatedSend").click(function(){
    	var btn = $(this)
        btn.button('loading');
    	$.ajax({ 
    		type	: "POST",
    		url		: _path + "/pwdmgr/retake/advice.zf",
    		async	: false,
    		cache	: false,
    		data	: {
    			"username" 		: $("#userName").val(),
    			"step" 			: "2",
    			"retakeType"	: form.find("input[name='retakeType']:checked").val(),
    			"map['uuid']"	: form.find("#returnUuid").val()
        	}
    	}).done(function (data){
    		//没有通过检查
    		if ("success" != data["status"]){
    			$("#verifTip2").html(data["message"]).show();
    		}else{
    			$("#returnUuid" ).val(data["uuid"]);
    		}
    	}).always(function () {
    		//59秒之后才能再次重发
    		var times = 58;
    		var intr = window.setInterval(function(){
    			if(times == 0){
    				window.clearInterval(intr);
    				btn.text("再次发送");
    				btn.button('reset');
    			} else {
    				//改变提醒
        			var text = times + "秒钟后再次发送...";
        			btn.attr("data-loading-text", text);
        			btn.prop("disabled",true).text(text).prop("disabled",true);
    			}
    			
    			times --;
    		}, 1000);
        });
    });
    
    
});
