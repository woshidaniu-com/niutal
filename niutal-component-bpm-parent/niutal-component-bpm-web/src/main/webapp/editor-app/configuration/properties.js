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
'use strict';

var ACTIVITI = ACTIVITI || {};

ACTIVITI.CONFIG = {
	'contextRoot' : EDITOR.UTIL.getParameterByName('context') + '/activiti-rest-service',
	'properties'  : 
		{
    		'multiInstance' : 
    			{
    		    	'variable'				: '_multi_instance_assignee',
    		    	'collectionExpression'			: '${multiInstanceService.resolveCollection(execution)}',
    		    	'completionConditionExpress' 		: '${multiInstanceService.resolveCompletionCondition(execution)}',
    		    	'completionConditionExpressionTempalte' : '${multiInstanceService.resolveCompletionCondition(\'{0}\', execution)}',
    		    	'pattern' :  /\{\d{1}\}/g
    			},
    			
		'sequenceConditionExpression'　: 
			{
		    	'template' : '${flowConditionInvocation.invoke(\'{0}\',execution,authenticatedUserId)}',
		    	'pattern' :  /\{\d{1}\}/g
			}
		}
};


var KISBPM = KISBPM || {};

KISBPM.URL = {

    getModel : function(modelId) {
	return ACTIVITI.CONFIG.contextRoot + '/model/' + modelId + '/json.zf';
    },

    getStencilSet : function() {
	return ACTIVITI.CONFIG.contextRoot + '/editor/stencilset.zf?version='
		+ Date.now();
    },

    putModel : function(modelId) {
	return ACTIVITI.CONFIG.contextRoot + '/model/' + modelId + '/save.zf';
    },
    
    getBizFiled: function(bizId){
	return ACTIVITI.CONFIG.contextRoot + '/biz/' + bizId + '/getBizFileds.zf';
    },
    
    getProcessCategories: function(){
	//return ACTIVITI.CONFIG.contextRoot + '/processCategory/list.zf';
	return ACTIVITI.CONFIG.contextRoot + '/processCategory/getAll.zf';
    }
};

KISBPM.CONFIG = {
    'showRemovedProperties' : false
};

KISBPM.HEADER_CONFIG = {
    'showAppTitle' : true,
    'showHeaderMenu' : true,
    'showMainNavigation' : true,
    'showPageHeader' : true
};

KISBPM.PROPERTY_CONFIG = {
    "string" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/default-value-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/string-property-write-mode-template.html"
    },
    "boolean" : {
	"templateUrl" : "editor-app/configuration/properties/boolean-property-template.html"
    },
    "text" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/default-value-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/text-property-write-template.html"
    },
    "kisbpm-multiinstance" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/multiinstance-property-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/multiinstance-property-write-template.html"
    },
    "kisbpm-multiinstancecondition":{
	"readModeTemplateUrl" : "editor-app/configuration/properties/multiinstance-condition-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/multiinstance-condition-write-template.html"
    },
    "oryx-formproperties-complex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/form-properties-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/form-properties-write-template.html"
    },
    "oryx-executionlisteners-multiplecomplex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/execution-listeners-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/execution-listeners-write-template.html"
    },
    "oryx-tasklisteners-multiplecomplex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/task-listeners-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/task-listeners-write-template.html"
    },
    "oryx-eventlisteners-multiplecomplex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/event-listeners-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/event-listeners-write-template.html"
    },
    "oryx-usertaskassignment-complex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/assignment-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/assignment-write-template.html"
    },
    "oryx-servicetaskfields-complex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/fields-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/fields-write-template.html"
    },
    "oryx-callactivityinparameters-complex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/in-parameters-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/in-parameters-write-template.html"
    },
    "oryx-callactivityoutparameters-complex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/out-parameters-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/out-parameters-write-template.html"
    },
    "oryx-subprocessreference-complex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/subprocess-reference-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/subprocess-reference-write-template.html"
    },
    "oryx-sequencefloworder-complex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/sequenceflow-order-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/sequenceflow-order-write-template.html"
    },
    "oryx-conditionsequenceflow-complex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/condition-expression-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/condition-expression-write-template.html"
    },

    "oryx-signaldefinitions-multiplecomplex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/signal-definitions-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/signal-definitions-write-template.html"
    },
    "oryx-signalref-string" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/default-value-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/signal-property-write-template.html"
    },
    "oryx-messagedefinitions-multiplecomplex" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/message-definitions-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/message-definitions-write-template.html"
    },
    "oryx-messageref-string" : {
	"readModeTemplateUrl" : "editor-app/configuration/properties/default-value-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/message-property-write-template.html"
    },
    "oryx-processnamespace-complex":{
	"readModeTemplateUrl" : "editor-app/configuration/properties/process-namespace-display-template.html",
	"writeModeTemplateUrl" : "editor-app/configuration/properties/process-namespace-write-template.html"
    },
    "multiinstance_condition":{}
};

