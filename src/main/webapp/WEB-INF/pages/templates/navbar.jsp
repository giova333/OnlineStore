
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize var="loggedIn" access="isAuthenticated()" />
<sec:authorize var="isAdmin"  access="hasRole('ADMIN')"/>
<sec:authorize var="isAdminOrManager" access="hasAnyRole('ADMIN', 'MANAGER')"/>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/"><span class="glyphicon glyphicon-leaf"></span> Spring Store</a>
        </div>
       <div class="collapse navbar-collapse">
           <ul class="nav navbar-nav">
               <li class="active"><a href="/"><span class="glyphicon glyphicon-home"></span> Home</a></li>
               <li><a href="/products"><span class="glyphicon glyphicon-phone"></span> Products</a></li>
               <c:if test="${isAdmin}">
                   <li><a href="/users"><span class="glyphicon glyphicon-user"></span> Users</a></li>
               </c:if>
           </ul>
           <ul ng-app="cartApp" class="nav navbar-nav navbar-right">
               <c:choose>
                   <c:when test="${loggedIn}">
                       <li>
                           <form action="/logout" method="post" role="form" class="navbar-form">
                               <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                               <button type="submit" class="btn btn-link">
                                   <span class="glyphicon glyphicon-log-out"></span> Logout
                               </button>
                           </form>
                       </li>
                   </c:when>
                   <c:otherwise>
                       <li><a href="/signup"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
                       <li><a href="/login"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
                   </c:otherwise>
               </c:choose>
               <li ng-controller="CartController"><a href="/cart"><span class="glyphicon glyphicon-shopping-cart"></span>
                   Shopping Cart <span id="cartItems" ng-show="getTotalQuantity() > 0">{{getTotalQuantity()}}</span></a></li>
           </ul>
       </div>
    </div>
</div>
