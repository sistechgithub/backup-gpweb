'use strict';

angular.module('gpApp')
.service('factoryCep',function(Logradouro, Bairro, Cidade, ConsultaCep){
	
	var getEnderecoByCep = {};
	
    this.getEnderecoByCep =  function(cep){
    	
    	var endereNovo    = {nome : '',  bairro : {}, cep : ''};
    	var bairroNovo    = {nome : '', cidade : {}};
    	var endereApi     = {};
    	var endereRetorno = {id : '', nome : '',  bairro : {}, cep : ''}; 
    	
    	ConsultaCep.getLogradouroByCep(cep)
    	.then(
    			
    		function(data){//consulta no banco com o cep
    			data = angular.fromJson(data);
    			return data; //retorno do resultado caso o endereço já esteja cadastrado no banco
    	})
    	.catch(
    			
    		function(data){ //função para cadastrar o endereço caso ele não exista no banco de dados
    			if (data.status == 404){
    				ConsultaCep.getConsultaCepApi(cep)
    				.success(
    					
    					function(data){
    						endereApi = data;
    						
    						endereNovo.nome = data.logradouro;
    						endereNovo.cep = cep;
    						bairroNovo.nome = data.bairro;
    						
    						ConsultaCep.getBairro(endereApi.bairro,endereApi.localidade,endereApi.uf)
    						.then(
    							function(data){
    								data = angular.fromJson(data);
    								endereNovo.bairro = data.data;
    								Logradouro.save(endereNovo,function(data){endereNovo = data;return data;});    
    								return endereNovo;
    							}	
    						).catch(
    							function(data){
    								if (data.status == 404){
    									ConsultaCep.getCidade(endereApi.localidade,endereApi.uf)
    									.then(
    										function(data){
    		    								data = angular.fromJson(data);
    											bairroNovo.cidade = data.data;
    											Bairro.save(bairroNovo,function(data){
    												data = angular.fromJson(data);
    												endereNovo.bairro = data;
    												Logradouro.save(endereNovo,function(data){endereNovo = data;return data;});
    												});
    											return endereNovo;
    										}	
    									).catch();
    								}
    							}	
    						);
    					})
    				.catch();
    			}
    		}	
    	);
    	return endereNovo;
    };
});
