$(document).ready(function () {

    function showSelect(id, selectValues) {
        $.each(selectValues, function(key, value) {
            $('#' + id)
                .append($("<option></option>")
                    .attr("value", key)
                    .text(value));
        });
    }

    function getArray(aim, name) {
        var array = [];
        $.ajax({
            type: "GET",
            url: aim + name ,
            dataType: 'json',
            async: false,
            cache: true,
            success: function (elems) {
                $.each(elems, function(idx, elem) {
                    array.push(elem.name);
                })
            }
        })
        return array;
    }
    
    function sendUser(user) {
        $.ajax({
            type: "POST",
            url: '/newUser' ,
            dataType: 'json',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            data: user,
            async: false,
            cache: true,
            // success: function (elems) {
            //
            // }
        })
        $('#login').hide();
        $('#password').hide();
        $('#name').hide();
        $('#department').hide();
        $('#position').hide();
        $('#sexField').hide();
        $('#trueSubmit').hide();
        $('#reset').hide();
        $('#about').hide();
        $('#w').hide();
        $('#c').hide();

        var $msg1 = $("<p align='center'>").text("Congratulations! Your registration is completed");
        var $msg2 = $("<p align='center'>").text("Please log in.");
        $("#regField").append($msg1).append($msg2);
        
    }
    var $org;
    $('#falseSubmit').click(function () {
        $org = $('#selectOrganisation option:selected').text();
        $selectPositions = getArray("/positions/", $org);
        $selectDepartments = getArray("/departments/", $org);
        $('#organisation').hide();
        showSelect(id='selectDepartment', $selectDepartments);
        showSelect(id='selectPosition', $selectPositions);
        $('#department').show("slow");
        $('#position').show("slow");
        $('#falseSubmit').hide();
        $('#trueSubmit').show();
//     <p align="center">Connect with colleagues and the world around you.</p>


    })


    $('#trueSubmit').click(function () {
        $enterMsg = "Please, enter ";
        if (!$('#login').val()) {
            alert($enterMsg + "login");
        } else if (!$('#password').val()) {
            alert($enterMsg + "password");
        } else if (!$('#name').val()) {
            alert($enterMsg + "name");
        } else {
            $login = $('#login').val();
            $pwd = $('#password').val();
            $name = $('#name').val();
            $dep = $('#selectDepartment option:selected').text();
            $pos = $('#selectPosition option:selected').text();
            $sex = $('#sex option:selected').text();
            $user = {
                'username': $login,
                'name': $name,
                'password': $pwd,
                'organisation' : $org,
                'department' : $dep,
                'position' : $pos,
                'sex' : $sex
            };
            sendUser(JSON.stringify($user));
        }

    })


})