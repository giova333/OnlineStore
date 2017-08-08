$(document).ready(function() {
    $("#reset_password").validate({

        errorClass: "has-error",
        validClass: "msg",

        rules: {
            password: {
                required : true,
                minlength: 4,
                maxlength: 20
            },

            repeatPassword: {
                required : true,
                equalTo: "#password"
            }
        },

        messages: {
            password: {
                required : "Please provide a password",
                minlength: "Your password must be at least 4 characters long",
                maxlength: "Your password must be less then 20 characters long"
            },

            repeatPassword: {
                required : "Repeat password",
                equalTo: "Password doesn't match"
            }
        },

        submitHandler: function(form) {
            form.submit();
        }
    });
});
