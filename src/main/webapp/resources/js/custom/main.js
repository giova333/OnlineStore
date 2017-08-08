
$(document).ready(function() {
    $('#olvidado').click(function(e) {
        e.preventDefault();
        $('div#form-olvidado').removeClass("jumbotron jumbotron-margin").toggle('500');

    });
    $('#acceso').click(function(e) {
        e.preventDefault();
        $('div#form-olvidado').addClass("jumbotron jumbotron-margin").toggle('500');
    });
});
