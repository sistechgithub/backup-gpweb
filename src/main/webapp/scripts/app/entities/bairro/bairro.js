'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('bairro', {
                parent: 'entity',
                url: '/bairros',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Bairros'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bairro/bairros.html',
                        controller: 'BairroController'
                    }
                },
                resolve: {
                }
            })
            .state('bairro.detail', {
                parent: 'entity',
                url: '/bairro/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Bairro'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/bairro/bairro-detail.html',
                        controller: 'BairroDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Bairro', function($stateParams, Bairro) {
                        return Bairro.get({id : $stateParams.id});
                    }]
                }
            })
            .state('bairro.new', {
                parent: 'bairro',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bairro/bairro-dialog.html',
                        controller: 'BairroDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nm_bairro: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('bairro', null, { reload: true });
                    }, function() {
                        $state.go('bairro');
                    })
                }]
            })
            .state('bairro.edit', {
                parent: 'bairro',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bairro/bairro-dialog.html',
                        controller: 'BairroDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Bairro', function(Bairro) {
                                return Bairro.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bairro', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('bairro.delete', {
                parent: 'bairro',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/bairro/bairro-delete-dialog.html',
                        controller: 'BairroDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Bairro', function(Bairro) {
                                return Bairro.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('bairro', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
