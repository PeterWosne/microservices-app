(function () {
   angular
      .module('market', ['ngRoute', 'ngStorage'])
      .config(config)
      .run(run);

   function config($routeProvider) {
           $routeProvider
               .when('/', {
                   templateUrl: 'welcome/welcome.html',
                   controller: 'welcomeController'
               })
               .when('/store', {
                   templateUrl: 'store/store.html',
                   controller: 'storeController'
               })
               .when('/cart', {
                   templateUrl: 'cart/cart.html',
                   controller: 'cartController'
               })
               .when('/orders', {
                   templateUrl: 'orders/orders.html',
                   controller: 'ordersController'
               })
               .when('/register', {
                   templateUrl: 'register/register.html',
                   controller: 'registerController'
               })
               .otherwise({
                   redirectTo: '/'
               });
       }

   function run($rootScope, $http, $localStorage) {

        if(!$localStorage.guestCartId) {
           $http.get('http://localhost:5555/cart/api/v1/cart/generate_id')
              .then(function (response) {
                 $localStorage.guestCartId = response.data.value;
              });
        };

        //подшиваем токен авторизации ко всем запросам
        if($localStorage.webUser) {
           //перед тем как привязать токен к повторному запросу, необходимо проверить что токен актуальный(или же удалить данные из локалсториджа и убрать автоподшивку токена)
           try {
              let jwt = $localStorage.webUser.token;
              let payload = JSON.parse(atob(jwt.split('.')[1]));
              let currentTime = parseInt(new Date().getTime()/1000);
              if(currentTime > payload.exp) {
                 console.log("Token has expired!")
                 delete $localStorage.webUser;
                 $http.defaults.headers.common['Authorization'] = '';
              } else {
                 $http.defaults.headers.common['Authorization'] = 'Bearer ' + $localStorage.webUser.token;
              };
           }catch (e) {
           }
        };
   }

})();


angular.module('market').controller('indexController', function ($scope, $http, $localStorage, $rootScope, $location) {

    //авторизация
    $scope.tryToAuth = function () {
         $http.post('http://localhost:5555/auth/auth/authenticate', $scope.user)
            .then(function successCallback(response) {
                console.log(response.data);
                if(response.data.token) {
                   //сразу подшиваем хедер авторизации с токеном
                   $http.defaults.headers.common['Authorization'] = 'Bearer ' + response.data.token;

                   $localStorage.webUser = {username: $scope.user.username, token: response.data.token};
                   $scope.user.username = null;
                   $scope.user.password = null;

                   $location.path('/');
                };
            },function errorCallback(response) {
        });
    };

    $scope.logout = function () {
       delete $localStorage.webUser;
       $http.defaults.headers.common['Authorization'] = '';
       $location.path('/');
    };

    $rootScope.isUserLoggedIn = function () {
        if($localStorage.webUser) {
           return true;
        }else {
           return false;
        }
    };
});
