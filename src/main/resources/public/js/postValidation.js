$(document).ready(function () {
    $('#postForm').validate({
        rules: {
            postContent: 'required',
        },
        messages: {
            postContent: 'Debes escribir algo.'
        }
    })
})

