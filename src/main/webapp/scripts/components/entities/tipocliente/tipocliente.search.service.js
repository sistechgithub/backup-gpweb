'use strict';

angular.module('gpApp')
    .factory('TipoClienteSearch', function ($resource) {
        return $resource('api/_search/tipoclientes/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
