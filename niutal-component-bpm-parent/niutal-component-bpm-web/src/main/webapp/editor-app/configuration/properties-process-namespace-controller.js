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

var KisBpmProcessNamespaceCtrl = [ '$scope', '$q', '$modal', '$translate' ,function($scope, $q ,$modal, $translate) {
	  // Config for the modal window
	  var opts = {
	       template:  'editor-app/configuration/properties/process-namespace-popup.html?version=' + Date.now(),
	       scope: $scope
	  };

	  // Open the dialog
	  $modal(opts);

}];

var KisBpmProcessNamespacePopupCtrl = ['$rootScope', '$scope', '$q', '$timeout','$translate', '$http', function($rootScope, $scope, $q, $timeout, $translate, $http) {
    
    var DEFAULT_PROCESS_CATEGORY = 
    {
	id : 'default',
	name:$translate('PROPERTY.PROCESS.NAMESPACE.DEFAULT')
    };
    
    if($scope.property.value !== undefined){
	$scope.processCategory = angular.copy($scope.property.value);
    }
    
    $scope.processCategories = [];
    //查询类别
    var processCategoryUrl = KISBPM.URL.getProcessCategories();
    $http({method: 'GET', url: processCategoryUrl}).
        success(function (data, status, headers, config) {
           $scope.processCategories = angular.fromJson(data);
           
           if($scope.processCategories && $scope.processCategory){
               angular.forEach($scope.processCategories, function(v,k){
        	   if(v.id == $scope.processCategory.id){
        	       $scope.selectedProcessCategory = v;  
        	   }
               });
           }
        }).
        error(function (data, status, headers, config) {
         
        });
    
    $scope.selectedProcessCategoryChanged = function() {
	
	if($scope.selectedProcessCategory){
	    $scope.processCategory = 
		{
			id:$scope.selectedProcessCategory.id, 
			name:$scope.selectedProcessCategory.name
		} 
	}else{
	    $scope.processCategory = undefined;
	}
    };
    
    $scope.save = function() {
	if($scope.processCategory){
	    $scope.property.value = $scope.processCategory ;
	}else{
	    $scope.property.value = DEFAULT_PROCESS_CATEGORY ;
	}
	
	$scope.updatePropertyInModel($scope.property);
        $scope.close();
    };

    
    // Close button handler
    $scope.close = function() {
    	$scope.property.mode = 'read';
    	$scope.$hide();
    };
}];