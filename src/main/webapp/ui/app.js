var app=angular.module('iw3',[
	'ngRoute','productos',
	'ui.bootstrap','ngStorage','oitozero.ngSweetAlert',
	'ngSanitize', 'ngAnimate', 'ngTouch', 
	'chart.js', 'ngStomp', 'graficos'
	
	]);

app.constant('URL_BASE','http://localhost:8080');
app.constant('URL_WS','http://localhost:8080/api/v1/ws');

app.config(function($localStorageProvider){
	$localStorageProvider.setKeyPrefix('iw3/');
});

app.run(['$rootScope','$uibModal','CoreService','$location','$log','$localStorage', '$stomp',
	function($rootScope, $uibModal, CoreService, $location, $log, $localStorage, $stomp) {

	$rootScope.stomp=$stomp;

	$rootScope.relocate = function(loc) {
		$rootScope.oldLoc=$location.$$path;
		$location.path(loc);
	};
	
	$rootScope.userData=function() {
		return $localStorage.userdata;
	};
	
	$rootScope.logout=function() {
		CoreService.logout();
	};
	
	$rootScope.openLoginForm = function(size) {
		if (!$rootScope.loginOpen) {
			//$rootScope.cleanLoginData();
			$rootScope.loginOpen = true;
			$uibModal.open({
				animation : true,
				backdrop : 'static',
				keyboard : false,
				templateUrl : 'ui/vistas/login.html',
				controller : 'LoginController',
				size : size//,
				//resolve : {
				//	user : function() {
				//		return $rootScope.user;
				//	}
				//}
			});
		}
	};
	
	//$rootScope.openLoginForm();

	CoreService.authInfo();
	
} 
]);


/*
angular.module('iw3').controller('ControladorDiv1',function($scope){
	$scope.titulo="Hola desde el controller 1";
	
	$scope.datos={
		size:10
	};
	
});

app.controller('ControladorDiv2',function($scope){
	$scope.titulo="Hola desde el controller 2";

	
});
*/
// Double binding
// Vista <-$scope-> Controller