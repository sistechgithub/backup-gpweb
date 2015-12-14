'use strict';

angular.module('gpApp')
    .factory('Bairro', function ($resource, DateUtils) {
        return $resource('api/bairros/:id', {}, {
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
