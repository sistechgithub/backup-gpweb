'use strict';

angular.module('gpApp')
    .factory('LogradouroCep', function ($resource, DateUtils) {
        return $resource('api/logradouros/cep/:cep', {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
