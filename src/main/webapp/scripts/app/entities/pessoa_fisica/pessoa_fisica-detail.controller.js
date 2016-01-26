'use strict';

angular.module('gpApp')
    .controller('Pessoa_fisicaDetailController', function ($scope, $rootScope, $stateParams, entity, Pessoa_fisica, Pessoa, Logradouro) {
        $scope.pessoa_fisica = entity;
        $scope.load = function (id) {
            Pessoa_fisica.get({id: id}, function(result) {
                $scope.pessoa_fisica = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:pessoa_fisicaUpdate', function(event, result) {
            $scope.pessoa_fisica = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
