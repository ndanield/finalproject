$(document).ready(function () {
   $("#registerButton").on("click", function () {
       if ($("#password").val() !== $("#confirmPassword").val()) {
           showError()
       }

   })
});

function showError() {
    $("#error-in-form").html("" +
        "<div class=\"alert alert-dismissible alert-danger\">\n" +
        "<button type=\"button\" class=\"close\" data-dismiss=\"alert\">&times;</button>\n" +
        "<strong>¡Ay no!</strong> Hay campos vacios o incorrectos. Por favor, revisa el formulario.\n" +
        "</div>")
}