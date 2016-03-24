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
					
					var onBeforeSaveOrUpdate = function(){
			        	
			        	//Setting to uppercase
			        	$scope.fabricante.nome = angular.uppercase($scope.fabricante.nome);
			        	
			        	//others validations here...
			        };   

					$scope.save = function() {
						$scope.isSaving = true;
						
						onBeforeSaveOrUpdate(); //validations rules
						
						if ($scope.fabricante.id != null) {
							Fabricante.update($scope.fabricante, onSaveSuccess,
									onSaveError);
						} else {
							Fabricante.save($scope.fabricante, onSaveSuccess,
									onSaveError);
						}
					};		

					$scope.clear = function() {
						$modalInstance.dismiss('cancel');
					};
					}
					
					
				]);
