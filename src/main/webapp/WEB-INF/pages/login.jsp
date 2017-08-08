<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <jsp:include page="templates/header.jsp"/>
    <body>
        <jsp:include page="templates/navbar.jsp"/>

        <div class="container">
            <div class="row">
                <div class="col-md-6 col-md-offset-3 text-center">
                    <div class="jumbotron jumbotron-margin" id="form-olvidado">
                        <form class="form-signin" method="POST">
                            <h2 class="title">Login</h2>

                            <div class="form-group ${error != null ? 'has-error' : ''}">

                                <span class="msg">${message}</span>

                                <span class="has-error">${wrong}</span>

                                <input name="username" type="text" class="form-control"
                                       placeholder="Username" autofocus="true"/>

                                <input name="password" type="password" class="form-control"
                                       placeholder="Password"/>

                                <span>${error}</span>

                                <label class="checkbox pull-left">
                                    <input type="checkbox" name="remember-me">
                                    Remember me
                                </label>


                                <label class="pull-right fgp">
                                    <a href="#" id="olvidado"><span class="glyphicon glyphicon-envelope"></span>
                                        Forgot your password?
                                    </a>
                                </label>

                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                <button class="btn btn-lg btn-primary btn-block" type="submit">
                                    <span class="glyphicon glyphicon-log-in"></span>
                                    Login
                                </button>

                                <h4 class="text-center"><a href="/signup">
                                    <span class="glyphicon glyphicon-user"></span>
                                    Signup
                                </a></h4>
                            </div>
                        </form>
                    </div>
                    <div style="display: none;" class="fgp-modal" id="form-olvidado">
                        <h4 class="text-left title">
                            Forgot your password?
                        </h4>
                        <form accept-charset="UTF-8" role="form" id="login-recordar" method="post" action="/forgotmypassword">
                            <fieldset>
                                 <span class="help-block text-left">
                                     Email address you use to log in to your account
                                     <br>
                                      We'll send you an email with instructions to choose a new password.
                                  </span>
                                <div class="form-group input-group">
                                     <span class="input-group-addon">
                                          @
                                     </span>
                                    <input class="form-control" placeholder="Email" name="email" type="email" required="">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                </div>
                                <button type="submit" class="btn btn-primary btn-block" id="btn-olvidado">
                                    Continue
                                </button>
                                <p class="help-block">
                                    <a class="text-muted" href="/login" id="access"><small>Account Access</small></a>
                                </p>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="templates/footer.jsp"/>
    </body>
</html>
