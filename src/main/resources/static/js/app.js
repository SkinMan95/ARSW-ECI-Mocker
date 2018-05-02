var username;
var session;
var color;

function getRandomColor() {
  var letters = '0123456789ABCDEF';
  var color = '#';
  for (var i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)];
  }
  return color;
}

function Login(){
    username = document.getElementById("nombre").value;
    session = document.getElementById("session").value;
    if (username == ""|| session == "") {
        alert("DEBE LLENAR TODOS LOS CAMPOS");
    }
    color = getRandomColor();
    
    alert('{ Nombre: ' + username + ' , ' + 'Color: ' + color + ' , ' + 'Sesion: ' + session + ' }');
    
    
}
