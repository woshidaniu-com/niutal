//加载SELECT标签列表数据
function loadOption() {
	var selectObj = jQuery(".tj_select");
	if (selectObj != null && selectObj.length > 0) {
		jQuery(selectObj).each(function(i, n) {
			if (jQuery(n)) {
				jQuery(n).textClue( {
					id : jQuery(n).attr("id"),
					divId : jQuery(n).attr("id") + "Div",
					url : _path + '/zfxg/wjdc/wjbase_getCxzdOption.html',
					listKey : 'MC',
					listText : 'MC',
					params : {
						bm : jQuery('#bm').val(),
						zd : jQuery(n).attr("id")
					}
				});
			}
		});
	}
}

//回显查询条件值
function dispFiledValue() {
	var valueStr = jQuery('#valueStr').val();
	if (valueStr != "" && valueStr != null) {
		var array = valueStr.split("!!@@split!!@@");
		for ( var i = 0; i < array.length; i++) {
			var zdmc = array[i].split("!!=@@")[0];
			var zdz = array[i].split("!!=@@")[1];
			if (jQuery("#" + zdmc)) {
				jQuery("#" + zdmc).val(zdz);
			}
		}
	}
}



/*
 * 优化界面选项,分组项时，最末一行增加“合计”名称(此功能代码混乱，后期考虑重构)
 * ligl
 * 20131011
 */
function setInit(){
	var trLength = jQuery("div.formbox tbody tr").length;
	jQuery("div.formbox tbody tr").each(function(index){
		var flag = false;
		var flag1 = false;
		jQuery(this).find("td").each(function(index1){
			var tdText = jQuery(this).text();
			if(tdText != null && jQuery.trim(tdText) != ""){
				flag1 = true;
				return;
			}else{
				if(index1 == 0){
					flag = true;
				}
			}
		});
		if(flag && flag1 && index == trLength - 1 ){//最末内容行，且第一列为空，修改为“合计”
			jQuery(this).find("td:first").text("合计");
		}
	});

}