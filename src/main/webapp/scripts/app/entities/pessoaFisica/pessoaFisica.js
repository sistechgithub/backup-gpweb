'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pessoaFisica', {
                parent: 'entity',
                url: '/pessoaFisicas',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'PessoaFisicas'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pessoaFisica/pessoaFisicas.html',
                        controller: 'PessoaFisicaController'
                    }
                },
                resolve: {
                }
            })
            .state('pessoaFisica.detail', {
                parent: 'entity',
                url: '/pessoaFisica/{id}',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'PessoaFisica'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/pessoaFisica/pessoaFisica-detail.html',
                        controller: 'PessoaFisicaDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'PessoaFisica', function($stateParams, PessoaFisica) {
                        return PessoaFisica.get({id : $stateParams.id});
                    }]
                }
            })
            .state('pessoaFisica.new', {
                parent: 'pessoaFisica',
                url: '/new',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoaFisica/pessoaFisica-dialog.html',
                        controller: 'PessoaFisicaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {cpf: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pessoaFisica', null, { reload: true });
                    }, function() {
                        $state.go('pessoaFisica');
                    })
                }]
            })
            .state('pessoaFisica.edit', {
                parent: 'pessoaFisica',
                url: '/{id}/edit',
                data: {
                    roles: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/pessoaFisica/pessoaFisica-dialog.html',
                        controller: 'PessoaFisicaDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PessoaFisica', function(PessoaFisica) {
                                return PessoaFisica.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pessoaFisica', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
