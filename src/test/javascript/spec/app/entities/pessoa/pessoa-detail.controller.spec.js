'use strict';

describe('Pessoa Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPessoa, MockLogradouro;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPessoa = jasmine.createSpy('MockPessoa');
        MockLogradouro = jasmine.createSpy('MockLogradouro');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Pessoa': MockPessoa,
            'Logradouro': MockLogradouro
        };
        createController = function() {
            $injector.get('$controller')("PessoaDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'gpApp:pessoaUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
