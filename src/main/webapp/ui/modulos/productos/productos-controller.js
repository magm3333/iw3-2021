angular.module('productos').controller('Productos',function($scope,ProductosService){
	$scope.titulo='Productos';
	$scope.textoBuscado='le';
	$scope.lista=[];
	
	ProductosService.lista().then(
		function(resp){
			if(resp.status==200) {
				$scope.lista=resp.data;
			}
			console.log(resp);
		},
		function(err){
			console.log(err);
		}
	);
	
});