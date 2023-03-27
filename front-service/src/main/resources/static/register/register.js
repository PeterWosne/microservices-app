angular.module('market').controller('registerController', function ($scope, $http, $localStorage, $location) {

    $scope.createNewUser = function () {
        $http.post('http://localhost:5555/auth/auth/register', $scope.newUser)
           .then(function (response) {
              if(response.data.value == 'USER_CREATED') {
                 $scope.newUser = null;
                 alert('Your account has been created successfully!');
                 $location.path('/');
              };

              if (response.data.value == 'PASSWORD_NOT_MATCHING') {
                 $scope.newUser = null;
                 alert('Password not matching');
              }

              if(response.data.value == 'USER EXISTS') {
                 $scope.newUser = null;
                 alert('User with this username or email already registered');
              }
           });
     };
});