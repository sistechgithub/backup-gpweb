'use strict';

angular.module('gpApp')
	.controller('FabricanteDeleteController', function($scope, $modalInstance, entity, Fabricante) {

        $scope.fabricante = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Fabricante.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });