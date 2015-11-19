'use strict';

angular.module('gpApp')
    .controller('SubgrupoDetailController', function ($scope, $rootScope, $stateParams, entity, Subgrupo) {
        $scope.subgrupo = entity;
        $scope.load = function (id) {
            Subgrupo.get({id: id}, function(result) {
                $scope.subgrupo = result;
            });
        };
        var unsubscribe = $rootScope.$on('gpApp:subgrupoUpdate', function(event, result) {
            $scope.subgrupo = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
