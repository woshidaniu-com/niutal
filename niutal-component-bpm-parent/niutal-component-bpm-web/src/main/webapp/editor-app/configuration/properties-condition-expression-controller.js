/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

/*
 * Condition expression
 */

var KisBpmConditionExpressionCtrl = [ '$scope', '$q', '$modal', '$translate' ,function($scope, $q ,$modal, $translate) {
    
    var initialDefer = $q.defer();
    
    //Config for the field type operator
    var EQUALS_Promise = $translate('PROPERTY.SEQUENCEFLOW.CONDITION.VARIABLE.EQUALS');
    var NOTEQUALS_Promise = $translate('PROPERTY.SEQUENCEFLOW.CONDITION.VARIABLE.NOTEQUALS');
    var LESSEQUALSTHAN_Promise = $translate('PROPERTY.SEQUENCEFLOW.CONDITION.VARIABLE.LESSEQUALSTHAN');
    var LESSTHAN_Promise = $translate('PROPERTY.SEQUENCEFLOW.CONDITION.VARIABLE.LESSTHAN');
    var GREATEREQUALSTHAN_Promise = $translate('PROPERTY.SEQUENCEFLOW.CONDITION.VARIABLE.GREATEREQUALSTHAN');
    var GREATERTHAN_Promise = $translate('PROPERTY.SEQUENCEFLOW.CONDITION.VARIABLE.GREATERTHAN');
    var IN_Promise = $translate('PROPERTY.SEQUENCEFLOW.CONDITION.VARIABLE.IN');
    
    $q.all([EQUALS_Promise,		//==
	    NOTEQUALS_Promise,		//!=
	    LESSEQUALSTHAN_Promise,	//<=
	    LESSTHAN_Promise,		//<
	    GREATEREQUALSTHAN_Promise,	//>=
	    GREATERTHAN_Promise,		//>
	    IN_Promise			//in
	    ]).then(function(results){
	 $scope.fieldOperatorDef = {
		    'number':[
			{'key':'<', 'value':results[3]},
			{'key':'<=', 'value':results[2]},
			{'key':'==', 'value':results[0]},
			{'key':'>=', 'value':results[4]},
			{'key':'>', 'value':results[5]},
			{'key':'!=', 'value':results[1]}
			],
		    'string':[
			{'key':'==', 'value':results[0]},
			{'key':'!=', 'value':results[1]}
			],
		    'enum':[
			{'key':'IN', 'value':results[6]}
		    ]
	    };
	 $scope.typeMapper = {
		 'number' : 'number',
		 'string' : 'text'
	 };
	 initialDefer.resolve();
    });
    
    
    initialDefer.promise.then(function(){
	  // Config for the modal window
	    var opts = {
	        template:  'editor-app/configuration/properties/condition-expression-popup.html?version=' + Date.now(),
	        scope: $scope
	    };

	    // Open the dialog
	    $modal(opts);
    });
  
}];

