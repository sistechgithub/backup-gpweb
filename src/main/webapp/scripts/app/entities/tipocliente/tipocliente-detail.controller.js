'use strict';

angular.module('gpApp')
    .controller('TipoClienteDetailController', function ($scope, $rootScope, $stateParams, entity, TipoCliente) {
        $scope.tipocliente = entity;
        $scope.load = function (id) {
            TipoCliente.get({id: id}, function(result) {
                $scope.tipocliente = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:tipoclienteUpdate', function(event, result) {
            $scope.tipocliente = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
