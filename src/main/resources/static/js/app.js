var username;
var session;

function Login(){
    username = document.getElementById("nombre").value;
    session = document.getElementById("session").value;
    if (username == ""|| session == "") {
        alert("DEBE LLENAR TODOS LOS CAMPOS");
    } 
    
}
