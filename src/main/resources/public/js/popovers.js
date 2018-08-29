$(document).ready(function() {
    // $('#friendRequestPopover').popover({
    //     html: true,
    //     content: function() {
    //         return $('#popover_content_wrapper').html();
    //     }
    // });

    $('#notificationPopover').popover({
       html: true,
       content: function () {
           return $('#popover2_content_wrapper').html();
       }
    });
});
