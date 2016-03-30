'use strict';

angular.module('gpApp').directive('logradouro', function(ConsultaCep, $parse) {
    return {
        restrict: 'E',
        templateUrl: 'scripts/components/logradouro/logradouro-layout.html', 
        scope: {
        	entidade: '=' 
        },
        link: function ($scope, $element, $attrs){
        	
        	$scope.findcep = function() {            		
					
					if (($scope.entidade.logradouro.cep) && ($scope.entidade.logradouro.cep.length > 7)) {
						
						ConsultaCep.clear;
						
						/* Consultando endereço na base de dados*/
						ConsultaCep.getLogradouroByCep($scope.entidade.logradouro.cep).then(
								function(data){
									data = angular.fromJson(data);				
									$scope.entidade.logradouro = data.data;									
								},
								//caso o endereço não exista na base ele procura em api gratuita
								function(data){
									
									if (data.status == 404){
										ConsultaCep.getConsultaCepApi($scope.entidade.logradouro.cep).then(
										function(data){
										  if (!data.erro){
											data = angular.fromJson(data);		
											$scope.entidade.logradouro = data;											
										  }else{
											$scope.entidade.logradouro = null;												
										  }	
										});
									}
								}
						   );
					}else{
						$scope.entidade.logradouro = null;							
					}
			};	
        }
     }
});