var KisBpmConditionExpressionPopupCtrl = [ '$scope', '$q', '$timeout','$translate', '$http', function($scope, $q, $timeout, $translate, $http) {
    //bizFieldData
    var bizFieldData = $scope.bizFieldData;
    // Put json representing condition on scope
    
    $scope.conditionExpression = {'value': '', 'type': 'field', 'bizFieldCondtions':[]};
    
    if($scope.property.value !== undefined && angular.isString($scope.property.value) && $scope.property.value.length > 0){
	$scope.conditionExpression.value = $scope.property.value;
	$scope.conditionExpression.type = 'custom';
    }else if($scope.property.value !== undefined && angular.isObject($scope.property.value)){
	if($scope.property.value.evaluateExpression !== undefined){
	    $scope.conditionExpression.value = $scope.property.value.evaluateExpression;
	}
	if($scope.property.value.type !== undefined){
	    $scope.conditionExpression.type = $scope.property.value.type;
	}
	if($scope.property.value.bizFieldCondtions !== undefined){
	    $scope.conditionExpression.bizFieldCondtions = angular.copy($scope.property.value.bizFieldCondtions);
	}
    }
    
    //console.debug($scope.property.value.expression);
    
    $scope.translationsRetrieved = false;
    
    $scope.selectedBizFieldConditions = [];
    
    $scope.bizFieldConditions = $scope.conditionExpression['bizFieldCondtions'];
    
    $scope.operators = [];
    
    $scope.labels = {};
    
    var fieldPromise = $translate('PROPERTY.SEQUENCEFLOW.CONDITION.TABLE.COL.FILED');
    var operatorPromise = $translate('PROPERTY.SEQUENCEFLOW.CONDITION.TABLE.COL.OPERATOR');
    var valuePromise = $translate('PROPERTY.SEQUENCEFLOW.CONDITION.TABLE.COL.VALUE');
    
    $q.all([fieldPromise, operatorPromise, valuePromise]).then(function(results) { 
    	$scope.labels.fieldLabel = results[0];
        $scope.labels.operatorLabel = results[1];
        $scope.labels.valueLabel = results[2];
        $scope.translationsRetrieved = true;
        //初始化表格
    	// Config for grid
        $scope.gridOptions = {
            data: 'bizFieldConditions',
            enableRowReordering: false,
            enableSorting: false,
            headerRowHeight: 28,
            multiSelect: false,
            keepLastSelected : false,
            afterSelectionChange: function(item,event){
        	//console.debug(item);
        	item.entity.operators = $scope.fieldOperatorDef[item['entity']['type']]; 
            },
            selectedItems: $scope.selectedBizFieldConditions,
            columnDefs: [{ field: 'label', displayName: $scope.labels.fieldLabel },
                { field: 'operator', displayName: $scope.labels.operatorLabel},
                { field: 'value', displayName: $scope.labels.valueLabel}]
        };
    });
    
    //条件类型
    //条件表达式
    //业务属性列表
    
    //新建一个条件
    $scope.addNewFieldCondition = function(){
	if(bizFieldData != undefined && bizFieldData.length > 0){
	    	var newItem = angular.copy(bizFieldData[0]);
		var operators = $scope.fieldOperatorDef[newItem['type']];
		newItem.operators = operators;
		newItem.operator = operators[0]['key'];
		newItem.andOr = '&&';
		$scope.bizFieldConditions.push(newItem);
		        
		$timeout(function(){
		    $scope.gridOptions.selectItem($scope.bizFieldConditions.length - 1, true);
		});
	}
	
    };
	
    //删除条件
    $scope.removeFiledCondition = function(){
	if ($scope.selectedBizFieldConditions.length > 0) {
            var index = $scope.bizFieldConditions.indexOf($scope.selectedBizFieldConditions[0]);
            $scope.gridOptions.selectItem(index, false);
            $scope.bizFieldConditions.splice(index, 1);

            $scope.selectedBizFieldConditions.length = 0;
            if (index < $scope.bizFieldConditions.length) {
                $scope.gridOptions.selectItem(index + 1, true);
            } else if ($scope.bizFieldConditions.length > 0) {
                $scope.gridOptions.selectItem(index - 1, true);
            }
        }
    }
    
    $scope.fieldChanged = function() {
	var newFieldName = $scope.selectedBizFieldConditions[0].name;
	//重新设置当前选中的字段对象
	angular.forEach(bizFieldData, function(item, index){
	    if(newFieldName == item['name']){
		var newItem = angular.copy(item);
		var operators = $scope.fieldOperatorDef[newItem['type']];
		newItem.operators = operators;
		newItem.operator = operators[0]['key'];
		delete $scope.selectedBizFieldConditions[0].operator;
		delete $scope.selectedBizFieldConditions[0].value;
		angular.extend($scope.selectedBizFieldConditions[0], newItem);
		return false;
	    }
	});
	
	
    };
    
    $scope.fieldEnumChanged = function(){
	var selectedValues = [];
	var selectEnumKeys = [];
	angular.forEach($scope.selectedBizFieldConditions[0]['values'], function(item, index){
	    if(item.selected){
		selectedValues.push(item['value']);
		selectEnumKeys.push(item['key']);
	    }
	});
	
	if(selectedValues.length > 0){
	    $scope.selectedBizFieldConditions[0]['value'] = selectedValues.join(',');
	    $scope.selectedBizFieldConditions[0]['enumKeys'] = selectEnumKeys;
	}else{
	    $scope.selectedBizFieldConditions[0]['value'] = '';
	}
	
	//console.debug($scope.selectedBizFieldConditions[0]['values']);
    };
    
    $scope.save = function() {
        //$scope.property.value = $scope.conditionExpression.value;
	var evaluateExpression = $scope.evaluateConditionExpression();
	var expressionTemplate = ACTIVITI.CONFIG.properties.sequenceConditionExpression.template;
	var expressionPattern = ACTIVITI.CONFIG.properties.sequenceConditionExpression.pattern;
	$scope.property.value = {
			"type":$scope.conditionExpression.type, //类型
			"value": $scope.conditionExpression.value,//自定义表达式
			"bizFieldCondtions":$scope.conditionExpression.bizFieldCondtions,//业务属性条件配置
			"evaluateExpression": expressionTemplate.replace(expressionPattern, evaluateExpression) //最终形成的条件表达式
	}
	
	//console.debug($scope.property.value);
	$scope.updatePropertyInModel($scope.property);
        $scope.close();
    };

    //判断是否有值
    $scope.hasValues = function(val){
	if(val==undefined || val == null){
	    return false;
	}
	if(angular.isNumber(val)){
	    return true;
	}
	if(angular.isString(val)){
	    return val.length > 0;
	}
	if(angular.isArray(val)){
	    return val.length > 0;
	}
    }
    
    $scope.evaluateConditionExpression = function(){
	if($scope.conditionExpression.type == 'field'){
	    $scope.conditionExpression.value = '';
	    var evaluateExpression = [];
	    var o_vals = $scope.conditionExpression.bizFieldCondtions;
	    if(o_vals != undefined && o_vals.length > 0){
		angular.forEach(o_vals, function(n,i){
		    if($scope.hasValues(n['operator']) && $scope.hasValues(n['value']) ){
			if(n['type'] == 'string'){
			    evaluateExpression.push(n['andOr']);
			    evaluateExpression.push('('+ n['name'] + n['operator'] + '\\\'' + n['value'] + '\\\')');
			}else if(n['type'] == 'number'){
			    evaluateExpression.push(n['andOr']);
			    evaluateExpression.push('('+ n['name'] + n['operator'] + n['value'] + ')');
			}else if(n['type'] == 'enum'){
			    evaluateExpression.push(n['andOr']);
			    var enumExpression = [];
			    angular.forEach(n['enumKeys'], function(nn,ii){
				enumExpression.push(n['name'] + '==\\\'' + nn + '\\\'');
			    });
			    evaluateExpression.push('(' + enumExpression.join(' || ') + ')')
			}
		    }
		});
	    }
	    //移除第一个元素
	    evaluateExpression.shift();
	    //console.debug(evaluateExpression.join(' '));
	    if(evaluateExpression.length == 0){
		return '';
	    }
	    return '${' + evaluateExpression.join(' ') + '}';
	}else if($scope.conditionExpression.type = 'custom'){
	    $scope.conditionExpression.bizFieldCondtions = [];
	    return $scope.conditionExpression.value;
	}else{
	    return '';
	}
    };
    
    // Close button handler
    $scope.close = function() {
    	$scope.property.mode = 'read';
    	$scope.$hide();
    };
}];