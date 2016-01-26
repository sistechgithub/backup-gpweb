'use strict';

angular.module('gpApp').controller('Pessoa_fisicaDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Pessoa_fisica', 'Pessoa', 'Logradouro',
        function($scope, $stateParams, $modalInstance, $q, entity, Pessoa_fisica, Pessoa, Logradouro) {

        $scope.pessoa_fisica = entity;
        $scope.id_pessoas = Pessoa.query({filter: 'pessoa_fisica-is-null'});
        $q.all([$scope.pessoa_fisica.$promise, $scope.id_pessoas.$promise]).then(function() {
            if (!$scope.pessoa_fisica.id_pessoa || !$scope.pessoa_fisica.id_pessoa.id) {
                return $q.reject();
            }
            return Pessoa.get({id : $scope.pessoa_fisica.id_pessoa.id}).$promise;
        }).then(function(id_pessoa) {
            $scope.id_pessoas.push(id_pessoa);
        });
        $scope.logradouros = Logradouro.query();
        $scope.load = function(id) {
            Pessoa_fisica.get({id : id}, function(result) {
                $scope.pessoa_fisica = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:pessoa_fisicaUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.pessoa_fisica.id != null) {
                Pessoa_fisica.update($scope.pessoa_fisica, onSaveSuccess, onSaveError);
            } else {
                Pessoa_fisica.save($scope.pessoa_fisica, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
