$('form.ajax-comment').off().on("submit", function (e) {
    var url = $(this).attr("action");
    var method = $(this).attr("method");
    var postId = $(this).find("input[type=hidden]:first").val();
    var content = $(this).find("#comment-form" + postId + ":first").val();

    console.log(content);
    console.log(url);
    console.log(method);
    console.log(postId);

    var data = {
        postId: postId,
        content: content
    };

    $.ajax({
        url: url,
        type: method,
        data: data,
        success: function (data) {
            console.log(data);
            $("#comment-table"+ postId).append(data);

            console.log("Nuevo comentario agregado");
        },
        error: function () {
            console.log("TT algo salio mal al tratar de comentar");
        }
    })

    e.preventDefault();
});
