'use strict';

angular.module('gpApp')
    .controller('EstadoDetailController', function ($scope, $rootScope, $stateParams, entity, Estado) {
        $scope.estado = entity;
        $scope.load = function (id) {
            Estado.get({id: id}, function(result) {
                $scope.estado = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:estadoUpdate', function(event, result) {
            $scope.estado = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
