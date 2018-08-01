$(document).ready(function() {

    $(".dropdown-menu a").click(function(e) {
        e.preventDefault();
        var selText = $(this).text();
        $("#dropdownDsButton").text(selText);
        //currentDsId = $(this).attr("value");
        var default_loc = "data/" + selText.split(' ').join('_').toLowerCase() + ".csv"; // temporary
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

            if ($("#ds" + dsName).length != 0) {
                console.log("duplication"); //convert to validation error
            } else {
                addDs(dsName, dsLoc);
            }
        }
    });

    $("#selectedTriggerValue1").prop("checked", true);

    $("#selectedStorageValue1").prop("checked", true);

    $("#selectedTypeValue1").prop("checked", true);

    $("#dsList").on("click", ".remove_button", function() {
        var name = $(this).val();
        removeDs(name);
    });

});


function addDs(name, loc) {
    $("#dsList").append(
        "<li class=\"list-group-item project-list-item\" id=\"ds" + name + "\">" +
        "<p class=\"float-left\">Name: " + name + "<br />Location: " + loc + "</p>" +
        "<button type=\"button\" class=\"btn btn-outline-primary remove_button float-right\" value=\"" + name + "\">Remove</button>" +
        "</li>"
    );

    $("#experimentDsDataIds").append(
        "<option id=\"dsId" + name + "\" value=\"" + name +"\" selected=\"selected\"></option>"
    );

    $("#experimentDsDataLoc").append(
        "<option id=\"dsLoc" + name + "\" value=\"" + loc +"\" selected=\"selected\"></option>"
    );
}

function removeDs(name) {
    var idString = "#ds" + name;
    var id = "#dsId" + name;
    var loc = "#dsLoc" + name;
    $(idString).remove();
    $(id).remove();
    $(loc).remove();

    if($("#dsList li").length < 1) {
        $("#dsListEmpty").show();
    }
}
