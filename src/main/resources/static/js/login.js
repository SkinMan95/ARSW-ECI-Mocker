
var LoginControllerModule = (function () {

    var init = function () {

        $('#login').click(function () {
            var user = $("#nombre").val();
            var session = $('#session>option:selected').text();
            if (user === "" || session === "") {
                alert("DEBE LLENAR TODOS LOS CAMPOS");
            }
            console.log("USUARIO: " + user);
            console.log("SESION: " + session);
            
            tokenUsuario();

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
            var user = {userName: $('#nombreR').val(), userId: 0, color: $('#color').val()};
            nuevoUsuario(user);
        });
        
        actualizarLista();
    };
    
    var nuevaSesion = function (session){
        LoginRestController.newSession(session, {
            onSuccess: function (payload) {
                actualizarLista();
            },
            onFailed: function (error) {
                console.error(error.response.data);
                alert(error.response.data);
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
                console.error(error.response.data);
                alert(error.response.data);
            }
        });
        
    };
    
    var nuevoUsuario = function (user){
        LoginRestController.newUser(user,{
            onSuccess: function (payload){
                console.log(payload.data);
                alert("Se registro satisfactoriamente");
            },
            onFailed: function (error) {
                console.error(error.response.data);
                alert(error.response.data);
            }
        });
    };
    
    var tokenUsuario = function (){
        var user = {userName: $('#nombre').val(), userId: 0, color: $('#color').val()};
        var sesion = $('#session>option:selected').val();
        LoginRestController.tokenUser(sesion, user,{
            onSuccess: function(payload){
               sessionStorage.setItem("usuario",JSON.stringify(user));
               sessionStorage.setItem("token",payload.data);
               sessionStorage.setItem("sesion",$('#session>option:selected').val());
               sessionStorage.setItem("nombreSesion",$('#session>option:selected').text());
               window.location.href="canvas.html"
           
            },
            onFailed: function (error){
               console.error(error.response.data);
                alert(error.response.data); 
            }
        });
    };
    
    
    


    return {
        init: init
    };

})();













