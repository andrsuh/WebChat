$('.bubble-left').click(function () {
    alert("Clicked");
    $(this).toggleClass('message-clicked');
});


// $(document).ready(function() {
//     var block = document.getElementsByClassName("message-thread")[0];
//     block.scrollTop = block.scrollHeight;
// })


function getMyName() {
    var name;
    $.ajax({
        type: "GET",
        url: "/myName",
        async: false,
        cache: true,
        success: function (msg) {
            name = msg;
        }
    })
    return name;
}

$(document).ready(function () {
    var $myName = getMyName();
    alert($myName);

    var $users = [];
    var socket = new SockJS('/messages');
    stompClient = Stomp.over(socket);
    $.ajax({
        type: "GET",
        url: "/allUsers",
        dataType: 'json',
        async: false,
        cache: true,
        success: function (msg) {
            stompClient.connect({}, function (frame) {
                $.each(msg, function (idx, user) {
                    $users.push({'id': user.id, 'username': user.username, 'name': user.name});
                    alert('/userMessages/' + $myName + '/' + user.id);
                    stompClient.subscribe('/userMessages/' + $myName + '/' + user.id, function (greeting) {
                        $('.message-thread').empty();
                        var $result = $.grep($users, function (e) {
                            return e.id == user.id;
                        });
                        var $friendName = $result[0].name;
                        var $myName = "me";
                        $.each(jQuery.parseJSON(greeting.body), function (idx, msg) {
                            var $innerName;
                            var $sideName;
                            if (msg.srcUserID == user.id) {
                                $sideName = "bubble-left";
                                $innerName = $("<label>", {class: "message-user"}).text($friendName);
                            } else {
                                $innerName = $("<label>", {class: "message-user"}).text($myName);
                                $sideName = "bubble-right"
                            }
                            var $innerMsg = $("<p>").text(msg.content + " ");
                            var $div = $("<div>", {class: "message " + $sideName})
                                .append($innerName).append($innerMsg);
                            $(".message-thread").append($div);

                        })
                    });
                })
            });
        },
    });


    $('.user').click(function (event) {
        var id = $(this).attr('id');
        stompClient.send("/app/messages/" + id); //, {}, JSON.stringify({ 'id': id }));
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
