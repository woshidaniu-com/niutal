/*
 * 初始化设置
 */
function setInit(xmxsms){
	setXmxsmsView(xmxsms);
	
	setCxkz();//设置查询快照
	setCxzd();//设置查询字段
}

/*
 * 初始化设置，查询快照
 */
function setInitCxkz(obj){
	if(obj != null){
		kzzsGltj(obj.gltj);
	}
}

//点击统计查询按钮
function tjcx(){
	var xmdm = jQuery("#xmdm").val();
	if(xmdm == null || xmdm == ""){
		$.alert("请选择统计项目！");
		return false;
	}	
	setCxkzValue();
	setGltjmc();
	var url = _path+"/niutal/tjcx/tjcx/tjcxlb.zf?timestamp=" + new Date().getTime();
	
	$("#form1").attr("action",url);
	$("#form1").submit();
}

