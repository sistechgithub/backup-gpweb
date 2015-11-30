'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('fabricante', {
                parent: 'entity',
                url: '/fabricantes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Fabricantes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fabricante/fabricantes.html',
                        controller: 'FabricanteController'
                    }
                },
                resolve: {
                }
            })
            .state('fabricante.detail', {
                parent: 'entity',
                url: '/fabricante/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Fabricante'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/fabricante/fabricante-detail.html',
                        controller: 'FabricanteDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Fabricante', function($stateParams, Fabricante) {
                        return Fabricante.get({id : $stateParams.id});
                    }]
                }
            })
            .state('fabricante.new', {
                parent: 'fabricante',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fabricante/fabricante-dialog.html',
                        controller: 'FabricanteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    nm_fabricante: null,
                                    cd_cgc: null,
                                    cd_cgf: null,
                                    nn_numero: null,
                                    cs_complemento: null,
                                    cd_tel: null,
                                    cd_fax: null,
                                    fl_inativo: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('fabricante', null, { reload: true });
                    }, function() {
                        $state.go('fabricante');
                    })
                }]
            })
            .state('fabricante.edit', {
                parent: 'fabricante',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fabricante/fabricante-dialog.html',
                        controller: 'FabricanteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Fabricante', function(Fabricante) {
                                return Fabricante.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fabricante', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('fabricante.delete', {
                parent: 'fabricante',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/fabricante/fabricante-delete-dialog.html',
                        controller: 'FabricanteDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Fabricante', function(Fabricante) {
                                return Fabricante.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('fabricante', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
