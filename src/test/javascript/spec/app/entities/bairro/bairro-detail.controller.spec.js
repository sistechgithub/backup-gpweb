'use strict';

describe('Bairro Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockBairro, MockCidade;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockBairro = jasmine.createSpy('MockBairro');
        MockCidade = jasmine.createSpy('MockCidade');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Bairro': MockBairro,
            'Cidade': MockCidade
        };
        createController = function() {
            $injector.get('$controller')("BairroDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'gpApp:bairroUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
