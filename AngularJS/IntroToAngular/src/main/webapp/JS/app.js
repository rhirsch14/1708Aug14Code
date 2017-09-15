/**
 * AngularJS app
 */

/*
 * An AngularJS module define an application.
 * The module is the container for different parts of your app.
 */

/* The [] tells angular to create a project
 *	with the name passed in
 * Not providing the [] will tell angular
 * 	that the project already exists
 */
var app = angular.module('myApp',[]);

/*
 * Brings in a scope (in this case it's the function)
 *  The AngularJS app defined by ng-app="myApp"
 *  is running inside of our body tag. We have also
 *  defied a controller - myCtrl - inside of the body
 *  tag. myCtrl is a JAVASCRIPT FUNCTION
 * AngularJS will invoke the controller with the
 *  $scope object.
 * $scope is the application object (the owner of the
 *  variables and functions)
 */
app.controller('myCtrl', function($scope){
	$scope.hello = "hello world";
	$scope.fn = "Sample";
	$scope.ln = "Name";
	
	$scope.fullName = function(){
		return $scope.fn + " " + $scope.ln;
	}
	
})