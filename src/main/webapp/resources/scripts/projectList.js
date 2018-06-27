$(document).ready( function() {
    // Create data table
    var dTable;
    dTable = $("#projectList").DataTable({
        "bLengthChange": false,
        "bFilter": true,
        "searching": true,
        dom: "tp"
    });

    // Data table order when drop down changed
    $(".dropdown-menu li a").on("click", function(){
        var selText = $(this).text();
        $(this).parents(".dropdown").find(".btn").html(selText+'<span class="caret"></span>');
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

});

