var StateSessionControllerModule = (function () {

    var session = null;

    var init = function () {
        // console.assert(session != null);
        var session = Number(sessionStorage.getItem("sesion"));
        RestMockerController.getConnectedUsers(session, {
            onSuccess: function (payload) {
                actualizarListaUsuarios(payload.data);
            },
            onFailed: function (error) {
                console.error(error.response.data);
                alert(error.response.data);
            }
        });
    };

    var actualizarListaUsuarios = function (usuarios) {
        for (i in usuarios) {
            $('#usuariosConectados')
                    .append($('<button>')
                    .attr( "class", "btn btn-primary")
                    .attr("title", usuarios[i].userName)
            );
        }
    };

    return {
        init: init
    }
})();

