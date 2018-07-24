$(document).ready(function() {
    var memberEmail;

    var matchingUsers = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace("value"),
        queryTokenizer: Bloodhound.tokenizers.whitespace,

        remote: {
            url: "/api/user?matching=%QUERY",
            wildcard: "%QUERY"
        }
    });

    $("#userInput").typeahead(null, {
        name: "users",
        display: "name",
        minLength: 3,
        source: matchingUsers,
        templates: {
            empty: `<span class="empty-message">No matching users</span>`,
            suggestion: Handlebars.compile(
                `<div>
                    <img src="/resources/images/profile/{{image}}" class="rounded-circle avatar" alt="Profile picture"/>
                    <span><strong>{{name}}</strong> ({{email}})</span>
                </div>`)
        }
    });

    $("#userInput").bind("typeahead:select", function(ev, member) {
        memberEmail = member.email;
        selectMember(member.id, member.name, member.image, member.email);
    });

    $("#setUserRoleBtn").click( function() {
        $("#setUserRoleBtn").prop("disabled", true);
        var role = $("input[name=optradio]:checked").val();
        setUserRole(memberEmail, role);
    });

});

function selectMember(id, name, image, email) {
    $("#userInput").typeahead("close");
    $("#userInput").typeahead("val", "");

    $("#projectMembersList").append(
        `<li id="listItem${id}" class="list-group-item project-list-item">
            <img src="/resources/images/profile/${image}" class="rounded-circle avatar" alt="Profile picture"/>
            <span>${name} (${email})</span>
        </li>`
    );

    $.ajax({
        type: 'GET',
        url: "/api/userrole?username=" + email,
        success: function(data) {
            setRoleRadio(data);
        }
    });
}

function setRoleRadio(role) {
    if(role === "ADMIN") {
        $("#adminRadio").prop("checked", true);
    } else if(role === "ORGANISATIONLEAD") {
        $("#orgLeadRadio").prop("checked", true);
    } else if(role === "USER") {
        $("#userRadio").prop("checked", true);
    }
}

function setUserRole(name, role) {
    $.ajax({
        type: 'POST',
        url: "/api/updaterole?username=" + name + "&role=" + role,
        success: function(data) {
            setRoleRadio(data);
            $("#setUserRoleBtn").prop("disabled", false);
        }
    });
}