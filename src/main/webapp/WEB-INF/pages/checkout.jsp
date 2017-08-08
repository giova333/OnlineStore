<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <jsp:include page="templates/header.jsp"/>
    <body>
        <jsp:include page="templates/navbar.jsp"/>
        <section>
            <div class="jumbotron">
                <div class="container">
                    <h1 class="title">Payment Info</h1>
                </div>
            </div>
        </section>
        <div class="container">
            <div class="row">
                <div class="col-md-6 col-md-offset-3 text-center">
                    <h1 class="title">Pay With Stripe</h1>
                    <br/>
                    <img src="/resources/img/stripe.png">
                    <br/><br/>
                    <form action="/charge" method="post" id='checkout-form'>
                        <input type="hidden" name="amount" value="${order.totalPrice * 100}"/>
                        <input type="hidden" name="orderNumber" value="${order.orderNumber}"/>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <label>Price: ${order.totalPrice}$</label>
                        <script src="https://checkout.stripe.com/checkout.js"
                                class='stripe-button'
                                data-key="${stripePublicKey}"
                                data-image="/resources/img/spring.png"
                                data-name="Spring Store"
                                data-description="Checkout"
                                data-amount="${order.totalPrice * 100}">
                        </script>
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="templates/footer.jsp"/>
    </body>

</html>
