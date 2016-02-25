'use strict';

angular.module('gpApp')
    .factory('UserSearch', function ($resource) {
        return $resource('api/_search/users/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
