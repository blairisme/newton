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

$(document).ready(function() {
    var matchingUsers = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('value'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,

        remote: {
            url: '/api/user?matching=%QUERY',
            wildcard: '%QUERY'
        }
    });

    $('#projectMemberInput').typeahead(null, {
        name: 'users',
        display: 'name',
        minLength: 3,
        source: matchingUsers,
        templates: {
            empty: ['<span class="empty-message">No matching users</span>'].join('\n'),
            suggestion: Handlebars.compile('<div> <img src="/resources/images/profile.jpg" class="rounded-circle avatar" alt="Profile picture"/> <span><strong>{{name}}</strong> ({{email}})</span></div>')
        }
    });

    $('#projectMemberInput').bind('typeahead:select', function(ev, member) {
        addMember(member.id, member.name, member.email);
    });
});

function addMember(id, name, email) {
    $('#projectMemberInput').typeahead('close');
    $('#projectMemberInput').typeahead('val', '');

    $('#projectMembersListEmpty').hide();

    $('#projectMembersList').append(
        `<li id="listItem${id}" class="list-group-item project-list-item">
            <img src="/resources/images/profile.jpg" class="rounded-circle avatar" alt="Profile picture"/>
            <span>${name} (${email})</span>
            <button type="button" class="btn btn-outline-primary remove_button" onclick="removeMember(${id})">Remove</button>
        </li>`
    );

    $('#projectMembersData').append(
        `<option id="dataItem${id}" value="${name}" selected="selected"></option>`
    );
}

function removeMember(id) {
    $(`#listItem${id}`).remove();
    $(`#dataItem${id}`).remove();

    if ($('#projectMembersList li').length == 0) {
        $('#projectMembersListEmpty').show();
    }
}