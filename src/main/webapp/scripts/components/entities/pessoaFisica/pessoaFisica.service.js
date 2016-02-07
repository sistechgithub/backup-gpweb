'use strict';

angular.module('gpApp')
    .factory('PessoaFisica', function ($resource, DateUtils) {
        return $resource('api/pessoaFisicas/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
