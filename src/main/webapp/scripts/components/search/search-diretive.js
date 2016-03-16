'use strict';
angular.module('gpApp').directive('search', ['$resource', 'ParseLinks', function($resource, ParseLinks) {
		return {
			restrict : 'E',
			templateUrl : 'scripts/components/search/search-layout.html',
			require : 'ngModel',
			link : function($scope, $element, $attrs, ngModelCtrl) {
				
				
				$scope.valueSearch = '';
				
				//Default value is the first field of array
				$scope.fieldForSearch = $scope.fieldsSearch[0].value;

				$scope.search = function() {
					
					//The first field need to be a number because the default is the code
					if (($scope.fieldForSearch === $scope.fieldsSearch[0].value)
							&& (isNaN($scope.valueSearch))) {						
						ngModelCtrl.$setViewValue([]); //Cleaning the search passed by ngmodel on html
						$scope.links = "";
					} else {
						if ($scope.valueSearch === '') {
							$scope.loadAll(); 
						} else {
							//Get to the server the values
							var SearchEntity = $resource('api/_search/' + $attrs.ngModel + '/:query', {}, {
					            'query': { method: 'GET', isArray: true}
					        });
							SearchEntity.query({
								query : [
								         	$scope.fieldForSearch,
											$scope.valueSearch.toUpperCase() 
										],
										
										page: $scope.page, size: 20 //pagination
								
							}, function(result, headers) {
								$scope.links = ParseLinks.parse(headers('link')); //redefining the pagination 								
								ngModelCtrl.$setViewValue(result); //applying result
							}, function(response) {
								if (response.status === 404) {
								  ngModelCtrl.$setViewValue([]); //Cleaning the search								 								  
							    }
						    });
					    };
				    };
			    };
		    }
	    };
}]);