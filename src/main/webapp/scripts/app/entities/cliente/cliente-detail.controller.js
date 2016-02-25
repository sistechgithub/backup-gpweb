'use strict';

angular.module('gpApp')
    .controller('ClienteDetailController', function ($scope, $rootScope, $stateParams, entity, Cliente) {
        $scope.cliente = entity;
        $scope.load = function (id) {
            Cliente.get({id: id}, function(result) {
                $scope.cliente = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:clienteUpdate', function(event, result) {
            $scope.cliente = result;
        });
        $scope.$on('$destroy', unsubscribe);
    });
