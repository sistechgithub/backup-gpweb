'use strict';

angular.module('gpApp')
    .controller('PessoaController', function ($scope, Pessoa, PessoaSearch, ParseLinks) {
        $scope.pessoas = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Pessoa.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pessoas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            Pessoa.get({id: id}, function(result) {
                $scope.pessoa = result;
                $('#deletePessoaConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Pessoa.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePessoaConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.search = function () {
            PessoaSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pessoas = result;
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
            $scope.pessoa = {nome: null, id: null};
        };
    });
