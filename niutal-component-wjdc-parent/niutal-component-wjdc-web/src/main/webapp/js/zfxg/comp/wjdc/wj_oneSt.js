/**
 * 对单个题目的相关操作
 */
Wj_OneSt=function(){
	
	/**
	 * 增加所有试题编辑相关的按钮
	 */
	this.addAllStEditButton=function(wjModel){
		var st_divs=jQuery(".question_li");
		var stModels=wjModel.tmxx;
		var st_edit_index=0;
		var stid;
		for(var i=0;i<stModels.length;i++){
			if(stModels[i].stlx==WjModel_STLX.STDL){
				continue;
			}
			stid=stModels[i].stid;
			//jQuery(st_divs[i]).append("<button type='button' onclick='' >编辑</button>");
			jQuery(st_divs[st_edit_index]).html("<div style='width:100%;' align='right'><span id='span_stCheckMsg_"+stid+"' style='color:red'></span><button type='button' onclick='wj_oneSt.toEditOneSt(this,static_wjModel,\""+i+"\")' >编辑</button></div><div>"+jQuery(st_divs[st_edit_index]).html()+"<div>");
			st_edit_index++;
		}
	}
	
	/**
	 * 编辑单个试题,打开文本框
	 */
	this.toEditOneSt=function(curr_obj,wjModel,index_id){
		if(jQuery(curr_obj).html()=="编辑"){
			jQuery(curr_obj).html("关闭");
			var stModel=wjModel.tmxx[index_id];
			jQuery("#textInput_oneSt").prev().html("编辑");
			jQuery("#textInput_oneSt").remove();
			jQuery(curr_obj).after('<textarea style="width:94%;"  rows="10" id="textInput_oneSt" onkeyup="wj_oneSt.editOneSt(this,static_wjModel,\''+index_id+'\')"></textarea>');
			jQuery("#textInput_oneSt").val(stModel.getTmText());
		}else{//关闭
			wjFormat();
			jQuery(curr_obj).html("编辑");
			jQuery("#textInput_oneSt").remove();
		}
	}
	
	/**
	 * 编辑单个试题
	 */
	this.editOneSt=function(curr_obj,wjModel,index_id){
		var oneStText=jQuery(curr_obj).val();
		wjjx.static_stbh=index_id;
		var stModel=wjjx.wbjx_getTiMuModel(oneStText,index_id);
		jQuery(curr_obj).parent().next().html(stModel.getTmHtml());
		stModel.getEditStxxCheckMsg();
		//处理主文本框中的所有试题文本
		wjModel.tmxx[index_id]=stModel;
		jQuery(wjjx.textarea).val(wjModel.getWjText());
	}
}