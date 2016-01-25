'use strict';

angular.module('gpApp').controller('Pessoa_juridicaDialogController',
    ['$scope', '$stateParams', '$modalInstance', '$q', 'entity', 'Pessoa_juridica', 'Pessoa',
        function($scope, $stateParams, $modalInstance, $q, entity, Pessoa_juridica, Pessoa) {

        $scope.pessoa_juridica = entity;
        $scope.id_pessoas = Pessoa.query({filter: 'pessoa_juridica-is-null'});
        $q.all([$scope.pessoa_juridica.$promise, $scope.id_pessoas.$promise]).then(function() {
            if (!$scope.pessoa_juridica.id_pessoa || !$scope.pessoa_juridica.id_pessoa.id) {
                return $q.reject();
            }
            return Pessoa.get({id : $scope.pessoa_juridica.id_pessoa.id}).$promise;
        }).then(function(id_pessoa) {
            $scope.id_pessoas.push(id_pessoa);
        });
        $scope.load = function(id) {
            Pessoa_juridica.get({id : id}, function(result) {
                $scope.pessoa_juridica = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('gpApp:pessoa_juridicaUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.pessoa_juridica.id != null) {
                Pessoa_juridica.update($scope.pessoa_juridica, onSaveSuccess, onSaveError);
            } else {
                Pessoa_juridica.save($scope.pessoa_juridica, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
