var mainApp = angular.module("productApp", ['ngRoute', 'ngCookies', 'cartApp']);

mainApp.service('ProductService', function () {
    var savedData = {};
    function set(data) {
        //savedData = data;
        localStorage.setItem("product", data.id);
    }

    function get() {
       // return savedData;
        return localStorage.getItem("product");
    }

    return {
        set: set,
        get: get
    }
});
/**
 * Creating directive and service for uploading file
 * and saving new product
 */
mainApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs){
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;

            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

mainApp.service('ProductAdd', ['$http','$location', function ($http, $location) {
    this.addNewProduct = function (file, uploadUrl, product) {
        var fd = new FormData();
        fd.append('file', file);

        $http.post(uploadUrl, fd, {
            transformRequest: angular.identity,
            headers: {'Content-Type': undefined}
        })
            .then(function (resp) {
                console.log("Response status for img uploading: " + resp.status);
                $http.post("/rest/products/add", product).then(function (response) {
                    console.log("Response status for product saving: " + response.status);
                    $location.path("/viewProducts");
                });
            });
    }
}]);

mainApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider.
        when('/productDetails', {
            templateUrl: 'productDetail.htm',
            controller: 'ProductDetailsController'
        }).

        when('/addProduct', {
            templateUrl: 'addProduct.htm',
            controller: 'AddProductController'
        }).

        when('/viewProducts', {
            templateUrl: 'viewProducts.htm',
            controller: 'ProductsController'
        }).

        otherwise({
            redirectTo: '/viewProducts'
        });
}]);

mainApp.controller("ProductsController", ProductsController);
mainApp.controller("ProductDetailsController", ProductDetailsController);
mainApp.controller("AddProductController", AddProductController);

function ProductsController($scope, $http, $location, ProductService) {
    $http.get("/rest/products").then(function (response) {
        $scope.products = response.data;
    });

    $scope.getVal = function (event) {
        $scope.filter = event;
    };

    $scope.showDetails = function (product) {
        ProductService.set(product);
        $location.path("/productDetails");
    }
}

function ProductDetailsController($scope, $http, $window, ProductService, $location, CartService) {
    $scope.userId = ProductService.get();
    $http.get("/rest/products/" + $scope.userId).then(function (response) {
       $scope.product = response.data;
    });

    $scope.remove = function () {
        var ensure = confirm("Do you want to delete this product?");
        if (ensure) {
            $http.delete("/rest/products/" + $scope.product.id).then(function () {
                $location.path("/viewProducts");
            });
        }
    };

    $scope.addToCart = function (product, quantity) {
        if (typeof quantity === "undefined"){
            quantity = 1;
        }
        $scope.cartItem = {
            product : product,
            quantity : quantity
        };

        CartService.saveItem($scope.cartItem);
        $window.location.href = "/cart";
    };
}

function AddProductController($scope, $http, ProductAdd, $location) {
    $scope.addProduct = function (product) {
        var url = "/rest/products/addImage";
        product.productImage = $scope.image.name;
        ProductAdd.addNewProduct($scope.image, url, product);
    }
}

mainApp.run(['$http', '$cookies', function($http, $cookies) {
    $http.defaults.headers.post['X-CSRFToken'] = $cookies.csrftoken;
}]);


