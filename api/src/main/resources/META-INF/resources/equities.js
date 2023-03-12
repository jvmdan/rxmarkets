function refresh() {
    $.get('api/equities', function (equities) {
        var list = '';
        (equities || []).forEach(function (equity) {
            list = list
                + '<tr>'
                + '<td>' + equity.id + '</td>'
                + '<td>' + equity.name + '</td>'
                + '<td><a href="#" onclick="deleteEquity(' + equity.id + ')">Delete</a></td>'
                + '</tr>'
        });
        if (list.length > 0) {
            list = ''
                + '<table><thead><th>Id</th><th>Name</th><th></th></thead>'
                + list
                + '</table>';
        } else {
            list = "No equities in database"
        }
        $('#all-equities').html(list);
    });
}

function deleteFruit(id) {
    $.ajax('api/equities/' + id, {method: 'DELETE'}).then(refresh);
}


$(document).ready(function () {

    $('#create-equity-button').click(function () {
        var equityName = $('#equity-name').val();
        $.post({
            url: 'api/equities',
            contentType: 'application/json',
            data: JSON.stringify({name: equityName})
        }).then(refresh)
    });

    refresh();
});
