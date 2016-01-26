'use strict';

angular.module('gpApp')
    .controller('Pessoa_juridicaController', function ($scope, $state, $modal, Pessoa_juridica, Pessoa_juridicaSearch, ParseLinks) {
      
        $scope.pessoa_juridicas = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Pessoa_juridica.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pessoa_juridicas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            Pessoa_juridicaSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pessoa_juridicas = result;
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
            $scope.pessoa_juridica = {
                cd_cgc: null,
                cd_cgf: null,
                nm_fantasia: null,
                cd_cnpj: null,
                ds_responsavel: null,
                ds_obs: null,
                id: null
            };
        };
    });
