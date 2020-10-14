
jQuery(function() {
	setCurrentMenu1();// 设置当前位置
	onShowTjxm();
});

/*
 * 初始化设置
 */
function setInit(xmxsms){
	setXmxsmsView(xmxsms);
	
	setCxkz();//设置查询快照
	setCxzd();//设置查询字段
	setBbl();//报表列设置
	setTjx();//统计项设置
}

/*
 * 初始化设置，查询快照
 */
function setInitCxkz(obj){
	if(obj != null){
		kzzsGltj(obj.gltj);
		kzzsBbl(obj.bbhxl,obj.bbzxl);
		kzzsTjx(obj.tsx);
	}
}


//报表列设置
function setBbl() {
	var sHtml = createBblHtml();
	jQuery("#bblDiv").html(sHtml);
	createBblDefaultHtml();
	bblTdxg();
}

//生成报表列,html
function createBblHtml() {
	var sHtml = "";
	var bblList = tjbbPzModel['bblList'];
	if (bblList == null || bblList.length == 0) {// 无配置报表列
		return sHtml;
	}
	sHtml += "<input name='list1SortOrder' type='hidden' />";

	sHtml += "<div class='gltj1'>";//列的展示
	sHtml += "<ul id='demo0'>";
	sHtml += "</ul>";
	sHtml += "</div>";

	sHtml += "<div class='gltj2'>";//已选横向列
	sHtml += "<p>已选横向列</p>";
	sHtml += "<ul id='demo1'>";
	sHtml += "</ul>";
	sHtml += "</div>";

	sHtml += "<div class='gltj2'>";//已选纵向列
	sHtml += "<p>已选纵向列</p>";
	sHtml += "<ul id='demo2'>";
	sHtml += "</ul>";
	sHtml += "</div>";
	return sHtml;
}



//生成报表列默认值,html
function createBblDefaultHtml() {
	var sHtml = "";
	var bblList = tjbbPzModel['bblList'];
	if (bblList == null || bblList.length == 0) {// 无配置报表列
		return sHtml;
	}
	for ( var i = 0; i < bblList.length; i++) {
		var o = bblList[i];
		sHtml += createBblZdk(o.zdmc,o.zdsm);
	}
	jQuery("#demo0").html(sHtml);
}

/*
 * 生成一个报表列块
 */
function createBblZdk(zdmc,zdsm){
	var sHtml = "";
	sHtml += "<li>";
	sHtml += "<a href='javascript:;' title='拖动'  >" + zdsm + "</a>";
	sHtml += "<input type='hidden' name='bblzddm' value='" + zdmc + "'/>";
	sHtml += "</li>";
	return sHtml;
}

//统计项设置
function setTjx() {
	var sHtml = createTjxHtml();
	jQuery("#tjxTbody").html(sHtml);
}

