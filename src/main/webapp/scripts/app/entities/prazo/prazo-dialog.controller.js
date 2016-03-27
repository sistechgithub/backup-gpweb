'use strict';

angular.module('gpApp').controller('PrazoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Prazo',
        function($scope, $stateParams, $modalInstance, entity, Prazo) {

        $scope.prazo = entity;    
        
        var prazoIntervalo = {
         		ordemIntervalo:null,
         		intervalo:null,
         		nome:null,
         		id:null
         		};   
        
        $scope.criarIntervalos = function(){
        	
	       if($scope.prazo.intervaloConfigurado){ 	
	        for (var i = 0; i < ($scope.prazo.qntParcelas-1); i++){	
	          if (!$scope.prazo.intervalos[i]){	        	
	        	prazoIntervalo = {
	             		ordemIntervalo: i+1,
	             		intervalo:null,
	             		nome:(i+1)+' - '+(i+2),
	             		id:null
	             		};
	        	$scope.prazo.intervalos[i] = prazoIntervalo;
	          }
	        }
	       }else{
	    	   $scope.prazo.intervalos = []; 
	       }
        }
        
        $scope.load = function(id) {
            Prazo.get({id : id}, function(result) {
                $scope.prazo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:prazoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };
        
        var onBeforeSaveOrUpdate = function(){
        	
        	//Setting to uppercase
        	$scope.prazo.nome = angular.uppercase($scope.prazo.nome);        	
             
        };      
        
        $scope.save = function () {
            $scope.isSaving = true;
            
            onBeforeSaveOrUpdate(); //validations rules
            
            if ($scope.prazo.id != null) {
            	Prazo.update($scope.prazo, onSaveSuccess, onSaveError);
            } else {
                Prazo.save($scope.prazo, onSaveSuccess, onSaveError);
            }
        };        
       
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
