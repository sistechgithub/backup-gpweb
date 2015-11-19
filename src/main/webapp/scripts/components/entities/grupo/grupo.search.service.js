'use strict';

angular.module('gpApp')
    .factory('GrupoSearch', function ($resource) {
        return $resource('api/_search/grupos/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
