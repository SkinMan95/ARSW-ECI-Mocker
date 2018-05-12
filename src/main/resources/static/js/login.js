
var LoginControllerModule = (function () {

    var init = function () {

        $('#login').click(function () {
            var user = $("#nombre").val();
            var session = $("#session").val();
            if (user === "" || session === "") {
                alert("DEBE LLENAR TODOS LOS CAMPOS");
            }
            console.log("USUARIO: " + user);
            console.log("SESION: " + session);

        })

    };

    return {
        init: init
    }

})();













