'use strict';

angular.module('gpApp')
    .controller('ClienteController', function ($scope, Cliente, ClienteSearch, ParseLinks) {
        
        $scope.clientes = [];
        $scope.page = 1;
        $scope.loadAll = function() {
            Cliente.query({page: $scope.page, per_page: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.clientes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();        


        $scope.search = function () {
            ClienteSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.clientes = result;
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
            $scope.cliente = {
                id: null
            };
        };
    });
