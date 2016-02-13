'use strict';

angular.module('gpApp')
    .controller('UserManagementController', function ($scope, User, UserSearch, ParseLinks) {
        $scope.users = [];
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN"];

        $scope.page = 0;
        $scope.loadAll = function () {
            User.query({page: $scope.page, per_page: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.users = result;
            });
        };
        
        $scope.search = function () {
        	UserSearch.query({query: $scope.searchQuery}, function(result) {
                $scope.users = result;
            }, function(response) {
                if(response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.setActive = function (user, isActivated) {
            user.activated = isActivated;
            User.update(user, function () {
                $scope.loadAll();
                $scope.clear();
            });
        };

        $scope.clear = function () {
            $scope.user = {
                id: null, login: null, firstName: null, lastName: null, email: null,
                activated: null, langKey: null, createdBy: null, createdDate: null,
                lastModifiedBy: null, lastModifiedDate: null, resetDate: null,
                resetKey: null, authorities: null
            };
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    });
