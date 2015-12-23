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
.factory('LogradouroCep', function ($resource, DateUtils) {
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
.factory('ConsultaCEPApi', function($resource, DateUtils, ConsuBairro, ConsuCidade, Logradouro, Bairro){
	 return $resource('https://viacep.com.br/ws/:cep/json/', {}, {
	        'get': {
	            method: 'GET',
	            transformResponse: function(data){
					var logra = {id : null, nome : '', cep : '', bairro : {}};
					var nome = '';
					var cep = '';
					var bairro = '';
					var cidade = '';
					var estado = '';
					if(!data.erro){
						data = angular.fromJson(data);
						
						nome   = data.logradouro;
						cep    = data.cep.replace('-','');
						bairro = data.bairro;
						cidade = data.localidade;
						estado = data.uf;						

						logra.id = null;
						logra.cep = cep;
						logra.nome = nome;
						
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
						
						ConsuBairro.get({
							bairroNome : data.bairro, 
							cidadeNome : data.localidade, 
							estadoNome : data.uf
							},//se existir o bairo, o cadastro do logradouro já é realizado 
							function(resultBairro){
								if(resultBairro){
									logra.bairro = resultBairro;
						            Logradouro.save(logra, function(data){
					                    data = angular.fromJson(data);
						            	return logra;
						            });
								}
							},//se não ele realizado o cadastro do bairro
							function(errorBairro){
								if (errorBairro.status == 404){
									ConsuCidade.get({
										cidadeNome : cidade,
										estadoNome : estado},
										function(resultCidade){
											logra.bairro.nome = bairro;
											logra.bairro.cidade = resultCidade;
											Bairro.save(logra.bairro);
											 Logradouro.save(logra, function(data){
								                    data = angular.fromJson(data);
									            	logra = data;
									            });
										});
								}
							});	
						return logra;
					}
				}
	        }
	    });
}
);

