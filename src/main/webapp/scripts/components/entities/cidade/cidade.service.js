'use strict';

angular.module('gpApp')
    .factory('Cidade', function ($resource, DateUtils) {
        return $resource('api/cidades/:id', {}, {
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
