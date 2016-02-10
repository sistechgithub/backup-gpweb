'use strict';

angular.module('gpApp')
    .controller('ProdutoController', function ($scope, $state, $modal, Produto, ProdutoSearch, ParseLinks) {
      
        $scope.produtos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Produto.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.produtos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            ProdutoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.produtos = result;
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
            $scope.produto = {
                nmProduto: null,
                vlCusto: null,
                qtSaldo: null,
                vlVenda: null,
                id: null
            };
        };
    });
