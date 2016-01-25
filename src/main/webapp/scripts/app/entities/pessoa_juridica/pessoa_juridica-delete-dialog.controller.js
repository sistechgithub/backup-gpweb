'use strict';

angular.module('gpApp')
	.controller('Pessoa_juridicaDeleteController', function($scope, $modalInstance, entity, Pessoa_juridica) {

        $scope.pessoa_juridica = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Pessoa_juridica.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });