jQuery(function($) {

	var yhm = '';var jsdm = '';var itemType='';
	
	function initGroupItem(groupId){
		/**权限组item*/
		$('#data-rule-group-item').empty();
		if($('#'+groupId).parent().attr('hasItem') != undefined){
			$.post('itemsByRelation.zf',{groupId:groupId,yhm:yhm,jsdm:jsdm,type:itemType},function(data){
				if(data){
					var nav = [];var content = [];var itemHas = '';
					nav.push('<ul class="nav nav-tabs"><li class="tabfirst"><a href="#" data-toggle="tab" >全部</a></li>');
					content.push('<div class="tab-content">');
					var n = 0;
					$.each(data,function(k,item){
						if(n == 0){
							nav.push('<li class="tabitem active"><a href="#gr-'+k+'" data-toggle="tab">'+k+'</a></li>');
							content.push('<div id="gr-'+k+'" class="tab-pane active in">');
						}else{
							nav.push('<li class="tabitem"><a href="#gr-'+k+'" data-toggle="tab">'+k+'</a></li>');
							content.push('<div id="gr-'+k+'" class="tab-pane">');
						}
						n ++;
						$.each(item,function(i,obj){
							if(obj['has'] > 0){itemHas = 'checked=""';}else{itemHas = '';}
							content.push('<div class="groupitem" >');
							content.push('<input class="default-checkbox primary-checkbox" type="checkbox" groupId="'+obj['groupId']+'" id="groupitem-'+obj['itemKey']+'" '+itemHas+' />');
							content.push('<label for="groupitem-'+obj['itemKey']+'">'+obj['itemName']+'</label>');
							content.push('</div>');
						})
						content.push('</div>');
					});
					nav.push('</ul>');
					$('#data-rule-group-item').append(nav.join('')+content.join(''));
					$('.tabfirst').click(function(){
						$('.tab-content').find('div').removeClass('tab-pane');
					});
					$('.tabitem').click(function(){
						$('.tab-content').find('div').addClass('tab-pane');
					});
					/**保存权限组选项*/
					$('.groupitem').change(function(e){
						var gid = $(this).find('input').attr('groupId');
						var itemValues = "'";
						$.each($('.tab-content').find('input[type=checkbox]:checked'),function(i,o){
							var iv = $(o).attr('id').split('-')[1];
							if(itemValues == "'"){
								itemValues += iv;
							}else{
								itemValues += "','" + iv;
							}
						});
						itemValues += "'";
						$.post('saveItemValues.zf',{groupId:gid,yhm:yhm,jsdm:jsdm,itemValues:itemValues,type:itemType},function(data){
							if(data.status == 'error'){
						    	$.error(data.message);
						    }
						});
					});
				}
			});
		}
	}
	
	$('.treeitem').click(function(){
		$('.treeitem').removeClass('active');
		$(this).addClass('active');
		$('#data-rule-group-item').empty();
		var type = $(this).attr('item-type');
		var data = $(this).attr('item-data');
		if(type=='user'){yhm = data;itemType='user';jsdm = $(this).parent().parent().parent().find('span[item-type="role"]').attr('item-data');}
		else if(type=='role'){jsdm = data;yhm='';itemType='role';}
		
		$.post('listByType.zf',{groupType:type,yhm:yhm,jsdm:jsdm},function(data){
			if(data){
				
				var html = [];var has = '';var hasItem = '';
//				var ruleGroupId = '';
				$.each(data,function(i,item){
//					if(i == 0){ruleGroupId = item['groupId'];}
					if(item['has'] > 0){has = 'checked=""';}else{has = '';}
					if(item['selectItem'] != undefined && item['selectItem'] != null && item['selectItem'] != ''){hasItem = 'hasItem="1"';}else{hasItem = 'hasItem="0"'}
					html.push('<div class="ruleitem" '+hasItem+'>');
					html.push('<input class="default-checkbox primary-checkbox" name="'+item['groupCode']+'" type="checkbox" id="'+item['groupId']+'" '+has+' />');
					html.push('<label for="'+item['groupId']+'">'+item['groupName']+'</label>');
					if(item['selectItem'] != undefined && item['selectItem'] != null && item['selectItem'] != ''){
						html.push('<label class="show-item">【查看子选项】</label>');
					}
					html.push('</div>');
				});
				$('#data-rule-group').empty().append(html.join(''));
				
//				initGroupItem(ruleGroupId);
				
				$('.show-item').click(function(){
					initGroupItem($(this).parent().find('input').attr('id'));
				});
				
				$('.ruleitem').change(function(e){
					var groupId = $(this).find('input').attr('id');
					var hasItem = $(this).attr('hasItem');
					var oper = 'del';
					if($(this).find('input').attr('checked') != undefined){
						oper = 'add';
					}
					var groupCode = $(this).find('input').attr('name');
					var repetitiveItem = $('#data-rule-group').find('input[name="'+groupCode+'"]:checked');
					/**检查groupCode 同一Code选项只能保存一个*/
					if(oper == 'add' && repetitiveItem.length > 1){
						$(this).find('input').removeAttr('checked');
						$.each(repetitiveItem,function(i,ri){
							if(ri['id'] != groupId){
								$.alert('配置未保存！与【'+$(ri).next().text()+'】重复');
							}
						});
						return;
					}
					/**保存配置*/
					$.post('saveRuleGroupRelation.zf',{oper:oper,groupId:groupId,yhm:yhm,jsdm:jsdm,type:itemType},function(data){
						if(data.status == 'error'){
					    	$.error(data.message);
					    }
						if(hasItem == '1'){initGroupItem(groupId);}
					});
				});
    		}
    	});
	});

});