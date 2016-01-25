'use strict';

angular.module('gpApp')
    .controller('BairroController', function ($scope, $state, $modal, Bairro, BairroSearch, ParseLinks) {
      
        $scope.bairros = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Bairro.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.bairros = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            BairroSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.bairros = result;
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
            $scope.bairro = {
                nm_bairro: null,
                id: null
            };
        };
    });
