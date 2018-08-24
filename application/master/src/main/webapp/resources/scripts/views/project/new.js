/**
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 *
 * Author: Blair Butterworth
 */

function addMember(id, name, email, image) {
    $("#projectMemberInput").typeahead("close");
    $("#projectMemberInput").typeahead("val", "");

    $("#projectMembersListEmpty").hide();
    var memberDataId = "#dataItem" + id;

    if($(memberDataId).length === 0) {
        $("#projectMembersList").append(
            "<li id=\"listItem" + id + "\" class=\"list-group-item project-list-item\">" +
            "<img src=\"/resources/images/profile/" + image + "\" class=\"rounded-circle avatar\" alt=\"Profile picture\"/>" +
            "<span>" + name + " (" + email + ")</span>" +
            "<button type=\"button\" class=\"btn btn-outline-primary remove_button\" onclick=\"removeMember(" + id + ")\">Remove</button>" +
        "</li>"
        );

        $("#projectMembersData").append(
            "<option id=\"dataItem${id}\" value=\"${id}\" selected=\"selected\"></option>"
        );
    }
}

function removeMember(id) {
    $("#listItem" + id).remove();
    $("#dataItem" + id).remove();

    if ($("#projectMembersList li").length === 0) {
        $("#projectMembersListEmpty").show();
    }
}

$(document).ready(function() {
    var matchingUsers = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace("value"),
        queryTokenizer: Bloodhound.tokenizers.whitespace,

        remote: {
            url: "/api/users?matching=%QUERY",
            wildcard: "%QUERY"
        }
    });

    $("#projectMemberInput").typeahead(null, {
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

    $("#projectMemberInput").bind("typeahead:select", function(ev, member) {
        addMember(member.id, member.name, member.email, member.image);
    });

    $("#projectIconInput:file").change(function() {
        var files = $(this)[0].files;
        if (files.length > 0) {

            var file = files[0];
            $("#projectIconLabel").html(file.name);

            if (file.size > 1024 * 1024) {
                this.setCustomValidity("Please provide an image smaller than 1MB.");
            }
            else if (! (file.type === "image/jpeg" || file.type === "image/png" || file.type === "image/gif")) {
                this.setCustomValidity("Please provide an image smaller than 1MB.");
            }
            else {
                this.setCustomValidity("");
            }
        }
        else {
            $("#projectIconLabel").html("");
            this.setCustomValidity("");
        }
    });
});
