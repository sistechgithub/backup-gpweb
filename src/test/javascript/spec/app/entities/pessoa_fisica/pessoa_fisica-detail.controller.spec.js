'use strict';

describe('Pessoa_fisica Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPessoa_fisica, MockPessoa, MockLogradouro;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPessoa_fisica = jasmine.createSpy('MockPessoa_fisica');
        MockPessoa = jasmine.createSpy('MockPessoa');
        MockLogradouro = jasmine.createSpy('MockLogradouro');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Pessoa_fisica': MockPessoa_fisica,
            'Pessoa': MockPessoa,
            'Logradouro': MockLogradouro
        };
        createController = function() {
            $injector.get('$controller')("Pessoa_fisicaDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'gpApp:pessoa_fisicaUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
