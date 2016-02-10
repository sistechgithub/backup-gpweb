'use strict';

angular.module('gpApp')
    .controller('ClienteDetailController', function ($scope, $rootScope, $stateParams, entity, Cliente) {
        $scope.cliente = entity;
        $scope.load = function (id) {
            Cliente.get({id: id}, function(result) {
                $scope.cliente = result;
            });
        };
        $rootScope.$on('gpApp:clienteUpdate', function(event, result) {
            $scope.cliente = result;
        });
    });
