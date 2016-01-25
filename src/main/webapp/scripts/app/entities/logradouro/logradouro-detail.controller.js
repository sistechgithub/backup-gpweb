'use strict';

angular.module('gpApp')
    .controller('LogradouroDetailController', function ($scope, $rootScope, $stateParams, entity, Logradouro, Bairro) {
        $scope.logradouro = entity;
        $scope.load = function (id) {
            Logradouro.get({id: id}, function(result) {
                $scope.logradouro = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:logradouroUpdate', function(event, result) {
            $scope.logradouro = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
