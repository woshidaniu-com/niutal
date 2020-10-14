/**
 * 2012-09-10
 */

/**问卷类型*/
WjModel_WJLX = {
    DCL : "DCL",//调查类
    CPL : "CPL"//测评类
};

/**试题类型*/
WjModel_STLX = {
    DX : "DX",//单选
    DXS : "DXS",//多选
    DXZH : "DXZH",//单选组合
    DXSZH : "DXSZH",//多选组合
    DHWB : "DHWB",//单行文本
    DHWBS : "DHWBS",//多行文本
	STDL : "STDL"//试题大类,作为一种特殊的试题类型处理
};

/**问卷全局参数*/
WjModel_Parameter = {
	//显示的方式，用于区分文件当前是在，进行编辑状态，还是在浏览状态,显示的时候好进行判断区分
	showType:"edit",//显示方式
	showTypeValue:{
		edit:"edit",//编辑状态
		browser:"browser"//浏览状态
	},
	
	//格式化参数-用于确定是否使用空格格式化选项信息
	format_spaceSplitXxxx:true,//默认true
	//自动添加试题编号
	autoAddStbh:true,//默认true
	wjlx:WjModel_WJLX.DCL,//问卷类型
	
	other:""
}


/**问卷*/
WjModel_WenJuan = function(){
    this.wjid = "";//问卷id
    this.wjmc = "";//问卷名称
    this.wjlx = "";//问卷类型
    this.wjzt = "";//问卷状态
    this.fblx = "";//问卷发布类型，如果是'1'代表匿名问卷
    this.jsy = "";//卷首语
    this.jwy = "";//卷尾语
    this.wjzf = ""//问卷总分
    this.autoaddstbh = "";//自动增加试题编号
    this.tmxx = new Array();//题目信息
    
	this.getWjHtml;//获取问卷html
	this.getWjHidden;//获取问卷hidden
	this.getWjText///获取问卷格式化后的text
	this.getWjSubmitCheckZt;//文件提交验证状态
};

/**题目*/
WjModel_TiMu = function(){
    this.stid = "";//试题id
    this.stmc = "";//试题名称
    this.stlx = "";//试题类型
    this.dhxxgs = "";//单行选项个数
    this.sfbd = "";//是否必答
    this.stzf = "";//试题总分
    this.xssx = "";//显示顺序
    this.xxkzdxzs = "";//选项可最多选择数
    
	this.stbh="";//试题编号
	
    this.xxxx = new Array();//选项信息WjModel_Xxxx
    
    this.getTmHtml;//题目html_用于试题的展现 该方法下面有分支多个方法，在这里就不进行罗列了
    this.getTmHidden;//题目hidden_用于向后台传递数据
    this.getTmText;//题目text_用于获取试题的格式化后的文本
    this.getTmAttrText;//获取题目的属性文本
};

/**题目-选项信息*/
WjModel_Xxxx= function(){
    this.xxid = "";//选项id
    this.xxbh = "";//选项编号
    this.xxmc = "";//选项名称
    this.xxfz = "";//选项分值
    this.sfklr = "";//是否可录入
    this.xssx = ""//显示顺序
    	
    this.getXxxxAttrText;//获取选项的属性文本
};

/**题目-获取题目的html*/
WjModel_TiMu.prototype.getTmHtml = function(){
	var tmHtml="";
	switch(this.stlx){
		case WjModel_STLX.DX : tmHtml=this.getTmHtml_Dx(); break;//单选题
		case WjModel_STLX.DXS : tmHtml=this.getTmHtml_Dxs(); break;//多选题
		case WjModel_STLX.DXZH : tmHtml=this.getTmHtml_Dxzh(); break;//单选组合
		case WjModel_STLX.DXSZH : tmHtml=this.getTmHtml_Dxszh(); break;//多选组合
		case WjModel_STLX.DHWB : tmHtml=this.getTmHtml_Dhwb(); break;//单行文本
		case WjModel_STLX.DHWBS : tmHtml=this.getTmHtml_Dhwbs(); break;//多行文本
		case WjModel_STLX.STDL : tmHtml=this.getTmHtml_Stdl(); break;//试题大类
		default : alert("\""+this.stmc+"\"未知的试题类型");//未知类型
	}
	tmHtml=tmHtml?tmHtml:"";
	return tmHtml;
};

/**题目-获取单选题目的html*/
WjModel_TiMu.prototype.getTmHtml_Dx=function(){
	return this.getTmHtml_comman();
}

/**题目-获取多选题目的html*/
WjModel_TiMu.prototype.getTmHtml_Dxs=function(){
	return this.getTmHtml_comman();
}

