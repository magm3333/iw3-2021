angular.module('productos').controller('Productos',
	function($scope,ProductosService, $uibModal, SweetAlert, Notification){
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
		SweetAlert.swal({
				title: "Eliminar producto",
				text: "Está segur@ que desea eliminar el producto <strong>"+p.descripcion+"</strong>?",
				type: "warning",
			    showCancelButton: true,
				confirmButtonColor: "#DD6B55",
				confirmButtonText: "Si, eliminar producto!",
				cancelButtonText: "No",
				closeOnConfirm: true,
				html: true
			}, function(confirm){
				if(confirm) {
					ProductosService.remove(p).then(
						function(resp) {
							$scope.refresh();
							Notification.success({message:'El producto se ha eliminado',title:'Operación existosa!'});
						}, 
						function(err) {
							Notification.error({message:'No se ha podido eliminar el producto',title:'Operación fallida!'});
						}
					);
				}
			});
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
						function(resp) {
							$scope.refresh();
							Notification.success({message:'El producto se agregó correctamente',title:'Operación existosa!'});
						}, 
						function(err) {
							Notification.error({message:'No se ha podido agregar el producto',title:'Operación fallida!'});
						}
					);
				} else { //Editar
					ProductosService.edit(instancia).then(
						function(resp) {
							$scope.refresh();
							Notification.success({message:'El producto se modificó correctamente',title:'Operación existosa!'});
						}, 
						function(err) {
							Notification.error({message:'No se ha podido modificar el producto',title:'Operación fallida!'});
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
