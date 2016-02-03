'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pessoa', {
                parent: 'entity',
                url: '/pessoas',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Pessoas'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pessoa/pessoas.html',
                        controller: 'PessoaController'
                    }
                },
                resolve: {
                }
            })
            .state('pessoa.detail', {
                parent: 'entity',
                url: '/pessoa/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Pessoa'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pessoa/pessoa-detail.html',
                        controller: 'PessoaDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Pessoa', function($stateParams, Pessoa) {
                        return Pessoa.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pessoa.new', {
                parent: 'pessoa',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoa/pessoa-dialog.html',
                        controller: 'PessoaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    id_pessoa: null,
                                    nm_pessoa: null,
                                    cd_tel: null,
                                    cd_cel: null,
                                    cd_fax: null,
                                    id_logradouro: null,
                                    nm_numero: null,
                                    ds_complemento: null,
                                    ds_situacao: null,
                                    fl_fisica: null,
                                    ds_email: null,
                                    ds_obs: null,
                                    ds_historico: null,
                                    fl_vendedor: null,
                                    ds_inativo: null,
                                    fl_usuario: null,
                                    dt_cadastro: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pessoa', null, { reload: true });
                    }, function() {
                        $state.go('pessoa');
                    })
                }]
            })
            .state('pessoa.edit', {
                parent: 'pessoa',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoa/pessoa-dialog.html',
                        controller: 'PessoaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Pessoa', function(Pessoa) {
                                return Pessoa.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pessoa', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('pessoa.delete', {
                parent: 'pessoa',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoa/pessoa-delete-dialog.html',
                        controller: 'PessoaDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Pessoa', function(Pessoa) {
                                return Pessoa.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pessoa', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
