'use strict';

angular.module('gpApp')
	.controller('EstadoDeleteController', function($scope, $modalInstance, entity, Estado) {

        $scope.estado = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Estado.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });