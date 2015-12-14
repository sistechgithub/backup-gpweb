'use strict';

angular.module('gpApp')
    .factory('CidadeSearch', function ($resource) {
        return $resource('api/_search/cidades/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
