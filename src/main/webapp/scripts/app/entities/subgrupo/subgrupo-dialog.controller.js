'use strict';

angular.module('gpApp').controller('SubgrupoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Subgrupo',
        function($scope, $stateParams, $modalInstance, entity, Subgrupo) {

        $scope.subgrupo = entity;
        $scope.load = function(id) {
            Subgrupo.get({id : id}, function(result) {
                $scope.subgrupo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:subgrupoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };
        
        var onBeforeSaveOrUpdate = function(){
        	
        	//Setting to uppercase
        	$scope.subgrupo.nmSubGrupo = angular.uppercase($scope.subgrupo.nmSubGrupo);
        	
        	//Setting default currency values
        	if(($scope.subgrupo.vlCusto == null) || ($scope.subgrupo.vlCusto == undefined)){
        		$scope.subgrupo.vlCusto = 0.00;
        	};        	 
        	if(($scope.subgrupo.vlValor == null) || ($scope.subgrupo.vlValor == undefined)){
        		$scope.subgrupo.vlValor = 0.00;
        	};     
        };   

        $scope.save = function () {
            $scope.isSaving = true;
            
            onBeforeSaveOrUpdate(); //validations rules
            
            if ($scope.subgrupo.id != null) {
                Subgrupo.update($scope.subgrupo, onSaveSuccess, onSaveError);
            } else {
                Subgrupo.save($scope.subgrupo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
