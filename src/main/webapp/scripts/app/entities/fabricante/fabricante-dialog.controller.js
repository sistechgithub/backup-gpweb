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
				function($scope, $stateParams, $modalInstance, entity,
						Fabricante,LogradouroCep) {

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

					$scope.findcep = function(cep) {
						if (cep.length > 6) {
							LogradouroCep.get({
								cep : cep
							}, function(result) {
								if (result)
									$scope.fabricante.logradouro = result;
							});
						}
					};

					$scope.clear = function() {
						$modalInstance.dismiss('cancel');
					};
				} ]);
