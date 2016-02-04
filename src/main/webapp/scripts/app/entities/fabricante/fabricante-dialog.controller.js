'use strict';

angular.module('gpApp').controller(
		'FabricanteDialogController',
		[
				'$scope',
				'$stateParams',
				'$modalInstance',
				'entity',
				'Fabricante',
				'ConsultaCep',
				function($scope, $stateParams, $modalInstance, entity,
						Fabricante, ConsultaCep) {
					
					$scope.cep = '';
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

					$scope.findcep = function(valid, cepDom) {
					if (valid){	
						$scope.cep = cepDom;
						if ($scope.cep.length > 6) {
							ConsultaCep.clear;
							ConsultaCep.getLogradouroByCep($scope.cep).then(
									function(data){
										data = angular.fromJson(data);
										$scope.fabricante.logradouro = data.data;
									},
									function(data){
										if (data.status == 404){
											ConsultaCep.getConsultaCepApi($scope.cep).then(function(data){
												data = angular.fromJson(data);
												console.log(data);
												$scope.fabricante.logradouro = data;
											});
										}
									}
							);
						}
					};			

					$scope.clear = function() {
						$modalInstance.dismiss('cancel');
					};
					}
				} ]);