KISBPM.TOOLBAR = {
    ACTIONS : {

	saveModel : function(services) {

	    var modal = services.$modal({
		backdrop : true,
		keyboard : true,
		template : 'editor-app/popups/save-model.html?version='
			+ Date.now(),
		scope : services.$scope
	    });
	},

	undo : function(services) {

	    // Get the last commands
	    var lastCommands = services.$scope.undoStack.pop();

	    if (lastCommands) {
		// Add the commands to the redo stack
		services.$scope.redoStack.push(lastCommands);

		// Force refresh of selection, might be that the undo command
		// impacts properties in the selected item
		if (services.$rootScope
			&& services.$rootScope.forceSelectionRefresh) {
		    services.$rootScope.forceSelectionRefresh = true;
		}

		// Rollback every command
		for (var i = lastCommands.length - 1; i >= 0; --i) {
		    lastCommands[i].rollback();
		}

		// Update and refresh the canvas
		services.$scope.editor.handleEvents({
		    type : ORYX.CONFIG.EVENT_UNDO_ROLLBACK,
		    commands : lastCommands
		});

		// Update
		services.$scope.editor.getCanvas().update();
		services.$scope.editor.updateSelection();
	    }

	    var toggleUndo = false;
	    if (services.$scope.undoStack.length == 0) {
		toggleUndo = true;
	    }

	    var toggleRedo = false;
	    if (services.$scope.redoStack.length > 0) {
		toggleRedo = true;
	    }

	    if (toggleUndo || toggleRedo) {
		for (var i = 0; i < services.$scope.items.length; i++) {
		    var item = services.$scope.items[i];
		    if (toggleUndo
			    && item.action === 'KISBPM.TOOLBAR.ACTIONS.undo') {
			services.$scope.safeApply(function() {
			    item.enabled = false;
			});
		    } else if (toggleRedo
			    && item.action === 'KISBPM.TOOLBAR.ACTIONS.redo') {
			services.$scope.safeApply(function() {
			    item.enabled = true;
			});
		    }
		}
	    }
	},

	redo : function(services) {

	    // Get the last commands from the redo stack
	    var lastCommands = services.$scope.redoStack.pop();

	    if (lastCommands) {
		// Add this commands to the undo stack
		services.$scope.undoStack.push(lastCommands);

		// Force refresh of selection, might be that the redo command
		// impacts properties in the selected item
		if (services.$rootScope
			&& services.$rootScope.forceSelectionRefresh) {
		    services.$rootScope.forceSelectionRefresh = true;
		}

		// Execute those commands
		lastCommands.each(function(command) {
		    command.execute();
		});

		// Update and refresh the canvas
		services.$scope.editor.handleEvents({
		    type : ORYX.CONFIG.EVENT_UNDO_EXECUTE,
		    commands : lastCommands
		});

		// Update
		services.$scope.editor.getCanvas().update();
		services.$scope.editor.updateSelection();
	    }

	    var toggleUndo = false;
	    if (services.$scope.undoStack.length > 0) {
		toggleUndo = true;
	    }

	    var toggleRedo = false;
	    if (services.$scope.redoStack.length == 0) {
		toggleRedo = true;
	    }

	    if (toggleUndo || toggleRedo) {
		for (var i = 0; i < services.$scope.items.length; i++) {
		    var item = services.$scope.items[i];
		    if (toggleUndo
			    && item.action === 'KISBPM.TOOLBAR.ACTIONS.undo') {
			services.$scope.safeApply(function() {
			    item.enabled = true;
			});
		    } else if (toggleRedo
			    && item.action === 'KISBPM.TOOLBAR.ACTIONS.redo') {
			services.$scope.safeApply(function() {
			    item.enabled = false;
			});
		    }
		}
	    }
	},

	cut : function(services) {
	    KISBPM.TOOLBAR.ACTIONS._getOryxEditPlugin(services.$scope)
		    .editCut();
	    for (var i = 0; i < services.$scope.items.length; i++) {
		var item = services.$scope.items[i];
		if (item.action === 'KISBPM.TOOLBAR.ACTIONS.paste') {
		    services.$scope.safeApply(function() {
			item.enabled = true;
		    });
		}
	    }
	},

	copy : function(services) {
	    KISBPM.TOOLBAR.ACTIONS._getOryxEditPlugin(services.$scope)
		    .editCopy();
	    for (var i = 0; i < services.$scope.items.length; i++) {
		var item = services.$scope.items[i];
		if (item.action === 'KISBPM.TOOLBAR.ACTIONS.paste') {
		    services.$scope.safeApply(function() {
			item.enabled = true;
		    });
		}
	    }
	},

	paste : function(services) {
	    KISBPM.TOOLBAR.ACTIONS._getOryxEditPlugin(services.$scope)
		    .editPaste();
	},

	deleteItem : function(services) {
	    KISBPM.TOOLBAR.ACTIONS._getOryxEditPlugin(services.$scope)
		    .editDelete();
	},

	addBendPoint : function(services) {

	    var dockerPlugin = KISBPM.TOOLBAR.ACTIONS
		    ._getOryxDockerPlugin(services.$scope);

	    var enableAdd = !dockerPlugin.enabledAdd();
	    dockerPlugin.setEnableAdd(enableAdd);
	    if (enableAdd) {
		dockerPlugin.setEnableRemove(false);
		document.body.style.cursor = 'pointer';
	    } else {
		document.body.style.cursor = 'default';
	    }
	},

	removeBendPoint : function(services) {

	    var dockerPlugin = KISBPM.TOOLBAR.ACTIONS
		    ._getOryxDockerPlugin(services.$scope);

	    var enableRemove = !dockerPlugin.enabledRemove();
	    dockerPlugin.setEnableRemove(enableRemove);
	    if (enableRemove) {
		dockerPlugin.setEnableAdd(false);
		document.body.style.cursor = 'pointer';
	    } else {
		document.body.style.cursor = 'default';
	    }
	},

	/**
	 * Helper method: fetches the Oryx Edit plugin from the provided scope,
	 * if not on the scope, it is created and put on the scope for further
	 * use.
	 * 
	 * It's important to reuse the same EditPlugin while the same scope is
	 * active, as the clipboard is stored for the whole lifetime of the
	 * scope.
	 */
	_getOryxEditPlugin : function($scope) {
	    if ($scope.oryxEditPlugin === undefined
		    || $scope.oryxEditPlugin === null) {
		$scope.oryxEditPlugin = new ORYX.Plugins.Edit($scope.editor);
	    }
	    return $scope.oryxEditPlugin;
	},

	zoomIn : function(services) {
	    KISBPM.TOOLBAR.ACTIONS._getOryxViewPlugin(services.$scope).zoom(
		    [ 1.0 + ORYX.CONFIG.ZOOM_OFFSET ]);
	},

	zoomOut : function(services) {
	    KISBPM.TOOLBAR.ACTIONS._getOryxViewPlugin(services.$scope).zoom(
		    [ 1.0 - ORYX.CONFIG.ZOOM_OFFSET ]);
	},

	zoomActual : function(services) {
	    KISBPM.TOOLBAR.ACTIONS._getOryxViewPlugin(services.$scope)
		    .setAFixZoomLevel(1);
	},

	zoomFit : function(services) {
	    KISBPM.TOOLBAR.ACTIONS._getOryxViewPlugin(services.$scope)
		    .zoomFitToModel();
	},

	alignVertical : function(services) {
	    KISBPM.TOOLBAR.ACTIONS._getOryxArrangmentPlugin(services.$scope)
		    .alignShapes([ ORYX.CONFIG.EDITOR_ALIGN_MIDDLE ]);
	},

	alignHorizontal : function(services) {
	    KISBPM.TOOLBAR.ACTIONS._getOryxArrangmentPlugin(services.$scope)
		    .alignShapes([ ORYX.CONFIG.EDITOR_ALIGN_CENTER ]);
	},

	sameSize : function(services) {
	    KISBPM.TOOLBAR.ACTIONS._getOryxArrangmentPlugin(services.$scope)
		    .alignShapes([ ORYX.CONFIG.EDITOR_ALIGN_SIZE ]);
	},
	
	help : function(services) {

	    var modal = services.$modal({
		template : 'editor-app/popups/help-model.html?version='
			+ Date.now(),
		scope : services.$scope
	    });
	},
	
	closeEditor : function(services) {
	    window.close();
	    //window.location.href = "./";
	},

	/**
	 * Helper method: fetches the Oryx View plugin from the provided scope,
	 * if not on the scope, it is created and put on the scope for further
	 * use.
	 */
	_getOryxViewPlugin : function($scope) {
	    if ($scope.oryxViewPlugin === undefined
		    || $scope.oryxViewPlugin === null) {
		$scope.oryxViewPlugin = new ORYX.Plugins.View($scope.editor);
	    }
	    return $scope.oryxViewPlugin;
	},

	_getOryxArrangmentPlugin : function($scope) {
	    if ($scope.oryxArrangmentPlugin === undefined
		    || $scope.oryxArrangmentPlugin === null) {
		$scope.oryxArrangmentPlugin = new ORYX.Plugins.Arrangement(
			$scope.editor);
	    }
	    return $scope.oryxArrangmentPlugin;
	},

	_getOryxDockerPlugin : function($scope) {
	    if ($scope.oryxDockerPlugin === undefined
		    || $scope.oryxDockerPlugin === null) {
		$scope.oryxDockerPlugin = new ORYX.Plugins.AddDocker(
			$scope.editor);
	    }
	    return $scope.oryxDockerPlugin;
	}
    }
};

