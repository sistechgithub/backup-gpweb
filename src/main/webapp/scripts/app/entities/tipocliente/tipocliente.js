'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('tipocliente', {
                parent: 'entity',
                url: '/tipoclientes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Tipos de Clientes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tipocliente/tipoclientes.html',
                        controller: 'TipoClienteController'
                    }
                },
                resolve: {
                }
            })
            .state('tipocliente.detail', {
                parent: 'entity',
                url: '/tipocliente/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Tipo Cliente'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/tipocliente/tipocliente-detail.html',
                        controller: 'TipoClienteDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'TipoCliente', function($stateParams, TipoCliente) {
                        return TipoCliente.get({id : $stateParams.id});
                    }]
                }
            })
            .state('tipocliente.new', {
                parent: 'tipocliente',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tipocliente/tipocliente-dialog.html',
                        controller: 'TipoClienteDialogController',
                        size: 'lg',
                        backdrop: 'static',
                        resolve: {
                            entity: function () {
                                return {
                                    nome: null,
                                    vlDescontoMax: null,
                                    vlComissao: null,
                                    diaFatura: null,
                                    precoVenda: null,
                                    vip: null,
                                    bloqueado: null,
                                    dataCadastro: null,
                                    vlMeta:null,
                                    flCrediario:null,
                                    flCheque:null,
                                    flDinheiro:null,
                                    flCartao:null,
                                    forcaJuros:null,
                                    bloqueiaPrazo:null,
                                    prazos: []
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('tipocliente', null, { reload: true });
                    }, function() {
                        $state.go('tipocliente');
                    })
                }]
            })
            .state('tipocliente.edit', {
                parent: 'tipocliente',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tipocliente/tipocliente-dialog.html',
                        controller: 'TipoClienteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TipoCliente', function(Prazo) {
                                return Prazo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tipocliente', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('tipocliente.delete', {
                parent: 'tipocliente',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/tipocliente/tipocliente-delete-dialog.html',
                        controller: 'TipoClienteDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Prazo', function(Prazo) {
                                return Prazo.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('tipocliente', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
