'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pessoa_juridica', {
                parent: 'entity',
                url: '/pessoa_juridicas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Pessoa_juridicas'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pessoa_juridica/pessoa_juridicas.html',
                        controller: 'Pessoa_juridicaController'
                    }
                },
                resolve: {
                }
            })
            .state('pessoa_juridica.detail', {
                parent: 'entity',
                url: '/pessoa_juridica/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Pessoa_juridica'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pessoa_juridica/pessoa_juridica-detail.html',
                        controller: 'Pessoa_juridicaDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Pessoa_juridica', function($stateParams, Pessoa_juridica) {
                        return Pessoa_juridica.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pessoa_juridica.new', {
                parent: 'pessoa_juridica',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoa_juridica/pessoa_juridica-dialog.html',
                        controller: 'Pessoa_juridicaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    cd_cgc: null,
                                    cd_cgf: null,
                                    nm_fantasia: null,
                                    cd_cnpj: null,
                                    ds_responsavel: null,
                                    ds_obs: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pessoa_juridica', null, { reload: true });
                    }, function() {
                        $state.go('pessoa_juridica');
                    })
                }]
            })
            .state('pessoa_juridica.edit', {
                parent: 'pessoa_juridica',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoa_juridica/pessoa_juridica-dialog.html',
                        controller: 'Pessoa_juridicaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Pessoa_juridica', function(Pessoa_juridica) {
                                return Pessoa_juridica.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pessoa_juridica', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('pessoa_juridica.delete', {
                parent: 'pessoa_juridica',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoa_juridica/pessoa_juridica-delete-dialog.html',
                        controller: 'Pessoa_juridicaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Pessoa_juridica', function(Pessoa_juridica) {
                                return Pessoa_juridica.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pessoa_juridica', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
