'use strict';

angular.module('gpApp')
    .factory('Grupo', function ($resource, DateUtils) {
        return $resource('api/grupos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dt_promo = DateUtils.convertLocaleDateFromServer(data.dt_promo);
                    data.dt_operacao = DateUtils.convertLocaleDateFromServer(data.dt_operacao);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    //data.dt_promo = DateUtils.convertLocaleDateToServer(data.dt_promo);
                    data.dt_operacao = DateUtils.convertLocaleDateToServer(data.dt_operacao);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {                	
                    //data.dt_promo = DateUtils.convertLocaleDateToServer(data.dt_promo);                    
                    data.dt_operacao = DateUtils.convertLocaleDateToServer(data.dt_operacao);
                    return angular.toJson(data);
                }
            }
        });
    });
