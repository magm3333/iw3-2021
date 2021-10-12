angular.module('iw3').factory('CoreService',function($http,URL_BASE,$log){
	return {
		login: function(user) {
			const config={
				method:'POST',
				url: URL_BASE+'/login-json',
				headers : { 'Content-Type': 'application/x-www-form-urlencoded' },
				data: `username=${user.name}&password=${user.password}`
				//data: 'username='+user.name+'&password'+user.password
			};
			return $http(config);	
		},
		authInfo:function(){
			//$log.log()
			return $http.get(URL_BASE+"/auth-info");
		}
	};
});