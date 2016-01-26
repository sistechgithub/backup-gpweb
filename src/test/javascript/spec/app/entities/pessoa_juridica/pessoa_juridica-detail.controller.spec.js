'use strict';

describe('Pessoa_juridica Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockPessoa_juridica, MockPessoa;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockPessoa_juridica = jasmine.createSpy('MockPessoa_juridica');
        MockPessoa = jasmine.createSpy('MockPessoa');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'Pessoa_juridica': MockPessoa_juridica,
            'Pessoa': MockPessoa
        };
        createController = function() {
            $injector.get('$controller')("Pessoa_juridicaDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'gpApp:pessoa_juridicaUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
