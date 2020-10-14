//解析已经选中的数据
var parseSelectedDataForLinkage = function(data , filter){
	if(data == undefined){
		return "";
	}
	var returnVal = {};
	jQuery.each(data , function(k , v){
		if(v == undefined){
			return false;
		}
		var vals = [];
		for ( var i = 0; i < v.length; i++) {
			vals.push( v[i]['columnKeyValue']);
		}
		if(filter!=undefined && filter.length > 0){
			 if(jQuery.inArray(k , filter) > -1){
				 returnVal[k] = vals;
			 }
		}else{
			returnVal[k] = vals;
		}
	});
	return JSON.stringify(returnVal);
}

//解析已经选中的数据
var parseSelectedDataForMoreLink = function(data , filter){
	if(data == undefined){
		return "";
	}
	var returnVal = {};
	jQuery.each(data , function(k , v){
		if(v == undefined){
			return false;
		}
		var vals = [];
		for ( var i = 0; i < v.length; i++) {
			vals.push( v[i]['columnKeyValue']);
		}
		if(filter!=undefined && filter.length > 0){
			 if(jQuery.inArray(k , filter) > -1){
				 returnVal[k] = vals;
			 }
		}
	});
	return JSON.stringify(returnVal);
}

//解析已经选中的数据
var parseAllSelectedData = function(data){
	if(data == undefined){
		return "";
	}
	var returnVal = {};
	jQuery.each(data , function(k , v){
		if(v == undefined){
			return false;
		}
		var vals = [];
		for ( var i = 0; i < v.length; i++) {
			vals.push( v[i]['columnKeyValue']);
		}
		returnVal[k] = vals;
	});
	
	for ( var iterable_element in returnVal) {
		return JSON.stringify(returnVal);
	}
	return "";
}

/**
 * 创建更过选项区域的搜索功能
 * @param searchName
 * @return
 */
var createMoreDataSearchBlock = function(searchName){
	var searchDiv = jQuery('<div>').addClass('more-data-search');
	var searchSpan = jQuery('<span>搜索: </span>');
	var searchInput = jQuery('<input type="text">');
	searchDiv.append(searchSpan).append(searchInput);
	searchInput.keyup(function(){
		var searchvalue = jQuery(this).val().toUpperCase();
		if(jQuery.trim(searchvalue) == ''){
			searchName.find('a').removeClass('search-match').show();
		}else{
			searchName.find('a').hide();
			/*searchName.find('a').filter(function(){
				var target = jQuery(this).text().toUpperCase();
				return target.indexOf(searchvalue) < 0;
			}).hide();*/
			searchName.find('a').filter(function(){
				var target = jQuery(this).text().toUpperCase();
				if(target.indexOf(searchvalue) < 0){
					jQuery(this).hide();
					return false;
				}else{
					return true;
				}
			}).show();
		}
	});
	return searchDiv;
}

//创建点击元素
var createDataItem = function(type , params){
	//高级查询配置id
	var configId = SS.Config.getConfigId();
	//查询字段名
	var searchName = params['searchName'];
	var dd_data_a = jQuery('<a></a>' , {'name': type , 'href': 'javascript:void(0);' , 'value': params.columnKeyValue , 'searchname': searchName});
	dd_data_a.html(params.columnLabelValue);
	
	//判断是否已经被选中
	var selected_data = SS.DataUtil.getSelectedData();
	//要对比的目标选中列表
	var targetArr = selected_data[params.searchName];
	if(targetArr != undefined && targetArr.length > 0){
		jQuery.each(targetArr , function(i , n){
			if(n['columnKeyValue'] == params['columnKeyValue']){
				dd_data_a.addClass('item-selected');
				dd_data_a.attr('select-status' , '1');
				var selected_ref = jQuery('#dl_choice').find('dd[searchname="' + params['searchName'] + '"][value="' + params['columnKeyValue'] + '"]');
				dd_data_a.data('selected_ref' , selected_ref);
				//把创建的点击元素传给已经选中
				selected_ref.data('sourceRef' , dd_data_a);
				return false;
			}
		});
	}
	//判断是否已经被选中
	
	dd_data_a.bind('click', function(){
		//var $a_this = jQuery(this);
		var select_status = jQuery(this).attr('select-status');
		if(!select_status || select_status == '0'){
			//判断是否是单选的
			var radio = params['configObject']['isOnly'];
			if(radio &&  radio == 'yes'){
				jQuery('#dl_choice').find("dd[searchname='"+params['searchName']+"']").find('a').click();
			}
			//判断是否是单选的
			
			jQuery(this).addClass('item-selected');
			jQuery(this).attr('select-status' , '1');
			//创建已选元素
			var select_dd = jQuery('<dd>' , {'id': 'select_' + params['searchName'] , 'searchname': params['searchName'] , 'value': params['columnKeyValue'] , 'label' : params['columnLabelValue']}).appendTo(jQuery('#dl_choice'));
			var select_dd_a = jQuery('<a>' , {'href' : "javascript:void(0);"}).
						append(jQuery('<h5>' , {'text': params['configObject']['searchLabel']})).append(params['columnLabelValue']).append(jQuery('<span class="close-icon" title="删除条件"></span>'));
			select_dd.data('sourceRef' , jQuery(this));
			//这里的代码有问题！！！！！！！！！！！！！！！！ 
			select_dd_a.one('click' , function(){	
				jQuery(this).parent().data('sourceRef').removeClass('item-selected');
				jQuery(this).parent().data('sourceRef').attr('select-status' , '0');
				jQuery(this).parent().remove();
				SS.DataUtil.removeSData(params);
				if(jQuery('#dl_choice').find('dd').length > 0){
					jQuery('#search_selected').css('display', 'block');
				}else {
					jQuery('#search_selected').css('display', 'none');
				}
			});
			select_dd_a.appendTo(select_dd);
			select_dd.appendTo(jQuery('#dl_choice'));
			jQuery(this).data('selected_ref' , select_dd);
			SS.DataUtil.addSData(params);
		}else{
			jQuery(this).removeClass('item-selected');
			jQuery(this).attr('select-status' , '0');
			jQuery(this).data('selected_ref').remove();
			SS.DataUtil.removeSData(params);
		}
		//判断该条件是否需要联动其他字段数据
		var configObject = SS.DataUtil.getConfigObject(searchName);
		if(configObject['linkageList'] != undefined && configObject['linkageList'].length > 0){
			//MASK 
			for ( var i = 0; i < configObject['linkageList'].length; i++) {
				var searchname = configObject['linkageList'][i];
				SS.DataUtil.getHoldEl(searchname).mask('' , 0);
			}
			//联动条件查询
			var linkage_url = SS.Config.getContextPath() + '/zfxg/search/superSearch_getLinkageData.html?timestamp=' + new Date().getTime();
			//处理已经选中的数据传给联动查询使用
			var newData = parseSelectedDataForLinkage(SS.DataUtil.getSelectedData());
			var linkage_data = {'searchSign': configId, 'linkageName': searchName, 'linkageValue': newData};
			var linkage_ck = function(data){
				// 根据返回的数据重新构建相应HTML代码块
				jQuery.each(data , function(index , _searchdata){
					var _searchName = _searchdata['searchName'];
					var _dataList = _searchdata['sourceDataItemList'];
					var _$dd_data = jQuery('#dd-data-' + _searchName);
					// 删除之前的数据，并添加新数据
					SS.DataUtil.getHoldEl(_searchName).removeData('data').data('data' , {initial_data : _dataList , 
																 total_count : _searchdata['totalCount'] , 
																 initial_count: _searchdata['currentCount'] , 
																 has_more : _searchdata['hasMore'] ,
																 more_data_status: 'init'});
					//删除老的节点
					_$dd_data.empty();
					SS.DataUtil.getHoldEl(_searchName).find('div[searchname="' + _searchName + '"]').remove();
					//添加新的节点
					var _dd_data_ul = createShowBlock({intial_label: SS.DataUtil.getConfigObject(_searchName)['useInitial']}, _dataList , _searchName).appendTo(_$dd_data);
					//删除老节点
					_$dd_data.find('a[name="a_more"]').remove();
					//添加新的节点
					if(_searchdata['hasMore']){
						jQuery('<a name="a_more" href="javascript:void(0);" class="more_down">更多</a>')
								.bind('click' , {searchName : _searchName , dd_data_ul: _dd_data_ul},moreLinkClickHandler).appendTo(_$dd_data);
					}
				});
				//UNMASK 
				for ( var i = 0; i < configObject['linkageList'].length; i++) {
					var searchname = configObject['linkageList'][i];
					SS.DataUtil.getHoldEl(searchname).unmask();
				}
			};
			jQuery.post(linkage_url , linkage_data , linkage_ck , 'json');
			//联动条件查询
		}
		
		if(jQuery('#dl_choice').find('dd').length > 0){
			jQuery('#search_selected').css('display', 'block');
		}else {
			jQuery('#search_selected').css('display', 'none');
		}
	});
	return dd_data_a;
};

