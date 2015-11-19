'use strict';

angular.module('gpApp')
	.controller('GrupoDeleteController', function($scope, $modalInstance, entity, Grupo) {

        $scope.grupo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Grupo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });