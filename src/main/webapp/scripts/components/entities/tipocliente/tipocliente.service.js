'use strict';

angular.module('gpApp')
    .factory('TipoCliente', function ($resource, DateUtils) {
        return $resource('api/tipoclientes/:id', {}, {
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
