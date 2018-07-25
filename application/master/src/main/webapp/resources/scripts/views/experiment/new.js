$(document).ready(function() {
    var currentDsId;

    $('input[type="radio"][name="selectedTypeValue"]').click(function() {
        if($(this).attr("id") === "selectedTypeValue3") {
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
                addDs(currentDsId, dsName, dsLoc);
            }
        }
    });

    $("#selectedTriggerValue1").prop("checked", true);

    $("#selectedStorageValue1").prop("checked", true);

    $("#selectedTypeValue1").prop("checked", true);

    $("#selectedTypeValue2").prop("disabled", true);

    $("#selectedNotebookTypeValue1").prop("checked", true);

    $("#selectedNotebookTypeValue2").prop("disabled", true);
});


function addDs(id, name, loc) {
    $("#dsList").append(
        "<li class=\"list-group-item project-list-item\" id=\"ds" + id + "\">" +
        "<p class=\"float-left\">Name: " + name + "<br />Location: " + loc + "</p>" +
        "<button type=\"button\" class=\"btn btn-outline-primary remove_button float-right\" onclick=\"removeDs(" + id + ")\">Remove</button>" +
        "</li>"
    );

    $("#experimentDsDataIds").append(
        "<option id=\"dsId" + id + "\" value=\"" + id +"\" selected=\"selected\"></option>"
    );

    $("#experimentDsDataLoc").append(
        "<option id=\"dsLoc" + id + "\" value=\"" + loc +"\" selected=\"selected\"></option>"
    );
}

function removeDs(id) {
    var idString = "#ds" + id;
    var id = "#dsId" + id;
    var loc = "#dsLoc" + id;
    $(idString).remove();
    $(id).remove();
    $(loc).remove();

    if($("#dsList li").length < 1) {
        $("#dsListEmpty").show();
    }
}
