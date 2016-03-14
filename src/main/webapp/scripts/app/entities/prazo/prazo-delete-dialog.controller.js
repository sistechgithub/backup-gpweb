'use strict';

angular.module('gpApp')
	.controller('PrazoDeleteController', function($scope, $modalInstance, entity, Prazo) {

        $scope.prazo = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Prazo.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });