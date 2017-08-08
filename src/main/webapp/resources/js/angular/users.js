
var mainApp = angular.module("userApp", ['ngRoute', 'ngCookies', 'cartApp']);

mainApp.service('UserService', function () {
   var savedData = {};
   function set(data) {
       savedData = data;
   }

   function get() {
       return savedData;
   }

    return {
        set: set,
        get: get
    }
});

mainApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/addUser', {
            templateUrl: 'addUser.htm',
            controller: 'AddStudentController'
        }).
        when('/editUser', {
            templateUrl: 'editUser.htm',
            controller: 'EditUserController'
        }).
        when('/viewUsers', {
            templateUrl: 'viewUsers.htm',
            controller: 'UsersController'
        }).

        otherwise({
            redirectTo: '/viewUsers'
        });
}]);

mainApp.controller("AddStudentController", AddStudentController);
mainApp.controller("EditUserController", EditUserController);
mainApp.controller("UsersController", UsersController);

function UsersController($scope, $http, $location, UserService) {
    $http.get("/rest/users").then(function (response) {
        $scope.users = response.data;
    });

    $scope.updateStatus = function (user, status) {
        $http.patch("rest/users/" + user.id, status)
            .then(function (response) {
                  alert(user.username + " status has been changed")

                },function (response) {
                   console.log(response.statusText);
                   alert("Something went wrong");
            });
    };

    $scope.editUser = function(user) {
        UserService.set(user);
        $location.path("/editUser");
    };

    $scope.deleteUser = function(user) {
        var ensure = confirm("Do you want to delete current user?");
        if (ensure) {
            $http.delete("/rest/users/" + user.id).then(function (response) {
                $scope.refreshUsers();
            });
        }
    };

    $scope.refreshUsers = function () {
        $http.get("/rest/users").then(function (response) {
            $scope.users = response.data;
        });
    }
}

function AddStudentController($scope, $http, $location) {
    $scope.createUser = function () {
        $http.post("rest/users/", $scope.user).then(function (response) {
            $location.path("/viewUsers");
        })
    }
}

function EditUserController($scope, $http, $location, UserService) {
    $scope.user = UserService.get();
    $scope.saveUser = function(user) {
        $http.put("/rest/users/" + user.id, user).then(function (response) {
            $location.path("/viewUsers");
        });
    }
}

mainApp.run(['$http', '$cookies', function($http, $cookies) {
    $http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
}]);


