'use strict';

angular.module('gpApp')
    .controller('ClienteDeleteController', function($scope, $modalInstance, entity, Cliente) {

        $scope.cliente = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cliente.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });