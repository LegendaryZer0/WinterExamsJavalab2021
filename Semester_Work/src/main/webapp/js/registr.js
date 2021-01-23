

$(document).ready(function () {
    let finData;
    $('#submit-btn').click(function () {
        let login = $('#login').val();
        let password = $('#password').val();
        let confirm = $('#confirm').val();
        let csrf = $('#_csrf_token').val();


        $.ajax({

            type: 'POST',
            url: 'register',
            data: {
                login: login,
                password: password,
                confirm: confirm,

            },
            beforeSend: function () {

                //nothing needed here
                //usually you can display a loading message
            },
            success: function (data) {
                console.log(data);
                finData=data


                //on success show something to the user
                //data is the html returned from the calling page, in your case the servlet
                //put the html in the correct place
            },
            statusCode:{
                302: function (){
                    console.log("Перевожу на профиль");
                    window.location.replace('selfProfile');
                },
                200:function (){
                    console.log("Показываю результат ajax")
                    $('#x').html(finData);
                }
            }
            /* error: function () {
                 //show some error message here
             }*/
        });
    });
});



