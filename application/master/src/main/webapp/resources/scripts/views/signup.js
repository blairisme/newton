// Code based on code from https://www.codebyamir.com/blog/user-account-registration-with-spring-boot
$(document).ready(function() {

    $("#StrengthProgressBar").zxcvbnProgressBar({ passwordInput: "#passwordInput" });

    $('#passwordInput, #confirmPassword').on("keyup", function () {
        if ($("#passwordInput").val() === $("#confirmPassword").val() || $("#passwordInput").val() === "") {
            $("#confirmPassword").removeClass("is-invalid");
            $("#passwordInput").removeClass("is-invalid");
            $("#passwordMsg").hide();
            document.getElementById("submitBtn").disabled = false;
        } else {
            $("#confirmPassword").addClass("is-invalid");
            $("#passwordInput").addClass("is-invalid");
            $("#passwordMsg").show();
            document.getElementById("submitBtn").disabled = true;
        }
    });

});