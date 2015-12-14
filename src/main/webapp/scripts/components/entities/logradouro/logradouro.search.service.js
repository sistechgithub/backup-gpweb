'use strict';

angular.module('gpApp')
    .factory('LogradouroSearch', function ($resource) {
        return $resource('api/_search/logradouros/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
