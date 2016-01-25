'use strict';

describe('Logradouro Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockLogradouro, MockBairro;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockLogradouro = jasmine.createSpy('MockLogradouro');
        MockBairro = jasmine.createSpy('MockBairro');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Logradouro': MockLogradouro,
            'Bairro': MockBairro
        };
        createController = function() {
            $injector.get('$controller')("LogradouroDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'gpApp:logradouroUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
