angular.module('productos').controller('Productos',
	function($scope,ProductosService, $uibModal, SweetAlert){
	$scope.titulo='Productos';

	$scope.lista=[];
	
	$scope.refresh = function() {
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
	}; //End refresh
	
	$scope.refresh();
	
	$scope.remove=function(p) {
		ProductosService.remove(p).then(
			function(resp){
				$scope.refresh();
				SweetAlert.swal( "Se eliminó correctamente el producto", "Eliminado OK");
			},
			function(err){
				SweetAlert.swal( "Problemas eliminando el producto",err, "Error");
			}
		);
	};
	$scope.addEdit=function(p) {
		var modalInstance = $uibModal.open({
			animation: true,
			backdrop: false,
			controllerAs: '$ctrl',
			size: 'large',
			controller: 'AgregaEditaProductoModal',
			templateUrl: 'ui/modulos/productos/add-edit-productos-modal.html',
			resolve: {
				producto: angular.copy(p)
			}
		});
		
		modalInstance.result.then(
			function(instancia){
				if(!p) { //Agregar
					ProductosService.add(instancia).then(
						function(resp){
							$scope.refresh();
							SweetAlert.swal( "Se agregó correctamente el producto", "Agregado OK");
						},
						function(err){
							SweetAlert.swal( "Problemas agregando el producto",err, "Error");
						}
					);
				} else { //Editar
					ProductosService.edit(instancia).then(
						function(resp){
							$scope.refresh();
							SweetAlert.swal( "Se editó correctamente el producto", "Agregado OK");
						},
						function(err){
							SweetAlert.swal( "Problemas editando el producto",err, "Error");
						}
					);
				}
			},
			function(){}
		);
	}; //End addEdit
});

angular.module('productos').controller('AgregaEditaProductoModal',
	function($uibModalInstance, producto){
		console.log(producto)
		var $ctrl=this;
		$ctrl.agregar=!producto;
		
		console.log('=>',$ctrl.agregar)
		
		if(!producto) {
			producto={descripcionExtendida:'', descripcion:'', precio: 0.0, enStock:false};
		}
		
		$ctrl.instancia=producto;
		
		console.log('==>',$ctrl.instancia)
		
		$ctrl.ok=function() {
			$uibModalInstance.close($ctrl.instancia);
		};
		$ctrl.volver=function() {
			$uibModalInstance.dismiss();
		};
		
		$ctrl.verGuardar=function() {
			return $ctrl.instancia.descripcion.length>2 && $ctrl.instancia.precio>0;
		};
	});
