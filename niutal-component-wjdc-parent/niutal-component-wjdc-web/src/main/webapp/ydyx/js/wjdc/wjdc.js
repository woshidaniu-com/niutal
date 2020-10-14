/**
 * author: xiaokang
 * 移动端问卷调查页面需要引入JS脚本
 */
var static_wjModel = new WjModel_WenJuan();
jQuery(function(){
	WjModel_Parameter.showType=WjModel_Parameter.showTypeValue.browser;
	static_wjModel.wjlx=jQuery("#wjlx").val();
	static_wjModel.wjzf=jQuery("#wjzf").val();
	static_wjModel.fblx=jQuery('#fblx').val();
	if(jQuery("#hidden_autoaddstbh").val()!=""){
		static_wjModel.autoaddstbh=(jQuery("#hidden_autoaddstbh").val()=="true"?true:false);
		WjModel_Parameter.autoAddStbh=static_wjModel.autoaddstbh;
		if(jQuery("#autoAddStbh")[0]){
			jQuery("#autoAddStbh").val(jQuery("#hidden_autoaddstbh").val());
		}
	}
	WjModel_Parameter.wjlx=static_wjModel.wjlx;
    //2.获取问卷
    jQuery.ajaxSetup({async : false});
    getWjstxx();
    //3.获取答案
    //3.(1)如果是匿名问卷，不需要获取用户答题信息
    if(static_wjModel.fblx != '1'){
    	getWjdaxx();
    }
    jQuery.ajaxSetup({async : true});
    var myscroll=new iScroll("wrapper");
    $('.list_exam1 input[type=radio]').customInput();
	$('.list_exam1 input[type=checkbox]').customInput();
});

/**问卷提交验证*/
function wjSubmitCheck(){
	var res=static_wjModel.getWjSubmitCheckZt(true);
    if(!res){
    	return false;
    }
	var _do = function(){
	   //默认提交的路径
		var url="zfxg/wjdc/ydstgl_saveWjDa.html";
	    document.forms[0].action=url;
		document.forms[0].submit();
	}
	addConfirm('您确定要提交问卷吗？',_do);
}

/**匿名问卷提交验证*/
function nmwjSubmitCheck(ref){
	var res=static_wjModel.getWjSubmitCheckZt(true);
    if(!res){
    	return false;
    }
    var $this = jQuery(ref);
	var _nmdo = function(){
	   //默认提交的路径
		var url="zfxg/wjdc/nmwj_saveNmWjDa.html";
	    document.forms[0].action=url;
		var formParam = jQuery(document.forms[0]).serialize();
		$this.attr('disabled' , true);
		jQuery.post(url, formParam, function(data){
			alert(data);
			jQuery('input', document.forms[0]).not(':button').attr('disabled' , true);
			$this.remove();
		},'json');
	}

	showConfirmDivLayer('提交问卷？',{'okFun':_nmdo});
}

/**获取问卷试题信息，自动加载使用，并显示加载出来
 * @param {boolean} sfhqda 是否获取答案
 * */
function getWjstxx(){
	var date=new Date();
	var wjid=document.getElementById("wjid").value;
	var fblx=document.getElementById("fblx").value;
	var url="zfxg/wjdc/stgl_getWjStxxList.html?wjModel.wjid="+wjid+"&datemsram="+date.getMilliseconds();
	//如果是匿名问卷类型
	if(fblx == '1'){
		url="zfxg/wjdc/nmwj_getNmWjStxxList.html?wjModel.wjid="+wjid+"&datemsram="+date.getMilliseconds();
	}
	var cs=new Wj_Client_Service();
	cs.wjModel=static_wjModel;
	cs.wjGet_pretreatment(url,cs);
	
}

/**获取问卷答案信息，自动加载使用，并显示加载出来*/
function getWjdaxx(){
	var date=new Date();
	var wjid=document.getElementById("wjid").value;
	var djrid="";
	if(document.getElementById("djrid")){
		djrid=document.getElementById("djrid").value;
	}else{
		return false;
	}
	var url="zfxg/wjdc/stgl_getWjDaList.html?wjModel.wjid="+wjid+"&wjModel.djrid="+djrid+"&datemsram="+date.getMilliseconds();
	var cs=new Wj_Client_Service();
	cs.wjModel=static_wjModel;
	cs.wjdaGet_pretreatment(url,cs);
}
