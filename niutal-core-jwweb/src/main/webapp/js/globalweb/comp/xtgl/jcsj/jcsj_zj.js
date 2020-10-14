jQuery(function($){
	
	//基础数据某类型下的代码为唯一性验证
	$.validator.addMethod("jcsjUnique", function(value, element,param) {
		var isUnique = false;
		$.ajax({
			url:"jcsj_cxValideDm.html",
			type:"post",
			dataType:"json",
			async:false ,
			data:{"pkValue": $('#lxdm').val() + "_" + $('#dm').val() },
			success:function(data){
				if(data!=null){
					isUnique = false;
				}else{
					isUnique = true;
				}
			}
		});
		return this.optional(element) || isUnique;   
	},function(param,element){
		return $.validator.format("该类型下的代码{0}已存在，不能使用!",[$(element).val()]);
	});
	
	/*====================================================绑定按钮事件====================================================*/
	
	
});

