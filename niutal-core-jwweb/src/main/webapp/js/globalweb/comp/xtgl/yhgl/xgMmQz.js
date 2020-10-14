jQuery(function($) {
	// 验证用户原密码
	$.validator.addMethod("checkPassword", function(value, element, param) {
		var isUnique = false;
		$.ajax({
			url:_path+'/xtgl/mmgl_cxCheckYhMm.html',
			async: false,
			type:"post",
			dataType:"json",
			data:{yhm:$("#yhm").val(),kl:value},					
			success:function(data){
					isUnique = data;
			}
		});
		return isUnique || this.optional(element) ;
	},"原密码输入错误");
	
	$("#ajaxForm").validateForm({
		//提交前的回调函数
		beforeSubmit : function(formData, jqForm, options) {
			$('#btn_xg').button('loading');
			return true;
		}
	});
	$("#mm").strength({"target":"#strengthID","strongKey":"yhmmdj"});

	$("#btn_xg").click(function (event) {
	    var btn = $(this)
	    submitForm("ajaxForm",function(responseText,statusText){
	    	btn.button('reset');
	    	if(responseText.indexOf("成功") > -1){
	    		$.success(responseText,function(){
					 document.location.href = _path+"/xtgl/login_logout.html";
				});
			}else if(responseText.indexOf("失败") > -1){
				$.error(responseText,function(){
				});
			}
	    });
	});
	
});