'use strict';

angular.module('gpApp').controller('GrupoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Grupo',
        function($scope, $stateParams, $modalInstance, entity, Grupo) {

        $scope.grupo = entity;
        $scope.load = function(id) {
            Grupo.get({id : id}, function(result) {
                $scope.grupo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:grupoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.grupo.id != null) {
                Grupo.update($scope.grupo, onSaveSuccess, onSaveError);
            } else {
                Grupo.save($scope.grupo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
