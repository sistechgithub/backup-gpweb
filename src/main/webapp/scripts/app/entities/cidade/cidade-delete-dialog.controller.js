'use strict';

angular.module('gpApp')
	.controller('CidadeDeleteController', function($scope, $modalInstance, entity, Cidade) {

        $scope.cidade = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Cidade.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });