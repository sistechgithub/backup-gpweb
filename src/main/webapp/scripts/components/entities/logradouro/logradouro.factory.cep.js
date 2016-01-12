'use strict';

angular.module('gpApp')
.service('Cep',function(Logradouro, Bairro, Cidade, ConsultaCep){
	
	var getEnderecoByCep = {};
	
    this.getEnderecoByCep =  function(cep){
    	
    	var endereNovo    = {};
    	var bairroNovo    = {};
    	var endereApi     = {};
    	
    	if((endereNovo = ConsultaCep.getLogradouroByCep(cep)).status =! 404){
    		
    		return endereNovo;
    		
    	}else if((endereApi = ConsultaCep.getConsultaCepApi(cep)).bairro){
    		
    		endereNovo = {};
    		endereNovo.logradouro = endereApi.logradouro;
    		bairroNovo.nome = endereApi.bairro;
    		
    		if ((endereNovo = ConsultaCep.getBairro(endereNovo, endereApi.bairro, endereApi.localidade, endereApi.uf)).status != 404){
    			
    			return endereNovo;
    			
    		}else if (ConsultaCep.getCidade(bairroNovo, endereApi.localidade, endereApi.uf).status != 404){
    			
    			return getEnderecoByCep(cep);
    		}
    	}
    	
    };
});
