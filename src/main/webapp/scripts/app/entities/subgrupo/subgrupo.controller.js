'use strict';

angular.module('gpApp')
    .controller('SubgrupoController', function ($scope, $state, $modal, Subgrupo, SubgrupoSearch, ParseLinks) {
      
        $scope.subgrupos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Subgrupo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.subgrupos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            SubgrupoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.subgrupos = result;
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
            $scope.subgrupo = {
            		nmSubGrupo: null,
                    vlCusto: null,
                    vlValor: null,
                    flEnvio: false,
                    nnNovo: 1,
                    id: null	
            };
        };
    });
