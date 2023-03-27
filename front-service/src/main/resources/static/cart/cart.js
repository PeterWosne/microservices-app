angular.module('market').controller('cartController', function ($scope, $http, $localStorage, $location) {

//     $scope.loadCart = function () {
//         $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.guestCartId)
//            .then(function (response) {
//               console.log(response);
//               $scope.items = response.data.items;
//               $scope.totalPrice = response.data.totalPrice;
//            });
//     };

     $scope.loadCart = function () {
              $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.guestCartId)
                 .then(function successCallback(response) {
                    $scope.items = response.data.items;
                    $scope.totalPrice = response.data.totalPrice;
                 },function errorCallback(response) {
                    console.log(response);
                    $location.path('/');
                 });
          };


     $scope.increment = function (id) {
         $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.guestCartId + '/add/' + id)
             .then(function (response) {
                $scope.loadCart();
             });
     };

     $scope.decrement = function (id) {
          $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.guestCartId + '/delete/' + id)
              .then(function (response) {
                 $scope.loadCart();
          });
     };

     $scope.clearCart = function () {
         $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.guestCartId + '/clear')
              .then(function (response) {
                 $scope.loadCart();
         });
     };

     $scope.createOrder = function () {
         $http.post('http://localhost:5555/core/api/v1/orders', $scope.newShipping)
            .then(function (response) {
               $scope.loadCart();
            });
     };

     $scope.loadCart();
});