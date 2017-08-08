$(document).ready(function() {
    $("#registration").validate({

        errorClass: "has-error",
        validClass: "msg",

        rules: {
            firstName: {
                required : true,
                maxlength: 20
            },

            lastName: {
                required : true,
                maxlength: 20
            },

            email: {
                required : true,
                email: true
            },

            username: {
                required : true,
                minlength: 5,
                maxlength: 20
            },

            password: {
                required : true,
                minlength: 4,
                maxlength: 20
            },

            confirmPassword: {
                required : true,
                equalTo: "#password"
            }
        },

        messages: {
            firstName: {
                required : "Firstname is required",
                maxlength: "Max length is 20 characters"
            },

            lastName: {
                required : "Lastname is required",
                maxlength: "Max length is 20 characters"
            },

            email: {
                required : "Email is required",
                email: "Incorrect email address"
            },

            username: {
                required : "Username is required",
                minlength: "Your username must be at least 5 characters long",
                maxlength: "Your username must be less then 20 characters long"
            },

            password: {
                required : "Please provide a password",
                minlength: "Your password must be at least 4 characters long",
                maxlength: "Your password must be less then 20 characters long"
            },

            confirmPassword: {
                required : "Repeat password",
                equalTo: "Password doesn't match"
            }
        },

        submitHandler: function(form) {
            form.submit();
        }
    });
});
