'use strict';

angular.module('gpApp')
    .factory('ClienteSearch', function ($resource) {
        return $resource('api/_search/clientes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
