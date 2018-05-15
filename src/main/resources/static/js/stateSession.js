var StateSessionControllerModule = (function () {

    var session = null;
     

    var init = function () {
        
        session = Number(sessionStorage.getItem("sesion"));
        
        console.info('Connecting...');
        var socket = new SockJS('/stompendpoint');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            stompClient.subscribe('/topic/' + session + '/users', function(eventbody){
                var usuarios = JSON.parse(eventbody.body);
                actualizarListaUsuarios(usuarios);
            }
                    );
        });
        
        $('#abandonarS').click(function(){
            eliminarUsuarioDeSesion();
        }
                );


       
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
                .css({"color": "black", "font-size": "20px"})
                .text("Sesion: " + sessionStorage.getItem("nombreSesion"));
    };
    
    var eliminarUsuarioDeSesion = function (){
        var token = sessionStorage.getItem("token");
        console.log(token);
        RestMockerController.dissociateUser(token,{
             onSuccess: function (payload) {
                 console.window.alert("Se ha desconectado de la sesion: " + session);
                 window.location.href = "index.html";
            },
            onFailed: function (error) {
                console.error(error.response.data);
                alert(error.response.data);
            }
        });
    };

    return {
        init: init
    };
})();

