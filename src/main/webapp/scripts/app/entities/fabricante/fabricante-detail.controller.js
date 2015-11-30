'use strict';

angular.module('gpApp')
    .controller('FabricanteDetailController', function ($scope, $rootScope, $stateParams, entity, Fabricante) {
        $scope.fabricante = entity;
        $scope.load = function (id) {
            Fabricante.get({id: id}, function(result) {
                $scope.fabricante = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:fabricanteUpdate', function(event, result) {
            $scope.fabricante = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
