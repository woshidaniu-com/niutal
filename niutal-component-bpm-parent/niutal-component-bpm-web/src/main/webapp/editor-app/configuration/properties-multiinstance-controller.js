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
 * Execution listeners
 */

var KisBpmMultiInstanceDisplayCtrl = [ '$scope', function($scope){
    $scope.multiInstanceTypeTranslateMap = {
    	    'None':'PROPERTY.MULTIINSTANCE_TYPE_NONE',
    	    'Parallel':'PROPERTY.MULTIINSTANCE_TYPE_PARALLEL',
    	    'Sequential':'PROPERTY.MULTIINSTANCE_TYPE_SEQUENTIAL'
        };
}];

var KisBpmMultiInstanceCtrl = [ '$scope', function($scope) {
    if ($scope.property.value == undefined && $scope.property.value == null)
    {
    	$scope.property.value = 'None';
    }
    
    $scope.multiInstanceChanged = function() {
	var shape = $scope.selectedShape;
	var stencil = shape.getStencil();
	var properties = $scope.selectedItem.properties;
	var property = $scope.property;
	var key = property.key;
        var newValue = property.value;
        var oldValue = shape.properties[key];
         
         if (newValue != oldValue) {
             var commandClass = ORYX.Core.Command.extend({
                 construct: function (key1, oldValue1, newValue1, shape1, facade1) {
                     this.key = key1;
                     this.oldValue = oldValue1;
                     this.newValue = newValue1;
                     this.shape = shape1;
                     this.facade = facade1;
                 },
                 execute: function () {
                     this.shape.setProperty(this.key, this.newValue);
                     //automatic update when multi instance mode is changed
                     //update multi instance collection
                     //update multi instance variable
                     //update multi instance completion condition
                     //update task defintion assignee to '${_multiInstanceTaskAssignee}'
                     if(newValue == 'None'){
                	 this.shape.setProperty('multiinstance_collection', '');
                         this.shape.setProperty('multiinstance_variable', '');
                         this.shape.setProperty('multiinstance_condition', '');
                         this.shape.setProperty('usertaskassignment', '');
                     }else{
                	 this.shape.setProperty('multiinstance_collection', $scope.config.properties.multiInstance.collectionExpression);
                         this.shape.setProperty('multiinstance_variable', $scope.config.properties.multiInstance.variable);
                         //设置成默认的方式
                         if(this.shape.properties['multiinstance_condition'] == undefined){
                             this.shape.setProperty('multiinstance_condition', 
                        	     {
                        	 	'type' : 'default',
                        	     });
                         }
                         var assigneeExpression = '${' + $scope.config.properties.multiInstance.variable + '}';
                         this.shape.setProperty('usertaskassignment', {'assignment': {'assignee':assigneeExpression}});
                     }
                     
                     this.facade.getCanvas().update();
                     this.facade.updateSelection();
                 },
                 rollback: function () {
                     this.shape.setProperty(this.key, this.oldValue);
                     this.facade.getCanvas().update();
                     this.facade.updateSelection();
                 }
             });
             // Instantiate the class
             var command = new commandClass(key,oldValue,newValue,shape,$scope.editor);
             // Execute the command
             $scope.editor.executeCommands([command]);
             
             $scope.editor.handleEvents({
                 type: ORYX.CONFIG.EVENT_PROPWINDOW_PROP_CHANGED,
                 elements: [shape],
                 key: key
             });

             // Switch the property back to read mode, now the update is done
             property.mode = 'read';

             // Fire event to all who is interested
             // Fire event to all who want to know about this
             var event = {
                 type: KISBPM.eventBus.EVENT_TYPE_PROPERTY_VALUE_CHANGED,
                 property: property,
                 oldValue: oldValue,
                 newValue: newValue
             };
             KISBPM.eventBus.dispatch(event.type, event);
         } else {
             // Switch the property back to read mode, no update was needed
             property.mode = 'read';
         }

    };
    
}];