
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
        });
        
        $('#registrarUsuario').click(function(){
            var user = {userName: $('#nombre'), userId: 0, color: "#ffffff"}
            nuevoUsuario(user);
        })
        
        actualizarLista();
    };
    
    var nuevaSesion = function (session){
        LoginRestController.newSession(session, {
            onSuccess: function (payload) {
                actualizarLista();
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
                console.log(payload);
            },
            onFailed: function(error){
                console.error(error);
                alert("Fallo");
            }
        });
        
    };
    
    var nuevoUsuario = function (user){
        LoginRestController.newUser(user,{
            onSuccess: function (){
                alert("Se registro satisfactoriamente")
            },
            onFailed: function (error) {
                console.error(error);
                alert("Fallo");
            }
        });
    };
    


    return {
        init: init
    }

})();













