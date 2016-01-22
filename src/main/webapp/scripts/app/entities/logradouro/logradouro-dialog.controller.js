'use strict';

angular.module('gpApp').controller('LogradouroDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Logradouro', 'Bairro',
        function($scope, $stateParams, $modalInstance, entity, Logradouro, Bairro) {

        $scope.logradouro = entity;
        $scope.bairros = Bairro.query();
        $scope.load = function(id) {
            Logradouro.get({id : id}, function(result) {
                $scope.logradouro = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:logradouroUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.logradouro.id != null) {
                Logradouro.update($scope.logradouro, onSaveSuccess, onSaveError);
            } else {
                Logradouro.save($scope.logradouro, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
