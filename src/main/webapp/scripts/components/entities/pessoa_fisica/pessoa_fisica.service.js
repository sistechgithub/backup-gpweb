'use strict';

angular.module('gpApp')
    .factory('Pessoa_fisica', function ($resource, DateUtils) {
        return $resource('api/pessoa_fisicas/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dt_nascimento = DateUtils.convertLocaleDateFromServer(data.dt_nascimento);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.dt_nascimento = DateUtils.convertLocaleDateToServer(data.dt_nascimento);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.dt_nascimento = DateUtils.convertLocaleDateToServer(data.dt_nascimento);
                    return angular.toJson(data);
                }
            }
        });
    });
