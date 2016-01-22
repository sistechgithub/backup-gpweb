'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('logradouro', {
                parent: 'entity',
                url: '/logradouros',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Logradouros'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/logradouro/logradouros.html',
                        controller: 'LogradouroController'
                    }
                },
                resolve: {
                }
            })
            .state('logradouro.detail', {
                parent: 'entity',
                url: '/logradouro/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Logradouro'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/logradouro/logradouro-detail.html',
                        controller: 'LogradouroDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Logradouro', function($stateParams, Logradouro) {
                        return Logradouro.get({id : $stateParams.id});
                    }]
                }
            })
            .state('logradouro.new', {
                parent: 'logradouro',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/logradouro/logradouro-dialog.html',
                        controller: 'LogradouroDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nm_logradouro: null,
                                    cd_dep: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('logradouro', null, { reload: true });
                    }, function() {
                        $state.go('logradouro');
                    })
                }]
            })
            .state('logradouro.edit', {
                parent: 'logradouro',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/logradouro/logradouro-dialog.html',
                        controller: 'LogradouroDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Logradouro', function(Logradouro) {
                                return Logradouro.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('logradouro', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('logradouro.delete', {
                parent: 'logradouro',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/logradouro/logradouro-delete-dialog.html',
                        controller: 'LogradouroDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Logradouro', function(Logradouro) {
                                return Logradouro.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('logradouro', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
