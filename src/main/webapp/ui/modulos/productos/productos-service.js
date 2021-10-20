angular.module('productos').factory('ProductosService',function($http, URL_BASE){
	return {
		lista:function() {
			return $http.get(URL_BASE+'/productos');			
		},
		add:function(p) {
			return $http.post(URL_BASE+'/productos',p);
		},
		edit:function(p) {
			return $http.put(URL_BASE+'/productos',p);
		},
		remove:function(p) {
			return $http.delete(URL_BASE+'/productos/'+p.id);
		},
		listaRubros:function() {
			return $http.get(URL_BASE+'/rubros');			
		},
	};
});