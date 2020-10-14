/**
 * @discretion	: default messages for the jQuery SearchBox plugin.
 * @author    	: kangzhidong 
 * @email     	: hnxyhcwdl1003@163.com
 * @example   	: 1.引用jquery的库文件js/jquery.js
  				  2.引用样式文件css/uiwidget.searchBox-1.0.1.css
 				  3.引用效果的具体js代码文件 uiwidget.searchBox-1.0.1.js
 				  4.<script language="javascript" type="text/javascript">
					jQuery(function($) {
					
						$("#scrollDiv").searchBox({
							afterRender : function(){
								/*这个方法是初始化后的回调函数，在需要做一些事情的时候重写即可
							
							}
						});
						
					});
					</script>
 */
(function($){
	
	
	
	
	$.multiui = $.multiui || {};
	$.multiui.widget = $.multiui.widget || {};
	
	/*====================== SearchBox CLASS DEFINITION ====================== */
	
	$.multiui.widget.SearchBox = function(element,options){
		options.beforeRender.call(this,element);	//渲染前的函数回调
		try {
			this.initialize.call(this, element, options);
		} catch (e) {
			options.errorRender.call(this,e);
		}
		options.afterRender.call(this,element);	/*渲染后的函数回调*/
	};
	
	$.multiui.widget.SearchBox.prototype = {
		constructor: $.multiui.widget.SearchBox,
		/*初始化组件参数*/
		initialize 	: function(element, options) {
			var $this = this; 
			var messages = $.multiui.widget.messages.searchbox; 
			
			// build main options before element iteration    
		    var settings = $.extend(true, {},  options ,{
		    	conditionList:[],	/*当前的条件集合*/
				condition_rows:{}	/*条件区域元素缓存对象*/
		    });
		    
		    // the base DOM structure needed to create a searchbox
			var templates = {
				filter:	function(){
					var html = [];
						html.push('<div class="row search-filter ">');
							html.push('<div class="col-sm-12 col-md-12">');
								html.push('<div class="form-group form-group-sm"  style="margin-bottom:0px;">');
									html.push('<label for="" class="col-sm-2 col-md-2 control-label" >&nbsp;</label>');
									html.push('<div class="col-sm-8 col-md-8  buttons">');
										html.push('<div class="input-group">');
											//如果定义了模糊过来选项
											if($.founded(settings.filterText)){
												html.push('<span class="input-group-addon"><label>'+ settings.filterText +'</label></span>');
											}
											html.push('<input type="text" value="" name="searchInput" class="form-control input-sm filter-input"  placeholder="'+messages.placeholder+'">');
											html.push('<span class="input-group-btn">');
												html.push('<button class="btn btn-primary btn-sm" type="button" name="query"><i class=" bigger-110 icon-search"></i>&nbsp;'+messages.queryButtonText+'</button>' );
												html.push('<button class="btn btn-default btn-sm" type="button" name="reset"><i class=" bigger-110 icon-repeat"></i>&nbsp;'+messages.resetButtonText+'</button>');
											html.push('</span>');
										html.push('</div>');
									html.push('</div>');
								html.push('</div>');
							html.push('</div>');
							//如果定义了模糊过来选项
							if($.founded(settings.filters)){
								html.push('<div class="col-sm-12 col-md-12">');
									html.push('<div class="form-group form-group-sm"  style="margin-bottom:0px;">');
										html.push('<label for="" class="col-sm-2 col-md-2 control-label">&nbsp;</label>');
										html.push('<div class=" col-sm-8 col-md-8 types">');
											html.push('<label class="radio-inline"><input checked="checked" value="all" name="searchFilter" type="radio">' + messages.allRadioText + '&nbsp;&nbsp;</label>' );
										html.push('</div>');
									html.push('</div>');
								html.push('</div>');
							}
					html.push('</div>');
					return 	html.join("");
			   },
			   filterRadio	:function(key,text){
			    	return '<label class="radio-inline"><input value="'+key+'" name="searchFilter" type="radio"/>'+text+ '&nbsp;&nbsp;</label>';
			   },
			   selected:function(){
				   return '<div class="row selected-attr">' +
							'<div class="col-sm-12 col-md-12">' +
								'<div class="form-group form-group-sm" style="margin-bottom:5px;">' +
									'<label for="" class="col-sm-2 col-md-2 control-label">'+messages.seletedLabelText+'：</label>' +
									'<div class="col-sm-9 col-md-9 selecteds">' +
									'</div>' +
								'</div>' +
							'</div>' +
						'</div>';
			    },
			    condition_item:'<div class="row condition-item"></div>',
				condition_row:function(itemObject,liItems){
					return '<div class="col-sm-12 col-md-12 condition-row">' +
							'<div class="form-group form-group-sm" style="margin-bottom:5px;">' +
								'<label for="" class="col-sm-2 col-md-2 control-label title">'+itemObject.text+'：</label>' +
								'<div class="col-sm-9 col-md-9 items" name="'+itemObject.index+'"><ul>' +
								liItems +
								'</ul></div>' +
							'</div>' +
						'</div>';
				},
				query_row:function(itemObject){
					return 	'<div class="row query-item ">' +
								'<div class="col-sm-12 col-md-12">' +
									'<div class="form-group form-group-sm pull-right"  style="margin-bottom:0px;">' +
										'<div class="col-sm-12 col-md-12  buttons">' +
											 '<div class="btn-group">' +
											    '<button class="btn btn-primary btn-sm" type="button" name="query"><i class=" bigger-110 icon-search"></i>&nbsp;'+messages.queryButtonText+'</button>' +
												'<button class="btn btn-default btn-sm" type="button" name="reset"><i class=" bigger-110 icon-repeat"></i>&nbsp;'+messages.resetButtonText+'</button>' +
										    '</div>' + 
										'</div>' +
									'</div>' +
								'</div>' +
						'</div>';
				},
				fixed:	function(index){
					return '<li class="col-sm-12 col-md-12"  name="fixed" index="'+index+'">'+
								'<div class="input-group input-group-xs">' +
								    '<input type="text" value="" class="form-control pull-left input-xs input-xs-last fixed"/>' +
								    '<span class="input-group-btn pull-left">' +
								    	'<button class="btn btn-default btn-xs sure" type="button">'+messages.sureButtonText+'</button>' +
								    '</span>'  +
							    '</div>' +
						   '</li>';
				},
				range:	function(itemObject){
					var classStr = (itemObject.type||"string").toLowerCase();
					var readonlyStr = "",onkeyupStr = "",styleStr = "";
					if(classStr=="time"){
						readonlyStr = ' readonly="readonly" '; 
					}else if(classStr=="integer"){
						styleStr = ' style = "ime-mode:disabled " ';
						//整数
						onkeyupStr = ' onkeyup="this.value=this.value.replace(/[^\\d]/g,\'\')" onbeforepaste="clipboardData.setData(\'text\',clipboardData.getData(\'text\').replace(/[^\\d]/g,\'\'))" '; 
					}else if(classStr=="number"){
						//数字[整数，浮点数]
					}
					return '<li class="col-sm-12 col-md-12" name="range" index="'+itemObject.index+'">'+
								'<div class="input-group input-group-xs">' +
									'<input type="text" value="" class="form-control pull-left input-xs range '+classStr+'"  '+ readonlyStr + styleStr + onkeyupStr +'/>' +
									'<span class="pull-left" style="line-height: 16px;padding: 2px;">'+messages.toLabelText+'</span>' +
									'<input type="text" value="" class="form-control pull-left input-xs input-xs-last range '+ classStr +'"  '+ readonlyStr + styleStr + onkeyupStr +'/>' +
									'<span class="input-group-btn pull-left">' +
							    		'<button class="btn btn-default btn-xs sure" type="button">'+messages.sureButtonText+'</button>' +
							    	'</span>' +
							    '</div>' +
						  '</li>'; 
				},
				items	:function(itemObject){
					/*判断attr属性是否存在，并判断类型，获得结果*/
					var attr = $.founded(itemObject.attr)?($.isFunction(itemObject.attr)? itemObject.attr.call(this,itemObject):itemObject.attr):"";
					var html = [];
					//遍历生成已有数据的DOM对象
					$.each(itemObject.items||[], function(i,item){
						var indexStr = (itemObject.index||"")+"_"+item.key;
						html.push('<li class="col-sm-1 col-md-1" name="'+(item.moreEvent||"fixed")+'" index="'+indexStr+'" >');
						html.push('<a href="javascript:void(0);" '+attr + (item.checked?' class="selectedValue" ':"")+'>'+item.text+'</a>');
						html.push('</li>');
					});
				    return html.join("");
			    },
			    select 	:function(items,itemObject){
			    	var index = itemObject.index,
			    		multiple = itemObject.multiple||"";
			    	var html = ['<li class="col-sm-12 col-md-12"  name="select" index="'+index+'">'];
				    	html.push('<div class="input-group select-group-xs">');
				    	html.push('<select class="form-control pull-left select-xs single-select" '+($.founded(multiple) ? (' data-multiple="'+multiple+'"') : "" )+'>');
				    	html.push('<option value=""  index="'+(index||"")+'_all" >' + messages.allSelectText + '</option>');
				    	//遍历生成已有数据的DOM对象
						$.each(items||[], function(i,item){
							var indexStr = (index||"")+"_"+item.key;
							html.push('<option value="'+ item.key +'"  index="'+indexStr+'" ' +  (item.selected?' selected="selected" ':"")  +'>' + item.text + '</option>');
						});
						html.push('</select></div></li>');
				    return html.join("");
			    },
			    dualSelect:	function(index,leftItems,rightItems){
					var html = ['<li class="col-sm-12 col-md-12"  name="dualSelect" index="'+index+'">'];
						html.push('<div class="input-group select-group-xs">');
					    	html.push('<select class="form-control pull-left select-xs dual-select" >');
					    		html.push('<option value="">' + messages.allSelectText + '</option>');
					    		//遍历生成已有数据的DOM对象
								$.each(leftItems||[], function(i,item){
									html.push('<option value="'+ item.key +'" ' +  (item.selected?' selected="selected" ':"")  +'>' + item.text + '</option>');
								});
							html.push('</select>');
							html.push('<span class="pull-left" style="line-height: 16px;padding: 2px;">'+messages.toLabelText+'</span>');
							html.push('<select class="form-control pull-left select-xs select-xs-last " >');
				    			html.push('<option value="">' + messages.allSelectText + '</option>');
				    		//遍历生成已有数据的DOM对象
							$.each(rightItems||[], function(i,item){
								html.push('<option value="'+ item.key +'"  ' +  (item.selected ? ' selected="selected" ':"")  +'>' + item.text + '</option>');
							});
							html.push('</select>');
							html.push('<span class="input-group-btn pull-left"><button class="btn btn-default btn-xs sure" type="button">'+messages.sureButtonText+'</button></span>');
						html.push('</div>');
					html.push('</li>');
					return html.join("");	
				},
				radio	:function(index,items){
			    	var html = ['<li class="col-sm-12 col-md-12"  name="radio" index="'+index+'">','<div class="input-group input-group-xs">'];
					//遍历生成已有数据的DOM对象
					$.each(items||[], function(i,item){
						var indexStr = (index||"")+"_"+item.key;
						var nameStr  = (index||"")+"_radio";
						html.push('<label class="radio-inline radio-inline-xs">');
						html.push('<input class="form-control pull-left radio-xs" value="'+item.key+'" name="'+nameStr+'" index="'+indexStr+'" ' +  (item.checked?' checked="checked" ':"") + ' type="radio"/>'+item.text+ '&nbsp;&nbsp;');
						html.push('</label>');
					});
					html.push('</div></li>');
				    return html.join("");
			    },
			    checkbox:function(index,items){
			    	var html = ['<li class="col-sm-12 col-md-12"  name="checkbox" index="'+index+'">','<div class="input-group input-group-xs">'];
					//遍历生成已有数据的DOM对象
					$.each(items||[], function(i,item){
						var indexStr = (index||"")+"_"+item.key;
						var nameStr  = (index||"")+"_checkbox";
						html.push('<label class="checkbox-inline checkbox-inline-xs">');
						html.push('<input class="form-control pull-left checkbox-xs" value="'+item.key+'" name="'+nameStr+'" index="'+indexStr+'" ' +  (item.checked?' checked="checked" ':"") + ' type="checkbox"/>'+item.text+ '&nbsp;&nbsp;');
						html.push('</label>');
					});
					html.push('</div></li>');
				    return html.join("");
			    },
			    more	: function(itemObject) {
			    	return '<div class="col-sm-1 col-md-1 more" index="'+itemObject.index+'"><a href="javascript:void(0);" class="more_up" style="display: block;">&nbsp;'+messages.downLabelText+' </a></div>';
			    },
			    condition_retract:function(){
			    	return '<div class="col-sm-12 col-md-12 condition-retract"><p><a class="up" href="javascript:void(0);">'+messages.moreItemUpText+'</a></p></div>';
			    }
			};
			
			/*定义可直接且可通过$(element).trigger(methodName) 调用的函数*/
			var methods = {
				searchResult:function(){
					settings.onSearchClick.call($this,$this.getConditions(),$this.getMergeConditions());
    			},
				/*重置当前整体高级查询生成的条件；及选中效果*/
				resetConditions:function(){
					
					/*==================清空选中效果====================*/
					/*有模糊查询栏*/
					if(search_filter.size()>0){
						$(search_filter).find("div.buttons").find("input[name=searchInput]").val("");
						if($(search_filter).find('input.filter-input').size() > 0){
							$(search_filter).find("div.types").find(":radio:eq(0)").click();
						}
					}
					/*清除已选择显示效果*/
					$(selected_attr).find("div.selecteds").empty();
					/*有高级查询项*/
					if(condition_item.size()>0){
						$(condition_item).find(":text").val("");
						$(condition_item).find("div.items a").removeClass("selectedValue");
					}
					
					$.each(settings.model,function(i,itemObject){
						/*循环items*/
						var item_arr = [];
						$.each(itemObject.items||[],function(i,itemObj){
							itemObj.checked = false;
							var indexStr = (itemObject.index||"")+"_"+itemObj.key;
							$.each(settings.conditionList||[],function(i,item){
								if(item.indexStr == indexStr){
									item_arr.push(itemObj);
									return false;
								}
							});
						});
						/*移除非默认元素*/
						if(item_arr.length>0){
							$this.removeSelectedItem(itemObject,item_arr);
						}
						/*选择效果显示区域*/
						var condition_ul = $(settings.condition_rows[itemObject.index]).find("ul:eq(0)");
						if($.defined(itemObject.select)&&$.toBoolean(itemObject.select)){
							/*下拉框*/
							$(condition_ul).find("select.select-xs").val("");
						}else if($.defined(itemObject.dualSelect)&&$.toBoolean(itemObject.dualSelect)){
							//双下拉
							$(condition_ul).find("select.select-xs").each(function(){
								$(this).data("initialized",false);
								$(this).data("requestMap",{});
								$(this).val("");
							});
						}else if($.defined(itemObject.radio)&&$.toBoolean(itemObject.radio)){
							/*单选框*/
							$(condition_ul).find(":radio").removeAttr("checked");
						}else if($.defined(itemObject.checkbox)&&$.toBoolean(itemObject.checkbox)){
							/*多选框*/
							$(condition_ul).find(":checkbox").removeAttr("checked");
						}
						
					});
					
					/*重置条件集合*/
					settings.conditionList = [];
					
					/*自动重新查询*/
					if(settings.autoSearch){
						settings.onSearchClick.call($this,$this.getConditions(),$this.getMergeConditions());
					}
				}
			};
			
			/*扩展this:组件函数*/
	    	$.extend($this,{
	    		/*构建一个内置元素对象*/
	    		buildItem:function(itemObject,key,value,options){
	    			var indexStr = (itemObject.index||"")+"_"+key;
	    			return $.extend(true ,options||{},{"index":indexStr,"key":key,"text":value});
	    		},
	    		/*添加一个选中条件对象；并同时添加选择效果*/
				addSelectedItem:function(itemObject,itemObjs){
	    			//已选条件区域
					var selected_div = $(selected_attr).find("div.selecteds");
					/*选择效果显示区域*/
					var condition_ul = $(settings.condition_rows[itemObject.index]).find("ul:eq(0)");
					var itemObj_arr = [];
					if($.isArray(itemObjs)){
						itemObj_arr = jQuery.merge(itemObj_arr,itemObjs)
					}else{
						itemObj_arr = [itemObjs];
					}
					//当前条件是否多选
					var multiple = itemObject.multiple;
					
					var isChange = false;
					$.each(itemObj_arr||[],function(i,itemObj){
						itemObj.checked = true;
						itemObj.keyIndex = itemObject.index;
						itemObj.indexStr = (itemObject.index||"")+"_"+itemObj.key;
						
						//如果是非多选，则需要每次添加前移除相同条件的已选项
						if(!multiple){
							//移除相同条件项已选的条件UI元素
							$(selected_div).find("div.selected[index*='"+itemObject.index+"']").each(function(i,itemElement){
								var indexStr = $(this).attr("index");
								//筛选出相同条件项的已选条件对象
								var deleted_arr = $.grep(settings.conditionList||[], function(itemObj,i){
									  return indexStr == itemObj.indexStr;
								});
								//移除相同条件项已选的条件数据缓存
								$this.removeSelectedItem(itemObject,deleted_arr);
							});
							
						}
						
						if(!itemObj.isDefault){
							addItem(itemObject,itemObj);
						}
						if(!hasItem(settings.conditionList,itemObj)){
							isChange = true;;
							/*添加选择的数据对象*/
							settings.conditionList.push(itemObj);
							/*添加选择的数据选择效果*/
							$(selected_div).append('<div class="col-sm-12 col-md-2 selected" index="'+itemObj.indexStr+'"><a index="'+itemObj.indexStr+'" key="'+itemObj.key+'" href="javascript:void(0);">'+itemObject.text+":"+itemObj.text+'<span class="close-icon"></span></a></div>');
							/*移除选择a元素样式*/
							var li_a = $(condition_ul).find("li[index='"+itemObj.indexStr+"']").find("a:eq(0)");
							$(li_a).addClass("selectedValue");
							/*绑定移除选中按钮*/
							$(selected_div).find("div[index='"+itemObj.indexStr+"']").find("span.close-icon").unbind().click(function(e){
								e.stopPropagation();
								var indexStr2 = $(this).parent().parent().attr("index");
								var clickItemObj = undefined;
								$.each(settings.conditionList||[],function(i,itemObj){
									if(itemObj.indexStr==indexStr2){
										clickItemObj = itemObj;
										return false;
									}
								});
								/*移除选中元素对象*/
								$this.removeSelectedItem(itemObject,clickItemObj);
								return false;
							});
							
							$(selected_attr).show();
						}
					});
					if(isChange){
						cascadeEvent(itemObject);
						if(settings.autoSearch){
							settings.onSearchClick.call($this,$this.getConditions(),$this.getMergeConditions());
						}
					}
				},
	    		/*移除选中的元素*/
				removeSelectedItem:function(itemObject,itemObjs){
					if($.founded(itemObjs)){
						/*选择效果显示区域*/
						var condition_ul = $(settings.condition_rows[itemObject.index]).find("ul:eq(0)");
						var selected_div = $(selected_attr).find("div:eq(0)");
						var itemObj_arr = [];
						if($.isArray(itemObjs)){
							itemObj_arr = jQuery.merge(itemObj_arr,itemObjs)
						}else{
							itemObj_arr = [itemObjs];
						}
						var isChange = false;
						var removeIndexs = [];
						$.each(itemObj_arr||[],function(i,itemObj){
							var indexStr = (itemObject.index||"")+"_"+itemObj.key;
							
							itemObj.removed = true;
							/*第一个参数为当前元素，第二个参数而元素索引值;过滤函数必须返回 true 以保留元素或 false 以删除元素。*/
							settings.conditionList = jQuery.grep(settings.conditionList||[], function(item,i){
								if(item.indexStr==indexStr){
									isChange = true;
									return false;
								}else{
									return true;
								}
							});
							
							/*移除选择a元素样式*/
							var li_a = $(condition_ul).find("li[index='"+indexStr+"']").find("a:eq(0)");
							$(li_a).removeAttr("class");
							$(li_a).removeClass("selectedValue");
							$(selected_div).find("div[index='"+indexStr+"']").remove();
							if($(selected_div).find("div.selected").size()==0){
								$(selected_div).parent().hide();
							}
							
						});
						if(isChange){
							cascadeEvent(itemObject);
							if(settings.autoSearch){
								settings.onSearchClick.call($this,$this.getConditions(),$this.getMergeConditions());
							}
						}
					}
				},
				/*获取当前整体高级查询生成的条件*/
				getConditions:function(){
					var conditionMap = {};
					var sortList = (settings.conditionList||[]).sort(function(a, b) {
						return a["index"].localeCompare(b["index"]);
					});
					$.each(settings.model,function(i,itemObject){
						/*循环选择条件生成条件对象*/
						var index = 0;
						$.each(sortList,function(k,itemObj){
							if(itemObj.keyIndex == itemObject.index){
								conditionMap[itemObject.index+"["+index+"]"] = itemObj.key;
								index += 1;
							}
						});
					});
					/*有模糊查询栏*/
					if(search_filter.size()>0){
						var searchVal = $(search_filter).find("div.buttons").find("input[name='searchInput']").val();
						var searchVal_arr = [];
						if($.founded(searchVal)){
							$.each(searchVal.split(" ")||[],function(i,val){
								if($.founded($.trim(val))){
									searchVal_arr.push(val);
								}
							});
						}
						if(searchVal_arr.length>0){
							//如果有radio选项则添加filterKey参数
							if($(search_filter).find('input.filter-input').size() > 0){
								conditionMap["filterKey"] =  $(search_filter).find("div.types").find("input[name='searchFilter']:checked").val();
							}
							$.each(searchVal_arr,function(i,val){
								conditionMap["filter_list["+i+"]"] = val;
							});
						}
					}
					return conditionMap;
				},
				/*获取当前整体高级查询生成的条件:多个条件合并为,分割*/
				getMergeConditions:function(){
					var conditionMap = {};
					var sortList = (settings.conditionList||[]).sort(function(a, b) {
						return a["index"].localeCompare(b["index"]);
					});
					$.each(settings.model,function(i,itemObject){
						/*循环选择条件生成条件对象*/
						$.each(sortList,function(k,itemObj){
							if(itemObj.keyIndex == itemObject.index){
								if(!conditionMap[itemObject.index]){
									conditionMap[itemObject.index] = [];
								}
								conditionMap[itemObject.index].push(itemObj.key);
							}
						});
					});
					/*有模糊查询栏*/
					if(search_filter.size()>0){
						var searchVal = $(search_filter).find("div.buttons").find("input[name='searchInput']").val();
						var searchVal_arr = [];
						if($.founded(searchVal)){
							$.each(searchVal.split(" ")||[],function(i,val){
								if($.founded($.trim(val))){
									searchVal_arr.push(val);
								}
							});
						}
						if(searchVal_arr.length>0){
							//如果有radio选项则添加filterKey参数
							if($(search_filter).find('input.filter-input').size() > 0){
								conditionMap["filterKey"] =  $(search_filter).find("div.types").find("input[name='searchFilter']:checked").val();
							}
							conditionMap["filter_list"] = [];
							$.each(searchVal_arr,function(i,val){
								conditionMap["filter_list"].push(val);
							});
						}
					}
					$.each(conditionMap,function(key,array){
						if($.isArray(array)){
							conditionMap[key] = array.join(",");
						}
					});
					return conditionMap;
				}
	    	},methods);
		    	
	    	$.each(methods,function(key,func){
				$(element).bind(key,func);
			});
		    	
			/*======私有函数=========*/ 
			
			/*构建高级查询dom元素*/
			function buildContainer(options){
				var context = new Array();
				context.push('<div class="form-horizontal sl_all_form searchbox" style="display: block;border: 0px;">');
					//需要模糊查询 searchFilter = true|yes|1
					if($.defined(options.searchFilter) && $.toBoolean(options.searchFilter)){
						context.push('<!-- fuzzy search -->');
						context.push(templates.filter.call($this));
						context.push('<!-- fuzzy search end-->');
					}else{
						context.push('<!-- search query-->');
						context.push(templates.query_row.call($this));
						context.push('<!-- search query end-->');
					}
					//需要已选条件 selectedAttr = true|yes|1
					if($.toBoolean(options.selectedAttr)){
						context.push('<!-- selected conditions -->');
						context.push(templates.selected.call($this));
						context.push('<!-- selected conditions end-->');
					}
					if($.defined(options.model)&&options.model.length>0){
						context.push('<!-- advanced query-->');
						context.push(templates.condition_item);
						context.push('<!-- advanced query end-->');
					}
					
				context.push('</div>');
				return context.join("");
			};
			
			
			/*判断对象数组itemObjList中是否存在itemObj对象*/
			function hasItem(itemObjList,itemObj){
				var ishave = false;
				$.each(itemObjList||[],function(i,item){
					if(item.index==itemObj.index&&item.key==itemObj.key){
						ishave = true;
						return false;
					}else{
						ishave = false;
					}
				});
				return ishave;
			};
			
			
			/*向对象itemObject的items数组添加一个对象*/
			function addItem(itemObject,itemObj){
				/*循环当前itemObject的items数组判断是否添加的元素已经存在*/
				if(!hasItem(itemObject.items,itemObj)){
					itemObject.items.push(itemObj);
				}
			};
			
			/*获取itemObject.items中指定key的元素*/
			function getItem(itemObject,index){
				/*var index = itemObject.index+"-"+key;*/
				var itemObj = null;
				$.each(itemObject.items||[],function(i,item){
					if(item.index==index){
						itemObj = item;
						return false;
					}
				});
				return itemObj;
			};
			
			/*根据元素生成html*/
			function buildItemsHtml(itemObject,items){
				/*遍历当前可见项*/
				var temp  = [];
				$.each(items||[], function(i,itemObj){
					addItem(itemObject,itemObj);
				});
				/*判断当前选项是否是默认值*/
				if($.founded(itemObject.defaults)){
					//判断当前items是否包含默认值
					$.each(itemObject.defaults||[], function(j,defaultItem){
						//判断已有数据中是否有默认值，并追加部分属性
						$.each(itemObject.items, function(i,item){
							defaultItem.isDefault = true;
							defaultItem.index = (itemObject.index||"")+"_"+defaultItem.key;
							if(item.key == defaultItem.key){
								defaultItem.isHave = true;
								defaultItem.checked = true;
								item.checked = true;
								item.isDefault = true;
								$this.addSelectedItem(itemObject,item);
								return false;
							}
						});
					});
				}
				return templates.items.call($this,itemObject);
			};
			
			/*级联事件*/
			function cascadeEvent(itemObject){
				/*=======判断当前点击项是否有子集关联点================*/
				var cascades = [];
				/*迭代其他mode项，判断关联子model查询*/
				$.each(settings.model,function(i,cascadeObject){
					/*非当前model项*/
					if(itemObject.index!=cascadeObject.index&&$.defined(cascadeObject.parent)){
						/*判断关联条件是否是数组*/
						if($.isArray(cascadeObject.parent)){
							/*循环关联条件;判断*/
							$.each(cascadeObject.parent,function(j,key){
								/*如果此项的parent收到当前点击项的影响，则缓存此项*/
								if($.trim(itemObject.index)==$.trim(key)){
									cascades.push(cascadeObject);
									return false;
								}
							});
						}else if($.trim(itemObject.index)==$.trim(cascadeObject.parent)){
							cascades.push(cascadeObject);
						}
					}
				});
				/*都到关联的条件项存在*/
				if(cascades.length>0){
					/*循环受到级联影响的对象，重新加载数据*/
					$.each(cascades,function(i,cascadeObject){
						/*请求参数*/
						var paramMap = {};
						/*获得非当前点击项的其他条件索引*/
						var indexs = [];	
						/*判断关联条件是否是数组*/
						if($.isArray(cascadeObject.parent)){
							/*循环关联条件;判断*/
							$.each(cascadeObject.parent,function(j,key){
								/*如果此项的parent收到当前点击项的影响，则缓存此项*/
								indexs.push(key);
							});
						}else{
							indexs.push(cascadeObject.parent);
						}
						/*根据索引获得其他条件的参数*/
						$.each(indexs||[],function(j,indexStr){
							var numIndex = 0;
							$.each(settings.conditionList||[],function(k,itemObj){
								if(itemObj.keyIndex == indexStr){
									paramMap[indexStr+"["+numIndex+"]"] = itemObj.key;
									numIndex += 1;
								}
							});
						});
						/*根据父级条件重新查询子级数据*/
						var cascade_condition_row = $(condition_item).find("div[name='"+cascadeObject.index+"']");
						if($.founded(cascadeObject.url) && $.founded(cascadeObject.mapper)){
							loadRemoteData.call(cascade_condition_row,cascadeObject.url,cascade_condition_row,cascadeObject,paramMap,function(){
								addDefaultItem(cascade_condition_row,cascadeObject);
							});
						}else if($.defined(itemObject.dualSelect)&&$.toBoolean(itemObject.dualSelect)){
							//双下拉
							$(cascade_condition_row).find("ul:eq(0)").find("select.select-xs").each(function(){
								$(this).data("initialized",false);
								$(this).data("requestMap",paramMap);
								$(this).val("");
							});
						}
					});
				 }
			};
			
			/*添加默认值*/
			function addDefaultItem(condition_row,itemObject){
				/*处理因数据尚未加载到本地，在条件选择区前面追加的默认值*/
				if($.founded(itemObject.defaults)){
					var condition_ul = $(condition_row).find("ul:eq(0)");
					/*判断attr属性是否存在，并判断类型，获得结果*/
					var attr = $.founded(itemObject.attr)?($.isFunction(itemObject.attr)? itemObject.attr.call($this,itemObject):itemObject.attr):"";
					//添加默认值不在本地时候的条件
					$.each(itemObject.defaults||[], function(j,defaultItem){
						if(!defaultItem.isHave){
							var indexStr = (itemObject.index||"")+"_"+defaultItem.key;
							var str = '<a href="javascript:void(0);" '+ attr +' class="selectedValue" >'+defaultItem.text+'</a>';
							$(condition_ul).prepend('<li class="col-sm-12 col-md-1" name="defalut" index="'+indexStr+'" >'+ str + '</li>');
							$this.addSelectedItem(itemObject,defaultItem);
						}
					});
					$(condition_ul).find("li[name='defalut']").each(function(index){
						var defaultItem = itemObject.defaults[index];
						$(this).find("a:eq(0)").unbind().click(defaultItem,function(e){
							e.stopPropagation();
							/*点击的对象数据*/
							$(this).addClass("selectedValue");
							$this.addSelectedItem(itemObject,e.data);
						});
					});
				}
			};
			
			/*设置下拉框已选项*/
			function setSelectSelectedItem(itemObject,selectElement,condition_ul){
				//得到当前选项
				var selected = $(selectElement).find("option:selected");
				if($(selected).founded() ){
					//构建选择项
					var itemObj = $this.buildItem(itemObject,$(selected).val(),$(selected).text());
					//判断是否多选
					var multiple = $(selectElement).data("multiple")||false;
					if($.founded(multiple) && (multiple=="multiple" || multiple == "true" || multiple == true)){
						//先移除选项，再添加选项；防止重复添加
						$this.removeSelectedItem(itemObject,itemObj);
						$this.addSelectedItem(itemObject,itemObj);
					}else{
						var pre_selectedIndex  = $(selectElement).data("preIndex");
						if($.founded(pre_selectedIndex)){
							var pre_selected = $(selectElement).find("option[index='"+pre_selectedIndex+"']")
							if($(pre_selected).defined()){
								//构建选择项
								var deleteItemObj = $this.buildItem(itemObject,$(pre_selected).val(),$(pre_selected).text());
								//移除上次选项
								$this.removeSelectedItem(itemObject,deleteItemObj);
							}
						}
						$(selectElement).data("preIndex",itemObj.key);
						$this.addSelectedItem(itemObject,itemObj);
					}
				}
			}
			
			/*设置双下拉框已选项*/
			function setDualSelectSelectedItem(itemObject,leftSelect,rightSelect,condition_ul){
				//得到当前选项
				var leftSelected = $(leftSelect).find("option:selected");
				var rightSelected = $(rightSelect).find("option:selected");
				//判断左右下拉框都选择value不为空的选项
				if($(leftSelected).founded() && $(rightSelected).founded()){
					$(leftSelect).find("option").each(function(){
						var leftOption = this;
						if($(this).founded() ){
							$(rightSelect).find("option").each(function(){
								if($(this).founded() ){
									//构建选择项
									var itemObj = $this.buildItem(itemObject,($(leftOption).val()+"||"+$(this).val()),($(leftOption).text()+"~"+$(this).text()));
									//先移除选项，再添加选项；防止重复添加
									$this.removeSelectedItem(itemObject,itemObj);
								}
							});
						}
					});
					//构建选择项
					var itemObj = $this.buildItem(itemObject,($(leftSelected).val()+"||"+$(rightSelected).val()),($(leftSelected).text()+"~"+$(rightSelected).text()));
					//先移除选项，再添加选项；防止重复添加
					$this.removeSelectedItem(itemObject,itemObj);
					$this.addSelectedItem(itemObject,itemObj);
				}
			}
			
			/*设置单选框已选项*/
			function setRadioCheckedItem(itemObject,condition_ul){
				/*单选框对象*/
				var radios = $(condition_ul).find(":radio");
				$(radios).each(function(){
					//构建选择项
					var itemObj = $this.buildItem(itemObject,$(this).val(),$(this)[0].nextSibling.nodeValue);
					if($(this).prop("checked")){
						//先移除选项，再添加选项；防止重复添加
						$this.removeSelectedItem(itemObject,itemObj);
						$this.addSelectedItem(itemObject,itemObj);
					}else{
						//移除选项
						$this.removeSelectedItem(itemObject,itemObj);
					}
				});
			}
			
			/*设置多选框已选项*/
			function setCheckboxCheckedItem(itemObject,condition_ul){
				/*多选框对象*/
				var checkboxs = $(condition_ul).find(":checkbox");
				$(checkboxs).each(function(){
					//构建选择项
					var itemObj = $this.buildItem(itemObject,$(this).val(),$(this)[0].nextSibling.nodeValue);
					if($(this).prop("checked")){
						//先移除选项，再添加选项；防止重复添加
						$this.removeSelectedItem(itemObject,itemObj);
						$this.addSelectedItem(itemObject,itemObj);
					}else{
						//移除选项
						$this.removeSelectedItem(itemObject,itemObj);
					}
				});
			}
			
			/*加载双select下拉框数据的函数*/
			function loadSelectData(remoteURL,condition_row,itemObject){
				var selectElement = this;
				var initialized = $(selectElement).data("initialized");
				if((!$.defined(initialized)||!$.toBoolean(initialized)) && $.founded(remoteURL)  ){
					loadRemoteData.call(selectElement,remoteURL,condition_row,itemObject,$(selectElement).data("requestMap")||{},function(){
						addDefaultItem(condition_row,itemObject);
					}).always(function() {
						if($.defined(itemObject.dualSelect)&&$.toBoolean(itemObject.dualSelect)){
							/*双下拉框*/
							if($.founded(itemObject.url)){
								$(condition_row).find("ul:eq(0)").find("select.select-xs").each(function(){
									$(this).data("initialized",true);
								});
							}
						}else{
							$(selectElement).data("initialized",true);
						}
					});
				}
			}
			
			/*绑定事件*/
			function bindEvent(condition_row,itemObject ){
				/*缓存当前条件区域*/
				settings.condition_rows[itemObject.index] = condition_row;
				/*选择效果显示区域*/
				var condition_ul = $(condition_row).find("ul:eq(0)");
				
				if($.defined(itemObject.select)&&$.toBoolean(itemObject.select)){
					/*下拉框*/
					$(condition_ul).find("select.select-xs").unbind().change(function(event){
						/*初始化已选对象*/
						setSelectSelectedItem(itemObject,this,condition_ul);
					});
				}else if($.defined(itemObject.dualSelect)&&$.toBoolean(itemObject.dualSelect)){
					/*双下拉框*/
					var selects = $(condition_ul).find("select.select-xs");
					$(selects[0]).unbind().click(function(event){
						//加载select数据
						var remoteURL = $.founded(itemObject.url) ? itemObject.url : itemObject.leftURL;
						loadSelectData.call(this,remoteURL,condition_row,itemObject);
					});
					$(selects[1]).unbind().click(function(event){
						//加载select数据
						var remoteURL = $.founded(itemObject.url) ? itemObject.url : itemObject.rightURL;
						loadSelectData.call(this,remoteURL,condition_row,itemObject);
					});
					/*绑定双下拉框后的确定按钮点击事件*/
					$(condition_ul).find("button.sure").unbind().click(function(e){
						/*初始化已选对象*/
						setDualSelectSelectedItem(itemObject,selects[0],selects[1],condition_ul);
					});
				}else if($.defined(itemObject.radio)&&$.toBoolean(itemObject.radio)){
					/*单选框*/
					$(condition_ul).find(":radio").unbind().change(function(event){
						/*初始化已选对象*/
						setRadioCheckedItem(itemObject,condition_ul);
					});
				}else if($.defined(itemObject.checkbox)&&$.toBoolean(itemObject.checkbox)){
					/*多选框*/
					$(condition_ul).find(":checkbox").unbind().click(function(event){
						/*初始化已选对象*/
						setCheckboxCheckedItem(itemObject,condition_ul);
					});
				}else{
					
					if($.defined(itemObject.range)&&$.toBoolean(itemObject.range)){
						/*起始结束范围*/
						var inputs = $(condition_ul).find("input[type='text']");
						var classStr = (itemObject.type||"string").toLowerCase();
						if(classStr=="integer"){
							//只能输入参数自动绑定脚本
							$(inputs).off('keydown.widget.data-api').on('keydown.widget.data-api', function (e) {
								var event = $.event.fix(e);
								if((!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39)) &&
									(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105)))){
									//取消事件冒泡
									event.stopPropagation();
									//取消浏览器默认行为
									event.preventDefault();
									event.returnValue=false;
								}
							});
						}else if(classStr == "number"){
							//只能输入参数自动绑定脚本
							$(inputs).off('keyup.widget.data-api').on('keyup.widget.data-api', function (e) {
								var nubmer = $.trim(this.value);
								if(nubmer.length>0 && isNaN(nubmer)){
									//只能输入数字和.
									this.value = nubmer.replace(/[^\d|.]/g,'')||"";
									//处理连续输入多个.
									this.value = nubmer.replace(/\.+/g,'.')||"";
									
									var firstIndex = nubmer.indexOf('.');
									//以.开始自动在前方添加0
									if(firstIndex == 0){
										this.value = '0'+nubmer;//0.
									}
									if( firstIndex > 0){
										//判断小数点后的内容
										var substr = nubmer.substring(nubmer.indexOf('.'),nubmer.length);
										if($.trim(substr).length>0){
											var lastIndex	= nubmer.lastIndexOf(".");
											if( firstIndex != lastIndex ){
												this.value = nubmer.substr(0,lastIndex);
											}
										}
									}
								}
							}).off('blur.widget.data-api').on('blur.widget.data-api', function (e) {
								var nubmer = $.trim(this.value);
								if(nubmer.length>0 && isNaN(nubmer)){
									var firstIndex = nubmer.indexOf('.');
									//以.开始自动在前方添加0
									if(firstIndex == (nubmer.length - 1)){
										this.value = nubmer.substr(0,firstIndex);
									}
									this.value = Number(this.value);
								}
							});
						}
					}
					
					/*绑定选择事件*/
					$(condition_ul).find("li[name='fixed'],li[name='append']").each(function(index){
						//当前条件项的数据
						var clickItem = itemObject.items[index];
						//绑定点击事件
						$(this).find("a:eq(0)").unbind().click(clickItem||{},function(e){
							e.stopPropagation();
							/*点击的对象数据*/
							$(this).addClass("selectedValue");
							$this.addSelectedItem(itemObject,e.data);
						});
					});
					
					//绑定自定义事件
					$(condition_ul).find("input[type=text]").each(function(i,input){
						$.each(itemObject.events||{},function(name,func){
							$(input).unbind(name).bind(name,func);
						});
					});
					
					/*绑定文本框后的确定按钮点击事件*/
					$(condition_ul).find("button.sure").unbind().click(function(e){
						e.stopPropagation();
						var li_el = $(this).closest("li[name='fixed'],li[name='range']");
						//.parent().parent().parent();
						var inputs = $(li_el).find(":text");
						if(inputs.size()==2&&$.founded($(inputs[0]).val())&&$.founded($(inputs[1]).val())){
							var itemObj = $this.buildItem(itemObject,($(inputs[0]).val()+"||"+$(inputs[1]).val()),($(inputs[0]).val()+"~"+$(inputs[1]).val()));
							$this.addSelectedItem(itemObject,itemObj);
						}else if(inputs.size()==1&&$.founded($(inputs[0]).val())){
							var itemObj = $this.buildItem(itemObject,$(inputs[0]).val(),$(inputs[0]).val());
							$this.addSelectedItem(itemObject,itemObj);
						}
					});
					
				}
				
				/*绑定更多事件*/
				$(condition_row).find("div.more a").unbind().click(function(e){
					e.stopPropagation();
					var a_elment = this;
					var index = $(a_elment).attr("index");
					var showSize = itemObject.showSize||settings.showSize;
					if($(a_elment).hasClass("more_up")){
						/*url模式；且数据已经全部加载到了本地*/
						if($.founded(itemObject.url)&&itemObject.local){
							var items = [];
							$.each(itemObject.localItems,function(i,itemObj){
								if(i >= showSize){
									itemObj["moreEvent"] = "append";
									items.push(itemObj);
								}
							});
							$(condition_row).find("ul:eq(0)").empty().append(buildItemsHtml(itemObject,items));
							/*更多状态*/
							$(a_elment).removeClass("more_up").addClass("more_down");
							$(a_elment).html("&nbsp;"+messages.upLabelText);
						}else if($.founded(itemObject.url)&&$.founded(itemObject.mapper)){
							/*url模式：重新加载数据*/
							var mapper = itemObject.mapper;
							var params = {};
								/*排序方式 asc , desc*/
								params[settings.paramNames.order||"queryModel.sortOrder"] = itemObject.order;
								/*排序字段 */
								params[settings.paramNames.sort||"queryModel.sortName"] = itemObject.sort;
								/*筛选当前条件已选对象*/
				                params["checked"] = function(){
				                	var tmp = [];
				                	$.each(settings.conditionList||[],function(i,itemObj){
				                		//过滤当前条件索引的已选条件
				                		if(itemObj.indexStr && itemObj.indexStr.indexOf(itemObject.index) == 0 ){
				                			tmp.push(itemObj);
				                		}
				                	});
				                	return tmp;
				                }.call(this);
				                //是否进行数据范围过滤
								params["rangeable"] = itemObject.rangeable;
								//清除数据项
								params.items = [];
								params.localItems = [];
							/*如果超过两倍于显示数据量，则使用弹窗*/
							if(itemObject.moreEvent == "dialog"){
								 //扩展不确定参数到基础参数
								$.extend(params,itemObject);
								params[settings.paramNames.page||"queryModel.currentPage"] = 0;
								params[settings.paramNames.rows||"queryModel.showCount"] = -1;/*不分页*/
								settings.onMoreClick.call($this,itemObject,params);
							}else if(itemObject.moreEvent == "append"){/*追加方式*/
								/*更多状态*/
								$(a_elment).removeClass("more_up").addClass("more_down");
								$(a_elment).html("&nbsp;"+messages.upLabelText);
								/*表示是第一次点击当前项目的more*/
								if($(condition_row).find("ul:eq(0)").find("li[name=append]").size()==0){
									params[settings.paramNames.page||"queryModel.currentPage"] = '1';
									params[settings.paramNames.rows||"queryModel.showCount"] = settings.maxSize;/*查询最大数据*/
									params[settings.paramNames.minNum||"minNum"] = showSize;
									params[settings.paramNames.maxNum||"maxNum"] = settings.maxSize;
									jQuery.ajaxSetup({async:false});
									/*远程获取数据*/
									$.getJSON(itemObject.url,params, function(data){
										var items = [];
										if($.defined(data)){
											/*表示服务端防护的是[{}]格式数据*/
											if($.isArray(data)){
												$.each(data||[],function(i,rowObj){
													var key = rowObj[mapper.key],text = rowObj[mapper.text];
													var itemObj = $this.buildItem(itemObject,key,text,rowObj);
													itemObj["moreEvent"] = "append";
													items.push(itemObj);
												});
											}else{
												$.each(data[settings.jsonReader.root]||[],function(i,rowObj){
													var key = rowObj[mapper.key],text = rowObj[mapper.text];
													var itemObj = $this.buildItem(itemObject,key,text,rowObj);
													itemObj["moreEvent"] = "append";
													items.push(itemObj);
												});
											}
											$(condition_row).find("ul:eq(0)").empty().append(buildItemsHtml(itemObject,items));
											/*对当前条件区域绑定事件*/
											bindEvent(condition_row,itemObject );
										}
										/*是否隐藏*/
										if( !$.toBoolean(itemObject.hidden) && ($.founded(items) || $(condition_row).find("ul:eq(0)").find("li").size() > 0 )){
											/*显示*/
											$(condition_row).show();
										}else{
											/*隐藏*/
											$(condition_row).hide();
										}
									});
									jQuery.ajaxSetup({async:true});
								}else{
									$(condition_row).find("ul:eq(0)").find("li[name='append']").show();
								}
							}
						}else{
							/*更多状态*/
							$(a_elment).removeClass("more_up").addClass("more_down");
							$(a_elment).html("&nbsp;"+messages.upLabelText);
							/*表示是第一次点击当前项目的more*/
							if($(condition_row).find("ul:eq(0)").find("li[name='append']").size()==0){
								if($.defined(itemObject.options)&&$.isArray(itemObject.options)){
									/*固定选择项*/
									var items = [];
									$.each(itemObject.options,function(index,itemData){
										if(index>=showSize){
											$.each(itemData,function(key,value){
												var itemObj = $this.buildItem(itemObject,key,value);
												itemObj["moreEvent"] = "append";
												items.push(itemObj);
												return false;
											});
										}
									});
									$(condition_row).find("ul:eq(0)").empty().append(buildItemsHtml(itemObject,items));
								}else if($.defined(itemObject.range) && $.toBoolean(itemObject.range) ){
									/*范围选项*/
									var items = [];
									$.each(itemObject.rangeItems||[],function(index,itemData){
										if(index >= showSize){
											var itemObj = $this.buildItem(itemObject,(itemData[0]+"||"+itemData[1]),(itemData[0]+"~"+itemData[1]));
											itemObj["moreEvent"] = "append";
											items.push(itemObj);
										}
									});
									$(condition_row).find("ul:eq(0)").empty().append(buildItemsHtml(itemObject,items));
								}
								/*对当前条件区域绑定事件*/
								bindEvent(condition_row,itemObject );
							}else{
								$(condition_row).find("ul:eq(0)").find("li[name='append']").show();
							}
						}
						$(a_elment).parent().prev("div").find("a").each(function(i,a){
							/*绑定选择事件*/
							$(a).unbind().click(function(e){
								e.stopPropagation();
								$(this).addClass("selectedValue");
								var itemObj = getItem(itemObject,$(this).parent().attr("index"));
								$this.addSelectedItem(itemObject,itemObj);
								$(selected_attr).show();
							});
						});
					}else{
						/*收起状态*/
						$(condition_row).find("ul:eq(0)").find("li[name=append]").hide();
						/*收起状态*/
						$(a_elment).removeClass("more_down").addClass("more_up");
						$(a_elment).html("&nbsp;"+messages.downLabelText);
					}
				});
			};

			/*加载远程数据*/
			function loadRemoteData(remoteURL,condition_row,itemObject,paramMap,callBack){
				//触发此函数绑定的元素
				var triggerElement 	= this;
				var mapper 			= itemObject.mapper; 
				var showSize 		= itemObject.showSize||settings.showSize;
				var params 			= {};
					params[settings.paramNames.page||"queryModel.currentPage"] = "1";
					params[settings.paramNames.rows||"queryModel.showCount"] = showSize;
					params[settings.paramNames.minNum||"minNum"] = 0;
					params[settings.paramNames.maxNum||"maxNum"] = showSize;
					/*排序方式 asc , desc*/
					params[settings.paramNames.order||"queryModel.sortOrder"] = itemObject.order;
					/*排序字段 */
					params[settings.paramNames.sort||"queryModel.sortName"] = itemObject.sort;
					//是否进行数据范围过滤
					params["rangeable"] = itemObject.rangeable;
				//扩展级联参数到基础参数
				$.extend(true , params,itemObject.data||{},paramMap);
				
				itemObject.more = false;
				itemObject.items = [];
				itemObject.localItems = [];
				
				/*远程获取数据*/
				return $.getJSON(remoteURL,params, function(data){
					var items = [];
					if($.defined(data)){
						/*记录数*/
						var records = 0;
						/*表示服务端防护的是[{}]格式数据*/
						if($.isArray(data)){
							/*记录数*/
							records = data.length;
							$.each(data||[],function(i,rowObj){
								var key = rowObj[mapper.key],text = rowObj[mapper.text];
								itemObject.localItems.push($this.buildItem(itemObject,key,text,rowObj));
							});
							/*追加方式*/
							itemObject.moreEvent = "append";
							/*数据已在本地*/
							itemObject.local = true;
						}else{
							
							$.each(data[settings.jsonReader.root],function(i,rowObj){
								var key = rowObj[mapper.key],text = rowObj[mapper.text];
								itemObject.localItems.push($this.buildItem(itemObject,key,text,rowObj));
							});
							/*记录数*/
							records = data[settings.jsonReader.records] || itemObject.localItems.length;
							/*如果超过3倍于显示数据量，则使用弹窗*/
							if(records>(showSize*3)){
								itemObject.moreEvent = "dialog";
							}else{
								/*追加方式*/
								itemObject.moreEvent = "append";
							}
						}
						var condition_ul = $(condition_row).find("ul:eq(0)");
						if($.defined(itemObject.select)&&$.toBoolean(itemObject.select)){
							/*下拉框*/
							$(condition_ul).empty().append(templates.select.call(this,itemObject.localItems,itemObject));
							/*初始化已选对象*/
							setSelectSelectedItem(itemObject,$(condition_ul).find("select.select-xs"),condition_ul);
						}else if($.defined(itemObject.dualSelect)&&$.toBoolean(itemObject.dualSelect)){
							/*双下拉框*/
							var selects = $(condition_ul).find("select.select-xs");
							var html = ['<option value=""  index="'+(itemObject.index||"")+'_all" >' + messages.allSelectText + '</option>'];
							$.each(itemObject.localItems||[], function(i,item){
								html.push('<option value="'+ item.key +'" ' +  (item.selected?' selected="selected" ':"")  +'>' + item.text + '</option>');
							});
							
							if($.founded(itemObject.url)){
								$(selects[0]).empty().append(html.join(""));
								$(selects[1]).empty().append(html.join(""));
							}else{
								if(remoteURL == itemObject.leftURL && $(triggerElement).is(selects[0])){
									$(selects[0]).empty().append(html.join(""));
								}else if(remoteURL == itemObject.rightURL && $(triggerElement).is(selects[1])){
									$(selects[1]).empty().append(html.join(""));
								}
							}
							/*初始化已选对象*/
							setDualSelectSelectedItem(itemObject,selects[0],selects[1],condition_ul);
						}else if($.defined(itemObject.radio)&&$.toBoolean(itemObject.radio)){
							/*单选框*/
							$(condition_ul).empty().append(templates.radio.call(this,itemObject.index,itemObject.localItems));
							/*初始化已选对象*/
							setRadioCheckedItem(itemObject,condition_ul);
						}else if($.defined(itemObject.checkbox)&&$.toBoolean(itemObject.checkbox)){
							/*多选框*/
							$(condition_ul).empty().append(templates.checkbox.call(this,itemObject.index,itemObject.localItems));
							/*初始化已选对象*/
							setCheckboxCheckedItem(itemObject,condition_ul);
						}else{
							/*仅显示限制数内的内容*/
							$.each(itemObject.localItems,function(index,itemObj){
								if(index<showSize){
									items.push(itemObj);
								}
							});
							$(condition_ul).empty().append(buildItemsHtml(itemObject,items));
							$(condition_row).find("div.more").remove();
							/*有比定义可显示数据更多的数据  */
							if(records > showSize){
								/*是否有更多元素 */
								itemObject.more = true;
								$(condition_row).find("div.form-group").append(templates.more.call($this, itemObject));
							}
						}
						
						/*对当前条件区域绑定事件*/
						bindEvent(condition_row,itemObject );
						if(jQuery.isFunction(callBack)){
							callBack.call($this);
						}
					}
					/*是否隐藏*/
					if( !$.toBoolean(itemObject.hidden) && ($.founded(items) || $(condition_row).find("ul:eq(0)").find("li").size() > 0 )){
						/*显示*/
						$(condition_row).show();
					}else{
						/*隐藏*/
						$(condition_row).hide();
					}
				});
			};
			
			//构建高级查询基础UI框架
			$(element).empty().append(buildContainer(settings));
			//模糊查询区域
			var search_filter = $(element).find("div.search-filter:eq(0)");
			//查询按钮区域
			var query_row = $(element).find("div.query-item:eq(0)");
			//已选添加区域
			var selected_attr = $(element).find("div.selected-attr:eq(0)");
			//条件整体区域
			var condition_item = $(element).find("div.condition-item:eq(0)");
			
			
			//定义延时函数，从而在元素加载完成后，再进行事件的绑定
			$.when($.Deferred(function(dtd1){
				
				/*有模糊查询栏*/
				if(search_filter.size()>0 && $.founded(settings.filters)){
					
					/*模糊查询项*/
					$.each(settings.filters||[],function(index,itemObject){
						/*循环当前查询项*/
						$.each(itemObject,function(key,value){
							$(search_filter).find("div.types").append(templates.filterRadio(key, value));
							return false;/*只有一个；终止循环*/
						});
					});
					
				}
			
				/*有高级查询条件项*/
				if(condition_item.size()>0){
					//初始化清空
					$(condition_item).empty();
					//延时对象集合
					var deferreds = [];
					/*生成高级查询*/
					$.each(settings.model,function(index,itemObject){
						//扩展初始参数
						$.extend(true,itemObject,{
							//追加方式
							"moreEvent"	: $.defined(itemObject.moreEvent) ? itemObject.moreEvent : "append",
							//是否条件多选
							"multiple"	: $.defined(itemObject.multiple) ? $.toBoolean(itemObject.multiple) : true,
							//是否进行数据范围过滤
							"rangeable"	: $.defined(itemObject.rangeable) ? $.toBoolean(itemObject.rangeable) : true,
							//条件项
							"items"		: [],
							//默认条件项
							"defaults"	: itemObject.defaults||[],
							//默认显示条件项总数
							"showSize"	: itemObject.showSize||settings.showSize,
							/*排序方式 asc , desc*/
							"order"		: ($.type(itemObject.order) == "string" ? itemObject.order : ""),
							/*排序字段 */	
							"sort"		: ($.type(itemObject.sort) == "string" ? itemObject.sort : "")
							
						});
						
						var showSize = itemObject.showSize;
						
						var html = new Array();
						/*固定选择项*/
						if($.defined(itemObject.options)&&$.isArray(itemObject.options)){
							var items = [];
							$.each(itemObject.options,function(i,itemData){
								$.each(itemData,function(key,value){
									var itemObj = $this.buildItem(itemObject,key,value);
									if(i<showSize){
										items.push(itemObj);
									}
									return false;
								});
							});
							html.push(buildItemsHtml(itemObject,items));
						}else if($.defined(itemObject.range) && $.toBoolean(itemObject.range)){
							if($.founded(itemObject.rangeItems)){
								/*范围选项*/
								var items = [];
								$.each(itemObject.rangeItems||[],function(i,itemData){
									var itemObj = $this.buildItem(itemObject,(itemData[0]+"||"+itemData[1]),(itemData[0]+"~"+itemData[1]));
									if(i<showSize){
										items.push(itemObj);
									}
								});
								html.push(buildItemsHtml(itemObject,items));
							}else{
								/*添加可自定义的范围项*/
								html.push(templates.range.call(this,itemObject));
							}
						}else if($.defined(itemObject.fixed)&&$.toBoolean(itemObject.fixed)){
							/*固定值*/
							html.push(templates.fixed.call(this,itemObject.index));
						}else if($.defined(itemObject.select)&&$.toBoolean(itemObject.select)){
							/*下拉框*/
							html.push(templates.select.call(this,itemObject.items,itemObject));
						}else if($.defined(itemObject.dualSelect)&&$.toBoolean(itemObject.dualSelect)){
							/*双下拉框*/
							html.push(templates.dualSelect.call(this,itemObject.index,itemObject.leftItems,itemObject.rightItems));
						}else if($.defined(itemObject.radio)&&$.toBoolean(itemObject.radio)){
							/*单选框*/
							html.push(templates.radio.call(this,itemObject.index,itemObject.items));
						}else if($.defined(itemObject.checkbox)&&$.toBoolean(itemObject.checkbox)){
							/*多选框*/
							html.push(templates.checkbox.call(this,itemObject.index,itemObject.items));
						}
						/*构建一个查询条件组*/
						$(condition_item).append(templates.condition_row.call(this,itemObject,html.join("")));
						
						//单个条件区域集合
						var condition_row = $(condition_item).find("div.condition-row").eq(index);
						/*缓存当前条件区域*/
						settings.condition_rows[itemObject.index] = condition_row;
						
						deferreds.push($.Deferred(function(dtd){
							
							/*url模式*/
							if($.founded(itemObject.url)&&$.founded(itemObject.mapper)){
								loadRemoteData.call(condition_row,itemObject.url,condition_row,itemObject,{},function(){
									addDefaultItem(condition_row,itemObject);
								}).always(function() {
									// 改变deferred对象的执行状态为：已完成
									dtd.resolve(); 
								});
							}else{
								addDefaultItem(condition_row,itemObject);
								/*是否隐藏*/
								if( !$.toBoolean(itemObject.hidden) && ($.founded(itemObject.items) || $(condition_row).find("ul:eq(0)").find("li").size() > 0 )){
									/*显示*/
									$(condition_row).show();
								}else{
									/*隐藏*/
									$(condition_row).hide();
								}
								// 改变deferred对象的执行状态为：已完成
								dtd.resolve(); 
							}
							
							var condition_ul = $(condition_row).find("ul:eq(0)");
							if($.defined(itemObject.select)&&$.toBoolean(itemObject.select)){
								/*初始化已选对象*/
								setSelectSelectedItem(itemObject,$(condition_ul).find("select.select-xs"),condition_ul);
							}else if($.defined(itemObject.dualSelect)&&$.toBoolean(itemObject.dualSelect)){
								/*初始化已选对象*/
								var selects = $(condition_ul).find("select.select-xs");
								setDualSelectSelectedItem(itemObject,selects[0],selects[1],condition_ul);
							}else if($.defined(itemObject.radio)&&$.toBoolean(itemObject.radio)){
								/*初始化已选对象*/
								setRadioCheckedItem(itemObject,condition_ul);
							}else if($.defined(itemObject.checkbox)&&$.toBoolean(itemObject.checkbox)){
								/*初始化已选对象*/
								setCheckboxCheckedItem(itemObject,condition_ul);
							}
							
						})); // 返回promise对象);
						//jQuery提供了deferred.promise()方法。它的作用是，在原来的deferred对象上返回另一个deferred对象，后者只开放与改变执行状态无关的方法（比如done()方法和fail()方法），
						//屏蔽与改变执行状态有关的方法（比如resolve()方法和reject()方法），从而使得执行状态不能被改变
						
					});
					//延时执行完成后执行最终函数
					$.when(deferreds).always(function(){ 
						
						var condition_rows = $(condition_item).find("div.condition-row");
						$(condition_rows).last().find(".form-group").removeAttr("style");
						if($(condition_rows).size()<=2){
							$(condition_rows).last().css({"border-bottom":"solid 1px #a7bdd3"});
						}else{
							//判断 可伸缩retractable = true|yes|1 且 出现伸缩区域需要的最小条件数是否达到
							if($.toBoolean(settings.retractable) && $(condition_rows).size() > 2 ){
								$(condition_item).append(templates.condition_retract.call($this));
							}
						}
						
						// 改变deferred对象的执行状态为：已完成
						dtd1.resolve(); 
						
					});
					
				}
				
				
			})).always(function(){ 
				
				/*==========绑定事件======================================*/
				
				/*有模糊查询栏 或 精确查询按钮*/
				if(search_filter.size()>0 || query_row.size()>0){
					/*模糊查询按钮*/
					var buttons =  [
					    {click:function(){settings.onSearchClick.call($this,$this.getConditions(),$this.getMergeConditions());}},
					    {click:function(){ this.resetConditions(); }}
				    ];
					$.each(buttons,function(index,itemObject){
						/*获得模糊查询按钮元素*/
						var button = $(search_filter).find("div.buttons").find("button:eq("+index+")");
						/*循环当前查询项*/
						$.each(itemObject,function(key,func){
							$(button).unbind(key).bind(key,function(e){
								/*调用实际上的事件*/
								func.call($this,e);
							});
						});
						
						/*获得精确查询按钮元素*/
						var button = $(query_row).find("div.buttons").find("button:eq("+index+")");
						/*循环当前查询项*/
						$.each(itemObject,function(key,func){
							$(button).unbind(key).bind(key,function(e){
								/*调用实际上的事件*/
								func.call($this,e);
							});
						});
						
					});
					
				}
				/*有模糊查询栏：组织模糊查询项事件*/
				if(search_filter.size() > 0 && $(search_filter).find('input.filter-input').size() > 0){
					
					$(search_filter).find('input.filter-input').unbind("keydown").bind("keydown", function (e) {
						var event = $.event.fix(e);
						//回车自动查询
						if( event.keyCode == 13){
							//取消浏览器默认行为
							event.preventDefault();
							//调用资产查询代码
							settings.onSearchClick.call($this,$this.getConditions(),$this.getMergeConditions());
						}
						//取消事件冒泡
						event.stopPropagation();
						//阻止剩余的事件处理函数执行并且防止事件冒泡到DOM树上
						event.stopImmediatePropagation();
					});
				}
				
				/*有高级查询项*/
				if(condition_item.size()>0){
					/*生成高级查询*/
					$.each(settings.model,function(i,itemObject){
						/*获得当前高级查询项区域*/
						var condition_row = $(condition_item).find("div.items").eq(i);
						/*先绑定非url模式的事件*/
						if(!$.founded(itemObject.url)){
							bindEvent(condition_row,itemObject );
						}
					});
				}
				
				//有添加伸缩控制
				var condition_retract = $(condition_item).find("div.condition-retract");
				if(condition_retract.size()>0){
					//由于当条件数大于2时，才会出现伸缩区域，这里出现了则表示条件不止2个，且切换函数应该初始是收起，之后是伸展
					var isShow = true;
					$(condition_retract[0]).find("a").click(function(e){
						if(isShow == true){
							isShow = false;
							$(this).text(messages.moreItemBottom);
							//$(condition_retract).removeClass("more-item-up").addClass("more-item-bottom");
							$(this).removeClass("up").addClass("down");
							$(condition_item).find("div.condition-row").each(function(){
								$(this).hide();
							});
						}else{
							isShow = true;
							$(this).text(messages.moreItemUpText);
							//$(condition_retract).removeClass("more-item-bottom").addClass("more-item-up");
							$(this).removeClass("down").addClass("up");
							$(condition_item).find("div.condition-row").each(function(index,condition_row){
								var itemObject = settings.model[index];
								/*显示*/
								if(!$.toBoolean(itemObject.hidden) && ($.founded(itemObject["items"]) || $(condition_row).find("ul:eq(0)").find("li").size() > 0 )){
									$(this).show();
								}
							});
						}
					});
				}
			});
			
			//显示已选条件区域
			if(selected_attr.find("div.selected").size()>0){
				selected_attr.show();
			}
			//显示高级查询区域
			$(element).find("div.searchbox:eq(0)").show();
			
		},
		setDefaults	: function(settings){
			$.extend($.fn.searchBox.defaults, settings );
		}
	}
	
	/* SearchBox PLUGIN DEFINITION  */
	
	$.fn.searchBox = function(option){
		if (typeof option == 'string'){
			var $this = $(this[0]), data = $this.data('searchBox');
			if (!data && option == 'destroy') {return;}
			if (data){
				//处理后的参数
				var args = $.grep( arguments || [], function(n,i){
					return i >= 1;
				});
				//调用函数
				return data[option].apply(data, [].concat(args || []) );
			}
		}else{
			return this.each(function () {
				var $this = $(this), data = $this.data('searchBox');
				if (!data){
					var options = $.extend(true,{}, $.fn.searchBox.defaults, $this.data(), ((typeof option == 'object' && option) ? option : {}));
					$this.data('searchBox', (data = new $.multiui.widget.SearchBox(this, options)));
				}
			});
		}
	};
	
	$.fn.searchBox.defaults = {
		/*JSON数据处理参数*/
		jsonReader		: {      
			root: "items",
			records: "totalResult"
		},
		/*组件进行渲染前的回调函数：如重新加载远程数据并合并到本地数据*/
		beforeRender	: $.noop,
		/*组件渲染出错后的回调函数*/
		errorRender		: $.noop,
		/*组件渲染完成后的回调函数*/
		afterRender		: $.noop, 
		/*其他参数*/
		onSearchClick	:function(paramMap){
			search('tabGrid',paramMap);
		},
		onMoreClick		:function(itemObject,paramMap){
			var $this1 = this;
			if(!itemObject.gridType){
				 throw new Error("model 需要一个 gridType 参数！ ");
			}
			
			/*调用公共数据选择窗口*/
			jQuery.showSelectDialog(itemObject.gridType,$.extend(paramMap,{
				"width":  800,
				"height" : 500,
				"title": itemObject.text+"列表"
			}),function(result_arr,deleted_arr){
				
				/*判断是否选择，且配置了mapper*/
				if(jQuery.founded(result_arr)&&jQuery.founded(itemObject.mapper)){
					var item_arr = [];
					jQuery.each(result_arr||[],function(i,rowObj){
						var key = rowObj.key,text = rowObj.text;
						var itemObj = $this1.buildItem(itemObject,key,text,rowObj);
						item_arr.push(itemObj);
					});
					$this1.addSelectedItem(itemObject,item_arr);
				}
				
				if(jQuery.founded(deleted_arr)){
					$this1.removeSelectedItem(itemObject,deleted_arr);
				}
					
		  	});
		},
		showSize		: 6,
		maxSize			: 100,
		searchFilter	: true,	/*是否需要模糊查询*/
		selectedAttr	: true,	/*是否需要已选条件*/
		retractable		: true, /*是否可伸缩条件区域*/
		autoSearch		: false,/*是否自动查询*/
		filterText		: null, /*模糊查询输入框前的text*/
		filters			: [],
		model			: [],	/*更多查询*/
		paramNames 		: {
			page 	: "queryModel.currentPage",
			rows 	: "queryModel.showCount",
			order 	: "queryModel.sortOrder",
			sort 	: "queryModel.sortName",
			minNum 	: "minNum",
			maxNum 	: "maxNum"
		}
	};
	
	
})(jQuery);