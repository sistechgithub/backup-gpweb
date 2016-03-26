'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cidade', {
                parent: 'entity',
                url: '/cidades',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Cidades'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cidade/cidades.html',
                        controller: 'CidadeController'
                    }
                },
                resolve: {
                }
            })
            .state('cidade.detail', {
                parent: 'entity',
                url: '/cidade/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Cidade'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cidade/cidade-detail.html',
                        controller: 'CidadeDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Cidade', function($stateParams, Cidade) {
                        return Cidade.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cidade.new', {
                parent: 'cidade',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cidade/cidade-dialog.html',
                        controller: 'CidadeDialogController',
                        size: 'lg',
                        backdrop: 'static',
                        resolve: {
                            entity: function () {
                                return {
                                    nm_cidade: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cidade', null, { reload: true });
                    }, function() {
                        $state.go('cidade');
                    })
                }]
            })
            .state('cidade.edit', {
                parent: 'cidade',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cidade/cidade-dialog.html',
                        controller: 'CidadeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cidade', function(Cidade) {
                                return Cidade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cidade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cidade.delete', {
                parent: 'cidade',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cidade/cidade-delete-dialog.html',
                        controller: 'CidadeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cidade', function(Cidade) {
                                return Cidade.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cidade', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
