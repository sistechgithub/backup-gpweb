'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('grupo', {
                parent: 'entity',
                url: '/grupos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Grupos'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grupo/grupos.html',
                        controller: 'GrupoController'
                    }
                },
                resolve: {
                }
            })
            .state('grupo.detail', {
                parent: 'entity',
                url: '/grupo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Grupo'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/grupo/grupo-detail.html',
                        controller: 'GrupoDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Grupo', function($stateParams, Grupo) {
                        return Grupo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('grupo.new', {
                parent: 'grupo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/grupo/grupo-dialog.html',
                        controller: 'GrupoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nm_grupo: null,
                                    vl_comissao: null,
                                    fl_desconto: null,
                                    fl_promo: null,
                                    dt_promo: null,
                                    dt_operacao: null,
                                    fl_semcontagem: null,
                                    fl_envio: null,
                                    nn_novo: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('grupo', null, { reload: true });
                    }, function() {
                        $state.go('grupo');
                    })
                }]
            })
            .state('grupo.edit', {
                parent: 'grupo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/grupo/grupo-dialog.html',
                        controller: 'GrupoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Grupo', function(Grupo) {
                                return Grupo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('grupo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('grupo.delete', {
                parent: 'grupo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/grupo/grupo-delete-dialog.html',
                        controller: 'GrupoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Grupo', function(Grupo) {
                                return Grupo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('grupo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