//生成统计项,html
function createTjxHtml() {
	var sHtml = "";
	var tsxList = tjbbPzModel['tsxList'];
	sHtml += "<tr>";
	sHtml += "<th width='10%'>合计项</th>";
	sHtml += "<td width='35%'>";
	sHtml += "<label>";	
	sHtml += "<input type='checkbox' name='hjx'  value='hxlhj' >";
	sHtml += "横向合计";
	sHtml += "</label>";
	sHtml += "&nbsp;";
	sHtml += "<label>";	
	sHtml += "<input type='checkbox' name='hjx'  value='zxlhj' >";
	sHtml += "纵向合计";
	sHtml += "</label>";
	sHtml += "</td>";
	sHtml += "<th width='15%'>数据展现模式</th>";
	sHtml += "<td width='40%'>";
	sHtml += "<label>";	
	sHtml += "<input type='checkbox' name='sjzxms'  checked='checked' value='zs'>";
	sHtml += "总数";
	sHtml += "</label>";
	sHtml += "&nbsp;";
	sHtml += "<label>";	
	sHtml += "<input type='checkbox' name='sjzxms'  value='bfb'>";
	sHtml += "百分比";
	sHtml += "</label>";
	sHtml += "</td>";
	sHtml += "</tr>";
	if(tsxList != null && tsxList.length > 0){
		sHtml += "<tr>";
		sHtml += "<th width='10%'>数据项</th>";
		sHtml += "<td width='35%'>";
		
		for ( var i = 0; i < tsxList.length; i++) {
			var o = tsxList[i];			
			sHtml += "<label>";	
			sHtml += "<input type='checkbox' name='sjx' value='"+o.tsxmc+"'>";
			sHtml += o.tsxsm;
			sHtml += "</label>";
			sHtml += "&nbsp;";
		}
		sHtml += "</td>";
		sHtml += "<th width='15%'>数据项模式</th>";
		sHtml += "<td width='40%'>";
		sHtml += "<label>";	
		sHtml += "<input type='checkbox' name='sjxms' value='max'>";
		sHtml += "最大值";
		sHtml += "</label>";
		sHtml += "&nbsp;";
		sHtml += "<label>";	
		sHtml += "<input type='checkbox' name='sjxms' value='min'>";
		sHtml += "最小值";
		sHtml += "</label>";
		sHtml += "&nbsp;";
		sHtml += "<label>";	
		sHtml += "<input type='checkbox' name='sjxms' value='avg'>";
		sHtml += "平均值";
		sHtml += "</label>";
		sHtml += "&nbsp;";
		sHtml += "<label>";	
		sHtml += "<input type='checkbox' name='sjxms' value='sum'>";
		sHtml += "总和";
		sHtml += "</label>";
		//添加特殊项百分比统计
		sHtml += "<label>";	
		sHtml += "<input type='checkbox' name='sjxms' value='ptg'>";
		sHtml += "百分比";
		sHtml += "</label>";
		//添加特殊项百分比统计
		sHtml += "</td>";	
		sHtml += "</tr>";		
	}	
	return sHtml;
}


/*
 * 报表列拖动效果
 */
function bblTdxg() {
	jQuery("#demo0, #demo1, #demo2").dragsort( {
		dragSelector : "a",
		dragBetween : true,
		dragEnd : saveOrder,
		placeHolderTemplate : "<li class='placeHolder'><div></div></li>"
	});
}

function saveOrder() {
	var curUlId = jQuery(this).parent().attr("id");
	if(curUlId == "demo0"){//从已选列拖到待选列去掉叉号
		jQuery(this).find("span").remove();
	}else{//从未选列拖到已选列，增加叉号
		jQuery(this).not(":has('span')").append(yxbblYcHtml());
	}

	var data = jQuery("#demo2 li").map(function() {
		return jQuery(this).children().html();
	}).get();
	jQuery("input[name=list1SortOrder]").val(data.join("|"));
}

/*
 * 已选报表列移除
 */
function yxbblYc(obj){
	jQuery("#demo0").append(jQuery(obj).parent());
	jQuery("#demo0 li").find("span").remove();//删除叉号
}

/*
 * 返回已选报表列小叉叉代码
 */
function yxbblYcHtml(){
	return "<span class='del' title='移除' onclick='yxbblYc(this)'>×</span>";
}

/*
 * 快照设置报表列
 */
function kzzsBbl(bbhxl,bbzxl){
	createBblDefaultHtml();//生成报表列默认值,html
	jQuery("#demo1").html("");
	jQuery("#demo2").html("");	
	if(bbhxl != null){//报表横向列赋值
		var bbhxlArr = bbhxl.split(",");
		for ( var i = 0; i < bbhxlArr.length; i++) {
			jQuery("#demo0 li").each(function(index){//默认显示报表列
				var bblzddm = jQuery(this).find("[name='bblzddm']").val();
				if(bbhxlArr[i] == bblzddm){
					jQuery("#demo1").append("<li>" + jQuery(this).html() + yxbblYcHtml() +  "</li>");
					jQuery(this).remove();
				}
			});
		}
	}
	
	if(bbzxl != null){//报表纵向列赋值
		var bbzxlArr = bbzxl.split(",");
		for ( var i = 0; i < bbzxlArr.length; i++) {
			jQuery("#demo0 li").each(function(index){//默认显示报表列
				var bblzddm = jQuery(this).find("[name='bblzddm']").val();
				if(bbzxlArr[i] == bblzddm){
					jQuery("#demo2").append("<li>" + jQuery(this).html() + yxbblYcHtml() +  "</li>");
					jQuery(this).remove();
				}
			});
		}
	}
}

