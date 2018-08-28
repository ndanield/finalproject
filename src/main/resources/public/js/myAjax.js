$('form.ajax').off().on("submit", function (e) {

    var url = $(this).attr("action");
    var id = $(this).data("id");
    var method = $(this).attr("method");
    var type = $(this).data("type")

    var data = {
        id: id,
        type: type
    }

    $.ajax({
        url: url,
        type: method,
        data: data,
        success: function (data) {
            var counts = data.split(',');
            $('#badge-like' + id).html(counts[0]);
            $('#badge-dislike' + id).html(counts[1]);

            console.log("Votaste");
        },
        error: function () {
            alert("TT algo salio mal en tú voto.");
        }
    })

    e.preventDefault();
});