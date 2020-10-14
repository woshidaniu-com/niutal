var static_wjModel = new WjModel_WenJuan();
var wjjx = new Wj_WenBenJieXi();
var wj_oneSt=new Wj_OneSt();
jQuery(function(){
	//1.初始化相关参数
	if($("#textInput")[0]){//判断问卷处于编辑状态还是处于浏览状态
		WjModel_Parameter.showType=WjModel_Parameter.showTypeValue.edit;
	}else{
		WjModel_Parameter.showType=WjModel_Parameter.showTypeValue.browser;
	}
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
	wjjx.wjModel=static_wjModel;
	//WjModel_Parameter.wjlx=jQuery("#wjlx").val();
	
	wjjx.textarea = document.getElementById("textInput");//绑定文本域元素
	wjjx.wjview = document.getElementById("wjview");//绑定试题展现元素
	
    $("#textInput").keyup(function(){//绑定文本域的对象事件
        static_wjModel = wjjx.wbjx();
        //向试题中添加编辑按钮
        //wj_oneSt.addAllStEditButton(static_wjModel);
    }).keyup();
    //2.获取问卷
    jQuery.ajaxSetup({async : false});
    getWjstxx();
    //3.获取答案
    //3.(1)如果是匿名问卷，不需要获取用户答题信息
    //if(static_wjModel.fblx != '1'){
    	getWjdaxx();
    //}
    jQuery.ajaxSetup({async : true});
    //4.向试题中添加编辑按钮
    if(WjModel_Parameter.showType==WjModel_Parameter.showTypeValue.edit){//处于编辑状态时才进行添加
    	wj_oneSt.addAllStEditButton(static_wjModel);
    }
});

/**问卷文本格式化*/
function wjFormat(){
    //var wjjx = new Wj_WenBenJieXi();
    //wjjx.text = $("#textInput").val();
    static_wjModel = wjjx.wbjx();
	wjjx.textarea.value=static_wjModel.getWjText();
	
	var hidden_div_id="hidden_div_408039475";
	var hidden_div=document.getElementById(hidden_div_id);
	if(!hidden_div){
		hidden_div=document.createElement("div");
		hidden_div.id=hidden_div_id;
		jQuery(document.forms[0]).append(hidden_div);
	}
	//alert(static_wjModel.getWjHidden());
	//wjjx.textarea.value=static_wjModel.getWjHidden();
	jQuery(hidden_div).html(static_wjModel.getWjHidden());
	static_wjModel.getEditStxxCheckMsg();
}

/**问卷提交验证*/
function wjSubmitCheck(){
	var res=static_wjModel.getWjSubmitCheckZt(true);
    if(!res){
    	return false;
    }
    
	var _do = function(){
	   //默认提交的路径
		var url="zfxg/wjdc/stgl_saveWjDa.html";
	    document.forms[0].action=url;
		document.forms[0].submit();
	}

	showConfirmDivLayer('您确定要提交问卷吗？一旦提交，将不允许修改',{'okFun':_do});
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

/**问卷题目编辑保存验证*/
function wjTmEditSaveCheck(){
	wjFormat();
	var msg=static_wjModel.getEditStxxCheckMsg();
	if(msg!=""){
		alert(msg);
		return false;
	}
	var url="zfxg/wjdc/stgl_saveEditStxx.html";
	document.forms[0].action=url;
	document.forms[0].submit();
	
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
