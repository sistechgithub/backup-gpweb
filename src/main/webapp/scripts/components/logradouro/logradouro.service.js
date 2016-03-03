'use strict';

angular.module('gpApp')
    .factory('Logradouro', function ($resource, DateUtils) {
        return $resource('api/logradouros/:id', {}, {
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
