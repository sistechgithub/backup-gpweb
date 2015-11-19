'use strict';

angular.module('gpApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


