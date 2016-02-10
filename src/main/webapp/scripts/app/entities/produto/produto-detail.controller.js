'use strict';

angular.module('gpApp')
    .controller('ProdutoDetailController', function ($scope, $rootScope, $stateParams, entity, Produto, Grupo) {
        $scope.produto = entity;
        $scope.load = function (id) {
            Produto.get({id: id}, function(result) {
                $scope.produto = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:produtoUpdate', function(event, result) {
            $scope.produto = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
