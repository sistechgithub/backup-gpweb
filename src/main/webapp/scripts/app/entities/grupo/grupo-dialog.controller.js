'use strict';

angular.module('gpApp').controller('GrupoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Grupo', '$http',
        function($scope, $stateParams, $modalInstance, entity, Grupo, $http) {

        $scope.grupo = entity;
        $scope.load = function(id) {
            Grupo.get({id : id}, function(result) {
                $scope.grupo = result;
                
                /*
                rgAux1 =($scope.grupo.dt_promo != null);
                console.log('Value for rgAux1 = ' + rgAux1);
                */
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:grupoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };
        
        var onBeforeSaveOrUpdate = function(){
        	
        	//Setting to uppercase
        	$scope.grupo.nm_grupo = angular.uppercase($scope.grupo.nm_grupo);
        	
        	//Setting default currency values
        	if(($scope.grupo.vl_comissao == null) || ($scope.grupo.vl_comissao == undefined)){
        		$scope.grupo.vl_comissao = 0.00;
        	};        	 
        	if(($scope.grupo.vl_desconto == null) || ($scope.grupo.vl_desconto == undefined)){
        		$scope.grupo.vl_desconto = 0.00;
        	};
             
        };        
      

        $scope.save = function () {
            $scope.isSaving = true;
            
            onBeforeSaveOrUpdate(); //validations rules
            
            if ($scope.grupo.id != null) {
                Grupo.update($scope.grupo, onSaveSuccess, onSaveError);
            } else {
                Grupo.save($scope.grupo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
