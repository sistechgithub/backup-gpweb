'use strict';

angular.module('gpApp')
    .factory('PrazoSearch', function ($resource) {
        return $resource('api/_search/prazos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
