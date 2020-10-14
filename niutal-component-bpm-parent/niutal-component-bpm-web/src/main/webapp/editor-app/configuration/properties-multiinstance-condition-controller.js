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
 * Multiinstance Completion Condition Config
 */

var KisBpmMultiinstanceConditionCtrl = [ '$scope', '$modal' ,function($scope ,$modal) {
    
    // Config for the modal window
    var opts = {
        template:  'editor-app/configuration/properties/multiinstance-condition-popup.html?version=' + Date.now(),
        scope: $scope
    };

    // Open the dialog
    $modal(opts);
  
}];

var KisBpmMultiinstanceConditionPopupCtrl = [ '$scope', '$q', '$timeout','$translate', '$http', function($scope, $q, $timeout, $translate, $http) {
    console.debug($scope.property.value);
    // Put json representing form properties on scope
    if ($scope.property.value !== undefined && $scope.property.value !== null ) {
	
        if ($scope.property.value.constructor == String && $scope.property.value.length > 0)
        {
            $scope.multiinstanceCondition = {"type" : "custom", "value" : $scope.property.value};
        }
        else
        {
            // Note that we clone the json object rather then setting it directly,
            // this to cope with the fact that the user can click the cancel button and no changes should have happened
            $scope.multiinstanceCondition = angular.copy($scope.property.value);
        }
        
    } else {
        $scope.multiinstanceCondition = {"type":"default"};
    }
    
    if($scope.multiinstanceCondition.type == 'custom'){
	$scope.cutomExpressionCopy = $scope.multiinstanceCondition.value;
    }

    $scope.$watch('multiinstanceCondition.type', function(newValue, oldValue) {
	 // if(newValue == 'default'){
	 //     $scope.multiinstanceCondition.value = 0;
         //}
	 //if(newValue == 'number'){
	 //     $scope.multiinstanceCondition.value = 1;
	 //}
	 //if(newValue == 'percentage'){
         //   $scope.multiinstanceCondition.value = 100;
         //}
	 if(newValue == 'custom'){
	     $scope.multiinstanceCondition.value = $scope.cutomExpressionCopy;
	 }
    });
    
    $scope.$watch('multiinstanceCondition.value', function(newValue, oldValue) {
	 if($scope.multiinstanceCondition.type == 'custom'){
	     $scope.cutomExpressionCopy = $scope.multiinstanceCondition.value;
        }
   });
	
    //删除条件
    $scope.removeFiledCondition = function(){}
    
    $scope.fieldChanged = function() {};
    
    $scope.fieldEnumChanged = function(){};
    
    $scope.save = function() {
	//判断数据是否合法 
	
	if(!$scope.multiinstanceConditionForm.$valid){
	    return false;
	}
	var evaluateExpression = $scope.evaluateConditionExpression();
	var expressionTemplate = ACTIVITI.CONFIG.properties.multiInstance.completionConditionExpressionTempalte;
	var expressionPattern = ACTIVITI.CONFIG.properties.multiInstance.pattern;
	$scope.property.value = {
			"type":$scope.multiinstanceCondition.type, //类型
			"value": $scope.multiinstanceCondition.value,//自定义表达式
			"evaluateExpression": expressionTemplate.replace(expressionPattern, evaluateExpression) //最终形成的条件表达式
	}
	console.debug($scope.multiinstanceCondition.type);
	$scope.updatePropertyInModel($scope.property);
        $scope.close();
    };
    
    $scope.evaluateConditionExpression = function(){
	if($scope.multiinstanceCondition.type == 'percentage'){
	    if(angular.isNumber($scope.multiinstanceCondition.value)){
		 return '${nrOfCompletedInstances/nrOfInstances >= ' + $scope.multiinstanceCondition.value/100 + '}';
	    }
	}else if($scope.multiinstanceCondition.type == 'number'){
	    if(angular.isNumber($scope.multiinstanceCondition.value)){
		 return '${nrOfCompletedInstances >= ' + $scope.multiinstanceCondition.value + '}';
	    }
	}else if($scope.multiinstanceCondition.type == 'custom'){
	    	return $scope.multiinstanceCondition.value;
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