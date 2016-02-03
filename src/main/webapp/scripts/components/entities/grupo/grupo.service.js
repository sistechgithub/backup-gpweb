'use strict';

angular.module('gpApp')
    .factory('Grupo', function ($resource, DateUtils) {
        return $resource('api/grupos/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.dtPromo = DateUtils.convertLocaleDateFromServer(data.dtPromo);
                    data.dtOperacao = DateUtils.convertLocaleDateFromServer(data.dtOperacao);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    //data.dtPromo = DateUtils.convertLocaleDateToServer(data.dtPromo);
                    data.dtOperacao = DateUtils.convertLocaleDateToServer(data.dtOperacao);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {                	
                    //data.dtPromo = DateUtils.convertLocaleDateToServer(data.dtPromo);                    
                    data.dtOperacao = DateUtils.convertLocaleDateToServer(data.dtOperacao);
                    return angular.toJson(data);
                }
            }
        });
    });
