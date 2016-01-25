'use strict';

angular.module('gpApp')
    .factory('Pessoa_juridicaSearch', function ($resource) {
        return $resource('api/_search/pessoa_juridicas/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
