'use strict';

angular.module('gpApp')
    .factory('Pessoa_juridica', function ($resource, DateUtils) {
        return $resource('api/pessoa_juridicas/:id', {}, {
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
