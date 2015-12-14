'use strict';

angular.module('gpApp').controller('CidadeDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Cidade', 'Estado',
        function($scope, $stateParams, $modalInstance, entity, Cidade, Estado) {

        $scope.cidade = entity;
        $scope.estados = Estado.query();
        $scope.load = function(id) {
            Cidade.get({id : id}, function(result) {
                $scope.cidade = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:cidadeUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.cidade.id != null) {
                Cidade.update($scope.cidade, onSaveSuccess, onSaveError);
            } else {
                Cidade.save($scope.cidade, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
