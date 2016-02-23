'use strict';

angular.module('gpApp')
    .factory('ProdutoSearch', function ($resource) {
        return $resource('api/_search/produtos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