//创建初始化加载的选项区域
var createShowBlock = function(options, dataList , searchName){
	var default_option = {'intial_label': 'no'};
	jQuery.extend(default_option , options);
	//判断数据列表是否为空，如果为空就创建一个无数据提示
	if(dataList == undefined || dataList.length == 0){
		return createEmptyInfo();
	}
	//判断数据列表是否为空，如果为空就创建一个无数据提示
	
	var dd_data_ul = jQuery('<ul>');
	if(default_option.intial_label == 'yes'){
		var firstInitialLabel , first_dd_data_li;
		jQuery.each(dataList , function(index , value){
			if(!firstInitialLabel || value['columnInitialValue'] != firstInitialLabel){
				firstInitialLabel = value['columnInitialValue'];
				first_dd_data_li =  jQuery('<li></li>').attr('name' , 'li_show').attr('data-inital-label' , firstInitialLabel).css({'dispaly' : 'inline'})
										.prepend(jQuery('<i class="data-inital-label">' +firstInitialLabel + '</i>').css('float' , 'left')).appendTo(dd_data_ul);
			}
			
			var params = {searchName:searchName, columnInitialValue: value['columnInitialValue'], 
							columnKeyValue: value['columnKeyValue'], 
							columnLabelValue: value['columnLabelValue'], 
							configObject: SS.DataUtil.getConfigObject(searchName)};
			createDataItem('a_show' , params).appendTo(first_dd_data_li);
		});
	}else if(default_option.intial_label == 'no'){
			jQuery.each(dataList , function(index , value){
				var dd_data_li = jQuery('<li>').attr('name' , 'li_show').css({'dispaly' : 'inline'});
				dd_data_li.attr('data-inital-label' , value['columnInitialValue']);
				var params = {searchName:searchName, columnInitialValue: value['columnInitialValue'], 
						columnKeyValue: value['columnKeyValue'], 
						columnLabelValue: value['columnLabelValue'], 
						configObject: SS.DataUtil.getConfigObject(searchName)};
				dd_data_li.append(createDataItem('a_show' , params));
				dd_data_ul.append(dd_data_li);
			});
		}
		return dd_data_ul;
	};
	
	/**
	 * 首字母点击事件
	 */
	function initalLabelLoadDataHandler(event){
		var $this = event.data.ref;
		var $searchName = event.data.searchName;
		var $dataObject = SS.DataUtil.getDataObject($searchName);
		var $initialLabel = event.data.initialLabel;
		var searchSign , searchName , initalLabel;
		searchSign = SS.Config.getConfigId();//$this.attr('searchsign');
		searchName = $searchName;//$this.attr('searchname');
		if($initialLabel == '0-9'){
			initalLabel = 'NUMBERIC';
		}else if($initialLabel == '其他'){
			initalLabel = 'OTHER';
		}else{
			initalLabel = $initialLabel;
		}
		var configObject = SS.DataUtil.getConfigObject(searchName);
		var more_data_div = jQuery('#more-data-' + searchName);
		$this.addClass('selected').siblings().removeClass('selected');
		// 如果已经加载过，直接显示
		if($dataObject['more_data'][initalLabel] && $dataObject['more_data'][initalLabel]['load_status'] == 'loaded'){
			more_data_div.removeClass('more-data-max');
			var data_list = $dataObject['more_data'][initalLabel]['data_list'];
			more_data_div.empty();
			if(data_list == undefined || data_list.length == 0){
				more_data_div.append(createEmptyInfo());
			}else{
				jQuery.each(data_list, function(index , item){
					var params = {searchName:searchName, columnInitialValue: item['columnInitialValue'], 
							columnKeyValue: item['columnKeyValue'], 
							columnLabelValue: item['columnLabelValue'], 
							configObject: SS.DataUtil.getConfigObject(searchName)};
					var more_data_a = createDataItem('a_more_'+item['columnInitialValue'] , params);
					more_data_div.append(more_data_a);	
				});
				//如果div高度过长,为其生成滚动条
				if(more_data_div.height() > 170){
					more_data_div.addClass('more-data-max');
					var moreDataSearch = createMoreDataSearchBlock(more_data_div);
					more_data_div.prepend(moreDataSearch);
				}
			}
		}else if($dataObject['more_data'][initalLabel] == undefined || $dataObject['more_data'][initalLabel]['load_status'] == 'init'){
			more_data_div.mask('' , 0);
			var newData = parseSelectedDataForMoreLink(SS.DataUtil.getSelectedData() , configObject['byLinkageList']);
			jQuery.getJSON(
					SS.Config.getContextPath() + '/zfxg/search/superSearch_getMoreDataWithIntialLabel.html?timestamp=' + new Date().getTime(),
					{searchSign :searchSign, searchName: searchName, initalLabel: initalLabel, linkageValue: newData},
					function(data){
						var dataArr = data['sourceDataItemList'];
						if(dataArr == undefined || dataArr.length == 0){
							more_data_div.empty().append(createEmptyInfo());
						}else{
							more_data_div.empty();
							jQuery.each(dataArr, function(index , item){
								var params = {searchName:searchName, columnInitialValue: item['columnInitialValue'], 
										columnKeyValue: item['columnKeyValue'], 
										columnLabelValue: item['columnLabelValue'], 
										configObject: SS.DataUtil.getConfigObject(searchName)};
								var more_data_a = createDataItem('a_more_'+item['columnInitialValue'] , params);
								more_data_div.append(more_data_a);	
							});
							more_data_div.removeClass('more-data-max');
							//如果div高度过长,为其生成滚动条
							if(more_data_div.height() > 170){
								more_data_div.addClass('more-data-max');
								var moreDataSearch = createMoreDataSearchBlock(more_data_div);
								more_data_div.prepend(moreDataSearch);
							}
						}
						$dataObject['more_data'][initalLabel] = {data_list:dataArr, load_status: 'loaded'};
						more_data_div.unmask();
					}
			);
		}
		
	}	
	
