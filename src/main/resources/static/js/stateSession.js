var StateSessionControllerModule = (function () {

    var session = null;

    var init = function () {
        // console.assert(session != null);
        var session = Number(sessionStorage.getItem("sesion"));
        RestMockerController.getConnectedUsers(session, {
            onSuccess: function (payload) {
                actualizarListaUsuarios(payload.data);
                actualizarSesion();
            },
            onFailed: function (error) {
                console.error(error.response.data);
                alert(error.response.data);
            }
        });
    };

    var actualizarListaUsuarios = function (usuarios) {
        for (i in usuarios) {
            var color = "#" + usuarios[i].color;
            $('#usuariosConectados')
                    .append($('<button>')
                            .attr("class", "btn")
                            .css({"background-color": color})
                            .css({"margin": "5px"})
                            .css({"width": "40px", "height": "40px"})
                            .attr("title", usuarios[i].userName)
                            );
        }
    };

    var actualizarSesion = function () {
        console.log(sessionStorage.getItem("nombreSesion"));
        $('#sesionN')
                .css({"color":"black"})
                .text("Sesion: " + sessionStorage.getItem("nombreSesion"));
    };

    return {
        init: init
    }
})();

