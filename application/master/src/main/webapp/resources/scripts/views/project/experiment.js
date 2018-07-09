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
    experimentExecuting = $('#experimentExecuting').val();
    if (experimentExecuting === 'true') {
        setTimeout(checkComplete, 2000);
    }
});

function checkComplete() {
    experimentId = $('#experimentId').val();
    $.ajax({
        type: 'GET',
        url: "/api/experiment/iscomplete?experimentId=" + experimentId,
        success: function(data) {
            if (data === true) {
                location.reload();
            }
            else {
                setTimeout(checkComplete, 2000);
            }
        },
    });
}