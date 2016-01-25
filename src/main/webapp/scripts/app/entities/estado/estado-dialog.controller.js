'use strict';

angular.module('gpApp').controller('EstadoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Estado',
        function($scope, $stateParams, $modalInstance, entity, Estado) {

        $scope.estado = entity;
        $scope.load = function(id) {
            Estado.get({id : id}, function(result) {
                $scope.estado = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:estadoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.estado.id != null) {
                Estado.update($scope.estado, onSaveSuccess, onSaveError);
            } else {
                Estado.save($scope.estado, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
