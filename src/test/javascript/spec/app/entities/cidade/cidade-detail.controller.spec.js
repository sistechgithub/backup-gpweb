'use strict';

describe('Cidade Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockCidade, MockEstado;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockCidade = jasmine.createSpy('MockCidade');
        MockEstado = jasmine.createSpy('MockEstado');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Cidade': MockCidade,
            'Estado': MockEstado
        };
        createController = function() {
            $injector.get('$controller')("CidadeDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'gpApp:cidadeUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
