'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cliente', {
                parent: 'entity',
                url: '/clientes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Clientes'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cliente/clientes.html',
                        controller: 'ClienteController'
                    }
                },
                resolve: {
                }
            })
            .state('cliente.detail', {
                parent: 'entity',
                url: '/cliente/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Cliente'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/cliente/cliente-detail.html',
                        controller: 'ClienteDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Cliente', function($stateParams, Cliente) {
                        return Cliente.get({id : $stateParams.id});
                    }]
                }
            })
            .state('cliente.new', {
                parent: 'cliente',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cliente/cliente-dialog.html',
                        controller: 'ClienteDialogController',
                        size: 'lg',
                        backdrop: 'static',
                        resolve: {
                            entity: function () {
                                return {
                                    tipoPessoa: 'F',
                                    nome: null,
                                    dataCadastro: new Date(),
                                    id: null,
                                    pessoaFisica: {
                                        cpf: null
                                    },
                                    pessoaJuridica: {
                                        cnpj: null
                                    }
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('cliente', null, { reload: true });
                    }, function() {
                        $state.go('cliente');
                    })
                }]
            })
            .state('cliente.edit', {
                parent: 'cliente',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cliente/cliente-dialog.html',
                        controller: 'ClienteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Cliente', function(Cliente) {
                                return Cliente.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cliente', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cliente.delete', {
                parent: 'cliente',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/cliente/cliente-delete-dialog.html',
                        controller: 'ClienteDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Cliente', function(Cliente) {
                                return Cliente.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cliente', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
