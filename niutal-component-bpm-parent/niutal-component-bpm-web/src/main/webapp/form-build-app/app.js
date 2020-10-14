angular
	.module(
		"formBuilder",
		[ "ui.bootstrap", "ui.select", "formio", "ngFormBuilder",
			"jsonFormatter" ])
	.config([ "$locationProvider", function($locationProvider) {
	    $locationProvider.html5Mode({
		enabled : true,
		requireBase : false
	    });
	} ])
	.run(
		[
			"$rootScope",
			'formioComponents',
			'$timeout',
			'$window',
			'$document',
			'$location',
			'$http',
			'$q',
			'ngDialog',
			function($rootScope, formioComponents, $timeout,
				$window, $document, $location, $http, $q,
				ngDialog) {
			    // define loading overlay
			    $rootScope.holdOn = function(action) {
				if (HoldOn && HoldOn[action]) {
				    HoldOn[action]();
				}
			    };
			    $rootScope.initialized = false;

			    $rootScope.displays = [ {
				name : 'form',
				title : '普通表单'
			    }, {
				name : 'wizard',
				title : '向导型表单'
			    } ];

			    $rootScope.form = {
				components : [],
				display : 'form'
			    };

			    var urlParams = $location.search();
			    $rootScope.modelId = urlParams['modelId'];
			    $rootScope.contextPath = urlParams['context'];
			    $rootScope.modelJsonDataDefer = $q.defer();

			    if (!$rootScope.contextPath) {
				ngDialog.open({
				    template : 'parameter-context-error.html',
				    width : 300
				});

				$rootScope.modelJsonDataDefer.reject();
			    } else {
				if ($rootScope.modelId) {
				    $http(
					    {
						method : 'GET',
						url : $rootScope.contextPath
							+ '/form-service/model/'
							+ $rootScope.modelId
							+ '/json.zf'
					    }).then(
					    function(response) {
						$rootScope.modelJsonDataDefer
							.resolve(response['data']);
					    },
					    function(response) {
						$rootScope.modelJsonDataDefer
							.reject();
					    });
				} else {
				    ngDialog.open({
						template : 'parameter-modelId-error.html',
						width : 300
					    });
				    $rootScope.modelJsonDataDefer.reject();
				}
			    }

			    //
			    $rootScope.modelJsonDataDefer.promise
				    .then(function(data) {
					$rootScope.initialiaed = true;
					angular.extend($rootScope.form, data['json_modelData']);
					$rootScope.modelName = data['name'];
					$rootScope.modelDesc = data['description'];
					$rootScope.formUpdateUnsaved = false;
					$rootScope.showDebugConsole = false;
					$rootScope.renderForm = true;
					$rootScope
						.$on('formUpdate',
							function(event, form) {
							    angular
								    .merge(
									    $rootScope.form,
									    form);
							    $rootScope.renderForm = false;
							    $rootScope.formUpdateUnsaved = true;
							    setTimeout(
								    function() {
									$rootScope.renderForm = true;
								    }, 10);
							});

					$rootScope
						.$watch(
							'formUpdateUnsaved',
							function(newValue,
								oldValue) {
							    var prifix = '[*未保存]';
							    if (newValue
								    && (!_
									    .startsWith(
										    $document[0].title,
										    prifix))) {
								$document[0].title = prifix
									+ $document[0].title;
								return;
							    }
							    if ((!newValue)
								    && _
									    .startsWith(
										    $document[0].title,
										    prifix)) {
								$document[0].title = _
									.trimStart(
										$document[0].title,
										prifix);
							    }
							});

					var originalComps = _
						.cloneDeep($rootScope.form.components);
					var currentDisplay = 'form';
					$rootScope
						.$watch(
							'form.display',
							function(display) {
							    if (display
								    && (display !== currentDisplay)) {
								currentDisplay = display;
								if (display === 'form') {
								    $rootScope.form.components = originalComps;
								} else {
								    $rootScope.form.components = [ {
									type : 'panel',
									input : false,
									title : 'Page 1',
									theme : 'default',
									components : originalComps
								    } ];
								}
							    }
							});

				    });

			} ]);