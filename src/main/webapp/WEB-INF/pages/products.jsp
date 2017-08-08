<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize var="isAdminOrManager" access="hasAnyRole('ADMIN', 'MANAGER')"/>

<html ng-app="productApp">
 <jsp:include page="templates/header.jsp"/>
    <body>
        <jsp:include page="templates/navbar.jsp"/>
        <section>
            <div class="jumbotron">
                <div class="container">
                    <h1 class="title">Products</h1>
                    <p>All the available products in our store</p>
                </div>
            </div>
        </section>
        <span style="color:green" class="text-center awesome">${success}</span>
        <span style="color:red" class="text-center awesome">${failure}</span>
        <div ng-view></div>
       <script type="text/ng-template" id="viewProducts.htm">
           <section class="container">
               <c:if test="${isAdminOrManager}">
                   <a href="/products#!/addProduct" class="btn btn-info" role="button">
                       <span class="glyphicon glyphicon-plus"></span>
                   </a>
                   <hr/>
               </c:if>
               Category:
               <div class="btn-group">
                   <button ng-click="getVal('')" type="button" class="btn btn-info">All</button>
                   <button ng-click="getVal('PHONE')" type="button" class="btn btn-primary">Phones</button>
                   <button ng-click="getVal('TABLET')" type="button" class="btn btn-primary">Tablets</button>
                   <button ng-click="getVal('NOTEBOOK')" type="button" class="btn btn-primary">Notebooks</button>
                   <button ng-click="getVal('TV')" type="button" class="btn btn-primary">TV</button>
               </div>
               <div class="row productList">
                   <div class="col-sm-6 col-md-3" ng-repeat="product in products | filter: filter">
                       <div class="thumbnail" >
                           <div class="caption">
                               <h3>{{product.productName}}</h3>
                               <p><img class="products" ng-src="/resources/img/products/{{product.productImage}}"></p>
                               <p>{{'$' + product.productPrice}}</p>
                               <p>Available {{product.unitInStock}} units in stock</p>
                               <p><a ng-click="showDetails(product)" class="btn btn-primary" href="">
                                   <span class="glyphicon-info-sign glyphicon"></span> Details</a></p>
                           </div>
                       </div>
                   </div>
               </div>
           </section>
       </script>
       <script type="text/ng-template" id="productDetail.htm">
            <section class="container">
                <div class="row">
                    <div class="col-md-5">
                        <p><img class="single" ng-src="/resources/img/products/{{product.productImage}}"></p>
                    </div>
                    <div class="col-md-5">
                       <h3>{{product.productName}}</h3>
                        <p>{{product.productDescription}}</p>
                        <p>
                            <strong>Manufacturer</strong> : {{product.productManufacturer}}
                        </p>
                        <p>
                            <strong>Category</strong> : {{product.productType}}
                        </p>
                        <p>
                            <strong>Avalable units in stock</strong> : {{product.unitInStock}}
                        </p>
                        <h4>{{product.productPrice}} USD</h4>

                        <p><strong>Quantity:</strong> <input type="number" class="quantity text-center" value="1" ng-model="quantity" min="1"/></p>
                        <div class="btn-group">
                            <a href="/products#!/viewProducts"  class="btn btn-success">
                                <span class="glyphicon glyphicon-hand-left"></span>
                                Back
                            </a>
                        </div>
                        <div class="btn-group">
                            <button ng-click="addToCart(product, quantity)" type="button" class="btn btn-warning">
                                <span class="glyphicon glyphicon-shopping-cart"></span>
                                Add to Cart
                            </button>
                        </div>
                        <c:if test="${isAdminOrManager}">
                            <div class="btn-group">
                                <button ng-click="remove()" type="button" class="btn btn-danger">
                                    <span class="glyphicon glyphicon-remove"></span>
                                    Remove Product
                                </button>
                            </div>
                        </c:if>
                    </div>
                </div>
            </section>
        </script>
        <script type="text/ng-template" id="addProduct.htm">
            <h4 class="awesome">Add new product</h4>
            <hr/>
            <form name="form"> <!--Fake form -->
            <div class="form-horizontal addProduct">
            <div class="form-group">
                <label class="control-label col-sm-4" for="name">Name:</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="name" placeholder="Enter product name" ng-model="product.productName" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4" for="price">Price:</label>
                <div class="col-sm-8">
                    <input type="number" step="any" class="form-control" id="price" placeholder="Enter product price" ng-model="product.productPrice" required min="1">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4" for="manufacturer">Manufacturer:</label>
                <div class="col-sm-8">
                    <input type="text" class="form-control" id="manufacturer" placeholder="Enter manufacturer" ng-model="product.productManufacturer" required>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4" for="category">Category:</label>
                <div class="col-sm-8">
                    <select class="form-control" id="category" ng-model="product.productType" required>
                        <option value="PHONE">PHONE</option>
                        <option value="TABLET">TABLET</option>
                        <option value="NOTEBOOK">NOTEBOOK</option>
                        <option value="TV">TV</option>
                    </select>
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4" for="unitInStock">Unit in stock:</label>
                <div class="col-sm-8">
                    <input type="number" class="form-control" id="unitInStock" placeholder="Enter manufacturer" ng-model="product.unitInStock" required min="0">
                </div>
            </div>
            <div class="form-group">
                <label class="control-label col-sm-4" for="description">Description:</label>
                <div class="col-sm-8">
                    <textarea class="form-control" id="description" placeholder="Description" ng-model="product.productDescription" required></textarea>
                </div>
            </div>
                <div class="form-group">
                    <label class="control-label col-sm-4" for="image">Image:</label>
                    <div class="col-sm-8">
                        <input type="file" class="form:input-large" id="image" accept="image/*" placeholder="Image" file-model="image" />
                    </div>
                </div>
                <button ng-disabled="form.$invalid" ng-click="addProduct(product)" class="btn btn-success" role="button">
                    <span class="glyphicon glyphicon-plus"></span>
                    Add product
                </button>
            </div>
            </form>
        </script>

        <jsp:include page="templates/footer.jsp"/>
    </body>
</html>
