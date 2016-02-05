'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('subgrupo', {
                parent: 'entity',
                url: '/subgrupos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Subgrupos'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/subgrupo/subgrupos.html',
                        controller: 'SubgrupoController'
                    }
                },
                resolve: {
                }
            })
            .state('subgrupo.detail', {
                parent: 'entity',
                url: '/subgrupo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Subgrupo'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/subgrupo/subgrupo-detail.html',
                        controller: 'SubgrupoDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Subgrupo', function($stateParams, Subgrupo) {
                        return Subgrupo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('subgrupo.new', {
                parent: 'subgrupo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/subgrupo/subgrupo-dialog.html',
                        controller: 'SubgrupoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                	  nmSubGrupo: null,
	                                  vlCusto: null,
	                                  vlValor: null,
	                                  flEnvio: false,
	                                  nnNovo: 1,
	                                  id: null	                                  
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('subgrupo', null, { reload: true });
                    }, function() {
                        $state.go('subgrupo');
                    })
                }]
            })
            .state('subgrupo.edit', {
                parent: 'subgrupo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/subgrupo/subgrupo-dialog.html',
                        controller: 'SubgrupoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Subgrupo', function(Subgrupo) {
                                return Subgrupo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('subgrupo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('subgrupo.delete', {
                parent: 'subgrupo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/subgrupo/subgrupo-delete-dialog.html',
                        controller: 'SubgrupoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Subgrupo', function(Subgrupo) {
                                return Subgrupo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('subgrupo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
