'use strict';

angular.module('gpApp')
    .controller('NavbarController', function ($scope, $location, $state, $ocLazyLoad, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
    });