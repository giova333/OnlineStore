<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html ng-app="userApp">
    <jsp:include page="templates/header.jsp"/>
    <body>
    <jsp:include page="templates/navbar.jsp"/>
    <div class="container">
        <div class="row">
            <div class="col-md-10 col-md-offset-1">
                <h6 class="title text-center">Users Management</h6>
                <div class="jumbotron jumbotron-margin">
                    <div ng-view></div>
                    <script type="text/ng-template" id="addUser.htm">
                        <h2 class="title text-center">Add User</h2>
                        <form name="myForm"> <!--We dont need this form, for validation only -->
                        <div class="form-group form-add">
                            <input type="text" class="form-control" placeholder="Firstname" ng-model="user.firstName" required maxlength="20"/>
                            <input type="text" class="form-control" placeholder="Lastname" ng-model="user.lastName" required maxlength="20"/>
                            <input type="text" class="form-control" placeholder="Username" ng-model="user.username" required minlength="5" maxlength="20"/>
                            <input type="password" class="form-control" placeholder="Password" ng-model="user.password" required minlength="4" maxlength="20"/>
                            <input type="email" class="form-control" placeholder="Email" ng-model="user.email" required/>
                            <br/>
                            <button ng-disabled="myForm.$invalid" ng-click="createUser()" type="button" class="btn btn-primary pull-left"> <span class="glyphicon glyphicon-plus"></span></button>
                        </div>
                        </form>
                    </script>
                    <script type="text/ng-template" id="editUser.htm">
                        <h2 class="title text-center"> Edit user </h2>
                        <form name="myForm"> <!--We dont need this form, for validation only -->
                        <div class="form-group text-center form-add">
                            <input type="hidden" ng-model="user.id"/>
                            <input type="text" class="form-control" placeholder="Firstname" ng-model="user.firstName" required maxlength="20"/>
                            <input type="text" class="form-control" placeholder="Lastname" ng-model="user.lastName" required maxlength="20"/>
                            <input type="text" class="form-control" placeholder="Username" ng-model="user.username" required minlength="5" maxlength="20"/>
                            <input type="password" class="form-control" placeholder="Password" ng-model="user.password" required minlength="4" maxlength="20"/>
                            <input type="email" class="form-control" placeholder="Email" ng-model="user.email" required/>
                            <button ng-disabled="myForm.$invalid" ng-click="saveUser(user)" type="button" class="btn btn-success pull-left">Edit</button>
                        </div>
                        </form>
                    </script>
                    <script type="text/ng-template" id="viewUsers.htm">
                        Search : <input type="text" ng-model="search"><br/><br/>
                        <a href="/users#!/addUser" class="btn btn-info add pull-right" role="button">
                            <span class="glyphicon glyphicon-plus"></span>
                            Add user</a>
                        <table class="table">
                            <thead class="thead-inverse">
                              <tr>
                                  <th>Id</th>
                                  <th>First Name</th>
                                  <th>Last Name</th>
                                  <th>Username</th>
                                  <th>Email</th>
                                  <th>Enabled</th>
                                  <th>Edit</th>
                                  <th>Remove</th>
                              </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="user in users | filter : search">
                                <th>{{user.id}}</th>
                                <td>{{user.firstName}}</td>
                                <td>{{user.lastName}}</td>
                                <td>{{user.username}}</td>
                                <td>{{user.email}}</td>
                                <td><input ng-disabled="user.username=='admin'" type="checkbox" ng-checked="user.enabled"
                                                                                ng-model="status" ng-change="updateStatus(user, status)"></td>
                                <td>
                                    <a ng-hide="user.username=='admin'" ng-click="editUser(user)" href="">
                                        <span class="glyphicon glyphicon-pencil"></span>
                                    </a>
                                </td>
                                <td>
                                    <a ng-hide="user.username=='admin'" ng-click="deleteUser(user)" href="">
                                        <span class="glyphicon glyphicon-remove" style="color:red"></span>
                                    </a>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </script>

                </div>
            </div>
        </div>
    </div>
    <jsp:include page="templates/footer.jsp"/>
    </body>
</html>