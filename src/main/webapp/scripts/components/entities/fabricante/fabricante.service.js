'use strict';

angular.module('gpApp')
    .factory('Fabricante', function ($resource, DateUtils) {
        return $resource('api/fabricantes/:id', {}, {
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
