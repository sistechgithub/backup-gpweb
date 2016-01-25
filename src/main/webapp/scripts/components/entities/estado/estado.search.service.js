'use strict';

angular.module('gpApp')
    .factory('EstadoSearch', function ($resource) {
        return $resource('api/_search/estados/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
