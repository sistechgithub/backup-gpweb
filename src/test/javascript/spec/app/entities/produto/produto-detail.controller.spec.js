'use strict';

describe('Produto Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockProduto, MockGrupo;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockProduto = jasmine.createSpy('MockProduto');
        MockGrupo = jasmine.createSpy('MockGrupo');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Produto': MockProduto,
            'Grupo': MockGrupo
        };
        createController = function() {
            $injector.get('$controller')("ProdutoDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'gpApp:produtoUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
