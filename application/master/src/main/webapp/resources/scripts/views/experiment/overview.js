/**
 * Newton (c) 2018
 *
 * This work is licensed under the MIT License. To view a copy of this
 * license, visit
 *
 *      https://opensource.org/licenses/MIT
 *
 * Author: Blair Butterworth
 * Author: John Wilkie
 */

function checkComplete() {
    var experimentId = $("#experimentId").val();
    $.ajax({
        type: "GET",
        url: "/api/experiment/iscomplete?experiment=" + experimentId,
        success: function(data) {
            if (data === true) {
                location.reload();
            }
            else {
                setTimeout(checkComplete, 2000);
            }
        }
    });
}

// Builds the HTML Table out of myList.s
// Code heavily based on: https://stackoverflow.com/questions/5180382/convert-json-data-to-a-html-table
function buildHtmlTable(selector, myList) {

    var columns = addAllColumnHeaders(myList, selector);

    if(columns.length > 0) {
        $(selector).append("<tbody>");
        for (var i = 0; i < myList.length; i++) {
            var row$ = $("<tr/>");
            for (var colIndex = 0; colIndex < columns.length; colIndex++) {
                var cellValue = myList[i][columns[colIndex]];
                if (cellValue == null) cellValue = "";
                row$.append($("<td/>").html(cellValue));
            }
            $(selector).append(row$);
        }
        $(selector).append("</tbody>");
    } else {
        document.getElementById("jsonError").innerText =
            "File is not in a formatted in a manner that can be displayed.";
    }
}

// Adds a header row to the table and returns the set of columns.
// Need to do union of keys from all records as some records may not contain
// all records.
function addAllColumnHeaders(myList, selector) {
    var columnSet = [];
    var headerTr$ = $("<tr/>");

    for (var i = 0; i < myList.length; i++) {
        var rowHash = myList[i];
        for (var key in rowHash) {
            if ($.inArray(key, columnSet) === -1) {
                columnSet.push(key);
                headerTr$.append($('<th/>').html(key));
            }
        }
    }
    $(selector).append($("<thead/>").html(headerTr$));

    return columnSet;
}

function autoResize(id){
    var newheight;
    var newwidth;

    if(document.getElementById){
        newheight=document.getElementById(id).contentWindow.document.body.scrollHeight;
        newwidth=document.getElementById(id).contentWindow.document.body.scrollWidth;
    }

    document.getElementById(id).height= (newheight) + "px";
    document.getElementById(id).width= (newwidth) + "px";
}

$(document).ready(function() {
    var outcomeTable;

    experimentExecuting = $("#experimentExecuting").val();
    if (experimentExecuting === "true") {
        setTimeout(checkComplete, 2000);
    }

    $(".dropdown-menu li a").on("click", function(){
        var selection = $(this).text();
        $.ajax({
            type: "GET",
            url: selection,
            success: function(data) {
                var obj = data;
                if(outcomeTable != null) {
                    outcomeTable.destroy();
                    $("#testOutcome thead").remove();
                    $("#testOutcome tbody").remove();
                }
                buildHtmlTable(document.getElementById("testOutcome"),obj);
                outcomeTable = $("#testOutcome").DataTable({
                    dom: "t"
                });
            }
        });
    });

});