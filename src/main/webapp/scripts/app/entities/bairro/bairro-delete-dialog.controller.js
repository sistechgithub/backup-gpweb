'use strict';

angular.module('gpApp')
	.controller('BairroDeleteController', function($scope, $modalInstance, entity, Bairro) {

        $scope.bairro = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Bairro.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });