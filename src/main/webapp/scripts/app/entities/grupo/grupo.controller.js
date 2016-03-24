'use strict';

angular.module('gpApp')
    .controller('GrupoController', function ($scope, $state, $modal, Grupo, GrupoSearch, ParseLinks) {
          	
        $scope.grupos = [];
        $scope.page = 0;
        
        // Fields that will be used on the directive search
        $scope.fieldsSearch = [ {
			desc : 'Código',
			value : 'codigo'
		}, {
			desc : 'Descrição',
			value : 'descricao'
		} ];              
        
        $scope.loadAll = function() {
            Grupo.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));                
                $scope.grupos = result;
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

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.grupo = {
                nmGrupo: null,
                vlComissao: null,
                vlDesconto: null,
                flPromo: false,
                flDesco: false,
                dtPromo: new Date(),                
                flSemcontagem: false,
                flEnvio: false,
                nnNovo: 1,
                nnType: 1,
                nnDay: 1,
                nmDayweek: 'SABADOS',
                id: null
            };    
        };
    });
