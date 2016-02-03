'use strict';

angular.module('gpApp')
	.controller('PessoaDeleteController', function($scope, $modalInstance, entity, Pessoa) {

        $scope.pessoa = entity;
        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Pessoa.delete({id: id},
                function () {
                    $modalInstance.close(true);
                });
        };

    });