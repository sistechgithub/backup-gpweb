'use strict';

angular.module('gpApp')
    .controller('NavtopController', function ($scope, $location, $state, $ocLazyLoad, Auth, Principal, ENV) {
        $scope.isAuthenticated = Principal.isAuthenticated;
        $scope.$state = $state;
        $scope.inProduction = ENV === 'prod';

        $scope.logout = function () {
            Auth.logout();
            $state.go('home');
        };
        $ocLazyLoad.load([{
		  files: ['assets/js/AdminLTE-NavTop.js'],
		  cache: false
		},{
		  files: ['assets/js/fullscreen.js'],
		  cache: false
		}]);
    });