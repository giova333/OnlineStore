<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize var="isAuthenticated" access="isAuthenticated()"/>
<sec:authorize var="isAnonymous" access="isAnonymous()"/>


<html ng-app="cartApp">
    <jsp:include page="templates/header.jsp"/>
    <body>
        <jsp:include page="templates/navbar.jsp"/>
        <section>
            <div class="jumbotron">
                <div class="container">
                    <h1 class="title">Shopping Cart</h1>
                </div>
            </div>
        </section>
            <div class="container">
                <div ng-view></div>
                <script type="text/ng-template" id="cart.htm">
                <table id="cart" class="table table-hover table-condensed">
                    <thead>
                        <tr>
                            <th style="width: 50%">Product</th>
                            <th style="width: 10%">Price</th>
                            <th style="width: 8%">Quantity</th>
                            <th style="width: 22%" class="text-center">Total</th>
                            <th style="width: 10%"></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr ng-repeat="item in cartItems track by $index">
                            <td data-th="Product">
                                <div class="row">
                                    <div class="col-sm-2 hidden-xs"><img ng-src="/resources/img/products/{{item.product.productImage}}" class="img-responsive"/></div>
                                    <div class="col-sm-10">
                                        <h4 class="nomargin">{{item.product.productName}}</h4>
                                        <p>{{item.product.productDescription}}</p>
                                    </div>
                                </div>
                            </td>
                            <td data-th="Price">{{item.product.productPrice}}$</td>
                            <td data-th="Quantity">
                                <input type="number" ng-change="refreshQuantity()" class="form-control text-center" ng-model="item.quantity" min="1" max="{{item.product.unitInStock}}"/>
                            </td>
                            <td data-th="Total" class="text-center">{{item.totalPrice = item.product.productPrice * item.quantity}}$</td>
                            <td class="actions" data-th="">
                                <button ng-click="removeItem(item)" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i></button>
                            </td>
                        </tr>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td><a href="/products" class="btn btn-warning"><i class="fa fa-angle-left"></i> Continue Shopping</a></td>
                        <td colspan="2" class="hidden-xs"></td>
                        <td class="hidden-xs text-center"><strong>Total {{getTotal()}}$</strong></td>
                        <c:if test="${isAuthenticated}">
                            <td><a href="" ng-click="toCheckout(getTotal())" class="btn btn-success btn-block">Checkout <i class="fa fa-angle-right"></i></a></td>
                        </c:if>
                        <c:if test="${isAnonymous}">
                            <td><a href="/login" class="btn btn-success btn-block">Checkout <i class="fa fa-angle-right"></i></a></td>
                        </c:if>
                    </tr>
                    </tfoot>
                </table>
                </script>
                <script type="text/ng-template" id="checkout.htm">
                    <div class="row">
                        <div class="col-md-8 col-md-offset-2">
                            <div id ="mainContentWrapper">
                                <h2 class="title text-center">Review Your Order</h2>
                                <hr style="width: 100%"/>
                                <a href="/products" class="btn btn-info" style="width: 100%;">Add More Products & Services</a>
                                <hr style="width: 100%"/>
                                <div class="shopping_cart">
                                    <form class="form-horizontal" role="form" name="form" action="" method="post" id="payment-form">
                                        <div class="panel-group" id="accordion">
                                            <div class="panel panel-default">
                                                <div class="panel-heading">
                                                    <h4 class="panel-title">
                                                        <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne">Review
                                                            Your Order</a>
                                                    </h4>
                                                </div>
                                                <div id="collapseOne" class="panel-collapse collapse in">
                                                    <div class="panel-body">
                                                        <div class="items">
                                                            <div class="col-md-9">
                                                                <table class="table">
                                                                    <tr>
                                                                        <td>
                                                                            <ul ng-repeat="item in cartItems track by $index">
                                                                                <li>
                                                                                    <div class="col-sm-2 hidden-xs"><img ng-src="/resources/img/products/{{item.product.productImage}}" class="img-responsive"/></div>
                                                                                    <strong>Name:</strong> {{item.product.productName}}
                                                                                    <strong>Quantity:</strong> {{item.quantity}}
                                                                                    <strong>Price:</strong> {{item.totalPrice}}$

                                                                                </li>
                                                                                <br/><br/>
                                                                            </ul>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </div>
                                                            <div class="col-md-3">
                                                                <div style="text-align: center;">
                                                                    <h3>Order Total</h3>
                                                                    <h3><span style="color:green;">{{total}}$</span></h3>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="panel panel-default">
                                            <div class="panel-heading">
                                                <h4 class="panel-title">
                                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo">
                                                        Contact Information</a>
                                                </h4>
                                            </div>
                                            <div id="collapseTwo" class="panel-collapse collapse">
                                                <div class="panel-body">
                                                    <b>Please verify your billing information.</b>
                                                    <br/><br/>
                                                    <table class="table table-striped">
                                                        <tr>
                                                            <td>
                                                                <label for="country">Country:</label>
                                                            </td>
                                                            <td>
                                                                <input class="form-control" id="country" ng-model="address.country"
                                                                       required="required" type="text"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <label for="city">City:</label>
                                                            </td>
                                                            <td>
                                                                <input class="form-control" id="city" ng-model="address.city"
                                                                       required="required" type="text"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <label for="streetName">Street Name:</label>
                                                            </td>
                                                            <td>
                                                                <input class="form-control" id="streetName" ng-model="address.streetName"
                                                                       required="required" type="text"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <label for="houseNumber">House Number:</label>
                                                            </td>
                                                            <td>
                                                                <input class="form-control" id="houseNumber" ng-model="address.houseNumber"
                                                                       required="required" type="text"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <label for="zipCode">Zip Code:</label>
                                                            </td>
                                                            <td>
                                                                <input class="form-control" id="zipCode" ng-model="address.zipCode"
                                                                       required="required" type="text" maxlength="10"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <label for="phoneNumber">Phone Number:</label>
                                                            </td>
                                                            <td>
                                                                <input class="form-control" id="phoneNumber" ng-model="address.phoneNumber"
                                                                       required="required" type="tel" pattern="^[0-9\-\+\s\(\)]*$" minlength="5"/>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>
                                                                <button ng-disabled="form.$invalid" ng-click="checkoutOrder()" type="button" class="btn btn-info">
                                                                     <span><i class="fa fa-cc-stripe"></i> Payment</span>
                                                                </button>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>

                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </script>
            </div>
        <jsp:include page="templates/footer.jsp"/>
    </body>
</html>