/**题目-获取单选组合题目的html*/
WjModel_TiMu.prototype.getTmHtml_Dxzh=function(){
	return this.getTmHtml_comman();
}

/**题目-获取多选组合题目的html*/
WjModel_TiMu.prototype.getTmHtml_Dxszh=function(){
	return this.getTmHtml_comman();
}

/**题目-获取单行文本题目的html*/
WjModel_TiMu.prototype.getTmHtml_Dhwb=function(){
	var tmHtml="";
	tmHtml=this.getTmMcHtml_comman();
	tmHtml+='<textarea name="textname_'+this.stid+'" id="textid_'+this.stid+'" cols="" rows="1"></textarea>';
	return this.getTmDivCssHtml_comman(tmHtml);
}

/**题目-获取多行题目的html*/
WjModel_TiMu.prototype.getTmHtml_Dhwbs=function(){
	var tmHtml="";
	tmHtml=this.getTmMcHtml_comman();
	tmHtml+='<textarea name="textname_'+this.stid+'" id="textid_'+this.stid+'" cols="" rows="5"></textarea>';
	return this.getTmDivCssHtml_comman(tmHtml);
}

/**题目-获取试题大类题目的html*/
WjModel_TiMu.prototype.getTmHtml_Stdl=function(){
	var tmHtml="";
	var e_stmc_css=document.createElement("h2");
	var e_stmc=document.createTextNode(this.stmc);
	e_stmc_css.appendChild(e_stmc);
	tmHtml=this.getElementHTML(e_stmc_css);
	return tmHtml;
}

/**题目-获取题目html-通用*/
WjModel_TiMu.prototype.getTmHtml_comman = function(){
	var tmHtml="";
	//题目名称html
	tmHtml=this.getTmMcHtml_comman();
	//选项html
	var e_xx_table=document.createElement("table");
	jQuery(e_xx_table).css("width","100%");
	jQuery(e_xx_table).css("border","0");
	
	var input_type=this.getTmXxInputType();
	var e_xx_tab_tr;
	var xxs=this.xxxx;
	for(var i=0;i<xxs.length;i++){
		var xx=xxs[i];
		if(i%this.dhxxgs==0){
			e_xx_tab_tr=document.createElement("tr");
			e_xx_table.appendChild(e_xx_tab_tr);
		}
		var e_xx=document.createTextNode(xx.xxmc);
		var e_xx_tab_td=document.createElement("td");
		jQuery(e_xx_tab_td).css("width",100/this.dhxxgs+"%");
		e_xx_tab_tr.appendChild(e_xx_tab_td);
		jQuery(e_xx_tab_td).html("<lable><input type='"+input_type+"' name='xxname_"+this.stid+"' id='"+xx.xxid+"'  value='"+xx.xxid+"'/>"+this.getElementHTML(e_xx)+xx.getXxxxAttrText()+"</lable>");
		if((this.stlx==WjModel_STLX.DXZH||this.stlx==WjModel_STLX.DXSZH)&&(i==xxs.length-1)){
			jQuery(e_xx_tab_td).append("<input type='text' name='textname_"+this.stid+"' id='textid_"+this.stid+"'/>");
		}
	}
	tmHtml+=this.getElementHTML(e_xx_table);
	return this.getTmDivCssHtml_comman(tmHtml);
}

/**
 * 题目-获取题目包装样式后的html，在外面包装一层样式div
 * @param {String} tmHtml 题目的html
 */
WjModel_TiMu.prototype.getTmDivCssHtml_comman = function(tmHtml){
	return "<div class='question_li'>"+tmHtml+"</div>";
}

/**题目-获取题目名称html*/
WjModel_TiMu.prototype.getTmMcHtml_comman = function(){
	var tmMcHtml="";
	var e_stmc_css=document.createElement("h4");
	var e_stmc;//=document.createTextNode(this.stbh+"."+this.stmc);
	if(WjModel_Parameter.autoAddStbh){
		e_stmc=document.createTextNode(this.stbh+"."+this.stmc)
	}else{
		e_stmc=document.createTextNode(this.stmc)
	}
	e_stmc_css.appendChild(e_stmc);
	tmMcHtml=this.getElementHTML(e_stmc_css);
	tmMcHtml+="\n"+this.getTmAttrText();
	return tmMcHtml;
}

/**题目-获取题目选项input标签的type*/
WjModel_TiMu.prototype.getTmXxInputType = function(){
	var input_type;
	switch(this.stlx){
		case WjModel_STLX.DX : input_type="radio"; break;//单选题
		case WjModel_STLX.DXS : input_type="checkbox"; break;//多选题
		case WjModel_STLX.DXZH : input_type="radio"; break;//单选组合
		case WjModel_STLX.DXSZH : input_type="checkbox"; break;//多选组合
		//case WjModel_STLX.DHWB : input_type=this.getTmHtml_Dhwb(); break;//单行文本
		//case WjModel_STLX.DHWBS : input_type=this.getTmHtml_Dhwbs(); break;//多行文本
		//case WjModel_STLX.STDL : input_type=this.getTmHtml_Stdl(); break;//试题大类
		default : alert("\""+this.stmc+"\"未知的试题类型");//未知类型
	}
	return input_type;
}

/**题目-获取题目的hidden*/
WjModel_TiMu.prototype.getTmHidden = function(){
	//创建题目的hidden隐藏元素
	var hiddenHtml="";
	hiddenHtml+=this.getTmPropertyHiddenHTML("hidden_stid",this.stid);//试题id
	hiddenHtml+=this.getTmPropertyHiddenHTML("hidden_stmc",this.stmc);//试题名称
	hiddenHtml+=this.getTmPropertyHiddenHTML("hidden_stlx",this.stlx);//试题类型
	hiddenHtml+=this.getTmPropertyHiddenHTML("hidden_dhxxgs",this.dhxxgs);//单行选项个数
	hiddenHtml+=this.getTmPropertyHiddenHTML("hidden_sfbd",this.sfbd);//是否必答
	hiddenHtml+=this.getTmPropertyHiddenHTML("hidden_stzf",this.stzf);//试题总分
	hiddenHtml+=this.getTmPropertyHiddenHTML("hidden_xxkzdxzs",this.xxkzdxzs);//选项可最多选择数
	//处理试题的选项信息
	var xxs=this.xxxx;
	for(var i=0;i<xxs.length;i++){
		var xx=xxs[i];
		hiddenHtml+=this.getTmPropertyHiddenHTML("hidden_xxxx_xxid_"+this.stid,xx.xxid);
		hiddenHtml+=this.getTmPropertyHiddenHTML("hidden_xxxx_xxmc_"+this.stid,xx.xxmc);
		hiddenHtml+=this.getTmPropertyHiddenHTML("hidden_xxxx_xxfz_"+this.stid,xx.xxfz);
		//hiddenHtml+=this.getTmPropertyHiddenHTML("hidden_xxxx_xxmc_"+this.stid,xx.sfklr);
	}
	return hiddenHtml;
}

/**获取题目指定属性hidden属性
 * 通用方法，请勿随意修改
 * @param {Object} e_name 元素的名称
 * @param {Object} e_value 元素的值
 */
WjModel_TiMu.prototype.getTmPropertyHiddenHTML = function(e_name,e_value){
	var hiddenElement=document.createElement("input");
	hiddenElement.type="hidden";
	hiddenElement.name=e_name;
	hiddenElement.value=(e_value?e_value:"");
	return this.getElementHTML(hiddenElement);
}

/**获取一个临时的div，用于脚本生成html标签临时的存放
 * 通用方法，请勿随意修改
 * @param {Object} ele 元素对象
 */
WjModel_TiMu.prototype.getElementHTML = function(ele){
	var div_id="tiMu_div_temp_id_345252523";
	var div=document.getElementById(div_id);
	if(!div){
		div=document.createElement("div");
		div.id=div_id;
		jQuery(document).append(div);
	}
	div.style.display="none";
	div.innerHTML="";
	div.appendChild(ele);
	return div.innerHTML;
}

/**题目-获取题目的格式化后的文本*/
WjModel_TiMu.prototype.getTmText = function(){
	var tmText="";
	tmText+=this.stmc;
	tmText+="\n"+this.getTmAttrText();
	var xxs=this.xxxx;
	for(var i=0;i<xxs.length;i++){
		var xx=xxs[i];
		tmText+="\n";
		tmText+=xx.xxmc;
		tmText+=xx.getXxxxAttrText();//获取选项属性文本
	}
	return tmText;
}

/**题目-获取题目的属性文本*/
WjModel_TiMu.prototype.getTmAttrText = function(){
	if(WjModel_Parameter.showType!=WjModel_Parameter.showTypeValue.edit){//只有问卷处于编辑状态时才显示题目相关的属性
		return "";
	}
	if(this.stlx==WjModel_STLX.STDL){//试题大类不进行属性的显示
		return "";
	}
	var tmAttrText="";
	var stlx_dm=this.stlx;
	var stlx_name="";
	switch(stlx_dm){
			case WjModel_STLX.DX: stlx_name="单选题"; break;
			case WjModel_STLX.DXS : stlx_name="多选题"; break;
			case WjModel_STLX.DXZH : stlx_name="单选组合"; break;
			case WjModel_STLX.DXSZH : stlx_name="多选组合"; break;
			case WjModel_STLX.DHWB : stlx_name="单行文本"; break;
			case WjModel_STLX.DHWBS : stlx_name="多行文本"; break;
			case WjModel_STLX.STDL : stlx_name="试题大类"; break;
			default : break;alert("\""+stlx_dm+"\"未知的试题类型代码");//未知类型
		}
	tmAttrText+="[题目类型："+stlx_name+"]";
	if(this.dhxxgs!=""){
		tmAttrText+="[单行选项个数："+this.dhxxgs+"]";
	}
	if(this.sfbd!=""){
		tmAttrText+="[是否必答："+this.sfbd+"]";
	}
	if(this.stzf!=""||WjModel_Parameter.wjlx==WjModel_WJLX.CPL){
		tmAttrText+="[试题总分："+this.stzf+"]";
	}
	if(this.xxkzdxzs!=""){
		tmAttrText+="[选项可最多选择数："+this.xxkzdxzs+"]";
	}
	return tmAttrText;
}

/**选项-获取选项的属性文本*/
WjModel_Xxxx.prototype.getXxxxAttrText = function(){
	if(WjModel_Parameter.showType!=WjModel_Parameter.showTypeValue.edit){//只有问卷处于编辑状态时才显示题目相关的属性
		return "";
	}
	var xxAttrText="";
	if(this.xxfz!=""||WjModel_Parameter.wjlx==WjModel_WJLX.CPL){
		xxAttrText+="[选项分值："+this.xxfz+"]";
	}
	if(this.sfklr!=""){
		xxAttrText+="[是否可录入："+this.sfklr+"]";
	}
	return xxAttrText;
}

/**问卷-获取问卷html*/
WjModel_WenJuan.prototype.getWjHtml=function(){
    var wjHtml = "";
    var tms = this.tmxx;
    for (var i = 0; i < tms.length; i++) {
        var tm = tms[i];
        wjHtml += tm.getTmHtml();
    }
	return wjHtml;
}

/**问卷-获取问卷hidden*/
WjModel_WenJuan.prototype.getWjHidden=function(){
    var wjHidden = "";
    var tms = this.tmxx;
    for (var i = 0; i < tms.length; i++) {
        var tm = tms[i];
        wjHidden += tm.getTmHidden();
    }
	return wjHidden;
}

/**问卷-获取问卷格式化后的text*/
WjModel_WenJuan.prototype.getWjText=function(){
    var wjText = "";
    var tms = this.tmxx;
    for (var i = 0; i < tms.length; i++) {
        var tm = tms[i];
        wjText += tm.getTmText();
        wjText += "\n\n";
    }
	return wjText;
}

/**
 * 问卷-获取问卷提交提交验证状态
 * @param {boolean} ifAlert 验证时是否提示具体信息
 */
WjModel_WenJuan.prototype.getWjSubmitCheckZt=function(ifAlert){
	var tms = this.tmxx;
	for(var i=0;i<tms.length;i++){
		var tm=tms[i];
		//if(tm.sfbd!="是"){
		//	continue;
		//}
		if(tm.stlx==WjModel_STLX.DX||tm.stlx==WjModel_STLX.DXZH||tm.stlx==WjModel_STLX.DXS||tm.stlx==WjModel_STLX.DXSZH){
			var user_select = jQuery("input[name='xxname_"+tm.stid+"']").filter(":checked");
			if(tm.sfbd=="是"&&user_select.length==0){
				if (ifAlert) {
					alert("\"" + tm.stmc + "\"必须选择！");
				}
				return false;
			}
			
			//验证复选类的题目的选项可最多选择数
			if(user_select.length>0&&(tm.stlx==WjModel_STLX.DXS||tm.stlx==WjModel_STLX.DXSZH)){
				if(tm.xxkzdxzs!=""&&tm.xxkzdxzs<user_select.length){
					if (ifAlert) {
						alert("\"" + tm.stmc + "\"最多可选择"+tm.xxkzdxzs+"项！");
					}
					return false;
				}
			}
			
			//组合题目如果是选择的最后一个选项，那么还需要验证文本是否填写
			if(user_select.length>0&&(tm.stlx==WjModel_STLX.DXZH||tm.stlx==WjModel_STLX.DXSZH)){
				if(user_select[user_select.length-1].id==tm.xxxx[tm.xxxx.length-1].xxid
						&&jQuery("#textid_"+tm.stid).val().replace(/(^\s*)|(\s*$)/g, "")==""){
					if (ifAlert) {
						alert("\"" + tm.stmc + "\"中的文本必须输入！");
					}
					return false;
				}	
				
				if(jQuery("#textid_"+tm.stid).val().length > 100){
					if (ifAlert) {
						alert("\"" + tm.stmc + "\"输入字数过多，请限制在100以内！");
					}
					return false;
				}
			}	
			
		}
		if(tm.sfbd=="是"&&(tm.stlx==WjModel_STLX.DHWB||tm.stlx==WjModel_STLX.DHWBS)){
			var text = jQuery("#textid_"+tm.stid).val();
			if(text.replace(/\s*/g,"")==""){
				if (ifAlert) {
					alert("\""+tm.stmc+"\"不可为空！");
				}
				return false;
			}
		}
		
		if(tm.stlx==WjModel_STLX.DHWB||tm.stlx==WjModel_STLX.DHWBS){
			var text = jQuery("#textid_"+tm.stid).val();
			if(text.length > 500){
				if (ifAlert) {
					alert("\""+tm.stmc+"\"输入字数过多，请限制在500以内！");
				}
				return false;
			}
		}

		
	}
	return true;
}

