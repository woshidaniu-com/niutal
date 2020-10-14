jQuery(function($){
	
	//全局函数有提供format方法，此处需要使用setps插件自带的format方法覆盖；
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
	
	$.extend($.validator.messages, {
		equalTo: "两次密码输入不一致"
	});
	
	var form = $("#example-advanced-form").show();
	
	form.find("input[name='retakeType']").change(function(){
		 $(".text-verif-tip").hide();
	});

    
	/*步将要切换回调*/
	function doStepChanging(event, currentIndex, newIndex){
		var result = false;
		var requstMap = {
			"username" 	: $("#userName").val(),
			"step" 		: (currentIndex + 1)
    	};
    	var requestURL = null;
    	console.log(currentIndex);
    	switch (currentIndex){
            case 0:{
            	//找到类型，并且找到填入的值
            	var $radio = form.find("section:eq(0)").find(":radio:checked");
            	var verifiType = $radio.val();
            	requstMap["verifiType" ] = verifiType;
            	
            	var verifiValue = $radio.parent().next().find("input").val();
            	if(verifiType == "email"){
            		requstMap["email" ] = verifiValue;    		
            	}else{
            		requstMap["phone" ] = verifiValue;
            	}
            	requestURL = _path + "/pwdmgr/retake/verifi.zf";
            }; break;
            case 1:{
            	requstMap["retakeType"] = form.find("input[name='retakeType']:checked").val();
            	requstMap["map['uuid']"] = form.find("#returnUuid").val();
            	requestURL = _path + "/pwdmgr/retake/advice.zf";
            }; break;
            case 2:{
            	requstMap["map['uuid']"] = form.find("#returnUuid").val();
            	requstMap["map['captcha']"] = form.find("#captcha").val();
            	requstMap["retakeType"] = form.find("input[name='retakeType']:checked").val();
            	requestURL = _path + "/pwdmgr/retake/retake.zf";
            }; break;
        }
    	if(!$.founded(requestURL)){
    		console.log("requestURL not found.");
    		return false;
    	}
    	$.ajax({ 
    		type	: "POST",
    		url		: requestURL,
    		async	: false,
    		cache	: false,
    		data	: requstMap
    	}).done(function (data){
    		//没有通过检查
    		if ("success" != data["status"]){
    			$("#verifTip" + currentIndex ).html(data["message"]).show();
    			switch (currentIndex){
		            case 0:{
		            	form.find("section:eq("+currentIndex+")").addClass("has-error");
		            }; break;
		            case 1:{
		            }; break;
		            case 2:{
		            }; break;
		        }
    		}else{
    			//给通过的步骤元素改变颜色
    			form.find("section:eq("+currentIndex+")").addClass("has-success");
    			//添加通过后的标记
    			form.find("div.steps:eq(0)").find("li:eq("+currentIndex+")").addClass("success");
    			form.find("section:eq("+currentIndex+")").find(":input").prop("readonly",true);
    			
    			$("#step").val(data["step"])
    			//根据不同操作步骤，对不同的值进行赋值操作
    			switch (currentIndex){
		            case 0:{
		            	$("#verifTip1_text").html(data["message"]);
		            }; break;
		            case 1:{
		            	$("#mobileTip" ).html(data["message"]);
		            	$("#returnUuid" ).val(data["uuid"]);
		            }; break;
		            case 2:{
		            	$("#returnUuid" ).val(data["uuid"]);
		            }; break;
		        }
    			result = true ;
    		}
    	});
    	return result;
	}
	
	/*步将要结束回调*/
	function doFinishing(event, currentIndex){
		var result = false;
		$.ajax({ 
    		type	: "POST",
    		url		: _path + "/pwdmgr/retake/update.zf",
    		async	: false,
    		cache	: false,
    		data	: {
    			"username" 			: $("#userName").val(),
    			"step" 				: (currentIndex + 1),
    			"map['uuid']"		: form.find("#returnUuid").val(),
            	"map['captcha']"	: form.find("#captcha").val(),
    			"map['newpwd']" 	: form.find("#newPassword").val(),
    			"map['repeatpwd']" 	: form.find("#repeatPassword").val()
        	}
    	}).done(function (data){
    		//没有通过检查
    		if ("success" != data["status"]){
    			$("#verifTip" + currentIndex ).html(data["message"]).show();
            	form.find("section:eq("+currentIndex+")").addClass("has-error");
    		}else{
    			//给通过的步骤元素改变颜色
    			form.find("section:eq("+currentIndex+")").addClass("has-success");
    			//添加通过后的标记
    			form.find("div.steps:eq(0)").find("li:eq("+currentIndex+")").addClass("success");
    			form.find("section:eq("+currentIndex+")").find(":input").prop("readonly",true);
    			
    			$("#verifTip" + currentIndex ).html(data["message"]).removeClass("text-danger").addClass("text-success").show();
    			
    			result = true ;
    			
    			//5秒之后返回登录页面
        		var times = 5;
        		var successTip = $("#successTip");
        		var intr = window.setInterval(function(){
        			if(times == 0){
        				window.clearInterval(intr);
        				//回到登录页面
            			if($.founded(data["toURL"])){
            				window.location.href = data["toURL"];
            			}else{
            				window.location.href = _path + "/";
            			}
        			} else {
        				//改变提醒
            			successTip.html(times + "秒钟后将跳转到登录页面...");
        			}
        			times --;
        		}, 1000);
        		
    		}
    	});
		return result;
	}
	
	form.steps({
		headerTag			: "h3",
		bodyTag				: "section",
		transitionEffect	: "slideLeft",
		autoFocus			: true,
		forceMoveForward	: true,
		labels: {
			cancel	 : "取消",
			finish	 : "提交",
			next	 : "下一步",
			previous : "上一步",
			loading	 : "请等待 ..."
		},
		/*
		 * Events
		 */
		/**
		 * Fires before the step changes and can be used to prevent step changing by returning `false`. 
		 * Very useful for form validation. 
		 **/
		onStepChanging		: function (event, currentIndex, newIndex) { 
			console.log("onStepChanging: currentIndex-" + currentIndex +",newIndex-" + newIndex );
			// Allways allow previous action even if the current form is not valid!
			if (currentIndex > newIndex) {
				return true;
			}
			// Needed in some cases if the user went back (clean up)
			if (currentIndex < newIndex) {
				// To remove error styles
				form.find(".body:eq(" + newIndex + ") label.error").remove();
				form.find(".body:eq(" + newIndex + ") .error").removeClass("error");
			}
			form.validate().settings.ignore = ":disabled,:hidden,.ignore";
			if(form.valid()){
				return doStepChanging(event, currentIndex, newIndex)
			};
			return false;
		},

		/**
		 * Fires after the step has change. 
		 **/
		onStepChanged		: function (event, currentIndex, priorIndex) { 
			console.log("onStepChanged: currentIndex-" + currentIndex +",priorIndex-" + priorIndex );
			// Used to skip the "Warning" step if the user is old enough.
			if (currentIndex === 2 && Number($("#age-2").val()) >= 18)
			{
				form.steps("next");
			}
			// Used to skip the "Warning" step if the user is old enough and wants to the previous step.
			if (currentIndex === 2 && priorIndex === 3)
			{
				form.steps("previous");
			}
			$(".text-verif-tip").hide();
			if(currentIndex < 3){
				$("#captcha").clearInputs();
			}
			form.find("section").removeClass("has-error");
		},

		/**
		 * Fires after cancelation. 
		 **/
		onCanceled		: function (event) { 
			console.log("onCanceled");
		},

		/**
		 * Fires before finishing and can be used to prevent completion by returning `false`. 
		 * Very useful for form validation. 
		 **/
		onFinishing		: function (event, currentIndex) { 
			console.log("onFinishing: currentIndex-" + currentIndex);
			form.validate().settings.ignore = ":disabled,:hidden,.ignore";
			if(form.valid() && currentIndex == 3){
				return doFinishing(event, currentIndex);
			}
			return false;
		},

		/**
		 * Fires after completion. 
		 **/
		onFinished		: function (event, currentIndex) {
			//所有的操作都完成
			console.log("onFinished: currentIndex-" + currentIndex);
			//alert("Submitted!");
			$(form).find("div.actions ul li").remove();
		},

		/**
		 * Fires when the wizard is initialized. 
		 **/
		onInit		: function (event, currentIndex) { 
			 $(".text-verif-tip").hide();
			/* $(form).validateForm({
				//提示信息显示方式 tooltips|wrap
				tipMethod			: "wrap", 
				//进行验证前的回调函数
				beforeValidated:function(){
					return true;
				},
				//提交前的回调函数
				beforeSubmit:function(formData,jqForm,options){
					return true;
				},
				//提交后的回调函数  
				afterSuccess:function(responseData,statusText){
					afterSuccessFunc.call(validator, responseData,statusText);
				}
			});
			//认为的控制不能直接点击回到上一步
			//$(form).find("div.actions ul li:eq(0)").remove();
			//$(form).find("ul[role='tablist'] a" ).unbind().click(function(){return false;});
			 
			$("#newPassword").strength({"target":"#strengthID"});*/
			
		}
		
	});
	
    $(".form_retake_type input:radio").click(function(){
		var $this = $(this);
		var $input_parent = $this.parent().next();
		$input_parent.find(".retakeTypeInput").show();
		
		var $form_retake_type = $input_parent.parent();
		var $other_form_retake_type = $form_retake_type.siblings();
		$other_form_retake_type.find(".retakeTypeInput").val("").hide();
	});
	$(".form_retake_type input:radio").eq(0).click();
	
    $("#reinput").click(function(){
    	window.location.reload();
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
