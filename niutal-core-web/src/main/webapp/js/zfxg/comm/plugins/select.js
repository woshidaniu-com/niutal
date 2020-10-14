
/**
 * 省市二级联动,必须先选省才会联动出市
 * @author Penghui.Qu
 */
function initShengShi(){
	jQuery('#sheng').textClue({
		id:'sheng',
		divId:'shengDiv',
		url:_path+'/zfxg!plugins/xzqhAjax_getShengList.html',
		listKey:'dm',
		listText:'mc',
		nextNode:{
			id:'shi',
			valueId:'shidm',
			divId:'shiDiv',
			url:_path+'/zfxg!plugins/xzqhAjax_getShiList.html',
			listKey:'dm',
			listText:'mc'
		}
	});
}


function initXyZy(){
	var zyComplete = jQuery('#zymc').textClue({
		id:'zymc',
		divId:'zyDiv',
		url:_path+'/zfxg!plugins/xsbmAjax_getZyList.html',
		listKey:'zydmID',
		listText:'zymc',
		valueId:'zydmID',
		params:{parentKey:"bmdm"},
		event:{
			onselect:function(){
				//bjComplete.init();
			}
		}
	});
	
	jQuery('#bmmc').textClue({
		id:'bmmc',
		divId:'xyDiv',
		url:_path+'/zfxg!plugins/xsbmAjax_getXyList.html',
		listKey:'bmdm',
		listText:'bmmc',
		valueId:'bmdm',
		event:{
			onselect:function(){
				zyComplete.init();
				jQuery('#zydmID').val("");
				jQuery('#zymc').val("");
			}
		}
	});
}

function initNj(){
	jQuery('#njmc').textClue({
		id:'njmc',
		divId:'njDiv',
		url:_path+'/zfxg!plugins/xsbmAjax_getNjList.html',
		listKey:'njdm',
		listText:'njmc',
		valueId:'njdm'
	});
}

/**
 * 年级、学院、专业、班级联动，无硬性要求先选定要一级才出下一级
 * 年级 ：njdm/njmc
 * 学院：bmdm/bmmc
 * 专业：zydmID/zymc
 * 班级: bjdmID/bjmc
 * @author Penghui.Qu
 */
function initXsbm(needNj){
	//是否启用年级，默认需要
	var qyNj = needNj == null ? true : needNj;
	var bjParams = qyNj ? {parentKey:"bmdm,zydmID,njdm"} : {parentKey:"bmdm,zydmID"};
	
	var bjComplete = jQuery('#bjmc').textClue({
		id:'bjmc',
		divId:'bjDiv',
		url:_path+'/zfxg!plugins/xsbmAjax_getBjList.html',
		listKey:'bjdmID',
		listText:'bjmc',
		valueId:'bjdmID',
		params:bjParams
	});
	
	var zyComplete = jQuery('#zymc').textClue({
		id:'zymc',
		divId:'zyDiv',
		url:_path+'/zfxg!plugins/xsbmAjax_getZyList.html',
		listKey:'zydmID',
		listText:'zymc',
		valueId:'zydmID',
		params:{parentKey:"bmdm"},
		event:{
			onselect:function(){
				bjComplete.init();
				jQuery('#bjdmID').val("");
				jQuery('#bjmc').val("");
			}
		}
	});
	
	jQuery('#bmmc').textClue({
		id:'bmmc',
		divId:'xyDiv',
		url:_path+'/zfxg!plugins/xsbmAjax_getXyList.html',
		listKey:'bmdm',
		listText:'bmmc',
		valueId:'bmdm',
		event:{
			onselect:function(){
				zyComplete.init();
				bjComplete.init();
				jQuery('#zydmID').val("");
				jQuery('#zymc').val("");
				jQuery('#bjdmID').val("");
				jQuery('#bjmc').val("");
			}
		}
	});
	
	if (qyNj){
		jQuery('#njmc').textClue({
			id:'njmc',
			divId:'njDiv',
			url:_path+'/zfxg!plugins/xsbmAjax_getNjList.html',
			listKey:'njdm',
			listText:'njmc',
			valueId:'njdm',
			event:{
				onselect:function(){
					bjComplete.init();
					jQuery('#bjdmID').val("");
					jQuery('#bjmc').val("");
				}
			}
		});
	}
}


function initXyZyCc(){
	
	jQuery("#xlccdm").bind("change",function(){
		jQuery('#zydmID').val("");
		jQuery('#zymc').val("");
		_initZyList();
	});
	
	var _initZyList = function(){
		jQuery('#zymc').textClue({
			id:'zymc',
			divId:'zyDiv',
			url:_path+'/zfxg!plugins/xsbmAjax_getZyList.html',
			listKey:'zydmID',
			listText:'zymc',
			valueId:'zydmID',
			params:{bmdm:jQuery("#bmdm").val(),ccdm:jQuery("#xlccdm").val()},
			event:{
				onselect:function(){
					//bjComplete.init();
				}
			}
		});
	};
	
	
	jQuery('#bmmc').textClue({
		id:'bmmc',
		divId:'xyDiv',
		url:_path+'/zfxg!plugins/xsbmAjax_getXyList.html',
		listKey:'bmdm',
		listText:'bmmc',
		valueId:'bmdm',
		event:{
			onselect:function(){
				jQuery('#zydmID').val("");
				jQuery('#zymc').val("");
				_initZyList();
			}
		}
	});
	
	_initZyList();
}

