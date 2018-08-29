

function addUserToList(name, email, image) {
    $("#grantedPermissionList").append(
        "<li class=\"list-group-item project-list-item\" id=\"gp" + email.replace(/\./g, "").replace(/@/, "") + "\">" +
        "<img src=\"/resources/images/profile/" + image + "\" class=\"rounded-circle avatar\" alt=\"Profile picture\"/>" +
        "<span>" + name + " " + "(" + email + ")</span>" +
        "<button type=\"button\" class=\"btn btn-outline-primary remove_button float-right\" value=\"" + email + "\">Remove</button>" +
        "</li>"
    );
}

function selectMember(id, name, email, image) {
    $("#userInput").typeahead("close");
    $("#userInput").typeahead("val", "");
    var ident = $("#dropdownPermissionButton").text();
    var listId = "#gp" + email.replace(/\./g,"").replace(/@/, "");
    if($(listId).length === 0) {
        $.ajax({
            type: "POST",
            url: "/api/data/permissions/" + ident + "/add?user=" + email,
            success: function (data) {
                if (data) {
                    if ($("#grantedPermissionList li").length === 1) {
                        $("#emptyPermissionGroup").hide();
                    }
                    addUserToList(name, email, image);
                } else {
                    $("#couldNotRemovePermissionError").text("Error adding user!");
                    $("#couldNotRemovePermissionError").show();
                }
            }
        });
    }
}

function setGrantedPermissions(ident) {
    $("#couldNotRemovePermissionError").hide();
    $.ajax({
        type: "GET",
        url: "/api/data/permissions/" + ident + "/permission",
        success: function(data) {
            $("#emptyPermissionGroup").show();
            if(data.grantedPermissions.length > 0) {
                $("#emptyPermissionGroup").hide();
                $.each(data.grantedPermissions, function(i, user) {
                    addUserToList(user.name, user.email, user.image);
                });
            }
        }
    });
}

function removeGrantedPermission(ident, name, button) {
    $("#couldNotRemovePermissionError").hide();
    $.ajax({
        type: "POST",
        url: "/api/data/permissions/" + ident + "/remove?user=" + name,
        success: function(data) {
            if(data) {
                var id = "#gp" + name.replace(/\./g,"").replace(/@/, "");
                $(id).remove();
                if($("#grantedPermissionList li").length < 2) {
                    $("#emptyPermissionGroup").show();
                }
            } else {
                $("#couldNotRemovePermissionError").text("Could not remove permission!");
                $("#couldNotRemovePermissionError").show();
            }
            button.prop("disabled", false);
        }
    });
}

$(document).ready(function() {

    var currentPermission;

    var matchingUsers = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace("value"),
        queryTokenizer: Bloodhound.tokenizers.whitespace,

        remote: {
            url: "/api/privilegedusers?matching=%QUERY",
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
                `<div class="suggestion">
                    <img src="/resources/images/profile/{{image}}" class="rounded-circle avatar" alt="Profile picture"/>
                    <span><strong>{{name}}</strong> ({{email}})</span>
                </div>`)
        }
    });

    $("#userInput").bind("typeahead:select", function(ev, member) {
        selectMember(member.id, member.name, member.email, member.image);
    });

    $(".permission-dropdown-menu a").click(function (e) {
        e.preventDefault();
        var selText = $(this).text();
        $("#dropdownPermissionButton").text(selText);
        var emptyMsg = $("#emptyPermissionGroup");
        $("#grantedPermissionList").empty();
        $("#grantedPermissionList").append(emptyMsg);
        currentPermission = selText;
        $("#grantedInfoDiv").show();
        setGrantedPermissions(selText);
    });

    $("#grantedPermissionList").on("click", ".remove_button", function() {
        var name = $(this).val();
        var removeButtonClicked = $(this);
        removeButtonClicked.prop("disabled", true);
        removeGrantedPermission(currentPermission, name, removeButtonClicked);
    });

});