/*
 * 快照设置统计项
 */
function kzzsTjx(tjx){
	if(tjx == null || tjx == ""){
		return ;
	}
	jQuery("#tjxDiv input:checkbox:not([name='zs'])").removeAttr("checked");
	var arr = tjx.split(",");
	jQuery.each(arr,function(index,value){
		jQuery("#tjxDiv input:checkbox[value='"+value+"']").attr("checked","checked");
	});	
}


//点击统计按钮，统计数据
function tjlb(){

	if(checkTjlb()){
		setGltjmc();
		jQuery("#form1").submit();
	}
}

function checkTjlb(){
	var xmdm = jQuery("#xmdm").val();
	if(xmdm == null || xmdm == ""){
		alertInfo("请选择统计项目！");
		return false;
	}

	setCxkzValue();
	
	var gltj = jQuery("#gltj").val();
	var bbhxl = jQuery("#bbhxl").val();
	var bbzxl = jQuery("#bbzxl").val();
	var tsx = jQuery("#tsx").val();

	
	if(bbhxl == "" && bbzxl == ""){
		alert("请选择报表列！");
		return false;
	}
	
	if(jQuery("#demo2 [name='bblzddm']").length > 3){
		alertInfo("报表纵向列至多为3项，请重新选择(一次展示过多的统计项，会影响统计效率，请慎重)！");
		return false;		
	}

	if(tsx == ""){
		alertInfo("请选择统计项！");
		return false;		
	}
	
	var tsxList = tjbbPzModel['tsxList'];
	var sjxLength = jQuery("#tjxTbody input:checkbox[name='sjx']:checked").length;
	var sjxmsLength = jQuery("#tjxTbody input:checkbox[name='sjxms']:checked").length;
	var sjzxmsLength = jQuery("#tjxTbody input:checkbox[name='sjzxms']:checked").length;
	
	if(tsxList == null || tsxList.length == 0){//不存在数据项
		if(sjzxmsLength == 0){
			alertInfo("请选择数据展现模式！");
			return false;		
		}
	}else{
		if((sjxLength == 0 && sjxmsLength > 0) || (sjxLength > 0 && sjxmsLength == 0)){
			alertInfo("数据项及数据项模式必须同时设置！");
			return false;		
		}
		if(sjxLength == 0 && sjxmsLength == 0 && sjzxmsLength == 0){
			alertInfo("请选择统计项！");
			return false;		
		}
		//如果选择了数据项的百分比模式，则数据项的总计必须选中，纵向合计必须选中，不然无法计算！！！
		var sjxmsPTGLength = jQuery("#tjxTbody input:checkbox[name='sjxms'][value='ptg']:checked").length;
		var sjxmsSUMLength = jQuery("#tjxTbody input:checkbox[name='sjxms'][value='sum']:checked").length;
		var hjxZXLHJLength = jQuery("#tjxTbody input:checkbox[name='hjx'][value='zxlhj']:checked").length;
		if(sjxmsPTGLength == 1 && (sjxmsSUMLength == 0 || hjxZXLHJLength == 0)){
			alertInfo("数据项【百分比】模式已设置，合计项【纵向合计】和数据项模式【总和】必须同时设置！");
			return false;	
		}
		//如果选择了数据项的百分比模式，则数据项的总计必须选中，不然无法计算！！！
	}
	return true;
}

