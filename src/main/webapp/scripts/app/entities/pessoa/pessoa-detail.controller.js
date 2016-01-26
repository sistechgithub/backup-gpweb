'use strict';

angular.module('gpApp')
    .controller('PessoaDetailController', function ($scope, $rootScope, $stateParams, entity, Pessoa, Logradouro) {
        $scope.pessoa = entity;
        $scope.load = function (id) {
            Pessoa.get({id: id}, function(result) {
                $scope.pessoa = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:pessoaUpdate', function(event, result) {
            $scope.pessoa = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
