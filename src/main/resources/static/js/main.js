function addRedirectButton(whereToRedirect, id, text, row){
    const button = row.insertCell(-1);
    button.innerHTML = `<button><a href = ${whereToRedirect}?id=${id}>${text}</a></button>`
    return button.innerHTML
}

function addArrayToTable(array, table) {
    for (let i = 0; i < array.length; i++) {
        let row = table.insertRow();

        row.insertCell().textContent = array[i].id;
        row.insertCell().textContent = array[i].name;
        addRedirectButton("/api/delete", array[i].id, "delete", row)
        addRedirectButton("/edit_task.html", array[i].id, "edit", row )

    }
}

// load event
$(document).ready(function() {
    // get request
    $.get("api/list", function(data) {
        addArrayToTable(data, $("table")[0]);
    })
    .fail(function(){
        alert("SERVER ERROR")
     })
});
