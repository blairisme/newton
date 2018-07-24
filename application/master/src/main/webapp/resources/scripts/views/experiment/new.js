$(document).ready(function() {
    $('input[type="radio"][name="expTypeRadio"]').click(function() {
        if($(this).attr('id') == "jupyterRadio") {
            $('#notebookTypes').show();
        } else {
            $('#notebookTypes').hide();
        }
    });
});