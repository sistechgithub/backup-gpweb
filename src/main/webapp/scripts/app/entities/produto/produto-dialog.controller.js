'use strict';

angular.module('gpApp').controller('ProdutoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Produto', 'Grupo',
        function($scope, $stateParams, $modalInstance, entity, Produto, Grupo) {

        $scope.produto = entity;
        $scope.grupos = Grupo.query();
        $scope.load = function(id) {
            Produto.get({id : id}, function(result) {
                $scope.produto = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:produtoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };
        
        var onBeforeSaveOrUpdate = function(){
        	
        	//Setting to uppercase
        	$scope.produto.nmProduto = angular.uppercase($scope.produto.nmProduto);
        	
        	//Setting default currency values
        	if(($scope.produto.vlCusto == null) || ($scope.produto.vlCusto == undefined)){
        		$scope.produto.vlCusto = 0.00;
        	};        	 
        	if(($scope.produto.qtSaldo == null) || ($scope.produto.qtSaldo == undefined)){
        		$scope.produto.qtSaldo = 0.00;
        	};
        	if(($scope.produto.vlVenda == null) || ($scope.produto.vlVenda == undefined)){
        		$scope.produto.vlVenda = 0.00;
        	};  
        };

        $scope.save = function () {
            $scope.isSaving = true;
            
            onBeforeSaveOrUpdate(); //validations rules
            
            if ($scope.produto.id != null) {
                Produto.update($scope.produto, onSaveSuccess, onSaveError);
            } else {
                Produto.save($scope.produto, onSaveSuccess, onSaveError);
            }
        };        
       
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
