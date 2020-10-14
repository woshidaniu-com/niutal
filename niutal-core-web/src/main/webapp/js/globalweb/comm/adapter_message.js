/*
 * 因系统代码无法修改只能在此做弹出层适配
 * 原来弹出层插件ymPrompt 因有bug无法解决
 * @user:张俊伟【943】
 * @date:2013-10-9 12:00:00
 */
//原始消息框
var win_alert=window.alert;
//原始弹出层兼容
function showWindow(title,width,height,url,handler){
	showDialog(title,width,height,url,{callback:handler});
}
//兼容原始关闭 
function iFClose(){
	closeDialog();
}
window.alert=alertInfo;
//弹出层消息兼容
function showConfirmDivLayer(){
	var argumentsArr = Array.prototype.slice.call(arguments);
	var _fun = null;
	alertConfirm(argumentsArr[0],argumentsArr[1]["okFun"]);
}
function tipsWindown(title, content, width, height) {
	alertDialog(jQuery("#"+content.substr(3)).html(),width,height,default_title(title),{id:"tipsWindownDialog"});
}
function divLayerClose(){
	jQuery.dialog({id:'tipsWindownDialog'}).close();
}
/**jqGrid火狐谷歌浏览器问题
 * @param tabGrid
 * @param pager
 * @return
 */
function dialog_loadJqGrid(tabGrid,pager){
	if(tabGrid == null || tabGrid == ""){
		tabGrid = "#tabGrid";
	}
	var tabGridStr = tabGrid.substr(1);
	 if(!jQuery.browser.msie) {
		 jQuery("#gview_" + tabGridStr).width("100%");
			jQuery("#gbox_"+ tabGridStr).width("99%");
			jQuery(tabGrid).width("98%");
			jQuery(".ui-jqgrid-bdiv").width("100%");
			jQuery(".ui-jqgrid-htable").width("100%");
			jQuery(".ui-jqgrid-hdiv").width("100%");
			jQuery(pager).width("100%");
			jQuery(".ui-jqgrid-resize-ltr").remove();
     }
}

//获取根页面window对象。
function getRootWindowObj(win){
	if(win == null){
		return win;
	}
	var rootWin=win;
	if(frameElement && frameElement.api){
		rootWin=getParentDialogWin();
	}else{
		if(win.parent != null && win.parent != win){
			rootWin = getRootWindowObj(win.parent);
		}
	}
	return rootWin;
}

//获取适配宽度    dialogW：将要弹出层的宽度
function getAdapterWidth(dialogW){
	var rootWin =getRootWindowObj(window);
	var rootW = rootWin.document.documentElement.clientWidth;
	var adapterW=dialogW;
	if(dialogW > rootW){
		adapterW=rootW-50;
	}
	return adapterW;
}

//获取适配高度     dialogH:将要弹出层的高度
function getAdapterHeight(dialogH){
	var rootWin =getRootWindowObj(window);
	var rootH = rootWin.document.documentElement.clientHeight;
	var adapterH=dialogH;
	if(dialogH > rootH){
		adapterH = rootH-70;
	}
	return adapterH;
}