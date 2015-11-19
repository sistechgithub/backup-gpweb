'use strict';

angular.module('gpApp').controller('SubgrupoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Subgrupo',
        function($scope, $stateParams, $modalInstance, entity, Subgrupo) {

        $scope.subgrupo = entity;
        $scope.load = function(id) {
            Subgrupo.get({id : id}, function(result) {
                $scope.subgrupo = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:subgrupoUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.subgrupo.id != null) {
                Subgrupo.update($scope.subgrupo, onSaveSuccess, onSaveError);
            } else {
                Subgrupo.save($scope.subgrupo, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
