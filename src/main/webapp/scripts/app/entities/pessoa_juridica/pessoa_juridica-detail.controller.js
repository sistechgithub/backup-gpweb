'use strict';

angular.module('gpApp')
    .controller('Pessoa_juridicaDetailController', function ($scope, $rootScope, $stateParams, entity, Pessoa_juridica, Pessoa) {
        $scope.pessoa_juridica = entity;
        $scope.load = function (id) {
            Pessoa_juridica.get({id: id}, function(result) {
                $scope.pessoa_juridica = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:pessoa_juridicaUpdate', function(event, result) {
            $scope.pessoa_juridica = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
