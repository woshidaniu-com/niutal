/**问卷 客户端和服务端的交互*/
Wj_Client_Service = function(){
	
	this.wjModel;//WjModel_WenJuan必须绑定

    /**问卷保存提交*/
    this.wjSave_submit = function(){
        this.wjSave_pretreatment();
        //提交保存
    }
    
    /**问卷保存预处理,将试题的属性提取出来，放在指定div中*/
    this.wjSave_pretreatment = function(hidden_div_id){
    
    }
    
    /**问卷获取预处理，并处理成Model*/
    this.wjGet_pretreatment = function(url,cs){
    	jQuery.getJSON(url, function(data){
    		  cs.wj_jsonToWjModel(data);
    		});
        
    }
    
    
	
    /**将接收的json转化成问卷对象*/
	this.wj_jsonToWjModel=function(data){
		if(!data){return null};
		var tms=data.tmxxs;
		var xxs=data.xxxxs;
		var tmModels=new Array();
		var stbh=0;
		for(var i=0;i<tms.length;i++){
			var tm=tms[i];
			var tmModel=new WjModel_TiMu();
			
			tmModel.stid = tm.stid;//试题id
		    tmModel.stmc = tm.stmc;//试题名称
		    tmModel.stlx = tm.stlx;//试题类型
		    if(tm.dhxxgs){
		    	tmModel.dhxxgs = tm.dhxxgs;//单行选项个数
		    }
		    if(tm.sfbd){
		    	tmModel.sfbd = tm.sfbd;//是否必答
		    }
		    if(tm.stzf){
		    	tmModel.stzf = tm.stzf;//试题总分
		    }
		    if(tm.xxkzdxzs){
		    	tmModel.xxkzdxzs = tm.xxkzdxzs;//选项可最多选择数
		    }
		    //显示顺序
		    if(tmModel.stlx!=WjModel_STLX.STDL){
		    	tmModel.stbh=++stbh;//试题编号
		    }
		    var xxModels= new Array();
			for (var j = 0; j < xxs.length; j++) {
				var xx = xxs[j];
				if (tmModel.stid == xx.stid) {
					var xxModel = new WjModel_Xxxx();
					xxModel.xxid = xx.xxid;//选项id
					xxModel.xxmc = xx.xxmc;//选项名称
					if(xx.xxfz){
						xxModel.xxfz = xx.xxfz;//选项分值
					}
					xxModels[xxModels.length] = xxModel;
				}
			}
		    tmModel.xxxx = xxModels;//选项信息WjModel_Xxxx
		    tmModels[tmModels.length]=tmModel;
		}
		this.wjModel.tmxx=tmModels;
		jQuery("#wjview").append(this.wjModel.getWjHtml());
		return this.wjModel;
	}
	
	/**问卷获取预处理，并处理成Model*/
    this.wjdaGet_pretreatment = function(url,cs){
    	jQuery.getJSON(url, function(data){
    		cs.wjda_jsonToxxModel(data);
    		});
        
    }
    
    
	
    /**将接收的json转化成问卷对象*/
	this.wjda_jsonToxxModel=function(data){
		if(!data){return null};
		for(var i=0;i<data.length;i++){
			var xxda=data[i];
			var stid=xxda.stid;
			var xxid=xxda.xxid;
			var txnr=xxda.txnr;
			if(xxid!="0"){//选项
				jQuery("#"+xxid).attr("checked","checked");
			}else{//文本
				jQuery("#textid_"+stid).val(txnr);
			}
		}
	}
}
