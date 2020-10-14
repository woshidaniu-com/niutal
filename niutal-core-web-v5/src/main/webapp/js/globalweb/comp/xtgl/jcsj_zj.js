jQuery(function($){
	
	//基础数据某类型下的代码为唯一性验证
	$.validator.addMethod("jcsjUnique", function(value, element,param) {
		var isUnique = false;
		$.ajax({
			url:"valideDm.zf",
			type:"post",
			dataType:"json",
			async:false ,
			data:{"pkValue": $('#lxdm').val()+$('#dm').val() },
			success:function(data){
				isUnique = data;
			}
		});
		return this.optional(element) || isUnique;   
	},function(param,element){
		return $.validator.format("该类型下的代码{0}已存在，不能使用!",[$(element).val()]);
	});
	
});

