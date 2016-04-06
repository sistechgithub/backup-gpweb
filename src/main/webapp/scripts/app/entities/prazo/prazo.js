'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('prazo', {
                parent: 'entity',
                url: '/prazos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Prazos'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/prazo/prazos.html',
                        controller: 'PrazoController'
                    }
                },
                resolve: {
                }
            })
            .state('prazo.detail', {
                parent: 'entity',
                url: '/prazo/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Prazo'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/prazo/prazo-detail.html',
                        controller: 'PrazoDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Prazo', function($stateParams, Prazo) {
                        return Prazo.get({id : $stateParams.id});
                    }]
                }
            })
            .state('prazo.new', {
                parent: 'prazo',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/prazo/prazo-dialog.html',
                        controller: 'PrazoDialogController',
                        size: 'lg',
                        backdrop: 'static',
                        resolve: {
                            entity: function () {
                                return {

                                    nome: null,
                                    qntParcelas: 1,
                                    entrada: false,
                                    ajuste: 0,
                                    intervalo: 30,
                                    juros: 0, 
                                    valorMinimo: 0,
                                    intervaloConfigurado: false,
                                    intervalos:[]
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('prazo', null, { reload: true });
                    }, function() {
                        $state.go('prazo');
                    })
                }]
            })
            .state('prazo.edit', {
                parent: 'prazo',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/prazo/prazo-dialog.html',
                        controller: 'PrazoDialogController',
                        size: 'lg',
                        backdrop: 'static',
                        resolve: {
                            entity: ['Prazo', function(Prazo) {
                                return Prazo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prazo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('prazo.delete', {
                parent: 'prazo',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/prazo/prazo-delete-dialog.html',
                        controller: 'PrazoDeleteController',
                        size: 'md',
                        backdrop: 'static',
                        resolve: {
                            entity: ['Prazo', function(Prazo) {
                                return Prazo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('prazo', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
