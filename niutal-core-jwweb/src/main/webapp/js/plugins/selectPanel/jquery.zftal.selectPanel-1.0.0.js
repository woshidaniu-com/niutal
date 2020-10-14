;(function($) {
	
	
	var titles = ["","选择学生","选择教师","选择学院","选择专业","选择班级","选择课程","选择年级","选择机构","选择二级教室场地类别",
	              "选择角色","选择专业方向","选择教学场地","选择年级","选择用户","选择大类"];
	
	$.extend({
		
		/**进入公共选择页面*
		 * 参数说明：
		 * gridType:"1",  //列表类型：学生：1；教师：2,；学院：3； 专业：4；班级：5；课程：6 ；年级：7；机构：8；二级教室场地类别：9；角色：10；
		 * options ： {
		 * 
		 * 		njdm_id:?,	//年级ID
		 * 		jg_id:?,	//学院ID
		 * 		xx_id:?,	//系ID
		 * 		zyh_id:?,	//专业ID
		 * 		zyfx_id:?,	//专业方向ID
		 * 		bh_id:?,	//班级ID
		 * 
		 * 		"title":null,
		 * 		"url":"",
		 *		"mapper":null,
		 *		"index":"select",
		 *		//已选
		 *		"checked":[],//多个用,隔开
		 *		"multiselect":true,//新页面打开的grid是否多选
		 *		"width":  800,
		 *		"height" : 500		
		 * },
		 * callback:function(){} // 点击确定后的回调函数
		 */
		showSelectDialog:function(gridType,options,callbackFunc){
			//默认setting
			var defaultSettings = {
				"title":null,
				"mapper":null,
				"index":"select",
				//已选
				"checked":[],
				"multiselect":true,
				"selectAttr":true,
				"width":  800,
				"height" : 500,
			  	"gridType": gridType ||"1"  //列表类型：学生：1；教师：2,；学院：3； 专业：4；班级：5；课程：6 ；年级：7；机构：8；其他：任意值
			};
			
			callbackFunc = ($.defined(callbackFunc)&&jQuery.isFunction(callbackFunc))?callbackFunc:$.noop;
			
			//扩展默认设置
			$.extend( defaultSettings, options || {});
			var title = options.title||(titles[Number(defaultSettings["gridType"])]||"");
			var paramMap = $.extend(true, {}, defaultSettings);
			
			
			$.dialog($.extend({}, defaultSettings, {
				"title": title,
				"href":_path + "/grid/grid_cxCommonSelect.html",
				"data":paramMap,
				"modalName":"selectModal",
				"buttons":{
					success : {
						label : "确  定",
						className : "btn-primary",
						callback : function() {
							return callbackFunc.call(this,this.content.getResultArr(),this.content.getDeleteArr());
						}
					},
					cancel : {
						label : "关 闭",
						className : "btn-default"
					}
				}
			}));
		}
	});
	
	$.fn.extend({
		
		/**进入公共选择页面*
		 * 参数说明：
		 * gridType:"1",  //列表类型：学生：1；教师：2,；学院：3； 专业：4；班级：5；课程：6 ；年级：7；机构：8；其他：任意值
		 * options ： {
		 * 
		 * 		njdm_id:?,	//年级ID
		 * 		jg_id:?,	//学院ID
		 * 		xx_id:?,	//系ID
		 * 		zyh_id:?,	//专业ID
		 * 		zyfx_id:?,	//专业方向ID
		 * 		bh_id:?,	//班级ID
		 * 
		 * 		"title":null,
		 * 		"url":"",
		 *		"mapper":null,
		 *		"index":"select",
		 *		//已选
		 *		"checked":[],//多个用,隔开
		 *		"multiselect":true,//新页面打开的grid是否多选
		 *		"width":  800,
		 *		"height" : 500		
		 * },
		 * callback:function(){} // 点击确定后的回调函数
		 */
		loadSelectPanel:function(gridType,options,callbackFunc){
			//默认setting
			var defaultSettings = {
				"title":null,
				"href":"",
				"mapper":null,
				"index":"select",
				//已选
				"checked":[],
				"multiselect":true,
				"selectAttr":true,
				"width":  800,
				"height" : 500,
			  	"gridType": gridType ||"1"  //列表类型：学生：1；教师：2,；学院：3； 专业：4；班级：5；课程：6 ；年级：7；机构：8；其他：任意值
			};
			
			callbackFunc = ($.defined(callbackFunc)&&jQuery.isFunction(callbackFunc))?callbackFunc:$.noop;
			$.extend(defaultSettings, options || {});
			window.api = {
				data : defaultSettings||{},
				opener : self 
			};
			var title = defaultSettings.title||(titles[Number(defaultSettings["gridType"])-1]||"");
			//扩展默认设置
			$(this).load(_path + "/grid/grid_cxCommonSelect.html",$.extend(defaultSettings, options || {},{
				"title": title
			}), function(){
				callbackFunc.call(this,getResultArr(),getDeleteArr());
			});
		}
	});
}(jQuery));