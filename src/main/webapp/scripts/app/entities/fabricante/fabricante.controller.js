'use strict';

angular.module('gpApp')
    .controller('FabricanteController', function ($scope, $state, $modal, Fabricante, FabricanteSearch, ParseLinks) {
      
        $scope.fabricantes = [];
        $scope.page = 0;
        
        // Fields that will be used on the directive search
        $scope.fieldsSearch = [ 
	        {
				desc : 'Código',
				value : 'codigo'
			}, {
				desc : 'Descrição',
				value : 'descricao'
			}, {
				desc : 'Cidade',
				value : 'cidade'
			}, {
				desc : 'Fone',
				value : 'fone'
			}, {
				desc : 'Inativo',
				value : 'inativo'
			}        
		];
        
        $scope.loadAll = function() {
            Fabricante.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.fabricantes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
           
            if(!($scope.valueSearch === '')){
            	$scope.search();
            }else{
                $scope.loadAll();            	
            };
            
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
                nome: null,
                cnpj: null,
                ie: null,
                numero: null,
                complemento: null,
                telefone: null,
                fax: null,
                inativo: null,
                id: null,
                logradouro: null
            };
        };
    });