KISBPM.TOOLBAR_CONFIG = {
    "items" : [ {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.SAVE",
	"cssClass" : "editor-icon editor-icon-save",
	"action" : "KISBPM.TOOLBAR.ACTIONS.saveModel"
    }, {
	"type" : "separator",
	"title" : "",
	"cssClass" : "toolbar-separator"
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.CUT",
	"cssClass" : "editor-icon editor-icon-cut",
	"action" : "KISBPM.TOOLBAR.ACTIONS.cut",
	"enabled" : false,
	"enabledAction" : "element"
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.COPY",
	"cssClass" : "editor-icon editor-icon-copy",
	"action" : "KISBPM.TOOLBAR.ACTIONS.copy",
	"enabled" : false,
	"enabledAction" : "element"
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.PASTE",
	"cssClass" : "editor-icon editor-icon-paste",
	"action" : "KISBPM.TOOLBAR.ACTIONS.paste",
	"enabled" : false
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.DELETE",
	"cssClass" : "editor-icon editor-icon-delete",
	"action" : "KISBPM.TOOLBAR.ACTIONS.deleteItem",
	"enabled" : false,
	"enabledAction" : "element"
    }, {
	"type" : "separator",
	"title" : "TOOLBAR.ACTION.SAVE",
	"cssClass" : "toolbar-separator"
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.REDO",
	"cssClass" : "editor-icon editor-icon-redo",
	"action" : "KISBPM.TOOLBAR.ACTIONS.redo",
	"enabled" : false
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.UNDO",
	"cssClass" : "editor-icon editor-icon-undo",
	"action" : "KISBPM.TOOLBAR.ACTIONS.undo",
	"enabled" : false
    }, {
	"type" : "separator",
	"title" : "TOOLBAR.ACTION.SAVE",
	"cssClass" : "toolbar-separator"
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.ALIGNVERTICAL",
	"cssClass" : "editor-icon editor-icon-align-vertical",
	"action" : "KISBPM.TOOLBAR.ACTIONS.alignVertical",
	"enabled" : false,
	"enabledAction" : "element",
	"minSelectionCount" : 2
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.ALIGNHORIZONTAL",
	"cssClass" : "editor-icon editor-icon-align-horizontal",
	"action" : "KISBPM.TOOLBAR.ACTIONS.alignHorizontal",
	"enabledAction" : "element",
	"enabled" : false,
	"minSelectionCount" : 2
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.SAMESIZE",
	"cssClass" : "editor-icon editor-icon-same-size",
	"action" : "KISBPM.TOOLBAR.ACTIONS.sameSize",
	"enabledAction" : "element",
	"enabled" : false,
	"minSelectionCount" : 2
    }, {
	"type" : "separator",
	"title" : "TOOLBAR.ACTION.SAVE",
	"cssClass" : "toolbar-separator"
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.ZOOMIN",
	"cssClass" : "editor-icon editor-icon-zoom-in",
	"action" : "KISBPM.TOOLBAR.ACTIONS.zoomIn"
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.ZOOMOUT",
	"cssClass" : "editor-icon editor-icon-zoom-out",
	"action" : "KISBPM.TOOLBAR.ACTIONS.zoomOut"
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.ZOOMACTUAL",
	"cssClass" : "editor-icon editor-icon-zoom-actual",
	"action" : "KISBPM.TOOLBAR.ACTIONS.zoomActual"
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.ZOOMFIT",
	"cssClass" : "editor-icon editor-icon-zoom-fit",
	"action" : "KISBPM.TOOLBAR.ACTIONS.zoomFit"
    }, {
	"type" : "separator",
	"title" : "",
	"cssClass" : "toolbar-separator"
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.BENDPOINT.ADD",
	"cssClass" : "editor-icon editor-icon-bendpoint-add",
	"action" : "KISBPM.TOOLBAR.ACTIONS.addBendPoint",
	"id" : "add-bendpoint-button"
    }, {
	"type" : "button",
	"title" : "TOOLBAR.ACTION.BENDPOINT.REMOVE",
	"cssClass" : "editor-icon editor-icon-bendpoint-remove",
	"action" : "KISBPM.TOOLBAR.ACTIONS.removeBendPoint",
	"id" : "remove-bendpoint-button"
    } ],

    "secondaryItems" : [ 
	{
		"type" : "button",
		"title" : "TOOLBAR.ACTION.HELP",
		"cssClass" : "glyphicon glyphicon-question-sign",
		"action" : "KISBPM.TOOLBAR.ACTIONS.help"
	},
	{
		"type" : "separator",
		"title" : "",
		"cssClass" : "toolbar-separator"
	},
	{
	"type" : "button",
	"title" : "TOOLBAR.ACTION.CLOSE",
	"cssClass" : "glyphicon glyphicon-remove",
	"action" : "KISBPM.TOOLBAR.ACTIONS.closeEditor"
    } ]
};

