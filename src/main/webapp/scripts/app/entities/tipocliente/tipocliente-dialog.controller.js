'use strict';

angular.module('gpApp').controller('TipoClienteDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'TipoCliente', 'Prazo',
        function($scope, $stateParams, $modalInstance, entity, TipoCliente, Prazo) {

    	$scope.tipocliente = [];


    	$scope.dias = [];
    	
    	var listarDias = function(){
    		for(var i = 0; i <= 31; i++){
    			$scope.dias[i] = i;
    		}
    	}
    	
    	listarDias();    	
    	
        $scope.prazos = Prazo.query();
        
    	$scope.$watch('prazo', function(){
            $scope.tipocliente = entity;
    	});
        
        $scope.load = function(id) {
            TipoCliente.get({id : id}, function(result) {
                $scope.tipocliente = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:tipoclienteUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            
            if ($scope.tipocliente.id != null) {
            	TipoCliente.update($scope.tipocliente, onSaveSuccess, onSaveError);
            } else {
                TipoCliente.save($scope.tipocliente, onSaveSuccess, onSaveError);
            }
        };        
       
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
