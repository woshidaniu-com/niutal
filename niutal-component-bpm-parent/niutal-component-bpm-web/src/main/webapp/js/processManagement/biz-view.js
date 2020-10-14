//业务对象查看详情
$(function(){
    var bizId = $('#id', '#bizDetailForm').val();
    
    var getStringValues = function(){
	if(arguments && arguments.length > 0){
	    return arguments[0] == null ? '':arguments[0];
	}
    };
    
    var getEnumValues = function(){
	if(arguments && arguments.length > 0){
	    var enumValues = arguments[0];
	    //if(enumValues && typeof(enumValues) == 'string'){
		//enumValues = $.parseJSON(enumValues);
	    //}
	    return enumValues == null ? '': enumValues;
	}
    };
    
    var fieldTypeHandlers = {
	    'string': 	{'getValues': function(){return getStringValues.call(this, arguments[0]);}, 'getType': function(){return '字符';}},
	    'number': 	{'getValues': function(){return getStringValues.call(this, arguments[0]);}, 'getType': function(){return '数字';}},
	    'enum':  	{'getValues': function(){return getEnumValues.call(this, arguments[0]);}, 'getType': function(){return '枚举';}},
    };
    
    var createFieldTr = function(data){
	if(data){
	 var tr = $('<tr></tr>');
	 tr.attr('id', data['id']);
	 tr.append($('<td>'+data['label']+'</td>'));
	 tr.append($('<td>'+ (data['description'] == null ? '':data['description'])+'</td>'));
	 tr.append($('<td>'+fieldTypeHandlers[data['type']]['getType']()+'</td>'));
	 tr.append($('<td>'+fieldTypeHandlers[data['type']]['getValues'](data['values'])+'</td>'));
	 return tr;
	}
    };
    
    // 查询业务对象字段
    if(bizId){
	var url = _path + "/processBiz/" + bizId + "/getProcessBizFields.zf";
	$.getJSON(url, {}, function(data){
	    if(data && data.length > 0){
		var fieldTable = $('#process_biz_field_tab', '#bizDetailForm');
		fieldTable.find('#none-data-tr').hide();
		$.each(data, function(i, n){
		    fieldTable.find('tbody').append(createFieldTr(n));
		});
	    }
	});
    }
    
    
});