angular.module('productos').factory('ProductosService',function($http, URL_BASE){
	return {
		lista:function() {
			return $http.get(URL_BASE+'/productos');			
		}
	};
});