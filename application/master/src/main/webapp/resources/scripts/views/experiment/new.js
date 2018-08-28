function addDs(elementId, dataSourceId, dataSourceName, dataSourceLocation) {
    $("#dsList").append(
        "<li class=\"list-group-item project-list-item\" id=\"ds" + elementId + "\">" +
        "<p class=\"float-left\">Name: " + dataSourceName + "<br />Location: " + dataSourceLocation + "</p>" +
        "<button type=\"button\" class=\"btn btn-outline-primary remove_button float-right\" value=\"" + elementId + "\">Remove</button>" +
        "</li>"
    );

    $("#experimentDsDataIds").append(
        "<option id=\"dsId" + elementId + "\" value=\"" + dataSourceId +"\" selected=\"selected\"></option>"
    );

    $("#experimentDsDataLoc").append(
        "<option id=\"dsLoc" + elementId + "\" value=\"" + dataSourceLocation +"\" selected=\"selected\"></option>"
    );
}

function removeDs(elementId) {
    var idString = "#ds" + elementId;
    var id = "#dsId" + elementId;
    var loc = "#dsLoc" + elementId;

    $(idString).remove();
    $(id).remove();
    $(loc).remove();

    if($("#dsList li").length < 1) {
        var dsListEmpty = $("#dsListEmpty");
        dsListEmpty.show();
        dsListEmpty.prop("hidden", false);
    }
}

$(document).ready(function() {
    window.globalElementCounter = 0;

    $(".ds-dropdown-menu a").click(function(e) {
        e.preventDefault();

        var selText = $(this).text();
        $("#dropdownDsButton").text(selText);

        var selVal = $(this).attr("value");
        $("#experimentDsLoc").attr("value", selVal);

        var defaultLoc = "data/" + selVal.split(" ").join("_").toLowerCase();
        defaultLoc = defaultLoc.endsWith(".csv") ? defaultLoc : defaultLoc + ".csv";
        $("#experimentDsLoc").val(defaultLoc);
    });

    $("#addDsBtn").click(function() {
        var dataSourceLocation = $("#experimentDsLoc").val();
        var dataSourceName = $("#dropdownDsButton").text();
        var dataSourceId = $("#experimentDsLoc").attr("value");
        var elementId = window.globalElementCounter.toString();
        window.globalElementCounter = window.globalElementCounter + 1;

        if (dataSourceLocation.length <= 0) {
            Console.log("no location"); //convert to validation error
        } else {
            if ($("#dsListEmpty").is(":visible")) {
                $("#dsListEmpty").hide();
            }

            if ($("#ds" + dataSourceName).length !== 0) {
                Console.log("duplication"); //convert to validation error
            } else {
                addDs(elementId, dataSourceId, dataSourceName, dataSourceLocation);
            }
        }
    });

    $("#dsList").on("click", ".remove_button", function() {
        var elementId = $(this).val();
        removeDs(elementId);
    });

});


