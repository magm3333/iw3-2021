var app=angular.module('iw3',['ngRoute','productos']);


angular.module('iw3').controller('ControladorDiv1',function($scope){
	$scope.titulo="Hola desde el controller 1";
	
	$scope.datos={
		size:10
	};
	
});

app.controller('ControladorDiv2',function($scope){
	$scope.titulo="Hola desde el controller 2";

	
});

// Double binding
// Vista <-$scope-> Controller