'use strict';

angular.module('gpApp')
	.controller('TipoClienteDeleteController', function($scope, $modalInstance, entity, TipoCliente) {

        $scope.tipocliente = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TipoCliente.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });