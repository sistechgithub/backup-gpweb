'use strict';

angular.module('gpApp')
    .controller('TipoClienteController', function ($scope, $state, $modal, TipoCliente, TipoClienteSearch, ParseLinks) {
      
        $scope.tipoclientes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            TipoCliente.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.tipoclientes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            TipoClienteSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.tipoclientes = result;
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
            $scope.tipocliente = {
                    nome: null,
                    vlDescontoMax: null,
                    vlComissao: null,
                    diaFatura: null,
                    precoVenda: null,
                    vip: null,
                    bloqueado: null,
                    dataCadastro: null,
                    prazos: []
                };
        };
    });
