'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pessoa', {
                parent: 'entity',
                url: '/pessoas',
                data: {
                    roles: ['ROLE_USER'],
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
                    roles: ['ROLE_USER'],
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
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoa/pessoa-dialog.html',
                        controller: 'PessoaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nome: null, id: null};
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
                    roles: ['ROLE_USER'],
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
            });
    });
