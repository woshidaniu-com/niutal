
function submitDataRange(callbackFunc){
	var zgh_arr = [];
	$("input[name=yhm]").each(function(index,input){
		//多个用户
		zgh_arr.push($(input).val());
	});
	//调用提交方法
	$("#dataRangeBox").datarange("submit",{
		//显示提交进度的状态条元素
		progressElement	: "#bootboxStatus",
		url:_path + "/datarange/yhjssjfw_sjsqXgSjfwsz.html", //访问服务器地址
		data:{
			"jsdm":$("#js_id").val(),
			"js_id":$("#js_id").val(),
			"kzdx":"js",
			"yhm":zgh_arr.join(",")
		},
		 success:function(data){
			callbackFunc.call(this,data);
	     }
	});
}

//你可以在这里继续使用$作为别名...
jQuery(function($) {

	var zgh_arr = [];
	$("input[name=yhm]").each(function(index,input){
		//多个用户
		zgh_arr.push($(input).val());
	});
	
	$("#dataRangeBox").datarange({
		//用户数据范围JSON数据
		href:_path + "/datarange/yhjssjfw_cxSjfwszList.html", //访问服务器地址
		//请求参数
		data:{
			"jsdm":$("#js_id").val(),
			"js_id":$("#js_id").val(),
			"kzdx":"js",
			"yhm":zgh_arr.join(",")
		},
		//tab区域参数
		tablist:{
			href: _path + "/datarange/sjfwdx_cxSjfwdxInfo.html", //访问服务器地址
			data:{"kzdx":"js"},
			mapper:{"key":"bm","text":"zwmc"},
			active:"view_xtgl_jsbm"
		},
		//a-z区域参数
		droplist:{
			href:_path+'/datarange/sjfwdx_cxSjfwdxList.html', //访问服务器地址
			data:{"kzdx":"js"}
		},
		afterRender:function(element,options){
        	
		}
	});
	
});