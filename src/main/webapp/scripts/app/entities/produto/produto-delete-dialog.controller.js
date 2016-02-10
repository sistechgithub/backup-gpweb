'use strict';

angular.module('gpApp')
	.controller('ProdutoDeleteController', function($scope, $modalInstance, entity, Produto) {

        $scope.produto = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Produto.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });