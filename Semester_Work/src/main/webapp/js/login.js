
    $(document).ready(function () {
        let finData;
    $('#submit').click(function () {
        var login = $('#login').val();
        var password =$('#password').val();
        let csrf = $('#csrf-token').val();
        console.log("csr token from hidden input" +csrf)
        let chex;
        if($('#chex').is(':checked')){
            chex=true;
        }else {
            chex=false;
        }
        console.log(chex)
        let sendData ={
            login: login,
            password: password,
            isChecked: chex,

        }

        $.ajax({
            contentType: "application/json;charset=UTF-8",
            type: 'POST',
            url: 'login',
            data:JSON.stringify(sendData),

            beforeSend: function () {
                //nothing needed here
                //usually you can display a loading message
            },
            success: function (data) {
                console.log("AJAX data пришла");
                console.log(data.status);
                console.log("Статус");
                console.log(this.status);
                console.log("СтатусКод");
                console.log(this.statusCode);
                console.log("Показываю дату");
                console.log(data);
                finData=data;

            /*    if(data.status===200) {
                    console.log("Показываю результат ajax")
                    $('#x').html(data);
                }*/


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
