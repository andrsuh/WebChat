
$('.bubble-left').click(function() {
    alert("Clicked");
    $(this).toggleClass('message-clicked');
});


// $(document).ready(function() {
//     var block = document.getElementsByClassName("message-thread")[0];
//     block.scrollTop = block.scrollHeight;
// })



$(document).ready(function() {
    var $users = [];

    $.ajax( {
        type: "GET",
        url:  "/allUsers",
        dataType: 'json',
        async: false,
        cache: true,
        success: function(msg) {
            // var $a = jQuery.parseJSON(msg);
            $.each(msg, function(idx, user) {
                $users.push({'id': user.id, 'username': user.username, 'name': user.name});
            });
        },
    });

    var counter = 0;

    $('.user').click(function(event) {
        counter++;
        $('.message-thread').empty();
        var socket = new SockJS('/messages');
        stompClient = Stomp.over(socket);
        var id = $(this).attr('id');
        var $result = $.grep($users, function(e){ return e.id == id; });
        var $friendName = $result[0].name;
        var $myName = "me";
        stompClient.connect({}, function(frame) {
            stompClient.send("/app/messages", {}, JSON.stringify({ 'id': id }));
            console.log('Connected: ' + frame);
            stompClient.subscribe('/userMessages/name', function(greeting){
                alert(id);
                // alert(greeting.body);
                $.each(jQuery.parseJSON(greeting.body), function(idx, msg) {
                    var $innerName;
                    var $sideName;
                    if (msg.srcUserID == id) {
                        $sideName = "bubble-left";
                        $innerName = $("<label>", {class: "message-user"}).text($friendName);
                    } else {
                        $innerName = $("<label>", {class: "message-user"}).text($myName);
                        $sideName = "bubble-right"
                    }
                    var $innerMsg = $("<p>").text(msg.content + " " + counter);
                    var $div = $("<div>", {class: "message " + $sideName})
                        .append($innerName).append($innerMsg);
                    $(".message-thread").append($div);
                    // $(".message-thread").prepend($("<div class='message bubble-left'></div>"));

                })
            });

        });

    })

})






// $(document).ready(function() {
//     var block = document.getElementsByClassName("message-thread")[0];
//     block.scrollTop = block.scrollHeight;
//     $contentLoadTriggered = false;
//     $(".message-thread").scroll(function() {
//         if (block.scrollTop == 0) {
//             $contentLoadTriggered = true;
//             $.ajax( {
//                 type: "GET",
//                 url:  $SCRIPT_ROOT + "/user/",
//                 async: true,
//                 cache: false,
//                 success: function(msg) {
//                     var $a = jQuery.parseJSON(msg);
//                     var $innerName = $("<label>", {class: "message-user"}).text($a.usertitle);
//                     var $innerMsg = $("<p>").text($a.usermsg);
//
//                     var $div = $("<div>", {class: "message bubble-left"})
//                         .append($innerName).append($innerMsg);
//                     alert("ok");
//                     $(".message-thread").prepend($div);
//                     // $(".message-thread").prepend($("<div class='message bubble-left'></div>"));
//                     $contentLoadTriggered = false;
//                 },
//                 error: function (x, e) {
//                     alert("The call to the server side failed. " + x.responseText);
//                 }
//             });
//         }
//     });
// });
