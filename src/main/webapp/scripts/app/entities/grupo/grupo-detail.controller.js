'use strict';

angular.module('gpApp')
    .controller('GrupoDetailController', function ($scope, $rootScope, $stateParams, entity, Grupo) {
        $scope.grupo = entity;
        $scope.load = function (id) {
            Grupo.get({id: id}, function(result) {
                $scope.grupo = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:grupoUpdate', function(event, result) {
            $scope.grupo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
