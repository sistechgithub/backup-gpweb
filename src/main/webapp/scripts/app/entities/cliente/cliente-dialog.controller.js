'use strict';

angular.module('gpApp').controller('ClienteDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Cliente',
        function($scope, $stateParams, $modalInstance, entity, Cliente) {

        $scope.cliente = entity;
        $scope.load = function(id) {
            Cliente.get({id : id}, function(result) {
                $scope.cliente = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('gpApp:clienteUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.cliente.id != null) {
                Cliente.update($scope.cliente, onSaveFinished);
            } else {
                Cliente.save($scope.cliente, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
