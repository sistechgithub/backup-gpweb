'use strict';

angular.module('gpApp')
	.controller('LogradouroDeleteController', function($scope, $modalInstance, entity, Logradouro) {

        $scope.logradouro = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Logradouro.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });