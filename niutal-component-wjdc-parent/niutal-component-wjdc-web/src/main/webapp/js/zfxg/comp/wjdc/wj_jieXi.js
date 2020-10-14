/**
 * 2012-09-11
 * 文件文本解析，任务是将文本进行解析，生成需要的问卷对象
 */

/***/
Wj_WenBenJieXi = function(){
	
	this.textarea;//问卷文本textarea##必须绑定到对应的textarea上
	this.wjview;//问卷展现对象
	
	this.text;//问卷文本内容
	this.wbjx_errorMsg;//文本解析错误信息,用于解析后错误信息反馈
	this.wjModel = new WjModel_WenJuan();//问卷对象
	this.static_stbh=0;//试题编号，用于记录非试题大类的个数
	/**文本解析*/
	this.wbjx = function(){
		//this.wjModel.wjlx=WjModel_Parameter.wjlx;
		this.text=this.textarea.value;
		this.static_stbh=0;
		if(this.text.replace(/\s*/g,"")==""){
			this.wjModel.tmxx=new Array();
			jQuery(this.wjview).html("<legend>问卷内容</legend>"+this.wjModel.getWjHtml());
			return this.wjModel;
		}
		this.wbjx_pretreatment();
		this.wbjx_getWjModel();
		jQuery(this.wjview).html("<legend>问卷内容</legend>"+this.wjModel.getWjHtml());
		wj_oneSt.addAllStEditButton(static_wjModel);
		return this.wjModel;
		//alert(this.wjModel.getWjHidden());
	}
	
	/**文本解析-预处理*/
	this.wbjx_pretreatment = function(){
		//将文本简单的格式，去除多余的空白，以方便后面的解析
		//this.text = this.text.replace(/\r/g, "");//将所有的\r替换掉，主要是在ie浏览器中换行符是\r\n，而火狐是\n
		this.text = this.text.replace(/(^\s*)|(\s*$)/g, "");//将头部和尾部的空白移除
		if(!this.text.match(/\r/)){//非ie系列浏览器
			this.text = this.text.replace(/((\s*)\n(\s*)){2,}/g, "\n\n");//将两个或连个以上的空行替换为一个空行
		}
		var text_splits = this.text.split(/\n/);
		this.text = "";
		for (var i = 0; i < text_splits.length; i++) {
			this.text += text_splits[i].replace(/(^\s*)|(\s*$)/g, "") + "\n";//处理掉每一行记录两端的空白
		}
		this.text = this.text.replace(/\n$/, "");//移除尾部的空行
	}
	
	//1.文本预处理 将多余的空白移除,文本的两端、每行的两端，多行空白合并为一行
	//2.提取信息封装在wj_Model中
	
	this.wbjx_getWjModel = function(){
		var tm_texts = this.text.split(/\n\n/);
		var tmModels = new Array();
		for (var i = 0; i < tm_texts.length; i++) {
			//处理题目
			var tm=this.wbjx_getTiMuModel(tm_texts[i],i);
			tmModels[tmModels.length]=tm;
			//alert(tm.getTmHidden());
		}
		this.wjModel.tmxx=tmModels;
		//alert(this.wjModel.getWjText());
	}
	
	/**
	 * 根据题目文本获取题目对象
	 * @param {String} tm_text 题目文本
	 * @param {String} tm_xssx 显示顺序
	 */
	this.wbjx_getTiMuModel=function(tm_text,tm_xssx){
		var tm=new WjModel_TiMu();
		tm.stid="stid_"+tm_xssx;
		//只有一行试题文本，默认为试题大类
		//alert(tm_text.split(/\n/).length+"\n\n"+tm_text);
		if(tm_text.split(/\n/).length==1){
			tm.stmc=tm_text;
			tm.stlx=WjModel_STLX.STDL;
			return tm;
		}
		//处理题目属性
		var tm_attr_text=tm_text.match(/(^\[.*\]\n)|(\n\[.*\]\n)|(\n\[.*\]$)/);//获取用[]括起来的行，为属性行
		if(tm_attr_text&&tm_attr_text.length>0){
			tm_attr_text=tm_attr_text[0].replace(/\s*/g,"");
		}
		tm=this.wbjx_getTMAttr(tm,tm_attr_text);
		if(tm.stlx!=WjModel_STLX.STDL){
			tm.stbh=++this.static_stbh;
		}
		//处理题目选项信息
		tm_text=tm_text.replace(/(^\[.*\]\n)|(\n\[.*\]\n)|(\n\[.*\]$)/,"\n");//移除属性行
		tm_text = tm_text.replace(/(^\s*)|(\s*$)/g, "");//将头部和尾部的空白移除--上面的处理有可能会出空行，现在需要再处理一下
		var tm_lines=tm_text.split(/\n/);
		tm.stmc=tm_lines[0];//题目文本信息移除属性后的第一行默认为题目名称
		var xxs=new Array();
		for(var i=1;i<tm_lines.length;i++){
			if(WjModel_Parameter.format_spaceSplitXxxx){//选项信息以空格进行格式化
				var tm_line_xxs=tm_lines[i].replace(/\s+/," ").split(" ");
				if(i==1&&tm.dhxxgs==1){//根据第一行的选项的个数，确定题目单行选项显示的个数
					tm.dhxxgs=tm_line_xxs.length;
				}
				for(var j=0;j<tm_line_xxs.length;j++){
					xxs[xxs.length]=this.wbjx_getXxxxModel(tm_line_xxs[j],tm.dhxxgs*(i-1)+j,tm.stid);
				}
			}else{
				xxs[xxs.length]=this.wbjx_getXxxxModel(tm_lines[i],i,tm.stid);
			}
		}
		tm.xxxx=xxs;
		return tm;
	}
	
	/**
	 * 获取选项Model
	 * @param {String} xx_text 选项文本
	 * @param {String} xx_xssx 选项显示顺序
	 * @param {String} stid 试题id
	 */
	this.wbjx_getXxxxModel = function(xx_text,xx_xssx,stid){
		var xx=new WjModel_Xxxx();
		xx.xxid=stid+"_xxid_"+xx_xssx;
		xx.xxmc = "";//选项名称
		//获取属性
		if (WjModel_Parameter.wjlx == WjModel_WJLX.CPL) {//只有测评类的问卷才有选项分值属性
			xx.xxfz = this.wbjx_getTmAttrConfigValue("选项分值",xx_text,"");//选项分值
		}
	    xx.sfklr = this.wbjx_getTmAttrConfigValue("是否可录入",xx_text,"");//是否可录入
	    //将属性相关的配置移除掉，剩余的部分作为选项名称
		xx_text=xx_text.replace(/\[\s*选项分值\s*[:：][^\]]*\]/,"");
		xx_text=xx_text.replace(/\[\s*是否可录入\s*[:：][^\]]*\]/,"");
		xx.xxmc=xx_text.replace(/(^\s*)|(\s*$)/g, "");
		return xx;
	}
	
	/**
	 * 获取题目属性
	 * @param {WjModel_TiMu} tm  题目对象
	 * @param {String} tm_attr_text 从题目文本中获取的属性行文本
	 */
	this.wbjx_getTMAttr=function(tm,tm_attr_text){
		if(!tm_attr_text||tm_attr_text==null||tm_attr_text==""){
			tm_attr_text="[题目类型:单选题]";
		}
		//1.题目类型
		var stlx_name=this.wbjx_getTmAttrConfigValue("题目类型",tm_attr_text,"单选题");//试题类型,默认单选
		stlx_name=stlx_name.replace(/\s*/g,"");//替换掉所有的空白
		var stlx_dm=WjModel_STLX.DX;
		switch(stlx_name){
			case "单选题" : stlx_dm=WjModel_STLX.DX; break;
			case "多选题" : stlx_dm=WjModel_STLX.DXS; break;
			case "单选组合" : stlx_dm=WjModel_STLX.DXZH; break;
			case "多选组合" : stlx_dm=WjModel_STLX.DXSZH; break;
			case "单行文本" : stlx_dm=WjModel_STLX.DHWB; break;
			case "多行文本" : stlx_dm=WjModel_STLX.DHWBS; break;
			case "试题大类" : stlx_dm=WjModel_STLX.STDL; break;
			default : break;alert("\""+stlx_name+"\"未知的试题类型，系统将无法识别的试题类型默认为单选题");//未知类型
		}
		tm.stlx = stlx_dm;
		//2.是否必答
		var sfbd_temp=this.wbjx_getTmAttrConfigValue("是否必答",tm_attr_text,"是");//是否必答--该处应该在问卷创建时进行一个默认的设置
		if(!(sfbd_temp=="是"||sfbd_temp=="否")){
			sfbd_temp="";
		}
	    tm.sfbd = sfbd_temp;//是否必答--该处应该在问卷创建时进行一个默认的设置
        //3.单行选项个数
        if (tm.stlx == WjModel_STLX.DX ||
        tm.stlx == WjModel_STLX.DXS ||
        tm.stlx == WjModel_STLX.DXZH ||
        tm.stlx == WjModel_STLX.DXSZH) {
        	var dhxxgs_temp=this.wbjx_getTmAttrConfigValue("单行选项个数", tm_attr_text, "1");//单行选项个数,默认为1
        	if(!dhxxgs_temp.match(/^[1-9][0-9]?$/)){
        		dhxxgs_temp="1";
        	}
            tm.dhxxgs = dhxxgs_temp;
        }
	    //4.试题总分
		if (this.wjModel.wjlx == WjModel_WJLX.CPL) {//只有测评类的问卷才有试题总分属性
			var stzf_temp=this.wbjx_getTmAttrConfigValue("试题总分", tm_attr_text, "");//试题总分--该处需要根据问卷的类型来判断是否设置
			if(!stzf_temp.match(/^[1-9][0-9]?$/)){
				stzf_temp="";
			}
			tm.stzf = stzf_temp;
		}
		//5.选项可最多选择数
		if (tm.stlx == WjModel_STLX.DXS ||
		    tm.stlx == WjModel_STLX.DXSZH) {
		        var xxkzdxzs_temp=this.wbjx_getTmAttrConfigValue("选项可最多选择数", tm_attr_text, "");//选项可最多选择数
		        if(!xxkzdxzs_temp.match(/^[1-9][0-9]?$/)){
		        	xxkzdxzs_temp="";
		        }
		        tm.xxkzdxzs = xxkzdxzs_temp;
		}
		
		return tm;
	}
	
	/**
	 * 获取题目属性
	 * @param {Object} attrName 属性名称
	 * @param {Object} tm_attr_text 题目属性文本
	 * @param {Object} default_value 默认值
	 */
	this.wbjx_getTmAttrConfigValue=function(attrName,tm_attr_text,default_value){
		var attrValue = tm_attr_text.match("\\[\\s*"+attrName+"\\s*[:：][^\\]]*\\]");//中英文冒号都支持
		if(attrValue == null){
			return default_value;
		}
		if(attrValue.length == 0){
			attrValue = default_value;
		}else{
			attrValue = attrValue[0].split(/[:：]/)[1];
			attrValue = attrValue.substring(0, attrValue.length - 1);
		}
		
		return attrValue.replace(/^\s*|\s*$/g,"");
	}
	
	this.addContent = function(str){
		var textarea = this.textarea;
		textarea.focus();
		if(document.selection){
			document.selection.createRange().text=str; 
		}else{
			textarea.value=textarea.value.substr(0,textarea.selectionStart)+str+textarea.value.substring(textarea.selectionStart);
		}
		this.wbjx();
	}
	
	this.addConfig = function (type){
		var config = "[题目类型：" + type + "]";
		switch(type){
		case "单选题":;
		case "多选题":;
		case "单选组合":;
		case "多选组合":config+="[单行选项个数：]";break;
		case "单行文本":;
		case "多行文本":break;
		default:break;
		}
		config+="[是否必答：是]";
		if(this.wjModel.wjlx == WjModel_WJLX.CPL){
			config+="[试题总分：]";
		}
		this.addContent(config);
	}
	
	/**
	 * 验证题目选项信息
	 * @param {WjModel_TiMu} tm 题目对象
	 */
	this.wbjx_checkTmXxxx=function(tm){
		
	}
	
}
