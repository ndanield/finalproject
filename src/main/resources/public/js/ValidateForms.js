/*
$(function () {
    $("form[name='registerForm']").validate({
       rules: {
           firstName: "required",
           lastName: "required",
           password: {
               required: true,
               minlength: 5
           }
       },
        messages: {
            firstName: "Introduce tu nombre",
            lastName: "Introduce tu apellido",
            password:{
                required: "Debe crear una contraseña",
                minlength: "La contraseña debe contar con por lo menos 5 caracteres"
            }
        },
        submitHandler: function (form) {
            form.submit();
        }
    });
});*/

$(function () {
    $("#name_error_message").hide();
    $("#username_error_message").hide();
    $("#password_error_message").hide();
    $("#confPasswd_error_message").hide();
    $("#bornDate_error_message").hide();
    $("#city_error_message").hide();
    $("#name_error_message").hide();
});