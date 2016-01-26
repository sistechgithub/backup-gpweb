'use strict';

angular.module('gpApp')
    .factory('Pessoa_fisicaSearch', function ($resource) {
        return $resource('api/_search/pessoa_fisicas/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
