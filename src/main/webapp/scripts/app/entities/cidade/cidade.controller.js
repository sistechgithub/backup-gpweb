'use strict';

angular.module('gpApp')
    .controller('CidadeController', function ($scope, $state, $modal, Cidade, CidadeSearch, ParseLinks) {
      
        $scope.cidades = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Cidade.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.cidades = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            CidadeSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.cidades = result;
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
            $scope.cidade = {
                nm_cidade: null,
                id: null
            };
        };
    });
