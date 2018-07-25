$(document).ready(function() {
    var currentDsId;

    $('input[type="radio"][name="expTypeRadio"]').click(function() {
        if($(this).attr('id') == "jupyterRadio") {
            $('#notebookTypes').show();
        } else {
            $('#notebookTypes').hide();
        }
    });

    $(".dropdown-menu a").click(function(e) {
        e.preventDefault();
        var selText = $(this).text();
        $("#dropdownDsButton").text(selText);
        currentDsId = $(this).attr("value");
        var default_loc = "data/" + selText.split(' ').join('_').toLowerCase() + ".csv" // temporary
        $("#experimentDsLoc").val(default_loc);
    });

    $("#addDsBtn").click(function() {
        var dsLoc = $("#experimentDsLoc").val();
        var dsName = $("#dropdownDsButton").text();
        if(dsLoc.length <= 0){
            console.log("no location"); //convert to validation error
        } else {
            if ($("#dsListEmpty").is(":visible")) {
                $("#dsListEmpty").hide();
            }

            if ($("#ds" + currentDsId).length != 0) {
                console.log("duplication"); //convert to validation error
            } else {
                $("#dsList").append(
                    "<li class=\"list-group-item project-list-item\" id=\"ds" + currentDsId + "\">" +
                        "<p class=\"float-left\">Name: " + dsName + "<br />Location: " + dsLoc + "</p>" +
                        "<button type=\"button\" class=\"btn btn-outline-primary remove_button float-right\" onclick=\"removeDs(" + currentDsId + ")\">Remove</button>" +
                    "</li>"
                );
            }
        }
    });
});


function removeDs(id) {
    console.log("id: " + id);
    var idString = "#ds" + id;
    $(idString).remove();

    if($("#dsList li").length < 1) {
        $("#dsListEmpty").show();
    }
}
