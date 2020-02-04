function setValueTitleBefore(data) {
    const url_string = window.location.href;
    const url = new URL(url_string);
    const c = url.searchParams.get("id");
    document.getElementById("titleBefore").value = data[c];
}

function setValueId() {
    const url_string = window.location.href;
    const url = new URL(url_string);
    const c = url.searchParams.get("id");
    document.getElementById("value").value = c;
}

$(document).ready(function() {
 // get request
    $.get("api/list", function(data) {
        setValueTitleBefore(data);
        setValueId();

    })
    .fail(function(){
        alert("SERVER ERROR")
    });
});