'use strict';

angular.module('gpApp')
    .controller('FabricanteController', function ($scope, $state, $modal, Fabricante, FabricanteSearch, ParseLinks) {
      
        $scope.fabricantes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Fabricante.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.fabricantes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            FabricanteSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.fabricantes = result;
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
            $scope.fabricante = {
                nm_fabricante: null,
                cd_cgc: null,
                cd_cgf: null,
                nn_numero: null,
                cs_complemento: null,
                cd_tel: null,
                cd_fax: null,
                fl_inativo: null,
                id: null
            };
        };
    });
