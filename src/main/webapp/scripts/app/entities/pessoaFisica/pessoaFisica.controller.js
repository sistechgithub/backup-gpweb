'use strict';

angular.module('gpApp')
    .controller('PessoaFisicaController', function ($scope, PessoaFisica, PessoaFisicaSearch, ParseLinks) {
        $scope.pessoaFisicas = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            PessoaFisica.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pessoaFisicas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            PessoaFisica.get({id: id}, function(result) {
                $scope.pessoaFisica = result;
                $('#deletePessoaFisicaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            PessoaFisica.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePessoaFisicaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PessoaFisicaSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pessoaFisicas = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.pessoaFisica = {cpf: null, id: null};
        };
    });
