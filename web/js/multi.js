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
var reponseUser = -1, bonneReponse;
var againstLoser = false;

function init() {

    var hostname = $('script')[$('script').length-3]['src'].match(/http:\/\/(.+)\:/)[1];
    var hostname = $('script')[$('script').length-3]['src'].match(/http:\/\/(.+)\:(.+)\//)[1];

    // Connexion à socket.io
    socket = io.connect('http://'+hostname+':8000');

    // Gestion des evenements
    setEventHandlers();

    window.clearTimeout(refresh);

    // On demande le pseudo a l'utilisateur, on l'envoie au serveur et on l'affiche dans le titre
    $("#game").html("<h2>Jeu multijoueur</h2>"
    +"<p>Merci de rentrer un nom ou pseudonyme :</p>"
    +"<input type=\"text\" id=\"pseudo\" placeholder=\"Nom ou pseudonyme\" /><input type=\"submit\" id=\"start\" value=\"Valider\" />");
    $("#start").on("click", function() {
      pseudo = $("#pseudo").val();
      socket.emit('nouveau', pseudo);
      document.title = $("#pseudo").val() + ' - ' + document.title;
      $("#game").html("Recherche d'un adversaire...");
    });
    $("#pseudo").on('keypress', function(event) {
      if(event.which == 13) {
        pseudo = $("#pseudo").val();
        socket.emit('nouveau', pseudo);
        document.title = $("#pseudo").val() + ' - ' + document.title;
        $("#game").html("Recherche d'un adversaire...");
      }
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
  socket.on("qpass", function(gotAPoint) {
    reponseUser = -1;
    againstLoser = gotAPoint;
    checkAnswer();
  })
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
var timing = 5, secRestantes = 0, timer;
var nbQuestions = 0, currentQuestion = 1;
var baseWidth;

var score = 0;
var canClick = true;

function loadCat(id) {
  if(id === 0) category = json.cat1;
  if(id === 1) category = json.cat2;
  $("#game").html("<div id=\"timer\"></div>");
  $("#game").append("<div id=\"category\">Catégorie : "+category.nom_cat+"</div>");
  $("#game").append("<div id=\"theme\"></div>");
  loadTheme(id_theme);
}

function loadTheme(id) {
  theme = category.themes[id];
  $("#theme").html("<p id=\"question\"></p>");
  $("#theme").append("<ul id=\"answers\"><li id=\"rep1\">"+theme.reponse1+"</li>"
    +"<li id=\"rep2\">"+theme.reponse2+"</li><li id=\"both\">Les deux</li></ul>"
    +"<p id=\"question-count\">Question 0/0</p>");
  quest(id_quest);
}

function quest(id) {
  $("#question").html(theme.questions[id].intitule);
  startTimer();
  bonneReponse = parseInt(theme.questions[id].bonneReponse);
  console.info('Question ' + currentQuestion + '/' + theme.questions.length + ' : '
    +theme.questions[id].intitule);
  $("#question-count").html("Question "+currentQuestion+"/"+nbQuestions);
  if(canClick) {
    $("#rep1").off('click');
    $("#rep1").one("click", function() { reponseUser = 1; checkAnswer(); socket.emit('nextQuestion', reponseUser == bonneReponse); });
    $("#rep2").off('click');
    $("#rep2").one("click", function() { reponseUser = 2; checkAnswer(); socket.emit('nextQuestion', reponseUser == bonneReponse); });
    $("#both").off('click');
    $("#both").one("click", function() { reponseUser = 0; checkAnswer(); socket.emit('nextQuestion', reponseUser == bonneReponse); });
  }
}

function checkAnswer() {
  canClick = false;
  $("#rep1").off('click');
  $("#rep2").off('click');
  $("#both").off('click');
  stopTimer();
  if(reponseUser == bonneReponse || againstLoser) {
    score += secRestantes+1;
    againstLoser = false;
  }
  if(score > 1) {
    $("#score").html("Score : "+score+" miams");
    $("#score-responsive").html(score+" miams");
  } else {
    $("#score").html("Score : "+score+" miam");
    $("#score-responsive").html(score+" miam");
  }
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
  canClick = true;
  currentQuestion++;
  // Dernière question du thème en cours
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
  }
}

function play(questions) {
  json = questions;

  if(json.status != 1) {
    var message;
    switch(json.source) {
      case 'PDO':
        message = "Erreur lors de la connexion à la base de donnée : "+json.message;
        break;
      case 'Connector':
        message = "Erreur de requête SQL : "
        switch(json.message) {
          case 'wrong_arg_nmbr_where':
            message += "Mauvais nombre d'arguments dans la clause WHERE.";
            break;
          case 'wrong_arg_nmbr_order_by':
            message += "Mauvais nombre d'arguments dans la clause ORDER BY.";
            break;
          case 'wrong_arg_numbr_limit':
            message += "Mauvais nombre d'arguments dans la clause LIMIT.";
            break;
          case 'unknown_arg':
            message += "Argument inconnu détecté.";
            break;
        }
        break;
      case 'Questset':
        message = "Erreur dans le chargement du jeu de questions : "
        if(json.message == 'expected_questset_array') {
          message += "Un tableau de réponses est attendu."
        }
        break;
      case 'Categorie':
        if(json.message == 'cant_find_cat') {
          message = "Erreur dans le chargement de la catégorie : Impossible de trouver la catégorie.";
        }
        break;
    }

    $("#game").addClass("error");
    $("#game").html(message);
  } else {
    json.cat1.themes.forEach(function(theme) {
      nbQuestions += theme.questions.length;
    });
    json.cat2.themes.forEach(function(theme) {
      nbQuestions += theme.questions.length;
    });
    $("#navbar .current").html("<div id=\"score\">Score : "+score+" miam</div>");
    $("#responsive-navbar .name a").css("display", "inline");
    $(".name").append("<div id=\"score-responsive\">"+score+" miam</div>");
    loadCat(id_cat);
  }
}

function startTimer() {
  $("#timer").removeClass();
  var elm = document.getElementById("timer"),
      newone = elm.cloneNode(true);
  elm.parentNode.replaceChild(newone, elm);
  $("#timer").addClass("timer");
  baseWidth = $("#timer").width();
  timer = window.setTimeout(checkAnswer, timing*1000);
}

function stopTimer() {
  window.clearTimeout(timer);
  $("#timer").addClass("pause");
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
  var str = "<h2 id=\"score\">Vous avez marqué "+score+" miams</h2>"
    +"<p id=\"registerScore\">";
  if(disconnect) {
    str += gameInfos[0]+" s'est déconnecté.";
    stopTimer();
  } else {
    str += "Votre adversaire a marqué "+scoreAdversaire+" miams.<br />Le gagnant est... ";
    if(score > scoreAdversaire) {
      str += pseudo+" (vous).";
    } else if(score < scoreAdversaire) {
      str += gameInfos[0]+".";
    } else {
      str += "personne (égalité).";
    }
  }
  $("#game").html(str+"</p>");
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
