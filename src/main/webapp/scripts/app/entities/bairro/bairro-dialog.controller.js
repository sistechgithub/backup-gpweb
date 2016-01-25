'use strict';

angular.module('gpApp').controller('BairroDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Bairro', 'Cidade',
        function($scope, $stateParams, $modalInstance, entity, Bairro, Cidade) {

        $scope.bairro = entity;
        $scope.cidades = Cidade.query();
        $scope.load = function(id) {
            Bairro.get({id : id}, function(result) {
                $scope.bairro = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:bairroUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.bairro.id != null) {
                Bairro.update($scope.bairro, onSaveSuccess, onSaveError);
            } else {
                Bairro.save($scope.bairro, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
