'use strict';

angular.module('gpApp').controller(
		'FabricanteDialogController',
		[
				'$scope',
				'$stateParams',
				'$modalInstance',
				'entity',
				'Fabricante',
				function($scope, $stateParams, $modalInstance, entity,
						Fabricante) {
					
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
							console.log($scope.fabricante.logradouro);
							Fabricante.update($scope.fabricante, onSaveSuccess,
									onSaveError);
						} else {
							console.log($scope.fabricante.logradouro);
							Fabricante.save($scope.fabricante, onSaveSuccess,
									onSaveError);
						}
					};		

					$scope.clear = function() {
						$modalInstance.dismiss('cancel');
					};
					}
					
					
				]);
