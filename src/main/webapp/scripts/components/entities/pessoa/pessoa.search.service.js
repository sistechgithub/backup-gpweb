'use strict';

angular.module('gpApp')
    .factory('PessoaSearch', function ($resource) {
        return $resource('api/_search/pessoas/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
