'use strict';

angular.module('gpApp')
    .controller('EstadoController', function ($scope, $state, $modal, Estado, EstadoSearch, ParseLinks) {
      
        $scope.estados = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Estado.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.estados = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            EstadoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.estados = result;
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
            $scope.estado = {
                nm_estado: null,
                sg_estado: null,
                ds_pais: null,
                id: null
            };
        };
    });
