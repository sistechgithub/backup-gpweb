'use strict';

angular.module('gpApp')
    .controller('PessoaController', function ($scope, $state, $modal, Pessoa, PessoaSearch, ParseLinks) {
      
        $scope.pessoas = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Pessoa.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pessoas = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.search = function () {
            PessoaSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.pessoas = result;
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
            $scope.pessoa = {
                id_pessoa: null,
                nm_pessoa: null,
                cd_tel: null,
                cd_cel: null,
                cd_fax: null,
                id_logradouro: null,
                nm_numero: null,
                ds_complemento: null,
                ds_situacao: null,
                fl_fisica: null,
                ds_email: null,
                ds_obs: null,
                ds_historico: null,
                fl_vendedor: null,
                ds_inativo: null,
                fl_usuario: null,
                dt_cadastro: null,
                id: null
            };
        };
    });
