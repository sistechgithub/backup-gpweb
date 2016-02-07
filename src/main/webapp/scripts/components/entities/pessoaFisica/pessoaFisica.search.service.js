'use strict';

angular.module('gpApp')
    .factory('PessoaFisicaSearch', function ($resource) {
        return $resource('api/_search/pessoaFisicas/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
