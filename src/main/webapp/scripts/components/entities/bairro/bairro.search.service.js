'use strict';

angular.module('gpApp')
    .factory('BairroSearch', function ($resource) {
        return $resource('api/_search/bairros/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
