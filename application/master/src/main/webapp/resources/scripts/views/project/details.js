$(document).ready( function() {

    var dTable = $("#experimentList").DataTable({
        "bLengthChange": false,
        "iDisplayLength": 20,
        "bFilter": true,
        "searching": true,
        "order": [[2, "desc"]],
        dom: "t"
    });

    $("#starBtn").click( function() {
        var type = $(this).text();
        var id = $("#identifier").val();
        $(this).prop("disabled", true);
        $.ajax({
            type: "POST",
            url: "/project/" + id + "?type=" + type,
            success: function(data) {
                if(type === "Star") {
                    $("#starBtn").html("Unstar");
                } else {
                    $("#starBtn").html("Star");
                }
                $("#starBtn").prop("disabled", false);
            }
        });
    });

    // data tables filter
    $("#filterInput").on("keyup", function(){
        dTable.search($(this).val()).draw();
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

            case "Experiment name A-Z":
                dTable.order([1, "asc"]).draw();
                break;

            case "Experiment name Z-A":
                dTable.order([1, "desc"]).draw();
                break;
        }
    });

});