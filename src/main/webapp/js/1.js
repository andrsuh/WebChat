$('.bubble-left').click(function () {
    $(this).toggleClass('message-clicked');
});

$(document).ready(function () {
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

    function addMessage(srcID, dstID, msg) {
        var $result = $.grep($users, function (e) {
            return e.id == srcID;
        });

        var $senderName;
        if ($result.length != 0)
            $senderName = $result[0].name;
        else {
            $senderName = "me";
        }

        var $innerName;
        var $sideName;
        if (srcID == activeUserID) {
            $sideName = "bubble-left";
        } else {
            $sideName = "bubble-right"
        }
        $innerName = $("<label>", {class: "message-user"}).text($senderName);

        var $innerMsg = $("<p>").text(msg + " ");
        var $dt = new Date();
        var $time = $("<label>", {class: "message-timestamp"}).text($dt.getHours() + ":" + $dt.getMinutes());

        var $div = $("<div>", {class: "message " + $sideName})
            .append($innerName).append($time).append($innerMsg);
        $(".message-thread").append($div);
        var block = document.getElementsByClassName("message-thread")[0];
        block.scrollTop = block.scrollHeight;
    }

    $('.user').click(function (event) {
        var id = $(this).attr('id');
        $('#' + id + ' .circle').hide();
        setActive(activeUserID, id);
        activeUserID = id;
        stompClient.send("/app/messages/" + id);
    })


    $("#sendButton").click(function () {
        if ($("#msgTextArea").val()) {
            stompClient.send("/app/messages/newMessage/" + activeUserID, {}, $("#msgTextArea").val());
            addMessage(-1, activeUserID, $("#msgTextArea").val());
            $("#msgTextArea").val('');
        }
    })

    function setActive(activeUserID, id) {
        $('#' + activeUserID).toggleClass('active-user');
        $('#' + id).toggleClass('active-user');
        stompClient.send("/app/messages/" + id);
    }


    var $myName = getMyName();
    var $users = [];
    var socket = new SockJS('/messages');
    stompClient = Stomp.over(socket);
    var activeUserID;

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
                    stompClient.subscribe('/userMessages/' + $myName + '/' + user.id, function (greeting) {
                        $('.message-thread').empty();
                        $.each(jQuery.parseJSON(greeting.body), function (idx, msg) {
                            addMessage(msg.srcUserID, msg.dstUserID, msg.content);
                        })
                    });
                    stompClient.subscribe('/userMessages/newMessage/' + $myName + '/' + user.id, function (msg) {
                        var parseMsg = jQuery.parseJSON(msg.body);
                        if (parseMsg.srcUserID == activeUserID) {
                            addMessage(parseMsg.srcUserID, parseMsg.dstUserID, parseMsg.content);
                        } else {
                            $('#' + parseMsg.srcUserID + ' .circle').show();
                        }
                        $div = $('#' + parseMsg.srcUserID);
                        $div.remove();
                        $('#message-user-list-id').prepend($div);
                    })

                })
                activeUserID = $users[0].id;
                setActive(0, activeUserID);
            });

        },
    });

    $("#msgTextArea").keydown(function (event) {
        if (event.keyCode == 13 && event.ctrlKey) {
            $("#sendButton").click();
        }
    });

    $('#search').bind('input propertychange', function () {

        if (!$('#search').val()) {

        }
        $matchedFriends = [];
        var $result = $.grep($users, function (e) {
            if (e.name.toLowerCase().indexOf($('#search').val().toLowerCase()) >= 0) {
                $matchedFriends.push(e.id);
            }
        })
        $.each($users, function (idx, user) {
            $flag = 0;
            $.each($matchedFriends, function (idx2, friend) {
                if (friend == user.id) {
                    $flag = 1;
                }
            })
            if ($flag) {
                $('#' + user.id).show();
            } else {
                $('#' + user.id).hide();

            }

        })
    });

})