/** Inspired by https://github.com/krasimir/EventBus/blob/master/src/EventBus.js */
KISBPM.eventBus = {

    /** Event fired when the editor is loaded and ready */
    EVENT_TYPE_EDITOR_READY: 'event-type-editor-ready',

    /** Event fired when a selection is made on the canvas. */
    EVENT_TYPE_SELECTION_CHANGE: 'event-type-selection-change',

    /** Event fired when a toolbar button has been clicked. */
    EVENT_TYPE_TOOLBAR_BUTTON_CLICKED: 'event-type-toolbar-button-clicked',

    /** Event fired when a stencil item is dropped on the canvas. */
    EVENT_TYPE_ITEM_DROPPED: 'event-type-item-dropped',

    /** Event fired when a property value is changed. */
    EVENT_TYPE_PROPERTY_VALUE_CHANGED: 'event-type-property-value-changed',

    /** Event fired on double click in canvas. */
    EVENT_TYPE_DOUBLE_CLICK: 'event-type-double-click',

    /** Event fired on a mouse out */
    EVENT_TYPE_MOUSE_OUT: 'event-type-mouse-out',

    /** Event fired on a mouse over */
    EVENT_TYPE_MOUSE_OVER: 'event-type-mouse-over',

    /** Event fired when a model is saved. */
    EVENT_TYPE_MODEL_SAVED: 'event-type-model-saved',
    
    /** Event fired when the quick menu buttons should be hidden. */
    EVENT_TYPE_HIDE_SHAPE_BUTTONS: 'event-type-hide-shape-buttons',

    /** A mapping for storing the listeners*/
    listeners: {},

    /** The Oryx editor, which is stored locally to send events to */
    editor: null,

    /**
     * Add an event listener to the event bus, listening to the event with the provided type.
     * Type and callback are mandatory parameters.
     *
     * Provide scope parameter if it is important that the callback is executed
     * within a specific scope.
     */
    addListener: function (type, callback, scope) {

        // Add to the listeners map
        if (typeof this.listeners[type] !== "undefined") {
            this.listeners[type].push({scope: scope, callback: callback});
        } else {
            this.listeners[type] = [
                {scope: scope, callback: callback}
            ];
        }
    },

    /**
     * Removes the provided event listener.
     */
    removeListener: function (type, callback, scope) {
        if (typeof this.listeners[type] != "undefined") {
            var numOfCallbacks = this.listeners[type].length;
            var newArray = [];
            for (var i = 0; i < numOfCallbacks; i++) {
                var listener = this.listeners[type][i];
                if (listener.scope === scope && listener.callback === callback) {
                    // Do nothing, this is the listener and doesn't need to survive
                } else {
                    newArray.push(listener);
                }
            }
            this.listeners[type] = newArray;
        }
    },

    hasListener:function(type, callback, scope) {
        if(typeof this.listeners[type] != "undefined") {
            var numOfCallbacks = this.listeners[type].length;
            if(callback === undefined && scope === undefined){
                return numOfCallbacks > 0;
            }
            for(var i=0; i<numOfCallbacks; i++) {
                var listener = this.listeners[type][i];
                if(listener.scope == scope && listener.callback == callback) {
                    return true;
                }
            }
        }
        return false;
    },

    /**
     * Dispatch an event to all event listeners registered to that specific type.
     */
    dispatch:function(type, event) {
        if(typeof this.listeners[type] != "undefined") {
            var numOfCallbacks = this.listeners[type].length;
            for(var i=0; i<numOfCallbacks; i++) {
                var listener = this.listeners[type][i];
                if(listener && listener.callback) {
                    listener.callback.apply(listener.scope, [event]);
                }
            }
        }
    },

    dispatchOryxEvent: function(event, uiObject) {
        KISBPM.eventBus.editor.handleEvents(event, uiObject);
    }

};

