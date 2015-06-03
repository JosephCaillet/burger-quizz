/**************************************************
** GAME VARIABLES
**************************************************/
var socket;			// Socket
var gameInfos;

/**************************************************
** GAME INITIALISATION
**************************************************/

var disconnect = true;
var scoreAdversaire = 0;
var pseudo ='';

function init() {

    // Connexion à socket.io
    socket = io.connect('http://localhost:8000');

    // Gestion des evenements
    setEventHandlers();

    // On demande le pseudo a l'utilisateur, on l'envoie au serveur et on l'affiche dans le titre
    $("#game").html("<input type=\"text\" id=\"pseudo\" /><input type=\"submit\" id=\"start\" value=\"Valider\" />");
    $("#start").on("click", function() {
      pseudo = $("#pseudo").val();
      socket.emit('nouveau', pseudo);
      document.title = $("#pseudo").val() + ' - ' + document.title;
      $("#game").html("Recherche d'un adversare...");
    });
};

/**************************************************
** GAME EVENT HANDLERS
**************************************************/
var setEventHandlers = function() {
	socket.on("message", onMessage);
	socket.on("autres", onAutres);
  socket.on("game", onGame);
  socket.on("questions", play);
  socket.on("lolheded", endGame);
  socket.on("end", onEnd);
};

function onEnd(score) {
  disconnect = false;
  scoreAdversaire = score;
  endGame();
}

function onGame(game) {
  gameInfos = game;
  $("#game").html("Adversaire trouvé : "+game[0]+"<br />Début de la partie dans 5s.");
  window.setTimeout(function() {
    socket.emit('start', gameInfos[1])
  }, 5000);
}

function onMessage(message) {
    alert(message);
};

function onAutres(pseudo) {
    alert("Voici un nouveau joueur : " + pseudo);
};

/**************************************************
** GAME ENGINE (SORT OF)
**************************************************/

// IDs
var id_cat = 0, id_theme = 0, id_quest = 0;
// Shortcuts
var json, category, theme;
// Timer
var timing = 5, secRestantes, timer;
var baseWidth;

var score = 0;
var reponseUser = -1, bonneReponse;

function apiReq() {
  $.ajax({
    async: false,
    url: "../../api/",
    dataType: 'json',
    success: function(data) {
      json = data;
    }
  });
}

function loadCat(id) {
  if(id === 0) category = json.cat1;
  if(id === 1) category = json.cat2;
  console.log(category);
  $("#game").html("<p id=\"category\">Catégorie : "+category.nom_cat+"</p>");
  $("#game").append("<div id=\"theme\"></div>");
  $("#game").append("<div id=\"timer\" style=\"width:100%;height:20px;background:green\"></div>");
  $("#game").append("<div id=\"score\"></div>");
  loadTheme(id_theme);
}

function loadTheme(id) {
  theme = category.themes[id];
  $("#theme").html("<p id=\"question\"></p>");
  $("#theme").append("<ul id=\"answers\"><li id=\"rep1\">"+theme.reponse1+"</li>"
    +"<li id=\"rep2\">"+theme.reponse2+"</li><li id=\"both\">Les deux</li></ul>");
  quest(id_quest);
}

function quest(id) {
  $("#question").html(theme.questions[id].intitule);
  startTimer();
  bonneReponse = parseInt(theme.questions[id].bonneReponse);
  console.info('Question ' + (id_quest + 1) + '/' + theme.questions.length + ' : '
    +theme.questions[id].intitule);
  $("#rep1").off('click');
  $("#rep1").on("click", function() { reponseUser = 1; checkAnswer(); });
  $("#rep2").off('click');
  $("#rep2").on("click", function() { reponseUser = 2; checkAnswer(); });
  $("#both").off('click');
  $("#both").on("click", function() { reponseUser = 0; checkAnswer(); });
}

function checkAnswer() {
  stopTimer();
  if(reponseUser == bonneReponse) {
    score += secRestantes+1;
  }
  $("#score").html("Score : "+score);
  switch(bonneReponse) {
    case 0:   $("#rep1").addClass("wrong-answer");
              $("#rep2").addClass("wrong-answer");
              $("#both").addClass("good-answer");
              break;
    case 1:   $("#rep1").addClass("good-answer");
              $("#rep2").addClass("wrong-answer");
              $("#both").addClass("wrong-answer");
              break;
    case 2:   $("#rep1").addClass("wrong-answer");
              $("#rep2").addClass("good-answer");
              $("#both").addClass("wrong-answer");
              break;
  }
  window.setTimeout(nextQuestion, 2000);
}

function nextQuestion() {
  $("#rep1").removeClass();
  $("#rep2").removeClass();
  $("#both").removeClass();
  pauseGame();
  /*// Dernière question du thème en cours
  if((id_quest+1) == theme.questions.length)  {
    // Dernier thème de la catégorie en cours
    if((id_theme+1) == category.themes.length) {
      // Dernière catégorie
      if((id_cat+1) == 2) {
        pauseGame();
      } else {
        id_quest = 0;
        id_theme = 0;
        id_cat++;
        loadCat(id_cat);
      }
    } else {
      id_quest = 0;
      id_theme++;
      loadTheme(id_theme);
    }
  } else {
    id_quest++;
    quest(id_quest);
  }*/
}

function play(questions) {
  console.log(questions);
  json = questions;
  loadCat(id_cat);
}

function startTimer() {
  $("#timer").css("width", "100%");
  baseWidth = $("#timer").width();
  $("#timer").animate({'width' : '0%'}, timing*1000);
  timer = window.setTimeout(checkAnswer, timing*1000);
}

function stopTimer() {
  window.clearTimeout(timer);
  $("#timer").stop();
  secRestantes = Math.round($("#timer").width()/baseWidth*timing);
}

function pauseGame() {
  $("#game").html("<h2 id=\"score\">Vous avez marqué "+score+" miams</h2>"
  +"<p id=\"registerScore\">En attente de l'adversaire...</p>");
  // On indique au serveur qu'on a fini
  var options = [gameInfos[1], score];
  socket.emit('findugame', options);
}

function endGame() {
  stopTimer();
  $("#game").html("<h2 id=\"score\">Vous avez marqué "+score+" miams</h2>"
  +"<p id=\"registerScore\">");
  if(disconnect) {
    $("#game").append(gameInfos[0]+" s'est déconnecté.");
  } else {
    $("#game").append("Votre adversaire a marqué "+scoreAdversaire+" miams.<br />Le gagnant est... ");
    if(score > scoreAdversaire) {
      $("#game").append(pseudo+" (vous).");
    } else if(score < scoreAdversaire) {
      $("#game").append(gameInfos[0]+".");
    } else {
      $("#game").append("personne (égalité).");
    }
  }
  $("#game").append("</p>");
}

function addScore(userLogin, userScore) {
  $.ajax({
    async: false,
    url: "../../api/?page=palmares",
    type: "POST",
    dataType: 'json',
    data: {login: userLogin, score: userScore},
    success: function(data) {
      json = data;
    }
  });
}

function displayScores() {
  $.get("./api/?page=palmares", function(data) {
    var list = "<ol>";
    for(var i = 0; i < 10; i++) {
      list += "<li>"+data[i].login+" - "+data[i].score+"</li>";
    }
    list += "</ol>";
    $("#palmares").html(list);
  });
}
