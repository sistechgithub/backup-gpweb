'use strict';

angular.module('gpApp')
    .controller('PrazoController', function ($scope, $state, $modal, Prazo, PrazoSearch, ParseLinks) {
      
        $scope.prazos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Prazo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.prazos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PrazoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.prazos = result;
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
            $scope.prazo = {
                    nome: null,
                    qntparcelas: null,
                    entrada: null,
                    ajuste: null,
                    intervalo: null,
                    juros: null, 
                    valorminimo:null,
                    intervaloConfigurado:null
                };
        };
    });
