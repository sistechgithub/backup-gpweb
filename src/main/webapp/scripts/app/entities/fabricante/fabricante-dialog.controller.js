'use strict';

angular.module('gpApp').controller(
		'FabricanteDialogController',
		[
				'$scope',
				'$stateParams',
				'$modalInstance',
				'entity',
				'Fabricante',
				'LogradouroCep',
				'ConsultaCEPApi',
				function($scope, $stateParams, $modalInstance, entity,
						Fabricante,LogradouroCep,ConsultaCEPApi) {
					
					var cep = '';
					$scope.fabricante = entity;
					$scope.load = function(id) {
						Fabricante.get({
							id : id
						}, function(result) {
							$scope.fabricante = result;
						});
					};

					var onSaveSuccess = function(result) {
						$scope.$emit('gpApp:fabricanteUpdate', result);
						$modalInstance.close(result);
						$scope.isSaving = false;
					};

					var onSaveError = function(result) {
						$scope.isSaving = false;
					};

					$scope.save = function() {
						$scope.isSaving = true;
						if ($scope.fabricante.id != null) {
							Fabricante.update($scope.fabricante, onSaveSuccess,
									onSaveError);
						} else {
							Fabricante.save($scope.fabricante, onSaveSuccess,
									onSaveError);
						}
					};

					/*Consulta de CEP
					 * Caso no cadastro de endereço existente no banco não conste o CEP informado
					 * o sistema irá procura-lo na internet numa consulta de CEP gratuita,
					 * caso não o encontre, o sistema irá permitir que o cliente entre com
					 * o endereço manualmente*/
					$scope.findcep = function(cep) {
						if (cep.length > 6) {
							LogradouroCep.get({
								cep : cep
							}, function(result) {
								if (result){									
									$scope.fabricante.logradouro = result;
									cep = '';
								}
							}, function(errorConsulta){
								if (errorConsulta.status == 404){
									ConsultaCEPApi.get({cep : cep}, function(data){
										console.log(data);
										$scope.fabricante.logradouro = data;
										cep = '';
									});
								}
							});							
						};
					};
					
//					/*Consulta de bairro
//					 * caso o CEP seja procurado na internet, o bairro que é retornado na consulta
//					 * é verificado no banco de dados se constatada sua existência o mesmo é retornado
//					 * para o cadastro de logradouro e salvo normalmente, caso não ele será salvo */
//					var consulBairro = function(bairroNome, cidadeNome, estadoSigla){
//						ConsuBairro.get({
//							bairroNome : bairroNome, 
//							cidadeNome : cidadeNome,
//							estadoNome : estadoSigla
//						}, function(resultBairro){
//							if(resultBairro){
//								$scope.fabricante.logradouro.bairro = resultBairro;
//								console.log(resultBairro);
//							}
//						});						
//					};

					$scope.clear = function() {
						$modalInstance.dismiss('cancel');
					};
				} ]);
