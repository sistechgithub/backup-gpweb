'use strict';

angular.module('gpApp')
    .controller('PrazoDetailController', function ($scope, $rootScope, $stateParams, entity, Prazo) {
        $scope.prazo = entity;
        $scope.load = function (id) {
            Prazo.get({id: id}, function(result) {
                $scope.prazo = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:prazoUpdate', function(event, result) {
            $scope.prazo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
