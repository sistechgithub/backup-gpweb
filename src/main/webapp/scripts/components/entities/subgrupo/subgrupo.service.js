'use strict';

angular.module('gpApp')
    .factory('Subgrupo', function ($resource, DateUtils) {
        return $resource('api/subgrupos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dt_operacao = DateUtils.convertLocaleDateFromServer(data.dt_operacao);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dt_operacao = DateUtils.convertLocaleDateToServer(data.dt_operacao);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dt_operacao = DateUtils.convertLocaleDateToServer(data.dt_operacao);
                    return angular.toJson(data);
                }
            }
        });
    });
