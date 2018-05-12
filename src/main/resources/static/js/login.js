
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
            $('.newsession').hide();
        });
        
        $('#registrarUsuario').click(function(){
            var user = {userName: $('#nombre').text(), userId: 0, color: $('#color').text()}
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
                $('#session').html('');
                $('#session')
                            .append($('<option>')
                                     .attr('value', "")
                                   );
                $('#session')
                            .append($('<option>')
                                     .attr('value', "crearNuevo")
                                     .text("Crear sesion")
                                   );
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
        //console.log(user);
        LoginRestController.newUser(user,{
            onSuccess: function (payload){
                console.log(payload.data);
                alert("Se registro satisfactoriamente");
            },
            onFailed: function (err) {
                console.error(err);
                alert("Fallo");
            }
        });
    };
    
    
    


    return {
        init: init
    }

})();













