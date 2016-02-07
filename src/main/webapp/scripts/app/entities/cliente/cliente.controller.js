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

        $scope.delete = function (id) {
            Cliente.get({id: id}, function(result) {
                $scope.cliente = result;
                $('#deleteClienteConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Cliente.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteClienteConfirmation').modal('hide');
                    $scope.clear();
                });
        };

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
            $scope.cliente = {id: null};
        };
    });
