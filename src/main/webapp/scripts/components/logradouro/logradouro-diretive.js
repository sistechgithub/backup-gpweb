'use strict';

angular.module('gpApp')
    .directive('logradouro', function(ConsultaCep) {
        return {
            restrict: 'E',
            templateUrl: 'scripts/components/logradouro/logradouro-layout.html',
            controller: ['$scope',
                function($scope) {
            	/*Consulta de CEP
				 * Caso no cadastro de endereço existente no banco não conste o CEP informado
				 * o sistema irá procura-lo na internet numa consulta de CEP gratuita,
				 * caso não o encontre, o sistema irá permitir que o cliente entre com
				 * o endereço manualmente*/
            	$scope.findcep = function(valid, cepDom) {
            		console.log('teste diretiva cep');
					if (true){	
						$scope.cep = cepDom;
						if ($scope.cep.length > 7) {
							ConsultaCep.clear;
							ConsultaCep.getLogradouroByCep($scope.cep).then(
									function(data){
										data = angular.fromJson(data);
										$scope.entidade.logradouro = data.data;
									},
									function(data){
										if (data.status == 404){
											ConsultaCep.getConsultaCepApi($scope.cep).then(
											 function(data){
											  if (!data.erro){
												data = angular.fromJson(data);												
												$scope.entidade.logradouro = data;
											  }else{											  
												$scope.cepform.cep.$invalid = true;
											  }	
											});
										}
									}
							);
						}
					}
					};		
                }
            ],
            scope:{
            	entidade:"="
            }
        
        }
    });