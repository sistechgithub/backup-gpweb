'use strict';

angular.module('gpApp')
    .controller('Pessoa_fisicaController', function ($scope, $state, $modal, Pessoa_fisica, Pessoa_fisicaSearch, ParseLinks) {
      
        $scope.pessoa_fisicas = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Pessoa_fisica.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pessoa_fisicas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            Pessoa_fisicaSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pessoa_fisicas = result;
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
            $scope.pessoa_fisica = {
                dt_nascimento: null,
                cd_rg: null,
                cd_cpf: null,
                nm_pai: null,
                nm_mae: null,
                ds_estcivil: null,
                nm_conjuge: null,
                ds_profissao: null,
                ds_localtrab: null,
                ds_complemento: null,
                ds_apelido: null,
                ds_obs: null,
                id: null
            };
        };
    });
