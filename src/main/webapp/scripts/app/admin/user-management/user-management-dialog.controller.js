'use strict';

angular.module('gpApp').controller('UserManagementDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'User','Auth', 
        function($scope, $stateParams, $modalInstance, entity, User, Auth) {

        $scope.user = entity;
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN"];
        var onSaveSuccess = function (result) {
            $scope.isSaving = false;
            $modalInstance.close(result);
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.user.id != null) {
                User.update($scope.user, onSaveSuccess, onSaveError);
            } else {
                User.save($scope.user, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
        
        $scope.register = function () {
            if ($scope.user.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                $scope.user.langKey =  'pt' ;
                $scope.doNotMatch = null;
                $scope.error = null;
                $scope.errorUserExists = null;
                $scope.errorEmailExists = null;

                Auth.createAccount($scope.user).then(function () {
                    $scope.success = 'OK';
                    $scope.isSaving = false;
                    $modalInstance.close(true);
                }).catch(function (response) {
                    $scope.success = null;
                    $scope.isSaving = false;
                    if (response.status === 400 && response.data === 'login already in use') {
                        $scope.errorUserExists = 'ERROR';
                    } else if (response.status === 400 && response.data === 'e-mail address already in use') {
                        $scope.errorEmailExists = 'ERROR';
                    } else {
                        $scope.error = 'ERROR';
                    }
                });
            }
        };
}]);
