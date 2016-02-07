'use strict';

angular.module('gpApp').controller('PessoaFisicaDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'PessoaFisica', 'Pessoa',
        function($scope, $stateParams, $modalInstance, entity, PessoaFisica, Pessoa) {

        $scope.pessoaFisica = entity;
        $scope.pessoas = Pessoa.query({filter: 'pessoafisica-is-null'});
        $scope.load = function(id) {
            PessoaFisica.get({id : id}, function(result) {
                $scope.pessoaFisica = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('gpApp:pessoaFisicaUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.pessoaFisica.id != null) {
                PessoaFisica.update($scope.pessoaFisica, onSaveFinished);
            } else {
                PessoaFisica.save($scope.pessoaFisica, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
