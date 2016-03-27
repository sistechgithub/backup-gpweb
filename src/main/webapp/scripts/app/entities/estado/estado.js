'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('estado', {
                parent: 'entity',
                url: '/estados',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Estados'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/estado/estados.html',
                        controller: 'EstadoController'
                    }
                },
                resolve: {
                }
            })
            .state('estado.detail', {
                parent: 'entity',
                url: '/estado/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Estado'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/estado/estado-detail.html',
                        controller: 'EstadoDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Estado', function($stateParams, Estado) {
                        return Estado.get({id : $stateParams.id});
                    }]
                }
            })
            .state('estado.new', {
                parent: 'estado',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/estado/estado-dialog.html',
                        controller: 'EstadoDialogController',
                        size: 'lg',
                        backdrop: 'static',
                        resolve: {
                            entity: function () {
                                return {
                                    nm_estado: null,
                                    sg_estado: null,
                                    ds_pais: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('estado', null, { reload: true });
                    }, function() {
                        $state.go('estado');
                    })
                }]
            })
            .state('estado.edit', {
                parent: 'estado',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/estado/estado-dialog.html',
                        controller: 'EstadoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Estado', function(Estado) {
                                return Estado.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('estado', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('estado.delete', {
                parent: 'estado',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/estado/estado-delete-dialog.html',
                        controller: 'EstadoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Estado', function(Estado) {
                                return Estado.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('estado', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
