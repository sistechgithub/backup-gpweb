'use strict';

angular.module('gpApp')
    .controller('CidadeDetailController', function ($scope, $rootScope, $stateParams, entity, Cidade, Estado) {
        $scope.cidade = entity;
        $scope.load = function (id) {
            Cidade.get({id: id}, function(result) {
                $scope.cidade = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:cidadeUpdate', function(event, result) {
            $scope.cidade = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
