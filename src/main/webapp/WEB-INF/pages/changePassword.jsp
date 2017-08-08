<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <jsp:include page="templates/header.jsp"/>
    <body>
        <jsp:include page="templates/navbar.jsp"/>
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-lg-offset-3">
                    <div class="jumbotron jumbotron-margin">
                        <form class="form-signin" id="reset_password" method="POST">
                            <h4 class="form-heading text-left">Use the form below to change your password.</h4>
                            <div class="form-group">

                                <span class="has-error text-center">${tokenError}</span>

                                <input name="password" type="password" id="password" class="form-control"
                                       placeholder="New Password" autofocus="true"/>

                                <input name="repeatPassword" type="password" id="repeatPassword" class="form-control"
                                       placeholder="Repeat Password"/>

                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                                <input type="hidden" name="principal_Id" value="${principalId}"/>
                                <c:choose>
                                    <c:when test="${passwordReset eq 'false'}">
                                        <button class="btn btn-lg btn-primary btn-block" type="submit" disabled>
                                            <span class="glyphicon glyphicon-envelope"></span>
                                            Change Password
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn btn-lg btn-primary btn-block" type="submit">
                                            <span class="glyphicon glyphicon-envelope"></span>
                                            Change Password
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="templates/footer.jsp"/>
    </body>
</html>
