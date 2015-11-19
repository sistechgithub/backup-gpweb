'use strict';

angular.module('gpApp')
	.controller('SubgrupoDeleteController', function($scope, $modalInstance, entity, Subgrupo) {

        $scope.subgrupo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Subgrupo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });