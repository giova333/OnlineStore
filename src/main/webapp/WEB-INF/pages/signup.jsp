<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <jsp:include page="templates/header.jsp"/>
    <body>
        <jsp:include page="templates/navbar.jsp"/>
        <section>
            <div class="jumbotron">
                <div class="container">
                    <h1 class="title text-center">Sign Up</h1>
                </div>
            </div>
        </section>
        <div class="container">
            <div class="row main">
                <div class="main-login main-center">
                    <form:form class="form-horizontal" method="post" action="/signup" modelAttribute="user" id="registration">
                        <div class="form-group reg">
                            <label for="firstName" class="cols-sm-2 control-label">Your Firstname</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                    <form:input path="firstName" type="text" name="firstName" id="firstName" placeholder="Firstname" autofocus="true" class="inpt"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group reg">
                            <label for="lastName" class="cols-sm-2 control-label">Your Lastname</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-user fa" aria-hidden="true"></i></span>
                                    <form:input path="lastName" type="text" name="lastName" id="lastName" placeholder="Lastname" class="inpt"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group reg">
                            <label for="email" class="cols-sm-2 control-label">Your Email</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-envelope fa" aria-hidden="true"></i></span>
                                    <form:input path="email" type="text" name="email" id="email" placeholder="Email" class="inpt"/>
                                    <span class="has-error">${duplicateEmail}</span>
                                </div>
                            </div>
                        </div>
                        <div class="form-group reg">
                            <label for="username" class="cols-sm-2 control-label">Your username</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-users fa" aria-hidden="true"></i></span>
                                    <form:input path="username" type="text" name="username" id="username" placeholder="Username" class="inpt"/>
                                    <span class="has-error">${duplicateUsername}</span>
                                </div>
                            </div>
                        </div>

                        <div class="form-group reg">
                            <label for="password" class="cols-sm-2 control-label">Your Password</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <form:input path="password" type="password" name="password" id="password" placeholder="Password" class="inpt"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group reg">
                            <label for="confirmPassword" class="cols-sm-2 control-label">Confirm Password</label>
                            <div class="cols-sm-10">
                                <div class="input-group">
                                    <span class="input-group-addon"><i class="fa fa-lock fa-lg" aria-hidden="true"></i></span>
                                    <form:input path="confirmPassword" type="password" name="confirmPassword" id="confirmPassword" placeholder="Confirm password" class="inpt"/>
                                </div>
                            </div>
                        </div>
                        <button class="btn btn-lg btn-primary btn-block" type="submit">
                            <span class="glyphicon glyphicon-user"></span>
                            Register
                        </button>
                        <h4 class="text-center"><a href="/login">
                            <span class="glyphicon glyphicon-log-in"></span>
                            Login
                        </a></h4>
                    </form:form>
                </div>
            </div>
        </div>
        <jsp:include page="templates/footer.jsp"/>
    </body>
</html>
