'use strict';

angular.module('gpApp').controller('PessoaDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Pessoa', 'Logradouro',
        function($scope, $stateParams, $modalInstance, entity, Pessoa, Logradouro) {

        $scope.pessoa = entity;
        $scope.logradouros = Logradouro.query();
        $scope.load = function(id) {
            Pessoa.get({id : id}, function(result) {
                $scope.pessoa = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:pessoaUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.pessoa.id != null) {
                Pessoa.update($scope.pessoa, onSaveSuccess, onSaveError);
            } else {
                Pessoa.save($scope.pessoa, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
