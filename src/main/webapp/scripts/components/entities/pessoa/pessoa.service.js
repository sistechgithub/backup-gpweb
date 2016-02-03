'use strict';

angular.module('gpApp')
    .factory('Pessoa', function ($resource, DateUtils) {
        return $resource('api/pessoas/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dt_cadastro = DateUtils.convertLocaleDateFromServer(data.dt_cadastro);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dt_cadastro = DateUtils.convertLocaleDateToServer(data.dt_cadastro);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dt_cadastro = DateUtils.convertLocaleDateToServer(data.dt_cadastro);
                    return angular.toJson(data);
                }
            }
        });
    });