var HelpModelCtrl = ['$scope', function($scope){
    $scope.close = function () {
    	$scope.$hide();
    };
}];

/** Custom controller for the save dialog */
var SaveModelCtrl = [ '$rootScope', '$scope', '$http', '$route', '$location',
    function ($rootScope, $scope, $http, $route, $location) {

    var modelMetaData = $scope.editor.getModelMetaData();

    var description = '';
    if (modelMetaData.description) {
    	description = modelMetaData.description;
    }
    
    var saveDialog = { 'name' : modelMetaData.name,
            'description' : description};
    
    $scope.saveDialog = saveDialog;
    
    var json = $scope.editor.getJSON();
    json = JSON.stringify(json);

    var params = {
        modeltype: modelMetaData.model.modelType,
        json_xml: json,
        name: 'model'
    };

    $scope.status = {
        loading: false
    };

    $scope.close = function () {
    	$scope.$hide();
    };

    $scope.saveAndClose = function () {
    	$scope.save(function() {
    		window.location.href = "./";
    	});
    };
    $scope.save = function (successCallback) {

        if (!$scope.saveDialog.name || $scope.saveDialog.name.length == 0) {
            return;
        }

        // Indicator spinner image
        $scope.status = {
        	loading: true
        };
        
        modelMetaData.name = $scope.saveDialog.name;
        modelMetaData.description = $scope.saveDialog.description;

        var json = $scope.editor.getJSON();
        json = JSON.stringify(json);
        
        var selection = $scope.editor.getSelection();
        $scope.editor.setSelection([]);
        
        // Get the serialized svg image source
        var svgClone = $scope.editor.getCanvas().getSVGRepresentation(true);
        $scope.editor.setSelection(selection);
        if ($scope.editor.getCanvas().properties["oryx-showstripableelements"] === false) {
            var stripOutArray = jQuery(svgClone).find(".stripable-element");
            for (var i = stripOutArray.length - 1; i >= 0; i--) {
            	stripOutArray[i].remove();
            }
        }

        // Remove all forced stripable elements
        var stripOutArray = jQuery(svgClone).find(".stripable-element-force");
        for (var i = stripOutArray.length - 1; i >= 0; i--) {
            stripOutArray[i].remove();
        }

        // Parse dom to string
        var svgDOM = DataManager.serialize(svgClone);

        var params = {
            json_xml: json,
            svg_xml: svgDOM,
            name: $scope.saveDialog.name,
            description: $scope.saveDialog.description
        };

        // Update
        $http({    method: 'PUT',
            data: params,
            ignoreErrors: true,
            headers: {'Accept': 'application/json',
                      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'},
            transformRequest: function (obj) {
                var str = [];
                for (var p in obj) {
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                }
                return str.join("&");
            },
            url: KISBPM.URL.putModel(modelMetaData.modelId)})

            .success(function (data, status, headers, config) {
                $scope.editor.handleEvents({
                    type: ORYX.CONFIG.EVENT_SAVED
                });
                $scope.modelData.name = $scope.saveDialog.name;
                $scope.modelData.lastUpdated = data.lastUpdated;
                
                $scope.status.loading = false;
                $scope.$hide();

                // Fire event to all who is listening
                var saveEvent = {
                    type: KISBPM.eventBus.EVENT_TYPE_MODEL_SAVED,
                    model: params,
                    modelId: modelMetaData.modelId,
		            eventType: 'update-model'
                };
                KISBPM.eventBus.dispatch(KISBPM.eventBus.EVENT_TYPE_MODEL_SAVED, saveEvent);

                // Reset state
                $scope.error = undefined;
                $scope.status.loading = false;

                // Execute any callback
                if (successCallback) {
                    successCallback();
                }

            })
            .error(function (data, status, headers, config) {
                $scope.error = {};
                //console.log('Something went wrong when updating the process model:' + JSON.stringify(data));
                $scope.status.loading = false;
            });
    };

}];