//创建更多选项区域
var moreLinkClickHandler = function(event){
	//数据缓存对象
	var searchName = event.data.searchName;
	//数据对象
	var dataObject = SS.DataUtil.getDataObject(searchName);
	//配置对象
	var configObject = SS.DataUtil.getConfigObject(searchName);
	//加载状态
	var more_data_status = dataObject['more_data_status'];
	//selfref
	var $this = jQuery(this);
	if($this.attr('class') == 'more_up'){
		//如果不是字母搜,則直接隱藏數據
		if(configObject['useInitial'] == 'no'){
			jQuery("li[name='li_more']" , SS.DataUtil.getHoldEl(searchName)).hide();
		}else{
		//如果是字母搜,顯示DIV
			jQuery('.more_block' , SS.DataUtil.getHoldEl(searchName)).hide();
		}
		$this.attr('class' , 'more_down').html('更多');
	}else{
		if(more_data_status == 'init'){
			
			//联动数据
			//处理已经选中的数据传给联动查询使用
			var newData = parseSelectedDataForMoreLink(SS.DataUtil.getSelectedData() , configObject['byLinkageList']);
			
			if(configObject['useInitial'] == 'no'){
				//加载数据
				SS.DataUtil.getHoldEl(searchName).mask("" , 0);
				jQuery.getJSON(SS.Config.getContextPath() + '/zfxg/search/superSearch_getMoreData.html?timestamp=' + new Date().getTime(),
						{searchSign :SS.Config.getConfigId(), searchName: searchName, 'linkageValue': newData},
						function(data){
							var dataList = data['sourceDataItemList'];
							jQuery.each(dataList , function(index , value){
								var params = {searchName:searchName, columnInitialValue: value['columnInitialValue'], 
										columnKeyValue: value['columnKeyValue'], 
										columnLabelValue: value['columnLabelValue'], 
										configObject: SS.DataUtil.getConfigObject(searchName)};
								var dd_data_a = createDataItem('a_more' , params);
								var dd_data_more_li = jQuery('<li></li>').attr('name' , 'li_more').css({'dispaly' : 'inline'}).append(dd_data_a);
								event.data.dd_data_ul.append(dd_data_more_li);
							});
							SS.DataUtil.getHoldEl(searchName).unmask();
						});
			}else{
				createMoreBlock(SS.Config.getConfigId(), searchName , newData);
			}
			dataObject['more_data_status'] = 'loaded';
		}else if(more_data_status == 'loaded'){
			//如果不是字母搜,則直接顯示數據
			if(configObject['useInitial'] == 'no'){
				jQuery("li[name='li_more']" , SS.DataUtil.getHoldEl(searchName)).show();
			}else{
				jQuery('.more_block' , SS.DataUtil.getHoldEl(searchName)).show();
			}
		}
		$this.attr('class' , 'more_up').html('收起');
	}
};


