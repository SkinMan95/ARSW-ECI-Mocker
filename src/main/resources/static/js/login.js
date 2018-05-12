
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

        });
        
        $('.newsession').hide();
        
        $('#session').click(function(){
            if ($('#session>option:selected').val() == "crearNuevo") {
                $('.newsession').show();
            } else {
                $('.newsession').hide();
            }
        });
        
        $('#registrar').click(function(){
            nuevaSesion($('#nSesion').val());
        })
        
        actualizarLista();
    };
    
    var nuevaSesion = function (session){
        LoginRestController.newSession(session, {
            onSuccess: function (payload) {
                actualizarLista();
                alert("EXITO");
            },
            onFailed: function (error) {
                console.error(error);
                alert("Fallo");
            }
        });
    };
    
    var actualizarLista = function () {
        LoginRestController.getSessions({
            onSuccess: function (payload){
                var lista = payload.data;
                for(i in lista){
                    $('#session')
                            .append($('<option>')
                                     .attr('value', lista[i].sessionID)
                                     .text(lista[i].sessionName)
                                   );
                }
                alert("EXITO");
                console.log(payload);
            },
            onFailed: function(error){
                console.error(error);
                alert("Fallo");
            }
        });
        
    };

    return {
        init: init
    }

})();













