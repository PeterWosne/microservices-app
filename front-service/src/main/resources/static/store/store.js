angular.module('market').controller('storeController', function ($scope, $http, $localStorage) {

   $scope.loadProducts = function (indexPage) {
          $http({
             url: 'http://localhost:5555/core/api/v1/products',
             method: 'GET',
             params: {
                p: indexPage,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                title_part: $scope.filter ? $scope.filter.title_part : null
             }
          }).then(function (response) {
             console.log(response.data);
             $scope.cart = response.data;
             $scope.generatePagesList(response.data.totalPages);
          });
   };


        //добавление товара в корзину
   $scope.addProductToCart = function (id) {
      $http.get('http://localhost:5555/cart/api/v1/cart/' + $localStorage.guestCartId + '/add/' + id)
          .then(function (response) {
      });
   };

   $scope.generatePagesList = function (totalPages) {
         var out = [];
         for(var i=0;i<totalPages;i++) {
             out.push(i + 1);
         }
         $scope.pagesList = out;
   }


//   DELETE PRODUCT
//   $scope.deleteProduct = function (id) {
//           $http.delete('http://localhost:5555/core/api/v1/products/' + id)
//              .then(function (response) {
//                  $scope.loadProducts();
//              });
//        };

//     CREATE NEW PRODUCT
//     $scope.createNewProduct = function () {
//        $http.post('http://localhost:5555/core/api/v1/products', $scope.newProduct)
//           .then(function (response) {
//              $scope.loadProducts();
//              $scope.newProduct = null;
//           });
//     };

   $scope.loadProducts();
});