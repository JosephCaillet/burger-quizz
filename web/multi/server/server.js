var io = require('socket.io'); // Chargement du module pour mettre en place les websockets
var http = require('http');
var json;
// Variables
var server; // Le socket

var lobby = [];
var games = [];
// Gestion des evenements
// Attend l'évènement "connection"
// Le client génère cet évènement lorsque la connexion est établie avec le serveur (voir l'établissement de connexion côté client)
// En cas de connexion appel à la fonctione onSocketConnection
// Un paramètre est envoyé en paramètre de l'évènement "connection" : ce paramètre représente le client
var setEventHandlers = function() {
    server.sockets.on("connection", onSocketConnection);
};


function apiReq() {
  http.get("http://localhost/burger-quizz/web/api/", function(res) {
    var data = "";
    res.on("data", function(returned) {
      data += returned;
    })
    res.on("end", function() {
      json = JSON.parse(data.toString());
    })
  });
}
// Fonction prenant en paramètre le client (voir ci-dessus)
// Réception ou envoi d'évènement à partir de cet objet : client
function onSocketConnection(client) {

    // Attente de l'évènement "new"
    // Dans cet exemple l'évènement "new" est envoyé avec un paramètre "pseudo"
    client.on('nouveau', function(pseudo) {
        // Log pour debug
        console.log('Nouveau joueur : '+ pseudo +' !');
        // Envoi d'un message au client
        //client.emit('message', 'bien reçu !!');

        if(lobby.length > 0) {
          games.push({joueur1: lobby[0], joueur2: {login: pseudo, socket: client}, idGame: games.length, json: ''});
          games[games.length-1].joueur1.socket.emit("game", [games[games.length-1].joueur2.login, games[games.length-1].idGame]);
          games[games.length-1].joueur2.socket.emit("game", [games[games.length-1].joueur1.login, games[games.length-1].idGame]);
          lobby = [];
        } else {
          lobby.push({login: pseudo, socket: client});
        }
        // Envoi d'un message aux autres clients connectés
        //client.broadcast.emit('autres', pseudo);
    });
    client.on('error', function(err) {
      console.log(err);
    });
    client.on('start', function(gameID) {
      if(!games[gameID].json) {
        console.log("slt");
        apiReq();
        games[gameID].json = json;
      }
      console.log(json);
      client.emit('questions', games[gameID].json);
    });

};

// Initialisation
function init() {
    // Le server temps réel écoute sur le port 8000
    server = io.listen(8000);

    // Gestion des évènements
    setEventHandlers();

};

// Lance l'initialisation
init();
