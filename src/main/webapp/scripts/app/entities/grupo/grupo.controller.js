'use strict';

angular.module('gpApp')
    .controller('GrupoController', function ($scope, $state, $modal, Grupo, GrupoSearch, ParseLinks) {
      
        $scope.grupos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Grupo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.grupos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            GrupoSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.grupos = result;
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
            $scope.grupo = {
                nm_grupo: null,
                vl_comissao: null,
                vl_desconto: null,
                fl_promo: false,
                dt_promo: new Date(),                
                fl_semcontagem: false,
                fl_envio: false,
                nn_novo: 1,
                nn_type: 1,
                nn_day: 1,
                nm_dayweek: 'SABADOS',
                id: null
            };    
        };
    });
