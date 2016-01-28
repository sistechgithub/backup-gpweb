'use strict';

angular.module('gpApp').controller('GrupoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Grupo', '$http',
        function($scope, $stateParams, $modalInstance, entity, Grupo, $http) {

        $scope.grupo = entity;
        $scope.load = function(id) {
            Grupo.get({id : id}, function(result) {
                $scope.grupo = result;
                
                /*
                rgAux1 =($scope.grupo.dtPromo != null);
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
        	$scope.grupo.nmGrupo = angular.uppercase($scope.grupo.nmGrupo);
        	
        	//Setting default currency values
        	if(($scope.grupo.vlComissao == null) || ($scope.grupo.vlComissao == undefined)){
        		$scope.grupo.vlComissao = 0.00;
        	};        	 
        	if(($scope.grupo.vlDesconto == null) || ($scope.grupo.vlDesconto == undefined)){
        		$scope.grupo.vlDesconto = 0.00;
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
