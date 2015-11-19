'use strict';

angular.module('gpApp')
    .factory('SubgrupoSearch', function ($resource) {
        return $resource('api/_search/subgrupos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
