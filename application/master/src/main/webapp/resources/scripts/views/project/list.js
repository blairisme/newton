$(document).ready( function() {
    // Create data table
    var dTable;

    dTable = $("#projectList").DataTable({
        "bLengthChange": false,
        "iDisplayLength": 10,
        "bFilter": true,
        "searching": true,
        "order": [[2, "desc"]],
        dom: "tp"
    });

    // Update icons on page change
    dTable.on("draw", function () {
        setIcons();
    });

    // Data table order when drop down changed
    $(".dropdown-menu li a").on("click", function(e){
        e.preventDefault();
        var selText = $(this).text();
        $(this).parents(".dropdown").find(".btn").html(selText+"<span class=\"caret\"></span>");
        switch(selText){
            case "Most recently updated":
                dTable.order([2, "desc"]).draw();
                break;

            case "Least recently updated":
                dTable.order([2, "asc"]).draw();
                break;

            case "Project name A-Z":
                dTable.order([1, "asc"]).draw();
                break;

             case "Project name Z-A":
                 dTable.order([1, "desc"]).draw();
                break;
        }
    });

    // data tables filter
    $("#filterInput").on("keyup", function(){
        dTable.search($(this).val()).draw();
    });

    $(document).on("click", ".starIcon", function() {
        var image = $(this);
        var type = (image.hasClass("star") ? "Unstar" : "Star");
        image.prop("disabled", true);
        $.ajax({
            type: "POST",
            url: "/project/" + this.id + "?type=" + type,
            success(data) {
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


