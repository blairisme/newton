$(document).ready( function() {

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

});