$(document).ready( function() {

    $("#starBtn").click( function() {
        var type = $(this).text();
        $(this).prop("disabled", true);
        $.ajax({
            type: 'POST',
            url: "/project/project-fizzyo?type=" + type,
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