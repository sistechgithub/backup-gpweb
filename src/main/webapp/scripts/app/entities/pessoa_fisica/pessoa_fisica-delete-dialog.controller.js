'use strict';

angular.module('gpApp')
	.controller('Pessoa_fisicaDeleteController', function($scope, $modalInstance, entity, Pessoa_fisica) {

        $scope.pessoa_fisica = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Pessoa_fisica.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });