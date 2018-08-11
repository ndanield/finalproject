$(document).ready(function() {
    $('#friendRequestPopover').popover({
        container: 'body',
        html: true,
        content: function() {
            return $('#popover_content_wrapper').html();
        }
    });
});