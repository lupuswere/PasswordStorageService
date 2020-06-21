$(function () {
    window.loggedInUser = '';
    $('#login-form').hide();
    $('#results').hide();
    $.ajax({
        method: 'GET',
        url: '/users/check',
        statusCode: {
            200: function(data) {
                if (data !== '') {
                    window.loggedInUser = data;
                    $('#login-status').text('You are logged in as ' + data);
                    $('#login-form').hide();
                    $('#logout').show();
                } else {
                    $('#login-form').show();
                }
            }
        }
    });

    $('#logout').click(function () {
        $.ajax({
            method: 'GET',
            url: '/users/logout',
            statusCode: {
                200: function(data) {
                    window.loggedInUser = '';
                    $('#login-status').text('');
                    $('#login-form').show();
                    $('#results').show();
                    $('#logout').hide();
                }
            }
        });
    });
});

function login(form) {
    let xhr = new XMLHttpRequest();
    xhr.open('POST', '/users/login', true);
    xhr.setRequestHeader('Content-type', 'application/json')
    xhr.onreadystatechange = function() {

    };
    xhr.send(JSON.stringify({
        "userId": form.uname.value,
        "password": form.psw.value
    }));
}

