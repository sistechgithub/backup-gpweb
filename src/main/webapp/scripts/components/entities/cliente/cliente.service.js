'use strict';

angular.module('gpApp')
    .factory('Cliente', function ($resource, DateUtils) {
        return $resource('api/clientes/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {                	
                    data = angular.fromJson(data);
                    data.pessoaFisica.dataNascimento = DateUtils.convertLocaleDateFromServer(data.pessoaFisica.dataNascimento);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {                	
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {                	
                    return angular.toJson(data);
                }
            }
        });
    });