/**获取编辑试题验证信息*/
WjModel_WenJuan.prototype.getEditStxxCheckMsg = function(){
	var appendEditStxxCheckMsg=function(stid,stdiv,checkMsg){
		jQuery("#span_stCheckMsg_"+stid).html(checkMsg).parent.parent.css("border-style","solid").css("border-width","1px").css("border-color","red");
	}
	var msg="";
	var oneMsg="";
	var tms=this.tmxx;
	//必须有题目，才可以提交
	if(tms.length==0){
		msg="请添加题目后再提交保存！";
		return msg;
	}
	var tm;//题目
	var xxs;//选项
	var stzf_total=0;//试题总分
	for(var i=0;i<tms.length;i++){
		tm=tms[i];
		oneMsg=tm.getEditStxxCheckMsg();
		if(oneMsg!=""){
			msg+=oneMsg+"\n";
		}
		//验证测评类分值问题，测评类所有的题目都应该有分数
		if(this.wjlx==WjModel_WJLX.CPL&&tm.stlx!=WjModel_STLX.STDL){
			if(tm.stzf!=""){
				stzf_total+=parseInt(tm.stzf);
			}
		}
	}
	if(this.wjlx==WjModel_WJLX.CPL){
		if(this.wjzf!=stzf_total){
			msg+="问卷总分为："+this.wjzf+"，试题总分为："+stzf_total+"。两分值必须相等！";
		}
	}
	return msg;
}

/**题目——获取编辑试题验证信息*/
WjModel_TiMu.prototype.getEditStxxCheckMsg=function(){
	var msg="";
	
	var tm=this;//题目
	var xxs=tm.xxxx;//选项
	//选择类题目的选项个数至少2个
	if(tm.stlx==WjModel_STLX.DX||tm.stlx==WjModel_STLX.DXZH||tm.stlx==WjModel_STLX.DXS||tm.stlx==WjModel_STLX.DXSZH){
		if(xxs.length<2){
			msg+="选项个数不符合要求，应至少两项！";
		}
	}
	//验证测评类分值问题，测评类所有的题目都应该有分数
	if(WjModel_Parameter.wjlx==WjModel_WJLX.CPL&&tm.stlx!=WjModel_STLX.STDL){
		if(tm.stzf!=""){
			if(xxs.length>0){
				var xxfz_total=0;
				var xxfz_mark=false;
				for(var j=0;j<xxs.length;j++){
					var xx=xxs[j];
					if(xx.xxfz!=""){
						xxfz_total+=parseInt(xx.xxfz);
						xxfz_mark=true;
					}
				}
				if(xxfz_mark){
					if(tm.stzf!=xxfz_total){
						msg+="试题分值与其选项分值不一致！";
					}
				}else{
					msg+="未设置选项分值！";
				}
			}
		}else{
			msg+="未设置题目分数！";
		}
	}
	if(msg!=""){
		jQuery("#span_stCheckMsg_"+tm.stid).html(msg).parent().parent().css("border-style","solid").css("border-width","1px").css("border-color","red");
	}else{
		jQuery("#span_stCheckMsg_"+tm.stid).html(msg).parent().parent().css("border-style","").css("border-width","0px").css("border-color","");
	}
	if(msg!=""){
		msg="\""+tm.stmc+"\""+msg;
	}		
	return msg;
	
}
