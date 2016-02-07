'use strict';

angular.module('gpApp').controller('PessoaDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Pessoa',
        function($scope, $stateParams, $modalInstance, entity, Pessoa) {

        $scope.pessoa = entity;
        $scope.load = function(id) {
            Pessoa.get({id : id}, function(result) {
                $scope.pessoa = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('gpApp:pessoaUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.pessoa.id != null) {
                Pessoa.update($scope.pessoa, onSaveFinished);
            } else {
                Pessoa.save($scope.pessoa, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
