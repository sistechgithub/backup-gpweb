'use strict';

angular.module('gpApp')
    .factory('FabricanteSearch', function ($resource) {
        return $resource('api/_search/fabricantes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
