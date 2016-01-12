'use strict';

angular.module('gpApp')
.service('ConsultaCep',function($http, $q){
	var getLogradouroByCep = '';
	var getConsultaCepApi = '';
	var endereco = $q.defer();
	
    this.getLogradouroByCep = function(cep){	    
		return $http.get('api/logradouros/cep/'+cep);
	};
	
	this.getConsultaCepApi = function(cep){			
		$http.get('https://viacep.com.br/ws/'+cep+'/json/').success(function(data){    		
        	if (data){	   
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
				
				console.log(data);
				
				$http.post('api/logradouros/'+cep+'/'+data.logradouro+'/'+data.bairro+'/'+data.localidade+
						'/'+data.uf).success(function(data){
							data = angular.fromJson(data);
							endereco.resolve(data);
						});
        	};		
    });
		return endereco.promise;
    };	
    
	
});
