var cartApp = angular.module("cartApp", ['ngCookies', 'ngRoute']);

cartApp.service('CartService', function () {
    var products = [];

    if (localStorage.getItem("cart") !== null){
        products = JSON.parse(localStorage.getItem("cart"));
    }
    function saveItemToCart(item) {
        var index = duplicatesExists(item);

        if (index === -1) {
            products.push(item);
        }else {
            products[index].quantity += item.quantity;
        }
        localStorage.setItem("cart", JSON.stringify(products));
    }

    function setItems(items) {
        products = items;
        localStorage.setItem("cart", JSON.stringify(products));
    }

    function getItems() {
        return products
    }

    function duplicatesExists(item) {
        for (var i = 0; i < products.length; i++){
            if (products[i].product.id === item.product.id){
                return i;
            }
        }
        return -1;
    }

    return {
        saveItem : saveItemToCart,
        getItems : getItems,
        setItems : setItems
    }
});

cartApp.service('Checkout', function () {
    function set(data) {
        localStorage.setItem("price", data);
    }

    function get() {
        return localStorage.getItem("price");
    }

    return {
        set: set,
        get: get
    }
});

cartApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
    when('/cart', {
        templateUrl: 'cart.htm',
        controller: 'CartController'
    }).
    when('/checkout', {
        templateUrl: 'checkout.htm',
        controller: 'CheckoutController'
    }).
    otherwise({
        redirectTo: '/cart'
    });
   
}]);

cartApp.controller("CartController", CartController);
cartApp.controller("CheckoutController", CheckoutController);

function CartController($scope, $location, CartService, Checkout) {

    $scope.cartItems = CartService.getItems();


    $scope.removeItem = function (item) {
        for (var i = 0; i < $scope.cartItems.length; i++){
            if ($scope.cartItems[i].product.id === item.product.id){
                $scope.cartItems.splice(i, 1);
                CartService.setItems($scope.cartItems);
                return;
            }
        }
    };
    
    $scope.getTotal = function () {
        var total = 0;
        for (var i = 0; i < $scope.cartItems.length; i++){
            total += $scope.cartItems[i].totalPrice;
        }
        return total;
    };

    $scope.getTotalQuantity = function () {
        var totalQuantity = 0;
        for (var i = 0; i < $scope.cartItems.length; i++){
            totalQuantity += $scope.cartItems[i].quantity;
        }
        return totalQuantity;
    };
    
    $scope.refreshQuantity = function () {
        CartService.setItems($scope.cartItems);
    };

    $scope.toCheckout = function (data) {
        Checkout.set(data);
        $location.path("/checkout");
    }
}

function CheckoutController($scope, $http, $window, Checkout, CartService) {
    $scope.cartItems = CartService.getItems();
    $scope.total = Checkout.get();
    
    $scope.checkoutOrder = function () {
        var products = [];
        for (var i = 0; i < $scope.cartItems.length; i++){
            products.push($scope.cartItems[i].product);
        }
        var order = {
            address : $scope.address,
            totalPrice : $scope.total,
            cartItems : $scope.cartItems
        };

        $http.post("/rest/order/add", order).then(function (response) {
            localStorage.clear();
            $window.location.href = "/checkout/" + response.data.orderNumber;
        })
    }
}