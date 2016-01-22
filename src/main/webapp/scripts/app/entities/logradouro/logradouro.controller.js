'use strict';

angular.module('gpApp')
    .controller('LogradouroController', function ($scope, $state, $modal, Logradouro, LogradouroSearch, ParseLinks) {
      
        $scope.logradouros = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Logradouro.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.logradouros = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            LogradouroSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.logradouros = result;
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
            $scope.logradouro = {
                nm_logradouro: null,
                cd_dep: null,
                id: null
            };
        };
    });
