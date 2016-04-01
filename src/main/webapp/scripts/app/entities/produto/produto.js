'use strict';

angular.module('gpApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('produto', {
                parent: 'entity',
                url: '/produtos',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Produtos'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/produto/produtos.html',
                        controller: 'ProdutoController'
                    }
                },
                resolve: {
                }
            })
            .state('produto.detail', {
                parent: 'entity',
                url: '/produto/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'Produto'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/produto/produto-detail.html',
                        controller: 'ProdutoDetailController'
                    }
                },
                resolve: {
                    entity: ['$stateParams', 'Produto', function($stateParams, Produto) {
                        return Produto.get({id : $stateParams.id});
                    }]
                }
            })
            .state('produto.new', {
                parent: 'produto',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/produto/produto-dialog.html',
                        controller: 'ProdutoDialogController',
                        size: 'lg',
                        backdrop: 'static',
                        resolve: {
                            entity: function () {
                                return {
                                    nmProduto: null,
                                    vlCusto: null,
                                    qtSaldo: null,
                                    vlVenda: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('produto', null, { reload: true });
                    }, function() {
                        $state.go('produto');
                    })
                }]
            })
            .state('produto.edit', {
                parent: 'produto',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/produto/produto-dialog.html',
                        controller: 'ProdutoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Produto', function(Produto) {
                                return Produto.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('produto', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('produto.delete', {
                parent: 'produto',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/produto/produto-delete-dialog.html',
                        controller: 'ProdutoDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Produto', function(Produto) {
                                return Produto.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('produto', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
