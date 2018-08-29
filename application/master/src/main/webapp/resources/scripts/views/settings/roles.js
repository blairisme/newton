

function setRoleRadio(role) {
    if(role === "ADMIN") {
        $("#adminRadio").prop("checked", true);
    } else if(role === "ORGANISATIONLEAD") {
        $("#orgLeadRadio").prop("checked", true);
    } else if(role === "USER") {
        $("#userRadio").prop("checked", true);
    }
}

function selectMember(id, name, image, email) {
    $("#messageSuccess").hide();
    $("#userInput").typeahead("close");
    $("#userInput").typeahead("val", "");
    $("#projectMembersList").empty();

    $("#projectMembersList").append(
        "<div>" +
            "<img src=\"/resources/images/profile/" + image + "\" class=\"rounded-circle avatar\" alt=\"Profile picture\"/>" +
            "<span>" + name + " (" + email + ")</span>" +
        "</div>"
    );

    $.ajax({
        type: "GET",
        url: "/api/userrole?username=" + email,
        success(data) {
            setRoleRadio(data, id);
            $("#selectedUser").show();
        }
    });
}

function setUserRole(email, role, name) {
    $.ajax({
        type: "POST",
        url: "/api/updaterole?username=" + email + "&role=" + role,
        success(data) {
            $("#setUserRoleBtn").prop("disabled", false);
            $("#selectedUser").hide();
            $("#projectMembersList").empty();
            var successDiv = $("#messageSuccess");
            successDiv.text("Set " + name + " to role: " + data.toLowerCase());
            successDiv.css("display", "inline-block");
        }
    });
}

$(document).ready(function() {
    var memberEmail;
    var memberName;

    var matchingUsers = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace("value"),
        queryTokenizer: Bloodhound.tokenizers.whitespace,

        remote: {
            url: "/api/users?matching=%QUERY",
            wildcard: "%QUERY"
        }
    });

    $("#userInput").typeahead(null, {
        name: "users",
        display: "name",
        minLength: 3,
        source: matchingUsers,
        templates: {
            empty: "<span class=\"empty-message\">No matching users</span>",
            suggestion: Handlebars.compile(
                "<div>" +
                "<img src=\"/resources/images/profile/{{image}}\" class=\"rounded-circle avatar\" alt=\"Profile picture\"/>" +
                "<span><strong>{{name}}</strong> ({{email}})</span>" +
                "</div>")
        }
    });

    $("#userInput").bind("typeahead:select", function(ev, member) {
        memberEmail = member.email;
        memberName = member.name;
        selectMember(member.id, member.name, member.image, member.email);
    });

    $("#setUserRoleBtn").click( function() {
        $("#setUserRoleBtn").prop("disabled", true);
        var role = $("input[name=optradio]:checked").val();
        setUserRole(memberEmail, role, memberName);
    });

});