/**
 * 創建更多選擇框 首字母搜索模式
 */
function createMoreBlock(searchSign , searchName , linkageValue){
	//用于存储更多数据的对象
	var more_data = {};
	//查询更多数据
	SS.DataUtil.getHoldEl(searchName).mask("" , 0);
	jQuery.post(
			SS.Config.getContextPath() + '/zfxg/search/superSearch_getInitalLabelCountData.html?timestamp=' + new Date().getTime(),
			{searchSign :searchSign, searchName: searchName, 'linkageValue': linkageValue},
			function(data){
				var more = jQuery('<div></div>',{'searchname' : searchName}).addClass('more_block');
				var more_ul = jQuery('<ul></ul>').addClass('more_block_ul');
				more.append(more_ul);
				var countdata = data['initialLabelCount'];
				jQuery.each(countdata , function(index , item){
					var columnInitialLabelCount = item['columnInitialLabelCount'];
					var columnInitialValue = item['columnInitialValue'];
					var $columnInitialValue = (columnInitialValue == null || jQuery.trim(columnInitialValue) == '') ? '其他' : columnInitialValue;
					var more_ul_li = jQuery('<li></li>').html($columnInitialValue);
					more_ul_li.attr('data-load-status' , 'init');
					more_ul_li.addClass(index == 0 ? 'li_first' : '').addClass(index == countdata.length-1 ? 'li_last' : '');
					more_ul_li.attr({'labelvalue':columnInitialValue , 'count': columnInitialLabelCount, "searchsign": searchSign , "searchname" : searchName});
					//注册点击事件
					more_ul_li.bind('click' , {ref : more_ul_li , searchName: searchName , initialLabel: $columnInitialValue} , initalLabelLoadDataHandler);
					more_ul.append(more_ul_li);
				});
				//首次加载的数据
				var firstmoredata = data['moreData'];
				var initialLabelValue = data['initialLabelValue'];
				
				//存储加载的数据/////////////
				more_data_obj = {'load_status' : 'loaded' , 'data_list' : firstmoredata['sourceDataItemList']};
				more_data[initialLabelValue] = more_data_obj;
				SS.DataUtil.getDataObject(searchName)['more_data'] = more_data;
				//存储加载的数据/////////////
				
				var $target_li = jQuery("li[labelvalue='"+ initialLabelValue + "']" , more);
				$target_li.attr('data-load-status' , 'loaded');
				$target_li.addClass('selected');
				var more_div = jQuery('<div>' , {id : 'more-data-' + searchName}).addClass('more-data').appendTo(more);
				jQuery.each(firstmoredata['sourceDataItemList'] , function(index , item){
					var params = {searchName:searchName, columnInitialValue: item['columnInitialValue'], 
							columnKeyValue: item['columnKeyValue'], 
							columnLabelValue: item['columnLabelValue'], 
							configObject: SS.DataUtil.getConfigObject(searchName)};
					var more_data_a = createDataItem('a_more_'+ initialLabelValue , params);
					more_div.append(more_data_a);	
				});
				
				jQuery('#dd-data-' + searchName).after(more);
				//如果div高度过长,为其生成滚动条
				more_div.removeClass('more-data-max');
				if(more_div.height() > 170){
					more_div.addClass('more-data-max');
					var moreDataSearch = createMoreDataSearchBlock(more_div);
					more_div.prepend(moreDataSearch);
				}
				SS.DataUtil.getHoldEl(searchName).unmask();
			} , 'json');	
};