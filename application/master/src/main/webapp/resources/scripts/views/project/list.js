function findActiveDataTable(projectsTable, starredTable){
    var currentTab = $("ul.nav-tabs li a.active").text();
    var activeTable;
    if(currentTab === "Your projects"){
        activeTable = projectsTable;
    } else if (currentTab === "Starred projects") {
        activeTable = starredTable;
    }
    return activeTable;
}

$(document).ready( function() {
    // Create data tables
    var dTable, dTableStarred;

    dTable = $("#projectList").DataTable({
        "bLengthChange": false,
        "iDisplayLength": 25,
        "bFilter": true,
        "searching": true,
        "order": [[2, "desc"]],
        dom: "t"
    });

    dTableStarred = $("#projectListStarred").DataTable({
        "bLengthChange": false,
        "iDisplayLength": 25,
        "bFilter": true,
        "searching": true,
        "order": [[2, "desc"]],
        dom: "t"
    });

    // Data table order when drop down changed
    $(".dropdown-menu li a").on("click", function(e){
        e.preventDefault();
        var selText = $(this).text();
        $(this).parents(".dropdown").find(".btn").html(selText+"<span class=\"caret\"></span>");
        var tableToUpdate = findActiveDataTable(dTable, dTableStarred);
        switch(selText){
            case "Most recently updated":
                tableToUpdate.order([2, "desc"]).draw();
                break;

            case "Least recently updated":
                tableToUpdate.order([2, "asc"]).draw();
                break;

            case "Project name A-Z":
                tableToUpdate.order([1, "asc"]).draw();
                break;

             case "Project name Z-A":
                 tableToUpdate.order([1, "desc"]).draw();
                break;
        }
    });

    // data tables filter
    $("#filterInput").on("keyup", function(){
        var tableToUpdate = findActiveDataTable(dTable, dTableStarred);
        tableToUpdate.search($(this).val()).draw();
    });

    // Reset table on tab change
    $("a[data-toggle=\"tab\"]").on("shown.bs.tab", function (e) {
        var target = $(e.target).attr("href"); // activated tab
        $("#filterInput").val("");
        $("#dropdownMenuButton").html("Most recently updated"+"<span class=\"caret\"></span>");
        if(target === "Starred projects") {
            dTableStarred.search("").draw();
            dTableStarred.order([2, "desc"]).draw();
        } else {
            dTable.search("").draw();
            dTable.order([2, "desc"]).draw();
        }

    });

    $(document).on("click", ".starIcon", function() {
        var image = $(this);
        var type = (image.hasClass("star") ? "Unstar" : "Star");
        image.prop("disabled", true);
        $.ajax({
            type: "POST",
            url: "/project/" + this.id + "?type=" + type,
            success: function(data) {
                if(type === "Star") {
                    image.removeClass("no-star");
                    image.addClass("star");
                } else {
                    image.removeClass("star");
                    image.addClass("no-star");
                }
                image.prop("disabled", false);
            }
        });
    });

});


