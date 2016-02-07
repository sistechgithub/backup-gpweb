'use strict';

angular.module('gpApp')
    .controller('PessoaFisicaDetailController', function ($scope, $rootScope, $stateParams, entity, PessoaFisica, Pessoa) {
        $scope.pessoaFisica = entity;
        $scope.load = function (id) {
            PessoaFisica.get({id: id}, function(result) {
                $scope.pessoaFisica = result;
            });
        };
        $rootScope.$on('gpApp:pessoaFisicaUpdate', function(event, result) {
            $scope.pessoaFisica = result;
        });
    });
