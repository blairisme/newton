
var colors = ["#00467d","#c3f73a","#95e06c","#68b684","#1c1018","#a8dadc","#457b9d","#e63946","#721817","#fa9f42","#541388", "#ffd400", "#d90368"];

function getBgColor(letter) {
    var index = letter.charCodeAt(0);
    index = index % colors.length;
    return colors[index];
}

function setIcons() {
    $(".default-icon-text").each(function( ) {
        $(this).css("background-color", getBgColor($(this).text()));
    });
}

$(document).ready( function() {
   setIcons();
});