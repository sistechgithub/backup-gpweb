'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pessoa_fisica', {
                parent: 'entity',
                url: '/pessoa_fisicas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Pessoa_fisicas'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pessoa_fisica/pessoa_fisicas.html',
                        controller: 'Pessoa_fisicaController'
                    }
                },
                resolve: {
                }
            })
            .state('pessoa_fisica.detail', {
                parent: 'entity',
                url: '/pessoa_fisica/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Pessoa_fisica'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pessoa_fisica/pessoa_fisica-detail.html',
                        controller: 'Pessoa_fisicaDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Pessoa_fisica', function($stateParams, Pessoa_fisica) {
                        return Pessoa_fisica.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pessoa_fisica.new', {
                parent: 'pessoa_fisica',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoa_fisica/pessoa_fisica-dialog.html',
                        controller: 'Pessoa_fisicaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    dt_nascimento: null,
                                    cd_rg: null,
                                    cd_cpf: null,
                                    nm_pai: null,
                                    nm_mae: null,
                                    ds_estcivil: null,
                                    nm_conjuge: null,
                                    ds_profissao: null,
                                    ds_localtrab: null,
                                    ds_complemento: null,
                                    ds_apelido: null,
                                    ds_obs: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pessoa_fisica', null, { reload: true });
                    }, function() {
                        $state.go('pessoa_fisica');
                    })
                }]
            })
            .state('pessoa_fisica.edit', {
                parent: 'pessoa_fisica',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoa_fisica/pessoa_fisica-dialog.html',
                        controller: 'Pessoa_fisicaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Pessoa_fisica', function(Pessoa_fisica) {
                                return Pessoa_fisica.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pessoa_fisica', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('pessoa_fisica.delete', {
                parent: 'pessoa_fisica',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoa_fisica/pessoa_fisica-delete-dialog.html',
                        controller: 'Pessoa_fisicaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Pessoa_fisica', function(Pessoa_fisica) {
                                return Pessoa_fisica.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pessoa_fisica', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
