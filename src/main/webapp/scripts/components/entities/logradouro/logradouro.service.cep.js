'use strict';

angular.module('gpApp')
.factory('ConsuBairro', function($resource){
	return $resource('/api/bairros/getbycidadeestado/:bairroNome/:cidadeNome/:estadoNome', {}, {
        'get': {
            method: 'GET',
            transformResponse: function (data) {
            	if (data){
                    
            		data = angular.fromJson(data);
					
                    return data;
            	}
            }
        }
    });
})
.factory('ConsuCidade', function($resource){
	return $resource('/api/cidades/getbynome/:cidadeNome/:estadoNome', {}, {
        'get': {
            method: 'GET',
            transformResponse: function (data) {
            	if (data){
                    data = angular.fromJson(data);
                    return data;
            	}
            }
        }
    });
})
.factory('LogradouroCep', function ($resource) {
    return $resource('api/logradouros/cep/:cep', {}, {
        'get': {
            method: 'GET',
            transformResponse: function (data) {
            	if (data){
                    data = angular.fromJson(data);
                    return data;
            	}
            }
        }
    });
})
.factory('ConsultaCEPApi', function($resource){
	 return $resource('https://viacep.com.br/ws/:cep/json/', {}, {
	        'get': {
	            method: 'GET',
	            transformResponse: function(data){
	            	if (data){	   
	            		
	            		data = angular.fromJson(data);
						data.bairro = data.bairro.replace(/[á|ã|â|à]/gi, "a");
						data.bairro = data.bairro.replace(/[é|ê|è]/gi, "e");
						data.bairro = data.bairro.replace(/[í|ì|î]/gi, "i");
						data.bairro = data.bairro.replace(/[õ|ò|ó|ô]/gi, "o");
						data.bairro = data.bairro.replace(/[ú|ù|û]/gi, "u");
						data.bairro = data.bairro.replace(/[ç]/gi, "c");
						data.bairro = data.bairro.replace(/[ñ]/gi, "n");
						data.bairro = data.bairro.replace(/[á|ã|â]/gi, "a");

						data.localidade = data.localidade.replace(/[á|ã|â|à]/gi, "a");
						data.localidade = data.localidade.replace(/[é|ê|è]/gi, "e");
						data.localidade = data.localidade.replace(/[í|ì|î]/gi, "i");
						data.localidade = data.localidade.replace(/[õ|ò|ó|ô]/gi, "o");
						data.localidade = data.localidade.replace(/[ú|ù|û]/gi, "u");
						data.localidade = data.localidade.replace(/[ç]/gi, "c");
						data.localidade = data.localidade.replace(/[ñ]/gi, "n");
						data.localidade = data.localidade.replace(/[á|ã|â]/gi, "a");
						
	            		return data;
	            	}
				}
	        }
	    });
}
);

