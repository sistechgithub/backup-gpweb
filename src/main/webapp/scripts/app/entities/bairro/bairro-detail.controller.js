'use strict';

angular.module('gpApp')
    .controller('BairroDetailController', function ($scope, $rootScope, $stateParams, entity, Bairro, Cidade) {
        $scope.bairro = entity;
        $scope.load = function (id) {
            Bairro.get({id: id}, function(result) {
                $scope.bairro = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:bairroUpdate', function(event, result) {
            $scope.bairro = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
